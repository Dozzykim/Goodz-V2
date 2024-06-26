package com.springproject.goodz.utils.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springproject.goodz.utils.dto.Files;
import com.springproject.goodz.utils.mapper.FileMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    
    @Autowired
    private FileMapper fileMapper;

    @Value("${upload.path}")    // application.properties에 설정한 업로드 경로
    private String uploadPath;

    @Override
    public List<Files> list() throws Exception {
        return fileMapper.list();
    }

    @Override
    public Files select(int no) throws Exception {

        Files file = fileMapper.select(no);

        return file;
    }
    
    // 대표이미지 조회 - 부모기준
    @Override
    public Files selectMainImg(Files file) throws Exception {

        return fileMapper.selectMainImg(file);
    }

    @Override
    public int insert(Files file) throws Exception {
        return fileMapper.insert(file);
    }

    @Override
    public int update(Files file) throws Exception {
        return fileMapper.update(file);
    }

    /**
     * 파일 삭제 (시스템 상 실제파일 & DB 데이터 삭제)
     */
    @Override
    public int delete(int no) throws Exception {

        // 파일 정보 세팅
        Files file = fileMapper.select(no);

        // DB 에 있는 파일 정보 삭제
        int result = fileMapper.delete(no);

        // 파일 시스템의 파일 삭제
        if (result > 0) {
            // 미리 세팅해서 저장해둔 객체 정보로 삭제
            String filePath = file.getFilePath(); 
            File deleteFile = new File(filePath);

            // 파일 존재 확인
            if ( !deleteFile.exists() ) {
                return result;
            }

            // 파일 삭제
            if (deleteFile.delete()) {
                log.info("파일이 정상적으로 삭제되었습니다.");
            } else {
                log.info("파일 삭제에 실패하였습니다.");
            }
        }
        return result;
    }

    @Override
    public List<Files> listByParent(Files file) throws Exception {
        return fileMapper.listByParent(file);
    }

    /**
     * 게시글에 종속된 첨부파일 꺼내서 삭제 요청.
     */
    @Override
    public int deleteByParent(Files file) throws Exception {

        int result = 0;

        // 종속된 파일 리스트 꺼내기
        List<Files> fileList = fileMapper.listByParent(file);
        
        for (Files deleteFile : fileList) {
            int no = deleteFile.getNo(); // 파일번호
            result += delete(no);
        }
        return result;
    }

    @Override
    public boolean upload(Files file, String dir) throws Exception {
        uploadPath = "C:/upload"; // 이거 지우면 반복업로드할때 163행이 반복되어서 경로 이상해짐

        log.info("file: " + file);

        file.setParentTable(dir);     

        MultipartFile mf = file.getFile();
        if (mf == null || mf.isEmpty()) {
            throw new Exception("파일이 없습니다.");
        }

        // String dir = file.getParentTable();     

        // MultipartFile mf = file.getFile();
        // 파일 정보 : 원본 파일명, 파일 용량, 파일 데이터
        String originName = mf.getOriginalFilename();
        long fileSize = mf.getSize();
        byte[] fileData = mf.getBytes();

        String fileDirPath = uploadPath + File.separator + dir;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + "_" + originName;
        String filePath = fileDirPath + File.separator + fileName;
        uploadPath +=  "/" + dir;

        log.info("업로드 경로: " + uploadPath);
        // File 객체 생성 => new File(업로드 경로, 설정할 파일명);
        File uploadFile = new File(uploadPath, fileName);

        log.info("최종 업로드 경로:" + uploadFile);

        //File uploadFile = new File(filePath);
        FileCopyUtils.copy(fileData, uploadFile);

        file.setFileName(fileName);
        file.setOriginName(originName);
        file.setParentTable(dir);
        // filePath = C:/uploade/UID_원본파일명.확장자

        //String filePath = uploadPath + "/" + fileName;
        log.info("파일패쓰:" + filePath);
        file.setFilePath(filePath);
        file.setFileSize(fileSize);

        fileMapper.insert(file);

        return true;
    }

    @Override
    public Files download(int no) throws Exception {
        return fileMapper.select(no);
    }
} 

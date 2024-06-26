package com.springproject.goodz.user.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springproject.goodz.user.dto.CustomUser;
import com.springproject.goodz.user.dto.Shippingaddress;
import com.springproject.goodz.user.dto.UserAuth;
import com.springproject.goodz.user.dto.UserSocial;
import com.springproject.goodz.user.dto.Users;
import com.springproject.goodz.user.mapper.UserMapper;
import com.springproject.goodz.utils.dto.Files;
import com.springproject.goodz.utils.service.FileService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private FileService fileService;



    @Override
    public boolean login(Users user) throws Exception {
        // // 💍 토큰 생성
        String username = user.getUserId();    // 아이디
        String password = user.getPassword();    // 암호화되지 않은 비밀번호
        UsernamePasswordAuthenticationToken token 
            = new UsernamePasswordAuthenticationToken(username, password);
        
        // 토큰에 요청 정보 등록
        // token.setDetails( new WebAuthenticationDetails(request) );

        // 토큰을 이용하여 인증
        Authentication authentication = authenticationManager.authenticate(token);

        // 인증된 사용자 확인
        CustomUser loginUser = (CustomUser) authentication.getPrincipal();
        log.info("인증된 사용자 아이디 : " + loginUser.getUser().getUsername());
        boolean result = authentication.isAuthenticated();

        // // 인증 여부 확인
        // boolean result = authentication.isAuthenticated();

        // 시큐리티 컨텍스트에 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return result;
    }

    @Override
    public Users select(String username) throws Exception {
        Users user = userMapper.select(username);
        return user;
    }

    @Override
    public Users selectByNickname(String nickname) throws Exception {
        Users user = userMapper.selectByNickname(nickname);
        return user;
    }

    @Override
    public int join(Users user) throws Exception {
        String username = user.getUserId();
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);  // 🔒 비밀번호 암호화
        user.setPassword(encodedPassword);

        // 회원 등록
        int result = userMapper.join(user);

        if( result > 0 ) {
            // 회원 기본 권한 등록
            UserAuth userAuth = new UserAuth();
            userAuth.setUserId(username);
            userAuth.setAuth("ROLE_USER");
            result = userMapper.insertAuth(userAuth);
        }
        return result;
    }

    @Override
    public int updateUser(Users user) throws Exception {
        log.info("유저 " + user.getUserId() + " 정보 업데이트 처리 진행중...");

        /* ⬇️ 유저 정보 변경 처리 ⬇️ */
        String requestPw = user.getPassword();

        if (requestPw != null && requestPw != "") {
            String newPw = passwordEncoder.encode(requestPw);
            log.info("변경요청 : " + newPw);
            // 암호화된 비밀번호로 세팅
            user.setPassword(newPw);
        }

        // 🔄️ 유저 정보 업데이트
        int result = userMapper.update(user);
        log.info("처리 결과: " + result + " (0: 실패 / 1: 성공)");



        /* ⬇️ 프로필 사진 업데이트 처리 ⬇️ */
        MultipartFile newImg = user.getProfileImgFile();

        // 첨부 X
        if (newImg.isEmpty() || newImg == null ) {
            log.info("프사 변경X");
            return result;
        }

        // 첨부 O(기존 이미지 삭제 및 데이터 삭제 후 새 이미지 등록 및 데이터 추가)
        // 프로필사진 정보 가져와야해서 select 호출
        user = select(user.getUserId());

        log.info("기존 프사 삭제 처리...");
        int fileNo = user.getProfileImgNo();
        result = fileService.delete(fileNo);
        log.info("처리 결과: " + result + " (0: 실패 / 1: 성공)");
        
        log.info("새 프사 등록 처리...");
        String dir = "user";
        int parentNo = user.getNo();
        
        Files uploadFile = new Files();
        uploadFile.setParentNo(parentNo);  // 유저 번호
        uploadFile.setFile(newImg);        // 새로운 이미지
        uploadFile.setFileCode(1);

        boolean uploadcheck = fileService.upload(uploadFile, dir);

        if (uploadcheck) {
            log.info( "새로운 프사 업로드 성공...");
        }




        // Users updateUser = new Users();



        // String dir = "user";
        // int parentNo = user.getNo();
        
        // MultipartFile profileImgFile = user.getProfileImgfile();

        // if (profileImgFile.isEmpty()) {
        //     log.info("빈 파일인데?");
        // }

        // // fileService 에 매개변수로 넘길 file 객체 세팅
        // Files uploadFile = new Files();
        // uploadFile.setParentNo(parentNo);           // 유저번호
        // uploadFile.setFile(profileImgFile);           // 첨부했던 파일을 dto에 담음

        // boolean uploadcheck = fileService.upload(uploadFile, dir);
        
        // int result = 0;

        // if (uploadcheck) {
        //     log.info("프사 등록 성공");
        //     result = 1;
        // }

        return result;
    }

    @Override
    public int insertAuth(UserAuth userAuth) throws Exception {
        int result = userMapper.insertAuth(userAuth);
        return result;
    }

    @Override
    public String findId(String phone, String name) throws Exception {
        String id = userMapper.findId(phone, name);
        return id;
    }


    @Override
    public boolean check(String userId, String nickname) throws Exception {
        Integer result = userMapper.check(userId, nickname);
        System.out.println("Check Result: " + result); // 로깅 추가
        return result != null && result == 0; // null 체크 추가
    }

    public Users findPw(String username, String birth, String userId) throws Exception {
        log.info("findPw 메소드 호출: username={}, birth={}, userId={}", username, birth, userId);
        Users findMan = userMapper.findPw(username, birth, userId);
        return findMan;
    } 
    
    @Override
    public int changePw(String newPw, String userId) throws Exception {
        // 새 비밀번호를 암호화하여 업데이트
        String password = passwordEncoder.encode(newPw);
        log.info("새로운 비밀번호 암호화 결과: {}", password);
         
    try {
        int result = userMapper.changePw(password, userId);
        log.info("userMapper.changePw 결과 : {}", result);
        if(result > 0) {
            log.info("비밀번호 변경 성공");
            return result; // 성공
        } else {
            log.info("비밀번호 변경 실패");
            return 0;
        }
    } catch (Exception e) {
        log.error("비밀번호 변경 중 오류 발생" , e);
        throw e;
    }
}

    @Override
    public Users findUserByUsername(String username) throws Exception {
        return userMapper.findUserByUsername(username);
    }


    @Override
    public boolean checkPassword(String userId, String rawPassword) throws Exception {
        Users user = userMapper.select(userId);
        if (user != null) {
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    /**
     * 배송지 등록
     */
    @Override
    public int insertAddress(Shippingaddress shippingaddress) throws Exception {

        // 기본 배송지로 설정하려는 경우
        if (shippingaddress.getIsDefault()) {
            // 사용자의 모든 배송지를 가져옴
            List<Shippingaddress> shippingaddresses = userMapper.selectByUserId(shippingaddress.getUserId());
            for (Shippingaddress addr : shippingaddresses) {
                // 기존 기본 배송지를 해제
                if (addr.getIsDefault()) {
                    addr.setIsDefault(false);
                    userMapper.updateAddress(addr);
                }
            }
        }
        // 새 배송지를 추가
        return userMapper.insertAddress(shippingaddress);

    }

    /**
     * 배송지 업데이트 (기본 배송지 등록 여부 포함)
     */
    @Override
    public int updateAddress(Shippingaddress shippingaddress) throws Exception {
        // 기본 배송지 설정을 변경하려는 경우
        if (shippingaddress.getIsDefault()) {
            // 사용자의 모든 배송지를 가져옴
            List<Shippingaddress> shippingaddresses = userMapper.selectByUserId(shippingaddress.getUserId());
            for (Shippingaddress addr : shippingaddresses) {
                // 기존 기본 배송지를 해제
                if (addr.getIsDefault()) {
                    addr.setIsDefault(false);
                    userMapper.updateAddress(addr);
                }
            }
        }
        // 새 배송지 또는 업데이트된 배송지를 저장
        return userMapper.updateAddress(shippingaddress);
    }

    /**
     * 유저의 배송지 목록 조회
     */
    @Override
    public List<Shippingaddress> selectByUserId(String userId) throws Exception {
        
        return userMapper.selectByUserId(userId);
    }

    /**
     * 유저 단일 배송지 조회
     */
    @Override
    public Shippingaddress selectAddress(int addressNo) throws Exception {
        Shippingaddress shippingaddress = userMapper.selectAddress(addressNo);
        return shippingaddress;
    }

    /**
     * 주소 삭제
     */
    @Override
    public int deleteAddress(int addressNo) throws Exception {
        return userMapper.deleteAddress(addressNo);
        
    }

    // 기본 배송지 여부 확인
    @Override
    public boolean isDefaultAddress(int addressNo) throws Exception {
        Shippingaddress shippingaddress = userMapper.selectAddress(addressNo);
        return shippingaddress != null && shippingaddress.isDefault();
    }

    // 유저 계좌 등록 
    @Override
    public void insertAccount(String userId, String account) throws Exception {
        userMapper.insertAccount(userId, account);
    }


    
    @Override
    public int insertSocial(UserSocial userSocial) throws Exception {
        int result = userMapper.insertSocial(userSocial);
        return result;
    }

    @Override
    public UserSocial selectSocial(UserSocial userSocial) throws Exception {
        UserSocial selectedUserSocial = userMapper.selectSocial(userSocial);
        return selectedUserSocial;
    }

    @Override
    public int updateSocial(UserSocial userSocial) throws Exception {
        int result = userMapper.updateSocial(userSocial);
        return result;
    }

    @Override
    public Users selectBySocial(UserSocial userSocial) throws Exception {
        Users user = userMapper.selectBySocial(userSocial);
        return user;
    }
    
    

}
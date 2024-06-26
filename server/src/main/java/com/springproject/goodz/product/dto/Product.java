package com.springproject.goodz.product.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Product {
    
    private int pNo;                // 상품 번호
    private String productName;     // 상품 이름
    private int initialPrice;       // 초기 발매 가격
    private String bName;           // 브랜드 이름
    private String category;        // 상의, 하의, 신발, 악세사리
    private int views;              // 조회수
    private Date createdAt;         // 상품 등록 날짜
    private Date updatedAt;         // 상품 수정 날짜
    
    private List<ProductOption> options; // 연관된 옵션 목록
    private String imageUrl;        // 첫 번째 이미지 URL을 저장할 필드
    private int mainImgNo;             // 대표이미지 파일번호 (file_code = 1 인 파일)
    private List<MultipartFile> productFiles; // 추가된 필드

    private int minPrice; // 같은 상품 내 최저가 
    private String formattedMinPrice; // 최저가 상품을 원화 형식으로 표현하기 위한 변수

    private String size;
    private int fluctuatedPrice;
    
    // imageUrl의 Getter와 Setter
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // productFiles의 Getter와 Setter
    public List<MultipartFile> getProductFiles() {
        return productFiles;
    }

    public void setProductFiles(List<MultipartFile> productFiles) {
        this.productFiles = productFiles;
    }
}

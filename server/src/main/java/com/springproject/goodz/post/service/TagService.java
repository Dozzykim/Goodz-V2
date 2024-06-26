package com.springproject.goodz.post.service;

import java.util.List;

import com.springproject.goodz.post.dto.Post;
import com.springproject.goodz.post.dto.Tag;

public interface TagService {

    // 상품태그 리스트 조회 - 상품번호 기준
    public List<Post> taggedProduct(int productNo) throws Exception;
    
    // 상품태그 추가 - 게시글 등록 시
    public int insert(Tag tag) throws Exception;

    // 게시글에 종속된 상품태그 삭제 - 게시글 수정 시
    public int delete(int postNo) throws Exception;

    
}

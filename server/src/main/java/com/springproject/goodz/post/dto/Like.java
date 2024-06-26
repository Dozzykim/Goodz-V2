package com.springproject.goodz.post.dto;

import java.util.List;

import lombok.Data;

@Data
public class Like {
    private int likeNo;             // 좋아요 번호
    private String userId;          // 유저 아이디
    private int postNo;             // 게시글 번호
    // private Date createdAt;      // 좋아요 누른 날 6/4 데이터 가져와서 쓸 일 없을거같아 주석처리 -도희-
    // private Date updatedAt;      // 좋아요 취소 날 6/3 쓸일없을거같아 삭제함 -도희-
}

package com.springproject.goodz.user.service;

import java.util.List;

import com.springproject.goodz.user.dto.Shippingaddress;
import com.springproject.goodz.user.dto.UserAuth;
import com.springproject.goodz.user.dto.UserSocial;
import com.springproject.goodz.user.dto.Users;

public interface UserService {
    
    // 로그인
    public boolean login(Users user) throws Exception;

    // 회원 조회 - id
    public Users select(String username) throws Exception;

    // 회원 조회 - nickname
    public Users selectByNickname(String nickname) throws Exception;

    // 회원 가입
    public int join(Users user) throws Exception;

    // 회원 수정
    public int updateUser(Users user) throws Exception;

    // 회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception;

    // 아이디 찾기
    public String findId(String phone, String name) throws Exception;
    
    // 비밀번호 찾기
    public Users findPw(String name, String birth, String userId ) throws Exception;

    // 비밀번호 변경
    public int changePw(String newPw, String userId) throws Exception;

    // 회원 가입 시 아이디 중복 체크
    public boolean check(String userId, String nickname) throws Exception;

    // 유저 정보 조회
    public Users findUserByUsername(String username) throws Exception;

    // 회원 정보 수정 시 비밀 번호 확인
    public boolean checkPassword(String userId, String rawPassword) throws Exception;

    // 유저 주소 등록
    public int insertAddress(Shippingaddress shippingaddress) throws Exception;

    // 유저 주소 업데이트 (기본 배송지 등록 여부 포함)
    public int updateAddress(Shippingaddress shippingaddress) throws Exception;

    // 유저의 주소 목록
    public List<Shippingaddress> selectByUserId(String userId) throws Exception;

    // 배송지 단일 조회
    public Shippingaddress selectAddress(int addressNo) throws Exception;

    // 배송지 삭제
    public int deleteAddress(int addressNo) throws Exception;

    // 기본 배송지 여부 확인
    public boolean isDefaultAddress(int addressNo) throws Exception;

    // 유저 계좌 등록
    public void insertAccount(String userId, String account) throws Exception;

    // 소셜 회원 가입
    public int insertSocial(UserSocial userSocial) throws Exception;

    // 소셜 회원 조회
    public UserSocial selectSocial(UserSocial userSocial) throws Exception;

    // 소셜 회원 수정
    public int updateSocial(UserSocial userSocial) throws Exception;

    // 소셜 정보로 회원 조회
    public Users selectBySocial(UserSocial userSocial) throws Exception;

}
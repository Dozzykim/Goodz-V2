package com.springproject.goodz.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springproject.goodz.user.dto.CustomUser;
import com.springproject.goodz.user.dto.Users;
import com.springproject.goodz.user.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

/**
 *  🔐 UserDetailsService : 사용자 정보 불러오는 인터페이스
 *  ✅ 이 인터페이스를 구현하여, 사용자 정보를 로드하는 방법을 정의할 수 있습니다.
 */
@Slf4j
// @Service
public class UserDetailServiceImpl implements UserDetailsService {

    // // @Autowired
    // // private UserMapper userMapper;

    // @Override
    // public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    //     log.info(":::::::::: UserDetailServiceImpl ::::::::::");
    //     log.info("- 사용자 정의 인증을 위해, 사용자 정보 조회");
    //     log.info("- userId : " + userId);

    //     Users user = null;
    //     try {
    //         // 👩‍💼 사용자 정보 및 권한 조회
    //         user = userMapper.login(userId);
    //         log.info("user : " + user);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     if( user == null ) {
    //         throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + userId);
    //     }

    //     // 🔐 CustomUser ➡ UserDetails
    //     CustomUser customUser = new CustomUser(user);
        
    //     return customUser;
    // }
    
}
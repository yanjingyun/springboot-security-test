package com.yjy.config.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 自定义用户认证与授权
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	/**
	 * 加载用户
	 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟加载数据库用户信息
    	
    	String password = new BCryptPasswordEncoder().encode("123");
        
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("user:add"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("user:info"));
        
        // 由框架完成认证工作
        return new User(username, password, grantedAuthorities);
    }
}

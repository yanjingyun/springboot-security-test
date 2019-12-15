package com.yjy.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yjy.security.entity.JwtUser;
import com.yjy.system.entity.User;
import com.yjy.system.service.UserService;

@Component
public class MyUserService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByUserName(username);
		return new JwtUser(user);
	}
}

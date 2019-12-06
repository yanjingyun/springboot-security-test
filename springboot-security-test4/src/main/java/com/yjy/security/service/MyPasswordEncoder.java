package com.yjy.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码匹配器
 */
public class MyPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(rawPassword.toString());
	}
}

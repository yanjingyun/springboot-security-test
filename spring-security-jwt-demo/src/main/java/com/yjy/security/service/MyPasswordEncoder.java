package com.yjy.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义密码匹配器（引入加密算法：md5、sha1、sm3等）
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

	// 暂时使用明文存储
	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(rawPassword.toString());
	}
}

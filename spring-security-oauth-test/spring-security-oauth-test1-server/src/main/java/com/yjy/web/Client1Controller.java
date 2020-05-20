package com.yjy.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟客户端client1中相关api，实际上这些api来自于另一系统
 */
@RestController
public class Client1Controller {
	
	@RequestMapping("/get-code")
	public String getCode(String code) {
		return "code=" + code;
	}
	
	// 一个普通的REST API，为客户端提供用户详细信息以判断用户是否登陆、是否有权限访问资源
	@RequestMapping("/oauth/me")
	public Principal userInfo (Principal principal) {
		return principal;
	}
}

package com.yjy.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟客户端client2中相关api，实际上这些api来自于另一系统
 */
@RestController
@RequestMapping("/client2")
public class Client2Controller {
	
	@RequestMapping("/main")
	public String main() {
		return "client2主界面";
	}
	
	// 一个普通的REST API，为客户端提供用户详细信息以判断用户是否登陆、是否有权限访问资源
	@RequestMapping("/oauth/me")
	public Principal userInfo (Principal principal) {
		return principal;
	}
}

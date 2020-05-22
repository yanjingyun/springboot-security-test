package com.yjy.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@PreAuthorize("hasAuthority('user:add')")
	@RequestMapping("/add")
	public String add() { return "user:add()..."; }

	@PreAuthorize("hasAuthority('user:info')")
	@RequestMapping("/info")
	public String info() { return "user:info()..."; }
}

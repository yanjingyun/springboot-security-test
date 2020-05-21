package com.yjy.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyResourceController {

	@RequestMapping("/getMyResource")
	public String getMyResource() {
		return "getMyResource";
	}
}

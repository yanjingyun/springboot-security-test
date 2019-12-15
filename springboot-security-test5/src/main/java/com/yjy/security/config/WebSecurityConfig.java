package com.yjy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity //1.开启SpringSecurity支持
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 2.定义哪些路径需要被保护，哪些不需要被保护
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/home").permitAll() //“/”和“/home”不需要任何身份
				.anyRequest().authenticated() //所有其它路径必须经过身份认证
				.and()
			.formLogin() //自定义登录页面，每个人都可以查它
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
	// 3.配置用户（1、基于内存 2、基于数据库），需要引入PasswordEncoder类Bean
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String encode = new BCryptPasswordEncoder().encode("123456");
		auth.inMemoryAuthentication() //基于内存
			.withUser("admin").password(encode).roles("ADMIN")
			.and()
			.withUser("user").password(encode).roles("USER");
	}
}

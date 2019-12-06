package com.yjy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yjy.security.service.MyUserService;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserService myUserService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 配置用户：模拟数据库配置用户
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		String encode = new BCryptPasswordEncoder().encode("123456");
//		auth.inMemoryAuthentication().withUser("admin").password(encode).roles("ADMIN", "USER");
//		auth.inMemoryAuthentication().withUser("user1").password(encode).roles("USER");
		auth.userDetailsService(myUserService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // 开启请求权限配置
				.antMatchers("/authentication/*", "/login").permitAll() // 不需要登录就可以访问
				// .antMatchers("/user/**").hasAnyRole("USER") // 需要具有ROLE_USER角色才能访问
				.anyRequest().authenticated() // 匹配所有路径，用户登录后可访问
				.and().formLogin() // 使用表单登录(默认地址为/login)
				.loginPage("/authentication/login") // 设置登录页面
				.loginProcessingUrl("/authentication/form").defaultSuccessUrl("/currentUser"); // 设置默认登录成功后跳转的页面
	}
}
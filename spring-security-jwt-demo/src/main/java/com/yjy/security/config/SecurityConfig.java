package com.yjy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.yjy.security.exception.JWTAccessDeniedHandler;
import com.yjy.security.exception.JWTAuthenticationEntryPoint;
import com.yjy.security.filter.JWTAuthenticationFilter;
import com.yjy.security.filter.JWTAuthorizationFilter;
import com.yjy.security.service.MyPasswordEncoder;
import com.yjy.security.service.MyUserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserService myUserService;

	@Autowired
	private MyPasswordEncoder myPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 设置自定义的userDetailsService以及密码编码器
		auth.userDetailsService(myUserService).passwordEncoder(myPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors() // 解决跨域问题
				.and().csrf().disable() // 禁用CSRF保护（创建非浏览器客户端使用的服务需要禁用CSRF保护）
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 不创建session会话
				.and().authorizeRequests() // 开启请求权限配置
				.antMatchers("/auth/register").permitAll() // 放行url
				// .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN") //该路径需要admin角色
				.anyRequest().authenticated() // 其它所有资源都需要验证用户才能访问

				.and() // 添加自定义Filter
				.addFilter(new JWTAuthenticationFilter(authenticationManager())) // 认证filter
				.addFilter(new JWTAuthorizationFilter(authenticationManager())) // 授权filter

				// 授权异常处理
				.exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
				.accessDeniedHandler(new JWTAccessDeniedHandler());
	}

}

package com.yjy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.yjy.security.constants.SecurityConstants;
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
		http.csrf().disable() // 禁用CSRF保护（创建非浏览器客户端使用的服务需要禁用CSRF保护）
				.cors() // 解决跨域问题
				.and().authorizeRequests() // 开启请求权限配置
				.antMatchers(HttpMethod.POST, SecurityConstants.AUTH_LOGIN_URL).permitAll()
				// 指定路径下的资源需要验证了的用户才能访问
				.antMatchers("/api/**").authenticated().antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
				// 其他都放行了
				.anyRequest().permitAll().and()
				// 添加自定义Filter
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
				// 不需要session（不创建会话）
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 授权异常处理
				.exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
				.accessDeniedHandler(new JWTAccessDeniedHandler());
		// 防止H2 web 页面的Frame 被拦截
		http.headers().frameOptions().disable();
	}

}

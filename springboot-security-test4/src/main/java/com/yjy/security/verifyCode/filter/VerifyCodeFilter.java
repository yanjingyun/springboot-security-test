package com.yjy.security.verifyCode.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yjy.security.verifyCode.exception.ImageCodeException;
import com.yjy.security.verifyCode.utils.VerifyCodeUtil;

@Component
public class VerifyCodeFilter extends OncePerRequestFilter implements InitializingBean {
	
	private Set<String> urls = new HashSet<>();
	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		// 这里可以设置，哪些地址是需要图片验证码进行验证的
		urls.add("/authentication/form"); // 登录地址
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		boolean action = false;
		// 判断请求地址是否需要图片验证码
		for (String url : urls) {
			if (antPathMatcher.match(url, request.getRequestURI())) {
				action = true;
				break;
			}
		}
		if (action) {
			try {
				// 验证验证码是否正确
				validate(request);
			} catch (ImageCodeException e) {
				// 验证码错误则抛出异常
				request.getRequestDispatcher("/authentication/login?error").forward(request, response);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private void validate(HttpServletRequest request) {
		String verifyCodeRequest = request.getParameter(VerifyCodeUtil.VERIFY_CODE_SESSION_PRE_KEY);
		if (StringUtils.isEmpty(verifyCodeRequest)) {
			throw new ImageCodeException("验证码不能为空");
		}
		
		HttpSession session = request.getSession();
		String verifyCodeSession = (String) session.getAttribute(VerifyCodeUtil.VERIFY_CODE_SESSION_PRE_KEY);
		if (!verifyCodeSession.equalsIgnoreCase(verifyCodeRequest)) {
			throw new ImageCodeException("验证码错误");
		}
		session.removeAttribute(VerifyCodeUtil.VERIFY_CODE_SESSION_PRE_KEY);
	}
}

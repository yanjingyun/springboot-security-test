package com.yjy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;
	
    /**
	 * 客户端配置
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		System.out.println(new BCryptPasswordEncoder().encode("secret"));
		String secret = passwordEncoder.encode("secret");

		// withClient-客户端ID，secret-客户端secret，grantTypes-授权类型，scopes-授权范围，redirectUris-注册回调地址
		// 授权码授权方式-客户端信息：client_id必填，secret必填，grant_type必填，redirect_uri必填，scope选填
		// 在内存中配置四个客户端（也可以在数据库中配置）
		clients.inMemory()
				// client_1 获取令牌：授权码方式、刷新令牌方式
				.withClient("client_1").secret(secret).accessTokenValiditySeconds(3600)
				.refreshTokenValiditySeconds(604800).authorizedGrantTypes("authorization_code", "refresh_token")
				.scopes("all").redirectUris("http://baidu.com").and()

				// client_2 获取令牌：密码授权方式、刷新令牌方式
				.withClient("client_2").secret(secret).accessTokenValiditySeconds(3600)
				.refreshTokenValiditySeconds(604800).authorizedGrantTypes("password", "refresh_token").scopes("all")
				.and()
				// client_3 获取令牌：简化授权方式
				.withClient("client_3").secret(secret).accessTokenValiditySeconds(3600).authorizedGrantTypes("implicit")
				.redirectUris("http://baidu.com").scopes("all").and()

				// client_4 获取令牌：客户端授权方式
				.withClient("client_4").secret(secret).accessTokenValiditySeconds(3600)
				.authorizedGrantTypes("client_credentials").scopes("all");
	}

	/**
	 * 授权服务器端点配置:
	 */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	// 设置用户认证的管理器和生成的令牌的存储方式
		endpoints.authenticationManager(this.authenticationManager).tokenStore(tokenStore());
		// 设置令牌加强器（生成令牌时使用）
		endpoints.tokenEnhancer(jwtAccessTokenConverter());

		// 设置userDetailsService刷新token时候会用到
		endpoints.userDetailsService(userDetailsService);
    }
    
    /**
	 * 创建令牌存储对象
	 */
    @Bean
    public TokenStore tokenStore() {
    	// 使用JwtTokenStore时，必须注入一个JwtAccessTokenConverter，用于解析JWT令牌
    	return new JwtTokenStore(jwtAccessTokenConverter());
    }
    
    /**
     * 创建JWT令牌转换器（实际上是一种特殊的JWT令牌增强器）
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
    	// 设置JWT令牌的签名key，此外还可以往令牌里添加数据
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("signingKey");
        return converter;
    }
}

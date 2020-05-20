package com.yjy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 配置OAuth认证服务器
 */
@Configuration
@EnableAuthorizationServer //开启SecurityOAuth2注解，以提供/oauth/token、/oauth/check_token、/oauth/authorize等endpoint
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {

	// 注入 WebSecurityConfiguration 中配置的 BCryptPasswordEncoder
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 配置客户端系统client
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory() //使用内存设置
			.withClient("client1") //client_id
			.secret(passwordEncoder.encode("secret")) //client_secret，需要加密
			.authorizedGrantTypes("authorization_code") //授权类型
			.scopes("app") //授权范围
			.redirectUris("http://localhost:8080/get-code") //注册回调地址
			
			.and()
			.withClient("client2") //客户端2
			.secret(passwordEncoder.encode("secret"))
			.authorizedGrantTypes("client_credentials")
			.scopes("read");
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
		// oauthServer.allowFormAuthenticationForClients();
	}
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("secret"));
	}
}
package com.yjy.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * Oauth2配置类
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * 1.用来配置客户端详情服务（ClientDetailsService）
     * org.springframework.security.oauth2.provider.ClientDetailsService
     * 可以把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用JdbcClientDetailsService客户端详情服务
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
        // clients.jdbc(dataSource); // 这样写也可以
    }

    /**
     * 2.用来配置令牌端点(Token Endpoint)的安全约束，授权端点开放
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        // 开启/oauth/token_key验证端口认证权限访问
        security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 3.用来配置令牌访问端点和令牌服务(Token Services)
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter());
        endpoints.tokenStore(jwtTokenStore());
    }

    /**
     * 用来生成token的转换器
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * JwtAccessToken转换器
     * token令牌默认是有签名的，且资源服务器需要验证这个签名
     * 此处的加密及验签包括两种方式：对称加密（对称加密需要授权服务器和资源服务器存储同一key值）、非对称加密（非对称加密可使用密钥加密，暴露公钥给资源服务器验签）
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("yjy");   //  Sets the JWT signing key

//        使用非对称加密算法来对Token进行签名
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
//        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));

        return jwtAccessTokenConverter;
    }

}

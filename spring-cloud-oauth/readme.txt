



@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsService kiteUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore redisTokenStore;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 调用此方法才能支持 password 模式
                .userDetailsService(kiteUserDetailsService) // 设置用户验证服务
                .tokenStore(redisTokenStore); // 指定 token 的存储方式
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsServiceBuilder jcsb = clients.jdbc(dataSource);
        jcsb.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 第一行代码是允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401。
        security.allowFormAuthenticationForClients();
        // 第二行和第三行分别是允许已授权用户访问 checkToken 接口和获取 token 接口。
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }
}




#### spring-cloud-oauth-test01:
	--测试spring cloud 整合 oauth2
	测试详情参考：spring cloud 整合security oauth2.postman_collection.json
	
	--相关接口
	POST /oauth/authorize  授权码模式认证授权接口
	GET/POST /oauth/token  获取 token 的接口
	POST  /oauth/check_token  检查 token 合法性接口

1.请求认证服务端获取token（密码模式）
	POST http://localhost:6001/oauth/token?grant_type=password&username=admin&password=123456&scope=all
	Accept: */*
	Cache-Control: no-cache
	Authorization: Basic dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==
	说明：
		Authorization 要加在请求头中，格式为 Basic 空格 base64(clientId:clientSecret)
		使用 base64 编码(user-client:user-secret-8888)之后的值为 dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==
		通过 https://www.sojson.com/base64.html 在线编码获取
	响应参数：
	{
		"access_token": "9f958300-5005-46ea-9061-323c9e6c7a4d",
		"token_type": "bearer",
		"refresh_token": "0f5871f5-98f1-405e-848e-80f641bab72e",
		"expires_in": 3599,
		"scope": "all"
	}


2.使用token 请求资源接口
	GET http://localhost:6101/client-user/get
	Accept: */*
	Cache-Control: no-cache
	Authorization: bearer ce334918-e666-455a-8ecd-8bd680415d84

3.刷新令牌
	### 换取 access_token
	POST http://localhost:6001/oauth/token?grant_type=refresh_token&refresh_token=706dac10-d48e-4795-8379-efe8307a2282
	Accept: */*
	Cache-Control: no-cache
	Authorization: Basic dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==



#### spring-cloud-oauth-test02:
	--客户端信息存入数据库

#### spring-cloud-oauth-test03:
	--用 JWT 替换 redisToken

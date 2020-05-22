
单体应用存在的问题：
	随着业务的发展，开发变得越来月复杂。
	修改、新增某个功能，需要对整个系统进行测试，重新部署
	一个模块出现问题，很可能导致整个系统崩溃
	多个团队开发同时


OAuth协议：
	主要解决授权问题，该协议定义了四种授权方式：授权码方式、密码授权方式、简化授权方式、客户端授权方式
	
	授权服务器主要是提供用户认证、授权、颁发令牌等功能，而资源服务器主要是保护用户资源。授权服务器先给合法的用户颁发令牌，用户再使用获得的令牌到资源服务器申请受保护的用户资源，而资源服务器接收到用户的请求后，会先验证用户的令牌，只有令牌合法才会把用户请求的资源返回给用户，否则拒绝返回。
	
	资源服务器验证用户的令牌可以有多种，既可以到授权服务器处进行验证，也可以自己验证。只有授权服务器使用非对称加密令牌或者使用对称加密令牌的方式，且资源服务器知道授权服务器用于加密令牌的密钥所对应的公钥时，资源服务器才能自己验证令牌。


OAuth2授权服务器 && OAuth2资源服务器



AuthorizationServerConfigurerAdapter类如下：
	public class AuthorizationServerConfigurerAdapter implements AuthorizationServerConfigurer {

		// 1.配置客户端：clientId、secret、scope、resourceIds、authorizedGrantTypes、redirectUrl
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {}
		
		// 2.用来配置令牌访问端点和令牌服务
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {}

		// 3.配置令牌端点的安全约束
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {}
	}


单点登录：
	《单点登录流程图.png》
	https://blog.csdn.net/chenleiking/article/details/81046233

SSO与OAuth2描述：
	SSO是一种思想，或者说是一种解决方案，是抽象的，我们要做的就是按照它的这种思想去实现单点登录。
	OAuth2是用来允许用户授权第三方应用访问他在另一个服务器上的资源的一种协议，它不是用来做单点登录的，但我们可以利用它来实现单点登录。


验证服务器(AuthorizationServer)主要功能：
	1、验证用户的username/password
	2、验证客户端的client_id和secret
	3、为验证通过的用户或客户端颁发code或token
	4、验证客户端提供的token是否合法
	5、根据有效的token提供对应的用户或客户端详细信息


由@EnableAuthorizationServer注解引出的配置：
	其导入两个配置类：AuthorizationServerEndpointsConfiguration和AuthorizationServerSecurityConfiguration
	根据@EnableAuthorizationServer注解可知，该AuthorizationServer暴露了两个Http Endpoint：
		/oauth/authorize //实现类AuthorizationEndpoint
		/oauth/token	//实现类TokenEndpoint

退出：
	退出即清空所有SSO客户端的会话，即所有端点的Session失效。本文采用jwt，因此撤销token不太容易。
	本例采用在退出时先退出业务服务器，成功以再回调认证服务器。存在问题：需要主动依次调用各个业务服务器的lgout方法。


*********************************实战*********************************
spring-security-oauth-test1-server： 测试Spring Security OAuth2
	流程：
		1、引入jar包：
			spring-cloud-starter-oauth2
			spring-security-oauth2-autoconfigure //将为我们省去很多的配置事项
		2、创建OAuth配置类AuthorizationServerConfig（配置客户端信息）
			@EnableAuthorizationServer
			重写configure(ClientDetailsServiceConfigurer clients)方法
		3、创建SpringSecurity配置类WebSecurityConfig（配置登录用户信息）
			@EnableWebSecurity
			重写configure(AuthenticationManagerBuilder auth)方法
		4、创建客户端项目并测试（模拟客户端，与认证中心写在一起）
			...
	
	2种客户端方式测试：
	方式1：基于内存存储-令牌模式（APP项目）
		详情：https://www.funtl.com/zh/spring-security-oauth2/%E5%9F%BA%E4%BA%8E%E5%86%85%E5%AD%98%E5%AD%98%E5%82%A8%E4%BB%A4%E7%89%8C.html
		1、客户端1浏览器中请求认证中心server得到code
			http://localhost:8080/oauth/authorize?client_id=client1&response_type=code
		2、客户端1发送post请求得到access_token
			说明：client1-客户端Id，secret-客户端secret，code-步骤1的code
			http://client1:secret@localhost:8080/oauth/token?code=CxZGdK&grant_type=authorization_code

			--另一种获取access_token方式（感觉没有上面的接口好）
				post请求：localhost:8080/oauth/token?grant_type=authorization_code&code=H9BiYp&client_id=client1&client_secret=secret
				该接口还需要在Oauth配置类中添加如下配置方法：
				// 详情：https://blog.csdn.net/u012040869/article/details/80140515
				@Override
				public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
					oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
					// oauthServer.allowFormAuthenticationForClients();
				}

		3、客户端1获取用户详细信息
			http://localhost:8080/oauth/me
		附：客户端1理论上是另一项目，跟认证中心分开，目前跟认证中心写在一起（模拟客户端1）

	方式2：基于内存存储-客户端模式（PC项目）
		1、客户端2未登录进入主界面
			http://localhost:8080/client2/main
			此时会跳转到认证中心server进行登录，登录成功自动跳转到客户端2的main界面。
		2、客户端2获取用户信息
			http://localhost:8080/client2/oauth/me
		附：客户端2理论上是另一项目，跟认证中心分开，目前跟认证中心写在一起（模拟客户端2）




spring-security-oauth-test1-server： 测试Spring Security OAuth2
	相关链接：https://blog.csdn.net/weixin_44516305/article/details/88886839
	修改jwt存储token

	测试：
	1.用户认证：浏览器访问以下地址会自动跳转到用户认证页面
	  http://localhost:8080/oauth/authorize?client_id=client_1&response_type=code
	  说明：
	    1、参数redirect_uri可选，但必须是该client_id中配置的uri，否则报错。
	    2、系统无该client_id时会报错：error="invalid_client"


	2.获取令牌：使用postman访问以下地址到授权服务器获取访问令牌和刷新令牌:
	  http://localhost:8080/oauth/token?grant_type=authorization_code&client_id=client_1&client_secret=secret&code=h0MILI
	  说明：
	    1、必须是POST请求；grant_type必须为authorization_code；
	    2、grant_type、client_id、client_secret必填，且与配置类中保持一致；
	    3、client_id和client_secret也可以使用Basic认证的方式传递给授权服务器。
	3.获取资源：
	  使用访问令牌到资源服务器获取用户资源，访问令牌必须是Bear认证的方式传递给资源服务器。
	  http://localhost:8080/getMyResource


spring-security-oauth-test3：授权码方式进行认证和授权
	PostMan测试《spring-security-oauth-test3测试.postman_collection.json》
	关键技术：Springboot, Spring-security, Spring-security-oauth2。

	本系统存在两个子系统：授权服务器和资源服务器。访问资源服务器的资源时需要去授权服务器拿到access_token，且该access_token有该资源的权限。

	用Oauth2技术对访问受保护的资源的客户端进行认证与授权：提供一个简化版，用户、token数据保存在内存中，用户与客户端的认证授权服务、资源服务，都是在同一个工程中。现实项目中，技术架构通常上将用户与客户端的认证授权服务设计在一个子系统（工程）中，而资源服务设计为另一个子系统（工程）。


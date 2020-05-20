
单体应用存在的问题：
	随着业务的发展，开发变得越来月复杂。
	修改、新增某个功能，需要对整个系统进行测试，重新部署
	一个模块出现问题，很可能导致整个系统崩溃
	多个团队开发同时

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
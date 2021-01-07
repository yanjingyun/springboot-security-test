
********************************springboot 整合 spring security oauth2 ********************************
spring-security-oauth-test3：
	PostMan测试《spring-security-oauth-test3测试.postman_collection.json》
	关键技术：Springboot, Spring-security, Spring-security-oauth2。
	本系统存在两个子系统：授权服务器和资源服务器。访问资源服务器的资源时需要去授权服务器拿到access_token，且该access_token有该资源的权限。
	描述：
		客户端信息直接存放在内存中
		token使用jwt进行存储
		用户及权限信息通过UserDetailService类加载出来；
	测试：
		测试1：client_1通过授权码模式获取access_token
		测试2：client_2通过密码模式获取access_token


springboot-security-oauth-jwt-demo		--Spring Security OAuth2实战
	整合SpringSecurity,OAuth2,JWT相关技术。包括1个服务端和2个客户端。
	流程说明：
		1.先启动server，再启动两个client
		输入：http://localhost:8082/memberSystem/member/list，会跳转到登录页
		输入：http://localhost:8083/orderSystem/order/list，也会跳转到登录页
		2.在输入user1/user1后，进入memberSystem系统，切换orderSystem系统无需重新登录
		3.测试权限：user1用户能跳转到detail页面，不能跳转到test页面
	退出：
		退出即清空所有SSO客户端的会话，即所有端点的Session失效。本文采用jwt，因此撤销token不太容易。
		本例采用在退出时先退出业务服务器，成功以再回调认证服务器。存在问题：需要主动依次调用各个业务服务器的lgout方法。

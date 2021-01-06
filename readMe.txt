springboot-security-test1: 自定义登录页面
	编写SpringSecurity配置类，并继承WebSecurityConfigurerAdapter类：1、配置用户 2、配置路径
	测试：浏览器输入http://localhost:8080/currentUser，会自动跳转到http://localhost:8080/authentication/login 再输入账号密码后，会重新跳转到原来的地址。

springboot-security-test2： 模拟数据库加载用户
	1.添加UserDetailsService实现类，并重写loadUserByUsername(username)方法
	2.在SpringSecurity配置类中，重写configure(auth)方法
	测试：浏览器输入http://localhost:8080/currentUser，会自动跳转到http://localhost:8080/authentication/login 再输入账号密码后，会重新跳转到原来的地址。

springboot-security-test3： 测试自定义密码匹配器
	1.创建MyPasswordEncoder类，实现PasswordEncoder接口
	2.在SpringSecurity配置类中，声明该Bean对象

springboot-security-test4： 添加验证码
	添加自定义Filter，并在SpringSecurity配置类引入。
	1.测试前端能正常返回验证码
	2.SpringSecurity添加验证码：创建验证码过滤器类、在SpringSecurity配置类中引入该过滤器
		http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)

------------------分割线------------------
http://www.spring4all.com/article/428 	--spring4all系列教程

springboot-security-test5 ：测试初引入SpringSecurity
	自定义登录页面
	基于内存配置用户

Spring Security 实现QQ登录（未实现）
	详情：http://www.spring4all.com/article/424


spring-security-jwt-demo ：测试springboot整合security和jwt
	数据库信息：db_security_jwt.sql
	测试实例：security-jwt测试.postman_collection.json
	
	--附加功能：
	集成验证码验证实现思路：
		-1）生成验证码时，随机生成验证码key，并将key和验证码存入redis，将key和验证码返回给前端；
		-2）前端登录时，将附带验证码key，后台通过key拿到生成的验证码后，与用户输入的验证码对比。
			--继承UsernamePasswordAuthenticationFilter类，并在attemptAuthentication()方法中完成验证码校验、用户校验

	前端密码加密传输(使用RSA非对称加密)：
		-1）后端使用RSA生成密钥和公钥，并存入redis(key=公钥，value=密钥)，将公钥返回给前端；
		-2）前端登录时，使用公钥对password加密，并将公钥和加密后的password传给后端；
		-3）后端通过公钥从redis中取出对应密钥，对password进行解密。



spring-security-oauth-test目录：
	所有相关子项目，从头开始测试Spring Security OAuth2相关


springboot-security-oauth-jwt-demo：Spring Security OAuth2实战
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


springcloud-security-oauth2-test	--spring cloud 整合 security oauth2
	参考别人的项目(https://www.jianshu.com/p/4ce5577bab74)
	--详情查看 ./springcloud-security-oauth2-test/readme.txt 文件


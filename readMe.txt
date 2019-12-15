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
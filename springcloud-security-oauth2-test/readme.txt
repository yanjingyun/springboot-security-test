

***********************spring cloud 整合 spring security oauth2***********************
#### spring-cloud-oauth-test01:
	--测试spring cloud 整合 oauth2
	测试详情参考：spring cloud 整合security oauth2.postman_collection.json
	
#### spring-cloud-oauth-test02:
	--客户端信息存入数据库

#### spring-cloud-oauth-test03:
	--用 JWT 替换 redisToken



*****************spring cloud 整合 security oauth2*****************
springcloud-security-oauth2-test01
	--redis存储token，使用自定义RedisTokenStore类（默认的会报错）
	测试：spring cloud 整合security oauth2.postman_collection.json


springcloud-security-oauth2-test02
	--jwt存储token，改造test01项目，将 RedisTokenStore 替换为 JwtTokenStore
	service-auth 项目：
		1.修改 AuthorizationServerConfiguration类： 1、移除redis相关 2、添加JwtTokenStore
	service-hi 项目：
		1.修改 ResourceServerConfiguration类：添加JwtTokenStore
		2.修改application.yml配置信息
	测试：spring cloud 整合security oauth2.postman_collection.json



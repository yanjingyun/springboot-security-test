springboot-security-test1: �Զ����¼ҳ��
	��дSpringSecurity�����࣬���̳�WebSecurityConfigurerAdapter�ࣺ1�������û� 2������·��
	���ԣ����������http://localhost:8080/currentUser�����Զ���ת��http://localhost:8080/authentication/login �������˺�����󣬻�������ת��ԭ���ĵ�ַ��

springboot-security-test2�� ģ�����ݿ�����û�
	1.���UserDetailsServiceʵ���࣬����дloadUserByUsername(username)����
	2.��SpringSecurity�������У���дconfigure(auth)����
	���ԣ����������http://localhost:8080/currentUser�����Զ���ת��http://localhost:8080/authentication/login �������˺�����󣬻�������ת��ԭ���ĵ�ַ��

springboot-security-test3�� �����Զ�������ƥ����
	1.����MyPasswordEncoder�࣬ʵ��PasswordEncoder�ӿ�
	2.��SpringSecurity�������У�������Bean����

springboot-security-test4�� �����֤��
	����Զ���Filter������SpringSecurity���������롣
	1.����ǰ��������������֤��
	2.SpringSecurity�����֤�룺������֤��������ࡢ��SpringSecurity������������ù�����
		http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)

------------------�ָ���------------------
http://www.spring4all.com/article/428 	--spring4allϵ�н̳�

springboot-security-test5 �����Գ�����SpringSecurity
	�Զ����¼ҳ��
	�����ڴ������û�

Spring Security ʵ��QQ��¼��δʵ�֣�
	���飺http://www.spring4all.com/article/424






spring-security-jwt-demo ������springboot����security��jwt
	���ݿ���Ϣ��db_security_jwt.sql
	����ʵ����security-jwt����.postman_collection.json
	
	--���ӹ��ܣ�
	������֤����֤ʵ��˼·��
		-1��������֤��ʱ�����������֤��key������key����֤�����redis����key����֤�뷵�ظ�ǰ�ˣ�
		-2��ǰ�˵�¼ʱ����������֤��key����̨ͨ��key�õ����ɵ���֤������û��������֤��Աȡ�
			--�̳�UsernamePasswordAuthenticationFilter�࣬����attemptAuthentication()�����������֤��У�顢�û�У��

	ǰ��������ܴ���(ʹ��RSA�ǶԳƼ���)��
		-1�����ʹ��RSA������Կ�͹�Կ��������redis(key=��Կ��value=��Կ)������Կ���ظ�ǰ�ˣ�
		-2��ǰ�˵�¼ʱ��ʹ�ù�Կ��password���ܣ�������Կ�ͼ��ܺ��password������ˣ�
		-3�����ͨ����Կ��redis��ȡ����Ӧ��Կ����password���н��ܡ�










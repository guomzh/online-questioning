# online-questioning
在线问答平台, 这是我模仿知乎做的一个问答交流平台
使用到的技术栈：  
1、spring/springboot  
* 拦截器

2、mybatis  
3、前端模板引擎freemarker  
4、Redis  
5、solr  
6、fastDFS  

## 注册模块：  
1. 用户名合法性检测（长度，敏感词，重复，特殊字符）  
2. 密码长度要求  
3. 密码salt加密，密码强度检查（md5库）  
4. 用户邮件/短信激活  

## 登录模块
1、服务器密码校验/三方校验，token（sessionId或者cookie的一个key）登记  
* 服务器端token关联userId  
* 客户端存储token(本地或者cookie)  

2、服务器端/客户端token设置有效期（记住登录）  
3、登出：服务器端/客户端token删除或者session清理



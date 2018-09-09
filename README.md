# online-questioning  在线问答平台, 这是我模仿知乎做的一个问答交流平台

使用到的技术栈：  
1、spring/springboot  
* 拦截器

2、mybatis  
3、前端模板引擎freemarker  
4、Redis  
5、solr  
6、fastDFS  

开发通用的新模块流程：  
1、数据库设计  
2、Model：模型定义，与数据库相匹配  
3、Dao层：数据操作  
4、Service:服务包装  
5、Controller：业务入口，数据交互  
6、单元测试  

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

## 问题发布模块
* HTML/敏感词过滤
```
  //html过滤
  question.setContent(HtmlUtils.htmlEscape(question.getContent()));
  敏感词过滤通过Trie树存储敏感词汇，匹配文本串，对匹配到的敏感词打码或者删除
```
* 多线程

### 关于多线程的一些运用知识回顾如下：  
```
  Future作用：进行线程与线程间通信
  1. 返回异步结果：Future<Integer>future =service.submit(new Callable<Integer>{ });
  2. 阻塞等待返回结果（future.get()）
  3. timeout(future.get(100,TimeUnit.MILLISECONDS))
  4. 获取线程中的Exception
```

### 评论中心

### 消息中心（赞，评论通知，私信通知，回答采纳等）


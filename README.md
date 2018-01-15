microservice-common
===================================

microservice-common是一个common project，该project包含一些公用的架构和工具类。

### note
1. JDK使用的1.8
2. 在修改源码前，请导入googlestyle-java.xml以保证一致的代码格式
3. 代码使用了lombok，在IDE内调试需要安装相关插件: [安装文档](https://projectlombok.org/setup)

### 架构
1. DAO相关架构，使用模板方法模式并定义前规则和后规则，可进行rule的校验
2. Controller相关架构，自定义path注解以对应function请求参数，可以控制同一入口访问，方便统计pv、性能等

### 工具类
1. 参数解析、异常处理等工具类
2. md5加密工具类
3. json, bean转换工具类

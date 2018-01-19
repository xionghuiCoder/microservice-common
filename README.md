microservice-common
===================================

### microservice-common是一个common project，该project包含一些公用的架构和工具类。

### 架构
<ol>
<li>DAO相关架构，使用模板方法模式并定义前规则和后规则，可进行rule的校验</li>
<li>Controller相关架构，自定义path注解以对应function请求参数，可以控制同一入口访问，方便统计pv、性能等</li>
</ol>

### 工具类
<ol>
<li>参数解析、异常处理等工具类</li>
<li>md5加密工具类</li>
<li>json, bean转换工具类</li>
</ol>

### note
<ol>
<li>JDK支持1.6或1.6+</li>
<li>在修改源码前，请导入googlestyle-java.xml以保证一致的代码格式</li>
</ol>

### 代码使用了lombok，在IDE内调试需要安装相关插件: [安装文档](https://projectlombok.org/setup)

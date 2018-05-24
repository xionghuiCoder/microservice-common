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

### 功能说明

## 1. 方法出入参自动打印
```java
  @ParamLogAnnotation                                   //在需要打印出入参的方法上添加此注解
  public AopResponse test(AopRequest request){
    return new AopResponse(true,"success return.......");
  } 
```

调用方法后 结果输出：
```text
 - 方法[test] 请求时间:2018-05-23 10:43:12| 入参：request:{"id":1,"name":"firstRequest","type":1001};
 - 方法[test] 请求时间:2018-05-23 10:43:12| 返回结果时间:2018-05-23 10:43:12| 出参：{"result":"success return.......","success":true}
```

## 2. 入参自动校验
入参实体：
```java
public class SimpleBean {

  @NotNull
  private int id;

  @NotBlank
  @Size(min = 2, max = 8,message = "be care the name length ，it should between 2 and 8 !")
  private String name;

  @NotNull(message = "score should be not null")
  @DoubleVal   //自定义校验
  private Double score;
  
  get set ...
}
```
校验:
```java
  @Test
  public void test(){
    SimpleBean myBean=new SimpleBean();
//    myBean.setName("abc");
//    myBean.setScore(1.23446);

    func(myBean);
  }
  
    private void func(SimpleBean bean){
      //******校验入参********
      ValidatorUtils.validate(bean);
      //******校验入参********
      
      System.out.println(JSONObject.toJSONString(bean));
    }
```
结果：
```text
java.lang.IllegalArgumentException: 参数异常：SimpleBean.name : 不能为空
```

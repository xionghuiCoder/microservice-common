package com.github.xionghuicoder.microservice.common.aop;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/3/2 下午2:11
 * @description
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring-config-test.xml"})
public class ParamLogAOPTest {

  @Resource
  private AOPService aopService;

  @Test
  public void test(){
    aopService.test(new AopRequest(1,"firstRequest",1001));
  }

}

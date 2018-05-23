package com.github.xionghuicoder.microservice.common.validator;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/5/23 下午2:18
 * @description
 */
public class ValidatorTest {


  private void func(SimpleBean bean){
    //******校验入参********
    ValidatorUtils.validate(bean);
    //******校验入参********

    System.out.println(JSONObject.toJSONString(bean));
  }

  @Test
  public void test(){
    SimpleBean myBean=new SimpleBean();
//    myBean.setName("abc");
//    myBean.setScore(1.23446);

    func(myBean);
  }
}

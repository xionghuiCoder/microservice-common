package com.github.xionghuicoder.microservice.common.aop;

import com.github.xionghuicoder.microservice.common.annotation.ParamLogAnnotation;
import org.springframework.stereotype.Service;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/3/2 下午3:28
 * @description
 * @since 1.0.0
 */
@Service
public class AOPService {

  @ParamLogAnnotation
  public AopResponse test(AopRequest request){
    return new AopResponse(true,"success return.......");
  }

}

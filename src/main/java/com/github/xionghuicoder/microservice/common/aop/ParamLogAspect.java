package com.github.xionghuicoder.microservice.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.utils.TimeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@Aspect
public class ParamLogAspect {
    private static final Logger LOGGER= LoggerFactory.getLogger(ParamLogAspect.class);

    @Pointcut(value = "@annotation(com.github.xionghuicoder.microservice.common.annotation.ParamLogAnnotation)")
    public void aspect(){}


    @Around("com.github.xionghuicoder.microservice.common.aop.ParamLogAspect.aspect()")
    public Object call(JoinPoint joinPoint) throws Throwable{

      String time=TimeUtils.longToString(System.currentTimeMillis(),TimeUtils.dateFormatTYpe);

      ProceedingJoinPoint pdp=(ProceedingJoinPoint)joinPoint;

      //请求入参
      Object[] args=joinPoint.getArgs();
      String[] argSName=((MethodSignature)pdp.getSignature()).getParameterNames();

      StringBuilder in=new StringBuilder();
      in.append("方法[").append(pdp.getSignature().getName()).append("] 请求时间:")
          .append(time)
          .append("| 入参：");

      int length=args.length;
      for (int i=0;i<length;i++){
        in.append(argSName[i]).append(":").append(JSONObject.toJSONString(args[i])).append(";");
      }
      //打印入参
      LOGGER.info(in.toString());
      Object result= pdp.proceed();

      //打印出参
      StringBuilder out=new StringBuilder();
      out.append("方法[").append(pdp.getSignature().getName()).append("] 请求时间:").append(time).append("| 返回结果时间:")
          .append(TimeUtils.longToString(System.currentTimeMillis(),TimeUtils.dateFormatTYpe))
          .append("| 出参：")
          .append(JSONObject.toJSONString(result));

      LOGGER.info(out.toString());

      return result;
    }
}

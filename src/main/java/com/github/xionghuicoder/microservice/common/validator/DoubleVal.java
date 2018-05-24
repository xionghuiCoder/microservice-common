package com.github.xionghuicoder.microservice.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/5/23 下午2:59
 * @description  自定义参数检验注解
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=DoubleValidator.class)
public @interface DoubleVal {

  String message() default"请确认数值位数、范围等是否符合要求";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

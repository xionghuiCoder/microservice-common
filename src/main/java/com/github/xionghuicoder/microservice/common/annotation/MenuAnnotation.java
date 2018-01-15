package com.github.xionghuicoder.microservice.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 开启路径配置
 *
 * @author xionghui
 * @since 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MenuAnnotation {

  @AliasFor("menus")
  String[] value() default {};

  @AliasFor("value")
  String[] menus() default {};

}

package com.github.xionghuicoder.microservice.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 菜单配置
 *
 * @author xionghui
 * @version 1.0.0
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

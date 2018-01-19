package com.github.xionghuicoder.microservice.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;

/**
 * 配置路径，方法，uri等信息
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PathConfigAnnotation {

  @AliasFor("path")
  String value() default "";

  @AliasFor("value")
  String path() default "";

  String uri() default "/";

  boolean supportZuul() default true;

  boolean supportFeign() default false;

  HttpRequestMethod[] method() default {HttpRequestMethod.GET, HttpRequestMethod.POST};
}

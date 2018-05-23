package com.github.xionghuicoder.microservice.common.validator;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/5/23 下午1:58
 * @description
 */
public class ValidatorUtils {

  private static Validator validator;

  static {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }



  public static <T> void validate(T obj) {

    Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);

    //抛出检验异常
    for (ConstraintViolation<T> error : constraintViolations) {

      StringBuffer buffer = new StringBuffer().append("参数异常：").append( obj.getClass().getSimpleName()).append(".")
          .append(error.getPropertyPath().toString()).append(" : ")
          .append(error.getMessage());

      throw new IllegalArgumentException(buffer.toString());
    }

  }

}

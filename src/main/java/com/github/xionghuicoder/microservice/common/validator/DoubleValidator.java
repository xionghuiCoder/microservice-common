package com.github.xionghuicoder.microservice.common.validator;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/5/23 下午3:00
 * @description 自定义参数检验器
 */
public class DoubleValidator implements ConstraintValidator<DoubleVal,Double> {

  private String doubleReg = "^\\d+(\\.\\d{1,2})?$";//正则表达式
  private Pattern doublePattern = Pattern.compile(doubleReg);

  @Override
  public void initialize(DoubleVal constraintAnnotation) {

  }

  @Override
  public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
    return aDouble == null || doublePattern.matcher(aDouble.toString()).matches();

  }
}

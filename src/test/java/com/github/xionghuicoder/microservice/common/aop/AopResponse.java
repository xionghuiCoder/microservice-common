package com.github.xionghuicoder.microservice.common.aop;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/3/2 下午3:41
 * @description
 * @since 1.0.0
 */
public class AopResponse {

  private boolean isSuccess;
  private Object result;

  public AopResponse(boolean isSuccess, Object result) {
    this.isSuccess = isSuccess;
    this.result = result;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    isSuccess = success;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }
}

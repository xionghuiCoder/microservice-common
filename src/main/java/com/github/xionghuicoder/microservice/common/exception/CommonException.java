package com.github.xionghuicoder.microservice.common.exception;

import com.github.xionghuicoder.microservice.common.bean.enums.IHttpResultEnum;

/**
 * utils的公共异常
 *
 * @author xionghui
 * @since 1.0.0
 */
public class CommonException extends RuntimeException {
  private static final long serialVersionUID = -7872128763736499065L;

  private IHttpResultEnum httpResultEnum;
  private String[] args;

  public CommonException() {
    super();
  }

  public CommonException(String message) {
    super(message);
  }

  public CommonException(Throwable cause) {
    super(cause);
  }

  public CommonException(String message, Throwable cause) {
    super(message, cause);
  }

  public CommonException(IHttpResultEnum httpResultEnum, String... args) {
    this.httpResultEnum = httpResultEnum;
    this.args = args;
  }

  public CommonException(String message, IHttpResultEnum httpResultEnum, String... args) {
    super(message);
    this.httpResultEnum = httpResultEnum;
    this.args = args;
  }

  public IHttpResultEnum getHttpResultEnum() {
    return this.httpResultEnum;
  }

  public String[] getArgs() {
    return this.args;
  }
}

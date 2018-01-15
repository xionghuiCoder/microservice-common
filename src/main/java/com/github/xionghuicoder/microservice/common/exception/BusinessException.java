package com.github.xionghuicoder.microservice.common.exception;

import com.github.xionghuicoder.microservice.common.bean.enums.IHttpResultEnum;

/**
 * 业务异常
 *
 * @author xionghui
 * @since 1.0.0
 */
public class BusinessException extends CommonException {
  private static final long serialVersionUID = -3999464059228303405L;

  public BusinessException() {
    super();
  }

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException(IHttpResultEnum httpResultEnum, String... args) {
    super(httpResultEnum, args);
  }

  public BusinessException(String message, IHttpResultEnum httpResultEnum, String... args) {
    super(message, httpResultEnum, args);
  }
}

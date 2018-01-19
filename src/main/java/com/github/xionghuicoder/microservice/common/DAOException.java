package com.github.xionghuicoder.microservice.common;

import com.github.xionghuicoder.microservice.common.bean.enums.IHttpResultEnum;

/**
 * DAO异常类
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class DAOException extends CommonException {
  private static final long serialVersionUID = 9174004860670429214L;

  public DAOException() {
    super();
  }

  public DAOException(String message) {
    super(message);
  }

  public DAOException(Throwable cause) {
    super(cause);
  }

  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }

  public DAOException(IHttpResultEnum httpResultEnum, String... args) {
    super(httpResultEnum, args);
  }
}

package com.github.xionghuicoder.microservice.common.utils;

import com.github.xionghuicoder.microservice.common.CommonException;
import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.bean.enums.IHttpResultEnum;

/**
 * 异常处理类，把异常信息转为HttpResult
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonExceptionUtils {

  public static HttpResult<?> unwrap(Throwable t) {
    HttpResult<?> httpResult = null;
    while (t != null) {
      if (t instanceof CommonException) {
        CommonException commonException = (CommonException) t;
        IHttpResultEnum httpResultEnum = commonException.getHttpResultEnum();
        if (httpResultEnum != null) {
          // 此处不break，因为需要找到最里面一层的httpResultEnum
          httpResult = HttpResult.custom(httpResultEnum).setArgs(commonException.getArgs()).build();
        }
      }
      t = t.getCause();
    }
    if (httpResult == null) {
      httpResult = HttpResult.custom(HttpResultEnum.Throwable).build();
    }
    return httpResult;
  }
}

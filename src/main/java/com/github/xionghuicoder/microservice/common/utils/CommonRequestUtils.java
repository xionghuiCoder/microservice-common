package com.github.xionghuicoder.microservice.common.utils;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest工具类
 *
 * @author xionghui
 * @since 1.0.0
 */
public class CommonRequestUtils {

  /**
   * 获取请求参数
   */
  public static Map<String, String> getParameterMap(HttpServletRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("request is null");
    }
    // 用LinkedHashMap是为了保证插入顺序
    Map<String, String> paramsMap = new LinkedHashMap<String, String>();
    Enumeration<?> paramKeys = request.getParameterNames();
    while (paramKeys.hasMoreElements()) {
      String key = (String) paramKeys.nextElement();
      String value = request.getParameter(key);
      paramsMap.put(key, value);
    }
    return paramsMap;
  }
}

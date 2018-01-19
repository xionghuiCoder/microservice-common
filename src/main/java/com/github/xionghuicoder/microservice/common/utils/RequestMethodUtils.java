package com.github.xionghuicoder.microservice.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;

/**
 * RequestMethod字典类
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class RequestMethodUtils {
  private static final Map<String, HttpRequestMethod> HTTPREQUESTMETHOD_MAP =
      new HashMap<String, HttpRequestMethod>();

  static {
    for (HttpRequestMethod httpRequestMethod : HttpRequestMethod.values()) {
      HTTPREQUESTMETHOD_MAP.put(httpRequestMethod.name().toUpperCase(), httpRequestMethod);
    }
  }

  public static HttpRequestMethod getByName(String name) {
    if (name != null) {
      name = name.toUpperCase();
    }
    return HTTPREQUESTMETHOD_MAP.get(name);
  }
}

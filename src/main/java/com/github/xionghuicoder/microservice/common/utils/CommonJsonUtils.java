package com.github.xionghuicoder.microservice.common.utils;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * json工具类，把Object转为json
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonJsonUtils {
  private static SerializeConfig CONFIG = new SerializeConfig();

  static {
    CONFIG.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
  }

  public static JSONObject object2Json(Object obj, String[] keys) {
    String jsonStr = JSON.toJSONString(obj, CONFIG);
    JSONObject result = JSON.parseObject(jsonStr);
    if (keys != null && result != null) {
      JSONObject tmp = new JSONObject();
      for (String k : keys) {
        Object v = result.get(k);
        tmp.put(k, v);
      }
      result = tmp;
    }
    return result;
  }
}

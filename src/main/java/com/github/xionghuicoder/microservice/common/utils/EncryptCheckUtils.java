package com.github.xionghuicoder.microservice.common.utils;

import java.text.ParseException;
import java.util.Date;

import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.exception.BusinessException;

/**
 * 加密检查：计算时间是否在前后5分钟范围
 *
 * @author xionghui
 * @since 1.0.0
 */
public class EncryptCheckUtils {

  public static void encryptCheck(String function, String body, String time, String key,
      String token) {
    if (function == null || body == null || time == null || key == null || token == null) {
      throw new BusinessException("params illegal");
    }

    Date date;
    try {
      date = CommonConstants.DATE_FORMAT.get().parse(time);
    } catch (ParseException e) {
      throw new BusinessException("time format illegal");
    }
    Date now = new Date();
    // 转换成分钟
    long between = Math.abs((now.getTime() - date.getTime()) / 60_000);
    if (between > 5) {
      throw new BusinessException("time is illegal: " + now + " " + date);
    }

    String value = MD5Utils.md5(function + body + time + key);
    if (!value.equals(token)) {
      throw new BusinessException("value not equals token: " + value + " " + token);
    }
  }
}

package com.github.xionghuicoder.microservice.common.utils;

import java.security.MessageDigest;

import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.exception.BusinessException;

/**
 * md5加密处理类
 *
 * @author xionghui
 * @since 1.0.0
 */
public class MD5Utils {

  // 用来将字节转换成 16 进制表示的字符
  private final static char[] hexDigits =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  /*
   * md5加密操作
   */
  public static String md5(String inbuf) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(inbuf.getBytes(CommonConstants.UTF8_ENCODING));
      byte tmp[] = md.digest();
      char str[] = new char[16 * 2];
      int k = 0;
      for (int i = 0; i < 16; i++) {
        byte byte0 = tmp[i];
        // >>> 为逻辑右移，将符号位一起右移
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      throw new BusinessException(e);
    }
  }
}

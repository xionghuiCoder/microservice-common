package com.github.xionghuicoder.microservice.common.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * common常量类
 *
 * @author xionghui
 * @since 1.0.0
 */
public class CommonConstants {

  public static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
  };

  public static final ThreadLocal<DateFormat> DAY_FORMAT = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd");
    }
  };

  // 编码
  public static final String UTF8_ENCODING = "UTF-8";
  public static final String GBK_ENCODING = "GBK";
  public static final String ISO8859_ENCODING = "ISO-8859-1";

  // 上传文件uri
  public static final String UPLOAD_URI = "upload";
  // 下载文件uri
  public static final String DOWNLOAD_URI = "download";

  // 该值不能变, 用于网关透传到后端服务的cookie前缀
  public static final String PASS_COOKIE_PREFIX = "cookie_";
  // 该值不能变, 用于网关获取多语cookie和eureka服务之间传递多语header
  public static final String LANGUAGE_COOKIE_HEADER = "language";
  // 该值不能变, CommonResourceBundleMessageSourceUtils使用该路径
  public static final String COMMON_LANGUAGE_MESSAGE = "language/common";
  // 该值不能变, 所有其它服务使用该路径
  public static final String LANGUAGE_MESSAGE = "language/message";

  public static final String FUNCTION = "function";
  public static final String BODY = "body";
  // 额外的参数，非必须
  public static final String Ext = "ext";

  // diff标识修改时候的diff bean
  public static final String DIFF_BODY = "diff";
  // origin标识修改时候的origin bean
  public static final String ORIGIN_BODY = "origin";

  public static final String ZUUL_HEAD = "zuul";

  public static final String USER_HEAD = "user";
  public static final String USER_EMPID = "empId";
  public static final String USER_NAME = "name";
  public static final String USER_EMAIL = "email";

  public static final String PERMISSION_HEAD = "permission";
  public static final String PERMISSION_MENU = "menu";
  public static final String PERMISSION_VALUES = "values";

  // 新增返回uuid
  public static final String UUID = "uuid";
  // 批量删除参数key
  public static final String ORIGINS = "origins";

  // 枚举使用的langguageCode
  public static final String LANGUAGE_CODE_ENUM = "languageCode";

}

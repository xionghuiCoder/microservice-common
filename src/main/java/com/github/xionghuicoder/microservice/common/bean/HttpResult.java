package com.github.xionghuicoder.microservice.common.bean;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.bean.enums.IHttpResultEnum;
import com.github.xionghuicoder.microservice.common.utils.CommonResourceBundleMessageSourceUtils;

/**
 * HTTP处理结果
 *
 * @author xionghui
 * @since 1.0.0
 */
public class HttpResult<T> implements Serializable {
  private static final long serialVersionUID = -8583915914014410000L;

  private int code;
  private String msg;
  private T data;
  private JSONObject detail;

  /**
   * json转object时需要无参构造函数
   */
  private HttpResult() {}

  private HttpResult(IHttpResultEnum httpResultEnum, Locale locale, String[] args, T data,
      JSONObject detail) {
    this.code = httpResultEnum.getCode();
    String languageCode = httpResultEnum.getLanguageCode();
    String msg = CommonResourceBundleMessageSourceUtils.getMessage(languageCode, locale, args);
    this.msg = msg;
    this.data = data;
    this.detail = detail;
  }

  public int getCode() {
    return this.code;
  }

  public String getMsg() {
    return this.msg;
  }

  public T getData() {
    return this.data;
  }

  public JSONObject getDetail() {
    return this.detail;
  }


  public static <T> Builder<T> custom(IHttpResultEnum httpResultEnum) {
    if (httpResultEnum == null) {
      httpResultEnum = HttpResultEnum.UndefinedError;
    }
    return new Builder<T>(httpResultEnum);
  }

  /**
   * builder构建类
   *
   * @author xionghui
   * @since 1.0.0
   */
  public static class Builder<T> {
    private final IHttpResultEnum httpResultEnum;
    private final JSONObject detail;
    private Locale locale;
    private String[] args;
    private T data;

    private Builder(IHttpResultEnum httpResultEnum) {
      this.httpResultEnum = httpResultEnum;
      this.detail = new JSONObject();
      String requestId = UUID.randomUUID().toString();
      this.detail.put("requestId", requestId);
    }

    public Builder<T> setLocale(Locale locale) {
      this.locale = locale;
      return this;
    }

    public Builder<T> setArgs(String... args) {
      this.args = args;
      return this;
    }

    public Builder<T> setData(T data) {
      this.data = data;
      return this;
    }

    public HttpResult<T> build() {
      return new HttpResult<T>(this.httpResultEnum, this.locale, this.args, this.data, this.detail);
    }
  }

  @Override
  public String toString() {
    return "HttpResult [code=" + this.code + ", msg=" + this.msg + ", data=" + this.data
        + ", detail=" + this.detail + "]";
  }
}

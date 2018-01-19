package com.github.xionghuicoder.microservice.common.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;

/**
 * 请求参数bean
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonParamsBean implements Serializable {
  private static final long serialVersionUID = -5868979159748751037L;

  // 请求来源是否是网关
  private final boolean isZuul;
  // 请求类型：比如get或者post
  private final HttpRequestMethod httpRequestMethod;
  private final String uri;
  // function标示请求的接口, 通过function去找接口
  private final String function;
  // body为一个json串, 包括请求参数
  private final String body;
  private final JSONObject bodyJson;
  private final String ext;
  // 权限信息
  private final JSONObject permissionJson;
  // 用户信息
  private final User user;

  private CommonParamsBean(boolean isZuul, HttpRequestMethod httpRequestMethod, String uri,
      String function, String body, JSONObject bodyJson, String ext, JSONObject permissionJson,
      User user) {
    this.isZuul = isZuul;
    this.httpRequestMethod = httpRequestMethod;
    this.uri = uri;
    this.function = function;
    this.body = body;
    this.bodyJson = bodyJson;
    this.ext = ext;
    this.permissionJson = permissionJson;
    this.user = user;
  }

  public boolean isZuul() {
    return this.isZuul;
  }

  public HttpRequestMethod getHttpRequestMethod() {
    return this.httpRequestMethod;
  }

  public String getUri() {
    return this.uri;
  }

  public String getFunction() {
    return this.function;
  }

  public String getBody() {
    return this.body;
  }

  public JSONObject getBodyJson() {
    return this.bodyJson;
  }

  public String getExt() {
    return this.ext;
  }

  public JSONObject getPermissionJson() {
    return this.permissionJson;
  }

  public User getUser() {
    return this.user;
  }

  @Override
  public String toString() {
    return "CommonParamsBean [isZuul=" + this.isZuul + ", httpRequestMethod="
        + this.httpRequestMethod + ", uri=" + this.uri + ", function=" + this.function + ", body="
        + this.body + ", bodyJson=" + this.bodyJson + ", ext=" + this.ext + ", permissionJson="
        + this.permissionJson + ", user=" + this.user + "]";
  }

  public static CommonParamsBean.Builder custom() {
    return new Builder();
  }

  /**
   * builder构建类
   *
   * @author xionghui
   * @version 1.0.0
   * @since 1.0.0
   */
  public static class Builder {
    private HttpRequestMethod httpRequestMethod;
    private boolean isZuul;
    private String uri;
    private String function;
    private String body;
    private JSONObject bodyJson;
    private String ext;
    private JSONObject permissionJson;
    private User user;

    private Builder() {}

    public Builder setHttpRequestMethod(HttpRequestMethod httpRequestMethod) {
      this.httpRequestMethod = httpRequestMethod;
      return this;
    }

    public Builder setIsZuul(boolean isZuul) {
      this.isZuul = isZuul;
      return this;
    }

    public Builder setUri(String uri) {
      this.uri = uri;
      return this;
    }

    public Builder setFunction(String function) {
      this.function = function;
      return this;
    }

    public Builder setBody(String body) {
      this.body = body;
      return this;
    }

    public Builder setBodyJson(JSONObject bodyJson) {
      this.bodyJson = bodyJson;
      return this;
    }

    public Builder setExt(String ext) {
      this.ext = ext;
      return this;
    }

    public Builder setPermissionJson(JSONObject permissionJson) {
      this.permissionJson = permissionJson;
      return this;
    }

    public Builder setUser(User user) {
      this.user = user;
      return this;
    }

    public CommonParamsBean build() {
      return new CommonParamsBean(this.isZuul, this.httpRequestMethod, this.uri, this.function,
          this.body, this.bodyJson, this.ext, this.permissionJson, this.user);
    }
  }

  /**
   * 用户相关信息
   * 
   * @author xionghui
   * @version 1.0.0
   * @since 1.0.0
   */
  public static class User implements Serializable {
    private static final long serialVersionUID = -1765421076425048092L;

    private final String empId;

    private final String name;

    private final String email;

    public User(String empId, String name, String email) {
      this.empId = empId;
      this.name = name;
      this.email = email;
    }

    public String getEmpId() {
      return this.empId;
    }

    public String getName() {
      return this.name;
    }

    public String getEmail() {
      return this.email;
    }

    @Override
    public String toString() {
      return "User [empId=" + this.empId + ", name=" + this.name + ", email=" + this.email + "]";
    }
  }
}

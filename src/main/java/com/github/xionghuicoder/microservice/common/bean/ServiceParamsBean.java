package com.github.xionghuicoder.microservice.common.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.bean.CommonParamsBean.User;

/**
 * service参数
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class ServiceParamsBean implements Serializable {
  private static final long serialVersionUID = -9119124667474365022L;

  // body包括请求参数
  private final String body;
  private final JSONObject bodyJson;
  private final String ext;
  // 权限信息
  private final JSONObject permissionJson;
  // 用户信息
  private final User user;

  public ServiceParamsBean(String body, JSONObject bodyJson, String ext, JSONObject permissionJson,
      User user) {
    this.body = body;
    this.bodyJson = bodyJson;
    this.ext = ext;
    this.permissionJson = permissionJson;
    this.user = user;
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
    return "ServiceParamsBean [body=" + this.body + ", bodyJson=" + this.bodyJson + ", ext="
        + this.ext + ", permissionJson=" + this.permissionJson + ", user=" + this.user + "]";
  }

  public static ServiceParamsBean.Builder custom() {
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
    private String body;
    private JSONObject bodyJson;
    private String ext;
    private JSONObject permissionJson;
    private User user;

    private Builder() {}

    public Builder setBody(String body) {
      this.body = body;
      return this;
    }

    public Builder setBodyJson(JSONObject bodyJson) {
      this.bodyJson = bodyJson;
      return this;
    }

    public Builder setPermissionJson(JSONObject permissionJson) {
      this.permissionJson = permissionJson;
      return this;
    }

    public Builder setExt(String ext) {
      this.ext = ext;
      return this;
    }

    public Builder setUser(User user) {
      this.user = user;
      return this;
    }

    public ServiceParamsBean build() {
      return new ServiceParamsBean(this.body, this.bodyJson, this.ext, this.permissionJson,
          this.user);
    }
  }
}

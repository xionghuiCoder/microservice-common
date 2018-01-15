package com.github.xionghuicoder.microservice.common.bean;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.bean.CommonParamsBean.User;

/**
 * 上传文件service参数
 *
 * @author xionghui
 * @since 1.0.0
 */
public class UploadServiceParamsBean extends ServiceParamsBean {
  private static final long serialVersionUID = 1620658824125135378L;

  // 上传时包含此参数
  private final MultipartFile file;

  public UploadServiceParamsBean(String body, JSONObject bodyJson, String ext,
      JSONObject permissionJson, User user, MultipartFile file) {
    super(body, bodyJson, ext, permissionJson, user);
    this.file = file;
  }

  public MultipartFile getFile() {
    return this.file;
  }

  @Override
  public String toString() {
    return "UploadServiceParamsBean [file=" + this.file + "] super: " + super.toString();
  }

  public static UploadServiceParamsBean.UploadBuilder uploadCustom() {
    return new UploadBuilder();
  }

  /**
   * builder构建类
   *
   * @author xionghui
   * @since 1.0.0
   */
  public static class UploadBuilder {
    private String body;
    private JSONObject bodyJson;
    private String ext;
    private JSONObject permissionJson;
    private User user;
    private MultipartFile file;

    private UploadBuilder() {}

    public UploadBuilder setBody(String body) {
      this.body = body;
      return this;
    }

    public UploadBuilder setBodyJson(JSONObject bodyJson) {
      this.bodyJson = bodyJson;
      return this;
    }

    public UploadBuilder setExt(String ext) {
      this.ext = ext;
      return this;
    }

    public UploadBuilder setPermissionJson(JSONObject permissionJson) {
      this.permissionJson = permissionJson;
      return this;
    }

    public UploadBuilder setUser(User user) {
      this.user = user;
      return this;
    }

    public UploadBuilder setFile(MultipartFile file) {
      this.file = file;
      return this;
    }

    public UploadServiceParamsBean build() {
      return new UploadServiceParamsBean(this.body, this.bodyJson, this.ext, this.permissionJson,
          this.user, this.file);
    }
  }
}

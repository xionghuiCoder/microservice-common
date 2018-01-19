package com.github.xionghuicoder.microservice.common.bean;

public class RegisterBean {
  private final String uri;
  private final String path;

  public RegisterBean(String uri, String path) {
    this.uri = uri;
    this.path = path;
  }

  public String getUri() {
    return this.uri;
  }

  public String getPath() {
    return this.path;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + ((this.uri == null) ? 0 : this.uri.hashCode());
    result = 31 * result + ((this.path == null) ? 0 : this.path.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RegisterBean)) {
      return false;
    }
    RegisterBean other = (RegisterBean) obj;
    if (this.uri == null ? other.uri != null : !this.uri.equals(other.uri)) {
      return false;
    }
    if (this.path == null ? other.path != null : !this.path.equals(other.path)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "RegisterBean [uri=" + this.uri + ", path=" + this.path + "]";
  }
}

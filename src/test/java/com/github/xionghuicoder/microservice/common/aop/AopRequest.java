package com.github.xionghuicoder.microservice.common.aop;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/3/2 下午3:39
 * @description
 * @since 1.0.0
 */
public class AopRequest {

  private int id;
  private String name;
  private int type;

  public AopRequest(int id, String name, int type) {
    this.id = id;
    this.name = name;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "AopRequest{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", type=" + type +
        '}';
  }
}

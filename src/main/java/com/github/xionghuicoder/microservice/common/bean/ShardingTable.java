package com.github.xionghuicoder.microservice.common.bean;

/**
 * 分表table bean
 *
 * @author xionghui
 * @date 2018/12/12
 */
public class ShardingTable {
  /**
   * sharding 表名称
   */
  private String name;

  public ShardingTable() {
    super();
  }

  private ShardingTable(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return "ShardingTable [name=" + this.name + "]";
  }

  /**
   * builder
   *
   * @author xionghui
   * @date 2018/12/12
   */
  public static class Builder {
    private String name;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public ShardingTable build() {
      return new ShardingTable(this.name);
    }
  }
}

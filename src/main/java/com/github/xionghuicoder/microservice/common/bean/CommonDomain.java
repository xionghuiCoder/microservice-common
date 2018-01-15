package com.github.xionghuicoder.microservice.common.bean;

import java.sql.Timestamp;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonDomain implements IQueryCondition {
  // fieldSet是给update时记录哪些key需要和数据库的属性比较的set
  private Set<String> fieldSet;
  // origin domain
  private CommonDomain originDomain;

  protected String uuid;

  protected Boolean ds;

  protected String note;

  // 乐观锁: update时使用
  protected Integer version;

  protected String creator;
  protected Timestamp createTime;
  protected String updater;
  protected Timestamp updateTime;

}

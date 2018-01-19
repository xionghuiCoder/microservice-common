package com.github.xionghuicoder.microservice.common.bean;

import java.sql.Timestamp;
import java.util.Set;

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

  public Set<String> getFieldSet() {
    return this.fieldSet;
  }

  public void setFieldSet(Set<String> fieldSet) {
    this.fieldSet = fieldSet;
  }

  public CommonDomain getOriginDomain() {
    return this.originDomain;
  }

  public void setOriginDomain(CommonDomain originDomain) {
    this.originDomain = originDomain;
  }

  public String getUuid() {
    return this.uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Boolean getDs() {
    return this.ds;
  }

  public void setDs(Boolean ds) {
    this.ds = ds;
  }

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public String getCreator() {
    return this.creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Timestamp getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getUpdater() {
    return this.updater;
  }

  public void setUpdater(String updater) {
    this.updater = updater;
  }

  public Timestamp getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public String toString() {
    return "CommonDomain [fieldSet=" + this.fieldSet + ", originDomain=" + this.originDomain
        + ", uuid=" + this.uuid + ", ds=" + this.ds + ", note=" + this.note + ", version="
        + this.version + ", creator=" + this.creator + ", createTime=" + this.createTime
        + ", updater=" + this.updater + ", updateTime=" + this.updateTime + "]";
  }
}

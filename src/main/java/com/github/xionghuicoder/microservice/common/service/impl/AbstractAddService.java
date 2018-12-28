package com.github.xionghuicoder.microservice.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.xionghuicoder.microservice.common.dao.repository.InsertRepository;
import com.github.xionghuicoder.microservice.common.service.AddService;

/**
 * 新增数据抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> InsertRepository
 * @param <Po> Po
 */
public abstract class AbstractAddService<Dao extends InsertRepository<Po>, Po>
    implements AddService<Po> {
  @Autowired
  protected Dao dao;

  @Override
  public int add(final Po record) {
    return this.dao.insert(record);
  }

  @Override
  public int batchAdd(final List<Po> records) {
    return this.dao.batchInsert(records);
  }

  @Override
  public int batchAddOnDuplicateKey(final List<Po> records) {
    return this.dao.batchInsertOnDuplicateKey(records);
  }
}

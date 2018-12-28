package com.github.xionghuicoder.microservice.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.xionghuicoder.microservice.common.dao.repository.UpdateRepository;
import com.github.xionghuicoder.microservice.common.service.EditService;

/**
 * 更新服务抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> UpdateRepository
 * @param <Po> Po
 * @param <Example> Example
 */
public abstract class AbstractEditService<Dao extends UpdateRepository<Po, Example>, Po, Example>
    implements EditService<Po, Example> {
  @Autowired
  protected Dao dao;

  @Override
  public int editById(final Po record) {
    return this.dao.updateById(record);
  }

  @Override
  public int editByExample(final Po record, final Example example) {
    return this.dao.updateByExample(record, example);
  }

  @Override
  public int batchEdit(final List<Po> records) {
    return this.dao.batchUpdate(records);
  }
}

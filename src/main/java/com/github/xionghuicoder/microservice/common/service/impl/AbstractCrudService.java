package com.github.xionghuicoder.microservice.common.service.impl;

import java.util.List;

import com.github.xionghuicoder.microservice.common.dao.repository.CrudRepository;
import com.github.xionghuicoder.microservice.common.service.CrudService;

/**
 * 基本增删改查(CRUD)数据访问服务抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> CrudRepository
 * @param <Po> Po
 * @param <Example> Example
 * @param <Type> 主键字段数据类型(Integer,Long等)
 */
public abstract class AbstractCrudService<Dao extends CrudRepository<Po, Example, Type>, Po, Example, Type>
    extends AbstractGetService<Dao, Po, Example, Type> implements CrudService<Po, Example, Type> {

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

  @Override
  public int removeById(final Type id) {
    return this.dao.deleteById(id);
  }

  @Override
  public int removeByExample(final Example example) {
    return this.dao.deleteByExample(example);
  }

  @Override
  public int removeIn(final List<Po> records) {
    return this.dao.deleteIn(records);
  }
}

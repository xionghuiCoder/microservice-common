package com.github.xionghuicoder.microservice.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.xionghuicoder.microservice.common.dao.repository.DeleteRepository;
import com.github.xionghuicoder.microservice.common.service.RemoveService;

/**
 * 删除服务抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> DeleteRepository
 * @param <Po> Po
 * @param <Example> Example
 * @param <Type> 主键字段数据类型(Integer,Long等)
 */
public abstract class AbstractRemoveService<Dao extends DeleteRepository<Po, Example, Type>, Po, Example, Type>
    implements RemoveService<Po, Example, Type> {
  @Autowired
  protected Dao dao;

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

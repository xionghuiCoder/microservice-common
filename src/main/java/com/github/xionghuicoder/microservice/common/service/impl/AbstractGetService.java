package com.github.xionghuicoder.microservice.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.xionghuicoder.microservice.common.dao.repository.SelectRepository;
import com.github.xionghuicoder.microservice.common.service.GetService;

/**
 * 查询服务抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> SelectRepository
 * @param <Po> Po
 * @param <Example> Example
 * @param <Type> 主键字段数据类型(Integer,Long等)
 */
public abstract class AbstractGetService<Dao extends SelectRepository<Po, Example, Type>, Po, Example, Type>
    implements GetService<Po, Example, Type> {
  @Autowired
  protected Dao dao;

  @Override
  public boolean exists(final Example example) {
    return this.dao.countByExample(example) > 0;
  }

  @Override
  public int countByExample(final Example example) {
    return this.dao.countByExample(example);
  }

  @Override
  public Po getById(final Type id) {
    return this.dao.selectById(id);
  }

  @Override
  public List<Po> getByExample(final Example example) {
    return this.dao.selectByExample(example);
  }

  @Override
  public List<Po> getAll() {
    return this.dao.selectByExample(null);
  }

  @Override
  public Po getOneByExample(final Example example) {
    return this.dao.selectOneByExample(example);
  }

  @Override
  public List<Po> getIn(final List<Po> records) {
    return this.dao.selectIn(records);
  }
}

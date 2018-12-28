package com.github.xionghuicoder.microservice.common.service.sharding.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;
import com.github.xionghuicoder.microservice.common.dao.repository.sharding.SelectShardingRepository;
import com.github.xionghuicoder.microservice.common.service.sharding.GetShardingService;

/**
 * 分表查询服务抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> SelectShardingRepository
 * @param <Po> Po
 * @param <Example> Example
 * @param <Type> 主键字段数据类型(Integer,Long等)
 */
public abstract class AbstractGetShardingService<Dao extends SelectShardingRepository<Po, Example, Type>, Po, Example, Type>
    implements GetShardingService<Po, Example, Type> {
  @Autowired
  protected Dao dao;

  @Override
  public boolean exists(final Example example, final ShardingTable shardingTable) {
    return this.dao.countByExample(example, shardingTable) > 0;
  }

  @Override
  public int countByExample(final Example example, final ShardingTable shardingTable) {
    return this.dao.countByExample(example, shardingTable);
  }

  @Override
  public Po getById(final Type id, final ShardingTable shardingTable) {
    return this.dao.selectById(id, shardingTable);
  }

  @Override
  public List<Po> getByExample(final Example example, final ShardingTable shardingTable) {
    return this.dao.selectByExample(example, shardingTable);
  }

  @Override
  public List<Po> getAll(final ShardingTable shardingTable) {
    return this.dao.selectByExample(null, shardingTable);
  }

  @Override
  public Po getOneByExample(final Example example, final ShardingTable shardingTable) {
    return this.dao.selectOneByExample(example, shardingTable);
  }

  @Override
  public List<Po> getIn(final List<Po> records, final ShardingTable shardingTable) {
    return this.dao.selectIn(records, shardingTable);
  }
}

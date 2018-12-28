package com.github.xionghuicoder.microservice.common.service.sharding.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;
import com.github.xionghuicoder.microservice.common.dao.repository.sharding.DeleteShardingRepository;
import com.github.xionghuicoder.microservice.common.service.sharding.RemoveShardingService;

/**
 * 分表删除服务抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> DeleteShardingRepository
 * @param <Po> Po
 * @param <Example> Example
 * @param <Type> 主键字段数据类型(Integer,Long等)
 */
public abstract class AbstractRemoveShardingService<Dao extends DeleteShardingRepository<Po, Example, Type>, Po, Example, Type>
    implements RemoveShardingService<Po, Example, Type> {
  @Autowired
  protected Dao dao;

  @Override
  public int removeById(final Type id, final ShardingTable shardingTable) {
    return this.dao.deleteById(id, shardingTable);
  }

  @Override
  public int removeByExample(final Example example, final ShardingTable shardingTable) {
    return this.dao.deleteByExample(example, shardingTable);
  }

  @Override
  public int removeIn(final List<Po> records, final ShardingTable shardingTable) {
    return this.dao.deleteIn(records, shardingTable);
  }
}

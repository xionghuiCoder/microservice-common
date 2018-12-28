package com.github.xionghuicoder.microservice.common.service.sharding.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;
import com.github.xionghuicoder.microservice.common.dao.repository.sharding.UpdateShardingRepository;
import com.github.xionghuicoder.microservice.common.service.sharding.EditShardingService;

/**
 * 分表更新服务抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> UpdateShardingRepository
 * @param <Po> Po
 * @param <Example> Example
 */
public abstract class AbstractEditShardingService<Dao extends UpdateShardingRepository<Po, Example>, Po, Example>
    implements EditShardingService<Po, Example> {
  @Autowired
  protected Dao dao;

  @Override
  public int editById(final Po record, final ShardingTable shardingTable) {
    return this.dao.updateById(record, shardingTable);
  }

  @Override
  public int editByExample(final Po record, final Example example,
      final ShardingTable shardingTable) {
    return this.dao.updateByExample(record, example, shardingTable);
  }

  @Override
  public int batchEdit(final List<Po> records, final ShardingTable shardingTable) {
    return this.dao.batchUpdate(records, shardingTable);
  }
}

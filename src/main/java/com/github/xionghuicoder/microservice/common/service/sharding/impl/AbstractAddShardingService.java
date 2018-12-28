package com.github.xionghuicoder.microservice.common.service.sharding.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;
import com.github.xionghuicoder.microservice.common.dao.repository.sharding.InsertShardingRepository;
import com.github.xionghuicoder.microservice.common.service.sharding.AddShardingService;

/**
 * 分表新增数据抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> InsertShardingRepository
 * @param <Po> Po
 */
public abstract class AbstractAddShardingService<Dao extends InsertShardingRepository<Po>, Po>
    implements AddShardingService<Po> {
  @Autowired
  protected Dao dao;

  @Override
  public int add(final Po record, final ShardingTable shardingTable) {
    return this.dao.insert(record, shardingTable);
  }

  @Override
  public int batchAdd(final List<Po> records, final ShardingTable shardingTable) {
    return this.dao.batchInsert(records, shardingTable);
  }

  @Override
  public int batchAddOnDuplicateKey(final List<Po> records, final ShardingTable shardingTable) {
    return this.dao.batchInsertOnDuplicateKey(records, shardingTable);
  }
}

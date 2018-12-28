package com.github.xionghuicoder.microservice.common.service.sharding.impl;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;
import com.github.xionghuicoder.microservice.common.dao.repository.sharding.CrudShardingRepository;
import com.github.xionghuicoder.microservice.common.service.sharding.CrudShardingService;

/**
 * 分表基本增删改查(CRUD)数据访问服务抽象类
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <Dao> CrudShardingRepository
 * @param <Po> Po
 * @param <Example> Example
 * @param <Type> 主键字段数据类型(Integer,Long等)
 */
public abstract class AbstractCrudShardingService<Dao extends CrudShardingRepository<Po, Example, Type>, Po, Example, Type>
    extends AbstractGetShardingService<Dao, Po, Example, Type>
    implements CrudShardingService<Po, Example, Type> {

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

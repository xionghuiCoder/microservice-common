package com.github.xionghuicoder.microservice.common.service.sharding;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;

/**
 * 分表新增服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T> Po
 */
public interface AddShardingService<T> {

  /**
   * 插入一条数据，忽略record中的id
   *
   * @param record 插入的数据
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int add(T record, ShardingTable shardingTable);

  /**
   * 批量插入数据
   *
   * @param records 批量插入的数据
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int batchAdd(List<T> records, ShardingTable shardingTable);

  /**
   * 使用mysql on duplicate key 语句插入与修改
   *
   * @param records 批量插入的数据
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int batchAddOnDuplicateKey(List<T> records, ShardingTable shardingTable);
}

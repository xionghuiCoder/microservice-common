package com.github.xionghuicoder.microservice.common.service.sharding;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;

/**
 * 分表更新服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T>
 * @param <U>
 */
public interface EditShardingService<T, U> {
  /**
   * 根据主键更新用户信息
   *
   * @param record 更新的记录
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int editById(T record, ShardingTable shardingTable);

  /**
   * 根据条件更新数据
   *
   * @param record 更新的记录
   * @param example where条件对象
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int editByExample(T record, U example, ShardingTable shardingTable);

  /**
   * 批量更新记录
   * 
   * @param records 批量更新的记录
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int batchEdit(List<T> records, ShardingTable shardingTable);
}

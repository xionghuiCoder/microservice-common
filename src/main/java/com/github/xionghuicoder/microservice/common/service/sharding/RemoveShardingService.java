package com.github.xionghuicoder.microservice.common.service.sharding;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;

/**
 * 分表删除服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T>
 * @param <U>
 * @param <K>
 */
public interface RemoveShardingService<T, U, K> {
  /**
   * 根据主键删除记录
   *
   * @param id id
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int removeById(K id, ShardingTable shardingTable);

  /**
   * 根据条件删除记录
   *
   * @param example 查询Example条件参数
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int removeByExample(U example, ShardingTable shardingTable);

  /**
   * 根据records的id删除数据
   * 
   * @param records 记录列表
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int removeIn(List<T> records, ShardingTable shardingTable);
}

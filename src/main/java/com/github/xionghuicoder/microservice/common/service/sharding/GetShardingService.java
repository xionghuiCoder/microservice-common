package com.github.xionghuicoder.microservice.common.service.sharding;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;

/**
 * 分表查询服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface GetShardingService<T, U, K> {

  /**
   * 根据查询条件判断数据是否存在
   *
   * @param example 查询条件
   * @param shardingTable 分表对象
   * @return 满足查询的数据是否存在
   */
  boolean exists(U example, ShardingTable shardingTable);

  /**
   * 根据查询条件查询数据条数
   *
   * @param example 查询条件
   * @param shardingTable 分表对象
   * @return 满足查询的数据条数
   */
  int countByExample(U example, ShardingTable shardingTable);

  /**
   * 通过主键找出一条数据
   *
   * @param id 主键id值
   * @param shardingTable 分表对象
   * @return 当前id对象的记录
   */
  T getById(K id, ShardingTable shardingTable);

  /**
   * 根据条件查询零条及多条数据
   *
   * @param example 查询条件参数
   * @param shardingTable 分表对象
   * @return 记录列表
   */
  List<T> getByExample(U example, ShardingTable shardingTable);

  /**
   * 根据条件查询所有记录
   *
   * @param shardTable 分表对象
   * @return 记录列表
   */
  List<T> getAll(ShardingTable shardingTable);

  /**
   * 根据条件查询一条数据
   *
   * @param example 查询条件参数
   * @param shardingTable 分表对象
   * @return 分页记录列表
   */
  T getOneByExample(U example, ShardingTable shardingTable);

  /**
   * select in() 查询
   *
   * @param records 包含id的列表
   * @param shardingTable 分表对象
   * @return 记录列表
   */
  List<T> getIn(List<T> records, ShardingTable shardingTable);
}

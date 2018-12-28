package com.github.xionghuicoder.microservice.common.dao.repository.sharding;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;

/**
 * 分表删除
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface DeleteShardingRepository<T, U, K> {
  /**
   * 根据主键删除记录
   *
   * @param id id主键值
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int deleteById(@Param("id") K id, @Param("shardingTable") ShardingTable shardingTable);

  /**
   * 根据条件删除记录
   *
   * @param example 查询Example条件参数
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int deleteByExample(@Param("example") U example,
      @Param("shardingTable") ShardingTable shardingTable);

  /**
   * 根据records的id删除数据
   *
   * @param records 记录列表
   * @param shardingTable 分表对象
   * @return 影响的记录数
   */
  int deleteIn(@Param("records") List<T> records,
      @Param("shardingTable") ShardingTable shardingTable);
}

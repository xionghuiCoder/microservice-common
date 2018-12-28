package com.github.xionghuicoder.microservice.common.dao.repository.sharding;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;

/**
 * 分表更新
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 * @param <U> Example
 */
public interface UpdateShardingRepository<T, U> {
  /**
   * 根据主键更新用户信息
   *
   * @param record pojo记录
   * @param shardTable 分表对象
   * @return 影响的记录数
   */
  int updateById(@Param("record") T record, @Param("shardingTable") ShardingTable shardingTable);

  /**
   * 根据条件更新数据
   *
   * @param record pojo记录
   * @param example
   * @param shardTable 分表对象
   * @return 影响的记录数
   */
  int updateByExample(@Param("record") T record, @Param("example") U example,
      @Param("shardingTable") ShardingTable shardingTable);

  /**
   * @param records pojo记录集
   * @param shardTable 分表对象
   * @return 影响的记录数
   */
  int batchUpdate(@Param("records") List<T> records,
      @Param("shardingTable") ShardingTable shardingTable);
}

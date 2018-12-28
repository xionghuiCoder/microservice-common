package com.github.xionghuicoder.microservice.common.dao.repository.sharding;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;

/**
 * 分表插入数据
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 */
public interface InsertShardingRepository<T> {
  /**
   * 插入一条数据，忽略record中的ID
   *
   * @param record pojo对象
   * @param shardTable 分表对象
   * @return 影响的记录数
   */
  int insert(@Param("record") T record, @Param("shardingTable") ShardingTable shardingTable);

  /**
   * @param records pojo记录集
   * @param shardTable 分表对象
   * @return 影响的记录数
   */
  int batchInsert(@Param("records") List<T> records,
      @Param("shardingTable") ShardingTable shardingTable);

  /**
   * 使用mysql on duplicate key 语句插入与修改
   *
   * @param records pojo记录集
   * @param shardTable 分表对象
   * @return 影响的记录数
   */
  int batchInsertOnDuplicateKey(@Param("records") List<T> records,
      @Param("shardingTable") ShardingTable shardingTable);
}

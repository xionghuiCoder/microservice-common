package com.github.xionghuicoder.microservice.common.dao.repository.sharding;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.xionghuicoder.microservice.common.bean.ShardingTable;

/**
 * 分表查询
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface SelectShardingRepository<T, U, K> {
  /**
   * 通过主键找出一条数据
   *
   * @param id 主键id值
   * @param shardTable 分表对象
   * @return 记录
   */
  T selectById(@Param("id") K id, @Param("shardingTable") ShardingTable shardingTable);

  /**
   * 根据条件查询零条及多条数据
   *
   * @param example 查询条件参数
   * @param shardTable 分表对象
   * @return 记录列表
   */
  List<T> selectByExample(@Param("example") U example,
      @Param("shardingTable") ShardingTable shardingTable);

  /**
   * 根据条件查询一条数据
   *
   * @param example 查询条件参数
   * @param shardTable 分表对象
   * @return 第一条记录
   */
  T selectOneByExample(@Param("example") U example,
      @Param("shardingTable") ShardingTable shardingTable);

  /**
   * @param records pojo记录集
   * @param shardTable 分表对象
   * @return 记录列表
   */
  List<T> selectIn(@Param("records") List<T> records,
      @Param("shardingTable") ShardingTable shardingTable);

  /**
   * 根据条件获取查询的总记录数
   *
   * @param example 查询条件参数
   * @param shardTable 分表对象
   * @return 总记录数
   */
  int countByExample(@Param("example") U example,
      @Param("shardingTable") ShardingTable shardingTable);
}

package com.github.xionghuicoder.microservice.common.dao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 插入数据
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 */
public interface InsertRepository<T> {
  /**
   * 插入一条数据，忽略record中的id
   *
   * @param record 插入的数据
   * @return 影响的记录数
   */
  int insert(@Param("record") T record);

  /**
   * 批量插入数据
   *
   * @param records 批量插入的数据
   * @return 影响的记录数
   */
  int batchInsert(@Param("records") List<T> records);

  /**
   * 使用mysql on duplicate key 语句插入与修改
   *
   * @param records 批量插入的数据
   * @return 影响的记录数
   */
  int batchInsertOnDuplicateKey(@Param("records") List<T> records);
}

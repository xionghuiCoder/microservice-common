package com.github.xionghuicoder.microservice.common.dao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 更新数据
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 * @param <U> Example
 */
public interface UpdateRepository<T, U> {
  /**
   * 根据主键更新用户信息
   *
   * @param record 修改的记录
   * @return 影响的记录数
   */
  int updateById(@Param("record") T record);

  /**
   * 根据条件更新数据
   *
   * @param record 修改的记录
   * @param example 修改条件
   * @return 影响的记录数
   */
  int updateByExample(@Param("record") T record, @Param("example") U example);

  /**
   * 批量修改记录
   * 
   * @param records 批量修改的记录
   * @return 影响的记录数
   */
  int batchUpdate(@Param("records") List<T> records);
}

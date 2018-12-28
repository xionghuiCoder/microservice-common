package com.github.xionghuicoder.microservice.common.dao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 删除数据
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface DeleteRepository<T, U, K> {
  /**
   * 根据主键删除记录
   *
   * @param id id主键值
   * @return 影响的记录数
   */
  int deleteById(@Param("id") K id);

  /**
   * 根据条件删除记录
   *
   * @param example 查询Example条件参数
   * @return 影响的记录数
   */
  int deleteByExample(@Param("example") U example);

  /**
   * 根据records的id列表删除记录
   *
   * @param records 包含id的列表
   * @return 影响的记录数
   */
  int deleteIn(@Param("records") List<T> records);
}

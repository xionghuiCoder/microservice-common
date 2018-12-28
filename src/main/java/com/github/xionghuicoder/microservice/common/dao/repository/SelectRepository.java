package com.github.xionghuicoder.microservice.common.dao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 查询数据
 *
 * @author xionghui
 * @date 2018/12/12
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface SelectRepository<T, U, K> {
  /**
   * 通过主键找出一条数据
   *
   * @param id 主键id值
   * @return 记录
   */
  T selectById(@Param("id") K id);

  /**
   * 根据条件查询零条及多条数据
   *
   * @param example 查询条件参数
   * @return 记录列表
   */
  List<T> selectByExample(@Param("example") U example);

  /**
   * 根据条件查询一条数据
   *
   * @param example 查询条件参数
   * @return 第一条记录
   */
  T selectOneByExample(@Param("example") U example);

  /**
   * 根据in查询数据
   *
   * @param records 记录查询条件列表
   * @return 记录列表
   */
  List<T> selectIn(@Param("records") List<T> records);

  /**
   * 根据条件获取查询的总记录数
   *
   * @param example 查询条件参数
   * @return 总记录数
   */
  int countByExample(@Param("example") U example);
}

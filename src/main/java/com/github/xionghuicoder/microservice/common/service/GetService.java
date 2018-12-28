package com.github.xionghuicoder.microservice.common.service;

import java.util.List;

/**
 * 查询服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface GetService<T, U, K> {

  /**
   * 根据查询条件判断数据是否存在
   *
   * @param example 查询条件
   * @return 满足查询的数据是否存在
   */
  boolean exists(U example);

  /**
   * 根据查询条件查询数据条数
   *
   * @param example 查询条件
   * @return 满足查询的数据条数
   */
  int countByExample(U example);

  /**
   * 通过主键找出一条数据
   *
   * @param id 主键id值
   * @return 当前id对象的记录
   */
  T getById(K id);

  /**
   * 根据条件查询零条及多条数据
   *
   * @param example 查询条件参数
   * @return 记录列表
   */
  List<T> getByExample(U example);

  /**
   * 根据条件查询所有记录
   *
   * @return 记录列表
   */
  List<T> getAll();

  /**
   * 根据条件查询一条数据
   *
   * @param example 查询条件参数
   * @return 一条记录
   */
  T getOneByExample(U example);

  /**
   * select in() 查询
   *
   * @param records 包含id的列表
   * @return 记录列表
   */
  List<T> getIn(List<T> records);
}

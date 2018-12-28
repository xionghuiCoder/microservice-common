package com.github.xionghuicoder.microservice.common.service;

import java.util.List;

/**
 * 删除服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T> Po
 * @param <U> Example
 * @param <K> 主键字段数据类型(Integer,Long等)
 */
public interface RemoveService<T, U, K> {
  /**
   * 根据主键删除记录
   *
   * @param id id主键值
   * @return 影响的记录数
   */
  int removeById(K id);

  /**
   * 根据条件删除记录
   *
   * @param example 查询Example条件参数
   * @return 影响的记录数
   */
  int removeByExample(U example);

  /**
   * 根据records的id列表删除记录
   *
   * @param records 包含id的列表
   * @return 影响的记录数
   */
  int removeIn(List<T> records);
}

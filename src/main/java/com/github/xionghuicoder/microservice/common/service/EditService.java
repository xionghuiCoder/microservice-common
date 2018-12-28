package com.github.xionghuicoder.microservice.common.service;

import java.util.List;

/**
 * 更新服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T> Po
 * @param <U> Example
 */
public interface EditService<T, U> {
  /**
   * 根据主键更新用户信息
   *
   * @param record 更新的记录
   * @return 影响的记录数
   */
  int editById(T record);

  /**
   * 根据条件更新数据
   *
   * @param record 更新的记录
   * @param example where条件对象
   * @return 影响的记录数
   */
  int editByExample(T record, U example);

  /**
   * 批量更新记录
   *
   * @param records 批量更新的记录
   * @return 影响的记录数
   */
  int batchEdit(List<T> records);
}

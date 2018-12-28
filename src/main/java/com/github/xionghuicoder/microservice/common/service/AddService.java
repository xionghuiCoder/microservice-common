package com.github.xionghuicoder.microservice.common.service;

import java.util.List;

/**
 * 新增服务
 *
 * @author xionghui
 * @date 2018/12/28
 * @param <T> Po
 */
public interface AddService<T> {

  /**
   * 插入一条数据，忽略record中的id
   *
   * @param record 插入的数据
   * @return 影响的记录数
   */
  int add(T record);

  /**
   * 批量插入数据
   *
   * @param records 批量插入的数据
   * @return 影响的记录数
   */
  int batchAdd(List<T> records);

  /**
   * 使用mysql on duplicate key 语句插入与修改
   *
   * @param records 批量插入的数据
   * @return 影响的记录数
   */
  int batchAddOnDuplicateKey(List<T> records);
}

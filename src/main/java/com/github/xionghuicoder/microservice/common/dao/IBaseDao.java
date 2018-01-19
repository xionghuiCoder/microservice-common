package com.github.xionghuicoder.microservice.common.dao;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.CommonDomain;

/**
 * 定义DAO操作接口
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IBaseDao<B extends CommonDomain> {

  void insert(B bean);

  int update(B bean);

  List<B> queryOrigin(B condition);

  int delete(B bean);

  int batchInsert(List<B> beanList);

  int batchDelete(List<B> beanList);

  List<B> queryOriginList(List<B> beanList);
}

package com.github.xionghuicoder.microservice.common.dao;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.CommonDomain;

/**
 * 定义DAO操作接口
 *
 * @author xionghui
 * @since 1.0.0
 */
public interface IBaseDao<B extends CommonDomain> {

  public void insert(B bean);

  public int update(B bean);

  public List<B> queryOrigin(B condition);

  public int delete(B bean);

  public int batchInsert(List<B> beanList);

  public int batchDelete(List<B> beanList);

  public List<B> queryOriginList(List<B> beanList);

}

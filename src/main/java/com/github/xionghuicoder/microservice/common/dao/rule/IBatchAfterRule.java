package com.github.xionghuicoder.microservice.common.dao.rule;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.CommonDomain;

/**
 * 该规则在执行完sql但是未提交事务时执行
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IBatchAfterRule<B extends CommonDomain> {

  void afterRule(List<B> beanList, List<B> originBeanList);
}

package com.github.xionghuicoder.microservice.common.dao.rule;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.CommonDomain;

/**
 * 该规则在执行sql前时执行
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IBatchBeforeRule<B extends CommonDomain> {

  void beforeRule(List<B> beanList, List<B> originBeanList);
}

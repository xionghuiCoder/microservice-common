package com.github.xionghuicoder.microservice.common.dao.rule;

import java.util.List;

import com.github.xionghuicoder.microservice.common.bean.CommonDomain;

/**
 * 该规则在执行批量sql前时执行
 *
 * @author xionghui
 * @since 1.0.0
 */
public interface IBatchBeforeRule<B extends CommonDomain> {

  public void beforeRule(List<B> beanList, List<B> originBeanList);

}

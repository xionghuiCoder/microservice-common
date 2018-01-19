package com.github.xionghuicoder.microservice.common.dao.rule.impl;

import java.sql.Timestamp;
import java.util.UUID;

import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.bean.CommonDomain;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.dao.rule.IBeforeRule;

/**
 * 检查创建人是否为空，设置uuid和创建时间
 * 
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class CheckParamsInsertBeforeRule<B extends CommonDomain> implements IBeforeRule<B> {

  @Override
  public void beforeRule(B bean, B originBean) {
    String creator = bean.getCreator();
    if (creator == null) {
      throw new BusinessException("creator is null", HttpResultEnum.ParamsCreatorNullError);
    }

    String uuid = UUID.randomUUID().toString();
    bean.setUuid(uuid);

    bean.setUpdater(null);
    bean.setUpdateTime(null);

    Timestamp createTime = new Timestamp(System.currentTimeMillis());
    bean.setCreateTime(createTime);
  }
}

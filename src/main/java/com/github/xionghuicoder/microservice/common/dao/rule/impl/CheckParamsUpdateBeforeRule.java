package com.github.xionghuicoder.microservice.common.dao.rule.impl;

import java.sql.Timestamp;

import com.github.xionghuicoder.microservice.common.bean.CommonDomain;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.dao.rule.IBeforeRule;
import com.github.xionghuicoder.microservice.common.exception.BusinessException;

/**
 * 检查updater和uuId是否为null，补全updateTime
 *
 * @author xionghui
 * @since 1.0.0
 */
public class CheckParamsUpdateBeforeRule<B extends CommonDomain> implements IBeforeRule<B> {

  @Override
  public void beforeRule(CommonDomain bean, CommonDomain originBean) {
    String uuid = bean.getUuid();
    if (uuid == null) {
      throw new BusinessException("uuid is null", HttpResultEnum.ParamsUuidNullError);
    }

    String updater = bean.getUpdater();
    if (updater == null) {
      throw new BusinessException("updater is null", HttpResultEnum.ParamsUpdateNullError);
    }

    bean.setCreator(null);
    bean.setCreateTime(null);

    Timestamp updateTime = new Timestamp(System.currentTimeMillis());
    bean.setUpdateTime(updateTime);
  }
}

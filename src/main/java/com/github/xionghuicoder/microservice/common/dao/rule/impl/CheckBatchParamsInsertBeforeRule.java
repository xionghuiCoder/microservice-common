package com.github.xionghuicoder.microservice.common.dao.rule.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.github.xionghuicoder.microservice.common.bean.CommonDomain;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.dao.rule.IBatchBeforeRule;
import com.github.xionghuicoder.microservice.common.exception.BusinessException;

/**
 * 检查创建人是否为空,设置uuid和创建时间
 *
 * @author xionghui
 * @since 1.0.0
 */
public class CheckBatchParamsInsertBeforeRule<B extends CommonDomain>
    implements IBatchBeforeRule<B> {

  @Override
  public void beforeRule(List<B> beanList, List<B> originBeanList) {
    Timestamp createTime = new Timestamp(System.currentTimeMillis());
    for (B bean : beanList) {
      String creator = bean.getCreator();
      if (creator == null) {
        throw new BusinessException("creator is null", HttpResultEnum.ParamsCreatorNullError);
      }

      String uuid = UUID.randomUUID().toString();
      bean.setUuid(uuid);

      bean.setUpdater(null);
      bean.setUpdateTime(null);

      bean.setCreateTime(createTime);
    }
  }
}

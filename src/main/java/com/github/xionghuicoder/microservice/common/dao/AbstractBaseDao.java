package com.github.xionghuicoder.microservice.common.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.CommonDomain;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.bean.enums.IHttpResultEnum;
import com.github.xionghuicoder.microservice.common.dao.rule.IAfterRule;
import com.github.xionghuicoder.microservice.common.dao.rule.IBatchAfterRule;
import com.github.xionghuicoder.microservice.common.dao.rule.IBatchBeforeRule;
import com.github.xionghuicoder.microservice.common.dao.rule.IBeforeRule;
import com.github.xionghuicoder.microservice.common.dao.rule.impl.CheckBatchParamsInsertBeforeRule;
import com.github.xionghuicoder.microservice.common.dao.rule.impl.CheckParamsInsertBeforeRule;
import com.github.xionghuicoder.microservice.common.dao.rule.impl.CheckParamsUpdateBeforeRule;
import com.github.xionghuicoder.microservice.common.exception.BusinessException;
import com.github.xionghuicoder.microservice.common.exception.DAOException;

/**
 * DAO模板方法
 *
 * @author xionghui
 * @since 1.0.0
 */
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public abstract class AbstractBaseDao<B extends CommonDomain> implements IBaseDao<B> {
  protected IBaseDao<B> iBaseDao;

  private final LinkedList<IBeforeRule<B>> iBeforeRuleList = new LinkedList<>();
  private final LinkedList<IAfterRule<B>> iAfterRuleList = new LinkedList<>();

  private final LinkedList<IBatchBeforeRule<B>> iBatchBeforeRuleList = new LinkedList<>();
  private final LinkedList<IBatchAfterRule<B>> iBatchAfterRuleList = new LinkedList<>();

  @Override
  public void insert(B bean) {
    this.checkBean(bean);
    this.iBeforeRuleList.addFirst(new CheckParamsInsertBeforeRule<B>());
    for (IBeforeRule<B> iBeforeRule : this.iBeforeRuleList) {
      iBeforeRule.beforeRule(bean, bean);
    }
    this.iBaseDao.insert(bean);
    for (IAfterRule<B> iAfterRule : this.iAfterRuleList) {
      iAfterRule.afterRule(bean, bean);
    }
  }

  protected void checkUpdateBefore(B diffBean, B originDBBean) {
    Integer version = originDBBean.getVersion();
    if (version == null) {
      throw new DAOException(HttpResultEnum.VersionNullError);
    }
    @SuppressWarnings("unchecked")
    B originDomain = (B) diffBean.getOriginDomain();
    this.convertBean(diffBean, originDomain, originDBBean);

    for (IBeforeRule<B> iBeforeRule : this.iBeforeRuleList) {
      iBeforeRule.beforeRule(diffBean, originDBBean);
    }
    // 填充version, 进行乐观锁校验
    diffBean.setVersion(version);
  }

  protected void checkUpdateAfter(B diffBean, B originDBBean) {
    for (IAfterRule<B> iAfterRule : this.iAfterRuleList) {
      iAfterRule.afterRule(diffBean, originDBBean);
    }
  }

  /**
   * 前端diff包含key则表示改值，不包含key则表示无修改; 前端origin包含的key表示不能变动，否则提示修改失败
   */
  @Override
  public int update(B diffBean) {
    this.checkAndInitDiffOriginBean(diffBean);
    B originDBBean = this.queryOriginBean(diffBean);
    this.iBeforeRuleList.addFirst(new CheckParamsUpdateBeforeRule<B>());
    this.checkUpdateBefore(diffBean, originDBBean);
    int result = this.iBaseDao.update(diffBean);
    if (result != 1) {
      throw new DAOException(HttpResultEnum.UpdateWhileUpdatedDeletedFail);
    }
    this.checkUpdateAfter(diffBean, originDBBean);
    return result;
  }

  protected B queryOriginBean(B condition) {
    B domain = null;
    if (condition != null && condition.getUuid() != null) {
      List<B> domainList = this.iBaseDao.queryOrigin(condition);
      if (domainList != null && domainList.size() == 1) {
        domain = domainList.get(0);
      }
    }
    if (domain == null) {
      throw new DAOException(HttpResultEnum.NotExistedError);
    }
    return domain;
  }

  /**
   * 计算diffBean和originDBBean的数据，恢复diffBean未修改的一些值
   */
  private void convertBean(B diffBean, B originDomain, B originDBBean) {
    originDomain.setUuid(diffBean.getUuid());
    originDBBean.setUuid(diffBean.getUuid());

    Set<String> diffFieldSet = diffBean.getFieldSet();
    Set<String> originFieldSet = originDomain.getFieldSet();
    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(diffBean.getClass());
    for (PropertyDescriptor pd : pds) {
      Method writeMethod = pd.getWriteMethod();
      Method readMethod = pd.getReadMethod();
      if (writeMethod == null || readMethod == null) {
        // 忽略字段class,class为Object的getClass()对应的属性,没有对应的setClass(Class<?> clazz)方法
        continue;
      }
      readMethod.setAccessible(true);
      writeMethod.setAccessible(true);
      try {
        String fieldName = pd.getName();
        Object originDBValue = readMethod.invoke(originDBBean);
        if (originFieldSet.contains(fieldName)) {
          Object originValue = readMethod.invoke(originDomain);
          if (originValue == null ? originDBValue != null : !originValue.equals(originDBValue)) {
            throw new DAOException(HttpResultEnum.UpdateWhileUpdated);
          }
          if (!diffFieldSet.contains(fieldName)) {
            // 未变动的field恢复回旧值
            writeMethod.invoke(diffBean, originValue);
          }
        }
      } catch (ReflectiveOperationException e) {
        throw new DAOException(e);
      }
    }
  }

  protected void checkAndInitDiffOriginBean(B diffBean) {
    this.checkBean(diffBean);
    Set<String> diffFieldSet = diffBean.getFieldSet();
    if (diffFieldSet == null) {
      throw new DAOException(HttpResultEnum.UpdateDiffNullError);
    }
    CommonDomain originDomain = diffBean.getOriginDomain();
    Set<String> originFieldSet = null;
    if (originDomain == null || !originDomain.getClass().equals(diffBean.getClass())
        || (originFieldSet = originDomain.getFieldSet()) == null || originFieldSet.size() == 0) {
      throw new DAOException(HttpResultEnum.UpdateOriginEmptyError);
    }

    diffBean.setUuid(originDomain.getUuid());
    diffBean.setVersion(null);
    diffBean.setCreator(null);
    diffBean.setCreateTime(null);
    diffBean.setUpdateTime(null);

    //  不求uuid得diff
    originFieldSet.remove(CommonConstants.UUID);
    originDomain.setVersion(null);
    originDomain.setCreator(null);
    originDomain.setCreateTime(null);
    // 防止updater被篡改
    originDomain.setUpdater(diffBean.getUpdater());
    originDomain.setUpdateTime(null);
  }

  @Override
  public List<B> queryOrigin(B condition) {
    throw new DAOException("not support");
  }

  @Override
  public int delete(B bean) {
    this.checkBean(bean);
    B originDBBean = this.queryOriginBean(bean);
    Integer version = originDBBean.getVersion();
    if (version == null) {
      throw new DAOException(HttpResultEnum.VersionNullError);
    }
    this.checkDeleteBean(bean, originDBBean);
    for (IBeforeRule<B> iBeforeRule : this.iBeforeRuleList) {
      iBeforeRule.beforeRule(bean, originDBBean);
    }
    // 填充version, 进行乐观锁校验
    bean.setVersion(version);
    int result = this.iBaseDao.delete(bean);
    if (result != 1) {
      throw new DAOException(HttpResultEnum.DeleteNotExistedError);
    }
    for (IAfterRule<B> iAfterRule : this.iAfterRuleList) {
      iAfterRule.afterRule(bean, originDBBean);
    }
    return result;
  }

  /**
   * 检查数据是否有改动
   */
  private void checkDeleteBean(B originDomain, B originDBBean) {
    originDBBean.setUuid(originDomain.getUuid());
    Set<String> originFieldSet = originDomain.getFieldSet();
    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(originDomain.getClass());
    for (PropertyDescriptor pd : pds) {
      Method writeMethod = pd.getWriteMethod();
      Method readMethod = pd.getReadMethod();
      if (writeMethod == null || readMethod == null) {
        // 忽略字段class,class为Object的getClass()对应的属性,没有对应的setClass(Class<?> clazz)方法
        continue;
      }
      readMethod.setAccessible(true);
      try {
        if (originFieldSet.contains(pd.getName())) {
          Object originValue = readMethod.invoke(originDomain);
          Object originDBValue = readMethod.invoke(originDBBean);
          if (originValue == null ? originDBValue != null : !originValue.equals(originDBValue)) {
            throw new DAOException(HttpResultEnum.DeleteWhileUpdated);
          }
        }
      } catch (ReflectiveOperationException e) {
        throw new DAOException(e);
      }
    }
  }

  @Override
  public int batchInsert(List<B> beanList) {
    this.checkBeanList(beanList);
    this.iBatchBeforeRuleList.addFirst(new CheckBatchParamsInsertBeforeRule<B>());
    for (IBatchBeforeRule<B> iBatchBeforeRule : this.iBatchBeforeRuleList) {
      iBatchBeforeRule.beforeRule(beanList, beanList);
    }
    int result = this.iBaseDao.batchInsert(beanList);
    if (result != beanList.size()) {
      throw new BusinessException(HttpResultEnum.BatchInsertFailed);
    }
    for (IBatchAfterRule<B> iBatchAfterRule : this.iBatchAfterRuleList) {
      iBatchAfterRule.afterRule(beanList, beanList);
    }
    return result;
  }

  @Override
  public int batchDelete(List<B> beanList) {
    this.checkBeanList(beanList);
    List<B> originDBBeanList =
        this.checkBatchOperationBefore(beanList, HttpResultEnum.BatchDeleteWhileChangeError);
    Map<String, B> beanMap = new HashMap<>();
    for (B bean : originDBBeanList) {
      beanMap.put(bean.getUuid(), bean);
    }
    List<B> originBeanList = new ArrayList<>();
    for (B bean : beanList) {
      B originBean = beanMap.get(bean.getUuid());
      if (originBean != null) {
        originBeanList.add(originBean);
      }
    }
    if (beanList.size() != originBeanList.size()) {
      throw new DAOException(HttpResultEnum.BatchDeleteWhileChangeError);
    }
    for (IBatchBeforeRule<B> iBatchBeforeRule : this.iBatchBeforeRuleList) {
      iBatchBeforeRule.beforeRule(beanList, originBeanList);
    }
    int result = this.iBaseDao.batchDelete(beanList);
    if (result != beanList.size()) {
      throw new DAOException(HttpResultEnum.BatchDeleteWhileChangeError);
    }
    for (IBatchAfterRule<B> iBatchAfterRule : this.iBatchAfterRuleList) {
      iBatchAfterRule.afterRule(beanList, originBeanList);
    }
    this.checkBatchOperationAfter(beanList, HttpResultEnum.BatchDeleteWhileChangeError);
    return result;
  }

  protected List<B> checkBatchOperationBefore(List<B> beanList, IHttpResultEnum httpResultEnum) {
    List<B> originDBBeanList = this.queryOriginBeanList(beanList);
    this.checkBatchOperationBean(beanList, originDBBeanList, httpResultEnum);
    return originDBBeanList;
  }

  protected List<B> queryOriginBeanList(List<B> conditionList) {
    List<B> domainList = null;
    if (conditionList != null && conditionList.size() > 0) {
      for (B condition : conditionList) {
        if (condition.getUuid() == null) {
          throw new DAOException("uuid in conditionList is null");
        }
      }
      domainList = this.iBaseDao.queryOriginList(conditionList);
    }
    if (conditionList == null || domainList == null || domainList.size() == 0
        || conditionList.size() != domainList.size()) {
      throw new DAOException(HttpResultEnum.NotExistedError);
    }
    return domainList;
  }

  protected void checkBatchOperationAfter(List<B> beanList, IHttpResultEnum httpResultEnum) {
    List<B> originDBBeanList = this.queryOriginBeanList(beanList);
    this.checkBatchOperationBean(beanList, originDBBeanList, httpResultEnum);
  }

  @Override
  public List<B> queryOriginList(List<B> beanList) {
    throw new DAOException("not support");
  }

  /**
   * 检查数据是否有改动
   */
  private void checkBatchOperationBean(List<B> beanList, List<B> originDBBeanList,
      IHttpResultEnum httpResultEnum) {
    Map<String, B> beanMap = new HashMap<>();
    for (B bean : originDBBeanList) {
      beanMap.put(bean.getUuid(), bean);
    }
    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(beanList.get(0).getClass());
    Set<String> originFieldSet = beanList.get(0).getFieldSet();
    for (PropertyDescriptor pd : pds) {
      Method writeMethod = pd.getWriteMethod();
      Method readMethod = pd.getReadMethod();
      if (writeMethod == null || readMethod == null) {
        // 忽略字段class,class为Object的getClass()对应的属性,没有对应的setClass(Class<?> clazz)方法
        continue;
      }
      readMethod.setAccessible(true);
      for (B originDomain : beanList) {
        B originDBDomain = beanMap.get(originDomain.getUuid());
        if (originDBDomain == null) {
          throw new DAOException(httpResultEnum);
        }
        try {
          if (originFieldSet.contains(pd.getName())) {
            Object originValue = readMethod.invoke(originDomain);
            Object originDBValue = readMethod.invoke(originDBDomain);
            if (originValue == null ? originDBValue != null : !originValue.equals(originDBValue)) {
              throw new DAOException(httpResultEnum);
            }
          }
        } catch (ReflectiveOperationException e) {
          throw new DAOException(e);
        }
      }
    }
  }

  protected void addBeforeRule(IBeforeRule<B> iBeforeRule) {
    if (iBeforeRule == null) {
      throw new DAOException("iBeforeRule is null");
    }
    this.iBeforeRuleList.add(iBeforeRule);
  }

  protected void addAfterRule(IAfterRule<B> iAfterRule) {
    if (iAfterRule == null) {
      throw new DAOException("iAfterRule is null");
    }
    this.iAfterRuleList.add(iAfterRule);
  }

  protected void addBatchBeforeRule(IBatchBeforeRule<B> iBatchBeforeRule) {
    if (iBatchBeforeRule == null) {
      throw new DAOException("iBatchBeforeRule is null");
    }
    this.iBatchBeforeRuleList.add(iBatchBeforeRule);
  }

  protected void addBatchAfterRule(IBatchAfterRule<B> iBatchAfterRule) {
    if (iBatchAfterRule == null) {
      throw new DAOException("iBatchAfterRule is null");
    }
    this.iBatchAfterRuleList.add(iBatchAfterRule);
  }

  /**
   * bean、dao不能为null
   */
  private void checkBean(B bean) {
    if (bean == null) {
      throw new DAOException("bean is null");
    }
    if (this.iBaseDao == null) {
      throw new DAOException("iBaseDao is null");
    }
  }

  /**
   * beanList、dao不能为null
   */
  private void checkBeanList(List<B> beanList) {
    if (beanList == null || beanList.size() == 0) {
      throw new DAOException("beanList is empty");
    }
    for (B bean : beanList) {
      if (bean == null) {
        throw new DAOException("bean in beanList is null");
      }
    }
    if (this.iBaseDao == null) {
      throw new DAOException("iBaseDao is null");
    }
  }
}

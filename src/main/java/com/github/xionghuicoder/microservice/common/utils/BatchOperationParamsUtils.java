package com.github.xionghuicoder.microservice.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.CommonDomain;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;

/**
 * 处理修改，删除，批量删除等操作的参数
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class BatchOperationParamsUtils {

  public static <T extends CommonDomain> T dealUpdateParams(JSONObject bodyJson, Class<T> clazz) {
    JSONObject diffJson = bodyJson.getJSONObject(CommonConstants.DIFF_BODY);
    if (diffJson == null) {
      throw new BusinessException("diff bean is null", HttpResultEnum.UpdateDiffNullError);
    }
    JSONObject originJson = bodyJson.getJSONObject(CommonConstants.ORIGIN_BODY);
    if (originJson == null || originJson.size() == 0) {
      throw new BusinessException("origin bean is empty", HttpResultEnum.UpdateOriginEmptyError);
    }
    // 不使用JSON.toJavaObject是为了兼容时间格式转换
    T diffBean = JSON.parseObject(diffJson.toJSONString(), clazz);
    diffBean.setFieldSet(diffJson.keySet());

    // 不使用JSON.toJavaObject是为了兼容时间格式转换
    CommonDomain originDomain = JSON.parseObject(originJson.toJSONString(), clazz);
    diffBean.setOriginDomain(originDomain);
    originDomain.setFieldSet(originJson.keySet());
    return diffBean;
  }

  public static <T extends CommonDomain> T dealDeleteParams(JSONObject bodyJson, Class<T> clazz) {
    // 不使用JSON.toJavaObject是为了兼容时间格式转换
    T bean = JSON.parseObject(bodyJson.toJSONString(), clazz);
    bean.setFieldSet(bodyJson.keySet());
    return bean;
  }

  public static <T extends CommonDomain> List<T> dealBatchDeleteParams(JSONObject bodyJson,
      Class<T> clazz) {
    JSONArray originArray = bodyJson.getJSONArray(CommonConstants.ORIGINS);
    if (originArray == null || originArray.size() == 0) {
      throw new BusinessException("origins is empty", HttpResultEnum.BatchDeleteOriginsNullError);
    }
    List<T> originList = new ArrayList<T>(originArray.size());
    for (Object obj : originArray) {
      JSONObject json = (JSONObject) obj;
      T bean = JSON.parseObject(json.toJSONString(), clazz);
      bean.setFieldSet(json.keySet());
      originList.add(bean);
    }
    return originList;
  }
}

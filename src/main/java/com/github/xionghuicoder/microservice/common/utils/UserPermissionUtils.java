package com.github.xionghuicoder.microservice.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.CommonParamsBean;

/**
 * 编码和解码user,permission(因为可能有中文等字符)<br />
 *
 * 获取user,permission信息
 *
 * @author xionghui
 * @since 1.0.0
 */
public class UserPermissionUtils {

  public static String encode(String s) throws UnsupportedEncodingException {
    if (s == null) {
      return null;
    }
    return URLEncoder.encode(s, CommonConstants.UTF8_ENCODING);
  }

  public static String decode(String s) throws UnsupportedEncodingException {
    if (s == null) {
      return null;
    }
    return URLDecoder.decode(s, CommonConstants.UTF8_ENCODING);
  }

  public static JSONObject fetchBuc(HttpServletRequest request)
      throws UnsupportedEncodingException {
    if (request == null) {
      return null;
    }
    String buc = request.getHeader(CommonConstants.USER_HEAD);
    if (buc == null) {
      return null;
    }
    buc = URLDecoder.decode(buc, CommonConstants.UTF8_ENCODING);
    return JSON.parseObject(buc);
  }

  public static CommonParamsBean.User fetchBucUser(HttpServletRequest request)
      throws UnsupportedEncodingException {
    JSONObject buc = UserPermissionUtils.fetchBuc(request);
    if (buc == null) {
      return null;
    }
    String empId = buc.getString(CommonConstants.USER_EMPID);
    String name = buc.getString(CommonConstants.USER_NAME);
    String email = buc.getString(CommonConstants.USER_EMAIL);
    CommonParamsBean.User user = new CommonParamsBean.User(empId, name, email);
    return user;
  }

  public static JSONObject fetchAcl(HttpServletRequest request)
      throws UnsupportedEncodingException {
    if (request == null) {
      return null;
    }
    String acl = request.getHeader(CommonConstants.PERMISSION_HEAD);
    if (acl == null) {
      return null;
    }
    acl = URLDecoder.decode(acl, CommonConstants.UTF8_ENCODING);
    return JSON.parseObject(acl);
  }
}

package com.github.xionghuicoder.microservice.common.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.annotation.MenuAnnotation;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.CommonParamsBean;
import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.RegisterBean;
import com.github.xionghuicoder.microservice.common.bean.ServiceParamsBean;
import com.github.xionghuicoder.microservice.common.bean.UploadServiceParamsBean;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.bean.enums.LanguageEnum;
import com.github.xionghuicoder.microservice.common.utils.CommonExceptionUtils;
import com.github.xionghuicoder.microservice.common.utils.CommonRequestUtils;
import com.github.xionghuicoder.microservice.common.utils.RequestMethodUtils;
import com.github.xionghuicoder.microservice.common.utils.UserPermissionUtils;

/**
 * 公共controller操作，负责请求检查，映射等
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class CommonController extends RegisterController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

  private static final Set<String> NOLOG_FUNCTION_SET = new HashSet<String>();

  // 存储多语
  private static final ThreadLocal<Locale> LOCALE_THREADLOCAL = new ThreadLocal<Locale>();

  public static Locale getLocale() {
    return LOCALE_THREADLOCAL.get();
  }

  /**
   * restful服务请求入口
   *
   * @param request 请求request
   * @return HttpResult<?>
   */
  protected HttpResult<?> recieve(HttpServletRequest request) {
    HttpResult<?> httpResult = null;
    try {
      LOGGER.info("recieve begin");
      CommonParamsBean paramsBean = this.convertCheckParams(request);
      LOGGER.info("recieve params, method: {}, url: {}, params: {}",
          paramsBean.getHttpRequestMethod(), request.getRequestURL().toString(), paramsBean);
      ControllerProxy controllerProxy = this.dealController(paramsBean);
      ServiceParamsBean serviceParamsBean = ServiceParamsBean.custom().setBody(paramsBean.getBody())
          .setBodyJson(paramsBean.getBodyJson()).setExt(paramsBean.getExt())
          .setPermissionJson(paramsBean.getPermissionJson()).setUser(paramsBean.getUser()).build();
      httpResult = (HttpResult<?>) controllerProxy.getMethod()
          .invoke(controllerProxy.getController(), serviceParamsBean);
      if (httpResult == null) {
        throw new BusinessException(HttpResultEnum.ReturnNullError);
      }
      String function = paramsBean.getFunction();
      // 有些接口返回数据太大不易打印日志，比如获取页面多语接口
      if (NOLOG_FUNCTION_SET.contains(function)) {
        LOGGER.info("recieve end function: {}", function);
      } else {
        LOGGER.info("recieve end: {}", httpResult);
      }
    } catch (Throwable t) {
      httpResult = CommonExceptionUtils.unwrap(t);
      LOGGER.info("recieve error end: {}", httpResult);
      LOGGER.error("recieve error: ", t);
    } finally {
      LOCALE_THREADLOCAL.remove();
    }
    return httpResult;
  }

  /**
   * download请求入口
   *
   * @param request 请求request
   */
  protected void download(HttpServletRequest request) {
    try {
      LOGGER.info("download begin");
      CommonParamsBean paramsBean = this.convertCheckParams(request);
      LOGGER.info("download params, method: {}, url: {}, params: {}",
          paramsBean.getHttpRequestMethod(), request.getRequestURL().toString(), paramsBean);
      ControllerProxy controllerProxy = this.dealController(paramsBean);
      ServiceParamsBean serviceParamsBean = ServiceParamsBean.custom().setBody(paramsBean.getBody())
          .setBodyJson(paramsBean.getBodyJson()).setExt(paramsBean.getExt())
          .setPermissionJson(paramsBean.getPermissionJson()).setUser(paramsBean.getUser()).build();
      controllerProxy.getMethod().invoke(controllerProxy.getController(), serviceParamsBean);
      LOGGER.info("download end");
    } catch (Throwable t) {
      LOGGER.error("download error: ", t);
    } finally {
      LOCALE_THREADLOCAL.remove();
    }
  }

  /**
   * upload请求入口
   *
   * @param request 请求request
   * @param file 上传文件
   * @return HttpResult<?>
   */
  protected HttpResult<?> upload(HttpServletRequest request, MultipartFile[] files) {
    HttpResult<?> httpResult = null;
    try {
      LOGGER.info("upload begin");
      CommonParamsBean paramsBean = this.convertCheckParams(request);
      if (files == null || files.length == 0) {
        throw new BusinessException("upload files is null or files' length is 0",
            HttpResultEnum.UploadNullFileError);
      }
      List<String> filenameList = new ArrayList<String>();
      for (MultipartFile file : files) {
        if (file == null) {
          throw new BusinessException("upload file in files is null",
              HttpResultEnum.UploadNullFileError);
        }
        filenameList.add(file.getOriginalFilename());
      }
      LOGGER.info("upload params, method: {}, url: {}, files: {}, params: {}",
          paramsBean.getHttpRequestMethod(), request.getRequestURL().toString(), filenameList,
          paramsBean);
      ControllerProxy controllerProxy = this.dealController(paramsBean);
      UploadServiceParamsBean uploadServiceParamsBean = UploadServiceParamsBean.uploadCustom()
          .setBody(paramsBean.getBody()).setBodyJson(paramsBean.getBodyJson())
          .setExt(paramsBean.getExt()).setPermissionJson(paramsBean.getPermissionJson())
          .setUser(paramsBean.getUser()).setFiles(files).build();
      httpResult = (HttpResult<?>) controllerProxy.getMethod()
          .invoke(controllerProxy.getController(), uploadServiceParamsBean);
      if (httpResult == null) {
        throw new BusinessException(HttpResultEnum.ReturnNullError);
      }
      LOGGER.info("upload end: {}", httpResult);
    } catch (Throwable t) {
      httpResult = CommonExceptionUtils.unwrap(t);
      LOGGER.info("recieve error end: {}", httpResult);
      LOGGER.error("recieve error: ", t);
    } finally {
      LOCALE_THREADLOCAL.remove();
    }
    return httpResult;
  }

  private ControllerProxy dealController(CommonParamsBean paramsBean) {
    RegisterBean registerBean =
        new RegisterBean(this.dealUri(paramsBean.getUri()), paramsBean.getFunction());
    ControllerProxy controllerProxy = this.getBean(registerBean);
    if (controllerProxy == null) {
      throw new BusinessException(HttpResultEnum.NullControllerError);
    }
    if (paramsBean.isZuul() ? !controllerProxy.isSupportZuul()
        : !controllerProxy.isSupportFeign()) {
      throw new BusinessException(HttpResultEnum.NotSupportController);
    }

    HttpRequestMethod[] httpRequestMethods = controllerProxy.getHttpRequestMethods();
    if (httpRequestMethods != null) {
      boolean isMatched = false;
      HttpRequestMethod httpRequestMethod = paramsBean.getHttpRequestMethod();
      for (HttpRequestMethod httpMethod : httpRequestMethods) {
        if (httpRequestMethod == httpMethod) {
          isMatched = true;
          break;
        }
      }
      if (!isMatched) {
        throw new BusinessException(HttpResultEnum.RequestMethodError);
      }
    }
    // 内部接口调用不检查菜单权限
    if (paramsBean.isZuul()) {
      this.checkMenu(controllerProxy.getController().getClass(), controllerProxy.getMethod(),
          paramsBean.getPermissionJson());
    }
    return controllerProxy;
  }

  /**
   * 转换并检查参数
   *
   * @param request 请求request
   * @return CommonParamsBean
   * @throws IOException IOException
   * @throws ServletException ServletException
   */
  private CommonParamsBean convertCheckParams(HttpServletRequest request)
      throws IOException, ServletException {
    // 获取多语信息
    String code = request.getHeader(CommonConstants.LANGUAGE_COOKIE_HEADER);
    LanguageEnum languageEnum = LanguageEnum.getLanguageEnum(code);
    LOCALE_THREADLOCAL.set(languageEnum.locale);

    String zuul = request.getHeader(CommonConstants.ZUUL_HEAD);
    boolean isZuul = (String.valueOf(Boolean.TRUE).equals(zuul) ? true : false);

    Map<String, String> paramsMap = CommonRequestUtils.getParameterMap(request);
    String method = request.getMethod();
    HttpRequestMethod httpRequestMethod = RequestMethodUtils.getByName(method);
    String uri = request.getRequestURI();
    String function = paramsMap.get(CommonConstants.FUNCTION);
    if (function == null) {
      throw new BusinessException(HttpResultEnum.FunctionLackError);
    }
    String body = paramsMap.get(CommonConstants.BODY);
    JSONObject bodyJson = null;
    try {
      bodyJson = JSON.parseObject(body);
    } catch (JSONException e) {
      throw new BusinessException(HttpResultEnum.BodyFormatError);
    }
    if (bodyJson == null) {
      throw new BusinessException(HttpResultEnum.BodyLackError);
    }
    String ext = paramsMap.get(CommonConstants.Ext);

    JSONObject acl = UserPermissionUtils.fetchAcl(request);
    if (acl == null) {
      throw new BusinessException(HttpResultEnum.PermissionsLackError);
    }
    CommonParamsBean.User user = UserPermissionUtils.fetchBucUser(request);
    if (user == null) {
      throw new BusinessException(HttpResultEnum.UserLackError);
    }
    CommonParamsBean paramsBean = CommonParamsBean.custom().setIsZuul(isZuul)
        .setHttpRequestMethod(httpRequestMethod).setUri(uri).setFunction(function).setBody(body)
        .setBodyJson(bodyJson).setExt(ext).setPermissionJson(acl).setUser(user).build();
    return paramsBean;
  }

  /**
   * 检查菜单权限
   *
   * @param controllerClass controllerClass
   * @param controllerMethod controllerMethod
   * @param permissionJson permissionJson
   */
  private void checkMenu(Class<?> controllerClass, Method controllerMethod,
      JSONObject permissionJson) {
    String[] menus = null;
    MenuAnnotation methodMenuAnnotation = controllerMethod.getAnnotation(MenuAnnotation.class);
    if (methodMenuAnnotation != null) {
      menus = methodMenuAnnotation.value();
    } else {
      MenuAnnotation classMenuAnnotation = controllerClass.getAnnotation(MenuAnnotation.class);
      if (classMenuAnnotation != null) {
        menus = classMenuAnnotation.value();
      }
    }
    if (menus == null) {
      return;
    }

    boolean menuRight = false;
    if (permissionJson != null) {
      JSONObject menuJson = permissionJson.getJSONObject(CommonConstants.PERMISSION_MENU);
      if (menuJson != null) {
        for (String menu : menus) {
          Boolean value = menuJson.getBoolean(menu);
          if (value != null) {
            menuRight = value;
            // 只要一个菜单有权限就ok
            if (menuRight) {
              break;
            }
          }
        }
      }
    }
    if (!menuRight) {
      throw new BusinessException("checkMenu no menuPermission",
          HttpResultEnum.NoAclMenuRightError);
    }
  }

  protected static void addNoLogFunction(String function) {
    NOLOG_FUNCTION_SET.add(function);
  }
}

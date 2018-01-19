package com.github.xionghuicoder.microservice.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.enums.LanguageEnum;
import com.github.xionghuicoder.microservice.common.controller.CommonController;

/**
 * 多语初始化处理工具类，会同时获取common的多语数据
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonResourceBundleMessageSourceUtils extends ResourceBundleMessageSource {
  private static final Map<Locale, Map<String, String>> CACHED_RESOURCE_BUNDLE_MAP =
      new ConcurrentHashMap<Locale, Map<String, String>>();

  protected void init() {
    this.init(null);
  }

  protected void initDefaultPath() {
    this.init(CommonConstants.LANGUAGE_MESSAGE);
  }

  protected void init(String basename) {
    synchronized (CommonResourceBundleMessageSourceUtils.class) {
      for (LanguageEnum languageEnum : LanguageEnum.values()) {
        PropertiesUtils propertiesUtils = new PropertiesUtils();
        ResourceBundle.Control controller = new ResourceBundle.Control() {};
        this.load(CommonConstants.COMMON_LANGUAGE_MESSAGE, languageEnum.locale);
        this.checkKeyRepeat(CommonConstants.COMMON_LANGUAGE_MESSAGE, languageEnum.locale,
            propertiesUtils, controller);
        if (basename != null) {
          this.load(basename, languageEnum.locale);
          this.checkKeyRepeat(basename, languageEnum.locale, propertiesUtils, controller);
        }
      }
    }
  }

  private void load(String basename, Locale locale) {
    Map<String, String> bundleMap = CACHED_RESOURCE_BUNDLE_MAP.get(locale);
    if (bundleMap == null) {
      bundleMap = new ConcurrentHashMap<String, String>();
      CACHED_RESOURCE_BUNDLE_MAP.put(locale, bundleMap);
    }
    ResourceBundle bundle = null;
    try {
      bundle = this.doGetBundle(basename, locale);
    } catch (MissingResourceException e) {
      throw new BusinessException(
          "ResourceBundle [" + basename + "], [" + locale + "] not found for MessageSource: ", e);
    }
    if (bundle == null) {
      throw new BusinessException(
          "basename: " + basename + " with locale: " + locale + " is illegal");
    }
    Enumeration<String> keys = bundle.getKeys();
    while (keys.hasMoreElements()) {
      String key = CommonResourceBundleMessageSourceUtils.charsetUtf8(keys.nextElement());
      String value = CommonResourceBundleMessageSourceUtils.charsetUtf8(bundle.getString(key));
      bundleMap.put(key, value);
    }
  }

  /**
   * 检查key是否重复
   */
  private void checkKeyRepeat(String basename, Locale locale, PropertiesUtils propertiesUtils,
      ResourceBundle.Control controller) {
    ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
    String path =
        controller.toResourceName(controller.toBundleName(basename, locale), "properties");
    try {
      InputStream is = classLoader.getResourceAsStream(path);
      propertiesUtils.load(path, is);
    } catch (IOException e) {
      throw new BusinessException(e);
    } catch (NullPointerException e) {
      throw new BusinessException("load " + path + " NullPointerException", e);
    }
  }

  public static String getMessage(String key, String... args) {
    return getMessage(key, null, args);
  }

  public static String getMessage(String key, Locale locale, String... args) {
    if (locale == null) {
      locale = CommonController.getLocale();
    }
    String message = tryGetMessage(locale, CACHED_RESOURCE_BUNDLE_MAP, key, args);
    return message == null ? key : message;
  }

  private static String tryGetMessage(Locale locale,
      Map<Locale, Map<String, String>> resourceBundleMap, String key, String[] args) {
    String message = null;
    Map<String, String> bundleMap = resourceBundleMap.get(locale);
    if (bundleMap != null) {
      String msg = bundleMap.get(key);
      if (msg != null) {
        if (ObjectUtils.isEmpty(args)) {
          message = msg;
        } else {
          MessageFormat messageFormat = new MessageFormat(msg, locale);
          message = messageFormat.format(args);
        }
      }
    }
    return message;
  }

  public static String charsetUtf8(String s) {
    if (s == null) {
      return s;
    }
    try {
      return new String(s.getBytes("ISO-8859-1"), CommonConstants.UTF8_ENCODING);
    } catch (UnsupportedEncodingException e) {
      throw new BusinessException(e);
    }
  }
}

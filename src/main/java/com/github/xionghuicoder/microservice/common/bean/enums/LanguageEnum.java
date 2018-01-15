package com.github.xionghuicoder.microservice.common.bean.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 国际化多语言
 *
 * @author xionghui
 * @since 1.0.0
 */
public enum LanguageEnum {
  Simpchn("simpchn", "简体中文", Locale.SIMPLIFIED_CHINESE), //
  Tradchn("tradchn", "繁體中文", Locale.TRADITIONAL_CHINESE), //
  English("english", "English", Locale.ENGLISH), //
  ;

  // default is Simpchn
  private static final LanguageEnum DEFAULT = Simpchn;

  public final String code;
  public final String name;
  public final Locale locale;

  private LanguageEnum(String code, String name, Locale locale) {
    this.code = code;
    this.name = name;
    this.locale = locale;
  }

  private static final JSONArray VALUE_ARRAY = new JSONArray();
  private static final Map<String, LanguageEnum> LANGUAGE_MAP = new HashMap<>();

  static {
    for (LanguageEnum languageEnum : LanguageEnum.values()) {
      JSONObject value = new JSONObject();
      VALUE_ARRAY.add(value);
      value.put("code", languageEnum.code);
      value.put("name", languageEnum.name);

      LANGUAGE_MAP.put(languageEnum.code, languageEnum);
    }
  }

  public static JSONArray getLanguageArray() {
    JSONArray copy = new JSONArray();
    for (Object obj : VALUE_ARRAY) {
      JSONObject json = (JSONObject) obj;
      copy.add(json.clone());
    }
    return copy;
  }

  public static LanguageEnum getLanguageEnum(String code) {
    LanguageEnum language = LANGUAGE_MAP.get(code);
    if (language == null) {
      language = DEFAULT;
    }
    return language;
  }
}

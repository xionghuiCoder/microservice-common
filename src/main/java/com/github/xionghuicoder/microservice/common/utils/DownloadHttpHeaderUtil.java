package com.github.xionghuicoder.microservice.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;

/**
 * 下载的header设置
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class DownloadHttpHeaderUtil {

  public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response,
      String fileName) {
    try {
      // 中文文件名支持
      String encodedfileName = null;
      String agent = request.getHeader("USER-AGENT");
      // IE
      if (null != agent && -1 != agent.indexOf("MSIE")) {
        encodedfileName = URLEncoder.encode(fileName, CommonConstants.UTF8_ENCODING);
      } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
        encodedfileName = new String(fileName.getBytes(CommonConstants.UTF8_ENCODING),
            CommonConstants.ISO8859_ENCODING);
      } else {
        encodedfileName = java.net.URLEncoder.encode(fileName, CommonConstants.UTF8_ENCODING);
      }
      response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
    } catch (UnsupportedEncodingException e) {
      throw new BusinessException(e);
    }
  }
}

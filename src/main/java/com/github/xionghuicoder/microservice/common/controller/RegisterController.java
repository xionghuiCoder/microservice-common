package com.github.xionghuicoder.microservice.common.controller;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.annotation.ControllerMappingAnnotation;
import com.github.xionghuicoder.microservice.common.annotation.EnablePathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.annotation.PathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.RegisterBean;
import com.github.xionghuicoder.microservice.common.bean.ServiceParamsBean;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;

/**
 * 实现controller的一些公共操作，比如扫描包，重定义bean，注册新bean等<br>
 *
 * {@link #beanMap beanMap}不需要销毁，因为{@link RegisterController RegisterController}使用spring管理生命周期，<br>
 * {@link RegisterController RegisterController}被回收后{@link #beanMap
 * beanMap}也会被回收（参考java的根对象垃圾回收机制），<br>
 * 建议每个项目只使用一个<tt>Controller</tt>，否则每个{@link #beanMap beanMap}都会缓存一份beanMap。
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class RegisterController implements BeanFactoryAware {
  private static final String RESOURCE_PATTERN = "/**/*.class";

  private final Map<RegisterBean, Object> beanMap = new ConcurrentHashMap<RegisterBean, Object>();

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    ControllerMappingAnnotation controllerMappingAnnotation =
        this.getClass().getAnnotation(ControllerMappingAnnotation.class);
    if (controllerMappingAnnotation == null) {
      return;
    }
    String path = controllerMappingAnnotation.value();
    String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
        + ClassUtils.convertClassNameToResourcePath(path) + RESOURCE_PATTERN;
    this.dealResource(pattern, (ListableBeanFactory) beanFactory);
  }

  /**
   * 扫描包
   *
   * @param pattern 扫描包的路径
   * @param beanFactory beanFactory
   */
  private void dealResource(String pattern, ListableBeanFactory beanFactory) {
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    try {
      Map<Object, Boolean> identityBeanMap = new IdentityHashMap<Object, Boolean>();
      Resource[] resources = resourcePatternResolver.getResources(pattern);
      MetadataReaderFactory readerFactory =
          new CachingMetadataReaderFactory(resourcePatternResolver);
      for (Resource resource : resources) {
        if (resource.isReadable()) {
          MetadataReader reader = readerFactory.getMetadataReader(resource);
          String className = reader.getClassMetadata().getClassName();
          Class<?> beanClazz = Class.forName(className);
          EnablePathConfigAnnotation enablePathConfigAnnotation =
              beanClazz.getAnnotation(EnablePathConfigAnnotation.class);
          // 未开启路径配置则不处理该bean
          if (enablePathConfigAnnotation == null) {
            continue;
          }
          String[] beanNames = beanFactory.getBeanNamesForType(beanClazz);
          // 没有bean
          if (beanNames == null || beanNames.length == 0) {
            throw new BusinessException("dealResource beanNames illegal: " + beanClazz);
          }
          Object bean = beanFactory.getBean(beanClazz);
          // 不重复处理bean
          if (identityBeanMap.containsKey(bean)) {
            continue;
          }
          identityBeanMap.put(bean, true);
          this.registerBeans(beanClazz, bean);
        }
      }
    } catch (Exception e) {
      throw new BusinessException("dealResource error", e);
    }
  }

  /**
   * 注册新bean
   *
   * @param beanClazz beanClazz
   * @param bean bean
   */
  private void registerBeans(Class<?> beanClazz, Object bean) {
    // 只遍历公有方法
    Method[] methods = beanClazz.getMethods();
    if (methods == null) {
      return;
    }
    for (Method method : methods) {
      PathConfigAnnotation pathConfigAnnotation = method.getAnnotation(PathConfigAnnotation.class);
      if (pathConfigAnnotation != null) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes == null) {
          throw new BusinessException(
              "the controller method's params don't matched " + HttpResult.class);
        }
        if (parameterTypes.length != 1) {
          throw new BeanCreationException("the controller method's params' length don't matched 1");
        }
        if (!ServiceParamsBean.class.isAssignableFrom(parameterTypes[0])) {
          throw new BusinessException(
              "the controller method's params don't matched ServiceParamsBean");
        }
        ControllerProxy controllerProxy =
            new ControllerProxy(bean, pathConfigAnnotation.supportZuul(),
                pathConfigAnnotation.supportFeign(), pathConfigAnnotation.method(), method);
        String uri = pathConfigAnnotation.uri();
        String path = pathConfigAnnotation.value();
        if ("".equals(path)) {
          path = pathConfigAnnotation.path();
        }
        RegisterBean registerBean = new RegisterBean(this.dealUri(uri), path);
        Object oldBean = this.beanMap.put(registerBean, controllerProxy);
        if (oldBean != null) {
          throw new BusinessException("create bean repeat, registerBean is " + registerBean);
        }
      }
    }
  }

  protected String dealUri(String uri) {
    uri = this.removeSemicolonContentInternal(uri);
    uri = this.uriDecode(uri, Charset.forName(CommonConstants.UTF8_ENCODING));
    uri = this.getSanitizedPath(uri);

    String[] pathDirs = StringUtils.tokenizeToStringArray(uri, "/", true, true);
    return "/" + StringUtils.arrayToDelimitedString(pathDirs, "/");
  }

  private String removeSemicolonContentInternal(String requestUri) {
    int semicolonIndex = requestUri.indexOf(';');
    while (semicolonIndex != -1) {
      int slashIndex = requestUri.indexOf('/', semicolonIndex);
      String start = requestUri.substring(0, semicolonIndex);
      requestUri = (slashIndex != -1) ? start + requestUri.substring(slashIndex) : start;
      semicolonIndex = requestUri.indexOf(';', semicolonIndex);
    }
    return requestUri;
  }

  private String uriDecode(String source, Charset charset) {
    int length = source.length();
    if (length == 0) {
      return source;
    }

    ByteArrayOutputStream bos = new ByteArrayOutputStream(length);
    boolean changed = false;
    for (int i = 0; i < length; i++) {
      int ch = source.charAt(i);
      if (ch == '%') {
        if (i + 2 < length) {
          char hex1 = source.charAt(i + 1);
          char hex2 = source.charAt(i + 2);
          int u = Character.digit(hex1, 16);
          int l = Character.digit(hex2, 16);
          if (u == -1 || l == -1) {
            throw new IllegalArgumentException(
                "Invalid encoded sequence \"" + source.substring(i) + "\"");
          }
          bos.write((char) ((u << 4) + l));
          i += 2;
          changed = true;
        } else {
          throw new IllegalArgumentException(
              "Invalid encoded sequence \"" + source.substring(i) + "\"");
        }
      } else {
        bos.write(ch);
      }
    }
    return (changed ? new String(bos.toByteArray(), charset) : source);
  }

  /**
   * Sanitize the given path with the following rules:
   * <ul>
   * <li>replace all "//" by "/"</li>
   * </ul>
   */
  private String getSanitizedPath(final String path) {
    String sanitized = path;
    while (true) {
      int index = sanitized.indexOf("//");
      if (index < 0) {
        break;
      } else {
        sanitized = sanitized.substring(0, index) + sanitized.substring(index + 1);
      }
    }
    return sanitized;
  }

  /**
   * 获取bean
   *
   * @param registerBean registerBean
   * @return ControllerProxy ControllerProxy
   */
  ControllerProxy getBean(RegisterBean registerBean) {
    try {
      Object bean = this.beanMap.get(registerBean);
      if (bean instanceof ControllerProxy) {
        return (ControllerProxy) bean;
      }
    } catch (NoSuchBeanDefinitionException e) {
      // swallow
    }
    return null;
  }


  /**
   * controller代理类，携带controller、method、RequestMethod信息
   *
   * @author xionghui
   * @version 1.0.0
   * @since 1.0.0
   */
  protected class ControllerProxy {
    private final Object controller;
    private final boolean supportZuul;
    private final boolean supportFeign;
    private final HttpRequestMethod[] httpRequestMethods;
    private final Method method;

    ControllerProxy(Object controller, boolean supportZuul, boolean supportFeign,
        HttpRequestMethod[] httpRequestMethods, Method method) {
      this.controller = controller;
      this.supportZuul = supportZuul;
      this.supportFeign = supportFeign;
      this.httpRequestMethods = httpRequestMethods;
      this.method = method;
    }

    public boolean isSupportZuul() {
      return this.supportZuul;
    }

    public boolean isSupportFeign() {
      return this.supportFeign;
    }

    public Object getController() {
      return this.controller;
    }

    public HttpRequestMethod[] getHttpRequestMethods() {
      return this.httpRequestMethods;
    }

    public Method getMethod() {
      return this.method;
    }

    @Override
    public String toString() {
      return "ControllerProxy [controller=" + this.controller + ", supportZuul=" + this.supportZuul
          + ", supportFeign=" + this.supportFeign + ", httpRequestMethods="
          + Arrays.toString(this.httpRequestMethods) + ", method=" + this.method + "]";
    }
  }
}

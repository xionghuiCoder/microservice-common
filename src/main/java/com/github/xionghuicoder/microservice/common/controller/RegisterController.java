package com.github.xionghuicoder.microservice.common.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import com.github.xionghuicoder.microservice.common.annotation.ControllerMappingAnnotation;
import com.github.xionghuicoder.microservice.common.annotation.EnablePathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.annotation.PathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.ServiceParamsBean;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;

/**
 * 实现controller的一些公共操作，比如扫描包，重定义bean，注册新bean等
 *
 * @author xionghui
 * @since 1.0.0
 */
public abstract class RegisterController implements BeanFactoryAware {
  private static final String RESOURCE_PATTERN = "/**/*.class";

  private ConfigurableListableBeanFactory configBeanFactory;

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    // cache BeanFactory
    this.configBeanFactory = (ConfigurableListableBeanFactory) beanFactory;

    ControllerMappingAnnotation controllerMappingAnnotation =
        this.getClass().getAnnotation(ControllerMappingAnnotation.class);
    if (controllerMappingAnnotation == null) {
      return;
    }
    String path = controllerMappingAnnotation.value();
    String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
        + ClassUtils.convertClassNameToResourcePath(path) + RESOURCE_PATTERN;
    this.dealResource(pattern);
  }

  /**
   * 扫描包
   */
  private void dealResource(String pattern) {
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    try {
      Map<Object, Object> identityBeanMap = new IdentityHashMap<>();
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
          String[] beanNames = this.configBeanFactory.getBeanNamesForType(beanClazz);
          // 没有bean则继续
          if (beanNames == null || beanNames.length == 0) {
            continue;
          }
          Object bean = this.configBeanFactory.getBean(beanClazz);
          // 不重复处理bean
          if (identityBeanMap.containsKey(bean)) {
            continue;
          }
          identityBeanMap.put(bean, null);
          this.registerBeans(beanClazz, bean);
        }
      }
    } catch (Exception e) {
      throw new BeanInstantiationException(this.getClass(),
          "CommonController.postProcessBeanFactory error", e);
    }
  }

  /**
   * 注册新bean
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
        Parameter[] params = method.getParameters();
        if (params == null) {
          throw new BeanCreationException(
              "the controller method's params don't matched " + HttpResult.class);
        }
        if (params.length != 1) {
          throw new BeanCreationException("the controller method's params' length don't matched 1");
        }
        if (!ServiceParamsBean.class.isAssignableFrom(params[0].getType())) {
          throw new BeanCreationException(
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
        String beanName = this.buildBeanName(uri, path);
        if (this.configBeanFactory.containsBean(beanName)) {
          throw new BeanCreationException("create bean repeat, bean name is " + path);
        }
        this.configBeanFactory.registerSingleton(beanName, controllerProxy);
      }
    }
  }

  protected String buildBeanName(String uri, String path) {
    return uri + "-" + path;
  }

  /**
   * 获取bean
   */
  protected ControllerProxy getBean(String beanName) {
    try {
      Object bean = this.configBeanFactory.getBean(beanName);
      if (bean instanceof ControllerProxy) {
        return (ControllerProxy) bean;
      }
    } catch (NoSuchBeanDefinitionException e) {
      // swallow it
    }
    return null;
  }


  /**
   * controller代理类，携带controller、method、RequestMethod信息
   *
   * @author xionghui
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
      return "ControllerProxy [controller=" + this.controller + ", httpRequestMethods="
          + Arrays.toString(this.httpRequestMethods) + ", method=" + this.method + "]";
    }
  }
}

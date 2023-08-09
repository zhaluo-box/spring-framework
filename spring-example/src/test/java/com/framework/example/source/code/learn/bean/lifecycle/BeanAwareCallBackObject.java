package com.framework.example.source.code.learn.bean.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringValueResolver;

/**
 * Created  on 2023/8/9 15:15:53
 *
 * @author zl
 */
@Slf4j(topic = "e")
public class BeanAwareCallBackObject
		implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, EnvironmentAware, EmbeddedValueResolverAware, ResourceLoaderAware,
		ApplicationEventPublisherAware, MessageSourceAware, ApplicationContextAware {

	@Override
	public void setBeanName(String name) {
		log.info("1 Bean Aware 接口回调测试 : [BeanNameAware] - " + name);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		log.info("2 Bean Aware 接口回调测试 ： [BeanClassLoaderAware ]- " + classLoader.getClass());
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		log.info("3 Bean Aware 接口回调测试 : [BeanFactoryAware] - " + beanFactory.getClass());
	}

	@Override
	public void setEnvironment(Environment environment) {
		log.info("4 Bean Aware 接口回调测试 : [EnvironmentAware] - " + environment.getClass());
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		log.info("5 Bean Aware 接口回调测试 : [EmbeddedValueResolverAware] - " + resolver.getClass());
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		log.info("6 Bean Aware 接口回调测试 : [ResourceLoaderAware] - " + resourceLoader.getClass());
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		log.info("7 Bean Aware 接口回调测试 : [ApplicationEventPublisherAware] - " + applicationEventPublisher.getClass());
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		log.info("8 Bean Aware 接口回调测试 : [MessageSourceAware] - " + messageSource.getClass());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.info("9 Bean Aware 接口回调测试 : [ApplicationContextAware] - " + applicationContext.getClass());
	}
}

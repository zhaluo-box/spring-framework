package com.framework.example.source.code.learn.dependency.source;

import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 依赖来源测试
 * 查找与注入有什么区别
 * Created  on 2023/3/8 11:11:45
 *
 * @author zl
 */
public class DependencySourceTest {

	/**
	 * <p>
	 * resolvable dependencies 都是在{@link AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)}
	 * 注册的特殊Bean， 没有beanDefinition ,so throws NoSuchBeanDefinitionException
	 * </p>
	 * 2023/3/8 I need to debug trace my code why does it throw NoSuchBeanException?
	 * debug trace method
	 *
	 * @see DefaultListableBeanFactory#getBean(Class) ==> resolveBean method
	 */
	@Test
	@DisplayName("resolvable dependencies inject test, expect inject fail, throw noSuchBeanException")
	public void resolveDependenciesInjectTest() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(InjectResolvableDependency.class);
		context.refresh();
		try {
			ApplicationContext bean = context.getBean(ApplicationContext.class);
		} catch (NoSuchBeanDefinitionException ex) {
			ex.printStackTrace();
		}

		InjectResolvableDependency injectResolvableDependency = context.getBean(InjectResolvableDependency.class);
		System.out.println(injectResolvableDependency.getApplicationContext());
	}

	@Getter
	static class InjectResolvableDependency {

		@Autowired
		private ApplicationContext applicationContext;

	}
}

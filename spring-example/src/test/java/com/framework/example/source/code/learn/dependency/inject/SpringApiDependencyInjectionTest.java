package com.framework.example.source.code.learn.dependency.inject;

import com.framework.example.common.entity.UserHolder;
import com.framework.example.inject.RoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Spring api 依赖注入测试
 * Created  on 2023/2/10 11:11:12
 *
 * @author zl
 */
public class SpringApiDependencyInjectionTest {

	@Test
	@DisplayName("Aware回调注入测试")
	public void awareCallbackInjectionTest() {

		// new 一个 context
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 通过API 注册一个Bean
		context.register(AwareInjectionTest.class);
		// 启动应用上下文
		context.refresh();

		// 验证是不是自己想要的Bean
		AwareInjectionTest awareInjectionTest = context.getBean(AwareInjectionTest.class);
		System.out.println(awareInjectionTest.getEnvironment());
		System.out.println(awareInjectionTest.getApplicationContext());
		System.out.println(awareInjectionTest.getBeanFactory());

		System.out.println("验证context.getBeanFactory== beanFactory :" + (context.getBeanFactory() == awareInjectionTest.getBeanFactory()));
		System.out.println("context= AwareInjectionTest.getApplicationContext :" + (context == awareInjectionTest.getApplicationContext()));

		// register 只是注册这个bean的definition
		context.register(RoleService.class);

		System.out.println("------getBean的时候才会创建这个Bean的实例----");
		System.out.println(context.getBean(RoleService.class));

		// 手动关闭应用上下文
		context.close();
	}

	@Test
	@DisplayName("基于Bean Definition constructor api 注入")
	public void testBeanDefinitionConst() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-definition-context.xml");

		BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class)
															 .addConstructorArgReference("example-user")
															 .getBeanDefinition();

		context.registerBeanDefinition("userHolder", beanDefinition);
		context.refresh();

		System.out.println(context.getBean(UserHolder.class));

		context.close();

	}

	static class AwareInjectionTest implements ApplicationContextAware, BeanFactoryAware, EnvironmentAware {

		private BeanFactory beanFactory;

		private ApplicationContext applicationContext;

		private Environment environment;

		@Override
		public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
			this.beanFactory = beanFactory;
		}

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.applicationContext = applicationContext;
		}

		@Override
		public void setEnvironment(Environment environment) {
			this.environment = environment;
		}

		public BeanFactory getBeanFactory() {
			return beanFactory;
		}

		public ApplicationContext getApplicationContext() {
			return applicationContext;
		}

		public Environment getEnvironment() {
			return environment;
		}
	}
}

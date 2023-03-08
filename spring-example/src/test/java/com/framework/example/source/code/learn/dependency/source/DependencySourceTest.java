package com.framework.example.source.code.learn.dependency.source;

import com.framework.example.common.entity.User;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

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
			context.getBean(ApplicationContext.class);
		} catch (NoSuchBeanDefinitionException ex) {
			ex.printStackTrace();
		}

		InjectResolvableDependency injectResolvableDependency = context.getBean(InjectResolvableDependency.class);
		System.out.println(injectResolvableDependency.getApplicationContext());
		context.close();
	}

	/**
	 * debug trace method
	 *
	 * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)
	 * @see AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(Object, String, PropertyValues)
	 * @see AutowiredAnnotationBeanPostProcessor#AutowiredAnnotationBeanPostProcessor()
	 */
	@Test
	@DisplayName("外部化配置作为依赖来源注入")
	public void externalConfigurationDependencySourceTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		BeanDefinition externalConfigurableBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(ExternalConfigurationDependency.class)
																				 .setInitMethodName("initMethod")
																				 .getBeanDefinition();

		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-definition-context.xml");

		context.registerBeanDefinition("externalConfigurableDependencySource", externalConfigurableBeanDefinition);
		context.refresh();
		ExternalConfigurationDependency bean = context.getBean(ExternalConfigurationDependency.class);
		System.out.println("autowired object user : " + bean.getUser());
		context.close();

	}

	@Getter
	@Configuration
	@PropertySource(value = "META-INF/default.properties", encoding = "UTF-8")
	static class ExternalConfigurationDependency implements InitializingBean {

		@Value("${user.id:-1}")
		private Long id;

		@Value("${user.name:zhang-san}")
		private String username;

		@Value("${user.resource:classpath://default.properties}")
		private Resource resource;

		@Autowired
		private User user;

		@PostConstruct
		public void init() {
			System.out.println("java annotation @PostConstruct : user.id = " + id);
		}

		@Override
		public void afterPropertiesSet() throws Exception {
			System.out.println("implements InitializingBean  afterProperties : user.name = " + username);
		}

		public void initMethod() {
			System.out.println("init-method : user.resource = " + resource);
		}

	}

	@Getter
	static class InjectResolvableDependency {

		@Autowired
		private ApplicationContext applicationContext;

		@Autowired
		private ApplicationEventPublisher applicationEventPublisher;

		@Autowired
		private BeanFactory beanFactory;

		@Autowired
		private ResourceLoader resourceLoader;

		@PostConstruct
		public void initByInjection() {
			System.out.println("beanFactory == applicationContext " + (beanFactory == applicationContext));
			System.out.println("beanFactory == applicationContext.getBeanFactory() " + (beanFactory == applicationContext.getAutowireCapableBeanFactory()));
			System.out.println("resourceLoader == applicationContext " + (resourceLoader == applicationContext));
			System.out.println("ApplicationEventPublisher == applicationContext " + (applicationEventPublisher == applicationContext));
		}

		@PostConstruct
		public void initByLookup() {
			getBean(BeanFactory.class);
			getBean(ApplicationContext.class);
			getBean(ResourceLoader.class);
			getBean(ApplicationEventPublisher.class);
		}

		private <T> T getBean(Class<T> beanType) {
			try {
				return beanFactory.getBean(beanType);
			} catch (NoSuchBeanDefinitionException e) {
				System.err.println("当前类型" + beanType.getName() + " 无法在 BeanFactory 中查找!");
			}
			return null;
		}

	}
}

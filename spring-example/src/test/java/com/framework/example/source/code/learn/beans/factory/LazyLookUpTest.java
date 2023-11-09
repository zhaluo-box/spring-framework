package com.framework.example.source.code.learn.beans.factory;

import com.framework.example.common.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Bean 的延迟查找测试
 * Created  on 2023/11/9 21:21:13
 *
 * @author zl
 */
public class LazyLookUpTest {

	/**
	 * 里面涉及一些递归查找
	 *
	 * @see org.springframework.context.annotation.ConfigurationClass
	 * @see org.springframework.context.annotation.ConfigurationClassParser
	 * @see org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(BeanDefinitionRegistry)
	 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
	 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForConfigurationClass(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator)
	 */

	@Test
	@DisplayName("@Bean 的实现方式")
	public void BeanMethod() {

	}

	/**
	 *
	 */
	@Test
	@DisplayName("bean 是否是缓存的")
	public void beanIsCached() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 注册 Configuration Class
		context.register(ObjectFactoryLazyLookupDemo.class);

		context.refresh();

		User user = context.getBean(User.class);
		for (int i = 0; i < 5; i++) {
			System.out.println(user == context.getBean(User.class));
		}

		// 关闭 Spring 应用上下文
		context.close();
	}

	/**
	 * 打印显示User 初始化一次
	 * 延迟初始化 ， 延迟查找不一样（用的时候才查找）
	 *
	 * @see DefaultListableBeanFactory.DependencyObjectProvider
	 */
	@Test
	@DisplayName("Bean延迟查找测试")
	public void testLazyLookUp() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 注册 Configuration Class
		context.register(ObjectFactoryLazyLookupDemo.class);

		// 启动 Spring 应用上下文
		context.refresh();

		ObjectFactoryLazyLookupDemo objectFactoryLazyLookupDemo = context.getBean(ObjectFactoryLazyLookupDemo.class);

		// userObjectFactory userObjectProvider;

		// 代理对象
		ObjectFactory<User> userObjectFactory = objectFactoryLazyLookupDemo.userObjectFactory;
		ObjectFactory<User> userObjectProvider = objectFactoryLazyLookupDemo.userObjectProvider;

		System.out.println("userObjectFactory == userObjectProvider : " + (userObjectFactory == userObjectProvider));

		System.out.println(
				"userObjectFactory.getClass() == userObjectProvider.getClass() : " + (userObjectFactory.getClass() == userObjectProvider.getClass()));

		// 实际对象（延迟查找）
		System.out.println("user = " + userObjectFactory.getObject());
		System.out.println("user = " + userObjectProvider.getObject());
		System.out.println("user = " + context.getBean(User.class));

		// 关闭 Spring 应用上下文
		context.close();
	}

	public static class ObjectFactoryLazyLookupDemo {

		@Autowired
		public ObjectFactory<User> userObjectFactory;

		@Autowired
		public ObjectFactory<User> userObjectProvider;

		@Bean
		public User user() {
			return new User().setId(2).setName("zl");
		}
	}

}

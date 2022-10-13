package com.framework.example.source.code.learn.dependency.lookup;

import com.framework.example.common.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Created  on 2022/10/13 10:10:41
 * {@link org.springframework.beans.factory.ObjectProvider}
 *
 * @author zl
 */
public class ObjectProviderTest {

	/**
	 * 集合类型的依赖
	 */
	@Test
	@DisplayName("ObjectProvider 实现了迭代器，并扩展了Java 8 Steam")
	public void streamTest() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		User user = new User().setAge(11).setName("张三");
		User child = new User().setAge(11).setName("张三的儿子");
		beanFactory.registerSingleton("user", user);
		beanFactory.registerSingleton("child", child);

		ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
		beanProvider.stream().forEach(System.out::println);
	}

	/**
	 * 单个Bean测试
	 */
	@Test
	@DisplayName("获取单个Bean ObjectProvider getBean-> ObjectFactory getObject ")
	public void getBeanTest() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		User user = new User().setAge(11).setName("张三");
		beanFactory.registerSingleton("user", user);
		ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
		System.out.println(beanProvider.getObject());
	}

	/**
	 * 如果不存在
	 * spring 对 Supplier 的支持，相对而言 supplier 要比反射更快
	 */
	@Test
	@DisplayName("spring ObjectProvider 对 Supplier 的支持，相对而言 supplier 要比反射更快 ")
	public void getBeanIfAva() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		ObjectProvider<User> beanProvider = beanFactory.getBeanProvider(User.class);
		System.out.println(beanProvider.getIfAvailable(() -> new User().setAge(11).setName("张三")));
	}

}

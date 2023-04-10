package com.framework.example.source.code.learn.dependency.scope;

import com.framework.example.common.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Bean 作用域测试
 * singleton ， 在整个IOC 中并非唯一不变，例如父子Bean之间 就不一定是唯一的，
 * prototype ： 原型
 * request ： 基于一次请求的
 * session ： 基于会话
 * application 与 singleton 区别不大，
 * Created  on 2023/3/10 16:16:01
 *
 * @author zl
 */
public class BeanScopeTest {

	/**
	 * 自定义Bean scope 测试，但是也需要观察官方源代码的测试案例
	 */
	@Test
	@DisplayName("custom scope test")
	public void customScopeTest() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.addBeanFactoryPostProcessor(beanFactory -> beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope()));
		// 为什么自定义写的BeanDefinition  ThreadLocalScope 不生效？？？
//		AbstractBeanDefinition userDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class)
//																	 .setScope(ThreadLocalScope.SCOPE_NAME)
//																	 .addPropertyValue("name", UUID.randomUUID().toString())
//																	 .getBeanDefinition();
//		context.registerBeanDefinition("user", userDefinition);

		context.register(BeanConfig.class);
		context.refresh();
		printUserInfo(context);
		context.close();

	}

	private void printUserInfo(AnnotationConfigApplicationContext applicationContext) {

		for (int i = 0; i < 3; i++) {
			Thread thread = new Thread(() -> {
				User user = applicationContext.getBean(User.class);
				//				User user = applicationContext.getBean("user", User.class);
				System.out.printf("[Thread id :%d] user = %s%n", Thread.currentThread().getId(), user);
			});
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

	static class BeanConfig {

		@Bean
		@org.springframework.context.annotation.Scope(ThreadLocalScope.SCOPE_NAME)
		public User user() {
			return createUser();
		}

		private User createUser() {
			return new User().setId(1).setName(UUID.randomUUID().toString());
		}
	}

	static class ThreadLocalScope implements Scope {

		// scope 的名称
		public static final String SCOPE_NAME = "thread-local-scope";

		// 定义ThreadLocal 存放本地变量

		private final NamedThreadLocal<Map<String, Object>> scopes = new NamedThreadLocal<Map<String, Object>>(SCOPE_NAME) {
			@Override
			protected Map<String, Object> initialValue() {
				return new HashMap<>();
			}
		};

		@Override
		public Object get(String name, ObjectFactory<?> objectFactory) {
			Map<String, Object> context = getContext();

			Object obj = context.get(name);
			if (obj == null) {
				obj = objectFactory.getObject();
				context.put(name, obj);
			}

			return obj;
		}

		private Map<String, Object> getContext() {
			return scopes.get();
		}

		@Override
		public Object remove(String name) {
			return getContext().remove(name);
		}

		@Override
		public void registerDestructionCallback(String name, Runnable callback) {

		}

		@Override
		public Object resolveContextualObject(String key) {
			Map<String, Object> context = getContext();
			return context.get(key);
		}

		@Override
		public String getConversationId() {
			Thread thread = Thread.currentThread();
			return String.valueOf(thread.getId());
		}

	}
}

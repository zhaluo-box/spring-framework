package com.framework.example.source.code.learn.dependency.inject;

import com.framework.example.common.entity.User;
import com.framework.example.common.entity.UserHolder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 注解依赖注入测试
 * Created  on 2023/2/10 11:11:11
 *
 * @author zl
 */
public class AnnotationDependencyInjectionTest {

	@Test
	@DisplayName("方法注入测试")
	public void methodInjectionTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-definition-context.xml");
		context.register(MethodInjection.class);
		context.refresh();
		System.out.println(context.getBean(MethodInjection.class));
		context.close();
	}

	@Test
	@DisplayName("构造器注入测试")
	public void constructorInjectionTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-definition-context.xml");
		context.register(ConstructorInjection2.class);
		context.refresh();
		System.out.println(context.getBean(ConstructorInjection2.class));
		context.close();
	}

	@Test
	@DisplayName("字段注入测试")
	public void fieldInjectionTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-definition-context.xml");
		context.register(FieldInjection.class);
		context.refresh();
		FieldInjection fieldInjection = context.getBean(FieldInjection.class);
		System.out.println(fieldInjection);
		context.close();
	}

	@Test
	@DisplayName("懒加载/延迟注入测试")
	public void lazyAnnotationInjectionTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-definition-context.xml");

		context.register(LazyInjection.class);
		context.refresh();
		LazyInjection lazyObject = context.getBean(LazyInjection.class);
		System.out.println(lazyObject.getUser());
		System.out.println("objectProvider lazy inject");
		lazyObject.getUserObjectProvider().forEach(System.out::println);
		System.out.println("ObjectFactory lazy inject(unsafe inject)" + lazyObject.getUserObjectFactory().getObject());
		context.close();
	}

	@Getter
	public static class LazyInjection {

		@Autowired
		private User user;

		@Autowired
		private ObjectFactory<User> userObjectFactory;

		@Autowired
		private ObjectProvider<User> userObjectProvider;
	}

	public static class ConstructorInjection {
		@Bean
		public UserHolder userHolder(User user) {
			return new UserHolder(user);
		}
	}

	@ToString
	public static class ConstructorInjection2 {
		@Getter
		@Setter
		private User user;

		public ConstructorInjection2(User user) {
			this.user = user;
		}

	}

	@ToString
	public static class FieldInjection {

		@Autowired
		private
		//    static // @Autowired 会忽略掉静态字段
		User user;

	}

	@ToString
	public static class MethodInjection {

		private User user2;

		/**
		 * 方法级别的注入
		 *
		 * @param user 用户
		 */
		@Autowired
		@Qualifier("example-user")
		public void initUser2(User user) {
			this.user2 = user;
		}

	}
}

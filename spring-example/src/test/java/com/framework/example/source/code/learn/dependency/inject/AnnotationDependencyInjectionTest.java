package com.framework.example.source.code.learn.dependency.inject;

import com.framework.example.annotation.MyAutowire;
import com.framework.example.annotation.MyInject;
import com.framework.example.common.entity.User;
import com.framework.example.common.entity.UserHolder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 注解驱动依赖注入测试
 * Created  on 2023/2/10 11:11:11
 *
 * @author zl
 */
public class AnnotationDependencyInjectionTest {

	@Test
	@DisplayName("循环引用（依赖）测试")
	public void CircularReferencesTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 注册 Configuration Class
		context.register(CircularReferencesConfig.class);

		// 如果设置为 false，则抛出异常信息如：currently in creation: Is there an unresolvable circular reference?
		// 默认值为 true
		context.setAllowCircularReferences(true);

		// 启动 Spring 应用上下文
		context.refresh();

		System.out.println("Student : " + context.getBean(Student.class));
		System.out.println("ClassRoom : " + context.getBean(ClassRoom.class));

		// 关闭 Spring 应用上下文
		context.close();
	}

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

	/**
	 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#setAutowiredAnnotationTypes(Set)
	 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#setAutowiredAnnotationType(Class)
	 * @see com.framework.example.annotation.MyInject
	 * @see com.framework.example.annotation.MyAutowire
	 */
	@Test
	@DisplayName("自定义注解测试 ")
	public void customAnnotationInjectionTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-definition-context.xml");
		context.register(CustomAnnotationConfigBean.class);
		context.register(CustomAnnotationInjection.class);
		context.refresh();
		CustomAnnotationInjection injection = context.getBean(CustomAnnotationInjection.class);
		System.out.println("@Autowired : " + injection.getUser());
		System.out.println("@MyAutowired : " + injection.getMyAutowiredUser());
		System.out.println("@MyInject : " + injection.getMyInjectedUser());
		context.close();
	}

	/**
	 * debug method
	 *
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String)
	 * @see DependencyDescriptor
	 * @see org.springframework.beans.factory.support.AutowireCandidateResolver
	 */
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
		System.out.println("ObjectProvider lazy inject");
		System.out.println("Optional<User> perform :" + lazyObject.getOptionalUser().orElse(new User().setName("optional injected fail")));
		lazyObject.getUserObjectProvider().forEach(System.out::println);
		System.out.println("ObjectFactory lazy inject(unsafe inject)" + lazyObject.getUserObjectFactory().getObject());
		context.close();
	}

	/**
	 * @see InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(Object, String)
	 * 如果返回false 会直接跳出，后续的postProcessor 将停止对Bean的处理
	 * 需要结合 orderd 接口一起工作
	 */
	@Test
	@DisplayName("忽略注入测试  没有重现，验证失败。 TODO")
	public void IgnoreAutowiredInjectionTest() {
		// TODO @XX 2023/3/7 待验证
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-definition-context.xml");

		context.register(IgnoreAutowired.class);
		context.refresh();
		IgnoreAutowired ignoreAutowired = context.getBean(IgnoreAutowired.class);
		System.out.println("ignoreAutowired.getUser() = " + ignoreAutowired.getUser());
		System.out.println("context.getBean(\"user\", User.class) = " + context.getBean("user", User.class));
		context.close();
	}

	public static class CircularReferencesConfig {

		@Bean
		public static Student student() {
			Student student = new Student();
			student.setId(1L);
			student.setName("张三");
			return student;
		}

		@Bean
		public static ClassRoom classRoom() {
			ClassRoom classRoom = new ClassRoom();
			classRoom.setName("C122");
			return classRoom;
		}

	}

	@Getter
	@Setter
	public static class Student {
		private Long id;

		private String name;

		@Autowired
		private ClassRoom classRoom;

		@Override
		public String toString() {
			return "Student{" + "id=" + id + ", name='" + name + '\'' + ", classRoom=" + classRoom.getName() + '}';
		}
	}

	@Getter
	@Setter
	public static class ClassRoom {
		private String name;

		@Autowired
		private Collection<Student> students;

		@Override
		public String toString() {
			return "ClassRoom{" + "name='" + name + '\'' + ", students=" + students.getClass() + '}';
		}
	}

	@Getter
	public static class LazyInjection {

		@Autowired
		private User user;

		@Autowired
		private Optional<User> optionalUser;

		@Autowired
		private ObjectFactory<User> userObjectFactory;

		@Autowired
		private ObjectProvider<User> userObjectProvider;
	}

	@Getter
	public static class IgnoreAutowired implements InstantiationAwareBeanPostProcessor {

		@Autowired
		private User user;

		/**
		 * 对于当前类 @Autowired 注解的字段不生效
		 *
		 * @param beanClass the class of the bean to be instantiated
		 * @param beanName  the name of the bean
		 * @return
		 * @throws BeansException
		 */
		@Override
		public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
			System.out.println("beanName = " + beanName);
			System.out.println("beanClass = " + beanClass);
			if (StringUtils.hasText(beanName) && beanName.equals("user") && User.class.equals(beanClass)) {
				return new User().setId(1000);
			}
			return null;
		}

		//		@Override
		//		public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		//
		//			return false;
		//		}
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
	@Getter
	public static class CustomAnnotationInjection {

		@Autowired
		private
		//    static // @Autowired 会忽略掉静态字段
		User user;

		@MyInject
		private User myInjectedUser;

		@MyAutowire
		private User myAutowiredUser;

	}

	public static class CustomAnnotationConfigBean {

		@Bean
		public AutowiredAnnotationBeanPostProcessor myAutowiredPostProcessor() {
			AutowiredAnnotationBeanPostProcessor postProcessor = new AutowiredAnnotationBeanPostProcessor();
			postProcessor.setAutowiredAnnotationTypes(new HashSet<>(Arrays.asList(MyAutowire.class, MyInject.class)));
			return postProcessor;
		}
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

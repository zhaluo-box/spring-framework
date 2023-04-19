package com.framework.example.source.code.learn.definition;

import com.framework.example.common.entity.SuperUser;
import com.framework.example.common.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created  on 2022/10/11 16:16:32
 *
 * @author wmz
 */
@Configuration
public class BeanDefinitionTest {

	@Test
	@DisplayName("测试bean的别名")
	void beanAliasTest() {
		// 配置 XML 配置文件
		// 启动 Spring 应用上下文
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definition-context.xml");
		// 通过别名 zl-user 获取曾用名 user 的 bean
		User user = beanFactory.getBean("user", User.class);
		User aliasUser = beanFactory.getBean("example-user", User.class);
		System.out.println("zl-user 是否与 user Bean 相同：" + (user == aliasUser));
	}

	/**
	 * BeanDefinition 测试
	 */
	@Test
	@DisplayName("Bean Definition create test")
	void beanDefinitionCreationTest() {
		// 通过BeanDefinitionBuilder
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
		BeanDefinition beanDefinition = beanDefinitionBuilder.addPropertyValue("name", "李四")
															 .addPropertyValue("id", 11)
															 .setScope(AbstractBeanDefinition.SCOPE_SINGLETON) // 设置Bean的Scope
															 .getBeanDefinition();
		System.out.println(beanDefinition);
	}

	/**
	 * {@link   org.springframework.beans.factory.DefaultListableBeanFactoryTests#canReferenceParentBeanFromChildViaAlias()}
	 */
	@Test
	@DisplayName("childBeanDefintion测试")
	void childBeanDefintionTest() {
		// 通过 new AbstractBeanDefinition 及其派生类的（也可以称之为泛化）
		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setBeanClass(User.class);
		MutablePropertyValues propertyValues = genericBeanDefinition.getPropertyValues();
		propertyValues.add("name", "张三");
		propertyValues.add("id", 12);

		// ChildBeanDefintion 在构造上需要传递一个 父Bean的名称
		ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition("gener");
		childBeanDefinition.setDescription("需要一个父Bean的名称");

		DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
		defaultListableBeanFactory.registerBeanDefinition("gener", genericBeanDefinition);
		defaultListableBeanFactory.registerBeanDefinition("child", childBeanDefinition);

		// 获取Bean 并验证是否继承了父类的属性
		User child = defaultListableBeanFactory.getBean("child", User.class);
		assertThat(child.getName()).isEqualTo("张三");
	}

	/**
	 * 合并xml 配置的BeanDefinition test
	 *
	 * @see RootBeanDefinition root bd 通常是标准BD merge 之后都会转为一个root BD
	 * @see GenericBeanDefinition xml配置bean 通常都是generic eg: xml
	 * @see ChildBeanDefinition ??? api 定义继承的时候 builder.childBean
	 * need to debug method {@link AbstractBeanFactory#getMergedBeanDefinition(String, BeanDefinition, BeanDefinition)}  }
	 */
	@Test
	@DisplayName("Bean 信息合并测试， 以XML的配置作为演示")
	void mergeXmlConfigBeanDefinitionTest() {

		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

		ClassPathResource classPathResource = new ClassPathResource("META-INF/bean-definition-context.xml");
		EncodedResource resource = new EncodedResource(classPathResource, StandardCharsets.UTF_8);
		int size = xmlBeanDefinitionReader.loadBeanDefinitions(resource);

		assertThat(size).isEqualTo(2).as("期待加载的db数量为 ", size);

		User user = beanFactory.getBean("user", User.class);
		assertThat(user.getAge()).isEqualTo(12).as("年龄为12 ");

		User superUser = beanFactory.getBean("superUser", SuperUser.class);
		System.out.println(superUser);

	}

	/**
	 * api 定义的继承关系，原本指定的class 类型会被转为父类的类型
	 */
	@Test
	@DisplayName("注解Bean的 BeanDefinition 测试")
	void mergeAnnotationConfigBeanDefintionTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanDefinitionTest.class);

		ChildBeanDefinition beanDefinition = (ChildBeanDefinition) BeanDefinitionBuilder.childBeanDefinition("user")
																						.addPropertyValue("name", "child")
																						.getBeanDefinition();

		// 制定了BeanClass 也没用，最后返回的还是User 父类的类型
		AbstractBeanDefinition beanDefinition1 = BeanDefinitionBuilder.genericBeanDefinition(SuperUser.class)
																	  .setParentName("user")
																	  .addPropertyValue("name", "gener")
																	  .getBeanDefinition();

		context.registerBeanDefinition("superUser2", beanDefinition);
		context.registerBeanDefinition("superUser3", beanDefinition1);

		try {

			AbstractBeanDefinition beanDefinition2 = BeanDefinitionBuilder.rootBeanDefinition(SuperUser.class)
																		  .setParentName("user")
																		  .addPropertyValue("name", "root")
																		  .getBeanDefinition();
			context.registerBeanDefinition("superUser4", beanDefinition2);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			assertThat(exception).isInstanceOf(IllegalArgumentException.class).as("root Bean 不能被改变parentName");
		}

		System.out.println("superUser :");
		context.getBeanProvider(SuperUser.class).forEach(System.out::println);
		System.out.println("user      :");
		context.getBeanProvider(User.class).forEach(System.out::println);

		context.close();
	}

	//	@Configuration
	//	@Data
	public class AnnotationConfiguration {

		@Bean
		public User user() {
			return new User().setName("parent").setAge(12);
		}

		@Bean
		public SuperUser superUser() {
			return new SuperUser().setAddress("121");
		}

	}
}

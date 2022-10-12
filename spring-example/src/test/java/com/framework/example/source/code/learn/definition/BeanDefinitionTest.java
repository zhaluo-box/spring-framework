package com.framework.example.source.code.learn.definition;

import com.framework.example.common.entity.User;
import com.sun.xml.internal.bind.v2.TODO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created  on 2022/10/11 16:16:32
 *
 * @author wmz
 */
class BeanDefinitionTest {

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
	public void childBeanDefintionTest() {
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
}

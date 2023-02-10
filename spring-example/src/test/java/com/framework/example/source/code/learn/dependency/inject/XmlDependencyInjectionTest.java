package com.framework.example.source.code.learn.dependency.inject;

import com.framework.example.common.entity.UserHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml 依赖注入测试
 * Created  on 2023/2/10 11:11:10
 *
 * @author zl
 */
public class XmlDependencyInjectionTest {

	@Test
	@DisplayName("setter 方法注入测试")
	public void setterMethodInjectionTest() {
		ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext();
		xmlApplicationContext.setConfigLocation("classpath:/META-INF/dependency-injection-context.xml");

		xmlApplicationContext.refresh();
		UserHolder userHolder = xmlApplicationContext.getBean(UserHolder.class);
		System.out.println(userHolder);

		xmlApplicationContext.close();
	}

	@Test
	@DisplayName("构造器注入测试")
	public void constructorInjectionTest() {

		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-injection-context.xml");

		// 显式的使用 <constructor-arg ref='user'>
		UserHolder userHolder2 = beanFactory.getBean("userHolder2", UserHolder.class);
		// 隐式的使用 采用 autowireType= constructor
		UserHolder userHolder3 = beanFactory.getBean("userHolder3", UserHolder.class);
		System.out.println(userHolder2);
		System.out.println(userHolder3);
	}
}

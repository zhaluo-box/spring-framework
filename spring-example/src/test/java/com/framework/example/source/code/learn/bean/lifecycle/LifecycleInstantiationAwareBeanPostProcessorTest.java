package com.framework.example.source.code.learn.bean.lifecycle;

import com.framework.example.common.entity.User;
import com.framework.example.common.entity.UserHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created  on 2023/5/8 16:16:00
 *
 * @author zl
 * @see org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor
 */
public class LifecycleInstantiationAwareBeanPostProcessorTest {

	/**
	 * 期待： 有返回值，对指定的Bean进行替换，并且后面的逻辑不在对当前Bean的属性进行填充
	 *
	 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInstantiation(Class, String)
	 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#populateBean(String, RootBeanDefinition, BeanWrapper)
	 */
	@Test
	@DisplayName("测试BeanPostprocessor 前置处理")
	void postProcessBeforeInstantiationTest() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean-lifecycle-context.xml");

		context.register(MyInstantiationAwareBeanPostProcessor.class);
		context.refresh();

		//		postProcessBeforeInstantiation method  return  obj, obj != null 则后续不在进行进行实例化，采用return 的对象
		User user1 = context.getBean("user1", User.class);
		System.out.println("context.getBean(\"user1\") = " + user1);
		assertThat(user1.getName()).isEqualTo("我是老大");

		// postProcessAfterInstantiation method  return false, 则无论是基础属性还是 引用属性，都不再进行填充
		UserHolder userHolder = context.getBean("userHolder", UserHolder.class);
		System.out.println("context.getBean(\"userHolder\") = " + userHolder);
		assertThat(userHolder.getUser()).isNull();

		System.out.println("测试构造器是否受 postProcessAfterInstantiation method 影响, 结果是不受影响");
		UserHolder userHolder2 = context.getBean("userHolder2", UserHolder.class);
		System.out.println("context.getBean(\"userHolder2\") = " + userHolder2);
		assertThat(userHolder2.getUser()).hasNoNullFieldsOrPropertiesExcept("user").describedAs("构造器注入就是这么叼，谁叫这是Java呢");

		context.close();
	}

	public static class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

		@Override
		public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

			if (ObjectUtils.nullSafeEquals(beanName, "user1") && beanClass.equals(User.class)) {
				return new User().setName("我是老大");
			}

			return null;
		}

		@Override
		public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

			// 如果是user holder 则不进行bean 注入填充
			if (ObjectUtils.nullSafeEquals(beanName, "userHolder") || ObjectUtils.nullSafeEquals(beanName, "userHolder2")) {
				return false;
			}

			return true;
		}
	}
}

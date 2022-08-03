package com.example.spring.learn.postprocess;

import com.example.spring.learn.common.entity.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * Created  on 2022/1/22 17:17:43
 *
 * @author zl
 */
@Configuration
public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("==postProcessBeanFactory");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("===postProcessBeanDefinitionRegistry");
		Class<Student> studentClass = Student.class;
		BeanDefinitionBuilder bdBuilder = BeanDefinitionBuilder.genericBeanDefinition(studentClass);
		bdBuilder.setScope(BeanDefinition.SCOPE_PROTOTYPE); // 设置一个原型Bean
		AbstractBeanDefinition bd = bdBuilder.getBeanDefinition();
		registry.registerBeanDefinition("student", bd);
	}
}

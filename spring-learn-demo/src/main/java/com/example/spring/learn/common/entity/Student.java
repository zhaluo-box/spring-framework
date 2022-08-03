package com.example.spring.learn.common.entity;

/**
 * BeanDefinitionRegistryPostProcessor 演示需要的Bean
 * Created  on 2022/1/22 17:17:45
 *
 * @author zl
 */
public class Student {

	public void showMe() {
		System.out.println("我是通过实现 BeanDefinitionRegistryPostProcessor 注册的Bean ");
	}

}

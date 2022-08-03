package com.example.spring.learn.service;

import com.example.spring.learn.common.service.WelcomeService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class DefaultWelcomeService implements WelcomeService, InitializingBean, DisposableBean {

	@Override
	public String sayHello(String name) {
		return "Hello," + name;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println(" InitializingBean: afterPropertiesSet: 初始化Bean  对Bean的属性进行自定义填充");
	}

	public void initMethod() {
		System.out.println("xml tag:init-Method : 初始化自定义Method ");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("DisposableBean: destroy: 销毁Bean");
	}

	public void destroyMethod() {
		System.out.println("xml tag:destroy-method : 销毁Bean ");
	}
}

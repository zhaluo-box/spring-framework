package com.example.spring.learn.controller;

import com.example.spring.learn.common.service.WelcomeService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 */
@Controller
public class WelcomeController {

	@Autowired
	private WelcomeService helloService;

	public void handleRequest() {
		helloService.sayHello("强大的spring 框架!");
	}
}

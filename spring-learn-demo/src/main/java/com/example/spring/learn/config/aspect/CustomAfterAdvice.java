package com.example.spring.learn.config.aspect;

import org.aopalliance.aop.Advice;
import org.springframework.aop.*;

import java.lang.reflect.Method;

/**
 * Created  on 2022/7/22 22:22:09
 *
 * @author zl
 */
public class CustomAfterAdvice implements AfterAdvice, AfterReturningAdvice, BeforeAdvice, IntroductionAdvisor {

	@Override
	public Advice getAdvice() {
		return null;
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}

	@Override
	public ClassFilter getClassFilter() {
		return null;
	}

	@Override
	public void validateInterfaces() throws IllegalArgumentException {

	}

	@Override
	public Class<?>[] getInterfaces() {
		return new Class[0];
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("自定义 afterReturning 执行了");
		System.out.println(method.getName() + "  ----  " + target.getClass());
	}



}

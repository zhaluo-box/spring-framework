package com.example.spring.learn.config.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created  on 2022/7/22 22:22:13
 *
 * @author zl
 */
public class CustomMethodInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return null;
	}
}

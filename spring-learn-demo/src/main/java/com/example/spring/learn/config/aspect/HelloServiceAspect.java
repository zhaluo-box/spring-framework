package com.example.spring.learn.config.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created  on 2022/7/17 14:14:07
 *
 * @author zl
 */
@Aspect
@Component
public class HelloServiceAspect {

	@Pointcut("execution(* com.example.spring.learn.service.DefaultWelcomeService.*(..))")
	public void pointcut() {
	}

	@Before("pointcut()")
	public void before(JoinPoint joinPoint) {
		System.out.println("before 拦截" + joinPoint);
	}

	@After("pointcut()")
	public void after(JoinPoint joinPoint) {
		System.out.println("after 拦截" + joinPoint);
	}

	@AfterReturning(pointcut = "pointcut()", returning = "returnValue")
	public void afterReturning(JoinPoint joinPoint, Object returnValue) {
		System.out.println("return 拦截" + joinPoint);
		System.out.println("return 拦截, 返回值" + returnValue);
	}
}

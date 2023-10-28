package com.framework.example.source.code.learn.context.annotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created  on 2023/10/28 23:23:54
 *
 * @author zl
 */

public class AnnotationTest {

	@Test
	@DisplayName("测试spring 注解的嵌套")
	public void testComponentScanHirTest() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(PackageScanTest.class);

		context.refresh();
		System.out.println("context.getBean(PackageScanTest.class) = " + context.getBean(PackageScanTest.class));
		context.close();
	}

	@ComponentScan
	public static class PackageScanTest {

	}

	@MyComponent2
	public static class AnnotationTestObj {

	}

	@Component
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface MyComponent {

	}

	@Component
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface MyComponent2 {

	}

}

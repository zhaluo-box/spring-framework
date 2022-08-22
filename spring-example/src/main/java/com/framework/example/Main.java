package com.framework.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created  on 2022/8/22 21:21:27
 *
 * @author zl
 */
//@Slf4j
public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.refresh();
	}
}

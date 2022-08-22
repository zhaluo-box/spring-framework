package com.framework.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created  on 2022/8/22 21:21:27
 *
 * @author zl
 */
@Slf4j
public class Main {

	public static void main(String[] args) {
		log.debug("--------");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.refresh();
	}
}

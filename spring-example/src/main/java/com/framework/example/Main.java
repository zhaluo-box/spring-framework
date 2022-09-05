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

	static class Sub {
		//		如果包含一个显式声明的无参构造，依旧会抛出create bean 异常，还是无法推断出正确的构造，
		//BeanCreationException 当存在多个，或者不存在构造函数时抛出 （Java 反射获取的隐式构造函数除外）
		//		public Sub() {
		//			System.out.println("给与一个初始构造");
		//		}

		//		public Sub(Main main) {
		//			System.out.
		//
		//			("采用Main 构造");
		//		}
	}

	public static void main(String[] args) {
		log.debug("--------");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		//		context.register(Main.class); // 如果Sub Class 被static  修饰 它就可以作为一个Class 单独存在，而非静态时作为一个属性在Main Class 中，所以需要现有Main 后有Sub
		context.register(Sub.class);
		context.refresh();
		context.getBean(Sub.class);
	}
}

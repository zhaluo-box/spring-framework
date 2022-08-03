package com.example.spring.learn;

import com.example.spring.learn.common.entity.Student;
import com.example.spring.learn.common.entity.User;
import com.example.spring.learn.common.service.WelcomeService;
import com.example.spring.learn.service.DefaultWelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 程序入口
 */
@Configuration
@ComponentScan(value = "com.example.spring.learn")
@EnableAspectJAutoProxy
public class Application {

	public static void main(String[] args) {
		annotationTest();
		//		fileSystemTest();
	}

	private static void annotationTest() {

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println("BeanDefinition : " + beanDefinitionName);
		}

		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("defaultWelcomeService");
		System.out.println(welcomeService.sayHello("强大的spring 框架"));

		// 演示自定义BeanDefinitionRegistryProcessor
		Student student1 = (Student) applicationContext.getBean("student");
		Student student2 = (Student) applicationContext.getBean("student");
		// 演示原型Bean
		System.out.println(student1);
		System.out.println(student2);

		student1.showMe();

	}

	private static void fileSystemTest() {
		System.out.println("welcome spring !");

		//TODO　　修改为classPath:/spring/spring-config.xml
		String path = "C:\\workspace\\spring-framework-5.2.0.RELEASE\\spring-learn-demo\\src\\main\\resources\\spring\\spring-config.xml";

		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(path);
		WelcomeService helloService = (WelcomeService) context.getBean("helloService");
		System.out.println(helloService.sayHello("张三"));

		System.out.println("无参构造 : " + context.getBean("user1"));
		System.out.println("无参构造 : " + context.getBean("user1"));
		System.out.println("静态工厂 : " + context.getBean("user2"));
		System.out.println("静态工厂 : " + context.getBean("user2"));
		System.out.println("实例工厂 : " + context.getBean("user3"));
		System.out.println("实例工厂 : " + context.getBean("user3"));

		// FactoryBean  
		System.out.println("factoryBean : " + context.getBean("userFactoryBean"));
		System.out.println("factoryBean : " + context.getBean("&userFactoryBean"));

		context.close();

	}
}

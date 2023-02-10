package com.framework.example.source.code.learn.dependency.inject;

import com.framework.example.inject.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring 注入模型测试
 * setter 注入
 * 构造器注入  xml <construct-arg />
 * 字段注入 private object  obj, 上面加 @Autowire  @Resource @Value 。。。
 * 方法注入 典型的就是@Bean  还有加在方法上的@Autowire
 * 回调注入
 * Created  on 2022/9/7 15:15:12
 *
 * @author zl
 */
public class InjectModelTest {

	/**
	 * {@link org.springframework.beans.factory.annotation.InjectionMetadata}
	 * {@link org.springframework.beans.factory.annotation.InjectionMetadata.InjectedElement 与其派生类}
	 * {@link org.springframework.beans.factory.config.DependencyDescriptor 依赖描述器}
	 */
	@Test
	@DisplayName("测试Resource与Autowired")
	public void testResourceInject() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.framework.example.inject");
		context.refresh();
		context.getBean(UserService.class);
		context.close();
	}



}

package com.framework.example.source.code.learn.inject;

import com.framework.example.inject.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring 注入模型测试
 * setter 注入
 * 构造器注入
 * 属性注入 不存在，只不过是 注解的一种描述方式
 * Created  on 2022/9/7 15:15:12
 *
 * @author zl
 */
public class InjectModelTest {

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

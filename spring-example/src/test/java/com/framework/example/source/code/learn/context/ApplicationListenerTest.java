package com.framework.example.source.code.learn.context;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.ResolvableType;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * spring 事件监听器测试
 * Created  on 2023/10/26 00:0:21
 *
 * @author zl
 */
public class ApplicationListenerTest {

	/**
	 * mercy-spring lesson 190
	 *
	 * <p> 事件被执行多次，主要是因为父上下文也注册了同样的监听器，不注册就不会出现被执行多次的情况</p>
	 *
	 * @see AbstractApplicationContext#publishEvent(Object, ResolvableType)
	 */
	@Test
	@DisplayName("spring 事件监听器，层次性上下文造成的现场测试")
	public void HierarchicalSpringEventPropagateTest() {
		// 1. 创建 parent Spring 应用上下文
		AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
		parentContext.setId("parent-context");
		// 注册 MyListener 到 parent Spring 应用上下文
		parentContext.register(MyListener.class);

		// 2. 创建 current Spring 应用上下文
		AnnotationConfigApplicationContext currentContext = new AnnotationConfigApplicationContext();
		currentContext.setId("current-context");
		// 3. current -> parent
		currentContext.setParent(parentContext);
		// 注册 MyListener 到 current Spring 应用上下文
		currentContext.register(MyListener.class);

		// 4.启动 parent Spring 应用上下文
		parentContext.refresh();

		// 5.启动 current Spring 应用上下文
		currentContext.refresh();

		// 关闭所有 Spring 应用上下文
		currentContext.close();
		parentContext.close();
	}

	static class MyListener implements ApplicationListener<ApplicationContextEvent> {

		/**
		 * 静态之后就可以达到过滤的效果，主要是因为上面是两个不同的实例
		 */
		private Set<ApplicationContextEvent> processedEvents = new LinkedHashSet<>();

		@Override
		public void onApplicationEvent(ApplicationContextEvent event) {
			if (processedEvents.add(event)) {
				System.out.printf("监听到 Spring 应用上下文[ ID : %s ] 事件 :%s\n", event.getApplicationContext().getId(), event.getClass().getSimpleName());
			}
		}
	}

}

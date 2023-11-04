package com.framework.example.source.code.learn.core.enviroument;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * {@link org.springframework.context.annotation.ConfigurationClassParser#parse(String, String)}
 * <p>
 * {@link org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass(org.springframework.context.annotation.ConfigurationClass, ConfigurationClassParser.SourceClass, Predicate)}
 * <p>
 * {@link org.springframework.context.annotation.ConfigurationClassParser#processPropertySource(AnnotationAttributes)} 处理 {@link org.springframework.context.annotation.PropertySource} 注解
 * <p>
 * {@link org.springframework.context.annotation.ConfigurationClassParser#addPropertySource(PropertySource)} 注解 value 中有多个地址，同名值的一个覆盖， 多个资源的文件处于 同一个name 下的{@link PropertySource} 存放
 * spring.env 测试，主要测试其依赖注入的原理
 * Created  on 2023/11/4 15:15:25
 *
 * @author zl
 * @see Environment
 */
public class EnvironmentTest {

	/**
	 * 默认创建的是 {@link org.springframework.core.env.StandardEnvironment}
	 * web 环境下创建的是 {@link org.springframework.web.context.support.StandardServletEnvironment}
	 * spring 与 spring boot 下 environment 不一样，environment 留下了扩展点，
	 * <p>
	 * 一个是覆写 {@link AbstractApplicationContext#createEnvironment()}
	 * <p>
	 * 另一个是 {@link AbstractApplicationContext#setEnvironment(ConfigurableEnvironment)}
	 * <p>
	 * *
	 *
	 * @see AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)
	 * @see AbstractApplicationContext#getEnvironment()
	 * @see AbstractApplicationContext#createEnvironment()
	 */
	@Test
	@DisplayName("依赖注入&查找测试")
	public void dependencyInjectAndLookUpTest() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.register(EnvironmentTestObj.class);

		context.refresh();

		Environment environment = context.getBean(ConfigurableApplicationContext.ENVIRONMENT_BEAN_NAME, Environment.class);
		EnvironmentTestObj environmentTestObj = context.getBean(EnvironmentTestObj.class);

		System.out.println("environmentTestObj.environment == environment = " + (environmentTestObj.environment == environment));

		context.close();

	}

	/**
	 * {@link DefaultConversionService} 接口需要回顾
	 * {@link org.springframework.format.support.DefaultFormattingConversionService} spring cloud 采用（还是扩展）的类型实现
	 *
	 * @see AutowiredAnnotationBeanPostProcessor#AutowiredAnnotationBeanPostProcessor()
	 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(Object, String, PropertyValues)
	 * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#resolveFieldValue(Field, Object, String)
	 * @see DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String, Set, TypeConverter)
	 * @see DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, String, Set, TypeConverter)
	 * @see QualifierAnnotationAutowireCandidateResolver#getSuggestedValue(DependencyDescriptor)
	 */
	@Test
	@DisplayName("@Value依赖注入测试")
	public void valueInjectTest() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.register(EnvironmentTestObj.class);

		context.refresh();

		EnvironmentTestObj environmentTestObj = context.getBean(EnvironmentTestObj.class);
		System.out.println("environmentTestObj.username = " + environmentTestObj.username);

		context.close();

	}

	/**
	 * debug 查看 propertySources 中的数据
	 * <p>
	 * FIFO frist in frist out 的一种体现
	 * <p>
	 * {@link org.springframework.context.annotation.ConfigurationClassParser#parse(String, String)}
	 * <p>
	 * {@link org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass(org.springframework.context.annotation.ConfigurationClass, ConfigurationClassParser.SourceClass, Predicate)}
	 * <p>
	 * {@link org.springframework.context.annotation.ConfigurationClassParser#processPropertySource(AnnotationAttributes)} 处理 {@link org.springframework.context.annotation.PropertySource} 注解
	 * <p>
	 * {@link org.springframework.context.annotation.ConfigurationClassParser#addPropertySource(PropertySource)} 注解 value 中有多个地址，同名值的一个覆盖， 多个资源的文件处于 同一个name 下的{@link PropertySource} 存放
	 */
	@Test
	@DisplayName("手动添加 PropertySource")
	public void propertySourceManualAddTest() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.register(EnvironmentTestObj.class);
		ConfigurableEnvironment environment = context.getEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();

		System.out.println("propertySources = " + propertySources);

		Map<String, Object> source = new HashMap<>();
		source.put("user.name", "张三");
		MapPropertySource mapPropertySource = new MapPropertySource("manual-add-source", source);
		propertySources.addFirst(mapPropertySource);

		context.refresh();
		source.put("user.name", "007");

		EnvironmentTestObj environmentTestObj = context.getBean(EnvironmentTestObj.class);
		System.out.println("environmentTestObj.username = " + environmentTestObj.username);

		context.close();

	}

	public static class EnvironmentTestObj implements EnvironmentAware {

		@Value("${user.name}")
		private String username;

		private Environment environment;

		@Autowired
		private Environment environment2;

		@Override
		public void setEnvironment(Environment environment) {
			this.environment = environment;
		}
	}
}

package com.framework.example.source.code.learn.bean.configmetadata;

import com.framework.example.common.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置元信息测试
 * Created  on 2023/8/31 11:11:32
 *
 * @author zl
 */
public class ConfigMetadataTest {

	@Test
	@DisplayName("扩展spring xml 名称空间测试")
	public void extensionXmlNameSpaceTest() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("META-INF/user-context.xml");

		Map<String, User> beansOfType = beanFactory.getBeansOfType(User.class);
		System.out.println("beansOfType = " + beansOfType);
	}

	/**
	 * <p>
	 * 触发时机
	 *
	 * @see AbstractApplicationContext#obtainFreshBeanFactory()
	 * @see AbstractRefreshableApplicationContext#refreshBeanFactory()
	 * @see org.springframework.context.support.AbstractXmlApplicationContext#loadBeanDefinitions(XmlBeanDefinitionReader)
	 * @see XmlBeanDefinitionReader#doLoadBeanDefinitions(InputSource, Resource)
	 * @see DefaultBeanDefinitionDocumentReader#parseBeanDefinitions(Element, BeanDefinitionParserDelegate)  这里是解析自定义元素和spring 自带元素的地方
	 * @see org.springframework.beans.factory.xml.BeanDefinitionParserDelegate#parseCustomElement(Element, BeanDefinition)
	 * </p>
	 *
	 * <p>
	 * 核心流程
	 * BeanDefinitionParserDelegate#parseCustomElement(org.w3c.dom.Element, BeanDefinition)
	 * 获取 namespace
	 * 通过 namespace 解析 NamespaceHandler
	 * 构造 ParserContext
	 * 解析元素，获取 BeanDefinintion:
	 * </p>
	 */
	@Test
	@DisplayName("扩展spring xml 名称空间测试2")
	public void extensionXmlNameSpaceTest2() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
		//		reader.loadBeanDefinitions("META-INF/user-context.xml");
		// context.register(XmlImportConfig.class);

		context.setConfigLocation("classpath:/META-INF/user-context.xml");

		context.refresh();

		Map<String, User> beansOfType = context.getBeansOfType(User.class);
		System.out.println("beansOfType = " + beansOfType);

		context.close();
	}

	/**
	 * yml 配置bean 与 spring @configProperties 的联系 ？
	 * 由于 spring Beans 中 optional("org.yaml:snakeyaml")， 所以测试的时候需要手动添加依赖 compile('org.yaml:snakeyaml:2.0')
	 */
	@Test
	@DisplayName("yml 配置Bean元信息 测试")
	@SuppressWarnings("all")
	public void yamlConfigBeanTest() {
		// 创建 IoC 底层容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 创建 XML 资源的 BeanDefinitionReader
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		// 记载 XML 资源
		reader.loadBeanDefinitions("META-INF/yaml-property-source-context.xml");
		// 获取 Map YAML 对象
		Map<String, Object> yamlMap = beanFactory.getBean("yamlMap", Map.class);
		System.out.println(yamlMap);

		Properties yamlProperties = beanFactory.getBean("yamlProperties", Properties.class);
		System.out.println("yamlProperties = " + yamlProperties);
	}

	/**
	 * @see org.springframework.beans.factory.support.PropertiesBeanDefinitionReader
	 * @see PropertiesBeanDefinitionReader#registerBeanDefinitions(Map, String, String) 主要是解析bean 的名称 & properties 中的其他元素属性
	 * @see PropertiesBeanDefinitionReader#registerBeanDefinition(String, Map, String, String) 真正对properties 中的属性取值并转换为BeanDefinition
	 */
	@Test
	@DisplayName("properties 配置bean 元信息测试")
	public void propertiesConfigBeanTest() {

		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		PropertiesBeanDefinitionReader propertiesBeanDefinitionReader = new PropertiesBeanDefinitionReader(beanFactory);
		propertiesBeanDefinitionReader.loadBeanDefinitions("META-INF/user-config-metadata.properties");

		User user = beanFactory.getBean(User.class);
		System.out.println("user = " + user);

	}

	@Test
	@DisplayName("propertySource 引入外部化配置")
	public void propertySourceConfigTest() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		// 扩展 Environment 中的 PropertySources
		// 添加 PropertySource 操作必须在 refresh 方法之前完成
		HashMap<String, Object> propertiesSourceMap = new HashMap<>();
		propertiesSourceMap.put("user.name", "zs");
		MapPropertySource mapPropertySource = new MapPropertySource("properties-1", propertiesSourceMap);

		context.getEnvironment().getPropertySources().addFirst(mapPropertySource);

		// 注册当前类作为 Configuration Class
		context.register(PropertySourceConfigObj.class);
		// 启动 Spring 应用上下文
		context.refresh();
		// beanName 和 bean 映射
		Map<String, User> usersMap = context.getBeansOfType(User.class);
		for (Map.Entry<String, User> entry : usersMap.entrySet()) {
			System.out.printf("User Bean name : %s , content : %s \n", entry.getKey(), entry.getValue());
		}
		System.out.println(context.getEnvironment().getPropertySources());
		// 关闭 Spring 应用上下文
		context.close();

	}

	@Configuration
	@PropertySource(value = "classpath:/META-INF/user-config-metadata.properties", encoding = "utf-8")
	public static class PropertySourceConfigObj {

		@Bean
		public User user(@Value("${user.name}") String name, @Value("${user.id}") int id) {
			return new User().setName(name).setId(id);
		}
	}

	@Configuration
	@ImportResource("classpath:/META-INF/user-context.xml")
	public static class XmlImportConfig {

		{
			System.out.println("XmlImportConfig init!");
		}
	}
}

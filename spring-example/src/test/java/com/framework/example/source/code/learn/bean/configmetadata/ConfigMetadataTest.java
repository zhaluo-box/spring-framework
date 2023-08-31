package com.framework.example.source.code.learn.bean.configmetadata;

import com.framework.example.common.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

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
	 * yml 配置bean  与 spring @configProperties 的联系
	 * 由于 spring Beans 中 optional("org.yaml:snakeyaml")， 所以测试的时候需要手动添加依赖 compile('org.yaml:snakeyaml:2.0')
	 */
	@Test
	@DisplayName("yml 配置Bean 测试")
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
}

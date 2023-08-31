package com.framework.example.source.code.learn.bean.configmetadata;

import com.framework.example.common.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

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
}

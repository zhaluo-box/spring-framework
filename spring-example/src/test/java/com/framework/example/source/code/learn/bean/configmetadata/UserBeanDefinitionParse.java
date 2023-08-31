package com.framework.example.source.code.learn.bean.configmetadata;

import com.framework.example.common.entity.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created  on 2023/8/30 16:16:19
 *
 * @author zl
 */
public class UserBeanDefinitionParse extends AbstractSimpleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return User.class;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		setPropertyValue("id", element, builder);
		setPropertyValue("name", element, builder);
		setPropertyValue("liveCity", element, builder);
		// TODO  对于集合属性的解析
		setPropertyValue("livedCity", element, builder);
		setPropertyReference("subUser", element, builder);

		postProcess(builder, element);
	}

	private static void setPropertyReference(String attributeName, Element element, BeanDefinitionBuilder builder) {
		String attributeValue = element.getAttribute(attributeName);
		if (StringUtils.hasText(attributeValue)) {
			builder.addPropertyReference(attributeName, attributeValue);// -> <property name="" ref=""/>
		}
	}

	private void setPropertyValue(String attributeName, Element element, BeanDefinitionBuilder builder) {
		String attributeValue = element.getAttribute(attributeName);
		if (StringUtils.hasText(attributeValue)) {
			builder.addPropertyValue(attributeName, attributeValue); // -> <property name="" value=""/>
		}
	}
}

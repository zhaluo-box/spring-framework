package com.framework.example.source.code.learn.bean.configmetadata;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created  on 2023/8/31 11:11:15
 *
 * @author zl
 */
public class UserNameSpaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("user", new UserBeanDefinitionParse());
	}

}

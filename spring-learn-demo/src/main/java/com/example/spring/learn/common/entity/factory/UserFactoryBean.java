package com.example.spring.learn.common.entity.factory;

import com.example.spring.learn.common.entity.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created  on 2021/12/7 11:11:39
 * factoryBean 学习
 */
public class UserFactoryBean implements FactoryBean<User> {

	@Override
	public User getObject() throws Exception {
		return new User();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}

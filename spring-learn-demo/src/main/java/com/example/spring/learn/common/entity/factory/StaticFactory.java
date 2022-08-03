package com.example.spring.learn.common.entity.factory;

import com.example.spring.learn.common.entity.User;

/**
 * 静态工厂创建User
 */
public class StaticFactory {

	public static User getUser() {
		return new User();
	}

}

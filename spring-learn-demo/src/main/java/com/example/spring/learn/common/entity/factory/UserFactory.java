package com.example.spring.learn.common.entity.factory;

import com.example.spring.learn.common.entity.User;

/**
 * Created  on 2021/12/7 14:14:19
 */
public class UserFactory {

	public User getUser() {
		return new User();
	}
}

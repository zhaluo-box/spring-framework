package com.framework.example.common.entity;

import lombok.ToString;

/**
 * Created  on 2023/2/10 11:11:28
 *
 * @author zl
 */
@ToString
public class UserHolder {

	public UserHolder() {
	}

	public UserHolder(User user) {
		this.user = user;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
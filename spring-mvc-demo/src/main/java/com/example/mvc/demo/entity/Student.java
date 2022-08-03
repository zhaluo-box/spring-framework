package com.example.mvc.demo.entity;

/**
 * Created  on 2022/7/31 15:15:47
 *
 * @author zl
 */

public class Student {

	private String name;

	private String nickName;

	private String address;

	public Student() {
	}

	public Student(String name, String nickName, String address) {
		this.name = name;
		this.nickName = nickName;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

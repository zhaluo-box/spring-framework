package com.framework.example.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created  on 2022/10/12 10:10:57
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public class User {

	private int id;

	private String name;

	private int age;

}

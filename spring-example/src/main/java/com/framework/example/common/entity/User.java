package com.framework.example.common.entity;

import com.framework.example.common.enums.City;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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

	/**
	 * 生活的城市
	 */
	private City liveCity;

	/**
	 * 生活过的城市
	 */
	private List<City> livedCity;

}

package com.framework.example.common.entity;

import com.framework.example.common.enums.City;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created  on 2022/10/12 10:10:57
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public class User implements InitializingBean, DisposableBean {

	private Integer id;

	private String name;

	private Integer age;

	/**
	 * 生活的城市
	 */
	private City liveCity;

	/**
	 * 生活过的城市
	 */
	private List<City> livedCity;

	/**
	 * 子对象
	 */
	private User subUser;

	@Override
	public void destroy() throws Exception {
		System.out.println("user bean 销毁！");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("user bean 初始化！");
	}
}

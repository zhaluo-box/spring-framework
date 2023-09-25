package com.framework.example.common.entity;

import lombok.Data;

import java.util.Properties;

/**
 * 声明周期& 数据访问演示类
 * Created  on 2023/9/25 14:14:27
 *
 * @author zl
 */
@Data
public class LifeCyclePreview {

	private String id;

	/**
	 * 基于自定义的StringToProperties 对内容进行填充
	 */
	private Properties context;

	/**
	 * 基于Converter 将properties 转为 text
	 */
	private String contextAsText;
}

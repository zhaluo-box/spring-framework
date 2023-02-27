package com.framework.example.source.code.learn.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.util.HashMap;
import java.util.List;

/**
 * @author zl
 * @see ResolvableType
 * Created  on 2023/2/27 15:15:06
 */
public class ResolvableTypeTest {

	@Test
	@DisplayName(" ResolvableType 简单测试")
	public void simpleTest() throws NoSuchFieldException {
		// 解析一个字段为type
		ResolvableType t = ResolvableType.forField(ResolvableTestExample.class.getDeclaredField("myMap"));
		//
		System.out.println("解析它的父类 ： " + t.getSuperType()); // AbstractMap<Integer, List<String>>
		System.out.println("as map 明确这是一个Map : " + t.asMap()); // Map<Integer, List<String>>
		System.out.println(t.getGeneric(0).resolve()); // Integer
		System.out.println(t.getGeneric(1).resolve()); // List
		System.out.println(t.getGeneric(1)); // List<String>
		System.out.println(t.resolveGeneric(1, 0)); // String

	}

	static class ResolvableTestExample {

		private HashMap<Integer, List<String>> myMap;

	}
}

package com.framework.example.source.code.learn.beans;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

/**
 * 名字需要重新改，暂时先这样
 * <p>
 * Created  on 2023/9/23 16:16:13
 *
 * @author zl
 */
class PropertyEditorTest {

	@Test
	@DisplayName("测试")
	void testCustomerPropertyEditor() {

		// 模拟 Spring Framework 操作
		// 有一段文本 name = 牛马;
		String text = "name = 牛马";

		PropertyEditor propertyEditor = new StringToPropertiesPropertyEditor();
		// 传递 String 类型的内容
		propertyEditor.setAsText(text);

		System.out.println(propertyEditor.getValue());

		System.out.println(propertyEditor.getAsText());
	}

	static class StringToPropertiesPropertyEditor extends PropertyEditorSupport implements PropertyEditor {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			//			super.setAsText(text);

			// 2. 将 String 类型转换成 Properties 类型
			Properties properties = new Properties();
			try {
				properties.load(new StringReader(text));
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}

			// 3. 临时存储 Properties 对象
			setValue(properties);

			// next 获取临时 Properties 对象 #getValue();

		}

		/**
		 * 改变格式，从原本的toString（） 改成自己的strBuilder append
		 */
		@Override
		public String getAsText() {
			Properties properties = (Properties) getValue();

			StringBuilder textBuilder = new StringBuilder();

			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				textBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(System.getProperty("line.separator"));
			}

			return textBuilder.toString();
		}
	}

}

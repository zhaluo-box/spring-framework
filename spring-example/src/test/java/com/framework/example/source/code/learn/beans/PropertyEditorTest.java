package com.framework.example.source.code.learn.beans;

import com.framework.example.common.entity.LifeCyclePreview;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.TypeConverterDelegate;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.awt.*;
import java.beans.PropertyChangeListener;
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
public class PropertyEditorTest {

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

	/**
	 * note:propertyEditor 注册了也没生效
	 * <p>
	 * 通过find usage 进行推断代码
	 *
	 * @see BeanWrapperImpl#convertForProperty(Object, String)
	 * @see TypeConverterDelegate#convertIfNecessary(String, Object, Object, Class, TypeDescriptor)
	 * @see AbstractBeanFactory#getTypeConverter()
	 * @see AbstractBeanFactory#setConversionService(ConversionService) 查看这个方法调用的地方
	 * @see AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory) 约定了 conversionService 的ID名称
	 */
	@Test
	@DisplayName("自定义PropertyEditorRegistrar 测试")
	void customPropertyEditorRegistrarTest() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
		reader.loadBeanDefinitions("META-INF/property-editor-context.xml");

		//		context.register(CustomPropertyEditorRegistrar.class);  propertyEditor 注册了也没生效
		context.refresh();

		LifeCyclePreview lifeCyclePreview = context.getBean("lifeCyclePreview", LifeCyclePreview.class);
		System.out.println("lifeCyclePreview = " + lifeCyclePreview);

		context.close();
	}

	public static class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

		@Override
		public void registerCustomEditors(PropertyEditorRegistry registry) {
			registry.registerCustomEditor(LifeCyclePreview.class, "context", new StringToPropertiesPropertyEditor());
		}
	}

	public static class StringToPropertiesPropertyEditor extends PropertyEditorSupport implements PropertyEditor {

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

		@Override
		public Object getValue() {
			return super.getValue();
		}

		@Override
		public Object getSource() {
			return super.getSource();
		}

		@Override
		public void firePropertyChange() {
			super.firePropertyChange();
		}

		public StringToPropertiesPropertyEditor() {
			super();
		}

		public StringToPropertiesPropertyEditor(Object source) {
			super(source);
		}

		@Override
		public void setSource(Object source) {
			super.setSource(source);
		}

		@Override
		public void setValue(Object value) {
			super.setValue(value);
		}

		@Override
		public boolean isPaintable() {
			return super.isPaintable();
		}

		@Override
		public void paintValue(Graphics gfx, Rectangle box) {
			super.paintValue(gfx, box);
		}

		@Override
		public String getJavaInitializationString() {
			return super.getJavaInitializationString();
		}

		@Override
		public String[] getTags() {
			return super.getTags();
		}

		@Override
		public Component getCustomEditor() {
			return super.getCustomEditor();
		}

		@Override
		public boolean supportsCustomEditor() {
			return super.supportsCustomEditor();
		}

		@Override
		public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
			super.addPropertyChangeListener(listener);
		}

		@Override
		public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
			super.removePropertyChangeListener(listener);
		}
	}

}

package com.framework.example.source.code.learn.resource;

import org.apache.logging.log4j.core.util.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ApplicationContextAwareProcessor;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * {@link AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)}
 * 中体现了注入 abstractApplication & ResourceLoader 是同一个对象
 * {@link ApplicationContextAwareProcessor#invokeAwareInterfaces(Object)} 这里也体现了注入的是同一个对象
 * Created  on 2023/9/7 11:11:28
 *
 * @author zl
 */
public class ResourceLoaderTest {

	/**
	 * ResourceLoader 是 resource 的更上一层包装
	 *
	 * @see org.springframework.core.io.ResourceLoader
	 */
	@Test
	@DisplayName("测试ResourceLoader#getResource()")
	public void testGetResource() {

		String currentJavaFilePath = System.getProperty("user.dir") + "/src/test/java/com/framework/example/source/code/learn/resource/ResourceLoaderTest.java";
		FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
		Resource resource = fileSystemResourceLoader.getResource(currentJavaFilePath);

		EncodedResource encodedResource = new EncodedResource(resource, StandardCharsets.UTF_8);

		try (Reader reader = encodedResource.getReader()) {
			System.out.println(IOUtils.toString(reader));
		} catch (IOException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}

	}
}

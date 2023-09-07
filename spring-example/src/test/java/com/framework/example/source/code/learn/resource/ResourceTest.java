package com.framework.example.source.code.learn.resource;

import org.apache.logging.log4j.core.util.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * Created  on 2023/9/7 11:11:28
 *
 * @author zl
 */
public class ResourceTest {

	/**
	 * System.getProperty("user.dir") 在main & test 下结果不一致
	 */
	@Test
	public void testUserDir() {
		System.out.println("System.getProperty(\"user.dir\") = " + System.getProperty("user.dir"));
	}

	/**
	 * @see org.springframework.core.io.FileSystemResource
	 */
	@Test
	@DisplayName("展示从文件系统获取资源")
	public void testFileSystemResource() throws IOException {

		String currentJavaFilePath = System.getProperty("user.dir") + "/src/test/java/com/framework/example/source/code/learn/resource/ResourceTest.java";
		FileSystemResource fileSystemResource = new FileSystemResource(currentJavaFilePath);
		EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");
		// 字符输入流
		try (Reader reader = encodedResource.getReader()) {
			System.out.println(IOUtils.toString(reader));
		}

	}
}

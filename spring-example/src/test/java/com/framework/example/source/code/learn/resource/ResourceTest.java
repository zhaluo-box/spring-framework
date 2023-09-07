package com.framework.example.source.code.learn.resource;

import org.apache.logging.log4j.core.util.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author zl
 * @see org.springframework.core.io.ResourceTests
 * <p>
 * Created  on 2023/9/7 11:11:28
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

	/**
	 * FileUrlResource 会自动拼接file://
	 */
	@Test
	@DisplayName("File URL protocol test")
	public void testFileURlSystemResource() throws IOException {
		String currentJavaFilePath = System.getProperty("user.dir") + "/src/test/java/com/framework/example/source/code/learn/resource/ResourceTest.java";
		FileUrlResource fileUrlResource = new FileUrlResource(currentJavaFilePath);
		EncodedResource encodedResource = new EncodedResource(fileUrlResource, StandardCharsets.UTF_8);
		// 字符输入流
		try (Reader reader = encodedResource.getReader()) {
			System.out.println(IOUtils.toString(reader));
		}
	}

	@Test
	@DisplayName("testUrlResource")
	public void testUrlResource() throws IOException {

		String currentJavaFilePath = System.getProperty("user.dir") + "/src/test/java/com/framework/example/source/code/learn/resource/ResourceTest.java";

		UrlResource urlResource = new UrlResource(ResourceUtils.URL_PROTOCOL_FILE, currentJavaFilePath);
		EncodedResource encodedResource = new EncodedResource(urlResource, StandardCharsets.UTF_8);
		// 字符输入流
		try (Reader reader = encodedResource.getReader()) {
			System.out.println(IOUtils.toString(reader));
		}
	}

	@Test
	@DisplayName("url protocol assert")
	public void testURlProtocol() throws MalformedURLException {

		URL url = new URL("jar:file:app.jar!/");
		System.out.println("url.getProtocol() = " + url.getProtocol());

	}
}

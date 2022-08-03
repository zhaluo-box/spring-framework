package com.example.mvc.demo.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created  on 2022/7/31 15:15:41
 *
 * @author zl
 */

public class StartWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * springContext 中相关的配置类
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { SpringRootConfig.class };
	}

	/**
	 * dispatchServlet 中上下文相关的Bean
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { MVCConfig.class };
	}

	/**
	 * 请求路径映射
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * 过滤器
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { characterEncodingFilter };
	}

}

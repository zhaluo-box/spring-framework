package com.example.spring.learn.config.importannotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

/**
 *
 * @see AbstractApplicationContext#refresh()
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
 * @see org.springframework.context.annotation.ImportSelector
 */
public class CustomImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[0];
	}
}

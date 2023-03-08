package com.framework.example.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * 继承 {@link Autowired}
 * Created  on 2023/3/7 10:10:34
 *
 * @author zl
 */
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Autowired
public @interface MyAutowire {
}

package com.framework.example.annotation;

import java.lang.annotation.*;

/**
 * Created  on 2023/3/7 10:10:35
 *
 * @author zl
 */
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyInject {
}

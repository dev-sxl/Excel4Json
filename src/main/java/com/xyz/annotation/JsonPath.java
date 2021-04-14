package com.xyz.annotation;

import java.lang.annotation.*;

/**
 * @author sxl
 * @since 2021/4/14 13:47
 */
@Inherited
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonPath {

    String[] path();

}

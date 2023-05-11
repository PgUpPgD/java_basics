package com.example.excel.reflect.getWay;

import java.lang.annotation.*;

/**
 * API
 *
 * @version 1.0
 * Date: 2020/11/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Documented
public @interface API {

    /**
     * 接口编号
     */
    String code();

    /**
     * 接口描述
     */
    String description() default "";

}

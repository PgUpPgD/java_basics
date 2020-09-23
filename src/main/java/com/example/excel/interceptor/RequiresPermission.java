package com.example.excel.interceptor;
import java.lang.annotation.*;

/**
 * @author HK
 * @date 2020-09-03 9:21
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {
    String value() default "";
}

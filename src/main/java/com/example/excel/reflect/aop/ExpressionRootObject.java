package com.example.excel.reflect.aop;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: tangHC
 * @Date: 2023/5/8 18:52
 */
public class ExpressionRootObject {
    private final Method method;
    private final Object[] args;
    private final Object target;
    private final Class<?> targetClass;

    public ExpressionRootObject(Method method, Object[] args, Object target, Class<?> targetClass) {
        Assert.notNull(method, "Method is required");
        Assert.notNull(targetClass, "targetClass is required");
        this.method = method;
        this.target = target;
        this.targetClass = targetClass;
        this.args = args;
    }

    public Method getMethod() {
        return this.method;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public Object getTarget() {
        return this.target;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }
}

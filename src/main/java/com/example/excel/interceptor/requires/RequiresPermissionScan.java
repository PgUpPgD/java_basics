package com.example.excel.interceptor.requires;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  ContextAware
    实现了这个接口的bean，当spring容器初始化的时候，会自动的将ApplicationContext注入进来
    并执行setApplicationContext方法
 */
@Component
public class RequiresPermissionScan implements ApplicationContextAware {
    private List<String> mPaths = new ArrayList<>();

    public List<String> paths() {
        return mPaths;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //类上被@RestController标记的
        Map<String, Object> restControllers = applicationContext.getBeansWithAnnotation(RestController.class);
        for (Map.Entry<String, Object> kv : restControllers.entrySet()) {
            Class<?> clazz = kv.getValue().getClass();

            //类上被@RequestMapping注解标记的
            RequestMapping rootRequestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
            if (rootRequestMapping == null) {
                continue;
            }
            //类上被@RequiresPermission注解标记的
            RequiresPermission requiresPermission = AnnotationUtils.findAnnotation(clazz, RequiresPermission.class);
            if (requiresPermission != null) {
                String fullPath = "/" + rootRequestMapping.value()[0] + "/**";
                mPaths.add(fullPath);
            }

            Method[] methods = clazz.getMethods();

            for (Method method : methods) {
                //方法上被相关注解标记的
                requiresPermission = AnnotationUtils.findAnnotation(method, RequiresPermission.class);
                if (requiresPermission != null) {
                    RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                    if (requestMapping != null) {
                        String fullPath = "/" + rootRequestMapping.value()[0] + "/" + requestMapping.value()[0];
                        mPaths.add(fullPath);
                    }
                }
            }
        }
    }


}

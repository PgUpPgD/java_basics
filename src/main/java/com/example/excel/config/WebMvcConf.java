package com.example.excel.config;

import com.example.excel.interceptor.SourceInterceptor;
import com.example.excel.interceptor.requires.PermissionInterceptor;
import com.example.excel.interceptor.requires.RequiresPermissionScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConf implements WebMvcConfigurer {

    @Autowired
    private SourceInterceptor sourceAccessInterceptor;

    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Autowired
    private RequiresPermissionScan requiresPermissionScan;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //被@LoginRequired注解的拦截器         一般方式
        registry.addInterceptor(sourceAccessInterceptor).addPathPatterns("/**");

        //被@RequiresPermission注解的拦截器        进阶版
        if(requiresPermissionScan.paths() != null && requiresPermissionScan.paths().size() > 0) {
            //                要注册的拦截器                   拦截的匹配路径
            registry.addInterceptor(permissionInterceptor).addPathPatterns(requiresPermissionScan.paths());
        }
    }
}

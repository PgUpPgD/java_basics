package com.example.excel.config;

import com.example.excel.interceptor.RequiresPermissionScan;
import com.example.excel.interceptor.SourceAccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorTrainConfigurer implements WebMvcConfigurer {

    @Autowired   //配置拦截规则
    private SourceAccessInterceptor sourceAccessInterceptor;

    @Autowired   //获取要被拦截的路径
    private RequiresPermissionScan requiresPermissionScan;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new SourceAccessInterceptor()).addPathPatterns("/**");
        if(requiresPermissionScan.paths() != null && requiresPermissionScan.paths().size() > 0) {
            //                要注册的拦截器                   拦截的匹配路径
            registry.addInterceptor(sourceAccessInterceptor).addPathPatterns(requiresPermissionScan.paths());
        }
    }
}

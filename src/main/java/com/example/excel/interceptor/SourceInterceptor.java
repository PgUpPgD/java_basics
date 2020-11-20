package com.example.excel.interceptor;

import com.example.excel.annotation.LoginRequired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring MVC应用启动时会搜集并分析每个Web控制器方法，从中提取对应 "<请求匹配条件,控制器方法>" 的映射关系，
 * 形成一个映射关系表保存在一个RequestMappingHandlerMapping bean中。
 * 然后在客户请求到达时，再使用RequestMappingHandlerMapping中的该映射关系表找到相应的控制器方法去处理该请求。
 * 在RequestMappingHandlerMapping中保存的每个 "<请求匹配条件,控制器方法>" 映射关系对儿中,
 * "请求匹配条件"通过RequestMappingInfo包装和表示，
 * 而"控制器方法"则通过 HandlerMethod 来包装和表示。
 */
@Component
public class SourceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("preHandle");
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            LoginRequired annotation = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
            if (annotation == null){
                return true;
            }else {
                // 有LoginRequired注解说明需要登录，提示用户登录
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().print("你访问的资源需要登录");
                return false;
            }
        }else if (handler instanceof ResourceHttpRequestHandler){
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("afterCompletion");
    }
}

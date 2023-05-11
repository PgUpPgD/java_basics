package com.example.excel.reflect.getWay;

import com.alibaba.fastjson.JSONObject;
import com.example.excel.reflect.aop.AopHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: tangHC
 * @Date: 2023/5/8 18:44
 */
@Slf4j
@RestController
@RequestMapping(value = "", produces = {"application/json;charset=UTF-8", "application/xml", "text/plain"})
public class DispatcherController implements ApplicationContextAware, InitializingBean {

    @Setter
    private ApplicationContext applicationContext;

    private final Map<String, Triple<Class<?>, Method, Object>> apiMap = new HashMap<>();


    @RequestMapping(value = {"/dispatch"})
    public ApiResponse dispatchAPI(@RequestParam(required = false) String transNo, @RequestBody JSONObject params)
            throws InvocationTargetException, IllegalAccessException {
        final String transCode = params.getString("transCode");
        final Triple<Class<?>, Method, Object> triple = apiMap.get(transCode);
        if (triple == null) {
            return ApiResponse.of(NoDataApiResponse.class, params.toJavaObject(ApiRequest.class), "404", "接口不存在");
        }
        final Method method = triple.getMiddle();
        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        final Object[] parameters = new Object[genericParameterTypes.length];
        for (int i = 0; i < genericParameterTypes.length; i++) {
            if (i == 0) {
                parameters[i] = params.toJavaObject(genericParameterTypes[i]);
            } else {
                parameters[i] = null;
            }
        }
        return (ApiResponse) method.invoke(triple.getRight(), parameters);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(API.class);
        beansWithAnnotation.forEach((k, v) -> {
            final Class<?> targetClass = AopHelper.getProxyTargetClass(v);
            Class<?>[] interfaces = targetClass.getInterfaces();
            while (interfaces.length > 0) {
                List<Class<?>> list = new ArrayList<>();
                for (Class<?> face : interfaces) {
                    if (!face.isAnnotationPresent(API.class)) {
                        continue;
                    }
                    // 处理接口
                    for (Method method : face.getDeclaredMethods()) {
                        if (!method.isAnnotationPresent(API.class)) {
                            continue;
                        }
                        final API api = method.getAnnotation(API.class);
                        apiMap.put(api.code(), Triple.of(targetClass, method, v));
                        log.info("接口定义:code={},description={},location={}#{},target={}",
                                api.code(), api.description(), face.getName(), method.getName(), targetClass.getName());
                    }
                    final Class<?>[] superFaces = face.getInterfaces();
                    if (superFaces.length > 0) {
                        for (Class<?> superFace : superFaces) {
                            if (!superFace.isAnnotationPresent(API.class)) {
                                continue;
                            }
                            list.add(superFace);
                        }
                    }
                }
                if (list.isEmpty()) {
                    break;
                }
                Class<?>[] superInterfaces = new Class<?>[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    superInterfaces[i] = list.get(i);
                }
                interfaces = superInterfaces;
            }
        });
    }

}















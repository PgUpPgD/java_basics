package com.example.excel.helper;

import com.alibaba.fastjson.JSON;
import com.example.excel.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class JsonHelper {

    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T json2Bean(String json, Class<T> clazz) throws Exception{
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <K, V> Map<K, V> json2Map(String json, Class<K> k, Class<V> v) {
        try {
            JavaType jvt = objectMapper.getTypeFactory().constructParametricType(HashMap.class, k, v);
            Map<K, V> urMap = objectMapper.readValue(json, jvt);
            System.out.println(urMap);
            return urMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> json2List(String str, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            return objectMapper.readValue(str, javaType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bean2json(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static void main(String[] args) throws Exception{
        User user = new User("id", "name", "userinfo");
        String str = JSON.toJSONString(user);
        User bean = json2Bean(str, User.class);
        System.out.println(bean);

        HashMap<String, Object> map = new HashMap<>();
        map.put("id",bean.getUserId());
        map.put("name",bean.getName());
        String str1 = JSON.toJSONString(map);
        Map<String, Object> map1 = json2Map(str1, String.class, Object.class);

        List<String> list = Arrays.asList("a", "b", "c", "d");
        String str2 = JSON.toJSONString(list);
        List<String> list1 = json2List(str2, String.class);
        System.out.println(list1);

        Integer str3 = 30;
        String str4 = bean2json(str3);
        System.out.println(str4);
    }

}

package com.example.excel.helper;

import com.example.excel.entity.User;
import com.example.excel.utils.ServletUtils;

import javax.servlet.http.HttpSession;

public class UserHelper {

    public static String getToken(){
        return ServletUtils.getRequest().getHeader("token");
    }

    //一般登录后存储当前用户的信息  注;多个用户之间的信息是否覆盖或者混淆有待测试
    public static void updateUserInfoToSession(User user){
        HttpSession session = ServletUtils.getSession();
        session.setAttribute("userInfo", user);
    }

    public static User getUser() {
        return (User) ServletUtils.getSession().getAttribute("userInfo");
    }

    public static String getUserId(){
        User user = getUser();
        return user == null ? null : user.getUserId();
    }

    public static String parseUserCacheKey(String userId, String source) {
        StringBuilder sb = new StringBuilder();
        sb.append(RedisKeyHelper.VIPKEY).append(userId).append(":").append(source);
        return sb.toString();
    }

}

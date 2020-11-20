package com.example.excel.interceptor.requires;

import com.example.excel.config.R;
import com.example.excel.helper.JsonHelper;
import com.example.excel.helper.UserHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = UserHelper.getToken();
        if (StringUtils.isEmpty(token)) {
            writeJsonBean(request, response, R.error(410, "请登录后，再进行操作"));
            return false;
        }

//        TokenBean bean = Decode.decryptToken(token);
//        if (bean == null || StringUtils.isEmpty(bean.getToken())) {
//            writeJsonBean(request, response, R.error(CodeMsgUtil.code3000, "请登录后，再进行操作"));
//            return false;
//        }
//        String data = RedisManager.getStr(UserHelper.parseUserCacheKey(bean.getToken(), bean.getSource()));
//        LoginUserInfo userInfo = JsonHelper.json2Bean(data, LoginUserInfo.class);
//        if (userInfo == null || token.equals(userInfo.getToken()) == false) {
//            writeJsonBean(request, response, R.error(CodeMsgUtil.code3000, "请登录后，再进行操作"));
//            return false;
//        }
        return true;
    }

    private void writeJsonBean(HttpServletRequest request, HttpServletResponse response, Object data) throws Exception {
        String json = JsonHelper.bean2json(data);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
    }

}



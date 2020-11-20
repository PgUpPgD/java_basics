package com.example.excel.servlet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CookieTest {

    /*
    1. 概念：客户端会话技术，将数据保存到客户端
    2. 实现原理
		基于响应头set-cookie和请求头cookie实现
    3. 使用步骤
		1. 创建Cookie对象，绑定数据
			new Cookie(String name, String value)
		2. （响应)发送Cookie对象到浏览器端保存
			response.addCookie(Cookie cookie)
		3. 设置cookie
        	void setPath(java.lang.String uri)   ：设置cookie的有效访问路径
        	void setMaxAge(int expiry) ： 设置cookie的有效时间
        	void setValue(java.lang.String newValue) ：设置cookie的值
		4. 服务器接收cookie，拿到数据
			Cookie[]  request.getCookies()
    4.cookie的细节
       	1. void setPath(java.lang.String uri)：设置cookie的有效访问路径。
       			有效路径指的是cookie的有效路径保存在哪里，那么浏览器在有效路径下访问服务器时就会带着cookie信息，否则不带cookie信息。
		2. void setMaxAge(int expiry) ： 设置cookie的有效时间。
				正整数：表示cookie数据保存浏览器的缓存目录（硬盘中），数值表示保存的时间。
				负整数：表示cookie数据保存浏览器的内存中。浏览器关闭cookie就丢失了！！
				零：表示删除同名的cookie数据
		3. 可以保存多个cookie，但是浏览器一般只允许存放300个站点，每个站点最多存放20个Cookie，每个Cookie的大小限制为4KB。
		4. 在tomcat 8 之前 cookie中不能直接存储中文数据。
		   在tomcat 8 之后，cookie支持中文数据。特殊字符还是不支持，建议使用URL编码存储，URL解码解析,URLEncoder.encode(String value,"utf-8");
     */

    @RequestMapping("/cookie")
    public String cookie(HttpServletRequest request, HttpServletResponse response){
        //获取所有cookie
//        String cookie = request.getHeader("Cookie");
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies){
            System.out.println(c.getName() + ":" + c.getValue());
        }

        //返回cookie   response返回乱码可设置字符集
//        response.setHeader("Set-Cookie", "name=jack&age=18");
        Cookie cookie = new Cookie("jack", "18");
        //设置有效路径  访问路径不匹配时，获取不到当前添加的新cookie
//        cookie.setPath("/cookie/");
        //设置有效时间
//        cookie.setMaxAge(10);
        response.addCookie(cookie);
        return "cookie访问成功";
    }

}

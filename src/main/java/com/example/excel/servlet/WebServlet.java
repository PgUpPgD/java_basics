package com.example.excel.servlet;

import com.example.excel.reqMsg.ReqMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@ResponseBody
public class WebServlet {

    /*
    概念：Hyper Text Transfer Protocol 超文本传输协议(HTTP)
	传输协议：对浏览器客户端 和  服务器端 之间数据传输的格式规范
    1. request和response对象是由服务器创建的。我们来使用它们
	2. request对象是来获取请求消息，response对象是来设置响应消息
    所有域对象：（域范围从大到小）
				ServletContext		域对象
				HttpSession 		域对象
                HttpServletRequet	域对象
				PageContext			域对象
     */
    @RequestMapping("/servlet")
    public String test(HttpServletRequest request, ReqMsg req){
        //通过头名称获取对应的值
        String host = request.getHeader("host");
        //获取的所有头名称
        Enumeration<String> headeNames = request.getHeaderNames();
        //遍历
        while(headeNames.hasMoreElements()){
            //头名称
            String name = headeNames.nextElement();
            //获取值
            String value = request.getHeader(name);

            System.out.println(name + "=" + value);
        }
        //请求行
        System.out.println("请求路径URL:" + request.getRequestURL());
        System.out.println("请求路径URI:" + request.getRequestURI());

        System.out.println("请求方式：" + request.getMethod());
        System.out.println("协议：" + request.getProtocol());
        System.out.println("当前web应用的路径：" + request.getContextPath());
        return "servlet请求成功";
    }
}

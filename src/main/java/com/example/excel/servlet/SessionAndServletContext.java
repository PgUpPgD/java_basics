package com.example.excel.servlet;

import com.example.excel.reqMsg.ReqMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class SessionAndServletContext {

    /*
    1）创建或得到session对象
        HttpSession getSession()
        HttpSession getSession(boolean create)
	2）设置session对象
        void setMaxInactiveInterval(int interval)  ： 设置session的有效时间
        void invalidate()     ： 销毁session对象
        java.lang.String getId()  ： 得到session编号
	3）保存会话数据到session对象
		void setAttribute(java.lang.String name, java.lang.Object value)  ： 保存数据
		java.lang.Object getAttribute(java.lang.String name)  ： 获取数据
		void removeAttribute(java.lang.String name) ： 清除数据

	2、 ServletContext域对象：作用范围在整个web应用中有效！！！
		ServletContext域对象：数据保存在服务器中。
		保存数据： void setAttribute(java.lang.String name, java.lang.Object object)
		获取数据： java.lang.Object getAttribute(java.lang.String name)
		删除数据： void removeAttribute(java.lang.String name)
     */
    @RequestMapping("/session")
    public void test(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();

        //得到session编号
        String id = session.getId();
        System.out.println("session编号" + id);

        ReqMsg msg = new ReqMsg();
        msg.setAge(18);
        msg.setName("tom");
        session.setAttribute("name", msg);
        session.setMaxInactiveInterval(45);  //有效时间

        //ServletContext ：代表整个web应用，可以和程序的容器(服务器)来通信
        ServletContext context = request.getServletContext();
        context.setAttribute("age", msg);

        try {
//            response.getWriter().write("访问session成功");
            response.sendRedirect("/servletContext"); //重定向
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/servletContext")
    public String test1(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        //得到session编号
        String id = session.getId();
        System.out.println("session编号" + id);
        ReqMsg name = (ReqMsg)session.getAttribute("name");
        System.out.println("Session" + name);

        ServletContext context = request.getServletContext();
        ReqMsg age = (ReqMsg)context.getAttribute("age");
        System.out.println("ServletContext:" + age);

        //销毁信息
        session.invalidate();
        context.removeAttribute("age");
//        try {     //转发
//            request.getRequestDispatcher("/back").forward(request,response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "/back";
    }

    @ResponseBody
    @RequestMapping("/back")
    public String test2(){
        return "转发成功";
    }

}

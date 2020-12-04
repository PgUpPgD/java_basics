package com.example.excel.designMode.mediatorPattern;

import java.util.Date;

//步骤 1
//创建中介类。
public class ChatRoom {
    public static void showMessage(User user, String message){
        System.out.println(new Date().toString()
                + " [" + user.getName() +"] : " + message);
    }
}

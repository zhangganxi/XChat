package com.itheima.xchat.view;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.view
 *  @文件名:   LoginView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/19 16:41
 *  @描述：    TODO
 */

public interface LoginView {
    void onUserNameError();//登录时用户名输入非法

    void onPasswordError();//登录时密码输入非法

    void onStartLogin();//开始登录的UI操作

    void onLoginSuccess();//登录成功之后UI操作

    void onLoginFailed();//登录失败之后UI操作
}

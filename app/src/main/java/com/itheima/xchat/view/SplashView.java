package com.itheima.xchat.view;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.view
 *  @文件名:   SplashView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/19 15:35
 *  @描述：    TODO
 */
public interface SplashView {

    //登录之后的操作
    void onLoggedIn();

    //没有登录的操作
    void onNoLoggedIn();
}


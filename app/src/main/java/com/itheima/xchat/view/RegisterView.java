package com.itheima.xchat.view;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.view
 *  @文件名:   RegisterView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/28 20:52
 *  @描述：    TODO
 */
public interface RegisterView {

    void onUserNameError();//用户名输入非法ui操作

    void onPasswordError(); //密码输入非法的UI操作

    void onConfirmPassword(); //确认密码非法的UI操作

    void onRegisterSuccess(); //注册成功时候的UI操作

    void onRegisterFeiled(); //注册失败时候的UI操作

    void onUserAlreadyExist(); //用户名已存在的UI操作

    void onStartRegister();  //开始注册时候UI操作
}

package com.itheima.xchat.view;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.view
 *  @文件名:   ContactView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/8 13:30
 *  @描述：    TODO
 */

public interface ContactView {
    void onLoadContactSuccess(); //加载联系人成功之后UI操作

    void onLoadContactFailed(); //加载联系人失败之后的UI操作

    void delContactSuccess();  //删除联系人成功

    void delContactFailed(); //删除联系人失败
 }

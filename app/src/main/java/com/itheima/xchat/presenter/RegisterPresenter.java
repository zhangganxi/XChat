package com.itheima.xchat.presenter;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter
 *  @文件名:   RegisterPresenter
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/28 20:52
 *  @描述：    TODO
 */

public interface RegisterPresenter {

    void register(String username,String password,String confirmPassword);  //给View层调用的P注册的操作
}

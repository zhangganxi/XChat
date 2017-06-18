package com.itheima.xchat.presenter;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter
 *  @文件名:   ContactPresent
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/8 13:30
 *  @描述：    TODO
 */

public interface ContactPresent {

    void loadContacts();//加载数据

    void refresh(); //刷新数据

    void delContact(String username);//删除联系人

    void unBind();//解除绑定
}

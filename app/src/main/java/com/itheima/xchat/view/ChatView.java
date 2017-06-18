package com.itheima.xchat.view;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.view
 *  @文件名:   ChatView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/16 12:12
 *  @描述：    TODO
 */
public interface ChatView {

    void sendMessageSuccess(); //发送消息成功

    void sendMessageFailed();  //发送消息失败

    void onStartSendMessage();  //发送消息

    String getCurUsername();  //获取当前对话用户名

    void onReceiveMessage();  //UI获取到消息的操作

    void onInitMessages(); //初始化聊天记录

    void onLoadMoreMessages(int size); //加载更多的聊天记录
}

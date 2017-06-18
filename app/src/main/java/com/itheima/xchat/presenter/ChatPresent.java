package com.itheima.xchat.presenter;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter
 *  @文件名:   ChatPresent
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/16 12:14
 *  @描述：    TODO
 */

import com.hyphenate.chat.EMMessage;

import java.util.List;

public interface ChatPresent {
    void sendMessage(String username, String messageContent); //发送消息

    List<EMMessage> getMessage();  //获取消息liaobiao

    void removeListener();  //移除监听器

    void loadInitMessage(String username); //初始化消息界面;

    void loadMoreMessage(String username);  //加载更多聊天记录;
}

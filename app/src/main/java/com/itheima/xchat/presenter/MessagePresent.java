package com.itheima.xchat.presenter;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter
 *  @文件名:   MessagePresent
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/20 22:49
 *  @描述：    TODO
 */

import com.hyphenate.chat.EMConversation;

import java.util.List;

public interface MessagePresent {
    void loadMessageData(); //加载回话列表

    List<EMConversation> getMessages(); //获取对话列表

    void onDestroyListener();  //销毁监听者

    void unBind(); //解除绑定
}

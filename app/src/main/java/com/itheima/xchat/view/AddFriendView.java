package com.itheima.xchat.view;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.view
 *  @文件名:   AddFriendView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/12 22:13
 *  @描述：    TODO
 */

public interface AddFriendView {
    void searchSuccess(); //搜索成功

    void searchFailed(); //搜索失败

    void sendAddFriendRequestSuccess(); //发送添加好友申请成功

    void sendAddFriendRequestFailed(); //发送添加好友申请失败
}

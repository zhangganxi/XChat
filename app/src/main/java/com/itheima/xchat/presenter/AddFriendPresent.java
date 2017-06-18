package com.itheima.xchat.presenter;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter
 *  @文件名:   AddFriendPresent
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/12 22:14
 *  @描述：    TODO
 */

import com.itheima.xchat.model.AddFriendBean;

import java.util.List;

public interface AddFriendPresent {
    void search(String keyword); //搜索好友

    List<AddFriendBean> getSearchFriends();  //获取好友列表

    void onDestroy();//注销EventBus
}

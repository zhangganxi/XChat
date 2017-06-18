package com.itheima.xchat.event;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.event
 *  @文件名:   AddFriendEvent
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/15 19:00
 *  @描述：    TODO
 */
public class AddFriendEvent {
    private static final String TAG = "AddFriendEvent";

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    private String mUsername;

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }

    private String mReason;

    public AddFriendEvent(String username){
        this.mUsername = username;
    }

}

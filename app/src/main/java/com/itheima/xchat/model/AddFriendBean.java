package com.itheima.xchat.model;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.model
 *  @文件名:   AddFriendBean
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/13 22:28
 *  @描述：    TODO
 */
public class AddFriendBean {
    private static final String TAG = "AddFriendBean";
    private String username;

    private String registerTime;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    private boolean added;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
}

package com.itheima.xchat.model;

import cn.bmob.v3.BmobUser;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.model
 *  @文件名:   User
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/28 22:51
 *  @描述：    TODO
 */
public class User extends BmobUser {
    private static final String TAG = "User";

    public boolean isGander() {
        return gander;
    }

    public void setGander(boolean gander) {
        this.gander = gander;
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    private boolean gander;
    private int age;


    public User(String userName,String password){
        setUsername(userName);
        setPassword(password);
    }
}

package com.itheima.xchat.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.model
 *  @文件名:   Contact
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/15 16:05
 *  @描述：    TODO
 */
@Entity
public class Contact {
    private static final String TAG = "Contact";

    @Id
    public Long id;

    public String userName;

    @Generated(hash = 2041396140)
    public Contact(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Generated(hash = 672515148)
    public Contact() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

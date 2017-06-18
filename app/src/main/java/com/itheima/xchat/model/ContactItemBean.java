package com.itheima.xchat.model;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.model
 *  @文件名:   ContactItemBean
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/8 15:36
 *  @描述：    TODO
 */
public class ContactItemBean {
    private static final String TAG = "ContactItemBean";

    private String firstLetter;

    private String contactName;

    private boolean isShowFirstLetter;

    public boolean isShowFirstLetter() {
        return isShowFirstLetter;
    }

    public void setShowFirstLetter(boolean showFirstLetter) {
        isShowFirstLetter = showFirstLetter;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }




}

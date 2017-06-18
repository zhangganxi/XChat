package com.itheima.xchat.utils;

import android.content.Context;

import com.itheima.xchat.app.XChatApplication;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.utils
 *  @文件名:   UIUtils
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/2 13:13
 *  @描述：    TODO
 */
public class UIUtils {
    private static final String TAG = "UIUtils";

    public static Context getContext(){
        return XChatApplication.getContext();
    }
}

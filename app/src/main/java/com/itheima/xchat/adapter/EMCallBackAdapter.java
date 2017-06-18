package com.itheima.xchat.adapter;

import com.hyphenate.EMCallBack;
import com.itheima.xchat.utils.ThreadUtils;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.adapter
 *  @文件名:   EMCallBackAdapter
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/31 22:11
 *  @描述：    TODO
 */
public class EMCallBackAdapter implements EMCallBack {
    private static final String TAG = "EMCallBackAdapter";

    @Override
    public void onSuccess() {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onSuccessOperational();
            }
        });
    }

    @Override
    public void onError(int code, String error) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onErrorOperational();
            }
        });
    }

    @Override
    public void onProgress(int progress, String status) {

    }

    public void onSuccessOperational() {

    }

    public void onErrorOperational() {

    }
}

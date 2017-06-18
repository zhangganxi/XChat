package com.itheima.xchat.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.utils
 *  @文件名:   ThreadUtils
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/29 22:33
 *  @描述：    TODO
 */
public class ThreadUtils {
    private static final String TAG = "ThreadUtils";

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private static Executor mExecutor = Executors.newSingleThreadExecutor();

    public static void runOnBackgoundThread(Runnable runnable){
        mExecutor.execute(runnable);
    }

    public static void runOnMainThread(Runnable runnable){
        mHandler.post(runnable);
    }
}

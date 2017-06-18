package com.itheima.xchat.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.itheima.xchat.presenter.SplashPresenter;
import com.itheima.xchat.view.SplashView;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter.impl
 *  @文件名:   SplashPresenterImpl
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/19 15:42
 *  @描述：    TODO
 */
public class SplashPresenterImpl implements SplashPresenter {
    private static final String TAG = "SplashPresenterImpl";

    SplashView mSplashView;
    /*因为是MVP架构,V层和P层都相互持有对方的一个对象引用;
        通过构造方法,将View传过来
    * */
    public SplashPresenterImpl(SplashView splashView){
        this.mSplashView = splashView;
    }

    /*检查用户登录状态
    * */
    @Override
    public void checkLoginStatus() {
        if (isLoggedIn()){
            mSplashView.onLoggedIn();
        }else {
            mSplashView.onNoLoggedIn();
        }
    }

    /*判断是否是登录和在线
    * */
    private boolean isLoggedIn() {
        //判断用户是否登录状态并且判断是否是连接状态

        return EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected();

    }
}

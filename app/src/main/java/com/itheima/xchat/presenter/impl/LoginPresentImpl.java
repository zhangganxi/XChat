package com.itheima.xchat.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.itheima.xchat.adapter.EMCallBackAdapter;
import com.itheima.xchat.presenter.LoginPresent;
import com.itheima.xchat.utils.StringUtil;
import com.itheima.xchat.view.LoginView;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter.impl
 *  @文件名:   LoginPresentImpl
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/19 16:42
 *  @描述：    TODO
 */
public class LoginPresentImpl implements LoginPresent {
    private static final String TAG = "LoginPresentImpl";
    private LoginView mLoginView;

    public LoginPresentImpl(LoginView loginView){
        this.mLoginView = loginView;
    }

    @Override
    public void login(String username, String password) {
        if (StringUtil.isValidUserName(username)){
            if (StringUtil.isValidPassword(password)){
                //登录环信
                mLoginView.onStartLogin();
                EMClient.getInstance().login(username,password,mEMCallback);
            }else{
                mLoginView.onPasswordError();
            }
        }else{
            mLoginView.onUserNameError();
        }
    }

    //环信login的回调
    private EMCallBackAdapter mEMCallback = new EMCallBackAdapter() {

        @Override
        public void onSuccessOperational() {
            super.onSuccessOperational();
            mLoginView.onLoginSuccess();
        }

        @Override
        public void onErrorOperational() {
            super.onErrorOperational();
            mLoginView.onLoginFailed();
        }
    };
}

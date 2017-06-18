package com.itheima.xchat.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.itheima.xchat.model.User;
import com.itheima.xchat.presenter.RegisterPresenter;
import com.itheima.xchat.utils.Constants;
import com.itheima.xchat.utils.StringUtil;
import com.itheima.xchat.utils.ThreadUtils;
import com.itheima.xchat.view.RegisterView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter.impl
 *  @文件名:   RegisterPresentImpl
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/28 20:54
 *  @描述：    TODO
 */
public class RegisterPresentImpl implements RegisterPresenter {
    private static final String TAG = "RegisterPresentImpl";
    private RegisterView mRegisterView;

    public RegisterPresentImpl(RegisterView registerView){
        this.mRegisterView = registerView;
    }

    @Override
    public void register(String username, String password, String confirmPassword) {
        //判断用户名是否合法
        if (StringUtil.isValidUserName(username)){
            //判断密码是否合法
            if (StringUtil.isValidPassword(password)){
                //判断确认密码是否与密码一致
                if (password.equals(confirmPassword)){
                    //注册的时候弹出进度对话框
                    mRegisterView.onStartRegister();
                    registerBmob(username,password);
                }else{
                    mRegisterView.onConfirmPassword();
                }
            }else{
                mRegisterView.onPasswordError();
            }
        }else{
            //用户名不合法交给view层处理uI
            mRegisterView.onUserNameError();
        }
    }

    private void registerBmob(final String username, final String password) {
        User user = new User(username,password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    //null表示没有异常,注册成功
                    //注册环信
                    registerEaseMob(username,password);
                }else{
                    if (e.getErrorCode() == Constants.USER_ALREADY_EXIST){
                        mRegisterView.onUserAlreadyExist();
                    }else{
                        mRegisterView.onRegisterFeiled();
                    }
                }
            }
        });
    }

    private void registerEaseMob(final String username, final String password) {
        ThreadUtils.runOnBackgoundThread(new Runnable() {
            @Override
            public void run() {
                //注册失败会抛出HyphenateException
                try {
                    EMClient.getInstance().createAccount(username, password);//同步方法
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onRegisterSuccess();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onRegisterFeiled();
                        }
                    });
                }
            }
        });
    }
}

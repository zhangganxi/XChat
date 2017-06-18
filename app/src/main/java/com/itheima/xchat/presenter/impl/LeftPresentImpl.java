package com.itheima.xchat.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.itheima.xchat.adapter.EMCallBackAdapter;
import com.itheima.xchat.presenter.LeftPresenter;
import com.itheima.xchat.ui.fragment.LeftFragment;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter.impl
 *  @文件名:   LeftPresentImpl
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/6 22:44
 *  @描述：    TODO
 */
public class LeftPresentImpl implements LeftPresenter {
    private static final String TAG = "LeftPresentImpl";
    private LeftFragment mLeftFragment;

    public LeftPresentImpl(LeftFragment leftFragment) {
        this.mLeftFragment = leftFragment;
    }


    @Override
    public void logout() {
        mLeftFragment.startLogout();
        EMClient.getInstance().logout(true,mEMCallBackAdapter);
    }

    private EMCallBackAdapter mEMCallBackAdapter = new EMCallBackAdapter(){
        @Override
        public void onSuccessOperational() {
            super.onSuccessOperational();
            mLeftFragment.hideProgressDialog();
            mLeftFragment.logoutSuccess();
        }

        @Override
        public void onErrorOperational() {
            super.onErrorOperational();
            mLeftFragment.hideProgressDialog();
        }
    };
}

package com.itheima.xchat.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.base
 *  @文件名:   BaseFragment
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/19 14:08
 *  @描述：    TODO
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private int mLayoutId;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this,root);
        init();
        return root;
    }

    /*给子类完成一些初始化操作
    * */
    protected void init() {

    }

    /*子类必须传布局
    * */
    public abstract int getLayoutId();

    public void gotoActivity(Class<? extends Activity> clazz){
       gotoActivity(clazz,true);
    };
     public void gotoActivity(Class<? extends Activity> clazz,boolean isFinish){
        Intent intent = new Intent(getActivity(),clazz);
        getActivity().startActivity(intent);
         if (isFinish) {
             getActivity().finish();
         }
    };

    public void gotoActivity(Class<? extends Activity> clazz,boolean isFinish,Intent intent){
        intent.setClass(getActivity(),clazz);
        getActivity().startActivity(intent);
        if (isFinish){
            getActivity().finish();
        }
    }


    //showProgressDialog
    public void showProgressDialog(String msg){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getContext());
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }
    //隐藏进度条对话框
    public void hideProgressDialog(){
        if (mProgressDialog != null){
            mProgressDialog.hide();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProgressDialog = null;
    }


}

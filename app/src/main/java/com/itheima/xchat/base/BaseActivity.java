package com.itheima.xchat.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.base
 *  @文件名:   BaseActivity
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/19 13:47
 *  @描述：    TODO
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static final long DEFAULE_TIME = 2000;
    private int mLayoutId;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ProgressDialog mProgressDialog;
    private InputMethodManager mInputMethodManager;
    public boolean isShowSoftInput = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //使用ButterKnige
        ButterKnife.bind(this);
        init();
    }

    /*初始化操作
    * */
    protected void init() {

    }

    /*让子类必须传一个xml布局
    * */
    public abstract int getLayoutId();

    /*公共的跳转页面方法的抽取
    * */
    public void gotoActivity(Class<? extends Activity> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
       finish();
    }

    /*延时操作的方法抽取
    * */
    public void postDe(Runnable runnable){
        mHandler.postDelayed(runnable,DEFAULE_TIME);
    }

    //showProgressDialog
    public void showProgressDialog(String msg){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
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

    //隐藏软键盘
    public void hideSoftKeyboard(){
        if (mInputMethodManager == null){
            mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
        if (isShowSoftInput){
            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当Activity销毁时,将对话框置为null;释放资源
        mProgressDialog = null;
    }
}

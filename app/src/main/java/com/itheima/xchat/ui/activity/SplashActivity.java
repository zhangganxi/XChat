package com.itheima.xchat.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.widget.ImageView;

import com.itheima.xchat.MainActivity;
import com.itheima.xchat.R;
import com.itheima.xchat.base.BaseActivity;
import com.itheima.xchat.presenter.SplashPresenter;
import com.itheima.xchat.presenter.impl.SplashPresenterImpl;
import com.itheima.xchat.view.SplashView;

import butterknife.BindView;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui
 *  @文件名:   SplashActivity
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/19 14:54
 *  @描述：    TODO
 */
public class SplashActivity extends BaseActivity implements SplashView, Animator.AnimatorListener {
    private static final String TAG = "SplashActivity";
    @BindView(R.id.splash)
    ImageView mSplash;
    /*持有一个p层的对象应用
    * */
    private SplashPresenter mSplashPresenter;
    private Animator mAnim;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        super.init();
        //new一个P层对象;
        mSplashPresenter = new SplashPresenterImpl(this);
        initAnimation();
        initEvent();

    }

    private void initAnimation() {
        // 加载动画
        mAnim = AnimatorInflater.loadAnimator(this, R.animator.splash);
        //显示的调用invalidate
        mSplash.invalidate();
        mAnim.setTarget(mSplash);
        mAnim.start();
    }

    private void initEvent() {
        mAnim.addListener(this);
    }

    /*用户已经登录的操作
    * */
    @Override
    public void onLoggedIn() {
        gotoActivity(MainActivity.class);
    }

    /*用户没有登录的操作
    * */
    @Override
    public void onNoLoggedIn() {
       gotoActivity(LoginActivity.class);
    }

    //动画开始的回调
    @Override
    public void onAnimationStart(Animator animation) {

    }
    //动画结束的回调
    @Override
    public void onAnimationEnd(Animator animation) {
        mSplashPresenter.checkLoginStatus();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}

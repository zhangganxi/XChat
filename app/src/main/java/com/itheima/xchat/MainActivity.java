package com.itheima.xchat;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;
import com.itheima.xchat.base.BaseActivity;
import com.itheima.xchat.ui.activity.LoginActivity;
import com.itheima.xchat.ui.fragment.LeftFragment;
import com.itheima.xchat.utils.UIUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";

    @BindView(R.id.main_drawerlayout)
    DrawerLayout mMainDrawerlayout;
    @BindView(R.id.fragment_left)
    FrameLayout mFragmentLeft;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initLeftFragment();
        initListener();
    }

    private void initListener() {
        EMClient.getInstance().addConnectionListener(mEMConnectionListener);
    }

    private void initLeftFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction br = fragmentManager.beginTransaction();
        br.add(R.id.fragment_left,new LeftFragment());
        br.commit();
    }

    public void drawerLayoutOpen() {
        mMainDrawerlayout.openDrawer(GravityCompat.START);
    }

    //实现ConnectionListener接口
    EMConnectionListener mEMConnectionListener = new EMConnectionListener() {


        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(final int errorCode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(errorCode == EMError.USER_REMOVED){
                        // 显示帐号已经被移除
                    }else if (errorCode == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        gotoActivity(LoginActivity.class);
                        Toast.makeText(UIUtils.getContext(),getString(R.string.occupied),Toast.LENGTH_SHORT).show();
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)){
                            //连接不到聊天服务器
                        }
                        else{
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(mEMConnectionListener);
    }
}

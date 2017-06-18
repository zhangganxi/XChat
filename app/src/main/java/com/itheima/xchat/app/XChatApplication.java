package com.itheima.xchat.app;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.itheima.xchat.BuildConfig;
import com.itheima.xchat.R;
import com.itheima.xchat.database.DataBaseManager;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.base
 *  @文件名:   XChatApplication
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/20 21:57
 *  @描述：    TODO
 */
public class XChatApplication extends Application {
    private static final String TAG = "XChatApplication";
    private static Context mContext;
    private SoundPool mSoundPool;
    private int mDuan;
    private int mYulu;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initHuanXin();
        initBomb();
        initGreenDao();
        initListener();
        initSoundPool();
    }

    private void initSoundPool() {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        mDuan = mSoundPool.load(getApplicationContext(), R.raw.duan, 1);
        mYulu = mSoundPool.load(getApplicationContext(), R.raw.yulu, 1);
    }

    private void initListener() {
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
    }

    private void initGreenDao() {
        DataBaseManager.getInstance().init(getApplicationContext());
    }

    private void init() {
        mContext = getApplicationContext();
    }

    private void initBomb() {
        //第一：默认初始化
        Bmob.initialize(this, "fc6bd0f4d4cfa81b557a16b5d4ce8835");
    }

    private void initHuanXin() {
        int pid = android.os.Process.myPid();//获取进程id
        String processAppName = getProcessName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        //默认进程名是包名,但是如果App也有可能在其他进程中运行,如果不是在默认进程中运行,就直接return,防止SDK初始化N次
        /*如果App都在一个进程中,这个步骤可以不做
        * */
        if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        EMOptions options = new EMOptions();
        // 默认添加好友时，需不需要验证,false是需要验证,true表示不需要验证
        options.setAcceptInvitationAlways(true);
        //初始化
        EMClient.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        if (BuildConfig.DEBUG){
            EMClient.getInstance().setDebugMode(true);
        }
    }

    //
    private String getProcessName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();//获取正在运行的进程的集合
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {                //遍历进程集合
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {       //跟据进程ID获取进程名字
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    public static Context getContext() {
        return mContext;
    }

    private EMMessageListener mEMMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            if (isForeground()) {
                //如果是前台进程
                mSoundPool.play(mDuan,1,1,0,0,1);
            } else {
                //如果是后台进程
                showNotification(messages.get(0));
                mSoundPool.play(mYulu,1,1,0,0,1);
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };

    private void showNotification(EMMessage emMessage) {
        EMMessageBody body = emMessage.getBody();
        String notifyContent = null;
        if (body instanceof EMTextMessageBody){
            notifyContent = ((EMTextMessageBody) body).getMessage();
        }else{
            notifyContent = "不是一个文本";
        }
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Notification notification = builder.setContentTitle(getApplicationContext().getString(R.string.notify_title))
                .setContentText(notifyContent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setSmallIcon(R.mipmap.ic_chat)
                .getNotification();
        NotificationManager systemService = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        systemService.notify(0,notification);
    }

    public boolean isForeground() {
        ActivityManager systemService = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = systemService.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses){
            if (runningAppProcessInfo.processName.equals(getPackageName())){
                return runningAppProcessInfo.importance == IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }
}

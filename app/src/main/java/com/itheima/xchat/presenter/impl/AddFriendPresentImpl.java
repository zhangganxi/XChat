package com.itheima.xchat.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.itheima.xchat.database.DataBaseManager;
import com.itheima.xchat.event.AddFriendEvent;
import com.itheima.xchat.model.AddFriendBean;
import com.itheima.xchat.model.User;
import com.itheima.xchat.presenter.AddFriendPresent;
import com.itheima.xchat.utils.ThreadUtils;
import com.itheima.xchat.view.AddFriendView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter.impl
 *  @文件名:   AddFriendPresentImpl
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/12 22:15
 *  @描述：    TODO
 */
public class AddFriendPresentImpl implements AddFriendPresent {
    private static final String TAG = "AddFriendPresentImpl";

    private AddFriendView mAddFriendView;

    private List<AddFriendBean> mFriendBeans;

    public AddFriendPresentImpl (AddFriendView addFriendView){
        this.mAddFriendView = addFriendView;
        mFriendBeans = new ArrayList<AddFriendBean>();
        EventBus.getDefault().register(this);
    }

    @Override
    public void search(String keyword) {

        mFriendBeans.clear();

        BmobQuery<User> query = new BmobQuery<User>();

        query.addWhereStartsWith("username",keyword);  //需要交费之后才可以,使用该功能,模糊查询

        query.addWhereNotEqualTo("username", EMClient.getInstance().getCurrentUser());

        query.findObjects(new FindListener<User>() {
            @Override
            public void done(final List<User> list, BmobException e) {
                if (e ==  null){
                    ThreadUtils.runOnBackgoundThread(new Runnable() {
                        @Override
                        public void run() {
                            List<String> contacts = DataBaseManager.getInstance().getContacts();
                            for (User user:list){
                                AddFriendBean addFriendBean = new AddFriendBean();
                                String username = user.getUsername();
                                String createdAt = user.getCreatedAt();
                                addFriendBean.setUsername(username);
                                addFriendBean.setRegisterTime(createdAt);
                                if (contacts != null){
                                    boolean isAdd = contacts.contains(username);
                                    addFriendBean.setAdded(isAdd);
                                }
                                mFriendBeans.add(addFriendBean);
                            }
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAddFriendView.searchSuccess();
                                }
                            });
                        }
                    });

                }else{
                    mAddFriendView.searchFailed();
                }

            }
        });
    }

    @Override
    public List<AddFriendBean> getSearchFriends() {
        return mFriendBeans;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(AddFriendEvent event) {
//        Toast.makeText(UIUtils.getContext(),event.getUsername(),Toast.LENGTH_SHORT).show();
        try {
            EMClient.getInstance().contactManager().addContact(event.getUsername(),event.getReason());  //发送添加好友申请
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    mAddFriendView.sendAddFriendRequestSuccess();
                }
            });
        } catch (HyphenateException e) {
            e.printStackTrace();
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    mAddFriendView.sendAddFriendRequestFailed();
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}

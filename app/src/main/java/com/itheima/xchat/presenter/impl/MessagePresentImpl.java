package com.itheima.xchat.presenter.impl;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.itheima.xchat.presenter.MessagePresent;
import com.itheima.xchat.utils.ThreadUtils;
import com.itheima.xchat.view.MessageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter.impl
 *  @文件名:   MessagePresentImpl
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/20 22:50
 *  @描述：    TODO
 */
public class MessagePresentImpl implements MessagePresent {
    private static final String TAG = "MessagePresentImpl";

    private MessageView mMessageView;
    private List<EMConversation> mEMConversations;

    public MessagePresentImpl(MessageView messageView){
        this.mMessageView = messageView;
        mEMConversations = new ArrayList<EMConversation>();
        initListener();
    }

    private void initListener() {
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);

    }

    @Override
    public void loadMessageData() {

        ThreadUtils.runOnBackgoundThread(new Runnable() {
            @Override
            public void run() {
                mEMConversations.clear();
                Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
                if (conversations == null){
                    return;
                }
                Collection<EMConversation> values = conversations.values();

                mEMConversations.addAll(values);

                Collections.sort(mEMConversations, new Comparator<EMConversation>() {
                    @Override
                    public int compare(EMConversation o1, EMConversation o2) {

                        return (int) (o2.getLastMessage().getMsgTime() - o1.getLastMessage().getMsgTime());
                    }
                });


                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMessageView != null){
                            mMessageView.onConversationsLoaded();
                        }
                    }
                });
            }
        });
    }

    @Override
    public List<EMConversation> getMessages() {
        return mEMConversations;
    }

    private EMMessageListener mEMMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    loadMessageData();
                }
            });

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

    @Override
    public void onDestroyListener() {
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListener);
    }

    @Override
    public void unBind() {
        mMessageView = null;
    }


}

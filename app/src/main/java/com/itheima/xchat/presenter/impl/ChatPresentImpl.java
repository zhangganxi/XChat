package com.itheima.xchat.presenter.impl;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.itheima.xchat.presenter.ChatPresent;
import com.itheima.xchat.utils.ThreadUtils;
import com.itheima.xchat.view.ChatView;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter.impl
 *  @文件名:   ChatPresentImpl
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/16 12:14
 *  @描述：    TODO
 */
public class ChatPresentImpl implements ChatPresent {
    private static final String TAG = "ChatPresentImpl";

    private ChatView mChatView;
    private List<EMMessage> mMessages;
    private static final int PAGESIZE = 20;

    public ChatPresentImpl(ChatView chatView) {
        this.mChatView = chatView;
        mMessages = new ArrayList<EMMessage>();
        initReceiveListener();
    }

    private void initReceiveListener() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }


    @Override
    public void sendMessage(final String username, final String messageContent) {
        final EMMessage message = EMMessage.createTxtSendMessage(messageContent, username);
        mMessages.add(message);
        mChatView.onStartSendMessage();

        ThreadUtils.runOnBackgoundThread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().chatManager().sendMessage(message);
                message.setMessageStatusCallback(mEMCallBack);
            }
        });

    }

    private EMCallBack mEMCallBack = new EMCallBack() {
        @Override
        public void onSuccess() {
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    mChatView.sendMessageSuccess();
                }
            });
        }

        @Override
        public void onError(int code, String error) {
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    mChatView.sendMessageFailed();
                }
            });
        }

        @Override
        public void onProgress(int progress, String status) {

        }
    };

    @Override
    public List<EMMessage> getMessage() {
        return mMessages;
    }

    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            final EMMessage message = messages.get(0);
            if (message.getUserName().equals(mChatView.getCurUsername())) {
                mMessages.add(message);
                markMsgRead();
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onReceiveMessage();
                    }
                });

            }
            ;
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    private void markMsgRead() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mChatView.getCurUsername());
        //指定会话消息未读数清零
        conversation.markAllMessagesAsRead();
    }

    @Override
    public void removeListener() {
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    public void loadInitMessage(final String username) {
        ThreadUtils.runOnBackgoundThread(new Runnable() {
            @Override
            public void run() {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
                if (conversation == null){
                    return;
                }
                //获取此会话的所有消息
                List<EMMessage> messages = conversation.getAllMessages();
                mMessages.addAll(messages);

                conversation.markAllMessagesAsRead();//指定会话消息未读数清零

                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onInitMessages();
                    }
                });
            }
        });
    }

    @Override
    public void loadMoreMessage(final String username) {
        ThreadUtils.runOnBackgoundThread(new Runnable() {
            @Override
            public void run() {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
                String startMsgId = mMessages.get(0).getMsgId();
                //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
                // 获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
                final List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, PAGESIZE);
                mMessages.addAll(0,messages);
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onLoadMoreMessages(messages.size());
                    }
                });
            }
        });
    }


}

package com.itheima.xchat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;
import com.itheima.xchat.utils.UIUtils;
import com.itheima.xchat.widget.ChatReceiveItemView;
import com.itheima.xchat.widget.ChatSendItemView;

import java.util.List;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.adapter
 *  @文件名:   ChatAdapter
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/16 15:05
 *  @描述：    TODO
 */
public class ChatAdapter extends RecyclerView.Adapter {
    private static final String TAG = "ChatAdapter";
    private List<EMMessage> mMessages;
    private final int ITEM_VIEW_SEND = 0;
    private final int ITEM_VIEW_RECEIVE = 1;
    private final int TIME_INTERVAL = 30000;


    public ChatAdapter(List<EMMessage> message) {
        this.mMessages = message;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_SEND){
            return new ChatSendViewHodler(new ChatSendItemView(UIUtils.getContext()));
        }else{
            return new ChatReceiveViewHodler(new ChatReceiveItemView(UIUtils.getContext()));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean isShowTime = false;
        if (position == 0 || getIsShowTime(position)){
            isShowTime = true;
        }

        if (holder instanceof ChatSendViewHodler){
            ((ChatSendViewHodler)holder).mChatSendItemView.bindData(mMessages.get(position),isShowTime);

        }else{
            ((ChatReceiveViewHodler)holder).mChatReceiveItemView.bindData(mMessages.get(position),isShowTime);
        }



    }

    private boolean getIsShowTime(int position) {
        return mMessages.get(position).getMsgTime() - mMessages.get(position- 1).getMsgTime() > TIME_INTERVAL;
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage message = mMessages.get(position);
        EMMessage.Direct direct = message.direct();
        return direct == EMMessage.Direct.SEND ? ITEM_VIEW_SEND : ITEM_VIEW_RECEIVE;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ChatSendViewHodler extends RecyclerView.ViewHolder{

        private ChatSendItemView mChatSendItemView;

        public ChatSendViewHodler(ChatSendItemView itemView) {
            super(itemView);
            this.mChatSendItemView = itemView;
        }
    }

    public class ChatReceiveViewHodler extends RecyclerView.ViewHolder{

        private ChatReceiveItemView mChatReceiveItemView;

        public ChatReceiveViewHodler(ChatReceiveItemView itemView) {
            super(itemView);
            this.mChatReceiveItemView = itemView;

        }
    }



}

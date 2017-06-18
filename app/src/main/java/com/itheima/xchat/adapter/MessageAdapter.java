package com.itheima.xchat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMConversation;
import com.itheima.xchat.utils.UIUtils;
import com.itheima.xchat.widget.MessageItemView;

import java.util.List;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.adapter
 *  @文件名:   MessageAdapter
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/20 22:53
 *  @描述：    TODO
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    private static final String TAG = "MessageAdapter";

    private List<EMConversation> mEMConversations;

    public MessageAdapter(List<EMConversation> messages) {
        this.mEMConversations = messages;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageHolder(new MessageItemView(UIUtils.getContext()));
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, final int position) {
        holder.mMessageItemView.bindData(mEMConversations.get(position));

        final String userName = mEMConversations.get(position).getLastMessage().getUserName();
        holder.mMessageItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(userName);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mEMConversations.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        private MessageItemView mMessageItemView;

        public MessageHolder(MessageItemView itemView) {
            super(itemView);
            this.mMessageItemView = itemView;
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String username);
    }
}

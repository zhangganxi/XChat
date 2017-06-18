package com.itheima.xchat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.itheima.xchat.R;
import com.itheima.xchat.utils.UIUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.widget
 *  @文件名:   MessageItemView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/20 22:27
 *  @描述：    TODO
 */
public class MessageItemView extends RelativeLayout {
    private static final String TAG = "MessageItemView";
    @BindView(R.id.img_head)
    ImageView mImgHead;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_last_message)
    TextView mTvLastMessage;
    private BadgeView mBadge;

    public MessageItemView(Context context) {
        this(context, null);
    }

    public MessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_message, this);
        mBadge = new BadgeView(UIUtils.getContext());
        ButterKnife.bind(this,this);
    }

    public void bindData(EMConversation emConversation) {
        EMMessage lastMessage = emConversation.getLastMessage();
        mTvUsername.setText(lastMessage.getUserName());
        if (lastMessage.getBody() instanceof EMTextMessageBody){
            mTvLastMessage.setText(((EMTextMessageBody)lastMessage.getBody()).getMessage());
        }else{
            mTvLastMessage.setText(UIUtils.getContext().getString(R.string.not_string));
        }
        long msgTime = lastMessage.getMsgTime();
        String timestampString = DateUtils.getTimestampString(new Date(msgTime));
        mTvTime.setText(timestampString);

        initBadgeView(emConversation);
    }

    private void initBadgeView(EMConversation conversation) {
        if (conversation.getUnreadMsgCount()==0){
            mBadge.setVisibility(View.GONE);
            return;
        }
        mBadge.setTargetView(mImgHead);
        mBadge.setBadgeCount(conversation.getUnreadMsgCount());
    }
}

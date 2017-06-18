package com.itheima.xchat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.itheima.xchat.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.widget
 *  @文件名:   ChatSendItemView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/16 15:08
 *  @描述：    TODO
 */
public class ChatReceiveItemView extends RelativeLayout {
    private static final String TAG = "ChatSendItemView";
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.img_head)
    ImageView mImgHead;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    public ChatReceiveItemView(Context context) {
        this(context, null);
    }

    public ChatReceiveItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_chat_receive, this);
        ButterKnife.bind(this,this);
    }

    public void bindData(EMMessage emMessage, boolean isShowTime) {
        if (isShowTime){
            mTvTime.setVisibility(VISIBLE);
            long msgTime = emMessage.getMsgTime();
            String time = DateUtils.getTimestampString(new Date(msgTime));
            mTvTime.setText(time);
        }else{
            mTvTime.setVisibility(View.GONE);
        }
        EMMessageBody body = emMessage.getBody();
        if (body instanceof EMTextMessageBody) {
            String message = ((EMTextMessageBody) body).getMessage();
            mTvContent.setText(message);
        } else {
            mTvContent.setText("000000");
        }

    }
}

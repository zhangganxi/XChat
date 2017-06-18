package com.itheima.xchat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
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
 *  @文件名:   ChatSendItemView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/16 15:08
 *  @描述：    TODO
 */
public class ChatSendItemView extends RelativeLayout {
    private static final String TAG = "ChatSendItemView";
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.img_head)
    ImageView mImgHead;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.prb_send)
    ProgressBar mPrbSend;
    @BindView(R.id.img_send_error)
    ImageView mImgSendError;

    public ChatSendItemView(Context context) {
        this(context, null);
    }

    public ChatSendItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initListener();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_chat_send, this);
        ButterKnife.bind(this, this);
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

        switch (emMessage.status()) {
            case SUCCESS:
                mPrbSend.setVisibility(View.GONE);
                mImgSendError.setVisibility(View.GONE);
                break;
            case FAIL:
                mPrbSend.setVisibility(View.GONE);
                mImgSendError.setVisibility(View.VISIBLE);
                break;
            default:

                break;
        }
    }

    private void initListener() {
        mTvContent.setOnLongClickListener(mOnLongClickListener);
    }

    OnLongClickListener mOnLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(UIUtils.getContext(),"复制",Toast.LENGTH_SHORT).show();
            return false;
        }
    };
}

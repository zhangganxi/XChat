package com.itheima.xchat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.xchat.R;
import com.itheima.xchat.event.AddFriendEvent;
import com.itheima.xchat.model.AddFriendBean;
import com.itheima.xchat.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 *  @项目名：  XChat
 *  @包名：    com.itheima.xchat.widget
 *  @文件名:   AddFriendItemView
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/13 22:07
 *  @描述：    TODO
 */
public class AddFriendItemView extends LinearLayout {
    private static final String TAG = "AddFriendItemView";
    @BindView(R.id.img_head)
    ImageView mImgHead;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_register_time)
    TextView mTvRegisterTime;
    @BindView(R.id.btn_addfriend)
    Button mBtnAddfriend;


    public AddFriendItemView(Context context) {
        this(context, null);
    }

    public AddFriendItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_addfriend, this);
        ButterKnife.bind(this,this);
    }

    public void bindData(AddFriendBean addFriendBean) {
        mTvUsername.setText(addFriendBean.getUsername());
        mTvRegisterTime.setText(addFriendBean.getRegisterTime());
        if (addFriendBean.isAdded()){
            mBtnAddfriend.setText(UIUtils.getContext().getString(R.string.isAdd));
            mBtnAddfriend.setEnabled(false);
        }else{
            mBtnAddfriend.setText(UIUtils.getContext().getString(R.string.add_friend));
            mBtnAddfriend.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_addfriend)
    public void onClick() {
        AddFriendEvent event = new AddFriendEvent(mTvUsername.getText().toString());
        EventBus.getDefault().post(event);
    }
}

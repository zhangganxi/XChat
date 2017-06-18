package com.itheima.xchat.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itheima.xchat.R;
import com.itheima.xchat.adapter.MessageAdapter;
import com.itheima.xchat.base.BaseFragment;
import com.itheima.xchat.presenter.MessagePresent;
import com.itheima.xchat.presenter.impl.MessagePresentImpl;
import com.itheima.xchat.ui.activity.ChatActivity;
import com.itheima.xchat.utils.UIUtils;
import com.itheima.xchat.view.MessageView;

import butterknife.BindView;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui.fragment
 *  @文件名:   ContactFragment
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/2 12:26
 *  @描述：    TODO
 */
public class MessageFragment extends BaseFragment implements MessageView {
    private static final String TAG = "ContactFragment";
    @BindView(R.id.rlv_message)
    RecyclerView mRlvMessage;
    private MessagePresent mMessagePresent;
    private MessageAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_messaging;
    }

    @Override
    protected void init() {
        super.init();
        mMessagePresent = new MessagePresentImpl(this);
        initRecylerView();
        initData();
        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    private void initData(){
        mMessagePresent.loadMessageData();
    }

    private void initRecylerView() {
        mRlvMessage.setHasFixedSize(true);
        mRlvMessage.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        mAdapter = new MessageAdapter(mMessagePresent.getMessages());
        mRlvMessage.setAdapter(mAdapter);
    }


    @Override
    public void onConversationsLoaded() {
       mAdapter.notifyDataSetChanged();
    }

    MessageAdapter.OnItemClickListener mOnItemClickListener = new MessageAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String username) {
            Intent intent = new Intent();
            intent.putExtra("username",username);
            gotoActivity(ChatActivity.class,false,intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMessagePresent.onDestroyListener();
        mMessagePresent.unBind();
    }
}

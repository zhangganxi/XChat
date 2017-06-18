package com.itheima.xchat.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.itheima.xchat.R;
import com.itheima.xchat.adapter.ContactListAdapter;
import com.itheima.xchat.base.BaseFragment;
import com.itheima.xchat.manager.WrapContentLinearLayoutManager;
import com.itheima.xchat.presenter.impl.ContactPresentImpl;
import com.itheima.xchat.ui.activity.ChatActivity;
import com.itheima.xchat.utils.UIUtils;
import com.itheima.xchat.view.ContactView;
import com.itheima.xchat.widget.SlideBar;

import butterknife.BindView;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui.fragment
 *  @文件名:   ContactFragment
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/2 12:26
 *  @描述：    TODO
 */
public class ContactFragment extends BaseFragment implements ContactView {
    private static final String TAG = "ContactFragment";
    private static final int POSITION_NOT_FIND = -1;
    @BindView(R.id.rcl_contact)
    RecyclerView mRclContact;
    @BindView(R.id.srf_contact)
    SwipeRefreshLayout mSrfContact;
    @BindView(R.id.slb_contact)
    SlideBar mSlbContact;
    @BindView(R.id.tv_firstchar)
    TextView mTvFirstchar;

    private ContactPresentImpl mContactPresent;
    private ContactListAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void init() {
        super.init();
        mContactPresent = new ContactPresentImpl(this);
        initRefreshLayout();
        initSlideBar();
        initData();
        initView();
        initListener();
    }

    private void initListener() {
        //设置好友监听
        EMClient.getInstance().contactManager().setContactListener(mEMContactListener);
    }

    private void initSlideBar() {
        mSlbContact.setOnSlideIndexListener(mOnSlideIndexListener);
    }

    private void initRefreshLayout() {
        mSrfContact.setColorSchemeResources(R.color.blue, R.color.red, R.color.chargray);
        mSrfContact.setOnRefreshListener(mOnRefreshListener);
    }

    private void initData() {
        mContactPresent.loadContacts();
    }

    private void initView() {
        mRclContact.setHasFixedSize(true);//固定大小
        mRclContact.setLayoutManager(new WrapContentLinearLayoutManager(UIUtils.getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ContactListAdapter(mContactPresent.getContacts());
        mRclContact.setAdapter(mAdapter);
        mAdapter.setOnLongItemListener(mOnLongClickItemListener);
        mAdapter.setOnClickItemListener(mOnClickItemListener);
    }

    @Override
    public void onLoadContactSuccess() {
        mSrfContact.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadContactFailed() {
        Toast.makeText(UIUtils.getContext(), getString(R.string.load_contact_failed), Toast.LENGTH_SHORT).show();
        mSrfContact.setRefreshing(false);
    }

    @Override
    public void delContactSuccess() {
        Toast.makeText(UIUtils.getContext(),UIUtils.getContext().getString(R.string.del_friend_success),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void delContactFailed() {
        Toast.makeText(UIUtils.getContext(),UIUtils.getContext().getString(R.string.del_friend_failed),Toast.LENGTH_SHORT).show();
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mContactPresent.refresh();
            mAdapter.notifyDataSetChanged();
        }
    };

    private SlideBar.OnSlideIndexListener mOnSlideIndexListener = new SlideBar.OnSlideIndexListener() {
        @Override
        public void onIndexChange(String index) {
            mTvFirstchar.setVisibility(View.VISIBLE);
            mTvFirstchar.setText(index);

            int position = getPosition(index);
            if (position != POSITION_NOT_FIND){
                mRclContact.scrollToPosition(position);
            }

        }

        @Override
        public void onIndexChangeEnd() {
            mTvFirstchar.setVisibility(View.GONE);
        }
    };

    //条目长按事件
    ContactListAdapter.OnLongClickItemListener mOnLongClickItemListener = new ContactListAdapter.OnLongClickItemListener() {
        @Override
        public void onLongClick(String username) {
            showDelFriendDialog(username);
        }
    };

    ContactListAdapter.OnClickItemListener mOnClickItemListener = new ContactListAdapter.OnClickItemListener() {
        @Override
        public void onClickItem(String username) {
            Intent intent = new Intent();
            intent.putExtra("username",username);
            gotoActivity(ChatActivity.class,false,intent);
        }
    };

    private void showDelFriendDialog(final String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(UIUtils.getContext().getString(R.string.del_friend_title));
        String message = String.format(UIUtils.getContext().getString(R.string.del_friend_message),username);
        builder.setMessage(message);
        builder.setNegativeButton(UIUtils.getContext().getString(R.string.cancol), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(UIUtils.getContext().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mContactPresent.delContact(username);
            }
        });
       builder.show();
    }

    public int getPosition(String firstLetter){
        for (int i = 0; i < mContactPresent.getContacts().size();i ++){
            if (firstLetter.equals(mContactPresent.getContacts().get(i).getFirstLetter())){
                return i;
            }
        }
        return POSITION_NOT_FIND;
    }

    private EMContactListener mEMContactListener = new EMContactListener() {

        //增加联系人
        @Override
        public void onContactAdded(String username) {
            mContactPresent.refresh();
        }

        //删除联系人回调
        @Override
        public void onContactDeleted(String username) {
            mContactPresent.refresh();
        }

        //收到好友邀请
        @Override
        public void onContactInvited(String username, String reason) {

        }

        //好友申请被同意
        @Override
        public void onFriendRequestAccepted(String username) {

        }

        //好友申请被拒绝
        @Override
        public void onFriendRequestDeclined(String username) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContactPresent.unBind();
    }
}

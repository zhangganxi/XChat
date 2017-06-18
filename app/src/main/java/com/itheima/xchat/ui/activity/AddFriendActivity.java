package com.itheima.xchat.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.xchat.R;
import com.itheima.xchat.adapter.AddFriendAdapter;
import com.itheima.xchat.base.BaseActivity;
import com.itheima.xchat.presenter.AddFriendPresent;
import com.itheima.xchat.presenter.impl.AddFriendPresentImpl;
import com.itheima.xchat.utils.UIUtils;
import com.itheima.xchat.view.AddFriendView;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui.activity
 *  @文件名:   AddFriendActivity
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/12 21:33
 *  @描述：    TODO
 */
public class AddFriendActivity extends BaseActivity implements AddFriendView {
    private static final String TAG = "AddFriendActivity";
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.img_search)
    ImageView mImgSearch;
    @BindView(R.id.rlv_add_friend)
    RecyclerView mRlvAddFriend;


    private AddFriendPresent mAddFriendPresent;
    private AddFriendAdapter mAddFriendAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void init() {
        super.init();
        mAddFriendPresent = new AddFriendPresentImpl(this);
        isShowSoftInput = true;
        initActionBar();
        initListener();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRlvAddFriend.setHasFixedSize(true);
        mRlvAddFriend.setLayoutManager(new LinearLayoutManager(this));
        mAddFriendAdapter = new AddFriendAdapter(mAddFriendPresent.getSearchFriends());
        mRlvAddFriend.setAdapter(mAddFriendAdapter);

    }

    private void initListener() {
        mEdtSearch.setOnEditorActionListener(mOnEditorActionListener);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.add_friend));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.img_search)
    public void onClick() {
        search();
    }

    private void search() {
        showProgressDialog("正在加载...");
        hideSoftKeyboard();
        String keyword = mEdtSearch.getText().toString().trim();
        mAddFriendPresent.search(keyword);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            search();
            return true;
        }
    };



    @Override
    public void searchSuccess() {
        Toast.makeText(UIUtils.getContext(), "搜索成功", Toast.LENGTH_SHORT).show();
        hideProgressDialog();
        mAddFriendAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchFailed() {
        Toast.makeText(UIUtils.getContext(), "搜索失败", Toast.LENGTH_SHORT).show();
        hideProgressDialog();
    }

    @Override
    public void sendAddFriendRequestSuccess() {
        Toast.makeText(UIUtils.getContext(),"发送申请成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendAddFriendRequestFailed() {
        Toast.makeText(UIUtils.getContext(),"发送申请失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddFriendPresent.onDestroy();
    }
}

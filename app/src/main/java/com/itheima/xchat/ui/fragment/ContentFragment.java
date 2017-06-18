package com.itheima.xchat.ui.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.itheima.xchat.MainActivity;
import com.itheima.xchat.R;
import com.itheima.xchat.adapter.MainViewPagerAdapter;
import com.itheima.xchat.base.BaseFragment;
import com.itheima.xchat.ui.activity.AddFriendActivity;
import com.itheima.xchat.utils.ThreadUtils;
import com.itheima.xchat.widget.BadgeView;

import java.util.List;

import butterknife.BindView;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui.fragment
 *  @文件名:   ContentFragment
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/1 23:46
 *  @描述：    TODO
 */
public class ContentFragment extends BaseFragment {
    private static final String TAG = "ContentFragment";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private int[] imageResId = {R.drawable.chat_selector, R.drawable.people_selector, R.drawable.explore_selector, R.drawable.me_selector};
    private BadgeView mBadgeViewConversation;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected void init() {
        super.init();
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        //设置 Toolbar menu
        mToolbar.inflateMenu(R.menu.menu_search);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.drawerLayoutOpen();
            }
        });

        initBadge();

    }

    private void initBadge() {
        mBadgeViewConversation = new BadgeView(getActivity());
    }

    private void initData() {
        mTabLayout.setupWithViewPager(mViewPager);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getActivity().getSupportFragmentManager(), imageResId);
        mViewPager.setAdapter(adapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));

        }

    }

    private void setUpDedgeForConversation() {
        int unreadMsgsCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        if (unreadMsgsCount == 0){
            mBadgeViewConversation.setVisibility(View.GONE);
            return;
        }
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        View view = tab.getCustomView();
        mBadgeViewConversation.setTargetView(view);
        mBadgeViewConversation.setBadgeCount(unreadMsgsCount);
    }

    private void initEvent() {
        // 设置menu item 点击事件
        mToolbar.setOnMenuItemClickListener(mOnMenuItemClickListener);
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
    }

    //Toolbar菜单按钮的点击事件
    private Toolbar.OnMenuItemClickListener mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_search:
                    Toast.makeText(getContext(), "搜索按钮被点击了", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item_add:
                    gotoActivity(AddFriendActivity.class, false);
                    break;
            }
            return false;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        setUpDedgeForConversation();
    }

    private EMMessageListener mEMMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                   setUpDedgeForConversation();
                }
            });
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListener);
    }
}

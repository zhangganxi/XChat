package com.itheima.xchat.ui.fragment;

import android.widget.TextView;

import com.itheima.xchat.R;
import com.itheima.xchat.base.BaseFragment;

import butterknife.BindView;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui.fragment
 *  @文件名:   MeFragment
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/2 12:44
 *  @描述：    TODO
 */
public class MeFragment extends BaseFragment {
    private static final String TAG = "MeFragment";
    @BindView(R.id.tv_contact)
    TextView mTvContact;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init() {
        super.init();
        mTvContact.setText(this.getClass().getSimpleName());
    }


}

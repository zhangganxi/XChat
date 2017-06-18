package com.itheima.xchat.ui.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.itheima.xchat.R;
import com.itheima.xchat.base.BaseFragment;
import com.itheima.xchat.presenter.impl.LeftPresentImpl;
import com.itheima.xchat.ui.activity.LoginActivity;
import com.itheima.xchat.view.LeftView;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui.fragment
 *  @文件名:   LeftFragment
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/6 21:59
 *  @描述：    TODO
 */
public class LeftFragment extends BaseFragment implements LeftView {
    private static final String TAG = "LeftFragment";
    @BindView(R.id.tv_writeoff)
    Button mTvWriteoff;
    private LeftPresentImpl mLeftPresent;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_left;
    }

    @Override
    protected void init() {
        super.init();

        mLeftPresent = new LeftPresentImpl(this);
    }

    @OnClick(R.id.tv_writeoff)
    public void onClick() {
       showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定退出当前账号?");
        // 设置取消按钮
        builder.setNegativeButton("取消", mOnCanenlClickListener);
        // 设置确定按钮
        builder.setPositiveButton("确定", mConfirmOnClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private DialogInterface.OnClickListener mOnCanenlClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

    private DialogInterface.OnClickListener mConfirmOnClickListener =  new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mLeftPresent.logout();
        }
    };

    @Override
    public void logoutSuccess() {
        gotoActivity(LoginActivity.class);
    }

    @Override
    public void startLogout() {
        showProgressDialog(getString(R.string.start_logout));
    }
}

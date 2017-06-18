package com.itheima.xchat.ui.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.xchat.MainActivity;
import com.itheima.xchat.R;
import com.itheima.xchat.base.BaseActivity;
import com.itheima.xchat.presenter.impl.LoginPresentImpl;
import com.itheima.xchat.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui
 *  @文件名:   LoginActivity
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/19 16:29
 *  @描述：    TODO
 */
public class LoginActivity extends BaseActivity implements LoginView {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_CODE = 100;
    @BindView(R.id.cimv_head)
    CircleImageView mCimvHead;
    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_newuser)
    TextView mTvNewuser;
    private LoginPresentImpl mLoginPresent;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();
        mLoginPresent = new LoginPresentImpl(this);
        mEdtPassword.setOnEditorActionListener(mOnEditorActionListener);
    }

    @OnClick({R.id.btn_login, R.id.tv_newuser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_newuser:
                gotoActivity(RegisterActivity.class);
                break;
        }
    }

    private void requestWriteExternalStoragePermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    private boolean hasWriteExternalStoragePermission() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void login() {
        hideSoftKeyboard();
        String username = mEdtUsername.getText().toString().trim();
        String password = mEdtPassword.getText().toString().trim();
        if (hasWriteExternalStoragePermission()) {
            mLoginPresent.login(username, password);
        } else {
            requestWriteExternalStoragePermission();
        }
    }

    TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            login();
            return true;
        }
    };

    @Override
    public void onUserNameError() {
        mEdtUsername.setError(getString(R.string.username_error));
    }

    @Override
    public void onPasswordError() {
        mEdtPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void onStartLogin() {
        showProgressDialog("正在登录...");
    }

    @Override
    public void onLoginSuccess() {
        hideProgressDialog();
        gotoActivity(MainActivity.class);
    }

    @Override
    public void onLoginFailed() {
        hideProgressDialog();
        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    login();
                } else {
                    Toast.makeText(this, getString(R.string.no_permission), Toast.LENGTH_SHORT).show();
                }
                break;
            default:

                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

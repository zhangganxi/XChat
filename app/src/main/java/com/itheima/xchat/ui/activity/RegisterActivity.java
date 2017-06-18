package com.itheima.xchat.ui.activity;

import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.xchat.R;
import com.itheima.xchat.base.BaseActivity;
import com.itheima.xchat.presenter.impl.RegisterPresentImpl;
import com.itheima.xchat.view.RegisterView;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui
 *  @文件名:   RegisterActivity
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/3/28 20:24
 *  @描述：    TODO
 */
public class RegisterActivity extends BaseActivity implements RegisterView {
    private static final String TAG = "RegisterActivity";
    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.edt_confirm_password)
    EditText mEdtConfirmPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private RegisterPresentImpl mRegisterPresent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        super.init();
        mRegisterPresent = new RegisterPresentImpl(this);
        //软件键盘的监听事件
        mEdtConfirmPassword.setOnEditorActionListener(mOnEditorActionListener);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        register();
    }

    private void register() {
        hideSoftKeyboard();
        String userName = mEdtUsername.getText().toString().trim();
        String password = mEdtPassword.getText().toString().trim();
        String confirmPassword = mEdtConfirmPassword.getText().toString().trim();
        mRegisterPresent.register(userName,password,confirmPassword);
    }

    //软件的监听事件
    TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            register();
            return true;
        }
    };
    //用户名不合法UI操作
    @Override
    public void onUserNameError() {
        mEdtUsername.setError(getString(R.string.username_error));
    }

    //密码不合法UI操作
    @Override
    public void onPasswordError() {
        mEdtPassword.setError(getString(R.string.password_error));
    }

    //确认密码不合法的UI操作
    @Override
    public void onConfirmPassword() {
        mEdtConfirmPassword.setError(getString(R.string.confirm_password_error));
    }

    //注册成的UI操作
    @Override
    public void onRegisterSuccess() {
        hideProgressDialog();
        Toast.makeText(this,"注册成功", Toast.LENGTH_LONG).show();
    }

    //注册失败的UI操作
    @Override
    public void onRegisterFeiled() {
        hideProgressDialog();
        Toast.makeText(this,"注册失败", Toast.LENGTH_LONG).show();
    }

    //注册用户名已存在的UI操作
    @Override
    public void onUserAlreadyExist() {
        hideProgressDialog();
        Toast.makeText(this,"用户名已存在", Toast.LENGTH_LONG).show();
    }

    //开会注册
    @Override
    public void onStartRegister() {
        showProgressDialog(getString(R.string.start_register));
    }
}

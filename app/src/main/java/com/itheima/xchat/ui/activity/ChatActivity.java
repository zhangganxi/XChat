package com.itheima.xchat.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.xchat.R;
import com.itheima.xchat.adapter.ChatAdapter;
import com.itheima.xchat.base.BaseActivity;
import com.itheima.xchat.presenter.ChatPresent;
import com.itheima.xchat.presenter.impl.ChatPresentImpl;
import com.itheima.xchat.utils.UIUtils;
import com.itheima.xchat.view.ChatView;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.ui.activity
 *  @文件名:   ChatActivity
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/16 11:16
 *  @描述：    TODO
 */
public class ChatActivity extends BaseActivity implements ChatView {
    private static final String TAG = "ChatActivity";
    @BindView(R.id.rlv_message)
    RecyclerView mRlvMessage;
    @BindView(R.id.edt_send)
    EditText mEdtSend;
    @BindView(R.id.btn_send)
    Button mBtnSend;

    private ChatPresent mChatPresent;
    private String mUsername;
    private ChatAdapter mChatAdapter;

    //Activity最外层的Layout视图
    private View activityRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    private boolean isFinish = true;


    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init() {
        super.init();
        initData();
        initActionBar();
        initRecyclerView();
        initListener();
        initMessages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加layout大小发生改变监听器
        activityRootView.addOnLayoutChangeListener(mOnLayoutChangeListener);
    }

    View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
            if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){

                scrollToBottom();
                isFinish = false;
                isShowSoftInput = true;
            }
            if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){

               if (isFinish){
                   finish();
               }
            }
        }
    };

    private void initData() {
        mUsername = getIntent().getStringExtra("username");
        mChatPresent = new ChatPresentImpl(this);

        activityRootView = findViewById(R.id.root_layout);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;
    }

    private void initRecyclerView() {
        mRlvMessage.setHasFixedSize(true);
        mRlvMessage.setLayoutManager(new LinearLayoutManager(this));
        mChatAdapter = new ChatAdapter(mChatPresent.getMessage());
        mRlvMessage.setAdapter(mChatAdapter);
    }

    private void initMessages() {
        mChatPresent.loadInitMessage(mUsername);
    }

    private void initListener() {
        mEdtSend.addTextChangedListener(mTextWatcher);
        mEdtSend.setOnEditorActionListener(mOnEditorActionListener);

        mRlvMessage.addOnScrollListener(mOnScrollListener);
        mRlvMessage.setOnTouchListener(mOnTouchListener);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mUsername);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isFinish){
                    finish();
                }else{
                    isFinish = true;
                }
                hideSoftKeyboard();
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btn_send)
    public void onClick() {
        sendMessage();
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mBtnSend.setEnabled(s.length() > 0);
        }
    };


    TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            sendMessage();
            return true;
        }
    };

    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                if (position == 0){
                    mChatPresent.loadMoreMessage(mUsername);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

        }
    };

    View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "onTouch: ==============");
                    hideSoftKeyboard();
                    break;
                default:

                    break;
            }
            return false;
        }
    };

    private void sendMessage() {
        String messageContent = mEdtSend.getText().toString().trim();
        mChatPresent.sendMessage(mUsername,messageContent);
    }

    @Override
    public void sendMessageSuccess() {

        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendMessageFailed() {
        Toast.makeText(UIUtils.getContext(),UIUtils.getContext().getString(R.string.send_failed),Toast.LENGTH_SHORT).show();
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStartSendMessage() {
        mEdtSend.getEditableText().clear();
        mChatAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    public void scrollToBottom(){
        mRlvMessage.scrollToPosition(mChatPresent.getMessage().size()-1);
    }

    @Override
    public String getCurUsername() {
        return mUsername;
    }

    @Override
    public void onReceiveMessage() {
        mChatAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    @Override
    public void onInitMessages() {
        mChatAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    @Override
    public void onLoadMoreMessages(int size) {
        mChatAdapter.notifyDataSetChanged();
        mRlvMessage.scrollToPosition(size);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatPresent.removeListener();
    }
}

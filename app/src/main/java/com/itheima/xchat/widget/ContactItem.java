package com.itheima.xchat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.xchat.R;
import com.itheima.xchat.model.ContactItemBean;
import com.itheima.xchat.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.widget
 *  @文件名:   ContactItem
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/8 14:14
 *  @描述：    TODO
 */
public class ContactItem extends RelativeLayout {
    private static final String TAG = "ContactItem";
    @BindView(R.id.tv_firstchar)
    TextView mTvFirstchar;
    @BindView(R.id.img_avatar)
    ImageView mImgAvatar;
    @BindView(R.id.tv_contact_name)
    TextView mTvContactName;

    public ContactItem(Context context) {
        this(context, null);
    }

    public ContactItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_contact, this);
        ButterKnife.bind(this,this);
    }

    public void onBindData(ContactItemBean itemBean) {
        String firstLetter = itemBean.getFirstLetter();
        String contactName = itemBean.getContactName();

        mTvContactName.setText(contactName);

        if (itemBean.isShowFirstLetter()){
            mTvFirstchar.setVisibility(View.VISIBLE);
            mTvFirstchar.setText(firstLetter);
        }else{
            mTvFirstchar.setVisibility(View.GONE);
        }
    }

}

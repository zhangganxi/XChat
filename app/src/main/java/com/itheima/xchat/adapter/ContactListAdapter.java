package com.itheima.xchat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.xchat.model.ContactItemBean;
import com.itheima.xchat.utils.UIUtils;
import com.itheima.xchat.widget.ContactItem;

import java.util.List;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.adapter
 *  @文件名:   ContactListAdapter
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/8 14:11
 *  @描述：    TODO
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHodler> {
    private static final String TAG = "ContactListAdapter";

    private List<ContactItemBean> mContacts;

    public ContactListAdapter(List<ContactItemBean> contacts){
        this.mContacts = contacts;
    }

    @Override
    public ContactListViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactListViewHodler(new ContactItem(UIUtils.getContext()));
    }

    @Override
    public void onBindViewHolder(ContactListViewHodler holder, final int position) {

        holder.mContactView.onBindData(mContacts.get(position));

        holder.mContactView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongClickItemListener != null) {
                    mOnLongClickItemListener.onLongClick(mContacts.get(position).getContactName());
                }
                return true;
            }
        });
        holder.mContactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItemListener != null){
                    mOnClickItemListener.onClickItem(mContacts.get(position).getContactName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mContacts == null){
            return 0;
        }
        return mContacts.size();
    }

    public class ContactListViewHodler extends RecyclerView.ViewHolder{

        private ContactItem mContactView;

        public ContactListViewHodler(ContactItem itemView) {
            super(itemView);
            mContactView = itemView;
        }
    }


    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener){
        this.mOnClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener{
        void onClickItem(String username);
    }

    private OnLongClickItemListener mOnLongClickItemListener;

    public void setOnLongItemListener(OnLongClickItemListener onLongItemListener){
        this.mOnLongClickItemListener = onLongItemListener;
    }

    public interface OnLongClickItemListener{
        void onLongClick(String username);
    }
}

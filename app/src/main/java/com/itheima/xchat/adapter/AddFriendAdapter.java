package com.itheima.xchat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.itheima.xchat.model.AddFriendBean;
import com.itheima.xchat.utils.UIUtils;
import com.itheima.xchat.widget.AddFriendItemView;

import java.util.List;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.adapter
 *  @文件名:   AddFriendAdapter
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/13 22:04
 *  @描述：    TODO
 */
public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.AddFriendItemViewHodler> {
    private static final String TAG = "AddFriendAdapter";

    private List<AddFriendBean> mAddFriendBeen;

    public AddFriendAdapter(List<AddFriendBean> addFriends){
        this.mAddFriendBeen = addFriends;
    }

    @Override
    public AddFriendItemViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddFriendItemViewHodler(new AddFriendItemView(UIUtils.getContext()));
    }

    @Override
    public void onBindViewHolder(AddFriendItemViewHodler holder, int position) {
        holder.mAddFriendItemView.bindData(mAddFriendBeen.get(position));

    }

    @Override
    public int getItemCount() {
        return mAddFriendBeen.size();
    }

    public class AddFriendItemViewHodler extends RecyclerView.ViewHolder{

        private AddFriendItemView mAddFriendItemView;

        public AddFriendItemViewHodler(AddFriendItemView itemView) {
            super(itemView);
            this.mAddFriendItemView = itemView;
        }
    }




}

package com.itheima.xchat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.itheima.xchat.R;
import com.itheima.xchat.factory.FragmentFactory;
import com.itheima.xchat.utils.UIUtils;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.adapter
 *  @文件名:   MainViewPagerAdapter
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/2 12:21
 *  @描述：    TODO
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MainViewPagerAdapter";
    private int[] mImageResId;

    public MainViewPagerAdapter(FragmentManager fm, int[] imageResId) {
        super(fm);
        this.mImageResId = imageResId;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        if (mImageResId == null){
            return 0;
        }
        return mImageResId.length;
    }


    public View getTabView(int position){
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.tab_view, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_tab);
        textView.setBackgroundResource(mImageResId[position]);
        return view;
    }
}


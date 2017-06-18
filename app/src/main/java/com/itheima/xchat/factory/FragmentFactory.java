package com.itheima.xchat.factory;

import com.itheima.xchat.base.BaseFragment;
import com.itheima.xchat.ui.fragment.ContactFragment;
import com.itheima.xchat.ui.fragment.FindFragment;
import com.itheima.xchat.ui.fragment.MeFragment;
import com.itheima.xchat.ui.fragment.MessageFragment;

import java.util.HashMap;
import java.util.Map;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.factory
 *  @文件名:   FragmentFactory
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/2 12:33
 *  @描述：    TODO
 */
public class FragmentFactory {
    private static final String TAG = "FragmentFactory";
    private static final int FRAGMENT_MESSAGE = 0;
    private static final int FRAGMENT_CONTACT = 1;
    private static final int FRAGMENT_FIND = 2;
    private static final int FRAGMENT_ME = 3;

    public static Map<Integer,BaseFragment> map = new HashMap<>();

    public static BaseFragment createFragment(int position){
        BaseFragment fragment = null;

        if (map.containsKey(position)){
            return map.get(position);
        }

       switch (position) {
            case FRAGMENT_MESSAGE:
                fragment = new MessageFragment();
                break;
           case FRAGMENT_CONTACT:
                fragment = new ContactFragment();
                break;
           case FRAGMENT_FIND:
                fragment = new FindFragment();
                break;
           case FRAGMENT_ME:
                fragment = new MeFragment();
                break;
            default:

                break;
        }

        map.put(position,fragment);

        return fragment;
    }

}

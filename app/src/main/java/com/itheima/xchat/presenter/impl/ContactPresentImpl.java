package com.itheima.xchat.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.itheima.xchat.database.DataBaseManager;
import com.itheima.xchat.model.Contact;
import com.itheima.xchat.model.ContactItemBean;
import com.itheima.xchat.presenter.ContactPresent;
import com.itheima.xchat.ui.fragment.ContactFragment;
import com.itheima.xchat.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.presenter.impl
 *  @文件名:   ContactPresentImpl
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/8 13:31
 *  @描述：    TODO
 */
public class ContactPresentImpl implements ContactPresent {
    private static final String TAG = "ContactPresentImpl";

    private ContactFragment mContactFragment;
    private List<ContactItemBean> mContactItemBeanList;

    public ContactPresentImpl(ContactFragment contactFragment) {
        mContactItemBeanList = new ArrayList<ContactItemBean>();
        this.mContactFragment = contactFragment;
    }

    @Override
    public void loadContacts() {
        ThreadUtils.runOnBackgoundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    DataBaseManager.getInstance().clearContacts();

                    List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    getContactItemList(contacts);
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mContactFragment  != null) {
                                mContactFragment.onLoadContactSuccess();
                            }
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mContactFragment  != null) {
                                mContactFragment.onLoadContactFailed();
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public void refresh() {
        //清空数据
        mContactItemBeanList.clear();
        //重新加载数据
        loadContacts();
    }

    @Override
    public void delContact(final String username) {
        ThreadUtils.runOnBackgoundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(username);
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                        if (mContactFragment != null) {
                            mContactFragment.delContactSuccess();
                        }
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mContactFragment != null) {
                                mContactFragment.delContactFailed();
                            }
                        }
                    });
                }
            }
        });


    }

    @Override
    public void unBind() {
        mContactFragment = null;
    }

    private void getContactItemList(List<String> contacts) {
        Collections.sort(contacts, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.charAt(0) - o2.charAt(0);  //升序摆列
            }
        });

        for (int i = 0; i < contacts.size(); i++) {
            ContactItemBean bean = new ContactItemBean();
            String firstLetter = String.valueOf(contacts.get(i).charAt(0)).toUpperCase();
            String contactName = contacts.get(i);
            bean.setFirstLetter(firstLetter);
            bean.setContactName(contactName);

            boolean isShowFirstLetter = true;

            if (i > 0) {
                String last = String.valueOf(contacts.get(i-1).charAt(0)).toUpperCase();
                if (firstLetter.equals(last)) {
                    isShowFirstLetter = false;
                }
            }
            bean.setShowFirstLetter(isShowFirstLetter);

            Contact contact = new Contact();
            contact.setUserName(contactName);
            DataBaseManager.getInstance().saveContact(contact);

            mContactItemBeanList.add(bean);
        }
    }

    public List<ContactItemBean> getContacts() {
        return mContactItemBeanList;
    }
}

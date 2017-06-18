package com.itheima.xchat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.xchat.model.Contact;
import com.itheima.xchat.model.DaoMaster;
import com.itheima.xchat.model.DaoSession;
import com.itheima.xchat.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  XChat 
 *  @包名：    com.itheima.xchat.database
 *  @文件名:   DataBaseManager
 *  @创建者:   zhangganxi
 *  @创建时间:  2017/4/15 17:52
 *  @描述：    TODO
 */
public class DataBaseManager {
    private static final String TAG = "DataBaseManager";

    private static DataBaseManager mDataBaseManager;
    private DaoSession mDaoSession;

    private DataBaseManager(){};

    public static DataBaseManager getInstance() {
        if (mDataBaseManager == null){
            synchronized (DataBaseManager.class){
                if (mDataBaseManager == null){
                    mDataBaseManager = new DataBaseManager();
                }
            }
        }
        return mDataBaseManager;
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, Constants.Database.DATABASE_NAME, null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        mDaoSession = daoMaster.newSession();
    }


    //保存或者更新数据
    public void saveContact(Contact contact) {
        mDaoSession.getContactDao().save(contact);  //在源码中当,有该联系人,就更新该联系人,当没有该联系人就插入数据
    }

    public List<String> getContacts() {
        List<String> contacts = new ArrayList<>();
        List<Contact> list = mDaoSession.getContactDao().queryBuilder().list();
        for (Contact contact:list){
            String userName = contact.getUserName();
            contacts.add(userName);
        }
        return contacts;
    }

    //清空数据好友
    public void clearContacts() {
        mDaoSession.getContactDao().deleteAll();
    }
}

package com.blockadm.common.greeddao.manager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.blockadm.common.greeddao.dao.DaoMaster;
import com.blockadm.common.greeddao.dao.DaoSession;


/**
 * 创建者      lj DELL
 * 创建时间    2018/9/10 11:41
 * 描述        ${TODO}
 * <p>
 * 更新者      $Author$
 * <p>
 * 更新时间    $Date$
 * 更新描述    ${TODO}
 */

public class GreenDaoManager {

    private static volatile  GreenDaoManager manager = null;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    private GreenDaoManager(Context context){
        if (manager  == null){
            //创建数据库shop.db
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "shop.db", null);
            //获取可写数据库
            SQLiteDatabase db = helper.getWritableDatabase();
            //获取数据库对象
            mDaoMaster = new DaoMaster(db);
            //获取dao对象管理者
            mDaoSession = mDaoMaster.newSession();
        }
    }


    public static GreenDaoManager getInstance(Context context){
        if (manager == null){
            synchronized (GreenDaoManager.class){
                if (manager == null){
                    manager = new GreenDaoManager(context.getApplicationContext());
                }
            }
        }
        return manager;
    }


    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}

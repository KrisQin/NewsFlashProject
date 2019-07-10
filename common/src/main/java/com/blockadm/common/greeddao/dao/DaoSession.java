package com.blockadm.common.greeddao.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.blockadm.common.greeddao.bean.HistoryNickNameIdentifierInfo;

import com.blockadm.common.greeddao.dao.HistoryNickNameIdentifierInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig historyNickNameIdentifierInfoDaoConfig;

    private final HistoryNickNameIdentifierInfoDao historyNickNameIdentifierInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        historyNickNameIdentifierInfoDaoConfig = daoConfigMap.get(HistoryNickNameIdentifierInfoDao.class).clone();
        historyNickNameIdentifierInfoDaoConfig.initIdentityScope(type);

        historyNickNameIdentifierInfoDao = new HistoryNickNameIdentifierInfoDao(historyNickNameIdentifierInfoDaoConfig, this);

        registerDao(HistoryNickNameIdentifierInfo.class, historyNickNameIdentifierInfoDao);
    }
    
    public void clear() {
        historyNickNameIdentifierInfoDaoConfig.clearIdentityScope();
    }

    public HistoryNickNameIdentifierInfoDao getHistoryNickNameIdentifierInfoDao() {
        return historyNickNameIdentifierInfoDao;
    }

}
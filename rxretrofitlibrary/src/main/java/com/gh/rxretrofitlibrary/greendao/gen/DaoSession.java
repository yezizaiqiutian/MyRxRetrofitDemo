package com.gh.rxretrofitlibrary.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.gh.rxretrofitlibrary.http.cookie.CookieResulte;

import com.gh.rxretrofitlibrary.greendao.gen.CookieResulteDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cookieResulteDaoConfig;

    private final CookieResulteDao cookieResulteDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cookieResulteDaoConfig = daoConfigMap.get(CookieResulteDao.class).clone();
        cookieResulteDaoConfig.initIdentityScope(type);

        cookieResulteDao = new CookieResulteDao(cookieResulteDaoConfig, this);

        registerDao(CookieResulte.class, cookieResulteDao);
    }
    
    public void clear() {
        cookieResulteDaoConfig.clearIdentityScope();
    }

    public CookieResulteDao getCookieResulteDao() {
        return cookieResulteDao;
    }

}

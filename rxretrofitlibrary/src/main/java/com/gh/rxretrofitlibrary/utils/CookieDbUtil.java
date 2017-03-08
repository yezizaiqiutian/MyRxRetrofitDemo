package com.gh.rxretrofitlibrary.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gh.rxretrofitlibrary.RxRetrofitApp;
import com.gh.rxretrofitlibrary.greendao.gen.CookieResulteDao;
import com.gh.rxretrofitlibrary.greendao.gen.DaoMaster;
import com.gh.rxretrofitlibrary.greendao.gen.DaoSession;
import com.gh.rxretrofitlibrary.http.cookie.CookieResulte;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author: gh
 * @description: 数据缓存
 * 数据库工具类-geendao运用
 * @date: 2017/2/28 09:40
 * @note:
 */

public class CookieDbUtil {

    private static CookieDbUtil db;
    private final static String dbName = "tests_db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    private CookieDbUtil() {
        context = RxRetrofitApp.getApplication();
        openHelper = new DaoMaster.DevOpenHelper(context, dbName);
    }

    public static CookieDbUtil getInstance() {
        if (db == null) {
            synchronized (CookieDbUtil.class) {
                if (db == null) {
                    db = new CookieDbUtil();
                }
            }
        }
        return db;
    }

    /**
     * 获取可读数据库
     * @return
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 增
     * @param info
     */
    public void saveCookie(CookieResulte info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao dao = daoSession.getCookieResulteDao();
        dao.insert(info);
    }

    /**
     * 删
     * @param info
     */
    public void deleteCookie(CookieResulte info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao dao = daoSession.getCookieResulteDao();
        dao.delete(info);
    }

    /**
     * 改
     * @param info
     */
    public void updateCookie(CookieResulte info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao dao = daoSession.getCookieResulteDao();
        dao.update(info);
    }

    /**
     * 查    根据Url
     * @param url
     * @return
     */
    public CookieResulte queryCookieBy(String url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao dao = daoSession.getCookieResulteDao();
        QueryBuilder<CookieResulte> qb = dao.queryBuilder();
        qb.where(CookieResulteDao.Properties.Url.eq(url));
        List<CookieResulte> list = qb.list();
        if (list.isEmpty())
            return null;
         else
            return list.get(0);
    }

    /**
     * 查    所有
     * @return
     */
    public List<CookieResulte> queryCookieAll() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao dao = daoSession.getCookieResulteDao();
        QueryBuilder<CookieResulte> qb = dao.queryBuilder();
        return qb.list();
    }

}

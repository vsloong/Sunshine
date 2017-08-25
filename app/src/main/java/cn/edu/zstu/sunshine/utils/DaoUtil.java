package cn.edu.zstu.sunshine.utils;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseApplication;
import cn.edu.zstu.sunshine.greendao.DaoMaster;
import cn.edu.zstu.sunshine.greendao.DaoSession;

/**
 * GreenDao的工具类
 * Created by CooLoongWu on 2017-8-25 10:24.
 */

public class DaoUtil {

    private static DaoUtil daoUtil;
    private static DaoSession daoSession;
    private static final String DB_NAME = AppConfig.getDBName();

    public static DaoUtil getInstance() {
        if (daoUtil == null) {
            daoUtil = new DaoUtil();
        }
        return daoUtil;
    }

    private DaoUtil() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.getAppContext(), DB_NAME);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

    public DaoSession getSession() {
        return daoSession;
    }
}

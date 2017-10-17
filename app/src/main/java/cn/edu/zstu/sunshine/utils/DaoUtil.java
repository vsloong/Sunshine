package cn.edu.zstu.sunshine.utils;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseApplication;
import cn.edu.zstu.sunshine.entity.CampusCard;
import cn.edu.zstu.sunshine.entity.Exam;
import cn.edu.zstu.sunshine.entity.Network;
import cn.edu.zstu.sunshine.greendao.CampusCardDao;
import cn.edu.zstu.sunshine.greendao.DaoMaster;
import cn.edu.zstu.sunshine.greendao.DaoSession;
import cn.edu.zstu.sunshine.greendao.ExamDao;
import cn.edu.zstu.sunshine.greendao.NetworkDao;

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

    public static <T> void insertOrUpdate(List<T> data) {
        if (data != null && !data.isEmpty()) {
            String[] name = data.get(0).getClass().getName().split("\\.");
            String className = name[name.length - 1];
            Logger.e("泛型名称：" + className);
            switch (className) {
                case "CampusCard":
                    insertCampusCard((List<CampusCard>) data);
                    break;
                case "Network":
                    insertNetwork((Network) data.get(0));
                    break;
                case "Exam":
                    insertExam((List<Exam>) data);
                    break;
                default:
                    break;
            }
        } else {
            Logger.e("泛型类型：" + "null");
        }
    }

    /**
     * 存储饭卡数据
     *
     * @param data 饭卡消费列表数据
     */
    private static void insertCampusCard(final List<CampusCard> data) {
        daoSession.getCampusCardDao().getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (CampusCard card : data) {
                    CampusCard campusCard = daoSession
                            .getCampusCardDao()
                            .queryBuilder()
                            .where(
                                    CampusCardDao.Properties.UserId.eq(AppConfig.getDefaultUserId()),
                                    CampusCardDao.Properties.Time.eq(card.getTime()))
                            .unique();
                    if (campusCard == null) {
                        card.complete();
                        daoSession.getCampusCardDao().insert(card);
                        Logger.e("插入新的饭卡消费数据");
                    } else {
                        Logger.e("饭卡消费数据已存在");
                    }
                }
                //存储完毕刷新页面，因为在线程中，所以需要使用EventBus来通知
                EventBus.getDefault().post(new CampusCard());
            }
        });
    }

    /**
     * 存储考试数据
     *
     * @param data 考试列表数据
     */
    private static void insertExam(final List<Exam> data) {
        daoSession.getCampusCardDao().getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (Exam exam : data) {
                    Exam oldExam = daoSession
                            .getExamDao()
                            .queryBuilder()
                            .where(
                                    ExamDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                                    //ExamDao.Properties.Time.eq(exam.getTime()))
                            )
                            .unique();
                    if (oldExam == null) {
                        exam.complete();
                        daoSession.getExamDao().insert(exam);
                        Logger.e("插入新的考试数据");
                    } else {
                        Logger.e("考试数据已存在");
                        daoSession.getExamDao().update(
                                EntityCopyUtil.copyExam(oldExam, exam)
                        );
                    }
                }
                //存储完毕刷新页面，因为在线程中，所以需要使用EventBus来通知
                EventBus.getDefault().post(new Exam());
            }
        });
    }

    /**
     * 插入或者更新网费数据
     *
     * @param network 网费信息实体
     */
    private static void insertNetwork(Network network) {
        Network networkData = daoSession.getNetworkDao().queryBuilder()
                .where(NetworkDao.Properties.UserId.eq(AppConfig.getDefaultUserId()))
                .build()
                .unique();
        if (networkData != null) {
            daoSession.getNetworkDao().update(
                    EntityCopyUtil.copyNetwork(networkData, network)
            );
            Logger.e("网费数据更新");
        } else {
            daoSession.getNetworkDao().insert(network);
            Logger.e("网费数据插入");
        }
        EventBus.getDefault().post(network);
    }
}

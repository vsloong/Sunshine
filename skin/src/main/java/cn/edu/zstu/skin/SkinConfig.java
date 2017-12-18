package cn.edu.zstu.skin;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 皮肤的配置类，只需Context，跟其他类无关
 * Created by CooLoongWu on 2017-12-4 16:54.
 */

class SkinConfig {

    private static final String TAG = "SkinConfig";
    private static final String SKIN_SP_NAME = "skin_sp_name";
    private static final String SKIN_PATH = "skin_path";
    private static final String SKIN_PKG = "skin_pkg";
    private static final String SKIN_TIME_EFFECTIVE = "skin_time_effective";
    private static final String SKIN_TIME_EXPIRY = "skin_time_expiry";

    static final String SKIN_CONFIG_CHANGED_ERROR = "该位置不存在换肤包";

    static final String SKIN_PREFIX = "skin"; //换肤资源的前缀

    private SharedPreferences sp;
    private Context context;

    SkinConfig(Context context) {
        this.context = context;
        this.sp = getSP(context);
    }

    private SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(SKIN_SP_NAME, Context.MODE_PRIVATE);
    }

    boolean setSkinConfig(String skinPath, String skinEffectiveTime, String skinExpiryTime) throws Exception {

        boolean isSucceed = setSkinPath(skinPath) &&
                setSkinEffectiveTime(skinEffectiveTime) &&
                setSkinExpiryTime(skinExpiryTime);

        if (isSucceed) {
            return true;
        } else {
            throw new Exception("您指定的位置不存在换肤包，已清空所有换肤配置");
        }
    }

    String getSkinPath() {
        return sp.getString(SkinConfig.SKIN_PATH, "");
    }

    private boolean setSkinPath(String skinPath) {
        if (new File(skinPath).exists()) {

            PackageManager pkgManager = context.getPackageManager();
            PackageInfo pkgInfo = pkgManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            Log.e(TAG, "该位置（" + skinPath + "）存在皮肤包，且包名为：" + pkgInfo.packageName);

            return sp.edit().putString(SkinConfig.SKIN_PATH, skinPath).commit() &&
                    setSkinPkgName(pkgInfo.packageName);
        } else {
            Log.e(TAG, "该位置（" + skinPath + ")不存在皮肤包，已清空所有配置");
            clear();
            return false;
        }
    }

    String getSkinPkgName() {
        return sp.getString(SkinConfig.SKIN_PKG, "");
    }

    private boolean setSkinPkgName(String skinPkgName) {
        return sp.edit().putString(SkinConfig.SKIN_PKG, skinPkgName).commit();
    }

    long getSkinEffectiveTime() {
        return sp.getLong(SkinConfig.SKIN_TIME_EFFECTIVE, 0);
    }

    private boolean setSkinEffectiveTime(String skinEffectiveTime) {
        return sp.edit().putLong(SkinConfig.SKIN_TIME_EFFECTIVE, getMillis(skinEffectiveTime)).commit();
    }

    long getSkinExpiryTime() {
        return sp.getLong(SkinConfig.SKIN_TIME_EXPIRY, 0);
    }

    private boolean setSkinExpiryTime(String skinExpiryTime) {
        return sp.edit().putLong(SkinConfig.SKIN_TIME_EXPIRY, getMillis(skinExpiryTime)).commit();
    }

    void clear() {
        sp.edit().clear().apply();
    }

    boolean isConfigInvalid() {
        return getSkinPkgName().isEmpty() ||
                getSkinPath().isEmpty() ||
                getSkinEffectiveTime() == 0 ||
                getSkinExpiryTime() == 0;
    }

    /**
     * 把传来的"yyyy-MM-dd HH:mm:ss"转换为毫秒数
     *
     * @param dataStr 时间字符串
     * @return 系统毫秒数
     */
    private long getMillis(String dataStr) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date date = df.parse(dataStr);
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

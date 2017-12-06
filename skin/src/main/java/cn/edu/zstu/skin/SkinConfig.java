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
 * 皮肤的配置类
 * Created by CooLoongWu on 2017-12-4 16:54.
 */

class SkinConfig {

    private static final String SKIN_SP_NAME = "skin_sp_name";
    private static final String SKIN_PATH = "skin_path";
    private static final String SKIN_PKG = "skin_pkg";
    private static final String SKIN_TIME_EFFECTIVE = "skin_time_effective";
    private static final String SKIN_TIME_EXPIRY = "skin_time_expiry";
    private static final String SKIN_SUFFIX = "skin_suffix";

    public static final String SKIN_PREFIX = "skin:";

    private final String TAG = "SkinConfig";

    private SharedPreferences sp;

    SkinConfig(Context context) {
        this.sp = getSP(context);
    }

    private SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(SKIN_SP_NAME, Context.MODE_PRIVATE);
    }

    void setSkinConfig(String skinPath, String skinEffectiveTime, String skinExpiryTime) {
        setSkinPath(skinPath);
        setSkinEffectiveTime(skinEffectiveTime);
        setSkinExpiryTime(skinExpiryTime);
    }

    String getSkinPath() {
        return sp.getString(SkinConfig.SKIN_PATH, "");
    }

    private void setSkinPath(String skinPath) {
        if (new File(skinPath).exists()) {
            sp.edit().putString(SkinConfig.SKIN_PATH, skinPath).apply();
            PackageManager pkgManager = SkinManager.getInstance().getContext().getPackageManager();
            PackageInfo pkgInfo = pkgManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            Log.e(TAG, "该位置（" + skinPath + "）存在皮肤包，且包名为：" + pkgInfo.packageName);
            setSkinPkgName(pkgInfo.packageName);
        } else {
            Log.e(TAG, "该位置（" + skinPath + ")没有皮肤");
        }
    }

    String getSkinPkgName() {
        return sp.getString(SkinConfig.SKIN_PKG, "");
    }

    private void setSkinPkgName(String skinPkgName) {
        sp.edit().putString(SkinConfig.SKIN_PKG, skinPkgName).apply();
    }

    long getSkinEffectiveTime() {
        return sp.getLong(SkinConfig.SKIN_TIME_EFFECTIVE, 0);
    }

    private void setSkinEffectiveTime(String skinEffectiveTime) {
        sp.edit().putLong(SkinConfig.SKIN_TIME_EFFECTIVE, getMillis(skinEffectiveTime)).apply();
    }

    long getSkinExpiryTime() {
        return sp.getLong(SkinConfig.SKIN_TIME_EXPIRY, 0);
    }

    private void setSkinExpiryTime(String skinExpiryTime) {
        sp.edit().putLong(SkinConfig.SKIN_TIME_EXPIRY, getMillis(skinExpiryTime)).apply();
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

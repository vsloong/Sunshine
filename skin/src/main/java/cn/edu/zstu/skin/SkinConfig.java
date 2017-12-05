package cn.edu.zstu.skin;

import android.content.Context;
import android.content.SharedPreferences;

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

    private static SkinConfig skinConfig;
    private SharedPreferences sp;

    static SkinConfig getInstance() {
        if (skinConfig == null) {
            synchronized (SkinConfig.class) {
                if (skinConfig == null) {
                    skinConfig = new SkinConfig();
                }
            }
        }
        return skinConfig;
    }

    private SkinConfig() {

    }

    void init(Context context) {
        this.sp = getSP(context);
    }

    void setSkinConfig(String skinPath, String skinPkgName, String skinEffectiveTime, String skinExpiryTime) {
        setSkinPath(skinPath);
        setSkinPkgName(skinPkgName);
        setSkinEffectiveTime(skinEffectiveTime);
        setSkinExpiryTime(skinExpiryTime);
    }

    private SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(SKIN_SP_NAME, Context.MODE_PRIVATE);
    }

    String getSkinPath() {
        return sp.getString(SkinConfig.SKIN_PATH, "");
    }

    private void setSkinPath(String skinPath) {
        sp.edit().putString(SkinConfig.SKIN_PATH, skinPath).apply();
    }

    String getSkinPkgName() {
        return sp.getString(SkinConfig.SKIN_PKG, "");
    }

    private void setSkinPkgName(String skinPkgName) {
        sp.edit().putString(SkinConfig.SKIN_PKG, skinPkgName).apply();
    }

    private String getSkinEffectiveTime() {
        return sp.getString(SkinConfig.SKIN_TIME_EFFECTIVE, "");
    }

    private void setSkinEffectiveTime(String skinEffectiveTime) {
        sp.edit().putString(SkinConfig.SKIN_TIME_EFFECTIVE, skinEffectiveTime).apply();
    }

    private String getSkinExpiryTime() {
        return sp.getString(SkinConfig.SKIN_TIME_EXPIRY, "");
    }

    private void setSkinExpiryTime(String skinExpiryTime) {
        sp.edit().putString(SkinConfig.SKIN_TIME_EXPIRY, skinExpiryTime).apply();
    }

    void clear() {
        sp.edit().clear().apply();
    }

    boolean isConfigInvalid() {
        return getSkinPkgName().isEmpty() ||
                getSkinPath().isEmpty() ||
                getSkinEffectiveTime().isEmpty() ||
                getSkinExpiryTime().isEmpty();
    }
}

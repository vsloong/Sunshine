package cn.edu.zstu.skin;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 皮肤的配置类
 * Created by CooLoongWu on 2017-12-4 16:54.
 */

public class SkinConfig {

    private static final String SKIN_SP_NAME = "skin_sp_name";
    private static final String SKIN_PATH = "skin_path";
    private static final String SKIN_PKG = "skin_pkg";
    private static final String SKIN_SUFFIX = "skin_suffix";

    public static final String SKIN_PREFIX = "skin:";

    String getSkinPath(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SKIN_SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(SkinConfig.SKIN_PATH, "");
    }

    void setSkinPath(Context context, String skinPath) {
        SharedPreferences sp = context.getSharedPreferences(SKIN_SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(SkinConfig.SKIN_PATH, skinPath).apply();
    }

    String getSkinPkgName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SKIN_SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(SkinConfig.SKIN_PKG, "");
    }

    void setSkinPkgName(Context context, String skinPkgName) {
        SharedPreferences sp = context.getSharedPreferences(SKIN_SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(SkinConfig.SKIN_PKG, skinPkgName).apply();
    }


    void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SKIN_SP_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    boolean isConfigInvalid(Context context) {
        return getSkinPkgName(context).isEmpty() ||
                getSkinPath(context).isEmpty();
    }
}

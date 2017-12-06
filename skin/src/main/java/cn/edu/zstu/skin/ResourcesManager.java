package cn.edu.zstu.skin;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * 资源管理类
 * Created by CooLoongWu on 2017-12-5 14:18.
 */

public class ResourcesManager {

    private final String TAG = "ResourcesManager";

    private static final String RES_TYPE_DRAWABLE = "drawable";
    private static final String RES_TYPE_MIPMAP = "mipmap";
    private static final String RES_TYPE_COLOR = "color";

    private Resources resources;


    ResourcesManager(Resources resources) {
        this.resources = resources;
    }

    public Drawable getDrawableByName(String name) {
        try {
            name = appendSuffix(name);
            return resources.getDrawable(
                    resources.getIdentifier(
                            name,
                            RES_TYPE_DRAWABLE,
                            SkinManager.getInstance().getSkinConfig().getSkinPkgName()));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "获取drawable资源失败");
            return null;
        }
    }

    public Drawable getMipmapByName(String name) {
        try {
            name = appendSuffix(name);
            return resources.getDrawable(
                    resources.getIdentifier(
                            name,
                            RES_TYPE_MIPMAP,
                            SkinManager.getInstance().getSkinConfig().getSkinPkgName()));

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "获取mipmap资源失败");
            return null;
        }
    }

    private String appendSuffix(String name) {
//        if (!TextUtils.isEmpty(mSuffix))
//            return name += "_" + mSuffix;
        return name;
    }
}

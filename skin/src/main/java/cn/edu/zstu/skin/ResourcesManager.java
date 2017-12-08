package cn.edu.zstu.skin;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * 资源管理类
 * <p>
 * 【！！对外开放！！】
 * 得到外部皮肤资源管理对象，比如外部是ListView，RecyclerView的话
 * 可以将ResourcesManager开放出去供开发者自行进行资源的替换
 * <p>
 * Created by CooLoongWu on 2017-12-5 14:18.
 */

public class ResourcesManager {

    private static final String TAG = "ResourcesManager";
    private static final String RES_TYPE_DRAWABLE = "drawable";
    private static final String RES_TYPE_MIPMAP = "mipmap";
    private static final String RES_TYPE_COLOR = "color";

    private Resources resources;
    private SkinConfig skinConfig;

    ResourcesManager(Resources resources, SkinConfig skinConfig) {
        this.resources = resources;
        this.skinConfig = skinConfig;
    }

    public Drawable getDrawableByName(String name) {
        try {
            return resources.getDrawable(
                    resources.getIdentifier(
                            name,
                            RES_TYPE_DRAWABLE,
                            skinConfig.getSkinPkgName()));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Drawable getMipmapByName(String name) {
        try {
            return resources.getDrawable(
                    resources.getIdentifier(
                            name,
                            RES_TYPE_MIPMAP,
                            skinConfig.getSkinPkgName()));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

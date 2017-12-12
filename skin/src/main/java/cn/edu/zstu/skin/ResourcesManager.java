package cn.edu.zstu.skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

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
            return resources.getDrawable(identifier(name, RES_TYPE_DRAWABLE));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "获取drawable资源" + name + "失败");
            e.printStackTrace();
            return null;
        }
    }

    public Drawable getMipmapByName(String name) {
        try {
            return resources.getDrawable(identifier(name, RES_TYPE_MIPMAP));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "获取mipmap资源" + name + "失败");
            e.printStackTrace();
            return null;
        }
    }

    public int getColor(String name) {
        try {
            return resources.getColor(identifier(name, RES_TYPE_COLOR));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "获取color资源" + name + "失败");
            return -1;
        }
    }


    public ColorStateList getColorStateList(String name) {
        try {
            return resources.getColorStateList(identifier(name, RES_TYPE_COLOR));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "获取colorStateList资源" + name + "失败");
            e.printStackTrace();
            return null;
        }
    }

    private int identifier(String name, String type) {
        return resources.getIdentifier(
                name,
                type,
                skinConfig.getSkinPkgName());
    }
}

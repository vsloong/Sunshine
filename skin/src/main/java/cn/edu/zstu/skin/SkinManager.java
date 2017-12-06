package cn.edu.zstu.skin;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;

/**
 * 节日换肤的管理类
 * <p>
 * 预想的使用方法：
 * 1、在需要换肤的Activity的onCreate中注册，onDestroy中取消注册
 * 2、设置一次换肤的参数【包括皮肤所在位置，皮肤生效时间，皮肤过期时间】，并立即换肤或恢复默认皮肤
 * 3、暂无，能简单就尽量简单
 * <p>
 * Created by CooLoongWu on 2017-12-4 16:20.
 */

public class SkinManager {

    private static SkinManager skinManager;
    private final String TAG = "SkinManager";

    private Context context;
    private ResourcesManager resourcesManager;
    private SkinConfig skinConfig;
    private Activity activity;

    public static SkinManager getInstance() {
        if (skinManager == null) {
            synchronized (SkinManager.class) {
                if (skinManager == null) {
                    skinManager = new SkinManager();
                }
            }
        }
        return skinManager;
    }

    private SkinManager() {

    }

    private void init() {
        //只做一次初始化动作
        if (null == skinConfig) {
            skinConfig = new SkinConfig(context);
        }
        if (null == resourcesManager) {
            try {
                setResourcesManager();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "外部皮肤资源管理对象创建失败");
            }
        }
    }

    /**
     * 声明，原本打算方法名为register，但是联想到后面可能不需要unregister所以更换方法名
     *
     * @param context 上下文
     */
    public void declare(Context context) {
        this.context = context.getApplicationContext();
        this.activity = (Activity) context;
        init();

        loadSkin();
    }

    /**
     * 设置换肤资源的路径以及皮肤生效时间及过期时间
     *
     * @param skinPath          皮肤包路径
     * @param skinEffectiveTime 生效时间
     * @param skinExpiryTime    过期时间
     */
    public void setSkinConfig(String skinPath, String skinEffectiveTime, String skinExpiryTime) {
        skinConfig.setSkinConfig(
                skinPath, skinEffectiveTime, skinExpiryTime
        );
    }

    Context getContext() {
        return context;
    }

    private boolean canLoadSkin() {
        //如果皮肤配置中没有这些选项那么就不去加载皮肤
        if (skinConfig.isConfigInvalid()) {
            Log.e(TAG, "换肤包配置信息不完善");
            return false;
        }

        //如果皮肤配置中时间已过期那么也不去加载皮肤
        long currentTime = System.currentTimeMillis();
        if (currentTime < skinConfig.getSkinEffectiveTime() ||
                currentTime > skinConfig.getSkinExpiryTime()) {
            Log.e(TAG, "皮肤不在有效期内或已过期");
            return false;
        }

        return true;
    }

    private void loadSkin() {
        if (canLoadSkin()) {
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            test2(viewGroup);
        }
    }

    private void test2(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            if (view instanceof ViewGroup) {
                test2((ViewGroup) view);
            } else {
                Log.e(TAG, "视图的TAG：" + view.getTag() + "；ID：" + view.getId());
                //根据TAG获取该控件类型，然后更换相应的皮肤【tag中需要有：src,图片名】
                for (SkinAttrType attrType : SkinAttrType.values()) {
                    if (attrType.getAttrType().equals("src")) {

                        attrType.applyNewAttr(view, "banner");
                    }
                }
            }
        }
    }


    /**
     * 实例化外部皮肤资源管理的对象
     *
     * @throws Exception 错误信息
     */
    private void setResourcesManager() throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);

        addAssetPath.invoke(assetManager, skinConfig.getSkinPath());
        Resources defaultResources = context.getResources();
        Resources resources = new Resources(
                assetManager,
                defaultResources.getDisplayMetrics(),
                defaultResources.getConfiguration());
        resourcesManager = new ResourcesManager(resources);
    }

    /**
     * 得到外部皮肤资源管理对象
     */
    ResourcesManager getResourcesManager() {
        return resourcesManager;
    }

    /**
     * 得到皮肤配置对象
     */
    SkinConfig getSkinConfig() {
        return skinConfig;
    }

    /**
     * 清除换肤包配置信息
     */
    public void clearSkinConfig() {
        skinConfig.clear();
    }
}

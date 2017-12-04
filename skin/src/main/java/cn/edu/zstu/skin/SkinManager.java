package cn.edu.zstu.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

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

    private Context context;
    private Resources resources;
    private SkinConfig skinConfig;

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

    /**
     * 声明，原本打算方法名为register，但是联想到这里可能不需要unregister所以更换方法名
     *
     * @param context 上下文
     */
    private void declare(Context context) {
        this.context = context;
        this.skinConfig = new SkinConfig();
    }

    public SkinConfig getSkinConfig() {
        return skinConfig;
    }

    private void loadSkin() throws Exception {
        //如果皮肤配置中没有这些选项那么就不去加载皮肤
        if (skinConfig.isConfigInvalid(context)) {
            return;
        }
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);

        addAssetPath.invoke(assetManager, skinConfig.getSkinPath(context));
        Resources defaultResources = context.getResources();
        resources = new Resources(
                assetManager,
                defaultResources.getDisplayMetrics(),
                defaultResources.getConfiguration());
    }

}

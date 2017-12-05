package cn.edu.zstu.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

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
    public void declare(Context context) {
        this.context = context.getApplicationContext();
        SkinConfig.getInstance().init(context);
    }

    public void setSkinConfig(String skinPath, String skinEffectiveTime, String skinExpiryTime) {
        SkinConfig.getInstance().setSkinConfig(
                skinPath, skinEffectiveTime, skinExpiryTime
        );
    }

    public Context getContext() {
        return context;
    }

    private void loadSkin() throws Exception {
        //如果皮肤配置中没有这些选项那么就不去加载皮肤
        if (SkinConfig.getInstance().isConfigInvalid())
            return;

        //如果皮肤配置中时间已过期那么也不去加载皮肤
        long currentTime = System.currentTimeMillis();
        if (currentTime < SkinConfig.getInstance().getSkinEffectiveTime() ||
                currentTime > SkinConfig.getInstance().getSkinExpiryTime())
            return;
        
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);

        addAssetPath.invoke(assetManager, SkinConfig.getInstance().getSkinPath());
        Resources defaultResources = context.getResources();
        Resources resources = new Resources(
                assetManager,
                defaultResources.getDisplayMetrics(),
                defaultResources.getConfiguration());

        resourcesManager = new ResourcesManager(resources);
    }

    public ResourcesManager getResources() {
        try {
            loadSkin();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "加载皮肤失败");
        }
        return resourcesManager;
    }
}

package cn.edu.zstu.skin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 节日换肤的管理类
 * <p>
 * 预想的使用方法：
 * 1、在视图加载完毕后【在onCreate方法的setContentView后直接使用apply方法即可】
 * 2、设置一次换肤的参数【包括皮肤所在位置，皮肤生效时间，皮肤过期时间】，并立即换肤或恢复默认皮肤
 * 3、暂无，能简单就尽量简单
 * <p>
 * Created by CooLoongWu on 2017-12-4 16:20.
 */

public class SkinManager {

    private static SkinManager skinManager;
    private final String TAG = "SkinManager";

    private ResourcesManager resourcesManager;
    private SkinConfig skinConfig;
    private List<SkinView> skinViews = new ArrayList<>();
    private OnSkinChangedListener listener;

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
     * 必须是首先要做的
     *
     * @param context 上下文
     */
    private void init(Context context, OnSkinChangedListener listener) {
        //只做一次初始化动作，必须先初始化SkinConfig，因为ResourcesManager中需要用到皮肤的包名等
        if (null == this.skinConfig) {
            skinConfig = new SkinConfig(context);
        }
        if (null == this.resourcesManager) {
            try {
                setResourcesManager(context, skinConfig);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "外部皮肤资源管理对象创建失败");
            }
        }

        if (null == listener) {
            if (null == this.listener)
                this.listener = OnSkinChangedListener.DEFAULT_ON_SKIN_CHANGED_LISTENER;
        } else {
            if (null == this.listener)
                this.listener = listener;
        }
    }

    /**
     * 【！！对外开放！！】
     * 得到外部皮肤资源管理对象，比如外部是ListView，RecyclerView的话
     * 可以将ResourcesManager开放出去供开发者自行进行资源的替换。
     * <p>
     * 可能需要搭配 isSkinEffective 方法使用
     * <p>
     * if(isSkinEffective(context)){ getResourcesManager(context).xxx() }
     */
    public ResourcesManager getResourcesManager(Context context) {
        init(context, null);
        return resourcesManager;
    }

    /**
     * 【！！对外开放！！】
     *
     * @param context 上下文
     * @return 皮肤是否存在及是否在有效换肤时间内
     */
    public boolean isSkinEffective(Context context) {
        init(context, null);
        return canChangeSkin();
    }

    /**
     * 【！！对外开放！！】
     * 设置换肤资源的路径以及皮肤生效时间及过期时间
     * <p>
     * 并且改变配置后会立即检查是否需要更换皮肤，如果需要则立即更换
     *
     * @param skinPath          皮肤包路径
     * @param skinEffectiveTime 生效时间
     * @param skinExpiryTime    过期时间
     */
    public void setSkinConfig(Context context, String skinPath,
                              String skinEffectiveTime,
                              String skinExpiryTime) {
        init(context, null);
        boolean isSuccess = skinConfig.setSkinConfig(
                skinPath, skinEffectiveTime, skinExpiryTime
        );

        if (isSuccess) {
            apply((Activity) context);
        }
    }

    /**
     * 【！！对外开放！！】
     * 换肤（如果有皮肤的配置并且皮肤在有效期内，那么就去更换皮肤）
     *
     * @param activity Activity
     */
    public void apply(Activity activity) {
        init(activity, null);
        findAndChangeTargetView(activity);
    }

    /**
     * 【！！对外开放！！】
     * 换肤（如果有皮肤的配置并且皮肤在有效期内，那么就去更换皮肤）
     *
     * @param activity Activity
     */
    public void apply(Activity activity, OnSkinChangedListener listener) {
        init(activity, listener);
        findAndChangeTargetView(activity);
    }

    /**
     * 得到Activity的根布局，然后需要循环遍历布局中的所有控件
     *
     * @param activity Activity
     */
    @SuppressLint("StaticFieldLeak")
    private void findAndChangeTargetView(Activity activity) {
        if (canChangeSkin()) {
            listener.onStart();
            final ViewGroup viewGroup = activity.findViewById(android.R.id.content);

            new AsyncTask<Void, Void, List<SkinView>>() {

                @Override
                protected List<SkinView> doInBackground(Void... voids) {
                    skinViews.clear();//记得每次查找时先将之前获取的换肤视图数据清空，否则会累计
                    getViewWithTag(viewGroup);
                    return skinViews;
                }

                @Override
                protected void onPostExecute(List<SkinView> skinViews) {
                    for (SkinView skinView : skinViews) {
                        changeViewWithTag(skinView);
                    }
                    listener.onSucceed();
                }
            }.execute();
        }
    }

    /**
     * @return 是否可以更换皮肤
     */
    private boolean canChangeSkin() {
        //如果皮肤配置中没有这些配置选项那么就不去加载皮肤
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

    /**
     * 循环遍历视图中的所有控件，把带有标志性tag的控件属性进行替换
     *
     * @param viewGroup viewGroup
     */
    private void getViewWithTag(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            if (view instanceof ViewGroup) {
                getViewWithTag((ViewGroup) view);
            } else {
                if (null == view.getTag())
                    continue;
                //Log.e(TAG, "视图的TAG：" + view.getTag() + "；ID：" + view.getId());
                //根据TAG获取该控件类型，然后更换相应的皮肤【tag中需要有：src,图片名】
                String tagStr = (String) view.getTag();
                String[] tagItems = tagStr.split("[|]");
                for (String tagItem : tagItems) {
                    //如果tag中有skin的前缀，那么说明这就是要换肤的控件了
                    if (tagItem.startsWith(SkinConfig.SKIN_PREFIX) && tagItem.contains(":")) {
                        String[] temp = tagItem.split("[:]");
                        if (null == temp || temp.length != 4)
                            continue;
                        //Log.e(TAG, "attrType：" + temp[1] + "；resType：" + temp[2] + "；resName：" + temp[3]);
                        //changeViewWithTag(view, temp[1], temp[2], temp[3]);
                        skinViews.add(new SkinView(view, temp[1], temp[2], temp[3]));
                    }
                }
            }
        }
    }

    /**
     * 根据设置tag中的attrType和resType、resName来更换皮肤
     *
     * @param skinView 要换肤的View
     */
    private void changeViewWithTag(SkinView skinView) {
        for (SkinAttrType skinAttrType : SkinAttrType.values()) {
            if (skinAttrType.getAttrType().equals(skinView.getAttrType())) {
                listener.onChanging(skinView);
                skinAttrType.applyNewAttr(resourcesManager,
                        skinView.getView(),
                        skinView.getResType(),
                        skinView.getResName());
            }
        }
    }

    /**
     * 实例化外部皮肤资源管理的对象
     *
     * @throws Exception 错误信息
     */
    private void setResourcesManager(Context context, SkinConfig skinConfig) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);

        addAssetPath.invoke(assetManager, skinConfig.getSkinPath());
        Resources defaultResources = context.getResources();
        Resources resources = new Resources(
                assetManager,
                defaultResources.getDisplayMetrics(),
                defaultResources.getConfiguration());
        resourcesManager = new ResourcesManager(resources, skinConfig);
    }
}

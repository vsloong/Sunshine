package cn.edu.zstu.skin;

import android.util.Log;

/**
 * 换肤配置改变监听器
 * Created by CooLoongWu on 2017-12-18 13:33.
 */

public interface OnSkinConfigChangedListener {

    OnSkinConfigChangedListener DEFAULT_SKIN_CONFIG_CHANGED_LISTENER = new DefaultOnSkinConfigChangedListener();

    void onSucceed();

    void onFail(String msg);

    class DefaultOnSkinConfigChangedListener implements OnSkinConfigChangedListener {
        @Override
        public void onSucceed() {
            Log.e("OnSkinConfigListener", "换肤配置成功");
        }

        @Override
        public void onFail(String msg) {
            Log.e("OnSkinConfigListener", msg);
        }
    }

}

package cn.edu.zstu.skin;

import android.util.Log;

/**
 * 皮肤改变接口
 * Created by CooLoongWu on 2017-12-15 15:27.
 */

public interface OnSkinChangedListener {
    String TAG = "OnSkinChangedListener";
    OnSkinChangedListener DEFAULT_ON_SKIN_CHANGED_LISTENER = new DefaultOnSkinChangedListener();

    void onStart();

    void onChanging(SkinView skinView);

    void onSucceed();

    void onFail();

    class DefaultOnSkinChangedListener implements OnSkinChangedListener {
        @Override
        public void onStart() {
            Log.e(TAG, "开始换肤");
        }

        @Override
        public void onChanging(SkinView skinView) {
            Log.e(TAG, "换肤中==>" +
                    "AttrType：" + skinView.getAttrType() +
                    "ResType：" + skinView.getResType() +
                    "ResName：" + skinView.getResName());
        }

        @Override
        public void onSucceed() {
            Log.e(TAG, "换肤成功");
        }

        @Override
        public void onFail() {
            Log.e(TAG, "换肤失败");
        }
    }

}

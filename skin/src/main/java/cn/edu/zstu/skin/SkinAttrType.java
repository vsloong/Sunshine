package cn.edu.zstu.skin;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 要换肤控件属性的类型【background，textColor，src】
 * <p>
 * 各个控件的background，可能为drawable或者color
 * ImageView的src，src中可能为drawable或者mipmap
 * TextView或者EditText的textColor，是颜色值
 * <p>
 * Created by CooLoongWu on 2017-12-6 14:20.
 */

public enum SkinAttrType {

    SRC("src") {
        @Override
        void applyNewAttr(ResourcesManager resourcesManager, View view, String resType, String resName) {
            if (view instanceof ImageView) {
                Drawable drawable = null;
                switch (resType) {
                    case "drawable":
                        drawable = resourcesManager.getDrawableByName(resName);
                        break;
                    case "mipmap":
                        drawable = resourcesManager.getMipmapByName(resName);
                        break;
                    default:
                        break;
                }
                if (null != drawable) {
                    ((ImageView) view).setImageDrawable(drawable);
                }
            }
        }
    },
    TEXTCOLOR("textColor") {
        @Override
        void applyNewAttr(ResourcesManager resourcesManager, View view, String resType, String resName) {
            if (view instanceof TextView) {
                switch (resType) {
                    case "color":
                        int color = resourcesManager.getColor(resName);
                        if (-1 != color) {
                            ((TextView) view).setTextColor(color);
                        }
                        break;
                    case "drawable":
                        ColorStateList colorStateList = resourcesManager.getColorStateList(resName);
                        if (null != colorStateList) {
                            ((TextView) view).setTextColor(colorStateList);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    };

    String attrType;

    SkinAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }

    abstract void applyNewAttr(ResourcesManager resourcesManager, View view, String resType, String resName);
}

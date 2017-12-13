package cn.edu.zstu.skin;

import android.view.View;

/**
 * 要换肤的视图
 * <p>
 * view     要更换皮肤的视图
 * attrType 控件属性类型【src、textColor、background】
 * resType  资源所属类型【drawable、mipmap、color、string】
 * resName  资源名称
 * <p>
 * Created by CooLoongWu on 2017-12-13 15:26.
 */

public class SkinView {

    private View view;
    private String attrType;
    private String resType;
    private String resName;

    public SkinView(View view, String attrType, String resType, String resName) {
        this.view = view;
        this.attrType = attrType;
        this.resType = resType;
        this.resName = resName;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }


}

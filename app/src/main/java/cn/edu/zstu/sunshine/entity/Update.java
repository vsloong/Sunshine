package cn.edu.zstu.sunshine.entity;

/**
 * 检查更新及换肤的实体类
 * Created by CooLoongWu on 2017-11-29 14:54.
 */

public class Update {
    private long versionCode;
    private String versionName;
    private String versionInfo;
    private String versionDownloadUrl;

    private boolean skinChange;
    private long skinCode;
    private String skinName;
    private String skinInfo;
    private String skinDownloadUrl;
    private String skinEffectiveTime;
    private String skinExpiryTime;

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getVersionDownloadUrl() {
        return versionDownloadUrl;
    }

    public void setVersionDownloadUrl(String versionDownloadUrl) {
        this.versionDownloadUrl = versionDownloadUrl;
    }

    public boolean isSkinChange() {
        return skinChange;
    }

    public void setSkinChange(boolean skinChange) {
        this.skinChange = skinChange;
    }

    public long getSkinCode() {
        return skinCode;
    }

    public void setSkinCode(long skinCode) {
        this.skinCode = skinCode;
    }

    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public String getSkinInfo() {
        return skinInfo;
    }

    public void setSkinInfo(String skinInfo) {
        this.skinInfo = skinInfo;
    }

    public String getSkinDownloadUrl() {
        return skinDownloadUrl;
    }

    public void setSkinDownloadUrl(String skinDownloadUrl) {
        this.skinDownloadUrl = skinDownloadUrl;
    }

    public String getSkinEffectiveTime() {
        return skinEffectiveTime;
    }

    public void setSkinEffectiveTime(String skinEffectiveTime) {
        this.skinEffectiveTime = skinEffectiveTime;
    }

    public String getSkinExpiryTime() {
        return skinExpiryTime;
    }

    public void setSkinExpiryTime(String skinExpiryTime) {
        this.skinExpiryTime = skinExpiryTime;
    }
}

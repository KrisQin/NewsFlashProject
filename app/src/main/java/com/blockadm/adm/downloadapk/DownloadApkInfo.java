package com.blockadm.adm.downloadapk;

/**
 * Created by Kris on 2019/7/4
 *
 * @Describe TODO {  }
 */
public class DownloadApkInfo {

    //下载地址
    private String downloadUrl;
    //更新内容
    private String description;
    //apk大小(单位是M)
    private float downloadSize;
    //显示版本号 如1.01
    private String versionName;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(float downloadSize) {
        this.downloadSize = downloadSize;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

}

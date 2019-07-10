package com.blockadm.common.bean;

import java.io.Serializable;

/**
 * Created by hp on 2019/2/28.
 */

public class CreateColumnParams  implements Serializable {
    private String coverImgs;
    private String price;
    private String videoPath;
    private  String name;
    private int accessStatus;

    public int getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(int accessStatus) {
        this.accessStatus = accessStatus;
    }

    public String getCoverImgs() {
        return coverImgs;
    }

    public void setCoverImgs(String coverImgs) {
        this.coverImgs = coverImgs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

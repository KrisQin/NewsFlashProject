package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/11.
 */

public class ImageBean {


    /**
     * thumbnail : https://img.jinse.com/1496010_live.png
     * width : 100
     * url : https://img.jinse.com/1496010_watermark.png
     * height : 100
     */

    private String thumbnail;
    private int width;
    private String url;
    private int height;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

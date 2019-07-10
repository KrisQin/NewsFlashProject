package com.blockadm.common.bean;

/**
 * Created by hp on 2019/2/16.
 */

public class PalyDetailDto {


    /**
     * isDownload : 0
     * nlscType : 0
     * isCollection : 0
     * isShare : 0
     * typeId : 1
     * id : 12
     * title : 单课8
     * coverImgs : http://image.blockadm.pro//image/10/0/20190213/3fdd6a68-5f66-4c53-9b0a-70cea1b96f84
     * url : http://public.blockadm.pro//audio/10/0/20190216/b2da6cf8-458f-4c61-a7dd-65bfa5c54f39
     */

    private int isDownload;
    private int nlscType;
    private int isCollection;
    private int isShare;
    private int typeId;
    private int id;
    private String title;
    private String coverImgs;
    private String url;
    private  String subtitle;
    private String showContentHtmlUrl;
    private String audioUrl;
    private String pictureLiveUrl;
    private int contentType;


    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getPictureLiveUrl() {
        return pictureLiveUrl;
    }

    public void setPictureLiveUrl(String pictureLiveUrl) {
        this.pictureLiveUrl = pictureLiveUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getShowContentHtmlUrl() {
        return showContentHtmlUrl;
    }

    public void setShowContentHtmlUrl(String showContentHtmlUrl) {
        this.showContentHtmlUrl = showContentHtmlUrl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(int isDownload) {
        this.isDownload = isDownload;
    }

    public int getNlscType() {
        return nlscType;
    }

    public void setNlscType(int nlscType) {
        this.nlscType = nlscType;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public int getIsShare() {
        return isShare;
    }

    public void setIsShare(int isShare) {
        this.isShare = isShare;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImgs() {
        return coverImgs;
    }

    public void setCoverImgs(String coverImgs) {
        this.coverImgs = coverImgs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.blockadm.common.bean;

import java.io.Serializable;

/**
 * Created by hp on 2019/2/18.
 */

public class QiniuTokenParams implements Serializable {


    /**
     * uploadToken : 2J1u4NbybL_D0QKZMDRyPN4QL91ZF7jK-XIaWfzr:mh03xNNG-GdMpkUdz1ZcYRPWjN4=:eyJzY29wZSI6ImJsb2NrYWRtLWltYWdlIiwiaXNQcmVmaXhhbFNjb3BlIjpudWxsLCJkZWFkbGluZSI6MTU1MDQ4MDYxNzg1NSwiaW5zZXJ0T25seSI6bnVsbCwiZW5kVXNlciI6bnVsbCwicmV0dXJuVXJsIjpudWxsLCJyZXR1cm5Cb2R5IjpudWxsLCJjYWxsYmFja1VybCI6bnVsbCwiY2FsbGJhY2tIb3N0IjpudWxsLCJjYWxsYmFja0JvZHkiOm51bGwsImNhbGxiYWNrQm9keVR5cGUiOm51bGwsInBlcnNpc3RlbnRPcHMiOm51bGwsInBlcnNpc3RlbnROb3RpZnlVcmwiOm51bGwsInBlcnNpc3RlbnRQaXBlbGluZSI6bnVsbCwic2F2ZUtleSI6Ii9pbWFnZS8xMC9udWxsLzIwMTkwMjE4LzliZWViNTViLTIwZmMtNGQ2OC05MjQ1LTUwMWE5ZmU5NTA5YyIsImZzaXplTWluIjpudWxsLCJmc2l6ZUxpbWl0IjpudWxsLCJkZXRlY3RNaW1lIjpudWxsLCJtaW1lTGltaXQiOm51bGwsImZpbGVUeXBlIjpudWxsfQ==
     * uploadPath : http://upload.qiniup.com
     * savePath : http://image.blockadm.pro/
     * saveFullPath : http://image.blockadm.pro//image/10/null/20190218/9beeb55b-20fc-4d68-9245-501a9fe9509c
     */

    private String uploadToken;
    private String uploadPath;
    private String savePath;
    private String saveFullPath;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public void setUploadToken(String uploadToken) {
        this.uploadToken = uploadToken;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSaveFullPath() {
        return saveFullPath;
    }

    public void setSaveFullPath(String saveFullPath) {
        this.saveFullPath = saveFullPath;
    }
}

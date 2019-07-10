package com.blockadm.common.bean.params;

/**
 * Created by Kris on 2019/5/16
 *
 * @Describe TODO {  }
 */
public class NewsLiveLessonsParams {


    /**
     * nickName : string
     * pageNum : 0
     * pageSize : 0
     * sysTypeId : 0
     * title : string
     */

    private String nickName;
    private int pageNum;
    private int pageSize;
    private Integer sysTypeId;
    private String title;

    public NewsLiveLessonsParams(String nickName, int pageNum, int pageSize, Integer sysTypeId,
                                 String title) {
        this.nickName = nickName;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sysTypeId = sysTypeId;
        this.title = title;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSysTypeId() {
        return sysTypeId;
    }

    public void setSysTypeId(Integer sysTypeId) {
        this.sysTypeId = sysTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

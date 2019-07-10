package com.blockadm.common.bean;

/**
 * Created by Kris on 2019/6/6
 *
 * @Describe TODO {  }
 */
public class sds {


    /**
     * msg : 成功！
     * code : 0
     * data : {"playCountTotal":"17","nlscReadCount":"1.4k","subscribeCountTotal":"0",
     * "newsLiveLessonsReadCount":"2.6w","readCountTotal":"4w","newsArticleReadCount":"4.9k",
     * "studyReadCountTotal":"9.3k","newsLessonsReadCount":"7.9k"}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * playCountTotal : 17
         * nlscReadCount : 1.4k
         * subscribeCountTotal : 0
         * newsLiveLessonsReadCount : 2.6w
         * readCountTotal : 4w
         * newsArticleReadCount : 4.9k
         * studyReadCountTotal : 9.3k
         * newsLessonsReadCount : 7.9k
         */

        private String playCountTotal;
        private String nlscReadCount;
        private String subscribeCountTotal;
        private String newsLiveLessonsReadCount;
        private String readCountTotal;
        private String newsArticleReadCount;
        private String studyReadCountTotal;
        private String newsLessonsReadCount;

        public String getPlayCountTotal() {
            return playCountTotal;
        }

        public void setPlayCountTotal(String playCountTotal) {
            this.playCountTotal = playCountTotal;
        }

        public String getNlscReadCount() {
            return nlscReadCount;
        }

        public void setNlscReadCount(String nlscReadCount) {
            this.nlscReadCount = nlscReadCount;
        }

        public String getSubscribeCountTotal() {
            return subscribeCountTotal;
        }

        public void setSubscribeCountTotal(String subscribeCountTotal) {
            this.subscribeCountTotal = subscribeCountTotal;
        }

        public String getNewsLiveLessonsReadCount() {
            return newsLiveLessonsReadCount;
        }

        public void setNewsLiveLessonsReadCount(String newsLiveLessonsReadCount) {
            this.newsLiveLessonsReadCount = newsLiveLessonsReadCount;
        }

        public String getReadCountTotal() {
            return readCountTotal;
        }

        public void setReadCountTotal(String readCountTotal) {
            this.readCountTotal = readCountTotal;
        }

        public String getNewsArticleReadCount() {
            return newsArticleReadCount;
        }

        public void setNewsArticleReadCount(String newsArticleReadCount) {
            this.newsArticleReadCount = newsArticleReadCount;
        }

        public String getStudyReadCountTotal() {
            return studyReadCountTotal;
        }

        public void setStudyReadCountTotal(String studyReadCountTotal) {
            this.studyReadCountTotal = studyReadCountTotal;
        }

        public String getNewsLessonsReadCount() {
            return newsLessonsReadCount;
        }

        public void setNewsLessonsReadCount(String newsLessonsReadCount) {
            this.newsLessonsReadCount = newsLessonsReadCount;
        }
    }
}

package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/2/13.
 */

public class UserMsgDto {


    /**
     * records : [{"icon":"http://image.blockadm.pro/sys/msg/png/official0.png","id":8,"userNickName":null,"title":null,"userId":null,"content":"uuuuuuuuu","createDate":"2019-02-13 16:55:58"},{"icon":"http://image.blockadm.pro/sys/msg/png/concern0.png","id":4,"userNickName":null,"title":"vv","userId":null,"content":"gggggggg","createDate":"2019-02-12 16:55:47"},{"icon":null,"id":5,"userNickName":null,"title":"xx","userId":null,"content":"hhhhhhhh","createDate":"2019-02-11 16:55:50"},{"icon":"http://image.blockadm.pro/sys/msg/png/concern0.png","id":7,"userNickName":null,"title":null,"userId":null,"content":"kkkkkkkkk","createDate":"2019-02-10 16:55:55"},{"icon":null,"id":6,"userNickName":null,"title":null,"userId":null,"content":"jjjjjjjjj","createDate":"2019-02-09 16:55:53"},{"icon":"http://image.blockadm.pro/sys/msg/png/official1.png","id":3,"userNickName":null,"title":"my title","userId":null,"content":"dffsdfdfdfs","createDate":"2019-02-13 15:39:24"}]
     * total : 6
     * size : 20
     * current : 1
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private int pages;
    private List<RecordsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * icon : http://image.blockadm.pro/sys/msg/png/official0.png
         * id : 8
         * userNickName : null
         * title : null
         * userId : null
         * content : uuuuuuuuu
         * createDate : 2019-02-13 16:55:58
         */
  private boolean isOpen;

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        private String icon;
        private int id;
        private String userNickName;
        private String title;
        private String userId;
        private String content;
        private String createDate;
        private String readIcon;
        private int state;
        private int position;
        private String htmlContent;
        private int typeId;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getReadIcon() {
            return readIcon;
        }

        public void setReadIcon(String readIcon) {
            this.readIcon = readIcon;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}

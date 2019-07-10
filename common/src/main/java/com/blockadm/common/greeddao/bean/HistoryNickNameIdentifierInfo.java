package com.blockadm.common.greeddao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;


/**
 * Created by Kris on 2019/5/31
 *
 * @Describe TODO {  }
 */

@Entity
public class HistoryNickNameIdentifierInfo {

    @Id(autoincrement = true)
    private long Id;

    private String nickName ;

    //（Unique表示该属性必须在数据库中的唯一的值）
    private String identifier;

    private String headImageUrl;

    @Generated(hash = 1126071976)
    public HistoryNickNameIdentifierInfo(long Id, String nickName,
            String identifier, String headImageUrl) {
        this.Id = Id;
        this.nickName = nickName;
        this.identifier = identifier;
        this.headImageUrl = headImageUrl;
    }

    @Generated(hash = 987260942)
    public HistoryNickNameIdentifierInfo() {
    }

    public long getId() {
        return this.Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getHeadImageUrl() {
        return this.headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

   
}

package com.blockadm.common.im.entity;

/**
 * Created by Kris on 2019/5/22
 *
 * @Describe TODO { 消息类型 }
 */
public class MessageType {

    /**
     * 文本类型消息
     */
    public static final int MSG_TYPE_TEXT = 0x00;

    /**
     * 文本类型消息 -- 打赏
     */
    public static final int MSG_TYPE_REWARD = 0x10;

    /**
     * 文本类型消息 -- 提问
     */
    public static final int MSG_TYPE_ASK = 0x11;

    /**
     * 图片类型消息
     */
    public static final int MSG_TYPE_IMAGE = 0x20;
    /**
     * 语音类型消息
     */
    public static final int MSG_TYPE_AUDIO = 0x30;
    /**
     * 视频类型消息
     */
    public static final int MSG_TYPE_VIDEO = 0x40;
    /**
     * 文件类型消息
     */
    public static final int MSG_TYPE_FILE = 0x50;
    /**
     * 位置类型消息
     */
    public static final int MSG_TYPE_LOCATION = 0x60;

    /**
     * 自定义图片类型消息
     */
    public static final int MSG_TYPE_CUSTOM_FACE = 0x70;

    /**
     * 提示类信息
     */
    public static final int MSG_TYPE_TIPS = 0x100;
    /**
     * 群创建提示消息
     */
    public static final int MSG_TYPE_GROUP_CREATE = 0x101;
    /**
     * 群创建提示消息
     */
    public static final int MSG_TYPE_GROUP_DELETE = 0x102;
    /**
     * 群成员加入提示消息
     */
    public static final int MSG_TYPE_GROUP_JOIN = 0x103;
    /**
     * 群成员退群提示消息
     */
    public static final int MSG_TYPE_GROUP_QUITE = 0x104;
    /**
     * 群成员被踢出群提示消息
     */
    public static final int MSG_TYPE_GROUP_KICK = 0x105;
    /**
     * 群名称修改提示消息
     */
    public static final int MSG_TYPE_GROUP_MODIFY_NAME = 0x106;
    /**
     * 群通知更新提示消息
     */
    public static final int MSG_TYPE_GROUP_MODIFY_NOTICE = 0x107;

    /**
     * 消息未读状态
     */
    public static final int MSG_STATUS_READ = 0x111;
    /**
     * 消息删除状态
     */
    public static final int MSG_STATUS_DELETE = 0x112;
    /**
     * 消息撤回状态
     */
    public static final int MSG_STATUS_REVOKE = 0x113;
    /**
     * 消息正常状态
     */
    public static final int MSG_STATUS_NORMAL = 0;
    /**
     * 消息发送中状态
     */
    public static final int MSG_STATUS_SENDING = 1;
    /**
     * 消息发送成功状态
     */
    public static final int MSG_STATUS_SEND_SUCCESS = 2;
    /**
     * 消息发送失败状态
     */
    public static final int MSG_STATUS_SEND_FAIL = 3;
    /**
     * 消息内容下载中状态
     */
    public static final int MSG_STATUS_DOWNLOADING = 4;
    /**
     * 消息内容未下载状态
     */
    public static final int MSG_STATUS_UN_DOWNLOAD = 5;
    /**
     * 消息内容已下载状态
     */
    public static final int MSG_STATUS_DOWNLOADED = 6;

}

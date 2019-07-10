package com.blockadm.common.config;

/**
 * Created by Kris on 2019/5/28
 *
 * @Describe TODO {  }
 */
public class LiveConfig {

    //0:文本内容、1：图片URL、2：语音URL、3：视频URL 4:打赏 5：提问  6:发红包  7：领红包
    public static final String TEXT_Content_Type = "0";
    public static final String IMAGE_Content_Type = "1";
    public static final String VOICE_Content_Type = "2";
    public static final String VEDIO_Content_Type = "3";
    public static final String REWARD_Content_Type = "4";
    public static final String ASK_Content_Type = "5";
    public static final String Send_HB_Type = "6";
    public static final String Receive_HB_Type = "7";

    /*
    打赏格式： 内容 + BlockADM_PAY
    提问格式： 名称 + BlockADM_ASK + 内容
    领红包格式： 内容 + BlockADM_LHB
    发红包格式： BlockADM_FHB
    禁言格式： 内容 + BlockADM_BAN
    可以发言格式： 内容 + BlockADM_CAN
    可以发言格式： 内容 + BlockADM_CAN

    管理员在直播发出的文字消息格式： 内容 + BlockADM_ZB
    管理员在交流发出的文字消息格式： 内容 + BlockADM_JL
     */

    public static final String BlockADM_PAY = "BlockADM_PAY"; //打赏
    public static final String BlockADM_ASK = "BlockADM_ASK"; //提问
    public static final String BlockADM_LHB = "BlockADM_LHB"; //领红包
    public static final String BlockADM_FHB = "BlockADM_FHB"; //发红包
    public static final String BlockADM_BAN = "BlockADM_BAN"; //禁言
    public static final String BlockADM_CAN = "BlockADM_CAN"; //可以发言
    public static final String BlockADM_ZBM = "BlockADM_ZBM"; //管理员在直播发出的文字消息
    public static final String BlockADM_JLM = "BlockADM_JLM"; //管理员在交流发出的文字消息

    public static final int Page_Load_Num = 10;


}

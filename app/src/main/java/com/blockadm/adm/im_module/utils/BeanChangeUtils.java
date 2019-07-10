package com.blockadm.adm.im_module.utils;

import com.blockadm.common.bean.live.responseBean.HistoryLiveLessonsInfo;
import com.blockadm.common.bean.live.responseBean.HistoryLiveLessonsListInfo;
import com.blockadm.common.config.LiveConfig;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.im.entity.MessageType;
import com.blockadm.common.utils.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kris on 2019/5/29
 *
 * @Describe TODO {  }
 */
public class BeanChangeUtils {


    /**
     * 社群直播和交流历史聊天记录 实体转换
     * @param listInfo
     * @param historyMessageList
     */
    public static void historyLiveLessonsList2MessageInfoList(HistoryLiveLessonsListInfo listInfo,List<MessageInfo> historyMessageList) {

        if (listInfo != null && historyMessageList != null) {

            List<HistoryLiveLessonsInfo> records = listInfo.getRecords();

            for (int i = 0; i < records.size(); i++) {

                HistoryLiveLessonsInfo lessonsInfo = records.get(i);

                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setHistoryMsg(true);

                messageInfo.setHeadImageUrl(lessonsInfo.getIcon());
                messageInfo.setName(lessonsInfo.getNick_name());
                messageInfo.setContent(lessonsInfo.getContent());
                messageInfo.setDataPath(lessonsInfo.getContent());
                messageInfo.setPlayTime(lessonsInfo.getPlayTime());
                messageInfo.setMemberId(lessonsInfo.getMemberId());

                if (lessonsInfo.getCreateNewsLiveLessonsMemberId() == lessonsInfo.getMemberId()) {
                    messageInfo.setSpeaker(true);
                }else {
                    messageInfo.setSpeaker(false);
                }

                //图片
                if (StringUtils.isEquals(lessonsInfo.getContentType()+"", LiveConfig.IMAGE_Content_Type)) {
                    messageInfo.setMsgType(MessageType.MSG_TYPE_IMAGE);
                }
                //语音
                else if (StringUtils.isEquals(lessonsInfo.getContentType()+"",LiveConfig.VOICE_Content_Type)) {
                    messageInfo.setMsgType(MessageType.MSG_TYPE_AUDIO);
                }
                //文字
                else if (StringUtils.isEquals(lessonsInfo.getContentType()+"",LiveConfig.TEXT_Content_Type)) {
                    messageInfo.setMsgType(MessageType.MSG_TYPE_TEXT);
                }
                //打赏
                else if (StringUtils.isEquals(lessonsInfo.getContentType()+"",LiveConfig.REWARD_Content_Type)) {
                    messageInfo.setMsgType(MessageType.MSG_TYPE_TEXT);
                    messageInfo.setNormalMsg(true);
                    messageInfo.setReward(true);

                    String content = lessonsInfo.getNick_name()+"打赏了"+lessonsInfo.getMasterNickName()+lessonsInfo.getMoneyMsg();
                    messageInfo.setContent(content);
                }
                //提问
                else if (StringUtils.isEquals(lessonsInfo.getContentType()+"",LiveConfig.ASK_Content_Type)) {
                    messageInfo.setMsgType(MessageType.MSG_TYPE_TEXT);
                    messageInfo.setAskQuestion(true);

                    String name = lessonsInfo.getNick_name() + "花" + lessonsInfo.getMoneyMsg() + "提问";
                    messageInfo.setName(name);
                }
                //发红包
                else if (StringUtils.isEquals(lessonsInfo.getContentType()+"",LiveConfig.Send_HB_Type)) {
                    messageInfo.setMsgType(MessageType.MSG_TYPE_TEXT);
                    messageInfo.setSendHongbao(true);
                    messageInfo.setHongbaoId(lessonsInfo.getRedPacketId());
                }
                //领红包
                else if (StringUtils.isEquals(lessonsInfo.getContentType()+"",LiveConfig.Receive_HB_Type)) {
                    messageInfo.setMsgType(MessageType.MSG_TYPE_TEXT);
                    messageInfo.setReceiveHongbao(true);
                    messageInfo.setNormalMsg(true);
                    String content = lessonsInfo.getNick_name()+"领取了"+lessonsInfo.getMasterNickName()+"的红包";
                    messageInfo.setContent(content);
                }


                historyMessageList.add(0,messageInfo);
            }
        }
    }

}

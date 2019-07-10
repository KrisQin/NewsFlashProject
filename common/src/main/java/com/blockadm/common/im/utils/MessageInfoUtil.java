package com.blockadm.common.im.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.config.LiveConfig;
import com.blockadm.common.im.FileUtil;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.UIKitConstants;
import com.blockadm.common.im.call.TCallback;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.im.entity.MessageType;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.StringUtils;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFaceElem;
import com.tencent.imsdk.TIMFileElem;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMGroupTipsElemGroupInfo;
import com.tencent.imsdk.TIMGroupTipsGroupInfoType;
import com.tencent.imsdk.TIMGroupTipsType;
import com.tencent.imsdk.TIMImage;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMImageType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageStatus;
import com.tencent.imsdk.TIMSnapshot;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.TIMVideo;
import com.tencent.imsdk.TIMVideoElem;
import com.tencent.imsdk.ext.message.TIMMessageExt;
import com.tencent.imsdk.log.QLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MessageInfoUtil {

    public static final String GROUP_CREATE = "group_create";
    public static final String GROUP_DELETE = "group_delete";

    private static Context mContext;

    public static void setContext(Context mContext) {
        MessageInfoUtil.mContext = mContext;
    }


    public static MessageInfo buildTextMessage(String message) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMTextElem ele = new TIMTextElem();
        ele.setText(message);
        TIMMsg.addElement(ele);
        info.setExtra(message);
        info.setMsgTime(System.currentTimeMillis());
        info.setSelf(true);
        info.setTIMMessage(TIMMsg);
        info.setFromUser(TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageType.MSG_TYPE_TEXT);
        return info;
    }

    public static MessageInfo buildCustomFaceMessage(int groupId, String faceName) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMFaceElem ele = new TIMFaceElem();
        ele.setIndex(groupId);
        ele.setData(faceName.getBytes());
        TIMMsg.addElement(ele);
        info.setExtra("[自定义表情]");
        info.setMsgTime(System.currentTimeMillis());
        info.setSelf(true);
        info.setTIMMessage(TIMMsg);
        info.setFromUser(TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageType.MSG_TYPE_CUSTOM_FACE);
        return info;
    }


    public static MessageInfo buildImageMessage(Activity activity, final Uri uri,
                                                boolean compressed,
                                                boolean appPohto) {
        final MessageInfo info = new MessageInfo();
        UserInfoDto userInfoDto =
                (UserInfoDto) ACache.get(activity).getAsObject(ACache.user_Info_Dto);
        if (userInfoDto != null) {
            info.setHeadImageUrl(userInfoDto.getIcon());
            info.setName(userInfoDto.getNickName());
        }

        final TIMImageElem ele = new TIMImageElem();
        //不是应用自己拍摄的照片，先copy一份过来
        if (!appPohto) {
            ImageUtil.CopyImageInfo copInfo = ImageUtil.copyImage(uri,
                    UIKitConstants.IMAGE_DOWNLOAD_DIR);
            if (copInfo == null)
                return null;
            ele.setPath(copInfo.getPath());
            info.setDataPath(copInfo.getPath());
            info.setImgWithd(copInfo.getWidth());
            info.setImgHeight(copInfo.getHeight());
            info.setDataUri(FileUtil.getUriFromPath(copInfo.getPath()));
        } else {
            info.setDataUri(uri);
            int size[] = ImageUtil.getImageSize(uri);
            String path = FileUtil.getPathFromUri(uri);
            ele.setPath(path);
            info.setDataPath(path);
            info.setImgWithd(size[0]);
            info.setImgHeight(size[1]);
        }

        TIMMessage TIMMsg = new TIMMessage();
        TIMMessageExt ext = new TIMMessageExt(TIMMsg);
        ext.setSender(TIMManager.getInstance().getLoginUser());
        ext.setTimestamp(System.currentTimeMillis());
        if (!compressed)
            ele.setLevel(0);
        TIMMsg.addElement(ele);
        info.setSelf(true);
        info.setTIMMessage(TIMMsg);
        info.setExtra("[图片]");
        info.setMsgTime(System.currentTimeMillis());
        info.setFromUser(TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageType.MSG_TYPE_IMAGE);
        return info;
    }

    public static MessageInfo buildVideoMessage(String imgPath, String videoPath, int width,
                                                int height, long duration) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMVideoElem ele = new TIMVideoElem();

        TIMVideo video = new TIMVideo();
        video.setDuaration(duration / 1000);
        video.setType("mp4");
        TIMSnapshot snapshot = new TIMSnapshot();

        snapshot.setWidth(width);
        snapshot.setHeight(height);
        ele.setSnapshot(snapshot);
        ele.setVideo(video);
        ele.setSnapshotPath(imgPath);
        ele.setVideoPath(videoPath);

        TIMMsg.addElement(ele);
        Uri videoUri = Uri.fromFile(new File(videoPath));
        info.setSelf(true);
        info.setImgWithd(width);
        info.setImgHeight(height);
        info.setDataPath(imgPath);
        info.setDataUri(videoUri);
        info.setTIMMessage(TIMMsg);
        info.setExtra("[视频]");
        info.setMsgTime(System.currentTimeMillis());
        info.setFromUser(TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageType.MSG_TYPE_VIDEO);
        return info;
    }

    public static MessageInfo buildAudioMessage(Activity activity, String recordPath,
                                                int duration) {
        MessageInfo info = new MessageInfo();
        info.setDataPath(recordPath);
        TIMMessage TIMMsg = new TIMMessage();
        TIMMessageExt ext = new TIMMessageExt(TIMMsg);
        ext.setSender(TIMManager.getInstance().getLoginUser());
        ext.setTimestamp(System.currentTimeMillis() / 1000);
        TIMSoundElem ele = new TIMSoundElem();
        ele.setDuration(duration / 1000);
        ele.setPath(recordPath);
        TIMMsg.addElement(ele);

        UserInfoDto userInfoDto =
                (UserInfoDto) ACache.get(activity).getAsObject(ACache.user_Info_Dto);
        if (userInfoDto != null) {
            info.setHeadImageUrl(userInfoDto.getIcon());
            info.setName(userInfoDto.getNickName());
        }
        info.setSelf(true);
        info.setTIMMessage(TIMMsg);
        info.setExtra("[语音]");
        info.setMsgTime(System.currentTimeMillis());
        info.setFromUser(TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageType.MSG_TYPE_AUDIO);
        return info;
    }

    public static MessageInfo buildFileMessage(Uri fileUri) {
        String filePath = FileUtil.getPathFromUri(fileUri);
        File file = new File(filePath);
        if (file.exists()) {
            MessageInfo info = new MessageInfo();
            info.setDataPath(filePath);
            TIMMessage TIMMsg = new TIMMessage();
            TIMFileElem ele = new TIMFileElem();
            TIMMessageExt ext = new TIMMessageExt(TIMMsg);
            ext.setSender(TIMManager.getInstance().getLoginUser());
            ext.setTimestamp(System.currentTimeMillis() / 1000);
            ele.setPath(filePath);
            ele.setFileName(file.getName());
            TIMMsg.addElement(ele);
            info.setSelf(true);
            info.setTIMMessage(TIMMsg);
            info.setExtra("[文件]");
            info.setMsgTime(System.currentTimeMillis());
            info.setFromUser(TIMManager.getInstance().getLoginUser());
            info.setMsgType(MessageType.MSG_TYPE_FILE);
            info.setStatus(MessageType.MSG_STATUS_SENDING);
            return info;
        }
        return null;
    }

    public static MessageInfo buildReadNoticeMessage(String peer) {
        MessageInfo info = new MessageInfo();
        info.setPeer(peer);
        info.setMsgType(MessageType.MSG_STATUS_READ);
        return info;
    }

    public static MessageInfo buildGroupCustomMessage(String action, String message) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMCustomElem ele = new TIMCustomElem();
        ele.setData(action.getBytes());
        ele.setExt(message.getBytes());
        TIMMsg.addElement(ele);
        info.setSelf(true);
        info.setTIMMessage(TIMMsg);
        info.setExtra(message);
        info.setMsgTime(System.currentTimeMillis());
        if (action.equals(GROUP_CREATE)) {
            info.setMsgType(MessageType.MSG_TYPE_GROUP_CREATE);
        } else if (action.equals(GROUP_DELETE)) {
            info.setMsgType(MessageType.MSG_TYPE_GROUP_DELETE);
        }
        return info;
    }

    public static MessageInfo buildGroupTipsMessage(String peer, int type, String message) {
        MessageInfo info = new MessageInfo();
        info.setSelf(true);
        info.setMsgType(type);
        info.setPeer(peer);
        info.setExtra(message);
        info.setMsgTime(System.currentTimeMillis());
        return info;
    }

    //    public static List<MessageInfo> TIMMessages2MessageInfos(List<TIMMessage> timMessages,
    //                                                             boolean isGroup) {
    //        if (timMessages == null)
    //            return null;
    //        List<MessageInfo> messageInfos = new ArrayList<>();
    //        for (int i = 0; i < timMessages.size(); i++) {
    //            TIMMessage timMessage = timMessages.get(i);
    //            MessageInfo info = TIMMessage2MessageInfo(timMessage, isGroup);
    //            if (info != null)
    //                messageInfos.add(info);
    //        }
    //        return messageInfos;
    //    }

    private static List<String> users = new ArrayList<>();
    private static List<TIMUserProfile> resultList = new ArrayList<>();


    private static void getUsersProfileByIdentifier(String identifier,
                                                    final TCallback<List<TIMUserProfile>> callback) {
        users.clear();
        users.add(identifier);

        //获取用户资料
        TIMFriendshipManager.getInstance().getUsersProfile(users, true,
                new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int code, String desc) {
                        //错误码 code 和错误描述 desc，可用于定位请求失败原因
                        //错误码 code 列表请参见错误码表
                        L.d("", "getUsersProfile failed: " + code + " desc");
                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> result) {

                        if (callback != null) {
                            callback.callback(result);
                        }


                        L.d("", "getUsersProfile succ");
                        for (TIMUserProfile res : result) {
                            L.d("", "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                                    + " faceUrl: " + res.getFaceUrl());
                        }
                    }
                });


    }

    public static String subContent(String content) {
        if (StringUtils.isNotEmpty(content) && content.length() >= LiveConfig.BlockADM_ASK.length()) {
            return content.substring(0, content.length() - LiveConfig.BlockADM_ASK.length());
        } else {
            return "";
        }
    }

    private static int subContentToInt(String content) {
        try {
            if (StringUtils.isNotEmpty(content) && content.length() > LiveConfig.BlockADM_ASK.length()) {
                String id = content.substring(0,
                        content.length() - LiveConfig.BlockADM_ASK.length());
                return Integer.valueOf(id);
            }
        } catch (Exception e) {
            L.d(LiveManager.TAG, "subContentToInt Exception: " + e.toString());
        }

        return -1;

    }


    /**
     * @param timMessage
     * @param userInfoDto
     * @param isGroup
     * @param isLiveView  true:直播界面  false:交流界面
     * @param tCallback
     */
    public static void TIMMessage2MessageInfo(TIMMessage timMessage, UserInfoDto userInfoDto,
                                              boolean isGroup, final boolean isLiveView,
                                              final TCallback<MessageInfo> tCallback) {
        if (timMessage == null || timMessage.status() == TIMMessageStatus.HasDeleted) {
            tCallback.callback(null);
            return;
        }

        String peer = timMessage.getConversation().getPeer();
        if (StringUtils.isNotEquals(peer,LiveManager.getInstance().getGroupId())) { //如果不是当前群的消息，则不进行处理
            return;
        }

        String sender = timMessage.getSender();
        final MessageInfo msgInfo = new MessageInfo();
        msgInfo.setTIMMessage(timMessage);
        msgInfo.setGroup(isGroup);
        msgInfo.setMsgId(timMessage.getMsgId());

        if (isGroup) {
            TIMGroupMemberInfo memberInfo = timMessage.getSenderGroupMemberProfile();
            if (memberInfo != null && !TextUtils.isEmpty(memberInfo.getNameCard())) {
                msgInfo.setFromUser(memberInfo.getNameCard());
            } else {
                msgInfo.setFromUser(sender);
            }
        } else {
            msgInfo.setFromUser(sender);
        }
        msgInfo.setMsgTime(timMessage.timestamp() * 1000);
        msgInfo.setPeer(timMessage.getConversation().getPeer());
        msgInfo.setSelf(sender.equals(TIMManager.getInstance().getLoginUser()));

        boolean isAskTextType = false; //true:文本形式发出的提问类型,name需要自定义

        if (timMessage.getElementCount() > 0) {
            TIMElem ele = timMessage.getElement(0);
            if (ele == null) {
                QLog.e("MessageInfoUtil", "msg found null elem");
                tCallback.callback(null);
                return;
            }

            //获取当前元素的类型
            TIMElemType elemType = ele.getType();
            L.d(LiveManager.TAG, " 获取当前元素的类型: " + elemType + " ; isLiveView: " + isLiveView);

            if (elemType == TIMElemType.Invalid) {
                QLog.e("MessageInfoUtil", "invalid");
                tCallback.callback(null);
                return;
            }

            //自定义消息
            if (elemType == TIMElemType.Custom) {
                dealCustomMsg(ele, msgInfo);
            }
            //处理群组相关信息
            else if (elemType == TIMElemType.GroupTips) {
                dealGroupMsg(ele, msgInfo);
            }
            //处理文本，图片，语音，视频，自定义表情，文件消息
            else {

                //处理文本消息
                if (elemType == TIMElemType.Text) {

                    TIMTextElem timTextElem = (TIMTextElem) ele;
                    String content = timTextElem.getText();

                    if (content.contains(LiveConfig.BlockADM_ASK)) {
                        isAskTextType = true;
                    }

                    boolean isNeedContinueDealMsg = dealTextTypeMsg(timMessage, msgInfo, ele,
                            isLiveView);
                    if (!isNeedContinueDealMsg) {
                        return;
                    }
                }
                //处理图片消息
                else if (elemType == TIMElemType.Image) {
                    dealImageMsg(ele, msgInfo);
                }
                //处理语音消息
                else if (elemType == TIMElemType.Sound) {
                    dealSoundMsg(ele, msgInfo);
                }
                //处理自定义表情
                else if (elemType == TIMElemType.Face) {
                    if (!dealFaceMsg(ele, msgInfo)) {
                        tCallback.callback(null);
                        return;
                    }
                }
                //处理视频消息
                else if (elemType == TIMElemType.Video) {
                    dealVideoMsg(ele, msgInfo);
                }
                //处理文件消息
                else if (elemType == TIMElemType.File) {
                    dealFileMsg(ele, msgInfo);

                }
                msgInfo.setMsgType(TIMElemType2MessageInfoType(elemType));
            }

        } else {
            QLog.e("MessageInfoUtil", "msg elecount is 0");
            tCallback.callback(null);
            return;
        }

        if (timMessage.status() == TIMMessageStatus.HasRevoked) {
            msgInfo.setStatus(MessageType.MSG_STATUS_REVOKE);
            msgInfo.setMsgType(MessageType.MSG_STATUS_REVOKE);
        } else {
            if (msgInfo.isSelf()) {
                if (timMessage.status() == TIMMessageStatus.SendFail) {
                    msgInfo.setStatus(MessageType.MSG_STATUS_SEND_FAIL);
                } else if (timMessage.status() == TIMMessageStatus.SendSucc) {
                    msgInfo.setStatus(MessageType.MSG_STATUS_SEND_SUCCESS);
                } else if (timMessage.status() == TIMMessageStatus.Sending) {
                    msgInfo.setStatus(MessageType.MSG_STATUS_SENDING);
                }
            }
        }

        dealNameImageUrl(timMessage, userInfoDto, isAskTextType, msgInfo, tCallback);


    }

    /**
     * 处理名字和头像
     * @param timMessage
     * @param userInfoDto
     * @param isAskTextType
     * @param msgInfo
     * @param tCallback
     */
    private static void dealNameImageUrl(TIMMessage timMessage, UserInfoDto userInfoDto,
                                         boolean isAskTextType, final MessageInfo msgInfo,
                                         final TCallback<MessageInfo> tCallback) {
        TIMUserProfile timUserProfile = timMessage.getSenderProfile();

        if (timUserProfile != null)
            L.d(LiveManager.TAG_C, "nickName: " + timUserProfile.getNickName() + " ; " +
                    "headImageUrl: " + timUserProfile.getFaceUrl() + " ; timUserProfile" +
                    ".getIdentifier(): " + timUserProfile.getIdentifier() + " ; timMessage.isSelf" +
                    "(): " + timMessage.isSelf());

        //获取名字 头像 账号
        final String[] nickName = new String[1];
        final String[] identifier = new String[1];
        final String[] headImageUrl = new String[1];

        if (timMessage.isSelf()) {
            nickName[0] = userInfoDto.getNickName();
            identifier[0] = userInfoDto.getLoginIMAccount();
            headImageUrl[0] = userInfoDto.getIcon();

            if (!isAskTextType)
                msgInfo.setName(nickName[0]);
            msgInfo.setHeadImageUrl(headImageUrl[0]);

            tCallback.callback(msgInfo);
        } else {
            if (timUserProfile == null || StringUtils.isEmpty(timUserProfile.getFaceUrl()) || StringUtils.isEmpty(timUserProfile.getNickName())) {

                final boolean finalIsAskTextType = isAskTextType;
                getUsersProfileByIdentifier(timUserProfile.getIdentifier(),
                        new TCallback<List<TIMUserProfile>>() {
                            @Override
                            public void callback(List<TIMUserProfile> timUserProfiles) {
                                if (ListUtils.isNotEmpty(timUserProfiles)) {
                                    nickName[0] = timUserProfiles.get(0).getNickName();
                                    identifier[0] = timUserProfiles.get(0).getIdentifier();
                                    headImageUrl[0] = timUserProfiles.get(0).getFaceUrl();

                                    L.d(LiveManager.TAG_C,
                                            " getUsersProfileByIdentifier() --> name: " + nickName[0] +
                                                    " ; headImageUrl: " + headImageUrl[0] + " ; " +
                                                    "identifier(): " + identifier[0]);
                                    if (!finalIsAskTextType)
                                        msgInfo.setName(nickName[0]);
                                    msgInfo.setHeadImageUrl(headImageUrl[0]);

                                    tCallback.callback(msgInfo);
                                }
                            }
                        });

            } else {
                nickName[0] = timUserProfile.getNickName();
                identifier[0] = timUserProfile.getIdentifier();
                headImageUrl[0] = timUserProfile.getFaceUrl();

                if (!isAskTextType)
                    msgInfo.setName(nickName[0]);
                msgInfo.setHeadImageUrl(headImageUrl[0]);

                try {
                    int index = identifier[0].lastIndexOf("_");
                    String memberId = identifier[0].substring(index+1);
                    if (StringUtils.isNotEmpty(memberId)) {
                        msgInfo.setMemberId(Integer.valueOf(memberId));
                        L.d(LiveManager.TAG,"memberId: "+memberId+" ; identifier[0]: "+identifier[0]);
                    }

                }catch (Exception e) {
                    L.d(LiveManager.TAG,"Exception e: "+e.toString());
                }

                tCallback.callback(msgInfo);
            }
        }

    }

    /**
     * 处理视文件消息
     *
     * @param ele
     * @param msgInfo
     */
    private static void dealFileMsg(TIMElem ele, final MessageInfo msgInfo) {

        TIMFileElem fileElem = (TIMFileElem) ele;
        final String path = UIKitConstants.FILE_DOWNLOAD_DIR + fileElem.getUuid();
        if (!msgInfo.isSelf()) {
            File file = new File(path);
            if (!file.exists()) {
                msgInfo.setStatus(MessageType.MSG_STATUS_UN_DOWNLOAD);
            } else {
                msgInfo.setStatus(MessageType.MSG_STATUS_DOWNLOADED);
            }
            msgInfo.setDataPath(path);
        } else {
            if (TextUtils.isEmpty(fileElem.getPath())) {
                fileElem.getToFile(path, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        QLog.e("MessageInfoUtil getToFile", code + ":" + desc);
                    }

                    @Override
                    public void onSuccess() {
                        msgInfo.setDataPath(path);
                    }
                });
            } else {
                msgInfo.setStatus(MessageType.MSG_STATUS_SEND_SUCCESS);
                msgInfo.setDataPath(fileElem.getPath());
            }

        }
        msgInfo.setExtra("[文件]");
    }

    /**
     * 处理视频消息
     *
     * @param ele
     * @param msgInfo
     */
    private static void dealVideoMsg(TIMElem ele, MessageInfo msgInfo) {
        TIMVideoElem videoEle = (TIMVideoElem) ele;
        if (msgInfo.isSelf() && !TextUtils.isEmpty(videoEle.getSnapshotPath())) {
            int size[] = ImageUtil.getImageSize(videoEle.getSnapshotPath());
            msgInfo.setImgWithd(size[0]);
            msgInfo.setImgHeight(size[1]);
            msgInfo.setDataPath(videoEle.getSnapshotPath());
            msgInfo.setDataUri(FileUtil.getUriFromPath(videoEle.getVideoPath()));
        } else {
            TIMVideo video = videoEle.getVideoInfo();
            final String videoPath =
                    UIKitConstants.VIDEO_DOWNLOAD_DIR + video.getUuid();
            Uri uri = Uri.parse(videoPath);
            msgInfo.setDataUri(uri);
            TIMSnapshot snapshot = videoEle.getSnapshotInfo();
            msgInfo.setImgWithd((int) snapshot.getWidth());
            msgInfo.setImgHeight((int) snapshot.getHeight());
            final String snapPath = UIKitConstants.IMAGE_DOWNLOAD_DIR + snapshot.getUuid();
            //判断快照是否存在,不存在自动下载
            if (new File(snapPath).exists()) {
                msgInfo.setDataPath(snapPath);
            }
        }

        msgInfo.setExtra("[视频]");
    }

    /**
     * 处理语音消息
     *
     * @param ele
     * @param msgInfo
     */
    private static void dealSoundMsg(TIMElem ele, final MessageInfo msgInfo) {
        TIMSoundElem soundElemEle = (TIMSoundElem) ele;
        msgInfo.setMsgType(MessageType.MSG_TYPE_AUDIO);
        if (msgInfo.isSelf()) {
            msgInfo.setDataPath(soundElemEle.getPath());
        } else {
            final String path =
                    UIKitConstants.RECORD_DOWNLOAD_DIR + soundElemEle.getUuid();
            File file = new File(path);
            if (!file.exists()) {
                soundElemEle.getSoundToFile(path, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        QLog.e("MessageInfoUtil getSoundToFile", code + ":" + desc);
                    }

                    @Override
                    public void onSuccess() {
                        msgInfo.setDataPath(path);
                    }
                });
            } else {
                msgInfo.setDataPath(path);
            }
        }
        msgInfo.setExtra("[语音]");
    }

    /**
     * 处理自定义表情
     *
     * @param ele
     * @param msgInfo
     */
    private static boolean dealFaceMsg(TIMElem ele, MessageInfo msgInfo) {
        TIMFaceElem txtEle = (TIMFaceElem) ele;
        if (txtEle.getIndex() < 1 || txtEle.getData() == null) {
            QLog.e("MessageInfoUtil", "txtEle data is null or index<1");
            return false;
        }
        msgInfo.setExtra("[自定义表情]");
        return true;
    }

    private static void dealImageMsg(TIMElem ele, MessageInfo msgInfo) {
        msgInfo.setMsgType(MessageType.MSG_TYPE_IMAGE);
        TIMImageElem imageEle = (TIMImageElem) ele;
        String localPath = imageEle.getPath();
        msgInfo.setDataPath(localPath);

        if (msgInfo.isSelf() && !TextUtils.isEmpty(localPath)) {
            int size[] = ImageUtil.getImageSize(localPath);
            msgInfo.setImgWithd(size[0]);
            msgInfo.setImgHeight(size[1]);
        }
        //本地路径为空，可能为更换手机或者是接收的消息
        else {
            List<TIMImage> imgs = imageEle.getImageList();
            for (int i = 0; i < imgs.size(); i++) {
                TIMImage img = imgs.get(i);
                if (img.getType() == TIMImageType.Thumb) {
                    final String path =
                            UIKitConstants.IMAGE_DOWNLOAD_DIR + img.getUuid();
                    msgInfo.setImgWithd((int) img.getWidth());
                    msgInfo.setImgHeight((int) img.getHeight());
                    File file = new File(path);
                    if (file.exists()) {
                        msgInfo.setDataPath(path);
                    }
                }
            }
        }

        msgInfo.setExtra("[图片]");
    }

    /**
     * 处理自定义消息
     *
     * @param ele
     * @param msgInfo
     */
    private static void dealCustomMsg(TIMElem ele, MessageInfo msgInfo) {
        TIMCustomElem customElem = (TIMCustomElem) ele;
        String data = new String(customElem.getData());
        if (data.equals(GROUP_CREATE)) {
            msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_CREATE);
            msgInfo.setExtra(new String(customElem.getExt()));
        } else if (data.equals(GROUP_DELETE)) {
            msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_DELETE);
            msgInfo.setExtra(new String(customElem.getExt()));
        } else {
            msgInfo.setExtra("[自定义消息]");
        }
    }

    /**
     * 处理群组相关信息
     *
     * @param ele
     * @param msgInfo
     */
    private static void dealGroupMsg(TIMElem ele, MessageInfo msgInfo) {
        TIMGroupTipsElem groupTips = (TIMGroupTipsElem) ele;
        TIMGroupTipsType tipsType = groupTips.getTipsType();
        String user = "";
        if (groupTips.getChangedGroupMemberInfo().size() > 0) {
            Object ids[] = groupTips.getChangedGroupMemberInfo().keySet().toArray();
            for (int i = 0; i < ids.length; i++) {
                user = user + ids[i].toString();
                if (i != 0)
                    user = "，" + user;
                if (i == 2 && ids.length > 3) {
                    user = user + "等";
                    break;
                }
            }

        } else
            user = groupTips.getOpUserInfo().getIdentifier();
        String message = user;
        if (tipsType == TIMGroupTipsType.Join) {
            msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_JOIN);
            message = message + "加入群组";
        }
        if (tipsType == TIMGroupTipsType.Quit) {
            msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_QUITE);
            message = message + "退出群组";
        }
        if (tipsType == TIMGroupTipsType.Kick) {
            msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_KICK);
            message = message + "被踢出群组";
        }
        if (tipsType == TIMGroupTipsType.ModifyGroupInfo) {
            List<TIMGroupTipsElemGroupInfo> modifyList = groupTips.getGroupInfoList();
            if (modifyList.size() > 0) {
                TIMGroupTipsElemGroupInfo modifyInfo = modifyList.get(0);
                TIMGroupTipsGroupInfoType modifyType = modifyInfo.getType();
                if (modifyType == TIMGroupTipsGroupInfoType.ModifyName) {
                    msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_MODIFY_NAME);
                    message = message + "修改群名称为\"" + modifyInfo.getContent() + "\"";
                } else if (modifyType == TIMGroupTipsGroupInfoType.ModifyNotification) {
                    msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_MODIFY_NOTICE);
                    message = message + "修改群公告";
                }
            }
        }
        msgInfo.setExtra(message);
    }

    /**
     * 处理文本消息
     *
     * @param timMessage
     * @param msgInfo
     * @param ele
     * @param isLiveView
     * @return
     */
    private static boolean dealTextTypeMsg(TIMMessage timMessage, MessageInfo msgInfo,
                                           TIMElem ele, boolean isLiveView) {

        TIMTextElem timTextElem = (TIMTextElem) ele;
        msgInfo.setMsgType(MessageType.MSG_TYPE_TEXT);

        String content = timTextElem.getText();

        L.d(LiveManager.TAG, "test_content content: " + content);

        //打赏
        if (content.contains(LiveConfig.BlockADM_PAY)) {
            msgInfo.setNormalMsg(true);
            msgInfo.setReward(true);
            msgInfo.setContent(subContent(content));
            if (!isLiveView) { //只显示在直播界面
                return false;
            }
        }
        //禁言 或 非禁言
        else if (content.contains(LiveConfig.BlockADM_BAN) || content.contains(LiveConfig.BlockADM_CAN)) {
            msgInfo.setNormalMsg(true);
            msgInfo.setLimit(true);
            msgInfo.setContent(subContent(content));
        }
        //领红包
        else if (content.contains(LiveConfig.BlockADM_LHB)) {
            msgInfo.setNormalMsg(true);
            msgInfo.setReceiveHongbao(true);
            msgInfo.setContent(subContent(content));
            if (!isLiveView) { //只显示在直播界面
                return false;
            }
        }
        //发红包
        else if (content.contains(LiveConfig.BlockADM_FHB)) {
            msgInfo.setSendHongbao(true);
            msgInfo.setHongbaoId(subContentToInt(content));
            if (!isLiveView) { //只显示在直播界面
                return false;
            }
        }
        //提问 内容格式：名称 + BlockADM_ASK + 内容
        else if (content.contains(LiveConfig.BlockADM_ASK)) {
            msgInfo.setAskQuestion(true);
            int index = content.indexOf(LiveConfig.BlockADM_ASK);
            String name = content.substring(0, index);
            String subContent = content.substring(index + LiveConfig.BlockADM_ASK.length());

            L.d(LiveManager.TAG,
                    "test_content name: " + name + " ; subContent: " + subContent);
            msgInfo.setName(name);
            msgInfo.setContent(subContent);
            if (!isLiveView) { //只显示在直播界面
                return false;
            }
        } else {

            //普通文本消息，在直播界面只显示管理员发出的消息，不显示普通用户的消息
            if (isLiveView && !LiveManager.getInstance().isSpeakerOrManagerMsg(timMessage)) {
                return false;
            }

            //管理员在直播界面发出的消息，只显示在直播界面，不显示在交流界面
            if (content.contains(LiveConfig.BlockADM_ZBM)) {
                if (!isLiveView) {
                    return false;
                } else {
                    msgInfo.setContent(subContent(content));
                }
            }

            //管理员在交流界面发出的消息，只显示在交流界面，不显示在直播界面
            else if (content.contains(LiveConfig.BlockADM_JLM)) {
                if (isLiveView) {
                    return false;
                } else {
                    msgInfo.setContent(subContent(content));
                }

            } else {
                msgInfo.setContent(timTextElem.getText());
            }

        }

        return true;
    }


    public static boolean checkMessage(TIMMessage msg, TIMCallBack callBack) {
        if (msg.getElementCount() > 0) {
            TIMElem ele = msg.getElement(0);
            if (ele.getType() == TIMElemType.Video) {
                TIMVideoElem videoEle = (TIMVideoElem) ele;
                TIMVideo video = (TIMVideo) videoEle.getVideoInfo();
                TIMSnapshot snapshot = videoEle.getSnapshotInfo();
                final String snapPath = UIKitConstants.IMAGE_DOWNLOAD_DIR + video.getUuid();
                //判断快照是否存在,不存在自动下载
                if (!new File(snapPath).exists()) {
                    snapshot.getImage(snapPath, callBack);
                }
                return true;
            } else if (ele.getType() == TIMElemType.Image) {
                TIMImageElem imageEle = (TIMImageElem) ele;
                List<TIMImage> imgs = imageEle.getImageList();
                for (int i = 0; i < imgs.size(); i++) {
                    TIMImage img = imgs.get(i);
                    if (img.getType() == TIMImageType.Thumb) {
                        final String path = UIKitConstants.IMAGE_DOWNLOAD_DIR + img.getUuid();
                        File file = new File(path);
                        if (!file.exists()) {
                            img.getImage(path, callBack);
                        }
                        return true;
                    }
                }
            }
            return false;

        }
        return true;
    }

    public static int TIMElemType2MessageInfoType(TIMElemType type) {
        switch (type) {
            case Text:
                return MessageType.MSG_TYPE_TEXT;
            case Image:
                return MessageType.MSG_TYPE_IMAGE;
            case Sound:
                return MessageType.MSG_TYPE_AUDIO;
            case Video:
                return MessageType.MSG_TYPE_VIDEO;
            case File:
                return MessageType.MSG_TYPE_FILE;
            case Location:
                return MessageType.MSG_TYPE_LOCATION;
            case Face:
                return MessageType.MSG_TYPE_CUSTOM_FACE;
            case GroupTips:
                return MessageType.MSG_TYPE_TIPS;
        }

        return -1;
    }

    public static interface CopyHandler {
        void copyComplete(MessageInfo messageInfo);
    }

    /*
    TIMTextElem timTextElem = (TIMTextElem) ele;
                msgInfo.setMsgType(MessageType.MSG_TYPE_TEXT);

                String content = timTextElem.getText();

                L.d(LiveManager.TAG, "test_content content: " + content);



                //打赏
                if (content.contains(LiveConfig.BlockADM_PAY)) {
                    msgInfo.setNormalMsg(true);
                    msgInfo.setReward(true);
                    msgInfo.setContent(subContent(content));
                    if (!isLiveView) { //只显示在直播界面
                        return;
                    }
                }
                //禁言 或 非禁言
                else if (content.contains(LiveConfig.BlockADM_BAN) || content.contains(LiveConfig
                .BlockADM_CAN)) {
                    msgInfo.setNormalMsg(true);
                    msgInfo.setLimit(true);
                    msgInfo.setContent(subContent(content));
                }
                //领红包
                else if (content.contains(LiveConfig.BlockADM_LHB)) {
                    msgInfo.setNormalMsg(true);
                    msgInfo.setReceiveHongbao(true);
                    msgInfo.setContent(subContent(content));
                    if (!isLiveView) { //只显示在直播界面
                        return;
                    }
                }
                //发红包
                else if (content.contains(LiveConfig.BlockADM_FHB)) {
                    msgInfo.setSendHongbao(true);
                    msgInfo.setHongbaoId(subContentToInt(content));
                    if (!isLiveView) { //只显示在直播界面
                        return;
                    }
                }
                //提问 内容格式：名称 + BlockADM_ASK + 内容
                else if (content.contains(LiveConfig.BlockADM_ASK)) {
                    isAskTextType = true;
                    msgInfo.setAskQuestion(true);
                    int index = content.indexOf(LiveConfig.BlockADM_ASK);
                    String name = content.substring(0, index);
                    String subContent = content.substring(index + LiveConfig.BlockADM_ASK.length());

                    L.d(LiveManager.TAG,
                            "test_content name: " + name + " ; subContent: " + subContent);
                    msgInfo.setName(name);
                    msgInfo.setContent(subContent);
                    if (!isLiveView) { //只显示在直播界面
                        return;
                    }
                } else {

                    //普通文本消息，在直播界面只显示管理员发出的消息，不显示普通用户的消息
                    if (isLiveView && !LiveManager.getInstance().isSpeakerOrManagerMsg
                    (timMessage)) {
                        return;
                    }

                    //管理员在直播界面发出的消息，只显示在直播界面，不显示在交流界面
                    if (content.contains(LiveConfig.BlockADM_ZBM)) {
                        if (!isLiveView) {
                            return;
                        }else {
                            msgInfo.setContent(subContent(content));
                        }
                    }

                    //管理员在交流界面发出的消息，只显示在交流界面，不显示在直播界面
                    else if (content.contains(LiveConfig.BlockADM_JLM) ) {
                        if (isLiveView) {
                            return;
                        }else {
                            msgInfo.setContent(subContent(content));
                        }

                    }else {
                        msgInfo.setContent(timTextElem.getText());
                    }

                }

     */

    /*
    TIMUserProfile timUserProfile = timMessage.getSenderProfile();

        if (timUserProfile != null)
            L.d(LiveManager.TAG_C, "nickName: " + timUserProfile.getNickName() + " ; " +
                    "headImageUrl: " + timUserProfile.getFaceUrl() + " ; timUserProfile" +
                    ".getIdentifier(): " + timUserProfile.getIdentifier() + " ; timMessage.isSelf" +
                    "(): " + timMessage.isSelf());

        //获取名字 头像 账号
        final String[] nickName = new String[1];
        final String[] identifier = new String[1];
        final String[] headImageUrl = new String[1];

        if (timMessage.isSelf()) {
            nickName[0] = userInfoDto.getNickName();
            identifier[0] = userInfoDto.getLoginIMAccount();
            headImageUrl[0] = userInfoDto.getIcon();

            msgInfo.setIdentifier(identifier[0]);

            if (!isAskTextType)
                msgInfo.setName(nickName[0]);
            msgInfo.setHeadImageUrl(headImageUrl[0]);

            tCallback.callback(msgInfo);
        } else {
            if (timUserProfile == null || StringUtils.isEmpty(timUserProfile.getFaceUrl()) ||
            StringUtils.isEmpty(timUserProfile.getNickName())) {

                final boolean finalIsAskTextType = isAskTextType;
                getUsersProfileByIdentifier(timUserProfile.getIdentifier(),
                        new TCallback<List<TIMUserProfile>>() {
                            @Override
                            public void callback(List<TIMUserProfile> timUserProfiles) {
                                if (ListUtils.isNotEmpty(timUserProfiles)) {
                                    nickName[0] = timUserProfiles.get(0).getNickName();
                                    identifier[0] = timUserProfiles.get(0).getIdentifier();
                                    headImageUrl[0] = timUserProfiles.get(0).getFaceUrl();

                                    L.d(LiveManager.TAG_C,
                                            " getUsersProfileByIdentifier() --> name: " +
                                            nickName[0] +
                                                    " ; headImageUrl: " + headImageUrl[0] + " ; " +
                                                    "identifier(): " + identifier[0]);
                                    msgInfo.setIdentifier(identifier[0]);
                                    if (!finalIsAskTextType)
                                        msgInfo.setName(nickName[0]);
                                    msgInfo.setHeadImageUrl(headImageUrl[0]);

                                    tCallback.callback(msgInfo);
                                }
                            }
                        });

            } else {
                nickName[0] = timUserProfile.getNickName();
                identifier[0] = timUserProfile.getIdentifier();
                headImageUrl[0] = timUserProfile.getFaceUrl();

                msgInfo.setIdentifier(identifier[0]);
                if (!isAskTextType)
                    msgInfo.setName(nickName[0]);
                msgInfo.setHeadImageUrl(headImageUrl[0]);

                tCallback.callback(msgInfo);
            }
        }
     */

    //    public static MessageInfo TIMMessage2MessageInfo(TIMMessage timMessage,
    //                                                     UserInfoDto userInfoDto, boolean
    //                                                     isGroup) {
    //        if (timMessage == null || timMessage.status() == TIMMessageStatus.HasDeleted)
    //            return null;
    //        String sender = timMessage.getSender();
    //        final MessageInfo msgInfo = new MessageInfo();
    //        msgInfo.setTIMMessage(timMessage);
    //        msgInfo.setGroup(isGroup);
    //        msgInfo.setMsgId(timMessage.getMsgId());
    //
    //        if (isGroup) {
    //            TIMGroupMemberInfo memberInfo = timMessage.getSenderGroupMemberProfile();
    //            if (memberInfo != null && !TextUtils.isEmpty(memberInfo.getNameCard()))
    //                msgInfo.setFromUser(memberInfo.getNameCard());
    //            else
    //                msgInfo.setFromUser(sender);
    //        } else {
    //            msgInfo.setFromUser(sender);
    //        }
    //        msgInfo.setMsgTime(timMessage.timestamp() * 1000);
    //        msgInfo.setPeer(timMessage.getConversation().getPeer());
    //        msgInfo.setSelf(sender.equals(TIMManager.getInstance().getLoginUser()));
    //
    //        if (timMessage.getElementCount() > 0) {
    //            TIMElem ele = timMessage.getElement(0);
    //            //            TIMTextElem timTextElem = (TIMTextElem) timMessage.getElement(0);
    //            //            msgInfo.setContent(timTextElem.getText());
    //
    //            //获取当前元素的类型
    //            TIMElemType elemType = ele.getType();
    //
    //            L.d(LiveManager.TAG, " 获取当前元素的类型: " + elemType);
    //            //            L.d(LiveManager.TAG, "elem type: " + elemType.name());
    //            if (elemType == TIMElemType.Text) { //处理文本消息
    //                TIMTextElem timTextElem = (TIMTextElem) ele;
    //                msgInfo.setMsgType(MessageType.MSG_TYPE_TEXT);
    //
    //                String content = timTextElem.getText();
    //
    //                //打赏
    //                if (content.contains(LiveConfig.BlockADM_PAY)) {
    //                    msgInfo.setNormalMsg(true);
    //                    msgInfo.setReward(true);
    //                    msgInfo.setContent(content.substring(0, LiveConfig.BlockADM_PAY.length
    //                    ()));
    //                }
    //                //禁言 或 非禁言
    //                else if (content.contains(LiveConfig.BlockADM_BAN) || content.contains
    //                (LiveConfig.BlockADM_CAN)) {
    //                    msgInfo.setNormalMsg(true);
    //                    msgInfo.setLimit(true);
    //                    msgInfo.setContent(content.substring(0, LiveConfig.BlockADM_BAN.length
    //                    ()));
    //                }
    //                //领红包
    //                else if (content.contains(LiveConfig.BlockADM_LHB)) {
    //                    msgInfo.setNormalMsg(true);
    //                    msgInfo.setReceiveHongbao(true);
    //                    msgInfo.setContent(content.substring(0, LiveConfig.BlockADM_LHB.length
    //                    ()));
    //                }
    //                //发红包
    //                else if (content.contains(LiveConfig.BlockADM_FHB)) {
    //                    msgInfo.setSendHongbao(true);
    //                }
    //                //提问 内容格式：名称 + BlockADM_ASK + 内容
    //                else if (content.contains(LiveConfig.BlockADM_ASK)) {
    //                    msgInfo.setAskQuestion(true);
    //
    //                    int index = content.indexOf(LiveConfig.BlockADM_ASK);
    //                    msgInfo.setName(content.substring(0, index));
    //                    msgInfo.setContent(content.substring(index+LiveConfig.BlockADM_ASK
    //                    .length()));
    //                } else {
    //                    msgInfo.setContent(timTextElem.getText());
    //                }
    //
    //            } else if (elemType == TIMElemType.Image) {//处理图片消息
    //                msgInfo.setMsgType(MessageType.MSG_TYPE_IMAGE);
    //                TIMImageElem imageEle = (TIMImageElem) ele;
    //                String localPath = imageEle.getPath();
    //                msgInfo.setDataPath(localPath);
    //
    //            } else if (elemType == TIMElemType.Sound) {//处理语音消息
    //                msgInfo.setMsgType(MessageType.MSG_TYPE_AUDIO);
    //            }
    //
    //            if (ele == null) {
    //                QLog.e("MessageInfoUtil", "msg found null elem");
    //                return null;
    //            }
    //
    //            TIMElemType type = ele.getType();
    //            if (type == TIMElemType.Invalid) {
    //                QLog.e("MessageInfoUtil", "invalid");
    //                return null;
    //            }
    //
    //            if (type == TIMElemType.Custom) {
    //                TIMCustomElem customElem = (TIMCustomElem) ele;
    //                String data = new String(customElem.getData());
    //                if (data.equals(GROUP_CREATE)) {
    //                    msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_CREATE);
    //                    msgInfo.setExtra(new String(customElem.getExt()));
    //                } else if (data.equals(GROUP_DELETE)) {
    //                    msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_DELETE);
    //                    msgInfo.setExtra(new String(customElem.getExt()));
    //                } else {
    //                    msgInfo.setExtra("[自定义消息]");
    //                }
    //            } else if (type == TIMElemType.GroupTips) {
    //                TIMGroupTipsElem groupTips = (TIMGroupTipsElem) ele;
    //                TIMGroupTipsType tipsType = groupTips.getTipsType();
    //                String user = "";
    //                if (groupTips.getChangedGroupMemberInfo().size() > 0) {
    //                    Object ids[] = groupTips.getChangedGroupMemberInfo().keySet().toArray();
    //                    for (int i = 0; i < ids.length; i++) {
    //                        user = user + ids[i].toString();
    //                        if (i != 0)
    //                            user = "，" + user;
    //                        if (i == 2 && ids.length > 3) {
    //                            user = user + "等";
    //                            break;
    //                        }
    //                    }
    //
    //                } else
    //                    user = groupTips.getOpUserInfo().getIdentifier();
    //                String message = user;
    //                if (tipsType == TIMGroupTipsType.Join) {
    //                    msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_JOIN);
    //                    message = message + "加入群组";
    //                }
    //                if (tipsType == TIMGroupTipsType.Quit) {
    //                    msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_QUITE);
    //                    message = message + "退出群组";
    //                }
    //                if (tipsType == TIMGroupTipsType.Kick) {
    //                    msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_KICK);
    //                    message = message + "被踢出群组";
    //                }
    //                if (tipsType == TIMGroupTipsType.ModifyGroupInfo) {
    //                    List<TIMGroupTipsElemGroupInfo> modifyList = groupTips.getGroupInfoList();
    //                    if (modifyList.size() > 0) {
    //                        TIMGroupTipsElemGroupInfo modifyInfo = modifyList.get(0);
    //                        TIMGroupTipsGroupInfoType modifyType = modifyInfo.getType();
    //                        if (modifyType == TIMGroupTipsGroupInfoType.ModifyName) {
    //                            msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_MODIFY_NAME);
    //                            message = message + "修改群名称为\"" + modifyInfo.getContent() + "\"";
    //                        } else if (modifyType == TIMGroupTipsGroupInfoType
    //                        .ModifyNotification) {
    //                            msgInfo.setMsgType(MessageType.MSG_TYPE_GROUP_MODIFY_NOTICE);
    //                            message = message + "修改群公告";
    //                        }
    //                    }
    //                }
    //                msgInfo.setExtra(message);
    //            } else {
    //                if (type == TIMElemType.Text) {
    //                    TIMTextElem txtEle = (TIMTextElem) ele;
    //                    msgInfo.setExtra(txtEle.getText());
    //                } else if (type == TIMElemType.Face) {
    //                    TIMFaceElem txtEle = (TIMFaceElem) ele;
    //                    if (txtEle.getIndex() < 1 || txtEle.getData() == null) {
    //                        QLog.e("MessageInfoUtil", "txtEle data is null or index<1");
    //                        return null;
    //                    }
    //                    msgInfo.setExtra("[自定义表情]");
    //
    //
    //                } else if (type == TIMElemType.Sound) {
    //                    TIMSoundElem soundElemEle = (TIMSoundElem) ele;
    //                    if (msgInfo.isSelf()) {
    //                        msgInfo.setDataPath(soundElemEle.getPath());
    //                    } else {
    //                        final String path =
    //                                UIKitConstants.RECORD_DOWNLOAD_DIR + soundElemEle.getUuid();
    //                        File file = new File(path);
    //                        if (!file.exists()) {
    //                            soundElemEle.getSoundToFile(path, new TIMCallBack() {
    //                                @Override
    //                                public void onError(int code, String desc) {
    //                                    QLog.e("MessageInfoUtil getSoundToFile", code + ":" +
    //                                    desc);
    //                                }
    //
    //                                @Override
    //                                public void onSuccess() {
    //                                    msgInfo.setDataPath(path);
    //                                }
    //                            });
    //                        } else {
    //                            msgInfo.setDataPath(path);
    //                        }
    //                    }
    //                    msgInfo.setExtra("[语音]");
    //                } else if (type == TIMElemType.Image) {
    //                    TIMImageElem imageEle = (TIMImageElem) ele;
    //                    String localPath = imageEle.getPath();
    //                    if (msgInfo.isSelf() && !TextUtils.isEmpty(localPath)) {
    //                        msgInfo.setDataPath(localPath);
    //                        int size[] = ImageUtil.getImageSize(localPath);
    //                        msgInfo.setImgWithd(size[0]);
    //                        msgInfo.setImgHeight(size[1]);
    //                    }
    //                    //本地路径为空，可能为更换手机或者是接收的消息
    //                    else {
    //                        List<TIMImage> imgs = imageEle.getImageList();
    //                        for (int i = 0; i < imgs.size(); i++) {
    //                            TIMImage img = imgs.get(i);
    //                            if (img.getType() == TIMImageType.Thumb) {
    //                                final String path =
    //                                        UIKitConstants.IMAGE_DOWNLOAD_DIR + img.getUuid();
    //                                msgInfo.setImgWithd((int) img.getWidth());
    //                                msgInfo.setImgHeight((int) img.getHeight());
    //                                File file = new File(path);
    //                                if (file.exists()) {
    //                                    msgInfo.setDataPath(path);
    //                                }
    //                            }
    //                        }
    //                    }
    //
    //                    msgInfo.setExtra("[图片]");
    //                } else if (type == TIMElemType.Video) {
    //                    TIMVideoElem videoEle = (TIMVideoElem) ele;
    //                    if (msgInfo.isSelf() && !TextUtils.isEmpty(videoEle.getSnapshotPath())) {
    //                        int size[] = ImageUtil.getImageSize(videoEle.getSnapshotPath());
    //                        msgInfo.setImgWithd(size[0]);
    //                        msgInfo.setImgHeight(size[1]);
    //                        msgInfo.setDataPath(videoEle.getSnapshotPath());
    //                        msgInfo.setDataUri(FileUtil.getUriFromPath(videoEle.getVideoPath()));
    //                    } else {
    //                        TIMVideo video = videoEle.getVideoInfo();
    //                        final String videoPath =
    //                                UIKitConstants.VIDEO_DOWNLOAD_DIR + video.getUuid();
    //                        Uri uri = Uri.parse(videoPath);
    //                        msgInfo.setDataUri(uri);
    //                        TIMSnapshot snapshot = videoEle.getSnapshotInfo();
    //                        msgInfo.setImgWithd((int) snapshot.getWidth());
    //                        msgInfo.setImgHeight((int) snapshot.getHeight());
    //                        final String snapPath =
    //                                UIKitConstants.IMAGE_DOWNLOAD_DIR + snapshot.getUuid();
    //                        //判断快照是否存在,不存在自动下载
    //                        if (new File(snapPath).exists()) {
    //                            msgInfo.setDataPath(snapPath);
    //                        }
    //                    }
    //
    //                    msgInfo.setExtra("[视频]");
    //                } else if (type == TIMElemType.File) {
    //                    TIMFileElem fileElem = (TIMFileElem) ele;
    //                    final String path = UIKitConstants.FILE_DOWNLOAD_DIR + fileElem.getUuid();
    //                    if (!msgInfo.isSelf()) {
    //                        File file = new File(path);
    //                        if (!file.exists()) {
    //                            msgInfo.setStatus(MessageType.MSG_STATUS_UN_DOWNLOAD);
    //                        } else {
    //                            msgInfo.setStatus(MessageType.MSG_STATUS_DOWNLOADED);
    //                        }
    //                        msgInfo.setDataPath(path);
    //                    } else {
    //                        if (TextUtils.isEmpty(fileElem.getPath())) {
    //                            fileElem.getToFile(path, new TIMCallBack() {
    //                                @Override
    //                                public void onError(int code, String desc) {
    //                                    QLog.e("MessageInfoUtil getToFile", code + ":" + desc);
    //                                }
    //
    //                                @Override
    //                                public void onSuccess() {
    //                                    msgInfo.setDataPath(path);
    //                                }
    //                            });
    //                        } else {
    //                            msgInfo.setStatus(MessageType.MSG_STATUS_SEND_SUCCESS);
    //                            msgInfo.setDataPath(fileElem.getPath());
    //                        }
    //
    //                    }
    //                    msgInfo.setExtra("[文件]");
    //                }
    //                msgInfo.setMsgType(TIMElemType2MessageInfoType(type));
    //            }
    //
    //        } else {
    //            QLog.e("MessageInfoUtil", "msg elecount is 0");
    //            return null;
    //        }
    //
    //        if (timMessage.status() == TIMMessageStatus.HasRevoked) {
    //            msgInfo.setStatus(MessageType.MSG_STATUS_REVOKE);
    //            msgInfo.setMsgType(MessageType.MSG_STATUS_REVOKE);
    //        } else {
    //            if (msgInfo.isSelf()) {
    //                if (timMessage.status() == TIMMessageStatus.SendFail) {
    //                    msgInfo.setStatus(MessageType.MSG_STATUS_SEND_FAIL);
    //                } else if (timMessage.status() == TIMMessageStatus.SendSucc) {
    //                    msgInfo.setStatus(MessageType.MSG_STATUS_SEND_SUCCESS);
    //                } else if (timMessage.status() == TIMMessageStatus.Sending) {
    //                    msgInfo.setStatus(MessageType.MSG_STATUS_SENDING);
    //                }
    //            }
    //        }
    //
    //        TIMUserProfile timUserProfile = timMessage.getSenderProfile();
    //
    //        if (timUserProfile != null)
    //            L.d(LiveManager.TAG_C, "nickName: " + timUserProfile.getNickName() + " ; " +
    //                    "headImageUrl: " + timUserProfile.getFaceUrl() + " ; timUserProfile" +
    //                    ".getIdentifier(): " + timUserProfile.getIdentifier() + " ; timMessage
    //                    .isSelf" +
    //                    "(): " + timMessage.isSelf());
    //
    //        final String[] nickName = new String[1];
    //        final String[] identifier = new String[1];
    //        final String[] headImageUrl = new String[1];
    //
    //        if (timMessage.isSelf()) {
    //            nickName[0] = userInfoDto.getNickName();
    //            identifier[0] = userInfoDto.getLoginIMAccount();
    //            headImageUrl[0] = userInfoDto.getIcon();
    //        } else {
    //            if (timUserProfile == null || StringUtils.isEmpty(timUserProfile.getFaceUrl())
    //            || StringUtils.isEmpty(timUserProfile.getNickName())) {
    //
    //                getUsersProfileByIdentifier(timUserProfile.getIdentifier(),
    //                        new TCallback<List<TIMUserProfile>>() {
    //                            @Override
    //                            public void callback(List<TIMUserProfile> timUserProfiles) {
    //                                if (ListUtils.isNotEmpty(timUserProfiles)) {
    //                                    nickName[0] = timUserProfiles.get(0).getNickName();
    //                                    identifier[0] = timUserProfiles.get(0).getIdentifier();
    //                                    headImageUrl[0] = timUserProfiles.get(0).getFaceUrl();
    //
    //                                    L.d(LiveManager.TAG_C,
    //                                            " getUsersProfileByIdentifier() --> name: " +
    //                                            nickName[0] +
    //                                                    " ; headImageUrl: " + headImageUrl[0] +
    //                                                    " ; " +
    //                                                    "identifier(): " + identifier[0]);
    //
    //                                }
    //                            }
    //                        });
    //
    //            } else {
    //                nickName[0] = timUserProfile.getNickName();
    //                identifier[0] = timUserProfile.getIdentifier();
    //                headImageUrl[0] = timUserProfile.getFaceUrl();
    //            }
    //        }
    //
    //        //        if (timUserProfile == null || StringUtils.isEmpty(timUserProfile
    //        .getIdentifier
    //        //        ())) {
    //        //            nickName = userInfoDto.getNickName();
    //        //            identifier = userInfoDto.getLoginIMAccount();
    //        //            headImageUrl = userInfoDto.getIcon();
    //        //        }else {
    //        //            nickName = timUserProfile.getNickName();
    //        //            identifier = timUserProfile.getIdentifier();
    //        //            headImageUrl = timUserProfile.getFaceUrl();
    //        //        }
    //
    //        msgInfo.setIdentifier(identifier[0]);
    //        msgInfo.setName(nickName[0]);
    //        msgInfo.setHeadImageUrl(headImageUrl[0]);
    //
    //
    //        return msgInfo;
    //    }


}

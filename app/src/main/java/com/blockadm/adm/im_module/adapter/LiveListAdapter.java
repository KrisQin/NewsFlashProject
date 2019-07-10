package com.blockadm.adm.im_module.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.call.LiveMsgCallback;
import com.blockadm.common.call.OnImageClickListener;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.call.OnSoundClickListener;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.call.TCallback;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.im.entity.MessageType;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMImage;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSoundElem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/5/9
 *
 * @Describe TODO { 直播列表 }
 */
public class LiveListAdapter extends RecyclerView.Adapter {

    private List<MessageInfo> mMsgInfoList;
    private Context mContext;
    private LiveMsgCallback mCallback;

    private int audio_min_width = 0;
    private int audio_max_width = 0;

    public LiveListAdapter(Context context, List<MessageInfo> msgInfoList,
                           LiveMsgCallback callback) {
        mContext = context;
        mMsgInfoList = msgInfoList;
        mCallback = callback;

        audio_min_width = ScreenUtils.dip2px(mContext, 60);
        audio_max_width = ScreenUtils.dip2px(mContext, 250);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view;

        L.d("ttt", " viewType: " + viewType);

        //历史消息或者别人发出的消息
        if (viewType == live_msg) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_receive_text_image_sound,
                    parent, false);
            return new TextImageSoundMsgHolder(view, mItemClickListener);
        }
        //当下自己发送的消息
        else if (viewType == my_send_msg) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_send_text_image_sound,
                    parent, false);
            return new TextImageSoundMsgHolder(view, mItemClickListener);
        }
        //打赏
        else if (viewType == reward_msg) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward_manager,
                    parent, false);
            return new RewardMsgHolder(view);
        }
        //其他普通消息
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal_list,
                    parent, false);
            return new NormalTextHolder(view, mItemClickListener);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final MessageInfo msg = mMsgInfoList.get(position);

        L.d("ttt",
                "viewHolder instanceof NormalTextHolder: " + (viewHolder instanceof NormalTextHolder));

        if (viewHolder instanceof NormalTextHolder) {

            try {
                NormalTextHolder holder = (NormalTextHolder) viewHolder;

                holder.tv_limit.setVisibility(View.GONE);
                holder.tv_receive_hongbao.setVisibility(View.GONE);

                String content = StringUtils.isEmpty(msg.getContent()) ? "" : msg.getContent();

                if (msg.isLimit()) {
                    holder.tv_limit.setVisibility(View.VISIBLE);
                    holder.tv_limit.setText(content);
                } else if (msg.isReceiveHongbao()) {
                    holder.tv_receive_hongbao.setVisibility(View.VISIBLE);
                    if (content.contains("红包")) {
                        SpannableString spannableString = new SpannableString(content);
                        ForegroundColorSpan foregroundColorSpan =
                                new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(foregroundColorSpan,
                                content.length() - 2,
                                content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        holder.tv_receive_hongbao.setText(spannableString);
                    } else {
                        holder.tv_receive_hongbao.setText(content);
                    }
                }

            } catch (Exception e) {
                L.d("ttt", " Exception e: " + e.toString());
            }

        } else if (viewHolder instanceof RewardMsgHolder) {
            RewardMsgHolder holder = (RewardMsgHolder) viewHolder;
            String content = StringUtils.isEmpty(msg.getContent()) ? "" : msg.getContent();

            List<String> managerNameList = LiveManager.getInstance().getManagerNameList();
            if (managerNameList != null) {
                for (int i = 0; i < managerNameList.size(); i++) {
                    String name = managerNameList.get(i);
                    if (content.contains(name)) {

                        SpannableString spannableString = new SpannableString(content);
                        ForegroundColorSpan foregroundColorSpan =
                                new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(foregroundColorSpan,
                                name.length() + content.indexOf(name),
                                content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        holder.tv_reward.setText(spannableString);

                        break;
                    }
                }
            }

        } else if (viewHolder instanceof TextImageSoundMsgHolder) {
            final TextImageSoundMsgHolder holder = (TextImageSoundMsgHolder) viewHolder;

            Glide.with(mContext).load(msg.getHeadImageUrl()).error(R.mipmap.picture_default).into(holder.iv_head_image);
            holder.tv_name.setText(msg.getName());
            holder.tv_status.setText(msg.isSpeaker() ? "主讲人" : "管理员");
            holder.tv_status.setVisibility(View.VISIBLE);

            holder.iv_hb_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.clickHBCallback(msg);
                    }
                }
            });

            holder.iv_head_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.clickHeadImageCallback(msg);
                    }
                }
            });

            if (msg.getMsgType() == MessageType.MSG_TYPE_TEXT) {
                L.d(LiveManager.TAG, "adapter msg.getMsgType() == MessageType.MSG_TYPE_TEXT ");
                showTextMsg(holder, msg);

            } else if (msg.getMsgType() == MessageType.MSG_TYPE_IMAGE) {
                L.d(LiveManager.TAG, "adapter msg.getMsgType() == MessageType.MSG_TYPE_IMAGE ");

                showImageMsg(holder, msg);
            } else if (msg.getMsgType() == MessageType.MSG_TYPE_AUDIO) {

                L.d(LiveManager.TAG, "adapter msg.getMsgType() == MessageType.MSG_TYPE_AUDIO ");
                showAudioMsg(holder, msg, position);
            }


        }

    }

    private void showAudioMsg(final TextImageSoundMsgHolder holder, MessageInfo msg,
                              final int position) {
        holder.layout_text.setVisibility(View.VISIBLE);
        holder.iv_player.setVisibility(View.VISIBLE);
        holder.tv_time.setVisibility(View.VISIBLE);
        holder.iv_sanjiao.setVisibility(View.VISIBLE);

        holder.tv_content.setVisibility(View.GONE);
        holder.iv_hb_image.setVisibility(View.GONE);
        holder.iv_pic.setVisibility(View.GONE);

        int duration;
        if (msg.isHistoryMsg()) {
            duration = msg.getPlayTime();
        } else {
            final TIMSoundElem soundElem = (TIMSoundElem) msg.getTIMMessage().getElement(0);
            duration = (int) soundElem.getDuration();
        }

        if (duration == 0)
            duration = 1;

        RelativeLayout.LayoutParams audioParams =
                (RelativeLayout.LayoutParams) holder.layout_text.getLayoutParams();
        audioParams.width = audio_min_width + ScreenUtils.dip2px(mContext, duration * 10);
        if (audioParams.width > audio_max_width)
            audioParams.width = audio_max_width;

        holder.layout_text.setLayoutParams(audioParams);
        holder.tv_time.setText(duration + "''");
        holder.tv_time.setVisibility(View.VISIBLE);
        holder.tv_content.setText("");


        L.d(LiveManager.TAG, "adapter sound msg : " + msg.getDataPath());
        holder.layout_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSoundClickListener != null)
                    mSoundClickListener.onSoundClickCallback(position, holder.iv_player);

            }
        });
    }

    private void showImageMsg(final TextImageSoundMsgHolder holder, final MessageInfo msg) {
        holder.layout_text.setVisibility(View.GONE);
        holder.iv_sanjiao.setVisibility(View.GONE);
        holder.iv_hb_image.setVisibility(View.GONE);

        holder.iv_pic.setVisibility(View.VISIBLE);

        int ww = ScreenUtils.dip2px(mContext, 113);
        int hh = ScreenUtils.dip2px(mContext, 95);

        String mLargeImageUrl = "";
        String mOriginalImageUrl = "";
        if (msg.isSelf()) {
            mLargeImageUrl = msg.getDataPath();
            mOriginalImageUrl = msg.getDataPath();


            Glide.with(mContext).load(msg.getDataPath())
                    .transform(new ImageUtils
                            .GlideRoundTransform(mContext, 4))
                    .override(ww, hh)
                    .fitCenter()
                    .into(holder.iv_pic);

        } else {

            if (msg.isHistoryMsg()) {
                mLargeImageUrl = msg.getDataPath();
                mOriginalImageUrl = msg.getDataPath();
            } else {
                TIMMessage timMessage = msg.getTIMMessage();
                long count = timMessage.getElementCount();
                if (count > 0) {
                    TIMElem timElem = timMessage.getElement(0);
                    //图片元素
                    TIMImageElem imageElem = (TIMImageElem) timElem;
                    ArrayList<TIMImage> list = new ArrayList<>();
                    list.addAll(imageElem.getImageList()); // 获取图片规格，每幅图片有三种规格，分别是 Original(原图)
                    // 、Large (大图) Thumb(缩略图)。

                    if (list.size() > 2) {
                        //TIMImage timImage = list.get(1); //获取缩略图 0：原图 1：缩略图 2：大图
                        mLargeImageUrl = list.get(2).getUrl(); //2：大图
                        mOriginalImageUrl = list.get(1).getUrl(); //1：缩略图
                        // 可以通过timImage.getImage(path, new TIMCallBack());
                        // 把图片下载到本地
                    }
                }
            }

            Glide.with(mContext).load(mOriginalImageUrl)
                    .transform(new ImageUtils.GlideRoundTransform(mContext, 4))
                    .override(ww, hh)
                    .fitCenter()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFirstResource) {

                            L.d(LiveManager.TAG,
                                    " 显示图片 Exception e: " + e.toString() + " " +
                                            "model: " + model);

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model,
                                                       Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache,
                                                       boolean isFirstResource) {

                            L.d(LiveManager.TAG, " 显示图片 正常 " + " model: " + model);
                            return false;
                        }
                    })
                    .into(holder.iv_pic);

        }

        final String originalImageUrl = mOriginalImageUrl;
        final String largeImageUrl = mLargeImageUrl;

        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageClickListener != null) {
                    mImageClickListener.onImageClickCallback(originalImageUrl, largeImageUrl);
                }
            }
        });


    }

    private void showTextMsg(TextImageSoundMsgHolder holder, MessageInfo msg) {
        holder.layout_text.setVisibility(View.VISIBLE);
        holder.tv_content.setVisibility(View.VISIBLE);
        holder.iv_sanjiao.setVisibility(View.VISIBLE);

        holder.iv_hb_image.setVisibility(View.GONE);
        holder.iv_player.setVisibility(View.GONE);
        holder.tv_time.setVisibility(View.GONE);
        holder.iv_pic.setVisibility(View.GONE);

        holder.tv_content.setText(msg.getContent());

        L.d("ttt", " msg.isAskQuestion(): " + msg.isAskQuestion());
        if (msg.isAskQuestion()) {
            holder.tv_status.setVisibility(View.GONE);
            String name = msg.getName();
            int huaIndex = name.indexOf("花");
            int askIndex = name.indexOf("提问");

            if (huaIndex != -1 && askIndex != -1) {
                SpannableString spannableString = new SpannableString(name);
                ForegroundColorSpan span1 =
                        new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_FFF85959));
                spannableString.setSpan(span1, huaIndex, askIndex,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holder.tv_name.setText(spannableString);
            } else {
                holder.tv_name.setText(msg.getName());
            }
        } else if (msg.isSendHongbao()) {
            holder.iv_hb_image.setVisibility(View.VISIBLE);
            holder.layout_text.setVisibility(View.GONE);
            holder.iv_sanjiao.setVisibility(View.GONE);
        }

        L.d("ttt", " msg.getContent(): " + msg.getContent());
    }


    OnSoundClickListener mSoundClickListener;

    public void setOnSoundClickListener(OnSoundClickListener listsener) {
        mSoundClickListener = listsener;
    }

    OnImageClickListener mImageClickListener;

    public void setOnImageClickListener(OnImageClickListener listsener) {
        mImageClickListener = listsener;
    }

    @Override
    public int getItemCount() {
        return mMsgInfoList.size();
    }

    private int live_msg = 0;
    private int normal_text_msg = 1;
    private int my_send_msg = 2;
    private int reward_msg = 3;//打赏

    @Override
    public int getItemViewType(int position) {

        MessageInfo messageInfo = mMsgInfoList.get(position);
        if (messageInfo.isNormalMsg()) {
            if (messageInfo.isReward()) {
                return reward_msg;
            } else {
                return normal_text_msg;
            }

        } else if (messageInfo.isMySendMsg()) {
            return my_send_msg;
        } else {
            return live_msg;
        }
    }

    OnRecycleViewItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnRecycleViewItemClickListener listener) {
        mItemClickListener = listener;
    }

    //打赏
    static class RewardMsgHolder extends RecyclerView.ViewHolder {

        TextView tv_reward;

        public RewardMsgHolder(View itemView) {
            super(itemView);
            tv_reward = itemView.findViewById(R.id.tv_reward);
        }
    }

    //收到的文字图片音频消息
    static class TextImageSoundMsgHolder extends RecyclerView.ViewHolder {

        CircleImageView iv_head_image;
        TextView tv_name;
        TextView tv_status;
        TextView tv_content;
        ImageView iv_pic;
        LinearLayout layout_text;
        ImageView iv_player; //播放按钮
        ImageView iv_sanjiao; //
        TextView tv_time;
        ImageView iv_hb_image; //

        public TextImageSoundMsgHolder(View itemView,
                                       final OnRecycleViewItemClickListener listener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, getPosition());
                    }
                }
            });
            iv_head_image = itemView.findViewById(R.id.iv_head_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            iv_sanjiao = itemView.findViewById(R.id.iv_sanjiao);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            layout_text = itemView.findViewById(R.id.layout_text);
            iv_hb_image = itemView.findViewById(R.id.iv_hb_image);
            iv_player = itemView.findViewById(R.id.iv_player);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }


    static class NormalTextHolder extends RecyclerView.ViewHolder {

        TextView tv_limit; //禁言
        TextView tv_receive_hongbao; //领红包

        public NormalTextHolder(View itemView, final OnRecycleViewItemClickListener listener) {
            super(itemView);
            tv_limit = itemView.findViewById(R.id.tv_limit);
            tv_receive_hongbao = itemView.findViewById(R.id.tv_receive_hongbao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, getPosition());
                    }
                }
            });

        }
    }

}

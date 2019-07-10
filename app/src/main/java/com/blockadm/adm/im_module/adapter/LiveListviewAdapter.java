//package com.blockadm.adm.im_module.adapter;
//
//import android.app.Activity;
//import android.support.constraint.ConstraintLayout;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.ForegroundColorSpan;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.blockadm.adm.MainApp;
//import com.blockadm.adm.R;
//import com.blockadm.adm.service.PlayService;
//import com.blockadm.common.comstomview.CircleImageView;
//import com.blockadm.common.im.LiveManager;
//import com.blockadm.common.im.entity.MessageInfo;
//import com.blockadm.common.im.entity.MessageType;
//import com.blockadm.common.utils.ImageUtils;
//import com.blockadm.common.utils.L;
//import com.blockadm.common.utils.ScreenUtils;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
//import com.tencent.imsdk.TIMElem;
//import com.tencent.imsdk.TIMImage;
//import com.tencent.imsdk.TIMImageElem;
//import com.tencent.imsdk.TIMMessage;
//import com.tencent.imsdk.TIMSoundElem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by Kris on 2019/5/9
// *
// * @Describe TODO { 直播列表 }
// */
//public class LiveListviewAdapter extends BaseAdapter {
//
//    private List<MessageInfo> mMsgInfoList;
//    private Activity mActivity;
//
//    private int audio_min_width = 0;
//    private int audio_max_width = 0;
//
//    public LiveListviewAdapter(Activity context, List<MessageInfo> msgInfoList) {
//        mActivity = context;
//        mMsgInfoList = msgInfoList;
//
//        audio_min_width = ScreenUtils.dip2px(mActivity, 60);
//        audio_max_width = ScreenUtils.dip2px(mActivity, 250);
//    }
//
//    @Override
//    public int getCount() {
//        return mMsgInfoList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mMsgInfoList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        LiveListviewAdapter.ViewHolder holder = null;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_live_list, null);
//
//            holder.tv_name = convertView.findViewById(R.id.tv_name);
//            holder.iv_head_image = convertView.findViewById(R.id.iv_head_image);
//            holder.tv_status = convertView.findViewById(R.id.tv_status);
//            holder.iv_pic = convertView.findViewById(R.id.iv_pic);
//            holder.layout_text = convertView.findViewById(R.id.layout_text);
//            holder.iv_player = convertView.findViewById(R.id.iv_player);
//            holder.tv_time = convertView.findViewById(R.id.tv_time);
//            holder.tv_content = convertView.findViewById(R.id.tv_content);
//            holder.tv_notice = convertView.findViewById(R.id.tv_notice);
//
//
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        MessageInfo msg = mMsgInfoList.get(position);
//
//
//        holder.tv_status.setVisibility(View.VISIBLE);
//        holder.tv_status.setText(msg.isSpeaker() ? "主讲人" : "管理员");
//
//        if (msg.getMsgType() == MessageType.MSG_TYPE_TEXT) {
//            L.d(LiveManager.TAG, "adapter msg.getMsgType() == MessageType.MSG_TYPE_TEXT ");
//            showText(holder, msg);
//
//        } else if (msg.getMsgType() == MessageType.MSG_TYPE_IMAGE) {
//            L.d(LiveManager.TAG, "adapter msg.getMsgType() == MessageType.MSG_TYPE_IMAGE ");
//
//            showImage(holder, msg);
//        } else if (msg.getMsgType() == MessageType.MSG_TYPE_AUDIO) {
//
//            L.d(LiveManager.TAG, "adapter msg.getMsgType() == MessageType.MSG_TYPE_AUDIO ");
//            showAudio(holder, msg);
//        }
//
//        return convertView;
//    }
//
//
//    private void showText(ViewHolder holder, MessageInfo msg) {
//
//        holder.iv_head_image.setVisibility(View.VISIBLE);
//        Glide.with(mActivity).load(msg.getHeadImageUrl()).into(holder.iv_head_image);
//        L.d("ttt", " msg.isAskQuestion(): " + msg.isAskQuestion());
//        if (msg.isAskQuestion()) {
//            holder.tv_status.setVisibility(View.GONE);
//            String name = msg.getName();
//            int huaIndex = name.indexOf("花");
//            int xiangIndex = name.indexOf("向");
//            int askIndex = name.indexOf("提问");
//
//            if (huaIndex != -1 && xiangIndex != -1 && askIndex != -1) {
//                SpannableString spannableString = new SpannableString(name);
//                ForegroundColorSpan span1 =
//                        new ForegroundColorSpan(mActivity.getResources().getColor(R.color.color_0A0A0A));
//                spannableString.setSpan(span1, huaIndex, xiangIndex,
//                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                ForegroundColorSpan span2 =
//                        new ForegroundColorSpan(mActivity.getResources().getColor(R.color.color_0A0A0A));
//                spannableString.setSpan(span2, xiangIndex + 1, askIndex,
//                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                holder.tv_name.setText(spannableString);
//            }
//        } else {
//            holder.tv_name.setText(msg.getName());
//        }
//
//        L.d("ttt", " msg.getContent(): " + msg.getContent());
//        holder.tv_content.setText(msg.getContent());
//
//        holder.layout_text.setVisibility(View.VISIBLE);
//        ConstraintLayout.LayoutParams audioParams =
//                (ConstraintLayout.LayoutParams) holder.layout_text.getLayoutParams();
//        int width = ScreenUtils.dip2px(mActivity, 262);
//        audioParams.width = width;
//        audioParams.matchConstraintMaxWidth = width;
//
//        holder.iv_player.setVisibility(View.GONE);
//        holder.tv_time.setVisibility(View.GONE);
//        holder.iv_pic.setVisibility(View.GONE);
//        holder.tv_notice.setVisibility(View.GONE);
//    }
//
//    private void showImage(final ViewHolder holder, final MessageInfo msg) {
//        String mLargeImageUrl = "";
//        String mOriginalImageUrl = "";
//        if (msg.isSelf()) {
//            holder.iv_pic.setVisibility(View.VISIBLE);
//            holder.layout_text.setVisibility(View.GONE);
//            holder.tv_notice.setVisibility(View.GONE);
//
//            holder.iv_head_image.setVisibility(View.VISIBLE);
//            Glide.with(mActivity).load(msg.getHeadImageUrl()).into(holder.iv_head_image);
//            holder.tv_name.setText(msg.getName());
//
//            mLargeImageUrl = msg.getDataPath();
//            mOriginalImageUrl = msg.getDataPath();
//
//            Glide.with(mActivity).load(msg.getDataPath()).into(holder.iv_pic);
//
//        } else {
//
//            if (msg.isHistoryMsg()) {
//                mLargeImageUrl = msg.getDataPath();
//                mOriginalImageUrl = msg.getDataPath();
//            } else {
//                TIMMessage timMessage = msg.getTIMMessage();
//                long count = timMessage.getElementCount();
//                if (count > 0) {
//                    TIMElem timElem = timMessage.getElement(0);
//                    //图片元素
//                    TIMImageElem imageElem = (TIMImageElem) timElem;
//                    ArrayList<TIMImage> list = new ArrayList<>();
//                    list.addAll(imageElem.getImageList()); // 获取图片规格，每幅图片有三种规格，分别是 Original(原图)
//                    // 、Large (大图) Thumb(缩略图)。
//
//                    if (list.size() > 2) {
//                        //TIMImage timImage = list.get(1); //获取缩略图 0：原图 1：缩略图 2：大图
//                        mLargeImageUrl = list.get(2).getUrl(); //2：大图
//                        mOriginalImageUrl = list.get(1).getUrl(); //1：缩略图
//                        // 可以通过timImage.getImage(path, new TIMCallBack());
//                        // 把图片下载到本地
//                    }
//                }
//
//            }
//
//            Glide.with(mActivity).load(mOriginalImageUrl)
//                    .transform(new ImageUtils.GlideRoundTransform(mActivity, 4))
//                    .listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model,
//                                                   Target<GlideDrawable> target,
//                                                   boolean isFirstResource) {
//
//                            L.d(LiveManager.TAG,
//                                    " 显示图片 Exception e: " + e.toString() + " " +
//                                            "model: " + model);
//
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model,
//                                                       Target<GlideDrawable> target,
//                                                       boolean isFromMemoryCache,
//                                                       boolean isFirstResource) {
//
//                            L.d(LiveManager.TAG, " 显示图片 正常 " + " model: " + model);
//                            holder.iv_pic.setVisibility(View.VISIBLE);
//                            holder.layout_text.setVisibility(View.GONE);
//                            holder.tv_notice.setVisibility(View.GONE);
//
//                            holder.iv_head_image.setVisibility(View.VISIBLE);
//                            Glide.with(mActivity).load(msg.getHeadImageUrl()).into(holder.iv_head_image);
//                            holder.tv_name.setText(msg.getName());
//
//                            return false;
//                        }
//                    })
//                    .into(holder.iv_pic);
//
//
//        }
//
//        final String originalImageUrl = mOriginalImageUrl;
//        final String largeImageUrl = mLargeImageUrl;
//
//        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //                if (mImageClickListener != null) {
//                //                    mImageClickListener.onImageClickCallback(originalImageUrl,
//                //                    largeImageUrl);
//                //                }
//            }
//        });
//
//
//    }
//
//    private void showAudio(final ViewHolder holder, final MessageInfo msg) {
//        holder.layout_text.setVisibility(View.VISIBLE);
//        holder.iv_pic.setVisibility(View.GONE);
//        holder.tv_notice.setVisibility(View.GONE);
//        holder.iv_player.setVisibility(View.VISIBLE);
//        holder.tv_time.setVisibility(View.VISIBLE);
//
//        holder.iv_head_image.setVisibility(View.VISIBLE);
//        Glide.with(mActivity).load(msg.getHeadImageUrl()).into(holder.iv_head_image);
//        holder.tv_name.setText(msg.getName());
//        int duration;
//        if (msg.isHistoryMsg()) {
//            duration = msg.getPlayTime();
//        } else {
//            final TIMSoundElem soundElem = (TIMSoundElem) msg.getTIMMessage().getElement(0);
//            duration = (int) soundElem.getDuration();
//        }
//
//        if (duration == 0)
//            duration = 1;
//
//        ConstraintLayout.LayoutParams audioParams =
//                (ConstraintLayout.LayoutParams) holder.layout_text.getLayoutParams();
//        audioParams.width = audio_min_width + ScreenUtils.dip2px(mActivity, duration * 10);
//        if (audioParams.width > audio_max_width)
//            audioParams.width = audio_max_width;
//        holder.tv_time.setText(duration + "''");
//        holder.tv_time.setVisibility(View.VISIBLE);
//        holder.tv_content.setText("");
//
//
//        L.d(LiveManager.TAG, "adapter sound msg : " + msg.getDataPath());
//        holder.layout_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.iv_player.setBackground(mActivity.getResources().getDrawable(R.mipmap.ico_play_stop));
//                MainApp.getPlayService1().play(msg.getDataPath(),
//                        new PlayService.PlayCompletionCallback() {
//                            @Override
//                            public void onCompletion() {
//                                holder.iv_player.setBackground(mActivity.getResources().getDrawable(R.mipmap.ico_play_go));
//                            }
//                        });
//            }
//        });
//    }
//
//
//    static class ViewHolder {
//
//        CircleImageView iv_head_image; // 头像
//        TextView tv_name; //名字
//        TextView tv_status; //主讲人或者管理员
//        ImageView iv_pic; //发送和接收的图片
//        LinearLayout layout_text;
//        ImageView iv_player; //播放按钮
//        TextView tv_time;
//        TextView tv_content;
//        TextView tv_notice;
//
//    }
//}

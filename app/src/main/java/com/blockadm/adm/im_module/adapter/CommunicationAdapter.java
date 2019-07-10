package com.blockadm.adm.im_module.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.im.entity.MessageType;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.widget.RoundRectLayout;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Kris on 2019/5/9
 *
 * @Describe TODO { 交流列表 }
 */
public class CommunicationAdapter extends RecyclerView.Adapter{

    private List<MessageInfo> mMsgInfoList;
    private Context mContext;
    private boolean isSpeaker;
    private ClickCallback mCallback;


    public CommunicationAdapter(Context context, List<MessageInfo> msgInfoList,boolean speaker, ClickCallback clickDianView) {
        mContext = context;
        isSpeaker = speaker;
        mMsgInfoList = msgInfoList;
        mCallback = clickDianView;
    }

    public void setSpeaker(boolean speaker) {
        isSpeaker = speaker;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        L.d(LiveManager.TAG,"test_content name viewType: "+viewType);
        View view;
        if (viewType == live_msg) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_communication_list,
                    parent, false);
            return new LiveHolder(view, mItemClickListener);
        }
        else if (viewType == my_send_msg) { //当下自己发送的消息
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_send_community_msg_list,
                    parent, false);
            return new MySendHolder(view, mItemClickListener);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_community_normal_list,
                    parent, false);
            return new NormalTextHolder(view);
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final MessageInfo msg = mMsgInfoList.get(position);

        L.d(LiveManager.TAG,"test_content name msg.isAskQuestion(): "+msg.isAskQuestion()+" ; msg.getName(): "+msg.getName());

        if (viewHolder instanceof LiveHolder) {
            final LiveHolder holder = (LiveHolder) viewHolder;
            if (msg.getMsgType() == MessageType.MSG_TYPE_TEXT) {

                holder.tv_content.setText(msg.getContent());

                if (msg.isAskQuestion()) {
                    String name = msg.getName();
                    L.d("test_name: "+name);
                    int huaIndex = name.indexOf("花");
                    int askIndex = name.indexOf("提问");

                    if (huaIndex != -1  && askIndex != -1) {
                        SpannableString spannableString = new SpannableString(name);
                        ForegroundColorSpan span1 =
                                new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_FFF85959));
                        spannableString.setSpan(span1, huaIndex, askIndex,
                                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        holder.tv_name.setText(spannableString);
                    }else {
                        holder.tv_name.setText(msg.getName());
                    }
                } else {
                    holder.tv_name.setText(msg.getName());
                }

                if (isSpeaker) {
                    holder.layout_dian.setVisibility(View.VISIBLE);
                }else {
                    holder.layout_dian.setVisibility(View.GONE);
                }


                if (StringUtils.isNotEmpty(msg.getHeadImageUrl())) {
                    Glide.with(mContext).load(msg.getHeadImageUrl()).into(holder.iv_head_image);
                }else {
                    Glide.with(mContext).load(R.mipmap.picture_default).into(holder.iv_head_image);
                }

                holder.layout_dian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCallback != null)
                        mCallback.clickDianView(holder.layout_limit, holder.tv_limit_text,position);
                    }
                });

                holder.layout_limit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCallback != null)
                            mCallback.clickLimitLayout(position);
                    }
                });
                holder.iv_head_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCallback != null)
                            mCallback.clickHeadImage(msg);
                    }
                });
            }
        }
        else if (viewHolder instanceof NormalTextHolder) {
            NormalTextHolder holder = (NormalTextHolder) viewHolder;
            String content = StringUtils.isEmpty(msg.getContent()) ? "" : msg.getContent();
            holder.tv_limit.setText(content);
        }
        else if (viewHolder instanceof MySendHolder) {
            MySendHolder holder = (MySendHolder) viewHolder;

            holder.tv_content.setText(msg.getContent());

            if (msg.isAskQuestion()) {
                String name = msg.getName();
                int huaIndex = name.indexOf("花");
                int askIndex = name.indexOf("提问");

                if (huaIndex != -1  && askIndex != -1) {
                    SpannableString spannableString = new SpannableString(name);
                    ForegroundColorSpan span1 =
                            new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_FFF85959));
                    spannableString.setSpan(span1, huaIndex, askIndex,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holder.tv_name.setText(spannableString);
                }else {
                    holder.tv_name.setText(msg.getName());
                }
            } else {
                holder.tv_name.setText(msg.getName());
            }
            if (StringUtils.isNotEmpty(msg.getHeadImageUrl())) {
                Glide.with(mContext).load(msg.getHeadImageUrl()).error(R.mipmap.picture_default).into(holder.iv_head_image);
            }else {
                Glide.with(mContext).load(R.mipmap.picture_default).into(holder.iv_head_image);
            }

            holder.iv_head_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null)
                        mCallback.clickHeadImage(msg);
                }
            });

        }

    }


    public interface ClickCallback {
        void clickDianView(RoundRectLayout layoutLimit,TextView textView,int position);

        void clickLimitLayout(int position);

        void clickHeadImage(MessageInfo msg);
    }

    OnRecycleViewItemClickListener mItemClickListener;
    public void setOnRecycleViewItemClickListener(OnRecycleViewItemClickListener listsener) {
        mItemClickListener = listsener;
    }

    @Override
    public int getItemCount() {
        return mMsgInfoList.size();
    }

    private int live_msg = 0;
    private int normal_text_msg = 1;
    private int my_send_msg = 2;

    @Override
    public int getItemViewType(int position) {
        if (mMsgInfoList.get(position).isNormalMsg()) {
            return normal_text_msg;
        }
        else if (mMsgInfoList.get(position).isMySendMsg()) {
            return my_send_msg;
        }
        else {
            return live_msg;
        }
    }

    static class NormalTextHolder extends RecyclerView.ViewHolder {

        TextView tv_limit; //禁言

        public NormalTextHolder(View itemView) {
            super(itemView);
            tv_limit = itemView.findViewById(R.id.tv_limit);
        }
    }

    static class MySendHolder extends RecyclerView.ViewHolder {

        CircleImageView iv_head_image;
        TextView tv_name;
        TextView tv_content;


        public MySendHolder(View itemView, final OnRecycleViewItemClickListener listener) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            iv_head_image = itemView.findViewById(R.id.iv_head_image);
            tv_content = itemView.findViewById(R.id.tv_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(v, getPosition());
                }
            });
        }
    }

    static class LiveHolder extends RecyclerView.ViewHolder {

        CircleImageView iv_head_image;
        TextView tv_name;
        LinearLayout layout_text;
        TextView tv_content;
        TextView tv_notice;
        RelativeLayout layout_dian;
        RoundRectLayout layout_limit;
        TextView tv_limit_text;
        ConstraintLayout root;


        public LiveHolder(View itemView, final OnRecycleViewItemClickListener listener) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            iv_head_image = itemView.findViewById(R.id.iv_head_image);
            layout_text = itemView.findViewById(R.id.layout_text);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_notice = itemView.findViewById(R.id.tv_notice);
            tv_limit_text = itemView.findViewById(R.id.tv_limit_text);
            layout_dian = itemView.findViewById(R.id.layout_dian);
            layout_limit = itemView.findViewById(R.id.layout_limit);
            root = itemView.findViewById(R.id.root);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(v, getPosition());
                }
            });
        }
    }
}

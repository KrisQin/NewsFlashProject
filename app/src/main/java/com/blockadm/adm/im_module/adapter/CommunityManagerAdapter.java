package com.blockadm.adm.im_module.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.call.CommunityManagerCallback;
import com.blockadm.common.bean.live.responseBean.LiveRecordsInfo;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Kris on 2019/5/16
 *
 * @Describe TODO {  }
 */
public class CommunityManagerAdapter extends RecyclerView.Adapter<CommunityManagerAdapter.MyHolder> {

    ArrayList<LiveRecordsInfo> mLiveRecordsList;
    Activity mActivity;
    CommunityManagerCallback mCallback;

    public CommunityManagerAdapter(ArrayList<LiveRecordsInfo> liveRecordsList, Activity activity,
                                   CommunityManagerCallback callback) {
        mLiveRecordsList = liveRecordsList;
        mActivity = activity;
        mCallback = callback;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_community_manager,
                parent, false);
        MyHolder holder = new MyHolder(view, mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final LiveRecordsInfo info = mLiveRecordsList.get(position);

        holder.tv_title.setText(info.getTitle());
        holder.tv_time.setText("直播时间:" + info.getLessonsTime());


        //状态(0 上架、1：下架、2：删除、3：开始直播，4：结束直播)
        if (info.getStatus() == 1) {
            holder.tv_down.setVisibility(View.VISIBLE);
            holder.layout_dian.setVisibility(View.GONE);
        } else {
            holder.tv_down.setVisibility(View.GONE);
            holder.layout_dian.setVisibility(View.VISIBLE);
        }


        // 0 直播中、1：预备中（未开始）、2：已结束
        if (info.getLiveStatus() == 0) {
            holder.tv_live_status.setText("直播中");
        } else if (info.getLiveStatus() == 1) {
            holder.tv_live_status.setText("预备中");
        } else if (info.getLiveStatus() == 2) {
            holder.tv_live_status.setText("已结束");
        }

        //accessStatus  付费状态   0 免费、1：付费   、2 ：密码
        holder.tv_lesson_status.setTextColor(mActivity.getResources().getColor(R.color.color_009F2C));
        if (info.getAccessStatus() == 0) {
            holder.tv_lesson_status.setText("免费");
        } else if (info.getAccessStatus() == 1) {
            holder.tv_lesson_status.setText(StringUtils.getUnitAmount()+info.getMoney()+"");
            holder.tv_lesson_status.setTextColor(mActivity.getResources().getColor(R.color.color_F85959));
        } else if (info.getAccessStatus() == 2) {
            holder.tv_lesson_status.setText("密码");
        }

        Glide.with(mActivity).load(info.getCoverImgs()).transform(new ImageUtils.GlideRoundTransform(mActivity,3)).error(R.drawable.picture_default).into(holder.iv_image);

        holder.layout_dian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.clickDianView(v,position);
            }
        });


        holder.tv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.clickDown(position);
            }
        });
    }


    OnRecycleViewItemClickListener mItemClickListener;

    public void setOnClickItemListener(OnRecycleViewItemClickListener listener) {
        mItemClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return mLiveRecordsList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_live_status;
        TextView tv_title;
        TextView tv_lesson_status;
        TextView tv_down;
        TextView tv_time;
        RelativeLayout layout_dian;

        public MyHolder(View itemView, final OnRecycleViewItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(v, getPosition());
                }
            });

            iv_image = itemView.findViewById(R.id.iv_image);
            tv_live_status = itemView.findViewById(R.id.tv_live_status);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_lesson_status = itemView.findViewById(R.id.tv_lesson_status);
            tv_down = itemView.findViewById(R.id.tv_down);
            tv_time = itemView.findViewById(R.id.tv_time);
            layout_dian = itemView.findViewById(R.id.layout_dian);

        }
    }
}

package com.blockadm.adm.im_module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.bean.live.responseBean.LiveRecordsInfo;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LiveChildAdapter extends RecyclerView.Adapter<LiveChildAdapter.MyViewHolder> {
    private ArrayList<LiveRecordsInfo> mInfoArrayList;
    private Context mContext;
    private OnRecycleViewItemClickListener mItemClickListener;

    public LiveChildAdapter(Context context, ArrayList<LiveRecordsInfo> list,OnRecycleViewItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
        mInfoArrayList = list;
        mContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_childs,null);
        MyViewHolder viewHolder =new MyViewHolder(view,mItemClickListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        LiveRecordsInfo info = mInfoArrayList.get(position);

        holder.tv_author.setText(info.getNickName());
        holder.tv_title.setText(info.getTitle());
        holder.tv_time.setText(info.getOpenLessonsTime());
        holder.tv_visitor.setText(info.getConvertVisitCount());

        //accessStatus  付费状态   0 免费、1：付费   、2 ：密码
        holder.tv_lesson_status.setTextColor(mContext.getResources().getColor(R.color.color_009F2C));
        if (info.getAccessStatus() == 0) {
            holder.tv_lesson_status.setText("免费");
        } else if (info.getAccessStatus() == 1) {
            holder.tv_lesson_status.setText(StringUtils.getUnitAmount()+info.getMoney());
            holder.tv_lesson_status.setTextColor(mContext.getResources().getColor(R.color.color_F85959));
        } else if (info.getAccessStatus() == 2) {
            holder.tv_lesson_status.setText("密码");
        }

        // 0 直播中、1：预备中（未开始）、2：已结束
        if (info.getLiveStatus() == 0) {
            holder.iv_live_status.setBackground(mContext.getResources().getDrawable(R.drawable.ic_liveing_image));
        }else if (info.getLiveStatus() == 1) {
            holder.iv_live_status.setBackground(mContext.getResources().getDrawable(R.drawable.ic_live_no_start));
        }else if (info.getLiveStatus() == 2) {
            holder.iv_live_status.setBackground(mContext.getResources().getDrawable(R.drawable.ic_live_over));
        }

        Glide.with(mContext).load(info.getCoverImgs())
                .transform(new ImageUtils.GlideRoundTransform(mContext, 3)).error(R.drawable.picture_default).into(holder.iv_image);


    }
    @Override
    public int getItemCount() {
        return mInfoArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_time;
        private TextView tv_author;
        private TextView tv_title;
        private TextView tv_visitor;
        private TextView tv_lesson_status;
        private ImageView iv_image;
        private ImageView iv_live_status;
        private OnRecycleViewItemClickListener listener;

        public MyViewHolder(View itemview, OnRecycleViewItemClickListener listener)
        {
            super(itemview);

            itemview.setOnClickListener(this);

            this.listener = listener;
            tv_time= (TextView) itemview.findViewById(R.id.tv_time);
            tv_author= (TextView) itemview.findViewById(R.id.tv_author);
            tv_title= (TextView) itemview.findViewById(R.id.tv_title);
            tv_visitor= (TextView) itemview.findViewById(R.id.tv_visitor);
            tv_lesson_status= (TextView) itemview.findViewById(R.id.tv_lesson_status);
            iv_image= (ImageView) itemview.findViewById(R.id.iv_image);
            iv_live_status= (ImageView) itemview.findViewById(R.id.iv_live_status);

        }

        @Override
        public void onClick(View v) {
            if (listener != null)
            listener.onItemClick(v,getPosition());
        }
    }
}
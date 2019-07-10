package com.blockadm.adm.im_module.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.live.responseBean.LiveRecordsInfo;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.utils.L;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Kris on 2019/5/14
 *
 * @Describe TODO { 个人主页：社群直播 }
 */
public class PersonLiveAdapter extends RecyclerView.Adapter<PersonLiveAdapter.MyViewHolder> {

    List<LiveRecordsInfo> mRecordsList;
    private Activity mActivity;

    public PersonLiveAdapter(List<LiveRecordsInfo> recordsList, Activity activity) {
        mRecordsList = recordsList;
        mActivity = activity;
    }

    @Override
    public PersonLiveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_lives,parent,false);
        MyViewHolder viewHolder =new MyViewHolder(view,mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            LiveRecordsInfo bean = mRecordsList.get(position);

            holder.mTv_name.setText(bean.getNickName());
            holder.mTv_status.setText(getStatusString(bean.getLiveStatus()));
            holder.mTv_time.setText(bean.getCreateTime());
            holder.mTv_title.setText(bean.getTitle());

            Glide.with(mActivity).load(bean.getCoverImgs()).into(holder.mIv_image);
        }catch (Exception e) {
            L.d(" Exception e: "+e.toString());
        }

    }

    private String getStatusString(int status) {
        //  0 直播中、1：预备中（未开始）、2：已结束
        switch (status) {
            case 0:
                return "直播中";
            case 1:
                return "预备中";
            case 2:
                return "已结束";
        }
        return "已结束";
    }

    private OnRecycleViewItemClickListener mItemClickListener;

    public void setOnRecyclerviewItemClickListener(OnRecycleViewItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTv_status;
        private TextView mTv_time;
        private TextView mTv_name;
        private TextView mTv_title;
        private ImageView mIv_image;

        public MyViewHolder(View itemView, final OnRecycleViewItemClickListener listener) {
            super(itemView);

            mTv_status = (TextView)itemView.findViewById(R.id.tv_live_status);
            mTv_time =  (TextView)itemView.findViewById(R.id.tv_time);
            mTv_name =  (TextView)itemView.findViewById(R.id.tv_Name);
            mTv_title =  (TextView)itemView.findViewById(R.id.tv_title);
            mIv_image =  (ImageView)itemView.findViewById(R.id.iv_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                    listener.onItemClick(v,getPosition());
                }
            });
        }
    }
}

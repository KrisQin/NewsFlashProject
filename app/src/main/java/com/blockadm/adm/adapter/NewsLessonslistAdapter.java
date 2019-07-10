package com.blockadm.adm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.RecordsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/22.
 */

public class NewsLessonslistAdapter extends RecyclerView.Adapter<NewsLessonslistAdapter.ViewHolder>  implements View.OnClickListener{
    private final Context context;
    private List<RecordsBean> records;

    private  int isSeeStatus;

    public NewsLessonslistAdapter(List<RecordsBean> records, Context context, int isSeeStatus) {
        this.records = records;
        this.context = context;
        this.isSeeStatus = isSeeStatus;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_lessons_list, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecordsBean recordsBean  = records.get(position);


        holder.tvTitle.setText(recordsBean.getTitle());


        if (isSeeStatus==1){
            if (recordsBean.getAccessStatus()==0){ //免费
                Drawable dra= context.getResources().getDrawable(R.mipmap.free);
                dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
                holder.tvStatus.setCompoundDrawables(dra,null,null,null);
            }else{ //上锁
                holder.tvStatus.setText("");
                Drawable dra= context.getResources().getDrawable(R.mipmap.lock);
                dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
                holder.tvStatus.setCompoundDrawables(dra,null,null,null);
            }
        }else if (isSeeStatus==0){ // 免费
            Drawable dra= context.getResources().getDrawable(R.mipmap.free);
            dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
            holder.tvStatus.setCompoundDrawables(dra,null,null,null);
        }else if (isSeeStatus==2){ //开锁
            Drawable dra= context.getResources().getDrawable(R.mipmap.ic_open_lock);
            dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
            holder.tvStatus.setCompoundDrawables(dra,null,null,null);
        }
        holder.tvTimeUpdate.setText(recordsBean.getCreateTime()+"更新");

        holder.itemView.setTag(position);

        if (recordsBean.isNowPlay() && (recordsBean.getContentType()==1 || recordsBean.getContentType()==3)){
            holder.tvTitle.setTextColor(Color.parseColor("#3164FE"));
            Drawable dra= context.getResources().getDrawable(R.mipmap.radio01_list_play);
            dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
            holder.tvStatus.setCompoundDrawables(dra,null,null,null);
        }

        if (recordsBean.getContentType()==3){
            holder.tvSoundType.setVisibility(View.VISIBLE);
            holder.tvImageText.setVisibility(View.VISIBLE);
        }else if (recordsBean.getContentType()==1){
            holder.tvSoundType.setVisibility(View.VISIBLE);
        }else if (recordsBean.getContentType()==2){
            holder.tvImageText.setVisibility(View.VISIBLE);
        }

    }
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener ;
    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        if (mOnRecyclerviewItemClickListener!=null){
            mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
        }

    }

    public void setmOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*  @BindView(R.id.tv_lessons_listnum)
          TextView tvLessonsListnum;*/
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_time_update)
        TextView tvTimeUpdate;

        @BindView(R.id.tv_sound_type)
        TextView tvSoundType;
        @BindView(R.id.tv_inagetext_type)
        TextView tvImageText;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}

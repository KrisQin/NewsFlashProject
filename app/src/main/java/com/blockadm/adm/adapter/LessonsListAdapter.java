package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.NewsLessonsDTO;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/21.
 */

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder> {

  private List<RecordsBean> newsLessonsDTOs;
  private    Context mContext;;
    public LessonsListAdapter(List<RecordsBean> newsLessonsDTOs, Context context) {
        this.newsLessonsDTOs =newsLessonsDTOs;
        this.mContext =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_list1, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        RecordsBean  bean  = newsLessonsDTOs.get(position);
        holder.tvTitle1.setText(bean.getTitle());
        holder.tvLooked.setText("浏览 "+bean.getReadCount());
        if (bean.getAccessStatus()== 0){
            holder.tvPrice.setText("免费");
        }else{
            holder.tvPrice.setText(StringUtils.getUnitAmount()+bean.getMoney());
        }
        holder.tvNickName.setText(bean.getNickName());

        Glide.with(mContext).load(bean.getCoverImgs()).transform(new ImageUtils.GlideRoundTransform(mContext, 3)).into(holder.ivImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRecyclerviewItemClickListener!=null){
                    mOnRecyclerviewItemClickListener.onItemClickListener(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsLessonsDTOs==null?0:newsLessonsDTOs.size();
    }

    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;

    public void setmOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title1)
        TextView tvTitle1;
        @BindView(R.id.tv_looked)
        TextView tvLooked;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_writer_nickname)
        TextView tvNickName;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

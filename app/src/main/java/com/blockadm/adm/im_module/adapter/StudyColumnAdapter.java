package com.blockadm.adm.im_module.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.StudyRecordsBean;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/21.
 */

public class StudyColumnAdapter extends RecyclerView.Adapter<StudyColumnAdapter.ViewHolder> {



    private List<StudyRecordsBean> newsLessonsSpecialColumnDtos;
    private Context mContext;
    ;

    public StudyColumnAdapter(List<StudyRecordsBean> newsLessonsSpecialColumnDtos,
                              Context context) {
        this.newsLessonsSpecialColumnDtos = newsLessonsSpecialColumnDtos;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_column_new, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            StudyRecordsBean bean = newsLessonsSpecialColumnDtos.get(position);
            if (bean == null) {
                holder.emptyItem.setVisibility(View.VISIBLE);
                holder.cardView.setVisibility(View.GONE);
            } else {
                holder.emptyItem.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.VISIBLE);

                holder.tvTitle1.setText(bean.getTitle());
                holder.tvLiulan.setText("" + bean.getConvertReadCount());

                if (bean.getAccessStatus() == 0) {
                    holder.viewLine.setVisibility(View.GONE);
                    holder.tvPrice.setText("免费");
                } else if (bean.getAccessStatus() == 1 && bean.getVipMoney() == 0) {
                    holder.viewLine.setVisibility(View.VISIBLE);
                    holder.tvPrice.setText(StringUtils.getUnitAmount()+bean.getMoney());
                    holder.tvMemberPrice.setText("会员免费");
                } else {
                    holder.viewLine.setVisibility(View.VISIBLE);
                    holder.tvPrice.setText(StringUtils.getUnitAmount()+bean.getMoney());
                    holder.tvMemberPrice.setText("会员价:" + bean.getVipMoney() + "");
                }

                holder.tvAuthor.setText(bean.getNickName());
                holder.tvSchedule.setText("更新至 " + bean.getFinishLessonsCount() + "节");

                Glide.with(mContext).load(bean.getCoverImgs()).transform(new ImageUtils.GlideRoundTransform(mContext, 3)).into(holder.ivImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnRecyclerviewItemClickListener != null) {
                            mOnRecyclerviewItemClickListener.onItemClickListener(v, position);
                        }
                    }
                });
            }

        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return newsLessonsSpecialColumnDtos == null ? 0 : newsLessonsSpecialColumnDtos.size();
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
        @BindView(R.id.tv_liulan)
        TextView tvLiulan;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_schedule)
        TextView tvSchedule;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.tv_member_price)
        TextView tvMemberPrice;
        @BindView(R.id.empty_item)
        RelativeLayout emptyItem;
        @BindView(R.id.cardView)
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

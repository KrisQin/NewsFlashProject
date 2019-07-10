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

public class ColumnlistAdapter extends RecyclerView.Adapter<ColumnlistAdapter.ViewHolder> {



    private List<StudyRecordsBean> newsLessonsSpecialColumnDtos;
    private Context mContext;
    ;

    public ColumnlistAdapter(List<StudyRecordsBean> newsLessonsSpecialColumnDtos, Context context) {
        this.newsLessonsSpecialColumnDtos = newsLessonsSpecialColumnDtos;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //D:\adm3\ADMshare\common\src\main\res\layout\item_columnist_new.xml
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_columnist_new, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            StudyRecordsBean bean =
                    newsLessonsSpecialColumnDtos.get(position);
            holder.tvTitle1.setText(bean.getTitle());
            holder.tvLiulan.setText("" + bean.getConvertReadCount());

            UserInfoDto userInfoDto = (UserInfoDto) ACache.get(mContext).getAsObject("userInfoDto");

            if (bean.getAccessStatus() == 0) {
                holder.viewLine.setVisibility(View.GONE);
                holder.tvPrice.setText("免费");
            }else if (bean.getAccessStatus() == 1 && bean.getVipMoney() == 0) {
                holder.viewLine.setVisibility(View.VISIBLE);
                holder.tvPrice.setText(StringUtils.getUnitAmount()+bean.getMoney());
                holder.tvMemberPrice.setText("会员免费");
            }else {
                holder.viewLine.setVisibility(View.VISIBLE);
                holder.tvPrice.setText(StringUtils.getUnitAmount()+bean.getMoney());
                holder.tvMemberPrice.setText("会员价:"+bean.getVipMoney()+"");
            }

//            if (bean.getAccessStatus() == 0) {
//                holder.tvPrice.setText("免费");
//                holder.tvPrice.setTextColor(Color.parseColor("#009F2C"));
//            } else if (bean.getAccessStatus() == 1 && bean.getSigningState() == 1 && userInfoDto != null && userInfoDto.getVipState() == 1) {
//                holder.tvPrice.setText("会员免费");
//                holder.tvPrice.setTextColor(Color.parseColor("#009F2C"));
//            } else if (bean.getAccessStatus() == 1 && bean.getSigningState() == 1 && userInfoDto != null && userInfoDto.getVipState() == 0) {
//                holder.tvPrice.setText("¥" + bean.getMoney());
//            } else if (bean.getAccessStatus() == 1 && bean.getSigningState() == 0 && userInfoDto != null && userInfoDto.getVipState() == 0) {
//                holder.tvPrice.setText("¥" + bean.getMoney());
//            } else if (bean.getAccessStatus() == 1 && bean.getSigningState() == 0 && userInfoDto != null && userInfoDto.getVipState() == 1) {
//                holder.tvPrice.setText("¥" + bean.getMoney());
//            } else {
//                holder.tvPrice.setText("¥" + bean.getMoney());
//            }

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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.blockadm.adm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ColumnOneDetailComActivity;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.AllListDto;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/21.
 */

public class StudyTopChildListAdapter extends RecyclerView.Adapter<StudyTopChildListAdapter.ViewHolder> {


    private List<AllListDto.PageListBean.RecordsBean> recordsBeans;
    private Context mContext;
    ;
    private AllListDto.PageListBean listBean;
    private int objectType;

    public StudyTopChildListAdapter(AllListDto.PageListBean listBean, int objectType1,
                                    Context context1) {
        this.recordsBeans = listBean.getRecords();
        this.listBean = listBean;
        this.mContext = context1;
        this.objectType = objectType1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_top_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AllListDto.PageListBean.RecordsBean bean = recordsBeans.get(position);
        holder.tvTitle1.setText(bean.getTitle());
        holder.tvLiulan.setText("" + bean.getReadCount());

        /*
（1）、免费 ：  memberId【创建者】 = memberId 【登录账户】 或者    accessStatus=0

（2）、会员免费 ： accessStatus=1 并且 vipMoney =0

（3）、付费情况 :   accessStatus=1 并且 vipMoney >0
        * */
        if (bean.getAccessStatus() == 0) {
            holder.viewLine.setVisibility(View.GONE);
            holder.tvPrice.setText("免费");
        } else if (bean.getAccessStatus() == 1 && bean.getVipMoney() == 0) {
            holder.viewLine.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(StringUtils.getUnitAmount()+bean.getMoney());
            holder.tvMemberPrice.setText("会员免费");
        } else {
            holder.viewLine.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(bean.getMoney() + "");
            holder.tvMemberPrice.setText("会员价:" + bean.getVipMoney() + "");
        }


        holder.tvAuthor.setText(bean.getNickName());
        if (objectType == 0) {
            holder.tvSchedule.setText("更新至 " + bean.getFinishLessonsCount() + "节");
            holder.tvSchedule.setVisibility(View.VISIBLE);
        } else {
            holder.tvSchedule.setVisibility(View.GONE);
        }


        Glide.with(mContext).load(bean.getCoverImgs())
                .transform(new ImageUtils.GlideRoundTransform(mContext, 3)).into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordsBeans != null && recordsBeans.size() > 0) {
                    AllListDto.PageListBean.RecordsBean bean = recordsBeans.get(position);
                    Intent intent = new Intent(mContext, ColumnOneDetailComActivity.class);
                    intent.putExtra("id", bean.getId());
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (recordsBeans.size() >= 3) {
            return 3;
        }
        if (recordsBeans.size() < 3) {
            return recordsBeans.size();
        }
        return 0;
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
        @BindView(R.id.tv_member_price)
        TextView tvMemberPrice;
        @BindView(R.id.view_line)
        View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

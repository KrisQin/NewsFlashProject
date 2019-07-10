package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.PageMyRecommendDetailWebDto;
import com.blockadm.common.utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/27.
 */

public class MoneyRewardAdapter extends RecyclerView.Adapter<MoneyRewardAdapter.ViewHolder> {

    private List<PageMyRecommendDetailWebDto.RecordsBean> records;
    private Context context ;
    public MoneyRewardAdapter(List<PageMyRecommendDetailWebDto.RecordsBean> records, Context context) {
        this.records =records;
        this.context  =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view =  LayoutInflater.from(context).inflate(R.layout.item_money_reward,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoneyRewardAdapter.ViewHolder holder, final int position) {
        PageMyRecommendDetailWebDto.RecordsBean  recordsBean =   records.get(position);
        ImageUtils.loadImageView(recordsBean.getAwardIcon(),holder.iv);
        holder.tvTitle.setText(recordsBean.getAwardRemark());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date =  sdf.parse(recordsBean.getCreateDate());
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            holder.tvTime.setText(sdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tvPrice.setTextColor(context.getResources().getColor(R.color.color_FFF85959));
        holder.tvPrice.setText("+"+recordsBean.getAwardPoint());
    }
    @Override
    public int getItemCount() {
        return records==null?0:records.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_point)
        TextView tvPoint;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

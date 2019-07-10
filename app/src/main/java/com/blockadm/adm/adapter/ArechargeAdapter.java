package com.blockadm.adm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.RechargeTypeDto;
import com.blockadm.common.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/13.
 */

public class ArechargeAdapter extends RecyclerView.Adapter<ArechargeAdapter.ViewHolder> {

    private Context context;
    private List<RechargeTypeDto.RechargeTypeListBean> rechargeTypeList;

    public ArechargeAdapter(Context context, List<RechargeTypeDto.RechargeTypeListBean> rechargeTypeList) {
        this.context = context;
        this.rechargeTypeList = rechargeTypeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recharge_list, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RechargeTypeDto.RechargeTypeListBean  rechargeTypeListBean =  rechargeTypeList.get(position);
        holder.tvMoney.setText(StringUtils.getUnitAmount()+rechargeTypeListBean.getMoney()+"");
        holder.tvPoint.setText(rechargeTypeListBean.getPoint()+"A点");
        if (rechargeTypeListBean.isCheck()){
            holder.tvMoney.setTextColor(Color.WHITE);
            holder.tvPoint.setTextColor(Color.WHITE);
            holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.recharge_bg));
        }else{
            holder.tvPoint.setTextColor(context.getResources().getColor(R.color.color_ff97979f));
            holder.tvMoney.setTextColor(context.getResources().getColor(R.color.color_0A0A0A));
            holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.recharge_bg2));
        }
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                RechargeTypeDto.RechargeTypeListBean  rechargeTypeListBean =  rechargeTypeList.get(position);
                for (int i = 0; i < rechargeTypeList.size(); i++) {
                    rechargeTypeList.get(i).setCheck(false);
                }
                if (rechargeTypeListBean.isCheck()){
                    rechargeTypeListBean.setCheck(false);
                }else{
                    rechargeTypeListBean.setCheck(true);
                }
               notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return rechargeTypeList == null ? 0 : rechargeTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_point)
        TextView tvPoint;
        @BindView(R.id.ll)
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

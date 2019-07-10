package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.VipDto;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.utils.ImageUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/21.
 */

public class MyVipAdapter extends RecyclerView.Adapter<MyVipAdapter.ViewHolder> {

    private ArrayList<VipDto> vipDtos;
    private int type;
    private Context context ;
    public MyVipAdapter(ArrayList<VipDto> vipDtos,int type) {
        this.vipDtos =vipDtos;
        this.type = type ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View  view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_vip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VipDto  vipDto  = vipDtos.get(position);
        if (type==1){
            ImageUtils.loadImageView(vipDto.getHasBuyUrl(),holder.civHeadimage);
            holder.tvItemTitle.setTextColor(context.getResources().getColor(R.color.color_0A0A0A));
            holder.tvItemDec.setText(vipDto.getHasBuyRemark());
        }else{
            ImageUtils.loadImageView(vipDto.getNoBuyUrl(),holder.civHeadimage);
            holder.tvItemTitle.setTextColor(context.getResources().getColor(R.color.color_FFB6B6BA));
            holder.tvItemDec.setText(vipDto.getNoBuyRemark());
        }
        holder.tvItemTitle.setText(vipDto.getTitle());



    }

    @Override
    public int getItemCount() {
        return vipDtos==null?0:vipDtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_headimage)
        CircleImageView civHeadimage;
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_dec)
        TextView tvItemDec;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

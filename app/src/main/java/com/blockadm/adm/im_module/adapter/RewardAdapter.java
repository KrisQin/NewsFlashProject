package com.blockadm.adm.im_module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.RewardInfo;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.utils.L;

import java.util.List;

/**
 * Created by Kris on 2019/5/15
 *
 * @Describe TODO {  }
 */
public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardHolder> {

    List<RewardInfo> mList;
    Context mContext;

    public RewardAdapter(List<RewardInfo> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public RewardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward, parent
                , false);
        RewardHolder holder = new RewardHolder(view, mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RewardHolder holder, int position) {

        RewardInfo info = mList.get(position);
        holder.tv_money.setText(info.getRewardMoney()+"");
        if (info.isSelect()) {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_blue_round_bg));
            holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_gray_round_bg));
            holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.color_0A0A0A));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnRecycleViewItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnRecycleViewItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    static class RewardHolder extends RecyclerView.ViewHolder {

        TextView tv_money;

        public RewardHolder(View itemView, final OnRecycleViewItemClickListener itemClickListener) {
            super(itemView);

            tv_money = (TextView) itemView.findViewById(R.id.tv_money);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemClickListener != null)
                        itemClickListener.onItemClick(v, getPosition());
                }
            });
        }
    }
}


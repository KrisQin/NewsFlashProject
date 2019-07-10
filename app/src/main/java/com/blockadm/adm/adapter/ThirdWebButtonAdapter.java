package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.RewardInfo;
import com.blockadm.common.bean.SysThirdWebListInfo;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.utils.GlideTools;
import com.blockadm.common.utils.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

/**
 * Created by Kris on 2019/6/27
 *
 * @Describe TODO {  }
 */
public class ThirdWebButtonAdapter extends RecyclerView.Adapter<ThirdWebButtonAdapter.ThirdWebHolder> {

    List<SysThirdWebListInfo> mList;
    Context mContext;

    public ThirdWebButtonAdapter(Context context,List<SysThirdWebListInfo> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public ThirdWebButtonAdapter.ThirdWebHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_third_web, parent
                , false);
        ThirdWebHolder holder = new ThirdWebHolder(view, mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ThirdWebButtonAdapter.ThirdWebHolder holder, int position) {

        SysThirdWebListInfo info = mList.get(position);
        if (info != null) {

            L.d("xxxs",info.getWebName()+" -- "+info.getWebLogo() +" -- "+info.getWebUrl());
            holder.tv_title.setText(info.getWebName()+"");
            Glide.with(mContext).load(info.getWebLogo()).error(R.mipmap.picture_default).into(holder.iv_head_image);
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

    static class ThirdWebHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        CircleImageView iv_head_image;

        public ThirdWebHolder(View itemView, final OnRecycleViewItemClickListener itemClickListener) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_head_image = (CircleImageView) itemView.findViewById(R.id.iv_head_image);

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

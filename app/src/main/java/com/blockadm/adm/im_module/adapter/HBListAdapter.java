package com.blockadm.adm.im_module.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.live.responseBean.LiveHBInfo;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.utils.ScreenUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Kris on 2019/6/14
 *
 * @Describe TODO {  }
 */
public class HBListAdapter extends BaseAdapter {
    
    private List<LiveHBInfo.ReceiveListBean> mList;
    private Activity mActivity;

    public HBListAdapter(List<LiveHBInfo.ReceiveListBean> list, Activity context) {
        mList = list;
        mActivity = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_hb_list, null);

            holder.iv_head_image = (CircleImageView) convertView.findViewById(R.id.iv_head_image);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LiveHBInfo.ReceiveListBean info = mList.get(position);

        Glide.with(mActivity).load(info.getIcon()).error(R.mipmap.picture_default).into(holder.iv_head_image);
        if (info.getRewardType() == 0) {
            holder.tv_amount.setText(info.getMoney()+"A钻");
        }else {
            holder.tv_amount.setText(info.getMoney()+"A点");
        }

        holder.tv_time.setText(info.getReceiveDate());
        holder.tv_name.setText(info.getNickName());
        
        return convertView;
    }
    
    
    
    static class ViewHolder {

        CircleImageView iv_head_image;
        TextView tv_amount;
        TextView tv_time;
        TextView tv_name;

    }
}

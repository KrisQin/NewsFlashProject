package com.blockadm.adm.im_module.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.live.responseBean.LiveHBInfo;
import com.blockadm.common.bean.live.responseBean.LiveRecordsInfo;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Kris on 2019/6/14
 *
 * @Describe TODO { 直播课程购买历史的adapter }
 */
public class CommunityBuyHistoryAdapter extends BaseAdapter {

    private List<LiveRecordsInfo> mList;
    private Activity mActivity;

    public CommunityBuyHistoryAdapter(List<LiveRecordsInfo> list, Activity context) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_community_buy_history, null);

            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_lessons_time = (TextView) convertView.findViewById(R.id.tv_lessons_time);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.tv_buy_time = (TextView) convertView.findViewById(R.id.tv_buy_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LiveRecordsInfo info = mList.get(position);

        Glide.with(mActivity).load(info.getCoverImgs()).error(R.mipmap.picture_default).into(holder.iv_image);

        holder.tv_lessons_time.setText(info.getOpenLessonsTime());
        holder.tv_title.setText(info.getTitle());
        holder.tv_amount.setText(StringUtils.getUnitAmount()+info.getMoney());
        holder.tv_buy_time.setText(info.getCreateTime()+"购买");

        return convertView;
    }
    
    
    
    static class ViewHolder {

        ImageView iv_image;
        TextView tv_lessons_time;
        TextView tv_title;
        TextView tv_amount;
        TextView tv_buy_time;

    }
}

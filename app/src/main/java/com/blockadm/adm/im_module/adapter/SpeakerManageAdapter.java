package com.blockadm.adm.im_module.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.bean.live.responseBean.LiveManagerInfo;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Kris on 2019/5/17
 *
 * @Describe TODO {  }
 */
public class SpeakerManageAdapter extends BaseAdapter {
    private List<LiveManagerInfo> mList;
    private Activity mActivity;

    public SpeakerManageAdapter(Activity activity, List<LiveManagerInfo> localUserList) {
        mList = localUserList;
        mActivity = activity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_manage_list, null);

            int w = ScreenUtils.getScreenWidth(mActivity);
            int h = ScreenUtils.dip2px(mActivity, 50);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w, h);
            convertView.setLayoutParams(params);

            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_manage = (TextView) convertView.findViewById(R.id.tv_manage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (ListUtils.isNotEmpty(mList)) {

            final String name = mList.get(position).getManagerNickName();

            holder.tv_name.setText(name);

            if (mList.get(position).isSelect()) {
                holder.tv_name.setTextColor(mActivity.getResources().getColor(R.color.blue));
                holder.tv_manage.setTextColor(mActivity.getResources().getColor(R.color.blue));
            }else {
                holder.tv_name.setTextColor(mActivity.getResources().getColor(R.color.color_0A0A0A));
                holder.tv_manage.setTextColor(mActivity.getResources().getColor(R.color.color_0A0A0A));
            }

            if (mList.get(position).isSpeaker()) {
                holder.tv_manage.setText("主讲人");
            } else {
                holder.tv_manage.setText("管理员");
            }

        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tv_name;
        TextView tv_manage;
    }

}

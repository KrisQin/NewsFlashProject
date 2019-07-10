package com.blockadm.common.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Kris on 2019/5/17
 *
 * @Describe TODO {  }
 */
public class PopupListAdapter extends BaseAdapter {
    private List<String> mList;
    private Activity mActivity;

    public PopupListAdapter(Activity activity,List<String> localUserList) {
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.pop_list_item,null);

            int w = ScreenUtils.dip2px(mActivity,74);
            int h = ScreenUtils.dip2px(mActivity,32);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w,h);
            convertView.setLayoutParams(params);

            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (ListUtils.isNotEmpty(mList)) {

            final String name = mList.get(position);

            holder.tv_name.setText(name);

        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tv_name;
    }

}

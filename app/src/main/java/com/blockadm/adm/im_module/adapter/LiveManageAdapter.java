package com.blockadm.adm.im_module.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.live.responseBean.LiveManageInfo;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.utils.GlideTools;
import com.blockadm.common.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/6/3
 *
 * @Describe TODO {  }
 */
public class LiveManageAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<LiveManageInfo> mInfoList;
    private ClickDianCallback callback;

    public LiveManageAdapter(Activity activity, List<LiveManageInfo> infoList,ClickDianCallback callback) {
        mActivity = activity;
        mInfoList = infoList;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return mInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfoList.get(position);
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_live_manage,null);

//            int w = ScreenUtils.getScreenWidth(mActivity);
//            int h = ScreenUtils.dip2px(mActivity,50);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w,h);
//            convertView.setLayoutParams(params);

            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_head_image = convertView.findViewById(R.id.iv_head_image);
            holder.layout_dian = convertView.findViewById(R.id.layout_dian);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        LiveManageInfo info = mInfoList.get(position);

        GlideTools.setImgByGlide(mActivity, info.getIcon(),
                R.drawable.picture_default, holder.iv_head_image);

        holder.tv_name.setText(info.getNickName());
        holder.layout_dian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.click(v,position);
            }
        });

        return convertView;
    }

    public interface ClickDianCallback {
        void click(View v,int position);
    }

    static class ViewHolder {
        CircleImageView iv_head_image;
        TextView tv_name;
        RelativeLayout layout_dian;
    }
}

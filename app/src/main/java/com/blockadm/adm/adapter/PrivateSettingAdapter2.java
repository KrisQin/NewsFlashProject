package com.blockadm.adm.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.PrivateListBean;

import java.util.List;

/**
 * Created by hp on 2019/1/31.
 */

public class PrivateSettingAdapter2 extends BaseAdapter {

    private List<PrivateListBean.ListBean> list;
    private Context  context;

    public PrivateSettingAdapter2(List<PrivateListBean.ListBean> list, Context  context) {
        this.context =context;
        this.list =list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public PrivateListBean.ListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PrivateListBean.ListBean  listBean =  getItem(position);

        View   view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_private_setting2,null);

        TextView  textView = view.findViewById(R.id.tv_name);

        textView.setText(listBean.getName());
        if (listBean.getIsSelect()==1){
            Drawable wetChat1 = context.getResources().getDrawable(R.mipmap.select_press);
            wetChat1.setBounds(0,0,wetChat1.getMinimumWidth(),wetChat1.getMinimumHeight());
            textView.setCompoundDrawables(null,null,wetChat1,null);
        }else{
            textView.setCompoundDrawables(null,null,null,null);
        }
        return view;
    }
}

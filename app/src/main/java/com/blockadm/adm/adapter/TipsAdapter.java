package com.blockadm.adm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blockadm.adm.R;

import java.util.List;


/**
 * Created by hp on 2019/2/14.
 */

public class TipsAdapter extends BaseAdapter {
    private Context context;
    private  List<String> explainList;
    public TipsAdapter(Context context, List<String> explainList) {
        this.context =context;
        this.explainList = explainList;

    }

    @Override
    public int getCount() {
        return explainList==null?0:explainList.size();
    }

    @Override
    public String getItem(int position) {
        return explainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item_tips,null,false);
        TextView textView =  view.findViewById(R.id.tv_tip);
        textView.setText(explainList.get(position));
        return view;
    }
}

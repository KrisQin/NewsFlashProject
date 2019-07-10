package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blockadm.adm.R;

/**
 * Created by hp on 2019/1/26.
 */

public class InfomationListSearchAdapter extends RecyclerView.Adapter<InfomationListSearchAdapter.ViewHolder>{

    private Context context;
    @Override
    public InfomationListSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view =  LayoutInflater.from(context).inflate(R.layout.item_information_search_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfomationListSearchAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

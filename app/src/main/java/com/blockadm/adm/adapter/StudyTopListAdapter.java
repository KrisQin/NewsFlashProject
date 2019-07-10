package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.AllListDto;
import com.blockadm.common.utils.ListUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/3/29.
 */

public class StudyTopListAdapter extends RecyclerView.Adapter<StudyTopListAdapter.ViewHolder> {


    private ArrayList<AllListDto> mAllListDtos = new ArrayList<>();
    private Context mContext;
    public StudyTopListAdapter(ArrayList<AllListDto> allListDtos, Context context) {
        if (this.mAllListDtos == null) {
            this.mAllListDtos = new ArrayList<>();
        }else {
            this.mAllListDtos.clear();
        }

        if (allListDtos != null) {
            this.mAllListDtos.addAll(allListDtos);
        }

        this.mContext = context;
    }

    @Override
    public StudyTopListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudyTopListAdapter.ViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_study_top, parent, false));
    }

    @Override
    public void onBindViewHolder(StudyTopListAdapter.ViewHolder holder, final int position) {
        AllListDto   bean  = mAllListDtos.get(position);
        holder.tvTypeName.setText(bean.getNavigationTypeName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        StudyTopChildListAdapter studyTopChildListAdapter = new StudyTopChildListAdapter(bean.getPageList(),bean.getObjectType(),mContext);
        holder.frv.setLayoutManager(linearLayoutManager);
        holder.frv.setAdapter(studyTopChildListAdapter);

    }

    @Override
    public int getItemCount() {
        return mAllListDtos ==null?0:mAllListDtos.size();
    }

    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;

    public void setmOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.frv)
        RecyclerView frv;
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

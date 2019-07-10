package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.CollectListDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/13.
 */

public class CollectListAdapter extends RecyclerView.Adapter<CollectListAdapter.ViewHolder> implements View.OnClickListener {

    private List<CollectListDto.RecordsBean> records;
    private  Context context;

    public CollectListAdapter(List<CollectListDto.RecordsBean> records, Context context) {
        this.context = context;
        this.records =records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_list, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CollectListDto.RecordsBean  recordsBean =  records.get(position);
        holder.tvTitle.setText(recordsBean.getTitle());

        switch (recordsBean.getTypeId()){
            case 1:
                holder.tvType.setText("精品课程");
            break;
            case 2:
                holder.tvType.setText("独家专栏");
                break;
            case 3:
                holder.tvType.setText("行业资讯");
                break;
            case 4:
                holder.tvType.setText("活动");
                break;
        }

        holder.itemView.setTag(position);


    }

    @Override
    public int getItemCount() {
        return records.size();
    }
   private  OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;
    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口

        if (mOnRecyclerviewItemClickListener!=null){
            mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
        }

    }

    public void setmOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_type)
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.blockadm.adm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.utils.FormatCurrentData;
import com.blockadm.common.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/10.
 */

public class InformationSearchAdapter extends RecyclerView.Adapter<InformationSearchAdapter.ViewHolder> implements View.OnClickListener {


    private List<NewsArticleListDTO.RecordsBean> records;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener ;

    public InformationSearchAdapter(List<NewsArticleListDTO.RecordsBean> records) {
        this.records = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_information_search, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvContent.setText(records.get(position).getSummary());
        holder.tvTitle.setText(records.get(position).getTitle());
        ImageUtils.loadImageView(records.get(position).getThumbnail(),holder.iv);
        holder.tvRes.setText(records.get(position).getResource());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return records==null?0:records.size();
    }

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

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_res)
        TextView tvRes;
        @BindView(R.id.iv)
        ImageView iv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

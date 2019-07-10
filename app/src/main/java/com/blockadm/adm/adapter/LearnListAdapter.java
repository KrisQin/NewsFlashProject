package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.LessonsAndNlscDTO;
import com.blockadm.common.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/26.
 */

public class LearnListAdapter extends RecyclerView.Adapter<LearnListAdapter.ViewHolder> implements View.OnClickListener {


    private Context context;
    private List<LessonsAndNlscDTO.RecordsBean> recordsBeans;

    public LearnListAdapter(Context context, List<LessonsAndNlscDTO.RecordsBean> recordsBeans) {
        this.context = context;
        this.recordsBeans = recordsBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_learn_list, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LessonsAndNlscDTO.RecordsBean  recordsBean =   recordsBeans.get(position);
        holder.tvTitle.setText(recordsBean.getTitle());

        if(recordsBean.getLessonsCount()==0){
            holder.tvNumberOfOourse.setVisibility(View.GONE);
        }else{
            holder.tvNumberOfOourse.setVisibility(View.VISIBLE);
            holder.tvNumberOfOourse.setText("课程数"+recordsBean.getLessonsCount());
        }

        holder.tvPageview.setText("浏览 "+recordsBean.getReadCount());
        if (recordsBean.getAccessStatus()==0){
            holder.tvPrice.setText("免费");
        }else if (recordsBean.getAccessStatus()==1){
            holder.tvPrice.setText(StringUtils.getUnitAmount()+recordsBean.getMoney());
        }
        holder.tvAuthor.setText(recordsBean.getNickName());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return recordsBeans == null ? 0 : recordsBeans.size();
    }
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener ;
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
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_number_of_oourse)
        TextView tvNumberOfOourse;
        @BindView(R.id.tv_pageview)
        TextView tvPageview;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

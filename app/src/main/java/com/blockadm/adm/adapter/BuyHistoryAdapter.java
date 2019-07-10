package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.BuyHistoryDto;
import com.blockadm.common.comstomview.RoundImageView;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/3/15.
 */

public class BuyHistoryAdapter extends RecyclerView.Adapter<BuyHistoryAdapter.ViewHolder> implements View.OnClickListener {

    private Context context ;
    private List<BuyHistoryDto.RecordsBean> recordsBeans;
    private int type;
    public BuyHistoryAdapter(List<BuyHistoryDto.RecordsBean> records, Context  context,int type) {
        this.context = context;
        this.recordsBeans = records;
        this.type = type;
    }

    @Override
    public BuyHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_buy_history_list, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BuyHistoryDto.RecordsBean  recordsBean = recordsBeans.get(position);
        holder.tvPrice.setText(StringUtils.getUnitAmount()+recordsBean.getMoney());
        holder.tvTitle.setText(recordsBean.getTitle());
        ImageUtils.loadImageView(recordsBean.getCoverImgs(),holder.iv);
        if (type == 1){
            holder.tvSchedule.setVisibility(View.GONE);
        }else{
            holder.tvSchedule.setText("更新至"+recordsBean.getFinishLessonsCount()+"节课");
        }

        holder.itemView.setTag(position);
    }



    @Override
    public int getItemCount() {
        return recordsBeans==null?0:recordsBeans.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv)
        RoundImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_schedule)
        TextView tvSchedule;
        @BindView(R.id.tv_buy_num)
        TextView tvBuyNum;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;


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
}

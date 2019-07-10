package com.blockadm.adm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.AcountDetailComActivity;
import com.blockadm.common.bean.AcountListDtoRecordBean;
import com.blockadm.common.utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/14.
 */

public class AcountListAdapter extends RecyclerView.Adapter<AcountListAdapter.ViewHolder> {
    private List<AcountListDtoRecordBean> records;
    private Context  context;

    public AcountListAdapter(List<AcountListDtoRecordBean> records) {
        this.records =records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context =parent.getContext();
        View view  =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acount_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AcountListDtoRecordBean recordsBean =   records.get(position);
        ImageUtils.loadImageView(recordsBean.getIcon(),holder.iv);
        holder.tvTitle.setText(recordsBean.getRemark());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date  date =  sdf.parse(recordsBean.getCreateDate());
            sdf = new SimpleDateFormat("MM-dd HH:mm");
            holder.tvTime.setText(sdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         *  io 0 收入 1 支出   payType  0 A点流水  1 现金流水  2 A钻流水
         * */
        switch (recordsBean.getIo()){
            case 0:
                holder.tvPrice.setTextColor(context.getResources().getColor(R.color.color_FFF85959));
                holder.tvPrice.setText("+"+showMoney(recordsBean));
                break;
            case 1:
                holder.tvPrice.setTextColor(context.getResources().getColor(R.color.color_0A0A0A));
                holder.tvPrice.setText("-"+showMoney(recordsBean));
                break;
        }
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcountListDtoRecordBean recordsBean  = records.get(position);
                Intent intent = new Intent(context, AcountDetailComActivity.class);
                intent.putExtra("recordsBean",recordsBean);
                context.startActivity(intent);
            }
        });


    }

    private String showMoney(AcountListDtoRecordBean recordsBean) {

        if (recordsBean.getPayType() == 0) {
            return recordsBean.getPoint()+" A点";
        }else if (recordsBean.getPayType() == 1) {
            return "¥ "+recordsBean.getPoint();
        }else if (recordsBean.getPayType() == 2) {
            return recordsBean.getPoint()+" A钻";
        }
        return  recordsBean.getPoint()+"";
    }

    @Override
    public int getItemCount() {
        return records==null?0:records.size();
    }

    public void setData(List<AcountListDtoRecordBean> data) {
        this.records = data;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_point)
        TextView tvPoint;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

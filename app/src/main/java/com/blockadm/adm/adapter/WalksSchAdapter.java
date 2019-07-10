package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.WalksSchduleDto;
import com.blockadm.common.comstomview.RoundImageView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/12.
 */

public class WalksSchAdapter extends RecyclerView.Adapter<WalksSchAdapter.ViewHolder> implements View.OnClickListener {


    private List<WalksSchduleDto.RecordsBean> records;
    private Context context;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;

    public WalksSchAdapter(List<WalksSchduleDto.RecordsBean> records, Context context) {
        this.records = records;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_walks_sch, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        WalksSchduleDto.RecordsBean  recordsBean =   records.get(position);
            ImageUtils.loadImageView(recordsBean.getLogo(),holder.ivImage);

        holder.tvTitle.setText(recordsBean.getTitle());
        holder.tvTime.setText(recordsBean.getShowTime());
        holder.tvAddress.setText(recordsBean.getProvince()+" "+recordsBean.getCity());
        if (recordsBean.getIsCollection()==1){
            holder.ivLike.setImageResource(R.mipmap.ic_like_select);
        }else{
            holder.ivLike.setImageResource(R.mipmap.ic_like);
        }
        holder.itemView.setTag(position);
        holder.ivLike.setTag(recordsBean);
        recordsBean.setPosition(position);
        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WalksSchduleDto.RecordsBean  recordsBean = (WalksSchduleDto.RecordsBean) v.getTag();
                if (recordsBean.getIsCollection()==0){
                    CommonModel.operateNewsActivityCount(new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse t) {
                            ToastUtils.showToast(t.getMsg());
                            if (t.getCode()==0){
                                recordsBean.setIsCollection(1);
                                notifyItemChanged(position);
                            }
                        }


                    },recordsBean.getId(),0,0);
                }else{
                    CommonModel.operateNewsActivityCount(new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse t) {
                            ToastUtils.showToast(t.getMsg());
                            if (t.getCode()==0){
                                recordsBean.setIsCollection(0);
                                notifyItemChanged(position);
                            }
                        }


                    },recordsBean.getId(),0,1);
                }

            }
        });

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

    public  void setmOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        RoundImageView ivImage;
        @BindView(R.id.iv_like)
        ImageView ivLike;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

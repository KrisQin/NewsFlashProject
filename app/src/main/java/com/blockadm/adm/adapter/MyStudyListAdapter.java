package com.blockadm.adm.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.MyStudyPageDTO;
import com.blockadm.common.comstomview.RoundImageView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blockadm.common.utils.ToastUtils.showToast;

/**
 * Created by hp on 2019/2/22.
 */

public class MyStudyListAdapter extends RecyclerView.Adapter<MyStudyListAdapter.ViewHolder> {


    private List<MyStudyPageDTO.RecordsBean> records;
    private Context context;

    public MyStudyListAdapter(List<MyStudyPageDTO.RecordsBean> records, Context context) {
        this.records = records;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mystudy_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MyStudyPageDTO.RecordsBean  recordsBean  = records.get(position);
        ImageUtils.loadImageView(recordsBean.getCoverImgs(),holder.iv);
        if (recordsBean.getAccessStatus()==0){
            holder.tvPrice.setText("免费");
        }else if (recordsBean.getAccessStatus()==1){
            holder.tvPrice.setText(StringUtils.getUnitAmount()+recordsBean.getMoney());
        }

        holder.tvTitle.setText(recordsBean.getTitle());
        holder.tvCrateTime.setText("创建时间: "+recordsBean.getCreateTime());


        holder.tvBuyNum.setText(recordsBean.getBuyPeopleCount()+"人购买");
        if (recordsBean.getStatus()==0){
            holder.tvUp.setText("下架");
            holder.tvUp.setPressed(false);
            holder.tvUp.setTextColor(context.getResources().getColor(R.color.color_ff97979f));
            Drawable dra= context.getResources().getDrawable(R.mipmap.ic_shelf_works_def);
            dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
            holder.tvUp.setCompoundDrawables(dra,null,null,null);
        }else{


            holder.tvUp.setPressed(true);
            holder.tvUp.setText("上架");
            holder.tvUp.setTextColor(context.getResources().getColor(R.color.color_FFF85959));
            Drawable dra= context.getResources().getDrawable(R.mipmap.ic_shelf_works);
            dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
            holder.tvUp.setCompoundDrawables(dra,null,null,null);
        }
        holder.tvSchedule.setText("更新至"+recordsBean.getFinishLessonsCount()+"节课");
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRecyclerviewItemClickListener!=null){
                    mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
                }
            }
        });
        holder.tvUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (recordsBean.getStatus()==0){

                    final MyComstomDialogView myComstomDialogView = new MyComstomDialogView(context);
                    myComstomDialogView.setTvTitle("确定下架吗？",View.VISIBLE)
                            .setChildMsg("下架后，除了已购买用户其他人将看不到你的专栏",View.VISIBLE)
                            .setChildMsg2("",View.GONE)
                            .setConfirmMsg("确定",View.VISIBLE)
                            .setConcelMsg("取消",View.VISIBLE)
                            .setConfirmSize(6)
                            .setCancelLisenner(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    myComstomDialogView.dismiss();
                                }
                            }).setConfirmLisenner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myComstomDialogView.dismiss();
                            CommonModel.updateNlscStatus(recordsBean.getId(), 1, new MyObserver<String>() {
                                @Override
                                public void onSuccess(BaseResponse<String> t) {
                                    showToast(t.getMsg());
                                    recordsBean.setStatus(1);
                                    notifyItemChanged(position);
                                }
                            });
                        }
                    });
                    myComstomDialogView.show();

                }else{
                    final MyComstomDialogView myComstomDialogView = new MyComstomDialogView(context);
                    myComstomDialogView.setTvTitle("确定设为上架吗？",View.VISIBLE).setRootBg(R.mipmap.boxbg2)
                            .setChildMsg("",View.GONE)
                            .setChildMsg2("",View.GONE)
                            .setConfirmMsg("确定",View.VISIBLE)
                            .setConcelMsg("取消",View.VISIBLE)
                            .setConfirmSize(6)
                            .setCancelLisenner(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    myComstomDialogView.dismiss();
                                }
                            }).setConfirmLisenner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myComstomDialogView.dismiss();

                            CommonModel.updateNlscStatus(recordsBean.getId(), 0, new MyObserver<String>() {
                                @Override
                                public void onSuccess(BaseResponse<String> t) {
                                    showToast(t.getMsg());
                                    recordsBean.setStatus(0);
                                    notifyItemChanged(position);
                                }
                            });
                        }
                    });
                    myComstomDialogView.show();

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return records==null?0:records.size();
    }

    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;

    public void setData(List<MyStudyPageDTO.RecordsBean> data) {
        this.records = data;
        notifyDataSetChanged();
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
        @BindView(R.id.tv_crate_time)
        TextView tvCrateTime;
        @BindView(R.id.tv_up)
        TextView tvUp;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }



    public void setmOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

}

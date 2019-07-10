package com.blockadm.adm.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;
import com.blockadm.adm.activity.TagComActivity;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.FormatCurrentData;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.SharedpfTools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/10.
 */

public class NewsArticlePageAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private  Context context;
    private List<NewsArticleListDTO.RecordsBean> records;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener ;

    public NewsArticlePageAdapter3(List<NewsArticleListDTO.RecordsBean> records, Context  context) {
        this.records = records;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_article3, parent, false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head_guanzhu, parent, false);
            return new ViewHolder1(view);
        }


    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        NewsArticleListDTO.RecordsBean  recordsBean =  records.get(position);
        return recordsBean.getId()==0?-1:1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder){
            ViewHolder  viewHolder = (ViewHolder) holder;
            viewHolder.tvContent.setText(records.get(position).getTitle());
            viewHolder.tvResName.setText(records.get(position).getResource());
            viewHolder.tvLiulan.setText(records.get(position).getConvertReadCount()+" 浏览 ");
            ImageUtils.loadImageView(records.get(position).getThumbnail(),viewHolder.iv);
            viewHolder.tvTime.setText(FormatCurrentData.getTimeRange(records.get(position).getPublishedTime()));

        }else{

                ViewHolder1  viewHolder1 = (ViewHolder1) holder;
                viewHolder1.tvManager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty((String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN,""))){
                            showNologinDialog();
                        }else{
                            Intent intent = new Intent(context, TagComActivity.class);
                            context.startActivity(intent);
                        }
                    }
                });
            }
        holder.itemView.setTag(position);
        }



    private void showNologinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.color_transparent);
        alertDialog.setCanceledOnTouchOutside(false);//外部触摸不单独关闭
        View view = View.inflate(context, R.layout.layout_no_login, null);
        Button bg = (Button) view.findViewById(R.id.bt_go_login);
        ImageView ivCancle = (ImageView) view.findViewById(R.id.iv_cancel);
        alertDialog.setView(view);
        alertDialog.show();
        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPathConfig.login_activity_path).withBoolean("is", false).navigation();
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

    public void setmOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    public void setData(List<NewsArticleListDTO.RecordsBean> data) {
        this.records = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_resName)
        TextView tvResName;
        @BindView(R.id.tv_liulan)
        TextView tvLiulan;
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_time)
        TextView tvTime;



        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_manager)
        TextView tvManager;


        public ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

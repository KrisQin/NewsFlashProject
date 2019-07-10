package com.blockadm.adm.adapter;

import android.Manifest;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.NewsFlashShareDialog;
import com.blockadm.adm.interf.OprateTypeListener;
import com.blockadm.adm.persenter.NewsFlashPresenter;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.blockadm.common.utils.ImageUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by hp on 2019/1/10.
 */

public class NewsFlashAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsFlashBeanDto.RecordsBean> records;
    private OprateTypeListener oprateTypeClick;
    private Activity activity;
    public NewsFlashAdapter(List<NewsFlashBeanDto.RecordsBean> records,Activity activity1) {
        this.records = records;
        this.activity =  activity1;
    }

    @Override
    public int getItemViewType(int position) {
        if (records != null && records.get(position).getImageList() != null) {
            return records.get(position).getImageList().size();
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                viewHolder = new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_flash0, parent, false));
                break;

            case 1:
                viewHolder = new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_flash1, parent, false));
                break;

            case 2:
                viewHolder = new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_flash2, parent, false));
                break;

            case 3:
                viewHolder = new ViewHolder3(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_flash3, parent, false));
                break;

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final NewsFlashBeanDto.RecordsBean  recordsBean  = records.get(position);
        if (holder instanceof ViewHolder0) {
            ViewHolder0 viewHolder0 = (ViewHolder0) holder;
            viewHolder0.tvTime.setText(recordsBean.getCreateTime());
            viewHolder0.tvTitle.setText(recordsBean.getTitle());
            viewHolder0.tvContent.setText(recordsBean.getContent());

            viewHolder0.tvNolike.setTag(recordsBean);
            viewHolder0.tvLike.setTag(recordsBean);

            int totalZan =  recordsBean.getNoZanCount()+recordsBean.getYesZanCount();
            if (totalZan!=0){
                double rateNo =  recordsBean.getNoZanCount()*100/totalZan;
                double rateYes =  recordsBean.getYesZanCount()*100/totalZan;

                viewHolder0.tvLike.setText(rateYes+"%");
                viewHolder0.tvNolike.setText(rateNo+"%");
            }else{
                viewHolder0.tvLike.setText("0%");
                viewHolder0.tvNolike.setText("0%");
            }
            viewHolder0.tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) v.getTag();
                    oprateTypeClick.onOprateTypeListen(recordsBean.getId(),0,position);
                }
            });

            viewHolder0.tvNolike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) v.getTag();
                    oprateTypeClick.onOprateTypeListen(recordsBean.getId(),1,position);
                }
            });

            if (recordsBean.getIsZan()==1){
                viewHolder0.tvNolike.setEnabled(false);
                viewHolder0.tvLike.setEnabled(false);
            }else{
                viewHolder0.tvNolike.setEnabled(true);
                viewHolder0.tvLike.setEnabled(true);
            }
            viewHolder0.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareShare(recordsBean);
                }
            });

        } else if (holder instanceof ViewHolder1) {
            ViewHolder1 viewHolder1 = (ViewHolder1) holder;

            viewHolder1.tvTime.setText(recordsBean.getCreateTime());
            viewHolder1.tvTitle.setText(recordsBean.getTitle());
            viewHolder1.tvContent.setText(recordsBean.getContent());
            String s = recordsBean.getImageList().get(0).getUrl();
            ImageUtils.loadImageView(s,viewHolder1.ivPath);

            int totalZan =  recordsBean.getNoZanCount()+recordsBean.getYesZanCount();
            if (totalZan!=0){
                double rateNo =  recordsBean.getNoZanCount()*100/totalZan;
                double rateYes =  recordsBean.getYesZanCount()*100/totalZan;

                viewHolder1.tvLike.setText(rateYes+"%");
                viewHolder1.tvNolike.setText(rateNo+"%");
            }else{
                viewHolder1.tvLike.setText("0%");
                viewHolder1.tvNolike.setText("0%");
            }
            if (recordsBean.getIsZan()==1){
                viewHolder1.tvNolike.setEnabled(false);
                viewHolder1.tvLike.setEnabled(false);
            }else{
                viewHolder1.tvNolike.setEnabled(true);
                viewHolder1.tvLike.setEnabled(true);
            }
            viewHolder1.tvNolike.setTag(recordsBean);
            viewHolder1.tvLike.setTag(recordsBean);
            viewHolder1.tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) v.getTag();
                    oprateTypeClick.onOprateTypeListen(recordsBean.getId(),0,position);
                }
            });


            viewHolder1.tvNolike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) v.getTag();
                    oprateTypeClick.onOprateTypeListen(recordsBean.getId(),1,position);
                }
            });
            viewHolder1.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareShare(recordsBean);
                }
            });

        } else if (holder instanceof ViewHolder2) {
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            viewHolder2.tvTime.setText(recordsBean.getCreateTime());
            viewHolder2.tvTitle.setText(recordsBean.getTitle());
            viewHolder2.tvContent.setText(recordsBean.getContent());
            ImageUtils.loadImageView(recordsBean.getImageList().get(0).getUrl(),viewHolder2.ivPath);
            ImageUtils.loadImageView(recordsBean.getImageList().get(1).getUrl(),viewHolder2.ivPath1);
            if (recordsBean.getIsZan()==1){
                viewHolder2.tvNolike.setEnabled(false);
                viewHolder2.tvLike.setEnabled(false);
            }else{
                viewHolder2.tvNolike.setEnabled(true);
                viewHolder2.tvLike.setEnabled(true);
            }

            int totalZan =  recordsBean.getNoZanCount()+recordsBean.getYesZanCount();
            if (totalZan!=0){
                double rateNo =  recordsBean.getNoZanCount()*100/totalZan;
                double rateYes =  recordsBean.getYesZanCount()*100/totalZan;

                viewHolder2.tvLike.setText(rateYes+"%");
                viewHolder2.tvNolike.setText(rateNo+"%");
            }else{
                viewHolder2.tvLike.setText("0%");
                viewHolder2.tvNolike.setText("0%");
            }

            viewHolder2.tvNolike.setTag(recordsBean);
            viewHolder2.tvLike.setTag(recordsBean);
            viewHolder2.tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) v.getTag();
                    oprateTypeClick.onOprateTypeListen(recordsBean.getId(),0,position);
                }
            });
            viewHolder2.tvNolike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) v.getTag();
                    oprateTypeClick.onOprateTypeListen(recordsBean.getId(),1,position);
                }
            });

            viewHolder2.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareShare(recordsBean);
                }
            });
        } else if (holder instanceof ViewHolder3) {
            ViewHolder3 viewHolder3 = (ViewHolder3) holder;

            viewHolder3.tvTime.setText(recordsBean.getCreateTime());
            viewHolder3.tvTitle.setText(recordsBean.getTitle());
            viewHolder3.tvContent.setText(recordsBean.getContent());
            ImageUtils.loadImageView(recordsBean.getImageList().get(0).getUrl(),viewHolder3.ivPath);
            ImageUtils.loadImageView(recordsBean.getImageList().get(1).getUrl(),viewHolder3.ivPath1);
            ImageUtils.loadImageView(recordsBean.getImageList().get(1).getUrl(),viewHolder3.ivPath2);

            int totalZan =  recordsBean.getNoZanCount()+recordsBean.getYesZanCount();
            if (totalZan!=0){
                double rateNo =  recordsBean.getNoZanCount()*100/totalZan;
                double rateYes =  recordsBean.getYesZanCount()*100/totalZan;

                viewHolder3.tvLike.setText(rateYes+"%");
                viewHolder3.tvNolike.setText(rateNo+"%");
            }else{
                viewHolder3.tvLike.setText("0%");
                viewHolder3.tvNolike.setText("0%");
            }
            if (recordsBean.getIsZan()==1){
                viewHolder3.tvNolike.setEnabled(false);
                viewHolder3.tvLike.setEnabled(false);
            }else{
                viewHolder3.tvNolike.setEnabled(true);
                viewHolder3.tvLike.setEnabled(true);
            }

            viewHolder3.tvNolike.setTag(recordsBean);
            viewHolder3.tvLike.setTag(recordsBean);
            viewHolder3.tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) v.getTag();
                    oprateTypeClick.onOprateTypeListen(recordsBean.getId(),0,position);
                }
            });
            viewHolder3.tvNolike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) v.getTag();
                    oprateTypeClick.onOprateTypeListen(recordsBean.getId(),1,position);
                }
            });

            viewHolder3.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepareShare(recordsBean);
                }
            });

        }









    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }



    class ViewHolder3 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time3)
        TextView tvTime;
        @BindView(R.id.tv_title3)
        TextView tvTitle;
        @BindView(R.id.tv_content3)
        TextView tvContent;
        @BindView(R.id.iv_path3)
        ImageView ivPath;
        @BindView(R.id.iv_path13)
        ImageView ivPath1;

        @BindView(R.id.iv_path23)
        ImageView ivPath2;

        @BindView(R.id.tv_nolike3)
        TextView tvNolike;
        /*      @BindView(R.id.pb)
              ProgressBar pb;*/
        @BindView(R.id.tv_like3)
        TextView tvLike;
        @BindView(R.id.iv_share3)
        ImageView ivShare;
        public ViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time2)
        TextView tvTime;
        @BindView(R.id.tv_title2)
        TextView tvTitle;
        @BindView(R.id.tv_content2)
        TextView tvContent;
        @BindView(R.id.iv_path2)
        ImageView ivPath;
        @BindView(R.id.iv_path12)
        ImageView ivPath1;
        @BindView(R.id.tv_nolike2)
        TextView tvNolike;
        /*      @BindView(R.id.pb)
              ProgressBar pb;*/
        @BindView(R.id.tv_like2)
        TextView tvLike;
        @BindView(R.id.iv_share2)
        ImageView ivShare;
        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time1)
        TextView tvTime;
        @BindView(R.id.tv_title1)
        TextView tvTitle;

        @BindView(R.id.tv_content1)
        TextView tvContent;

        @BindView(R.id.iv_path1)
        ImageView ivPath;
        @BindView(R.id.tv_nolike1)
        TextView tvNolike;
        /*      @BindView(R.id.pb)
              ProgressBar pb;*/
        @BindView(R.id.tv_like1)
        TextView tvLike;
        @BindView(R.id.iv_share1)
        ImageView ivShare;
        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_nolike)
        TextView tvNolike;
        /*      @BindView(R.id.pb)
              ProgressBar pb;*/
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.iv_share)
        ImageView ivShare;
        public ViewHolder0(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void setOprateTypeClick(OprateTypeListener oprateTypeClick) {
        this.oprateTypeClick = oprateTypeClick;
    }

    private void prepareShare(final NewsFlashBeanDto.RecordsBean recordsBean){
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            NewsFlashShareDialog newsFlashDialog = new NewsFlashShareDialog(activity,recordsBean);
                            newsFlashDialog.show();
                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                        }
                    }
                });


    }



}

package com.blockadm.adm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.AudioContentComActivity;
import com.blockadm.adm.activity.CommentDetailComActivity;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.activity.PictrueContentComActivity;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.AwardDto;
import com.blockadm.common.bean.CommentBeanDTO;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.params.AddReplyBean;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.ScrollDisabledListView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.SharedpfTools;

import java.util.List;

/**
 * Created by hp on 2019/1/17.
 */

public class PictrueContentCommentAdapter extends RecyclerView.Adapter<PictrueContentCommentAdapter.ViewHolder>  {


    private  Context context;
    private List<CommentBeanDTO.RecordsBean> records;
    private int visibilityHuifu;

    public PictrueContentCommentAdapter(List<CommentBeanDTO.RecordsBean> records, Context context) {
        this.records = records;
        this.context =context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pictrue_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CommentBeanDTO.RecordsBean recordsBean  = records.get(position);
        if (recordsBean!=null){
            holder.tvCreateTime.setText(recordsBean.getCreateTime());
            ImageUtils.loadImageView(recordsBean.getIcon(), holder.civHeadimage);
            holder.tvNickName.setText(recordsBean.getNickName());
            holder.tvContent.setText(recordsBean.getContent());
            ImageUtils.loadImageView(recordsBean.getIcon(),holder.civHeadimage);
            if (recordsBean.getZanCount()>0){
                holder.tvZan.setText(recordsBean.getZanCount()+"");
            }else{
                holder.tvZan.setText("");
            }
            if (recordsBean.getVipState()==1){
                holder.tvNickName.setTextColor(context.getResources().getColor(R.color.color_FF6B00));
            }else{
                holder.tvNickName.setTextColor(context.getResources().getColor(R.color.color_ff97979f));
            }
            ImageUtils.loadImageView(recordsBean.getLevelDiamondIcon(),holder.ivZuan);

            if (recordsBean.getIsZan()!=0){
                Drawable dra= context.getResources().getDrawable(R.mipmap.news_reply_agree_press);
                dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
                holder.tvZan.setCompoundDrawables(dra, null, null, null);

            }else{
                Drawable dra= context.getResources().getDrawable(R.mipmap.reply_agree_default);
                dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
                holder.tvZan.setCompoundDrawables(dra, null, null, null);
            }
            holder.tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recordsBean.getIsZan()==0) {
                        CommonModel.operateNewsLessonsCommentCount(0,recordsBean.getId(), 1, new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                if (t.getCode()==0){
                                    recordsBean.setIsZan(1);

                                    recordsBean.setZanCount(recordsBean.getZanCount() +1);
                                    notifyItemChanged(position);


                                    String tokenString  = (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN,"");
                                    if (!TextUtils.isEmpty(tokenString)){
                                        CommonModel.addEncourage(403, recordsBean.getId(), new MyObserver<AwardDto>() {
                                            @Override
                                            public void onSuccess(BaseResponse<AwardDto> t) {
                                                if (t.getCode()==0){
                                                    if (t.getData().getIsTrue()==0){
                                                        ShowPopupWindow(t.getData(),"点赞");
                                                    }
                                                }else {
                                                    Toast.makeText(context,t.getMsg(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }

                            }


                        });
                    }else{
                        CommonModel.operateNewsLessonsCommentCount(0,recordsBean.getId(), 0, new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                if (t.getCode()==0){
                                    recordsBean.setIsZan(0);
                                    recordsBean.setZanCount(recordsBean.getZanCount() -1 );
                                    notifyItemChanged(position);
                                }

                            }


                        });
                    }

                }
            });





            if (recordsBean.getReplyCount()>3){
                holder.tvOpen.setVisibility(View.VISIBLE);
                holder.tvOpen.setText("展开全部"+recordsBean.getReplyCount()+"跟帖>>");
            }else{
                holder.tvOpen.setVisibility(View.GONE);
                holder.tvOpen.setText("");
            }
            List<CommentReplyListBean>  commentReplyListBeans =  recordsBean.getCommentReplyList();
            CommentChildAdapter  commentChildAdapter = new CommentChildAdapter(commentReplyListBeans);
            holder.lvReply.setAdapter(commentChildAdapter);


            if (commentReplyListBeans!=null){
                holder.lvReply.setTag(commentReplyListBeans);

            }
            holder.tvFollow.setTag(recordsBean);
            holder.tvOpen.setTag(recordsBean);
            holder.lvReply.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<CommentReplyListBean>  commentReplyListBeans  = ( List<CommentReplyListBean>) parent.getTag();
                    if (commentReplyListBeans.size()>0){
                        CommentReplyListBean  commentReplyListBean =  commentReplyListBeans.get(position);

                        AddReplyBean addReplyBean = new AddReplyBean();
                        addReplyBean.setCommentType(1);
                        addReplyBean.setMasterMemberId(commentReplyListBean.getMemberId());
                        addReplyBean.setNewsLessonsCommentId(commentReplyListBean.getNewsLessonsCommentId());

                        addReplyBean.setParentId(commentReplyListBean.getId());
                        addReplyBean.setContent("@" + recordsBean.getNickName() + ": ");

                        //  addReplyBean.setId(commentReplyListBean.getId());
                        if (context instanceof PictrueContentComActivity){
                            PictrueContentComActivity pictrueContentActivity = (PictrueContentComActivity) context;
                            pictrueContentActivity.addReply(addReplyBean);
                        }else if (context instanceof AudioContentComActivity){
                            AudioContentComActivity audioContentActivity = (AudioContentComActivity) context;
                            audioContentActivity.addReply(addReplyBean);
                        }
                    }

                }
            });
            holder.tvFollow.setVisibility(visibilityHuifu);
            holder.tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentBeanDTO.RecordsBean recordsBean = (CommentBeanDTO.RecordsBean) v.getTag();
                    if (recordsBean !=null){
                        AddReplyBean  addReplyBean = new AddReplyBean();
                        addReplyBean.setCommentType(0);
                        addReplyBean.setParentId(0);
                        addReplyBean.setMasterMemberId(0);
                        addReplyBean.setNewsLessonsCommentId(recordsBean.getId());
                        addReplyBean.setContent("@" + recordsBean.getNickName() + ": ");
                        if (context instanceof PictrueContentComActivity){
                            PictrueContentComActivity pictrueContentActivity = (PictrueContentComActivity) context;
                            pictrueContentActivity.addReply(addReplyBean);
                        }else if (context instanceof AudioContentComActivity){
                            AudioContentComActivity audioContentActivity = (AudioContentComActivity) context;
                            audioContentActivity.addReply(addReplyBean);
                        }
                    }

                }
            });
            holder.tvOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentBeanDTO.RecordsBean recordsBean = (CommentBeanDTO.RecordsBean) v.getTag();
                    Intent intent  = new Intent(context, CommentDetailComActivity.class);
                    intent.putExtra(ConstantUtils.RECORDSBEAN,recordsBean);
                    context.startActivity(intent);
                }
            });
            holder.civHeadimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent(context, PersonnalIndexComActivity.class);
                    intent.putExtra(ConstantUtils.MENBERID,recordsBean.getMemberId());
                    context.startActivity(intent);
                }
            });

        }




    }
    private PopupWindow mPopupWindow;
    private  View popView;
    private TextView  tvType;
    private void ShowPopupWindow(final AwardDto  awardDto,String text) {
        popView = LayoutInflater.from(context).inflate(com.blockadm.common.R.layout.layout_share_award, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setContentView(popView);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // PopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, com.blockadm.adm.R.drawable.popupwindow_background));
        mPopupWindow.setAnimationStyle(com.blockadm.adm.R.style.MyPopupWindow_anim_style);
        tvType = (TextView) popView.findViewById(com.blockadm.adm.R.id.tv_type);//拍摄照片
        tvType.setText(text);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        mPopupWindow.setOutsideTouchable(false);  //设置点击屏幕其它地方弹出框消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        popView.setFocusable(false);
        mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.dismiss();
                tvType.setText("+"+awardDto.getAwardDiamond()+"A钻");
                mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();

                    }
                },2000);


            }
        }, 2000);
    }
    private android.os.Handler handler = new android.os.Handler();

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }
    public void setVisibilityHuifu(int visibilityHuifu) {
        this.visibilityHuifu = visibilityHuifu;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

      private  CircleImageView civHeadimage;
      private TextView tvNickName;
        private TextView tvContent;
        private TextView tvZan;
        private TextView tvCreateTime;
        private ImageView ivZuan;
        private TextView tvOpen;
        private ScrollDisabledListView lvReply;
        private TextView tvFollow;
        public ViewHolder(View itemView) {
            super(itemView);
            civHeadimage =   itemView.findViewById(R.id.civ_headimage);
            tvNickName =   itemView.findViewById(R.id.tv_nickname);
            tvContent =   itemView.findViewById(R.id.tv_content);
             tvZan =   itemView.findViewById(R.id.tv_zan);
            tvCreateTime =   itemView.findViewById(R.id.tv_cre_time);
            ivZuan =   itemView.findViewById(R.id.iv_zuan);
            lvReply =   itemView.findViewById(R.id.lv_reply);
            tvOpen =   itemView.findViewById(R.id.tv_open);
            tvFollow =   itemView.findViewById(R.id.tv_follow);

        }
    }
}

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
import com.blockadm.adm.activity.CommentDetailComActivity;
import com.blockadm.adm.activity.InfomationDetailActivty;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.AwardDto;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.params.AddReplyBean;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.ScrollDisabledListView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.SharedpfTools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/15.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private PageNewsArticleCommentDTO pageNewsArticleCommentDTO;
    private InfomationDetailActivty infomationDetailActivty;
    private Context context;

    public CommentAdapter(PageNewsArticleCommentDTO pageNewsArticleCommentDTO, InfomationDetailActivty infomationDetailActivty) {
        this.pageNewsArticleCommentDTO = pageNewsArticleCommentDTO;
        this.infomationDetailActivty = infomationDetailActivty;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PageNewsArticleCommentDTO.RecordsBean recordsBean = pageNewsArticleCommentDTO.getRecords().get(position);
        if (recordsBean != null) {
            holder.tvNickname.setText(recordsBean.getNickName());
            holder.tvContent.setText(recordsBean.getContent());
            holder.tvCreTime.setText(recordsBean.getCreateTime());
            ImageUtils.loadImageView(recordsBean.getIcon(), holder.civHeadimage);
            if (recordsBean.getZanCount() > 0) {
                holder.tvZan.setText(recordsBean.getZanCount() + "");
            } else {
                holder.tvZan.setText("   ");
            }

            if (recordsBean.getIsZan() == 1) {
                Drawable dra = context.getResources().getDrawable(R.mipmap.news_reply_agree_press);
                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                holder.tvZan.setCompoundDrawables(dra, null, null, null);
            } else {
                Drawable dra = context.getResources().getDrawable(R.mipmap.reply_agree_default);
                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                holder.tvZan.setCompoundDrawables(dra, null, null, null);
            }

            if (recordsBean.getVipState() == 1) {
                holder.tvNickname.setTextColor(context.getResources().getColor(R.color.color_FF6B00));
            } else {
                holder.tvNickname.setTextColor(context.getResources().getColor(R.color.color_ff97979f));
            }
            ImageUtils.loadImageView(recordsBean.getLevelDiamondIcon(), holder.ivZuan);

            if (recordsBean.getReplyCount() > 3) {
                holder.tvOpen.setVisibility(View.VISIBLE);
                holder.tvOpen.setText("展开全部" + recordsBean.getReplyCount() + "跟帖>>");
            } else {
                holder.tvOpen.setVisibility(View.GONE);
                holder.tvOpen.setText("");
            }
            List<CommentReplyListBean> commentReplyListBeans = recordsBean.getCommentReplyList();
            CommentChildAdapter commentChildAdapter = new CommentChildAdapter(commentReplyListBeans);
            holder.lvReply.setAdapter(commentChildAdapter);


            if (commentReplyListBeans != null) {
                holder.lvReply.setTag(commentReplyListBeans);

            }
            holder.tvFollow.setTag(recordsBean);
            holder.tvOpen.setTag(recordsBean);
            holder.lvReply.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<CommentReplyListBean> commentReplyListBeans = (List<CommentReplyListBean>) parent.getTag();
                    if (commentReplyListBeans.size() > 0) {
                        CommentReplyListBean commentReplyListBean = commentReplyListBeans.get(position);
                        int id1 = commentReplyListBean.getId();
                        int memberId = commentReplyListBean.getMemberId();
                        int newsArticleId = commentReplyListBean.getNewsArticleId();
                        int newsArticleCommentId = commentReplyListBean.getNewsArticleCommentId();
                        AddReplyBean addReplyBean = new AddReplyBean(1, "", 0, memberId, 0, newsArticleCommentId, id1, newsArticleId);
                        infomationDetailActivty.addReply(addReplyBean);
                    }

                }
            });

            holder.tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PageNewsArticleCommentDTO.RecordsBean recordsBean = (PageNewsArticleCommentDTO.RecordsBean) v.getTag();
                /*
                * "newsArticleCommentId": 0,
                   "newsArticleId": 0,
                * */
                    if (recordsBean != null) {
                        int newsArticleCommentId = recordsBean.getId();
                        int newsArticleId = recordsBean.getNewsArticleId();
                        AddReplyBean addReplyBean = new AddReplyBean(0, "@" + recordsBean.getNickName() + ": ", 0, 0, 0, newsArticleCommentId, 0, newsArticleId);
                        infomationDetailActivty.addReply(addReplyBean);
                    }

                }
            });
            holder.tvOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PageNewsArticleCommentDTO.RecordsBean recordsBean = (PageNewsArticleCommentDTO.RecordsBean) v.getTag();
                    Intent intent = new Intent(infomationDetailActivty, CommentDetailComActivity.class);
                    intent.putExtra(ConstantUtils.RECORDSBEAN, recordsBean);
                    infomationDetailActivty.startActivity(intent);
                }
            });
            holder.civHeadimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(infomationDetailActivty, PersonnalIndexComActivity.class);
                    intent.putExtra(ConstantUtils.MENBERID, recordsBean.getMemberId());
                    infomationDetailActivty.startActivity(intent);
                }
            });

            final boolean isZanStatus = recordsBean.getIsZan() == 1;

            holder.tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    final TextView tvZan = (TextView) v;
                    if (recordsBean.getIsZan() == 1) {
                        CommonModel.operateNewsArticleCommentCount(recordsBean.getId(),
                                recordsBean.getNewsArticleId(), 1, new MyObserver<String>() {
                                    @Override
                                    public void onSuccess(BaseResponse<String> t) {
                                        Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
                                        if (t.getCode() == 0) {
                                            recordsBean.setIsZan(0);
                                            Drawable dra = context.getResources().getDrawable(R.mipmap.reply_agree_default);
                                            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                                            tvZan.setCompoundDrawables(dra, null, null, null);

                                            if (isZanStatus) {
                                                holder.tvZan.setText(recordsBean.getZanCount() - 1 == 0 ? "   " : recordsBean.getZanCount() - 1 + "");
                                            } else {
                                                holder.tvZan.setText(recordsBean.getZanCount() == 0 ? "   " : recordsBean.getZanCount() + "");
                                            }

                                        }
                                    }
                                });
                    } else {
                        CommonModel.operateNewsArticleCommentCount(recordsBean.getId(), recordsBean.getNewsArticleId(), 0, new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean.setIsZan(1);
                                    Drawable dra = context.getResources().getDrawable(R.mipmap.news_reply_agree_press);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                                    tvZan.setCompoundDrawables(dra, null, null, null);


                                    if (isZanStatus) {
                                        holder.tvZan.setText(recordsBean.getZanCount() == 0 ? "   " : recordsBean.getZanCount() + "");
                                    } else {
                                        holder.tvZan.setText(recordsBean.getZanCount() + 1 + "");
                                    }

                                    String tokenString = (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
                                    if (!TextUtils.isEmpty(tokenString)) {
                                        CommonModel.addEncourage(402, recordsBean.getId(), new MyObserver<AwardDto>() {
                                            @Override
                                            public void onSuccess(BaseResponse<AwardDto> t) {
                                                if (t.getCode() == 0) {
                                                    if (t.getData().getIsTrue() == 0) {
                                                        ShowPopupWindow(t.getData(), "点赞");
                                                    }

                                                } else {
                                                    Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }

                }
            });


        }

    }


    private PopupWindow mPopupWindow;
    private View popView;
    private TextView tvType;

    private void ShowPopupWindow(final AwardDto awardDto, String text) {
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
                tvType.setText("+" + awardDto.getAwardDiamond() + "A钻");
                mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();

                    }
                }, 2000);


            }
        }, 2000);
    }

    private android.os.Handler handler = new android.os.Handler();

    @Override
    public int getItemCount() {
        return pageNewsArticleCommentDTO.getRecords() == null ? 0 : pageNewsArticleCommentDTO.getRecords().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_headimage)
        CircleImageView civHeadimage;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.lv_reply)
        ScrollDisabledListView lvReply;
        @BindView(R.id.tv_open)
        TextView tvOpen;
        @BindView(R.id.tv_cre_time)
        TextView tvCreTime;
        @BindView(R.id.tv_follow)
        TextView tvFollow;
        @BindView(R.id.iv_zuan)
        ImageView ivZuan;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

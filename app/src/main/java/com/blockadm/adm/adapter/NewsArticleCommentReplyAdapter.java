package com.blockadm.adm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.CommentDetailDto;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.ScrollDisabledListView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/17.
 */

public class NewsArticleCommentReplyAdapter extends RecyclerView.Adapter<NewsArticleCommentReplyAdapter.ViewHolder> {


    private List<CommentDetailDto.RecordsBean> records;
    private Context context;
    private int type;

    public NewsArticleCommentReplyAdapter(List<CommentDetailDto.RecordsBean> records, int type) {
        this.records = records;
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_articlecommentreplylayout, parent, false);
        return new ViewHolder(view,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CommentDetailDto.RecordsBean recordsBean = records.get(position);

        holder.tvCreTime.setText(recordsBean.getCreateTime());
        ImageUtils.loadImageView(recordsBean.getIcon(), holder.civHeadimage);
        holder.itemView.setTag(position);

        /*
        12000 -- 473

        15000 -- 590

        3409

         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.d("XTT","onItemClickListener mItemClickListener: "+mItemClickListener);
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, position);
                }
            }
        });

        switch (recordsBean.getCredentialsState()) {
            case 0:
                holder.tvInfoType.setVisibility(View.GONE);
                break;
            case 1:
                holder.tvInfoType.setVisibility(View.GONE);
                break;
            case 2:

                holder.tvInfoType.setText("机构· ");
                break;
            case 3:
                holder.tvInfoType.setText("机构·");
                break;
        }
        if (recordsBean.getCommentType() == 0) {
            holder.tvNickname.setText(recordsBean.getNickName());
            holder.tvContent.setText(recordsBean.getContent());
            holder.tvBackName.setVisibility(View.GONE);
        } else {
            holder.tvNickname.setText(recordsBean.getNickName());
            holder.tvBackName.setVisibility(View.VISIBLE);
            holder.tvBackName.setText("回复@" + recordsBean.getMasterNickName());
            holder.tvContent.setText("：" + recordsBean.getContent());
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

        holder.tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    if (recordsBean.getIsZan() == 1) {
                        CommonModel.replayZanCount(recordsBean.getId(),
                                recordsBean.getNewsArticleId(), 1, new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean.setIsZan(0);
                                    Drawable dra =
                                            context.getResources().getDrawable(R.mipmap.reply_agree_default);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(),
                                            dra.getMinimumHeight());
                                    holder.tvZan.setCompoundDrawables(dra, null, null, null);
                                }
                            }
                        });
                    } else {
                        CommonModel.replayZanCount(recordsBean.getId(),
                                recordsBean.getNewsArticleId(), 0, new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean.setIsZan(1);
                                    Drawable dra =
                                            context.getResources().getDrawable(R.mipmap.news_reply_agree_press);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(),
                                            dra.getMinimumHeight());
                                    holder.tvZan.setCompoundDrawables(dra, null, null, null);
                                }
                            }
                        });
                    }
                } else if (type == 2) {
                    if (recordsBean.getIsZan() == 1) {
                        CommonModel.replayZanCountLessonsComment(recordsBean.getId(), 1,
                                new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean.setIsZan(0);
                                    Drawable dra =
                                            context.getResources().getDrawable(R.mipmap.reply_agree_default);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(),
                                            dra.getMinimumHeight());
                                    holder.tvZan.setCompoundDrawables(dra, null, null, null);
                                }
                            }
                        });
                    } else {
                        CommonModel.replayZanCountLessonsComment(recordsBean.getId(), 0,
                                new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean.setIsZan(1);
                                    Drawable dra =
                                            context.getResources().getDrawable(R.mipmap.news_reply_agree_press);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(),
                                            dra.getMinimumHeight());
                                    holder.tvZan.setCompoundDrawables(dra, null, null, null);
                                }
                            }
                        });
                    }
                }
            }
        });


        holder.civHeadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonnalIndexComActivity.class);
                intent.putExtra(ConstantUtils.MENBERID, recordsBean.getMemberId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }

    OnRecycleViewItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnRecycleViewItemClickListener listener) {
        mItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_headimage)
        CircleImageView civHeadimage;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.tv_cre_time)
        TextView tvCreTime;
        @BindView(R.id.tv_author_info_type)
        TextView tvInfoType;
        @BindView(R.id.iv_zuan)
        ImageView ivZuan;

        @BindView(R.id.tv_back_name)
        TextView tvBackName;

        public ViewHolder(View itemView,final OnRecycleViewItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v,getPosition());
                    }
                }
            });
        }
    }
}

package com.blockadm.adm.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.FormatCurrentData;
import com.blockadm.common.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/10.
 */

public class NewsArticlePageAdapter extends RecyclerView.Adapter<NewsArticlePageAdapter.ViewHolder> implements View.OnClickListener {


    private List<NewsArticleListDTO.RecordsBean> records;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener ;
    private Context context;

    public NewsArticlePageAdapter(List<NewsArticleListDTO.RecordsBean> records) {
        this.records = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context =  parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_article, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NewsArticleListDTO.RecordsBean  recordsBean =  records.get(position);
        holder.tvContent.setText(recordsBean.getTitle());
        if (!TextUtils.isEmpty(recordsBean.getAuthor())){
            holder.tvResName.setText(recordsBean.getAuthor());
        }else{
            holder.tvResName.setText(recordsBean.getResource());
        }
        ImageUtils.loadImageView(recordsBean.getThumbnail(),holder.iv);
        holder.tvTime.setText(recordsBean.getPublishedAt());
       // holder.tvMsgNum.setText("评论 "+recordsBean.getCommentCount());
        holder.tvLiulan.setText(recordsBean.getConvertReadCount()+" 浏览");
     /*   if (recordsBean.getIsCollection()==0){
            Drawable rightDrawable = context.getResources().getDrawable(R.mipmap.ic_collection_works);
            rightDrawable.setBounds(0,0,rightDrawable.getMinimumWidth(),rightDrawable.getMinimumHeight());
            holder.tvCollect.setCompoundDrawables(null,null,rightDrawable,null);

        }else{
            Drawable rightDrawable = context.getResources().getDrawable(R.mipmap.ic_collection_works_press);
            rightDrawable.setBounds(0,0,rightDrawable.getMinimumWidth(),rightDrawable.getMinimumHeight());
            holder.tvCollect.setCompoundDrawables(null,null,rightDrawable,null);

        }

        holder.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                   int choose =0;
                    if (recordsBean.getIsCollection()==0){
                        choose = 0;
                        CommonModel.operateArticleCount(recordsBean.getId(), 3, choose, new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                recordsBean.setIsCollection(1);
                                recordsBean.setCollectCount(recordsBean.getCollectCount()+1);
                            }


                        });
                    }else{
                        choose =1;
                        CommonModel.operateArticleCount(recordsBean.getId(), 3, choose, new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                recordsBean.setIsCollection(0);
                                recordsBean.setCollectCount(recordsBean.getCollectCount()-1);
                            }


                        });
                    }
                    notifyItemChanged(position);
            }
        });*/

        holder.itemView.setTag(position);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_resName)
        TextView tvResName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv)
        ImageView iv;
      /*  @BindView(R.id.tv_msg_num)
        TextView tvMsgNum;*/

      /*  @BindView(R.id.tv_collect)
        TextView tvCollect;*/

        @BindView(R.id.tv_liulan)
        TextView tvLiulan;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

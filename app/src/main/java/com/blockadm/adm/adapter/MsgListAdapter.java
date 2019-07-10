package com.blockadm.adm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.UserMsgDto;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/13.
 */

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.ViewHolder> {

    private List<UserMsgDto.RecordsBean> records;
    private Context context;

    public MsgListAdapter(List<UserMsgDto.RecordsBean> records) {
        this.records = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_msg_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UserMsgDto.RecordsBean  recordsBean =  records.get(position);
        ImageUtils.loadImageView(recordsBean.getIcon(),holder.ivType);
        holder.tvContent.setText(Html.fromHtml(recordsBean.getHtmlContent()));
       // holder.tvName.setText(recordsBean.getUserNickName());
        holder.tvTitle.setText(recordsBean.getTitle());
        if (recordsBean.isOpen()){
            holder.tvContent.setMaxLines(100);
        }else{
            holder.tvContent.setMaxLines(1);
        }

        if (recordsBean.getState()==1){
            ImageUtils.loadImageView(recordsBean.getReadIcon(),holder.ivType);
        }else{
            ImageUtils.loadImageView(recordsBean.getIcon(),holder.ivType);
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date  date =  sdf.parse(recordsBean.getCreateDate());
            sdf = new SimpleDateFormat("MM-dd HH:mm");
            holder.tvTime.setText(sdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        recordsBean.setPosition(position);
        holder.tvContent.setTag(recordsBean);
        holder.itemView.setTag(holder.tvContent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tvContent = (TextView)v.getTag();
                final UserMsgDto.RecordsBean  recordsBean = (UserMsgDto.RecordsBean) tvContent.getTag();
                if (recordsBean.getTypeId()!=10){
                    if (recordsBean.isOpen()){
                        recordsBean.setOpen(false);
                    }else{
                        recordsBean.setOpen(true);
                        if (recordsBean.getState()==0){
                            CommonModel.markUserMsg(new MyObserver<String>() {
                                @Override
                                public void onSuccess(BaseResponse<String> t) {

                                    if (t.getCode()==0){
                                        recordsBean.setState(1);
                                        notifyItemChanged(recordsBean.getPosition());
                                    }else{
                                        Toast.makeText(context,t.getMsg(),Toast.LENGTH_SHORT).show();
                                    }

                                }


                            },recordsBean.getId(),1);
                        }
                    }
                }else{
                    if (recordsBean.isOpen()){
                        recordsBean.setOpen(false);
                    }else{
                        recordsBean.setOpen(true);}
                }


                notifyItemChanged(position);

            }
        });
        holder.tvName.setTag(recordsBean.getUserId());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent  = new Intent(context, PersonnalIndexComActivity.class);
                    intent.putExtra(ConstantUtils.MENBERID, Integer.parseInt((String) v.getTag()) );
                    context.startActivity(intent);
                }catch (Exception e){

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }

    public void setData(List<UserMsgDto.RecordsBean> data) {
        this.records = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_nickname)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.blockadm.adm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.UserListBean;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ImageUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/25.
 */

public class FransListAdapter extends RecyclerView.Adapter<FransListAdapter.ViewHolder> {

    private Context context;
    private List<UserListBean> records;

    public FransListAdapter(Context context, List<UserListBean> records) {
        this.records = records;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fams_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final UserListBean   recordsBean =  records.get(position);
        holder.tvNickName.setText(recordsBean.getNickName());
        ImageUtils.loadImageView(recordsBean.getIcon(),holder.ivImage);
        UserInfoDto  userInfoDto = (UserInfoDto) ACache.get(context).getAsObject("userInfoDto");
        if (userInfoDto!=null&&userInfoDto.getMemberId()==recordsBean.getId()){
            holder.tvAttention.setVisibility(View.GONE);
        }else{
            holder.tvAttention.setVisibility(View.VISIBLE);
        }


        switch (recordsBean.getMutualStatus()){
            case 0:
                holder.tvAttention.setCompoundDrawables(null, null, null, null);
                holder.tvAttention.setText("已关注");

                break;
            case 1:
                holder.tvAttention.setCompoundDrawables(null, null, null, null);
                holder.tvAttention.setText("已互粉");
                break;

            case -1:
                Drawable dra1= context.getResources().getDrawable(R.mipmap.ic_add_copy);
                dra1.setBounds(0, 0, dra1.getMinimumWidth(), dra1.getMinimumHeight());// 设置图片宽高
                holder.tvAttention.setCompoundDrawables(dra1, null, null, null);
                holder.tvAttention.setText("关注");
                break;
        }
         if (recordsBean!=null){
             holder.tvAttention.setTag(recordsBean);
             recordsBean.setPosition(position);
         }
        holder.tvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserListBean  recordsBean = (UserListBean) v.getTag();
               if (recordsBean.getMutualStatus()==-1){
                   CommonModel.addUserFollow(recordsBean.getId(), new MyObserver<String>() {
                       @Override
                       public void onSuccess(BaseResponse<String> baseResponse) {
                           recordsBean.setMutualStatus(Integer.parseInt(baseResponse.getData()));
                           notifyItemChanged(recordsBean.getPosition());
                           EventBus.getDefault().post(new UserDataEvent());
                       }


                   });
               }else{
                   CommonModel.deleteUserFollow(recordsBean.getId(), new MyObserver<String>() {
                       @Override
                       public void onSuccess(BaseResponse<String> baseResponse) {
                           recordsBean.setMutualStatus(Integer.parseInt(baseResponse.getData()));
                           notifyItemChanged(recordsBean.getPosition());
                           EventBus.getDefault().post(new UserDataEvent());
                       }


                   });
               }

            }
        });
       // holder.ivImage.setTag(recordsBean.getId()+"");
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context, PersonnalIndexComActivity.class);
                intent.putExtra(ConstantUtils.MENBERID, recordsBean.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return records==null?0:records.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        CircleImageView ivImage;
        @BindView(R.id.tv_nickName)
        TextView tvNickName;
        @BindView(R.id.tv_attention)
        TextView tvAttention;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.blockadm.adm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.GetMyLevelAppDto;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.utils.GlideTools;
import com.blockadm.common.utils.StringInterceptionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/21.
 */

public class MyLevelAdapter extends RecyclerView.Adapter<MyLevelAdapter.ViewHolder> {


    private List<GetMyLevelAppDto.LevelListBean> levelList;
    private Context context;

    private ClickItemCallback clickItemCallback;

    public interface ClickItemCallback {
        void showLoading();
        void dismissLoading();
    }


    public MyLevelAdapter(List<GetMyLevelAppDto.LevelListBean> levelList,ClickItemCallback clickItemCallback) {
        this.levelList = levelList;
        this.clickItemCallback = clickItemCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_level, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GetMyLevelAppDto.LevelListBean levelListBean = levelList.get(position);

        GlideTools.setImgByGlideSource(context,levelListBean.getReceiveUrl(),holder.ivStatus);
        switch (levelListBean.getReceiveState()){
            case 0 :
                holder.ivStatus.setEnabled(false);
                break;
            case 1 :
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickItemCallback.showLoading();
                        CommonModel.receiveLevelAward(new ComObserver<String>() {

                            @Override
                            public void onSuccess(BaseResponse<String> t,String errorMsg) {
                                clickItemCallback.dismissLoading();
                                if (t.getCode()==0){
                                    EventBus.getDefault().post(new UserDataEvent());
                                    levelListBean.setReceiveState(2);
                                    notifyItemChanged(position);
                                }else{
                                    Toast.makeText(context,t.getMsg(),Toast.LENGTH_SHORT).show();
                                }

                            }

                        },levelListBean.getLevelId());
                    }
                });
                break;
            case 2 :
                holder.ivStatus.setEnabled(false);
                break;

        }
        holder.tvItemTitle.setText(levelListBean.getLevelName());
        holder.tvItemDec.setText(StringInterceptionUtil.matcherSearchText(Color.RED,"邀请分享"+levelListBean.getAdvanceSize()+"/"+levelListBean.getAdvanceBase()+"位好友",levelListBean.getAdvanceSize()+""));



    }



    @Override
    public int getItemCount() {
        return levelList == null ? 0 : levelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_dec)
        TextView tvItemDec;
        @BindView(R.id.iv_status)
        ImageView ivStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.blockadm.adm.im_module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.im.entity.MessageInfo;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Kris on 2019/5/9
 *
 * @Describe TODO { 弹出的直播列表 }
 */
public class LiveToastAdapter extends RecyclerView.Adapter<LiveToastAdapter.LiveHolder> {

    private List<MessageInfo> mMsgInfoList;
    private Context mContext;

    public LiveToastAdapter(Context context, List<MessageInfo> msgInfoList) {
        mContext = context;
        mMsgInfoList = msgInfoList;
    }

    @Override
    public LiveToastAdapter.LiveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_toast_list,
                parent, false);
        return new LiveHolder(view);

    }

    @Override
    public void onBindViewHolder(LiveToastAdapter.LiveHolder holder, int position) {

        MessageInfo msg = mMsgInfoList.get(position);

        Glide.with(mContext).load(msg.getHeadImageUrl()).into(holder.iv_head_image);

        holder.tv_content.setText(msg.getContent());

    }


    @Override
    public int getItemCount() {
        return mMsgInfoList.size();
    }


    static class LiveHolder extends RecyclerView.ViewHolder {

        CircleImageView iv_head_image; // 头像
        TextView tv_content;


        public LiveHolder(View itemView) {
            super(itemView);

            iv_head_image = itemView.findViewById(R.id.iv_head_image);
            tv_content = itemView.findViewById(R.id.tv_content);

        }
    }
}

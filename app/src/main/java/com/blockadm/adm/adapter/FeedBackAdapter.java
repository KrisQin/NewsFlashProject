package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.FeedBackTypeDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/31.
 */

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.ViewHolder> {


    private ArrayList<FeedBackTypeDTO> feedBackTypeDTOS;
    private Context context;

    public FeedBackAdapter(Context context, ArrayList<FeedBackTypeDTO> feedBackTypeDTOS) {
        this.context = context;
        feedBackTypeDTOS.get(0).setChecked(true);
        this.feedBackTypeDTOS = feedBackTypeDTOS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed_back_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,  int position) {
        FeedBackTypeDTO  feedBackTypeDTO =  feedBackTypeDTOS.get(position);
        holder.tvName.setText(feedBackTypeDTO.getName());
        holder.ivStatus.setChecked(feedBackTypeDTO.isChecked());
        holder.ivStatus.setTag(feedBackTypeDTO);
        feedBackTypeDTO.setPosition(position);
        holder.ivStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < feedBackTypeDTOS.size(); i++) {
                    FeedBackTypeDTO  feedBackTypeDTO =  feedBackTypeDTOS.get(i);
                    feedBackTypeDTO.setChecked(false);
                }
                FeedBackTypeDTO  feedBackTypeDTO = (FeedBackTypeDTO) v.getTag();
                feedBackTypeDTO.setChecked(true);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return feedBackTypeDTOS == null ? 0 : feedBackTypeDTOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_status)
        CheckBox ivStatus;
        @BindView(R.id.tv_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

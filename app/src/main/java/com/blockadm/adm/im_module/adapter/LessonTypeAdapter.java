package com.blockadm.adm.im_module.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.LearnListAdapter;
import com.blockadm.common.bean.StudyTypeInfo;
import com.blockadm.common.call.OnRecycleViewItemClickListener;

import java.util.List;

/**
 * Created by Kris on 2019/6/5
 *
 * @Describe TODO {  }
 */
public class LessonTypeAdapter extends RecyclerView.Adapter<LessonTypeAdapter.MyViewHolder> {

    private List<StudyTypeInfo> mList;
    private Activity mActivity;

    public LessonTypeAdapter(Activity activity,List<StudyTypeInfo> list ) {
        mList = list;
        mActivity = activity;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_lessons_type, parent,
                false);
        return new MyViewHolder(view,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StudyTypeInfo info = mList.get(position);

        holder.tv_type.setText(info.getTypeName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    OnRecycleViewItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnRecycleViewItemClickListener listener) {
        mItemClickListener = listener;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout layout_type;
        TextView tv_type;


        public MyViewHolder(View itemView, final OnRecycleViewItemClickListener listener) {
            super(itemView);

            layout_type = itemView.findViewById(R.id.layout_type);
            tv_type = itemView.findViewById(R.id.tv_type);

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

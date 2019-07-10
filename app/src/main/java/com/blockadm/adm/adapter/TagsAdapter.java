package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.TagBeanDto;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/12.
 */

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.Viewholder> {



    private ArrayList<TagBeanDto> data;
    private Context context;

    public TagsAdapter(ArrayList<TagBeanDto> data, Context context) {

        this.data = data;
        this.context = context;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_tags, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        TagBeanDto  tagBeanDto = data.get(position);
        holder.tvTagname.setText(tagBeanDto.getName());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tagname)
        TextView tvTagname;

        public Viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

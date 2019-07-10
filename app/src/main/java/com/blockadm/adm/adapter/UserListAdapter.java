package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blockadm.adm.R;
import com.blockadm.common.bean.UserListBean;
import com.blockadm.common.comstomview.CustomLinearLayoutManager;
import com.blockadm.common.comstomview.RecycleViewDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/25.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {


    private Context context;
    private List<List<UserListBean>> records;


    public UserListAdapter(Context context, List<List<UserListBean>> records) {
        this.context = context;
        this.records = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        List<UserListBean> recordsBean = records.get(position);
        holder.rv.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, context.getResources().getColor(R.color.color_fff2f3f4)));

        FransListAdapter  fransListAdapter = new FransListAdapter(context,recordsBean);
        CustomLinearLayoutManager  linearLayoutManager = new CustomLinearLayoutManager(context);
        linearLayoutManager.setScrollEnabled(false);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rv.setLayoutManager(linearLayoutManager);
        holder.rv.setAdapter(fransListAdapter);

    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv)
        RecyclerView rv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

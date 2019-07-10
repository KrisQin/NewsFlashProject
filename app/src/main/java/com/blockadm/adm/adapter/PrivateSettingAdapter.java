package com.blockadm.adm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.PrivateListBean;
import com.blockadm.common.comstomview.NoScrollListView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/30.
 */

public class PrivateSettingAdapter extends RecyclerView.Adapter<PrivateSettingAdapter.ViewHolder> {


    private Context context;
    private ArrayList<PrivateListBean> privateListBeans;

    public PrivateSettingAdapter(ArrayList<PrivateListBean> privateListBeans, Context context) {
        this.context =context;
        this.privateListBeans = privateListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_private_setting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PrivateListBean  privateListBean  =    privateListBeans.get(position);
        holder.tvType.setText(privateListBean.getName());

        final PrivateSettingAdapter2   privateSettingAdapter2 = new PrivateSettingAdapter2(privateListBean.getList(),context);
        holder.nsl.setAdapter(privateSettingAdapter2);
        holder.nsl.setTag(privateListBean.getList());
        holder.nsl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final List<PrivateListBean.ListBean>  listBeans   = ( List<PrivateListBean.ListBean> ) parent.getTag();
                final PrivateListBean.ListBean  listBean =  listBeans.get(position);

                CommonModel.userSecretMark(listBean.getId(), new MyObserver<String>() {
                    @Override
                    public void onSuccess(BaseResponse t) {
                        for (int i = 0; i < listBeans.size(); i++) {
                            PrivateListBean.ListBean  listBean =  listBeans.get(i);
                            listBean.setIsSelect(0);
                        }
                        listBean.setIsSelect(1);
                        privateSettingAdapter2.notifyDataSetChanged();
                    }


                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return privateListBeans==null?0:privateListBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.nsl)
        NoScrollListView nsl;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

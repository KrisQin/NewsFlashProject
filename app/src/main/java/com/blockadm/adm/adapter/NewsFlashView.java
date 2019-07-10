package com.blockadm.adm.adapter;


import android.support.annotation.NonNull;

import com.blockadm.adm.R;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.wenld.multitypeadapter.base.MultiItemView;
import com.wenld.multitypeadapter.base.ViewHolder;

/**
 * Created by hp on 2019/1/9.
 */

public class NewsFlashView extends MultiItemView<NewsFlashBeanDto.RecordsBean> {

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.layout_list_ref_load1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull NewsFlashBeanDto.RecordsBean recordsBean, int i) {

    }
}


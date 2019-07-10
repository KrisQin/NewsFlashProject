package com.blockadm.adm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.blockadm.adm.Fragment.NewsFlashFragment;
import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;

/**
 * Created by Kris on 2019/7/10
 *
 * @Describe TODO { 快讯 }
 */
public class FastInfoTabActivity extends BaseComActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_info_tab);

        //状态变化时删除老的Fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.fragment_container, new NewsFlashFragment()); //将fragment添加到布局当中
        ft.commit(); //千万别忘记提交事务
    }


}

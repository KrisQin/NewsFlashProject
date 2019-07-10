package com.blockadm.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blockadm.common.utils.ContextUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Kris on 2019/5/5
 *
 * @Describe TODO {  }
 */
public abstract class LazyFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    public Activity mActivity;
    public Unbinder unbinder;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            initViews(view, savedInstanceState);
            initData();
        }

        ContextUtils.setActivity(getActivity());
        return view;
    }

    protected abstract int getLayoutId();

    protected void initViews(View view, @Nullable Bundle savedInstanceState) {
        isCreated = true;
        this.view = view;
        lazyLoad(this.view, savedInstanceState);
    }

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isCreated = false;
        hasLoaded = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.view = null;
    }

    private boolean hasLoaded = false;

    private boolean isCreated = false;

    private boolean isVisibleToUser = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;//注：关键步骤
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad(view, null);
    }

    private void lazyLoad(View view, Bundle savedInstanceState) {
        if (!isVisibleToUser || hasLoaded || !isCreated) {
            return;
        }
        lazyInitView(view, savedInstanceState);
        hasLoaded = true;//注：关键步骤，确保数据只加载一次
    }

    public abstract void lazyInitView(View view, Bundle savedInstanceState);


}
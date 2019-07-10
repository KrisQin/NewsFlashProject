package com.blockadm.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.TUtil;

/**
 * Created by hp on 2018/12/12.
 */

public abstract class BaseFragment  <P extends  BasePersenter,M extends BaseModel > extends Fragment implements IbaseView {


    public P mPersenter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setVM();
        ARouter.getInstance().inject(this);

        view = inflater.inflate(getLayoutId(),null,false);
        ContextUtils.setActivity(getActivity());
        return view;
    }

    protected boolean isVisibleToUser;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        L.d("setUserVisibleHint", "setUserVisibleHint, isVisibleToUser 111  = " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);

        L.d("setUserVisibleHint", "setUserVisibleHint, isVisibleToUser 222 = " + isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
//        // 如果还没有加载过数据 && 用户切换到了这个fragment
//        // 那就开始加载数据
//        if (!mHaveLoadData && isVisibleToUser) {
//            loadDataStart();
//            mHaveLoadData = true;
//        }
    }

    protected void loadDataStart() {

    }


    private void setVM() {
        mPersenter = TUtil.getT(this, 0);
        M model = TUtil.getT(this, 1);
        if (this instanceof IbaseView&&mPersenter!=null){
            mPersenter.addView(this,model);
        }
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        initView(rootView);
        L.d("setUserVisibleHint", "onViewCreated, isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            loadDataStart();
        }
    }

    protected abstract void initView(View view);

    public abstract int  getLayoutId() ;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPersenter!=null){
            mPersenter.destory();
        }

    }





    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showToast(Context context, String msg) {

    }

    @Override
    public void hideLoadDialog() {

    }


}

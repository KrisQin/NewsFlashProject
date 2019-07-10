package com.blockadm.common.base;

/**
 * Created by hp on 2018/12/11.
 */

public class BasePersenter <V  ,M >{

    public V mView;
    public M mModel;

    //加载View,建立连接
    public void addView(V v,M m) {
        this.mView = v;
        this.mModel = m;
    }

    //断开连接
    public void destory() {
        if (mView != null) {
            mView = null;
        }

        if (mModel !=null){
            mModel =null;
        }
    }


}

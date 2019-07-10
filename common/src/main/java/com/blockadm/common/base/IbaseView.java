package com.blockadm.common.base;

import android.content.Context;

/**
 */

public interface IbaseView<T> {
    public void showToast(Context context, String msg) ;

    public void showLoadDialog();

    public void hideLoadDialog() ;
}

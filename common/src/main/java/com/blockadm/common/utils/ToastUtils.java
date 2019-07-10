package com.blockadm.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yinqm on 2018/3/20.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(Utils.getContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

}

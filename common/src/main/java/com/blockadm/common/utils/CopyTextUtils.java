package com.blockadm.common.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Kris on 2019/5/16
 *
 * @Describe TODO { 复制文字 }
 */
public class CopyTextUtils {

    public static void copyText(Context context,String text) {
        ClipboardManager myClipboard = null;
        if (context != null) {
            myClipboard = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
        }

        if (myClipboard!=null){
            ClipData myClip = ClipData.newPlainText("text", text);
            myClipboard.setPrimaryClip(myClip);
            ToastUtils.showToast("已经复制到剪切板");
        }
    }
}

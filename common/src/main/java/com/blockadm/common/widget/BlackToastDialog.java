package com.blockadm.common.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.blockadm.common.R;
import com.blockadm.common.utils.ScreenUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 黑色提示Toast
 * Created by Administrator on 2017/11/23.
 */
public class BlackToastDialog {


    //    public static void showToastDialog(Context context, boolean isRewardSuccess) {
    //        showToastDialog(context,isRewardSuccess);
    //    }

    /**
     * 显示Toast
     *
     * @param context
     * @param isRewardSuccess
     */
    public static void showToastDialog(Context context, boolean isRewardSuccess) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.black_toast_dialog, null);

        LinearLayout layout = toastRoot.findViewById(R.id.layout);

        if (isRewardSuccess) {
            layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_reward_success));
        } else {
            layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_reward_fail));
        }

        int width = ScreenUtils.dip2px(context, 120);
        //设置控件的宽高
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
        layout.setLayoutParams(lp);

        //Toast的初始化
        Toast toastStart = new Toast(context);

        //使用自定义Toast 的时候有个坑，你在布局中设置宽高是无效的，必须在代码中动态设置，
        // 而且不能设置跟布局的宽高，必须设置第二级布局的LayoutParma。
        toastStart.setGravity(Gravity.CENTER, 0, 0);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);

        showMyToast(toastStart, 700);
    }

    /**
     * 设置Toast时间
     *
     * @param toast
     * @param cnt
     */
    private static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

}

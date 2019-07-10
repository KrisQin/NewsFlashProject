package com.blockadm.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.R;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.config.ComConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 2019/1/7.
 */

public class ContextUtils {
    private static Application baseApplication;
    private static AlertDialog alertDialog;

    public static void init(Application application) {
        baseApplication = application;
    }

    public static Application getBaseApplication() {
        return baseApplication;
    }

    private static Activity mActivity;

    public static void setActivity(Activity activity) {
        if (activity != null)
            ContextUtils.mActivity = activity;
    }

    public static void dismiss() {
//        if (alertDialog != null) {
//            alertDialog.dismiss();
//        }
    }

//    public static void showNoLoginDialog() {
//
//        try {
//            if (mActivity == null || mActivity.isFinishing()) {
//                return;
//            }
//
//            if (alertDialog == null) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.dialog_base);
//                alertDialog = builder.create();
//                alertDialog.getWindow().setGravity(Gravity.CENTER);
//                alertDialog.getWindow().setBackgroundDrawableResource(R.color.color_transparent);
//                alertDialog.setCanceledOnTouchOutside(false);//外部触摸不单独关闭
//                View view = View.inflate(mActivity, R.layout.layout_no_login, null);
//                Button bg = (Button) view.findViewById(R.id.bt_go_login);
//                ImageView ivCancle = (ImageView) view.findViewById(R.id.iv_cancel);
//                alertDialog.setView(view);
//                ivCancle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.cancel();
//                    }
//                });
//
//                bg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ARouter.getInstance().build(ARouterPathConfig.login_activity_path).withBoolean("is", false).navigation();
//                        alertDialog.dismiss();
//                    }
//                });
//
//            }
//
//            alertDialog.show();
//        }catch (Exception e) {
//            L.d("xxxs"," e: "+e.toString());
//        }
//
//
//
//
//
//    }


    public static void showNoLoginDialog(Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.dialog_base);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.color_transparent);
        alertDialog.setCanceledOnTouchOutside(false);//外部触摸不单独关闭
        View view = View.inflate(activity, R.layout.layout_no_login, null);
        Button bg = (Button) view.findViewById(R.id.bt_go_login);
        ImageView ivCancle = (ImageView) view.findViewById(R.id.iv_cancel);
        alertDialog.setView(view);
        alertDialog.show();
        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPathConfig.login_activity_path).withBoolean(
                        "is", false).navigation();
                alertDialog.dismiss();
            }
        });

    }
}

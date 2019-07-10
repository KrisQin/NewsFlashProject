package com.blockadm.adm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.AuthenticationComActivity;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.common.widget.CommonDialog;

/**
 * Created by Kris on 2019/6/5
 *
 * @Describe TODO {  }
 */
public class DialogUtils {

    /**
     * @param activity
     */
    public static void showRealNameAuthentLiveDialog(final Activity activity) {
        final MyComstomDialogView myComstomDialogView1 = new MyComstomDialogView(activity);
        myComstomDialogView1.setTvTitle("先去实名认证才能发布社群直播", View.VISIBLE).setRootBg(R.mipmap.boxbg2)
                .setChildMsg("", View.GONE)
                .setChildMsg2("", View.GONE)
                .setConcelMsg("", View.GONE)
                .setConfirmMsg("好的", View.VISIBLE)
                .setConfirmSize(6)
                .setCancelLisenner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).setConfirmLisenner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myComstomDialogView1.dismiss();
                Intent intent = new Intent(activity, AuthenticationComActivity.class);
                activity.startActivity(intent);
            }
        });
        myComstomDialogView1.show();
    }


    public static void showOpenLessonEmptyContentDialog(Activity activity) {
        CommonDialog mDownDialog = new CommonDialog.Builder(activity)
                .setTitle("请填写标题/直播类型/开课时间/上传封面/密码/价格")
                .setPositiveButton("我知道啦", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        mDownDialog.show();
    }

    public static void showSmallTimeDialog(Activity activity) {
        CommonDialog mDownDialog = new CommonDialog.Builder(activity)
                .setTitle("选择的该时间不符合开播条件")
                .setPositiveButton("重新选择", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        mDownDialog.show();
    }

}

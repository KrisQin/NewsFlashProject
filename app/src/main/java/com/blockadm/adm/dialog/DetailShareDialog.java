package com.blockadm.adm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.common.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/3/8.
 */

public class DetailShareDialog extends Dialog{

    @BindView(R.id.share_weibo)
    RelativeLayout shareWeibo;
    @BindView(R.id.view_share_weixin)
    RelativeLayout viewShareWeixin;
    @BindView(R.id.view_share_qq)
    RelativeLayout viewShareQq;
    @BindView(R.id.view_share_weixinfriend)
    RelativeLayout viewShareWeixinfriend;



    private  Context context;

    public DetailShareDialog(final Context context) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        setContentView(R.layout.dialog_detail_share);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(context));
        Window dialogWindow = getWindow();
      // dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
        initView();
    }

    private void initView() {
    }


    @OnClick({R.id.view_share_weixin, R.id.view_share_weixinfriend, R.id.view_share_qq, R.id.share_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.view_share_weixin:
                if (shareTypeListener!=null){
                    shareTypeListener.shareType(1);
                }

                break;
            case R.id.view_share_weixinfriend:
                if (shareTypeListener!=null){
                    shareTypeListener.shareType(2);
                }
                break;
            case R.id.view_share_qq:
                if (shareTypeListener!=null){
                    shareTypeListener.shareType(3);
                }
                break;
            case R.id.share_weibo:
                if (shareTypeListener!=null){
                    shareTypeListener.shareType(4);
                }

                break;
        }
    }

    private ShareTypeListener shareTypeListener;


    public interface  ShareTypeListener{
        void  shareType(int type);
    };

    public void setShareTypeListener(ShareTypeListener shareTypeListener) {
        this.shareTypeListener = shareTypeListener;
    }



}

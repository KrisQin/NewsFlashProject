package com.blockadm.adm.im_module.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.live.responseBean.LiveMsgInfo;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Kris on 2019/5/21
 *
 * @Describe TODO {  }
 */
public class ShowBigImageActivity extends BaseComActivity {

    public static final String Live_Msg_Info = "LiveMsgInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

//        LiveMsgInfo liveMsgInfo = (LiveMsgInfo) getIntent().getParcelableExtra(Live_Msg_Info);
        String url = getIntent().getStringExtra(Live_Msg_Info);

//        Uri uri = liveMsgInfo.getUri();

        ImageView iv_image = findViewById(R.id.iv_image);
        RelativeLayout layout_root = findViewById(R.id.layout_root);

        layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowBigImageActivity.this.finish();
            }
        });

        showDefaultLoadingDialog();
        Glide.with(this).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                       boolean isFirstResource) {
                dismissLoadingDialog();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model,
                                           Target<GlideDrawable> target,
                                           boolean isFromMemoryCache, boolean isFirstResource) {
                dismissLoadingDialog();
                return false;
            }
        }).into(iv_image);
    }
}

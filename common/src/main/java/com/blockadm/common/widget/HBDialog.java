package com.blockadm.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;

/**
 * Created by Kris on 2019/6/14
 *
 * @Describe TODO {  }
 */
public class HBDialog extends Dialog implements DialogInterface {

    public HBDialog(Context context) {
        super(context);

    }

    public HBDialog(Context context, int theme) {
        super(context, theme);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //		setCanceledOnTouchOutside(true);
    }


    public static class Builder {
        private Context mContext;
        private String mHeadImageUrl;
        private OnClickListener onClickListener;
        private OnClickListener onLookListener;
        private boolean isOverTime; // true:红包已过期

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setHeadImageUrl(String headImageUrl) {
            mHeadImageUrl = headImageUrl;
            return this;
        }

        public Builder setOverTime(boolean isOverTime) {
            this.isOverTime = isOverTime;
            return this;
        }


        public Builder setOnOpenClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder setOnLookClickListener(OnClickListener onClickListener) {
            this.onLookListener = onClickListener;
            return this;
        }

        /**
         * @param
         * @return
         * @Description 给dialog设置内容
         */
        public HBDialog create() {


            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final HBDialog dialog = new HBDialog(mContext,
                    R.style.dialog_base);
            // 设置样式
            View layout = inflater.inflate(R.layout.dialog_hb, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(width / 5 * 4,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            CircleImageView iv_head_image = layout.findViewById(R.id.iv_head_image);
            ImageView iv_open = layout.findViewById(R.id.iv_open);
            TextView tv_desc = layout.findViewById(R.id.tv_desc);
            ImageView iv_close = layout.findViewById(R.id.iv_close);
            TextView tv_look = layout.findViewById(R.id.tv_look);

            if (isOverTime) {
                tv_look.setVisibility(View.VISIBLE);
                iv_open.setVisibility(View.INVISIBLE);
                iv_close.setVisibility(View.INVISIBLE);
                tv_desc.setText("红包已过期");
            } else {
                tv_look.setVisibility(View.INVISIBLE);
                iv_open.setVisibility(View.VISIBLE);
                tv_desc.setText("恭喜发财，大吉大利");
            }

            if (StringUtils.isNotEmpty(mHeadImageUrl)) {
                Glide.with(mContext).load(mHeadImageUrl).error(R.mipmap.picture_default).into(iv_head_image);
            }

            iv_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null && !isOverTime)
                        onClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });

            tv_look.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLookListener != null && isOverTime)
                        onLookListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                }
            });

            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isOverTime)
                        dialog.dismiss();
                }
            });

            dialog.setContentView(layout);

            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);


            return dialog;
        }


    }
}

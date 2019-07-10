package com.blockadm.common.im.widget;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kris on 2019/5/24
 *
 * @Describe TODO {  }
 */
public class ToastConstraintLayout extends ConstraintLayout {


    private List<TextView> mTextViewList = new ArrayList<>();
    private List<CircleImageView> mImageViewList = new ArrayList<>();
    private List<LinearLayout> mLinearLayoutList = new ArrayList<>();

    public ToastConstraintLayout(Context context) {
        super(context);
        init();
    }

    public ToastConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.taost_text_layout, this);
        TextView mTv_content1 = findViewById(R.id.tv_content1);
        TextView mTv_content2 = findViewById(R.id.tv_content2);
        TextView mTv_content3 = findViewById(R.id.tv_content3);
        TextView mTv_content4 = findViewById(R.id.tv_content4);

        mTextViewList.add(mTv_content1);
        mTextViewList.add(mTv_content2);
        mTextViewList.add(mTv_content3);
        mTextViewList.add(mTv_content4);

        CircleImageView mIv_head_image1 = findViewById(R.id.iv_head_image1);
        CircleImageView mIv_head_image2 = findViewById(R.id.iv_head_image2);
        CircleImageView mIv_head_image3 = findViewById(R.id.iv_head_image3);
        CircleImageView mIv_head_image4 = findViewById(R.id.iv_head_image4);

        mImageViewList.add(mIv_head_image1);
        mImageViewList.add(mIv_head_image2);
        mImageViewList.add(mIv_head_image3);
        mImageViewList.add(mIv_head_image4);

        LinearLayout layout1 = findViewById(R.id.layout1);
        LinearLayout layout2 = findViewById(R.id.layout2);
        LinearLayout layout3 = findViewById(R.id.layout3);
        LinearLayout layout4 = findViewById(R.id.layout4);

        mLinearLayoutList.add(layout1);
        mLinearLayoutList.add(layout2);
        mLinearLayoutList.add(layout3);
        mLinearLayoutList.add(layout4);

    }

    private List<MessageInfo> mInfoList = new ArrayList<>();
    public void showToastMsg(List<MessageInfo> msgList,Context mContext) {

        try {
            if (ListUtils.isNotEmpty(msgList)) {
                mInfoList.clear();

                int count = msgList.size() >= 4 ? 4 : msgList.size();

                if (count < 4) { // 说明没有4条信息
                    for (int i = 0; i < 4; i++) {
                        mLinearLayoutList.get(i).setVisibility(GONE);
                    }
                }

                for (int i = msgList.size() - 1; i >= (msgList.size() - count); i--) {
                    MessageInfo info = msgList.get(i);
                    mInfoList.add(info);
                }

                L.d("XTT","mInfoList: "+mInfoList.size()+" ; count: "+count);

                for (int i = 0; i < mInfoList.size(); i++) {
                    mLinearLayoutList.get(i).setVisibility(VISIBLE);

                    MessageInfo info = mInfoList.get(i);
                    mTextViewList.get(i).setText(info.getContent());
                    Glide.with(mContext).load(info.getHeadImageUrl()).into(mImageViewList.get(i));
                }


            }
        }catch (Exception e) {
            L.d("XTT","Exception e: "+e.toString());
        }

    }
}

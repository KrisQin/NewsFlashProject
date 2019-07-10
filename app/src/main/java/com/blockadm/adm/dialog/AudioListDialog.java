package com.blockadm.adm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.AudioContentComActivity;
import com.blockadm.adm.activity.PictrueContentComActivity;
import com.blockadm.adm.adapter.NewsLessonslistAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.RecycleViewDivider;
import com.blockadm.common.utils.ScreenUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/9/19.
 */
public class AudioListDialog extends Dialog {


    @BindView(R.id.erv)
    EmptyRecyclerView erv;
    @BindView(R.id.tv_close)
    TextView tvClose;
    private int isSeeStatus;
    private ArrayList<RecordsBean> audioList;

    private Activity context;


    public AudioListDialog(final Activity context, ArrayList<RecordsBean> audioList, int isSeeStatus) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.audioList = audioList;
        this.isSeeStatus = isSeeStatus;
        setContentView(R.layout.dialog_audiolist);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = ScreenUtils.getScreenWidth(context);
        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
        initView();
    }


    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        erv.setLayoutManager(linearLayoutManager);
        erv.addItemDecoration(new RecycleViewDivider(
                context, LinearLayoutManager.HORIZONTAL, 1, context.getResources().getColor(R.color.color_97979F)));
        NewsLessonslistAdapter newsLessonslistAdapter = new NewsLessonslistAdapter(audioList, context, isSeeStatus);
        erv.setAdapter(newsLessonslistAdapter);

        newsLessonslistAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (context instanceof AudioContentComActivity) {
                    AudioContentComActivity audioContentActivity = (AudioContentComActivity) context;
                    audioContentActivity.palySelectedPosition(position);
                } else if (context instanceof PictrueContentComActivity) {
                    PictrueContentComActivity pictrueContentActivity = (PictrueContentComActivity) context;
                    pictrueContentActivity.palySelectedPosition(position);
                }

                dismiss();

            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}

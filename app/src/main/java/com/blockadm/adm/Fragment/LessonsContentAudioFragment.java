package com.blockadm.adm.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.activity.AudioContentComActivity;
import com.blockadm.adm.activity.ColumnOneDetailComActivity;
import com.blockadm.adm.activity.PictrueContentComActivity;
import com.blockadm.adm.adapter.NewsLessonslistAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.PalyDetailDto;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.SharedpfTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hp on 2019/1/22.
 */

public class LessonsContentAudioFragment extends BaseComFragment {
    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;

    @BindView(R.id.tv_sort_player)
    TextView tvSortPlayer;

    //    @BindView(R.id.tv_sort)
    //    TextView tvSort;
    Unbinder unbinder;
    @BindView(R.id.tv_reverse_sort)
    TextView tvReverseSort;
    @BindView(R.id.tv_order_sort)
    TextView tvOrderSort;
    Unbinder unbinder1;
    private ArrayList<RecordsBean> records;
    private int isSeeStatus;
    private boolean isReverse = false;
    private RecordsBean recordsBeanNow;
    private Timer timer;
    private MyTimerTask myTimerTask;


    @Override
    public int getLayoutId() {
        return R.layout.frag_lesson_content_state;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

//        tvSort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (records != null && records.size() > 0) {
//                    Collections.reverse(records);
//                    if (isReverse) {
//                        isReverse = false;
//                        Drawable dra = getResources().getDrawable(R.mipmap.order_default);
//                        dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
//                        tvSort.setCompoundDrawables(dra, null, null, null);
//                        tvSort.setText("倒序");
//                    } else {
//                        isReverse = true;
//                        Drawable dra = getResources().getDrawable(R.mipmap.order_go);
//                        dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
//                        tvSort.setCompoundDrawables(dra, null, null, null);
//                        tvSort.setText("顺序");
//
//                    }
//                    setDataToRecycleview();
//                }
//            }
//        });

        tvReverseSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (records != null && records.size() > 0) {
                    if (!isReverse) {
                        Collections.reverse(records);
                        isReverse = true;
                        setDataToRecycleview();
                    }

                }
            }
        });
        tvOrderSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (records != null && records.size() > 0) {
                    if (isReverse) {
                        Collections.reverse(records);
                        isReverse = false;
                        setDataToRecycleview();
                    }
                }
            }
        });

        tvSortPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortPlayerList();
            }
        });

        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            //打印当前name的内容

            if (isExit()) {
                if (MainApp.getPlayService1() != null && MainApp.getPlayService1().getCurrentPosition() >= (MainApp.getPlayService1().getDuration() - 300)) {
                    RecordsBean recordsBean = MainApp.getRecordsBean();
                    int position = recordsBean.getPosition();
                    if (records != null && records.size() > 0) {
                        if (position == records.size() - 1) {
                            return;
                        }
                        recordsBeanNow = records.get(position + 1);
                        if (isSeeStatus == 1 && recordsBeanNow.getAccessStatus() != 0) {
                            recordsBeanNow = recordsBean;
                            MainApp.setRecordsBean(recordsBean);
                            if (handler != null) {
                                handler.sendEmptyMessage(1002);
                            }
                        } else {
                            loadDetail(recordsBeanNow);
                        }

                    }
                }

            }
            if (handler != null) {
                handler.sendEmptyMessage(1001);
            }

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.what == 1001 && getActivity() != null) {
                    if (MainApp.getPlayService1() != null && MainApp.getPlayService1().isPlaying()) {
                        Drawable dra = getResources().getDrawable(R.mipmap.radio05_all_pause);
                        dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                        tvSortPlayer.setCompoundDrawables(dra, null, null, null);
                    } else {
                        Drawable dra = getResources().getDrawable(R.mipmap.radio04_all);
                        dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                        tvSortPlayer.setCompoundDrawables(dra, null, null, null);
                    }
                } else if (msg.what == 1002) {
                    // Toast.makeText(getActivity(),"请先购买，再体验！！！",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }


        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeMessages(1001);
            handler.removeMessages(1002);
            handler = null;

        }
        if (timer != null) {
            timer.cancel();
        }
        unbinder1.unbind();


    }


    private boolean isExit() {
        RecordsBean recordsBean = MainApp.getRecordsBean();
        if (recordsBean != null) {
            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {
                    RecordsBean recordsBean1 = records.get(i);
                    recordsBean1.setPosition(i);
                    if (recordsBean1.getId() == recordsBean.getId()) {
                        return true;
                    }
                }
            }
        } else {
            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {
                    RecordsBean recordsBean1 = records.get(i);
                    recordsBean1.setPosition(i);
                }
            }
        }

        return false;
    }

    private void sortPlayerList() {
        String token =
                (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            ARouter.getInstance().build(ARouterPathConfig.login_activity_path).withBoolean("is",
                    false).navigation();
        } else {
            if (records != null && records.size() > 0) {

                if (isExit() && MainApp.getPlayService1() != null && MainApp.getPlayService1().isPlaying()) {
                    Drawable dra = getResources().getDrawable(R.mipmap.radio05_all_pause);
                    dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                    tvSortPlayer.setCompoundDrawables(dra, null, null, null);
                    MainApp.getPlayService1().pause();
                } else if (isExit() && MainApp.getPlayService1() != null && !MainApp.getPlayService1().isPlaying()) {
                    Drawable dra = getResources().getDrawable(R.mipmap.radio04_all);
                    dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                    tvSortPlayer.setCompoundDrawables(dra, null, null, null);
                    MainApp.getPlayService1().start();
                } else if (!isExit() && MainApp.getPlayService1() != null && MainApp.getPlayService1().isPlaying()) {
                    Drawable dra = getResources().getDrawable(R.mipmap.radio04_all);
                    dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                    tvSortPlayer.setCompoundDrawables(dra, null, null, null);
                    MainApp.getPlayService1().start();
                } else {
                    Drawable dra = getResources().getDrawable(R.mipmap.radio04_all);
                    dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                    tvSortPlayer.setCompoundDrawables(dra, null, null, null);
                    recordsBeanNow = records.get(0);
                    loadDetail(recordsBeanNow);
                }
            }

        }
    }

    private void loadDetail(RecordsBean recordsBean) {
        if (getActivity() != null) {
            recordsBean.setNowPlay(true);
            MainApp.setRecordsBean(recordsBeanNow);
            CommonModel.findNewsLessonsPlayDetail(new MyObserver<PalyDetailDto>() {
                @Override
                public void onSuccess(BaseResponse<PalyDetailDto> t) {
                    if (t.getCode() == 0) {
                        PalyDetailDto palyDetailDto = t.getData();
                        ColumnOneDetailComActivity columnOneDetailActivity =
                                (ColumnOneDetailComActivity) getActivity();
                        columnOneDetailActivity.playList(palyDetailDto);
                        setDataToRecycleview();
                    }
                }
            }, recordsBean.getId(), ScreenUtils.getScreenWidth(getActivity()), "1");
        }

    }


    public void updateView(BaseResponse<ArrayList<RecordsBean>> lessonsDTOBaseResponse,
                           int isSeeStatus) {
        records = lessonsDTOBaseResponse.getData();
        Collections.reverse(records);
        this.isSeeStatus = isSeeStatus;
        setDataToRecycleview();
    }

    private void setDataToRecycleview() {

        L.d("  ------------------------- >   records: " + records + " ; erv: " + erv + " ; " +
                "isSeeStatus: " + isSeeStatus);
        if (records != null && erv != null) {
            RecordsBean recordsBean = MainApp.getRecordsBean();
            if (recordsBean != null) {
                if (records != null && records.size() > 0) {
                    for (int i = 0; i < records.size(); i++) {
                        RecordsBean recordsBean1 = records.get(i);
                        recordsBean1.setNowPlay(false);
                        if (recordsBean1.getId() == recordsBean.getId()) {
                            recordsBean1.setNowPlay(true);
                        }
                    }
                }
            }
            erv.setHasFixedSize(true);
            erv.setNestedScrollingEnabled(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            erv.setLayoutManager(linearLayoutManager);
            NewsLessonslistAdapter newsLessonslistAdapter = new NewsLessonslistAdapter(records,
                    getActivity(), isSeeStatus);
            erv.setAdapter(newsLessonslistAdapter);
            newsLessonslistAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    String token =
                            (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        ARouter.getInstance().build(ARouterPathConfig.login_activity_path).withBoolean("is", false).navigation();
                    } else {
                        RecordsBean recordsBean = records.get(position);
                        /*
                         *     isSeeStatus : 0:免费，1：未付费、2:已付费   和  课程列表    accessStatus  课件类型  0
                         *     免费、1：付费
                         *
                         * */
                        if (isSeeStatus == 2 || isSeeStatus == 0) {
                            if (recordsBean.getContentType() == 1 || recordsBean.getContentType() == 3) {
                                Intent intent = new Intent(getActivity(),
                                        AudioContentComActivity.class);
                                intent.putExtra("isSeeStatus", isSeeStatus);
                                intent.putExtra("recordsBean", recordsBean);
                                startActivityForResult(intent, REQUESTCODE_LESSONS);
                            } else if (recordsBean.getContentType() == 2) {
                                Intent intent = new Intent(getActivity(),
                                        PictrueContentComActivity.class);
                                intent.putExtra("isSeeStatus", isSeeStatus);
                                intent.putExtra("id", recordsBean.getId());
                                startActivityForResult(intent, REQUESTCODE_LESSONS);
                            }
                            return;
                        }
                        if (isSeeStatus == 1) {
                            if (recordsBean.getAccessStatus() == 0) {
                                if (recordsBean.getContentType() == 1 || recordsBean.getContentType() == 3) {
                                    Intent intent = new Intent(getActivity(),
                                            AudioContentComActivity.class);
                                    intent.putExtra("isSeeStatus", isSeeStatus);
                                    intent.putExtra("recordsBean", recordsBean);
                                    startActivityForResult(intent, REQUESTCODE_LESSONS);
                                } else if (recordsBean.getContentType() == 2) {
                                    Intent intent = new Intent(getActivity(),
                                            PictrueContentComActivity.class);
                                    intent.putExtra("isSeeStatus", isSeeStatus);
                                    intent.putExtra("id", recordsBean.getId());
                                    startActivityForResult(intent, REQUESTCODE_LESSONS);
                                }
                                return;
                            } else {
                                Toast.makeText(getActivity(), "请先购买，再体验！！！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "请先购买，再体验！！！", Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });

            try {
                ACache.get(getContext()).put(ConstantUtils.AUDIO_LIST, records);
            } catch (Exception e) {

            }

        }
    }

    private final int REQUESTCODE_LESSONS = 1133;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_LESSONS) {
            recordsBeanNow = MainApp.getRecordsBean();
            if (MainApp.getPlayService1() != null && MainApp.getPlayService1().isPlaying()) {
                MainApp.getPlayService1().start();
                setDataToRecycleview();
            }
        }
    }
}

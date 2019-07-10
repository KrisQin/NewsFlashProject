package com.blockadm.adm.im_module.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blockadm.adm.utils.DialogUtils;
import com.blockadm.adm.R;
import com.blockadm.adm.im_module.adapter.CommunityManagerAdapter;
import com.blockadm.adm.im_module.call.CommunityManagerCallback;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseRefreshActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.live.responseBean.LiveListInfo;
import com.blockadm.common.bean.live.responseBean.LiveOpenInfo;
import com.blockadm.common.bean.live.responseBean.LiveRecordsInfo;
import com.blockadm.common.bean.params.AddNewsLiveLessonsNoticeParams;
import com.blockadm.common.bean.params.NewsLiveLessonsParams;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.dialog.AnnouncementDialog;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.CopyTextUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.T;
import com.blockadm.common.utils.TimeUtils;
import com.blockadm.common.widget.ComListPopupWindow;
import com.blockadm.common.widget.CommonDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kris on 2019/5/16
 *
 * @Describe TODO { 社群管理 }
 */
public class CommunityListManagerActivity extends BaseRefreshActivity {

    private ArrayList<LiveRecordsInfo> mLiveRecordsList;
    private CommunityManagerAdapter mRecyclerAdapter;

    private String available = "0"; //"0：使用中 、1：已停用"
    private CommonDialog mDownDialog;

    private final String OPEN = "0"; //"0：使用中 、1：已停用",
    private final String CLOSE = "1";
    public static final String member_Id = "memberId";
    private LiveRecordsInfo mCurrentInfo;
    private ComListPopupWindow mPopupWindow;
    private ArrayList<String> mPopList;

    private AnnouncementDialog mAnnouncementDialog;
    private int mCredentialsState = -99;
    private RelativeLayout mLayout_buy_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomView(R.layout.activity_community_manager);
        setTitle("社群管理");
        setRightText("创建新课程");

        mLiveRecordsList = new ArrayList<>();
        mPopList = new ArrayList<>();


        mLayout_buy_history = findContentViewById(R.id.layout_buy_history);
        RecyclerView recyclerView = findContentViewById(R.id.recycler_view);

        mRecyclerAdapter = new CommunityManagerAdapter(mLiveRecordsList, this, mCallback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRecyclerAdapter);

        mRecyclerAdapter.setOnClickItemListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LiveRecordsInfo liveRecordsInfo = mLiveRecordsList.get(position);
                Intent intent = new Intent(CommunityListManagerActivity.this,
                        LiveDetailActivity.class);
                intent.putExtra(LiveDetailActivity.LIVE_ID, liveRecordsInfo.getId());
                intent.putExtra(LiveDetailActivity.TITLE, liveRecordsInfo.getTitle());
                startActivity(intent);
            }
        });

        mLayout_buy_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CommunityListManagerActivity.this,CommunityBuyHistoryActivity.class));
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        mPageNum = 1;
        queryNewsLiveLessons(false);
        getUserData();
    }

    private void getUserData() {
        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                mCredentialsState = userInfo.getCredentialsState();
                if (isClick && mCredentialsState == 0) {
                    showRealNameAuthentDialog();
                }
                isClick = false;
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }

    private boolean isClick = false;
    @Override
    protected void clickTitleRight() {

        String token =
                (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            ContextUtils.showNoLoginDialog(this);
            return;
        }

        isClick = true;
        if (mCredentialsState != -99) {
            if (mCredentialsState == 0) {
                showRealNameAuthentDialog();
                return;
            }
        }else {
            getUserData();
            return;
        }

        Intent intent = new Intent(this, OpenLessonsActivity.class);
        intent.putExtra(OpenLessonsActivity.COM_FROM_CommunityManagerActivity, true);
        startActivityForResult(intent, 101);

    }

    private void showRealNameAuthentDialog() {
        DialogUtils.showRealNameAuthentLiveDialog(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            mLiveRecordsList.clear();
            mPageNum = 1;
            queryNewsLiveLessons(false);
        }
    }

    private void showPupW(View view) {

        mPopupWindow = new ComListPopupWindow(this, mPopList, new ComListPopupWindow.OnItemClick() {
            @Override
            public void itemClick(int position) {
                if (position == 0) {
                    announcement();
                } else if (position == 1) {
                    startOrOverLesson();
                }
            }
        });

        mPopupWindow.showPopAtScreenRight(view, ScreenUtils.dip2px(this, -13), 0);
    }

    CommunityManagerCallback mCallback = new CommunityManagerCallback() {
        @Override
        public void clickDianView(View view, int position) { //点击三个点
            mCurrentInfo = mLiveRecordsList.get(position);
            mPopList.clear();
            mPopList.add("发布公告");
            if (mCurrentInfo.getLiveStatus() == 2) { //已结束
                mPopList.add("重新开始");
            } else {
                mPopList.add("结束直播");
            }
            showPupW(view);
        }

        @Override
        public void clickDown(int position) { //已下架
            showDownDialog();
        }

    };

    /**
     * 结束直播或者重新开始
     */
    private void startOrOverLesson() {
        if (mCurrentInfo.getLiveStatus() == 2) { //当前已结束，去重新开始


            showDateTimeDialog();
        } else {

            overLesson();
        }

    }

    /**
     * 结束直播
     */
    private void overLesson() {

        showDefaultLoadingDialog();

        CommonModel.operateNewsLiveLessonStatus(mCurrentInfo.getId(), 4, new MyObserver<LiveOpenInfo>() {
            @Override
            public void onSuccess(BaseResponse<LiveOpenInfo> t) {
                dismissLoadingDialog();

                if (t != null) {
                    T.showShort(CommunityListManagerActivity.this, t.getMsg());
                }

                if (t.isSuccess()) {
                    mPageNum = 1;
                    queryNewsLiveLessons(false);
                }else {
                    T.showShort(CommunityListManagerActivity.this,t.getMsg());
                }

            }
        });

    }

    /**
     * 重新开始
     */
    private void repeatStartLesson() {
        showDefaultLoadingDialog();
        CommonModel.updateOpenLessonsTime(mCurrentInfo.getId(), resultTime, new MyObserver<LiveOpenInfo>() {
            @Override
            public void onSuccess(BaseResponse<LiveOpenInfo> t) {
                dismissLoadingDialog();
                if (t != null) {
                    T.showShort(CommunityListManagerActivity.this, t.getMsg());
                }

                if (t.isSuccess()) {
                    mPageNum = 1;
                    queryNewsLiveLessons(false);
                }else {
                    T.showShort(CommunityListManagerActivity.this,t.getMsg());
                }
            }
        });
    }


    private TimePickerView dateTimeDialog, dateMinuteDialog;
    private String dateTimeStr = "";
    private String minuteTimeStr = "";
    private String resultTime = "";

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private String getMinuteTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    /**
     * 选择年月日
     *
     * @return
     */
    private void showDateTimeDialog() { //Dialog 模式下，在底部弹出
        if (dateTimeDialog == null) {
            dateTimeDialog = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(final Date date, View v) {
                    dateTimeStr = getTime(date);
                    L.d("xxx",
                            " dateTimeStr : " + dateTimeStr + " ; minuteTimeStr : " + minuteTimeStr);
                    dateTimeDialog.dismiss();
                    showMinuteTimeDialog();

                }
            })
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                    .build();

            Dialog mDialog = dateTimeDialog.getDialog();
            if (mDialog != null) {

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM);

                params.leftMargin = 0;
                params.rightMargin = 0;
                dateTimeDialog.getDialogContainerLayout().setLayoutParams(params);

                Window dialogWindow = mDialog.getWindow();
                if (dialogWindow != null) {
                    dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                    dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                    dialogWindow.setDimAmount(0.1f);
                }
            }
        }

        dateTimeDialog.show();

    }

    private void showSmallTimeDialog() {

        DialogUtils.showSmallTimeDialog(this);
    }

    /**
     * 选择时分
     *
     * @return
     */
    private void showMinuteTimeDialog() { //Dialog 模式下，在底部弹出

        if (dateMinuteDialog == null) {
            dateMinuteDialog = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(final Date date, View v) {
                    minuteTimeStr = getMinuteTime(date);
                    dateMinuteDialog.dismiss();

                    if (TimeUtils.smallThenCurrentTime(dateTimeStr,minuteTimeStr)) {
                        showSmallTimeDialog();
                    }else {
                        resultTime = dateTimeStr + " " + minuteTimeStr+":00";

                        repeatStartLesson();
                    }

                    L.d("xxx",
                            " dateTimeStr : " + dateTimeStr + " ; minuteTimeStr : " + minuteTimeStr);

                }
            })
                    .setType(new boolean[]{false, false, false, true, true, false})
                    .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                    .build();

            Dialog mDialog = dateMinuteDialog.getDialog();
            if (mDialog != null) {

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM);

                params.leftMargin = 0;
                params.rightMargin = 0;
                dateMinuteDialog.getDialogContainerLayout().setLayoutParams(params);

                Window dialogWindow = mDialog.getWindow();
                if (dialogWindow != null) {
                    dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                    dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                    dialogWindow.setDimAmount(0.1f);
                }
            }
        }

        dateMinuteDialog.show();

    }

    /**
     * 发布公告
     */
    private void announcement() {

        if (mAnnouncementDialog == null) {
            AnnouncementDialog.Builder builder = new AnnouncementDialog.Builder(this);
            builder.setOnSubmitCallback(new AnnouncementDialog.EditCallback() {
                @Override
                public void callback(String text) {
                    if (text.length() > 30) {
                        T.showShort(CommunityListManagerActivity.this, "内容超出限制，请重新输入");
                    } else {
                        addNewsLiveLessonsNotice(text);
                    }
                }
            });
            mAnnouncementDialog = builder.onCreate();
        }

        mAnnouncementDialog.show();
    }

    private void addNewsLiveLessonsNotice(String noticeContent) {

        showDefaultLoadingDialog();
        int newsLiveLessonsId = mCurrentInfo.getId();

        String json = GsonUtil.GsonString(new AddNewsLiveLessonsNoticeParams(available,
                newsLiveLessonsId, noticeContent));

        CommonModel.addNewsLiveLessonsNotice(json, new MyObserver() {
            @Override
            public void onSuccess(BaseResponse t) {

                T.showShort(CommunityListManagerActivity.this, "发布成功");
                dismissLoadingDialog();

                KeyboardUtils.hideSoftInput(CommunityListManagerActivity.this);
                mAnnouncementDialog.dismiss();

                mPageNum = 1;
                queryNewsLiveLessons(false);
            }

        });
    }

    private void showDownDialog() {
        if (mDownDialog == null) {
            mDownDialog = new CommonDialog.Builder(CommunityListManagerActivity.this)
                    .setTitle("您的直播间已经被下架，具体可咨询客服")
                    .setMessage("客服微信号：BlockADM001")
                    .setPositiveButton("复制微信号", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CopyTextUtils.copyText(CommunityListManagerActivity.this,
                                    "BlockADM001");
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
        }
        mDownDialog.show();
    }

    @Override
    protected void refreshData() {
        queryNewsLiveLessons(true);
    }

    private void queryNewsLiveLessons(final boolean isRefresh) {
        if (!isRefresh) {
            showDefaultLoadingDialog();
        }

        String jsonString = GsonUtil.GsonString(new NewsLiveLessonsParams("", mPageNum, mPageSize,
                null, ""));
        L.d("jsonString: " + jsonString);
        CommonModel.newsLiveLessons(jsonString, new ComObserver<LiveListInfo>() {

            @Override
            public void onSuccess(BaseResponse<LiveListInfo> response, String errorMsg) {
                if (!isRefresh) {
                    dismissLoadingDialog();
                }

                finishRefresh();


                if (response.getData() != null && response.getData().getRecords().size() < mPageSize) {
                    isCanLoadMore = false;
                }

                if (isRefresh || mPageNum == 1) {
                    mLiveRecordsList.clear();
                    if (response.getData() != null)
                        mLiveRecordsList.addAll(response.getData().getRecords());
                    mRecyclerAdapter.notifyDataSetChanged();
                } else {
                    if (response.getData() != null)
                        mLiveRecordsList.addAll(response.getData().getRecords());
                    mRecyclerAdapter.notifyDataSetChanged();
                }

                showEmptyView(ListUtils.isEmpty(mLiveRecordsList));
            }
        });

    }


}

package com.blockadm.adm.im_module.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.adapter.LiveManageAdapter;
import com.blockadm.adm.im_module.utils.ShareDialogUtils;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseTitleActivity;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.bean.live.responseBean.LiveManageInfo;
import com.blockadm.common.bean.live.responseBean.LiveMsgInfo;
import com.blockadm.common.call.TitleLeftClickListener;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.CopyTextUtils;
import com.blockadm.common.utils.GlideTools;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;
import com.blockadm.common.widget.ComListPopupWindow;
import com.blockadm.common.widget.CommonDialog;

import org.litepal.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/5/17
 *
 * @Describe TODO { 直播间管理员界面 }
 */
public class LiveManagerActivity extends BaseTitleActivity {

    private CircleImageView mIv_head_image;
    private TextView mTv_name;
    private TextView mTv_text;
    private Button mBt_submit;
    private ListView mListview;
    private Button mBt_add_manage;
    private LiveManageAdapter mAdapter;
    private List<LiveManageInfo> mInfoList;

    public static final String LIVE_ID = "id";
    public static final String Live_Detail_Info = "Live_Detail_Info";
    private ComListPopupWindow mPopupWindow;
    private int mNewsLiveLessonsId;
    public LiveDetailInfo mLiveDetailInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomView(R.layout.activity_live_manager);
        setTitle("直播间管理员");



        mLiveDetailInfo = (LiveDetailInfo) getIntent().getSerializableExtra(Live_Detail_Info);
        if (mLiveDetailInfo == null) {
            mLiveDetailInfo = new LiveDetailInfo();
        }

        mIv_head_image = findContentViewById(R.id.iv_head_image);
        mBt_add_manage = findContentViewById(R.id.bt_add_manage);
        mTv_name = findContentViewById(R.id.tv_name);
        mListview = findContentViewById(R.id.listview);
        mTv_text = findContentViewById(R.id.tv_text);
        mBt_submit = findContentViewById(R.id.bt_submit);
        mBt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        mBt_add_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addManage();
            }
        });


        mTv_name.setText(mLiveDetailInfo.getNickName());
        GlideTools.setImgByGlide(this, mLiveDetailInfo.getIcon(),
                R.drawable.picture_default, mIv_head_image);

        mInfoList = new ArrayList<>();
        mAdapter = new LiveManageAdapter(this, mInfoList, new LiveManageAdapter.ClickDianCallback() {
            @Override
            public void click(View v,int position) {
                showPop(v,position);
            }
        });
        mListview.setAdapter(mAdapter);

        mNewsLiveLessonsId = getIntent().getIntExtra(LIVE_ID, -1);
        findAllManagerListByNewsLiveLessonsId(mNewsLiveLessonsId +"");
    }


    private void addManage() {

        if (mInfoList.size() >= 5) {
            T.showShort(this,"最多可添加5个管理员");
            return;
        }

        showDefaultLoadingDialog();
        CommonModel.getAddLiveManagerUrl(mNewsLiveLessonsId, new MyObserver<String>() {
            @Override
            public void onSuccess(BaseResponse<String> t) {
                dismissLoadingDialog();
                if (t.isSuccess()) {
                    if (StringUtils.isNotEmpty(t.getData())) {
                        share(t.getData());
                    }else {
                        T.showShort(LiveManagerActivity.this,"分享链接为空");
                    }

                }else {
                    T.showShort(LiveManagerActivity.this,t.getMsg());
                }
            }
        });
    }

    private void showPop(View view, final int position) {

        if (mPopupWindow == null) {
            List<String> mList = new ArrayList<>();
            mList.add("移除管理员");
            mPopupWindow = new ComListPopupWindow(this, mList, new ComListPopupWindow.OnItemClick() {
                @Override
                public void itemClick(int index) {
                    showRemoveSure(position);

                }
            });
        }
        mPopupWindow.showPopAtScreenRight(view, ScreenUtils.dip2px(this,-13),0);


    }


    CommonDialog mDownDialog;
    private void showRemoveSure(final int position) {
        if (mDownDialog == null) {
            mDownDialog = new CommonDialog.Builder(this)
                    .setTitle("确定移除该管理员吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addOrRemoveManager(position,true);
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

    /**
     *
     * @param position
     * @param isRemove
     */
    private void addOrRemoveManager(final int position, final boolean isRemove) {
        showDefaultLoadingDialog();

        LiveManageInfo info = mInfoList.get(position);

        int choose = 0; //0:添加、1：移除
        if (isRemove) {
            choose = 1;
        }

        CommonModel.addOrRemoveManager(info.getNewsLiveLessonsId(),
                info.getManagerMemberId(), choose, new MyObserver() {
            @Override
            public void onSuccess(BaseResponse t) {

                dismissLoadingDialog();
                if (t.isSuccess())  {
                    if (isRemove) {
                        mInfoList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                }else {
                    T.showShort(LiveManagerActivity.this,t.getMsg());
                }
            }

        });
    }

    private void share(String shareUrl) {
        ShareDialogUtils.shareManage(this,mLiveDetailInfo.getCoverImgs(),mLiveDetailInfo.getTitle(),"",shareUrl);
    }

    private void findAllManagerListByNewsLiveLessonsId(String newsLiveLessonsId) {
        showDefaultLoadingDialog();
        CommonModel.findAllManagerListByNewsLiveLessonsId(newsLiveLessonsId, new MyObserver<List<LiveManageInfo>>() {
            @Override
            public void onSuccess(BaseResponse<List<LiveManageInfo>> response) {
                dismissLoadingDialog();
                mInfoList.addAll(response.getData());
                showLayout(ListUtils.isEmpty(mInfoList));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void submit() {

        addManage();
    }

    private void showLayout(boolean isEmptyManage) {
        if (isEmptyManage) {
            mBt_add_manage.setVisibility(View.GONE);
            mListview.setVisibility(View.GONE);
            mBt_submit.setVisibility(View.VISIBLE);
            mTv_text.setVisibility(View.VISIBLE);
        }else {
            mBt_add_manage.setVisibility(View.VISIBLE);
            mListview.setVisibility(View.VISIBLE);
            mBt_submit.setVisibility(View.GONE);
            mTv_text.setVisibility(View.GONE);
        }
    }






























}

package com.blockadm.adm.im_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.adapter.CommunityBuyHistoryAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseRefreshActivity;
import com.blockadm.common.bean.live.param.CommunityBuyHistoryParams;
import com.blockadm.common.bean.live.responseBean.LiveListInfo;
import com.blockadm.common.bean.live.responseBean.LiveRecordsInfo;
import com.blockadm.common.bean.params.NewsLiveLessonsParams;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/6/28
 *
 * @Describe TODO { 直播课购买历史 }
 */
public class CommunityBuyHistoryActivity extends BaseRefreshActivity {


    private ListView mListview;

    private List<LiveRecordsInfo> mLiveRecordsList;
    private CommunityBuyHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCustomView(R.layout.activity_community_buy_history);
        setTitle("购买历史");

        mListview = findContentViewById(R.id.listview);

        mLiveRecordsList = new ArrayList<>();

        mAdapter = new CommunityBuyHistoryAdapter(mLiveRecordsList,this);
        mListview.setAdapter(mAdapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiveRecordsInfo liveRecordsInfo = mLiveRecordsList.get(position);
                Intent intent = new Intent(CommunityBuyHistoryActivity.this, LiveDetailActivity.class);
                intent.putExtra(LiveDetailActivity.LIVE_ID, liveRecordsInfo.getId());
                intent.putExtra(LiveDetailActivity.TITLE, liveRecordsInfo.getTitle());
                startActivity(intent);
            }
        });

        findMyBuySQPage(false);

    }


    private void findMyBuySQPage(final boolean isRefresh) {
        if (!isRefresh) {
            showDefaultLoadingDialog();
        }

        String jsonString = GsonUtil.GsonString(new CommunityBuyHistoryParams(mPageNum,mPageSize));
        CommonModel.findMyBuySQPage(jsonString, new MyObserver<LiveListInfo>() {

            @Override
            public void onSuccess(BaseResponse<LiveListInfo> response) {
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
                    mAdapter.notifyDataSetChanged();
                } else {
                    if (response.getData() != null)
                        mLiveRecordsList.addAll(response.getData().getRecords());
                    mAdapter.notifyDataSetChanged();
                }

                showEmptyView(ListUtils.isEmpty(mLiveRecordsList));
            }
        });

    }

    @Override
    protected void refreshData() {
        findMyBuySQPage(true);
    }
}

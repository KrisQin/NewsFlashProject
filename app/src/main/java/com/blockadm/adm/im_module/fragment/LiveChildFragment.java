package com.blockadm.adm.im_module.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.activity.LiveDetailActivity;
import com.blockadm.adm.im_module.adapter.LiveChildAdapter;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.live.param.LiveLessonsParams;
import com.blockadm.common.bean.live.responseBean.LiveListInfo;
import com.blockadm.common.bean.live.responseBean.LiveRecordsInfo;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.T;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * 最新
 */
public class LiveChildFragment extends Fragment {

    private String mTitle;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private LiveChildAdapter recyclerAdapter;
    private boolean isCanLoadMore = true;

    private ArrayList<LiveRecordsInfo> mLiveRecordsList;

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_im_child_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        smartRefreshLayout = view.findViewById(R.id.smart_refresh_layout);

        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));

        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (isCanLoadMore) {
                    queryNewsLiveLessons(false);
                } else {
                    T.showShort(getActivity(), R.string.no_data_load_more);
                    smartRefreshLayout.finishLoadMore();
                }
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isCanLoadMore = true;
                queryNewsLiveLessons(true);
            }
        });

        smartRefreshLayout.setDisableContentWhenRefresh(true);
        smartRefreshLayout.setDisableContentWhenLoading(true);
        mLiveRecordsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new LiveChildAdapter(getActivity(), mLiveRecordsList, mItemClickListener);
        recyclerView.setAdapter(recyclerAdapter);

        queryNewsLiveLessons(true);
        return view;
    }

    OnRecycleViewItemClickListener mItemClickListener = new OnRecycleViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            LiveRecordsInfo liveRecordsInfo = mLiveRecordsList.get(position);
            Intent intent = new Intent(getActivity(), LiveDetailActivity.class);
            intent.putExtra(LiveDetailActivity.LIVE_ID, liveRecordsInfo.getId());
            intent.putExtra(LiveDetailActivity.TITLE, liveRecordsInfo.getTitle());
            startActivity(intent);
        }
    };

    private int mPageNum = 0;
    private int mPageSize = 10;

    private void queryNewsLiveLessons(final boolean isRefresh) {
        if (isRefresh) {
            mPageNum = 1;
        }else {
            mPageNum++;
        }
        mPageSize = 10;

        String jsonString = GsonUtil.GsonString(new LiveLessonsParams("", mPageNum, mPageSize,
                null, ""));
        L.d("jsonString: " + jsonString);
        CommonModel.queryNewsLiveLessons(jsonString, new ComObserver<LiveListInfo>() {


            @Override
            public void onSuccess(BaseResponse<LiveListInfo> t, String errorMsg) {


                if (t.getData() != null && t.getData().getRecords().size() < 10) {
                    isCanLoadMore = false;
                }

                if (isRefresh) {
                    mLiveRecordsList.clear();
                    if (t.getData() != null)
                        mLiveRecordsList.addAll(t.getData().getRecords());
                    recyclerAdapter.notifyDataSetChanged();
                    smartRefreshLayout.finishRefresh();
                } else {
                    if (t.getData() != null)
                        mLiveRecordsList.addAll(t.getData().getRecords());
                    recyclerAdapter.notifyDataSetChanged();
                    smartRefreshLayout.finishLoadMore();
                }

                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();

                L.d("mLessonsInfoList.size(): " + mLiveRecordsList.size() + "  ;isRefresh :" + isRefresh);

            }
        });
    }

    public String getmTitle() {
        return mTitle;
    }
}

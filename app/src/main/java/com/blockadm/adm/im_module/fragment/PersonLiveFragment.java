package com.blockadm.adm.im_module.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.activity.LiveDetailActivity;
import com.blockadm.adm.im_module.adapter.PersonLiveAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.live.param.LiveLessonsParams;
import com.blockadm.common.bean.live.responseBean.LiveListInfo;
import com.blockadm.common.bean.live.responseBean.LiveRecordsInfo;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.T;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Kris on 2019/5/14
 *
 * @Describe TODO { 个人主页：社群直播 }
 */
public class PersonLiveFragment extends BaseComFragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private PersonLiveAdapter mRecyclerAdapter;

    private int mPageNum = 0;
    private int mPageSize = 10;
    private boolean isCanLoadMore = true;
    private List<LiveRecordsInfo> mRecordsList = new ArrayList<>();

    private String memberId = "";
    private View mLayout_empty;

    public PersonLiveFragment() {
    }

    @SuppressLint("ValidFragment")
    public PersonLiveFragment(String memberId) {
        this.memberId = memberId;
    }

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);


        mLayout_empty = rootView.findViewById(R.id.layout_empty);
        mRecyclerAdapter = new PersonLiveAdapter(mRecordsList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mRecyclerAdapter);

        mRecyclerAdapter.setOnRecyclerviewItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LiveRecordsInfo liveRecordsInfo = mRecordsList.get(position);
                Intent intent = new Intent(getActivity(), LiveDetailActivity.class);
                intent.putExtra(LiveDetailActivity.LIVE_ID, liveRecordsInfo.getId());
                intent.putExtra(LiveDetailActivity.TITLE, liveRecordsInfo.getTitle());
                startActivity(intent);
            }
        });

        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (isCanLoadMore) {
                    pageNewsLessonsSpecialColumn(false,"");
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
                pageNewsLessonsSpecialColumn(true,"");
            }
        });

        smartRefreshLayout.setDisableContentWhenRefresh(true);
        smartRefreshLayout.setDisableContentWhenLoading(true);

        pageNewsLessonsSpecialColumn(true,"");
    }


    public void search(String title){
        mPageNum=1;
        pageNewsLessonsSpecialColumn(true,title);
    }

    private void pageNewsLessonsSpecialColumn(final boolean isRefresh,String title) {
        if (isRefresh) {
            mPageNum = 1;
        }else {
            mPageNum++;
        }

        mPageSize = 10;

        String jsonString = GsonUtil.GsonString(new LiveLessonsParams("", mPageNum, mPageSize,
                null, title,memberId));

        L.d("jsonString: " + jsonString);
        CommonModel.queryNewsLiveLessons(jsonString, new ComObserver<LiveListInfo>() {

            @Override
            public void onSuccess(BaseResponse<LiveListInfo> response, String errorMsg) {

                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();

                if (response.getData() != null && response.getData().getRecords().size() < 10) {
                    isCanLoadMore = false;
                }

                if (isRefresh) {
                    mRecordsList.clear();
                    if (response.getData() != null)
                        mRecordsList.addAll(response.getData().getRecords());
                    mRecyclerAdapter.notifyDataSetChanged();
                } else {
                    if (response.getData() != null && response.getData().getRecords().size() > 0) {
                        mRecordsList.addAll(response.getData().getRecords());
                        mRecyclerAdapter.notifyDataSetChanged();
                    }else {
                        T.showShort(getActivity(),R.string.no_data_load_more);
                    }
                }

                if (ListUtils.isEmpty(mRecordsList)) {
                    mLayout_empty.setVisibility(View.VISIBLE);
                    smartRefreshLayout.setVisibility(View.GONE);
                }else {
                    mLayout_empty.setVisibility(View.GONE);
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                }


                L.d("mLessonsInfoList.size(): " + mRecordsList.size() + "  ;isRefresh :" + isRefresh);

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_fragment_person_live;
    }
    
    
    
    


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

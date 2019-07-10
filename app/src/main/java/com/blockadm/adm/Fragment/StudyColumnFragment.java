//package com.blockadm.adm.Fragment;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.blockadm.adm.R;
//import com.blockadm.adm.activity.ColumnOneDetailComActivity;
//import com.blockadm.adm.activity.StudyTabActivity;
//import com.blockadm.adm.im_module.adapter.StudyColumnAdapter;
//import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
//import com.blockadm.adm.model.CommonModel;
//import com.blockadm.common.base.BaseComFragment;
//import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
//import com.blockadm.common.bean.StudyRecordsBean;
//import com.blockadm.common.bean.params.LessonListBean;
//import com.blockadm.common.http.BaseResponse;
//import com.blockadm.common.http.MyObserver;
//import com.blockadm.common.utils.GsonUtil;
//import com.blockadm.common.utils.L;
//import com.blockadm.common.utils.ListUtils;
//import com.blockadm.common.utils.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
//
//
///**
// * Created by Kris on 2019/5/30
// *
// * @Describe TODO { 学习界面中的fragment }
// */
//@SuppressLint("ValidFragment")
//public class StudyColumnFragment extends BaseComFragment {
//
//    @BindView(R.id.recycler_view)
//    RecyclerView mRecyclerView;
//
//    Unbinder unbinder;
//    @BindView(R.id.layout_empty)
//    RelativeLayout layoutEmpty;
//    Unbinder unbinder1;
//    private String sysTypeId;
//    private int pageNum = 1;
//    private int pageSize = 50;
//
//    private List<StudyRecordsBean> mStudyRecordsBeanList = new ArrayList<>();
//    private StudyTabActivity mActivity;
//    private StudyColumnAdapter columnlistAdapter;
//
//    public StudyColumnFragment() {
//    }
//
//    public StudyColumnFragment(String sysTypeId) {
//        this.sysTypeId = sysTypeId;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_study1;
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
////        this.isVisibleToUser = isVisibleToUser;//注：关键步骤
//        if (isVisibleToUser && mActivity != null) {
//            mActivity.setLoadMore(false);
//        }
//
//        super.setUserVisibleHint(isVisibleToUser);
//    }
//
//    @Override
//    protected void initView(View rootView) {
//        unbinder = ButterKnife.bind(this, rootView);
//
//        mActivity = (StudyTabActivity) getActivity();
//
//        loadData(true);
//
//        recyclerViewScrollListener();
//
//        columnlistAdapter = new StudyColumnAdapter(mStudyRecordsBeanList,mActivity);
//        mRecyclerView.setAdapter(columnlistAdapter);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//
//        columnlistAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
//            @Override
//            public void onItemClickListener(View v, int position) {
//                if (mStudyRecordsBeanList != null && mStudyRecordsBeanList.size() > 0) {
//                    StudyRecordsBean bean = mStudyRecordsBeanList.get(position);
//                    if (bean != null) {
//                        Intent intent = new Intent(getActivity(), ColumnOneDetailComActivity.class);
//                        intent.putExtra("id", bean.getId());
//                        startActivity(intent);
//                    }
//
//                }
//            }
//        });
//    }
//
//
//
//    /**
//     * 有可能是为了能够在空viewpager能够上下滑动而添加的6个空对象
//     * @return
//     */
//    private boolean isListEmpty() {
//        if (ListUtils.isEmpty(mStudyRecordsBeanList)) {
//            return true;
//        }
//        boolean flag = false;
//        for (int i = 0; i < mStudyRecordsBeanList.size(); i++) {
//            if (mStudyRecordsBeanList.get(i) != null) {
//                flag = true;
//                break;
//            }
//        }
//        return !flag;
//    }
//
//    /**
//     * 为了能够在空viewpager能够上下滑动，所以添加了6个空对象
//     */
//    private void setStudyRecordsBeanList() {
//        int count = 6 - mStudyRecordsBeanList.size();
//
//        if (count > 0 ) {
//            for (int i = 0; i < count; i++) {
//                mStudyRecordsBeanList.add(null);
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mActivity.setLoadMore(false);
//    }
//
//    private void recyclerViewScrollListener() {
//        //RecyclerView的滑动监听
//        //        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//        //            //当RecyclerView滑动时触发
//        //            @Override
//        //            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        //                super.onScrolled(recyclerView, dx, dy);
//        //                //获取可见item个数
//        //                int lastVisibleItemPosition = mGridLayoutManager
//        //                .findLastVisibleItemPosition();
//        //                //说明是最后一条数据
//        //                if (lastVisibleItemPosition + 1 == columnlistAdapter.getItemCount()) {
//        ////                    Snackbar.make(mRecyclerView, "一次只加载20条，查看更多请刷新哦。",Snackbar
//        // .LENGTH_SHORT).show();
//        //
//        //                    mActivity.setLoadMore(true);
//        //                }else {
//        //                    mActivity.setLoadMore(false);
//        //                }
//        //            }
//        //        });
//
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            //用来标记是否正在向最后一个滑动
//            boolean isSlidingToLast = false;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
//                // 当不滚动时
//                //                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                //                    //获取最后一个完全显示的ItemPosition
//                //                    int lastVisibleItem = manager
//                //                    .findLastCompletelyVisibleItemPosition();
//                //                    int totalItemCount = manager.getItemCount();
//                //
//                //                    // 判断是否滚动到底部，并且是向右滚动
//                //                    if (lastVisibleItem == (totalItemCount - 1) &&
//                //                    isSlidingToLast) {
//                //                        //加载更多功能的代码
//                //                        mActivity.setLoadMore(true);
//                //                    }else {
//                //                        mActivity.setLoadMore(false);
//                //                    }
//                //                }
//
//                //获取最后一个完全显示的ItemPosition
//                int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
//                int totalItemCount = manager.getItemCount();
//
//                // 判断是否滚动到底部，并且是向右滚动
//                if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
//                    //加载更多功能的代码
//                    mActivity.setLoadMore(true);
//                } else {
//                    mActivity.setLoadMore(false);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
//                if (dx > 0) {
//                    //大于0表示正在向右滚动
//                    isSlidingToLast = true;
//                } else {
//                    //小于等于0表示停止或向左滚动
//                    isSlidingToLast = true;
//                }
//            }
//        });
//    }
//
//    public void loadMore() {
//
//        loadData(false);
//    }
//
//
//    public void refreshData() {
//        loadData(true);
//    }
//
//    private boolean isLoadMore = false;
//
//    private void loadData(boolean isRefresh) {
//
//        isLoadMore = !isRefresh;
//        if (isRefresh) {
//            pageNum = 1;
//            mStudyRecordsBeanList.clear();
//        }else {
//            pageNum++;
//        }
//
//
//        if (TextUtils.isEmpty(sysTypeId)) {
//            LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
//            lessonListBean.setAccessStatus("1");
//            queryLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
//        } else {
//            if (StringUtils.isEquals(sysTypeId, StudyTabActivity.Column_Select_Title)) {
//                LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
//                lessonListBean.setAccessStatus("1");
//                queryLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
//            } else if (StringUtils.isEquals(sysTypeId, StudyTabActivity.Limit_Time_Free_Title)) {
//                LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
//                lessonListBean.setAccessStatus("0");
//                queryLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
//            } else {
//                queryLessonsSpecialColumn(GsonUtil.GsonString(new LessonListBean(0,
//                        pageNum, pageSize, sysTypeId)));
//            }
//
//
//        }
//    }
//
//    private void queryLessonsSpecialColumn(String jsonData) {
//
//        L.d("jsonData: "+jsonData);
//        CommonModel.pageNewsLessonsSpecialColumn(jsonData, new MyObserver<NewsLessonsSpecialColumnDto>() {
//            @Override
//            public void onSuccess(BaseResponse<NewsLessonsSpecialColumnDto> response) {
//                dealLessonsSpecialColumn(response);
//            }
//        });
//    }
//
//    private void dealLessonsSpecialColumn(BaseResponse<NewsLessonsSpecialColumnDto> response) {
//        if (response == null || response.getData() == null) {
//            return;
//        }
//
//        mStudyRecordsBeanList.addAll(response.getData().getRecords());
//
//        if (isListEmpty()) {
//            layoutEmpty.setVisibility(View.VISIBLE);
//        }else {
//            layoutEmpty.setVisibility(View.GONE);
//        }
//
//        setStudyRecordsBeanList();
//
//        columnlistAdapter.notifyDataSetChanged();
//
//        if (isLoadMore && response.getData() != null) {
//
//            if (response.getData().getRecords().size() < 50) {
//                pageNum--;
//                Toast.makeText(getActivity(), getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        mActivity.refreshFinish();
//        //        if (columnlistAdapter.getItemCount() == mStudyRecordsBeanList.size() - 1) {
//        //            if (mStudyRecordsBeanList.size() < 50)
//        //                Toast.makeText(getActivity(), getString(R.string.no_data_load_more),
//        //                        Toast.LENGTH_SHORT).show();
//        //        }
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder1 = ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder1.unbind();
//    }
//}

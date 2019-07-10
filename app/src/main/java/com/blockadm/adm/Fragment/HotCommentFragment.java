package com.blockadm.adm.Fragment;


import android.annotation.SuppressLint;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.PictrueContentCommentAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.ColumnComentBean;
import com.blockadm.common.bean.CommentBeanDTO;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hp on 2019/4/12.
 */

@SuppressLint("ValidFragment")
public class HotCommentFragment extends BaseComFragment {
    @BindView(R.id.tv_follow_up)
    TextView tvFollowUp;
    @BindView(R.id.ll_follow_up)
    LinearLayout llFollowUp;
    @BindView(R.id.swipe_target)
    RecyclerView evComment;
    @BindView(R.id.nsl)
    NestedScrollView nsl;
   /* @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private AppBarLayout appBarLayout;*/
    Unbinder unbinder;

    private int  pageNum =1;
    private int pageSize = 15;
    private int id;

    @SuppressLint("ValidFragment")
    public HotCommentFragment(int id, AppBarLayout  appBarLayout) {
        super();
        this.id = id ;
      /*  this.appBarLayout = appBarLayout;*/
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        initData();
    }

    public HotCommentFragment() {
    }
    private boolean isRefresh = false;
    private boolean isloadMore = false;
    private void initData() {
        ColumnComentBean columnComentBean =  new ColumnComentBean();
        columnComentBean.setDataType(2);
        columnComentBean.setObjectId(id);
        columnComentBean.setObjectType(0);
        columnComentBean.setPageNum(pageNum);
        columnComentBean.setPageSize(pageSize);
        CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(columnComentBean), myObserverList);
      /*  swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadingMore(true);
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                pageNum++;
                ColumnComentBean columnComentBean =  new ColumnComentBean();
                columnComentBean.setDataType(2);
                columnComentBean.setObjectId(id);
                columnComentBean.setObjectType(0);
                columnComentBean.setPageNum(pageNum);
                columnComentBean.setPageSize(pageSize);
                CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(columnComentBean), myObserverList);
            }
        });

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                recordsBeans.clear();
                pageNum=1;
                ColumnComentBean columnComentBean =  new ColumnComentBean();
                columnComentBean.setDataType(2);
                columnComentBean.setObjectId(id);
                columnComentBean.setObjectType(0);
                columnComentBean.setPageNum(pageNum);
                columnComentBean.setPageSize(pageSize);
                CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(columnComentBean), myObserverList);
            }
        });
        if (appBarLayout!=null){
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (swipeToLoadLayout!=null){
                        Log.i("onOffsetChanged", DesignViewUtils.isSlideToBottom(evComment)  +"            "+verticalOffset);
                        swipeToLoadLayout.setEnabled(verticalOffset >= 0|| DesignViewUtils.isSlideToBottom(evComment) ? true : false);
                    }

                }
            });
        }*/



        if (nsl != null) {
            nsl.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY < oldScrollY&&scrollY == 0) {
                        isRefresh = true;
                        Log.i("onScrollChange", "Scroll UP");
                        recordsBeans.clear();
                        pageNum=1;
                        ColumnComentBean columnComentBean =  new ColumnComentBean();
                        columnComentBean.setDataType(2);
                        columnComentBean.setObjectId(id);
                        columnComentBean.setObjectType(0);
                        columnComentBean.setPageNum(pageNum);
                        columnComentBean.setPageSize(pageSize);
                        CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(columnComentBean), myObserverList);
                    }



                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())&&scrollY > oldScrollY) {
                        Log.i("onScrollChange", "BOTTOM SCROLL");
                        isloadMore = true;
                        pageNum++;
                        ColumnComentBean columnComentBean =  new ColumnComentBean();
                        columnComentBean.setDataType(2);
                        columnComentBean.setObjectId(id);
                        columnComentBean.setObjectType(0);
                        columnComentBean.setPageNum(pageNum);
                        columnComentBean.setPageSize(pageSize);
                        CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(columnComentBean), myObserverList);
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recordsBeans!=null&&recordsBeans.size()>0){
            recordsBeans.clear();
            pageNum=1;
            ColumnComentBean columnComentBean =  new ColumnComentBean();
            columnComentBean.setDataType(2);
            columnComentBean.setObjectId(id);
            columnComentBean.setObjectType(0);
            columnComentBean.setPageNum(pageNum);
            columnComentBean.setPageSize(pageSize);
            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(columnComentBean), myObserverList);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_hot_comment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private List<CommentBeanDTO.RecordsBean> recordsBeans = new ArrayList<>();
    MyObserver myObserverList = new MyObserver<CommentBeanDTO>() {
        @Override
        public void onSuccess(BaseResponse<CommentBeanDTO> t) {


            if (t.getData() != null) {
                if (t.getData().getTotal() == 0) {
                    llFollowUp.setVisibility(View.GONE);
                } else {
                    llFollowUp.setVisibility(View.VISIBLE);
                    tvFollowUp.setText("跟帖(" + t.getData().getTotal() + ")");
                }
                recordsBeans.addAll(t.getData().getRecords());
                setAdapter();
                if (t.getData() != null && t.getData().getTotal() == recordsBeans.size()) {
                   if (isloadMore) {
                       isloadMore = false;
                        Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }

               if (isRefresh) {
                   isRefresh = false;
                   Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                }


            }

        }

    };


    private void setAdapter() {
        evComment.setHasFixedSize(true);
        evComment.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        evComment.setLayoutManager(linearLayoutManager);
        PictrueContentCommentAdapter pictrueContentCommentAdapter = new PictrueContentCommentAdapter(recordsBeans, getActivity());
        pictrueContentCommentAdapter.setVisibilityHuifu(View.GONE);
        evComment.setAdapter(pictrueContentCommentAdapter);

    }
}

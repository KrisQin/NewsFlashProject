package com.blockadm.adm.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ColumnOneDetailComActivity;
import com.blockadm.adm.activity.StudyTabTestActivity;
import com.blockadm.adm.activity.StudyTabTestActivity;
import com.blockadm.adm.im_module.adapter.StudyColumnAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
import com.blockadm.common.bean.StudyRecordsBean;
import com.blockadm.common.bean.params.LessonListBean;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Kris on 2019/5/30
 *
 * @Describe TODO { 学习界面中的fragment }
 */
@SuppressLint("ValidFragment")
public class StudyColumnTestFragment extends BaseComFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    Unbinder unbinder;
    @BindView(R.id.layout_empty)
    RelativeLayout layoutEmpty;
    Unbinder unbinder1;
    private String sysTypeId;
    private int pageNum = 1;
    private int pageSize = 20;

    private List<StudyRecordsBean> mStudyRecordsBeanList = new ArrayList<>();
    private StudyTabTestActivity mActivity;
    private StudyColumnAdapter columnlistAdapter;

    public StudyColumnTestFragment() {
    }

    public StudyColumnTestFragment(String sysTypeId) {
        this.sysTypeId = sysTypeId;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study1;
    }


    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);

        mActivity = (StudyTabTestActivity) getActivity();

        loadData(true);


        columnlistAdapter = new StudyColumnAdapter(mStudyRecordsBeanList,mActivity);
        mRecyclerView.setAdapter(columnlistAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        columnlistAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (mStudyRecordsBeanList != null && mStudyRecordsBeanList.size() > 0) {
                    StudyRecordsBean bean = mStudyRecordsBeanList.get(position);
                    if (bean != null) {
                        Intent intent = new Intent(getActivity(), ColumnOneDetailComActivity.class);
                        intent.putExtra("id", bean.getId());
                        startActivity(intent);
                    }

                }
            }
        });
    }



    /**
     * 有可能是为了能够在空viewpager能够上下滑动而添加的6个空对象
     * @return
     */
    private boolean isListEmpty() {
        if (ListUtils.isEmpty(mStudyRecordsBeanList)) {
            return true;
        }
        boolean flag = false;
        for (int i = 0; i < mStudyRecordsBeanList.size(); i++) {
            if (mStudyRecordsBeanList.get(i) != null) {
                flag = true;
                break;
            }
        }
        return !flag;
    }

    /**
     * 为了能够在空viewpager能够上下滑动，所以添加了6个空对象
     */
    private void setStudyRecordsBeanList() {
        int count = 6 - mStudyRecordsBeanList.size();

        if (count > 0 ) {
            for (int i = 0; i < count; i++) {
                mStudyRecordsBeanList.add(null);
            }
        }
    }

    public void loadMore() {
        loadData(false);
    }


    public void refreshData() {
        loadData(true);
    }

    private boolean isLoadMore = false;

    private void loadData(boolean isRefresh) {

        isLoadMore = !isRefresh;
        if (isRefresh) {
            pageNum = 1;
            mStudyRecordsBeanList.clear();
        }else {
            pageNum++;
        }

        if (TextUtils.isEmpty(sysTypeId)) {
            LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
            lessonListBean.setAccessStatus("1");
            queryLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
        } else {
            if (StringUtils.isEquals(sysTypeId, StudyTabTestActivity.Column_Select_Title)) {
                LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
                lessonListBean.setAccessStatus("1");
                queryLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
            } else if (StringUtils.isEquals(sysTypeId, StudyTabTestActivity.Limit_Time_Free_Title)) {
                LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
                lessonListBean.setAccessStatus("0");
                queryLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
            } else {
                queryLessonsSpecialColumn(GsonUtil.GsonString(new LessonListBean(0,
                        pageNum, pageSize, sysTypeId)));
            }


        }
    }

    private void queryLessonsSpecialColumn(String jsonData) {

        L.d("jsonData: "+jsonData);
        CommonModel.pageNewsLessonsSpecialColumn(jsonData, new MyObserver<NewsLessonsSpecialColumnDto>() {
            @Override
            public void onSuccess(BaseResponse<NewsLessonsSpecialColumnDto> response) {
                dealLessonsSpecialColumn(response);
            }
        });
    }

    private void dealLessonsSpecialColumn(BaseResponse<NewsLessonsSpecialColumnDto> response) {
        if (response == null || response.getData() == null) {
            return;
        }

        mStudyRecordsBeanList.addAll(response.getData().getRecords());

        if (isListEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
        }else {
            layoutEmpty.setVisibility(View.GONE);
        }

        setStudyRecordsBeanList();

        columnlistAdapter.notifyDataSetChanged();

        if (isLoadMore && response.getData() != null) {
            if (response.getData().getRecords().size() < pageSize) {
                pageNum--;
                Toast.makeText(getActivity(), getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
            }
        }

        mActivity.refreshFinish();
    }

}

package com.blockadm.adm.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ColumnOneDetailComActivity;
import com.blockadm.adm.adapter.ColumnlistAdapter;
import com.blockadm.adm.contact.StudyContract;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.StudyListModel;
import com.blockadm.adm.persenter.StudyListPresenter;
import com.blockadm.common.base.BaseFragment;
import com.blockadm.common.bean.NewsLessonsDTO;
import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
import com.blockadm.common.bean.StudyRecordsBean;
import com.blockadm.common.bean.params.LessonListBean;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/3/8.
 */

@SuppressLint("ValidFragment")
public class ExclusiveColumnFragment extends BaseFragment<StudyListPresenter, StudyListModel> implements StudyContract.View {

    private AppBarLayout appBarLayout;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    /*    @BindView(R.id.swipeToLoadLayout)
        SwipeToLoadLayout swipeToLoadLayout;*/
    Unbinder unbinder;
    @BindView(R.id.layout_state)
    StateLayout stateLayout;

    @BindView(R.id.tv_load_more)
    TextView tvLoadmore;

    private int pageNum = 1;
    private int pageSize = 20;
    private String memberId;

    @SuppressLint("ValidFragment")
    public ExclusiveColumnFragment(AppBarLayout appBarLayout) {
        this.appBarLayout = appBarLayout;
    }

    public ExclusiveColumnFragment(String memberId) {
        this.memberId = memberId;
    }

    public ExclusiveColumnFragment() {
    }

    private List<StudyRecordsBean> mStudyRecordsBeanList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);

        mRecyclerView.setFocusable(false);
        mRecyclerView.setFocusableInTouchMode(false);

        loadData();

        tvLoadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum++;
                if (TextUtils.isEmpty(memberId)) {
                    LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
                    lessonListBean.setAccessStatus("1");
                    mPersenter.pageNewsLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
                } else {
                    mPersenter.pageNewsLessonsSpecialColumn(GsonUtil.GsonString(new LessonListBean(0, pageNum, pageSize, memberId)));
                }
            }
        });
    }

    private void loadData() {

        stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
        stateLayout.setIngStateView(new CustomerIngView(getActivity()));
        stateLayout.switchState(StateLayout.State.ING);

        mStudyRecordsBeanList.clear();

        if (TextUtils.isEmpty(memberId)) {
            LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
            lessonListBean.setAccessStatus("1");
            mPersenter.pageNewsLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
        } else {
            mPersenter.pageNewsLessonsSpecialColumn(GsonUtil.GsonString(new LessonListBean(0,
                    pageNum, pageSize, memberId)));
        }


    }

    @Override
    public void showPageNewsLessonslist(BaseResponse<NewsLessonsDTO> lessonsDTOBaseResponse) {

    }


    ColumnlistAdapter columnlistAdapter;

    @Override
    public void pageNewsLessonsSpecialColumn(BaseResponse<NewsLessonsSpecialColumnDto> response) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        stateLayout.switchState(CONTENT);
        if (response.getData() == null) {
            return;
        }
        if (response.getData().getSize() == 0) {
            return;
        }
        if (pageNum != 1) {
            mStudyRecordsBeanList.addAll(response.getData().getRecords());
            columnlistAdapter = new ColumnlistAdapter(mStudyRecordsBeanList, getActivity());
        } else {
            columnlistAdapter = new ColumnlistAdapter(mStudyRecordsBeanList, getActivity());
            mStudyRecordsBeanList.addAll(response.getData().getRecords());
        }
        columnlistAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (mStudyRecordsBeanList != null && mStudyRecordsBeanList.size() > 0) {
                    StudyRecordsBean bean = mStudyRecordsBeanList.get(position);
                    Intent intent = new Intent(getActivity(), ColumnOneDetailComActivity.class);
                    intent.putExtra("id", bean.getId());
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(columnlistAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        if (columnlistAdapter != null && columnlistAdapter.getItemCount() == 0) {
            stateLayout.switchState(EMPTY);
            tvLoadmore.setVisibility(View.GONE);
        }

        if (columnlistAdapter.getItemCount() == response.getData().getTotal()) {
            if (pageNum > 1)
                Toast.makeText(getActivity(), getString(R.string.no_data_load_more),
                        Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_twolist_state;
    }

    public void onRefresh() {
        pageNum = 1;
        mStudyRecordsBeanList.clear();
        if (TextUtils.isEmpty(memberId)) {
            LessonListBean lessonListBean = new LessonListBean(0, pageNum, pageSize);
            lessonListBean.setAccessStatus("1");
            mPersenter.pageNewsLessonsSpecialColumn(GsonUtil.GsonString(lessonListBean));
        } else {
            mPersenter.pageNewsLessonsSpecialColumn(GsonUtil.GsonString(new LessonListBean(0,
                    pageNum, pageSize, memberId)));
        }
    }
}

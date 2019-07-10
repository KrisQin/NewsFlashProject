package com.blockadm.adm.Fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ColumnOneDetailComActivity;
import com.blockadm.adm.activity.LesssonsDetailComActivity;
import com.blockadm.adm.adapter.LearnListAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.LessonsAndNlscDTO;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.RecycleViewDivider;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/1/26.
 */

public class LearnFragment extends BaseComFragment {
    @BindView(R.id.swipe_target)
    EmptyRecyclerView rvNewsflashList;
    Unbinder unbinder;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;


    private int pageNum = 1;
    private int pageSize = 10;


    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
        stateLayout.setIngStateView(new CustomerIngView(getActivity()));
        stateLayout.switchState(StateLayout.State.ING);
        CommonModel.pageSearchNewsLessonsAndNlsc(GsonUtil.GsonString(new PageBean(pageNum,pageSize,"")) ,myObserver);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                recordsBeans.clear();
                key ="";
                pageNum=1;
                CommonModel.pageSearchNewsLessonsAndNlsc(GsonUtil.GsonString(new PageBean(pageNum,pageSize,"")) ,myObserver);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.pageSearchNewsLessonsAndNlsc(GsonUtil.GsonString(new PageBean(pageNum,pageSize,"")) ,myObserver);
            }
        });


    }
    private String key ="";
    public void search(String key){
        this.key =key;
        recordsBeans.clear();
        pageNum=1;
        CommonModel.pageSearchNewsLessonsAndNlsc(GsonUtil.GsonString(new PageBean(pageNum,pageSize,key)) ,myObserver);
    }


    MyObserver myObserver = new MyObserver<LessonsAndNlscDTO>() {
        @Override
        public void onSuccess(BaseResponse<LessonsAndNlscDTO> t) {
            stateLayout.switchState(CONTENT);
            if (t.getData()!=null){
                setAdapter(t.getData());
            }
        }

    };



    private  List<LessonsAndNlscDTO.RecordsBean>  recordsBeans = new ArrayList<>();
    private void setAdapter(LessonsAndNlscDTO lessonsAndNlscDTO ) {
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (lessonsAndNlscDTO!=null&&lessonsAndNlscDTO.getRecords()!=null){
            LearnListAdapter learnListAdapter ;
            if (pageNum!=1){
                recordsBeans.addAll(lessonsAndNlscDTO.getRecords());
              learnListAdapter = new LearnListAdapter(getContext(),recordsBeans);

            }else{
                learnListAdapter = new LearnListAdapter(getContext(),lessonsAndNlscDTO.getRecords());
                recordsBeans.addAll(lessonsAndNlscDTO.getRecords());
            }
           if (isAdded()){
               rvNewsflashList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.color_fff2f3f4)));
           }

            learnListAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    LessonsAndNlscDTO.RecordsBean recordsBean =  recordsBeans.get(position);
                    if (recordsBean.getTypeId()==1){
                        Intent intent = new Intent(getActivity(), LesssonsDetailComActivity.class);
                        intent.putExtra("id",recordsBean.getId());
                        startActivity(intent);
                    }else if (recordsBean.getTypeId()==2){
                        Intent intent = new Intent(getActivity(), ColumnOneDetailComActivity.class);
                        intent.putExtra("id",recordsBean.getId());
                        startActivity(intent);
                    }

                }
            });

            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            rvNewsflashList.setLayoutManager(linearLayoutManager);
            rvNewsflashList.setAdapter(learnListAdapter);
            if (learnListAdapter!=null&&learnListAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }
            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (learnListAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(learnListAdapter.getItemCount()-pageSize, 0);
                }else{
                    rvNewsflashList.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);
                if (learnListAdapter.getItemCount()==lessonsAndNlscDTO.getTotal()){
                    Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_userlist_content_state;
    }

}

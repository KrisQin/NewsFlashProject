package com.blockadm.adm.Fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ActivitysDetailComActivity;
import com.blockadm.adm.adapter.WalksSchAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.bean.WalksSchduleDto;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerErrorView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.L;
import com.blockadm.common.web.FullWebBaseViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/1/10.
 */

public class WalksSchFragment extends BaseComFragment {

    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;
    Unbinder unbinder;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private int pageNum = 1;
    private int pageSize = 5;


    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                records.clear();
                pageNum=1;
                CommonModel.newsActivityPage(GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")), observer);
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.newsActivityPage(GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")), observer);
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
        stateLayout.setIngStateView(new CustomerIngView(getActivity()));
        stateLayout.setErrorStateView(new CustomerErrorView(getActivity()));
        stateLayout.switchState(StateLayout.State.ING);

        String json = GsonUtil.GsonString(new PageBean(pageNum, pageSize, "",null));

        L.d("newsActivityPage json: "+json);

        CommonModel.newsActivityPage(json, observer);
    }

    MyObserver<WalksSchduleDto> observer = new MyObserver<WalksSchduleDto>() {
        @Override
        public void onSuccess(BaseResponse<WalksSchduleDto> t) {

            L.d("newsActivityPage t: "+t);
            if (t.getData() != null) {
                initAdapter(t.getData());
            }
        }
    };


    private List<WalksSchduleDto.RecordsBean> records = new ArrayList<>();
    private void initAdapter(WalksSchduleDto data) {

        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (data!=null&&data.getRecords()!=null){


            WalksSchAdapter   walksSchAdapter ;
            if (pageNum!=1){
                records.addAll(data.getRecords());
                walksSchAdapter = new WalksSchAdapter(records,getContext());
            }else{
                 walksSchAdapter = new WalksSchAdapter(data.getRecords(),getContext());
                records.addAll(data.getRecords());
            }
            walksSchAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    if (records!=null&&records.size()>0){
                        WalksSchduleDto.RecordsBean recordsBean  = records.get(position);
                        if (recordsBean.getCreateType() == 0) {
                            String url = ApiService.BASR_URL_RELEASE +"/news/visitor/share/shareUrl?contentType=1&objectId="+recordsBean.getId();
                            Intent intent = new Intent(getActivity(), FullWebBaseViewActivity.class);
                            intent.putExtra(FullWebBaseViewActivity.DATA_WEB_URL,url);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getActivity(), ActivitysDetailComActivity.class);
                            intent.putExtra("id", recordsBean.getId());
                            startActivity(intent);
                        }
                    }
                }
            });
          //  erv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.color_fff2f3f4)));

            erv.setAdapter(walksSchAdapter);
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);

            if (walksSchAdapter!=null&&walksSchAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }

            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (walksSchAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(walksSchAdapter.getItemCount()-pageSize, 0);
                }else{
                    erv.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);


                if (walksSchAdapter.getItemCount()==data.getTotal()){
                    Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.fra_walksch_customer_state;
    }









}

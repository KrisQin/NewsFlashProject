package com.blockadm.adm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.FransListAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.FansListDTO;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.bean.UserListBean;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.RecycleViewDivider;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerErrorView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/1/25.
 */
@Route(path = "/app/index/FansListComActivity")
public class FansListComActivity extends BaseComActivity {
    @BindView(R.id.tilte)
    BaseTitleBar tilte;

    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @Autowired
    int  id;

    private  int pageNum =1;
    private  int pageSize=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fans_list_state);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                recordsBeans.clear();
                pageNum= 1;
                CommonModel.pageFollow(GsonUtil.GsonString(new PageBean(pageNum,pageSize,id)),myObserver);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.pageFollow(GsonUtil.GsonString(new PageBean(pageNum,pageSize,id)),myObserver);
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(this));
        stateLayout.setIngStateView(new CustomerIngView(this));
        stateLayout.setErrorStateView(new CustomerErrorView(this));
       stateLayout.switchState(StateLayout.State.ING);

        CommonModel.pageFollow(GsonUtil.GsonString(new PageBean(pageNum,pageSize,id)),myObserver);

    }

    private MyObserver  myObserver = new MyObserver<FansListDTO>() {
        @Override
        public void onSuccess(BaseResponse<FansListDTO> t) {
            if (t.getData()!=null){
                setAdapter(t.getData());
            }
        }


    };
    private ArrayList<UserListBean>  recordsBeans = new ArrayList<>();
    private void setAdapter(FansListDTO fansListDTO ) {
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (fansListDTO!=null&&fansListDTO.getRecords()!=null){
            FransListAdapter  fransListAdapter ;
            if (pageNum!=1){
                recordsBeans.addAll(fansListDTO.getRecords());
                fransListAdapter = new FransListAdapter(this,recordsBeans);

            }else{
                fransListAdapter = new FransListAdapter(this,fansListDTO.getRecords());
                recordsBeans.addAll(fansListDTO.getRecords());

            }

            erv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.color_fff2f3f4)));


            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);
            erv.setAdapter(fransListAdapter);
            if (fransListAdapter!=null&&fransListAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }
            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (fransListAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(fransListAdapter.getItemCount()-pageSize, 0);
                }else{
                    erv.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);

                if (fransListAdapter.getItemCount()==fansListDTO.getTotal()){
                    Toast.makeText(this,getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

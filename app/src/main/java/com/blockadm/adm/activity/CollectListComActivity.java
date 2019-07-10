package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.CollectListAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.CollectListDto;
import com.blockadm.common.bean.PageBean;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/2/13.
 */

public class CollectListComActivity extends BaseComActivity {


    @BindView(R.id.tilte)
    BaseTitleBar titleBar;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;

    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_collect_list);
        ButterKnife.bind(this);

        titleBar.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();

    }

    private List<CollectListDto.RecordsBean> records = new ArrayList<>();
    private void initView() {

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                records.clear();
                pageNum=1;
                CommonModel.myNewsCollectionPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.myNewsCollectionPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(this));
        stateLayout.setIngStateView(new CustomerIngView(this));
        stateLayout.setErrorStateView(new CustomerErrorView(this));
        stateLayout.switchState(StateLayout.State.ING);

       CommonModel.myNewsCollectionPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));
    }


    MyObserver<CollectListDto> observer = new MyObserver<CollectListDto>() {
        @Override
        public void onSuccess(BaseResponse<CollectListDto> t) {
            setAdapter(t.getData());

        }


    };

    public void setAdapter(CollectListDto data) {

        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

       if (data!=null&&data.getRecords()!=null){


            CollectListAdapter collectListAdapter ;
            if (pageNum!=1){
                records.addAll(data.getRecords());
                collectListAdapter = new CollectListAdapter(records,this);
            }else{
                collectListAdapter = new CollectListAdapter(data.getRecords(),this);
                records.addAll(data.getRecords());
            }
           collectListAdapter.setmOnRecyclerviewItemClickListener(
                   new OnRecyclerviewItemClickListener() {
                       @Override
                       public void onItemClickListener(View v, int position) {
                           CollectListDto.RecordsBean  recordsBean =  records.get(position);
                           switch (recordsBean.getTypeId()){
                               case 1:
                                   Intent intent = new Intent(CollectListComActivity.this, LesssonsDetailComActivity.class);
                                   intent.putExtra("id",recordsBean.getObejctId());
                                   startActivity(intent);
                                   break;
                               case 2:
                                   Intent intent1 = new Intent(CollectListComActivity.this, ColumnOneDetailComActivity.class);
                                   intent1.putExtra("id",recordsBean.getObejctId());
                                   startActivity(intent1);
                                   break;
                               case 3:
                                   ARouter.getInstance().build("/app/index/InfomationDetailActivty").withInt("id", recordsBean.getObejctId()).navigation();
                                   break;
                               case 4:
                                       Intent intent4 = new Intent(CollectListComActivity.this, ActivitysDetailComActivity.class);
                                      intent4.putExtra("id", recordsBean.getObejctId());
                                       startActivity(intent4);
                                   break;
                           }

                       }
               }
           );


            erv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.color_fff2f3f4)));

            erv.setAdapter(collectListAdapter);
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);

            if (collectListAdapter!=null&&collectListAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }

           if (swipeToLoadLayout.isLoadingMore()){
               swipeToLoadLayout.setLoadingMore(false);
               if (collectListAdapter.getItemCount()>=pageSize){
                   linearLayoutManager.scrollToPositionWithOffset(collectListAdapter.getItemCount()-pageSize, 0);
               }else{
                   erv.smoothScrollToPosition(0);
               }
               linearLayoutManager.setStackFromEnd(true);

               if (collectListAdapter.getItemCount()==data.getTotal()){
                   Toast.makeText(this,getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
               }
           }
        }
    }
}

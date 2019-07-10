package com.blockadm.adm.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ColumnOneDetailComActivity;
import com.blockadm.adm.activity.LesssonsDetailComActivity;
import com.blockadm.adm.adapter.BuyHistoryAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.BuyHistoryDto;
import com.blockadm.common.bean.params.findMyBuyStudyPageParams;
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
 * Created by hp on 2019/3/15.
 */

@SuppressLint("ValidFragment")
public class BuyHistoryFragment extends BaseComFragment {

    private int pageNum = 1;
    private int pageSize = 10;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;

    private int  type;
    public BuyHistoryFragment(int i) {
        super();
        this.type = i;
    }


    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        initMyView();
    }


    @Override
    public int getLayoutId() {
        return R.layout.frag_buy_history;
    }



    MyObserver<BuyHistoryDto> observer = new MyObserver<BuyHistoryDto>() {
        @Override
        public void onSuccess(BaseResponse<BuyHistoryDto> t) {
            if (t.getData() != null) {
                setAdapter(t.getData());
            }
        }


    };
    private List<BuyHistoryDto.RecordsBean> records = new ArrayList<>();
    private void initMyView() {

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                records.clear();
                pageNum=1;
                CommonModel.findMyBuyStudyPage(GsonUtil.GsonString(new findMyBuyStudyPageParams(type, pageNum, pageSize)), observer);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.findMyBuyStudyPage(GsonUtil.GsonString(new findMyBuyStudyPageParams(type, pageNum, pageSize)), observer);
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
        stateLayout.setIngStateView(new CustomerIngView(getActivity()));
        stateLayout.setErrorStateView(new CustomerErrorView(getActivity()));
        stateLayout.switchState(StateLayout.State.ING);
        CommonModel.findMyBuyStudyPage(GsonUtil.GsonString(new findMyBuyStudyPageParams(type, pageNum, pageSize)), observer);
    }




    public void setAdapter(BuyHistoryDto data) {

        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (data!=null&&data.getRecords()!=null){


            BuyHistoryAdapter buyHistoryAdapter ;
            if (pageNum!=1){
                records.addAll(data.getRecords());
                buyHistoryAdapter = new BuyHistoryAdapter(records,getActivity(),type);
            }else{
                buyHistoryAdapter = new BuyHistoryAdapter(data.getRecords(),getActivity(),type);
                records.addAll(data.getRecords());
            }


           if (isAdded()){
               erv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.color_fff2f3f4)));
           }

            buyHistoryAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    if (records!=null&&records.size()>0){
                        BuyHistoryDto.RecordsBean recordsBean  = records.get(position);
                        if (recordsBean.getLessonsType()==0){
                            Intent intent = new Intent(getActivity(), ColumnOneDetailComActivity.class);
                            intent.putExtra("id",recordsBean.getId());
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getActivity(), LesssonsDetailComActivity.class);
                            intent.putExtra("id",recordsBean.getId());
                            startActivity(intent);
                        }
                    }
                }
            });
            erv.setAdapter(buyHistoryAdapter);
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);

            if (buyHistoryAdapter!=null&&buyHistoryAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }

            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (buyHistoryAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(buyHistoryAdapter.getItemCount()-pageSize, 0);
                }else{
                    erv.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);

                if (buyHistoryAdapter.getItemCount()==data.getTotal()){
                    Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

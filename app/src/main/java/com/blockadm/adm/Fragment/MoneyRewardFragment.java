package com.blockadm.adm.Fragment;

import android.annotation.SuppressLint;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.BountyHunterComActivity;
import com.blockadm.adm.adapter.MoneyRewardAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.PageMyRecommendDetailWebDto;
import com.blockadm.common.bean.params.PageMyRecommendDetailWebParams;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerErrorView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.DesignViewUtils;
import com.blockadm.common.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;

/**
 * Created by hp on 2019/2/27.
 */

@SuppressLint("ValidFragment")
public class MoneyRewardFragment extends BaseComFragment {
    private int  type ;



    private AppBarLayout appBarLayout;
    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private int pageNum = 1;
    private int pageSize = 20;


    public MoneyRewardFragment(int i,AppBarLayout appbar) {
        super();
        this.type = i;
        this.appBarLayout = appbar;
    }



    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        if (appBarLayout!=null){
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (swipeToLoadLayout!=null){
                        Log.i("onOffsetChanged", DesignViewUtils.isSlideToBottom(erv)  +"            "+verticalOffset);

                        swipeToLoadLayout.setEnabled(verticalOffset >= 0|| DesignViewUtils.isSlideToBottom(erv) ? true : false);
                    }

                }
            });
        }

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                records.clear();
                pageNum=1;
                CommonModel.pageMyRecommendDetailWeb(observer, GsonUtil.GsonString(new PageMyRecommendDetailWebParams(type,pageNum,pageSize)));
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.pageMyRecommendDetailWeb(observer, GsonUtil.GsonString(new PageMyRecommendDetailWebParams(type,pageNum,pageSize)));
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
        stateLayout.setIngStateView(new CustomerIngView(getActivity()));
        stateLayout.setErrorStateView(new CustomerErrorView(getActivity()));
        stateLayout.switchState(StateLayout.State.ING);
        CommonModel.pageMyRecommendDetailWeb(observer, GsonUtil.GsonString(new PageMyRecommendDetailWebParams(type,pageNum,pageSize)));
    }

    MyObserver<PageMyRecommendDetailWebDto> observer = new MyObserver<PageMyRecommendDetailWebDto>() {
        @Override
        public void onSuccess(BaseResponse<PageMyRecommendDetailWebDto> t) {
            if (t.getData() != null) {
                showList(t.getData());
            }

        }


    };


    private List<PageMyRecommendDetailWebDto.RecordsBean> records = new ArrayList<>();
    public void showList(PageMyRecommendDetailWebDto pageMyRecommendDetailWebDto) {
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (pageMyRecommendDetailWebDto !=null&& pageMyRecommendDetailWebDto.getRecords()!=null){
            if (getActivity() instanceof BountyHunterComActivity){
                BountyHunterComActivity bountyHunterActivity  = (BountyHunterComActivity) getActivity();
                bountyHunterActivity.setTotal(pageMyRecommendDetailWebDto.getTotal());
            }

            MoneyRewardAdapter moneyRewardAdapter ;
            if (pageNum!=1){
                records.addAll(pageMyRecommendDetailWebDto.getRecords());
                moneyRewardAdapter = new MoneyRewardAdapter(records,getActivity());
            }else{
                moneyRewardAdapter = new MoneyRewardAdapter(pageMyRecommendDetailWebDto.getRecords(),getActivity());
                records.addAll(pageMyRecommendDetailWebDto.getRecords());
            }



            erv.setAdapter(moneyRewardAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);

          /*  if (moneyRewardAdapter!=null&&moneyRewardAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }*/

            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (moneyRewardAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(moneyRewardAdapter.getItemCount()-pageSize, 0);
                }else{
                    erv.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);

                if (moneyRewardAdapter.getItemCount()==pageMyRecommendDetailWebDto.getTotal()){
                    Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_money_erward;
    }
}

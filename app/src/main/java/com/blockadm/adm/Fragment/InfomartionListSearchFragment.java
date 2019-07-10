package com.blockadm.adm.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.adapter.InformationSearchAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.comstomview.EmptyRecyclerView;
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
 * Created by hp on 2019/1/9.
 */

public class InfomartionListSearchFragment extends BaseComFragment {
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
        CommonModel.newsArticlePage(GsonUtil.GsonString(new PageBean(pageNum,pageSize,"")) ,myObserver);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                records.clear();
                key ="";
                pageNum=1;
                CommonModel.newsArticlePage(GsonUtil.GsonString(new PageBean(pageNum,pageSize,key)) ,myObserver);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.newsArticlePage(GsonUtil.GsonString(new PageBean(pageNum,pageSize,key)) ,myObserver);
            }
        });


    }
  private String key ="";
    public void search(String key){
        records.clear();
        pageNum=1;
        CommonModel.newsArticlePage(GsonUtil.GsonString(new PageBean(pageNum,pageSize,key)) ,myObserver);
    }


    MyObserver myObserver = new MyObserver<NewsArticleListDTO>() {
        @Override
        public void onSuccess(BaseResponse<NewsArticleListDTO> t) {
            stateLayout.switchState(CONTENT);
            if (t.getData()!=null){
                setAdapter(t.getData());
            }
        }

    };



    private List<NewsArticleListDTO.RecordsBean> records = new ArrayList<>();
    public void setAdapter(NewsArticleListDTO newsArticleListDTO) {
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (newsArticleListDTO!=null&&newsArticleListDTO.getRecords()!=null){
            if (getActivity() instanceof PersonnalIndexComActivity){
                PersonnalIndexComActivity personnalIndexActivity  = (PersonnalIndexComActivity) getActivity();
                personnalIndexActivity.setTotal(newsArticleListDTO.getTotal(),2);
            }

            InformationSearchAdapter informationSearchAdapter ;
            if (pageNum!=1){
                records.addAll(newsArticleListDTO.getRecords());
                informationSearchAdapter = new InformationSearchAdapter(records);
            }else{
                informationSearchAdapter = new InformationSearchAdapter(newsArticleListDTO.getRecords());
                records.addAll(newsArticleListDTO.getRecords());
            }
            informationSearchAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    if (records!=null&&records.size()>0){
                        NewsArticleListDTO.RecordsBean recordsBean  = records.get(position);
                        ARouter.getInstance().build("/app/index/InfomationDetailActivty").withInt("id", recordsBean.getId()).navigation();
                    }
                }
            });
           // rvNewsflashList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.color_fff2f3f4)));

            rvNewsflashList.setAdapter(informationSearchAdapter);
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            rvNewsflashList.setLayoutManager(linearLayoutManager);
            if (informationSearchAdapter!=null&&informationSearchAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }
            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (informationSearchAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(informationSearchAdapter.getItemCount()-pageSize, 0);
                }else{
                    rvNewsflashList.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);
            }
        }


    }


    @Override
    public int getLayoutId() {
        return R.layout.act_userlist_content_state;
    }




}

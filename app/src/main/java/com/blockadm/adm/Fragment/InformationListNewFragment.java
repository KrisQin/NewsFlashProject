package com.blockadm.adm.Fragment;

import android.annotation.SuppressLint;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.adapter.NewsArticlePageAdapter;
import com.blockadm.adm.contact.InformationListContract;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.InformationListModel;
import com.blockadm.adm.persenter.InformationListPresenter;
import com.blockadm.common.base.BaseFragment;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.params.NewsFlashBean;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerErrorView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.utils.DesignViewUtils;
import com.blockadm.common.utils.GsonUtil;

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

@SuppressLint("ValidFragment")
public class InformationListNewFragment extends BaseFragment<InformationListPresenter, InformationListModel> implements InformationListContract.View {



    Unbinder unbinder;
    private int type;

    private  AppBarLayout appBarLayout;
    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private int pageNum = 1;
    private int pageSize = 20;
    private int sysTypeId;

    public InformationListNewFragment() {
    }

    public InformationListNewFragment(int i, AppBarLayout appBarLayout, int id) {
        super();
        type = i;
        this.appBarLayout = appBarLayout;
        this.sysTypeId = id;




    }
     private String memberId;
    public InformationListNewFragment(String memberId) {
        super();
        this.memberId =memberId;
    }

    @Override
    protected void initView(View view) {
        unbinder =  ButterKnife.bind(this, view);
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
                mPersenter.newsArticlePage(GsonUtil.GsonString(new NewsFlashBean(type, pageNum, pageSize, "",memberId,sysTypeId)));
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                mPersenter.newsArticlePage(GsonUtil.GsonString(new NewsFlashBean(type, pageNum, pageSize, "",memberId,sysTypeId)));
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
        stateLayout.setIngStateView(new CustomerIngView(getActivity()));
        stateLayout.setErrorStateView(new CustomerErrorView(getActivity()));
        stateLayout.switchState(StateLayout.State.ING);
        erv.setFocusable(true);
        mPersenter.newsArticlePage(GsonUtil.GsonString(new NewsFlashBean( type,1, pageSize, "", memberId,sysTypeId)));
    }



    @Override
    public int getLayoutId() {
        return R.layout.fra_informationlist_customer_state;
    }


    private List<NewsArticleListDTO.RecordsBean> records = new ArrayList<>();
    @Override
    public void newsArticlePageShow(NewsArticleListDTO newsArticleListDTO) {
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (newsArticleListDTO!=null&&newsArticleListDTO.getRecords()!=null){
            if (getActivity() instanceof PersonnalIndexComActivity){
                PersonnalIndexComActivity personnalIndexActivity  = (PersonnalIndexComActivity) getActivity();
                personnalIndexActivity.setTotal(newsArticleListDTO.getTotal(),2);
            }

            NewsArticlePageAdapter  newsArticlePageAdapter ;
            if (pageNum!=1){
                records.addAll(newsArticleListDTO.getRecords());
                newsArticlePageAdapter = new NewsArticlePageAdapter(records);
            }else{
                newsArticlePageAdapter = new NewsArticlePageAdapter(newsArticleListDTO.getRecords());
                records.addAll(newsArticleListDTO.getRecords());
            }
            newsArticlePageAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    if (records!=null&&records.size()>0){
                        NewsArticleListDTO.RecordsBean recordsBean  = records.get(position);
                        ARouter.getInstance().build("/app/index/InfomationDetailActivty").withInt("id", recordsBean.getId()).navigation();
                    }
                }
            });

            //erv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.color_fff2f3f4)));

            erv.setAdapter(newsArticlePageAdapter);
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);

            if (newsArticlePageAdapter!=null&&newsArticlePageAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }

            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (newsArticlePageAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(newsArticlePageAdapter.getItemCount()-pageSize, 0);
                }else{
                    erv.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);
                if (newsArticlePageAdapter.getItemCount()==newsArticleListDTO.getTotal()){
                    Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }


    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}

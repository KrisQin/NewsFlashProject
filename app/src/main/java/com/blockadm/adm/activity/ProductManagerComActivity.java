package com.blockadm.adm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.NewsArticlePageAdapter;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.bean.TotalDto;
import com.blockadm.common.comstomview.BaseTitleBar;
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
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/2/22.
 */

public class ProductManagerComActivity extends BaseComActivity {


    Unbinder unbinder;
    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @BindView(R.id.tv_readcount)
    TextView textRead;

    private int pageNum = 1;
    private int pageSize = 10;
    @BindView(R.id.tilte)
    BaseTitleBar tilte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_production_manager);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                records.clear();
                pageNum=1;
                CommonModel.myPagenewsArticle(GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")), observer);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.myPagenewsArticle(GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")), observer);
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(this));
        stateLayout.setIngStateView(new CustomerIngView(this));
        stateLayout.setErrorStateView(new CustomerErrorView(this));
        stateLayout.switchState(StateLayout.State.ING);

        CommonModel.myPagenewsArticle(GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")), observer);
        CommonModel.getNewsTotal(new MyObserver<TotalDto>() {
            @Override
            public void onSuccess(BaseResponse <TotalDto> t) {

                if (t.getCode()==0){
                    textRead.setText(t.getData().getNewsArticleReadCount()+"");
                }else{
                    ToastUtils.showToast(t.getMsg());
                }
            }

        });
    }


    MyObserver<NewsArticleListDTO> observer = new MyObserver<NewsArticleListDTO>() {


        @Override
        public void onSuccess(BaseResponse<NewsArticleListDTO> t) {
            newsArticlePageShow(t.getData());

        }
    };



    private List<NewsArticleListDTO.RecordsBean> records = new ArrayList<>();
    public void newsArticlePageShow(NewsArticleListDTO newsArticleListDTO) {
        if (stateLayout!=null){
            stateLayout.switchState(CONTENT);
        }
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (newsArticleListDTO!=null&&newsArticleListDTO.getRecords()!=null){

            NewsArticlePageAdapter newsArticlePageAdapter ;
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

            //erv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.color_fff2f3f4)));

            erv.setAdapter(newsArticlePageAdapter);
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
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
                    Toast.makeText(this,getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }


    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

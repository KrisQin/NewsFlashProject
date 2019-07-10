package com.blockadm.adm.Fragment;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;

import com.blockadm.adm.activity.HtmlComActivity;
import com.blockadm.adm.activity.TagComActivity;

import com.blockadm.adm.adapter.NewsArticlePageAdapter3;
import com.blockadm.adm.adapter.TagsAdapter;
import com.blockadm.adm.event.MessageEvent;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.BannerListDto;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.bean.TagBeanDto;

import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.banner.BannerView;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerErrorView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.SharedpfTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/1/8.
 */

public class AttentionFragment1 extends BaseComFragment {



    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.rv_tags)
    RecyclerView rvTags;
    @BindView(R.id.ll)
    LinearLayout linearLayout;
    Unbinder unbinder;
    @BindView(R.id.layout_state)
    StateLayout stateLayout;
    @BindView(R.id.bannerView)
    BannerView bannerView;

    @BindView(R.id.swipe_target)
    EmptyRecyclerView erv;
    private int pageNum = 1;
    private int pageSize = 10;


    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private List<NewsArticleListDTO.RecordsBean> records = new ArrayList<>();
    private ArrayList<BannerListDto> bannerListDtos1;

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        selectView();
        tvManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty((String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN,""))){
                    showNologinDialog();
                }else{
                    Intent intent = new Intent(getContext(), TagComActivity.class);
                    startActivityForResult(intent,555);
                }
            }
        });

        bannerView.startLoop();
        bannerView.setScrollerDuration(1000);
        bannerView.setItemClick(new BannerView.OnBannerClickListener() {
            @Override
            public void onBannerItemClick(int position) {

                String  url =  bannerListDtos1.get(position).getRedirectUrl();
                if (!TextUtils.isEmpty(url)){
                    Intent intent = new Intent(getActivity(), HtmlComActivity.class);
                    intent.putExtra("url",url);
                    intent.putExtra("title", bannerListDtos1.get(position).getTitle());
                    startActivity(intent);
                }


            }
        });
        CommonModel.findSysBannerList(0,1 , new MyObserver<ArrayList<BannerListDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<BannerListDto>> t) {
                bannerListDtos1 = t.getData();
                try{
                    bannerView.setPictrues(t.getData());
                }catch (Exception e){

                }

            }


        });
    }
    NewsArticleListDTO.RecordsBean recordsBean =  new NewsArticleListDTO.RecordsBean();
    private void selectView() {

        ArrayList<TagBeanDto>  tagBeanDtos = (ArrayList<TagBeanDto>) ACache.get(getActivity()).getAsObject(ConstantUtils.TAGS);
        if (tagBeanDtos !=null&& tagBeanDtos.size()>0){
            swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {

                    records.clear();

                    pageNum=1;

                    CommonModel.findUserSubscribeNewsArticlePage(observer, GsonUtil.GsonString( new PageBean(pageNum,pageSize,"")));
                }
            });
            swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pageNum++;
                    CommonModel.findUserSubscribeNewsArticlePage(observer, GsonUtil.GsonString( new PageBean(pageNum,pageSize,"")));
                }
            });
            rvTags.setVisibility(View.GONE);
            bannerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            stateLayout.setVisibility(View.VISIBLE);

                  stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
                  stateLayout.setIngStateView(new CustomerIngView(getActivity()));
                  stateLayout.setErrorStateView(new CustomerErrorView(getActivity()));
                  stateLayout.switchState(StateLayout.State.ING);

                  CommonModel.findUserSubscribeNewsArticlePage(observer, GsonUtil.GsonString( new PageBean(pageNum,pageSize,"")));


        }else{
            rvTags.setVisibility(View.VISIBLE);
            bannerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            stateLayout.setVisibility(View.GONE);
            setTagAdapter();
        }


    }

    private void setTagAdapter() {
        showDefaultLoadingDialog();
        CommonModel.findAllNotParentIdSysLableList(new MyObserver<ArrayList<TagBeanDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<TagBeanDto>> t) {
                dismissLoadingDialog();
                if (t!=null){
                    ArrayList<TagBeanDto>  tagBeanDtos  =   t.getData();
                    ACache.get(getActivity()).put(ConstantUtils.TAGS_ALL,tagBeanDtos);
                    setAdapter(tagBeanDtos);

                }
            }
        });
    }


    MyObserver<NewsArticleListDTO> observer = new MyObserver<NewsArticleListDTO>() {
        @Override
        public void onSuccess(BaseResponse<NewsArticleListDTO> t) {
            setlist(t.getData());
        }

    };

    private int SPAN_COUNT=3;
    private void setAdapter(ArrayList<TagBeanDto> data) {
        if (rvTags!=null){
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),SPAN_COUNT );
            rvTags.setLayoutManager(layoutManager);
            TagsAdapter  tagsAdapter  = new TagsAdapter(data,getActivity());

            rvTags.setAdapter(tagsAdapter);
        }




    }
   private  NewsArticlePageAdapter3 newsArticlePageAdapter ;
    public void setlist(NewsArticleListDTO newsArticleListDTO) {
        if (stateLayout==null){
            return ;
        }
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout!=null&&swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (newsArticleListDTO!=null&&newsArticleListDTO.getRecords()!=null){
            if (pageNum!=1&&newsArticlePageAdapter!=null){
                records.addAll(newsArticleListDTO.getRecords());
                newsArticlePageAdapter.setData(records);
            }else{
                List<NewsArticleListDTO.RecordsBean>  recordsBeans = newsArticleListDTO.getRecords();
                recordsBeans.add(0,recordsBean);
                newsArticlePageAdapter = new NewsArticlePageAdapter3(recordsBeans,getContext());
                records.addAll(recordsBeans);
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
       // erv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.color_fff2f3f4)));
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);
            erv.setAdapter(newsArticlePageAdapter);


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
    private void showNologinDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.color_transparent);
        alertDialog.setCanceledOnTouchOutside(false);//外部触摸不单独关闭
        View view = View.inflate(getActivity(), R.layout.layout_no_login, null);
        Button bg = (Button) view.findViewById(R.id.bt_go_login);
        ImageView ivCancle = (ImageView) view.findViewById(R.id.iv_cancel);
        alertDialog.setView(view);
        alertDialog.show();
        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPathConfig.login_activity_path).withBoolean("is", false).navigation();
                alertDialog.dismiss();
            }
        });

    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_attention;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(MessageEvent messageEvent) {
        ArrayList<TagBeanDto>  tagBeanDtos = (ArrayList<TagBeanDto>) ACache.get(getActivity()).getAsObject(ConstantUtils.TAGS);

        if (tagBeanDtos !=null&& tagBeanDtos.size()>0){

            swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {

                    records.clear();

                    pageNum=1;

                    CommonModel.findUserSubscribeNewsArticlePage(observer, GsonUtil.GsonString( new PageBean(pageNum,pageSize,"")));
                }
            });
            swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pageNum++;
                    CommonModel.findUserSubscribeNewsArticlePage(observer, GsonUtil.GsonString( new PageBean(pageNum,pageSize,"")));
                }
            });
            rvTags.setVisibility(View.GONE);
            bannerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            stateLayout.setVisibility(View.VISIBLE);

            stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
            stateLayout.setIngStateView(new CustomerIngView(getActivity()));
            stateLayout.setErrorStateView(new CustomerErrorView(getActivity()));
            stateLayout.switchState(StateLayout.State.ING);
            records.clear();

            pageNum=1;
            CommonModel.findUserSubscribeNewsArticlePage(observer, GsonUtil.GsonString( new PageBean(pageNum,pageSize,"")));
        }else{
            rvTags.setVisibility(View.VISIBLE);
            bannerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            stateLayout.setVisibility(View.GONE);
            setTagAdapter();
        }


        CommonModel.findAllNotParentIdSysLableList(new MyObserver<ArrayList<TagBeanDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<TagBeanDto>> t) {
                if (t!=null){
                    ArrayList<TagBeanDto>  tagBeanDtos  =   t.getData();
                    ACache.get(getActivity()).put(ConstantUtils.TAGS_ALL,tagBeanDtos);

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}


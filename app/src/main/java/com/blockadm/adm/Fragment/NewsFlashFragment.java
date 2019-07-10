package com.blockadm.adm.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.MyVipComActivity;
import com.blockadm.adm.adapter.NewsFlashAdapter;
import com.blockadm.adm.contact.NewsFlashContract;
import com.blockadm.adm.interf.OprateTypeListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.model.NewsFlashModel;
import com.blockadm.adm.persenter.NewsFlashPresenter;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.base.BaseFragment;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.ZanBean;
import com.blockadm.common.bean.params.NewsFlashBean;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.DataString;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.SharedpfTools;

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

public class NewsFlashFragment extends BaseComFragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    EmptyRecyclerView rvNewsflashList;
    Unbinder unbinder;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.layout_state)
    StateLayout stateLayout;
    @BindView(R.id.tv_now)
    TextView tvNow;

    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        tvNow.setText("今天 "+ DataString.StringData());
        stateLayout.setEmptyStateView(new CustomerEmptyView(getActivity()));
        stateLayout.setIngStateView(new CustomerIngView(getActivity()));
        stateLayout.switchState(StateLayout.State.ING);
        getNewsFlashList(GsonUtil.GsonString(new NewsFlashBean( 2,pageNum, pageSize, "")));
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        getUserData();

    }
    
    private void getNewsFlashList(String jsonData) {
        CommonModel.getNewsFlashList(jsonData,new MyObserver<NewsFlashBeanDto>() {
            @Override
            public void onSuccess(BaseResponse<NewsFlashBeanDto> newsFlashBeanDto) {
                showNewsFlashList(newsFlashBeanDto.getData());
            }


        });
    }

    private void getUserData() {

        CommonModel.getUserData(getActivity(), new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
            }

            @Override
            public void error(int code, String msg) {

            }

        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.fra_new_flash_customer_state;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private List<NewsFlashBeanDto.RecordsBean> recordsBeans = new ArrayList<>();
    private  NewsFlashAdapter mAdapter;
    
    public void showNewsFlashList(NewsFlashBeanDto newsFlashBeanDto) {
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }
        if (newsFlashBeanDto==null||newsFlashBeanDto.getRecords()==null||newsFlashBeanDto.getRecords().size()==0){
            return;
        }

        List<NewsFlashBeanDto.RecordsBean> records = newsFlashBeanDto.getRecords();
        if (pageNum!=1){
            recordsBeans.addAll(newsFlashBeanDto.getRecords());
            mAdapter = new NewsFlashAdapter(recordsBeans,getActivity());
        }else{
            mAdapter = new NewsFlashAdapter(records, getActivity());
            recordsBeans.addAll(newsFlashBeanDto.getRecords());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter.setOprateTypeClick(new OprateTypeListener() {
            @Override
            public void onOprateTypeListen(int id, int type,int position) {
                positionLocation = position;
                CommonModel.operateNewsFlashCount(id,type,new MyObserver<ZanBean>() {

                    @Override
                    public void onSuccess(BaseResponse<ZanBean> zanBean) {
                        show0perateNewsFlashCount(zanBean.getData());
                    }

                });
            }
        });

      /*  rvNewsflashList.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.colorPrimaryDark)));*/
        rvNewsflashList.setLayoutManager(linearLayoutManager);
        rvNewsflashList.setAdapter(mAdapter);

        if (mAdapter!=null&&mAdapter.getItemCount()==0){
            stateLayout.switchState(EMPTY);
        }
        if (swipeToLoadLayout.isLoadingMore()){
            swipeToLoadLayout.setLoadingMore(false);
            if (mAdapter.getItemCount()>=pageSize){
                linearLayoutManager.scrollToPositionWithOffset(mAdapter.getItemCount()-pageSize, 0);
            }else{
                rvNewsflashList.smoothScrollToPosition(0);
            }
            linearLayoutManager.setStackFromEnd(true);

            if (mAdapter.getItemCount()==newsFlashBeanDto.getTotal()){
                Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void show0perateNewsFlashCount(ZanBean zanBean) {
        if (zanBean==null){
            return ;
        }

        if (recordsBeans!=null&&recordsBeans.size()>0&&mAdapter!=null){
            NewsFlashBeanDto.RecordsBean  recordsBean  = (NewsFlashBeanDto.RecordsBean) recordsBeans.get(positionLocation);
            recordsBean.setNoZanCount(zanBean.getNoZanCount());
            recordsBean.setYesZanCount(zanBean.getYesZanCount());
            recordsBean.setIsZan(1);
            mAdapter.notifyItemChanged(positionLocation);
        }
    }
    private int positionLocation;

    @Override
    public void onRefresh() {
        recordsBeans.clear();
        pageNum=1;
        getNewsFlashList(GsonUtil.GsonString(new NewsFlashBean(2,pageNum, pageSize, "")));

    }

    @Override
    public void onLoadMore() {
        pageNum++;
        getNewsFlashList(GsonUtil.GsonString(new NewsFlashBean( 2,pageNum, pageSize, "")));

    }
}

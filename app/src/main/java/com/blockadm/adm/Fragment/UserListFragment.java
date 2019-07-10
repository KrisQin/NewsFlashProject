package com.blockadm.adm.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.UserListAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.bean.UserListDTO;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.RecycleViewDivider;
import com.blockadm.common.comstomview.stateswitch.CustomerEmptyView;
import com.blockadm.common.comstomview.stateswitch.CustomerIngView;
import com.blockadm.common.comstomview.stateswitch.StateLayout;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;


/**
 * Created by hp on 2019/1/9.
 */

public class UserListFragment extends BaseComFragment {
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
        CommonModel.searchMember(GsonUtil.GsonString(new PageBean("",pageNum,pageSize)) ,myObserver);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                key ="";
                pageNum=1;
                CommonModel.searchMember(GsonUtil.GsonString(new PageBean(key,pageNum,pageSize)) ,myObserver);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.searchMember(GsonUtil.GsonString(new PageBean(key,pageNum,pageSize)) ,myObserver);
            }
        });


    }
    private String key ="";
    public void search(String key){
        pageNum=1;
        CommonModel.searchMember(GsonUtil.GsonString(new PageBean(key,pageNum,pageSize)) ,myObserver);
    }


    MyObserver myObserver = new MyObserver<UserListDTO>() {
        @Override
        public void onSuccess(BaseResponse<UserListDTO> t) {
            stateLayout.switchState(CONTENT);
            if (t.getData()!=null){
                setAdapter(t.getData());
            }
        }

    };




    private void setAdapter(UserListDTO fansListDTO ) {
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (fansListDTO!=null&&fansListDTO.getRecords()!=null){
            UserListAdapter   userListAdapter = new UserListAdapter(getContext(),fansListDTO.getRecords());;
            if (isAdded()){
                rvNewsflashList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.color_fff2f3f4)));
            }
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            rvNewsflashList.setLayoutManager(linearLayoutManager);
            rvNewsflashList.setAdapter(userListAdapter);
            if (userListAdapter!=null&&userListAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }
            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (userListAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(userListAdapter.getItemCount()-pageSize, 0);
                }else{
                    rvNewsflashList.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);

                if (userListAdapter.getItemCount()==fansListDTO.getTotal()){
                    Toast.makeText(getActivity(),getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_userlist_content_state;
    }




}

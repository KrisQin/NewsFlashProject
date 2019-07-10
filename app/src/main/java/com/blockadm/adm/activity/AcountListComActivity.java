package com.blockadm.adm.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.AcountListAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.AcountListDto;
import com.blockadm.common.bean.AcountListDtoRecordBean;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.comstomview.BaseTitleBar;
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
import com.blockadm.common.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/2/14.
 * TODO 账单
 */

public class AcountListComActivity extends BaseComActivity {


    @BindView(R.id.swipe_target)
    SwipeMenuRecyclerView erv;

    @BindView(R.id.layout_state)
    StateLayout stateLayout;
    @BindView(R.id.tilte)
    BaseTitleBar titleBar;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_acountlist_state);
        ButterKnife.bind(this);
        initView();
        titleBar.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<AcountListDtoRecordBean> records = new ArrayList<>();
    private void initView() {
        //  设置侧滑
        // 设置监听器。
        erv.setSwipeMenuCreator(mSwipeMenuCreator);
        erv.setSwipeMenuItemClickListener(mMenuItemClickListener);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                records.clear();
                pageNum=1;
                CommonModel.userAccountPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.userAccountPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(this));
        stateLayout.setIngStateView(new CustomerIngView(this));
        stateLayout.setErrorStateView(new CustomerErrorView(this));
        stateLayout.switchState(StateLayout.State.ING);


        CommonModel.userAccountPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));
    }


    MyObserver<AcountListDto> observer = new MyObserver<AcountListDto>() {
        @Override
        public void onSuccess(BaseResponse<AcountListDto> t) {
            setAdapter(t.getData());
        }


    };

    private AcountListAdapter acountListAdapter ;
    public void setAdapter(AcountListDto acountListDto) {
        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (acountListDto!=null&&acountListDto.getRecords()!=null){


            if (pageNum!=1){
                records.addAll(acountListDto.getRecords());
                acountListAdapter = new AcountListAdapter(records);
            }else{
                acountListAdapter = new AcountListAdapter(acountListDto.getRecords());
                records.addAll(acountListDto.getRecords());
            }
            erv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.color_fff2f3f4)));
            erv.setAdapter(acountListAdapter);
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);
            if (acountListAdapter!=null&&acountListAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }
            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (acountListAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(acountListAdapter.getItemCount()-pageSize, 0);
                }else{
                    erv.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);

                if (acountListAdapter.getItemCount()==acountListDto.getTotal()){
                    Toast.makeText(this,getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 创建菜单
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp70);

            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            @SuppressLint("ResourceType") SwipeMenuItem deleteItem = new SwipeMenuItem(AcountListComActivity.this)
                    .setBackground(getResources().getDrawable(R.drawable.red))
                    .setText("删除")
                    .setTextSize(18)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(deleteItem); // 添加菜单到右侧。

        }
    };
    /**
     * 菜单点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            final int menuPosition =  menuBridge.getAdapterPosition(); // 菜单在RecyclerView的Item中的Position。
            if (records!=null&&records.size()>0){
                AcountListDtoRecordBean  recordsBean = records.get(menuPosition);
                //  删除
                CommonModel.deleteUserAccount(new MyObserver<String>() {
                    @Override
                    public void onSuccess(BaseResponse<String> t) {
                        ToastUtils.showToast(t.getMsg());
                        if (t.getCode()==0&&acountListAdapter!=null){
                            records.remove(menuPosition);
                            acountListAdapter.setData(records);
                        }
                    }
                },recordsBean.getId());
            }


        }
    };

}

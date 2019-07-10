package com.blockadm.adm.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.MsgListAdapter;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.PageBean;
import com.blockadm.common.bean.UserMsgDto;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/2/13.
 */

public class MsgListComActivity extends BaseComActivity {

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
        setContentView(R.layout.act_msglist_state);
        ButterKnife.bind(this);
        initView();

        CommonModel.userMsgPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));

        titleBar.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new UserDataEvent());
                finish();
            }
        });
    }

    private void initView() {
        //  设置侧滑
        // 设置监听器。
        erv.setSwipeMenuCreator(mSwipeMenuCreator);
        erv.setSwipeMenuItemClickListener(mMenuItemClickListener);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                recordsBeans.clear();
                pageNum=1;
                CommonModel.userMsgPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.userMsgPage(observer, GsonUtil.GsonString(new PageBean(pageNum, pageSize, "")));
            }
        });
        stateLayout.setEmptyStateView(new CustomerEmptyView(this));
        stateLayout.setIngStateView(new CustomerIngView(this));
        stateLayout.setErrorStateView(new CustomerErrorView(this));
        stateLayout.switchState(StateLayout.State.ING);

    }


    MyObserver<UserMsgDto> observer = new MyObserver<UserMsgDto>() {
        @Override
        public void onSuccess(BaseResponse<UserMsgDto> t) {
            setAdapter(t.getData());

        }


    };
    private ArrayList<UserMsgDto.RecordsBean>  recordsBeans = new ArrayList<>();
    private MsgListAdapter msgListAdapter ;
    public void setAdapter(UserMsgDto userMsgDto) {

        stateLayout.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (userMsgDto!=null&&userMsgDto.getRecords()!=null){
            if (pageNum!=1){
                recordsBeans.addAll(userMsgDto.getRecords());
                msgListAdapter = new MsgListAdapter(recordsBeans);

            }else{
                msgListAdapter = new MsgListAdapter(userMsgDto.getRecords());
                recordsBeans.addAll(userMsgDto.getRecords());

            }

            erv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.color_fff2f3f4)));


            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);
            erv.setAdapter(msgListAdapter);
            if (msgListAdapter!=null&&msgListAdapter.getItemCount()==0){
                stateLayout.switchState(EMPTY);
            }
            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (msgListAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(msgListAdapter.getItemCount()-pageSize, 0);
                }else{
                    erv.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);

                if (msgListAdapter.getItemCount()==userMsgDto.getTotal()){
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


            @SuppressLint("ResourceType") SwipeMenuItem deleteItem = new SwipeMenuItem(MsgListComActivity.this)
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
            final int menuPosition = menuBridge.getAdapterPosition(); // 菜单在RecyclerView的Item中的Position。

                    if (recordsBeans!=null&&recordsBeans.size()>0){
                         final UserMsgDto.RecordsBean  recordsBean = recordsBeans.get(menuPosition);
                         if (recordsBean.getTypeId()!=10){
                             //  删除
                             CommonModel.markUserMsg(new MyObserver<String>() {
                                 @Override
                                 public void onSuccess(BaseResponse<String> t) {
                                     ToastUtils.showToast(t.getMsg());
                                     if (t.getCode()==0&&msgListAdapter!=null){
                                         recordsBeans.remove(menuPosition);
                                         msgListAdapter.setData(recordsBeans);

                                     }
                                 }
                             },recordsBean.getId(),2);
                         }else{
                             Toast.makeText(MsgListComActivity.this,"官方消息不能删除",Toast.LENGTH_SHORT).show();
                         }

                    }




        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            EventBus.getDefault().post(new UserDataEvent());
        }
        return super.onKeyDown(keyCode, event);
    }

}

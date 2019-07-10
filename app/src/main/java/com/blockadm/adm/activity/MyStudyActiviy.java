package com.blockadm.adm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.MyStudyListAdapter;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.event.MyStudyEvent;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.MyStudyPageDTO;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.params.FindMyStudyPageParams;
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
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.CONTENT;
import static com.blockadm.common.comstomview.stateswitch.StateLayout.State.EMPTY;

/**
 * Created by hp on 2019/2/22.
 */

public class MyStudyActiviy extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_build)
    TextView tvBuild;
    @BindView(R.id.tv_create)
    TextView tvCreate;

    @BindView(R.id.tv_buy_history)
    TextView tvBuyHistory;
    @BindView(R.id.layout_state)
    StateLayout layoutState;

    Unbinder unbinder;
    @BindView(R.id.swipe_target)
    SwipeMenuRecyclerView erv;



    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;



    private int pageNum = 1;
    private int pageSize = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_my_study);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    MyObserver<MyStudyPageDTO> observer = new MyObserver<MyStudyPageDTO>() {
        @Override
        public void onSuccess(BaseResponse<MyStudyPageDTO> t) {
            showList(t.getData());
        }
    };
    private     MyStudyListAdapter myStudyListAdapter ;

    private List<MyStudyPageDTO.RecordsBean> records = new ArrayList<>();
    private void initView() {
        //  设置侧滑
        // 设置监听器。
        //erv.setSwipeMenuCreator(mSwipeMenuCreator);
        //erv.setSwipeMenuItemClickListener(mMenuItemClickListener);


        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                records.clear();
                pageNum=1;
                CommonModel.findMyStudyPage(GsonUtil.GsonString(new FindMyStudyPageParams(pageNum, pageSize)), observer);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                CommonModel.findMyStudyPage(GsonUtil.GsonString(new FindMyStudyPageParams(pageNum, pageSize)), observer);
            }
        });
        layoutState.setEmptyStateView(new CustomerEmptyView(this));
        layoutState.setIngStateView(new CustomerIngView(this));
        layoutState.setErrorStateView(new CustomerErrorView(this));
        layoutState.switchState(StateLayout.State.ING);
        CommonModel.findMyStudyPage(GsonUtil.GsonString(new FindMyStudyPageParams(pageNum, pageSize)), observer);
    }

    public void showList(MyStudyPageDTO myStudyPageDTO) {
        layoutState.switchState(CONTENT);
        if (swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }

        if (myStudyPageDTO!=null&&myStudyPageDTO.getRecords()!=null){

            if (pageNum!=1){
                records.addAll(myStudyPageDTO.getRecords());
                myStudyListAdapter = new MyStudyListAdapter(records,this);
            }else{
                myStudyListAdapter = new MyStudyListAdapter(myStudyPageDTO.getRecords(),this);
                records.addAll(myStudyPageDTO.getRecords());
                erv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.color_fff2f3f4)));
            }
            myStudyListAdapter.setmOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {
                    if (records!=null&&records.size()>0){
                        MyStudyPageDTO.RecordsBean recordsBean  = records.get(position);
                        if (recordsBean.getLessonsType()==0){
                            Intent intent = new Intent(MyStudyActiviy.this, ColumnOneDetailComActivity.class);
                            intent.putExtra("id",recordsBean.getId());
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(MyStudyActiviy.this, LesssonsDetailComActivity.class);
                            intent.putExtra("id",recordsBean.getId());
                            startActivity(intent);
                        }
                    }
                }
            });



            erv.setAdapter(myStudyListAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            erv.setLayoutManager(linearLayoutManager);

            if (myStudyListAdapter!=null&&myStudyListAdapter.getItemCount()==0){
                layoutState.switchState(EMPTY);
            }

            if (swipeToLoadLayout.isLoadingMore()){
                swipeToLoadLayout.setLoadingMore(false);
                if (myStudyListAdapter.getItemCount()>=pageSize){
                    linearLayoutManager.scrollToPositionWithOffset(myStudyListAdapter.getItemCount()-pageSize, 0);
                }else{
                    erv.smoothScrollToPosition(0);
                }
                linearLayoutManager.setStackFromEnd(true);

                if (myStudyListAdapter.getItemCount()==myStudyPageDTO.getTotal()){
                    Toast.makeText(this,getString(R.string.no_data_load_more),Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
    @Subscribe()
    public void update(MyStudyEvent messageEvent) {
        records.clear();
        pageNum=1;
        CommonModel.findMyStudyPage(GsonUtil.GsonString(new FindMyStudyPageParams(pageNum, pageSize)), observer);
    }

    @OnClick({R.id.tv_create, R.id.tv_build,R.id.tv_buy_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_create:
                final MyComstomDialogView myComstomDialogView = new MyComstomDialogView(this);
                myComstomDialogView.setTvTitle("请在PC端上传你的课程(支持图文/音频/视频)",View.VISIBLE).setRootBg(R.mipmap.boxbg2)
                        .setChildMsg("",View.GONE)
                        .setChildMsg2("",View.GONE)
                        .setConcelMsg("",View.GONE)

                        .setConfirmMsg("确定",View.VISIBLE)
                        .setConfirmSize(6)
                        .setCancelLisenner(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myComstomDialogView.dismiss();
                            }
                        }).setConfirmLisenner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myComstomDialogView.dismiss();
                    }
                });
                myComstomDialogView.show();
                break;
            case R.id.tv_build:
                UserInfoDto  userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
                if (userInfoDto.getPersonalCredentialsSate()!=2&&userInfoDto.getOrganizationCredentialsSate()!=2){
                    final MyComstomDialogView  myComstomDialogView1 = new MyComstomDialogView(MyStudyActiviy.this);
                    myComstomDialogView1.setTvTitle("请先完成个人/实名认证",View.VISIBLE).setRootBg(R.mipmap.boxbg2)
                            .setChildMsg("",View.GONE)
                            .setChildMsg2("",View.GONE)
                            .setConcelMsg("",View.GONE)
                            .setConfirmMsg("确定",View.VISIBLE)
                            .setConfirmSize(6)
                            .setCancelLisenner(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).setConfirmLisenner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myComstomDialogView1.dismiss();
                            Intent  intent = new Intent(MyStudyActiviy.this, AuthenticationComActivity.class);
                            startActivity(intent);
                        }
                    });
                    myComstomDialogView1.show();

                }else{
                    Intent  intent = new Intent(this, CreateColumnComActivity.class);
                    startActivity(intent);
                }


                break;

            case R.id.tv_buy_history:
                Intent  intent = new Intent(this, BuyHistoryComActivity.class);
                startActivity(intent);
                break;
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
            @SuppressLint("ResourceType") SwipeMenuItem deleteItem = new SwipeMenuItem(MyStudyActiviy.this)
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
                MyStudyPageDTO.RecordsBean  recordsBean = records.get(menuPosition);


                CommonModel.updateNlscStatus(recordsBean.getId(), 2, new MyObserver<String>() {
                    @Override
                    public void onSuccess(BaseResponse<String> t) {

                        if (t.getCode()==0&&myStudyListAdapter!=null){
                            ToastUtils.showToast(t.getMsg());

                            records.remove(menuPosition);
                            myStudyListAdapter.setData(records);
                        }
                    }
                });

            }


        }
    };
}

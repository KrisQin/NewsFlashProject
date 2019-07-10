package com.blockadm.adm.activity;

/**
 * Created by hp on 2019/1/26.
 */


        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationSet;
        import android.view.animation.TranslateAnimation;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.blockadm.adm.R;
        import com.blockadm.adm.event.MessageEvent;
        import com.blockadm.adm.model.CommonModel;
        import com.blockadm.common.base.BaseComActivity;
        import com.blockadm.common.bean.TagBeanDto;
        import com.blockadm.common.comstomview.BaseTitleBar;
        import com.blockadm.common.comstomview.DragAdapter;
        import com.blockadm.common.comstomview.DragGridView;
        import com.blockadm.common.comstomview.MyGridView;
        import com.blockadm.common.comstomview.OtherAdapter;
        import com.blockadm.common.http.BaseResponse;
        import com.blockadm.common.http.MyObserver;
        import com.blockadm.common.utils.ACache;
        import com.blockadm.common.utils.ConstantUtils;
        import com.blockadm.common.utils.ToastUtils;

        import org.greenrobot.eventbus.EventBus;

        import java.util.ArrayList;


public class TagComActivity extends BaseComActivity implements OnItemClickListener {
    private MyGridView mOtherGv;
    private DragGridView mUserGv;
/*    private List<String> mUserList = new ArrayList<>();
    private List<String> mOtherList = new ArrayList<>();*/
    private OtherAdapter mOtherAdapter;
    private DragAdapter mUserAdapter;
    private  ArrayList<TagBeanDto> mUserList= new ArrayList<>();
    private  ArrayList<TagBeanDto> mOtherList= new ArrayList<>();
    private ArrayList<TagBeanDto> tagBeanDtos1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        BaseTitleBar baseTitleBar = findViewById(R.id.tilte);
        baseTitleBar.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACache.get(TagComActivity.this).put(ConstantUtils.TAGS,mUserList);
                EventBus.getDefault().post(new MessageEvent(""));
                finish();
            }
        });

   /*      tagBeanDtos1 = (ArrayList<TagBeanDto>) ACache.get(TagComActivity.this).getAsObject(ConstantUtils.TAGS_ALL);
        if (tagBeanDtos1!=null){
            for (int i = 0; i <tagBeanDtos1.size() ; i++) {
                TagBeanDto  tagBeanDto = tagBeanDtos1.get(i);
                if (tagBeanDto.getIsSubscribe()==0){
                    mOtherList.add(tagBeanDto) ;
                }else{
                    mUserList.add(tagBeanDto) ;
                }
            }
            initView();
        }*/


        CommonModel.findAllNotParentIdSysLableList(new MyObserver<ArrayList<TagBeanDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<TagBeanDto>> t) {
                if (t!=null){
                     tagBeanDtos1  =   t.getData();
                    ACache.get(TagComActivity.this).put(ConstantUtils.TAGS_ALL,tagBeanDtos1);
                    for (int i = 0; i <tagBeanDtos1.size() ; i++) {
                        TagBeanDto  tagBeanDto = tagBeanDtos1.get(i);
                        if (tagBeanDto.getIsSubscribe()==0){
                            mOtherList.add(tagBeanDto) ;
                        }else{
                            mUserList.add(tagBeanDto) ;
                        }
                    }
                    initView();
                }
            }
        });
    }


    private void refresh() {
        CommonModel.findAllNotParentIdSysLableList(new MyObserver<ArrayList<TagBeanDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<TagBeanDto>> t) {
                if (t!=null){
                    tagBeanDtos1 = t.getData();
                    ACache.get(TagComActivity.this).put(ConstantUtils.TAGS_ALL, tagBeanDtos1);

                }
            }
        });
    }

    public void initView() {
        mUserGv = (DragGridView) findViewById(R.id.userGridView);
        mOtherGv = (MyGridView) findViewById(R.id.otherGridView);
       mUserAdapter = new DragAdapter(this, mUserList,true);
        mOtherAdapter = new OtherAdapter(this, mOtherList,false);
        mUserGv.setAdapter(mUserAdapter);
        mOtherGv.setAdapter(mOtherAdapter);
        mUserGv.setOnItemClickListener(this);
        mOtherGv.setOnItemClickListener(this);
    }

    /**
     *获取点击的Item的对应View，
     *因为点击的Item已经有了自己归属的父容器MyGridView，所有我们要是有一个ImageView来代替Item移动
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }
    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     * 用于存放我们移动的View
     */
    private ViewGroup getMoveViewGroup() {
        //window中最顶层的view
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }
    /**
     * 点击ITEM移动动画
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final TagBeanDto moveChannel,
                          final GridView clickGridView, final boolean isUser) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // 判断点击的是DragGrid还是OtherGridView
                if (isUser) {
                    mOtherAdapter.setVisible(true);
                    mOtherAdapter.notifyDataSetChanged();
                    mUserAdapter.remove();
                } else {
                    mUserAdapter.setVisible(true);
                    mUserAdapter.notifyDataSetChanged();
                    mOtherAdapter.remove();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        switch (parent.getId()) {
            case R.id.userGridView:
                //position为 0，1 的不可以进行任何操作
                if (position != -1 && position !=-1) { //改为-1前2个就可以移出的

                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);//
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final TagBeanDto channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                        mOtherAdapter.setVisible(false);
                        //添加到最后一个
                        mOtherAdapter.addItem(channel);
                        CommonModel.userSubscribeLableDelete(new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                             if (t.getCode()==200){
                                 refresh();
                             }
                            }


                        },channel.getId());
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    mOtherGv.getChildAt(mOtherGv.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation, channel, mUserGv, true);
                                    mUserAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 500);
                    }
                }
                break;
            case R.id.otherGridView:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {

                    if(mUserAdapter.getCount()<5){
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final TagBeanDto channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
                        mUserAdapter.setVisible(false);
                        //添加到最后一个
                        mUserAdapter.addItem(channel);
                        CommonModel.userSubscribeLableAdd(new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                if (t.getCode()==200){
                                    refresh();
                                }
                            }

                        },channel.getId());
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    mUserGv.getChildAt(mUserGv.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation, channel, mOtherGv,false);
                                    mOtherAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 500);
                    }else{
                        ToastUtils.showToast("最多只能添加5个");
                    }

                }
                break;
            default:
                break;
        }
    }
}


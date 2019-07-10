package com.blockadm.adm.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.PictrueContentCommentAdapter;
import com.blockadm.adm.contact.LessonDetailContract;
import com.blockadm.adm.dialog.DetailShareDialog;
import com.blockadm.adm.event.PleyerEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.model.LessonsDetailModel;
import com.blockadm.adm.persenter.LessonDetailPresenter;
import com.blockadm.adm.service.PlayService;
import com.blockadm.common.base.BaseActivity;
import com.blockadm.common.bean.AwardDto;
import com.blockadm.common.bean.CommentBeanDTO;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.LessonsSpecialColumnDto;
import com.blockadm.common.bean.NewsLessonsDetailDTO;
import com.blockadm.common.bean.PalyDetailDto;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.params.AddCommentParams;
import com.blockadm.common.bean.params.AddReplyBean;
import com.blockadm.common.bean.params.CommentBeanParams;
import com.blockadm.common.comstomview.CheckEmptyTextView;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.FScrollView;
import com.blockadm.common.comstomview.TagLayout;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.DimenUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/28.
 */

public class PictrueContentComActivity extends BaseActivity<LessonDetailPresenter, LessonsDetailModel> implements LessonDetailContract.View {


   /* @BindView(R.id.tilte)
    BaseTitleBar tilte;*/

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_shard)
    ImageView ivShard;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.tl)
    TagLayout tl;
/*    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_share)
    TextView tvShare;*/
    @BindView(R.id.tv_follow_up)
    TextView tvFollowUp;
    @BindView(R.id.ev_comment)
    EmptyRecyclerView evComment;
    @BindView(R.id.rl_root)
    FScrollView fsl;
    @BindView(R.id.et_say_content)
    EditText etSayContent;
    @BindView(R.id.tv_send)
    CheckEmptyTextView tvSend;
    @BindView(R.id.ll__follow_up)
    RelativeLayout llFollowUp;

    @BindView(R.id.rl_audio)
    RelativeLayout  rlAudio;

    @BindView(R.id.iv_palyer)
    ImageView  ivPlayer;

    @BindView(R.id.tv_audio_title)
    TextView  tvAudioTitle;

    @BindView(R.id.tv_time1)
    TextView  tvTime1;

    @BindView(R.id.tv_time)
    TextView  tvTime;
    @BindView(R.id.v_line)
    View  vLine;

  private int pageSize=20;
  private int pageNum=1;
    private   int index =0;
    private PalyDetailDto palyDetailDto;

    private int isSeeStatus;
    private int id;
    private int imageMaxWidth;
    private ArrayList<RecordsBean> audioList;
    private PictrueContentCommentAdapter pictrueContentCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictrue_content);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id",-1);
        isSeeStatus = getIntent().getIntExtra("isSeeStatus",-1);
        imageMaxWidth = ScreenUtils.getScreenWidth(getApplicationContext());
        audioList = (ArrayList<RecordsBean>) ACache.get(this).getAsObject(ConstantUtils.AUDIO_LIST);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               back();
            }
        });
        CommonModel.findNewsLessonsPlayDetail(observer,id,  DimenUtils.px2dip(this, imageMaxWidth),"0");
        String json =GsonUtil.GsonString(new CommentBeanParams(id,1,pageNum,pageSize));
        CommonModel.pageNewsLessonsComment(json,myObserver);
        // 滑动加载
        fsl.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN :

                        break;
                    case MotionEvent.ACTION_MOVE :
                        index++;
                        break;
                    default :
                        break;
                }
                if (event.getAction() == MotionEvent.ACTION_UP &&  index > 0) {
                    index = 0;
                    View view = ((ScrollView) v).getChildAt(0);
                    if (view.getMeasuredHeight() <= v.getScrollY() + v.getHeight()) {
                        pageNum++;

                        CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(id,1,pageNum,pageSize)),myObserver);
                    }
                }
                return false;
            }
        });
        if (audioList!=null&&audioList.size()>0){
            for (int i = 0; i < audioList.size(); i++) {
                RecordsBean   recordsBean =  audioList.get(i);
                Log.i("pictrue_content_listone",recordsBean.getTitle() +id+"    "+recordsBean.getId()+"  "+TextUtils.isEmpty(recordsBean.getAudioUrl()));
                  if (id==recordsBean.getId()){
                      Log.i("pictrue_stone_title",recordsBean.getTitle());
                      recordsBeanNow = recordsBean;
                  }
            }

        }

        etSayContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()>0){
                    tvSend.setText("发送");
                    tvSend.setBackground(null);
                }else{
                    tvSend.setText("");
                    tvSend.setBackgroundResource(R.mipmap.talk);
                }

            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String tokenString  = (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN,"");
                if (!TextUtils.isEmpty(tokenString)&&recordsBeanNow!=null){
                    CommonModel.addEncourage(501, recordsBeanNow.getId(), new MyObserver<AwardDto>() {
                        @Override
                        public void onSuccess(BaseResponse<AwardDto> t) {
                            if (t.getCode()==0){
                                if (t.getData().getIsTrue()==0){
                                    try{
                                        ShowPopupWindow(t.getData(),"浏览图文");
                                    }catch (Exception e){

                                    }

                                }

                            }else {
                                Toast.makeText(PictrueContentComActivity.this,t.getMsg(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }
        }, 5000);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            back();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        if (recordsBeanNow!=null){
            Log.i("audiocontentres", recordsBeanNow.getPosition()+"");
            MainApp.setRecordsBean(recordsBeanNow);
            MainApp.setPalyerId(-1);
        }
        MainApp.setIsSeeStatus(isSeeStatus);
        finish();
    }

    private   RecordsBean   recordsBeanNow;


    public void palySelectedPosition(int position) {
   /*     RecordsBean pictrueBean  =pictrueList.get(position);
        id = pictrueBean.getId();
        recordsBeans.clear();
        pageNum=1;
        CommonModel.findNewsLessonsPlayDetail(observer,pictrueBean.getId(),  DimenUtils.px2dip(this,imageMaxWidth),"0");
        CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(pictrueBean.getId(), 1, pageNum, pageSize)), myObserver);*/
    }

    MyObserver<PalyDetailDto> observer = new MyObserver<PalyDetailDto>() {
        @Override
        public void onSuccess(BaseResponse<PalyDetailDto> t) {
            palyDetailDto = t.getData();
            if (palyDetailDto !=null){
                tvTitle.setText(palyDetailDto.getTitle());
                if (!TextUtils.isEmpty(palyDetailDto.getAudioUrl())){
                    rlAudio.setVisibility(View.VISIBLE);
                    tvAudioTitle.setText(palyDetailDto.getTitle());
                    initPlayerView(palyDetailDto.getAudioUrl());
                    MainApp.setPlayerRes(palyDetailDto);
                }else{
                    rlAudio.setVisibility(View.GONE);
                }
                initWeb(palyDetailDto.getShowContentHtmlUrl());
                if (palyDetailDto.getIsCollection() == 1){
                    ivCollect.setImageResource(R.mipmap.activity_like_press);
                }else{
                    ivCollect.setImageResource(R.mipmap.activity_like_default);
                }

            }else{
                ToastUtils.showToast(t.getMsg());
            }

        }


    };

    private void initPlayerView(String audioUrl) {
        try{
            if (handler!=null){
                handler.sendEmptyMessageDelayed(UPDATE,1000);
            }

        if (MainApp.getPlayService1()!=null){
            if (MainApp.getPlayService1().isPlaying()&& MainApp.getRecordsBean().getId()==palyDetailDto.getId()){

            }else if (MainApp.getRecordsBean().getId()!=palyDetailDto.getId()){
                MainApp.getPlayService1().play(audioUrl);
            }
        }else{
          bindPlayService(audioUrl);
        }

        }catch (Exception e){

        }

    }

    private boolean isBound = false;
    //绑定服务
    private void bindPlayService(String path) {
        if (isBound == false) {
            Intent intent = new Intent(this, PlayService.class);
            intent.putExtra("url",path);
            bindService(intent, conn, BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    //连接Activity和Service
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.PlayBinder playBinder = (PlayService.PlayBinder) service;
            PlayService playService = playBinder.getPlayService();
            MainApp.setPlayService1(playService);
            MainApp.setServiceConnection(conn);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MainApp.setPlayService1(null);
        }
    };

    private List<CommentBeanDTO.RecordsBean> recordsBeans = new ArrayList<>();
    private CommentBeanDTO commentBeanDTO;

    MyObserver  myObserver   =  new MyObserver <CommentBeanDTO>() {
        @Override
        public void onSuccess(BaseResponse<CommentBeanDTO> t) {
            if (t.getData()!=null){
                commentBeanDTO = t.getData();
                if (t.getData().getTotal()==0){
                    vLine.setVisibility(View.GONE);
                    llFollowUp.setVisibility(View.GONE);
                }else{
                    vLine.setVisibility(View.VISIBLE);
                    llFollowUp.setVisibility(View.VISIBLE);
                    tvFollowUp.setText("跟帖("+t.getData().getTotal()+")");
                }

                recordsBeans.addAll(t.getData().getRecords());
                t.getData().setRecords(recordsBeans);
                setAdapter();
                if (t.getData()!=null&&t.getData().getTotal()==recordsBeans.size()){
                    ToastUtils.showToast("已经加载到最后");
                }
            }

        }

    };

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PictrueContentComActivity.this, LinearLayoutManager.VERTICAL, false);
        evComment.setLayoutManager(linearLayoutManager);
         pictrueContentCommentAdapter = new PictrueContentCommentAdapter(recordsBeans, PictrueContentComActivity.this);
        pictrueContentCommentAdapter.setVisibilityHuifu(View.VISIBLE);
        evComment.setAdapter(pictrueContentCommentAdapter);


    }

    @Override
    public void showFindNewsLessonsSpecialColumnById(BaseResponse<LessonsSpecialColumnDto>  lessonsSpecialColumnDto) {

    }

    @Override
    public void showFindNewsLessonsById(BaseResponse<NewsLessonsDetailDTO> data) {



    }

    @Override
    public void showPageNewsLessonsById(BaseResponse<ArrayList<RecordsBean>> lessonsDTOBaseResponse) {

    }





    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PleyerEvent messageEvent) {
        if (handler!=null){
            handler.sendEmptyMessage(MUSICDURATION);
        }

    }
    private final int MUSICDURATION = 0x1;    //获取歌曲播放时间标志
    private final int UPDATE = 0x2;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MUSICDURATION:
                    tvTime.setText(formatTimeFromProgress(MainApp.getPlayService1().getDuration()));
                    break;
                case UPDATE:
                    try {
                        tvTime.setText(formatTimeFromProgress(MainApp.getPlayService1().getDuration()));
                        tvTime1.setText(formatTimeFromProgress(MainApp.getPlayService1().getCurrentPosition()));
                      if (MainApp.getPlayService1().getCurrentPosition()>=(MainApp.getPlayService1().getDuration()-300)){
                       /*   MainApp.getPlayService1().pause();*/

                            int position = recordsBeanNow.getPosition();
                            if (position==audioList.size()-1){
                              Toast.makeText(PictrueContentComActivity.this,"亲！已经到最后一首了哦！",Toast.LENGTH_SHORT).show();
                                return ;
                            }
                            recordsBeanNow  =audioList.get(position+1);
                            CommonModel.findNewsLessonsPlayDetail(observer,recordsBeanNow.getId(),  DimenUtils.px2dip(PictrueContentComActivity.this, imageMaxWidth),"0");
                            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(recordsBeanNow.getId(),1,pageNum,pageSize)),myObserver);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //handler.sendEmptyMessageDelayed(UPDATE);
                    if (handler!=null){
                        handler.sendEmptyMessageDelayed(UPDATE,1000);
                    }

                    break;
            }
        }
    };

    //解绑服务
    private void unbindPlayService() {
        if (isBound == true) {
            isBound = false;
            unbindService(conn);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeMessages(UPDATE);
            handler= null;
        }
        unbindPlayService();
    }

    private String formatTimeFromProgress(int progress) {
        // 总的秒数
        int msecTotal = progress / 1000;
        int min = msecTotal / 60;
        int msec = msecTotal % 60;
        String minStr = min < 10 ? "0" + min : "" + min;
        String msecStr = msec < 10 ? "0" + msec : "" + msec;
        return minStr + ":" + msecStr;
    }
    @OnClick({R.id.iv_collect, R.id.iv_shard,R.id.tv_send,R.id.iv_palyer})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_palyer:
                if (MainApp.getPlayService1()!=null){
                    if (MainApp.getPlayService1().isPlaying()){
                        ivPlayer.setImageResource(R.mipmap.radio_play_pictrue);
                        MainApp.getPlayService1().pause();
                    }else{
                        if (handler!=null){
                            handler.sendEmptyMessage(UPDATE);
                        }

                        MainApp.getPlayService1().start();
                        ivPlayer.setImageResource(R.mipmap.radio_pause_pictrue);
                    }

                }
                break;


            case R.id.tv_send:
                if (addReplyBean!=null){
                    String sendContent =   etSayContent.getText().toString().trim();
                    if (!TextUtils.isEmpty(sendContent)){
                        addReplyBean.setContent(sendContent);
                        String json = GsonUtil.GsonString(addReplyBean);

                        CommonModel.addContentReply(json, commentReplyListBeanMyObserver);
                    }

                }else{
                    String  content =  etSayContent.getText().toString().trim();
                    if (!TextUtils.isEmpty(content)&&recordsBeanNow!=null){
                        AddCommentParams addCommentParams = new AddCommentParams();
                        addCommentParams.setObjectType(1);
                        addCommentParams.setContent(content);
                        addCommentParams.setObjectId(recordsBeanNow.getId());
                        CommonModel.addNewsLessonsComment(GsonUtil.GsonString(addCommentParams), observerComentPictrue);
                    }

                }

                break;
            case R.id.iv_shard:

                share();
                break;
            case R.id.iv_collect:
                if (palyDetailDto.getIsCollection() == 1){
                    CommonModel.confirmOrCancelNewsLessonsCollectionOrShare(palyDetailDto.getId(),0,0,0, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> baseResponse) {
                            palyDetailDto.setIsCollection(0);
                            ivCollect.setImageResource(R.mipmap.activity_like_default);
                            ToastUtils.showToast(baseResponse.getMsg());
                        }

                    });
                }else{
                    CommonModel.confirmOrCancelNewsLessonsCollectionOrShare(palyDetailDto.getId(),0,0,1, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> baseResponse) {
                            palyDetailDto.setIsCollection(1);
                            ivCollect.setImageResource(R.mipmap.activity_like_press);
                        }


                    });
                }

                break;
        }
    }

    MyObserver<CommentReplyListBean> commentReplyListBeanMyObserver = new MyObserver<CommentReplyListBean>() {
        @Override
        public void onSuccess(BaseResponse<CommentReplyListBean> t) {
            CommentReplyListBean commentReplyListBean = t.getData();
            if (commentReplyListBean == null) {
                return;
            }
            if (commentBeanDTO != null && commentBeanDTO.getRecords() != null && commentBeanDTO.getRecords().size() > 0) {
                List<CommentBeanDTO.RecordsBean> records = commentBeanDTO.getRecords();
                int newsLessonsCommentId = commentReplyListBean.getNewsLessonsCommentId();
                for (int i = 0; i < records.size(); i++) {
                    CommentBeanDTO.RecordsBean recordsBean = records.get(i);
                    int id = recordsBean.getId();
                    if (newsLessonsCommentId == id) {
                        if (recordsBean != null) {
                            if (recordsBean.getCommentReplyList() != null) {

                                recordsBean.getCommentReplyList().add(0, commentReplyListBean);
                                recordsBean.setReplyCount(recordsBean.getCommentReplyList().size());
                            }
                            pictrueContentCommentAdapter = new PictrueContentCommentAdapter(commentBeanDTO.getRecords(), PictrueContentComActivity.this);
                            evComment.setAdapter(pictrueContentCommentAdapter);
                        }

                    }
                }
            }
            KeyboardUtils.hideSoftInput(PictrueContentComActivity.this);
            etSayContent.setText("");
            addReplyBean = null;
        }

    };
    MyObserver<CommentBeanDTO.RecordsBean> observerComentPictrue = new MyObserver<CommentBeanDTO.RecordsBean>() {
        @Override
        public void onSuccess(BaseResponse<CommentBeanDTO.RecordsBean> t) {
            if (t.getCode()==0){
                recordsBeans.add(0, t.getData());
                setAdapter();
                KeyboardUtils.hideSoftInput(PictrueContentComActivity.this);
                etSayContent.setText("");

                String tokenString  = (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN,"");
                if (!TextUtils.isEmpty(tokenString)){
                    CommonModel.addEncourage(302, recordsBeanNow.getId(), new MyObserver<AwardDto>() {
                        @Override
                        public void onSuccess(BaseResponse<AwardDto> t) {
                            if (t.getCode()==0){
                                if (t.getData().getIsTrue()==0){
                                    ShowPopupWindow(t.getData(),"评论");
                                }

                            }else {
                                Toast.makeText(PictrueContentComActivity.this,t.getMsg(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }else {
                Toast.makeText(PictrueContentComActivity.this,t.getMsg(),Toast.LENGTH_SHORT).show();
            }

        }


    };

    private PopupWindow mPopupWindow;
    private  View popView;
    private TextView  tvType;
    private void ShowPopupWindow(final AwardDto awardDto, String text) {
        popView = LayoutInflater.from(PictrueContentComActivity.this).inflate(com.blockadm.common.R.layout.layout_share_award, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setContentView(popView);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // PopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, com.blockadm.adm.R.drawable.popupwindow_background));
        mPopupWindow.setAnimationStyle(com.blockadm.adm.R.style.MyPopupWindow_anim_style);
        tvType = (TextView) popView.findViewById(com.blockadm.adm.R.id.tv_type);//拍摄照片
        tvType.setText(text);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        mPopupWindow.setOutsideTouchable(false);  //设置点击屏幕其它地方弹出框消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        popView.setFocusable(false);
        mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.dismiss();
                tvType.setText("+"+awardDto.getAwardDiamond()+"A钻");
                mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();

                    }
                },2000);


            }
        }, 5000);
    }
    private void share() {
        DetailShareDialog detailShareDialog  = new DetailShareDialog(this);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image = new UMImage(PictrueContentComActivity.this, palyDetailDto.getCoverImgs());//网络图片
                UserInfoDto userInfoDto = (UserInfoDto) ACache.get(PictrueContentComActivity.this).getAsObject("userInfoDto");
                int memberId =0;
                if (userInfoDto!=null){
                    memberId = userInfoDto.getMemberId();
                }
          /*      String clickPath = "";
                if (palyDetailDto.getNlscType()==0){

                }else if (palyDetailDto.getNlscType()==1){
                    clickPath = ApiService.BASR_URL_RELEASE+"/news/visitor/share/shareUrl?contentType=3&objectId="+palyDetailDto.getId()+"&memberId="+memberId;
                }*/

                String  clickPath = ApiService.BASR_URL_RELEASE+"/news/visitor/share/shareUrl?contentType=2&objectId="+palyDetailDto.getId()+"&memberId="+memberId;

                UMWeb web = new UMWeb(clickPath);
                web.setTitle(palyDetailDto.getTitle());//标题
                web.setThumb(image);  //缩略图
                if (TextUtils.isEmpty(palyDetailDto.getSubtitle())){
                    web.setDescription("让价值的创造者拥有价值");//描述
                }else{
                    web.setDescription(palyDetailDto.getSubtitle());//描述
                }


                switch (type){
                    case 1:
                        new ShareAction(PictrueContentComActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();

                        break;
                    case 2:
                        new ShareAction(PictrueContentComActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 3:
                        new ShareAction(PictrueContentComActivity.this).setPlatform(SHARE_MEDIA.QQ)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 4:
                        new ShareAction(PictrueContentComActivity.this).setPlatform(SHARE_MEDIA.SINA)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                }

            }
        });
        detailShareDialog.show();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(PictrueContentComActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(PictrueContentComActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(PictrueContentComActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };



    private void initWeb(String url) {
        WebSettings webSettings = wvContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);//设置适应Html5的一些方法
        webSettings.setBlockNetworkImage(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvContent.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        wvContent.setWebChromeClient(new WebChromeClient());
        wvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wvContent.getLayoutParams();
                params.width =ScreenUtils.getScreenWidth(PictrueContentComActivity.this)- DimenUtils.dip2px(PictrueContentComActivity.this,10);
               // params.rightMargin = DimenUtils.dip2px(PictrueContentComActivity.this,10);
               // params.height = ScreenUtils.getScreenHeight(PictrueContentComActivity.this) ;
                wvContent.setLayoutParams(params);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        wvContent.loadUrl(url);

    }
   private AddReplyBean addReplyBean;
    public void addReply(AddReplyBean addReplyBean) {
        KeyboardUtils.showSoftInput(etSayContent);
        etSayContent.setText(addReplyBean.getContent());
        etSayContent.setSelection(addReplyBean.getContent().length());
        this.addReplyBean = addReplyBean;
    }
}

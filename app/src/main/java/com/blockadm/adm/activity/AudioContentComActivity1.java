package com.blockadm.adm.activity;

import com.blockadm.common.base.BaseComActivity;

/**
 * Created by hp on 2019/1/28.
 */

public class AudioContentComActivity1 extends BaseComActivity {

/* *//*   @BindView(R.id.tilte)
    BaseTitleBar tilte;*//*

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_shard)
    ImageView ivShard;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_banner)
    RoundImageView ivBanner;

    @BindView(R.id.tv_follow_up)
    TextView tvFollowUp;
    @BindView(R.id.tv_multiple)
    TextView tvMultiple;

    @BindView(R.id.ll_follow_up)
    LinearLayout llFollowUp;
    @BindView(R.id.ev_comment)
    FullRecyclerView  evComment;
    @BindView(R.id.rl_root)
    FScrollView fsl;
    @BindView(R.id.et_say_content)
    EditText etSayContent;
    @BindView(R.id.tv_send)
    CheckEmptyTextView tvSend;
    @BindView(R.id.iv_leftplay)
    ImageView ivLeftplay;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_rightplay)
    ImageView ivRightplay;

    @BindView(R.id.playSeekBar)
    SeekBar seekBar;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_total_progress)
    TextView tvTotalProgress;
    private int pageSize = 20;
    private int pageNum = 1;
    private int index = 0;
    private RecordsBean audioBean;
    private ArrayList<RecordsBean> audioList;
    private AudioListDialog audioListDialog;
    private int isSeeStatus;
    private int id=-1;
    private int imageMaxWidth;
    private  int nowPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictrue_content3);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        audioBean = (RecordsBean) getIntent().getSerializableExtra("recordsBean");
        if (audioBean!=null){
            nowPosition = audioBean.getPosition();
        }
        isSeeStatus = getIntent().getIntExtra("isSeeStatus",-1);
        imageMaxWidth = ScreenUtils.getScreenWidth(getApplicationContext());
        id = getIntent().getIntExtra("id",-1);
        if (id!=-1){
            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(id, 1, pageNum, pageSize)), myObserver);
            CommonModel.findNewsLessonsPlayDetail(observer,id, imageMaxWidth,"1");
        }
        if (audioBean!=null){
            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(audioBean.getId(), 1, pageNum, pageSize)), myObserver);
            CommonModel.findNewsLessonsPlayDetail(observer, audioBean.getId(), imageMaxWidth,"1");
        }


        audioList = (ArrayList<RecordsBean>) ACache.get(this).getAsObject(ConstantUtils.AUDIO_LIST);



      *//*  if (id==-1){
            tilte.setOnRightOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (audioListDialog==null){
                        audioListDialog = new AudioListDialog(AudioContentComActivity.this,audioList,isSeeStatus);
                    }
                    audioListDialog.show();
                }
            });

        }else{
            tilte.setRightimageShow(View.GONE);
            ivLeftplay.setEnabled(false);
            ivRightplay.setEnabled(false);
        }*//*
        // 滑动加载
        fsl.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        index++;
                        break;
                    default:
                        break;
                }
                if (event.getAction() == MotionEvent.ACTION_UP && index > 0) {
                    index = 0;
                    View view = ((ScrollView) v).getChildAt(0);
                    if (view.getMeasuredHeight() <= v.getScrollY() + v.getHeight()) {
                        pageNum++;
                        if (id!=-1){
                            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(id, 1, pageNum, pageSize)), myObserver);
                        }
                        if (audioBean!=null){
                            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(audioBean.getId(), 1, pageNum, pageSize)), myObserver);
                        }

                    }
                }
                return false;
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MainApp.getPlayService1().setTo(progress*1000);
                    seekBar.setProgress(progress);
                    //tvProgress.setText(formatTimeFromProgress(MainApp.getPlayService1().getCurrentPosition()));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private boolean isBound = false;
    //绑定服务
    private void bindPlayService(String path) {
      if (isBound == false) {
            Intent intent = new Intent(AudioContentComActivity1.this, PlayService.class);
            intent.putExtra("url",path);
            bindService(intent, conn, BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    //解绑服务
    private void unbindPlayService() {
        if (isBound == true) {
            isBound = false;
            MainApp.getPlayService1().pause();
            unbindService(conn);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PleyerEvent messageEvent) {
        handler.sendEmptyMessage(MUSICDURATION);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        id=-1;

    }

    private final int MUSICDURATION = 0x1;    //获取歌曲播放时间标志
    private final int UPDATE = 0x2;
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
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MUSICDURATION:
                    tvTotalProgress.setText(formatTimeFromProgress(MainApp.getPlayService1().getDuration()));
                    seekBar.setMax(MainApp.getPlayService1().getDuration()/1000);
                    break;
                case UPDATE:
                    try {
                        tvProgress.setText(formatTimeFromProgress(MainApp.getPlayService1().getCurrentPosition()));
                        seekBar.setProgress(MainApp.getPlayService1().getCurrentPosition()/1000);
                        if (MainApp.getPlayService1().getCurrentPosition()>=MainApp.getPlayService1().getDuration()){
                            onViewClicked(ivRightplay);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //handler.sendEmptyMessageDelayed(UPDATE);
                    handler.sendEmptyMessageDelayed(UPDATE,1000);
                    break;
            }
        }
    };
  private PalyDetailDto palyDetailDto1;
    MyObserver<PalyDetailDto> observer = new MyObserver<PalyDetailDto>() {
        @Override
        public void onSuccess(BaseResponse<PalyDetailDto> t) {
            PalyDetailDto  palyDetailDto =  t.getData();
            if (palyDetailDto!=null){
                if (audioBean!=null){
                    audioBean.setPosition(nowPosition);
                }
                try{
                    palyDetailDto1 =palyDetailDto;
                    tvTitle.setText(palyDetailDto.getTitle());
                    ImageUtils.loadImageView(palyDetailDto.getCoverImgs(), ivBanner);
                    PalyDetailDto  palyDetailDto2 = MainApp.getPlayerRes();
                    if (palyDetailDto2!=null&&palyDetailDto2.getId()!=palyDetailDto1.getId()){

                        if (MainApp.getPlayService1().isPlaying()){
                            ivPlay.setImageResource(R.mipmap.ico_play_stop);
                        }else{
                            ivPlay.setImageResource(R.mipmap.ico_play_go);
                        }
                        MainApp.getPlayService1().play(palyDetailDto1.getAudioUrl());
                        handler.sendEmptyMessage(UPDATE);
                    }
                   else if (MainApp.getPlayService1()!=null){
                        if (MainApp.getPlayService1().isPlaying()){
                            ivPlay.setImageResource(R.mipmap.ico_play_stop);
                        }else{
                            ivPlay.setImageResource(R.mipmap.ico_play_go);
                        }

                        tvProgress.setText(formatTimeFromProgress(MainApp.getPlayService1().getCurrentPosition()));
                        tvTotalProgress.setText(formatTimeFromProgress(MainApp.getPlayService1().getDuration()));
                        seekBar.setMax(MainApp.getPlayService1().getDuration()/1000);
                        seekBar.setProgress(MainApp.getPlayService1().getCurrentPosition()/1000);
                        handler.sendEmptyMessage(UPDATE);
                    }else{
                        ivPlay.setImageResource(R.mipmap.ico_play_go);
                        bindPlayService(palyDetailDto1.getAudioUrl());
                    }
                    MainApp.setPlayerRes(palyDetailDto1);
                }catch(Exception e){
                    showToast(AudioContentComActivity1.this,e.toString());

                }
            }else{
               Toast.makeText(AudioContentComActivity1.this,t.getMsg(),Toast.LENGTH_SHORT).show();
            }

        }


    };
    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AudioContentComActivity1.this, LinearLayoutManager.VERTICAL, false);
        evComment.setLayoutManager(linearLayoutManager);
        PictrueContentCommentAdapter pictrueContentCommentAdapter = new PictrueContentCommentAdapter(recordsBeans, AudioContentComActivity1.this);
        evComment.setAdapter(pictrueContentCommentAdapter);

    }


   private BeishuDialog  beishuDialog;
    @OnClick({R.id.iv_leftplay, R.id.iv_play, R.id.iv_rightplay,R.id.tv_send,R.id.iv_back, R.id.iv_shard, R.id.iv_collect,R.id.tv_multiple})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_multiple:
                if (beishuDialog==null){
                    beishuDialog  = new BeishuDialog(this);
                }
                beishuDialog.show();
                beishuDialog.setSexSaveListner(new BeishuDialog.TypeSaveListner() {
                    @Override
                    public void onSaveClick(String textType, int type) {
                        tvMultiple.setText(textType);

                        switch (type){
                            case 1:

                                break;

                            case 2:

                                break;


                            case 3:

                                break;


                            case 4:

                                break;

                            case 5:

                                break;
                        }

                        beishuDialog.dismiss();
                    }
                });
                break;

            case R.id.iv_back:
                // MainApp.getPlayService1().getMediaPlayer().set
                if (id!=-1){
                    Log.i("audiocontentres", MainApp.getRecordsBean().getId()+"");
                    MainApp.setPalyerId(id);
                    MainApp.setRecordsBean(null);
                }else if (audioBean!=null){
                    Log.i("audiocontentres", audioBean.getPosition()+"");
                    MainApp.setRecordsBean(audioBean);
                    MainApp.setPalyerId(-1);
                }
                MainApp.setIsSeeStatus(isSeeStatus);
                finish();
                break;
            case R.id.iv_shard:
                 if (palyDetailDto1!=null){
                     share();
                 }
                break;
            case R.id.iv_leftplay:
                int position = audioBean.getPosition();
                if (position==0){
                    ToastUtils.showToast("亲！已经到第一首了哦！");
                    return ;
                }
                nowPosition =position-1;
                audioBean  =audioList.get(nowPosition);
                CommonModel.findNewsLessonsPlayDetail(observer, audioBean.getId(),imageMaxWidth,"1");
                recordsBeans.clear();
                pageNum =1;
                CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(audioBean.getId(), 1, pageNum, pageSize)), myObserver);
                break;

            case R.id.tv_send:
                String  content =  etSayContent.getText().toString().trim();
                if (!TextUtils.isEmpty(content)){
                    AddCommentParams addCommentParams = new AddCommentParams();
                    addCommentParams.setObjectType(1);
                    addCommentParams.setContent(content);
                    if (id!=-1){
                        addCommentParams.setObjectId(id);
                    }
                    if (audioBean!=null){
                        addCommentParams.setObjectId(audioBean.getId());
                    }

                    CommonModel.addNewsLessonsComment(GsonUtil.GsonString(addCommentParams), new MyObserver<CommentBean>() {
                        @Override
                        public void onSuccess(BaseResponse<CommentBean> t) {
                            recordsBeans.add(0,t.getData());
                            setAdapter();
                            KeyboardUtils.hideSoftInput(AudioContentComActivity1.this);
                            etSayContent.setText("");
                        }
                    });
                }
                break;
            case R.id.iv_play:
                try{
                    if (MainApp.getPlayService1().isPlaying()){
                        MainApp.getPlayService1().pause();
                        ivPlay.setImageResource(R.mipmap.ico_play_go);
                    }else{
                        MainApp.getPlayService1().start();
                        handler.sendEmptyMessage(UPDATE);
                        ivPlay.setImageResource(R.mipmap.ico_play_stop);
                    }
                }catch(Exception e){
                    showToast(AudioContentComActivity1.this,e.toString());

                }
                break;
            case R.id.iv_rightplay:

                int positionEnd = audioBean.getPosition();
                if (positionEnd==audioList.size()-1){
                    ToastUtils.showToast("亲！已经到最后一首了哦！");
                    return ;
                }
                   nowPosition = positionEnd+1;
                    audioBean  =audioList.get(nowPosition);
                    CommonModel.findNewsLessonsPlayDetail(observer, audioBean.getId(),imageMaxWidth,"1");
                   recordsBeans.clear();
                   pageNum =1;
                    CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(audioBean.getId(), 1, pageNum, pageSize)), myObserver);
                break;
            case R.id.iv_collect:

                if (palyDetailDto1.getIsCollection() == 1){
                    CommonModel.confirmOrCancelNewsLessonsCollectionOrShare(palyDetailDto1.getId(),0,0,0, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> baseResponse) {
                            palyDetailDto1.setIsCollection(0);
                            ivCollect.setImageResource(R.mipmap.activity_like_default);
                        }

                    });
                }else{
                    CommonModel.confirmOrCancelNewsLessonsCollectionOrShare(palyDetailDto1.getId(),0,0,1, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> baseResponse) {
                            palyDetailDto1.setIsCollection(1);
                            ivCollect.setImageResource(R.mipmap.activity_like_press);
                            palyDetailDto1.setIsCollection(1);
                        }


                    });
                }

                break;
        }
    }


    public void palySelectedPosition(int position) {
        nowPosition = position;
      audioBean  =audioList.get(position);
        CommonModel.findNewsLessonsPlayDetail(observer, audioBean.getId(),imageMaxWidth,"1");
        recordsBeans.clear();
        pageNum =1;
        CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(audioBean.getId(), 1, pageNum, pageSize)), myObserver);
        if (audioListDialog!=null){
            audioListDialog.dismiss();
        }
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


    private List<CommentBean> recordsBeans = new ArrayList<>();

    MyObserver myObserver = new MyObserver<CommentBeanDTO>() {
        @Override
        public void onSuccess(BaseResponse<CommentBeanDTO> t) {
            if (t.getData() != null) {
                if (t.getData().getTotal() == 0) {
                    llFollowUp.setVisibility(View.GONE);
                } else {
                    llFollowUp.setVisibility(View.VISIBLE);
                    tvFollowUp.setText("跟帖(" + t.getData().getTotal() + ")");
                }
                recordsBeans.addAll(t.getData().getRecords());
                setAdapter();
                if (t.getData() != null && t.getData().getTotal() == recordsBeans.size()) {
                    ToastUtils.showToast("已经加载到最后");
                }
            }

        }

    };

    private void share() {
        DetailShareDialog detailShareDialog  = new DetailShareDialog(this);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image = new UMImage(AudioContentComActivity1.this, palyDetailDto1.getCoverImgs());//网络图片
                UserInfoDto userInfoDto = (UserInfoDto) ACache.get(AudioContentComActivity1.this).getAsObject("userInfoDto");
                int memberId =0;
                if (userInfoDto!=null){
                    memberId = userInfoDto.getMemberId();
                }
                String clickPath = "";
                if (palyDetailDto1.getNlscType()==0){
                     clickPath = ApiService.BASR_URL_RELEASE+"/news/visitor/share/shareUrl?contentType=2&objectId="+palyDetailDto1.getId()+"&memberId="+memberId;
                }else if (palyDetailDto1.getNlscType()==1){
                    clickPath = ApiService.BASR_URL_RELEASE+"/news/visitor/share/shareUrl?contentType=3&objectId="+palyDetailDto1.getId()+"&memberId="+memberId;
                }

                UMWeb web = new UMWeb(clickPath);
                web.setTitle(palyDetailDto1.getTitle());//标题
                web.setThumb(image);  //缩略图
                 web.setDescription(palyDetailDto1.getSubtitle());//描述


                switch (type){
                    case 1:
                        new ShareAction(AudioContentComActivity1.this).setPlatform(SHARE_MEDIA.WEIXIN)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();

                        break;
                    case 2:
                        new ShareAction(AudioContentComActivity1.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 3:
                        new ShareAction(AudioContentComActivity1.this).setPlatform(SHARE_MEDIA.QQ)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 4:
                        new ShareAction(AudioContentComActivity1.this).setPlatform(SHARE_MEDIA.SINA)
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
        *//**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        *//**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(AudioContentComActivity1.this,"成功了",Toast.LENGTH_LONG).show();
        }

        *//**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         *//*
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AudioContentComActivity1.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        *//**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AudioContentComActivity1.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };*/
}

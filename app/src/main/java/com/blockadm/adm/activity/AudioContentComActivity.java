package com.blockadm.adm.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.PictrueContentCommentAdapter;
import com.blockadm.adm.dialog.AudioListDialog;
import com.blockadm.adm.dialog.BeishuDialog;
import com.blockadm.adm.dialog.DetailShareDialog;
import com.blockadm.adm.event.PleyerEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.service.PlayService;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.AwardDto;
import com.blockadm.common.bean.CommentBeanDTO;

import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.PalyDetailDto;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.params.AddCommentParams;
import com.blockadm.common.bean.params.AddReplyBean;
import com.blockadm.common.bean.params.CommentBeanParams;
import com.blockadm.common.comstomview.CheckEmptyTextView;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.FScrollView;
import com.blockadm.common.comstomview.FullRecyclerView;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
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

public class AudioContentComActivity extends BaseComActivity {

 /*   @BindView(R.id.tilte)
    BaseTitleBar tilte;*/

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_shard)
    ImageView ivShard;
    //    @BindView(R.id.iv_collect)
    //    ImageView ivCollect;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_multiple)
    TextView tvMultiple;
    @BindView(R.id.iv_banner)
    CircleImageView ivBanner;
    @BindView(R.id.ll_follow_up)
    LinearLayout llFollowUp;

    @BindView(R.id.tv_follow_up)
    TextView tvFollowUp;

    @BindView(R.id.ev_comment)
    FullRecyclerView evComment;

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

    @BindView(R.id.tv_imagetext)
    TextView tvImageText;
    @BindView(R.id.tv_total_progress)
    TextView tvTotalProgress;
    private RecordsBean audioBean;
    private ArrayList<RecordsBean> audioList;
    private AudioListDialog audioListDialog;
    private int isSeeStatus;
    private int id = -1;
    private int imageMaxWidth;
    private int nowPosition;
    private int pageSize = 20;
    private int pageNum = 1;
    private int index = 0;

    private PowerManager.WakeLock mWakeLock;
    private AddReplyBean addReplyBean;
    private String mPlayMsg;
    private boolean isNoPlayLessens; // true:当前课程无音频信息

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictrue_content2);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        audioBean = (RecordsBean) getIntent().getSerializableExtra("recordsBean");
        if (audioBean != null) {
            nowPosition = audioBean.getPosition();
        }
        isSeeStatus = getIntent().getIntExtra("isSeeStatus", -1);
        imageMaxWidth = ScreenUtils.getScreenWidth(getApplicationContext());
        id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            CommonModel.findNewsLessonsPlayDetail(observer, id, imageMaxWidth, "1");
            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(id, 1,
                    pageNum, pageSize)), myObserver);
        }
        if (audioBean != null) {
            CommonModel.findNewsLessonsPlayDetail(observer, audioBean.getId(), imageMaxWidth, "1");
            String json = GsonUtil.GsonString(new CommentBeanParams(audioBean.getId(), 1, pageNum
                    , pageSize));
            CommonModel.pageNewsLessonsComment(json, myObserver);
        }

        audioList = (ArrayList<RecordsBean>) ACache.get(this).getAsObject(ConstantUtils.AUDIO_LIST);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MainApp.getPlayService1().setTo(progress * 1000);
                    seekBar.setProgress(progress);
                    //tvProgress.setText(formatTimeFromProgress(MainApp.getPlayService1()
                    // .getCurrentPosition()));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        etSayContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    tvSend.setText("发送");
                    tvSend.setBackground(null);
                } else {
                    tvSend.setText("");
                    tvSend.setBackgroundResource(R.mipmap.talk);
                }

            }
        });
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
                        if (id != -1) {
                            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(id, 1, pageNum, pageSize)), myObserver);
                        }
                        if (audioBean != null) {
                            CommonModel.pageNewsLessonsComment(GsonUtil.GsonString(new CommentBeanParams(audioBean.getId(), 1, pageNum, pageSize)), myObserver);
                        }

                    }
                }
                return false;
            }
        });
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String tokenString =
                        (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
                if (!TextUtils.isEmpty(tokenString) && audioBean != null) {
                    CommonModel.addEncourage(501, audioBean.getId(), new MyObserver<AwardDto>() {
                        @Override
                        public void onSuccess(BaseResponse<AwardDto> t) {
                            if (t.getCode() == 0) {
                                if (t.getData().getIsTrue() == 0) {
                                    try {
                                        ShowPopupWindow(t.getData(), "收听课程");
                                    } catch (Exception e) {
                                    }
                                }

                            } else {
                                Toast.makeText(AudioContentComActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }
        }, 10000);
    }


    private boolean isBound = false;

    //绑定服务
    private void bindPlayService(String path) {

        try {
            if (isBound == false) {
                Intent intent = new Intent(AudioContentComActivity.this, PlayService.class);
                intent.putExtra("url", path);
                bindService(intent, conn, BIND_AUTO_CREATE);
                isBound = true;
            }
        } catch (Exception e) {

        }
    }

    //解绑服务
    private void unbindPlayService() {
        if (isBound == true) {
            isBound = false;
            unbindService(conn);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PleyerEvent messageEvent) {
        if (handler != null) {
            handler.sendEmptyMessage(MUSICDURATION);
        }
    }

    private final int MUSICDURATION = 0x1;    //获取歌曲播放时间标志
    private final int UPDATE = 0x2;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        id = -1;
        if (handler != null) {
            handler.removeMessages(UPDATE);
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }


        unbindPlayService();

    }


    //连接Activity和Service
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.PlayBinder playBinder = (PlayService.PlayBinder) service;
            PlayService playService = playBinder.getPlayService();
            MainApp.setPlayService1(playService);
            MainApp.setServiceConnection(conn);
            if (handler != null) {
                handler.sendEmptyMessageDelayed(UPDATE, 1000);
            }

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
                    seekBar.setMax(MainApp.getPlayService1().getDuration() / 1000);
                    break;
                case UPDATE:
                    try {
                        int currentPosition = MainApp.getPlayService1().getCurrentPosition();
                        int duration = MainApp.getPlayService1().getDuration();
                        tvProgress.setText(formatTimeFromProgress(currentPosition));
                        seekBar.setProgress(currentPosition / 1000);
                        Log.i("xxx", currentPosition + "                      " + duration);
                        Log.i("xxx",
                                "  ; isGoPictrueModel: " + isGoPictrueModel + " ; >= ？ " + (currentPosition >= (duration - 300)));

                        if (currentPosition >= (duration - 300) && duration != 0) {
                            if (!isGoPictrueModel) {
                                onViewClicked(ivRightplay);
                            }
                        }

                        if (MainApp.getPlayService1() != null && MainApp.getPlayService1().isPlaying()) {
                            ivPlay.setImageResource(R.mipmap.ico_play_stop);
                        } else {
                            ivPlay.setImageResource(R.mipmap.ico_play_go);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //handler.sendEmptyMessageDelayed(UPDATE);
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(UPDATE, 1000);
                    }

                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isGoPictrueModel = false;
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
    }

    private boolean isGoPictrueModel = false;
    private PalyDetailDto palyDetailDto1;
    MyObserver<PalyDetailDto> observer = new MyObserver<PalyDetailDto>() {
        @Override
        public void onSuccess(BaseResponse<PalyDetailDto> t) {
            PalyDetailDto palyDetailDto = t.getData();
            if (palyDetailDto != null) {
                if (audioBean != null) {
                    audioBean.setPosition(nowPosition);
                }
                try {
                    palyDetailDto1 = palyDetailDto;
                    tvTitle.setText(palyDetailDto.getTitle());
                    ImageUtils.loadImageView(palyDetailDto.getCoverImgs(), ivBanner);
                    PalyDetailDto palyDetailDto2 = MainApp.getPlayerRes();
                    if (palyDetailDto1.getContentType() != 3) {
                        tvImageText.setVisibility(View.GONE);
                    }
                    if (palyDetailDto2 != null && palyDetailDto2.getId() != palyDetailDto1.getId()) {

                        if (MainApp.getPlayService1().isPlaying()) {
                            ivPlay.setImageResource(R.mipmap.ico_play_stop);
                        } else {
                            ivPlay.setImageResource(R.mipmap.ico_play_go);
                        }
                        MainApp.getPlayService1().play(palyDetailDto1.getAudioUrl());
                        if (handler != null) {
                            handler.sendEmptyMessage(UPDATE);
                        }
                    } else if (MainApp.getPlayService1() != null) {
                        if (MainApp.getPlayService1().isPlaying()) {
                            ivPlay.setImageResource(R.mipmap.ico_play_stop);
                        } else {
                            ivPlay.setImageResource(R.mipmap.ico_play_go);
                        }
                        tvProgress.setText(formatTimeFromProgress(MainApp.getPlayService1().getCurrentPosition()));
                        tvTotalProgress.setText(formatTimeFromProgress(MainApp.getPlayService1().getDuration()));
                        seekBar.setMax(MainApp.getPlayService1().getDuration() / 1000);
                        seekBar.setProgress(MainApp.getPlayService1().getCurrentPosition() / 1000);
                        if (handler != null) {
                            handler.sendEmptyMessage(UPDATE);
                        }

                    } else {
                        bindPlayService(palyDetailDto1.getAudioUrl());
                    }
                    MainApp.setPlayerRes(palyDetailDto1);
                    //                    if (palyDetailDto1.getIsCollection() == 1){
                    //                        ivCollect.setImageResource(R.mipmap
                    //                        .activity_like_press);
                    //                    }else{
                    //                        ivCollect.setImageResource(R.mipmap
                    //                        .activity_like_default);
                    //                    }
                    tvMultiple.setText("1.0x");
                    changeplayerSpeed(1.0f);


                } catch (Exception e) {
                    showToast(AudioContentComActivity.this, e.toString());
                }
            } else {
                mPlayMsg = t.getMsg();
                isNoPlayLessens = true;
                Toast.makeText(AudioContentComActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }


    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        MainApp.setIsSeeStatus(isSeeStatus);
        if (audioBean != null) {
            Log.i("audiocontentres", audioBean.getPosition() + "");
            MainApp.setRecordsBean(audioBean);
            MainApp.setPalyerId(-1);
        } else if (id != -1) {
            Log.i("audiocontentres", id + "");
            MainApp.setPalyerId(id);
            MainApp.setRecordsBean(null);
        }

        finish();
    }

    private BeishuDialog beishuDialog;

    @OnClick({R.id.iv_leftplay, R.id.iv_play, R.id.iv_rightplay, R.id.iv_back, R.id.iv_shard,
            R.id.tv_multiple, R.id.iv_lsit, R.id.tv_back_list, R.id.tv_imagetext, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_imagetext:
                if (audioBean != null) {
                    isGoPictrueModel = true;
                    Intent intent = new Intent(this, PictrueContentComActivity.class);
                    intent.putExtra("isSeeStatus", isSeeStatus);
                    intent.putExtra("id", audioBean.getId());
                    startActivity(intent);
                }
                break;
            case R.id.tv_back_list:
                if (audioBean != null) {
                    Intent intent = new Intent(this, ColumnOneDetailComActivity.class);
                    intent.putExtra("id", audioBean.getNlscId());
                    startActivity(intent);
                }


                break;
            case R.id.tv_multiple:
                if (beishuDialog == null) {
                    beishuDialog = new BeishuDialog(this);
                }
                beishuDialog.show();
                beishuDialog.setSexSaveListner(new BeishuDialog.TypeSaveListner() {
                    @Override
                    public void onSaveClick(String textType, int type) {
                        switch (type) {
                            case 1:
                                tvMultiple.setText("0.7x");
                                changeplayerSpeed(0.7f);
                                break;
                            case 2:
                                tvMultiple.setText("1.0x");
                                changeplayerSpeed(1.0f);
                                break;

                            case 3:
                                tvMultiple.setText("1.25x");
                                changeplayerSpeed(1.2f);
                                break;


                            case 4:
                                tvMultiple.setText("1.5x");
                                changeplayerSpeed(1.4f);
                                break;

                            case 5:
                                tvMultiple.setText("2.0x");
                                changeplayerSpeed(1.8f);
                                break;
                        }

                        beishuDialog.dismiss();
                    }
                });
                break;

            case R.id.iv_lsit:
                if (audioListDialog == null) {
                    audioListDialog = new AudioListDialog(AudioContentComActivity.this, audioList
                            , isSeeStatus);
                }
                audioListDialog.show();

                break;

            case R.id.iv_back:
                back();
                break;
            case R.id.iv_shard:
                if (palyDetailDto1 != null) {
                    share();
                }
                break;
            case R.id.iv_leftplay:
                int position = audioBean.getPosition();
                if (position == 0) {
                    Toast.makeText(AudioContentComActivity.this, "亲！已经到第一首了哦！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                nowPosition = position - 1;
                audioBean = audioList.get(nowPosition);

                CommonModel.findNewsLessonsPlayDetail(observer, audioBean.getId(), imageMaxWidth,
                        "1");
                break;

            case R.id.iv_play:
                try {
                    if (isNoPlayLessens) {
                        showToast(AudioContentComActivity.this, mPlayMsg);
                        return;
                    }

                    if (MainApp.getPlayService1().isPlaying()) {
                        MainApp.getPlayService1().pause();
                        ivPlay.setImageResource(R.mipmap.ico_play_go);
                    } else {
                        MainApp.getPlayService1().start();
                        if (handler != null) {
                            handler.sendEmptyMessage(UPDATE);
                        }
                        ivPlay.setImageResource(R.mipmap.ico_play_stop);
                    }
                } catch (Exception e) {
                    showToast(AudioContentComActivity.this, e.toString());

                }
                break;
            case R.id.iv_rightplay:
                int positionEnd = audioBean.getPosition();
                if (positionEnd == audioList.size() - 1) {
                    ToastUtils.showToast("亲！已经到最后一首了哦！");
                    return;
                }
                nowPosition = positionEnd + 1;
                audioBean = audioList.get(nowPosition);
                if (audioBean.getContentType() == 1 || audioBean.getContentType() == 3) {
                    RecordsBean recordsBean = MainApp.getRecordsBean();
                    if (isSeeStatus == 1 && audioBean.getAccessStatus() != 0) {
                        //Toast.makeText(AudioContentComActivity.this,"请先购买，再体验！！！",Toast
                        // .LENGTH_SHORT).show();
                        audioBean = recordsBean;
                        MainApp.setRecordsBean(audioBean);
                    } else {
                        CommonModel.findNewsLessonsPlayDetail(observer, audioBean.getId(),
                                imageMaxWidth, "1");
                    }

                } else {
                    onViewClicked(ivRightplay);
                }

                break;
            //            case R.id.iv_collect:
            //              if (palyDetailDto1!=null){
            //                  if (palyDetailDto1.getIsCollection() == 1){
            //                      CommonModel.confirmOrCancelNewsLessonsCollectionOrShare
            //                      (palyDetailDto1.getId(),0,0,0, new MyObserver<String>() {
            //                          @Override
            //                          public void onSuccess(BaseResponse<String> baseResponse) {
            //                              palyDetailDto1.setIsCollection(0);
            //                              ivCollect.setImageResource(R.mipmap
            //                              .activity_like_default);
            //                          }
            //
            //                      });
            //                  }else{
            //                      CommonModel.confirmOrCancelNewsLessonsCollectionOrShare
            //                      (palyDetailDto1.getId(),0,0,1, new MyObserver<String>() {
            //                          @Override
            //                          public void onSuccess(BaseResponse<String> baseResponse) {
            //                              palyDetailDto1.setIsCollection(1);
            //                              ivCollect.setImageResource(R.mipmap
            //                              .activity_like_press);
            //                          }
            //
            //
            //                      });
            //                  }
            //
            //              }
            //
            //                break;

            case R.id.tv_send:
              /*  String  content =  etSayContent.getText().toString().trim();
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
                    CommonModel.addNewsLessonsComment(GsonUtil.GsonString(addCommentParams),
                    observerComment);
                }*/


                String content = etSayContent.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    if (addReplyBean != null) {
                        String sendContent = etSayContent.getText().toString().trim();
                        if (!TextUtils.isEmpty(sendContent)) {
                            addReplyBean.setContent(sendContent);
                            String json = GsonUtil.GsonString(addReplyBean);
                            CommonModel.addContentReply(json, commentReplyListBeanMyObserver);
                        }

                    } else {
                        if (!TextUtils.isEmpty(content) && audioBean != null) {
                            AddCommentParams addCommentParams = new AddCommentParams();
                            addCommentParams.setObjectType(1);
                            addCommentParams.setContent(content);
                            addCommentParams.setObjectId(audioBean.getId());
                            CommonModel.addNewsLessonsComment(GsonUtil.GsonString(addCommentParams), observerComment);
                        }

                    }
                }

                break;
        }
    }

    MyObserver<CommentReplyListBean> commentReplyListBeanMyObserver =
            new MyObserver<CommentReplyListBean>() {
                @Override
                public void onSuccess(BaseResponse<CommentReplyListBean> t) {
                    CommentReplyListBean commentReplyListBean = t.getData();
                    if (commentReplyListBean == null) {
                        return;
                    }
                    if (recordsBeans != null && recordsBeans.size() > 0) {
                        int newsLessonsCommentId = commentReplyListBean.getNewsLessonsCommentId();
                        for (int i = 0; i < recordsBeans.size(); i++) {
                            CommentBeanDTO.RecordsBean recordsBean = recordsBeans.get(i);
                            int id = recordsBean.getId();
                            if (newsLessonsCommentId == id) {
                                if (recordsBean != null) {
                                    if (recordsBean.getCommentReplyList() != null) {

                                        recordsBean.getCommentReplyList().add(0, commentReplyListBean);
                                        recordsBean.setReplyCount(recordsBean.getCommentReplyList().size());
                                    }
                                    setAdapter();
                                }

                            }
                        }
                    }
                    KeyboardUtils.hideSoftInput(AudioContentComActivity.this);
                    etSayContent.setText("");
                    addReplyBean = null;
                }

            };

    MyObserver<CommentBeanDTO.RecordsBean> observerComment =
            new MyObserver<CommentBeanDTO.RecordsBean>() {
                @Override
                public void onSuccess(BaseResponse<CommentBeanDTO.RecordsBean> t) {
                    if (t.getCode() == 0) {
                        recordsBeans.add(0, t.getData());
                        setAdapter();
                        KeyboardUtils.hideSoftInput(AudioContentComActivity.this);
                        etSayContent.setText("");


                        String tokenString =
                                (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
                        if (!TextUtils.isEmpty(tokenString)) {
                            CommonModel.addEncourage(302, audioBean.getId(), new MyObserver<AwardDto>() {
                                @Override
                                public void onSuccess(BaseResponse<AwardDto> t) {
                                    if (t.getCode() == 0) {
                                        if (t.getData().getIsTrue() == 0) {
                                            ShowPopupWindow(t.getData(), "评论");
                                        }
                                    } else {
                                        Toast.makeText(AudioContentComActivity.this, t.getMsg(),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    } else {
                        Toast.makeText(AudioContentComActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }

            };

    private PopupWindow mPopupWindow;
    private View popView;
    private TextView tvType;

    private void ShowPopupWindow(final AwardDto awardDto, String text) {
        popView =
                LayoutInflater.from(AudioContentComActivity.this).inflate(com.blockadm.common.R.layout.layout_share_award, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setContentView(popView);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // PopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, com.blockadm.adm
        // .R.drawable.popupwindow_background));
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
                tvType.setText("+" + awardDto.getAwardDiamond() + "A钻");
                mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                        0, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();

                    }
                }, 2000);


            }
        }, 2000);
    }

    private void changeplayerSpeed(float speed) {
        try {
            // this checks on API 23 and up
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (MainApp.getPlayService1() != null && MainApp.getPlayService1().isPlaying()) {
                    MainApp.getPlayService1().getMediaPlayer().setPlaybackParams(MainApp.getPlayService1().getMediaPlayer().getPlaybackParams().setSpeed(speed));
                    MainApp.getPlayService1().pause();
                    MainApp.getPlayService1().start();
                } else if (MainApp.getPlayService1() != null && MainApp.getPlayService1().getMediaPlayer() != null) {
                    MainApp.getPlayService1().getMediaPlayer().setPlaybackParams(MainApp.getPlayService1().getMediaPlayer().getPlaybackParams().setSpeed(speed));
                    MainApp.getPlayService1().pause();
                }
            } else {
                Toast.makeText(this, "请升级手机系统", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception E) {
            Log.i("changeplayerSpeed", E.toString());
        }
    }

    public void palySelectedPosition(int position) {
        nowPosition = position;
        audioBean = audioList.get(position);
        CommonModel.findNewsLessonsPlayDetail(observer, audioBean.getId(), imageMaxWidth, "1");
        if (audioListDialog != null) {
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


    private void share() {
        DetailShareDialog detailShareDialog = new DetailShareDialog(this);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image = new UMImage(AudioContentComActivity.this,
                        palyDetailDto1.getCoverImgs());//网络图片
                UserInfoDto userInfoDto =
                        (UserInfoDto) ACache.get(AudioContentComActivity.this).getAsObject(
                                "userInfoDto");
                int memberId = 0;
                if (userInfoDto != null) {
                    memberId = userInfoDto.getMemberId();
                }
           /*     String clickPath = "";
                if (palyDetailDto1.getNlscType()==0){

                }else if (palyDetailDto1.getNlscType()==1){
                    clickPath = ApiService
                    .BASR_URL_RELEASE+"/news/visitor/share/shareUrl?contentType=3&objectId
                    ="+palyDetailDto1.getId()+"&memberId="+memberId;
                }
*/
                String clickPath = ApiService.BASR_URL_RELEASE + "/news/visitor/share/shareUrl" +
                        "?contentType=2&objectId=" + palyDetailDto1.getId() + "&memberId=" + memberId;

                UMWeb web = new UMWeb(clickPath);
                web.setTitle(palyDetailDto1.getTitle());//标题
                web.setThumb(image);  //缩略图
                if (TextUtils.isEmpty(palyDetailDto1.getSubtitle())) {
                    web.setDescription("让价值的创造者拥有价值");//描述
                } else {
                    web.setDescription(palyDetailDto1.getSubtitle());//描述
                }
                switch (type) {
                    case 1:
                        new ShareAction(AudioContentComActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 2:
                        new ShareAction(AudioContentComActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 3:
                        new ShareAction(AudioContentComActivity.this).setPlatform(SHARE_MEDIA.QQ)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 4:
                        new ShareAction(AudioContentComActivity.this).setPlatform(SHARE_MEDIA.SINA)
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
            Toast.makeText(AudioContentComActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AudioContentComActivity.this, "失败" + t.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AudioContentComActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    private List<CommentBeanDTO.RecordsBean> recordsBeans = new ArrayList<>();

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

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AudioContentComActivity.this, LinearLayoutManager.VERTICAL, false);
        evComment.setLayoutManager(linearLayoutManager);
        PictrueContentCommentAdapter pictrueContentCommentAdapter = new PictrueContentCommentAdapter(recordsBeans, AudioContentComActivity.this);
        pictrueContentCommentAdapter.setVisibilityHuifu(View.VISIBLE);
        evComment.setAdapter(pictrueContentCommentAdapter);


    }

    public void addReply(AddReplyBean addReplyBean) {
        KeyboardUtils.showSoftInput(etSayContent);
        etSayContent.setText(addReplyBean.getContent());
        etSayContent.setSelection(addReplyBean.getContent().length());
        this.addReplyBean = addReplyBean;
    }
}

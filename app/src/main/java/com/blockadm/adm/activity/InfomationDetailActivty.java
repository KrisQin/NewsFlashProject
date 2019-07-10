package com.blockadm.adm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.CommentAdapter;
import com.blockadm.adm.contact.InfomationDetailContract;
import com.blockadm.adm.dialog.DetailShareDialog;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.model.InformationDetailModel;
import com.blockadm.adm.persenter.InfomationDetalPresenter;
import com.blockadm.common.base.BaseActivity;
import com.blockadm.common.bean.AwardDto;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.NewsArticleCommentDTO;
import com.blockadm.common.bean.OperateArticleCountDTO;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.params.AddReplyBean;
import com.blockadm.common.bean.params.ReportBean;
import com.blockadm.common.bean.params.newsArticleCommentBean;
import com.blockadm.common.comstomview.CheckEmptyTextView;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.FScrollView;
import com.blockadm.common.comstomview.TagLayout;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.DimenUtils;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2018/12/26.
 */
@Route(path = "/app/index/InfomationDetailActivty")
public class InfomationDetailActivty extends BaseActivity<InfomationDetalPresenter,
        InformationDetailModel> implements InfomationDetailContract.View {
    @Autowired
    int id;
    /*   @BindView(R.id.tilte)
       BaseTitleBar tilte;*/
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.wv_content)
    WebView webView;

    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_like_num)
    TextView tv_like_num;
    @BindView(R.id.tv_follow_up)
    TextView tvFollowUp;
    @BindView(R.id.ev_comment)
    EmptyRecyclerView evComment;
    @BindView(R.id.tv_read)
    TextView tvRead;
    @BindView(R.id.report)
    TextView report;
    @BindView(R.id.rl_root)
    FScrollView fsl;
    @BindView(R.id.et_say_content)
    EditText etSayContent;
    @BindView(R.id.tv_send)
    CheckEmptyTextView tvSend;
    @BindView(R.id.ll__follow_up)
    LinearLayout llFollowUp;

    @BindView(R.id.rl_author)
    LinearLayout rlAuthor;

    @BindView(R.id.civ_headimage_author)
    ImageView civHeadImageAuthor;


    @BindView(R.id.tv_author_info_type)
    TextView tvInfoType;


    @BindView(R.id.tv_author_res)
    TextView tvAuthorRes;

    @BindView(R.id.tv_time_publishedAt)
    TextView tvTimePublishedAt;

    @BindView(R.id.tv_attention)
    TextView tvAttention;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_admStatement)
    TextView tvAdmStatement;

    @BindView(R.id.iv_back)
    ImageView ivBack;


    @BindView(R.id.tag_layout)
    TagLayout tabLayout;


    @BindView(R.id.iv_share)
    ImageView ivShare;

    private AddReplyBean addReplyBean;
    private NewsArticleCommentDTO newsArticleCommentDTO;
    private PageNewsArticleCommentDTO pageNewsArticleCommentDTO;
    private CommentAdapter commentAdapter;
    private int pageNum = 1;
    private int pageSize = 10;
    private NewsArticleCommentDTO.CreateUserBean createUserBean;
    private boolean isInitContentZanStatus; //最开始的文章点赞状态
    private Handler handler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_infomation);
        ButterKnife.bind(this);
        int imageMaxWidth = ScreenUtils.getScreenWidth(getApplicationContext());
        mPersenter.getNewsArticlenewsArticle(DimenUtils.px2dip(this, imageMaxWidth), id);
        mPersenter.getNewsArticleCommentPage(GsonUtil.GsonString(new newsArticleCommentBean(id,
                pageNum, pageSize)));

        initView();
    }


    private int index = 0;

    private void initView() {
        tvSend.setCheckEmptyEditTexts(etSayContent);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsArticleCommentDTO != null) {
                    share();
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
                        mPersenter.getNewsArticleCommentPage(GsonUtil.GsonString(new newsArticleCommentBean(id, pageNum, pageSize)));
                    }
                }
                return false;
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

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String tokenString =
                        (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
                if (!TextUtils.isEmpty(tokenString)) {
                    CommonModel.addEncourage(201, id, new MyObserver<AwardDto>() {
                        @Override
                        public void onSuccess(BaseResponse<AwardDto> t) {
                            if (t.getCode() == 0) {
                                if (t.getData().getIsTrue() == 0) {
                                    ShowPopupWindow(t.getData(), "浏览资讯");
                                }

                            } else {
                                Toast.makeText(InfomationDetailActivty.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }
        }, 5000);
    }


    private PopupWindow mPopupWindow;
    private View popView;
    private TextView tvType;

    private void ShowPopupWindow(final AwardDto awardDto, String text) {
        if (InfomationDetailActivty.this.isFinishing()) {
            return;
        }

        if (mPopupWindow == null) {
            popView =
                    LayoutInflater.from(InfomationDetailActivty.this).inflate(com.blockadm.common.R.layout.layout_share_award, null);
            mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, false);
            mPopupWindow.setContentView(popView);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // PopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, com.blockadm
            // .adm.R.drawable.popupwindow_background));
            mPopupWindow.setAnimationStyle(com.blockadm.adm.R.style.MyPopupWindow_anim_style);

            tvType = (TextView) popView.findViewById(com.blockadm.adm.R.id.tv_type);//拍摄照片

            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) tvType.getLayoutParams();
            params.width = ScreenUtils.dip2px(this, 50);
            params.height = ScreenUtils.dip2px(this, 50);
            tvType.setLayoutParams(params);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
            mPopupWindow.setOutsideTouchable(false);  //设置点击屏幕其它地方弹出框消失
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            popView.setFocusable(false);
        }


        tvType.setText(text);

        if (InfomationDetailActivty.this.isFinishing()) {
            return;
        }
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


    private void share() {
        DetailShareDialog detailShareDialog = new DetailShareDialog(this);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                NewsArticleCommentDTO.NewsArticleBean newsArticleBean =
                        newsArticleCommentDTO.getNewsArticle();
                UMImage image = new UMImage(InfomationDetailActivty.this,
                        newsArticleBean.getThumbnail());//网络图片
                UserInfoDto userInfoDto =
                        (UserInfoDto) ACache.get(InfomationDetailActivty.this).getAsObject(
                                "userInfoDto");
                int memberId = 0;
                if (userInfoDto != null) {
                    memberId = userInfoDto.getMemberId();
                }

                String clickPath = ApiService.BASR_URL_RELEASE + "/news/visitor/share/shareUrl" +
                        "?contentType=0&objectId=" + id + "&memberId=" + memberId;
                UMWeb web = new UMWeb(clickPath);
                web.setTitle(newsArticleBean.getTitle());//标题
                web.setThumb(image);  //缩略图
                web.setDescription(newsArticleBean.getSummary());//描述
                switch (type) {
                    case 1:
                        new ShareAction(InfomationDetailActivty.this).setPlatform(SHARE_MEDIA.WEIXIN)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();

                        break;
                    case 2:
                        new ShareAction(InfomationDetailActivty.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 3:
                        new ShareAction(InfomationDetailActivty.this).setPlatform(SHARE_MEDIA.QQ)
                                .withMedia(web)
                                .setCallback(umShareListener)
                                .share();
                        break;
                    case 4:
                        new ShareAction(InfomationDetailActivty.this).setPlatform(SHARE_MEDIA.SINA)
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
            Toast.makeText(InfomationDetailActivty.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(InfomationDetailActivty.this, "失败" + t.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(InfomationDetailActivty.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };


    @Override
    protected void onDestroy() {

        webView.removeAllViews();
        webView.clearHistory();
        webView.destroy();
        webView = null;

        handler.removeCallbacksAndMessages(null);
        super.onDestroy();

    }

    private void initWeb(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);//设置适应Html5的一些方法
        webSettings.setBlockNetworkImage(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
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
                LinearLayout.LayoutParams params =
                        (LinearLayout.LayoutParams) webView.getLayoutParams();
                params.width =
                        ScreenUtils.getScreenWidth(InfomationDetailActivty.this) - DimenUtils.dip2px(InfomationDetailActivty.this, 15);
                //                params.height = Tools.getHightInPx(getActivity()) - Tools
                //                .dip2px(getActivity(), 150);

                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                webView.setLayoutParams(params);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        webView.loadUrl(url);

    }

    @Override
    public void showNewsArticlenewsArticle(NewsArticleCommentDTO newsArticleCommentDTO) {
        this.newsArticleCommentDTO = newsArticleCommentDTO;
        tvTitle.setText(newsArticleCommentDTO.getNewsArticle().getTitle());
        initWeb(newsArticleCommentDTO.getShowContentHtmlUrl());
        isInitContentZanStatus = newsArticleCommentDTO.getOperateArticleData().getIsZan() == 1;
        tvRead.setText("浏览 " + newsArticleCommentDTO.getNewsArticle().getConvertReadCount() + "");
        tv_like_num.setText(newsArticleCommentDTO.getNewsArticle().getZanCount() + "");
        createUserBean = newsArticleCommentDTO.getCreateUser();
        tvAdmStatement.setText(newsArticleCommentDTO.getNewsArticle().getAdmStatement());
        if (createUserBean == null) {
            rlAuthor.setVisibility(View.GONE);
            tvTimePublishedAt.setVisibility(View.VISIBLE);
            tvTimePublishedAt.setText(newsArticleCommentDTO.getNewsArticle().getPublishedAt());
        } else {
            tvTimePublishedAt.setVisibility(View.GONE);
            rlAuthor.setVisibility(View.VISIBLE);
            if (createUserBean.getIsFollowUserMember() == 0) {
                tvAttention.setBackgroundResource(R.mipmap.attention);
            } else {
                tvAttention.setBackgroundResource(R.mipmap.attention_on);
            }

            ImageUtils.loadImageView(createUserBean.getIcon(), civHeadImageAuthor);
            tvTime.setText(newsArticleCommentDTO.getNewsArticle().getPublishedAt());
            if (newsArticleCommentDTO.getNewsArticle().getSysLableList() != null && newsArticleCommentDTO.getNewsArticle().getSysLableList().size() > 0) {
                tabLayout.addTag(newsArticleCommentDTO.getNewsArticle().getSysLableList());
            }

            switch (createUserBean.getCredentialsState()) {
                case 0:
                    tvInfoType.setVisibility(View.GONE);
                    break;
                case 1:
                    tvInfoType.setVisibility(View.GONE);
                    break;
                case 2:
                    tvInfoType.setText("机构");
                    break;
                case 3:
                    tvInfoType.setText("机构");
                    break;
            }
            tvAuthorRes.setText(createUserBean.getNickName());
        }


        if (newsArticleCommentDTO.getOperateArticleData().getIsCollect() == 1) {
            Drawable dra = getResources().getDrawable(R.mipmap.news_like_press);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            tvCollect.setCompoundDrawables(null, dra, null, null);
        } else {

            Drawable dra = getResources().getDrawable(R.mipmap.news_like);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            tvCollect.setCompoundDrawables(null, dra, null, null);
        }

        if (newsArticleCommentDTO.getOperateArticleData().getIsZan() == 1) {
            Drawable dra = getResources().getDrawable(R.mipmap.news_agreepress);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            tv_like_num.setCompoundDrawables(null, dra, null, null);


        } else {
            Drawable dra = getResources().getDrawable(R.mipmap.news_agree);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            tv_like_num.setCompoundDrawables(null, dra, null, null);
        }

    }

    private List<PageNewsArticleCommentDTO.RecordsBean> recordsBeans = new ArrayList<>();

    @Override
    public void showNewsArticleCommentPage(PageNewsArticleCommentDTO pageNewsArticleCommentDTO) {
        if (pageNewsArticleCommentDTO.getTotal() == 0) {
            llFollowUp.setVisibility(View.GONE);
        } else {
            llFollowUp.setVisibility(View.VISIBLE);
            tvFollowUp.setText("跟帖(" + pageNewsArticleCommentDTO.getTotal() + ")");
        }

        recordsBeans.addAll(pageNewsArticleCommentDTO.getRecords());
        pageNewsArticleCommentDTO.setRecords(recordsBeans);
        this.pageNewsArticleCommentDTO = pageNewsArticleCommentDTO;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        evComment.setLayoutManager(linearLayoutManager);
        commentAdapter = new CommentAdapter(pageNewsArticleCommentDTO, this);
        evComment.setAdapter(commentAdapter);
        if (pageNewsArticleCommentDTO != null && pageNewsArticleCommentDTO.getTotal() == recordsBeans.size()) {
            ToastUtils.showToast("已经加载到最后");
        }


    }

    @Override
    public void showAddReply(CommentReplyListBean commentReplyListBean) {

        if (pageNewsArticleCommentDTO != null && pageNewsArticleCommentDTO.getRecords() != null && pageNewsArticleCommentDTO.getRecords().size() > 0) {
            List<PageNewsArticleCommentDTO.RecordsBean> recordsBeans =
                    pageNewsArticleCommentDTO.getRecords();
            int newsArticleCommentId = commentReplyListBean.getNewsArticleCommentId();
            for (int i = 0; i < recordsBeans.size(); i++) {
                PageNewsArticleCommentDTO.RecordsBean recordsBean = recordsBeans.get(i);
                int id = recordsBean.getId();
                if (newsArticleCommentId == id) {
                    if (recordsBean != null) {
                        if (recordsBean.getCommentReplyList() != null) {

                            recordsBean.getCommentReplyList().add(0, commentReplyListBean);
                            recordsBean.setReplyCount(recordsBean.getCommentReplyList().size());
                        }

                        commentAdapter = new CommentAdapter(pageNewsArticleCommentDTO, this);
                        evComment.setAdapter(commentAdapter);
                    }

                }
            }
        }
        KeyboardUtils.hideSoftInput(this);
        etSayContent.setText("");
        addReplyBean = null;
    }

    @Override
    public void operateArticleCount(BaseResponse<OperateArticleCountDTO> t) {
        Toast.makeText(this, t.getMsg(), Toast.LENGTH_SHORT).show();
        if (operateType == 3 && choose == 1) {
            newsArticleCommentDTO.getOperateArticleData().setIsCollect(0);
            Drawable dra = getResources().getDrawable(R.mipmap.news_like);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            tvCollect.setCompoundDrawables(null, dra, null, null);
        } else if (operateType == 3 && choose == 0) {
            newsArticleCommentDTO.getOperateArticleData().setIsCollect(1);
            Drawable dra = getResources().getDrawable(R.mipmap.news_like_press);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            tvCollect.setCompoundDrawables(null, dra, null, null);
        }

        if (operateType == 1 && choose == 1) {
            newsArticleCommentDTO.getOperateArticleData().setIsZan(0);
            Drawable dra = getResources().getDrawable(R.mipmap.news_agree);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            tv_like_num.setCompoundDrawables(null, dra, null, null);
            if (isInitContentZanStatus) {
                tv_like_num.setText(newsArticleCommentDTO.getNewsArticle().getZanCount() - 1 + "");
            } else {
                tv_like_num.setText(newsArticleCommentDTO.getNewsArticle().getZanCount() + "");
            }

        } else if (operateType == 1 && choose == 0) {
            newsArticleCommentDTO.getOperateArticleData().setIsZan(1);
            if (isInitContentZanStatus) {
                tv_like_num.setText(newsArticleCommentDTO.getNewsArticle().getZanCount() + "");
            } else {
                tv_like_num.setText(newsArticleCommentDTO.getNewsArticle().getZanCount() + 1 + "");
            }
            Drawable dra = getResources().getDrawable(R.mipmap.news_agreepress);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            tv_like_num.setCompoundDrawables(null, dra, null, null);

            String tokenString =
                    (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
            if (!TextUtils.isEmpty(tokenString)) {
                CommonModel.addEncourage(404, id, new MyObserver<AwardDto>() {
                    @Override
                    public void onSuccess(BaseResponse<AwardDto> t) {
                        if (t.getCode() == 0) {
                            if (t.getData().getIsTrue() == 0) {
                                ShowPopupWindow(t.getData(), "点赞");
                            }

                        } else {
                            Toast.makeText(InfomationDetailActivty.this, t.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        }

    }

    @Override
    public void showAddNewsArticleComment(PageNewsArticleCommentDTO.RecordsBean recordsBean) {
        recordsBeans.add(0, recordsBean);
        pageNewsArticleCommentDTO.setRecords(recordsBeans);
        commentAdapter = new CommentAdapter(pageNewsArticleCommentDTO, this);
        evComment.setAdapter(commentAdapter);
        KeyboardUtils.hideSoftInput(this);
        etSayContent.setText("");

        String tokenString =
                (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
        if (!TextUtils.isEmpty(tokenString)) {
            CommonModel.addEncourage(301, recordsBean.getId(), new MyObserver<AwardDto>() {
                @Override
                public void onSuccess(BaseResponse<AwardDto> t) {
                    if (t.getCode() == 0) {
                        if (t.getData().getIsTrue() == 0) {
                            ShowPopupWindow(t.getData(), "评论");
                        }

                    } else {
                        Toast.makeText(InfomationDetailActivty.this, t.getMsg(),
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


    @Override
    public void addReport(String msg) {
        showToast(this, msg);
    }


    public void addReply(AddReplyBean addReplyBean) {
        KeyboardUtils.showSoftInput(etSayContent);
        etSayContent.setText(addReplyBean.getContent());
        etSayContent.setSelection(addReplyBean.getContent().length());
        this.addReplyBean = addReplyBean;

    }

    private int operateType;
    private int choose;

    @OnClick({R.id.tv_collect, R.id.tv_like_num, R.id.tv_send, R.id.report, R.id.tv_attention,
            R.id.civ_headimage_author})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.civ_headimage_author:
                if (createUserBean != null) {
                    Intent intent = new Intent(this, PersonnalIndexComActivity.class);
                    intent.putExtra(ConstantUtils.MENBERID, createUserBean.getMemberId());
                    startActivity(intent);
                }


                break;
            case R.id.tv_collect:
                operateType = 3;
                if (newsArticleCommentDTO != null) {
                    if (newsArticleCommentDTO.getOperateArticleData().getIsCollect() == 0) {
                        choose = 0;
                        mPersenter.operateArticleCount(newsArticleCommentDTO.getNewsArticle().getId(), 3, choose);
                    } else {
                        choose = 1;
                        mPersenter.operateArticleCount(newsArticleCommentDTO.getNewsArticle().getId(), 3, choose);
                    }
                }


                break;

            case R.id.tv_like_num:
                operateType = 1;
                if (newsArticleCommentDTO != null) {
                    if (newsArticleCommentDTO.getOperateArticleData().getIsZan() == 0) {
                        choose = 0;
                        mPersenter.operateArticleCount(newsArticleCommentDTO.getNewsArticle().getId(), 1, 0);
                    } else {
                        choose = 1;
                        mPersenter.operateArticleCount(newsArticleCommentDTO.getNewsArticle().getId(), 1, 1);
                    }
                }

                break;
            case R.id.tv_attention:
                int memberId = (int) SharedpfTools.getInstance().get(ConstantUtils.MemberId, 0);
                if (memberId == createUserBean.getMemberId()) { //自己不能关注自己
                    return;
                }

                if (createUserBean != null) {
                    if (createUserBean.getIsFollowUserMember() == 0) {
                        CommonModel.addUserFollow(createUserBean.getMemberId(),
                                new MyObserver<String>() {
                                    @Override
                                    public void onSuccess(BaseResponse<String> baseResponse) {
                                        createUserBean.setIsFollowUserMember(1);
                                        EventBus.getDefault().post(new UserDataEvent());
                                        tvAttention.setBackgroundResource(R.mipmap.attention_on);
                                    }

                                });
                    } else {
                        CommonModel.deleteUserFollow(createUserBean.getMemberId(),
                                new MyObserver<String>() {
                                    @Override
                                    public void onSuccess(BaseResponse<String> baseResponse) {
                                        createUserBean.setIsFollowUserMember(0);
                                        EventBus.getDefault().post(new UserDataEvent());
                                        tvAttention.setBackgroundResource(R.mipmap.attention);
                                    }


                                });
                    }
                }

                break;

            case R.id.report:
                mPersenter.addReport(GsonUtil.GsonString(new ReportBean(id, 0)));
                break;

            case R.id.tv_send:
                String token =
                        (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
                if (TextUtils.isEmpty(token)) {
                    ContextUtils.showNoLoginDialog(this);
                    return;
                }

                if (addReplyBean != null) {
                    String sendContent = etSayContent.getText().toString().trim();
                    if (!TextUtils.isEmpty(sendContent)) {
                        addReplyBean.setContent(sendContent);
                        mPersenter.addReply(GsonUtil.GsonString(addReplyBean));
                    }

                } else {
                    String sendContent = etSayContent.getText().toString().trim();
                    if (!TextUtils.isEmpty(sendContent)) {
                        AddReplyBean addReplyBean = new AddReplyBean(sendContent, id);
                        mPersenter.addNewsArticleComment(GsonUtil.GsonString(addReplyBean));
                    }

                }

                break;
        }
    }
}

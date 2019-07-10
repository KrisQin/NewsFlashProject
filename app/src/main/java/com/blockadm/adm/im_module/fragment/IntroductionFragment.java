package com.blockadm.adm.im_module.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;
import com.blockadm.adm.activity.BuyDetailComActivity;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.im_module.activity.LiveDetailActivity;
import com.blockadm.adm.im_module.widget.InputHousePwdDialog;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.utils.BeanTransUtils;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.GlideTools;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hp on 2019/1/22.
 * TODO 简介
 */

public class IntroductionFragment extends BaseComFragment {


    @BindView(R.id.ll_web)
    LinearLayout llWeb;
    @BindView(R.id.iv_pictrue)
    ImageView ivPictrue;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_open_les_time)
    TextView tvOpenLesTime;
    @BindView(R.id.civ_headimage_author)
    CircleImageView civHeadimageAuthor;
    @BindView(R.id.tv_author_info_type)
    TextView tvAuthorInfoType;
    @BindView(R.id.tv_author_res)
    TextView tvAuthorRes;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.rl_author)
    LinearLayout rlAuthor;
    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.bottom_view)
    View bottomView;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private View ll_foot;


    private Unbinder unbinder;
    private LiveDetailActivity mActivity;

    @Override
    protected void initView(final View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        ll_foot = rootView.findViewById(R.id.ll_foot);
        mActivity = (LiveDetailActivity) getActivity();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live_introduction;
    }


    public LiveDetailInfo mLessonsDetailInfo;

    public void showData(LiveDetailInfo lessonsDetailInfo) {

        mLessonsDetailInfo = lessonsDetailInfo;

        try {

            // 0 直播中、1：预备中（未开始）、2：已结束
            if (mLessonsDetailInfo.getLiveStatus() == 0) {
                tvStatus.setText("正在进行...");
            } else if (mLessonsDetailInfo.getLiveStatus() == 1) {
                tvStatus.setText("预备中");
            } else if (mLessonsDetailInfo.getLiveStatus() == 2) {
                tvStatus.setText("已结束");
            }

            tvTitle.setText(lessonsDetailInfo.getTitle());
            tvOpenLesTime.setText(lessonsDetailInfo.getOpenLessonsTime());
            tvAuthorRes.setText(lessonsDetailInfo.getNickName());
            rlAuthor.setVisibility(View.VISIBLE);

            if (lessonsDetailInfo.getIsFollow() == 1) {
                tvAttention.setBackgroundResource(R.mipmap.attention_on);
            } else {
                tvAttention.setBackgroundResource(R.mipmap.attention);
            }


            switch (lessonsDetailInfo.getCredentialsState()) {
                case 0:
                    tvAuthorInfoType.setVisibility(View.GONE);
                    break;
                case 1:
                    tvAuthorInfoType.setVisibility(View.GONE);
                    break;
                case 2:

                    tvAuthorInfoType.setText("机构· ");
                    break;
                case 3:
                    tvAuthorInfoType.setText("机构·");
                    break;
            }

            GlideTools.setImgByGlide(mActivity, lessonsDetailInfo.getCoverImgs(),
                    R.drawable.picture_default, ivPictrue);
            GlideTools.setImgByGlide(mActivity, lessonsDetailInfo.getIcon(),
                    R.drawable.picture_default, civHeadimageAuthor);

            showBottomView(lessonsDetailInfo);

            initWeb(lessonsDetailInfo.getShowContentHtmlUrl());

        } catch (Exception e) {

        }
    }


    public void showBottomView(LiveDetailInfo liveDetailInfo) {

        boolean isShow =
                liveDetailInfo.getIsSeeStatus() == 1 || liveDetailInfo.getIsSeeStatus() == 3;
        boolean isNeedBuy = liveDetailInfo.getIsSeeStatus() == 1;

        if (isShow) {
            bottomView.setVisibility(View.VISIBLE);
            ll_foot.setVisibility(View.VISIBLE);
            if (isNeedBuy) {
                tvPrice.setText("购买: " + StringUtils.getUnitAmount()+ liveDetailInfo.getMoney());
            } else {
                tvPrice.setText("输入密码");
            }
        } else {
            bottomView.setVisibility(View.GONE);
            ll_foot.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initWeb(String url) {

        L.d(" initWeb url: " + url);
        final WebView webView = new WebView(getContext());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);//设置适应Html5的一些方法
        webSettings.setBlockNetworkImage(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
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
              /* ViewPager.LayoutParams params = (ViewPager.LayoutParams) llWeb.getLayoutParams();
                params.width =DimenUtils.dip2px(mActivity,200);
                params.height =  DimenUtils.dip2px(mActivity,200);
                llWeb.setLayoutParams(params);
                llWeb.setBackgroundColor(Color.parseColor("#00FFFF"));*/

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
        llWeb.removeAllViews();
        llWeb.addView(webView);
    }


    @OnClick({R.id.civ_headimage_author, R.id.tv_attention, R.id.ll_foot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_foot:
                clickBottomBt();
                break;
            case R.id.civ_headimage_author:
                clickHeadImageAuthor();
                break;
            case R.id.tv_attention:
                clickAttention();
                break;
        }
    }

    private void clickBottomBt() {
        //isSeeStatus : 0:免费，1：未付费、2:已付费,3:请输入密码,4:密码已校验通过
        if (mLessonsDetailInfo.getIsSeeStatus() == 1) {
            String token =
                    (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
            if (TextUtils.isEmpty(token)) {
                ARouter.getInstance().build(ARouterPathConfig.login_activity_path).withBoolean("is",
                        false).navigation();
            } else {
                Intent intent = new Intent(mActivity, BuyDetailComActivity.class);
                intent.putExtra(BuyDetailComActivity.LESSONS_DTO,BeanTransUtils.LiveDetailInfo2LessonsSpecialColumnDto(mLessonsDetailInfo));
                intent.putExtra(BuyDetailComActivity.LIVE_LESSONS,true);
                startActivity(intent);
            }
        } else if (mLessonsDetailInfo.getIsSeeStatus() == 3) {
            showInputPwdDialog();
        }
    }

    private InputHousePwdDialog mInputHousePwdDialog;

    /**
     * 输入房间密码
     */
    private void showInputPwdDialog() {
        mInputHousePwdDialog = new InputHousePwdDialog(mActivity,
                new InputHousePwdDialog.PutPwdCallback() {
                    @Override
                    public void callback(String pwd) {

                        checkAccessPwd(pwd);
                    }
                });
        mInputHousePwdDialog.show();
    }

    /**
     * 校验密码
     *
     * @param pwd
     */
    private void checkAccessPwd(String pwd) {
        showDefaultLoadingDialog();
        CommonModel.checkAccessPwd(mLessonsDetailInfo.getId() + "", pwd, new ComObserver() {

            @Override
            public void onSuccess(BaseResponse t, String errorMsg) {
                dismissLoadingDialog();
                if (t.getCode() == 0) { //密码输入正确
                    if (mInputHousePwdDialog != null) {
                        mInputHousePwdDialog.dismiss();
                    }
                    KeyboardUtils.hideSoftInput(mActivity);

                    ll_foot.setVisibility(View.GONE);

                    if (mActivity != null)
                        mActivity.queryLessonsDetail(true);
                } else if (t.getCode() == 1) { //密码输入错误
                    if (mInputHousePwdDialog != null) {
                        mInputHousePwdDialog.showErrorPwd();
                    }
                } else if (t.getCode() == 2) { //未登录
                    mInputHousePwdDialog.dismiss();
                }
            }

        });
    }

    private void clickAttention() {
        if (mLessonsDetailInfo != null) {
            if (mLessonsDetailInfo.getMemberId() == LiveManager.getInstance().getMemberId()) {
                T.showShort(mActivity,"不能关注自己");
                return;
            }

            if (mLessonsDetailInfo.getIsFollow() == 0) {
                CommonModel.addUserFollow(mLessonsDetailInfo.getMemberId(),
                        new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse t) {
                                mLessonsDetailInfo.setIsFollow(1);
                                tvAttention.setBackgroundResource(R.mipmap.attention_on);
                            }
                        });
            } else {
                CommonModel.deleteUserFollow(mLessonsDetailInfo.getMemberId(),
                        new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> baseResponse) {
                                mLessonsDetailInfo.setIsFollow(0);
                                tvAttention.setBackgroundResource(R.mipmap.attention);
                            }
                        });
            }
        }
    }

    private void clickHeadImageAuthor() {
        if (mLessonsDetailInfo != null) {
            Intent intent = new Intent(mActivity,
                    PersonnalIndexComActivity.class);
            intent.putExtra(ConstantUtils.MENBERID, mLessonsDetailInfo.getMemberId());
            startActivity(intent);
        }
    }
}

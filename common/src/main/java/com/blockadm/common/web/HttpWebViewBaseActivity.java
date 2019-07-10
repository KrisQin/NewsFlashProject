package com.blockadm.common.web;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.StringUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.ButterKnife;

/**
 * 创建者     Kris
 * 创建时间   2017/8/30
 * 描述	      ${TODO   参考:http://www.bkjia.com/Androidjc/976345.html
 *              TODO              http://blog.csdn.net/carson_ho/article/details/52693322}
 *
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public abstract class HttpWebViewBaseActivity extends BaseComActivity {


    protected WebView mWebView;
    private String titleText = "";
    private TextView mTv_title;
    private RelativeLayout mLayout_back;
    private RelativeLayout mLayout_title;
    private boolean isBackFinishActivity = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_webview);

        mTv_title = findViewById(R.id.tv_title);
        mLayout_back = findViewById(R.id.layout_back);
        mLayout_title = findViewById(R.id.layout_title);

        mLayout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        ButterKnife.bind(this);


        initView(savedInstanceState);
        initData(savedInstanceState);


    }

    private void back() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        }else {
            finish();
        }
    }

    protected String getHtmlTitle() {
        return "";
    }

    public void showTitle() {
        if (StringUtils.isNotEmpty(titleText)) {
            mLayout_title.setVisibility(View.VISIBLE);
            mTv_title.setText(titleText);
        }
    }



    protected void initView(Bundle savedInstanceState) {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setVerticalScrollbarOverlay(true);

        //        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null); // 防止打开H5时黑屏
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //设置WebView支持JavaScript
//                webSettings.setLoadWithOverviewMode(true); // 充满全屏
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        //        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        //        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 是否缓存
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.LOAD_NORMAL);
        }

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setBuiltInZoomControls(true); //放大缩小
        webSettings.setDomStorageEnabled(true);//设置适应Html5的一些方法
        webSettings.setBlockNetworkImage(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(
//                    android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webSettings.setMixedContentMode(
                    android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        //在js中调用本地java方法
        mWebView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
        //添加客户端支持
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new AuthorizationWebViewClient());
    }

    protected void initData(Bundle savedInstanceState) {


        String url = takeWebUrl();
        L.d(" web url = "+url);
        mWebView.loadUrl(url);

        titleText = getHtmlTitle();
        showTitle();

        isBackFinishActivity = getBackFinishActivityStatus();

        if (isBackFinishActivity) {
            mLayout_title.setVisibility(View.VISIBLE);
            mLayout_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doFinish();
                }
            });

        }

    }

    protected boolean getBackFinishActivityStatus() {
        return false;
    }

    protected abstract String takeWebUrl();



    class AuthorizationWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            L.d("HTP"," 设定加载开始的操作 ********************* ");
            //设定加载开始的操作
            showMsgLoadingDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            L.d("HTP"," 设定加载结束的操作 ********************* ");
            //设定加载结束的操作
            dismissMsgLoadingDialog();
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            dismissMsgLoadingDialog();
            L.d("HTP"," 设定加载返回错误的操作 ********************* ");
        }

        //设置不用系统浏览器打开,直接显示在当前Webview
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url); // 在当前的webview中跳转到新的url
                } else {
                    //当WebView 加载路径里，未以http,或者https开头时，尝试以Intent 打开其他app，
                    // 如果打开失败（本设备未安装此app），则继续加载。
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            } catch (Exception e){
                return false;
            }


//            return true;
        }
    }

    protected void doFinish() {
        finish();
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    protected class JsInterface {

        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String backStr) {

        }
    }


    @Override
    protected void onDestroy() {
        // 在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，
        // 然后移除 WebView，再销毁 WebView，最后置空。
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}


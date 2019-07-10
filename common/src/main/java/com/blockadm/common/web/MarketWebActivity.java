package com.blockadm.common.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.blockadm.common.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.utils.L;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by Kris on 2019/7/10
 *
 * @Describe TODO { 行情 }
 */
public class MarketWebActivity extends BaseComActivity {

    protected WebView mWebView;
    private String mUrl = "https://www.baidu.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_tab);

        initView();
        initData();
    }

    protected void initView() {
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

        //添加客户端支持
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new AuthorizationWebViewClient());
    }

    protected void initData() {

        L.d(" web url = "+ mUrl);
        mWebView.loadUrl(mUrl);
    }


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
        }
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

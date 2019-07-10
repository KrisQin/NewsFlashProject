//package com.blockadm.common.web;
//
//import android.app.Fragment;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.blockadm.common.R;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//
///**
// * Created by Kris on 2019/5/8
// *
// * @Describe TODO {  }
// */
//public class FullWebBaseFragment extends Fragment {
//
//    protected WebView mWebView;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_web_layout,null,false);
//
//        mWebView = view.findViewById(R.id.webview);
//
//        initView(savedInstanceState);
//        initData(savedInstanceState);
//
//        return view;
//    }
//
//    protected void initView(Bundle savedInstanceState) {
//        mWebView.setVerticalScrollbarOverlay(true);
//        //        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null); // 防止打开H5时黑屏
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true); //设置WebView支持JavaScript
//        //        webSettings.setLoadWithOverviewMode(true); // 充满全屏
//        //        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        //        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        //        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 是否缓存
//
//        webSettings.setDomStorageEnabled(true);//设置适应Html5的一些方法
//        webSettings.setBlockNetworkImage(false);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mWebView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
//        }
//
//    }
//}

package com.blockadm.adm.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.LessonsSpecialColumnDto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hp on 2019/1/22.
 */

public class LessonSynopsisFragment1 extends BaseComFragment {


    @BindView(R.id.ll_web)
    LinearLayout llWeb;

    private Unbinder unbinder;

    @Override
    protected void initView(final View rootView) {
        unbinder = ButterKnife.bind(this, rootView);

    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_lesson_synopsis1;
    }

    public void updateView(LessonsSpecialColumnDto lessonsSpecialColumnDto) {
        try{
            initWeb(lessonsSpecialColumnDto.getShowContentHtmlUrl());
        }catch (Exception e){

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }





    private void initWeb(String url) {
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
                params.width =DimenUtils.dip2px(getActivity(),200);
                params.height =  DimenUtils.dip2px(getActivity(),200);
                llWeb.setLayoutParams(params);
                llWeb.setBackgroundColor(Color.parseColor("#00FFFF"));*/

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
        webView.loadUrl(url);
        llWeb.addView(webView);
    }
}

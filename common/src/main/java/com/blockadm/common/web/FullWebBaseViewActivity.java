package com.blockadm.common.web;


import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;

/**
 * 全屏的
 * webview界面
 */
public class FullWebBaseViewActivity extends HttpWebViewBaseActivity {

    //网页链接
    public static final String DATA_WEB_URL = "WEB_URL";
    public static final String TITLE = "TITLE";
    public static final String Is_Back_Finish_Activity = "Is_Back_Finish_Activity";


    @Override
    protected String takeWebUrl() {
        String url = getIntent().getStringExtra(FullWebBaseViewActivity.DATA_WEB_URL);
        if(StringUtils.isEmpty(url)){
            T.showShort(this,"网页链接为空");
            finish();
        }
        return url;
    }

    @Override
    protected String getHtmlTitle() {
        String title = getIntent().getStringExtra(FullWebBaseViewActivity.TITLE);
        return title;
    }

    @Override
    protected boolean getBackFinishActivityStatus() {
        return getIntent().getBooleanExtra(Is_Back_Finish_Activity,false);
    }
}


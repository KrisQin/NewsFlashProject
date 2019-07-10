package com.blockadm.adm.Fragment;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.web.FullWebBaseViewActivity;



/**
 * Created by Kris on 2019/5/30
 *
 * @Describe TODO { 学习界面中的 喜马拉雅 fragment }
 */
public class StudyXMLYFragment extends BaseComFragment {


    private String mWebUrl;

    @Override
    protected void initView(View view) {

        ImageView iv_image = view.findViewById(R.id.iv_image);
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeb();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (StringUtils.isEmpty(mWebUrl)) {
            findXiMaLaYaWebUrl();
        }
    }

    private void findXiMaLaYaWebUrl() {
        CommonModel.findXiMaLaYaWebUrl(new MyObserver<String>() {
            @Override
            public void onSuccess(BaseResponse<String> t) {
                if (t != null) {
                    mWebUrl = t.getData();
                }
            }
        });
    }

    private void showWeb() {
        Intent intent2 = new Intent(getActivity(), FullWebBaseViewActivity.class);
        intent2.putExtra(FullWebBaseViewActivity.DATA_WEB_URL, mWebUrl);
        intent2.putExtra(FullWebBaseViewActivity.Is_Back_Finish_Activity,true);
        intent2.putExtra(FullWebBaseViewActivity.TITLE, "喜马拉雅");
        startActivity(intent2);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xmly;
    }
}

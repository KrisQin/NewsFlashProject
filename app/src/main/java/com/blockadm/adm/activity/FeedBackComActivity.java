package com.blockadm.adm.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.FeedBackAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.AddFeedBackBean;
import com.blockadm.common.bean.FeedBackTypeDTO;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/31.
 */

public class FeedBackComActivity extends BaseComActivity {


    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_type)
    EditText etType;

    @BindView(R.id.tv_num)
    TextView tvNum;
    private ArrayList<FeedBackTypeDTO> feedBackTypeDTOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_feed_back);
        ButterKnife.bind(this);


        CommonModel.listFeedbackType(new MyObserver<ArrayList<FeedBackTypeDTO>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<FeedBackTypeDTO>> t) {
                feedBackTypeDTOS = t.getData();
                FeedBackAdapter feedBackAdapter = new FeedBackAdapter(FeedBackComActivity.this, feedBackTypeDTOS);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(FeedBackComActivity.this,2);
                rlv.setLayoutManager(gridLayoutManager);
                rlv.setAdapter(feedBackAdapter);
            }
        });
        tilte.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             commit();
            }
        });

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("FeedBackComActivity", "afterTextChanged 被执行---->" + s.toString());
                tvNum.setText(s.toString().length()+"/100");

            }
        });

    }

    private void commit() {
        String content =  etContent.getText().toString();
        if(TextUtils.isEmpty(etContent.getText().toString())){
            ToastUtils.showToast("请输入你要反馈的内容");
            return ;
        }
       String nickName =  etNickname.getText().toString();

        if(TextUtils.isEmpty(nickName)){
            ToastUtils.showToast("请输入你的称呼");
            return ;
        }
        String linkType =  etType.getText().toString();
        String  typeId = "";
        if (feedBackTypeDTOS!=null){
            for (int i = 0; i <feedBackTypeDTOS.size() ; i++) {
                if (feedBackTypeDTOS.get(i).isChecked()){
                    typeId = feedBackTypeDTOS.get(i).getTypeId()+"";
                }

            }
        }

        AddFeedBackBean  addFeedBackBean =   new AddFeedBackBean();
        addFeedBackBean.setAddress(nickName);
        addFeedBackBean.setContent(content);
        addFeedBackBean.setTypeId(typeId);
        addFeedBackBean.setContact(linkType);
        CommonModel.addFeedback(new MyObserver<String>() {
            @Override
            public void onSuccess(BaseResponse<String> t) {
                ToastUtils.showToast(t.getMsg());
                if (t.getCode()==0){
                    finish();
                }

            }


        },GsonUtil.GsonString(addFeedBackBean));

    }
}

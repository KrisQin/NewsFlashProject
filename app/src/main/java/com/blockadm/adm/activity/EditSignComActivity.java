package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.UserParams;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/30.
 */

public class EditSignComActivity extends BaseComActivity {


    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    @BindView(R.id.tv_num)
    TextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_sign);
        ButterKnife.bind(this);
        UserInfoDto userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etContent.setText(userInfoDto.getSign());
        tvNum.setText(userInfoDto.getSign().length()+"/16");
        etContent.setSelection(userInfoDto.getSign().length());
        tilte.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etContent.getText().toString())){
                    ToastUtils.showToast("内容不能为空");
                }else{
                    UserParams userParams  = new UserParams();
                    userParams.setSign(etContent.getText().toString());
                    CommonModel.updateUserMember(GsonUtil.GsonString(userParams), new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> t) {
                            ToastUtils.showToast(t.getMsg());
                            if (t.getCode()==0){
                                Intent intent = new Intent();
                                intent.putExtra("sign",etContent.getText().toString());
                                setResult(RESULT_OK,intent);
                                KeyboardUtils.hideSoftInput(EditSignComActivity.this);
                                finish();
                            }

                        }

                    });
                }

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

                tvNum.setText(s.toString().length()+"/16");

            }
        });
    }

    @OnClick(R.id.tv_delete)
    public void onViewClicked() {
        etContent.setText("");
    }
}

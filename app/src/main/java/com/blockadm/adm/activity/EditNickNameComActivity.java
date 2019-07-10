package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

public class EditNickNameComActivity extends BaseComActivity {


    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    private UserInfoDto userInfoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_act);
        ButterKnife.bind(this);
        userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        etContent.setText(userInfoDto.getNickName());
        tilte.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilte.setClickable(false);
                if (TextUtils.isEmpty(etContent.getText().toString())){
                    ToastUtils.showToast("内容不能为空");
                }else{


                    UserParams userParams  = new UserParams();
                    userParams.setNickName(etContent.getText().toString());
                    CommonModel.updateUserMember(GsonUtil.GsonString(userParams), new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> t) {
                            ToastUtils.showToast(t.getMsg());
                            tilte.setClickable(true);
                            if (t.getCode()==0){
                                Intent intent = new Intent();
                                intent.putExtra("nickname",etContent.getText().toString());
                                setResult(RESULT_OK,intent);
                                KeyboardUtils.hideSoftInput(EditNickNameComActivity.this);
                                finish();
                            }

                        }

                    });
                }

            }
        });
    }

    @OnClick(R.id.iv_delete)
    public void onViewClicked() {
        etContent.setText("");
    }
}

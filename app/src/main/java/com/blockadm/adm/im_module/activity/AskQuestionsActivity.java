package com.blockadm.adm.im_module.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ArechargeComActivity;
import com.blockadm.adm.activity.SettingComActivity;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.bean.params.QuestionParams;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.PayPasswordDialog;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kris on 2019/5/15
 *
 * @Describe TODO { 提问 }
 */
public class AskQuestionsActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.rb_Azuan)
    RadioButton rbAzuan;
    @BindView(R.id.rb_Adian)
    RadioButton rbAdian;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.tv_min_et_money)
    TextView tvMinEtMoney;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    public static final String user_Info_Dto = "user_Info_Dto";
    public static final String Live_Detail_Info = "Live_Detail_Info";

    private int rewardType = 0;  // 0:A钻  1：A点
    private String mSpeakerNickName;
    private UserInfoDto mUserInfoDto;
    private LiveDetailInfo mLiveDetailInfo;
    private int mPoint;
    private double mDiamond;
    private boolean isCanNotAsk; //true:余额不足，不能提问  false:余额充足，可以提问
    private int mCurrentMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_questions);

        mLiveDetailInfo = (LiveDetailInfo) getIntent().getSerializableExtra(Live_Detail_Info);

        if (mLiveDetailInfo != null) {
            mSpeakerNickName = mLiveDetailInfo.getNickName();
        }

        ButterKnife.bind(this);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rbAzuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rewardType = 0;
                    tvMinEtMoney.setText("最少输入5A钻");
                    showMoneyStatus(false,true);
                }
            }
        });

        rbAdian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rewardType = 1;
                    tvMinEtMoney.setText("最少100A点");
                    showMoneyStatus(true,false);
                }
            }
        });

        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String string = etMoney.getText().toString().trim();
                if (string.contains(".")) {
                    T.showShort(AskQuestionsActivity.this, "只能输入整数");
                }

                if (StringUtils.isNotEmpty(string)) {
                    if (string.startsWith("0")) {
                        T.showShort(AskQuestionsActivity.this, "请输入正确的金额");
                    } else {
                        mCurrentMoney = Integer.valueOf(string);

                        showMoneyStatus(rbAdian.isChecked(),rbAzuan.isChecked());

                    }
                }else {
                    mCurrentMoney = 0;
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
            public void afterTextChanged(Editable editable) {
                int count = editable.toString().length();
                String countStr = count + "/500";
                if (count > 500) {
                    SpannableString spannableString = new SpannableString(countStr);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
                    spannableString.setSpan(foregroundColorSpan, 0,
                            String.valueOf(count).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    tvCount.setText(spannableString);
                } else {
                    tvCount.setText(countStr);
                }
            }
        });
    }

    private void showMoneyStatus(boolean isCheckAdian,boolean isCheckAzuan) {
        if (mCurrentMoney == 0) {
            return;
        }

        if (isCheckAdian) {
            if (mCurrentMoney > mPoint) {
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText("余额不足请充值 >");
                isCanNotAsk = true;
            } else {
                tvStatus.setVisibility(View.INVISIBLE);
                isCanNotAsk = false;
            }
        } else {
            if (isCheckAzuan){
                if (mCurrentMoney > mDiamond) {
                    tvStatus.setVisibility(View.VISIBLE);
                    tvStatus.setText("余额不足");
                    isCanNotAsk = true;
                } else {
                    tvStatus.setVisibility(View.INVISIBLE);
                    isCanNotAsk = false;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
    }

    private void getUserData() {

        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfoDto) {
                mUserInfoDto = userInfoDto;
                mPoint = mUserInfoDto.getPoint();
                String diamondStr = mUserInfoDto.getDiamond();
                if (StringUtils.isNotEmpty(diamondStr)) {
                    mDiamond = Double.parseDouble(diamondStr);
                }
                showMoneyStatus(rbAdian.isChecked(),rbAzuan.isChecked());
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }

    @OnClick({R.id.bt_submit, R.id.tv_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                if (isCanNotAsk) {
                    if (rbAdian.isChecked()) {
                        T.showShort(AskQuestionsActivity.this,"余额不足请充值");
                    }else {
                        T.showShort(AskQuestionsActivity.this,"余额不足");
                    }
                    return;
                }
                submit();
                break;
            case R.id.tv_status:
                goBuy();
                break;
        }
    }


    private void goBuy() {
        if (rbAdian.isChecked()) { //
            Intent intent = new Intent(this, ArechargeComActivity.class);
            startActivity(intent);
        }
    }

    private void submit() {

        final String content = etContent.getText().toString().trim();
        final String money = etMoney.getText().toString().trim();
        if (content.length() > 500) {
            T.showShort(this, "内容超过限制");
            return;
        }

        if (money.contains(".")) {
            T.showShort(this, "请输入整数的金额");
            return;
        }

        checkPwd();


    }

    private void checkPwd() {

        if (mUserInfoDto == null) {
            return;
        }

        if (mUserInfoDto.getIsSetPayPwd() == 0) {
            final MyComstomDialogView myComstomDialogView =
                    new MyComstomDialogView(this);
            myComstomDialogView.setTvTitle("为了你的账户安全，购买前需要设置支付密码", View.VISIBLE)
                    .setChildMsg("", View.GONE)
                    .setChildMsg2("", View.GONE)
                    .setConcelMsg("", View.GONE)
                    .setRootBg(R.mipmap.boxbg2)
                    .setConfirmMsg("确定", View.VISIBLE)
                    .setConfirmSize(6)
                    .setCancelLisenner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myComstomDialogView.dismiss();

                        }
                    }).setConfirmLisenner(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myComstomDialogView.dismiss();
                    Intent intent1 = new Intent(AskQuestionsActivity.this,
                            SettingComActivity.class);
                    startActivity(intent1);
                }
            });
            myComstomDialogView.show();
        } else {
            final PayPasswordDialog dialog = new PayPasswordDialog(AskQuestionsActivity.this,
                    R.style.mydialog);
            dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                @Override
                public void doConfirm(String password) {
                    dialog.dismiss();

                    showDefaultLoadingDialog();
                    CommonModel.checkPayPassword(password, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> t) {
                            if (t.getCode() == 0) {

                                ask();
                            } else {
                                dismissLoadingDialog();
                                Toast.makeText(AskQuestionsActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
                }
            });
            dialog.show();
        }
    }



    private void ask() {

        final String content = etContent.getText().toString().trim();
        final String money = etMoney.getText().toString().trim();
        final int questionMoney = Integer.valueOf(money);
        String json = GsonUtil.GsonString(new QuestionParams(mLiveDetailInfo.getMemberId(), questionMoney, rewardType,mLiveDetailInfo.getId(),content));

        CommonModel.question(json, new MyObserver() {
            @Override
            public void onSuccess(BaseResponse t) {

                dismissLoadingDialog();

                String title;
                if (rbAzuan.isChecked() && mUserInfoDto != null) {
                    title = mUserInfoDto.getNickName() + "花" + money + "A钻提问";
                } else {
                    title = mUserInfoDto.getNickName() + "花" + money + "A点提问";
                }

                Intent intent = new Intent();
                intent.putExtra(TITLE, title);
                intent.putExtra(CONTENT, content);

                if (t.getCode() == 0) {
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(FAIL, intent);
                }
                finish();

            }
        });
    }

    public static final int FAIL = 30;

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
}

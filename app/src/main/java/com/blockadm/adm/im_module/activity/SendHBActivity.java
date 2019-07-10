package com.blockadm.adm.im_module.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ArechargeComActivity;
import com.blockadm.adm.activity.SettingComActivity;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseTitleActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.live.param.SendHBParam;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.HBPayPasswordDialog;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;
import com.blockadm.common.widget.CommonDialog;

/**
 * Created by Kris on 2019/6/12
 *
 * @Describe TODO { 发红包界面 }
 */
public class SendHBActivity extends BaseTitleActivity {


    private EditText mEt_money;
    private EditText mEt_count;
    private EditText mEt_content;
    private RadioButton mRb_azuan;
    private RadioButton mRb_adian;
    private LinearLayout mLayout_money;
    private TextView mTv_money;
    private TextView mTv_unit;
    private TextView mTv_text_count;
    private Button mBt_submit;

    private UserInfoDto mUserInfoDto;
    private int rewardType; //0 A钻 , 1 A点
    private final int AZUAN_TYPE = 0;
    private final int ADIAN_TYPE = 1;
    private double mAmount;
    private int mCount;
    private int newsLiveLessonsId;

    public static final String news_Live_Lessons_Id = "newsLiveLessonsId";
    public static final String HB_id = "HB_id"; //红包id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomView(R.layout.activity_send_money);
        setTitle("发红包");
        newsLiveLessonsId = getIntent().getIntExtra(news_Live_Lessons_Id,0);


        mEt_money = findContentViewById(R.id.et_money);
        mEt_count = findContentViewById(R.id.et_count);
        mEt_content = findContentViewById(R.id.et_content);
        mRb_azuan = findContentViewById(R.id.rb_Azuan);
        mRb_adian = findContentViewById(R.id.rb_Adian);
        mLayout_money = findContentViewById(R.id.layout_money);
        mTv_money = findContentViewById(R.id.tv_money);
        mTv_unit = findContentViewById(R.id.tv_unit);
        mTv_text_count = findContentViewById(R.id.tv_text_count);
        mBt_submit = findContentViewById(R.id.bt_submit);

        mRb_azuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mEt_money.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //只能输入数字和小数点
            }
        });
        mRb_adian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEt_money.setInputType(InputType.TYPE_CLASS_NUMBER); //只输入数字

                    String money = mEt_money.getText().toString().trim();
                    String text = money.contains(".") ? money.substring(0, money.indexOf(".")) :
                            money;
                    mEt_money.setText(text);
                    mEt_money.setSelection(text.length());
                }

            }
        });

        mBt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        mEt_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String money = mEt_money.getText().toString().trim();
                if (StringUtils.isNotEmpty(money)) {
                    mLayout_money.setVisibility(View.VISIBLE);
                    mTv_money.setText(money);
                    if (mRb_adian.isChecked()) {
                        mTv_unit.setText("A点");
                    } else {
                        mTv_unit.setText("A钻");
                    }
                } else {
                    mLayout_money.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
    }

    private int mPoint;
    private double mDiamond;
    private void getUserData() {

        CommonModel.getUserData(this, new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                mUserInfoDto = userInfo;
                mPoint = mUserInfoDto.getPoint();
                String diamondStr = mUserInfoDto.getDiamond();
                if (StringUtils.isNotEmpty(diamondStr)) {
                    mDiamond = Double.parseDouble(diamondStr);
                }
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }


    private int getAdianMoney(String money) {
        double num = Double.parseDouble(money);//装换为double类型
        return (int) num;
    }

    private double getAzuanMoney(String money) {
        double num;
        java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.00");
        num = Double.parseDouble(money);//装换为double类型
        num = Double.parseDouble(myformat.format(num));//保留2为小数
        return num;
    }

    /**
     * A钻最少可以发10A钻
     * A点最少要发1000A点
     * 红包个数限制为200个
     */
    private void submit() {
        String money = mEt_money.getText().toString().trim();


        if (StringUtils.isNotEmpty(money)) {
            if (mRb_adian.isChecked()) {
                rewardType = ADIAN_TYPE;
                int AdianMoney = getAdianMoney(money) ;
                if (AdianMoney < 1000) {
                    T.showShort(this, "最少要发1000A点");
                    return;
                }
                mAmount = AdianMoney;
            } else if (mRb_azuan.isChecked()) {
                rewardType = AZUAN_TYPE;
                double AzuanMoney = getAzuanMoney(money);
                if (AzuanMoney  < 10) {
                    T.showShort(this, "最少要发10A钻");
                    return;
                }
                mAmount = AzuanMoney;
            }
        } else {
            T.showShort(this, "请输入红包金额");
            return;
        }

        String countStr = mEt_count.getText().toString().trim().replaceAll(" ","");
        if (StringUtils.isNotEmpty(countStr)) {
            mCount = Integer.valueOf(countStr);
            if (mCount > 200) {
                T.showShort(this, "最多可以发200个红包");
                return;
            }
        } else {
            T.showShort(this, "请输入红包个数");
            return;
        }

        if (mRb_adian.isChecked()) {
            if (mAmount > mPoint) {
                AdianShow();
                return;
            }
        } else if (mRb_azuan.isChecked()) {
            if (mAmount > mDiamond) {
                AzuanShow();
                return;
            }
        }

        checkPwd();
    }

    private void AdianShow() {
        new CommonDialog.Builder(this)
                .setTitle("余额不足，支付失败")
                .setPositiveButton("我要充值A点", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(SendHBActivity.this, ArechargeComActivity.class);
                        startActivity(intent);
                    }
                })
                .create().show();
    }

    private void AzuanShow() {
        new CommonDialog.Builder(this)
                .setTitle("余额不足，支付失败")
                .create().show();
    }


    private void checkPwd() {
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
                    Intent intent1 = new Intent(SendHBActivity.this,
                            SettingComActivity.class);
                    startActivity(intent1);
                }
            });
            myComstomDialogView.show();
        } else {
            final HBPayPasswordDialog dialog = new HBPayPasswordDialog(SendHBActivity.this,
                    R.style.mydialog);
            dialog.setDialogClick(new HBPayPasswordDialog.DialogClick() {
                @Override
                public void doConfirm(String password) {
                    dialog.dismiss();
                    showDefaultLoadingDialog();
                    CommonModel.checkPayPassword(password, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> t) {
                            if (t.getCode() == 0) {

                                sendHB();
                            } else {
                                dismissLoadingDialog();
                                Toast.makeText(SendHBActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
                }
            });

            String unit = rewardType == AZUAN_TYPE? "A钻":"A点";
            dialog.setMoney(mAmount+"",unit);
            dialog.show();
        }

    }


    private String content = "恭喜发财，大吉大利";
    private void sendHB() {

        String json = GsonUtil.GsonString(new SendHBParam(content,mAmount,newsLiveLessonsId,rewardType,mCount));

        showDefaultLoadingDialog();
        CommonModel.sendRedpacket(json, new MyObserver<Integer>() {
            @Override
            public void onSuccess(BaseResponse<Integer> t) {

                dismissLoadingDialog();
                if (t != null && t.isSuccess()) {
                    Intent intent = new Intent();
                    intent.putExtra(HB_id,t.getData());
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    T.showShort(SendHBActivity.this,t.getMsg());
                }

            }

        });
    }
























}

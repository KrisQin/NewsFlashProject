package com.blockadm.adm.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.PayPasswordDialog;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ToastUtils;
import com.blockadm.common.web.FullWebBaseViewActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/3/6.
 */

public class WithdrawComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;

    @BindView(R.id.iv_alipay)
    ImageView ivAlipay;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.et_point)
    AppCompatEditText etPoint;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    @BindView(R.id.et_name)
    EditText etNmame;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    private ClipboardManager myClipboard;
    private String nameString;
    
    private static final String Wechat_TAG = "1";
    private static final String Alipay_TAG = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_withdraw);
        ButterKnife.bind(this);
        ivAlipay.setTag(Alipay_TAG);
        ivWechat.setTag(Wechat_TAG);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        etPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               String content =  s.toString().trim();
               if (!TextUtils.isEmpty(content)){
                   int num = Integer.parseInt(content);
                   int money =  num/100;
                   tvMoney.setText("¥ "+money);
               }

            }
        });

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    int type = -1;
    private boolean isWXPay = true;
    @OnClick({R.id.iv_wechat, R.id.iv_alipay, R.id.tv_copy, R.id.tv_complete})
    public void onViewClicked(View view) {
        String tagWechat = (String) ivWechat.getTag();
        String tagAlipay = (String) ivAlipay.getTag();
        switch (view.getId()) {

            case R.id.iv_wechat:
                etNmame.setVisibility(View.VISIBLE);
                isWXPay = true;
                if (tagWechat.equals(Wechat_TAG)){
                    ivWechat.setTag(Alipay_TAG);
                    ivAlipay.setTag(Wechat_TAG);
                    ivWechat.setImageResource(R.mipmap.payforwechat_default);
                    ivAlipay.setImageResource(R.mipmap.payforalipay_press);
                }else{
                    ivWechat.setTag(Wechat_TAG);
                    ivAlipay.setTag(Alipay_TAG);
                    ivWechat.setImageResource(R.mipmap.payforwechat_press);
                    ivAlipay.setImageResource(R.mipmap.payforalipay_default);
                }
                break;
            case R.id.iv_alipay:
                etNmame.setVisibility(View.GONE);
                isWXPay = false;
                if (tagAlipay.equals(Wechat_TAG)){
                    ivAlipay.setTag(Alipay_TAG);
                    ivWechat.setTag(Wechat_TAG);
                    ivAlipay.setImageResource(R.mipmap.payforalipay_default);
                    ivWechat.setImageResource(R.mipmap.payforwechat_press);
                }else{
                    ivAlipay.setTag(Wechat_TAG);
                    ivWechat.setTag(Alipay_TAG);
                    ivAlipay.setImageResource(R.mipmap.payforalipay_press);
                    ivWechat.setImageResource(R.mipmap.payforwechat_default);
                }
                break;
            case R.id.tv_copy:
                String text = tvCopy.getText().toString();
                ClipData myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                ToastUtils.showToast("已经复制到剪切板");
                break;
            case R.id.tv_complete:
              UserInfoDto  userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
               String  point =  etPoint.getText().toString().trim();
               if (TextUtils.isEmpty(point)){
                   return;
               }
              final int pointNum = Integer.parseInt(point);

                if (pointNum<10000){
                   showToast(this,"最低提现100块");
                   return;
               }else if (pointNum>userInfoDto.getPoint()){
                   showToast(this,"请提现余额现有A点");
                   return ;
               }

                int yushu = pointNum%100;
                if (yushu>0){
                    showToast(this,"请提现A点为100的整数");
                    return ;
                }

                if (tagAlipay.equals(Wechat_TAG)){
                    type =0;
                }
                if (tagWechat.equals(Wechat_TAG)){
                    type =1;
                }
                nameString = etNmame.getText().toString().trim();
                if (TextUtils.isEmpty(nameString) && isWXPay){
                    showToast(this,"请输入姓名");
                    return ;
                }
                if (userInfoDto.getIsBindWXWithdrawAccount()==0){

                    String url = "https://mp.weixin.qq.com/s/W7w_s5jyzSUJlOjKWxhVCw";
                    Intent intent = new Intent(WithdrawComActivity.this, FullWebBaseViewActivity.class);
                    intent.putExtra(FullWebBaseViewActivity.DATA_WEB_URL,url);
                    WithdrawComActivity.this.startActivity(intent);

//                    final MyComstomDialogView  myComstomDialogView1 = new MyComstomDialogView(WithdrawComActivity.this);
//                    myComstomDialogView1.setTvTitle("请进入微信公众号授权",View.VISIBLE).setRootBg(R.mipmap.boxbg2)
//                            .setChildMsg("",View.GONE)
//                            .setChildMsg2("",View.GONE)
//                            .setConcelMsg("",View.GONE)
//                            .setConfirmMsg("确定",View.VISIBLE)
//                            .setConfirmSize(6)
//                           .setConfirmLisenner(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            myComstomDialogView1.dismiss();
//                            String appId = "wxc9b4ef7a71143f2a";//开发者平台ID
//                            IWXAPI api = WXAPIFactory.createWXAPI(WithdrawComActivity.this, appId, false);
//
//                            if (api.isWXAppInstalled()) {
//                                JumpToBizProfile.Req req = new JumpToBizProfile.Req();
//                                req.toUserName = "gh_121c7f56f6e8"; // 公众号原始ID
//                                req.extMsg = "";
//                                req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE; // 普通公众号
//                                api.sendReq(req);
//                            }else{
//                                Toast.makeText(WithdrawComActivity.this, "微信未安装", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//                    myComstomDialogView1.show();
                    return ;
                }

                if (userInfoDto.getIsSetPayPwd()==0){
                    final MyComstomDialogView myComstomDialogView = new MyComstomDialogView(WithdrawComActivity.this);
                    myComstomDialogView.setTvTitle("请到 我的>>设置>>支付密码 设置支付密码",View.VISIBLE)
                            .setChildMsg("",View.GONE)
                            .setChildMsg2("",View.GONE)
                            .setConcelMsg("",View.GONE)

                            .setConfirmMsg("我知道了",View.VISIBLE)
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
                        }
                    });
                    myComstomDialogView.show();
                }else  if (userInfoDto.getPersonalCredentialsSate()==0||userInfoDto.getPersonalCredentialsSate()==1){
                    final MyComstomDialogView  myComstomDialogView1 = new MyComstomDialogView(WithdrawComActivity.this);
                    myComstomDialogView1.setTvTitle("请先完成个人/实名认证",View.VISIBLE).setRootBg(R.mipmap.boxbg2)
                            .setChildMsg("",View.GONE)
                            .setChildMsg2("",View.GONE)
                            .setConcelMsg("",View.GONE)
                            .setConfirmMsg("确定",View.VISIBLE)
                            .setConfirmSize(6)
                            .setCancelLisenner(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).setConfirmLisenner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myComstomDialogView1.dismiss();
                            Intent  intent = new Intent(WithdrawComActivity.this, AuthenticationComActivity.class);
                            startActivity(intent);
                        }
                    });
                    myComstomDialogView1.show();

                }else  if (userInfoDto.getIsBindZFBWithdrawAccount()==0&&type==0){
                    Intent intent = new Intent(WithdrawComActivity.this, AddAlipayAcountComActivity.class);
                    startActivity(intent);
                }else{
                      final PayPasswordDialog dialog=new PayPasswordDialog(WithdrawComActivity.this,R.style.mydialog);
                      dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                        @Override
                        public void doConfirm(String password) {
                            dialog.dismiss();
                            CommonModel.checkPayPassword(password, new MyObserver <String>() {
                                @Override
                                public void onSuccess(BaseResponse <String> t) {
                                    if (t.getCode()==0){
                                        CommonModel.doWithdraw(nameString,pointNum, type, new MyObserver<String>() {
                                            @Override
                                            public void onSuccess(BaseResponse<String> t) {
                                                if (t.getCode()==0){
                                                    EventBus.getDefault().post(new UserDataEvent());
                                                    Intent intent = new Intent(WithdrawComActivity.this, WithdrawSuccessComActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else{
                                                    showToast(WithdrawComActivity.this,t.getMsg());
                                                }
                                            }


                                        });
                                    }else{
                                        Toast.makeText(WithdrawComActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });
                        }
                    });
                    dialog.show();
                }


                break;
        }
    }
}

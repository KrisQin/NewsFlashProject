package com.blockadm.adm.persenter;

import android.util.Log;

import com.blockadm.common.bean.params.LoginBean;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.adm.contact.LoginContract;
import com.blockadm.common.utils.ToastUtils;

import io.reactivex.disposables.Disposable;


/**
*/
public class LoginPresenter extends LoginContract.Presenter{

    private String tag = "LoginPresenter";
    @Override
    public void login(int type ,String username, String password,String recommend) {
        mModel.login(type ,username,password,recommend,new MyObserver<LoginBean>() {

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                Log.i(tag, "onSubscribe");
            }


            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(tag, "onError" +e.toString());
            }

            @Override
            public void onComplete() {
                super.onComplete();
                Log.i(tag, "onSubscribe");
            }

            @Override
            public void onSuccess(BaseResponse<LoginBean> t) {
                ToastUtils.showToast(t.getMsg());
                if (mView!=null){
                    mView.showLoginSucceed(t);
                }

            }
        });


    }

    @Override
    public void sendSms(String phoneNum) {
        mModel.sendSms(phoneNum,new MyObserver<String>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);

            }

            @Override
            public void onSuccess(BaseResponse<String> t) {
                if (mView!=null){
                    mView.showYanzhengma(t);
                }

            }

        });
    }

    @Override
    public void addFeedback(String jsonString) {
        mModel.addFeedback(jsonString,new MyObserver<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                Log.i(tag, "onSubscribesendSms");
            }



            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(tag, "onError" +e.toString());
            }

            @Override
            public void onComplete() {
                super.onComplete();
                Log.i(tag, "onSubscribe");
            }

            @Override
            public void onSuccess(BaseResponse<String> t) {

            }
        });
    }


}
package com.blockadm.adm.model;
import com.blockadm.common.http.RetrofitManager;
import com.blockadm.common.utils.Utils;
import com.blockadm.adm.contact.LoginContract;
import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 */
public class LoginModel implements LoginContract.Model {



    @Override
    public void login( int type ,String phone ,String code , String recommend , Observer  observer) {
        RetrofitManager.
                getService().
                login(type,phone,code,recommend).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);



    }

    @Override
    public void sendSms(String phoneNum, Observer observer) {
        RetrofitManager.
                getService().
                sendSms(phoneNum).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void addFeedback(String jsonString, Observer observer) {
        RetrofitManager.
                getService().
                addFeedback(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
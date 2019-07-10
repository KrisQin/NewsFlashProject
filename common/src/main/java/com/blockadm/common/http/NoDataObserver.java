package com.blockadm.common.http;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.base.BaseApplication;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.T;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by hp on 2018/12/11.
 */

public  abstract class NoDataObserver<T> implements Observer<T> {

    private Disposable disposable;
    private String errorMsg;




    @Override
    public void onSubscribe(Disposable d) {
        disposable = new CompositeDisposable();
    }

    @Override
    public void onNext(T value) {
        onSuccess(value);
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onError(Throwable e) {

        if (e instanceof IOException) {
            /** 没有网络 */
            errorMsg = "请检查你的网络状态";
        } else if (e instanceof HttpException) {
            /** 网络异常，http 请求失败，即 http 状态码不在 [200, 300) 之间, */
            errorMsg = ((HttpException) e).response().message();
        } else if (e instanceof ApiException) {
            /** 网络正常，http 请求成功，服务器返回逻辑错误 */
            errorMsg = ((ApiException)e).getMsg();
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            errorMsg = "解析错误";
        } else if (e instanceof ConnectException) {
            errorMsg = "连接失败";
        }else {
            /** 其他未知错误 */
            errorMsg = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : "unknown error";
        }


        L.d(errorMsg);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public abstract void onSuccess(T t);


}

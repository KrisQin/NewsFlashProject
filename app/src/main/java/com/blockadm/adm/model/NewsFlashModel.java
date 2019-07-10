package com.blockadm.adm.model;


import com.blockadm.adm.contact.NewsFlashContract;
import com.blockadm.common.http.RetrofitManager;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 */
public class NewsFlashModel implements NewsFlashContract.Model {




    @Override
    public void getNewsFlashList(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                getNewsFlashList(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void operateNewsFlashCount( int  id, int operateType, Observer observer) {
        RetrofitManager.
                getService().
                operateNewsFlashCount(id,operateType).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
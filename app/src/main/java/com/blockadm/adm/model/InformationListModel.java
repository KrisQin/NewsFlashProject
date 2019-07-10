package com.blockadm.adm.model;

import com.blockadm.adm.contact.InformationListContract;
import com.blockadm.common.http.RetrofitManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 */
public class InformationListModel implements InformationListContract.Model {


    @Override
    public void newsArticlePage(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                getNewsArticlePage(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
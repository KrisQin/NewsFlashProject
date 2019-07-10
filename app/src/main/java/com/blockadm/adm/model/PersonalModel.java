package com.blockadm.adm.model;

import com.blockadm.adm.contact.PersonalContract;
import com.blockadm.adm.contact.StudyContract;
import com.blockadm.common.http.RetrofitManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 */
public class PersonalModel implements PersonalContract.Model {




    @Override
    public void newsArticlePage(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                getNewsArticlePage(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void pageNewsLessons(String jsonString, Observer observer) {
      /*  RetrofitManager.
                getService().
                pageNewsLessons(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);*/
    }

    @Override
    public void pageNewsLessonsSpecialColumn(String jsonString, Observer observer) {
        RetrofitManager.
                getService().
                pageNewsLessonsSpecialColumn(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
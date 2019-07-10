package com.blockadm.adm.model;

import com.blockadm.adm.contact.StudyContract;
import com.blockadm.common.http.RetrofitManager;
import com.blockadm.common.utils.L;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 */
public class StudyListModel implements StudyContract.Model {



    @Override
    public void pageNewsLessonsList(String jsonString, Observer observer) {

        L.d("pageNewsLessonsList jsonString : "+jsonString);
        RetrofitManager.
                getService().
                pageNewsLessonsList(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void pageNewsLessonsSpecialColumn(String jsonString, Observer observer) {
        L.d("pageNewsLessonsSpecialColumn jsonString : "+jsonString);

        RetrofitManager.
                getService().
                pageNewsLessonsSpecialColumn(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
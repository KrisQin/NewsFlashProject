package com.blockadm.adm.model;

import com.blockadm.adm.contact.CommentDetailContract;
import com.blockadm.adm.contact.LessonDetailContract;
import com.blockadm.common.http.RetrofitManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 */
public class LessonsDetailModel implements LessonDetailContract.Model {

    @Override
    public void pageNewsLessons(String nlscId,String dataType, Observer observer) {
        RetrofitManager.
                getService().
                pageNewsLessons(nlscId,dataType).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void findNewsLessonsSpecialColumnById(int id,int w, Observer observer) {
        RetrofitManager.
                getService().
                findNewsLessonsSpecialColumnById(id,w).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void findNewsLessonsById(int id,int w, Observer observer) {
        RetrofitManager.
                getService().
                findNewsLessonsById(id,w).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
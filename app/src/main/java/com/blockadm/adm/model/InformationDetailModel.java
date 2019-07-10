package com.blockadm.adm.model;

import com.blockadm.adm.contact.InfomationDetailContract;
import com.blockadm.common.http.RetrofitManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 */
public class InformationDetailModel implements InfomationDetailContract.Model {






    @Override
    public void getNewsArticlenewsArticle(int imageMaxWidth,int id, Observer observer) {
        RetrofitManager.
                getService().
                getNewsArticlenewsArticle(imageMaxWidth,id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void getNewsArticleCommentPage(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                getNewsArticleCommentPage(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void addReply(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                addReply(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void operateArticleCount(int id, int operateType, int choose, Observer observer) {
        RetrofitManager.
                getService().
                operateArticleCount(id,operateType,choose).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void addNewsArticleComment(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                addNewsArticleComment(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void addReport(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                addReport(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
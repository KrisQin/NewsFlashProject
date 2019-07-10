package com.blockadm.adm.model;

import com.blockadm.adm.contact.CommentDetailContract;
import com.blockadm.common.http.RetrofitManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 */
public class CommentDetailModel implements CommentDetailContract.Model {
    @Override
    public void pageNewsArticleCommentReply(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                pageNewsArticleCommentReply(jsonData).
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


}
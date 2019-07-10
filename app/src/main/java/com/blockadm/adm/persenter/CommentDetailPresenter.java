package com.blockadm.adm.persenter;

import com.blockadm.adm.contact.CommentDetailContract;
import com.blockadm.common.bean.CommentDetailDto;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;


/**
*/
public class CommentDetailPresenter extends CommentDetailContract.Presenter{
    @Override
    public void pageNewsArticleCommentReply(String jsonData) {
        mModel.pageNewsArticleCommentReply(jsonData,new MyObserver<CommentDetailDto>() {

            @Override
            public void onSuccess(BaseResponse<CommentDetailDto> t) {
                mView.pageNewsArticleCommentReply(t.getData());
            }



        });
    }

    @Override
    public void addReply(String jsonData) {
        mModel.addReply(jsonData,new MyObserver<CommentReplyListBean>() {

            @Override
            public void onSuccess(BaseResponse<CommentReplyListBean> t) {
                mView.addReply(t.getData());
            }



        });
    }


}
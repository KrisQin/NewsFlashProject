package com.blockadm.adm.persenter;

import com.blockadm.adm.contact.InfomationDetailContract;
import com.blockadm.adm.contact.PersonalContract;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.NewsArticleCommentDTO;
import com.blockadm.common.bean.OperateArticleCountDTO;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;


/**
*/
public class InfomationDetalPresenter extends InfomationDetailContract.Presenter{



    @Override
    public void getNewsArticlenewsArticle(int imageMaxWidth ,int id) {
        mModel.getNewsArticlenewsArticle(imageMaxWidth,id,new MyObserver<NewsArticleCommentDTO>() {

            @Override
            public void onSuccess(BaseResponse<NewsArticleCommentDTO> newsArticleCommentDTO) {
                if (mView!=null){
                    mView.showNewsArticlenewsArticle(newsArticleCommentDTO.getData());
                }

            }



        });
    }

    @Override
    public void getNewsArticleCommentPage(String jsonData) {
        mModel.getNewsArticleCommentPage(jsonData,new MyObserver<PageNewsArticleCommentDTO>() {

            @Override
            public void onSuccess(BaseResponse<PageNewsArticleCommentDTO> pageNewsArticleCommentDTO) {
                if (mView!=null){
                    mView.showNewsArticleCommentPage(pageNewsArticleCommentDTO.getData());
                }

            }



        });
    }

    @Override
    public void addReply(String jsonData) {
        mModel.addReply(jsonData,new MyObserver<CommentReplyListBean>() {

            @Override
            public void onSuccess(BaseResponse<CommentReplyListBean> commentReplyListBean) {
                mView.showAddReply(commentReplyListBean.getData());
            }



        });
    }

    @Override
    public void operateArticleCount(int id, int operateType, int choose) {
        mModel.operateArticleCount(id,operateType,choose,new MyObserver<OperateArticleCountDTO>() {

            @Override
            public void onSuccess(BaseResponse<OperateArticleCountDTO> t) {
                mView.operateArticleCount(t);
            }



        });
    }

    @Override
    public void addNewsArticleComment(String jsonData) {
        mModel.addNewsArticleComment(jsonData,new MyObserver<PageNewsArticleCommentDTO.RecordsBean>() {

            @Override
            public void onSuccess(BaseResponse<PageNewsArticleCommentDTO.RecordsBean> recordsBean) {
                mView.showAddNewsArticleComment(recordsBean.getData());
            }



        });
    }

    @Override
    public void addReport(String jsonData) {
        mModel.addReport(jsonData,new MyObserver<String>() {

            @Override
            public void onSuccess(BaseResponse<String> s) {
                mView.addReport(s.getMsg());
            }



        });
    }
}
package com.blockadm.adm.contact;

import com.blockadm.common.base.BaseModel;
import com.blockadm.common.base.BasePersenter;
import com.blockadm.common.base.IbaseView;
import com.blockadm.common.bean.CommentDetailDto;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;

import io.reactivex.Observer;

/**
 * Created by hp on 2019/1/9.
 */

public class CommentDetailContract {

    public interface View extends IbaseView {
        void  pageNewsArticleCommentReply(CommentDetailDto commentDetailDto);
        void  addReply(CommentReplyListBean commentReplyListBean);
    }

    public  interface Model extends BaseModel {
        void pageNewsArticleCommentReply(String jsonData, Observer observer);
        void addReply(String jsonData, Observer observer);
    }

    public static abstract class Presenter extends BasePersenter<CommentDetailContract.View, CommentDetailContract.Model> {
        public abstract  void pageNewsArticleCommentReply(String jsonData);
        public abstract  void addReply(String jsonData);
    }
}

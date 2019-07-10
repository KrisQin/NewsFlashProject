package com.blockadm.adm.contact;



import com.blockadm.common.base.BaseModel;
import com.blockadm.common.base.BasePersenter;
import com.blockadm.common.base.IbaseView;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.NewsArticleCommentDTO;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.blockadm.common.bean.OperateArticleCountDTO;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;
import com.blockadm.common.bean.ZanBean;
import com.blockadm.common.http.BaseResponse;

import io.reactivex.Observer;

/**
 * Created by hp on 2019/1/9.
 */

public class InfomationDetailContract {

    public interface View extends IbaseView {
        void  showNewsArticlenewsArticle(NewsArticleCommentDTO newsArticleCommentDTO);
        void  showNewsArticleCommentPage(PageNewsArticleCommentDTO pageNewsArticleCommentDTO);
        void  showAddReply(CommentReplyListBean operateArticleCountDTO);
        void  operateArticleCount(BaseResponse<OperateArticleCountDTO> t);
        void  showAddNewsArticleComment(PageNewsArticleCommentDTO.RecordsBean recordsBean);
        void  addReport(String msg);
    }

    public  interface Model extends BaseModel {
        void getNewsArticlenewsArticle(int imageMaxWidth,int  id, Observer observer);
        void getNewsArticleCommentPage(String jsonData, Observer observer);

        void addReply(String jsonData, Observer observer);
        void operateArticleCount(int id, int operateType, int choose, Observer observer);
        void addNewsArticleComment(String jsonData, Observer observer);

        void addReport(String jsonData, Observer observer);
    }

    public static abstract class Presenter extends BasePersenter<InfomationDetailContract.View, InfomationDetailContract.Model> {
        public abstract  void getNewsArticlenewsArticle(int imageMaxWidth,int  id);

        public abstract  void getNewsArticleCommentPage(String jsonData);
        public abstract  void addReply(String jsonData);
        public abstract  void operateArticleCount(int id, int operateType, int choose);
        public abstract  void addNewsArticleComment(String jsonData);

        public abstract  void addReport(String jsonData);
    }
}

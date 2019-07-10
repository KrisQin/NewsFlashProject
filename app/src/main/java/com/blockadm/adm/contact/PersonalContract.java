package com.blockadm.adm.contact;

import com.blockadm.common.base.BaseModel;
import com.blockadm.common.base.BasePersenter;
import com.blockadm.common.base.IbaseView;
import com.blockadm.common.bean.NewsArticleCommentDTO;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.NewsLessonsDTO;
import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;
import com.blockadm.common.http.BaseResponse;

import io.reactivex.Observer;

/**
 * Created by hp on 2019/1/9.
 */

public class PersonalContract {

    public interface View extends IbaseView {
        void newsArticlePageShow(NewsArticleListDTO newsArticleListDTO);
        void showPageNewsLessonslist(BaseResponse<NewsLessonsDTO> lessonsDTOBaseResponse);
        void pageNewsLessonsSpecialColumn(BaseResponse<NewsLessonsSpecialColumnDto> newsLessonsSpecialColumnDtoBaseResponse);
    }

    public  interface Model extends BaseModel {
        void newsArticlePage(String jsonData, Observer observer);
        void pageNewsLessons(String jsonString, Observer observer);
        void pageNewsLessonsSpecialColumn(String jsonString,Observer observer);

    }

    public static abstract class Presenter extends BasePersenter<PersonalContract.View, PersonalContract.Model> {
        public abstract void newsArticlePage(String jsonData);
        public abstract void pageNewsLessonsSpecialColumn(String jsonString);
        public abstract  void  pageNewsLessons(String jsonString);

    }
}

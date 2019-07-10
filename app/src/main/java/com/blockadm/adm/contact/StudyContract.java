package com.blockadm.adm.contact;

import com.blockadm.common.base.BaseModel;
import com.blockadm.common.base.BasePersenter;
import com.blockadm.common.base.IbaseView;
import com.blockadm.common.bean.NewsLessonsDTO;
import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
import com.blockadm.common.http.BaseResponse;

import io.reactivex.Observer;

/**
 * Created by hp on 2018/12/12.
 */

public class StudyContract {

    public interface View extends IbaseView {
        void showPageNewsLessonslist(BaseResponse<NewsLessonsDTO> lessonsDTOBaseResponse);

        void pageNewsLessonsSpecialColumn(BaseResponse<NewsLessonsSpecialColumnDto> response);

    }

    public interface Model extends BaseModel {
        void pageNewsLessonsList(String jsonString, Observer observer);

        void pageNewsLessonsSpecialColumn(String jsonString, Observer observer);
    }

    public static abstract class Presenter extends BasePersenter<View, Model> {

        public abstract void pageNewsLessonsSpecialColumn(String jsonString);

        public abstract void pageNewsLessonsList(String jsonString);

    }
}

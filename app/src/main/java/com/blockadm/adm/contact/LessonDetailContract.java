package com.blockadm.adm.contact;

import com.blockadm.common.base.BaseModel;
import com.blockadm.common.base.BasePersenter;
import com.blockadm.common.base.IbaseView;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.bean.LessonsSpecialColumnDto;
import com.blockadm.common.bean.NewsLessonsDetailDTO;
import com.blockadm.common.http.BaseResponse;

import java.util.ArrayList;

import io.reactivex.Observer;

/**
 * Created by hp on 2019/1/9.
 */

public class LessonDetailContract {

    public interface View extends IbaseView {
        void  showFindNewsLessonsSpecialColumnById(BaseResponse<LessonsSpecialColumnDto> lessonsSpecialColumnDto);
        void  showFindNewsLessonsById(BaseResponse<NewsLessonsDetailDTO>  newsLessonsDetailDTO);
        void showPageNewsLessonsById(BaseResponse<ArrayList<RecordsBean>> lessonsDTOBaseResponse);
    }

    public  interface Model extends BaseModel {
        void findNewsLessonsSpecialColumnById(int id,int width, Observer observer);
        void findNewsLessonsById(int id,int width, Observer observer);
        void pageNewsLessons(String jsonString,String dataType ,Observer observer);
    }

    public static abstract class Presenter extends BasePersenter<LessonDetailContract.View, LessonDetailContract.Model> {
        public abstract  void findNewsLessonsSpecialColumnById(int id,int width);
        public abstract  void findNewsLessonsById(int id,int width);
        public abstract  void  pageNewsLessons(String jsonString,String dataType);
    }
}

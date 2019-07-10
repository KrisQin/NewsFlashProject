package com.blockadm.adm.contact;

import com.blockadm.common.base.BaseModel;
import com.blockadm.common.base.BasePersenter;
import com.blockadm.common.base.IbaseView;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.blockadm.common.bean.NewsLessonsDTO;
import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
import com.blockadm.common.http.BaseResponse;

import io.reactivex.Observer;

/**
 * Created by hp on 2019/1/9.
 */

public class InformationListContract {

    public interface View extends IbaseView {
        void newsArticlePageShow(NewsArticleListDTO newsArticleListDTO);

    }

    public  interface Model extends BaseModel {
        void newsArticlePage(String jsonData, Observer observer);


    }

    public static abstract class Presenter extends BasePersenter<InformationListContract.View, InformationListContract.Model> {

        public abstract void newsArticlePage(String jsonData);


    }
}

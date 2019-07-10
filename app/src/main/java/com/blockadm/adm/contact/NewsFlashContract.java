package com.blockadm.adm.contact;

import com.blockadm.common.base.BaseModel;
import com.blockadm.common.base.BasePersenter;
import com.blockadm.common.base.IbaseView;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.blockadm.common.bean.ZanBean;

import io.reactivex.Observer;

/**
 * Created by hp on 2019/1/9.
 */

public class NewsFlashContract {

    public interface View extends IbaseView {
       void showNewsFlashList(NewsFlashBeanDto newsFlashBeanDto);
        void show0perateNewsFlashCount(ZanBean zanBean);
    }

    public  interface Model extends BaseModel {
        void getNewsFlashList(String jsonData , Observer observer);
        void operateNewsFlashCount(int  id,int operateType , Observer observer);

    }

    public static abstract class Presenter extends BasePersenter<NewsFlashContract.View, NewsFlashContract.Model> {

        public abstract void getNewsFlashList(String jsonData);
        public abstract void operateNewsFlashCount(int  id,int operateType);

    }
}

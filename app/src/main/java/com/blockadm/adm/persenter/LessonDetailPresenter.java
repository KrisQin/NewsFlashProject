package com.blockadm.adm.persenter;

import android.util.Log;

import com.blockadm.adm.contact.LessonDetailContract;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.bean.LessonsSpecialColumnDto;
import com.blockadm.common.bean.NewsLessonsDetailDTO;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;

import java.util.ArrayList;


/**
*/
public class LessonDetailPresenter extends LessonDetailContract.Presenter{

    @Override
    public void findNewsLessonsSpecialColumnById(int id,int width) {
        mModel.findNewsLessonsSpecialColumnById(id,width,new MyObserver<LessonsSpecialColumnDto>() {

            @Override
            public void onSuccess(BaseResponse<LessonsSpecialColumnDto> t) {
                if(mView!=null){
                    mView.showFindNewsLessonsSpecialColumnById(t);
                }

            }


        });
    }

    @Override
    public void findNewsLessonsById(int id,int width) {
        mModel.findNewsLessonsById(id,width,new MyObserver<NewsLessonsDetailDTO>() {

            @Override
            public void onSuccess(BaseResponse<NewsLessonsDetailDTO> t) {
                if (mView!=null){
                    mView.showFindNewsLessonsById(t);
                }

            }



        });
    }

    @Override
    public void pageNewsLessons(String nlscId,String dataType) {
        Log.i("pageNewsLessons", nlscId+"    pageNewsLessons");
        mModel.pageNewsLessons(nlscId,dataType, new MyObserver<ArrayList<RecordsBean>>() {



            @Override
            public void onComplete() {
                super.onComplete();
            }

            @Override
            public void onSuccess(BaseResponse<ArrayList<RecordsBean>> lessonsDTOBaseResponse) {
                if (mView!=null){
                    mView.showPageNewsLessonsById(lessonsDTOBaseResponse);
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
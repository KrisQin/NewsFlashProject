package com.blockadm.adm.persenter;


import com.blockadm.adm.contact.StudyContract;
import com.blockadm.common.bean.NewsLessonsDTO;
import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.L;


/**
*/
public class StudyListPresenter extends StudyContract.Presenter{


    @Override
    public void pageNewsLessonsList(String jsonData) {

      mModel.pageNewsLessonsList(jsonData, new MyObserver<NewsLessonsDTO>() {



          @Override
          public void onComplete() {
              super.onComplete();
          }

          @Override
          public void onSuccess(BaseResponse<NewsLessonsDTO> lessonsDTOBaseResponse) {

              if (mView!=null){
                  mView.showPageNewsLessonslist(lessonsDTOBaseResponse);
              }

          }

          @Override
          public void onError(Throwable e) {
              super.onError(e);
          }
      });
    }

    @Override
    public void pageNewsLessonsSpecialColumn(String jsonData) {
        L.d("   22222222222 pageNewsLessonsSpecialColumn");
        mModel.pageNewsLessonsSpecialColumn(jsonData, new MyObserver<NewsLessonsSpecialColumnDto>() {

            @Override
            public void onSuccess(BaseResponse<NewsLessonsSpecialColumnDto> newsLessonsSpecialColumnDtoBaseResponse) {
                L.d("   22222222222 onSuccess   mView: "+mView);
                if (mView!=null){
                    mView.pageNewsLessonsSpecialColumn(newsLessonsSpecialColumnDtoBaseResponse);
                }

            }
        });
    }



}
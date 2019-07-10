package com.blockadm.adm.persenter;


import com.blockadm.adm.contact.PersonalContract;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.NewsLessonsDTO;
import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;


/**
*/
public class PersonalPresenter extends PersonalContract.Presenter{


    @Override
    public void newsArticlePage(String jsonData) {
      mModel.newsArticlePage(jsonData, new MyObserver<NewsArticleListDTO>() {



          @Override
          public void onComplete() {
              super.onComplete();
          }

          @Override
          public void onSuccess(BaseResponse<NewsArticleListDTO> newsArticleListDTO) {
              if (mView!=null){
                  mView.newsArticlePageShow(newsArticleListDTO.getData());
              }

          }

          @Override
          public void onError(Throwable e) {
              super.onError(e);
          }
      });
    }

    @Override
    public void pageNewsLessons(String jsonData) {
 /*       mModel.pageNewsLessons(jsonData, new MyObserver<NewsLessonsDTO>() {



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
        });*/
    }

    @Override
    public void pageNewsLessonsSpecialColumn(String jsonData) {
        mModel.pageNewsLessonsSpecialColumn(jsonData, new MyObserver<NewsLessonsSpecialColumnDto>() {



            @Override
            public void onComplete() {
                super.onComplete();
            }

            @Override
            public void onSuccess(BaseResponse<NewsLessonsSpecialColumnDto> newsLessonsSpecialColumnDtoBaseResponse) {
                if (mView!=null){
                    mView.pageNewsLessonsSpecialColumn(newsLessonsSpecialColumnDtoBaseResponse);
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
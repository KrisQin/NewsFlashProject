package com.blockadm.adm.persenter;


import com.blockadm.adm.contact.InformationListContract;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;


/**
*/
public class InformationListPresenter extends InformationListContract.Presenter{


    @Override
    public void newsArticlePage(String jsonData) {
      mModel.newsArticlePage(jsonData, new MyObserver<NewsArticleListDTO>() {



          @Override
          public void onComplete() {
              super.onComplete();
          }

          @Override
          public void onSuccess(BaseResponse<NewsArticleListDTO> newsArticleListDTO) {
              if (newsArticleListDTO!=null){
                  try{
                      mView.newsArticlePageShow(newsArticleListDTO.getData());
                  }catch (Exception e){

                  }

              }

          }

          @Override
          public void onError(Throwable e) {
              super.onError(e);
          }
      });
    }
}
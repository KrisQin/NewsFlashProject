package com.blockadm.adm.persenter;

import android.util.Log;

import com.blockadm.adm.contact.NewsFlashContract;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.blockadm.common.bean.ZanBean;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import io.reactivex.disposables.Disposable;


/**
*/
public class NewsFlashPresenter extends NewsFlashContract.Presenter{




    @Override
    public void getNewsFlashList(String jsonData) {
        mModel.getNewsFlashList(jsonData,new MyObserver<NewsFlashBeanDto>() {
            @Override
            public void onSuccess(BaseResponse<NewsFlashBeanDto> newsFlashBeanDto) {
                if (mView!=null){
                    mView.showNewsFlashList(newsFlashBeanDto.getData());
                }

            }


        });
    }

    @Override
    public void operateNewsFlashCount(int id,int  operateType ) {
        mModel.operateNewsFlashCount(id,operateType,new MyObserver<ZanBean>() {

            @Override
            public void onSuccess(BaseResponse<ZanBean> zanBean) {
                mView.show0perateNewsFlashCount(zanBean.getData());
            }

        });
    }


}
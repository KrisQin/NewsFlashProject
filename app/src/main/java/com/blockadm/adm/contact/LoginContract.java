package com.blockadm.adm.contact;

import com.blockadm.common.base.BaseModel;
import com.blockadm.common.base.BasePersenter;
import com.blockadm.common.base.IbaseView;
import com.blockadm.common.bean.params.LoginBean;
import com.blockadm.common.http.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observer;

/**
 * Created by hp on 2018/12/12.
 */

public class LoginContract {

    public interface View extends IbaseView {
        void showLoginSucceed(BaseResponse<LoginBean> loginBean);
        void showYanzhengma(BaseResponse<String> baseResponse);
    }

    public  interface Model extends BaseModel {
        void login(int type , String phone ,String code , String recommend ,  Observer observer);
        void sendSms(String phoneNum , Observer observer);
        void addFeedback(String phoneNum , Observer observer);
    }

      public static abstract class Presenter extends BasePersenter<View, Model> {

        public abstract void login(int type ,String username, String password,String recommend);
          public abstract  void sendSms(String phoneNum );

          public abstract  void addFeedback(String phoneNum );
    }
}

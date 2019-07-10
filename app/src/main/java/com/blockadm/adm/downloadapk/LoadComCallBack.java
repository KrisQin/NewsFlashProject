package com.blockadm.adm.downloadapk;

/**
 *  加载的基类回调
 * Created by Administrator on 2018/5/21.
 */
public interface LoadComCallBack {

    /**
     * 加载进度条
     */
    void showDefaultLoadingDialog();

    /**
     * 进度条消失
     */
    void dismissLoadingDialog();

    /**
     * 提示
     * @param message
     */
    void showToast(String message);

}

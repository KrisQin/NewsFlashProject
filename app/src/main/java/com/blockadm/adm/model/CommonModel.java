package com.blockadm.adm.model;

import android.app.Activity;

import com.blockadm.adm.activity.AuthenticationComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.CmlRequestBody;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.http.RetrofitManager;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.SharedpfTools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by hp on 2019/1/23.
 */

public class CommonModel {

    public static void addUserFollow(int id, Observer observer) {
        RetrofitManager.
                getService().
                addUserFollow(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void deleteUserFollow(int id, Observer observer) {
        RetrofitManager.
                getService().
                deleteUserFollow(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void confirmOrCancel(int objectId, int objectTypeId, int dataId,
                                       int operateType, Observer observer) {
        RetrofitManager.
                getService().
                confirmOrCancel(objectId, objectTypeId, dataId, operateType).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void getUserData(Observer observer) {
        RetrofitManager.
                getService().
                getUserData().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void getUserData(final Activity activity, final GetUserCallBack callBack) {
        RetrofitManager.
                getService().
                getUserData().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MyObserver<UserInfoDto>() {
                    @Override
                    public void onSuccess(BaseResponse<UserInfoDto> userInfoDtoBaseResponse) {


                        if (userInfoDtoBaseResponse.getCode() == 0) {
                            UserInfoDto userInfoDto = userInfoDtoBaseResponse.getData();

                            if (userInfoDto != null) {

                                if (callBack != null)
                                    callBack.backUserInfo(userInfoDto);

                                if (activity != null)
                                    ACache.get(activity).put("userInfoDto", userInfoDto);
                                String mScanUrl = userInfoDto.getRecommendUrl();
                                SharedpfTools.getInstance().put(ConstantUtils.RecommendUrl,
                                        mScanUrl);
                                SharedpfTools.getInstance().put(ConstantUtils.MemberId,
                                        userInfoDto.getMemberId());

                            } else {
                                if (callBack != null)
                                    callBack.error(userInfoDtoBaseResponse.getCode(),
                                            userInfoDtoBaseResponse.getMsg());

                            }

                        }
                        else {
                            if (callBack != null)
                                callBack.error(userInfoDtoBaseResponse.getCode(),
                                        userInfoDtoBaseResponse.getMsg());

                        }


                    }
                });

    }

    public static void getMemberInfoByUserId(int userId, Observer observer) {
        RetrofitManager.
                getService().
                getMemberInfoByUserId(userId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void pageFollow(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                pageFollow(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void pageConcern(String jsonData, MyObserver myObserver) {

        RetrofitManager.
                getService().
                pageConcern(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(myObserver);
    }

    public static void searchMember(String jsonData, MyObserver myObserver) {

        RetrofitManager.
                getService().
                searchMember(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(myObserver);
    }

    public static void pageSearchNewsLessonsAndNlsc(String jsonData, MyObserver myObserver) {

        RetrofitManager.
                getService().
                pageSearchNewsLessonsAndNlsc(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(myObserver);
    }

    public static void newsArticlePage(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                getNewsArticlePage(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void confirmOrCancelNewsLessonsCollectionOrShare(int objectId, int objectTypeId
            , int dataId, int operateType, Observer observer) {
        RetrofitManager.
                getService().
                confirmOrCancelNewsLessonsCollectionOrShare(objectId, objectTypeId, dataId,
                        operateType).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void pageNewsLessonsComment(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                pageNewsLessonsComment(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void addNewsLessonsComment(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                addNewsLessonsComment(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

/*    public static void buyLessonsOrSpecialColumn(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                buyLessonsOrSpecialColumn(@Query("objectId")int objectId,@Query("typeId")int
                typeId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }*/

    public static void updateUserMember(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                updateUserMember(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void userSecretList(Observer observer) {
        RetrofitManager.
                getService().
                userSecretList().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void userSecretMark(int id, Observer observer) {
        RetrofitManager.
                getService().
                userSecretMark(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void sendSetSms(Observer observer) {
        RetrofitManager.
                getService().
                sendSetSms().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void checkSetSms(String code, Observer observer) {
        RetrofitManager.
                getService().
                checkSetSms(code).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void setPassword(int type, String code, String passWord, Observer observer) {
        RetrofitManager.
                getService().
                setPassword(type, code, passWord).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void bindPhone(String phone, String code, Observer observer) {
        RetrofitManager.
                getService().
                bindPhone(phone, code).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void sendSms(String phone, Observer observer) {
        RetrofitManager.
                getService().
                sendSms(phone).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void listFeedbackType(Observer observer) {
        RetrofitManager.
                getService().
                listFeedbackType().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void listFeedbackType(String jsonString, Observer observer) {
        RetrofitManager.
                getService().
                listFeedbackType(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void newsActivityPage(String jsonString, Observer observer) {
        RetrofitManager.
                getService().
                newsActivityPage(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void findAllNotParentIdSysLableList(Observer observer) {
        RetrofitManager.
                getService().
                findAllNotParentIdSysLableList().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void userSubscribeLablelist(Observer observer) {
        RetrofitManager.
                getService().
                userSubscribeLablelist().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void userSubscribeLableDelete(Observer observer, int sysLableId) {
        RetrofitManager.
                getService().
                userSubscribeLableDelete(sysLableId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void userSubscribeLableAdd(Observer observer, int sysLableId) {
        RetrofitManager.
                getService().
                userSubscribeLableAdd(sysLableId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void findUserSubscribeNewsArticlePage(Observer observer, String json) {
        RetrofitManager.
                getService().
                findUserSubscribeNewsArticlePage(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void listRechargeType(Observer observer) {
        RetrofitManager.
                getService().
                listRechargeType().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void userMsgPage(Observer observer, String json) {
        RetrofitManager.
                getService().
                userMsgPage(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void operateNewsActivityCount(Observer observer, int id, int operateType,
                                                int choose) {
        RetrofitManager.
                getService().
                operateNewsActivityCount(id, operateType, choose).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void myNewsCollectionPage(Observer observer, String json) {
        RetrofitManager.
                getService().
                myNewsCollectionPage(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void markUserMsg(Observer observer, int id, int state) {
        RetrofitManager.
                getService().
                markUserMsg(id, state).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void userAccountPage(Observer observer, String json) {
        RetrofitManager.
                getService().
                userAccountPage(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void addFeedback(Observer observer, String json) {
        RetrofitManager.
                getService().
                addFeedback(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void deleteUserAccount(Observer observer, int id) {
        RetrofitManager.
                getService().
                deleteUserAccount(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void addNewsArticleComment(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                addNewsArticleComment(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void myEarnedUserAccount(Observer observer) {
        RetrofitManager.
                getService().
                myEarnedUserAccount().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void findNewsLessonsPlayDetail(Observer observer, int id, int width,
                                                 String dataType) {
        RetrofitManager.
                getService().
                findNewsLessonsPlayDetail(id, width, dataType).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * post请求上传文件....包括图片....流的形式传任意文件...
     * 参数1 url
     * file表示上传的文件
     * fileName....文件的名字,,例如aaa.jpg
     * params ....传递除了file文件 其他的参数放到map集合
     */
    private static final int DEFAULT_TIME_OUT = 25;//超时时间5s
    private static final int DEFAULT_READ_TIME_OUT = 15;//读取时间
    private static final int DEFAULT_WRITE_TIME_OUT = 15;//读取时间

    /**
     * 上传图片文件
     *
     * @param file
     * @param callback
     * @param token
     * @throws IOException
     */
    public static void uploadImage(File file, Callback callback, String token) throws IOException {

        //创建RequestBody封装参数
        //创建MultipartBody,给RequestBody进行设置
        //构建body
        RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", token)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(
                        "image/*"), file))
                .build();

        //        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        //        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        //        builder.writeTimeout(DEFAULT_WRITE_TIME_OUT,TimeUnit.SECONDS);
        final Request request =
                new Request.Builder().url("http://upload.qiniup.com/").post(multipartBody).build();
        final Call call = getOkhttpBuilder().build().newBuilder().writeTimeout(50,
                TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(callback);
    }


    private static OkHttpClient.Builder builder = null;

    private static OkHttpClient.Builder getOkhttpBuilder() {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
            builder.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS);
        }
        return builder;
    }

    /**
     * 上传MP3文件
     *
     * @param file
     * @param callback
     * @throws IOException
     */
    public static void uploadVoice(final File file, String token, final Callback callback) throws IOException {

        RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", token)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(
                        "audio/mp3"), file))
                .build();

        //                OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //                builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        //                builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        //                builder.writeTimeout(DEFAULT_WRITE_TIME_OUT,TimeUnit.SECONDS);
        final Request request = new Request.Builder()
                .url("http://upload.qiniup.com/").post(multipartBody).build();
        final Call call = getOkhttpBuilder().build().newBuilder().writeTimeout(50,
                TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(callback);

    }

    /*

    .removeHeader("User-Agent")
    .addHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
     */

    /**
     * 0 头像(不安全) 1 证件(安全) 2 微信提现(不安全) 3 编辑专栏(图片) 4 编辑专栏(视频) 5 讲师音频
     *
     * @param type
     * @param observer
     */
    public static void getImageUploadToken(int type, Observer observer) {
        RetrofitManager.
                getService().
                getImageUploadToken(type).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void addUserCredentials(Observer observer, String json) {
        RetrofitManager.
                getService().
                addUserCredentials(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void addOrUpdateWithdrawAccount(Observer observer, String json) {
        RetrofitManager.
                getService().
                addOrUpdateWithdrawAccount(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void getNewsActivity(Observer observer, int id, int winth) {
        RetrofitManager.
                getService().
                getNewsActivity(id, winth).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void getVipPrivilege(Observer observer) {
        RetrofitManager.
                getService().
                getVipPrivilege().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void getNewsTotal(Observer observer) {
        RetrofitManager.
                getService().
                getNewsTotal().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void findMyStudyPage(String json, Observer observer) {
        RetrofitManager.
                getService().
                findMyStudyPage(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void uploadImageProgress(CmlRequestBody cmlRequestBody, Callback callback) throws IOException {
        //创建RequestBody封装参数
        //创建MultipartBody,给RequestBody进行设置
        //构建body
  /*      RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", token)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse
                ("image*//*"), file))
                .build();*/

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS);
        final Request request =
                new Request.Builder().url("http://upload.qiniup.com/").post(cmlRequestBody).build();
        final Call call =
                builder.build().newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(callback);


    }


    public static void updateNlscStatus(int id, int status, Observer observer) {
        RetrofitManager.
                getService().
                updateNlscStatus(id, status).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void operateNewsLessonsCommentCount(int commentType, int id, int operateType,
                                                      Observer observer) {
        RetrofitManager.
                getService().
                operateNewsLessonsCommentCount(commentType, id, operateType).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void replayZanCount(int id, int newsArticleId, int choose, Observer observer) {
        RetrofitManager.
                getService().
                replayZanCount(id, newsArticleId, choose).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void findSysBannerList(int typeId, int location, Observer observer) {
        RetrofitManager.
                getService().
                findSysBannerList(typeId, location).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void recommendEarnedPoint(Observer observer) {
        RetrofitManager.
                getService().
                recommendEarnedPoint().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void pageMyRecommendDetailWeb(Observer observer, String json) {
        RetrofitManager.
                getService().
                pageMyRecommendDetailWeb(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void getMyLevelApp(Observer observer) {
        RetrofitManager.
                getService().
                getMyLevelApp().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void receiveLevelAward(Observer observer, int levelId) {
        RetrofitManager.
                getService().
                receiveLevelAward(levelId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void operateArticleCount(int id, int i, int choose, Observer observer) {
        RetrofitManager.
                getService().
                operateArticleCount(id, i, choose).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void addNewsLessonsSpecialColumn(String json, Observer observer) {
        RetrofitManager.
                getService().
                addNewsLessonsSpecialColumn(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * @param channel  购买渠道【0：支付宝、1：微信、2：网银、3：A点、4：A钻】
     * @param typeId   购买类型【0：精品课程、1：独家专栏、2：A点充值、3：购买VIP、4：购买直播课程，5：支付直播开课费用】
     * @param objectId id【精品课程、独家专栏、充值卡、VIP卡、直播课程】
     * @param observer
     */
    public static void purchase(int channel, int typeId, int objectId, Observer observer) {
        RetrofitManager.
                getService().
                purchase(channel, typeId, objectId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void checkPayPassword(String password, Observer observer) {
        RetrofitManager.
                getService().
                checkPayPassword(password).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void loginByThirdParty(String json, Observer observer) {
        RetrofitManager.
                getService().
                loginByThirdParty(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void doWithdraw(String name, int point, int channel, Observer observer) {
        RetrofitManager.
                getService().
                doWithdraw(name, point, channel).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void findUserAccountByOutTradeNo(String outTradeNo, Observer observer) {
        RetrofitManager.
                getService().
                findUserAccountByOutTradeNo(outTradeNo).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void myPagenewsArticle(String json, Observer observer) {
        RetrofitManager.
                getService().
                myPagenewsArticle(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void getAllRecommendCode(Observer observer) {
        RetrofitManager.
                getService().
                getAllRecommendCode().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void operateNewsArticleCommentCount(int id, int newsArticleId, int choose,
                                                      Observer observer) {
        RetrofitManager.
                getService().
                operateNewsArticleCommentCount(id, newsArticleId, choose).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void findSysTypeList(Observer observer) {
        RetrofitManager.
                getService().
                findSysTypeList(0, 0).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void findMyBuyStudyPage(String json, Observer observer) {
        RetrofitManager.
                getService().
                findMyBuyStudyPage(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void getAppUpdateData(String versionCode, int type, Observer observer) {
        RetrofitManager.
                getService().
                getAppUpdateData(versionCode, type).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void addLog(String log, int type, Observer observer) {
        RetrofitManager.
                getService().
                addLog(log, type).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void findAllList(Observer observer) {
        RetrofitManager.
                getService().
                findAllList().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void addContentReply(String dataJson, Observer observer) {
        RetrofitManager.
                getService().
                addContentReply(dataJson).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void newsLessonsCommentReplyPage(String dataJson, Observer observer) {
        RetrofitManager.
                getService().
                newsLessonsCommentReplyPage(dataJson).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void replayZanCountLessonsComment(int id, int choose, Observer observer) {
        RetrofitManager.
                getService().
                replayZanCountLessonsComment(id, choose).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void addEncourage(int typeId, int objectId, Observer observer) {
        RetrofitManager.
                getService().
                addEncourage(typeId, objectId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    /**
     * @param typeId   类型ID(0：行业资讯【文章、新闻】类型、1：课程类型、2：活动类型、3：直播课类型)
     * @param observer
     */
    public static void queryStudyTypeList(String typeId, Observer observer) {
        RetrofitManager.
                getService().
                //parentId: 父级ID(0代表查询顶级类型信息)
                        queryStudyTypeList("0", typeId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 分页查询直播课程信息
     *
     * @param dataJson
     * @param observer
     */
    public static void queryNewsLiveLessons(String dataJson, Observer observer) {
        RetrofitManager.
                getService().
                queryVisitorNewsLiveLessons(dataJson).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    /**
     * 查询直播課程详情
     *
     * @param id
     * @param observer
     */
    public static void queryNewsLiveLessonsDetailById(String id, Observer observer) {
        RetrofitManager.
                getService().
                queryNewsLiveLessonsDetailById(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 查询直播課程详情
     *
     * @param id
     * @param observer
     */
    public static void checkAccessPwd(String id, String accessPwd, Observer observer) {
        RetrofitManager.
                getService().
                //校验类型【0、精品课程、1：直播课程】
                        checkAccessPwd("1", id, accessPwd).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void pageNewsLessonsSpecialColumn(String jsonString, Observer observer) {

        RetrofitManager.
                getService().
                pageNewsLessonsSpecialColumn(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void getNewsFlashList(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                getNewsFlashList(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static void operateNewsFlashCount(int id, int operateType, Observer observer) {
        RetrofitManager.
                getService().
                operateNewsFlashCount(id, operateType).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 打赏
     *
     * @param jsonString
     * @param observer
     */
    public static void reward(String jsonString, Observer observer) {

        RetrofitManager.
                getService().
                reward(jsonString).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 查询打赏金额列表
     *
     * @param observer
     */
    public static void queryRewardList(Observer observer) {
        RetrofitManager.
                getService().
                queryRewardList().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 提问
     *
     * @param observer
     */
    public static void question(String json, Observer observer) {
        RetrofitManager.
                getService().
                question(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 添加公告信息
     *
     * @param observer
     */
    public static void addNewsLiveLessonsNotice(String json, Observer observer) {
        RetrofitManager.
                getService().
                addNewsLiveLessonsNotice(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 分页查询直播课程信息
     *
     * @param observer
     */
    public static void newsLiveLessons(String json, Observer observer) {
        RetrofitManager.
                getService().
                newsLiveLessons(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 添加直播课程
     *
     * @param observer
     */
    public static void addLiveLessons(String json, Observer observer) {
        RetrofitManager.
                getService().
                addLiveLessons(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 直播界面历史消息
     *
     * @param observer
     */
    public static void queryNewsLiveLessonsComment(String json, Observer observer) {
        RetrofitManager.
                getService().
                queryNewsLiveLessonsComment(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    /**
     * 查询当前直播课程中的所有群组成员、黑名单、禁言详情
     *
     * @param newsLiveLessonsId 直播课程id
     * @param type              0：群组 、1： 禁言、2：黑名单
     * @param observer
     */
    public static void findListStatus(String newsLiveLessonsId, String type, Observer observer) {
        RetrofitManager.
                getService().
                findListStatus(newsLiveLessonsId, type).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 查询当前会员对应直播课程中的群组成员、黑名单、禁言详情
     *
     * @param newsLiveLessonsId 直播课程id
     * @param type              0：群组 、1： 禁言、2：黑名单
     * @param observer
     */
    public static void findDetail(String newsLiveLessonsId, String memberId, String type,
                                  Observer observer) {
        RetrofitManager.
                getService().
                findDetail(newsLiveLessonsId, memberId, type).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
    /**
     * 查询当前会员对应直播课程中的群组成员、黑名单、禁言详情
     * @param newsLiveLessonsId 直播课程id
     * @param type  0：群组 、1： 禁言、2：黑名单
     * @param observer
     */

    /**
     * 对当前直播课程中的移除群组、黑名单、禁言操作
     *
     * @param jsonData
     * @param observer
     */
    public static void addOrRemove(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                addOrRemove(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 查询直播課程实时信息
     *
     * @param id
     * @param observer
     */
    public static void getNewsLiveLessonsRealTimeDetailById(int id, Observer observer) {
        RetrofitManager.
                getService().
                getNewsLiveLessonsRealTimeDetailById(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 添加直播聊天信息【课程讲师、管理员】
     *
     * @param jsonData
     * @param observer
     */
    public static void addNewsLiveLessonsComment(String jsonData, Observer observer) {
        RetrofitManager.
                getService().
                addNewsLiveLessonsComment(jsonData).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 查詢当前直播课程下的所有管理员信息
     *
     * @param newsLiveLessonsId
     * @param observer
     */
    public static void findAllManagerListByNewsLiveLessonsId(String newsLiveLessonsId,
                                                             Observer observer) {
        RetrofitManager.
                getService().
                findAllManagerListByNewsLiveLessonsId(newsLiveLessonsId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 添加和移除管理员
     *
     * @param newsLiveLessonsId 直播课程id
     * @param memberId          会员ID
     * @param choose            【0:添加、1：移除】
     * @return
     */
    public static void addOrRemoveManager(int newsLiveLessonsId, int memberId, int choose,
                                          Observer observer) {
        RetrofitManager.
                getService().
                addOrRemoveManager(newsLiveLessonsId, memberId, choose).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public static void getAddLiveManagerUrl(int liveLessonsId, Observer observer) {
        RetrofitManager.
                getService().
                getAddLiveManagerUrl(liveLessonsId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 重新设置开课时间
     *
     * @param id          直播课程id
     * @param lessonsTime 开课时间【時間格式：如 2018-08-08 08:08:08】
     * @param observer
     */
    public static void updateOpenLessonsTime(int id, String lessonsTime, Observer observer) {
        RetrofitManager.
                getService().
                updateOpenLessonsTime(id, lessonsTime).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 会员修改直播課程状态信息
     *
     * @param id       直播课程id
     * @param status   状态(0 上架、1：下架、2：删除、3：开始直播，4：结束直播)
     * @param observer
     */
    public static void operateNewsLiveLessonStatus(int id, int status, Observer observer) {
        RetrofitManager.
                getService().
                operateNewsLiveLessonStatus(id, status).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    /**
     * 发红包
     *
     * @param observer
     */
    public static void sendRedpacket(String json, Observer observer) {
        RetrofitManager.
                getService().
                sendRedpacket(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    /**
     * 直播界面分享和进入直播需要的操作
     * @param typeId 105  分享直播
     *               202  进入直播
     * @return
     */
    public static void addEncourage(int typeId, Observer observer) {
        RetrofitManager.
                getService().
                addEncourage(typeId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 分页查询已购买社群信息【购买历史】 注释：只需传入分页参数
     *
     * @param observer
     */
    public static void findMyBuySQPage(String json, Observer observer) {
        RetrofitManager.
                getService().
                findMyBuySQPage(json).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 获取第三方服务
     *
     * @param observer
     */
    public static void findAllThirdWebList(Observer observer) {
        RetrofitManager.
                getService().
                findAllThirdWebList().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 获取喜马拉雅网站访问URL
     *
     * @param observer
     */
    public static void findXiMaLaYaWebUrl(Observer observer) {
        RetrofitManager.
                getService().
                findXiMaLaYaWebUrl().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 绑定微信
     *
     * @param observer
     */
    public static void bindWX(String openId,Observer observer) {
        RetrofitManager.
                getService().
                bindWX(openId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    /**
     * 查询红包列表
     *
     * @param observer
     */
    public static void queryHBlist(Integer id, Observer observer) {
        RetrofitManager.
                getService().
                grabRedpacket(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    /**
     * 获取红包状态
     *
     * @param observer
     */
    public static void getRedpacketState(Integer id, Observer observer) {
        RetrofitManager.
                getService().
                getRedpacketState(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * A点和A钻的兑换
     *
     * @param typeId 兑换类型(0、奖金A点兑换为A钻 1、奖金A点兑换为现金A点)
     * @param amount 额度（必须为整数）
     * @return
     */
    public static void balanceExchange(int typeId, int amount, Observer observer) {
        RetrofitManager.
                getService().
                balanceExchange(typeId, amount).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * A钻提现
     * @return
     */
    public static void withdrawDiamond(double money,String accountNumber, Observer observer) {
        RetrofitManager.
                getService().
                withdrawDiamond(money, accountNumber).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 查询当前直播课程所有历史交流信息
     *
     * @param newsLiveLessonsId 直播课程id
     * @return
     */
    public static void findAllExchangeCommentList(int newsLiveLessonsId, Observer observer) {
        RetrofitManager.
                getService().
                findAllExchangeCommentList(newsLiveLessonsId).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }


}







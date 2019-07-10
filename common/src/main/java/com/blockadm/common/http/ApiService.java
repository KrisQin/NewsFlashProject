package com.blockadm.common.http;

import com.blockadm.common.bean.AcountListDto;
import com.blockadm.common.bean.AcountListDtoRecordBean;
import com.blockadm.common.bean.ActivitysDetailDto;
import com.blockadm.common.bean.AllListDto;
import com.blockadm.common.bean.AllRecommendCodeDto;
import com.blockadm.common.bean.AwardDto;
import com.blockadm.common.bean.BannerListDto;
import com.blockadm.common.bean.BuyHistoryDto;
import com.blockadm.common.bean.CollectListDto;
import com.blockadm.common.bean.CommentBean;
import com.blockadm.common.bean.CommentBeanDTO;
import com.blockadm.common.bean.CommentDetailDto;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.FansListDTO;
import com.blockadm.common.bean.FeedBackTypeDTO;
import com.blockadm.common.bean.FindSysTypeListDto;
import com.blockadm.common.bean.GetMyLevelAppDto;
import com.blockadm.common.bean.InComeDto;
import com.blockadm.common.bean.LessonsAndNlscDTO;
import com.blockadm.common.bean.LessonsSpecialColumnDto;
import com.blockadm.common.bean.LoginByThirdPartyDTO;
import com.blockadm.common.bean.MyStudyPageDTO;
import com.blockadm.common.bean.NewsArticleCommentDTO;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.blockadm.common.bean.NewsLessonsDTO;
import com.blockadm.common.bean.NewsLessonsDetailDTO;
import com.blockadm.common.bean.NewsLessonsSpecialColumnDto;
import com.blockadm.common.bean.PageMyRecommendDetailWebDto;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;
import com.blockadm.common.bean.PalyDetailDto;
import com.blockadm.common.bean.PayDTO;
import com.blockadm.common.bean.PersonalDTO;
import com.blockadm.common.bean.PrivateListBean;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.bean.RechargeTypeDto;
import com.blockadm.common.bean.RecommendEarnedPointDto;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.bean.RewardListInfo;
import com.blockadm.common.bean.StudyTypeInfo;
import com.blockadm.common.bean.TagBeanDto;
import com.blockadm.common.bean.ThirdWebListInfo;
import com.blockadm.common.bean.TotalDto;
import com.blockadm.common.bean.UpdataVersionDto;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.UserListDTO;
import com.blockadm.common.bean.UserMsgDto;
import com.blockadm.common.bean.VipDto;
import com.blockadm.common.bean.WalksSchduleDto;
import com.blockadm.common.bean.ZanBean;
import com.blockadm.common.bean.live.responseBean.BlackLimitUserDetailInfo;
import com.blockadm.common.bean.live.responseBean.HBStatusInfo;
import com.blockadm.common.bean.live.responseBean.HistoryLiveLessonsInfo;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.bean.live.responseBean.LiveHBInfo;
import com.blockadm.common.bean.live.responseBean.LiveListInfo;
import com.blockadm.common.bean.live.responseBean.HistoryLiveLessonsListInfo;
import com.blockadm.common.bean.live.responseBean.LiveManageInfo;
import com.blockadm.common.bean.live.responseBean.LiveOpenInfo;
import com.blockadm.common.bean.live.responseBean.RealTimeDetailInfo;
import com.blockadm.common.bean.params.LoginBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hp on 2018/12/11.
 */
public interface ApiService {

    // public static final String BASR_URL_DEBUG = "https://app.blockadm.pro";https://192.168.101
    // .28:8802

        public static final String BASR_URL_RELEASE = "https://app.blockadm.com"; //线上环境
//    public static final String BASR_URL_RELEASE = "http://app.blockadm.pro"; //测试环境

    @GET("/user/app/visitor/loginByPhoneCode")
    Observable<BaseResponse<LoginBean>> login(@Query("type") int type,
                                              @Query("phone") String phone,
                                              @Query("code") String code,
                                              @Query("recommend") String recommend);

    @GET("/user/app/visitor/sendSms")
    Observable<BaseResponse<String>> sendSms(@Query("phone") String phone);

    @POST("/user/app/visitor/addFeedback")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<String>> addFeedback(@Body String jsonSting);

    @POST("/news/app/visitor/newsFlash/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<NewsFlashBeanDto>> getNewsFlashList(@Body String jsonSting);

    @POST("/news/app/visitor/newsArticle/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<NewsArticleListDTO>> getNewsArticlePage(@Body String jsonSting);

    @GET("/news//app/newsFlashCount/operateNewsFlashCount")
    Observable<BaseResponse<ZanBean>> operateNewsFlashCount(@Query("id") int id, @Query(
            "operateType") int operateType);

    @GET("/news//app/visitor/newsArticle/get")
    Observable<BaseResponse<NewsArticleCommentDTO>> getNewsArticlenewsArticle(@Query(
            "imageMaxWidth") int imageMaxWidth, @Query("id") int id);

    @POST("/news/app/visitor/newsArticleComment/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<PageNewsArticleCommentDTO>> getNewsArticleCommentPage(@Body String jsonSting);

    @POST("/news/app/newsArticleComment/addReply")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<CommentReplyListBean>> addReply(@Body String jsonSting);

    @GET("/news/app/newsArticle/operateArticleCount")
    Observable<BaseResponse<String>> operateArticleCount(@Query("id") int id, @Query("operateType"
    ) int operateType, @Query("choose") int choose);

    @POST("/news/app/newsArticleComment/add")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<PageNewsArticleCommentDTO.RecordsBean>> addNewsArticleComment(@Body String jsonSting);

    @POST("/news/app/newsArticleReport/addReport")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<String>> addReport(@Body String jsonSting);


    /*/app/visitor/newsArticleCommentReply/page
分页查询行业资讯（文章、新闻）回复评论信息*/

    @POST("/news/app/visitor/newsArticleCommentReply/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<CommentDetailDto>> pageNewsArticleCommentReply(@Body String jsonSting);


    /*
     * /news/app/visitor/newsLessons/page
     * */

    @GET("/news/app/visitor/findAllNewsLessonsListByNlscId")
    Observable<BaseResponse<ArrayList<RecordsBean>>> pageNewsLessons(@Query("nlscId") String nlscId, @Query("dataType") String dataType);

    @POST("/news/app/visitor/newsLessons/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<NewsLessonsDTO>> pageNewsLessonsList(@Body String jsonSting);


    @POST("/news/app/visitor/newsLessonsSpecialColumn/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<NewsLessonsSpecialColumnDto>> pageNewsLessonsSpecialColumn(@Body String jsonSting);


    @GET("/news/app/visitor/findNewsLessonsSpecialColumnById")
    Observable<BaseResponse<LessonsSpecialColumnDto>> findNewsLessonsSpecialColumnById(@Query("id"
    ) int id, @Query("imageMaxWidth") int imageMaxWidth);

    @GET("/news/app/visitor/findNewsLessonsById")
    Observable<BaseResponse<NewsLessonsDetailDTO>> findNewsLessonsById(@Query("id") int id,
                                                                       @Query("imageMaxWidth") int imageMaxWidth);

    /*
    * /app/userFollow/add
粉丝列表(添加关注)
    * */

    @GET("/user/app/userFollow/add")
    Observable<BaseResponse<String>> addUserFollow(@Query("userId") int id);

    @GET("/user/app/userFollow/delete")
    Observable<BaseResponse<String>> deleteUserFollow(@Query("userId") int id);

    @GET("news/app/newsLessonsCollectionOrShare/confirmOrCancel")
    Observable<BaseResponse<String>> confirmOrCancel(@Query("objectId") int objectId, @Query(
            "objectTypeId") int objectTypeId, @Query("dataId") int dataId,
                                                     @Query("operateType") int operateType);

    @GET("/user/app/userMember/getUserData")
    Observable<BaseResponse<UserInfoDto>> getUserData();

    @GET("/user/app/visitor/getMemberInfoByUserId")
    Observable<BaseResponse<PersonalDTO>> getMemberInfoByUserId(@Query("userId") int id);

    @POST("/user/app/visitor/pageFollow")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<FansListDTO>> pageFollow(@Body String jsonSting);

    @POST("/user/app/visitor/pageConcern")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<FansListDTO>> pageConcern(@Body String jsonSting);


    @POST("/user/app/visitor/searchMember")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<UserListDTO>> searchMember(@Body String jsonSting);


    @POST("/news/app/visitor/searchNewsLessonsAndNlsc/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<LessonsAndNlscDTO>> pageSearchNewsLessonsAndNlsc(@Body String jsonSting);

    @GET("/news/app/newsLessonsCollectionOrShare/confirmOrCancel")
    Observable<BaseResponse<String>> confirmOrCancelNewsLessonsCollectionOrShare(@Query("objectId"
    ) int objectId, @Query("objectTypeId") int objectTypeId, @Query("dataId") int dataId, @Query(
            "operateType") int operateType);

    // /news/app/visitor/newsLessonsComment/page

    @POST("/news/app/visitor/newsLessonsComment/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<CommentBeanDTO>> pageNewsLessonsComment(@Body String jsonSting);

    @POST("/news/app/newsLessonsComment/add")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<CommentBeanDTO.RecordsBean>> addNewsLessonsComment(@Body String jsonSting);

    @GET("/news/app/buy/lessonsOrSpecialColumn")
    Observable<BaseResponse<CommentBean>> buyLessonsOrSpecialColumn(@Query("objectId") int objectId, @Query("typeId") int typeId);

    @POST("/user/app/userMember/update")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<String>> updateUserMember(@Body String jsonSting);

    @GET("/user/app/userSecret/list")
    Observable<BaseResponse<ArrayList<PrivateListBean>>> userSecretList();///user/app/userSecret
    // /mark

    @GET("/user/app/userSecret/mark")
    Observable<BaseResponse<String>> userSecretMark(@Query("id") int id);///user/app/userMember
    // /sendSetSms

    @GET("/user/app/userMember/sendSetSms")
    Observable<BaseResponse<String>> sendSetSms();//user/app/userMember/checkSetSms

    @GET("/user/app/userMember/checkSetSms")
    Observable<BaseResponse<String>> checkSetSms(@Query("code") String code);///user/app
    // /userMember/setPassword

    @GET("/user/app/userMember/setPassword")
    Observable<BaseResponse<String>> setPassword(@Query("type") int type,
                                                 @Query("code") String code,
                                                 @Query("password") String password);

    @GET("/user/app/userMember/bindPhone")
    Observable<BaseResponse<String>> bindPhone(@Query("phone") String phone,
                                               @Query("code") String code);

    @GET("/user/app/visitor/listFeedbackType")
    Observable<BaseResponse<ArrayList<FeedBackTypeDTO>>> listFeedbackType();

    @POST("/user/app/visitor/listFeedbackType")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<String>> listFeedbackType(@Body String jsonSting);

    @POST("/news/app/visitor/newsActivity/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<WalksSchduleDto>> newsActivityPage(@Body String jsonSting);

    @GET("/news/app/visitor/findAllNotParentIdSysLableList")
    Observable<BaseResponse<ArrayList<TagBeanDto>>> findAllNotParentIdSysLableList();


    @GET("/user/app/userSubscribeLable/list")
    Observable<BaseResponse<ArrayList<TagBeanDto>>> userSubscribeLablelist();

    @GET("/user/app/userSubscribeLable/add")
    Observable<BaseResponse<String>> userSubscribeLableAdd(@Query("sysLableId") int sysLableId);

    @GET("/user/app/userSubscribeLable/delete")
    Observable<BaseResponse> userSubscribeLableDelete(@Query("sysLableId") int sysLableId);

    @POST("/news/app/newsArticle/findUserSubscribeNewsArticlePage")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<NewsArticleListDTO>> findUserSubscribeNewsArticlePage(@Body String jsonSting);

    @GET("/user/app/userBalance/listRechargeType")
    Observable<BaseResponse<RechargeTypeDto>> listRechargeType();

    @POST("/user/app/userMsg/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<UserMsgDto>> userMsgPage(@Body String jsonSting);

    @GET("/news/app/newsActivity/operateNewsActivityCount")
    Observable<BaseResponse<String>> operateNewsActivityCount(@Query("id") int id, @Query(
            "operateType") int operateType, @Query("choose") int choose);

    @POST("/news/app/myNewsCollectionPage")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<CollectListDto>> myNewsCollectionPage(@Body String jsonSting);

    @GET("/user/app/userMsg/mark")
    Observable<BaseResponse<String>> markUserMsg(@Query("id") int id, @Query("state") int state);

    @POST("/user/app/userAccount/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<AcountListDto>> userAccountPage(@Body String jsonSting);

    @GET("/user/app/userAccount/delete")
    Observable<BaseResponse<String>> deleteUserAccount(@Query("id") int id);

    @GET("/user/app/userAccount/myEarned")
    Observable<BaseResponse<InComeDto>> myEarnedUserAccount();

    @GET("/news/app/newsLessons/findNewsLessonsPlayDetail")
    Observable<BaseResponse<PalyDetailDto>> findNewsLessonsPlayDetail(@Query("id") int id,
                                                                      @Query("imageMaxWidth") int imageMaxWidth, @Query("dataType") String dataType);

    @GET("/user/app/userCredentials/getUploadToken")
    Observable<BaseResponse<QiniuTokenParams>> getImageUploadToken(@Query("safeType") int safeType);


    @POST("/user/app/userCredentials/add")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<String>> addUserCredentials(@Body String jsonSting);


    @POST("/user/app/userBalance/addOrUpdateWithdrawAccount")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<String>> addOrUpdateWithdrawAccount(@Body String jsonSting);

    @GET("/news/app/visitor/newsActivity/get")
    Observable<BaseResponse<ActivitysDetailDto>> getNewsActivity(@Query("id") int id, @Query(
            "imageMaxWidth") int imageMaxWidth);

    @GET("/user/app/userBalance/getVipPrivilege")
    Observable<BaseResponse<ArrayList<VipDto>>> getVipPrivilege();

    @GET("/news/app/allNewsTotal")
    Observable<BaseResponse<TotalDto>> getNewsTotal();


    @POST("/news/app/newsLessonsSpecialColumn/findMyStudyPage")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<MyStudyPageDTO>> findMyStudyPage(@Body String jsonSting);

    @GET("/news/app/newsLessonsSpecialColumn/updateNlscStatus")
    Observable<BaseResponse<String>> updateNlscStatus(@Query("id") int id,
                                                      @Query("status") int status);

    @GET("/news/app/newsLessonsComment/operateNewsLessonsCommentCount")
    Observable<BaseResponse<String>> operateNewsLessonsCommentCount(@Query("commentType") int commentType, @Query("newsLessonsCommentId") int newsLessonsCommentId, @Query("operateType") int operateType);


    @GET("/news/app/newsArticleComment/replayZanCount")
    Observable<BaseResponse<String>> replayZanCount(@Query("id") int id,
                                                    @Query("newsArticleId") int newsArticleId,
                                                    @Query("choose") int choose);


    @GET("/news/app/visitor/sysBanner/findSysBannerList")
    Observable<BaseResponse<ArrayList<BannerListDto>>> findSysBannerList(@Query("typeId") int typeId, @Query("location") int location);

    ///user/app/userAccount/recommendEarnedPoint

    @GET("/user/app/userAccount/recommendEarnedPoint")
    Observable<BaseResponse<RecommendEarnedPointDto>> recommendEarnedPoint();


    ///user/app/userAccount/pageMyRecommendDetailWeb

    @POST("/user/app/userAccount/pageMyRecommendDetailWeb")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<PageMyRecommendDetailWebDto>> pageMyRecommendDetailWeb(@Body String jsonSting);


    @GET("/user/app/userLevel/getMyLevelApp")
    Observable<BaseResponse<GetMyLevelAppDto>> getMyLevelApp();


    @GET("/user/app/userLevel/receiveLevelAward")
    Observable<BaseResponse<String>> receiveLevelAward(@Query("levelId") int id);


    @POST("/news/app/newsLessonsSpecialColumn/add")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<String>> addNewsLessonsSpecialColumn(@Body String jsonSting);//


    @GET("/news/app/buy/purchase")
    Observable<BaseResponse<PayDTO>> purchase(@Query("channel") int channel,
                                              @Query("typeId") int typeId,
                                              @Query("objectId") int objectId);

    @GET("/user/app/userMember/checkPayPassword")
    Observable<BaseResponse<String>> checkPayPassword(@Query("password") String password);

    @POST("/user/app/visitor/loginByThirdParty")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<LoginByThirdPartyDTO>> loginByThirdParty(@Body String jsonSting);

    @GET("/user/app/userBalance/doWithdraw")
    Observable<BaseResponse<String>> doWithdraw(@Query("name") String name,
                                                @Query("point") int point,
                                                @Query("channel") int channel);


    @GET("/user/app/userAccount/findUserAccountByOutTradeNo")
    Observable<BaseResponse<AcountListDtoRecordBean>> findUserAccountByOutTradeNo(@Query(
            "outTradeNo") String outTradeNo);

    ///news/app/newsArticle/myPage
    @POST("/news/app/newsArticle/myPage")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<NewsArticleListDTO>> myPagenewsArticle(@Body String jsonSting);


    @GET("/user/app/userMember/getAllRecommendCode")
    Observable<BaseResponse<AllRecommendCodeDto>> getAllRecommendCode();

    @GET("/news/app/visitor/findSysTypeList")
    Observable<BaseResponse<ArrayList<FindSysTypeListDto>>> findSysTypeList(@Query("typeId") int typeId, @Query("parentId") int parentId);

    @GET("/news/app/newsArticleComment/operateNewsArticleCommentCount")
    Observable<BaseResponse<String>> operateNewsArticleCommentCount(@Query("id") int id, @Query(
            "newsArticleId") int newsArticleId, @Query("choose") int choose);

    @POST("/news/app/newsLessonsSpecialColumn/findMyBuyStudyPage")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<BuyHistoryDto>> findMyBuyStudyPage(@Body String jsonSting);

    @GET("/user/app/visitor/getAppUpdateData")
    Observable<BaseResponse<UpdataVersionDto>> getAppUpdateData(@Query("versionCode") String versionCode, @Query("type") int type);

    @GET("/user/app/visitor/addLog")
    Observable<BaseResponse<String>> addLog(@Query("log") String log, @Query("type") int type);


    @GET("/news/app/visitor/newsStudyTypeSetting/findAllList")
    Observable<BaseResponse<ArrayList<AllListDto>>> findAllList();


    @POST("/news/app/newsLessonsComment/addReply")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<CommentReplyListBean>> addContentReply(@Body String dataJson);


    @POST("/news/app/visitor/newsLessonsCommentReply/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<CommentDetailDto>> newsLessonsCommentReplyPage(@Body String dataJson);


    @GET("/news/app/newsLessonsComment/replayZanCount")
    Observable<BaseResponse<String>> replayZanCountLessonsComment(@Query("id") int id, @Query(
            "choose") int choose);

    @GET("/user/app/visitor/addEncourage")
    Observable<BaseResponse<AwardDto>> addEncourage(@Query("typeId") int typeId, @Query("objectId"
    ) int objectId);


    /**
     * 查询学习排版信息【只查询学习排版设置信息】
     *
     * @return
     */
    @GET("/news/app/visitor/findSysTypeList")
    Observable<BaseResponse<ArrayList<StudyTypeInfo>>> queryStudyTypeList(@Query("parentId") String parentId, @Query("typeId") String typeId);

    /**
     * 查询直播課程详情
     *
     * @return
     */
    @GET("/news/app/visitor/findNewsLiveLessonsDetailById")
    Observable<BaseResponse<LiveDetailInfo>> queryNewsLiveLessonsDetailById(@Query("id") String id);

    /**
     * 分页查询直播课程信息
     *
     * @return
     */
    @POST("/news/app/visitor/newsLiveLessons/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<LiveListInfo>> queryVisitorNewsLiveLessons(@Body String dataJson);


    /**
     * 校驗精品课程、直播课程密碼
     *
     * @return
     */
    @POST("/news/app/newsLiveLessons/checkAccessPwd")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse> checkAccessPwd(@Query("checkType") String checkType,
                                            @Query("id") String id,
                                            @Query("accessPwd") String accessPwd);


    /**
     * 打赏
     *
     * @return
     */
    @POST("/user/app/userAuthorContent/reward")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse> reward(@Body String dataJson);


    /**
     * 查询打赏金额列表
     *
     * @return
     */
    @POST("/user/app/userAuthorContent/rewardList")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<RewardListInfo>> queryRewardList();


    /**
     * 提问
     *
     * @return
     */
    @POST("/user/app/userAuthorContent/question")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse> question(@Body String dataJson);

    /**
     * 添加公告信息
     *
     * @return
     */
    @POST("/news/app/newsLiveLessonsNotice/add")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse> addNewsLiveLessonsNotice(@Body String dataJson);

    /**
     * 分页查询直播课程信息
     *
     * @return
     */
    @POST("/news/app/newsLiveLessons/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<LiveListInfo>> newsLiveLessons(@Body String dataJson);

    /**
     * 添加直播课程
     *
     * @return
     */
    @POST("/news/app/newsLiveLessons/add")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse> addLiveLessons(@Body String dataJson);


    /**
     * 直播界面历史消息
     *
     * @return
     */
    @POST("/news/app/newsLiveLessonsComment/page")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<HistoryLiveLessonsListInfo>> queryNewsLiveLessonsComment(@Body String dataJson);

    /**
     * 对当前直播课程中的移除群组、黑名单、禁言操作
     *
     * @return
     */
    @POST("/news/app/newsLiveLessonsBlacklistOrForbid/addOrRemove")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse> addOrRemove(@Body String dataJson);

    /**
     * 添加直播聊天信息【课程讲师、管理员】
     *
     * @return
     */
    @POST("/news/app/newsLiveLessonsComment/add")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse> addNewsLiveLessonsComment(@Body String dataJson);


    /**
     * 查询当前直播课程中的所有群组成员、黑名单、禁言详情
     *
     * @param newsLiveLessonsId 直播课程id
     * @param type              0：群组 、1： 禁言、2：黑名单
     * @return
     */
    @POST("/news/app/newsLiveLessonsBlacklistOrForbid/findList")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<List<BlackLimitUserDetailInfo>>> findListStatus(@Query(
            "newsLiveLessonsId") String newsLiveLessonsId, @Query("type") String type);

    /**
     * 查詢当前直播课程下的所有管理员信息
     *
     * @param newsLiveLessonsId 直播课程id
     * @return
     */
    @POST("/news/app/newsLiveLessonsManager/findAllManagerListByNewsLiveLessonsId")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<List<LiveManageInfo>>> findAllManagerListByNewsLiveLessonsId(@Query(
            "newsLiveLessonsId") String newsLiveLessonsId);


    /**
     * 添加和移除管理员
     *
     * @param newsLiveLessonsId 直播课程id
     * @param memberId          会员ID
     * @param choose            【0:添加、1：移除】
     * @return
     */
    @GET("/news/app/newsLiveLessonsManager/addOrRemoveManager")
    Observable<BaseResponse> addOrRemoveManager(@Query("newsLiveLessonsId") int newsLiveLessonsId
            , @Query("memberId") int memberId, @Query("choose") int choose);


    /**
     * 查询当前会员对应直播课程中的群组成员、黑名单、禁言详情
     *
     * @param newsLiveLessonsId 直播课程id
     * @param type              0：群组 、1： 禁言、2：黑名单
     * @return
     */
    @GET("/news/app/newsLiveLessonsBlacklistOrForbid/findUserMemberBlacklistOrForbidStatus")
    Observable<BaseResponse<Integer>> findDetail(@Query("newsLiveLessonsId") String newsLiveLessonsId, @Query("memberId") String memberId, @Query("type") String type);


    /**
     * (直播状态中)查询直播課程实时信息，若返回错误码或被拉黑需要
     * 跳转回社群列表并刷新列表数据【例如在线观看人数、直播状态、是否已下架等】;
     * 注释:当前直播课程属于正在直播中才需调用此接口
     *
     * @param id 直播课ID
     * @return
     */
    @GET("/news/app/visitor/getNewsLiveLessonsRealTimeDetailById")
    Observable<BaseResponse<RealTimeDetailInfo>> getNewsLiveLessonsRealTimeDetailById(@Query("id") int id);


    @GET("/news/visitor/share/getAddLiveManagerUrl")
    Observable<BaseResponse<String>> getAddLiveManagerUrl(@Query("liveLessonsId") int liveLessonsId);

    /**
     * 重新设置开课时间
     *
     * @param id          直播课程id
     * @param lessonsTime 开课时间【時間格式：如 2018-08-08 08:08:08】
     * @return
     */
    @GET("/news/app/newsLiveLessons/updateOpenLessonsTime")
    Observable<BaseResponse<LiveOpenInfo>> updateOpenLessonsTime(@Query("id") int id, @Query(
            "lessonsTime") String lessonsTime);


    /**
     * 会员修改直播課程状态信息
     *
     * @param id     直播课程id
     * @param status 状态(0 上架、1：下架、2：删除、3：开始直播，4：结束直播)
     * @return
     */
    @GET("/news/app/newsLiveLessons/operateNewsLiveLessonStatus")
    Observable<BaseResponse<LiveOpenInfo>> operateNewsLiveLessonStatus(@Query("id") int id,
                                                                       @Query("status") int status);


    /**
     * 查询红包列表
     *
     * @param redpacketId 红包id
     * @return
     */
    @GET("/user/app/redpacket/grabRedpacket")
    Observable<BaseResponse<LiveHBInfo>> grabRedpacket(@Query("redpacketId") int redpacketId);

    /**
     * 获取红包状态
     *
     * @param redpacketId 红包id
     * @return
     */
    @GET("/user/app/redpacket/getRedpacketState")
    Observable<BaseResponse<HBStatusInfo>> getRedpacketState(@Query("redpacketId") int redpacketId);

    /**
     * A点和A钻的兑换
     *
     * @param typeId 兑换类型(0、奖金A点兑换为A钻 1、奖金A点兑换为现金A点)
     * @param amount 额度（必须为整数）
     * @return
     */
    @GET("/user/app/userBalance/exchange")
    Observable<BaseResponse> balanceExchange(@Query("typeId") int typeId,
                                             @Query("amount") int amount);

    /**
     * A钻提现
     *
     * @return
     */
    @GET("/user/app/userWithdrawDiamond/withdrawDiamond")
    Observable<BaseResponse> withdrawDiamond(@Query("money") double money,
                                             @Query("accountNumber") String accountNumber);

    /**
     * 查询当前直播课程所有历史交流信息
     *
     * @param newsLiveLessonsId 直播课程id
     * @return
     */
    @GET("/news/app/newsLiveLessonsComment/findAllExchangeCommentList")
    Observable<BaseResponse<List<HistoryLiveLessonsInfo>>> findAllExchangeCommentList(@Query(
            "newsLiveLessonsId") int newsLiveLessonsId);

    /**
     * 获取第三方服务
     *
     * @return
     */
    @GET("/user/app/visitor/findAllThirdWebList")
    Observable<BaseResponse<ThirdWebListInfo>> findAllThirdWebList();

    /**
     * 获取喜马拉雅网站访问URL
     *
     * @return
     */
    @GET("/news/app/visitor/findXiMaLaYaWebUrl")
    Observable<BaseResponse<String>> findXiMaLaYaWebUrl();

    /**
     * 绑定微信
     *
     * @return
     */
    @GET("/user/app/userMember/bindWX")
    Observable<BaseResponse<String>> bindWX(@Query("openId") String openId);

    /**
     * 直播界面分享和进入直播需要的操作
     *
     * @param typeId 105  分享直播
     *               202  进入直播
     * @return
     */
    @GET("/user/app/visitor/addEncourage")
    Observable<BaseResponse> addEncourage(@Query("typeId") int typeId);


    /**
     * 发送红包
     *
     * @return
     */
    @POST("/user/app/redpacket/sendRedpacket")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<Integer>> sendRedpacket(@Body String dataJson);


    /**
     * 分页查询已购买社群信息【购买历史】 注释：只需传入分页参数
     *
     * @return
     */
    @POST("/news/app/newsLiveLessons/findMyBuySQPage")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<LiveListInfo>> findMyBuySQPage(@Body String dataJson);


}

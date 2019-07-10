package com.blockadm.common.config;

import android.content.Context;

import com.blockadm.common.utils.PreferencesUtils;

/**
 * Created by Kris on 2019/6/6
 *
 * @Describe TODO {  }
 */
public class ComConfig {

    //【0：精品课程、1：独家专栏、2：A点充值、3：购买VIP、4：购买直播课程，5：支付直播开课费用】
    public static final int Buy_Type_Id_Quality_Lessons = 0; //精品课程
    public static final int Buy_Type_Id_Exclusive_Column = 1; //独家专栏
    public static final int Buy_Type_Id_APoint_Pay = 2; //A点充值
    public static final int Buy_Type_Id_VIP_Pay = 3; //购买VIP
    public static final int Buy_Type_Id_Live_Lessons_Pay = 4; //购买直播课程
    public static final int Buy_Type_Id_Open_Lessons_Pay = 5; //支付直播开课费用

    //是否下次提醒
    public final static String VERSION_IS_NEXT_RMIND = "VERSION_IS_NEXT_RMIND";

    /**
     * 保存是否下次更新
     * @param context
     * @param isNextRemindUpdate
     */
    public static void putNextRemindUpdate(Context context,boolean isNextRemindUpdate){
        PreferencesUtils.putUserBoolean(context, VERSION_IS_NEXT_RMIND,isNextRemindUpdate);
    }


}

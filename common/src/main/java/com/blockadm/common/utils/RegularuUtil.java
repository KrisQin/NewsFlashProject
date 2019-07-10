package com.blockadm.common.utils;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/8/15.
 */

public class RegularuUtil {




    /*
     *bankNumberRegex = @"(^[0-9]{16}$)|([0-9]{19}$)";\
     银行卡号验证
     */
    public  static  boolean bankNumberRegex(String bankNumber){
        String regex = "(^[0-9]{16}$)|([0-9]{19}$)";
        if (!Pattern.matches(regex, bankNumber)){
            return false;
        }
        return true;
    }


    /*
    移动号段正则表达式
    * String regex1 = "^((13[4-9])|(147)|(148)|(15[0-2,7-9])|(178)|(166)|(186)|(198)|(199)|(170)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
    *
    *  联通号段正则表达式
    * String regex2 = "^((13[0-2])|(145)|(146)|(166)|(199)|(15[5-6])|(170)|(171)|(175)|(176)|(18[5,6]))\\d{8}|(1707)|(1708)|(1709)\\d{7}$";
    *
    * 电信号段正则表达式
    *
    * String regex3 = "^((133)|(153)|(170)|(171)|(173)|(174)|(177)|(166)|(199)|(18[0,1,6,9]))\\d{8}|(1700)|(1701)|(1702)\\d{7}$";
    *
    * */
    public  static  boolean telNumberRegex(String telNumber,Context context){
        String regex1 = "^((13[4-9])|(147)|(148)|(15[0-2,7-9])|(178)|(166)|(186)|(198)|(199)|(170)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
        String regex2 = "^((13[0-2])|(145)|(146)|(166)|(199)|(15[5-6])|(170)|(171)|(175)|(176)|(18[5,6]))\\d{8}|(1707)|(1708)|(1709)\\d{7}$";
        String regex3 = "^((133)|(153)|(170)|(171)|(173)|(174)|(177)|(166)|(199)|(18[0,1,6,9]))\\d{8}|(1700)|(1701)|(1702)\\d{7}$";
        if (Pattern.matches(regex1, telNumber)){
            return true;
        }else if (Pattern.matches(regex2, telNumber)){
            return true;
        }else if(Pattern.matches(regex3, telNumber)){
            return true;
        }
        ToastUtils.showToast("手机号不合法");
        return false;

    }


    public static  boolean idCardNumberRegex(String idCardNum, Context context) {

        boolean flag = false;
        //正则匹配身份证格式,缺陷是未检验日期的正确性
        Pattern p = Pattern.compile("(^[1-8][0-7]{2}\\d{3}([12]\\d{3})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}([0-9Xx])$)");

        java.util.regex.Matcher m = p.matcher(idCardNum);
        //匹配最后一位检验码是否正确
        int index[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        //检验码对应规则，第三位实际上应该是x，这个地方用100但是实际上检验时不会用到
        int check[] = {1, 0, 100, 9, 8, 7, 6, 5, 4, 3, 2};
        if (m.matches()) {
            int sum = 0;
            for (int i = 0; i < 17; i++) sum += index[i] * (idCardNum.charAt(i) - '0');
            sum %= 11;
            if (sum == 2 && (idCardNum.charAt(17) == 'x' || idCardNum.charAt(17) == 'X')) flag = true;
            else if (check[sum] == (idCardNum.charAt(17) - '0')) flag = true;
        }
        if (!flag) {
            ToastUtils.showToast("身份证号不合法");
            return  flag;
        }
        return flag;

     }

    public static boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }



    }

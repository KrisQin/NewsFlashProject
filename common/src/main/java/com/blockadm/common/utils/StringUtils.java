package com.blockadm.common.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextPaint;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static Pattern phone   = Pattern
            .compile("^((13[0-9])|(15[^4,\\D])|(17[5-6])|(18[0-3,5-9]))\\d{8}$");

    /**
     * is null or its length is 0 or it is made by space
     * <p/>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return
     * true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * <p/>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return
     * false.
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0 || "null".equals(str));
    }

    public static float getTextWidth(Context Context, String text, int textSize){
        TextPaint paint = new TextPaint();
        float scaledDensity = Context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(text);
    }

    /**
     * 获取默认字符串，如果为null，则返回""
     * @param str
     * @return
     */
    public static String getDefualtNullString(String str){
        return isEmpty(str) ? "" : str;
    }

    public static boolean isNull(Object str) {
        return (str == null || str.toString().length() == 0 || "null"
                .equals(str.toString()));
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getUnitAmount() {
        return "¥";
    }

    /**
     * compare two string
     *
     * @param actual
     * @param expected
     * @return
     * @see ObjectUtils#isEquals(Object, Object)
     */
    public static boolean isEquals(String actual, String expected) {
        return ObjectUtils.isEquals(actual, expected);
    }

    public static boolean isNotEquals(String actual, String expected) {
        return !ObjectUtils.isEquals(actual, expected);
    }
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断金额是否为0
     * @param amount
     * @return
     */
    public static boolean isZeroAmount (String amount) {
        return isEmpty(amount) || isEquals(amount,"0") || isEquals(amount,"0.0") || isEquals(amount,"0.00");
    }


    /**
     * capitalize first letter
     * <p/>
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                : new StringBuilder(str.length())
                .append(Character.toUpperCase(c))
                .append(str.substring(1)).toString();
    }

    /**
     * 保留两位小数点
     *
     * @return
     */
    public static String amountTwoZero(String str) {
        String string = "";
        if (str != null && str != "") {
            if (str.contains(".")) {
                String num = str + "0000";
                string = num.substring(0, num.indexOf(".") + 3);
            } else {
                string = str + ".00";
            }
        }

        return string;
    }

    /**
     * 保留number位小数点
     *
     * @return
     */
    public static String string2Decimal(String str,int number) {

        try {
            if (number < 0) {
                return str;
            } else if (number == 0) {
                if (str.contains(".")) {
                    return str.substring(0, str.indexOf("."));
                } else {
                    return str;
                }

            } else {
                String string = "";

                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < number; i++) {
                    sb.append("00");
                }

                if (str != null && str != "") {
                    if (str.contains(".")) {
                        String num = str + sb.toString();
                        string = num.substring(0, num.indexOf(".") + number + 1);
                    } else {
                        string = str + "." + sb.toString();
                    }
                }

                return string;
            }
        }catch (Exception e) {
            return str;
        }
    }


    /**
     *  第 number 位数后加 省略号： 如第4位后加省略号： 我爱美丽中国 --> 我爱美丽...
     * @return
     */
    public static String stringAddEllipsis(String str, int number) {
        String resultStr = "";
        if (null != str) {
            if (str.length() <= number) {
                resultStr = str;
            }else {
                resultStr = str.substring(0,number)+"...";
            }
        }
        return resultStr;
    }

    /**
     *  金额值转换： 把分转为元
     * @param str str 的单位是分钱
     * @return
     */
    public static String amountChange(String str) {

        L.d("XMPP","金额值 str = "+str);
        String string = "-";
        if (StringUtils.isNotEmpty(str)) {
            if (StringUtils.isEquals(str,"0")) {
                string = "0";
            }else if (str.length() == 1 && !StringUtils.isEquals(str,"0")) {
                string = "0.0"+str;
            }else if (str.length() == 2) {
                string = "0."+str;
            }else {
                string = StringUtils.insertString(str,".",str.length() - 2);
            }
        }
        L.d("XMPP","金额值 转换 string = "+string);

        return string;
    }

    /**
     * 保留一位小数点
     *
     * @return
     */
    public static String amountOneZero(String str) {
        String string = "";
        if (str != null && str != "") {
            if (str.contains(".")) {
                String num = str + "0000";
                string = num.substring(0, num.indexOf(".") + 2);
            } else {
                string = str + ".0";
            }
        }

        return string;
    }

    /**
     * 转为百分比 保留1个小数点，如： 5.0%
     * @param number
     * @return
     */
    public static String changeToOneZeroPercentage(String number) {

        double dNum = 0;
        try {
            if (StringUtils.isNotEmpty(number)) {
                dNum = Double.parseDouble(number);
            }
            int temp = (int) (dNum * 1000);
            dNum = (double) temp / 10;
            return dNum + "%";
        }catch (Exception e) {
            L.d("changeToOneZeroPercentage Exception e: "+e.toString());
        }

        return dNum+"";
    }

    /**
     * 转为百分比 保留2个小数点，如： 5.00%
     * @param number
     * @return
     */
    public static String changeToTwoZeroPercentage(String number) {

        double dNum = 0;
        try {
            if (StringUtils.isNotEmpty(number)) {
                dNum = Double.parseDouble(number);
            }
            DecimalFormat df = new DecimalFormat("0.00%");
            return df.format(dNum);
        }catch (Exception e) {
            L.d("changeToTwoZeroPercentage Exception e: "+e.toString());
        }

        return dNum+"";

    }

    /**
     * 转为百分比 最大精确到5位小数点,小数点后-->数字后的0自动省略,如：1.30200%显示为：1.302% ， 0.0%显示为：0%
     * @param number 入参number为不带%号的字符串数
     * @return
     */
    public static String changeToPercentage(String number) {

        double dNum = 0;
        try {
            if (StringUtils.isNotEmpty(number)) {
                dNum = Double.parseDouble(number);
            }
            DecimalFormat df = new DecimalFormat("0.00000%");
            String numStr = df.format(dNum);
            int index = 0;
            for (int i = numStr.length() - 2; i >= numStr.length() - 6; i--) { //小数点后5位
                String c = String.valueOf(numStr.charAt(i));
                if ("0".equals(c)) {
                    index++;
                }else {
                    break;
                }
            }

            String result;
            if (index == 5) { //说明小数点之后都是0
                result = numStr.substring(0,numStr.indexOf(".")) + "%";
            }else {
                result = numStr.substring(0,numStr.length()-1-index) + "%";
            }

            return result;
        }catch (Exception e) {
            L.d("changeToTwoZeroPercentage Exception e: "+e.toString());
        }

        return dNum+"";

    }

    /**
     * 转为百分比 显示整数，如： 5%
     * @param number 入参number为不带%号的字符串数
     * @return
     */
    public static String changeToNoZeroPercentage(String number) {

        double dNum = 0;
        try {
            if (StringUtils.isNotEmpty(number)) {
                dNum = Double.parseDouble(number);
            }
            return Integer.parseInt(new DecimalFormat("0").format(dNum * 100)) + "%";
        }catch (Exception e) {
            L.d("changeToNoZeroPercentage Exception e: "+e.toString());
        }

        return dNum+"";

    }


    /**
     * 不保留小数点
     *
     * @return
     */
    public static String amountNoZero(String str) {
        String string = "";
        if (str != null && str != "") {
            if (str.contains(".")) {
                string = str.substring(0, str.indexOf("."));
            } else {
                string = str;
            }
        }

        return string;
    }




    /**
     * 访客邀请 星期变换
     * @param time
     * @param period
     * @return
     */
    public static String changePeriod(String time,String period) {
        // 初始化
        time = time.substring(time.indexOf("周"), time.indexOf(")"));
        switch (time) {
            case "周一":
                period = "0000001";
                break;
            case "周二":
                period = "0000010";
                break;
            case "周三":
                period = "0000100";
                break;
            case "周四":
                period = "0001000";
                break;
            case "周五":
                period = "0010000";
                break;
            case "周六":
                period = "0100000";
                break;
            case "周日":
                period = "1000000";
                break;
        }

        return period;
    }


    public static int getIntFrom2ByteArray(final byte[] input) {
        final byte[] result = new byte[4];
        result[0] = 0;
        result[1] = 0;
        result[2] = input[0];
        result[3] = input[1];
        return getIntFromByteArray(result);
    }
    public static int getIntFromByteArray(final byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
    public static String bytesToHexStr(byte[] bytes) {
        String stmp;
        StringBuilder sb = new StringBuilder("");
        for (byte aByte : bytes) {
            stmp = Integer.toHexString(aByte & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * encoded in utf-8
     * <p/>
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * <p/>
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     *
     * @param href
     * @return <ul>
     * <li>if href is null, return ""</li>
     * <li>if not match regx, return source</li>
     * <li>return the last string that match regx</li>
     * </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern
                .compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * process special char in html
     * <p/>
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     *
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtils.isEmpty(source) ? source : source
                .replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * @param nameStr
     * @return 判断姓名是否合法，
     * <p/>
     * return false ; 说明姓名 不合法
     */
    public static Boolean isLegalOfName(String nameStr) {

        return !(null == nameStr || nameStr.length() == 0 || "null".equals(nameStr) || nameStr.length() > 10);

        //		else {
        //
        //			// 判断是否含有中文
        //			Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        //			Matcher m = p.matcher(nameStr);
        //
        //			// 如果含有中文
        //			if (m.find()) {
        //				if (nameStr.length() <= 10) { // 含有中文 , 姓名字符串长度 <=10 合法
        //					return true;
        //				}
        //
        //			} else {
        //				// 没有中文
        //				if (nameStr.length() <= 20) { // 没有中文 , 姓名字符串长度 <=20 合法
        //					return true;
        //				}
        //			}
        //		}


    }

    /**
     * 中文占2个字节，英文占1个字节
     * 姓名格式转换，有中文,则长度超过5显示"..." , 没有中文,则长度超过10显示"..."
     *
     * @param nameStr
     * @return
     */
    public static String nameFormatChange(String nameStr) {

        String name = "";

        if (nameStr == null || nameStr.length() == 0 || "null".equals(nameStr)) {

            name = "";

        } else {
            // 判断是否含有中文
            Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
            Matcher m = p.matcher(nameStr);

            // 如果含有中文
            if (m.find()) {
                if (nameStr.length() > 5) {
                    name = nameStr.substring(0, 5) + "..."; // 保留5位 , 后面添加 "..." , 如 欧阳慕容复后羿,显示为:欧阳慕容复...
                } else {
                    name = nameStr;
                }

            } else {
                // 没有中文
                if (nameStr.length() > 10) {
                    name = nameStr.substring(0, 10) + "...";
                } else {
                    name = nameStr;
                }
            }
        }

        return name;
    }

    /**
     * 字符长度最长为20
     *
     * @param nameStr
     * @return
     */
    public static String nameTwentyLength(String nameStr) {

        String name = "";

        if (nameStr == null || nameStr.length() == 0) {

            name = "";

        } else {
            if (name.length() <= 20) {
                name = nameStr;
            }else {
                name = nameStr.substring(0,20)+"...";
            }
        }

        return name;
    }

    /**
     * 判断是否是字母和数字的组合
     *
     * @param str
     * @return
     */
    public static boolean isContainDigitCharacter(String str) {
        boolean isDigit     = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String  regex   = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && (isLowerCase || isUpperCase) && str.matches(regex);
        return isRight;
    }

    /**
     * 中文占2个字节，英文占1个字节
     * 姓名格式转换，有中文,则长度超过16显示"..." , 没有中文,则长度超过32显示"..."
     *
     * @param nameStr
     * @return
     */
    public static String parkingNameChange(String nameStr) {

        String name = "";

        if (nameStr == null || nameStr.length() == 0 || "null".equals(nameStr)) {

            name = "";

        } else {
            // 判断是否含有中文
            Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
            Matcher m = p.matcher(nameStr);

            // 如果含有中文
            if (m.find()) {
                if (nameStr.length() > 16) {
                    name = nameStr.substring(0, 16) + "..."; // 保留16位 , 后面添加 "..." , 如 欧阳慕容复后羿,显示为:欧阳慕容复...
                } else {
                    name = nameStr;
                }

            } else {
                // 没有中文
                if (nameStr.length() > 32) {
                    name = nameStr.substring(0, 32) + "...";
                } else {
                    name = nameStr;
                }
            }
        }

        return name;

    }

    /**
     * transform half width char to full width char
     * <p/>
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * <p/>
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(CharSequence email) {
        if (isEmpty(email))
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean isPhone(CharSequence phoneNum) {
        if (isEmpty(phoneNum))
            return false;
        return phone.matcher(phoneNum).matches();
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(CharSequence str) {
        try {
            Integer.parseInt(str.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * byte[]数组转换为16进制的字符串。
     *
     * @param data 要转换的字节数组。
     * @return 转换后的结果。
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int    len = s.length();
        byte[] d   = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return d;
    }

    public static String nullStringToDefault(String str) {

        return (str == null || isEquals("null", str)) ? "" : str;
    }


    /**
     *  String 转为 int
     * @param str
     * @return
     */
    public static int string2Int(String str) {
        try {
            return Integer.valueOf(StringUtils.isEmpty(str)? "0":str);
        }catch (Exception e){
            return 0;
        }
    }

    /**
     *  String 转为 double
     * @param str
     * @return
     */
    public static double string2Double(String str) {
        try {
            return Double.valueOf(StringUtils.isEmpty(str)? "0":str);
        }catch (Exception e){
            return 0;
        }
    }


    /**
     * 在 rootStr 的第 position 位置插入 insertStr 字符串
     * <p/>
     * (就是把rootStr 的第 position 位变成 insertStr 字符串)
     * <p/>
     * 如： String rootStr = "ACCF8898766A"
     * String insertStr = ":"
     * <p/>
     * insertString(rootStr,insertStr,2) == AC:CF8898766A
     *
     * @param rootStr
     * @param insertStr
     * @param position
     * @return
     */
    public static String insertString(String rootStr, String insertStr, int position) {

        StringBuilder sb = new StringBuilder(rootStr);
        if (position >= rootStr.length()) {
            return sb.append(insertStr).toString();
        }

        sb.insert(position, insertStr);
        String sbStr = sb.toString();

        return sbStr;
    }

    /**
     * 在第二个位置中插入"-"
     * @param carNo
     * @return
     */
    public static String insertCarNo(String carNo) {
        String str = "";
        if (StringUtils.isNotEmpty(carNo)) {
            String rootStr = carNo.replaceAll("-","");
            str = StringUtils.insertString(rootStr,"-",1);
        }
        return str;
    }


    /**
     * 获取带 "-" 的车牌号
     *
     * @param carNo
     * @return
     */
    public static String takeCarNo(String carNo) {

        if (null != carNo) {
            // 车牌号格式：汉字 + A-Z + 5位A-Z或0-9
            String        carNumRegex = "^[\\u4e00-\\u9fa5]{1}-*[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\\u4e00-\\u9fa5]{1,2}$"; // 车牌号
            String        str         = carNo.replaceAll("-", "");
            StringBuilder sb          = new StringBuilder(str);
            if (sb.toString().matches(carNumRegex)) {
                sb.insert(1, "-");
                String sbStr = sb.toString();
                return sbStr;
            } else {
                return carNo;
            }
        }

        return "";
    }

    /**
     * 判断是否是车牌号
     */
    public static boolean isCarNo(String CarNum) {
        //匹配第一位汉字  ^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$
        String str = "京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼台港澳";
        if (!(CarNum == null || CarNum.equals(""))) {
            String s1 = CarNum.substring(0, 1);//获取字符串的第一个字符
            if (str.contains(s1)) {
                String s2 = CarNum.substring(1, CarNum.length());
                //不包含I O i o的判断
                if (s2.contains("I") || s2.contains("i") || s2.contains("O") || s2.contains("o")) {
                    return false;
                } else {
                    if (!CarNum.matches("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$")) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }


    /**
     * 当前省份对应的车牌的第一位数
     *
     * @param str
     * @return
     */
    public static String locationProvinceCarNo(String str) {
        String firstCarNo = "";

        if (!StringUtils.isEmpty(str)) {
            if (str.contains("北京")) {
                firstCarNo = "京";
            } else if (str.contains("天津")) {
                firstCarNo = "津";
            } else if (str.contains("上海")) {
                firstCarNo = "沪";
            } else if (str.contains("重庆")) {
                firstCarNo = "渝";
            } else if (str.contains("河北")) {
                firstCarNo = "冀";
            } else if (str.contains("河南")) {
                firstCarNo = "豫";
            } else if (str.contains("云南")) {
                firstCarNo = "云";
            } else if (str.contains("辽宁")) {
                firstCarNo = "辽";
            } else if (str.contains("黑龙江")) {
                firstCarNo = "黑";
            } else if (str.contains("湖南")) {
                firstCarNo = "湘";
            } else if (str.contains("安徽")) {
                firstCarNo = "皖";
            } else if (str.contains("山东")) {
                firstCarNo = "鲁";
            } else if (str.contains("新疆")) {
                firstCarNo = "新";
            } else if (str.contains("江苏")) {
                firstCarNo = "苏";
            } else if (str.contains("浙江")) {
                firstCarNo = "浙";
            } else if (str.contains("江西")) {
                firstCarNo = "赣";
            } else if (str.contains("湖北")) {
                firstCarNo = "鄂";
            } else if (str.contains("广西")) {
                firstCarNo = "桂";
            } else if (str.contains("甘肃")) {
                firstCarNo = "甘";
            } else if (str.contains("山西")) {
                firstCarNo = "晋";
            } else if (str.contains("内蒙")) {
                firstCarNo = "蒙";
            } else if (str.contains("陕西")) {
                firstCarNo = "陕";
            } else if (str.contains("吉林")) {
                firstCarNo = "吉";
            } else if (str.contains("福建")) {
                firstCarNo = "闽";
            } else if (str.contains("贵州")) {
                firstCarNo = "贵";
            } else if (str.contains("广东")) {
                firstCarNo = "粤";
            } else if (str.contains("青海")) {
                firstCarNo = "青";
            } else if (str.contains("西藏")) {
                firstCarNo = "藏";
            } else if (str.contains("四川")) {
                firstCarNo = "川";
            } else if (str.contains("宁夏")) {
                firstCarNo = "宁";
            } else if (str.contains("海南")) {
                firstCarNo = "琼";
            } else if (str.contains("台湾")) {
                firstCarNo = "台";
            } else if (str.contains("香港")) {
                firstCarNo = "港";
            } else if (str.contains("澳门")) {
                firstCarNo = "澳";
            }
        } else {
            firstCarNo = "粤";
        }

        return firstCarNo;
    }

    public static String getPayTypeDesc(String payMethodName) {
        String payName = "";
        if (payMethodName.contains("JST")) {
            payName = "捷顺通";
        }
        if (payMethodName.contains("ZFB")) {
            payName = "支付宝";
        }
        if (payMethodName.contains("WX")) {
            payName = "微信支付";
        }
        if (payMethodName.contains("YL")) {
            payName = "银联在线";
        }
        if (payMethodName.contains("CFT")) {
            payName = "财付通";
        }
        if (payMethodName.contains("YFB")) {
            payName = "易付宝";
        }
        if (payMethodName.contains("YZF")) {
            payName = "易支付";
        }
        if (payMethodName.contains("JSJK")) {
            payName = "余额";
        }
        if (payMethodName.contains("JYF")) {
            payName = "余额";
        }

        return payName;
    }


}
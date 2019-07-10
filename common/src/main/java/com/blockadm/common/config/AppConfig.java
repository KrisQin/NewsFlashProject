package com.blockadm.common.config;

import com.blockadm.common.utils.FileUtils;

/**
 * Created by Kris on 2019/4/29
 *
 * @Describe TODO {  }
 */
public class AppConfig {

    public static String APP_NAME = "blockadm";

    public static String package_NAME = "com.blockadm.adm";

    private static AppConfig INSTANCE = new AppConfig();
    public static String sdCardPath = FileUtils.getSDCardPath();
    public static String fileCrashLogPath = sdCardPath + "/.blockadm/.log/";
    private String crashEmail="401063070@qq.com";

    public static AppConfig getInstance() {

        if(INSTANCE == null){
            INSTANCE = new AppConfig();
        }

        return INSTANCE;
    }


    public String getCrashEmail() {
        return crashEmail;
    }

}

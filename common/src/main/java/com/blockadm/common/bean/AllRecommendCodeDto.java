package com.blockadm.common.bean;

/**
 * Created by hp on 2019/3/11.
 */

public class AllRecommendCodeDto {


    /**
     * recommendUrl : http://app.blockadm.pro/user/page/visitor/register?recommend=eefa517ade0fe004
     * recommendImage : data:image/jpeg;base64,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
     * recommendCode : 65ppo
     */

    private String recommendUrl;
    private String recommendImage;
    private String recommendCode;

    public String getRecommendUrl() {
        return recommendUrl;
    }

    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

    public String getRecommendImage() {
        return recommendImage;
    }

    public void setRecommendImage(String recommendImage) {
        this.recommendImage = recommendImage;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }
}

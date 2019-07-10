package com.blockadm.common.utils;

import com.blockadm.common.bean.LessonsSpecialColumnDto;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;

/**
 * Created by Kris on 2019/5/9
 *
 * @Describe TODO { bean类型转换 }
 */
public class BeanTransUtils {

    /**
     * 直播实体类 转为  学习实体类
     * @param detailInfo
     * @return
     */
    public static LessonsSpecialColumnDto LiveDetailInfo2LessonsSpecialColumnDto(LiveDetailInfo detailInfo) {
        LessonsSpecialColumnDto info = new LessonsSpecialColumnDto();

        if (detailInfo != null) {
            info.setMoney(detailInfo.getMoney());
            info.setVipMoney(detailInfo.getMoney());
            info.setCoverImgs(detailInfo.getCoverImgs());
            info.setTitle(detailInfo.getTitle());
            info.setNickName(detailInfo.getNickName());
            info.setId(detailInfo.getId());
        }

        return info;
    }

}

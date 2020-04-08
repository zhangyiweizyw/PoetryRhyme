package com.example.a15632.poetrydemo;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * 写demo时banner用的图片资源实体
 */
public class LocalImageEntity extends SimpleBannerInfo{
    private int bannerRes;

    public LocalImageEntity(int bannerRes){
        this.bannerRes=bannerRes;
    }

    @Override
    public Integer getXBannerUrl() {
        return bannerRes;
    }
}

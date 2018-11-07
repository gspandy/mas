package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

/**
 * 定点投放活动封装类，继承自PricePackageListDto以封装活动信息。
 * 当前用于“定点弹窗”需求，1元包月
 * @author yikun
 * @since 2014-08-28
 */
public class DirectionalPushPricePackageDto extends PricePackageListDto {

    /**
     * 活动资格。0--不符合条件，没有资格，1--符合条件，有资格
     */
    private Integer qualification;

    public DirectionalPushPricePackageDto() {
        super();
    }

    public Integer getQualification() {
        return this.qualification;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }

}

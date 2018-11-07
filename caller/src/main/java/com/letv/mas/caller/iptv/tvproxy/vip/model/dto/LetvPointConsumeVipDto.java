package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;

/**
 * 使用定额类单购买最贵套餐的返回值；
 * 本模型包含用户账户信息的账户有效期截至日期信息、乐点余额信息，可购买最贵套餐的套餐名称。
 * @author yikun
 */
public class LetvPointConsumeVipDto extends BaseResponse {

    /**
     * 操作结果状态，0--购买失败（原因不明）；1--购买成功；2--非法参数；3--乐点余额不足；4--获取套餐包信息失败，5--下单失败；6--
     * 支付失败;
     */
    private Integer resultStatus = 0;

    /**
     * 会员有效时间，格式为"yyyy-MM-dd"
     */
    private String validDate;

    /**
     * 当前乐点余额
     */
    private Integer letvPoint;

    /**
     * 以当前乐点余额可购买的最贵服务包套餐中文名称
     */
    private String packageName;

    public LetvPointConsumeVipDto() {
        super();
    }

    public Integer getResultStatus() {
        return this.resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getValidDate() {
        return this.validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public Integer getLetvPoint() {
        return this.letvPoint;
    }

    public void setLetvPoint(Integer letvPoint) {
        this.letvPoint = letvPoint;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getDataType() {
        return 0;
    }

}

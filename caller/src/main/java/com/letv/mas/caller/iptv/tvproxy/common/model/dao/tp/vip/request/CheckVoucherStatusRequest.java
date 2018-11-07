package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class CheckVoucherStatusRequest extends VipCommonRequest {

    /**
     * 代金券兑换码
     */
    private String couponCode;

    /**
     * 代金券的适用范围列表拼接字符串，用英文逗号分隔，传空表示查询所有；
     * 取值范围：1--普通vip套餐；2--高级vip套餐；3--电影单片；4--直播
     */
    private String types;

    /**
     * 从types解析后得到的代金券的适用范围列表拼
     */
    private List<Integer> typeList;

    public CheckVoucherStatusRequest() {
        super();
    }

    public CheckVoucherStatusRequest(String username, Long userid, String couponCode, String types) {
        super(username, userid);
        this.couponCode = couponCode;
        this.types = types;
    }

    /**
     * 参数校验；暂只对规定了取值范围且由客户端传值的参数进行校验
     * @return boolean；true--参数合法，false--参数非法
     */
    public boolean assertValid() {
        if (StringUtils.isNotBlank(this.types)) {
            // types为空格表示查询所有适用范围
            String[] typeArray = this.types.trim().split(VipTpConstant.COMMON_SPILT_SEPARATOR);
            if (ArrayUtils.isNotEmpty(typeArray)) {
                this.typeList = new ArrayList<Integer>();
                try {
                    for (String typeTmp : typeArray) {
                        Integer type = Integer.parseInt(typeTmp);
                        if (VipTpConstantUtils.VOUCHER_APPLICATION_TYPE.getVoucherApplicationTypeById(type.intValue()) == null) {
                            // 不支持的代金券应用范围
                            return false;
                        } else {
                            this.typeList.add(type);
                        }
                    }
                } catch (NumberFormatException e) {
                    // 非数字字符串
                    // e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 获取参数列表
     * @return
     */
    @Override
    public Map<String, Object> getParametersMap() throws Exception {
        Map<String, Object> parametersMap = super.getParametersMap();

        parametersMap.put("couponCode", this.couponCode);

        return parametersMap;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public List<Integer> getTypeList() {
        return this.typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public String getTypes() {
        return this.types;
    }

}

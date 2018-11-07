package com.letv.mas.caller.iptv.tvproxy.vip.builder;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.PricePackageListTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PricePackageListDto;
import com.letv.mas.caller.iptv.tvproxy.vip.util.VipUtil;

/**
 * PricePackage转换类，将PricePackage转换为PricePackageListDto
 * @author yikun
 */
public class PricePackageBuilder {

    public static PricePackageListDto build(final PricePackageListTpResponse.PackageRecord packageRecord, String langcode) {
        PricePackageListDto dto = null;
        if (isVipPackageAvailable(packageRecord)) {
            dto = new PricePackageListDto();
            dto.setId(packageRecord.getMonthType());
            dto.setPackageType(packageRecord.getMonthType());
            dto.setVipEndType(packageRecord.getEnd());
            // 注意直接取Boss字段，不能保证文案的可国际化
            dto.setPackageName(packageRecord.getName());
            // 可能需要根据语言环境设置
            // dto.setPackageName(MessageUtils.getMessage(VipConstants.PRICE_PACKAGE_TYPE_NAME
            // + "_" + dto.getId(), locale));

            dto.setPrice(packageRecord.getOriginPrice());
            dto.setOriginPrice(packageRecord.getOriginPrice());

            if (packageRecord.getCurrentPrice() != null) {
                dto.setCurrentPrice(packageRecord.getCurrentPrice());
            } else {
                dto.setCurrentPrice(packageRecord.getOriginPrice());
            }

            // 针对支付中心的乐点价格策略，新增乐点价格字段，单独设置乐点价格
            if (packageRecord.getUseCurrentPrice() != null && packageRecord.getUseCurrentPrice().booleanValue()) {
                dto.setLetvPointPrice(String.valueOf(getLetvPointPrice(dto.getCurrentPrice())));
            } else {
                dto.setLetvPointPrice(String.valueOf(getLetvPointPrice(dto.getPrice())));
            }

            dto.setDay(packageRecord.getDays());
            if (packageRecord.getDays() != null) {
                dto.setDuration(VipUtil.parseDayToMonth(packageRecord.getDays().intValue(), 31));
            }

            dto.setDiscount(getDiscount(dto.getCurrentPrice(), dto.getOriginPrice()));

            dto.setDesc(packageRecord.getVipDesc());
            dto.setVipDesc(packageRecord.getVipDesc());
            // dto.setDesc(MessageUtils.getMessage(VipConstants.PRICE_PACKAGE_TYPE_DESC
            // + "_" + dto.getPackageType(),
            // locale));
        }
        return dto;
    }

    /**
     * 判断vipPackageTp是否可解析
     * @param vipPackageTp
     * @param locale
     * @param vipType
     * @return
     */
    private static boolean isVipPackageAvailable(final PricePackageListTpResponse.PackageRecord packageRecord) {

        // 判断是否适合当前平台
        if (packageRecord == null || packageRecord.getTerminals() == null
                || !packageRecord.getTerminals().contains(VipTpConstant.TERMINAL_TV)
                || packageRecord.getMonthType() == null) {
            return false;
        }

        // 判断价格是否合法
        if (!isAvailablePackagePrice(packageRecord.getOriginPrice())
                || !isAvailablePackagePrice(packageRecord.getCurrentPrice())) {
            return false;
        }

        // 过滤包季
        if (String.valueOf(VipTpConstant.ORDER_TYPE_4).equals(packageRecord.getMonthType())) {
            return false;
        }

        return true;
    }

    /**
     * 获取折扣信息，最多保留一位有效小数
     * @param currentPrice
     * @param originPrice
     * @return
     */
    private static String getDiscount(String currentPrice, String originPrice) {
        String format = null;

        try {
            format = String.format("%.1f", Float.parseFloat(currentPrice) * 10.0f / Float.parseFloat(originPrice));
        } catch (Exception e) {
            return null;
        }

        if (format != null && format.endsWith("0")) {
            format = format.substring(0, format.indexOf("."));
        }
        return format;
    }

    /**
     * 判断套餐包价格字符串priceStr是否是合法的价格（非负浮点数）
     * @param priceStr
     * @return
     */
    private static boolean isAvailablePackagePrice(String priceStr) {
        Float price = StringUtil.toFloat(priceStr);
        return price != null && price >= 0f;
    }

    /**
     * 通过现金价格计算乐点价格
     * @param cashPrice
     * @return
     */
    private static Long getLetvPointPrice(String cashPrice) {
        Long letvPointPrice = 0L;

        try {
            Float price = Float.parseFloat(cashPrice);
            if (price != null && price > 0) {
                letvPointPrice = (long) (price * 10);
            }
        } catch (NumberFormatException e) {
            letvPointPrice = 0L;
        }

        return letvPointPrice;
    }
}

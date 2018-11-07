package com.letv.mas.caller.iptv.tvproxy.vip.builder;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.PricePackageListTpResponse;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.VipPackageDto;
import com.letv.mas.caller.iptv.tvproxy.vip.util.VipUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 会员套餐包转换类，用于将VipPackageInfo转换为VipPackageDto
 * @author
 */
public class VipPackageBuilder {

    /**
     * 将vipPackageTp转换为VipPackageDto
     * final Locale locale
     * @param vipPackageTp
     * @param locale
     *            语言设置，用于文案获取
     * @param terminal
     *            套餐适用终端，用于数据过滤
     * @return
     */
    public static VipPackageDto build(final PricePackageListTpResponse.PackageRecord packageRecord, final Integer vipType) {
        VipPackageDto vipPackageDto = null;
        if (isVipPackageAvailable(packageRecord, vipType)) {
            vipPackageDto = new VipPackageDto();
            vipPackageDto.setId(packageRecord.getMonthType());
            vipPackageDto.setVipType(String.valueOf(packageRecord.getEnd()));
            // 注意直接取Boss字段，不能保证文案的可国际化
            vipPackageDto.setPackageName(packageRecord.getName());

            if (packageRecord.getCurrentPrice() != null) {
                vipPackageDto.setCurrentPrice(packageRecord.getCurrentPrice());
            } else {
                vipPackageDto.setCurrentPrice(packageRecord.getOriginPrice());
            }
            vipPackageDto.setOriginPrice(packageRecord.getOriginPrice());
            vipPackageDto.setUseCurrentPrice(packageRecord.getUseCurrentPrice());
            if (packageRecord.getDays() != null) {
                vipPackageDto.setDuration(String.valueOf(VipUtil
                        .parseDayToMonth(packageRecord.getDays().intValue(), 31)));
            }
            vipPackageDto.setDiscount(getDiscount(vipPackageDto.getCurrentPrice(), vipPackageDto.getOriginPrice()));
            vipPackageDto.setVipDesc(packageRecord.getVipDesc());
            // 2015-06-09，TV版不需要套餐图片，所以注释掉
            // vipPackageDto.setMobileImg(packageRecord.getMobileImg());
            // vipPackageDto.setSuperMobileImg(packageRecord.getSuperMobileImg());
        }
        return vipPackageDto;
    }

    /**
     * 判断vipPackageTp是否可解析
     * @param vipPackageTp
     * @param locale
     * @param vipType
     * @return
     */
    private static boolean isVipPackageAvailable(PricePackageListTpResponse.PackageRecord vipPackageTp, Integer vipType) {
        if (vipPackageTp == null || vipType == null) {
            return false;
        }

        // String terminal = VipTpConstantUtils.getTermialByVipType(vipType);
        if (CollectionUtils.isEmpty(vipPackageTp.getTerminals())
                || !vipPackageTp.getTerminals().contains(VipTpConstant.PRICE_ZHIFU_TERMINAL_TV)) {
            return false;
        }

        return true;
    }

    private static String getDiscount(String currentPrice, String originPrice) {
        if (StringUtils.isBlank(currentPrice) || StringUtils.isBlank(originPrice)) {
            return null;
        }
        try {
            return String.format("%.1f", Float.parseFloat(currentPrice) * 10.0f / Float.parseFloat(originPrice));
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            return null;
        }
        /*
         * if (format != null && format.endsWith("0")) {
         * format = format.substring(0, format.indexOf("."));
         * }
         */
    }
}

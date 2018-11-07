package com.letv.mas.caller.iptv.tvproxy.vip.builder;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.ConsumptionRecordTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.OrderStatusTpResponseV2;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.OrderStatusTpResponseV3;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.NumberFormatUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.ConsumptionRecordDto;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.ConsumptionRecordDto2;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ConsumptionRecord转换器，将ConsumptionRecord转换为ConsumptionRecordDto
 * @author yikun
 */
public class ConsumptionRecordBuilder {

    public static ConsumptionRecordDto build(final ConsumptionRecordTpResponse.ConsumptionRecord consumptionRecord, AlbumVideoAccess albumVideoAccess,
                                             List<String> filteringOrderTypeList, CommonParam commonParam) {
        if (consumptionRecord == null || commonParam.getLangcode() == null) {
            return null;
        }

        if (!CollectionUtils.isEmpty(filteringOrderTypeList)
                && !filteringOrderTypeList.contains(consumptionRecord.getOrderType())) {
            return null;
        }

        // CIBN消费记录文案“高级VIP会员”修改为“TV影视会员”
        String orderName = consumptionRecord.getOrderName();
        // if (broadcastId != null && VipConstants.BROADCAST_CIBN ==
        // broadcastId) {
        // orderName =
        // orderName.replaceAll(VipConstants.CONSUME_RECORD_ORDERNAME_SRC_TEXT,
        // VipConstants.CONSUME_RECORD_ORDERNAME_TARGET_TEXT);
        // }
        String statusName = getStatusName(consumptionRecord.getStatus(), consumptionRecord.getOrderType());
        ConsumptionRecordDto dto = new ConsumptionRecordDto(consumptionRecord.getOrderId(), orderName,
                consumptionRecord.getMoney(), consumptionRecord.getMoneyName(), consumptionRecord.getCreateTime(),
                consumptionRecord.getCancelTime(), statusName, consumptionRecord.getOrderFrom(),
                consumptionRecord.getPayTime(), consumptionRecord.getIsShowRepay());
        if ((commonParam.getBroadcastId() != null) && (VipConstants.BROADCAST_CIBN == commonParam.getBroadcastId())) {
            dto.setMoneyName(null);// 国广版本不展示乐点
        }
        // 机卡绑定套餐的消费记录需要做特殊处理
        if (String.valueOf(VipTpConstant.ORDER_FROM_DEVICEBIND).equals(consumptionRecord.getOrderFrom())) {
            // 消费记录状态默认为“支付成功”
            dto.setStatusName(VipTpConstant.ORDER_STATUS_PACKAGE_SUCCEED);
        }

        // 单点支付新增逻辑，需要通过orderType判断当前订单是否为单点订单
        boolean isFilmOrder = String.valueOf(VipTpConstant.ORDER_TYPE_0).equals(consumptionRecord.getOrderType());
        String orderCreateAndCancelTimeFormat = isFilmOrder ? TimeUtil.SIMPLE_DATE_HOUR_MINUTE_FORMAT
                : TimeUtil.SHORT_DATE_FORMAT;

        dto.setOrderType(consumptionRecord.getOrderType());

        // 设置订单来源和订单支付状态
        dto.setOrderFromText(MessageUtils.getMessage(VipConstants.getOrderFromByCode(dto.getOrderFrom()),
                commonParam.getLangcode()));
        dto.setStatusNameText(MessageUtils.getMessage(VipConstants.getOrderStatusByCode(dto.getStatusName()),
                commonParam.getLangcode()));

        if (StringUtils.isNotBlank(consumptionRecord.getCreateTime())) {
            // 单点支付新增逻辑
            String createTime = TimeUtil.getDateStringFromLong(Long.valueOf(consumptionRecord.getCreateTime()),
                    orderCreateAndCancelTimeFormat);
            dto.setCreateTime(createTime);
        } else {
            dto.setCreateTime("-");
        }

        // 判断yuanxian返回值中订单失效时间是否合法
        boolean isCancelTimeValid = NumberFormatUtil.isNumeric(consumptionRecord.getCancelTime())
                && Long.parseLong(consumptionRecord.getCancelTime()) > 0;
        if (isCancelTimeValid) {
            // 单点支付新增逻辑
            String cancelTime = TimeUtil.getDateStringFromLong(Long.valueOf(consumptionRecord.getCancelTime()),
                    orderCreateAndCancelTimeFormat);
            dto.setCancelTime(cancelTime);
        } else {
            dto.setCancelTime("-");
        }

        if (StringUtils.isNotBlank(consumptionRecord.getPayTime())) {
            dto.setPayTime(TimeUtil.getDateString(new Date(Long.valueOf(consumptionRecord.getPayTime())),
                    TimeUtil.SHORT_DATE_FORMAT));
        } else {
            dto.setPayTime("-");
        }

        // 是要过滤得到的还有效的单点订单，要特殊处理
        if (isFilmOrder && !CollectionUtils.isEmpty(filteringOrderTypeList) && isCancelTimeValid) {
            if (VipTpConstant.ORDER_STATUS_VIDEO_SUCCEED.equals(consumptionRecord.getStatusName())) {
                // 单点有效时，计算剩余有效市场
                dto.setLeftEffectiveTime(getFilmLeftEffectiveTime(Long.parseLong(consumptionRecord.getCancelTime())
                        - System.currentTimeMillis()));
            }

            if (VipTpConstant.ORDER_STATUS_VIDEO_SUCCEED.equals(consumptionRecord.getStatusName())
                    || VipTpConstant.ORDER_STATUS_VIDEO_PASTDUE.equals(consumptionRecord.getStatusName())) {
                // 只要购买过，就需获取专辑信息，展示缩略图
                dto.setAlbumId(consumptionRecord.getAid2());
                AlbumMysqlTable album = albumVideoAccess.getAlbum(
                        Long.valueOf(consumptionRecord.getAid2()), commonParam);
                if (album != null) {
                    dto.setAlbumImg_400X300(album.getPic(400, 300));
                }
            }
        }

        return dto;
    }

    public static ConsumptionRecordDto buildV2(final OrderStatusTpResponseV2.UserPackageInfoV2.UserPackage consumptionRecord,AlbumVideoAccess albumVideoAccess,
            List<String> filteringOrderTypeList, CommonParam commonParam) {
        if (consumptionRecord == null || commonParam.getLangcode() == null) {
            return null;
        }

        if (!CollectionUtils.isEmpty(filteringOrderTypeList)
                && !filteringOrderTypeList.contains(consumptionRecord.getOrder_flag())) {
            return null;
        }

        // CIBN消费记录文案“高级VIP会员”修改为“TV影视会员”
        String orderName = consumptionRecord.getOrderName();
        // if (broadcastId != null && VipConstants.BROADCAST_CIBN ==
        // broadcastId) {
        // orderName =
        // orderName.replaceAll(VipConstants.CONSUME_RECORD_ORDERNAME_SRC_TEXT,
        // VipConstants.CONSUME_RECORD_ORDERNAME_TARGET_TEXT);
        // }
        String statusName = getStatusName(consumptionRecord.getStatus(), consumptionRecord.getOrder_flag());
        ConsumptionRecordDto dto = new ConsumptionRecordDto(consumptionRecord.getOrderid(), orderName,
                String.valueOf(consumptionRecord.getPayPrice()), String.valueOf(consumptionRecord.getPayPrice() * 100),
                consumptionRecord.getCreateTime(), consumptionRecord.getEndTime(), statusName,
                consumptionRecord.getPlatform(), consumptionRecord.getCreateTime(), consumptionRecord.getIsShowRepay());
        if ((commonParam.getBroadcastId() != null) && (VipConstants.BROADCAST_CIBN == commonParam.getBroadcastId())) {
            dto.setMoneyName(null);// 国广版本不展示乐点
        }
        // 机卡绑定套餐的消费记录需要做特殊处理
        if (String.valueOf(VipTpConstant.ORDER_FROM_DEVICEBIND).equals(consumptionRecord.getPlatform())) {
            // 消费记录状态默认为“支付成功”
            dto.setStatusName(VipTpConstant.ORDER_STATUS_PACKAGE_SUCCEED);
        }

        // 单点支付新增逻辑，需要通过orderType判断当前订单是否为单点订单
        boolean isFilmOrder = String.valueOf(VipTpConstant.ORDER_TYPE_0).equals(consumptionRecord.getOrder_flag());
        String orderCreateAndCancelTimeFormat = isFilmOrder ? TimeUtil.SIMPLE_DATE_HOUR_MINUTE_FORMAT
                : TimeUtil.SHORT_DATE_FORMAT;

        dto.setOrderType(consumptionRecord.getOrder_flag());

        // 设置订单来源和订单支付状态
        dto.setOrderFromText(MessageUtils.getMessage(VipConstants.getOrderFromByCode(dto.getOrderFrom()),
                commonParam.getLangcode()));
        dto.setStatusNameText(MessageUtils.getMessage(VipConstants.getOrderStatusByCode(dto.getStatusName()),
                commonParam.getLangcode()));

        if (StringUtils.isNotBlank(consumptionRecord.getCreateTime())) {
            // 单点支付新增逻辑
            String createTime = TimeUtil.getDateStringFromLong(Long.valueOf(consumptionRecord.getCreateTime()),
                    orderCreateAndCancelTimeFormat);
            dto.setCreateTime(createTime);
        } else {
            dto.setCreateTime("");
        }

        // 判断yuanxian返回值中订单失效时间是否合法
        boolean isCancelTimeValid = NumberFormatUtil.isNumeric(consumptionRecord.getEndTime())
                && Long.parseLong(consumptionRecord.getEndTime()) > 0;
        if (isCancelTimeValid) {
            // 单点支付新增逻辑
            String cancelTime = TimeUtil.getDateStringFromLong(Long.valueOf(consumptionRecord.getEndTime()),
                    orderCreateAndCancelTimeFormat);
            dto.setCancelTime(cancelTime);
        } else {
            dto.setCancelTime("");
        }

        if (StringUtils.isNotBlank(consumptionRecord.getCreateTime())) {
            dto.setPayTime(TimeUtil.getDateString(new Date(Long.valueOf(consumptionRecord.getCreateTime())),
                    TimeUtil.SHORT_DATE_FORMAT));
        } else {
            dto.setPayTime("");
        }

        // 是要过滤得到的还有效的单点订单，要特殊处理
        if (isFilmOrder && !CollectionUtils.isEmpty(filteringOrderTypeList) && isCancelTimeValid) {
            if (VipTpConstant.ORDER_STATUS_VIDEO_SUCCEED.equals(consumptionRecord.getStatus())) {
                // 单点有效时，计算剩余有效市场
                dto.setLeftEffectiveTime(getFilmLeftEffectiveTime(Long.parseLong(consumptionRecord.getEndTime())
                        - System.currentTimeMillis()));
            }

            if (VipTpConstant.ORDER_STATUS_VIDEO_SUCCEED.equals(consumptionRecord.getStatus())
                    || VipTpConstant.ORDER_STATUS_VIDEO_PASTDUE.equals(consumptionRecord.getStatus())) {
                // 只要购买过，就需获取专辑信息，展示缩略图
                dto.setAlbumId(consumptionRecord.getProductId());
                AlbumMysqlTable album = albumVideoAccess.getAlbum(
                        Long.valueOf(consumptionRecord.getProductId()), commonParam);
                if (album != null) {
                    dto.setAlbumImg_400X300(album.getPic(400, 300));
                }
            }
        }
        return dto;
    }

    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ConsumptionRecordDto2 buildV3(final OrderStatusTpResponseV3.UserPackageV3 consumptionRecord, String orderType,
                                                CommonParam commonParam) {
        if (consumptionRecord == null || commonParam.getLangcode() == null) {
            return null;
        }

        // CIBN消费记录文案“高级VIP会员”修改为“TV影视会员”
        String orderName = consumptionRecord.getOrder_name();
        // if (broadcastId != null && VipConstants.BROADCAST_CIBN ==
        // broadcastId) {
        // orderName =
        // orderName.replaceAll(VipConstants.CONSUME_RECORD_ORDERNAME_SRC_TEXT,
        // VipConstants.CONSUME_RECORD_ORDERNAME_TARGET_TEXT);
        // }
        if (StringUtil.isNotBlank(orderName)) {
            orderName = orderName.replaceAll("乐视", "");
        }
        ConsumptionRecordDto2 dto2 = new ConsumptionRecordDto2(consumptionRecord.getVip_order_id(), orderName,
                consumptionRecord.getOrder_start_time(), consumptionRecord.getOrder_end_time(),
                consumptionRecord.getPay_time());
        if ((commonParam.getBroadcastId() != null) && (VipConstants.BROADCAST_CIBN == commonParam.getBroadcastId())) {
            dto2.setMoneyName(null);// 国广版本不展示乐点
        }

        // 设置订单来源和订单支付状态
        dto2.setOrderFromText(MessageUtils.getMessage(VipConstants.getOrderFromByCode(dto2.getOrderFrom()),
                commonParam.getLangcode()));
        dto2.setStatusNameText(MessageUtils.getMessage(VipConstants.getOrderStatusByCode(dto2.getStatus()),
                commonParam.getLangcode()));

        if (StringUtils.isNotBlank(consumptionRecord.getOrder_start_time())) {
            dto2.setCreateTime(consumptionRecord.getPay_time());
        } else {
            dto2.setCreateTime("");
        }

        dto2.setCancelTime(consumptionRecord.getOrder_end_time());

        dto2.setPayTime(consumptionRecord.getPay_time());

        dto2.setAct_id(consumptionRecord.getAct_id());
        dto2.setAct_type(consumptionRecord.getAct_type());
        dto2.setPresent_name(consumptionRecord.getPresent_name());
        dto2.setPresent_img_url(consumptionRecord.getPresent_img_url());
        dto2.setPresent_url(consumptionRecord.getPresent_url());
        dto2.setShipping_url(consumptionRecord.getShipping_url());
        dto2.setStatus(consumptionRecord.getStatus());

        dto2.setPay_status(VipConstants.getOrderPayStatusByCode(consumptionRecord.getPay_status(),
                consumptionRecord.getIs_Month_payment()));
        // dto2.setPayStatusNameText(MessageUtils.getMessage(VipConstants.getOrderPayStatusByCode(consumptionRecord.getPay_status(),
        // consumptionRecord.getIs_Month_payment()),
        // commonParam.getLangcode()));
        if (dto2.getPay_status() != null) {
            if (dto2.getPay_status().equals(VipTpConstant.ORDER_PAY_STATUS_FAIL)) {
                dto2.setPayStatusNameText(MessageUtils.getMessage(VipTpConstant.ORDER_PAY_STATUS_FAIL_STR,
                        commonParam.getLangcode()));
            } else if (dto2.getPay_status().equals(VipTpConstant.ORDER_PAY_STATUS_SUCC)) {
                dto2.setPayStatusNameText(MessageUtils.getMessage(VipTpConstant.ORDER_PAY_STATUS_SUCC_STR,
                        commonParam.getLangcode()));
            } else if (dto2.getPay_status().equals(VipTpConstant.ORDER_PAY_STATUS_REFUND)) {
                dto2.setPayStatusNameText(MessageUtils.getMessage(VipTpConstant.ORDER_PAY_STATUS_REFUND_STR,
                        commonParam.getLangcode()));
            } else if (dto2.getPay_status().equals(VipTpConstant.ORDER_PAY_STATUS_AUTOMATIC_CHARGE_FAIL)) {
                dto2.setPayStatusNameText(MessageUtils.getMessage(VipTpConstant.ORDER_PAY_STATUS_CHARGE_FAIL_STR,
                        commonParam.getLangcode()));
            }
        }

        String payTimeStamp = date2TimeStamp(consumptionRecord.getPay_time(), "yyyy-MM-dd HH:mm:ss");
        Long s = (System.currentTimeMillis() - Long.parseLong(payTimeStamp)) / (1000 * 60 * 60 * 24);
        if (s < 30) {
            dto2.setIsExpired(false);
        } else {
            dto2.setIsExpired(true);
        }
        return dto2;
    }

    /**
     * 获取还在有效期内的单点影片剩余有效时间，格式为"天:时:分"，使用英文冒号（:）分割
     * @param leftEffectiveTimeMillis
     * @return
     */
    private static String getFilmLeftEffectiveTime(long leftEffectiveTimeMillis) {
        long leftEffectiveTime = leftEffectiveTimeMillis / 1000;
        // int days = (int) (leftEffectiveTime /
        // CalendarUtil.SECONDS_OF_PER_DAY);
        int days = 0;
        // int minutes = (int) ((leftEffectiveTime %
        // CalendarUtil.SECONDS_OF_PER_DAY) / CalendarUtil.SECONDS_OF_PER_HOUR);
        int minutes = (int) (leftEffectiveTime / CommonConstants.SECONDS_OF_1_HOUR);
        int seconds = (int) ((leftEffectiveTime % CommonConstants.SECONDS_OF_1_HOUR) / CommonConstants.SECONDS_OF_1_MINUTE);
        StringBuffer timeBuffer = new StringBuffer();
        timeBuffer.append(NumberFormatUtil.getDecimalNubmerFromInt(days, NumberFormatUtil.COMMON_DATE_AND_TIME_FORMAT))
                .append(TimeUtil.TIME_FORMAT_SEPARATOR);
        timeBuffer.append(
                NumberFormatUtil.getDecimalNubmerFromInt(minutes, NumberFormatUtil.COMMON_DATE_AND_TIME_FORMAT))
                .append(TimeUtil.TIME_FORMAT_SEPARATOR);
        timeBuffer.append(NumberFormatUtil.getDecimalNubmerFromInt(seconds,
                NumberFormatUtil.COMMON_DATE_AND_TIME_FORMAT));
        return timeBuffer.toString();
    }

    private static String getStatusName(String statusStr, String orderType) {
        // 0,未支付 |1,支付 |2,失效|3,退款|4,关闭
        Integer status = StringUtil.toInteger(statusStr, 0);
        // 0，1--单点，1001--直播，其他为套餐类型
        boolean isNotPackage = "0".equals(orderType) || "1".equals(orderType) || "1001".equals(orderType);
        String statusName = null;
        switch (status) {
        case 0:
            statusName = "0";
            break;
        case 1:
            statusName = isNotPackage ? "1" : "2";
            break;
        default:
            statusName = isNotPackage ? "-1" : "-2";
            break;
        }
        return statusName;
    }
}

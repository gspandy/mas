package com.letv.mas.caller.iptv.tvproxy.vip.builder;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.VipActivity;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.BossActivityData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.PaymentActivityTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PaymentActivityDto;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 付费活动转换器，将PaymentActivity转换为PaymentActivityDto
 * @author yikun
 */
public class PaymentActivityBuilder {

    public static PaymentActivityDto build(PaymentActivityTpResponse.PaymentActivity paymentActivity, Integer av) {
        PaymentActivityDto dto = null;
        if (isPaymentActivityAvailable(paymentActivity)) {

            // 因支付渠道中，支付宝、微信编号在自由版和国广版中不一样，服务端需要对支付渠道活动进行特殊处理，使其对客户端透明
            List<Integer> payTypeList = null;
            if (paymentActivity.getType() != null
                    && VipTpConstant.PAYMENT_ACTIVITY_TYPE_PAYMENT_CHANNEL == paymentActivity.getType() && av != null
                    && av == VipTpConstant.BROADCAST_APK_VERSION_CIBN) {

                // 是支取渠道活动且是国广版本
                payTypeList = processPayTypeList(paymentActivity);

                if (CollectionUtils.isEmpty(payTypeList)) {
                    // 如果特殊处理后，支付渠道活动对应的支付渠道列表为空，则说明活动配置不适用国广版本
                    return dto;
                }
            } else {
                payTypeList = paymentActivity.getPayTypeList();
            }

            dto = new PaymentActivityDto();
            Integer dis = paymentActivity.getDiscount();
            String discount = null;
            if (dis != null) {
                discount = "" + dis.intValue();
            }
            dto.setPayTypeList(payTypeList);
            dto.setActivityId(paymentActivity.getActivityId());
            dto.setType(paymentActivity.getType());
            dto.setMonthType(paymentActivity.getMonthType());
            dto.setDiscount(discount);
            dto.setProlongDays(paymentActivity.getProlongDays());
            dto.setIconText(paymentActivity.getIconText());
            dto.setLableText(paymentActivity.getLableText());
            dto.setBodyText(paymentActivity.getBodyText());
            dto.setNeedLogin(paymentActivity.getNeedLogin());
            dto.setAllowPayLepoint(paymentActivity.getAllowPayLepoint());
            dto.setHasUserQuota(paymentActivity.getHasUserQuota());
            dto.setLeftQuota(paymentActivity.getLeftQuota());
            dto.setUserQuota(paymentActivity.getUserQuota());
            if (paymentActivity.getAllowPayLepoint() == null || Boolean.FALSE == paymentActivity.getAllowPayLepoint()) {
                dto.setLetvPointPayText("乐点暂不参加活动");
            }
        }

        return dto;
    }

    public static PaymentActivityDto build(PaymentActivityTpResponse.PaymentActivity paymentActivityInfo, Integer vipType, String langcode) {
        PaymentActivityDto dto = null;
        if (isPaymentActivityAvailable(paymentActivityInfo)) {

            dto = new PaymentActivityDto();
            Integer dis = paymentActivityInfo.getDiscount();
            String discount = null;
            if (dis != null) {
                discount = "" + dis.intValue();
            }
            dto.setActivityId(paymentActivityInfo.getActivityId());
            dto.setType(paymentActivityInfo.getType());
            dto.setMonthType(paymentActivityInfo.getMonthType());
            dto.setPayTypeList(paymentActivityInfo.getPayTypeList());
            dto.setDiscount(discount);
            dto.setProlongDays(paymentActivityInfo.getProlongDays());
            dto.setIconText(paymentActivityInfo.getIconText());
            dto.setLableText(paymentActivityInfo.getLableText());
            dto.setBodyText(paymentActivityInfo.getBodyText());
            dto.setNeedLogin(paymentActivityInfo.getNeedLogin());
            dto.setAllowPayLepoint(paymentActivityInfo.getAllowPayLepoint());
            dto.setHasUserQuota(paymentActivityInfo.getHasUserQuota());
            dto.setQuota(paymentActivityInfo.getQuota());
            dto.setLeftQuota(paymentActivityInfo.getLeftQuota());
            dto.setUserQuota(paymentActivityInfo.getUserQuota());
            if (dto.getAllowPayLepoint() == null || Boolean.FALSE == dto.getAllowPayLepoint()) {
                dto.setLetvPointPayText(MessageUtils.getMessage(VipConstants.VIP_PAYMENTACTIVITY_LETVPOINTPAYTEXT,
                        langcode));
            }
        }
        return dto;
    }

    @SuppressWarnings("deprecation")
    public static PaymentActivityDto build(BossActivityData activityData, String langcode) {
        if (activityData == null) {
            return null;
        }
        if (activityData.getType() == null || activityData.getType() != 1) {
            // just need package activity data
            return null;
        }
        if (activityData.getTerminals() == null || !activityData.getTerminals().contains(VipTpConstant.TERMINAL_TV)) {
            return null;
        }
        if (activityData.getQuota() == null || 0 == activityData.getQuota() || activityData.getQuota() < -1) {
            return null;
        }
        if (activityData.getUserQuota() == null || activityData.getUserQuota() == 0 || activityData.getUserQuota() < -1) {
            return null;
        }
        if (activityData.getBeginTime() == null || activityData.getEndTime() == null
                || activityData.getBeginDate() == null || activityData.getEndDate() == null) {
            return null;
        }
        long currentTime = System.currentTimeMillis();
        Date date = new Date();
        Date startDate = TimeUtil.parseDate(activityData.getBeginDate(), TimeUtil.SIMPLE_DATE_FORMAT);
        Date endDate = TimeUtil.parseDate(activityData.getEndDate(), TimeUtil.SIMPLE_DATE_FORMAT);
        long startTime = 0;
        long endTime = 0;
        if (startDate != null) {
            startTime = startDate.getTime();
        }
        if (endDate != null) {
            endTime = endDate.getTime();
        }
        String time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        if (!(currentTime >= startTime && currentTime <= endTime)
                || !(TimeUtil.afterTime(activityData.getBeginTime(), time) && TimeUtil.beforeTime(
                        activityData.getEndTime(), time))) {
            return null;
        }

        PaymentActivityDto dto = new PaymentActivityDto();
        dto.setActivityId(activityData.getActivityPackageId());
        dto.setType(activityData.getType());
        dto.setMonthType(activityData.getMonthType());
        dto.setPayTypeList(activityData.getPayTypeList());
        dto.setDiscount(activityData.getDeduction());
        dto.setLableText(activityData.getLabel());
        dto.setNeedLogin(activityData.getNeedLogin());
        dto.setAllowPayLepoint(activityData.getAllowPayLepoint());
        dto.setQuota(activityData.getQuota());
        dto.setUserQuota(activityData.getUserQuota());
        dto.setImg(activityData.getTvOrientImg());
        dto.setUrl(activityData.getTvOrientUrl());
        dto.setBodyText(activityData.getTitle());
        dto.setPackageText(activityData.getPackageText());
        dto.setText(activityData.getText());
        dto.setCoupon(activityData.getCoupon());
        if (dto.getAllowPayLepoint() == null || Boolean.FALSE == dto.getAllowPayLepoint()) {
            dto.setLetvPointPayText(MessageUtils
                    .getMessage(VipConstants.VIP_PAYMENTACTIVITY_LETVPOINTPAYTEXT, langcode));
        }
        return dto;
    }

    public static PaymentActivityDto build(VipActivity activityData, String langcode) {
        if (activityData == null) {
            return null;
        }
        if (activityData.getType() == null || activityData.getType() != 1) { // only
                                                                             // support
                                                                             // package
                                                                             // model
            // just need package activity data
            return null;
        }
        if (activityData.getTerminals() == null || !activityData.getTerminals().contains(VipTpConstant.TERMINAL_TV)) {
            return null;
        }
        if (activityData.getBeginTime() == null || activityData.getEndTime() == null
                || activityData.getBeginDate() == null || activityData.getEndDate() == null) {
            return null;
        }
        long currentTime = System.currentTimeMillis();
        Date date = new Date();
        Date startDate = TimeUtil.parseDate(activityData.getBeginDate(), TimeUtil.SIMPLE_DATE_FORMAT);
        Date endDate = TimeUtil.parseDate(activityData.getEndDate(), TimeUtil.SIMPLE_DATE_FORMAT);
        long startTime = 0;
        long endTime = 0;
        if (startDate != null) {
            startTime = startDate.getTime();
        }
        if (endDate != null) {
            endTime = endDate.getTime();
        }
        String time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        if (!(currentTime >= startTime && currentTime <= endTime)
                || !(TimeUtil.afterTime(activityData.getBeginTime(), time) && TimeUtil.beforeTime(
                        activityData.getEndTime(), time))) {
            return null;
        }

        PaymentActivityDto dto = new PaymentActivityDto();
        dto.setActivityId(String.valueOf(activityData.getId()));
        dto.setType(activityData.getType());
        dto.setMonthType(activityData.getVipPackageId());
        dto.setPayTypeList(null); // TODO
        dto.setDiscount(null);
        VipActivity.Copywritings copywritings = activityData.getCopywritings();
        String lableText = "";
        if (null != copywritings && StringUtil.isNotBlank(copywritings.getLabel1())) {
            lableText += copywritings.getLabel1();
        }
        if (null != copywritings && StringUtil.isNotBlank(copywritings.getLabel2())) {
            lableText += copywritings.getLabel2();
        }
        dto.setLableText(lableText);
        dto.setNeedLogin(true);
        dto.setAllowPayLepoint(false);
        dto.setQuota(-1);
        dto.setUserQuota(-1);
        if (null != copywritings && StringUtil.isNotBlank(copywritings.getPic1())) {
            dto.setImg(copywritings.getPic1());
        }
        dto.setUrl(activityData.getDetailUrl());
        dto.setBodyText(null);
        dto.setPackageText(activityData.getTitle());
        dto.setText(activityData.getDescription());
        dto.setCoupon(1);
        if (dto.getAllowPayLepoint() == null || Boolean.FALSE == dto.getAllowPayLepoint()) {
            dto.setLetvPointPayText(MessageUtils
                    .getMessage(VipConstants.VIP_PAYMENTACTIVITY_LETVPOINTPAYTEXT, langcode));
        }
        return dto;
    }

    /**
     * 判断PaymentActivity是否可用;
     * 活动必须适用TV端；必须说明活动总数限制，必须说明用户参与活动次数限制；时间限制必须合法
     * @param paymentActivity
     * @return boolean true--可用，false--不可用
     */
    private static boolean isPaymentActivityAvailable(PaymentActivityTpResponse.PaymentActivity paymentActivity) {

        return isPaymentActivityApplyToTV(paymentActivity) && isPaymentActivityQuotaLegal(paymentActivity)
                && isPaymentActivityUserQuotaLegal(paymentActivity) && isPaymentActivityTimeLegal(paymentActivity);
    }

    /**
     * 判断活动信息是否适用TV端；
     * terminals字符串列表中必须包含“141007”
     * @return
     */
    private static boolean isPaymentActivityApplyToTV(PaymentActivityTpResponse.PaymentActivity paymentActivity) {

        return paymentActivity != null && !CollectionUtils.isEmpty(paymentActivity.getTerminals())
                && paymentActivity.getTerminals().contains(VipTpConstant.TERMINAL_TV);
    }

    /**
     * 判断付费活动总数限制是否合法；
     * hasUserQuota字段必须给出；为true时userQuota必须给出且大于0
     * @param paymentActivity
     * @return
     */
    private static boolean isPaymentActivityQuotaLegal(PaymentActivityTpResponse.PaymentActivity paymentActivity) {
        if (paymentActivity.getQuota() == null || 0 == paymentActivity.getQuota() || paymentActivity.getQuota() < -1) {
            return false;
        }

        if (paymentActivity.getQuota() > 0) {
            return (paymentActivity.getLeftQuota() != null && paymentActivity.getLeftQuota() > 0);
        }

        return true;

    }

    /**
     * 判断付费活动用户参数次数限制是否合法；
     * quota必须给出，只能为-1或其他正数值；大于0时，leftQuota必须给出，且大于0
     * @param paymentActivity
     * @return
     */
    private static boolean isPaymentActivityUserQuotaLegal(PaymentActivityTpResponse.PaymentActivity paymentActivity) {
        if (paymentActivity.getHasUserQuota() == null) {
            return false;
        }

        if (Boolean.TRUE == paymentActivity.getHasUserQuota()) {
            return (paymentActivity.getUserQuota() != null && paymentActivity.getUserQuota() > 0);
        }

        return true;
    }

    /**
     * 判断付费活动时间限制是否合法
     * @param paymentActivity
     * @return
     */
    private static boolean isPaymentActivityTimeLegal(PaymentActivityTpResponse.PaymentActivity paymentActivity) {
        if (paymentActivity == null || paymentActivity.getBeginTime() == null || paymentActivity.getEndTime() == null
                || paymentActivity.getIntervalBegin() == null || paymentActivity.getIntervalEnd() == null) {
            return false;
        }

        // 活动时间、日期均需合法
        long now = System.currentTimeMillis();
        String hourMinuteStr = TimeUtil.getDateStringFromLong(now, TimeUtil.TIME_FORMAT);

        return (now >= paymentActivity.getBeginTime() && now < paymentActivity.getEndTime())
                && (hourMinuteStr.compareTo(paymentActivity.getIntervalBegin()) >= 0 && hourMinuteStr
                        .compareTo(paymentActivity.getIntervalEnd()) < 0);
    }

    /**
     * 针对支付渠道活动进行特殊处理
     */
    private static List<Integer> processPayTypeList(PaymentActivityTpResponse.PaymentActivity paymentActivity) {
        List<Integer> payTypeList = null;
        Set<String> paymentChannelSet = null;

        // 转换数据
        List<Integer> originalPayTypeList = paymentActivity.getPayTypeList();
        if (!CollectionUtils.isEmpty(originalPayTypeList)) {
            paymentChannelSet = new HashSet<String>();
            for (Integer paymentChannel : originalPayTypeList) {
                if (paymentChannel == null || VipTpConstant.PAYMENT_CHANNEL_ALIPAY == paymentChannel
                        || VipTpConstant.PAYMENT_CHANNEL_WEIXIN == paymentChannel) {
                    // 如果是boss后台配置的自有版支付宝或者微信，过滤掉
                    continue;
                }
                // 其他支付渠道直接添加
                paymentChannelSet.add(String.valueOf(paymentChannel));
                if (paymentChannel != null
                        && (VipTpConstant.PAYMENT_CHANNEL_CIBN_ALI == paymentChannel || VipTpConstant.PAYMENT_CHANNEL_CIBN_WEIXIN == paymentChannel)) {
                    // 如果是国广环境中的支付宝或者微信，添加对应自由版的，注意，不是替换
                    paymentChannelSet.add(VipTpConstantUtils.getPaymentChannelFromCibnToLetv(paymentChannel));
                }
            }
        }

        // 整理数据
        if (!CollectionUtils.isEmpty(paymentChannelSet)) {
            payTypeList = new ArrayList<Integer>();
            for (String paymentChannelStr : paymentChannelSet) {
                Integer paymentChannel = StringUtil.toInteger(paymentChannelStr);
                if (paymentChannel != null) {
                    payTypeList.add(paymentChannel);
                }
            }
        }

        return payTypeList;
    }
}

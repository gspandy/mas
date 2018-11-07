package com.letv.mas.caller.iptv.tvproxy.vip.service;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.*;
import com.letv.mas.caller.iptv.tvproxy.common.constant.MmsTpConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.LetvUserVipCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.ActivityTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response.ActivityBatchTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response.ActivityData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response.AlbumDetailTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.DeviceBindConentV2;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.ButtonDto;
import com.letv.mas.caller.iptv.tvproxy.vip.util.VipUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by imom0 on 9/3/2017.
 */

@Service("vipV2Service")
@SuppressWarnings("all")
public class VipV2Service extends BaseService {

    @Autowired
    AlbumVideoAccess albumVideoAccess;

    private final static Logger LOG = LoggerFactory.getLogger(VipV2Service.class);
    protected static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // allow empty string parse to null object
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    public Response<FreeVipDto> getFreeVipV2(String iskonka, String imei, CommonParam commonParam) {
        Response<FreeVipDto> response = new Response<FreeVipDto>();
        String errorCode = null;

        boolean iskonkaFlag = VipConstants.ISKONKA_YES.equals(iskonka); // 是否是康佳电视,"0"--不是，"1"是
        String serialNumber = iskonkaFlag ? imei : commonParam.getMac(); // 只在iskonka为"1"时才进行处理

        // 领取会员试用
        Integer productType = CommonConstants.PRODUCT_TYPE_SERVICE_PACKAGE;
        String productId = null;
        Integer channel = null;// 领取免费会员的渠道号，不同类型的免费会员对应不同的渠道
        String signKey = null;// 领取免费会员的签名key，不同类型的免费会员对应不同的签名key
        if (iskonkaFlag) {
            // 新接口的康佳免费会员渠道号
            channel = ApplicationUtils.getInt(ApplicationConstants.FREE_VIP_CHANNEL_KONKA);
            // 新接口的康佳免费会员签名key
            signKey = ApplicationUtils.get(ApplicationConstants.FREE_VIP_SIGN_KEY_KONKA);
            productId = String.valueOf(VipTpConstant.ORDER_TYPE_44);
        } else {
            // 新接口的7天免费会员渠道号
            channel = ApplicationUtils.getInt(ApplicationConstants.FREE_VIP_CHANNEL_SEVEN_DAY);
            // 新接口的康佳免费会员签名key
            signKey = ApplicationUtils.get(ApplicationConstants.FREE_VIP_SIGN_KEY_SEVEN_DAY);
            productId = String.valueOf(VipTpConstant.ORDER_TYPE_40);
        }
        // invoke boss interface to receive free vip
        GetFreeVipV2TpResponse getFreeVipV2TpResponse = this.facadeTpDao.getVipTpDao().getFreeVipV2(channel, signKey,
                commonParam);
        if (getFreeVipV2TpResponse != null && "0".equals(getFreeVipV2TpResponse.getCode())
                && getFreeVipV2TpResponse.getData() != null && getFreeVipV2TpResponse.getData().getOrderid() != null) {// 成功

            // 获取用户到期时间
            FreeVipDto dto = new FreeVipDto();
            dto.setOrderId(getFreeVipV2TpResponse.getData().getOrderid());
            VipAccountTpResponse accountTpResponse = this.facadeTpDao.getVipTpDao().getVipAccount(commonParam);
            if (accountTpResponse != null) {
                dto.setCancelTime(String.valueOf(accountTpResponse.getSeniorendtime()));
                dto.setVipEndTime(TimeUtil.getDateStringFromLong(accountTpResponse.getSeniorendtime(),
                        TimeUtil.SHORT_DATE_FORMAT));
            }

            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(dto);
        } else {
            errorCode = ErrorCodeConstants.USER_FREE_VIP_GET_FAILURE;
            LOG.info("getFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: get free VIP failed.");
        }

        if (errorCode != null) {// 最后的失败处理
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            response.setResultStatus(1);
        }
        return response;
    }

    public Response<List<VoucherDto>> getUserVoucherV2(Integer type, String status, Integer page, Integer pageSize,
                                                       CommonParam commonParam) {
        String errorCode = null;
        List<VoucherDto> dataList = new ArrayList<VoucherDto>();
        if (StringUtil.isNotBlank(commonParam.getUserId())) {
            // 调BOSS接口获取代金券信息
            GetUserVoucherV2TpResponse tpResponse = this.facadeTpDao.getVipTpDao().getUserVoucherV2(status, page,
                    pageSize, commonParam);
            if (tpResponse != null && !CollectionUtils.isEmpty(tpResponse.getData().getRows())) {
                List<GetUserVoucherV2TpResponse.Data.Coupon> coupons = tpResponse.getData().getRows();
                Calendar now = Calendar.getInstance();
                String currentTime = TimeUtil.getDateString(now, TimeUtil.SIMPLE_DATE_FORMAT);
                for (GetUserVoucherV2TpResponse.Data.Coupon coupon : coupons) {
                    if (currentTime.compareTo(coupon.getValidEndTime()) >= 0) {
                        continue;// filter the expired voucher
                    }
                    VoucherDto voucherDto = new VoucherDto();
                    voucherDto.setCouponCode(coupon.getCode());
                    if (coupon.getValidStartTime() != null) {
                        voucherDto.setStartTime(TimeUtil.parseDate2Timestamp(coupon.getValidStartTime(),
                                TimeUtil.SIMPLE_DATE_FORMAT));
                    }
                    if (coupon.getValidEndTime() != null) {
                        voucherDto.setEndTime(TimeUtil.parseDate2Timestamp(coupon.getValidEndTime(),
                                TimeUtil.SIMPLE_DATE_FORMAT));
                    }
                    voucherDto.setAmount4Yuan(coupon.getAmount());
                    voucherDto.setName(coupon.getName());
                    // TODO: V2接口中未提供 productIds 和 templateId 数据相关的字段
                    // voucherDto.setProductIds(productIds);
                    // voucherDto.setTemplateId(voucher.getTemplateId());
                    dataList.add(voucherDto);
                }
            }
        }

        Response<List<VoucherDto>> response = new Response<List<VoucherDto>>();
        if (errorCode == null) {
            response.setData(dataList);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }
        return response;
    }

    /**
     * get bind info of machine and the user
     * @param type
     * @param deviceType
     * @param commonParam
     * @return
     */
    public Response<DeviceBindDto> getDeviceBindInfo(Integer type, Integer deviceType, CommonParam commonParam) {
        DeviceBindDto data = new DeviceBindDto();
        String errorCode = null;
        String logPrefix = "getDeviceBindInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + commonParam.getDeviceKey();
        Integer[] types = { VipConstants.DEVICE_BIND_QUERY_INT_TYPE_0, VipConstants.DEVICE_BIND_QUERY_INT_TYPE_1,
                VipConstants.DEVICE_BIND_QUERY_INT_TYPE_2 };
        List<Integer> typeList = Arrays.asList(types);
        Integer[] deviceTypes = { VipTpConstant.DEVICE_BIND_DEVICE_TYPE_TV,
                VipTpConstant.DEVICE_BIND_DEVICE_TYPE_MOBILE, VipTpConstant.DEVICE_BIND_DEVICE_TYPE_LETV_BOX };
        List<Integer> deviceTypeList = Arrays.asList(deviceTypes);
        if (!deviceTypeList.contains(deviceType)
                || !typeList.contains(type)
                || ((TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication()) || TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI
                        .equalsIgnoreCase(commonParam.getTerminalApplication())) && VipConstants.DEVICE_BIND_QUERY_INT_TYPE_1 != type)) {// 参数校验
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]: illegal parameter.");
        } else {
            if (VipConstants.DEVICE_BIND_QUERY_INT_TYPE_0 == type) {// 查询自带和赠送机卡
                // get bind info of current machine
                errorCode = this.getDeviceInfo(data, deviceType, errorCode, logPrefix, commonParam);
                if (errorCode == null) {
                    // get bind info of current user
                    errorCode = this.getPresentDeviceInfo(data, errorCode, commonParam);
                }
            } else if (VipConstants.DEVICE_BIND_QUERY_INT_TYPE_1 == type) {// 查询自带机卡
                // get bind info of current machine
                errorCode = this.getDeviceInfo(data, deviceType, errorCode, logPrefix, commonParam);
            } else if (VipConstants.DEVICE_BIND_QUERY_INT_TYPE_2 == type) {// 查询赠送
                // get bind info of current user
                errorCode = this.getPresentDeviceInfo(data, errorCode, commonParam);
            }
        }

        Response<DeviceBindDto> response = new Response<DeviceBindDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            // 设置显示优先级，应该从配置文件中读取
            if (data.getBindMonths() != null) {
                data.setPriority(VipConstants.DEVICE_BIND_QUERY_TYPE_1);
            } else {
                data.setPriority(VipConstants.DEVICE_BIND_QUERY_TYPE_2);
            }
            response.setData(data);
        }

        return response;
    }

    // private String getDeviceInfo(DeviceBindDto data, Integer deviceType,
    // String errorCode, String logPrefix,
    // CommonParam commonParam) {
    // if (deviceType != null &&
    // VipTpConstantUtils.DEVICE_BIND_LETV_APPLICATION_TYPE.getDeviceTypeApplicationById(deviceType)
    // == null) {
    // errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
    // LOG.info(logPrefix + "[errorCode=" + errorCode +
    // "]: illegal parameter.");
    // } else {
    // BossTpResponse<DeviceBindConentV2> deviceBindResponse =
    // this.facadeTpDao.getVipTpDao().getDeviceBindInfoV2(
    // deviceType, commonParam);
    // if (deviceBindResponse == null || !deviceBindResponse.isSucceed()
    // || CollectionUtils.isEmpty(deviceBindResponse.getData().getItems())) {
    // // 调用接口失败（异常或返回失败码）
    // errorCode = ErrorCodeConstants.PAY_GET_DEVICE_BIND_FAILURE;
    // LOG.info(logPrefix + "[errorCode=" + errorCode +
    // "]: get device bind info failed.");
    // } else {
    // List<DeviceBindConentV2.DeviceBindConentItem> items =
    // deviceBindResponse.getData().getItems();
    // Integer bindDuration = null;
    // List<DeviceBind> illegalDevices = new ArrayList<DeviceBind>(); // 不可用数据
    // List<DeviceBind> unBindedDevices = new ArrayList<DeviceBind>(); // 未绑定数据
    // List<DeviceBind> bindedDevices = new ArrayList<DeviceBind>(); // 已绑定数据
    // // List<DeviceBind> deviceBinds = new ArrayList<DeviceBind>();
    // // data.setDeviceBinds(deviceBinds);
    // for (DeviceBindConentV2.DeviceBindConentItem item : items) {
    // if (item != null && item.getStatus() != null) {
    // DeviceBind deviceBind = new DeviceBind();
    // // deviceBinds.add(deviceBind);
    // if (VipTpConstant.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_2 ==
    // item.getStatus()) {
    // bindDuration = item.getBindDuration();
    // if (bindDuration != null) {
    // deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_UNACTIVATED);
    // deviceBind.setBindDuration(bindDuration);
    // deviceBind.setBindDurationUnit(item.getBindDurationUnit());
    // unBindedDevices.add(deviceBind);
    // } else {
    // deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
    // illegalDevices.add(deviceBind);
    // }
    // } else if (VipTpConstant.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_6 ==
    // item.getStatus()) {
    // bindDuration = item.getBindDuration();
    // if (bindDuration != null) {
    // deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ACTIVATED);
    // deviceBind.setBindDuration(bindDuration);
    // deviceBind.setBindDurationUnit(item.getBindDurationUnit());
    // bindedDevices.add(deviceBind);
    // } else {
    // deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
    // illegalDevices.add(deviceBind);
    // }
    // } else {
    // deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
    // illegalDevices.add(deviceBind);
    // }
    // }
    // }
    //
    // // 从解析数据中进行筛选，有可领的优先展示可领，否则看是否有领取过的，最后展示无效数据
    // DeviceBind finalDeviceBind = null;
    // if (unBindedDevices.size() > 0) {
    // finalDeviceBind = unBindedDevices.get(0);
    // } else if (bindedDevices.size() > 0) {
    // finalDeviceBind = bindedDevices.get(0);
    // } else {
    // finalDeviceBind = illegalDevices.get(0);
    // }
    // if (finalDeviceBind != null && finalDeviceBind.getBindDuration() != null)
    // {
    // data.setIsDeviceActive(finalDeviceBind.getIsDeviceActive());
    // data.setBindDuration(finalDeviceBind.getBindDuration());
    // data.setBindDurationUnitType(MessageUtils.getMessage(
    // VipConstants.getBossV2DeciceBindDurationUnitMsgKey(finalDeviceBind.getBindDurationUnit()),
    // commonParam.getLangcode()));
    // } else {
    // data.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
    // }
    // }
    // }
    //
    // return errorCode;
    // }

    private String getDeviceInfo(DeviceBindDto data, Integer deviceType, String errorCode, String logPrefix,
            CommonParam commonParam) {
        if (deviceType != null
                && VipTpConstantUtils.DEVICE_BIND_LETV_APPLICATION_TYPE.getDeviceTypeApplicationById(deviceType) == null) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]: illegal parameter.");
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())
                || TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
            BossTpResponse<DeviceBindConentV2> deviceBindResponse = this.facadeTpDao.getVipTpDao().getDeviceBindInfoV2(
                    deviceType, commonParam);
            if (deviceBindResponse == null || !deviceBindResponse.isSucceed()
                    || CollectionUtils.isEmpty(deviceBindResponse.getData().getItems())) {
                // 调用接口失败（异常或返回失败码）
                errorCode = ErrorCodeConstants.PAY_GET_DEVICE_BIND_FAILURE;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]: get device bind info failed.");
            } else {
                List<DeviceBindConentV2.DeviceBindConentItem> items = deviceBindResponse.getData().getItems();
                Integer bindDuration = null;
                List<DeviceBind> illegalDevices = new ArrayList<DeviceBind>(); // 不可用数据
                List<DeviceBind> unBindedDevices = new ArrayList<DeviceBind>(); // 未绑定数据
                List<DeviceBind> bindedDevices = new ArrayList<DeviceBind>(); // 已绑定数据
                for (DeviceBindConentV2.DeviceBindConentItem item : items) {
                    if (item != null && item.getStatus() != null) {
                        DeviceBind deviceBind = new DeviceBind();
                        if (VipTpConstant.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_2 == item.getStatus()) {
                            bindDuration = item.getBindDuration();
                            if (bindDuration != null) {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_UNACTIVATED);
                                deviceBind.setBindDuration(bindDuration);
                                deviceBind.setBindDurationUnit(item.getBindDurationUnit());
                                unBindedDevices.add(deviceBind);
                            } else {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                                illegalDevices.add(deviceBind);
                            }
                        } else if (VipTpConstant.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_6 == item.getStatus()) {
                            bindDuration = item.getBindDuration();
                            if (bindDuration != null) {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ACTIVATED);
                                deviceBind.setBindDuration(bindDuration);
                                deviceBind.setBindDurationUnit(item.getBindDurationUnit());
                                bindedDevices.add(deviceBind);
                            } else {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                                illegalDevices.add(deviceBind);
                            }
                        } else {
                            deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                            illegalDevices.add(deviceBind);
                        }
                    }
                }

                // 从解析数据中进行筛选，有可领的优先展示可领，否则看是否有领取过的，最后展示无效数据
                DeviceBind finalDeviceBind = null;
                if (unBindedDevices.size() > 0) {
                    finalDeviceBind = unBindedDevices.get(0);
                } else if (bindedDevices.size() > 0) {
                    finalDeviceBind = bindedDevices.get(0);
                } else {
                    finalDeviceBind = illegalDevices.get(0);
                }
                if (finalDeviceBind != null && finalDeviceBind.getBindDuration() != null) {
                    data.setIsDeviceActive(finalDeviceBind.getIsDeviceActive());
                    data.setBindDuration(finalDeviceBind.getBindDuration());
                    data.setBindDurationUnitType(MessageUtils.getMessage(
                            VipConstants.getBossV2DeciceBindDurationUnitMsgKey(finalDeviceBind.getBindDurationUnit()),
                            commonParam.getLangcode()));
                } else {
                    data.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                }
            }
        } else {
            BossTpResponse<DeviceBindConentV2> deviceBindResponse = this.facadeTpDao.getVipTpDao().getDeviceBindInfoV2(
                    deviceType, commonParam);
            if (deviceBindResponse == null || !deviceBindResponse.isSucceed()
                    || CollectionUtils.isEmpty(deviceBindResponse.getData().getItems())) {
                // 调用接口失败（异常或返回失败码）
                errorCode = ErrorCodeConstants.PAY_GET_DEVICE_BIND_FAILURE;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]: get device bind info failed.");
            } else {
                List<DeviceBindConentV2.DeviceBindConentItem> items = deviceBindResponse.getData().getItems();
                Integer bindDuration = null;
                List<DeviceBind> illegalDevices = new ArrayList<DeviceBind>(); // 不可用数据
                List<DeviceBind> unBindedDevices = new ArrayList<DeviceBind>(); // 未绑定数据
                List<DeviceBind> bindedDevices = new ArrayList<DeviceBind>(); // 已绑定数据
                List<DeviceBind> deviceBinds = new ArrayList<DeviceBind>();
                data.setDeviceBinds(deviceBinds);
                for (DeviceBindConentV2.DeviceBindConentItem item : items) {
                    if (item != null && item.getStatus() != null) {
                        if (item.getTypeGroup() == null || item.getTypeGroup() != VipConstants.VIDEO_VIP_TYPE) {
                            continue;
                        }
                        DeviceBind deviceBind = new DeviceBind();
                        deviceBinds.add(deviceBind);
                        if (VipTpConstant.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_2 == item.getStatus()
                                || VipTpConstant.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_6 == item.getStatus()) {
                            deviceBind.setVipType(2);// 超级影视里都是大屏
                            deviceBind.setVipTypeName(item.getProductName());
                            Integer unit = item.getBindDurationUnit();
                            deviceBind.setBindDurationUnit(unit);
                            bindDuration = item.getBindDuration();
                            if (unit == VipConstants.DURATION_TYPE_YEAR) {
                                deviceBind.setBindMonths(bindDuration * 12);
                            } else if (unit == VipConstants.DURATION_TYPE_DAY) {
                                Integer bindMonth = VipUtil.parseDayToMonth(bindDuration, 31);
                                deviceBind.setBindMonths(bindMonth);
                            } else {
                                deviceBind.setBindMonths(bindDuration);
                            }
                            deviceBind.setBindDuration(bindDuration);
                            deviceBind.setBindDurationUnit(item.getBindDurationUnit());
                            if (item.getVipEndTime() != null) {
                                deviceBind.setVipEndTime(item.getVipEndTime() + "");
                            }
                        }

                        if (VipTpConstant.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_2 == item.getStatus()) {
                            if (bindDuration != null) {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_UNACTIVATED);
                                unBindedDevices.add(deviceBind);
                            } else {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                                illegalDevices.add(deviceBind);
                            }
                        } else if (VipTpConstant.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_6 == item.getStatus()) {
                            if (bindDuration != null) {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ACTIVATED);
                                bindedDevices.add(deviceBind);
                            } else {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                                illegalDevices.add(deviceBind);
                            }
                        } else {
                            deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                            illegalDevices.add(deviceBind);
                        }
                    }
                }

                // 从解析数据中进行筛选，有可领的优先展示可领，否则看是否有领取过的，最后展示无效数据
                DeviceBind finalDeviceBind = null;
                if (unBindedDevices.size() > 0) {
                    finalDeviceBind = unBindedDevices.get(0);
                } else if (bindedDevices.size() > 0) {
                    finalDeviceBind = bindedDevices.get(0);
                } else {
                    finalDeviceBind = illegalDevices.get(0);
                }
                if (finalDeviceBind != null && finalDeviceBind.getBindDuration() != null) {
                    data.setIsDeviceActive(finalDeviceBind.getIsDeviceActive());
                    // data.setBindDuration(finalDeviceBind.getBindDuration());
                    // String langcode = commonParam.getLangcode();
                    // data.setBindDurationUnitType(MessageUtils.getMessage(
                    // VipConstants.getBossV2DeciceBindDurationUnitMsgKey(finalDeviceBind.getBindDurationUnit()),
                    // langcode));
                    data.setBindMonths(finalDeviceBind.getBindMonths());
                } else {
                    data.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                }
            }
        }
        return errorCode;
    }

    /**
     * get bind info by user info
     * @param data
     * @param errorCode
     * @param commonParam
     * @return
     */
    private String getPresentDeviceInfo(DeviceBindDto data, String errorCode, CommonParam commonParam) {
        if (commonParam.getDeviceKey() == null || StringUtils.isBlank(commonParam.getMac())
                || StringUtils.isBlank(commonParam.getUserId())) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info("getPresentDeviceInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: illegal parameters.");
        } else {
            GetPresentDeviceBindTpResponse tpResponse = this.facadeTpDao.getVipTpDao()
                    .getPresentDeviceBind(commonParam);
            if (tpResponse == null || !tpResponse.isSucceed()) {
                errorCode = ErrorCodeConstants.PAY_GET_PRESENT_DEVICE_BIND_FAILURE;
                LOG.info("getPresentDeviceInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "[errorCode=" + errorCode
                        + "]: get present device bind failure.");
            } else {
                List<GetPresentDeviceBindTpResponse.PresentDeviceBindTpResponse> presentList = tpResponse.getValues()
                        .getPresentList();
                List<PresentDeviceBindDto> presentDeviceBinds = new LinkedList<PresentDeviceBindDto>();
                if (!CollectionUtils.isEmpty(presentList)) {
                    for (GetPresentDeviceBindTpResponse.PresentDeviceBindTpResponse presentDeviceBindTp : presentList) {
                        if (presentDeviceBindTp != null && presentDeviceBindTp.isAvailable()) {
                            Integer vipType = presentDeviceBindTp.getVipType();
                            if (vipType == null || vipType != 2) { // 过滤掉不是超级影视会员的类型(乐次元和体育会员)
                                continue;
                            }
                            PresentDeviceBindDto dto = new PresentDeviceBindDto();
                            // if (vipType == 2) {
                            dto.setTitle(MessageUtils.getMessage(VipConstants.VIP_RECEIVE_PRESENTDEVICE_TITLE_KEY,
                                    commonParam.getLangcode()));
                            // } else {
                            // dto.setTitle("领取乐视超级体育会员");
                            // }
                            dto.setId(presentDeviceBindTp.getId());
                            dto.setActiveTime(TimeUtil.getDateStringFromLong(presentDeviceBindTp.getCreateTime(),
                                    TimeUtil.SHORT_DATE_FORMAT));
                            dto.setAvailableTime(String.valueOf(presentDeviceBindTp.getPresentDuration()));
                            dto.setPresentProductName(MessageUtils.getMessage(
                                    VipConstants.VIP_PRESENTDEVICE_SUPER_MOBILE_TEXT_KEY, commonParam.getLangcode()));
                            presentDeviceBinds.add(dto);
                        }
                    }
                }
                data.setPresentDeviceBinds(presentDeviceBinds);
            }
        }
        return errorCode;
    }

    /**
     * 推广位接观星
     * @param pos
     * @param commonParam
     * @return
     */
    public List<PromotionDto> getStarGazerDataForPromotion(String pos, CommonParam commonParam) {
        String posStr = "";
        String direct = null;
        if (StringUtil.isNotBlank(pos)) {
            posStr = parseActivityPos(pos, commonParam);
        } else if (commonParam.getTerminalApplication() != null
                && commonParam.getTerminalApplication().equals("child_cibn")) { // 如果是儿童请求
            posStr = ActivityTpConstant.getAllKidPosition(commonParam);
        } else {
            posStr = ActivityTpConstant.getAllPosition(commonParam);
        }
        if (StringUtil.isBlank(posStr)) {
            return null;
        }
        if (commonParam != null) {
            if (StringUtil.isNotBlank(commonParam.getUserId())) {
                direct = ActivityTpConstant.ACTIVITY_LOGIN_DIRECT;
            } else {
                direct = ActivityTpConstant.ACTIVITY_NOLOGIN_DIRECT;
            }
        }
        List<PromotionDto> promotions = this.getStarGazerData(direct, posStr, commonParam, false);
        /*
         * 判断是否请求代理层打底数据（整体非10000，部分非10000）
         */
        boolean isRequestDefault = false;
        if (promotions == null) {
            isRequestDefault = true;
        } else {
            for (PromotionDto mPromotionDto : promotions) {
                if (mPromotionDto != null && mPromotionDto.getErrno() != null
                        && !mPromotionDto.getErrno().equals("10000")) {
                    isRequestDefault = true;
                    break;
                }
            }
        }
        if (isRequestDefault) {
            List<PromotionDto> temp = getStarGazerDefaultData(direct, posStr, commonParam);
            if (temp != null) {
                if (promotions == null) {
                    promotions = temp;
                    LOG.info("getStarGazerDataForPromotion_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + "_" + commonParam.getDeviceKey() + " pos: " + posStr + " form guanxing all error ");
                } else {
                    Map<String, PromotionDto> tempMap = new HashMap<String, PromotionDto>();
                    for (PromotionDto mPromotionDto : temp) {
                        if (mPromotionDto != null) {
                            tempMap.put(mPromotionDto.getPosition(), mPromotionDto);
                        }
                    }
                    int size = promotions.size();
                    String err_pos = "";
                    for (int t = 0; t < size; t++) {
                        PromotionDto mPromotionDto = promotions.get(t);
                        if (mPromotionDto != null && mPromotionDto.getPosition() != null
                                && mPromotionDto.getErrno() != null && !mPromotionDto.getErrno().equals("10000")) {
                            PromotionDto tempPromotionDto = tempMap.get(mPromotionDto.getPosition());
                            if (tempPromotionDto != null) {
                                tempPromotionDto.setErrno(null);
                                promotions.set(t, tempPromotionDto);
                            }
                            err_pos = err_pos + "_" + mPromotionDto.getPosition() + ":" + mPromotionDto.getErrno();
                        }
                        mPromotionDto.setErrno(null);
                    }
                    LOG.info("getStarGazerDataForPromotion_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + "_" + commonParam.getDeviceKey() + " pos: " + posStr + " form guanxing part error pos:"
                            + err_pos);
                }
            }
        }
        return promotions;
    }

    /**
     * 播放容器里的通知（p_notification）接观星
     * 读详情页的静态接口
     * @param albumId
     * @param videoId
     * @param commonParam
     * @return
     */
    public List<PromotionDto> getStarGazerDataForNotificationFormStatic(String albumId, String videoId,
            CommonParam commonParam) {
        List<PromotionDto> promotionDtoList = new ArrayList<PromotionDto>();
        /*
         * 需要从静态详情页接口中获取拼接观星direct字段，具体见需求
         */
        AlbumDetailTpResponse.AlbumDetail mAlbumDetail = this.getAlbumDetail(albumId, commonParam, videoId);
        if (mAlbumDetail != null && mAlbumDetail.getVideo() != null) {
            int category = 0;
            if (mAlbumDetail.getCategoryId() != null) {
                category = mAlbumDetail.getCategoryId();
            }
            int a_charge = 0;
            if (mAlbumDetail.getCharge() != null && mAlbumDetail.getCharge()) {
                a_charge = 1;
            }
            String v_charge = "0";
            if (mAlbumDetail.getVideo().getIfCharge() != null) {
                v_charge = mAlbumDetail.getVideo().getIfCharge();
            }

            String direct = category + "_" + a_charge + "_" + v_charge;
            String posStr = ActivityTpConstant.ACTIVITY_GUANXING_POSITION_NOTIFICATION;
            promotionDtoList = this.getStarGazerData(direct, posStr, commonParam, false);
            /*
             * 替换观星的X占位符，兼容大小写
             */
            if (promotionDtoList != null) {
                for (PromotionDto temp : promotionDtoList) {
                    if (temp != null && temp.getPosition() != null
                            && temp.getPosition().equals(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION)) {
                        String title = temp.getTitle();
                        if (StringUtil.isNotBlank(title) && (title.indexOf("X") > -1 || title.indexOf("x") > -1)
                                && StringUtil.isNotBlank(mAlbumDetail.getCharge_episode())) {
                            String str = "集";
                            if (mAlbumDetail.getCategoryId() == 11) {// 娱乐频道按期定义，其余都按集
                                str = "期";
                            }
                            title = title.replace("X", mAlbumDetail.getCharge_episode() + str);
                            title = title.replace("x", mAlbumDetail.getCharge_episode() + str);
                        }
                        temp.setTitle(title);
                        break;
                    }
                }
            }
        }
        return promotionDtoList;
    }

    /**
     * 播放容器里的通知（p_notification）接观星
     * @param albumId
     * @param videoId
     * @param commonParam
     * @return
     */
    public List<PromotionDto> getStarGazerDataForNotification(String albumId, String videoId,
            String notificationPositionStr, CommonParam commonParam) {

        String gNotificationPositionStr = ActivityTpConstant.getPosidByPosition(notificationPositionStr, commonParam);
        if (StringUtil.isNotBlank(gNotificationPositionStr)) {
            return this.getStarGazerDataForNotificationFormDB(albumId, videoId, gNotificationPositionStr, commonParam);
        }
        return null;
    }

    /**
     * 播放容器里的通知（p_notification）接观星
     * 读数据库
     * @param albumId
     * @param videoId
     * @param commonParam
     * @return
     */
    public List<PromotionDto> getStarGazerDataForNotificationFormDB(String albumId, String videoId,
            String notificationPositionStr, CommonParam commonParam) {
        List<PromotionDto> promotionDtoList = new ArrayList<PromotionDto>();
        /*
         * 需要从数据库中获取拼接观星direct字段，具体见需求
         */
        Long pid = StringUtil.toLong(albumId, null);
        if (pid == null) {
            return null;
        }
        Long vid = StringUtil.toLong(videoId, null);
        if (vid == null) {
            return null;
        }
        Integer p_type = null;
        if (null != commonParam && null != commonParam.getP_devType()) {
            p_type = commonParam.getP_devType();
        }
        /*
         * 查询当前专辑
         */
        AlbumMysqlTable albumMysql = albumVideoAccess.getAlbum(pid, commonParam);
        if (albumMysql == null) {
            return null;
        }
        int category = 0;
        int a_charge = 0;
        int v_charge = 0;
        int n_v_porder = -1;
        String n_v_episode = null;
        Boolean n_v_charge = null;
        String a_payPlatform = albumMysql.getPay_platform();
        String a_playPlatform = albumMysql.getPlay_platform();
        if (VideoCommonUtil.isCharge(a_payPlatform, a_playPlatform, commonParam.getP_devType())) {
            a_charge = 1;
        }
        Integer mCategory = albumMysql.getCategory();
        if (mCategory == null) {
            return null;
        }
        category = mCategory.intValue();
        /*
         * 如果专辑付费，则查询视频是否付费(专辑免费，视频肯定免费)
         */
        if (a_charge == 1) {
            VideoMysqlTable videoMysql = albumVideoAccess.getVideo(vid, commonParam);
            if (videoMysql == null) {
                return null;
            }
            String v_payPlatform = videoMysql.getPay_platform();
            String v_playPlatform = videoMysql.getPlay_platform();
            if (VideoCommonUtil.isCharge(v_payPlatform, v_playPlatform, commonParam.getP_devType())) {
                v_charge = 1;
            }
            Integer porder = videoMysql.getPorder();
            if (v_charge == 0 && porder != null) {// 得到当前视频的下一集集数
                // 综艺那种的porder为日期，因此推算不出下一集，所以只依赖热备里的收费剧集
                if (porder < 100000) {
                    n_v_porder = porder.intValue() + 1;
                } else {
                    n_v_charge = false;
                }
                Map video_porder_charge_map = albumMysql.getVideo_porder_charge();
                if (video_porder_charge_map != null && video_porder_charge_map.size() > 0) {
                    String video_porder_charge = (String) video_porder_charge_map.get(p_type);
                    // FIX 处理如果取不到的情况
                    if (StringUtil.isNotBlank(video_porder_charge)) {
                        String[] arr = video_porder_charge.split("_");
                        if (arr != null && arr.length == 3) {
                            Integer video_pro = StringUtil.toInteger(arr[0]);
                            Integer charge = StringUtil.toInteger(arr[1]);
                            String temp_n_v_episode = arr[2];
                            if (video_pro != null && charge != null) {
                                if (porder < 100000) {
                                    if (charge == 0) {
                                        if (n_v_porder <= video_pro) {
                                            n_v_charge = false;
                                        }
                                    } else {
                                        if (n_v_porder < video_pro) {
                                            n_v_charge = false;
                                        } else {
                                            n_v_charge = true;
                                            n_v_episode = temp_n_v_episode;
                                        }
                                    }
                                } else {
                                    // 综艺那种的porder为日期，只依赖热备里的收费剧集
                                    if (charge == 1) {
                                        if (porder < video_pro) {
                                            Calendar begin = TimeUtil.parseCalendar(porder + "",
                                                    TimeUtil.SHORT_DATE_FORMAT_NO_DASH);
                                            Calendar end = TimeUtil.parseCalendar(video_pro + "",
                                                    TimeUtil.SHORT_DATE_FORMAT_NO_DASH);
                                            if (begin != null && end != null) {
                                                int between = TimeUtil.daysBetween(begin, end);
                                                if (between <= 31) {
                                                    n_v_charge = true;
                                                    n_v_episode = temp_n_v_episode;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String direct = category + "_" + a_charge + "_" + v_charge;
        // String posStr =
        // ActivityTpConstant.ACTIVITY_GUANXING_POSITION_NOTIFICATION;
        promotionDtoList = this.getStarGazerData(direct, notificationPositionStr, commonParam, false);
        /*
         * 如果观星的title中含有X占位符，则说明要在免费视频中提示付费集数，如果下一集付费则本集提示
         */
        if (promotionDtoList != null && promotionDtoList.size() == 1) {
            PromotionDto temp = promotionDtoList.get(0);
            if (temp != null) {
                if (temp.getPosition() != null
                        && (temp.getPosition().equals(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION) || temp
                                .getPosition().equals(ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION))) {
                    String title = temp.getTitle();
                    if (a_charge == 1 && v_charge == 0 && StringUtil.isNotBlank(title)
                            && (title.indexOf("X") > -1 || title.indexOf("x") > -1)) {
                        String str = "集";
                        if (category == 11) {// 娱乐频道按期定义，其余都按集
                            str = "期";
                        }
                        // 查询专辑中第几集开始付费
                        /*
                         * String playPlayForm =
                         * MmsDataUtil.getPlayPlatform(commonParam
                         * .getP_devType());
                         * String payPlayForm =
                         * MmsDataUtil.getPayPlatform(commonParam
                         * .getP_devType());
                         * String v_type =
                         * MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN+"";
                         * VideoMysqlTable videoMysql =
                         * this.facadeService.getAlbumVideoAccess
                         * ().getMinChargeVideoByPid(pid,
                         * v_type,payPlayForm,playPlayForm, commonParam);
                         * if(videoMysql != null && videoMysql.getEpisode() !=
                         * null){
                         * title = title.replace("X", videoMysql.getEpisode() +
                         * str);
                         * title = title.replace("x", videoMysql.getEpisode() +
                         * str);
                         * }
                         */
                        boolean isShowNotification = false;
                        // 查询当前视频的下一集
                        int v_type = MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN;
                        if (n_v_charge == null) {
                            if (n_v_porder > -1) {
                                VideoMysqlTable videoMysql = albumVideoAccess
                                        .getVideoByPidAndPorder(pid, v_type, n_v_porder, commonParam);
                                if (videoMysql != null) {
                                    String payPlatform = videoMysql.getPay_platform();
                                    String playPlatform = videoMysql.getPlay_platform();
                                    String episode = videoMysql.getEpisode();
                                    int is_charge = VideoCommonUtil.isCharge(payPlatform, playPlatform,
                                            commonParam.getP_devType()) ? 1 : 0;
                                    Map<Integer, String> maps = albumMysql.getVideo_porder_charge();
                                    if (maps == null) {
                                        maps = new HashMap<>();
                                    }
                                    maps.put(p_type, n_v_porder + "_" + is_charge + "_" + episode);
                                    albumMysql.setVideo_porder_charge(maps);

                                    this.facadeCacheDao.getVideoCacheDao().setAlbum(pid, null, albumMysql);
                                    /*
                                     * 当下一集付费时替换X中内容，并显示，否则不显示
                                     */
                                    if (StringUtil.isNotBlank(episode) && is_charge == 1) {
                                        title = title.replace("X", episode + str);
                                        title = title.replace("x", episode + str);
                                        temp.setTitle(title);
                                        isShowNotification = true;
                                    }
                                }
                            }
                        } else if (n_v_charge && n_v_episode != null) {
                            title = title.replace("X", n_v_episode + str);
                            title = title.replace("x", n_v_episode + str);
                            temp.setTitle(title);
                            isShowNotification = true;
                        }

                        if (!isShowNotification) {
                            promotionDtoList.remove(0);
                        }
                    }
                }
            }
        }
        return promotionDtoList;
    }

    /**
     * 获取观星数据
     * @param direct
     * @param posStr
     * @param commonParam
     * @return
     */
    public List<PromotionDto> getStarGazerData(String direct, String posStr, CommonParam commonParam, boolean isUseCache) {
        List<PromotionDto> promotions = null;
        ActivityBatchTpResponse tpResponse = null;
        if (isUseCache) {
            tpResponse = this.facadeCacheDao.getActivityCacheDao().getActivity(direct, posStr, commonParam);
        }
        if (tpResponse == null || !tpResponse.isSuccess()) {
            tpResponse = this.facadeTpDao.getActivityTpDao().getActivity(direct, posStr, commonParam);
            if (tpResponse == null || !tpResponse.isNetSuccess()) {
                LOG.info("getStarGazerData_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "form guanxing error data");
            } else {
                if (isUseCache && tpResponse.isSuccess()) {
                    this.facadeCacheDao.getActivityCacheDao().setActivity(direct, posStr, commonParam, tpResponse);
                }
            }
        }
        if (tpResponse != null && tpResponse.isNetSuccess()) {
            // promotions = parseTpResponse(tpResponse, commonParam);
            promotions = parseTpResponse(tpResponse, commonParam, posStr);
        } else {
            LOG.info("getStarGazerData_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + " result error !!!");
        }
        return promotions;
    }

    public List<PromotionDto> tgetStarGazerData(String direct, String posStr, CommonParam commonParam, boolean fals) {
        List<PromotionDto> mPromotionDtos = new ArrayList<PromotionDto>();
        PromotionDto mPromotionDto = new PromotionDto();
        mPromotionDto.setTitle("ssssDXcccc");// ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION
        mPromotionDto.setPosition(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION);
        mPromotionDtos.add(mPromotionDto);
        return mPromotionDtos;
    }

    /**
     * 获取代理层打底数据
     * @param direct
     * @param posStr
     * @param commonParam
     * @return
     */
    public List<PromotionDto> getStarGazerDefaultData(String direct, String posStr, CommonParam commonParam) {
        List<PromotionDto> promotions = null;
        ActivityBatchTpResponse tpResponse = this.facadeCacheDao.getActivityCacheDao().getActivityDefaultData(direct,
                posStr, commonParam);
        if (tpResponse == null || !tpResponse.isSuccess()) {
            tpResponse = this.facadeTpDao.getActivityTpDao().getActivityDefaultData(direct, posStr, commonParam);
            if (tpResponse != null && tpResponse.isSuccess()) {
                this.facadeCacheDao.getActivityCacheDao().setActivityDefaultData(direct, posStr, commonParam,
                        tpResponse);
            } else {
                LOG.info("getStarGazerDefaultData_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "form static no data or error data");
            }
        }
        if (tpResponse != null && tpResponse.isSuccess()) {
            // promotions = parseTpResponse(tpResponse, commonParam);
            promotions = parseTpResponse(tpResponse, commonParam, posStr);
        }
        return promotions;
    }

    /**
     * 解析观星内容
     * @param tpResponse
     * @param commonParam
     * @return
     */
    public List<PromotionDto> parseTpResponse(ActivityBatchTpResponse tpResponse, CommonParam commonParam) {
        List<PromotionDto> promotions = new ArrayList<PromotionDto>();
        if (tpResponse.isSuccess()) {
            for (Map.Entry<String, List<ActivityData>> entry : tpResponse.getData().entrySet()) {
                List<ActivityData> activityList = entry.getValue();
                if (CollectionUtils.isEmpty(activityList)) {
                    continue;// 没有活动数据实体的，不作处理
                }
                String posId = entry.getKey();
                PromotionDto promotionDto = null;
                List<PromotionDto> tempPromotions = new ArrayList<PromotionDto>();
                for (ActivityData activityData : activityList) {
                    if (activityData != null) {
                        PromotionDto tempPromotionDto = parseActivity(posId, activityData, commonParam);
                        if (tempPromotionDto != null) {
                            tempPromotions.add(tempPromotionDto);
                            // break;// 一个运营位，解析一个活动数据即可
                        }
                    }
                }
                if (tempPromotions.size() == 1) {
                    promotionDto = tempPromotions.get(0);
                    tempPromotions.clear();
                } else if (tempPromotions.size() > 1) {
                    promotionDto = new PromotionDto();
                    PromotionDto temp_promotionDto = tempPromotions.get(0);
                    if (temp_promotionDto != null) {
                        promotionDto.setPosition(temp_promotionDto.getPosition());
                    }
                    promotionDto.setPromotions(tempPromotions);
                }
                if (promotionDto != null) {
                    promotions.add(promotionDto);
                }
            }
        }
        return promotions;
    }

    public List<PromotionDto> parseTpResponse(ActivityBatchTpResponse tpResponse, CommonParam commonParam, String pos) {
        List<PromotionDto> promotions = new ArrayList<PromotionDto>();
        if (StringUtil.isBlank(pos)) {
            return promotions;
        }
        Set<String> posSet = StringUtil.getStringToSet(pos, ",");
        if (tpResponse.isSuccess()) {
            for (Map.Entry<String, List<ActivityData>> entry : tpResponse.getData().entrySet()) {
                List<ActivityData> activityList = entry.getValue();
                if (CollectionUtils.isEmpty(activityList)) {
                    continue;// 没有活动数据实体的，不作处理
                }
                String posId = entry.getKey();
                if (StringUtil.isBlank(posId) || !posSet.contains(posId)) {
                    continue;// 请求位置不包含的，不作处理
                }
                PromotionDto promotionDto = null;
                List<PromotionDto> tempPromotions = new ArrayList<PromotionDto>();
                for (ActivityData activityData : activityList) {
                    if (activityData != null) {
                        PromotionDto tempPromotionDto = parseActivity(posId, activityData, commonParam);
                        if (tempPromotionDto != null) {
                            tempPromotions.add(tempPromotionDto);
                            // break;// 一个运营位，解析一个活动数据即可
                        }
                    }
                }
                if (tempPromotions.size() == 1) {
                    promotionDto = tempPromotions.get(0);
                    tempPromotions.clear();
                } else if (tempPromotions.size() > 1) {
                    promotionDto = new PromotionDto();
                    PromotionDto temp_promotionDto = tempPromotions.get(0);
                    if (temp_promotionDto != null) {
                        promotionDto.setPosition(temp_promotionDto.getPosition());
                    }
                    promotionDto.setPromotions(tempPromotions);
                }
                if (promotionDto != null) {
                    promotions.add(promotionDto);
                }
            }
        }
        return promotions;
    }

    public AlbumDetailTpResponse.AlbumDetail getAlbumDetail(String album, CommonParam commonParam, String videoId) {
        AlbumDetailTpResponse responsed = this.facadeTpDao.getActivityTpDao().getAlbumDetail(album, commonParam);
        AlbumDetailTpResponse.AlbumDetail mAlbumDetail = null;
        if (responsed != null && responsed.isSuccess()) {
            mAlbumDetail = responsed.getData();
            if (mAlbumDetail != null) {
                String charge_e = null;
                List<AlbumDetailTpResponse.Video> videos = mAlbumDetail.getPositiveSeries();
                if (videos != null) {
                    AlbumDetailTpResponse.Video video = null;
                    int size = videos.size();
                    for (int t = 0; t < size; t++) {
                        video = videos.get(t);
                        if (video != null) {
                            if (video.getIfCharge() != null && video.getIfCharge().equals("1")) {
                                charge_e = video.getEpisode();
                            }
                            if (video.getVideoId() != null && video.getVideoId().equals(videoId)) {
                                mAlbumDetail.setVideo(video);
                            }
                            if (charge_e != null && mAlbumDetail.getVideo() != null) {
                                break;
                            }
                        }
                        video = null;
                    }
                    if (charge_e != null) {
                        mAlbumDetail.setCharge_episode(charge_e);
                    }
                }

            }
        }
        return mAlbumDetail;
    }

    public Map<String, DPilotDto> getStarGazerMapDataForUrm(String direct, String posIds, CommonParam commonParam) {
        Map<String, DPilotDto> pilotDtoMap = null;
        if (StringUtil.isBlank(posIds)) {
            return pilotDtoMap;
        }
        List<DPilotDto> starGazerDatas = getStarGazerListDataForUrm(direct, posIds, commonParam);
        if (starGazerDatas != null && starGazerDatas.size() > 0) {
            pilotDtoMap = new HashMap<String, DPilotDto>();
            for (DPilotDto dto : starGazerDatas) {
                if (dto != null) {
                    pilotDtoMap.put(dto.getPosition(), dto);
                }
            }
        }
        return pilotDtoMap;
    }

    public List<DPilotDto> getStarGazerListDataForUrm(String direct, String posIds, CommonParam commonParam) {
        List<DPilotDto> pilotDtos = null;
        if (StringUtil.isBlank(posIds)) {
            return pilotDtos;
        }
        List<PromotionDto> starGazerDatas = getStarGazerDataForPromotion(posIds,
                commonParam);
        if (starGazerDatas != null && starGazerDatas.size() > 0) {
            pilotDtos = new ArrayList<DPilotDto>();

            for (PromotionDto mPromotionDto : starGazerDatas) {
                if (mPromotionDto != null) {
                    String posId = mPromotionDto.getPosition();
                    if (StringUtil.isNotBlank(posId)) {
                        int temp = posIds.indexOf(posId);
                        if (temp == -1) {
                            continue;
                        }
                    }
                    DPilotDto dto = parseGuanXingActivityData(mPromotionDto,
                            commonParam);
                    pilotDtos.add(dto);
                }
            }
        }
        return pilotDtos;
    }

    /**
     * 解析观星单个Activity
     * @param posId
     * @param activityData
     * @param commonParam
     * @return
     */
    public PromotionDto parseActivity(String posId, ActivityData activityData, CommonParam commonParam) {
        if (activityData != null && activityData.getType() != null
                && ActivityTpConstant.ACTIVITY_TYPE_PROMOTION.equals(activityData.getType())
                && activityData.getPromotion() != null && activityData.getPromotion().getCreative() != null) {
            String position = ActivityTpConstant.getPositionByPosid(posId, commonParam);
            if (position == null) {
                return null;
            }
            PromotionDto promotionDto = new PromotionDto();
            ActivityData.PromotionData promotionData = activityData.getPromotion();
            promotionDto.setPosition(position);
            promotionDto.setActivity_id(promotionData.getPromoid());
            if (activityData.getErrno() != null) {
                promotionDto.setErrno(activityData.getErrno());
            }
            Long timeout = promotionData.getTimeout();
            // 时间戳10位转13位
            if (timeout != null && timeout > -1) {
                timeout = timeout * 1000;
            }
            promotionDto.setActivity_timeout(timeout);
            promotionDto.setReqid(promotionData.getReqid());
            promotionDto.setSessionid(promotionData.getSessionid());
            ActivityData.ActionInfo mActionInfo = promotionData.getCreative();
            if (mActionInfo != null) {
                promotionDto.setCreativeid(mActionInfo.getId());
                // parseActivityShowInfo(promotionDto, mActionInfo);
                parseActivityShowInfoV2(promotionDto, mActionInfo);
                // parseActivityActionInfo(promotionDto, mActionInfo,
                // commonParam);
                parseActivityActionInfoV2(promotionDto, mActionInfo, commonParam);
                parseActivityReportInfo(promotionDto, mActionInfo);
                parseActivityDisplayInfo(promotionDto, mActionInfo);
            }
            return promotionDto;
        }
        return null;
    }

    /**
     * 解析观星接口返回值中的动作类型以及数据
     * @param data
     * @param actionInfo
     */
    /*
     * private void parseActivityActionInfo(PromotionDto data, ActionInfo
     * actionInfo, CommonParam commonParam) {
     * if (data == null || actionInfo == null || actionInfo.getJump() == null) {
     * return;
     * }
     * //模板样式决定所需展示字段
     * String position = data.getPosition();
     * Map<String, String> jump = actionInfo.getJump();
     * JumpData mJumpData = null;
     * if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_TOP.equals(position) ||
     * ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_TOP.equals(position)) {
     * if (jump.containsKey("params")) {
     * String jumpJson = jump.get("params");
     * if (StringUtil.isBlank(jumpJson)) {
     * return;
     * }
     * mJumpData = getJumpData(jumpJson, data, commonParam);
     * if (mJumpData != null && mJumpData.getType() != null) {
     * data.setJump(mJumpData);
     * data.setDataType(mJumpData.getType());
     * List<ButtonDto> buttons = data.getButtons();
     * if (buttons.size() > 0 && buttons.get(0) != null) {
     * buttons.get(0).setDataType(mJumpData.getType());
     * buttons.get(0).setJump(mJumpData);
     * }
     * }
     * }
     * } else if
     * (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM.equals(position)) {
     * List<ButtonDto> buttons = data.getButtons();
     * if (buttons.size() > 0 && buttons.get(0) != null &&
     * jump.containsKey("params_1")) {
     * String jumpJson = jump.get("params_1");
     * if (StringUtil.isBlank(jumpJson)) {
     * return;
     * }
     * mJumpData = getJumpData(jumpJson, data, commonParam);
     * if (mJumpData != null && mJumpData.getType() != null) {
     * buttons.get(0).setDataType(mJumpData.getType());
     * buttons.get(0).setJump(mJumpData);
     * data.setDataType(mJumpData.getType());
     * }
     * }
     * if (buttons.size() > 1 && buttons.get(1) != null &&
     * jump.containsKey("params_2")) {
     * String jumpJson = jump.get("params_2");
     * if (StringUtil.isBlank(jumpJson)) {
     * return;
     * }
     * mJumpData = getJumpData(jumpJson, data, commonParam);
     * if (mJumpData != null && mJumpData.getType() != null) {
     * buttons.get(1).setDataType(mJumpData.getType());
     * buttons.get(1).setJump(mJumpData);
     * }
     * }
     * } else if
     * (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_QUIT.equals(position)) {
     * //还没有确定
     * } else if
     * (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_TOP_TRY.equals(position)
     * ||
     * ActivityTpConstant.ACTIVITY_CLIENT_POSITION_HIGH_STREAM.equals(position)
     * ||
     * ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION.equals(position)
     * ||
     * ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION.equals(position
     * )
     * ||
     * ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_TOP_TRY.equals(position)
     * ) {
     * List<ButtonDto> buttons = data.getButtons();
     * if (buttons.size() > 0 && buttons.get(0) != null &&
     * jump.containsKey("params")) {
     * String jumpJson = jump.get("params");
     * if (StringUtil.isBlank(jumpJson)) {
     * return;
     * }
     * mJumpData = getJumpData(jumpJson, data, commonParam);
     * if (mJumpData != null && mJumpData.getType() != null) {
     * buttons.get(0).setDataType(mJumpData.getType());
     * buttons.get(0).setJump(mJumpData);
     * data.setDataType(mJumpData.getType());
     * }
     * }
     * } else {
     * if (jump.containsKey("params")) {
     * String jumpJson = jump.get("params");
     * if (StringUtil.isBlank(jumpJson)) {
     * return;
     * }
     * mJumpData = getJumpData(jumpJson, data, commonParam);
     * if (mJumpData != null && mJumpData.getType() != null) {
     * data.setJump(mJumpData);
     * data.setDataType(mJumpData.getType());
     * }
     * }
     * }
     * }
     */
    private void parseActivityActionInfoV2(PromotionDto data, ActivityData.ActionInfo actionInfo, CommonParam commonParam) {
        if (data == null || actionInfo == null || actionInfo.getJump() == null) {
            return;
        }
        // 模板样式决定所需展示字段
        String position = data.getPosition();
        Map<String, String> jump = actionInfo.getJump();
        JumpData mJumpData = null;
        if (jump.containsKey("params")) {
            String jumpJson = jump.get("params");
            if (StringUtil.isBlank(jumpJson)) {
                return;
            }
            mJumpData = getJumpData(jumpJson, data, commonParam);

            if (mJumpData != null && mJumpData.getType() != null) {
                data.setJump(mJumpData);
                data.setDataType(mJumpData.getType());
                List<ButtonDto> buttons = data.getButtons();
                if (buttons == null || buttons.isEmpty()) {
                    return;
                }
                if (buttons.size() > 0 && buttons.get(0) != null) {
                    buttons.get(0).setDataType(mJumpData.getType());
                    buttons.get(0).setJump(mJumpData);
                }
            }
        } else {
            List<ButtonDto> buttons = data.getButtons();
            if (buttons == null || buttons.isEmpty()) {
                return;
            }
            if (buttons.size() > 0 && buttons.get(0) != null && jump.containsKey("params_1")) {
                String jumpJson = jump.get("params_1");
                if (StringUtil.isBlank(jumpJson)) {
                    return;
                }
                mJumpData = getJumpData(jumpJson, data, commonParam);
                if (mJumpData != null && mJumpData.getType() != null) {
                    buttons.get(0).setDataType(mJumpData.getType());
                    buttons.get(0).setJump(mJumpData);
                    data.setDataType(mJumpData.getType());
                }
            }
            if (buttons.size() > 1 && buttons.get(1) != null && jump.containsKey("params_2")) {
                String jumpJson = jump.get("params_2");
                if (StringUtil.isBlank(jumpJson)) {
                    return;
                }
                mJumpData = getJumpData(jumpJson, data, commonParam);
                if (mJumpData != null && mJumpData.getType() != null) {
                    buttons.get(1).setDataType(mJumpData.getType());
                    buttons.get(1).setJump(mJumpData);
                }
            }
        }
    }

    public JumpData getJumpData(String jumpJson, PromotionDto data, CommonParam commonParam) {
        JumpData jumpData = new JumpData();
        if (data == null) {
            return jumpData;
        }
        String position = data.getPosition();
        if (StringUtil.isBlank(position)) {
            return jumpData;
        }
        String activityId = data.getActivity_id();
        jumpData = getJumpData(jumpJson, position, activityId, commonParam);
        return jumpData;
    }

    public static JumpData getJumpData(String jumpJson, String position, String activityId, CommonParam commonParam) {

        JumpData jumpData = new JumpData();
        if (StringUtil.isBlank(jumpJson)) {
            return jumpData;
        }
        Map<String, Object> paramMap = JsonUtil.parse(jumpJson, new TypeReference<Map<String, Object>>() {
        });
        if (CollectionUtils.isEmpty(paramMap)) {
            return jumpData;
        }
        // 这个是一级分类，1--h5，21--超级影视
        Integer paramType = StringUtil.toInteger(String.valueOf(paramMap.get("paramType")));
        if (paramType == null) {
            return jumpData;
        }
        if (paramType.equals(ActivityTpConstant.GUANXING_DATA_TYPE_APP)) {
            if (paramMap.get("paramValue") != null) {
                Map<String, String> valueMap = (Map<String, String>) paramMap.get("paramValue");
                GcParams gcParams = JsonUtil.parse(valueMap.get("params"), GcParams.class);
                if (gcParams == null) {
                    return jumpData;
                }
                Extension extend = new Extension();
                extend.setIsParse(0);
                extend.setAction(gcParams.action);
                extend.setResource("1");
                Map<String, String> jumpInfo = gcParams.value;
                if (jumpInfo != null) {
                    // int type = DataConstant.DATA_TYPE_BLANK;
                    Integer type = gcParams.type;
                    if (type == null) {
                        return jumpData;
                    }
                    if (type == DataConstant.DATA_TYPE_CHECKSTAND) {
                        int dataType = DataConstant.DATA_TYPE_CHECKSTAND;
                        PilotDto pilotDto_skip = new PilotDto();
                        pilotDto_skip.setId(activityId);
                        if (jumpInfo.containsKey("activityIds")) {
                            pilotDto_skip.setActivityIds(jumpInfo.get("activityIds"));
                        }
                        pilotDto_skip.setDataType(dataType);
                        pilotDto_skip.setPosition(position);
                        pilotDto_skip.setVendor(ApplicationUtils.get(ApplicationConstants.VIP_OPENECO_VENDER));
                        pilotDto_skip.setId(activityId);
                        jumpData.setValue(pilotDto_skip);
                        jumpData.setType(dataType);
                        jumpData.setExtend(extend);
                        if (StringUtil.isNotBlank(position)) {
                            LOG.error("getCashierIdForNoKnow native cashier_id=" + pilotDto_skip.getActivityIds()
                                    + " pos=" + position + " mac=" + commonParam.getMac() + " uid="
                                    + commonParam.getUserId());
                        }
                    } else if (type == DataConstant.DATA_TYPE_BROWSER) {
                        int dataType = DataConstant.DATA_TYPE_BROWSER;
                        Browser browser_skip = new Browser();
                        jumpData.setType(dataType);
                        if (jumpInfo.containsKey("browserType")) {
                            String browserType = jumpInfo.get("browserType");
                            if (browserType != null) {
                                try {
                                    int brower_type = Integer.parseInt(browserType);
                                    browser_skip.setBrowserType(brower_type);
                                } catch (Exception e) {

                                }
                            }
                        }
                        if (jumpInfo.containsKey("openType")) {
                            String openType = jumpInfo.get("openType");
                            if (openType != null) {
                                int open_type = Integer.parseInt(openType);
                                browser_skip.setOpenType(open_type);
                            }
                        }

                        browser_skip.setId(activityId);
                        // browser_skip.setUrl(data.getCashierUrl());
                        Map<String, String> urlMap = new HashMap<String, String>();
                        if (jumpInfo.get("urlMap") != null) {
                            urlMap.put("0", jumpInfo.get("urlMap"));
                            browser_skip.setUrl(jumpInfo.get("urlMap"));
                        } else if (jumpInfo.get("url") != null) {
                            urlMap.put("0", jumpInfo.get("url"));
                            browser_skip.setUrl(jumpInfo.get("url"));
                        }
                        browser_skip.setUrlMap(urlMap);
                        browser_skip.setDataType(dataType);
                        browser_skip.setPosition(position);
                        browser_skip.setVendor(ApplicationUtils.get(ApplicationConstants.VIP_OPENECO_VENDER));
                        browser_skip.setId(activityId);
                        jumpData.setValue(browser_skip);
                        jumpData.setExtend(extend);
                        if (StringUtil.isNotBlank(position) && StringUtil.isNotBlank(browser_skip.getUrl())
                                && browser_skip.getUrl().indexOf("cashierId=") > 0) {
                            LOG.error("getCashierIdForNoKnow h5 cashier_url=" + browser_skip.getUrl() + " pos="
                                    + position + " mac=" + commonParam.getMac() + " uid=" + commonParam.getUserId());
                        }
                    } else if (type == DataConstant.DATA_TYPE_ALBUM) {
                        AlbumDto album_skip = new AlbumDto();
                        album_skip.setTvCopyright(1);
                        if (jumpInfo.get("albumId") != null) {
                            album_skip.setAlbumId(jumpInfo.get("albumId"));
                        }
                        if (jumpInfo.get("tvCopyright") != null) {
                            try {
                                int tvCopyright = Integer.parseInt(jumpInfo.get("tvCopyright"));
                                album_skip.setTvCopyright(tvCopyright);
                            } catch (Exception e) {

                            }
                        }
                        album_skip.setSrc(1);
                        album_skip.setDataType(type);
                        jumpData.setValue(album_skip);
                        jumpData.setType(type);
                        jumpData.setExtend(extend);
                    } else if (type == DataConstant.DATA_TYPE_SUBJECT) {
                        Subject subject_skip = new Subject();
                        if (jumpInfo.get("subjectId") != null) {
                            subject_skip.setSubjectId(jumpInfo.get("subjectId"));
                        }
                        if (jumpInfo.get("subjectType") != null) {
                            try {
                                int subjectType = Integer.parseInt(jumpInfo.get("subjectType"));
                                subject_skip.setSubjectType(subjectType);
                            } catch (Exception e) {

                            }
                        }
                        subject_skip.setDataType(type);
                        jumpData.setValue(subject_skip);
                        jumpData.setType(type);
                        jumpData.setExtend(extend);
                    } else if (type == DataConstant.DATA_TYPE_VIDEO) {
                        VideoDto video_skip = new VideoDto();
                        video_skip.setAlbumId(jumpInfo.get("albumId"));
                        video_skip.setVideoId(jumpInfo.get("videoId"));
                        video_skip.setName(jumpInfo.get("name"));
                        video_skip.setSubName(jumpInfo.get("subName"));
                        if (jumpInfo.get("categoryId") != null) {
                            try {
                                int categoryId = Integer.parseInt(jumpInfo.get("categoryId"));
                                video_skip.setCategoryId(categoryId);
                            } catch (Exception e) {

                            }
                        }
                        if (jumpInfo.get("tvCopyright") != null) {
                            try {
                                int tvCopyright = Integer.parseInt(jumpInfo.get("tvCopyright"));
                                video_skip.setTvCopyright(tvCopyright);
                            } catch (Exception e) {

                            }
                        } else {
                            video_skip.setTvCopyright(1);
                        }
                        /*
                         * video_skip.setWebPlayUrl("");
                         * video_skip.setWebsite("");
                         */
                        jumpData.setValue(video_skip);
                        jumpData.setType(type);
                        jumpData.setExtend(extend);
                    } else if (type == DataConstant.DATA_TYPE_WF_SUBJECT) {
                        WFSubject page_skip = new WFSubject();
                        if (jumpInfo.get("pageId") != null) {
                            try {
                                int pageId = Integer.parseInt(jumpInfo.get("pageId"));
                                page_skip.setPageId(pageId);
                            } catch (Exception e) {

                            }
                        }
                        page_skip.setDataType(type);
                        jumpData.setValue(page_skip);
                        jumpData.setType(type);
                        jumpData.setExtend(extend);
                    }
                }
            }
        } else {
            jumpData.setType(DataConstant.DATA_TYPE_EXT_APP);
            if (paramMap.get("paramValue") != null) {
                Object paramValue = paramMap.get("paramValue");
                if (paramValue instanceof Map) {
                    Map<String, String> valueMap = (Map<String, String>) paramValue;
                    String params = valueMap.get("params");
                    if (StringUtil.isBlank(params)) {
                        return jumpData;
                    }
                    if (StringUtil.isNotBlank(position)) {
                        LOG.error("getCashierIdForNoKnow other jump =" + params + " pos=" + position + " mac="
                                + commonParam.getMac() + " uid=" + commonParam.getUserId());
                    }
                    GcParams gcParams = JsonUtil.parse(params, GcParams.class);
                    if (gcParams == null) {
                        return jumpData;
                    }
                    Extension extend = new Extension();
                    extend.setIsParse(1);
                    extend.setAction(gcParams.action);
                    extend.setResource("1");
                    if (paramMap.get("jumpDetect") != null) {
                        Object jumpDetectObject = paramMap.get("jumpDetect");
                        if (jumpDetectObject instanceof Map) {
                            Map<String, String> jumpDetect = (Map<String, String>) jumpDetectObject;
                            if (jumpDetect != null) {
                                if (jumpDetect.get("jumpPackage") != null) {
                                    extend.setAppPackageName(jumpDetect.get("jumpPackage"));
                                }
                            }
                        }
                    } else {
                        if (paramType.equals(ActivityTpConstant.GUANXING_DATA_TYPE_LAUNCHER_APP)) {
                            extend.setAppPackageName("com.stv.launcher");
                        }
                    }
                    Map<String, Object> msps = new HashMap<String, Object>();
                    msps.put("type", gcParams.type);
                    msps.put("value", gcParams.value);
                    jumpData.setValue(msps);
                    jumpData.setExtend(extend);
                }
            }
        }

        return jumpData;
    }

    public String parseActivityPos(String posIds, CommonParam commonParam) {
        List<String> posIdList = new LinkedList<String>();
        StringBuffer posSb = new StringBuffer();
        if (StringUtils.isNotBlank(posIds)) {// 先解析要查询的订单类型
            String[] typeArray = posIds.trim().split(",");
            posIdList = Arrays.asList(typeArray);
            for (String pos : posIdList) {
                String tmp = ActivityTpConstant.getPosidByPosition(pos, commonParam);
                if (StringUtil.isNotBlank(tmp)) {
                    posSb.append(tmp);
                    posSb.append(",");
                }
            }
            posIds = posSb.toString();
        }
        return posIds;
    }

    /**
     * 解析观星接口返回值中的展示信息
     * @param data
     * @param actionInfo
     * @param useButton
     */
    /*
     * public void parseActivityShowInfo(PromotionDto data, ActionInfo
     * actionInfo) {
     * if (data == null || actionInfo == null || actionInfo.getMaterial() ==
     * null) {
     * return;
     * }
     * // 模板样式决定所需展示字段
     * String position = data.getPosition();
     * Map<String, Object> material = actionInfo.getMaterial();
     * if (material.get("title") != null) {
     * data.setTitle((String) material.get("title"));
     * }
     * if (material.get("img") != null) {
     * data.setImg((String) material.get("img"));
     * }
     * if (material.get("sub_title") != null) {
     * data.setSubTitle((String) material.get("sub_title"));
     * }
     * if (material.get("video") != null) {
     * data.setVideo((String) material.get("video"));
     * }
     * if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM.equals(position))
     * {
     * long activity_conunt_time = -1L;
     * long activity_timeout = -1L;
     * if (material.get("conunt_time") != null) {
     * try {
     * Integer conunt_time = (Integer) material.get("conunt_time");
     * if (conunt_time != null) {
     * activity_conunt_time = conunt_time.intValue() * 60 * 60 * 1000;
     * }
     * } catch (Exception e) {
     * }
     * }
     * if (material.get("end_time") != null) {
     * if (activity_timeout <= 0) {
     * try {
     * Integer activity_timeout_temp = (Integer) material.get("end_time");
     * if (activity_timeout_temp != null) {
     * activity_timeout = activity_timeout_temp.longValue() * 1000;
     * }
     * } catch (Exception e) {
     * }
     * }
     * if (activity_timeout <= 0) {
     * try {
     * Long activity_timeout_temp = (Long) material.get("end_time");
     * if (activity_timeout_temp != null) {
     * activity_timeout = activity_timeout_temp.longValue() * 1000;
     * }
     * } catch (Exception e) {
     * }
     * }
     * }
     * if (activity_timeout > 0) {
     * if (activity_conunt_time > 0) {
     * data.setActivity_timeout(-1L);
     * long now = System.currentTimeMillis();
     * //下浮层到期时长要求在activity_conunt_time天数内显示，其余显示为-1
     * if (activity_timeout > now && activity_timeout - now <=
     * activity_conunt_time) {
     * data.setActivity_timeout(activity_timeout);
     * }
     * }
     * }
     * if (data.getActivity_timeout() != null && data.getActivity_timeout() >
     * -1L) {
     * data.setNowTime(System.currentTimeMillis());
     * }
     * ButtonDto mButtonDto = new ButtonDto();
     * if (material.get("button_1") != null) {
     * mButtonDto.setButtonText((String) material.get("button_1"));
     * }
     * mButtonDto.setOrder(1);
     * ButtonDto mButtonDto2 = new ButtonDto();
     * if (material.get("button_2") != null) {
     * mButtonDto2.setButtonText((String) material.get("button_2"));
     * }
     * mButtonDto2.setOrder(2);
     * List<ButtonDto> buttons = new ArrayList<ButtonDto>();
     * buttons.add(mButtonDto);
     * buttons.add(mButtonDto2);
     * data.setButtons(buttons);
     * } else if
     * (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_QUIT.equals(position)) {
     * //还没有确定
     * } else if
     * (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_TOP_TRY.equals(position)
     * ||
     * ActivityTpConstant.ACTIVITY_CLIENT_POSITION_HIGH_STREAM.equals(position)
     * || ActivityTpConstant.ACTIVITY_CLIENT_POSITION_TOP.equals(position)
     * ||
     * ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION.equals(position)
     * ||
     * ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_TOP_TRY.equals(position)
     * || ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_TOP.equals(position)
     * ||
     * ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION.equals(position
     * )
     * ||
     * ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_PARENT_FOCUS.equals(position
     * )) {
     * ButtonDto mButtonDto = new ButtonDto();
     * if (material.get("button") != null) {
     * mButtonDto.setButtonText((String) material.get("button"));
     * }
     * List<ButtonDto> buttons = new ArrayList<ButtonDto>();
     * buttons.add(mButtonDto);
     * data.setButtons(buttons);
     * }
     * }
     */
    public void parseActivityShowInfoV2(PromotionDto data, ActivityData.ActionInfo actionInfo) {
        if (data == null || actionInfo == null || actionInfo.getMaterial() == null) {
            return;
        }
        // 模板样式决定所需展示字段
        String position = data.getPosition();
        Map<String, Object> material = actionInfo.getMaterial();
        if (material.get("title") != null) {
            data.setTitle((String) material.get("title"));
        }
        if (material.get("img") != null) {
            data.setImg((String) material.get("img"));
        }
        if (material.get("sub_title") != null) {
            data.setSubTitle((String) material.get("sub_title"));
        }
        if (material.get("video") != null) {
            data.setVideo((String) material.get("video"));
        }
        List<ButtonDto> buttons = new ArrayList<ButtonDto>();
        if (material.get("button") != null) {
            ButtonDto mButtonDto = new ButtonDto();
            mButtonDto.setButtonText((String) material.get("button"));
            buttons.add(mButtonDto);
        } else {
            if (material.get("button_1") != null) {
                ButtonDto mButtonDto = new ButtonDto();
                mButtonDto.setButtonText((String) material.get("button_1"));
                mButtonDto.setOrder(1);
                buttons.add(mButtonDto);
            }
            if (material.get("button_2") != null) {
                ButtonDto mButtonDto = new ButtonDto();
                mButtonDto.setButtonText((String) material.get("button_2"));
                mButtonDto.setOrder(2);
                buttons.add(mButtonDto);
            }
        }

        if (!buttons.isEmpty()) {
            data.setButtons(buttons);
        }

        long activity_conunt_time = -1L;
        long activity_timeout = -1L;
        if (material.get("conunt_time") != null) {
            try {
                Integer conunt_time = (Integer) material.get("conunt_time");
                if (conunt_time != null) {
                    activity_conunt_time = conunt_time.intValue() * 60 * 60 * 1000;
                }

            } catch (Exception e) {

            }
        }
        if (material.get("end_time") != null) {
            if (activity_timeout <= 0) {
                try {
                    Integer activity_timeout_temp = (Integer) material.get("end_time");
                    if (activity_timeout_temp != null) {
                        activity_timeout = activity_timeout_temp.longValue() * 1000;
                    }
                } catch (Exception e) {
                }
            }
            if (activity_timeout <= 0) {
                try {
                    Long activity_timeout_temp = (Long) material.get("end_time");
                    if (activity_timeout_temp != null) {
                        activity_timeout = activity_timeout_temp.longValue() * 1000;
                    }
                } catch (Exception e) {
                }
            }
        }
        if (activity_timeout > 0) {
            if (activity_conunt_time > 0) {
                data.setActivity_timeout(-1L);
                long now = System.currentTimeMillis();
                // 下浮层到期时长要求在activity_conunt_time天数内显示，其余显示为-1
                if (activity_timeout > now && activity_timeout - now <= activity_conunt_time) {
                    data.setActivity_timeout(activity_timeout);
                }
            }
        }
        if (data.getActivity_timeout() != null && data.getActivity_timeout() > -1L) {
            data.setNowTime(System.currentTimeMillis());
        }
    }

    /**
     * 解析观星系统接口返回值中的上报信息
     * @param data
     * @param actionInfo
     */
    public void parseActivityReportInfo(PromotionDto data, ActivityData.ActionInfo actionInfo) {
        if (data == null || actionInfo == null || actionInfo.getTrack() == null) {
            return;
        }
        ActivityData.TrackInfo trackInfo = actionInfo.getTrack();
        PromotionDto.ReportData report = new PromotionDto.ReportData();
        data.setReport(report);
        report.setPosid(data.getPosition());
        report.setReqid(data.getReqid());
        report.setPromoId(data.getActivity_id());

        report.setMiaozhen(changeValue(trackInfo.getMiaozhen()));
        report.setAdmaster(changeValue(trackInfo.getAdmaster()));
    }

    /**
     * 解析观星系统接口返回值中的显示策略信息
     * @param data
     * @param actionInfo
     */
    public void parseActivityDisplayInfo(PromotionDto data, ActivityData.ActionInfo actionInfo) {
        if (data == null || actionInfo == null || actionInfo.getTrack() == null) {
            return;
        }
        String position = data.getPosition();
        Map<String, Object> material = actionInfo.getMaterial();
        PromotionDto.ShowConfig config = new PromotionDto.ShowConfig();
        boolean isConfig = false;
        if (material == null || material.size() == 0) {
            return;
        }
        if (material.get("display_time") != null) {
            try {
                Integer display_time = (Integer) material.get("display_time");
                config.setDisplayTime(display_time);
                isConfig = true;
            } catch (Exception e) {

            }
        }
        if (material.get("interval_time") != null) {
            try {
                Integer interval_time = (Integer) material.get("interval_time");
                config.setIntervalTime(interval_time);
                isConfig = true;
            } catch (Exception e) {

            }
        }
        if (material.get("times") != null) {
            try {
                Integer times = (Integer) material.get("times");
                config.setTimes(times);
                isConfig = true;
            } catch (Exception e) {

            }
        }
        if (isConfig) {
            data.setShowConfig(config);
        }
    }

    public PromotionDto.ReportAddressData changeValue(ActivityData.AddressInfo addressInfo) {
        PromotionDto.ReportAddressData data = new PromotionDto.ReportAddressData();
        if (addressInfo == null) {
            return data;
        }
        data.setClick(addressInfo.getClick());
        data.setImp(addressInfo.getImp());
        return data;
    }

    public Response<Map<String, List<PromotionDto>>> getGuanXingPromotion(String posids, CommonParam commonParam,
                                                                          String pid, String vid) {
        return this.getGuanXingPromotion(posids, commonParam, pid, vid, null);
    }

    public Response<Map<String, List<PromotionDto>>> getGuanXingPromotion(String posids, CommonParam commonParam,
            String pid, String vid, Long vet) {
        Response<Map<String, List<PromotionDto>>> response = new Response<Map<String, List<PromotionDto>>>();
        /*
         * 参数检查
         */
        if (commonParam == null) {
            response = (Response<Map<String, List<PromotionDto>>>) LetvUserVipCommonConstant.setErrorResponse(response,
                    ErrorCodeConstants.GET_ACTIVITY_PARAM_FAILURE, commonParam.getLangcode());
            return response;
        } else {
            String uid = commonParam.getUserId();
            String mac = commonParam.getMac();
            String key = commonParam.getDeviceKey();

            if (StringUtil.isBlank(mac)) {
                response = (Response<Map<String, List<PromotionDto>>>) LetvUserVipCommonConstant.setErrorResponse(
                        response, ErrorCodeConstants.GET_ACTIVITY_PARAM_FAILURE, commonParam.getLangcode());
                return response;
            }
        }

        /*
         * 解析位置，是否p_notification或者kid_p_notification(儿童APP)，
         * p_notification和kid_p_notification与其他位置不一样，需要单独请求观星数据
         */
        boolean isNotification = false;
        boolean isPromotion = true;
        String notificationPositionStr = "";
        if (StringUtil.isNotBlank(posids)
                && posids.indexOf(ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION) > -1) {
            // 儿童APP
            isNotification = true;
            posids = posids.replace(ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION, "");
            String temp = posids.replaceAll(",", "");
            notificationPositionStr = ActivityTpConstant.ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION;
            if (StringUtil.isBlank(temp)) {
                isPromotion = false;
            }
        } else if (StringUtil.isNotBlank(posids)
                && posids.indexOf(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION) > -1) {
            // 超级影视
            isNotification = true;
            posids = posids.replace(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION, "");
            String temp = posids.replaceAll(",", "");
            notificationPositionStr = ActivityTpConstant.ACTIVITY_CLIENT_POSITION_NOTIFICATION;
            if (StringUtil.isBlank(temp)) {
                isPromotion = false;
            }
        } else if (StringUtil.isBlank(posids) && StringUtil.isNotBlank(pid) && StringUtil.isNotBlank(vid)) {
            isNotification = true;
        }

        // 如果分析位置中包含p_notification或者kid_p_notification(儿童APP)，则pid和vid为必填项
        if (isNotification) {
            if (StringUtil.isBlank(pid) || StringUtil.isBlank(vid)) {
                response = (Response<Map<String, List<PromotionDto>>>) LetvUserVipCommonConstant.setErrorResponse(
                        response, ErrorCodeConstants.GET_ACTIVITY_PARAM_FAILURE, commonParam.getLangcode());
                return response;
            }
        }

        posids = filterPosIds(posids, vet);

        /*
         * 开始请求数据
         */
        List<PromotionDto> promotions = null;
        if (isPromotion) {
            promotions = this.getStarGazerDataForPromotion(posids, commonParam);
        }

        if (isNotification) {
            List<PromotionDto> notifications = getStarGazerDataForNotification(pid, vid, notificationPositionStr,
                    commonParam);
            if (notifications != null) {
                // promotion和notification数据合并
                if (promotions == null) {
                    promotions = new ArrayList<PromotionDto>();
                }
                promotions.addAll(notifications);
            }
        }

        filterPromotions(promotions, vet);

        if (promotions == null) {
            LOG.error("getGuanXingPromotion_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                    + ErrorCodeConstants.GET_ACTIVITY_FAILURE + "]: get activity failed.");
            response = (Response<Map<String, List<PromotionDto>>>) LetvUserVipCommonConstant.setErrorResponse(response,
                    ErrorCodeConstants.GET_ACTIVITY_FAILURE, commonParam.getLangcode());
        } else {
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            Map<String, List<PromotionDto>> mAppDto = new HashMap<String, List<PromotionDto>>();
            if (promotions.size() > 0) {
                mAppDto.put("promotions", promotions);
            }
            response.setData(mAppDto);
        }

        return response;
    }

    public String filterPosIds(String posids, Long vet) {
        if (!posids.contains(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_VIP_REMIND)) {
            return posids;
        }
        String title = showVipRemind(vet);
        if (title == null) {
            return posids.replace(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_VIP_REMIND, "");
        }
        return posids;
    }

    public void filterPromotions(List<PromotionDto> promotionDtos, Long vet) {
        if (promotionDtos == null || promotionDtos.size() <= 1) {
            return;
        }

        PromotionDto bottom = null;
        PromotionDto vip = null;
        for (PromotionDto promotionDto : promotionDtos) {
            String position = promotionDto.getPosition();
            if (StringUtil.isBlank(position)) {
                continue;
            }
            if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM.equals(position)) {
                bottom = promotionDto;
            } else if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_VIP_REMIND.equals(position)) {
                vip = promotionDto;
            }
        }
        if (vip != null) {
            String title = showVipRemind(vet);
            if (title == null) {
                promotionDtos.remove(vip);
                return;
            }
            vip.setTitle(title);
            if (bottom != null) {
                promotionDtos.remove(bottom);
            }
        }
    }

    private String showVipRemind(Long vet) {
        String title = null;
        if (vet == null) {
            return title;
        }
        long now = System.currentTimeMillis();

        int isVip = 0;
        int detween = 0;
        if (vet >= now) {
            isVip = 1;
            detween = (int) ((vet - now) / (1000 * 60 * 60 * 24));
        } else {
            detween = (int) ((now - vet) / (1000 * 60 * 60 * 24));
        }
        detween = Math.abs(detween);
        if (detween != 0 && detween != 7 && detween != 15 && detween != 30) {
            return title;
        }
        if (detween == 0) {
            if (isVip == 0) {
                title = "您的会员已在今天过期！";
            } else if (isVip == 1) {
                title = "您的会员将在今天到期！";
            }
        }
        if (detween == 1) {
            if (isVip == 0) {
                title = "您的会员已在昨天过期！";
            } else if (isVip == 1) {
                title = "您的会员将在明天到期！";
            }
        }
        if (detween == 7 || detween == 15 || detween == 30) {
            if (isVip == 0) {
                title = "您的会员已过期"+detween+"天! ";
            } else if (isVip == 1) {
                title = "您的会员还有"+detween+"天到期!";
            }
        }
        return title;
    }

    /**
     * get activity data from urm system
     * @param supportNew
     * @param deviceType
     * @param commonParam
     * @return
     */
    public Response<BaseData> getPilotV2(Integer supportNew, Integer deviceType, CommonParam commonParam) {
        Response<BaseData> response = new Response<BaseData>();
        if (TerminalUtil.isLetvUs(commonParam) || TerminalUtil.isLetvCommon(commonParam)) {
            return response;// letv_us or letv_common version no activity
        }
        DPilotDto data = new DPilotDto();

        String posIds = ActivityTpConstant.ACTIVITY_CLIENT_POSITION_TOP + ","
                + ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM;

        List<DPilotDto> dPilotDtos = getStarGazerListDataForUrm("", posIds, commonParam);
        if (dPilotDtos == null || dPilotDtos.size() == 0) {
            return response;
        }
        for (DPilotDto dto : dPilotDtos) {
            if (dto != null) {
                String position = dto.getPosition();
                if (StringUtil.isBlank(position)) {
                    continue;
                }
                if (!ActivityTpConstant.ACTIVITY_CLIENT_POSITION_TOP.equals(position)
                        && !ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM.equals(position)) {
                    continue;
                }
                if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_TOP.equals(position)) {
                    data.setTopData(dto);
                    response.setData(data);
                } else {
                    if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM.equals(position)) {
                        dto.setOpenType(7);
                    }
                    if (data.getTopData() != null) {
                        dto.setTopData(data.getTopData());
                        response.setData(dto);
                    } else {
                        data = dto;
                        response.setData(data);
                    }
                }
            }
        }

        return response;
    }

    public DPilotDto parseGuanXingActivityData(PromotionDto mPromotionDto, CommonParam commonParam) {
        if (mPromotionDto == null) {
            return null;
        }
        String position = mPromotionDto.getPosition();
        DPilotDto data = parseGuanXingActivityInfo(mPromotionDto, commonParam);
        if (data != null) {
            data.setPosition(position);
            data.setId(mPromotionDto.getActivity_id());
            // data.setCampaignId(mPromotionDto.getActivity_id());
            // data.setTouchMessageId(mPromotionDto.getActivity_id()+"_"+mPromotionDto.getPosition()+"_"+0);
            // data.setTouchSpotId(mPromotionDto.getPosition());
        } else {
            /*
             * this.facadeCacheDao.getVipCacheDao().deleteDeliverAddress(commonParam
             * .getUserId(),
             * position);
             */
        }
        return data;
    }

    private DPilotDto parseGuanXingActivityInfo(PromotionDto mPromotionDto, CommonParam commonParam) {
        if (mPromotionDto == null) {
            return null;
        }

        DPilotDto data = new DPilotDto();
        // set activity show info
        setGuanXingActivityInfo(data, mPromotionDto);

        setGuanXingActivityJumpInfo(data, mPromotionDto);

        data.setReport(mPromotionDto.getReport());

        String position = mPromotionDto.getPosition();
        if (StringUtil.isNotBlank(position)) {

        }
        if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM.equals(position)) {
            if (mPromotionDto.getActivity_timeout() != null) {
                data.setTimeOut(String.valueOf(mPromotionDto.getActivity_timeout()));
                if (mPromotionDto.getActivity_timeout() > -1) {
                    data.setNowTime(String.valueOf(System.currentTimeMillis()));
                } else {
                    data.setNowTime("-1");
                }
            }
        }
        return data;
    }

    private int getJumpType() {
        int dataType = 0;
        return dataType;
    }

    /**
     * set urm activity show info
     * @param data
     * @param position
     * @param showInfo
     */
    private void setGuanXingActivityInfo(PilotDto data, PromotionDto mPromotionDto) {
        String position = mPromotionDto.getPosition();
        if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_TOP.equals(position)) {
            data.setTitle(mPromotionDto.getTitle());// title
            List<ButtonDto> buts = mPromotionDto.getButtons();
            if (buts != null && buts.size() > 0) {
                data.setButtonContent(buts.get(0).getButtonText());
            }
        } else if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM.equals(position)) {
            List<ButtonDto> buts = mPromotionDto.getButtons();
            if (buts != null && buts.size() > 0) {
                data.setButtonContent(buts.get(0).getButtonText());
            }
            data.setTitle(mPromotionDto.getTitle());
            data.setPrivilegeTips(mPromotionDto.getSubTitle());
            data.setImg(mPromotionDto.getImg());
            data.setContentImage(mPromotionDto.getImg());
        } else if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_POPUP.equals(position)) {

        } else if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_MINE1.equals(position)) {
            data.setImg(mPromotionDto.getImg());
        } else if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_MINE2.equals(position)) {
            data.setImg(mPromotionDto.getImg());
        } else if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_MINE3.equals(position)) {
            data.setImg(mPromotionDto.getImg());
        }
    }

    private void setGuanXingActivityJumpInfo(PilotDto data, PromotionDto mPromotionDto) {
        String position = mPromotionDto.getPosition();
        if (ActivityTpConstant.ACTIVITY_CLIENT_POSITION_BOTTOM.equals(position)) {
            List<ButtonDto> buttons = mPromotionDto.getButtons();
            if (buttons != null && buttons.size() > 0) {
                JumpData mJumpData = buttons.get(0).getJump();
                setDataFormJumpInfo(mJumpData, data);
            }
        } else {
            JumpData mJumpData = mPromotionDto.getJump();
            setDataFormJumpInfo(mJumpData, data);
        }
    }

    private void setDataFormJumpInfo(JumpData mJumpData, PilotDto data) {
        if (mJumpData == null) {
            return;
        }
        int type = mJumpData.getType();
        data.setDataType(type);
        data.setJump(mJumpData);
        Object object = mJumpData.getValue();
        if (object == null) {
            return;
        }
        if (type == DataConstant.DATA_TYPE_BROWSER) {
            Browser temp = (Browser) object;
            if (temp.getUrlMap() != null && temp.getUrlMap().size() > 0) {
                data.setCashierUrl(temp.getUrlMap().get("0"));
            }
            int openType = temp.getOpenType();
            if (openType == DataConstant.BROWSER_OPENTYPE_INTERACTIVE) {
                data.setInteractive(1);
            } else if (openType == DataConstant.BROWSER_OPENTYPE_CHECKOUT) {
                data.setOpenType(DataConstant.OPENTYPE_H5_AND_CHECKOUT);
            } else {
                data.setOpenType(DataConstant.OPENTYPE_H5);
            }
            data.setId(temp.getId());
        } else if (type == DataConstant.DATA_TYPE_CHECKSTAND) {
            PilotDto temp = (PilotDto) object;
            data.setOrderType(temp.getOrderType());
            data.setActivityIds(temp.getActivityIds());
            data.setCPS_no(temp.getCPS_no());
            data.setCode_no(temp.getCode_no());
            data.setId(temp.getId());
            data.setOpenType(DataConstant.OPENTYPE_CHECKOUTER);
        }
    }

    private static class GcParams {
        @JsonProperty
        private String action;
        @JsonProperty
        private Integer type;
        @JsonProperty
        // private GcJumpInfo value;
        private Map<String, String> value;
        @JsonProperty
        private Integer callType;
        @JsonProperty
        private String from;
    }

    private static class GcJumpInfo {
        @JsonProperty
        private String url;
        @JsonProperty
        private String urlMap;
        @JsonProperty
        private Integer openType;
        @JsonProperty
        private String id;
        @JsonProperty
        private Integer dataType;
        @JsonProperty
        private Integer browserType;
        @JsonProperty
        private Integer orderType;
        @JsonProperty
        private String activityIds;
        @JsonProperty
        private String cps_no;// 订单渠道来源，乐视boss为对接支付app的合作方分配的订单渠道
        @JsonProperty
        private String code_no;// spu商品编码/sku商品编码，相当于新的商品id
        @JsonProperty
        private String delivery_address;
        @JsonProperty
        private String cashierDeskId;
    }

}

package com.letv.mas.caller.iptv.tvproxy.vip.service;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.Channel;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.LiveProjectPermissionResponseTp;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.CheckLiveTpResponse.CheckLiveError;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.OrderStatusTpResponseV2.UserPackageInfoV2.UserPackage;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.ConsumptionRecordTpResponse.ConsumptionRecord;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.UserStatus;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.RechargeRecordTpResponse.ChargeRecord;
import com.letv.mas.caller.iptv.tvproxy.live.LiveService;
import com.letv.mas.caller.iptv.tvproxy.user.UserService;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.CheckOutCounter.ProductType;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.CheckOutCounter.ProductType.ProductData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.SubjectPreLive;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageCommonResponse;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.ChartsDto;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.UserChildLockTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.SubjectDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.GetUserInfoTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.CheckOneKeyPayRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.GetBindInfoRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.GetLetvPointBalanceRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.GetPaymentActivityRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.TvStreamInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.common.util.ErrorCodeCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.live.model.bean.bo.Live;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.ChildLockDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoPlayConsumeGuideInfoDto;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.builder.*;
import com.letv.mas.caller.iptv.tvproxy.vip.model.bean.bo.MemberDeskActivity;
import com.letv.mas.caller.iptv.tvproxy.vip.model.bean.bo.MemberDeskInfo;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.vip.util.VipUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * 会员元数据Service，提供获取会员元数据相关方法，如获取充值记录、获取消费记录、获取收银台产品包、手机校验、鉴定加卡绑定、鉴定定向弹窗、获取支付活动、
 * 获取订单状态等
 * @author yikun
 */
@Service("vipMetadataService")
@SuppressWarnings("all")
public class VipMetadataService extends BaseService {

    private final static Logger LOG = LoggerFactory.getLogger(VipMetadataService.class);
    protected static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // allow empty string parse to null object
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @Autowired
    UserService userService;

    @Autowired
    LiveService liveService;

    @Autowired
    AlbumVideoAccess albumVideoAccess;

    /**
     * get user recharge record
     * @param day
     * @param commonParam
     * @return
     */
    public PageResponse<RechargeRecordDto> getRechargeRecord(Integer day, CommonParam commonParam) {
        List<RechargeRecordDto> dtoList = new ArrayList<RechargeRecordDto>();
        String errorCode = null;

        // get user recharge record from boss
        RechargeRecordTpResponse recResult = this.facadeTpDao.getVipTpDao().getRechargeRecord(day, commonParam);
        if (recResult == null) {
            errorCode = ErrorCodeConstants.PAY_GET_RECHARGE_RECORD_FAILURE;
            LOG.info("getRechargeRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + day
                    + "[errorCode=" + errorCode + "]: get recharge record failed.");
        } else if ((recResult.getCode() != null) && (recResult.getCode().intValue() == 1)) {
            // recharge record is empty
            errorCode = ErrorCodeConstants.PAY_RECHARGE_RECORD_NULL;
            LOG.info("getRechargeRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + day
                    + "[errorCode=" + errorCode + "]: return error [" + recResult.getMsg() + "]");
        } else {
            List<ChargeRecord> recordList = recResult.getChargeRecords();
            if ((commonParam.getBroadcastId() != null) && (commonParam.getBroadcastId() == CommonConstants.CIBN)) {// 国广版本
                for (ChargeRecord rec : recordList) {
                    RechargeRecordDto dto = new RechargeRecordDto();
                    dto.setChargetime(TimeUtil.getDateString(
                            TimeUtil.parseDate(rec.getActiveDate(), TimeUtil.SIMPLE_DATE_FORMAT),
                            TimeUtil.SHORT_DATE_FORMAT));
                    // if (rec.getAmount() != null) {
                    // dto.setMoney(rec.getAmount() * 100);
                    // }
                    String chargeTypeName = rec.getChargeTypeName();
                    if (chargeTypeName != null) {
                        chargeTypeName = chargeTypeName.replaceAll("乐卡", "兑换卡");
                        dto.setChargetype(chargeTypeName);
                        dto.setChargeTypeName(chargeTypeName);
                    } else {
                        dto.setChargetype("");
                        dto.setChargeTypeName("");
                    }
                    dto.setOrderid(rec.getChargeNumber());
                    dto.setPoint(rec.getPoint());
                    dtoList.add(dto);
                }
            } else {// 非国广版本
                for (ChargeRecord rec : recordList) {
                    RechargeRecordDto dto = new RechargeRecordDto();
                    dto.setChargetime(TimeUtil.getDateString(
                            TimeUtil.parseDate(rec.getActiveDate(), TimeUtil.SIMPLE_DATE_FORMAT),
                            TimeUtil.SHORT_DATE_FORMAT));
                    dto.setChargetype(rec.getChargeTypeName());
                    dto.setChargeTypeName(rec.getChargeTypeName());
                    if (rec.getAmount() != null) {
                        dto.setMoney(rec.getAmount() * 100);
                    }
                    dto.setOrderid(rec.getChargeNumber());
                    dto.setPoint(rec.getPoint());
                    dtoList.add(dto);
                }
            }
        }
        PageResponse<RechargeRecordDto> response = new PageResponse<RechargeRecordDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(dtoList);
        }

        return response;
    }

    public PageCommonResponse<ConsumptionRecordDto> getConsumptionRecord(Integer status, Integer day, Integer page,
                                                                         Integer pageSize, List<String> orderTypeList, CommonParam commonParam) {
        PageControl<ConsumptionRecordDto> pageControl = new PageControl<ConsumptionRecordDto>();
        String errorCode = null;
        String errorMsgCode = null;

        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (status == null || status < 0 || status > 3) {// 要查询的订单状态值校验
            validCode = VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_STATUS_ERROR;
        } else if (page == null || page < 1 || pageSize == null || pageSize < 1) {// 分页参数
            validCode = VipMsgCodeConstant.REQUEST_PAGE_ILLEGAL;
        } else if (day == null || day < 0) {// 查询的订单日期
            validCode = VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_DAY_ERROR;
        }
        if (errorCode == null && VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            errorMsgCode = errorCode + VipUtil.parseErrorMsgCode(VipConstants.CONSUMPTION_RECORD, validCode);
            LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: illegal parameter.");
        } else {
            if (StringUtil.isBlank(commonParam.getUserId())) {
                // compatible for hk and us application,because hk and us
                // application doesn't send userId parameter
                UserStatus user = userService.getUserStatus(commonParam);
                if (user != null) {
                    commonParam.setUserId(String.valueOf(user.getUserId()));
                }
            }
            // get user consumption record from boss
            ConsumptionRecordTpResponse tpResult = this.facadeTpDao.getVipTpDao().getConsumptionRecord(status, day,
                    page, pageSize, commonParam);
            if (tpResult == null || tpResult.getTotal() == null) {
                errorCode = ErrorCodeConstants.PAY_GET_CONSUMPTION_RECORD_FAILURE;
                errorMsgCode = errorCode + LetvUserVipCommonConstant.COMMON_HYPHEN + status;
                LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: get consume record failure.");
            } else {
                List<ConsumptionRecord> recList = tpResult.getData();
                List<ConsumptionRecordDto> dtoList = new ArrayList<ConsumptionRecordDto>();
                // 返回分页信息
                pageControl.setPageSize(pageSize);
                pageControl.setCurrentPage(page);
                if (!CollectionUtils.isEmpty(recList)) {
                    ConsumptionRecordDto dto = null;
                    for (ConsumptionRecord rec : recList) {
                        dto = ConsumptionRecordBuilder.build(rec,albumVideoAccess, orderTypeList, commonParam);
                        if (dto != null) {
                            dtoList.add(dto);
                        }
                    }
                }
                pageControl.setList(dtoList);
                pageControl.setCount(dtoList.size());

            }
        }
        PageCommonResponse<ConsumptionRecordDto> response = new PageCommonResponse<ConsumptionRecordDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, errorMsgCode, commonParam.getLangcode());
        } else {
            response = new PageCommonResponse<ConsumptionRecordDto>(pageControl);
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
        }

        return response;
    }

    public PageCommonResponse<ConsumptionRecordDto> getConsumptionRecordV2(Integer status, Integer day, Integer page,
                                                                           Integer pageSize, List<String> orderTypeList, CommonParam commonParam) {
        PageControl<ConsumptionRecordDto> pageControl = new PageControl<ConsumptionRecordDto>();
        String errorCode = null;
        String errorMsgCode = null;

        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (status == null || status < -1 || status > 3) {// 要查询的订单状态值校验
            validCode = VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_STATUS_ERROR;
        } else if (page == null || page < 1 || pageSize == null || pageSize < 1) {// 分页参数
            validCode = VipMsgCodeConstant.REQUEST_PAGE_ILLEGAL;
        } else if (day == null || day < 0) {// 查询的订单日期
            validCode = VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_DAY_ERROR;
        }
        if (errorCode == null && VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            errorMsgCode = errorCode + VipUtil.parseErrorMsgCode(VipConstants.CONSUMPTION_RECORD, validCode);
            LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: illegal parameter.");
        } else {
            if (StringUtil.isBlank(commonParam.getUserId())) {
                // compatible for hk and us application,because hk and us
                // application doesn't send userId parameter
                UserStatus user = userService.getUserStatus(commonParam);
                if (user != null) {
                    commonParam.setUserId(String.valueOf(user.getUserId()));
                }
            }
            // get user consumption record from boss
            OrderStatusTpResponseV2 tpResult = this.facadeTpDao.getVipTpDao().getConsumptionRecordV2(status, day, page,
                    pageSize, commonParam);
            if (tpResult == null || tpResult.getData().getTotal() == null) {
                errorCode = ErrorCodeConstants.PAY_GET_CONSUMPTION_RECORD_FAILURE;
                errorMsgCode = errorCode + LetvUserVipCommonConstant.COMMON_HYPHEN + status;
                LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: get consume record failure.");
            } else {
                List<UserPackage> recList = tpResult.getData().getRows();
                List<ConsumptionRecordDto> dtoList = new ArrayList<ConsumptionRecordDto>();
                // 返回分页信息
                pageControl.setPageSize(pageSize);
                pageControl.setCurrentPage(page);
                if (!CollectionUtils.isEmpty(recList)) {
                    ConsumptionRecordDto dto = null;
                    for (UserPackage rec : recList) {
                        dto = ConsumptionRecordBuilder.buildV2(rec,albumVideoAccess, orderTypeList,commonParam);
                        if (dto != null) {
                            dtoList.add(dto);
                        }
                    }
                }
                pageControl.setList(dtoList);
                pageControl.setCount(dtoList.size());

            }
        }
        PageCommonResponse<ConsumptionRecordDto> response = new PageCommonResponse<ConsumptionRecordDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, errorMsgCode, commonParam.getLangcode());
        } else {
            response = new PageCommonResponse<ConsumptionRecordDto>(pageControl);
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
        }

        return response;
    }

    public PageCommonResponse<ConsumptionRecordDto2> getConsumptionRecordV3(Integer status, Integer day, Integer page,
                                                                            Integer pageSize, String orderType, CommonParam commonParam) {
        PageControl<ConsumptionRecordDto2> pageControl = new PageControl<ConsumptionRecordDto2>();
        String errorCode = null;
        String errorMsgCode = null;

        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (status == null || status < -1 || status > 3) {// 要查询的订单状态值校验
            validCode = VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_STATUS_ERROR;
        } else if (page == null || page < 1 || pageSize == null || pageSize < 1) {// 分页参数
            validCode = VipMsgCodeConstant.REQUEST_PAGE_ILLEGAL;
        } else if (day == null || day < 0) {// 查询的订单日期
            validCode = VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_DAY_ERROR;
        }
        if (errorCode == null && VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            errorMsgCode = errorCode + VipUtil.parseErrorMsgCode(VipConstants.CONSUMPTION_RECORD, validCode);
            LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: illegal parameter.");
        } else {
            if (StringUtil.isBlank(commonParam.getUserId())) {
                // compatible for hk and us application,because hk and us
                // application doesn't send userId parameter
                UserStatus user = userService.getUserStatus(commonParam);
                if (user != null) {
                    commonParam.setUserId(String.valueOf(user.getUserId()));
                }
            }
            if (!orderType.equals("TEN_FAM")) {
                // get user consumption record from boss
                OrderStatusTpResponseV3 tpResult = this.facadeTpDao.getVipTpDao().getConsumptionRecordV3(status, day,
                        page, pageSize, orderType, commonParam);
                if (tpResult == null || tpResult.getBody() == null) {
                    errorCode = ErrorCodeConstants.PAY_GET_CONSUMPTION_RECORD_FAILURE;
                    errorMsgCode = errorCode + LetvUserVipCommonConstant.COMMON_HYPHEN + status;
                    LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: get consume record failure.");
                } else {
                    List<OrderStatusTpResponseV3.UserPackageV3> recList = tpResult.getBody();
                    List<ConsumptionRecordDto2> dtoList = new ArrayList<ConsumptionRecordDto2>();
                    // 返回分页信息
                    pageControl.setPageSize(pageSize);
                    pageControl.setCurrentPage(page);
                    if (!CollectionUtils.isEmpty(recList)) {
                        ConsumptionRecordDto2 dto = null;
                        for (OrderStatusTpResponseV3.UserPackageV3 rec : recList) {
                            dto = ConsumptionRecordBuilder.buildV3(rec, orderType,commonParam);
                            if (dto != null) {
                                dtoList.add(dto);
                            }
                        }
                    }
                    pageControl.setList(dtoList);
                    pageControl.setCount(dtoList.size());

                }
            } else {
                OrderStatusTpResponseTX tpResult = this.facadeTpDao.getVipTpDao().getConsumptionRecordV3ForTM(status,
                        day, page, pageSize, orderType, commonParam);
                if (tpResult == null || tpResult.getData() == null || !tpResult.getSuccess()) {
                    errorCode = ErrorCodeConstants.PAY_GET_CONSUMPTION_RECORD_FAILURE;
                    errorMsgCode = errorCode + LetvUserVipCommonConstant.COMMON_HYPHEN + status;
                    LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: get consume record failure.");
                } else {
                    pageControl.setPageSize(pageSize);
                    pageControl.setCurrentPage(page);
                    if (tpResult.getData().getDatas() != null) {
                        try {
                            List<ConsumptionRecordDto2> results = this.getValidListForTM(tpResult.getData().getDatas(),
                                    commonParam);
                            pageControl.setList(results);
                            pageControl.setCount(results.size());
                        } catch (Exception e) {
                            LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + "_" + commonParam.getDeviceKey() + " error e:" + e.getMessage());
                        }
                    }
                }
            }
        }
        PageCommonResponse<ConsumptionRecordDto2> response = new PageCommonResponse<ConsumptionRecordDto2>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, errorMsgCode, commonParam.getLangcode());
        } else {
            response = new PageCommonResponse<ConsumptionRecordDto2>(pageControl);
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
        }

        return response;
    }

    /**
     * 由于乐购吐出的订单是包含预购单和尾款单，因此需要对部分订单进行合并，乐购吐出的订单是按successTime到排序，因此如果是购买成功的预购订单，
     * 尾款单肯定在预订单之前
     * @param recList
     * @param commonParam
     * @return
     */
    private List<ConsumptionRecordDto2> getValidListForTMSuccessTimeOrder(List<OrderTXData.UserPackageTX> recList,
                                                                          CommonParam commonParam) {
        Map<String, ConsumptionRecordDto2> fpay_maps = new HashMap<>();
        Map<String, ConsumptionRecordDto2> pay_maps = new HashMap<>();
        List<ConsumptionRecordDto2> results = new ArrayList<>();
        for (OrderTXData.UserPackageTX rec : recList) {
            if (rec != null) {
                if (rec.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_FAIL
                        || rec.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_EXPIRE) {
                    continue;
                }
                if (rec.getOrderType() != null) {
                    if (rec.getOrderType() == VipTpConstant.ORDER_TM_TYPE_0) {
                        ConsumptionRecordDto2 value = this.changeValue(rec, commonParam);
                        if (value != null) {
                            results.add(value);
                        }
                    } else if (rec.getOrderType() == VipTpConstant.ORDER_TM_TYPE_1) {
                        if (rec.getOrderSubType() != null) {
                            if (rec.getOrderSubType().equals(VipTpConstant.SUB_ORDER_TM_TYPE_2)) {
                                if (rec.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_SUCC) {
                                    ConsumptionRecordDto2 value = this.changeValue(rec, commonParam);
                                    if (value != null) {
                                        results.add(value);
                                        String no = rec.getVirtualOrderNo();
                                        if (StringUtil.isNotBlank(no)) {
                                            fpay_maps.put(rec.getVirtualOrderNo(), value);
                                        }
                                    }
                                } else {
                                    continue;
                                }
                            } else if (rec.getOrderSubType().equals(VipTpConstant.SUB_ORDER_TM_TYPE_1)) {
                                if (rec.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_SUCC) {
                                    String no = rec.getVirtualOrderNo();
                                    if (StringUtil.isNotBlank(no)) {
                                        // 拿出尾款单，更改成主单(合并单)
                                        ConsumptionRecordDto2 temp = fpay_maps.get(no);
                                        if (temp != null) {
                                            temp.setStatusNameText("购买成功");
                                            continue;
                                        }
                                    }
                                }
                                ConsumptionRecordDto2 value = this.changeValue(rec, commonParam);
                                if (value != null) {
                                    results.add(value);
                                }
                            }
                        }
                    }
                }
            }
        }
        return results;
    }

    /**
     * 由于乐购吐出的订单是包含预购单和尾款单，因此需要对部分订单进行合并
     * @param recList
     * @param commonParam
     * @return
     */
    private List<ConsumptionRecordDto2> getValidListForTM(List<OrderTXData.UserPackageTX> recList,
                                                          CommonParam commonParam) {
        Map<String, ConsumptionRecordDto2> fpay_maps = new HashMap<>();
        Map<String, ConsumptionRecordDto2> psale_maps = new HashMap<>();
        List<ConsumptionRecordDto2> results = new ArrayList<>();
        for (OrderTXData.UserPackageTX rec : recList) {
            if (rec != null) {
                if (rec.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_FAIL
                        || rec.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_EXPIRE) {
                    continue;
                }
                if (rec.getOrderType() == null) {
                    rec.setOrderType(0);
                }
                if (rec.getOrderType() != null) {
                    if (rec.getOrderType() == VipTpConstant.ORDER_TM_TYPE_0) {
                        ConsumptionRecordDto2 value = this.changeValue(rec, commonParam);
                        if (value != null) {
                            results.add(value);
                        }
                    } else if (rec.getOrderType() == VipTpConstant.ORDER_TM_TYPE_1) {
                        if (rec.getOrderSubType() != null) {
                            if (rec.getOrderSubType() == VipTpConstant.SUB_ORDER_TM_TYPE_2) {
                                if (rec.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_SUCC) {
                                    String no = rec.getVirtualOrderNo();
                                    if (StringUtil.isBlank(no)) {
                                        continue;
                                    }
                                    ConsumptionRecordDto2 value = this.changeValue(rec, commonParam);
                                    ConsumptionRecordDto2 temp = psale_maps.get(no);
                                    if (temp != null
                                            && temp.getPay_status().equals(VipTpConstant.ORDER_PAY_STATUS_SUCC)) {
                                        // 拿出预售单进行合并
                                        temp.setPayTime(value.getPayTime());
                                        temp.setPayStatusNameText(MessageUtils.getMessage(
                                                VipTpConstant.ORDER_PAY_STATUS_SUCC_STR, commonParam.getLangcode())/* "购买成功" */);
                                        continue;
                                    }
                                    fpay_maps.put(no, value);
                                } else {
                                    continue;
                                }
                            } else if (rec.getOrderSubType() == VipTpConstant.SUB_ORDER_TM_TYPE_1) {
                                if (rec.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_SUCC) {
                                    String no = rec.getVirtualOrderNo();
                                    if (StringUtil.isBlank(no)) {
                                        continue;
                                    }
                                    ConsumptionRecordDto2 value = this.changeValue(rec, commonParam);
                                    if (value != null) {
                                        ConsumptionRecordDto2 temp = fpay_maps.get(no);
                                        if (temp != null && temp.getPay_status() != null
                                                && temp.getPay_status().equals(VipTpConstant.ORDER_PAY_STATUS_SUCC)) {// 拿出尾款单，更改成主单(合并单)
                                            // 拿出尾单进行合并
                                            value.setPayTime(temp.getPayTime());
                                            value.setPayStatusNameText(MessageUtils.getMessage(
                                                    VipTpConstant.ORDER_PAY_STATUS_SUCC_STR, commonParam.getLangcode())/* "购买成功" */);
                                        } else {// 如果没有尾款单，可能是还没有遇到，则放入map中已备相遇
                                            psale_maps.put(no, value);
                                        }
                                        results.add(value);
                                    }
                                } else {
                                    ConsumptionRecordDto2 value = this.changeValue(rec, commonParam);
                                    if (value != null) {
                                        results.add(value);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return results;
    }

    private ConsumptionRecordDto2 changeValue(OrderTXData.UserPackageTX order, CommonParam commonParam) {
        if (order == null || order.getOrderType() == null) {
            return null;
        }
        String orderId = null;
        String orderName = null;
        if (order.getOrderType() == VipTpConstant.ORDER_TM_TYPE_1) {
            orderId = order.getVirtualOrderNo();
            orderName = order.getOrderName();
        } else {
            orderId = order.getOrderNo();
            orderName = order.getOrderName();
        }
        if (StringUtil.isNotBlank(orderName)) {
            orderName = orderName.replaceAll("乐视", "");
        }
        Object successTime = order.getSuccessTime();
        if (successTime == null) {
            successTime = new Date();
        }
        /*
         * Object createTime = order.getCreateTime();
         * if (createTime == null) {
         * createTime = new Date();
         * }
         */
        /*
         * Object refundTime = order.getRefundTime();
         * if (refundTime == null) {
         * refundTime = new Date();
         * }
         */
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ConsumptionRecordDto2 dto2 = new ConsumptionRecordDto2(orderId, orderName, formater.format(successTime),
                this.calculateOrderEndTime(order), formater.format(successTime));
        if ((commonParam.getBroadcastId() != null) && (VipConstants.BROADCAST_CIBN == commonParam.getBroadcastId())) {
            dto2.setMoneyName(null);// 国广版本不展示乐点
        }

        dto2.setAct_type("0");
        dto2.setStatus("0");

        if (order.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_SUCC) {
            dto2.setPay_status(VipTpConstant.ORDER_PAY_STATUS_SUCC);
        } else if (order.getStatus() == VipTpConstant.TM_ORDER_PAY_STATUS_REFUND) {
            dto2.setPay_status(VipTpConstant.ORDER_PAY_STATUS_REFUND);
        }
        if (dto2.getPay_status().equals(VipTpConstant.ORDER_PAY_STATUS_SUCC)) {
            if (order.getOrderType() != null) {
                if (order.getOrderType() == VipTpConstant.ORDER_TM_TYPE_1) {
                    dto2.setPayStatusNameText(MessageUtils.getMessage(VipTpConstant.ORDER_PAY_STATUS_FM_SUCC_STR,
                            commonParam.getLangcode())/* "定金已付" */);
                } else if (order.getOrderType() == VipTpConstant.ORDER_TM_TYPE_0) {
                    dto2.setPayStatusNameText(MessageUtils.getMessage(VipTpConstant.ORDER_PAY_STATUS_SUCC_STR,
                            commonParam.getLangcode()));
                }
            } else {
                dto2.setPayStatusNameText(MessageUtils.getMessage(VipTpConstant.ORDER_PAY_STATUS_SUCC_STR,
                        commonParam.getLangcode()));
            }
        } else if (dto2.getPay_status().equals(VipTpConstant.ORDER_PAY_STATUS_REFUND)) {
            dto2.setPayStatusNameText(MessageUtils.getMessage(VipTpConstant.ORDER_PAY_STATUS_REFUND_STR,
                    commonParam.getLangcode()));
            Object refundTime = order.getRefundTime();
            if (refundTime != null) {
                dto2.setPayTime(formater.format(refundTime));
            }
        }

        String payTimeStamp = ConsumptionRecordBuilder.date2TimeStamp(dto2.getPayTime(), "yyyy-MM-dd HH:mm:ss");
        Long s = (System.currentTimeMillis() - Long.parseLong(payTimeStamp)) / (1000 * 60 * 60 * 24);
        if (s < 30) {
            dto2.setIsExpired(false);
        } else {
            dto2.setIsExpired(true);
        }

        return dto2;
    }

    public String calculateOrderEndTime(OrderTXData.UserPackageTX order) {
        Date successTime = order.getSuccessTime();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (successTime == null) {
            successTime = new Date();
            return formater.format(successTime);
        }
        if (order.getVendorProductDurationType() == null || order.getVendorProductDuration() == null) {
            return formater.format(successTime);
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(successTime);
        if (order.getVendorProductDurationType().equals(1)) {
            rightNow.add(Calendar.YEAR, order.getVendorProductDuration());
        } else if (order.getVendorProductDurationType().equals(2)) {
            rightNow.add(Calendar.MONTH, order.getVendorProductDuration());
        } else {
            rightNow.add(Calendar.DAY_OF_YEAR, order.getVendorProductDuration());
        }
        Date orderEndTime = rightNow.getTime();
        return formater.format(orderEndTime);
    }

    /**
     * get vip package data
     * @param langcode
     * @return
     */
    public PageResponse<PricePackageListDto> getPricePackageList(String langcode) {
        PageResponse<PricePackageListDto> response = new PageResponse<PricePackageListDto>();
        String logPrefix = "getPricePackageList_";
        String errorCode = null;

        // get vip package data
        PricePackageListTpResponse tpResult = this.facadeTpDao.getVipTpDao().getPricePackageList();
        if (tpResult == null || tpResult.getPackageList() == null) {
            errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]: get price package failed.");
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, langcode);
        } else {
            List<PricePackageListTpResponse.PackageRecord> recList = tpResult.getPackageList();
            List<PricePackageListDto> dtoList = new ArrayList<PricePackageListDto>();

            for (PricePackageListTpResponse.PackageRecord tpRec : recList) {
                PricePackageListDto dto = PricePackageBuilder.build(tpRec, langcode);
                if (dto != null) {
                    dtoList.add(dto);
                }
            }

            // 2015-05-26 超级手机领先版中，boss添加了排序功能，故无需服务端再做排序
            if (VipConstants.VIP_CHECKOUT_COUNTER_NEED_SORT_PACKAGE) {
                // 这里做一个兼容，实际从2.8.1之后，该分支路径不再执行
                response.setData(this.sortPricePackage(dtoList));
            } else {
                response.setData(dtoList);
            }
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
        }

        return response;
    }

    public Response<CheckPhoneDto> getPhoneCheck(String phone, CommonParam commonParam) {
        Response<CheckPhoneDto> response = new Response<CheckPhoneDto>();
        // check phone if support pay
        PhoneCheckTpResponse checkTp = this.facadeTpDao.getVipTpDao().getPhoneCheck(phone, commonParam.getClientIp());
        if (checkTp == null || "1".equals(checkTp.getCode())) {
            LOG.error("getPhoneCheck_" + commonParam.getMac() + "_" + phone + "_" + commonParam.getClientIp()
                    + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_PHONE_FAILURE + "]: phone check failed.");
            ErrorCodeCommonUtil.setErrorResponse(response, ErrorCodeConstants.PAY_CHECK_PHONE_FAILURE,
                    commonParam.getLangcode());
        } else {
            CheckPhoneDto data = new CheckPhoneDto();
            data.setPaymentChannel(checkTp.getPayType());
            data.setPrice(checkTp.getPrice());
            data.setGjprice(checkTp.getGjprice());
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(data);
        }

        return response;
    }

    /**
     * 查询订单状态
     * @param checkOrderRequest
     * @param locale
     * @return
     */
    /*
     * public Response<OrderStatusDto> checkOrderStatus(CheckOrderRequest
     * checkOrderRequest, Locale locale) { Response<OrderStatusDto> response =
     * new Response<OrderStatusDto>(); String logPrefix = "checkOrderStatus_" +
     * checkOrderRequest.getUsername() + "_" + checkOrderRequest.getOrderid();
     * String errorCode = null; Long userid = checkOrderRequest.getUserid(); //
     * 获取当前账户的用户中心ID if (!LetvUserVipCommonConstant.isUseridLegal(userid)) {
     * userid = this.facadeService.getUserSerivce().getUserCenterIdByUsername(
     * checkOrderRequest.getUsername()); if
     * (!LetvUserVipCommonConstant.isUseridLegal(userid)) { errorCode =
     * ErrorCodeConstants.USER_NOT_EXIST; LOG.info(logPrefix + "[errorCode=" +
     * errorCode + "]: user does not exist."); } else {
     * checkOrderRequest.setUserid(userid); } } OrderStatusTpResponse orderTp =
     * null; if (errorCode == null) { logPrefix = "checkOrderStatus_" +
     * checkOrderRequest.getUsername() + "_" + userid + "_" +
     * checkOrderRequest.getOrderid(); // 查询订单详情 orderTp =
     * this.facadeTpDao.getVipTpDao().checkOrderStatus(checkOrderRequest,
     * locale); if (orderTp == null || orderTp.getError() != null ||
     * orderTp.getData() == null || orderTp.getData().size() == 0) { errorCode =
     * ErrorCodeConstants.PAY_CHECK_ORDER_STATUS_FAILURE; LOG.error(logPrefix +
     * "[errorCode=" + errorCode + "]: check roder failed."); } } if (errorCode
     * == null) { // 设置返回 OrderStatusDto orderDto = new OrderStatusDto();
     * UserPackageInfo packageInfo = orderTp.getData().get(0);
     * orderDto.setAmount(StringUtil.toInteger(packageInfo.getMoney(), 0) *
     * 100); orderDto.setOrderId(checkOrderRequest.getOrderid());
     * orderDto.setPurchaseName(packageInfo.getOrderName());
     * orderDto.setPurchaseType(packageInfo.getOrderType()); //
     * 单点支付新增逻辑，需要通过orderType判断当前订单是否为单点订单 boolean isGingleOrder =
     * String.valueOf(VipTpConstant.ORDER_TYPE_0).equals(
     * packageInfo.getOrderType());
     * orderDto.setStatus(StringUtil.toInteger(packageInfo.getStatus(), 0)); if
     * (StringUtils.isNotBlank(packageInfo.getCreateTime())) { // 单点支付新增逻辑
     * String createTime = null; if (isGingleOrder) { createTime =
     * StringUtils.substringBeforeLast(CalendarUtil.getDateString( new
     * Date(Long.valueOf(packageInfo.getCreateTime())),
     * CalendarUtil.SIMPLE_DATE_FORMAT), ":"); } else { createTime =
     * CalendarUtil.getDateString(new
     * Date(Long.valueOf(packageInfo.getCreateTime())),
     * CalendarUtil.SHORT_DATE_FORMAT); } orderDto.setStartDate(createTime); }
     * if (StringUtils.isNotBlank(packageInfo.getCancelTime())) { // 单点支付新增逻辑
     * String cancelTime = null; if (isGingleOrder) { cancelTime =
     * StringUtils.substringBeforeLast(CalendarUtil.getDateString( new
     * Date(Long.valueOf(packageInfo.getCancelTime())),
     * CalendarUtil.SIMPLE_DATE_FORMAT), ":"); } else { cancelTime =
     * CalendarUtil.getDateString(new
     * Date(Long.valueOf(packageInfo.getCancelTime())),
     * CalendarUtil.SHORT_DATE_FORMAT); } orderDto.setEndDate(cancelTime); }
     * response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
     * response.setData(orderDto); } else { response =
     * (Response<OrderStatusDto>)
     * LetvUserVipCommonConstant.setErrorResponse(response, errorCode, locale);
     * } return response; }
     */
    /**
     * check order status
     * @param orderId
     * @param commonParam
     * @return
     */
    public Response<OrderStatusDto> checkOrderStatusV2(String orderId, CommonParam commonParam) {
        OrderStatusDto orderDto = new OrderStatusDto();
        String errorCode = null;

        // check order status
        OrderStatusTpResponseV2 orderTp = this.facadeTpDao.getVipTpDao().checkOrderStatusV2(orderId, commonParam);
        if (orderTp == null || orderTp.getError() != null || CollectionUtils.isEmpty(orderTp.getData().getRows())) {
            errorCode = ErrorCodeConstants.PAY_CHECK_ORDER_STATUS_FAILURE;
            LOG.error("checkOrderStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + orderId
                    + "[errorCode=" + errorCode + "]: check roder failed.");
        } else {
            UserPackage packageInfo = orderTp.getData().getRows().get(0);
            orderDto.setAmount(String.valueOf(packageInfo.getPayPrice() * 100));
            orderDto.setOrderId(orderId);
            orderDto.setPurchaseName(packageInfo.getOrderName());
            orderDto.setPurchaseType(packageInfo.getProductType());

            // if it's buying album then order create time show hour and minute
            String orderCreateAndCancelTimeFormat = TimeUtil.SHORT_DATE_FORMAT;
            if (String.valueOf(VipTpConstant.ORDER_TYPE_0).equals(packageInfo.getProductType())) {
                orderCreateAndCancelTimeFormat = TimeUtil.SIMPLE_DATE_HOUR_MINUTE_FORMAT;
            }

            orderDto.setStatus(StringUtil.toInteger(packageInfo.getStatus(), 0));
            Long createT = StringUtil.toLong(packageInfo.getCreateTime());
            if (createT != null) {
                String createTime = TimeUtil.getDateStringFromLong(createT, orderCreateAndCancelTimeFormat);
                orderDto.setStartDate(createTime);
            }
            Long cancelT = StringUtil.toLong(packageInfo.getEndTime());
            if (cancelT != null) {
                String cancelTime = TimeUtil.getDateStringFromLong(Long.valueOf(packageInfo.getEndTime()),
                        orderCreateAndCancelTimeFormat);
                orderDto.setEndDate(cancelTime);
            }
        }
        Response<OrderStatusDto> response = new Response<OrderStatusDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(orderDto);
        }

        return response;
    }

    public Response<OrderStatusDto> checkOrderStatus(String orderId, CommonParam commonParam) {
        OrderStatusDto orderDto = new OrderStatusDto();
        String errorCode = null;

        // check order status
        OrderStatusTpResponse orderTp = this.facadeTpDao.getVipTpDao().checkOrderStatus(orderId, commonParam);
        if (orderTp == null || orderTp.getError() != null || CollectionUtils.isEmpty(orderTp.getData())) {
            errorCode = ErrorCodeConstants.PAY_CHECK_ORDER_STATUS_FAILURE;
            LOG.error("checkOrderStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + orderId
                    + "[errorCode=" + errorCode + "]: check roder failed.");
        } else {
            OrderStatusTpResponse.UserPackageInfo packageInfo = orderTp.getData().get(0);
            orderDto.setAmount(String.valueOf(StringUtil.toInteger(packageInfo.getMoney(), 0) * 100));
            orderDto.setOrderId(orderId);
            orderDto.setPurchaseName(packageInfo.getOrderName());
            orderDto.setPurchaseType(packageInfo.getOrderType());

            // if it's buying album then order create time show hour and minute
            String orderCreateAndCancelTimeFormat = TimeUtil.SHORT_DATE_FORMAT;
            if (String.valueOf(VipTpConstant.ORDER_TYPE_0).equals(packageInfo.getOrderType())) {
                orderCreateAndCancelTimeFormat = TimeUtil.SIMPLE_DATE_HOUR_MINUTE_FORMAT;
            }

            orderDto.setStatus(StringUtil.toInteger(packageInfo.getStatus(), 0));
            Long createT = StringUtil.toLong(packageInfo.getCreateTime());
            if (createT != null) {
                String createTime = TimeUtil.getDateStringFromLong(createT, orderCreateAndCancelTimeFormat);
                orderDto.setStartDate(createTime);
            }
            Long cancelT = StringUtil.toLong(packageInfo.getCancelTime());
            if (cancelT != null) {
                String cancelTime = TimeUtil.getDateStringFromLong(Long.valueOf(packageInfo.getCancelTime()),
                        orderCreateAndCancelTimeFormat);
                orderDto.setEndDate(cancelTime);
            }
        }
        Response<OrderStatusDto> response = new Response<OrderStatusDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(orderDto);
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
    public Response<DeviceBindDto> getDeviceBindInfo(String type, Integer deviceType, CommonParam commonParam) {
        DeviceBindDto data = new DeviceBindDto();
        String errorCode = null;
        String logPrefix = "getDeviceBindInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + commonParam.getDeviceKey();
        String[] types = { VipConstants.DEVICE_BIND_QUERY_TYPE_0, VipConstants.DEVICE_BIND_QUERY_TYPE_1,
                VipConstants.DEVICE_BIND_QUERY_TYPE_2 };
        List<String> typeList = Arrays.asList(types);
        if (!typeList.contains(type)
                || ((TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                .getTerminalApplication()) || TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI
                .equalsIgnoreCase(commonParam.getTerminalApplication())) && !VipConstants.DEVICE_BIND_QUERY_TYPE_1
                .equals(type))) {// 参数校验
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]: illegal parameter.");
        } else {
            if (VipConstants.DEVICE_BIND_QUERY_TYPE_0.equals(type)) {// 查询自带和赠送机卡
                // get bind info of current machine
                errorCode = this.getDeviceInfo(data, deviceType, errorCode, logPrefix, commonParam);
                if (errorCode == null) {
                    // get bind info of current user
                    errorCode = this.getPresentDeviceInfo(data, errorCode, commonParam);
                }
            } else if (VipConstants.DEVICE_BIND_QUERY_TYPE_1.equals(type)) {// 查询自带机卡
                // get bind info of current machine
                errorCode = this.getDeviceInfo(data, deviceType, errorCode, logPrefix, commonParam);
            } else if (VipConstants.DEVICE_BIND_QUERY_TYPE_2.equals(type)) {// 查询赠送
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

    private String getDeviceInfo(DeviceBindDto data, Integer deviceType, String errorCode, String logPrefix,
                                 CommonParam commonParam) {
        if (deviceType != null && VipTpConstantUtils.DEVICE_BIND_LETV_APPLICATION_TYPE.getDeviceTypeApplicationById(deviceType) == null) {
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
            // 调用第三方接口，鉴别机卡绑定套餐信息
            DeviceBindTpResponse deviceBindTp = this.facadeTpDao.getVipTpDao().getDeviceBindInfo(deviceType,
                    commonParam);
            if (deviceBindTp == null || deviceBindTp.getValues() == null || deviceBindTp.getCode() == null
                    || deviceBindTp.getCode() != 0) {
                // 调用接口失败（异常或返回失败码）
                errorCode = ErrorCodeConstants.PAY_GET_DEVICE_BIND_FAILURE;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]: get device bind info failed.");
            } else {
                DeviceBindTpResponse.DeviceBindConent bindContent = deviceBindTp.getValues();
                if (bindContent != null) {
                    List<DeviceBind> deviceBinds = new ArrayList<DeviceBind>();
                    data.setDeviceBinds(deviceBinds);
                    List<DeviceBindTpResponse.DeviceBindConent.DeviceInfo> items = bindContent.getItems();
                    if (CollectionUtils.isEmpty(items)) {
                        DeviceBind deviceBind = new DeviceBind();
                        deviceBinds.add(deviceBind);
                        Boolean isDeviceActive = bindContent.getIsDeviceActive();
                        Integer bindDay = bindContent.getBindtime();
                        if (isDeviceActive == null || bindDay == null || bindDay <= 0) {
                            // 无效数据
                            data.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                            deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                        } else if (Boolean.TRUE == isDeviceActive) {
                            // 已领取
                            data.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ACTIVATED);
                            deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ACTIVATED);
                        } else {
                            // 未领取
                            data.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_UNACTIVATED);
                            deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_UNACTIVATED);
                            int bingMonth = VipUtil.parseDayToMonth(bindDay.intValue(), 31);
                            data.setBindMonths(Integer.valueOf(bingMonth));
                            deviceBind.setBindMonths(data.getBindMonths());
                        }
                    } else {
                        for (DeviceBindTpResponse.DeviceBindConent.DeviceInfo deviceInfo : items) {
                            Integer vipType = deviceInfo.getVipType();
                            if (vipType == null || vipType != 2) {
                                continue;
                            }
                            DeviceBind deviceBind = new DeviceBind();
                            deviceBind.setVipType(vipType);
                            deviceBind.setVipTypeName(deviceInfo.getVipTypeName());
                            Boolean isActive = deviceInfo.getIsActived();
                            Integer bindDay = deviceInfo.getBindDuration();
                            Integer status = deviceInfo.getStatus();
                            if (status != null && status == 3) { // 过滤掉退货的情况
                                continue;
                            }
                            if (isActive == null || bindDay == null || bindDay <= 0) {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ILLEGAL);
                            } else if (isActive) {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_ACTIVATED);
                            } else {
                                deviceBind.setIsDeviceActive(VipConstants.DEVICE_BIND_STATUS_UNACTIVATED);
                                int bindMonth = VipUtil.parseDayToMonth(bindDay, 31);
                                deviceBind.setBindMonths(bindMonth);
                            }
                            deviceBind.setVipEndTime(deviceInfo.getVipEndTime());
                            deviceBinds.add(deviceBind);
                        }
                        if (deviceBinds.size() > 0) {
                            DeviceBind deviceBind = deviceBinds.get(0);
                            data.setIsDeviceActive(deviceBind.getIsDeviceActive());
                            data.setBindMonths(deviceBind.getBindMonths());
                        }
                    }
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
                List<GetPresentDeviceBindTpResponse.PresentDeviceBindTpResponse> presentList = tpResponse.getValues().getPresentList();
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

    @Deprecated
    public Response<DirectionalPushPricePackageDto> checkDirectionalPushUser(CommonParam commonParam) {
        Response<DirectionalPushPricePackageDto> response = new Response<DirectionalPushPricePackageDto>();
        DirectionalPushPricePackageDto data = new DirectionalPushPricePackageDto();
        data.setQualification(0);// This business has been offline,so return
        // qualification for zero.
        response.setData(data);
        return response;
    }

    /**
     * 获取活动信息，暂时支持套餐和支付活动
     * @param username
     * @param svip
     *            会员类型，0--非会员，1--普通会员，9--超级会员（实际取值参照终端类型，同terminalType）
     * @param locale
     * @return
     */
    public PageResponse<PaymentActivityDto> getPaymentActivity(GetPaymentActivityRequest getPaymentActivityRequest,
                                                               CommonParam commonParam) {
        PageResponse<PaymentActivityDto> response = new PageResponse<PaymentActivityDto>();
        String logPrefix = "getPaymentActivity_" + getPaymentActivityRequest.getUsername() + "_"
                + getPaymentActivityRequest.getUserid();

        PaymentActivityTpResponse actTp = this.facadeTpDao.getVipTpDao().getPaymentActivity(getPaymentActivityRequest,
                commonParam.getLangcode());
        if (actTp == null || actTp.getCode() == null || actTp.getCode().intValue() != 0) {
            LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_GET_PAYMENT_ACTIVITY_FAILURE
                    + "]: get payment activity failed.");
            response = (PageResponse<PaymentActivityDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                    ErrorCodeConstants.PAY_GET_PAYMENT_ACTIVITY_FAILURE, commonParam.getLangcode());
        } else {
            List<PaymentActivityDto> data = new ArrayList<PaymentActivityDto>();

            if (actTp.getValues() != null && !CollectionUtils.isEmpty(actTp.getValues().getActivitys())) {
                Map<Integer, List<PaymentActivityTpResponse.PaymentActivity>> activitys = actTp.getValues().getActivitys();
                for (Entry<Integer, List<PaymentActivityTpResponse.PaymentActivity>> activityEntry : activitys.entrySet()) {
                    if (activityEntry != null && !CollectionUtils.isEmpty(activityEntry.getValue())) {
                        for (PaymentActivityTpResponse.PaymentActivity activity : activityEntry.getValue()) {
                            PaymentActivityDto dto = PaymentActivityBuilder.build(activity,
                                    getPaymentActivityRequest.getAv());
                            if (dto != null) {
                                data.add(dto);
                            }
                        }
                    }
                }
            }

            int originalPaymentActivitySize = actTp.getPaymentActivitySize();
            if (data.size() != originalPaymentActivitySize) {
                LOG.warn(logPrefix + ": execute filtering from " + originalPaymentActivitySize + " to " + data.size());
            }

            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(data);
        }

        return response;
    }

    public Response<VoucherStatusDto> checkVoucherStatus(String couponCode, String types, CommonParam commonParam) {

        String errorCode = null;

        // the coupon apply type
        List<Integer> typeList = new LinkedList<Integer>();
        if (StringUtil.isNotBlank(types)) {
            String[] ts = types.split(",");
            if ((ts != null) && (ts.length > 0)) {
                for (String t : ts) {
                    int type = StringUtil.toInteger(t, 0);
                    if (VipTpConstantUtils.VOUCHER_APPLICATION_TYPE.getVoucherApplicationTypeById(type) == null) {
                        errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
                        LOG.info("checkVoucherStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                                + couponCode + "[errorCode=" + errorCode + "]: illegal parameters.");
                        break;
                    } else {
                        typeList.add(type);
                    }
                }
            }
        }
        VoucherStatusDto data = null;
        if (errorCode == null) {
            // check coupon code status
            VoucherStatusTpResponse voucherStatusresponse = this.facadeTpDao.getVipTpDao().checkVoucherStatus(
                    couponCode, commonParam);
            if (voucherStatusresponse == null || voucherStatusresponse.getStatus() == null) {
                // 第三方无响应或代金券不适用TV端，都会返回null
                errorCode = ErrorCodeConstants.PAY_CHECK_VOUCHER_STATUS_FAILURE;
                LOG.info("checkVoucherStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + couponCode + "[errorCode=" + errorCode + "]: check voucher status failed.");
            } else if (1 == voucherStatusresponse.getStatus()) {
                // 不可用的情况包括1001-代金卷或兑换码不存在；1002-代金卷被冻结；1003-代金卷无效，已使用
                errorCode = ErrorCodeConstants.PAY_VOUCHER_NOT_AVAILABLE;
                LOG.info("checkVoucherStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + couponCode + "[errorCode=" + errorCode + "]: code=" + voucherStatusresponse.getCode()
                        + ", msg=" + voucherStatusresponse.getMsg());
            } else {
                data = VoucherStatusBuilder.build(voucherStatusresponse, typeList);
                if (data == null || CollectionUtils.isEmpty(data.getRules())) {
                    // 如果转换后的data为空，或根据types过滤后的代金券适用规则为空，则认为是代金券不适用
                    errorCode = ErrorCodeConstants.PAY_VOUCHER_NOT_APPLICATIVE;
                    LOG.info("checkVoucherStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + couponCode + "[errorCode=" + errorCode + "]: voucher not fit for required types[" + types
                            + "].");
                }
            }
        }
        Response<VoucherStatusDto> response = new Response<VoucherStatusDto>();
        if (errorCode == null) {
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(data);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    public Response<String> getUserAgreement(Locale locale) {
        String localeStr = LetvUserVipCommonConstant.getLocalString(locale);
        if (StringUtils.isEmpty(localeStr)) {
            localeStr = MessageUtils.DEFAULT_FULL_LOCAL_ZH_CN;
        }

        String userAggrement = this.facadeCacheDao.getVipCacheDao().getUserAgreement(localeStr);
        if (StringUtils.isBlank(userAggrement)) {
            // if the cache is empty then read static file and update cache
            userAggrement = this.facadeTpDao.getVipTpDao().readUserAggrement(
                    VipConstants.VIP_USER_AGGREMENT_COMMON_FILENAME + localeStr
                            + VipConstants.VIP_USER_AGGREMENT_FILENAME_POSTFIX);
            if (StringUtils.isNotBlank(userAggrement)) {
                this.facadeCacheDao.getVipCacheDao().setUserAgreement(localeStr, userAggrement);
            }
        }

        Response<String> response = new Response<String>();
        if (StringUtils.isBlank(userAggrement)) {
            LOG.info("getUserAgreement_[errorCode=" + ErrorCodeConstants.PAY_GET_USER_AGGREMENT_FAILURE
                    + "]: empty user aggrement text.");
            ErrorCodeCommonUtil.setErrorResponse(response, ErrorCodeConstants.PAY_GET_USER_AGGREMENT_FAILURE, locale);
        } else {
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(userAggrement);
        }

        return response;
    }

    /**
     * 根据购买类型获取支付渠道，先读缓存，缓存没有则读静态文件，静态文件有则更新缓存
     * @param purchaseTypes
     * @return
     */
    private String getPaymentChannel(String productType, Integer subend) {
        // String paymentChannelStr = (String) RedisUtil.get(key);
        String paymentChannelStr = this.facadeCacheDao.getVipCacheDao().getPaymentChannel(productType, subend);
        if (StringUtils.isBlank(paymentChannelStr)) {
            Map<String, String> map = this.facadeTpDao.getStaticTpDao().getStaticFileContent(
                    ApplicationUtils.get(ApplicationConstants.VIP_PROFILE_URL));// 静态文件内容
            if (map != null && map.size() > 0) {
                paymentChannelStr = map.get(VipTpConstantUtils.getPaymentChannelCacheKey(productType, subend));
            }
            if (StringUtils.isNotBlank(paymentChannelStr)) {// 将静态文件中的内容，设置到缓存中
                // 读取静态文件，设置缓存值
                // RedisUtil.set(key, paymentChannelStr);
                this.facadeCacheDao.getVipCacheDao().setPaymentChannel(productType, subend, paymentChannelStr);
            }
        }
        return paymentChannelStr;
    }

    /**
     * 校验当前用户是否有权播放某一直播
     * @param pid
     * @param liveId
     * @param streamId
     * @param isstart
     * @param termid
     * @param selectId
     * @param commonParam
     * @return
     */
    public Response<CheckLiveDto> checkLive(String pid, String liveId, String streamId, Integer isstart, String termid,
                                            String selectId, String streamCode, CommonParam commonParam) {
        Response<CheckLiveDto> response = new Response<CheckLiveDto>();
        String mac = commonParam.getMac();
        StringBuilder logPrefix = new StringBuilder("checkLive_").append(mac).append("_")
                .append(commonParam.getDeviceKey()).append("_").append(commonParam.getUserId()).append("_").append(pid)
                .append("_").append(liveId);
        String errorCode = null;
        String playUrl = "";
        // 参数校验
        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isBlank(pid)) {
            validCode = VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_PID_EMPTY;
        } else if (StringUtils.isBlank(liveId)) {
            validCode = VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_LIVEID_EMPTY;
        } else if (StringUtils.isBlank(mac)) {
            validCode = VipMsgCodeConstant.REQUEST_MAC_EMPTY;
        }
        // else if (StringUtils.isBlank(splatId)) {
        // validCode = VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_SPLATID_EMPTY;
        // }
        if (VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            response = (Response<CheckLiveDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                    errorCode + ErrorMsgCodeUtil.parseErrorMsgCode(BusinessCodeConstant.LIVE, validCode),
                    commonParam.getLangcode());
        } else {
            // 2016-08-26,splatid获取放在服务端，不再由客户端传值
            String splatId = LiveConstants.getLiveSplatIdByTerminalApplication(commonParam.getTerminalApplication(),
                    LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
            if (StringUtil.isNotBlank(selectId) && StringUtil.isNotBlank(streamCode)) {
                List<TvStreamInfoDto> streamInfo = liveService.tp2StreamInfo(pid, selectId,
                        commonParam.getLangcode(), commonParam.getBroadcastId(), splatId);
                for (TvStreamInfoDto info : streamInfo) {
                    if (streamCode.equalsIgnoreCase(info.getCode())) {
                        playUrl = info.getLiveUrl();
                        streamId = HttpUtil.getUrlParamValue(playUrl, "stream_id");
                        break;
                    }
                }
            }
            CheckLiveTpResponse tpResponse = this.facadeTpDao.getVipTpDao().doCheckLive(pid, liveId, streamId, splatId,
                    isstart, termid, logPrefix.toString(), commonParam);
            if (tpResponse != null && tpResponse.needActiveTicket()) {
                // 如果第一次鉴权失败，可能是因购买直播后未执行用券操作，则需执行激活直播券--重新鉴权操作
                LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                        + "]: check live permission failure 1st time, will try to active the live ticket.");
                String channel = null; // 频道，liveId的0-1位，共两位
                String category = null; // 赛事类型，liveId的2-4位，共三位
                String season = null; // 年份，liveId的5-8位，共四位
                String turn = null; // 轮次，liveId的9-11位，共三位
                String game = null; // 场次，liveId的12-15位，共四位
                if (StringUtils.isNotBlank(liveId)) {
                    channel = liveId.substring(0, 2);
                    category = liveId.substring(2, 5);
                    season = liveId.substring(5, 9);
                    turn = liveId.substring(9, 12);
                    game = liveId.substring(12);
                }
                // 1.查询直播券；
                // 注意，该yuanxian接口有bug，仅能在购买成功后的第一次查询能查到数据，第二次及以后的查询则无法查到数据；该问题会对当前逻辑流程造成影响；
                // 暂时性可用解决方案为，如果第一次鉴权失败，则直接暴力激活该券，再进行第二次鉴权
                CheckUserLiveTicketTpResponse checkTicketTpResponse = this.facadeTpDao.getVipTpDao()
                        .checkUserLiveTicket(channel, category, season, turn, game, logPrefix.toString(), commonParam);
                if (checkTicketTpResponse == null || !checkTicketTpResponse.hasBoughtLiveTicket()) {
                    LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                            + "]: check user live ticket failure.");
                } else {
                    // 2.激活直播券
                    ActiveUserLiveTicketTpResponse activeTicketTpResponse = this.facadeTpDao.getVipTpDao()
                            .activeUserLiveTicket(channel, category, season, turn, game, logPrefix.toString(),
                                    commonParam);
                    if (activeTicketTpResponse == null || !activeTicketTpResponse.isSucceed()) {
                        LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                                + "]: active user live ticket failure.");
                    } else {
                        // 第二次鉴权
                        LOG.info(logPrefix + ", active user live ticket successful.");
                        tpResponse = this.facadeTpDao.getVipTpDao().doCheckLive(pid, liveId, streamId, splatId,
                                isstart, termid, logPrefix.toString(), commonParam);
                    }
                }
            }

            if (tpResponse == null) {// 因为鉴权失败会返回其他信息，所以此处只判空
                // 请求失败
                LOG.info(logPrefix.toString() + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                        + "]: check live failure.");
                response = (Response<CheckLiveDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                        ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE, commonParam.getLangcode());
            } else {
                CheckLiveDto data = new CheckLiveDto();
                if (VipTpConstant.TP_STATTUS_CODE_1.equals(tpResponse.getStatus())) {
                    // 请求直接返回有权限，已购买，可以试看，试看结束
                    if (tpResponse.getIsPre()) {
                        // 可以试看
                        data.setHasPlayPermission(VipConstants.CHECK_LIVE_HAVE_TRY_PLAY_PERMISSSION);
                        LOG.info(logPrefix + ", user have try play permission of this live.");
                    } else {
                        // 可以观看完整
                        data.setHasPlayPermission(VipConstants.CHECK_LIVE_HAVE_PLAY_PERMISSSION);
                        LOG.info(logPrefix + ", user have play permission of this live.");
                    }
                    data.setToken(tpResponse.getToken());
                    data.setUinfo(tpResponse.getUinfo());
                    data.setCurTime(tpResponse.getCurTime());
                    data.setLiveStatus(tpResponse.getLiveStatus());
                    data.setOrdertype(tpResponse.getOrdertype());
                    data.setPrestart(tpResponse.getPrestart());
                    data.setPreend(tpResponse.getPreend());
                    data.setTeamName(tpResponse.getTeamName());
                    LOG.info(logPrefix + data.initTryPlayInfo());// 初始化试看信息，并打个日志记录结果
                    data.setTryPlayText(MessageUtils.getMessage(VipTpConstant.LIVE_TRY_PLAY_TEXT,
                            commonParam.getLangcode()));
                    playUrl = getLiveUrl(playUrl, data.getToken(), data.getUinfo(), commonParam);
                } else {
                    CheckLiveError error = tpResponse.getError();
                    if (error != null) {// 参数校验失败，boss直接不鉴权，则认为无观看权限
                        CheckLiveDto.CheckLiveErrorDto checkLiveErrorDto = new CheckLiveDto.CheckLiveErrorDto();
                        checkLiveErrorDto.setCheckLiveErrorValue(error);
                        data.setError(checkLiveErrorDto);
                        data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_PLAY_PERMISSSION);
                        LOG.info(logPrefix + ", parameter validate failure.");
                    }
                    CheckLiveTpResponse.CheckLiveInfo info = tpResponse.getInfo();
                    if (info != null) {// info中code为1004时，表示试看结束
                        CheckLiveDto.CheckLiveInfoDto checkLiveInfoDto = new CheckLiveDto.CheckLiveInfoDto();
                        checkLiveInfoDto.setCheckLiveInfoValue(info);
                        data.setInfo(checkLiveInfoDto);
                        if ("1004".equals(info.getCode())) {// 用户没有试看权限，表示试看结束
                            data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_TRY_PLAY_PERMISSSION);
                            LOG.info(logPrefix + ", user haven't try play permission of this live.");
                        } else if (VipTpConstantUtils.checkLiveHasBounghtByCode(info.getCode())) {// 已经购买过该场直播，但直播未开始
                            data.setHasPlayPermission(VipConstants.CHECK_LIVE_HAVE_PLAY_PERMISSSION);
                            LOG.info(logPrefix + ", user have play permission of this live, but the live not start.");
                        } else {
                            data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_PLAY_PERMISSSION);
                            LOG.info(logPrefix + ", user haven't play permission of this live.");
                        }
                    }
                    if (data.getHasPlayPermission() == null) {// 不是上述的情况默认为不可观看
                        data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_PLAY_PERMISSSION);
                        LOG.info(logPrefix + ", user haven't play permission of this live.");
                    }
                }
                data.setPlayUrl(playUrl);
                response.setData(data);
                response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            }
        }

        return response;
    }

    /**
     * 一键支付绑定查询接口，暂只支持查询paypal绑定
     * @param commonParam
     * @param paymentChannel
     * @param username
     * @param checkOneKeyPayRequest
     * @param locale
     * @return
     */
    public Response<CheckOneKeyPayDto> checkOneKeyPay(String username, Integer paymentChannel, CommonParam commonParam) {
        Response<CheckOneKeyPayDto> response = new Response<CheckOneKeyPayDto>();
        String userId = commonParam.getUserId();
        StringBuilder logPrefix = new StringBuilder("checkOneKeyPay_").append(username).append("_").append(userId)
                .append("_").append(commonParam.getMac()).append("_").append(paymentChannel);
        String errorCode = null;

        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isBlank(userId)) {
            validCode = VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        } else if (paymentChannel == null || (paymentChannel != 39 && paymentChannel != 41)) {// 支付渠道只支持paypal、易宝
            validCode = VipMsgCodeConstant.REUQEST_PAYMENTCHANNEL_ERROR;
        }
        if (VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            response = (Response<CheckOneKeyPayDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                    errorCode + VipUtil.parseErrorMsgCode(VipConstants.ACCOUNT_VIP, validCode),
                    commonParam.getLangcode());
        } else {
            switch (paymentChannel) {
                case VipTpConstant.PAYMENT_CHANNEL_PAYPAL:
                    CheckOneKeyPayTpResponse tpResponse = this.facadeTpDao.getVipTpDao().checkOneKeyPay(paymentChannel,
                            logPrefix.toString(), commonParam);
                    if (tpResponse == null || !tpResponse.assertSucceed()) {
                        LOG.info(logPrefix.toString() + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_ONE_KEY_PAY_FAILURE
                                + "]: check live failure.");
                        response = (Response<CheckOneKeyPayDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                                ErrorCodeConstants.PAY_CHECK_ONE_KEY_PAY_FAILURE, commonParam.getLangcode());
                    } else {
                        CheckOneKeyPayDto data = new CheckOneKeyPayDto();
                        if (tpResponse.hasBind()) {
                            data.setHasBindPaypal(VipConstants.ONE_KEY_PAY_CHECK_PAYPAL_HAS_BIND);
                            data.setPaypalAccount(tpResponse.getPaypalaccount());
                        } else {
                            data.setHasBindPaypal(VipConstants.ONE_KEY_PAY_CHECK_PAYPAL_NOT_BIND);
                        }
                        response.setData(data);
                        response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
                    }
                    break;
                case VipTpConstant.PAYMENT_CHANNEL_YEEPAYUNBIND:
                    // 缓存的key
                    // String canOneForMonth = (String) RedisUtil.get(key);
                    String canOneForMonth = this.facadeCacheDao.getVipCacheDao().getFlagBuyMonthlyPackage(userId);
                    if (VipConstants.VIP_CANNOT_ONEFORMONTH_VALUE.equals(canOneForMonth)) {
                        // 如果缓存数据中得到用户不可参加一元包月活动信息，直接返回就行，否则继续查询
                        CheckOneKeyPayDto data = new CheckOneKeyPayDto();
                        data.setUserid(commonParam.getUserId());
                        data.setIssale(false);
                        response.setData(data);
                    } else {
                        OneBindTpResponse oneBindTpResponse = this.facadeTpDao.getVipTpDao().getOneBind(
                                logPrefix.toString(), commonParam);
                        if (oneBindTpResponse != null) {
                            boolean isSale = oneBindTpResponse.getIssale();
                            CheckOneKeyPayDto data = new CheckOneKeyPayDto();
                            data.setUserid(oneBindTpResponse.getUserid());
                            data.setIssale(isSale);
                            if (!isSale) {// 将用户不可参加一元包月活动的信息缓存起来
                                // RedisUtil.setex(key,
                                // CalendarUtil.SECONDS_OF_PER_DAY,
                                // VipConstants.VIP_CANNOT_ONEFORMONTH_VALUE);
                                this.facadeCacheDao.getVipCacheDao().setFlagBuyMonthlyPackage(userId);
                            }
                            response.setData(data);
                            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
                        } else {
                            // 调用第三方接口失败
                            errorCode = ErrorCodeConstants.PAY_YEEPAY_GET_ONEBIND_FAILURE;
                            LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                            response = (Response<CheckOneKeyPayDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                                    errorCode, commonParam.getLangcode());
                        }
                    }
                    break;
                default:
                    // 校验支付渠道非法
                    errorCode = ErrorCodeConstants.PAY_YEEPAY_CHECK_BIND_TYPE_ILEGAL;
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                    response = (Response<CheckOneKeyPayDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                            errorCode, commonParam.getLangcode());
                    break;
            }
        }

        return response;
    }

    /**
     * 校验当前用户是否有权播放某一直播
     * @param commonParam
     * @param bsChannel
     * @param callback
     * @param isstart
     * @param streamId
     * @param splatId
     * @param liveid
     * @param pid
     * @param username
     * @param checkLiveRequest
     * @return
     */
    public Response<CheckLiveDto> tpsdkCheckLive(String username, String pid, String liveId, String splatId,
                                                 String streamId, Integer isstart, String bsChannel, CommonParam commonParam) {
        Response<CheckLiveDto> response = new Response<CheckLiveDto>();
        StringBuilder logPrefix = new StringBuilder("checkLive_").append(commonParam.getMac()).append("_")
                .append(liveId).append("_").append(pid).append("_").append(username).append("_")
                .append(commonParam.getUserId());

        CheckLiveTpResponse tpResponse = this.facadeTpDao.getVipTpDao().tpsdkCheckLive(pid, liveId, streamId, splatId,
                isstart, logPrefix.toString(), commonParam);
        if (tpResponse == null || tpResponse.getFailure()) {
            // 如果第一次鉴权失败，可能是因购买直播后未执行用券操作，则需执行激活直播券--重新鉴权操作
            LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                    + "]: check live permission failure 1st time.");

            if (this.tpsdkActiveUserLiveTicket(commonParam.getMac(), liveId)) {
                // 第二次鉴权
                tpResponse = this.facadeTpDao.getVipTpDao().tpsdkCheckLive(pid, liveId, streamId, splatId, isstart,
                        logPrefix.toString(), commonParam);
            } else {
                LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                        + "]: active user live ticket failure.");
            }

        }

        if (tpResponse == null || tpResponse.getFailure()) {
            // 请求失败
            LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE + "]: check live failure.");
            response = (Response<CheckLiveDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                    ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE, commonParam.getLangcode());
        } else {
            CheckLiveDto data = new CheckLiveDto();
            if (tpResponse.hasLivePlayPermission()) {
                // 请求直接返回有权限或已购买
                data.setHasPlayPermission(VipConstants.CHECK_LIVE_HAVE_PLAY_PERMISSSION);
                data.setToken(tpResponse.getToken());
                data.setUinfo(tpResponse.getUinfo());
            } else {
                data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_PLAY_PERMISSSION);
            }
            response.setData(data);
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
        }

        return response;
    }

    /**
     * 直播SDK获取订单信息
     * @param commonParam
     * @param orderId
     * @param checkOrderStatusCommonRequest
     * @param locale
     * @return
     */
    public Response<OrderStatusDto> tpsdkCheckOrderStatus(String orderId, CommonParam commonParam) {
        Response<OrderStatusDto> response = new Response<OrderStatusDto>();
        StringBuilder logPrefix = new StringBuilder("checkOrderStatus_").append(orderId).append("_")
                .append(commonParam.getMac());

        OrderStatusFromZhifuTpResponse orderStatusResponse = this.facadeTpDao.getVipTpDao().checkTpSdkOrderStatus(
                orderId, logPrefix.toString(), commonParam);
        if (orderStatusResponse == null || orderStatusResponse.getStatus() == null) {
            // TODO 校验不够全面，当返回成功时，应当校验第三方接口返回的订单号与传参订单号是否一致
            LOG.error(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_ORDER_STATUS_FAILURE
                    + "]: check roder failed.");
            response = (Response<OrderStatusDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                    ErrorCodeConstants.PAY_CHECK_ORDER_STATUS_FAILURE, commonParam.getLangcode());
        } else {
            OrderStatusDto data = new OrderStatusDto();

            if (VipTpConstant.ORDER_STATUS_API_ZHIFU_ERROR == orderStatusResponse.getStatus()) {
                data.setStatus(VipConstants.ORDER_STATUS_NOT_PAY);
            } else if (orderStatusResponse.hasPayedSuccessfully()) {
                data.setStatus(VipConstants.ORDER_STATUS_PAY_SUCCESS);
            } else {
                data.setStatus(VipConstants.ORDER_STATUS_INVALID);
            }

            data.setOrderId(orderId);
            data.setAmount(orderStatusResponse.getMoney());

            response.setData(data);
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
        }

        return response;
    }

    private String[] memberDeskUrm(int memberType) {
        String[] touchSpotId = new String[17];
        if (memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE
                || memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE_EXPIRE) {
            touchSpotId = memberDeskUrmMovie();
        } else if (memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_SPORT
                || memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_SPORT_EXPIRE) {
            touchSpotId = memberDeskUrmSport();
        } else {
            touchSpotId = memberDeskUrmNonOrMix();
        }
        return touchSpotId;
    }

    private String[] memberDeskUrmNonOrMix() {
        String[] touchSpotId = new String[17];
        touchSpotId[0] = VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[1] = VipTpConstant.URM_USER_INFO_SPORT_FOCUS_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[2] = VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[3] = VipTpConstant.URM_USER_INFO_SPORT_FOCUS_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[4] = VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_5 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;

        touchSpotId[5] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[6] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[7] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[8] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[9] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[10] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;

        touchSpotId[11] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[12] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[13] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[14] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[15] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[16] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        return touchSpotId;
    }

    private String[] memberDeskUrmSport() {
        String[] touchSpotId = new String[17];
        touchSpotId[0] = VipTpConstant.URM_USER_INFO_SPORT_FOCUS_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[1] = VipTpConstant.URM_USER_INFO_SPORT_FOCUS_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[2] = VipTpConstant.URM_USER_INFO_SPORT_FOCUS_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[3] = VipTpConstant.URM_USER_INFO_SPORT_FOCUS_4 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[4] = VipTpConstant.URM_USER_INFO_SPORT_FOCUS_5 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;

        touchSpotId[5] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[6] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[7] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[8] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_4 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[9] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_5 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[10] = VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_6 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;

        touchSpotId[11] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[12] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[13] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[14] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_4 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[15] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_5 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        touchSpotId[16] = VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_6 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_SPORT;
        return touchSpotId;
    }

    private String[] memberDeskUrmMovie() {
        String[] touchSpotId = new String[17];
        touchSpotId[0] = VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[1] = VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[2] = VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[3] = VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_4 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[4] = VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_5 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;

        touchSpotId[5] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[6] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[7] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[8] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_4 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[9] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_5 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[10] = VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_6 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;

        touchSpotId[11] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_1 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[12] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_2 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[13] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_3 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[14] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_4 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[15] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_5 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        touchSpotId[16] = VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_6 + "_"
                + VipMsgCodeConstant.VIP_TABLE_MEMBER_URM_TYPE_MOVIE;
        return touchSpotId;
    }

    private JumpData memberDeskSkipMode(MemberDeskActivityBaseData data, GetUrmActivityResponse.UrmActivityData.UrmActivityInfo tvInfo, CommonParam commonParam) {
        if (data == null || tvInfo == null) {
            return null;
        }
        JumpData jump = new JumpData();

        String jumpTo = tvInfo.getJump_to();
        int jumpType = tvInfo.getJump_type();
        if (jumpTo == null || "".equals(jumpTo) || tvInfo.getJump_info() == null) {
            return null;
        }

        Extension ext = new Extension();
        if ("1".equals(jumpTo) && (jumpType == 1 || jumpType == 4 || jumpType == 6)) {
            // 购物

            ext.setIsParse(1);
            ext.setLaunchMode(VipMsgCodeConstant.LAUNCH_MODE_BROADCAST);
            ext.setAction(VipMsgCodeConstant.ACTION_SHOPPING);
            ext.setResource("1");
            jump.setExtend(ext);

            String value = "";
            if (jumpType == 1) {
                value = "{type:1,value:{item_id:" + tvInfo.getJump_info().getItem_id() + ",category_id:\""
                        + tvInfo.getJump_info().getCategory_id() + "\",product_type:2,have_mmsid:"
                        + tvInfo.getJump_info().getHave_mmsid() + "}}";
            } else if (jumpType == 4) {
                value = "{type:4,value:{item_id:" + tvInfo.getJump_info().getItem_id() + "}}";
            } else {
                value = "{type:6,value:{item_id:" + tvInfo.getJump_info().getItem_id() + "}}";
            }
            jump.setValue(value);
        } else if ("2".equals(jumpTo)
                && (jumpType == 0 || jumpType == 1 || jumpType == 2 || jumpType == 3 || jumpType == 4 || jumpType == 7)) {
            // 体育

            jump = sportJumpData();
            String jsonVal = "";
            if (jumpType == 0) {
                jsonVal = "{type:0,source:\"lesports\",value:{jumpType:" + tvInfo.getJump_info().getJump_type() + "}}";
            } else if (jumpType == 1) {
                jsonVal = "{type:1,source:\"lesports\",value:{channelId:" + tvInfo.getJump_info().getChannelId()
                        + ",channelName:\"" + tvInfo.getJump_info().getChannelName() + "\"}}";
            } else if (jumpType == 2) {
                jsonVal = "{type:2,source:\"lesports\",value:{topicId:" + tvInfo.getJump_info().getTopicId() + "}}";
            } else if (jumpType == 3) {
                jsonVal = "{type:3,source:\"lesports\",value:{programId:" + tvInfo.getJump_info().getProgramId() + "}}";
            } else if (jumpType == 4) {
                jsonVal = "{type:4,source:\"lesports\",value:{fromTag:\"MembershipDesk\"}}";
            } else if (jumpType == 7) {
                jsonVal = "{type:7,source:\"lesports\",value:{gameId:" + tvInfo.getJump_info().getGameId()
                        + ",gameName:\"" + tvInfo.getJump_info().getGameName() + "\"}}";
            }
            Map map = new HashMap();
            map.put("burrowContent", jsonVal);
            jump.setValue(map);
            data.setJump(jump);
        } else if ("3".equals(jumpTo) && jumpType == 3) {
            // 游戏

            ext.setIsParse(1);
            ext.setLaunchMode(VipMsgCodeConstant.LAUNCH_MODE_HIDE_ACTIVITY);
            ext.setAction(ChannelConstants.JUMP_PARAMS_GAMECONTER_ACTION);
            ext.setResource("8192");
            jump.setExtend(ext);

            String jsonVal = "{type:3,typeString:\"FANYULE\",value:{id:" + tvInfo.getJump_info().getId()
                    + ",subType:\"FANYULE\"}}";
            jump.setValue(jsonVal);
            data.setJump(jump);
        } else if ("4".equals(jumpTo)
                && (jumpType == 1 || jumpType == 2 || jumpType == 3 || jumpType == 9 || jumpType == 21
                || jumpType == 23 || jumpType == 29 || jumpType == 100)) {
            // Tv版

            jump = movieJumpData();
            if (jumpType == 1) {
                // 专辑详情页
                if (!StringUtil.isInteger(tvInfo.getJump_info().getAlbumId())) {
                    return jump;
                }

                AlbumDto value = new AlbumDto();
                value.setAlbumId(tvInfo.getJump_info().getAlbumId());
                value.setSrc(1);
                // AlbumMysqlTable table =
                // this.facadeService.getVideoService().getAlbumById(
                // Long.valueOf(tvInfo.getJump_info().getAlbumId()), null);
                AlbumMysqlTable table = albumVideoAccess.getAlbum(
                        StringUtil.toLong(tvInfo.getJump_info().getAlbumId()), commonParam);
                if (table != null && table.getPlay_platform() != null) {
                    value.setTvCopyright(table.getPlay_platform().contains("420007") ? 1 : 0);
                } else {
                    value.setTvCopyright(0);
                }

                JumpData jumpData = new JumpData();
                jumpData.setType(1);
                jumpData.setValue(value);
                jump.setValue(jumpData);

                data.setJump(jump);
            } else if (jumpType == 2) {
                // 视频
            } else if (jumpType == 3) {
                // 专题页
                SubjectDto value = new SubjectDto();
                value.setDataType(3);
                value.setSubjectId(tvInfo.getJump_info().getSubjectId());
                value.setSubjectType(Integer.valueOf(tvInfo.getJump_info().getSubjectType()));

                JumpData jumpData = new JumpData();
                jumpData.setType(3);
                jumpData.setValue(value);
                jump.setValue(jumpData);

                data.setJump(jump);
            } else if (jumpType == 9) {
                // H5页面
                Browser value = new Browser();
                value.setBrowserType(2);
                value.setUrl(tvInfo.getJump_info().getUrl());

                JumpData jumpData = new JumpData();
                jumpData.setType(9);
                jumpData.setValue(value);
                jump.setValue(jumpData);

                data.setJump(jump);
            } else if (jumpType == 21) {
                // 定向收银台
                // urm不支持
            } else if (jumpType == 23) {
                // 919活动页
                PilotDto value = new PilotDto();
                value.setBlockId(tvInfo.getJump_info().getBlockId());

                JumpData jumpData = new JumpData();
                jumpData.setType(23);
                jumpData.setValue(value);
                jump.setValue(jumpData);

                data.setJump(jump);
            } else if (jumpType == 29) {
                // 视频营销
                PilotDto value = new PilotDto();
                value.setBlockId(tvInfo.getJump_info().getBlockId());

                JumpData jumpData = new JumpData();
                jumpData.setType(29);
                jumpData.setValue(value);
                jump.setValue(jumpData);

                data.setJump(jump);
            } else if (jumpType == 100) {
                // 会员白条
            }
        }

        return jump;
    }

    public static final Map<String, String> imageKeyMap = new HashMap<String, String>();

    static {
        // 焦点图
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_1, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_1);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_2, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_2);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_3, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_3);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_4, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_4);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_5, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_FOCUS_5);

        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_FOCUS_1, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_FOCUS_1);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_FOCUS_2, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_FOCUS_2);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_FOCUS_3, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_FOCUS_3);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_FOCUS_4, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_FOCUS_4);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_FOCUS_5, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_FOCUS_5);

        // 权益
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_1, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_1);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_2, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_2);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_3, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_3);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_4, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_4);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_5, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_5);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_6, "tv_" + VipTpConstant.URM_USER_INFO_MOVIE_RIGHTS_6);

        imageKeyMap
                .put(VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_1, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_1);
        imageKeyMap
                .put(VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_2, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_2);
        imageKeyMap
                .put(VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_3, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_3);
        imageKeyMap
                .put(VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_4, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_4);
        imageKeyMap
                .put(VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_5, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_5);
        imageKeyMap
                .put(VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_6, "gym_" + VipTpConstant.URM_USER_INFO_SPORT_RIGHTS_6);

        // 活动
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_1, "tv_"
                + VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_1);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_2, "tv_"
                + VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_2);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_3, "tv_"
                + VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_3);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_4, "tv_"
                + VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_4);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_5, "tv_"
                + VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_5);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_6, "tv_"
                + VipTpConstant.URM_USER_INFO_MOVIE_ACTIVITY_6);

        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_1, "gym_"
                + VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_1);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_2, "gym_"
                + VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_2);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_3, "gym_"
                + VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_3);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_4, "gym_"
                + VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_4);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_5, "gym_"
                + VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_5);
        imageKeyMap.put(VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_6, "gym_"
                + VipTpConstant.URM_USER_INFO_SPORT_ACTIVITY_6);
    }

    private void urmActivityJumpData(int memberType, Integer deviceType, CommonParam commonParam,
                                     MemberDeskActivity baseData, List<MemberDeskActivityBaseData> recommendList,
                                     List<MemberDeskActivityBaseData> rightsList, List<MemberDeskActivityBaseData> activitiesList) {
        String positions = "";
        String[] positionArray = memberDeskUrm(memberType);
        for (String position : positionArray) {
            positions = positions + "," + position.split("_")[0];
        }
        if ("".equals(positions)) {
            return;
        }

        GetUrmActivityBatchResponse responses = this.facadeTpDao.getVipTpDao().getUrmTouchDataList(
                positions.substring(1), /* 去掉第一个"," */
                deviceType, commonParam);
        if (responses == null || responses.getMessages() == null) {
            return;
        }
        int cycleIndex = 0;
        int index = 0;
        for (GetUrmActivityListResponse tpResponse : responses.getMessages()) {
            MemberDeskActivityBaseData data = new MemberDeskActivityBaseData();
            data.setDataType(Integer.valueOf(positionArray[cycleIndex].split("_")[1]));
            if (tpResponse != null) {
                data.setImage(tpResponse.getCreatives().get(0).getShow_info()
                        .get(imageKeyMap.get(tpResponse.getTouchSpotId()) + "_image1"));
                if (tpResponse.getCreatives() != null && tpResponse.getCreatives().get(0) != null
                        && tpResponse.getCreatives().get(0).getTv_info() != null) {
                    data.setJump(memberDeskSkipMode(data, tpResponse.getCreatives().get(0).getTv_info(), commonParam));
                }

                data.setReporting(tpResponse.getReporting());
                if (cycleIndex <= 4) {
                    if (cycleIndex == 0) {
                        index = 0;
                    }

                    if (tpResponse.getCreatives() != null && tpResponse.getCreatives().get(0) != null
                            && tpResponse.getCreatives().get(0).getShow_info() != null
                            && tpResponse.getTouchSpotId() != null) {
                        data.setSmallImage(tpResponse.getCreatives().get(0).getShow_info()
                                .get(imageKeyMap.get(tpResponse.getTouchSpotId()) + "_image2"));
                    }
                    recommendList.add(data);
                } else if (cycleIndex <= 10) {
                    if (cycleIndex == 5) {
                        index = 0;
                    }

                    rightsList.add(data);
                } else {
                    if (cycleIndex == 11) {
                        index = 0;
                    }

                    activitiesList.add(data);
                }
            } else {
                if (cycleIndex <= 4) {
                    if (cycleIndex == 0) {
                        index = 0;
                    }

                    recommendList.add(data);
                } else if (cycleIndex <= 10) {
                    if (cycleIndex == 5) {
                        index = 0;
                    }

                    rightsList.add(data);
                } else {
                    if (cycleIndex == 11) {
                        index = 0;
                    }

                    activitiesList.add(data);
                }
            }
            data.setIndex(index);
            cycleIndex++;
            index++;
        }
        baseData.setActivitiesList(activitiesList);
        baseData.setRecommendList(recommendList);
        baseData.setRightsList(rightsList);
    }

    /**
     * 获取会员桌面用户信息跳转逻辑
     * @param username
     * @param deviceKey
     *            是机卡机器的暗码，通过机器rom可以获取
     * @param deviceType
     * @param type
     * @param commonParam
     * @return
     */
    public Response<BaseData> memberDeskUrmActivity(Integer deviceType, CommonParam commonParam) {
        Response<BaseData> response = new Response<BaseData>();

        String errorCode = null;
        StringBuilder logPrefix = new StringBuilder("memberDeskUrmActivity").append("_")
                .append(commonParam.getUserId()).append("_").append(commonParam.getMac());
        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (commonParam.getMac() == null || "".equals(commonParam.getMac())) {
            /* 参数校验 */
            validCode = VipMsgCodeConstant.REQUEST_DEVICEKEY_EMPTY;
            errorCode = ErrorCodeConstants.VIP_MEMBER_DESK_ACTIVITY_NULL_MAC;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
            response = (Response<BaseData>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode, errorCode
                    + VipUtil.parseErrorMsgCode(VipConstants.ACCOUNT_VIP, validCode), commonParam.getLangcode());
        } else {
            MemberDeskActivity baseData = new MemberDeskActivity();

            List<MemberDeskActivityBaseData> recommendList = new ArrayList<MemberDeskActivityBaseData>();
            List<MemberDeskActivityBaseData> rightsList = new ArrayList<MemberDeskActivityBaseData>();
            List<MemberDeskActivityBaseData> activitiesList = new ArrayList<MemberDeskActivityBaseData>();
            if (StringUtils.isBlank(commonParam.getUserId())) {
                // 没登录

                urmActivityJumpData(VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_NOT_LOGIN, deviceType, commonParam,
                        baseData, recommendList, rightsList, activitiesList);
                response.setData(baseData);
            } else {
                // 登录了

                if (!StringUtil.isInteger(commonParam.getUserId())) {
                    validCode = VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
                    errorCode = ErrorCodeConstants.VIP_MEMBER_DESK_INFO_USERID;
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                    response = (Response<BaseData>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                            errorCode + VipUtil.parseErrorMsgCode(VipConstants.ACCOUNT_VIP, validCode),
                            commonParam.getLangcode());
                } else {
                    Response<UserAccountDto> userInfoResponse = getVipAccount(null, null, false, commonParam);
                    UserAccountDto userInfo = userInfoResponse.getData();

                    /* 参数校验 */
                    if (userInfo == null) {
                        validCode = VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
                        errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
                        LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                        response = (Response<BaseData>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                                errorCode + VipUtil.parseErrorMsgCode(VipConstants.ACCOUNT_VIP, validCode),
                                commonParam.getLangcode());
                    } else {
                        if ((userInfo.isSportVip() == null || userInfo.isSportVip() == VipConstants.SPORT_VIP_NON)
                                && (userInfo.getIsvip() != null && userInfo.getIsvip() == VipConstants.MOVIE_VIP_SUPER || userInfo
                                .getSeniorendtime() != null)) {
                            // 影视会员
                            urmActivityJumpData(VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE, deviceType,
                                    commonParam, baseData, recommendList, rightsList, activitiesList);
                        } else if ((userInfo.getIsvip() == null || userInfo.getIsvip() == VipConstants.MOVIE_VIP_NON)
                                && userInfo.isSportVip() != null
                                && (userInfo.isSportVip() == VipConstants.SPORT_VIP || userInfo.isSportVip() == VipConstants.SPORT_VIP_EXPIRE)) {
                            // 体育会员
                            urmActivityJumpData(VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_SPORT, deviceType,
                                    commonParam, baseData, recommendList, rightsList, activitiesList);
                        } else {
                            // 其他
                            urmActivityJumpData(VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_NON, deviceType, commonParam,
                                    baseData, recommendList, rightsList, activitiesList);
                        }
                        response.setData(baseData);
                    }
                }
            }
        }
        return response;
    }

    private JumpData movieJumpData() {
        JumpData jumpData = new JumpData();
        Extension extension = new Extension();
        extension.setIsParse(0);
        extension.setLaunchMode(VipMsgCodeConstant.LAUNCH_MODE_HIDE_ACTIVITY);
        extension.setAction(VipMsgCodeConstant.ACTION_MOVIE_MEMBER);
        extension.setResource("1");
        jumpData.setExtend(extension);
        return jumpData;
    }

    private void moviePrivilege(MemberDeskInfoBaseData baseData, String privilegeText) {
        baseData.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);
        baseData.setImg_index(VipMsgCodeConstant.VIP_TABLE_TYPE_PRIVILEGE);

        SubjectDto value = new SubjectDto();
        value.setDataType(3);
        value.setSubjectType(SubjectConstant.SUBJECT_TYPE_VIDEO);
        value.setSubjectId("7194");

        JumpData jumpData = new JumpData();
        jumpData.setType(3);
        jumpData.setValue(value);

        JumpData jump = movieJumpData();
        jump.setValue(jumpData);
        baseData.setJump(jump);
    }

    private void sportChannel(MemberDeskInfoBaseData baseData, String channelText) {
        baseData.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);
        baseData.setImg_index(VipMsgCodeConstant.VIP_TABLE_TYPE_CHANNEL);

        JumpData jumpData = sportJumpData();

        Map map = new HashMap();
        String jsonVal = "{type:0,source:\"lesports\",value:{jumpType:6}}";
        map.put("burrowContent", jsonVal);
        jumpData.setValue(map);

        baseData.setJump(jumpData);
    }

    /**
     * 获取会员桌面用户信息跳转逻辑
     * @param username
     * @param deviceKey
     *            是机卡机器的暗码，通过机器rom可以获取
     * @param deviceType
     * @param type
     * @param commonParam
     * @return
     */
    public Response<BaseData> memberDeskInfo(String deviceKey, Integer deviceType, String type, CommonParam commonParam) {
        Response<BaseData> response = new Response<BaseData>();

        // 调用第三方接口，鉴别机卡绑定套餐信息
        Integer[] deviceInfo = new Integer[2];
        deviceInfo[0] = 0;
        deviceInfo[1] = 0;
        if (commonParam.getDeviceKey() != null) {
            DeviceBindTpResponse deviceBindTp = this.facadeTpDao.getVipTpDao().getDeviceBindInfo(deviceType,
                    commonParam);

            if (deviceBindTp != null) {
                // 设备激活信息，只与设备有关，数组1是影视，2是体育
                // 0-是没查询到，1未激活，2是已激活
                List<DeviceBindTpResponse.DeviceBindConent.DeviceInfo> deviceInfoItems = deviceBindTp.getValues().getItems();

                if (deviceInfoItems != null) {
                    for (int i = 0; i < deviceInfoItems.size(); i++) {
                        DeviceBindTpResponse.DeviceBindConent.DeviceInfo info = deviceInfoItems.get(i);
                        if (info == null || info.getVipType() == null) {
                            continue;
                        }
                        if (info.getVipType() == 2) {
                            // 影视
                            if (info.getIsActived() != null && info.getIsActived()) {
                                deviceInfo[0] = 2;
                            } else {
                                deviceInfo[0] = 1;
                            }
                        } else if (info.getVipType() == 3) {
                            // 体育
                            if (info.getIsActived() != null && info.getIsActived()) {
                                deviceInfo[1] = 2;
                            } else {
                                deviceInfo[1] = 1;
                            }
                        }
                    }
                }
            }
        }

        String movieText = MessageUtils.getMessage("member_table_movie", commonParam.getLangcode());
        String sportText = MessageUtils.getMessage("member_table_sport", commonParam.getLangcode());
        String privilegeText = MessageUtils.getMessage("member_table_privilege", commonParam.getLangcode());
        String channelText = MessageUtils.getMessage("member_table_channel", commonParam.getLangcode());
        String memberText = MessageUtils.getMessage("member_table_member", commonParam.getLangcode());

        // -1->既不是影视会员又不是体育会员 || 未登录
        // 0 ->影视
        // 1 ->体育
        // 2 ->既是影视会员又是体育会员 || 都过期会员
        int memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_NON;

        long currentTime = System.currentTimeMillis();
        UserAccountDto userInfo = null;

        final int ITEM_NUM = 4;
        MemberDeskInfo baseData = new MemberDeskInfo();
        List<MemberDeskInfoBaseData> jumpList = new ArrayList<MemberDeskInfoBaseData>();
        if (StringUtils.isBlank(commonParam.getUserId())) {
            // 没登录
            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_NOT_LOGIN;

            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);
            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);
            for (int i = 0; i < ITEM_NUM; i++) {
                MemberDeskInfoBaseData data = new MemberDeskInfoBaseData();
                data.setIndex(i);
                if (i == 0) {
                    // 影视->登录
                    data.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_SKIP);
                    data.setImg_index(VipMsgCodeConstant.VIP_TABLE_IMG_TYPE_MONEY);
                    data.setBusinessString(movieText + memberText + "|"
                            + MessageUtils.getMessage("member_table_login", commonParam.getLangcode()));
                } else if (i == 1) {
                    // 体育->登录
                    data.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_SKIP);
                    data.setImg_index(VipMsgCodeConstant.VIP_TABLE_IMG_TYPE_MONEY);
                    data.setBusinessString(sportText + memberText + "|"
                            + MessageUtils.getMessage("member_table_login", commonParam.getLangcode()));
                } else if (i == 2) {
                    // 影视会员特权

                    data.setBusinessString(movieText + memberText + "|" + privilegeText);
                    moviePrivilege(data, privilegeText);
                } else if (i == 3) {
                    // 体育会员频道

                    data.setBusinessString(sportText + memberText + "|" + channelText);
                    sportChannel(data, channelText);
                }
                jumpList.add(data);
            }
            setShowContent(memberType, currentTime, sportText, movieText, userInfo, commonParam, baseData, deviceType);

            baseData.setDataList(jumpList);
            response.setData(baseData);
        } else {
            // 登录了

            /* 参数校验 */
            String errorCode = null;
            StringBuilder logPrefix = new StringBuilder("memberDeskInfo").append("_").append(commonParam.getUserId())
                    .append("_").append(commonParam.getMac());
            int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;

            if (!StringUtil.isInteger(commonParam.getUserId())) {
                validCode = VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
                errorCode = ErrorCodeConstants.VIP_MEMBER_DESK_INFO_USERID;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                response = (Response<BaseData>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                        errorCode + VipUtil.parseErrorMsgCode(VipConstants.ACCOUNT_VIP, validCode),
                        commonParam.getLangcode());
            } else {
                Response<UserAccountDto> userInfoResponse = getVipAccount(deviceKey, deviceType, false, commonParam);
                userInfo = userInfoResponse.getData();

                if (userInfo == null) {
                    validCode = VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
                    errorCode = ErrorCodeConstants.VIP_MEMBER_DESK_INFO_NON_USER;
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                    response = (Response<BaseData>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                            errorCode + VipUtil.parseErrorMsgCode(VipConstants.ACCOUNT_VIP, validCode),
                            commonParam.getLangcode());
                } else {
                    String mineText = MessageUtils.getMessage("member_table_mine", commonParam.getLangcode());

                    if (((/* 激活状态 */deviceInfo[0] == 0 && deviceInfo[1] == 0)
                            || (deviceInfo[0] == 2 && deviceInfo[1] == 2) || (deviceInfo[0] == 0 && deviceInfo[1] == 2) || (deviceInfo[0] == 2 && deviceInfo[1] == 0))

                            && (userInfo.isSportVip() == null || userInfo.isSportVip() == VipConstants.SPORT_VIP_NON)

                            && (userInfo.getIsvip() != null && userInfo.getIsvip() == VipConstants.MOVIE_VIP_SUPER || userInfo
                            .getSeniorendtime() != null)) {
                        // 不是体育会员 && (仅此影视会员 || 仅此影视过期)

                        if (userInfo.getIsvip() != VipConstants.MOVIE_VIP_SUPER && userInfo.getSeniorendtime() != null) {
                            // 影视会员过期了
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE_EXPIRE;

                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_EXPIRE);
                        } else {
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE;

                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                        }

                        baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);

                        for (int i = 0; i < ITEM_NUM; i++) {
                            MemberDeskInfoBaseData data = new MemberDeskInfoBaseData();
                            data.setIndex(i);
                            if (i == 0) {
                                // 会员续费 ->收银台
                                // 激活 ->机卡绑定

                                movieOperation(data, userInfo, deviceInfo, commonParam, memberText, movieText);
                            } else if (i == 1) {
                                // 我的消息
                                data.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);
                                data.setImg_index(VipMsgCodeConstant.VIP_TABLE_TYPE_MESSAGE);
                                data.setBusinessString(mineText + "|"
                                        + MessageUtils.getMessage("member_table_message", commonParam.getLangcode()));

                                JumpData jumpData = new JumpData();
                                Extension extension = new Extension();
                                extension.setLaunchMode(VipMsgCodeConstant.LAUNCH_MODE_HIDE_ACTIVITY);
                                extension.setIsParse(1);
                                extension.setAction(VipMsgCodeConstant.VIP_TABLE_MOVIE_MESSAGE_ACTION);
                                jumpData.setExtend(extension);

                                PilotDto value = new PilotDto();
                                value.setFrom("com.stv.plugin.membership");
                                value.setType(1);// 1, 表示跳首页
                                jumpData.setValue(value);

                                data.setJump(jumpData);
                            } else if (i == 2) {
                                // 会员特权

                                data.setBusinessString(memberText + "|" + privilegeText);
                                moviePrivilege(data, privilegeText);
                            } else if (i == 3) {
                                // 会员频道
                                data.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);
                                data.setImg_index(VipMsgCodeConstant.VIP_TABLE_TYPE_CHANNEL);
                                data.setBusinessString(memberText + "|" + channelText);

                                JumpData jumpData = movieJumpData();

                                JumpData value = new JumpData();
                                value.setType(49);
                                value.setValue(new HashMap());
                                jumpData.setValue(value);

                                data.setJump(jumpData);
                            }
                            jumpList.add(data);
                        }
                    } else if (((/* 激活状态 */deviceInfo[0] == 0 && deviceInfo[1] == 0)
                            || (deviceInfo[0] == 2 && deviceInfo[1] == 2) || (deviceInfo[0] == 0 && deviceInfo[1] == 2) || (deviceInfo[0] == 2 && deviceInfo[1] == 0))

                            && (userInfo.getIsvip() == null || userInfo.getIsvip() == VipConstants.MOVIE_VIP_NON)

                            && (userInfo.isSportVip() != null && (userInfo.isSportVip() == VipConstants.SPORT_VIP || userInfo
                            .isSportVip() == VipConstants.SPORT_VIP_EXPIRE))) {
                        // 不是影视会员 && (仅此体育会员 || 仅此体育过期)
                        if (userInfo.isSportVip() == VipConstants.SPORT_VIP_EXPIRE) {
                            // 体育会员过期了
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_SPORT_EXPIRE;

                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_EXPIRE);
                        } else {
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_SPORT;

                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                        }

                        baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);

                        for (int i = 0; i < ITEM_NUM; i++) {
                            MemberDeskInfoBaseData data = new MemberDeskInfoBaseData();
                            data.setIndex(i);
                            if (i == 0) {
                                // 体育侧仅此消费记录->用户中心
                                data.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);
                                data.setImg_index(VipMsgCodeConstant.VIP_TABLE_IMG_TYPE_MONEY);
                                data.setBusinessString(MessageUtils.getMessage("member_table_consume",
                                        commonParam.getLangcode())
                                        + "|"
                                        + MessageUtils.getMessage("member_table_record", commonParam.getLangcode()));

                                JumpData jumpData = sportJumpData();
                                Map map = new HashMap();
                                String jsonVal = "{type:9,source:\"lesports\",value:{fromTag:\"MembershipDesk\"}}";
                                map.put("burrowContent", jsonVal);
                                jumpData.setValue(map);
                                data.setJump(jumpData);
                            } else if (i == 1) {
                                // 会员活动
                                data.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);
                                data.setImg_index(VipMsgCodeConstant.VIP_TABLE_TYPE_ACTIVITY);
                                data.setBusinessString(memberText + "|"
                                        + MessageUtils.getMessage("member_table_activity", commonParam.getLangcode()));

                                sportMemberActivityOrLive(data, 101);
                            } else if (i == 2) {
                                // 会员直播
                                data.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);
                                data.setImg_index(VipMsgCodeConstant.VIP_TABLE_TYPE_LIVE);
                                data.setBusinessString(memberText + "|"
                                        + MessageUtils.getMessage("member_table_live", commonParam.getLangcode()));

                                sportMemberActivityOrLive(data, 99);
                            } else if (i == 3) {
                                // 会员频道

                                data.setBusinessString(memberText + "|" + channelText);
                                sportChannel(data, channelText);
                            }
                            jumpList.add(data);
                        }
                    } else if (deviceInfo[0] == 1/* 影视未激活 */|| deviceInfo[1] == 1/* 体育未激活 */) {
                        // 激活状态判断

                        memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_BOTH;

                        // 判断体育
                        if (userInfo.isSportVip() != null) {
                            if (userInfo.isSportVip() == VipConstants.SPORT_VIP) {
                                memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_SPORT;
                                baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                            } else if (userInfo.isSportVip() == VipConstants.SPORT_VIP_EXPIRE) {
                                baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_EXPIRE);
                            } else if (userInfo.isSportVip() == VipConstants.SPORT_VIP_NON) {
                                baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);
                            }
                        } else {
                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);
                        }

                        // 判断影视
                        if (userInfo.getIsvip() != null) {
                            if (userInfo.getIsvip() == VipConstants.MOVIE_VIP_SUPER) {
                                memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE;
                                baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                            } else if (userInfo.getSeniorendtime() != null) {
                                baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_EXPIRE);
                            } else if (userInfo.getIsvip() == VipConstants.MOVIE_VIP_NON) {
                                baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);
                            }
                        } else {
                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);
                        }

                        // 判断综合
                        if (userInfo.isSportVip() != null && userInfo.getIsvip() != null
                                && userInfo.isSportVip() == VipConstants.SPORT_VIP
                                && userInfo.getIsvip() == VipConstants.MOVIE_VIP_SUPER) {
                            // 既是影视会员又是体育会员
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_BOTH;

                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                        }

                        memberDeskInfoCommon(ITEM_NUM, userInfo, deviceInfo, jumpList, commonParam, sportText,
                                movieText, memberText, privilegeText, channelText);
                    } else {
                        if ((userInfo.getIsvip() != null && userInfo.getIsvip() != VipConstants.MOVIE_VIP_SUPER && userInfo
                                .getSeniorendtime() != null)
                                && (userInfo.isSportVip() != null && userInfo.isSportVip() == VipConstants.SPORT_VIP_EXPIRE)) {
                            // 都过期了
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_BOTH_EXPIRE;

                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_EXPIRE);
                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_EXPIRE);
                        } else if (userInfo.isSportVip() != null && userInfo.getIsvip() != null
                                && userInfo.isSportVip() == VipConstants.SPORT_VIP
                                && userInfo.getIsvip() == VipConstants.MOVIE_VIP_SUPER) {
                            // 既是影视会员又是体育会员
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_BOTH;

                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                        } else if ((userInfo.getIsvip() == null || userInfo.getIsvip() != VipConstants.MOVIE_VIP_SUPER/* 非影视 */)
                                && (userInfo.isSportVip() == null || userInfo.isSportVip() == VipConstants.SPORT_VIP_NON/* 非体育 */)) {
                            // 既不是影视会员又不是体育会员
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_NON;

                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);
                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_NON);
                        } else if (userInfo.isSportVip() != null && userInfo.getIsvip() != null
                                && userInfo.isSportVip() == VipConstants.SPORT_VIP_EXPIRE
                                && userInfo.getIsvip() == VipConstants.MOVIE_VIP_SUPER) {
                            // 是影视会员，体育会员过期了
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_SPORT_EXPIRE;

                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_EXPIRE);
                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                        } else if (userInfo.isSportVip() != null && userInfo.getIsvip() != null
                                && userInfo.isSportVip() == VipConstants.SPORT_VIP
                                && userInfo.getIsvip() != VipConstants.MOVIE_VIP_SUPER
                                && userInfo.getSeniorendtime() != null) {
                            // 是体育会员，影视会员过期了
                            memberType = VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE_EXPIRE;

                            baseData.setSportMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_YES);
                            baseData.setMovieMember(VipMsgCodeConstant.VIP_TABLE_MEMBER_STATUS_EXPIRE);
                        }
                        memberDeskInfoCommon(ITEM_NUM, userInfo, deviceInfo, jumpList, commonParam, sportText,
                                movieText, memberText, privilegeText, channelText);
                    }
                }
                setShowContent(memberType, currentTime, sportText, movieText, userInfo, commonParam, baseData,
                        deviceType);

                baseData.setDataList(jumpList);
                response.setData(baseData);
            }
        }
        return response;
    }

    private void memberDeskInfoCommon(int ITEM_NUM, UserAccountDto userInfo, Integer[] deviceInfo,
                                      List<MemberDeskInfoBaseData> jumpList, CommonParam commonParam, String sportText, String movieText,
                                      String memberText, String privilegeText, String channelText) {
        for (int i = 0; i < ITEM_NUM; i++) {
            MemberDeskInfoBaseData data = new MemberDeskInfoBaseData();
            data.setIndex(i);

            if (i == 0) {
                // 影视位

                movieOperation(data, userInfo, deviceInfo, commonParam, memberText, movieText);
            } else if (i == 1) {
                // 体育位

                sportOperation(data, userInfo, deviceInfo, commonParam, memberText, sportText);
            } else if (i == 2) {
                // 影视会员 特权

                data.setBusinessString(movieText + memberText + "|" + privilegeText);
                moviePrivilege(data, privilegeText);
            } else if (i == 3) {
                // 体育会员 频道

                data.setBusinessString(sportText + memberText + "|" + channelText);
                sportChannel(data, channelText);
            }
            jumpList.add(data);
        }
    }

    private JumpData sportJumpData() {
        JumpData jumpData = new JumpData();
        Extension extension = new Extension();
        extension.setIsParse(1);
        extension.setLaunchMode(VipMsgCodeConstant.LAUNCH_MODE_HIDE_ACTIVITY);
        extension.setAction(VipMsgCodeConstant.ACTION_SPORT);
        jumpData.setExtend(extension);
        return jumpData;
    }

    private void sportMemberActivityOrLive(MemberDeskInfoBaseData baseData, int pageIndex) {
        JumpData jumpData = sportJumpData();

        Map map = new HashMap();
        String jsonVal = "{type:12,source:\"lesports\",value:{pageIndex:" + pageIndex + "}}";
        map.put("burrowContent", jsonVal);
        jumpData.setValue(map);

        baseData.setJump(jumpData);
    }

    private void setShowContent(int memberType, long currentTime, String sportText, String movieText,
                                UserAccountDto userInfo, CommonParam commonParam, MemberDeskInfo data, Integer deviceType) {
        // 判断显示信息
        long time = 0;

        String superText = MessageUtils.getMessage("member_table_super", commonParam.getLangcode());

        if (userInfo == null || memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_NOT_LOGIN) {
            data.setShowContent(MessageUtils.getMessage("member_table_wonderful_content", commonParam.getLangcode()));
        } else {
            // 0->初始化
            // 1->会员已过期30天
            // 2->会员已过期
            // 3->会员30天后即将过期
            // 4->会员超过30天后才过期
            int movieType = 0;
            int sportType = 0;
            if (userInfo.getSeniorendtime() != null) {
                // 影视会员过期了
                time = userInfo.getSeniorendtime();

                int days = (int) ((currentTime - time) / (1000 * 60 * 60 * 24));
                if (days > 0) {
                    // 过期了

                    if (days < 30) {
                        // 超级影视会员已过期30天
                        movieType = 1;
                    } else {
                        // 超级影视会员已过期
                        movieType = 2;
                    }
                } else {
                    // 没过期

                    if (days > -30) {
                        // 超级影视会员30天后即将过期
                        movieType = 3;
                    } else {
                        movieType = 4;
                    }
                }
            }
            if (userInfo.getSportendtime() != null) {
                // 体育会员过期了
                time = userInfo.getSportendtime();

                int days = (int) ((currentTime - time) / (1000 * 60 * 60 * 24));
                Object[] codeMap = { superText };
                if (days > 0) {
                    // 过期了

                    if (days < 30) {
                        // 超级体育会员已过期30天
                        sportType = 1;
                    } else {
                        // 超级体育会员已过期
                        sportType = 2;
                    }
                } else {
                    // 没过期

                    if (days > -30) {
                        // 超级体育会员30天后即将过期
                        sportType = 3;
                    } else {
                        sportType = 4;
                    }
                }
            }

            Object[] movieCodeMap = { superText + movieText };
            Object[] sportCodeMap = { superText + sportText };

            String content = "";
            if (movieType == 1 && sportType == 1) {
                content = MessageUtils.getMessage("member_table_expire_30", commonParam.getLangcode(), movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_expire_30", commonParam.getLangcode(), sportCodeMap);
            } else if (movieType == 1 && sportType == 2) {
                content = MessageUtils.getMessage("member_table_expire_30", commonParam.getLangcode(), movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_already_expire", commonParam.getLangcode(),
                        sportCodeMap);
            } else if (movieType == 1 && sportType == 3) {
                content = MessageUtils.getMessage("member_table_expire_30", commonParam.getLangcode(), movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_will_expire", commonParam.getLangcode(), sportCodeMap);
            } else if (movieType == 2 && sportType == 1) {
                content = MessageUtils.getMessage("member_table_already_expire", commonParam.getLangcode(),
                        movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_expire_30", commonParam.getLangcode(), sportCodeMap);
            } else if (movieType == 2 && sportType == 2) {
                content = MessageUtils.getMessage("member_table_already_expire", commonParam.getLangcode(),
                        movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_already_expire", commonParam.getLangcode(),
                        sportCodeMap);
            } else if (movieType == 2 && sportType == 3) {
                content = MessageUtils.getMessage("member_table_already_expire", commonParam.getLangcode(),
                        movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_will_expire", commonParam.getLangcode(), sportCodeMap);
            } else if (movieType == 3 && sportType == 1) {
                content = MessageUtils.getMessage("member_table_will_expire", commonParam.getLangcode(), movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_expire_30", commonParam.getLangcode(), sportCodeMap);
            } else if (movieType == 3 && sportType == 2) {
                content = MessageUtils.getMessage("member_table_will_expire", commonParam.getLangcode(), movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_already_expire", commonParam.getLangcode(),
                        sportCodeMap);
            } else if (movieType == 3 && sportType == 3) {
                content = MessageUtils.getMessage("member_table_will_expire", commonParam.getLangcode(), movieCodeMap)
                        + "|"
                        + MessageUtils.getMessage("member_table_will_expire", commonParam.getLangcode(), sportCodeMap);
            } else if ((movieType == 4 || movieType == 0) && sportType == 1) {
                content = MessageUtils.getMessage("member_table_expire_30", commonParam.getLangcode(), sportCodeMap);
            } else if ((movieType == 4 || movieType == 0) && sportType == 2) {
                content = MessageUtils.getMessage("member_table_already_expire", commonParam.getLangcode(),
                        sportCodeMap);
            } else if ((movieType == 4 || movieType == 0) && sportType == 3) {
                content = MessageUtils.getMessage("member_table_will_expire", commonParam.getLangcode(), sportCodeMap);
            } else if (movieType == 1 && (sportType == 4 || sportType == 0)) {
                content = MessageUtils.getMessage("member_table_expire_30", commonParam.getLangcode(), movieCodeMap);
            } else if (movieType == 2 && (sportType == 4 || sportType == 0)) {
                content = MessageUtils.getMessage("member_table_already_expire", commonParam.getLangcode(),
                        movieCodeMap);
            } else if (movieType == 3 && (sportType == 4 || sportType == 0)) {
                content = MessageUtils.getMessage("member_table_will_expire", commonParam.getLangcode(), movieCodeMap);
            } else if ((movieType == 4 || movieType == 0) && (sportType == 4 || sportType == 0)) {
                // 既是影视又是体育且过期时间均大于30天
                content = getOperation(memberType, deviceType, commonParam);
            }
            data.setShowContent(content);
        }
    }

    private void sportOperation(MemberDeskInfoBaseData baseData, UserAccountDto userInfo, Integer[] deviceInfo,
                                CommonParam commonParam, String memberText, String sportText) {
        baseData.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);

        baseData.setImg_index(VipMsgCodeConstant.VIP_TABLE_IMG_TYPE_MONEY);
        if (deviceInfo != null && deviceInfo[1] == 1/* 没激活 */) {
            // 没激活-机卡绑定

            JumpData jumpData = new JumpData();
            machineCardBinding(baseData, jumpData, memberText, commonParam, sportText);
            baseData.setJump(jumpData);
        } else {
            if (userInfo.isSportVip() != null && userInfo.isSportVip() == VipConstants.SPORT_VIP_EXPIRE
                    || userInfo.isSportVip() == VipConstants.SPORT_VIP) {
                baseData.setBusinessString(sportText + memberText + "|"
                        + MessageUtils.getMessage("member_table_renew", commonParam.getLangcode()));
            } else {
                baseData.setBusinessString(sportText + memberText + "|"
                        + MessageUtils.getMessage("member_table_open", commonParam.getLangcode()));
            }

            // 收银台
            JumpData jumpData = sportJumpData();

            Map map = new HashMap();
            String jsonVal = "{type:4,source:\"lesports\",value:{fromTag:\"MembershipDesk\"}}";
            map.put("burrowContent", jsonVal);
            jumpData.setValue(map);
            baseData.setJump(jumpData);
        }
    }

    private void movieOperation(MemberDeskInfoBaseData baseData, UserAccountDto userInfo, Integer[] deviceInfo,
                                CommonParam commonParam, String memberText, String movieText) {
        baseData.setDataType(VipMsgCodeConstant.VIP_TABLE_TYPE_BURROW);

        baseData.setImg_index(VipMsgCodeConstant.VIP_TABLE_IMG_TYPE_MONEY);
        JumpData jumpData = new JumpData();
        if (deviceInfo != null && deviceInfo[0] == 1/* 没激活 */) {
            // 没激活-机卡绑定
            machineCardBinding(baseData, jumpData, memberText, commonParam, movieText);
        } else {
            if (userInfo.getIsvip() != null && userInfo.getIsvip() == VipConstants.MOVIE_VIP_SUPER
                    || userInfo.getSeniorendtime() != null) {
                baseData.setBusinessString(movieText + memberText + "|"
                        + MessageUtils.getMessage("member_table_renew", commonParam.getLangcode()));
            } else {
                baseData.setBusinessString(movieText + memberText + "|"
                        + MessageUtils.getMessage("member_table_open", commonParam.getLangcode()));
            }

            // 收银台
            Extension extension = new Extension();
            extension.setIsParse(0);
            extension.setLaunchMode(VipMsgCodeConstant.LAUNCH_MODE_HIDE_ACTIVITY);
            extension.setAction(VipMsgCodeConstant.VIP_TABLE_MOVIE_CHECKSTAND_ACTION);
            extension.setResource("1");
            jumpData.setExtend(extension);

            JumpData value = new JumpData();
            value.setType(21);
            value.setValue(new HashMap());
            jumpData.setValue(value);
        }

        baseData.setJump(jumpData);
    }

    private void machineCardBinding(MemberDeskInfoBaseData baseData, JumpData jumpData, String memberText,
                                    CommonParam commonParam, String movieOrSportText) {
        baseData.setBusinessString(movieOrSportText + memberText + "|"
                + MessageUtils.getMessage("member_table_activate", commonParam.getLangcode()));

        Extension extension = new Extension();
        extension.setLaunchMode(VipMsgCodeConstant.LAUNCH_MODE_HIDE_ACTIVITY);
        extension.setAction(VipMsgCodeConstant.VIP_TABLE_MOVIE_MACHINE_CARD_ACTION);
        extension.setResource("launcher");
        jumpData.setExtend(extension);
    }

    /**
     * @param memberType
     *            -1--既不是影视会员又不是体育会员，0--影视，1--体育，2--综合
     * @param deviceType
     * @param commonParam
     * @return
     */
    private String getOperation(int memberType, Integer deviceType, CommonParam commonParam) {
        // 获取运营触达位
        String[] positions = { VipTpConstant.URM_USER_INFO_MOVIE_OPERATION, VipTpConstant.URM_USER_INFO_SPORT_OPERATION };
        String[] movieOperation = new String[positions.length];
        String[] sportOperation = new String[positions.length];
        String[] bothOperation = new String[positions.length];
        int index = 0;
        String language = getMessageKey(commonParam.getLangcode());
        int languageIndex = 1;
        if ("zh_CN".equals(language)) {
            languageIndex = 1;
        } else if ("en_US".equals(language)) {
            languageIndex = 2;
        } else if ("zh_HK".equals(language)) {
            languageIndex = 3;
        }

        for (String position : positions) {
            GetUrmActivityResponse tpResponse = this.facadeTpDao.getVipTpDao().getUrmTouchData(position, deviceType,
                    commonParam);

            if (tpResponse != null) {
                if ((memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE || memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_BOTH)
                        && VipTpConstant.URM_USER_INFO_MOVIE_OPERATION.equals(position)) {
                    if (tpResponse.getMessages().get(0) != null) {
                        bothOperation[0] = movieOperation[0] = tpResponse.getMessages().get(0).getShow_info()
                                .get("tv_" + position + "_text" + languageIndex);
                    }
                    if (tpResponse.getMessages().size() > 1) {
                        movieOperation[1] = tpResponse.getMessages().get(1).getShow_info()
                                .get("tv_" + position + "_text" + languageIndex);
                    }
                } else if ((memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_SPORT || memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_BOTH)
                        && VipTpConstant.URM_USER_INFO_SPORT_OPERATION.equals(position)) {
                    if (tpResponse.getMessages().get(0) != null) {
                        bothOperation[1] = sportOperation[0] = tpResponse.getMessages().get(0).getShow_info()
                                .get("gym_" + position + "_text" + languageIndex);
                    }
                    if (tpResponse.getMessages().size() > 1) {
                        sportOperation[1] = tpResponse.getMessages().get(1).getShow_info()
                                .get("gym_" + position + "_text" + languageIndex);
                    }
                }
            }
            index++;
        }
        if (memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_BOTH) {
            return bothOperation[0] + "|" + bothOperation[1];
        } else if (memberType == VipMsgCodeConstant.VIP_TABLE_MEMBER_TYPE_MOVIE) {
            if (movieOperation[1] == null) {
                return movieOperation[0];
            } else {
                return movieOperation[0] + "|" + movieOperation[1];
            }
        } else {
            if (sportOperation[1] == null) {
                return sportOperation[0];
            } else {
                return sportOperation[0] + "|" + sportOperation[1];
            }
        }
    }

    /**
     * 默认语言地域信息：汉语_中国
     */
    public static final String DEFAULT_FULL_LOCAL_ZH_CN = "zh_CN";

    private static String getMessageKey(String lang) {
        if (StringUtils.isBlank(lang)) {// 默认中文
            lang = DEFAULT_FULL_LOCAL_ZH_CN;
        }
        try {
            if (lang != null && lang.indexOf("-") > 0) {
                lang = lang.replaceAll("-", "_");
            }
            if (lang != null && lang.indexOf("_") > 0) {
                return lang.substring(0, lang.indexOf("_")).toLowerCase() + "_"
                        + lang.substring(lang.indexOf("_") + 1).toUpperCase();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取账户的会员有效期
     * @param commonParam
     * @param deviceType
     * @param deviceKey
     * @param isMore
     *            false时仅此获取基本用户vip信息，且deviceType和deviceKey可以为空，
     *            true时deviceType和deviceKey不可以为空
     * @return
     */
    public Response<UserAccountDto> getVipAccount(String deviceKey, Integer deviceType, boolean isMore,
                                                  CommonParam commonParam) {
        Response<UserAccountDto> response = new Response<UserAccountDto>();
        StringBuilder logPrefix = new StringBuilder("getVipAccount_").append(commonParam.getUserId()).append("_")
                .append(commonParam.getMac());
        String errorCode = null;

        // 1.参数校验
        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isBlank(commonParam.getUsername()) && StringUtils.isBlank(commonParam.getUserId())) {
            validCode = VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (deviceType != null && !VipTpConstantUtils.deviceTypeIsLegal(deviceType)) {
            validCode = VipMsgCodeConstant.VIP_GET_ACCOUNT_REQUEST_DEVICETYPE_ERROR;
        }
        if (VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
            response = (Response<UserAccountDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                    errorCode + VipUtil.parseErrorMsgCode(VipConstants.ACCOUNT_VIP, validCode),
                    commonParam.getLangcode());
        } else {
            // 2.获取会员信息
            VipAccountTpResponse accountTpResponse = this.facadeTpDao.getVipTpDao().getVipAccount(commonParam);
            if (accountTpResponse == null) {
                // 如果出错，抛出异常
                errorCode = ErrorCodeConstants.PAY_GET_VIP_ACCOUNT_INFO_FAILURE;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                response = (Response<UserAccountDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                        commonParam.getLangcode());
            } else {
                UserAccountDto data = new UserAccountDto();
                data.setIsvip(accountTpResponse.getIsvip());

                if (isMore) {
                    data.setUsername(BroadcastConstant.transToBroadcastUsername(commonParam.getUsername(),
                            commonParam.getBroadcastId()));
                    Integer isbind = accountTpResponse.getIsbind() == null ? 0 : accountTpResponse.getIsbind();
                    data.setIsbind(isbind);

                    // isvip添加一天时长的缓存
                    // RedisUtil.setex(key, CalendarUtil.SECONDS_OF_PER_DAY,
                    // accountTpResponse.getSeniorendtime());
                    this.facadeCacheDao.getVipCacheDao().setUserVipSeniorEndTime(commonParam.getUserId(), deviceKey,
                            accountTpResponse.getSeniorendtime());
                    Long seniorEndTime = accountTpResponse.getSeniorendtime();
                    if (seniorEndTime != null) {
                        data.setValidDate(TimeUtil.getDateStringFromLong(seniorEndTime, TimeUtil.SHORT_DATE_FORMAT));
                    }

                    // 3.获取乐点余额
                    GetLetvPointBalanceRequest getLetvPointBalanceRequest = new GetLetvPointBalanceRequest(
                            commonParam.getUsername(), commonParam.getUserId());
                    GetLetvPointBalanceTpResponse letvPointBalanceTpResponse = this.facadeTpDao.getVipTpDao()
                            .getAccountLetvPointBalance(commonParam);
                    if (letvPointBalanceTpResponse != null && letvPointBalanceTpResponse.getLepoint() != null) {
                        data.setLetvPoint(letvPointBalanceTpResponse.getLepoint());
                    } else {
                        data.setLetvPoint(0);
                        // 如果出错，静默处理
                        errorCode = ErrorCodeConstants.PAY_GET_LETV_POINT_FAILURE;
                        LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                    }

                    // 4.获取用户机卡绑定信息
                    UserBindTpResponse userBindTp = this.facadeTpDao.getVipTpDao().getUserBindInfo(deviceType,
                            commonParam);
                    if (userBindTp != null && userBindTp.isSuccess()) {
                        this.checkUserBindOrtherDevice(data, deviceKey, userBindTp);
                    } else {
                        // 如果出错，设置为非法数据
                        data.setHasBindOtherDevice(0);// 无效数据
                        errorCode = ErrorCodeConstants.PAY_GET_USER_DEVICE_BIND_FAILURE;
                        LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                    }

                    // 5.获取用户基本信息
                    String pictuer = UserTpConstant.USER_ACOUNT_DEFAULT_PIC;
                    String displayName = null;
                    GetUserInfoTpResponse userInfoTpResponse = this.facadeTpDao.getUserTpDao().getUserInfoByUserid(
                            commonParam);
                    if (userInfoTpResponse != null && userInfoTpResponse.isSuccess()) {
                        // 设置用户头像
                        pictuer = this.getUserIcon(userInfoTpResponse, pictuer);
                        // 设置昵称
                        displayName = this.getUserDisplayName(userInfoTpResponse);
                    } else {
                        // 如果出错，静默处理
                        errorCode = ErrorCodeConstants.USER_GET_USER_INFO_FAILURE;
                        LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
                    }
                    if (StringUtils.isEmpty(displayName)) {
                        displayName = commonParam.getUsername();
                    }
                    data.setPicture(DomainMapping.changeDomainByBraodcastId(pictuer, commonParam.getBroadcastId(),
                            commonParam.getTerminalApplication()));
                    data.setDisplayName(displayName);
                } else {
                    data.setSeniorendtime(accountTpResponse.getSeniorendtime());
                    data.setIsSportVip(accountTpResponse.isSportVip());
                    data.setSportendtime(accountTpResponse.getSportendtime());
                }

                response.setData(data);
            }
        }
        return response;
    }

    /**
     * 触达数据
     * @param uid
     *            手机号
     * @param id
     *            mac地址
     * @param ip
     *            ip地址
     * @param devid
     *            devicekey
     * @param locale
     *            用户环境locale
     * @return
     */
    public Response<TouchUserDto> getTouchData(String uid, String id, String ip, String devid, String langcode) {
        Response<TouchUserDto> response = new Response<TouchUserDto>();
        String logPrefix = "getTouch_" + uid + "_" + id + "_" + ip + "_" + devid;

        TouchTpResponse touchTp = this.facadeTpDao.getVipTpDao().getTouchData(uid, id, ip, devid);

        if (touchTp == null || touchTp.getStatus() == 0) {
            LOG.error(logPrefix + "[errorCode=" + ErrorCodeConstants.TOUCH_USERCENTER_ILLEGAL + "]:  check failed.");
            response = (Response<TouchUserDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                    ErrorCodeConstants.TOUCH_USERCENTER_ILLEGAL, langcode);
        } else {
            TouchUserDto data = new TouchUserDto();
            data.setAlbum_id(touchTp.getAlbum_id());
            data.setBack_content(touchTp.getBack_content());
            data.setBack_img(touchTp.getBack_img());
            data.setBack_title(touchTp.getBack_title());
            data.setButton(touchTp.getButton());
            data.setContent(touchTp.getContent());
            data.setImg(touchTp.getImg());
            data.setJump_type(touchTp.getJump_type());
            data.setPushid(touchTp.getPushid());
            data.setStatus(touchTp.getStatus());
            data.setTitle(touchTp.getTitle());
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(data);
        }

        return response;
    }

    /**
     * get activity by user info
     * @param supportNew
     * @param deviceType
     * @param commonParam
     * @return
     */
    public Response<BaseData> getPilot(Integer supportNew, Integer deviceType, CommonParam commonParam) {
        Response<BaseData> response = new Response<BaseData>();
        String logPrefix = "getPilot_" + commonParam.getMac() + "_" + commonParam.getUserId();
        PilotDto data = new PilotDto();
        for (String vipCenterPosition : VipTpConstant.vip_center_positions_pilot) {// for循环取3个位置的数据，然后返回给客户端
            GetVipCenterActivityResponse tpResponse = this.facadeTpDao.getVipTpDao().getVipCenterActivity(
                    vipCenterPosition, deviceType, commonParam);
            PilotDto dto = this.parseVipCenterData(tpResponse, logPrefix,
                    VipTpConstant.getUserTouchPosition(vipCenterPosition), commonParam);
            if (dto != null) {
                if (VipTpConstant.VIP_CENTER_POSITION_TOP.equals(vipCenterPosition)) {
                    dto.setPosition(VipTpConstant.USER_TOUCH_POSITION_TOP);
                    data.setTopData(dto);
                    response.setData(data);
                } else {
                    if (VipTpConstant.VIP_CENTER_POSITION_BOTTOM.equals(vipCenterPosition)) {
                        String value = this.facadeCacheDao.getVipCacheDao().getUserBottomFlag(commonParam.getUserId(),
                                commonParam.getMac(), dto.getId());
                        value = "0";
                        if (VipTpConstant.USER_TOUCH_POSITION_BOTTOM_FLAG.equals(value)) {
                            continue;
                        } else {
                            dto.setPosition(VipTpConstant.USER_TOUCH_POSITION_BOTTOM);
                            dto.setOpenType(7);
                            this.facadeCacheDao.getVipCacheDao().setUserBottomFlag(commonParam.getUserId(),
                                    commonParam.getMac(), dto.getId(), VipTpConstant.USER_TOUCH_POSITION_BOTTOM_FLAG);
                        }
                    } else {
                        dto.setPosition(VipTpConstant.USER_TOUCH_POSITION_POPUP);
                        dto.setRate(1);
                    }
                    dto.setTopData(data.getTopData());
                    response.setData(dto);
                    return response;// 因为下浮层和定向弹窗只要一个，所以在此返回
                }
            }
        }

        return response;
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
        PilotDto data = new PilotDto();
        String[] positions = { VipTpConstant.URM_POSITION_TOP, VipTpConstant.URM_POSITION_BOTTOM,
                VipTpConstant.URM_POSITION_POPUP };
        for (String position : positions) {
            if (VipTpConstant.URM_POSITION_POPUP.equals(position)
                    && "0".equals(ApplicationUtils.get(VipConstants.ACTIVITY_POPUP_SWITCH))) {
                continue;// if popup switch is shutdown then no popup activity
            }
            GetUrmActivityResponse tpResponse = this.facadeTpDao.getVipTpDao().getUrmTouchData(position, deviceType,
                    commonParam);
            PilotDto dto = parseUrmActivityData(tpResponse, position, commonParam);
            if (dto != null) {
                if (VipTpConstant.URM_POSITION_TOP.equals(position)) {
                    dto.setPosition(VipTpConstant.USER_TOUCH_POSITION_TOP);
                    data.setTopData(dto);
                    response.setData(data);
                } else {
                    if (VipTpConstant.URM_POSITION_BOTTOM.equals(position)) {
                        dto.setPosition(VipTpConstant.USER_TOUCH_POSITION_BOTTOM);
                        dto.setOpenType(7);
                    } else {
                        dto.setPosition(VipTpConstant.USER_TOUCH_POSITION_POPUP);
                        dto.setRate(1);
                    }
                    dto.setTopData(data.getTopData());
                    response.setData(dto);
                    return response;// 因为下浮层和定向弹窗只要一个，所以在此返回
                }
            }
        }
        return response;
    }

    /**
     * 解析会员运营中心接口数据
     * @param tpResponse
     * @param position
     * @param logPrefix
     * @return
     */
    public PilotDto parseVipCenterData(GetVipCenterActivityResponse tpResponse, String logPrefix, String position,
                                       CommonParam commonParam) {
        GetVipCenterActivityResponse.VipCenterResultData chooseData = this.getVipCenterResultData(tpResponse);
        if (chooseData != null) {
            PilotDto data = new PilotDto();
            data.setId(chooseData.getAid());
            Integer dataType = chooseData.getGo_type();// 跳转类型
            GetVipCenterActivityResponse.VipCenterResultData.JumpDataParam param = chooseData.getGo_param();
            String deliverAddress = chooseData.getDelivery_address();
            if (StringUtil.isNotBlank(deliverAddress)) {
                this.facadeCacheDao.getVipCacheDao().setDeliverAddress(commonParam.getUserId(), position,
                        deliverAddress);
            } else {
                this.facadeCacheDao.getVipCacheDao().deleteDeliverAddress(commonParam.getUserId(), position);
            }
            // should entry checkout counter flag
            boolean entryCheckoutCounter = false;
            if (dataType != null) {
                if (dataType == DataConstant.DATA_TYPE_CHECKSTAND) {
                    entryCheckoutCounter = true;
                } else if (dataType == DataConstant.DATA_TYPE_BROWSER) {
                    Integer openType = param.getOpenType();
                    if (openType != null && openType == 1) {
                        entryCheckoutCounter = true;
                    }
                }
            }
            String payInfoJson = chooseData.getPay_info();
            if (entryCheckoutCounter && !StringUtil.isBlank(payInfoJson)) {// 收银台
                List<BossActivityData> dataList = JsonUtil.parse(payInfoJson,
                        new LetvTypeReference<List<BossActivityData>>() {
                        });
                Map<String, PaymentActivityDto> paymentActivityMap = this.parsePaymentActivityFromTpNew(dataList,
                        commonParam.getLangcode());
                if (!CollectionUtils.isEmpty(paymentActivityMap)) {
                    for (Entry<String, PaymentActivityDto> entry : paymentActivityMap.entrySet()) {
                        PaymentActivityDto pa = entry.getValue();
                        data.setOrderType(pa.getMonthType());// 套餐id
                        data.setActivityIds(pa.getActivityId());
                        data.setCashierUrl(pa.getUrl());
                        if (StringUtil.toFloat(pa.getDiscount(), 0F) > 0) {// 价格大于0，定向收银台
                        }
                        break;
                    }
                }
            }
            this.parseParam(data, param, dataType, position);
            this.setPilotDtoData(data, dataType, data.getOpenType(), position);
            return data;
        } else {
            this.facadeCacheDao.getVipCacheDao().deleteDeliverAddress(commonParam.getUserId(), position);
        }
        return null;
    }

    public PilotDto parseUrmActivityData(GetUrmActivityResponse tpResponse, String position, CommonParam commonParam) {
        if (tpResponse == null || CollectionUtils.isEmpty(tpResponse.getMessages())) {
            return null;
        }
        PilotDto data = parseUrmActivityInfo(tpResponse.getMessages().get(0), position, commonParam);
        if (data != null) {
            GetUrmActivityResponse.UrmActivityReport report = tpResponse.getReporting();
            if (report != null) {
                data.setId(report.getCampaign_id());
                data.setCampaignId(report.getCampaign_id());
                data.setTouchMessageId(report.getTouch_message_id());
                data.setTouchSpotId(report.getTouch_spot_id());
            }
            // set jump info
            this.setPilotDtoData(data, data.getDataType(), data.getOpenType(),
                    VipTpConstant.getUserTouchPositionV2(position));
        } else {
            this.facadeCacheDao.getVipCacheDao().deleteDeliverAddress(commonParam.getUserId(),
                    VipTpConstant.getUserTouchPositionV2(position));
        }
        return data;
    }

    public PilotDto parseUrmActivityData(GetUrmActivityListResponse tpResponse, String position, CommonParam commonParam) {
        if (tpResponse == null || CollectionUtils.isEmpty(tpResponse.getCreatives())) {
            return null;
        }
        PilotDto data = parseUrmActivityInfo(tpResponse.getCreatives().get(0), position, commonParam);
        if (data != null) {
            GetUrmActivityResponse.UrmActivityReport report = tpResponse.getReporting();
            if (report != null) {
                data.setId(report.getCampaign_id());
                data.setCampaignId(report.getCampaign_id());
                data.setTouchMessageId(report.getTouch_message_id());
                data.setTouchSpotId(report.getTouch_spot_id());
            }
            // set jump info
            this.setPilotDtoData(data, data.getDataType(), data.getOpenType(),
                    VipTpConstant.getUserTouchPositionV2(position));
        } else {
            this.facadeCacheDao.getVipCacheDao().deleteDeliverAddress(commonParam.getUserId(),
                    VipTpConstant.getUserTouchPositionV2(position));
        }
        return data;
    }

    public GetVipCenterActivityResponse.VipCenterResultData getVipCenterResultData(GetVipCenterActivityResponse tpResponse) {
        if (tpResponse != null && tpResponse.getStatus() != null && tpResponse.getStatus() == 0
                && !CollectionUtils.isEmpty(tpResponse.getData())) {
            List<GetVipCenterActivityResponse.VipCenterResultData> rusultDatas = tpResponse.getData();
            GetVipCenterActivityResponse.VipCenterResultData chooseData = null;
            for (GetVipCenterActivityResponse.VipCenterResultData resultData : rusultDatas) {
                Integer weight = resultData.getItem_weight();
                if (chooseData == null) {
                    chooseData = resultData;
                } else {
                    Integer oldWeight = chooseData.getItem_weight();
                    if (oldWeight == null) {
                        if (weight != null) {
                            chooseData = resultData;
                        }
                    } else if (weight != null) {
                        if (weight < oldWeight) {
                            chooseData = resultData;
                        } else if (weight == oldWeight) {
                            if (chooseData.getItem_time() != null && resultData.getItem_time() != null
                                    && resultData.getItem_time() > chooseData.getItem_time()) {
                                chooseData = resultData;
                            }
                        }
                    }
                }
            }
            return chooseData;
        }
        return null;
    }

    private JumpData buildJumpObj(Integer dataType, String dataValue) throws IOException {
        JumpData jump = new JumpData();
        ObjectMapper mapper = new ObjectMapper();
        if (dataType == DataConstant.DATA_TYPE_BLANK) {
            StaticBlock staticBlock = new StaticBlock();
            jump.setType(DataConstant.DATA_TYPE_BLANK);// 0
            jump.setValue(staticBlock);
        } else if (dataType == DataConstant.DATA_TYPE_ALBUM) {
            AlbumDto albumDto = new AlbumDto();
            albumDto.setAlbumId(dataValue + "");
            albumDto.setTvCopyright(1);
            albumDto.setSrc(1);
            jump.setType(DataConstant.DATA_TYPE_ALBUM);
            jump.setValue(albumDto);
        } else if (dataType == DataConstant.DATA_TYPE_VIDEO) {
            VideoDto videoDto = new VideoDto();
            videoDto.setVideoId(dataValue + "");
            videoDto.setAlbumId("");
            videoDto.setCategoryId(0);
            videoDto.setTvCopyright(0);
            videoDto.setWebsite("");
            videoDto.setWebPlayUrl("");
            jump.setType(DataConstant.DATA_TYPE_VIDEO);
            jump.setValue(videoDto);
        } else if (dataType == DataConstant.DATA_TYPE_BROWSER) {
            Browser browser = new Browser();
            browser.setOpenType(0);
            browser.setUrl(dataValue);
            browser.setBrowserType(2);
            jump.setType(DataConstant.DATA_TYPE_BROWSER);
            jump.setValue(browser);
        }
        return jump;
    }

    private Object parseSkipConfigInfo(String jsonStr, Object destObj) {
        if (jsonStr != null && !"".equals(jsonStr)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(NON_NULL);
                if (destObj instanceof AlbumDto) {
                    destObj = mapper.readValue(jsonStr, AlbumDto.class);
                } else if (destObj instanceof VideoDto) {
                    destObj = mapper.readValue(jsonStr, VideoDto.class);
                } else if (destObj instanceof SubjectPreLive) {
                    destObj = mapper.readValue(jsonStr, SubjectPreLive.class);
                } else if (destObj instanceof Subject) {
                    destObj = mapper.readValue(jsonStr, Subject.class);
                } else if (destObj instanceof Live) {
                    destObj = mapper.readValue(jsonStr, Live.class);
                } else if (destObj instanceof Channel) {
                    destObj = mapper.readValue(jsonStr, Channel.class);
                } else if (destObj instanceof Retrieve) {
                    destObj = mapper.readValue(jsonStr, Retrieve.class);
                } else if (destObj instanceof Browser) {
                    destObj = mapper.readValue(jsonStr, Browser.class);
                } else if (destObj instanceof Page) {
                    destObj = mapper.readValue(jsonStr, Page.class);
                } else if (destObj instanceof BaseBlock) {
                    destObj = mapper.readValue(jsonStr, BaseBlock.class);
                } else if (destObj instanceof Container) {
                    destObj = mapper.readValue(jsonStr, Container.class);
                } else if (destObj instanceof StarCms) {
                    destObj = mapper.readValue(jsonStr, StarCms.class);
                } else if (destObj instanceof ChartsDto) {
                    destObj = mapper.readValue(jsonStr, ChartsDto.class);
                } else if (destObj instanceof StaticBlock) {
                    destObj = mapper.readValue(jsonStr, StaticBlock.class);
                }
            } catch (Exception e) {
            }

        }
        return destObj;
    }

    /**
     * 获取账户乐点余额，如果查询不到，则返回0
     * @param username
     * @param userid
     * @param locale
     * @return
     */
    public int getLetvPointBalance(CommonParam commonParam) {
        int letvPoint = 0;
        GetLetvPointBalanceTpResponse letvPointBalanceTpResponse = this.facadeTpDao.getVipTpDao()
                .getAccountLetvPointBalance(commonParam);
        if (letvPointBalanceTpResponse != null && letvPointBalanceTpResponse.getLepoint() != null) {
            letvPoint = letvPointBalanceTpResponse.getLepoint();
        }
        return letvPoint;
    }

    /**
     * 查询绑卡信息
     * @param getBandInfoRequest
     * @param locale
     * @return
     */
    public PageResponse<BandInfoDto> getBindInfo(GetBindInfoRequest getBindInfoRequest, Locale locale) {
        PageResponse<BandInfoDto> response = new PageResponse<BandInfoDto>();
        String logPrefix = "getBandInfo_" + getBindInfoRequest.getUserid() + "_" + getBindInfoRequest.getCompanyid();
        String errorCode = null;

        int validCode = getBindInfoRequest.assertValid();
        if (VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            // 请求参数非法
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]");
            response = (PageResponse<BandInfoDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                    errorCode + VipUtil.parseErrorMsgCode(VipConstants.YEEPAY, validCode), locale);
        } else {
            GetBindInfoTpResponse getBindInfoTpResponse = this.facadeTpDao.getVipTpDao()
                    .getBindInfo(getBindInfoRequest);
            if (getBindInfoTpResponse != null && getBindInfoTpResponse.getStatus() == 1) {
                List<GetBindInfoTpResponse.BandInfo> bandinfos = getBindInfoTpResponse.getBandinfo();
                List<BandInfoDto> list = new LinkedList<BandInfoDto>();
                if (bandinfos != null && bandinfos.size() > 0) {
                    for (GetBindInfoTpResponse.BandInfo bandInfo : bandinfos) {
                        BandInfoDto bandInfoDto = new BandInfoDto();
                        bandInfoDto.setBankname(bandInfo.getBankname());
                        bandInfoDto.setCardno(bandInfo.getCardno());
                        bandInfoDto.setIdentityid(bandInfo.getIdentityid());
                        list.add(bandInfoDto);
                    }
                }
                response.setData(list);
                response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
                response.setTotalCount(list.size());
            } else {
                // 调用第三方接口失败
                errorCode = ErrorCodeConstants.PAY_YEEPAY_GET_BINDINFO_FAILURE;
                response = (PageResponse<BandInfoDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                        locale);
            }
        }
        return response;
    }

    /**
     * 获取收银台相关信息，目前包含套餐包（套餐基本信息，套餐活动，支付渠道活动），支付渠道，会员权益文案，收银台焦点图
     * @param commonParam
     * @param vipType
     * @param channelPriority
     * @param packagePriority
     * @param purchaseType2
     * @param locale
     * @return
     */
    public Response<CheckoutCounterDto> getCheckoutCounter(Integer purchaseType, String packagePriority,
                                                           String channelPriority, Integer vipType, Integer av, Integer subend, CommonParam commonParam) {
        Response<CheckoutCounterDto> response = new Response<CheckoutCounterDto>();
        String userId = commonParam.getUserId();
        StringBuilder logPrefix = new StringBuilder("getCheckoutCounter_").append(vipType).append("_").append(userId);
        String errorCode = null;
        String errorMsgCode = null;
        CheckoutCounterDto data = null;

        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isNotBlank(userId) && (!NumberFormatUtil.isNumeric(userId) || (Long.valueOf(userId)) < 0)) {
            // 进入收银台购买商品，需要用户信息，所以用户id不能为空
            validCode = VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        } else if (purchaseType == null || purchaseType < 1 || purchaseType > 3) {
            // 购买类型，1--单点，2--套餐，3--直播，其他值认为是非法值
            validCode = VipMsgCodeConstant.VIP_GET_CHECK_COUNTER_REQUEST_PURCHASE_TYPE_ERROR;
        } else if (av != null && av == 2) {// 第三方设备
            if (subend != null && subend == VipTpConstant.SUB_ORDER_FROM_DEFAULT) {// 需要知道是哪个合作方
                validCode = VipMsgCodeConstant.VIP_GET_CHECK_COUNTER_REQUEST_APK_VERSION_ERROR;
            } else if (purchaseType == 2) {// 目前支持直播收银台，不支持套餐收银台
                validCode = VipMsgCodeConstant.VIP_GET_CHECK_COUNTER_REQUEST_TPSDK_PURCHASE_ERROR;
            }
        }
        if (VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            response = (Response<CheckoutCounterDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                    errorCode + ErrorMsgCodeUtil.parseErrorMsgCode(BusinessCodeConstant.ACCOUNT_VIP, validCode),
                    commonParam.getLangcode());
            LOG.error(logPrefix + "[errorCode=" + errorCode + "]: illegal parameters.");
        } else {
            data = new CheckoutCounterDto(); // 这里就new是为了获取支付渠道时，针对易宝支付，顺便设置绑卡信息（如果有的话）
            // 1.针对所有收银台，都要获取支付渠道；如果获取失败，则不允许进入收银台；解析过程中，会对支付渠道进行排序
            // 获取最终的支付渠道列表，所有收银台都这么处理
            // TODO ligang 20151218 这个方法应该可以精简
            List<String> paymentChannelList = this.getPaymentchannelList(userId, String.valueOf(purchaseType),
                    channelPriority, data, subend, logPrefix.toString());

            if (CollectionUtils.isEmpty(paymentChannelList)) {
                errorCode = ErrorCodeConstants.PAY_GET_PAYMENTCHANNEL_ERROR;
                LOG.error(logPrefix + "[errorCode=" + errorCode + "]: get paymentchannel failure.");
                response = (Response<CheckoutCounterDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                        errorCode, commonParam.getLangcode());
            } else {
                if (purchaseType != VipTpConstant.PURCHASE_TYPE_2) {
                    // 非套餐收银台，指的是购买影片或者直播，所以商品信息已有，只需要展示支付渠道
                    data.setPaymentChannelList(paymentChannelList);// 添加支付渠道
                    response.setData(data);
                } else {// purchaseType=2表示进入的是套餐收银台，需要获取套餐列表，如果获取不到，则报错
                    PricePackageListTpResponse packageListTpResponse = this.facadeTpDao.getVipTpDao()
                            .getPricePackageList();
                    // 剩余操作基于套餐信息进行，如果获取套餐失败，则操作结束；否则，后续操作失败，静默（日志）处理
                    if (packageListTpResponse == null || "0".equals(packageListTpResponse.getStatus())) {
                        // 套餐收银台获取套餐失败，则报错，不允许进入收银台
                        errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
                        LOG.error(logPrefix + "[errorCode=" + errorCode + "]: get vip package failed.");
                        response = (Response<CheckoutCounterDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                                errorCode, commonParam.getLangcode());
                    } else {
                        // 整理套餐包信息
                        Map<String, VipPackageDto> vipPackageMap = this.parseVipPackageFromTp(packageListTpResponse,
                                vipType, commonParam.getLangcode());

                        if (CollectionUtils.isEmpty(vipPackageMap)) {
                            // 将筛选之后得到VipPackageDto空列表也作为一种业务异常
                            errorCode = ErrorCodeConstants.PAY_PRICE_PACKAGE_LIST_NULL;
                            LOG.error(logPrefix + "[errorCode=" + errorCode + "]: get vip package empty.");
                            response = (Response<CheckoutCounterDto>) LetvUserVipCommonConstant.setErrorResponse(
                                    response, errorCode, commonParam.getLangcode());
                        } else {
                            // 套餐包获取成功，且不为空，此时套餐包未排序
                            /*
                             * 处理活动 1.获取活动 1.组装活动 2.处理易宝活动
                             */

                            Long userid = null;
                            if (StringUtils.isNotBlank(userId)) {
                                userid = Long.valueOf(userId);
                            }

                            // 获取付费活动信息，支持未登录状态下获取活动信息
                            GetPaymentActivityRequest getPaymentActivityRequest = new GetPaymentActivityRequest(null,
                                    userid, null, null);
                            PaymentActivityTpResponse paymentActivityTpResponse = this.facadeTpDao.getVipTpDao()
                                    .getPaymentActivity(getPaymentActivityRequest, commonParam.getLangcode());

                            // 解析付费活动
                            Map<String, PaymentActivityDto> paymentActivityMap = null;
                            if (paymentActivityTpResponse == null || !paymentActivityTpResponse.isSucceed()) {
                                LOG.error(logPrefix + "[errorCode=" + errorCode + "]: get payment activity failed.");
                            } else {
                                paymentActivityMap = this.parsePaymentActivityFromTp(paymentActivityTpResponse,
                                        vipType, commonParam.getLangcode());
                                // 组装付费活动到套餐中
                                this.fillVipPackageWithPaymentActivityes(vipPackageMap, paymentActivityMap);

                                // 处理特殊活动
                                paymentActivityMap = this.getVirtualPaymentActivity(vipPackageMap, paymentActivityMap,
                                        paymentChannelList, userid, logPrefix.toString(), commonParam);
                            }

                            // 对套餐进行排序
                            List<VipPackageDto> vipPackageList = this.sortPackageList(vipPackageMap, packagePriority);
                            data.setVipPackages(vipPackageList);// 添加套餐
                            data.setActivities(paymentActivityMap);// 添加活动
                            data.setPaymentChannelList(paymentChannelList);// 添加支付渠道
                            response.setData(data);
                        }
                    }
                }
            }
        }

        return response;
    }

    public Response<VerificationCodeResultDto> yeepayVerificationCode(String ordernumber, Integer operType,
                                                                      String smscode, CommonParam commonParam) {
        Response<VerificationCodeResultDto> response = new Response<VerificationCodeResultDto>();
        StringBuilder logPrefix = new StringBuilder("yeepayVerificationCode_").append(ordernumber).append("_")
                .append(operType);
        String errorCode = null;

        // 参数校验
        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isBlank(ordernumber)) {
            validCode = VipMsgCodeConstant.VIP_YEEPAY_VERIFY_CODE_REQUEST_CORDERID_EMPTY;
        } else if (operType == null || operType < 1 || operType > 2) {
            validCode = VipMsgCodeConstant.VIP_YEEPAY_VERIFY_CODE_REQUEST_OPERTYPE_ERROR;
        } else if (operType == 2 && StringUtils.isBlank(smscode)) {
            validCode = VipMsgCodeConstant.VIP_YEEPAY_VERIFY_CODE_REQUEST_SMSCODE_ERROR;
        }
        if (VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.error(logPrefix + "[errorCode=" + errorCode + "]: illegal parameters.");
            response = (Response<VerificationCodeResultDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                    errorCode, errorCode + VipUtil.parseErrorMsgCode(VipConstants.YEEPAY, validCode),
                    commonParam.getLangcode());
        } else {
            if (operType != null) {
                VerificationCodeTpResponse tpResponse = this.facadeTpDao.getVipTpDao().yeepayVerificationCode(operType,
                        ordernumber, smscode, logPrefix.toString());
                if (tpResponse == null || tpResponse.getStatus() == 0) {
                    errorCode = operType == 1 ? ErrorCodeConstants.PAY_YEEPAY_GET_VERIFY_CODE_ERROR
                            : ErrorCodeConstants.PAY_YEEPAY_CONFIRMPAY_ERROR;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: yeepay verification code failed.");
                    response = (Response<VerificationCodeResultDto>) LetvUserVipCommonConstant.setErrorResponse(
                            response, errorCode, commonParam.getLangcode());
                } else {
                    VerificationCodeResultDto data = new VerificationCodeResultDto();
                    data.setCorderid(tpResponse.getCorderid());
                    data.setMsg(tpResponse.getErrormsg());
                    response.setData(data);
                }
            }
        }
        return response;
    }

    /**
     * 获取支付渠道列表
     * @param productType
     * @return
     */
    private List<String> getPaymentchannelList(String userid, String productType, String channelPriority,
                                               CheckoutCounterDto data, Integer subend, String logPrefix) {
        List<String> paymentchannelList = null; // 最终结果，如果有值，则已经排好序了
        // 读取默认的支付渠道，此时已经按照产品定义的顺序设置好
        String defaultPaymentChannelStr = this.getPaymentChannel(productType, subend);
        if (StringUtils.isNotBlank(defaultPaymentChannelStr)) {
            // TODO ligang 20151218 split这一步应该可以避免
            String[] values = defaultPaymentChannelStr.split(",");
            List<String> paymentchannelTempList = new LinkedList<String>();// 支付渠道列表的中间变量，用于删减和排序处理
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    paymentchannelTempList.add(value);
                }
            }

            if (StringUtils.isBlank(userid)) {// TODO ligang 20151218 这一步可以客户端做
                // 没有用户信息，即未登录，则没有41,42支付渠道和绑卡信息；因为41,42都与账户信息相关，故未登录时不予展示，实际也不知道改展示哪个为好
                paymentchannelTempList.remove("41");
                paymentchannelTempList.remove("42");
                LOG.info(logPrefix + ",userid is null so remove the 41 and 42 paymentchannel.");
            } else {// 登录情况要考虑易宝支付，目的是如果用户已经绑过卡，需要把绑卡信息返回个客户端，支付时需要用到

                if (paymentchannelTempList.contains("42")) {// 如果有易宝支付绑卡支付渠道类型[42]，则需要查询绑卡信息
                    GetBindInfoRequest getBindInfoRequest = new GetBindInfoRequest(userid);
                    GetBindInfoTpResponse getBindInfoTpResponse = this.facadeTpDao.getVipTpDao().getBindInfo(
                            getBindInfoRequest);
                    if (getBindInfoTpResponse != null && getBindInfoTpResponse.getStatus() != null
                            && getBindInfoTpResponse.getStatus() == 1) {
                        // 查询成功
                        List<GetBindInfoTpResponse.BandInfo> bandinfoList = getBindInfoTpResponse.getBandinfo();
                        if (bandinfoList != null && bandinfoList.size() > 0) {
                            // 用户有绑卡信息，显示42，去掉41
                            paymentchannelTempList.remove("41");
                            LOG.info(logPrefix + ",user have bindinfo so remove the 41 paymentchannel.");
                            if (paymentchannelTempList.contains("42")) {
                                // 如果支付配置了易宝快捷支付，才有必要把绑卡信息返回
                                List<BandInfoDto> list = new ArrayList<BandInfoDto>();
                                for (GetBindInfoTpResponse.BandInfo bandinfo : bandinfoList) {
                                    BandInfoDto bandInfoDto = new BandInfoDto();
                                    bandInfoDto.setBankname(bandinfo.getBankname());
                                    bandInfoDto.setCardno(bandinfo.getCardno());
                                    bandInfoDto.setIdentityid(bandinfo.getIdentityid());
                                    list.add(bandInfoDto);
                                }
                                data.setYeepaybandinfo(list);
                            }
                        } else {
                            // 没有绑卡信息，则显示41，去掉42
                            paymentchannelTempList.remove("42");
                            LOG.info(logPrefix + ",user have no bindinfo so remove the 42 paymentchannel.");
                        }
                    } else {
                        // 查询失败则都不展示
                        paymentchannelTempList.remove("41");
                        paymentchannelTempList.remove("42");
                        LOG.info(logPrefix + ",query user bindinfo failure so remove the 41 and 42 paymentchannel.");
                    }
                }
            }

            if (!CollectionUtils.isEmpty(paymentchannelTempList)) {
                paymentchannelList = new ArrayList<String>();// 初始化
                if (StringUtils.isNotBlank(channelPriority)) {
                    // 针对活动投放需要优先的支付渠道，做一次顺序调整，但也只是针对paymentchannelTempList进行调整
                    String[] channels = channelPriority.split(",");
                    for (String channel : channels) {
                        if (paymentchannelTempList.contains(channel)) {
                            // 只有指定优先级的支付渠道包含在结果中，才能调整
                            paymentchannelList.add(channel);
                            paymentchannelTempList.remove(channel); // 去重
                        }
                    }
                }

                paymentchannelList.addAll(paymentchannelTempList);
            }
        }
        // 代码漏洞？？？ArrayList直接remove出错，改成LinkedList可以直接remove
        return paymentchannelList;
    }

    /**
     * 从第三方响应中解析出VipPackageDto信息； 返回Map格式数据，key--套餐id，value--套餐；
     * 因为VipPackageListTpResponse中的套餐已经排好序，如果使用Map会打乱顺序（Map.values()方法返回乱序套餐列表），
     * 故使用Map封装后，最终结果还需重新排序。
     * @param vipPackageListResponse
     * @return
     */
    private Map<String, VipPackageDto> parseVipPackageFromTp(PricePackageListTpResponse packageListTpResponse,
                                                             Integer vipType, String langcode) {
        Map<String, VipPackageDto> vipPackageMap = null;
        List<PricePackageListTpResponse.PackageRecord> packageList = packageListTpResponse.getPackageList();
        if (!CollectionUtils.isEmpty(packageList)) {
            vipPackageMap = new HashMap<String, VipPackageDto>();
            for (PricePackageListTpResponse.PackageRecord packageRecord : packageList) {
                VipPackageDto dto = VipPackageBuilder.build(packageRecord, vipType);
                if (dto != null) {
                    vipPackageMap.put(dto.getId(), dto);
                }
            }
        }

        return vipPackageMap;
    }

    /**
     * 获取支付活动列表
     * @param actTp
     *            院线接口返回的支付活动数据
     * @param av
     * @param logPrefix
     *            日志文件，公共方法支持不同的日志头
     * @param userid
     *            用户ID，用于判断是否可以参加一元包月活动
     * @return
     */
    private List<PaymentActivityDto> getPaymentActivityList(PaymentActivityTpResponse actTp, Integer av,
                                                            String logPrefix, Long userid, List<VipPackageDto> vipPackageList, Locale locale, CommonParam commonParam) {
        List<PaymentActivityDto> list = new ArrayList<PaymentActivityDto>();
        PaymentActivityDto monthPaymentDto = null;
        boolean flag = true;// 是否将活动包月的支付渠道活动添加进来标志位
        String packageActivityDiscount = null;// 包月套餐包的套餐活动减免金额,用于计算虚拟活动一元包月的减免金额
        if (actTp == null || !actTp.isSucceed()) {// 日志写在这，是因为即使院线接口失败了，还是要查询一元包月活动的
            LOG.error(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_GET_PAYMENT_ACTIVITY_FAILURE
                    + "]: get payment activity failed.");
        } else {
            // 通过院线接口获取支付活动
            if (actTp.getValues() != null && !CollectionUtils.isEmpty(actTp.getValues().getActivitys())) {
                Map<Integer, List<PaymentActivityTpResponse.PaymentActivity>> activitys = actTp.getValues().getActivitys();
                for (Entry<Integer, List<PaymentActivityTpResponse.PaymentActivity>> activityEntry : activitys.entrySet()) {
                    if (activityEntry != null && !CollectionUtils.isEmpty(activityEntry.getValue())) {
                        for (PaymentActivityTpResponse.PaymentActivity activity : activityEntry.getValue()) {
                            PaymentActivityDto dto = PaymentActivityBuilder.build(activity, av);
                            if (dto != null) {
                                Integer monthType = activity.getMonthType();
                                // 包月活动&&支付渠道包含41，把活动信息备份起来，后期再决定是否添加
                                if (monthType != null && VipTpConstant.ORDER_TYPE_2 == monthType) {
                                    Integer type = dto.getType();// 1--套餐包活动，2--支付渠道活动
                                    if (type != null && type == 1) {
                                        list.add(dto);
                                        packageActivityDiscount = dto.getDiscount();
                                    } else if (type != null && type == 2) {
                                        List<Integer> payTypeList = activity.getPayTypeList();
                                        if (payTypeList != null && payTypeList.size() > 0
                                                && payTypeList.contains(VipTpConstant.PAYMENT_CHANNEL_YEEPAYUNBIND)) {
                                            monthPaymentDto = dto;// 先存储起来，在后期决定是否添加进来
                                        } else {
                                            list.add(dto);
                                        }
                                    }
                                } else {
                                    list.add(dto);
                                }
                            }
                        }
                    }
                }
            }
            int originalPaymentActivitySize = actTp.getPaymentActivitySize();
            if (list.size() != originalPaymentActivitySize) {
                LOG.warn(logPrefix + ": execute filtering from " + originalPaymentActivitySize + " to " + list.size());
            }
        }

        // 通过用户ID获取是否参加可以一元包月活动
        // 2015-06-10先写成直接查询接口判断，后期在修改成先读缓存
        if (userid != null && userid > 0 && vipPackageList != null && vipPackageList.size() > 0) {// 只有当用户ID合法时，才判断是否可以参加一元包月活动
            // String canOneForMonth = (String)
            // RedisUtil.get(VipConstants.VIP_CANNOT_ONEFORMONTH_KEY_PREFIX +
            // userid);
            String canOneForMonth = this.facadeCacheDao.getVipCacheDao().getFlagBuyMonthlyPackage(
                    String.valueOf(userid));
            if (!VipConstants.VIP_CANNOT_ONEFORMONTH_VALUE.equals(canOneForMonth)) {
                // 如果缓存中没有存储不可以参加一元包月活动，则需要查询确认是否需要添加虚拟活动
                CheckOneKeyPayRequest checkOneKeyPayRequest = new CheckOneKeyPayRequest(null, userid, null, null);
                OneBindTpResponse oneBindTpResponse = this.facadeTpDao.getVipTpDao().getOneBind(logPrefix, commonParam);
                if (oneBindTpResponse != null) {
                    if (oneBindTpResponse.getIssale()) {// 可以参加一元包月活动，则虚拟一个活动出来
                        for (VipPackageDto vipPackageDto : vipPackageList) {
                            if (String.valueOf(VipTpConstant.ORDER_TYPE_2).equals(vipPackageDto.getId())) {
                                // 取出包月套餐，获取其价格
                                String price = vipPackageDto.getCurrentPrice();
                                float discount = 0f;
                                if (StringUtils.isNotBlank(price) && NumberFormatUtil.isNumeric(price)) {

                                    float monthCount = StringUtil.toFloat(this.getMonthPaymentValues(
                                            VipConstants.VIP_MONTH_PAYMENT_AMOUNT_KEY,
                                            VipTpConstant.VIP_MONTH_PAYMENT_AMOUNT), 0f);

                                    discount = Float.valueOf(price) - monthCount;

                                    if (StringUtils.isNotBlank(packageActivityDiscount)
                                            && NumberFormatUtil.isNumeric(packageActivityDiscount)) {
                                        // 如果套餐包活动减免金额合法，还得先减去套餐包活动的减免金额
                                        discount = discount - Float.valueOf(packageActivityDiscount);
                                    }

                                    flag = false;// 可以参加易宝支付一元包月活动，则不能参加易宝支付其他的支付渠道活动了
                                    PaymentActivityDto dto = new PaymentActivityDto();
                                    List<Integer> payTypeList = new ArrayList<Integer>();
                                    payTypeList.add(new Integer(41));
                                    dto.setPayTypeList(payTypeList);
                                    dto.setActivityId(VipTpConstant.PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID);
                                    dto.setType(VipTpConstant.PAYMENT_ACTIVITY_TYPE_PAYMENT_CHANNEL);
                                    dto.setMonthType(VipTpConstant.ORDER_TYPE_2);
                                    dto.setProlongDays(0);
                                    String text = this.getMonthPaymentValues(VipConstants.VIP_MONTH_PAYMENT_TEXT_KEY,
                                            MessageUtils
                                                    .getMessage(ApplicationConstants.VIP_MONTH_PAYMENT_TEXT, locale));

                                    String monthCountText = String.format("%.1f", Float.valueOf(monthCount)) + text;
                                    dto.setIconText(monthCountText);// "一元包月"
                                    dto.setLableText(monthCountText);// "一元包月"
                                    dto.setBodyText(monthCountText);// "一元包月"
                                    dto.setNeedLogin(true);
                                    dto.setAllowPayLepoint(false);
                                    dto.setHasUserQuota(false);
                                    dto.setLeftQuota(-1);
                                    dto.setUserQuota(-1);
                                    dto.setDiscount(String.format("%.1f", Float.valueOf(discount)));
                                    list.add(dto);
                                    if (discount < 0) {
                                        // 价格小于等于1时，不添加虚拟活动，但是需要日志说明
                                        LOG.info(logPrefix + "， vip package current price is less than 1 yuan.");
                                    }
                                } else {
                                    LOG.info(logPrefix + "， vip package current price is illegal.");
                                }
                                break;
                            }
                        }
                    } else {// 将当前用户不可以参加一元包月活动信息缓存起来
                        // RedisUtil.setex(VipConstants.VIP_CANNOT_ONEFORMONTH_KEY_PREFIX
                        // + userid,
                        // CalendarUtil.SECONDS_OF_PER_DAY,
                        // VipConstants.VIP_CANNOT_ONEFORMONTH_VALUE);
                        this.facadeCacheDao.getVipCacheDao().setFlagBuyMonthlyPackage(String.valueOf(userid));
                    }
                }
            }
        }
        // 如果没有虚拟活动【一元包月】且套餐包月的活动不为空，则将套餐的包月活动加入到活动列表中来
        if (flag && monthPaymentDto != null) {
            list.add(monthPaymentDto);
        }
        return list;
    }

    private String getMonthPaymentValues(String key, String configValue) {
        String reValue = null;
        if (!StringUtils.isBlank(key)) {
            // reValue = (String) RedisUtil.get(key);
            reValue = this.facadeCacheDao.getVipCacheDao().getValue(key);
            if (StringUtils.isBlank(reValue)) {
                reValue = configValue;
                // RedisUtil.set(key, configValue);
                this.facadeCacheDao.getVipCacheDao().setValue(key, reValue);
            }
        }
        return reValue;
    }

    /**
     * 将付费活动相关信息，填充到套餐信息中
     * @param vipPackageMap
     * @param paymentActivityMap
     */
    private void fillVipPackageWithPaymentActivityes(Map<String, VipPackageDto> vipPackageMap,
                                                     Map<String, PaymentActivityDto> paymentActivityMap) {
        if (CollectionUtils.isEmpty(paymentActivityMap) || CollectionUtils.isEmpty(vipPackageMap)) {
            return;
        }
        for (Entry<String, PaymentActivityDto> paymentActivityEntry : paymentActivityMap.entrySet()) {
            String activityId = paymentActivityEntry.getKey();
            PaymentActivityDto paymentActivity = paymentActivityEntry.getValue();
            Integer monthType = paymentActivity.getMonthType();
            if (monthType == null) {// monthType指的是套餐ID，如果为空，则继续下一个
                continue;
            }
            VipPackageDto vipPackage = vipPackageMap.get(String.valueOf(monthType));
            if (vipPackage != null && paymentActivity.getType() != null) {
                if (VipTpConstant.PAYMENT_ACTIVITY_TYPE_PACKAGE == paymentActivity.getType()) {
                    // 套餐活动
                    vipPackage.setPackageActivityId(activityId);
                } else if (VipTpConstant.PAYMENT_ACTIVITY_TYPE_PAYMENT_CHANNEL == paymentActivity.getType()) {
                    // 支付渠道活动
                    List<Integer> payTypeList = paymentActivity.getPayTypeList();
                    if (!CollectionUtils.isEmpty(payTypeList)) {
                        Map<String, String> paymentChannelActivitys = vipPackage.getPaymentChannelActivitys();
                        if (paymentChannelActivitys == null) {
                            paymentChannelActivitys = new HashMap<String, String>();
                            vipPackage.setPaymentChannelActivitys(paymentChannelActivitys);
                        }
                        for (Integer payType : payTypeList) {
                            paymentChannelActivitys.put(String.valueOf(payType), activityId);
                        }
                    }
                }
            }
        }
    }

    /**
     * 套餐包进行排序
     * @param vipPackageList
     * @return
     */
    private List<VipPackageDto> sortPackageList(Map<String, VipPackageDto> vipPackageMap, String packagePriority) {
        List<VipPackageDto> vipPackageList = new ArrayList<VipPackageDto>();
        String packageOrder = null; // 仅仅是为了排序，而不是数据过滤；数据过滤放在boss做
        if (VipConstants.VIP_CHECKOUT_COUNTER_NEED_SORT_PACKAGE) {
            // 需要按照配置顺序排序
            packageOrder = this.readPackageOrder();
            if (StringUtils.isNotBlank(packagePriority)) {
                packageOrder = packagePriority + "," + packageOrder;
            }
        } else if (StringUtils.isNotBlank(packagePriority)) {
            // 不需要按照配置顺序排序但是需要设置套餐的优先顺序
            packageOrder = packagePriority;
        }
        if (StringUtils.isBlank(packageOrder)) {// 排序配置为空或者不需要优先配置
            vipPackageList.addAll(vipPackageMap.values());
        } else {
            String[] keys = packageOrder.split(",");
            for (String key : keys) {
                VipPackageDto vipPackageDto = vipPackageMap.get(key);
                if (vipPackageDto != null) {
                    vipPackageList.add(vipPackageDto);
                    vipPackageMap.remove(key);// 这么做的目的是，如果套餐包顺序配置中没有设置一些套餐，则追加在之后
                }
            }
            if (vipPackageMap != null && vipPackageMap.size() > 0) {
                vipPackageList.addAll(vipPackageMap.values());
            }
        }

        return vipPackageList;
    }

    /**
     * 对收银台套餐进行排序
     */
    private List<PricePackageListDto> sortPricePackage(List<PricePackageListDto> pricePackageList) {
        List<PricePackageListDto> sortedList = null;

        // 现采取“根据配置的id顺序”，对套餐进行排序
        if (!CollectionUtils.isEmpty(pricePackageList)
                && StringUtils.isNotBlank(VipConstants.PRICE_PACKAGE_DEFAULT_ID_ORDER)) {

            // 获取配置的id顺序字符串，各id用英文逗号拼接
            String[] idArray = VipConstants.PRICE_PACKAGE_DEFAULT_ID_ORDER
                    .split(LetvUserVipCommonConstant.COMMON_SPILT_SEPARATOR);

            // 整理pricePackageList
            Map<String, PricePackageListDto> pricePackageMap = new HashMap<String, PricePackageListDto>();
            for (PricePackageListDto pricePackage : pricePackageList) {
                pricePackageMap.put(pricePackage.getId(), pricePackage);
            }

            // 筛选数据
            sortedList = new ArrayList<PricePackageListDto>();
            for (String id : idArray) {
                PricePackageListDto dto = pricePackageMap.get(id);
                if (dto != null) {
                    sortedList.add(dto);
                }
            }
        }

        return sortedList;
    }

    /**
     * 验证产品类型列表是否可用； 判断规则为，参数列表不为空；使用英文逗号分隔符切割后，每个元素都是合法的产品类型
     * @param productTypes
     * @return
     */
    private boolean assertProductTypesAvaliable(String productTypes) {
        if (StringUtils.isBlank(productTypes)) {
            return false;
        }
        String[] productTypeArray = productTypes.split(LetvUserVipCommonConstant.COMMON_SPILT_SEPARATOR);
        if (ArrayUtils.isEmpty(productTypeArray)) {
            return false;
        }
        try {
            for (String productTypeStr : productTypeArray) {
                Integer productType = Integer.parseInt(productTypeStr);
                if (VipTpConstantUtils.BUYTYPE.getBuytypeById(productType.intValue()) == null) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            LOG.error("assertProductTypesAvaliable get error!", e);
            return false;
        }

        return true;
    }

    /**
     * 通过调用批量接口的方式，将用户购买的某一场直播券激活
     * @param userid
     * @param pid
     * @return boolean false--激活失败，预示用户鉴权也不会成功；true--激活成功，预示用户鉴权可成功
     */
    private boolean activeUserLiveTicket(Long userid, String pid, String mac, String deviceKey) {
        Map<String, LiveProjectPermissionResponseTp.PermissionInfo> permissions = this.facadeTpDao.getLiveTpDao().getPermission(pid,
                String.valueOf(userid), mac, deviceKey);
        if (CollectionUtils.isEmpty(permissions)) {
            return false;
        }
        LiveProjectPermissionResponseTp.PermissionInfo permission = permissions.get(pid);
        return permission != null && VipConstants.COMMOM_DEFAULT_STRING_1.equals(permission.getStatus());
    }

    /**
     * 直播SDK需求，通过调用批量接口的方式，将用户购买的某一场直播券激活
     * @param mac
     * @param pid
     * @return
     */
    private boolean tpsdkActiveUserLiveTicket(String mac, String pid) {
        Map<String, LiveProjectPermissionResponseTp.PermissionInfo> permissions = this.facadeTpDao.getLiveTpDao().getPermissionByMac(pid, mac);
        if (CollectionUtils.isEmpty(permissions)) {
            return false;
        }
        LiveProjectPermissionResponseTp.PermissionInfo permission = permissions.get(pid);
        return permission != null && VipConstants.COMMOM_DEFAULT_STRING_1.equals(permission.getStatus());
    }

    /**
     * 判断当前用户是否已经绑定过其他设备
     */
    private void checkUserBindOrtherDevice(UserAccountDto accountDto, String currentDeviceKey,
                                           UserBindTpResponse userBindTp) {
        Set<String> bindDeviceKeys = new HashSet<String>();
        String bindInfos = userBindTp.getValues().getBindInfos();
        String bindKeyList = userBindTp.getValues().getBindKeyList();
        if (StringUtils.isNotBlank(bindInfos)) {
            // 新机卡逻辑
            String[] bindKeyTypeArray = bindInfos.split(",");
            if (bindKeyTypeArray != null) {
                for (String bindKeyType : bindKeyTypeArray) {
                    if (StringUtils.isNotBlank(bindKeyType) && bindKeyType.contains("-")) {
                        String[] bindKeyTypeSuit = bindKeyType.split("-");
                        if (bindKeyTypeSuit != null
                                && bindKeyTypeSuit.length == 2
                                && StringUtils.isNotBlank(bindKeyTypeSuit[0])
                                && StringUtils.isNotBlank(bindKeyTypeSuit[1])
                                && (String.valueOf(VipTpConstant.DEVICE_BIND_DEVICE_TYPE_TV).equals(bindKeyTypeSuit[1]) || String
                                .valueOf(VipTpConstant.DEVICE_BIND_DEVICE_TYPE_LETV_BOX).equals(
                                        bindKeyTypeSuit[1]))) {
                            bindDeviceKeys.add(bindKeyTypeSuit[0].toUpperCase());
                        }
                    }
                }
            }
        } else {
            // 兼容老机卡逻辑
            String[] bindKeyArray = bindKeyList.split(",");
            if (bindKeyArray != null) {
                for (String bindKey : bindKeyArray) {
                    if (StringUtils.isNotBlank(bindKey)) {
                        bindDeviceKeys.add(bindKey.toUpperCase());
                    }
                }
            }
        }

        /**
         * 不论机卡或者非机卡设备，如果1.返回已绑设备列表中有一个设备，但是不是本机；或2.返回已绑设备列表有多个设备，均表示已绑定别的设备
         */
        if ((bindDeviceKeys.size() == 1 && !bindDeviceKeys.contains(currentDeviceKey.toUpperCase()))
                || bindDeviceKeys.size() > 1) {
            accountDto.setHasBindOtherDevice(1);
        } else {
            accountDto.setHasBindOtherDevice(2);
        }
    }

    /**
     * 从GetUserInfoTpResponse中获取用户头像，如果获取不到，则使用默认头像defaultIcon
     * @param userInfoTpResponse
     * @param defaultIcon
     * @return
     */
    private String getUserIcon(GetUserInfoTpResponse userInfoTpResponse, String defaultIcon) {
        String picture = userInfoTpResponse.getBean().getPicture();

        if (StringUtils.isNotBlank(picture)) {
            String[] pics = picture.split(",");
            if (pics != null && pics.length == 4) {
                picture = pics[1];
            }
        }

        picture = StringUtils.isNotBlank(picture) ? picture : defaultIcon;

        return picture;
    }

    /**
     * 获取用户昵称
     * @param getUserAccountInfoRequest
     * @param userInfoTpResponse
     * @return
     */
    private String getUserDisplayName(GetUserInfoTpResponse userInfoTpResponse) {
        String displayName = null;

        String[] names = new String[] { userInfoTpResponse.getBean().getMobile(),
                userInfoTpResponse.getBean().getEmail(), userInfoTpResponse.getBean().getNickname() };
        int i = 0;
        while (StringUtils.isEmpty(displayName) && i < names.length) {
            displayName = names[i];
            i++;
        }

        return displayName;
    }

    /**
     * 读取套餐顺序配置，读缓存，读静态文件，读配置文件
     * @return
     */
    private String readPackageOrder() {
        // 1、读缓存
        // String packageOrder = (String)
        // RedisUtil.get(VipConstants.PACKAGE_ORDER_KEY);
        String packageOrder = this.facadeCacheDao.getVipCacheDao().getPackageOrder();
        if (StringUtils.isBlank(packageOrder)) {
            // 2、读静态文件
            Map<String, String> map = this.facadeTpDao.getStaticTpDao().getStaticFileContent(
                    ApplicationUtils.get(ApplicationConstants.VIP_PROFILE_URL));// 静态文件内容
            if (map != null && map.size() > 0) {
                packageOrder = map.get(VipConstants.PACKAGE_ORDER_KEY);
            }
            // 3、如果读不到，读内存
            if (StringUtils.isBlank(packageOrder)) {
                packageOrder = VipConstants.PRICE_PACKAGE_ID_ORDER;
            } else {
                // RedisUtil.set(VipConstants.PACKAGE_ORDER_KEY, packageOrder);
                this.facadeCacheDao.getVipCacheDao().setPackageOrder(packageOrder);
            }
            packageOrder = VipConstants.PRICE_PACKAGE_ID_ORDER;
        }
        return packageOrder;
    }

    private Map<String, PaymentActivityDto> getVirtualPaymentActivity(Map<String, VipPackageDto> vipPackageMap,
                                                                      Map<String, PaymentActivityDto> activityMap, List<String> paymentChannelList, Long userid,
                                                                      String logPrefix, CommonParam commonParam) {
        if (!paymentChannelList.contains("41") || userid == null || userid < 0) {
            // 如果最终支付渠道不包含41，或未登录状态，直接不考虑处理特殊活动
            LOG.info(logPrefix + ",paymentChannel not contains 41.");
            return activityMap;
        }

        /** 3、用户可以参加包月套餐41支付渠道的活动 */
        // String canOneForMonth = (String)
        // RedisUtil.get(VipConstants.VIP_CANNOT_ONEFORMONTH_KEY_PREFIX +
        // userid);
        String canOneForMonth = this.facadeCacheDao.getVipCacheDao().getFlagBuyMonthlyPackage(String.valueOf(userid));
        if (!VipConstants.VIP_CANNOT_ONEFORMONTH_VALUE.equals(canOneForMonth)) {
            // 如果缓存中没有存储不可以参加活动标志，则需要查询确认是否需要添加虚拟活动
            CheckOneKeyPayRequest checkOneKeyPayRequest = new CheckOneKeyPayRequest(null, userid, null, null);
            OneBindTpResponse oneBindTpResponse = this.facadeTpDao.getVipTpDao().getOneBind(logPrefix, commonParam);

            if (oneBindTpResponse != null) {
                if (oneBindTpResponse.getIssale() != null && Boolean.TRUE == oneBindTpResponse.getIssale()) {
                    /*
                     * 用户能参加，则需要新建虚拟活动； 如果无通用的包月易宝绑卡支付，则直接添加；
                     * 如果有通用的包月易宝绑卡支付，则先干掉再添加；
                     * 虚拟活动价格=包月套餐现价-包月套餐上活动减免金额-特殊活动影响下最终价格（比如9.9）
                     */

                    if (activityMap == null) {
                        // 当activityMap为空时，新建一个，一来方式空指针异常，而来有可能原始activityMap为空，但后来能参加虚拟活动时就需要往里放
                        activityMap = new HashMap<String, PaymentActivityDto>();
                    }

                    // 获取包月套餐，包月套餐不为空才处理
                    VipPackageDto vipPackageDto = vipPackageMap.get(String.valueOf(VipTpConstant.ORDER_TYPE_2));

                    if (vipPackageDto != null) {
                        // 包月套餐上的套餐活动
                        PaymentActivityDto packageActivity = activityMap.get(vipPackageDto.getPackageActivityId());
                        // 包月套餐上的支付渠道活动
                        Map<String, String> paymentChannelActivitys = vipPackageDto.getPaymentChannelActivitys();
                        if (paymentChannelActivitys == null) {
                            paymentChannelActivitys = new HashMap<String, String>();
                            vipPackageDto.setPaymentChannelActivitys(paymentChannelActivitys);
                        }

                        // 包月套餐上原始的通用易宝支付渠道活动，可能没有；这个活动如果有，则需要被干掉
                        PaymentActivityDto yeepayPaymentChannelCommonActivity = activityMap.get(paymentChannelActivitys
                                .get("41"));

                        /**
                         * 计算套餐41支付渠道活动的最终减免金额逻辑： 1、根据活动计算BOSS的最终定价
                         * 2、套餐现价减去套餐活动减免金额，得到最终现价
                         * 3、最终现价减去BOSS最终定价，得到41支付渠道活动的最终减免金额
                         */
                        // 获取包月套餐现价
                        String packageCurrentPriceStr = vipPackageDto.getCurrentPrice(); // 包月套餐现价
                        String packageActivityPriceStr = packageActivity == null ? "0.0" : packageActivity
                                .getDiscount(); // 包月套餐上活动减免金额，没活动就是0.0
                        String yeepaySpecialAcrivityFinalPriceStr = this.getMonthPaymentValues(
                                VipConstants.VIP_MONTH_PAYMENT_AMOUNT_KEY, VipTpConstant.VIP_MONTH_PAYMENT_AMOUNT); // 特殊活动影响下最终价格

                        if (NumberFormatUtil.isNumeric(packageCurrentPriceStr)
                                && NumberFormatUtil.isNumeric(packageActivityPriceStr)
                                && NumberFormatUtil.isNumeric(yeepaySpecialAcrivityFinalPriceStr)) {
                            // String.format("%.1f", Float.valueOf(monthCount))
                            float packageCurrentPrice = StringUtil.toFloat(packageCurrentPriceStr);// 包月套餐现价
                            float packageActivityPrice = StringUtil.toFloat(packageActivityPriceStr);// 包月套餐上活动减免金额，没活动就是0.0
                            float yeepaySpecialAcrivityFinalPrice = StringUtil
                                    .toFloat(yeepaySpecialAcrivityFinalPriceStr);// 特殊活动影响下最终价格

                            float yeepaySpecialAcrivityDiscount = packageCurrentPrice - packageActivityPrice
                                    - yeepaySpecialAcrivityFinalPrice;

                            if (yeepaySpecialAcrivityDiscount < 0) {
                                // 价格小于0时，打个日志说明
                                LOG.info(logPrefix + "， vip package current price is less than 0 yuan.");
                            }
                            String yeepaySpecialAcrivityDiscountStr = String.format("%.1f",
                                    Float.valueOf(yeepaySpecialAcrivityDiscount));

                            PaymentActivityDto dto = new PaymentActivityDto();
                            List<Integer> payTypeList = new ArrayList<Integer>();
                            payTypeList.add(new Integer(41));// 41支付渠道
                            dto.setPayTypeList(payTypeList);
                            dto.setActivityId(VipTpConstant.PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID);
                            dto.setType(VipTpConstant.PAYMENT_ACTIVITY_TYPE_PAYMENT_CHANNEL);
                            dto.setMonthType(VipTpConstant.ORDER_TYPE_2);
                            dto.setProlongDays(0);

                            String monthCountText = String.format("%.1f",
                                    Float.valueOf(yeepaySpecialAcrivityFinalPrice))
                                    + this.getMonthPaymentValues(VipConstants.VIP_MONTH_PAYMENT_TEXT_KEY, MessageUtils
                                    .getMessage(ApplicationConstants.VIP_MONTH_PAYMENT_TEXT,
                                            commonParam.getLangcode()));
                            dto.setIconText(monthCountText);
                            dto.setLableText(monthCountText);
                            dto.setBodyText(monthCountText);
                            dto.setNeedLogin(true);
                            dto.setAllowPayLepoint(false);
                            dto.setHasUserQuota(false);
                            dto.setLeftQuota(-1);
                            dto.setUserQuota(-1);
                            dto.setDiscount(yeepaySpecialAcrivityDiscountStr);

                            // 将虚拟活动加入到活动map中
                            activityMap.put(VipTpConstant.PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID, dto);

                            // 将虚拟活动ID加入博阿月套餐支付渠道活动中，替换原有的（如果存在的话）
                            paymentChannelActivitys.put("41", VipTpConstant.PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID);
                        } else {
                            // 价格异常，需要打印日志，这个时候页面展示没有易宝特殊活动，但是极有可能，到boss支付后台是能够显示9.9
                            LOG.info(logPrefix
                                    + ",vippackage, vippackage activity or 41 paymentchannel final price illegal.");
                        }
                    }
                } else {// 将当前用户不可以参加一元包月活动信息缓存起来
                    LOG.info(logPrefix + ",current user cannot attend the 41 paymentchannel activity.");
                    // RedisUtil.setex(VipConstants.VIP_CANNOT_ONEFORMONTH_KEY_PREFIX
                    // + userid,
                    // CalendarUtil.SECONDS_OF_PER_DAY,
                    // VipConstants.VIP_CANNOT_ONEFORMONTH_VALUE);
                    this.facadeCacheDao.getVipCacheDao().setFlagBuyMonthlyPackage(String.valueOf(userid));
                }
            } else { // 查询失败 打印Log
                LOG.info(logPrefix + ",query user if can attend 41 paymentchannel activity failure.");
            }
        } else {
            LOG.info(logPrefix + ",current user cannot attend the 41 paymentchannel activity.");
        }
        return activityMap;
    }

    /**
     * 从第三方响应中解析出PaymentActivityDto信息；
     * 返回Map格式数据，key--活动id，value--整理后的活动信息，PaymentActivityDto；
     * @param paymentActivityTpResponse
     * @return
     */
    public Map<String, PaymentActivityDto> parsePaymentActivityFromTpNew(List<BossActivityData> dataList,
                                                                         String langcode) {
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        Map<String, PaymentActivityDto> paymentActivityMap = new HashMap<String, PaymentActivityDto>();
        for (BossActivityData activityData : dataList) {
            PaymentActivityDto activityDto = PaymentActivityBuilder.build(activityData, langcode);
            if (activityDto != null) {
                paymentActivityMap.put(activityDto.getActivityId(), activityDto);
            }
        }

        return paymentActivityMap;
    }

    /**
     * 从第三方响应中解析出PaymentActivityDto信息；
     * 返回Map格式数据，key--活动id，value--整理后的活动信息，PaymentActivityDto；
     * @param paymentActivityTpResponse
     * @return
     */
    private Map<String, PaymentActivityDto> parsePaymentActivityFromTp(
            PaymentActivityTpResponse paymentActivityTpResponse, Integer vipType, String langcode) {
        Map<String, PaymentActivityDto> paymentActivityMap = null;
        Map<Integer, List<PaymentActivityTpResponse.PaymentActivity>> activitys = paymentActivityTpResponse.getValues().getActivitys();
        if (!CollectionUtils.isEmpty(activitys)) {
            paymentActivityMap = new HashMap<String, PaymentActivityDto>();
            for (Entry<Integer, List<PaymentActivityTpResponse.PaymentActivity>> entry : activitys.entrySet()) {
                List<PaymentActivityTpResponse.PaymentActivity> paymentActivityInfoList = entry.getValue();
                if (!CollectionUtils.isEmpty(paymentActivityInfoList)) {
                    for (PaymentActivityTpResponse.PaymentActivity paymentActivityInfo : paymentActivityInfoList) {
                        PaymentActivityDto dto = PaymentActivityBuilder.build(paymentActivityInfo, vipType, langcode);
                        if (dto != null) {
                            paymentActivityMap.put(dto.getActivityId(), dto);
                        }
                    }
                }
            }
        }

        return paymentActivityMap;
    }

    /**
     * 解析ShowInfo，主要是解析link，link的格式：
     * "native{id=***;monthType=***;payType=***}或者h5{h5url=***;id=***;monthType=***;payType=***}"
     * @param showInfo
     * @return
     */
    private RecommendPopInfoDto.GuideInfo parseShowInfo(GetRecommendPopInfoTpResponse.RecommendPopInfo.ShowInfo showInfo) {
        RecommendPopInfoDto.GuideInfo guideInfo = new RecommendPopInfoDto.GuideInfo();
        guideInfo.setPic(showInfo.getPic());
        guideInfo.setTitle(showInfo.getTitle());

        String link = showInfo.getLink();
        if (StringUtils.isBlank(link)) {
            return guideInfo;
        } else {
            int index = link.indexOf("{");
            if (index <= 0) {// 关键字缺失，直接返回
                return guideInfo;
            }
            guideInfo.setOpenType(VipConstants.getRecommendPopOpenType(link.substring(0, index)));
            int lastIndex = link.lastIndexOf("}");
            if (lastIndex > 0) {
                link = link.substring(index + 1, lastIndex);// 截掉第一部分，剩余"id=***,monthType=***,payType=***"
            } else {
                link = link.substring(index + 1);// 截掉第一部分，剩余"id=***,monthType=***,payType=***"
            }

            link = link.replaceAll("；", ";");// 在此做一步容错处理，将中文逗号替换成英文逗号
            String[] array = link.split(";");// 第一次拆分
            if (array == null || array.length == 0) {
                return guideInfo;
            } else {
                Map<String, String> maps = new HashMap<String, String>();
                for (String str : array) {
                    if (StringUtils.isNotBlank(str)) {
                        String[] keyValue = str.split("=");
                        if (keyValue == null) {
                            continue;
                        }
                        if (keyValue.length == 2) {// 一般的参数解析
                            maps.put(keyValue[0], keyValue[1]);
                        } else if (keyValue.length > 2) {
                            // 特殊的参数解析(有多个等号)，例如解析h5url=http://letv.com?size=1&pageSize=20
                            int splitIndex = str.indexOf("=");
                            if (splitIndex > 0) {
                                maps.put(str.substring(0, splitIndex), str.substring(splitIndex + 1));
                            }
                        }
                    }
                }
                guideInfo.setId(maps.get("id"));
                guideInfo.setProductId(maps.get("monthType"));
                guideInfo.setPaymentChannel(maps.get("payType"));
                guideInfo.setJumpUrl(maps.get("h5url"));
            }
            return guideInfo;
        }
    }

    /**
     * get checkout counter data
     * @param productId
     * @param activityIds
     * @param position
     * @param commonParam
     * @return
     */
    public Response<CheckOutCounter> getCheckoutCounterNew(String productId, String activityIds, String position,
                                                           CommonParam commonParam) {
        CheckOutCounter data = new CheckOutCounter();
        String errorCode = null;
        // all checkout counter types need get vippackage data.
        PricePackageListTpResponse packageTpResponse = this.facadeTpDao.getVipTpDao().getPricePackageList();
        if (packageTpResponse == null || "0".equals(packageTpResponse.getStatus())
                || CollectionUtils.isEmpty(packageTpResponse.getPackageList())) {
            // get vippackage data failured.
            errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
            LOG.info("getCheckOutCounterNew_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + productId
                    + "_" + activityIds + "_" + position + "[errorCode=" + errorCode + "]: get vip package failed.");
        } else {
            List<PricePackageListTpResponse.PackageRecord> packageList = packageTpResponse.getPackageList();
            Map<String, ProductType> productDatas = new HashMap<String, ProductType>();
            Map<String, ProductData> productDataMap = new HashMap<String, ProductData>();
            // default image for checkout counter
            String defaultImg = this.getVipConfigInfo(VipConstants.VIP_CHECKOUT_COUNTER_DEFAULT_IMG, "");
            boolean isVideoSale = false;
            if (position != null && position.indexOf(VipTpConstant.USER_TOUCH_POSITION_VIDEO_SALE) > -1) {
                isVideoSale = true;
                defaultImg = this.getVipConfigInfo(VipConstants.VIP_CHECKOUT_COUNTER_DEFAULT_IMG_VIDEO_SALE, "");
            }
            // set vip package info and get year package id
            String yearPackageId = this.setVipPackageValue(packageList, productDatas, productDataMap, defaultImg,
                    commonParam);
            if (isVideoSale) {// video sale default year vip package
                productId = yearPackageId;
            }
            if (CollectionUtils.isEmpty(productDatas) || CollectionUtils.isEmpty(productDataMap)
                    || (isVideoSale && StringUtil.isBlank(productId))) {
                errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
                LOG.info("getCheckOutCounterNew_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + productId + "_" + activityIds + "_" + position + "[errorCode=" + errorCode
                        + "]: parse vippackage result is empty.");
            } else {
                Map<String, PaymentActivityDto> paymentActivityMap = null;
                if (StringUtil.isBlank(position)) {// non activity position
                    productId = "";
                    activityIds = this.getVipConfigInfo(VipConstants.VIP_CHECKOUT_COUNTER_DEFAULT_ACTIVITY_KEY, "");
                }
                if (StringUtil.isNotBlank(activityIds)) {
                    paymentActivityMap = this.getPaymentActivityById(activityIds, position, commonParam);
                    if (!CollectionUtils.isEmpty(paymentActivityMap)) {
                        if (StringUtil.isBlank(position)) {
                            for (Entry<String, PaymentActivityDto> entry : paymentActivityMap.entrySet()) {
                                PaymentActivityDto dto = entry.getValue();
                                productId = String.valueOf(dto.getMonthType());
                            }
                        }
                    } else if (StringUtil.isNotBlank(position)) {
                        // if from activity entry and activity is invalid then
                        // return error.
                        errorCode = ErrorCodeConstants.VIP_TOUCH_ACTIVITY_OFFLINE;
                        LOG.info("getCheckOutCounterNew_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                                + productId + "_" + activityIds + "_" + position + " parse direct payment activity ["
                                + activityIds + "] failed.");
                    }
                }
                if (errorCode == null) {
                    // set checkout counter data
                    this.setCheckOutCounterValue(data, productDataMap, productDatas, productId, position, commonParam);
                    if (CollectionUtils.isEmpty(data.getProductDatas())) {
                        errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
                        LOG.info("getCheckOutCounterNew_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                                + productId + "_" + activityIds + "_" + position + "[errorCode=" + errorCode
                                + "]: parse vippackage result is empty.");
                    } else {
                        // merge vip package and activity info
                        this.mergePackageAndActivityValue(data.getProductDatas(), paymentActivityMap, productDataMap,
                                commonParam.getLangcode());
                        // set checkout counter show text
                        this.setCheckoutCounterShowText(data, commonParam);
                    }
                }
            }
        }

        Response<CheckOutCounter> response = new Response<CheckOutCounter>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }
        return response;
    }

    /**
     * get activity info of member IOUs
     * @param activeScene
     *            from where to participate activity
     * @param commonParam
     * @return
     */
    public Response<BaseData> getFreeVipInfo(Integer activeScene, CommonParam commonParam) {
        GetFreeVipInfoDto data = new GetFreeVipInfoDto();
        ActiveFreeVipTpResponse tpResponse = this.facadeTpDao.getVipTpDao().getFreeVipInfo(activeScene, commonParam);
        String errorCode = null;
        if (tpResponse == null) {// interface invoke failure.
            errorCode = ErrorCodeConstants.VIP_GET_FREEVIP_INFO_ERROR;
        } else {
            if ((tpResponse.getStatus() == null) || (tpResponse.getStatus() == 0) || (tpResponse.getData() == null)) {
                // interface get data failure.
                errorCode = ErrorCodeConstants.VIP_GET_FREEVIP_INFO_ERROR;
            } else {
                // get data from third party and some word read from service
                // config file
                ActiveFreeVipTpResponse.ActiveFreeVipData activeFreeVipData = tpResponse.getData();
                data.setButtonText1(activeFreeVipData.getIndexbutton());
                data.setButtonText2(MessageUtils.getMessage(VipConstants.VIP_FREEVIP_BUTTON, commonParam.getLangcode()));
                data.setImg(activeFreeVipData.getIndeximge());
                ShowInfoDto rule = new ShowInfoDto();
                data.setRule(rule);
                rule.setButtonText1(MessageUtils.getMessage(VipConstants.VIP_FREEVIP_RULE_BUTTON,
                        commonParam.getLangcode()));
                rule.setTitle(MessageUtils.getMessage(VipConstants.VIP_FREEVIP_RULE_TITLE, commonParam.getLangcode()));
                rule.setContent(activeFreeVipData.getRule());

                ShowInfoDto statement = new ShowInfoDto();
                data.setStatement(statement);
                statement.setButtonText1(MessageUtils.getMessage(VipConstants.VIP_FREEVIP_STATEMENT_BUTTON,
                        commonParam.getLangcode()));
                statement.setTitle(MessageUtils.getMessage(VipConstants.VIP_FREEVIP_STATEMENT_TITLE,
                        commonParam.getLangcode()));
                statement.setContent(activeFreeVipData.getDisclaimer());
            }
        }

        Response<BaseData> response = new Response<BaseData>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }

        return response;
    }

    /**
     * active member IOUs service
     * @param activeScene
     *            from where to participate activity
     * @param activeType
     *            active type,1--active,2--get payment info
     * @param commonParam
     * @return
     */
    public Response<BaseData> activeFreeVip(Integer activeType, Integer activeScene, CommonParam commonParam) {
        ActiveFreeVipDto data = new ActiveFreeVipDto();
        String errorCode = null;
        if (activeType != null && activeType == 2) {// 获取还款信息
            if (StringUtil.isBlank(commonParam.getUserId())) {
                errorCode = ErrorCodeConstants.VIP_GET_FREEVIP_INFO_ERROR;
            } else {
                Object object = this.facadeCacheDao.getVipCacheDao().getPaymentInfo(commonParam);
                if (object != null) {
                    if (object instanceof ActiveFreeVipDto) {
                        data = (ActiveFreeVipDto) object;
                    } else {// if save json to cache and get object from cache
                        // will missing the class type
                        try {
                            String json = objectMapper.writeValueAsString(object);
                            data = JsonUtil.parse(json, ActiveFreeVipDto.class);
                        } catch (Exception e) {
                            LOG.error("activeFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + " error:" + e.getMessage(), e);
                        }
                    }
                } else {
                    errorCode = ErrorCodeConstants.VIP_GET_FREEVIP_INFO_ERROR;
                }
            }
        } else {// 获取激活信息
            if (StringUtil.isBlank(commonParam.getUserId())) {
                errorCode = ErrorCodeConstants.VIP_ACTIVE_FREEVIP_ERROR;
            } else {
                ActiveFreeVipTpResponse tpResponse = this.facadeTpDao.getVipTpDao().activeFreeVip(activeScene,
                        commonParam);
                if (tpResponse == null) {
                    // interface invoke failure.
                    errorCode = ErrorCodeConstants.VIP_ACTIVE_FREEVIP_ERROR;
                } else {
                    if ((tpResponse.getStatus() == null) || (tpResponse.getStatus() == 0)) {
                        // interface get data failure.
                        errorCode = ErrorCodeConstants.VIP_ACTIVE_FREEVIP_ERROR;
                    } else {// active free vip
                        // get data from third party and some word read from
                        // service
                        // config file
                        ActiveFreeVipTpResponse.ActiveFreeVipData activeFreeVipData = tpResponse.getDetails();
                        if (activeFreeVipData != null) {
                            data.setTitle(MessageUtils.getMessage(VipConstants.VIP_ACTIVE_FREEVIP_TITLE,
                                    commonParam.getLangcode()));
                            data.setVipEndDesc(MessageUtils.getMessage(VipConstants.VIP_ACTIVE_FREEVIP_DESC,
                                    commonParam.getLangcode()));
                            VipAccountTpResponse vipInfo = this.facadeTpDao.getVipTpDao().getVipAccount(commonParam);// 获取会员信息
                            if (vipInfo != null && vipInfo.getIsvip() != null && vipInfo.getIsvip() != 0) {// 会员状态已改变,使用会员时长
                                data.setVipEndDate(TimeUtil.getDateStringFromLong(vipInfo.getEndtime(),
                                        TimeUtil.SHORT_DATE_FORMAT));
                            } else if (activeFreeVipData.getFree_end_time() != null) { // 否则赋值为白条到期时间
                                data.setVipEndDate(TimeUtil.getDateStringFromLong(
                                        activeFreeVipData.getFree_end_time() * 1000, TimeUtil.SHORT_DATE_FORMAT));
                            }
                            Integer delayPayBackDays = StringUtil.toInteger(activeFreeVipData.getLimit_day());
                            data.setDelayPayBackDays(delayPayBackDays);
                            if (activeFreeVipData.getRequired_pay_time() != null) {
                                String delayPayBackDate = TimeUtil.getDateStringFromLong(
                                        activeFreeVipData.getRequired_pay_time() * 1000, TimeUtil.SHORT_DATE_FORMAT);
                                data.setDelayPayBackDate(delayPayBackDate);
                            }
                            Object[] codeMap = { delayPayBackDays };
                            data.setDelayPayBackDesc1(MessageUtils.getMessage(
                                    VipConstants.VIP_ACTIVE_FREEVIP_DELAY_DESC, commonParam.getLangcode(), codeMap));
                            data.setDelayPayBackNotice(MessageUtils.getMessage(
                                    VipConstants.VIP_ACTIVE_FREEVIP_DELAY_NOTICE, commonParam.getLangcode()));
                            data.setButtonText1(activeFreeVipData.getPay_button());
                            data.setButtonText2(MessageUtils.getMessage(VipConstants.VIP_ACTIVE_FREEVIP_PAY_BUTTON,
                                    commonParam.getLangcode()));
                            JumpData jump = new JumpData();
                            jump.setType(DataConstant.DATA_TYPE_FREE_VIP_PAYBACK);
                            PilotDto value = new PilotDto();
                            value.setDataType(DataConstant.DATA_TYPE_FREE_VIP_PAYBACK);
                            value.setOrderType(StringUtil.toInteger(activeFreeVipData.getPay_productid()));
                            value.setPosition("active");
                            jump.setValue(value);
                            data.setJump(jump);
                            Long startTime = System.currentTimeMillis() / 1000;
                            ActiveFreeVipDto paymentdata = this.parsePaymentInfo(activeFreeVipData.getPay_pric(),
                                    data.getButtonText2(), activeFreeVipData.getPay_image(), startTime,
                                    activeFreeVipData.getFree_end_time(), activeFreeVipData.getRequired_pay_time(),
                                    activeFreeVipData.getEndTime(), activeFreeVipData.getPay_monthtype(),
                                    activeFreeVipData.getPay_productid(), activeFreeVipData.getPay_desc(),
                                    commonParam.getLangcode());
                            if (paymentdata != null) {
                                this.facadeCacheDao.getVipCacheDao().setPaymentInfo(commonParam, paymentdata);
                            }
                        }
                    }
                }
            }
        }

        Response<BaseData> response = new Response<BaseData>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }

        return response;
    }

    public Response<List<VoucherDto>> getUserVoucher(Integer type, String status, Integer page, Integer pageSize,
                                                     CommonParam commonParam) {
        String errorCode = null;
        List<VoucherDto> dataList = new ArrayList<VoucherDto>();
        if (StringUtil.isNotBlank(commonParam.getUserId())) {
            // 调BOSS接口获取代金券信息
            GetUserVoucherTpResponse tpResponse = this.facadeTpDao.getVipTpDao().getUserVoucher(status, page, pageSize,
                    commonParam);
            if (tpResponse != null && !CollectionUtils.isEmpty(tpResponse.getData())) {
                List<GetUserVoucherTpResponse.Voucher> vouchers = tpResponse.getData();
                Calendar now = Calendar.getInstance();
                String currentTime = TimeUtil.getDateString(now, TimeUtil.SIMPLE_DATE_FORMAT);
                for (GetUserVoucherTpResponse.Voucher voucher : vouchers) {
                    if (currentTime.compareTo(voucher.getEndTime()) >= 0) {
                        continue;// filter the expired voucher
                    }
                    String platform = MmsDataUtil.getPayPlatform(commonParam.getP_devType());
                    if (CollectionUtils.isEmpty(voucher.getTerminals())
                            || !voucher.getTerminals().contains(Integer.parseInt(platform))) {
                        continue;// filter no tv terminal voucher
                    }
                    List<String> productIds = null;// suitable product id array
                    boolean flag = true;
                    if (type == 0) {
                        flag = false;
                    } else if (!CollectionUtils.isEmpty(voucher.getRules())) {
                        List<VoucherRule> rules = voucher.getRules();
                        for (VoucherRule voucherRule : rules) {
                            if (voucherRule.getType() != null && voucherRule.getType().equals(type)) {
                                productIds = voucherRule.getIds();
                                flag = false;// 只要适用当前收银台，就不过滤
                                break;
                            }
                        }
                    }
                    if (flag) {
                        continue;
                    }
                    VoucherDto voucherDto = new VoucherDto();
                    voucherDto.setCouponCode(voucher.getCouponCode());
                    if (voucher.getStartTime() != null) {
                        voucherDto.setStartTime(TimeUtil.parseDate2Timestamp(voucher.getStartTime(),
                                TimeUtil.SIMPLE_DATE_FORMAT));
                    }
                    if (voucher.getEndTime() != null) {
                        voucherDto.setEndTime(TimeUtil.parseDate2Timestamp(voucher.getEndTime(),
                                TimeUtil.SIMPLE_DATE_FORMAT));
                    }
                    voucherDto.setAmount4Yuan(voucher.getAmount4Yuan());
                    voucherDto.setName(voucher.getName());
                    voucherDto.setProductIds(productIds);
                    voucherDto.setTemplateId(voucher.getTemplateId());
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

    protected void setCheckOutCounterValue(CheckOutCounter data, Map<String, ProductData> productDataMap,
                                           Map<String, CheckOutCounter.ProductType> productTypes, String productId, String position, CommonParam commonParam) {
        if (StringUtil.isNotBlank(productId)) {
            data.setCheckOutCounterType(VipConstants.VIP_CHECKOUTCOUNTER_TYPE_DIRECT);
            ProductData productData = productDataMap.get(productId);
            if (productData != null) {// 所定向的套餐不为空
                productDataMap = new HashMap<String, ProductData>();
                productDataMap.put(productId, productData);
                Map<String, ProductType> directPackages = new HashMap<String, ProductType>();
                for (Entry<String, ProductType> entry : productTypes.entrySet()) {
                    List<ProductData> list = entry.getValue().getItems();
                    if (CollectionUtils.isEmpty(list)) {
                        continue;
                    }
                    for (ProductData pd : list) {
                        if (productId.equals(pd.getProductId())) {
                            List<ProductData> newList = new ArrayList<ProductData>();
                            newList.add(productData);
                            entry.getValue().setItems(newList);
                            directPackages.put(entry.getKey(), entry.getValue());
                            if (position != null && position.indexOf(VipTpConstant.USER_TOUCH_POSITION_VIDEO_SALE) > -1) {
                                productData.setAdditionalText(productData.getOriginPrice());
                            }
                            break;
                        }
                    }
                }
                data.setProductDatas(directPackages);
            } else if (StringUtil.isBlank(position)) {
                // non-touch position direct activity package is empty
                data.setCheckOutCounterType(VipConstants.VIP_CHECKOUTCOUNTER_TYPE_COMMON);
                data.setProductDatas(productTypes);
            }
        } else {
            data.setCheckOutCounterType(VipConstants.VIP_CHECKOUTCOUNTER_TYPE_COMMON);
            data.setProductDatas(productTypes);
        }
    }

    /**
     * set vip package info and get year package id
     * @param packageList
     * @param vipPackages
     * @param packageOptionMap
     */
    private String setVipPackageValue(List<PricePackageListTpResponse.PackageRecord> packageList, Map<String, ProductType> vipPackages,
                                      Map<String, ProductData> packageOptionMap, String defaultImg, CommonParam commonParam) {
        String yearPackageId = null;
        if (CollectionUtils.isEmpty(packageList)) {
            return yearPackageId;
        }
        String allTypes = this.getVipConfigInfo(VipConstants.VIP_PRODUCT_TYPES, VipConstants.VIP_PRODUCT_TYPES_DEFAULT);
        List<String> packList = null;
        if (TerminalUtil.isLetvUs(commonParam)) {
            // 美国行货要控制展示哪些套餐
            String packagesConfig = this.getVipConfigInfo(VipConstants.VIP_PRODUCT_PACKAGES_US, "");
            if (StringUtil.isNotBlank(packagesConfig)) {
                packList = new ArrayList<String>(Arrays.asList(packagesConfig.split(",")));
            }
        }

        List<Integer> types = new ArrayList<Integer>();
        if (allTypes != null) {
            String[] ts = allTypes.split(",");
            for (String v : ts) {
                Integer value = StringUtil.toInteger(v);
                if (value != null) {
                    types.add(value);
                }
            }
        }
        for (PricePackageListTpResponse.PackageRecord packageRecord : packageList) {
            List<String> terminals = packageRecord.getTerminals();
            String platform = MmsDataUtil.getPayPlatform(commonParam.getP_devType());
            if (CollectionUtils.isEmpty(terminals) || !terminals.contains(platform)
                    || StringUtil.toDouble(packageRecord.getCurrentPrice(), 0D) <= 0) {
                continue;
            }
            if (!CollectionUtils.isEmpty(packList) && !packList.contains(packageRecord.getMonthType())) {
                continue;
            }
            Integer days = packageRecord.getDays();
            if (days != null && days > 0 && days % 31 == 0) {
                Integer months = days / 31;
                if (months == 12) {
                    // if months equals 12 means it's year package
                    yearPackageId = packageRecord.getMonthType();
                }
                for (int type : types) {
                    if (months % type == 0) {
                        Integer time = months / type;
                        String typeStr = String.valueOf(type);
                        if (vipPackages.get(typeStr) == null) {
                            ProductType productType = new ProductType();
                            vipPackages.put(typeStr, productType);
                            if (productType.getItems() == null) {
                                List<ProductData> items = new LinkedList<ProductData>();
                                ProductData product = new ProductData();
                                this.setProductDataValue(product, packageRecord, time, typeStr, defaultImg, commonParam);
                                packageOptionMap.put(product.getProductId(), product);
                                items.add(product);
                                productType.setItems(items);
                            } else {
                                ProductData product = new ProductData();
                                this.setProductDataValue(product, packageRecord, time, typeStr, defaultImg, commonParam);
                                packageOptionMap.put(product.getProductId(), product);
                                productType.getItems().add(product);
                            }
                        } else {
                            ProductType productType = vipPackages.get(typeStr);
                            if (productType.getItems() == null) {
                                List<ProductData> items = new LinkedList<ProductData>();
                                ProductData product = new ProductData();
                                this.setProductDataValue(product, packageRecord, time, typeStr, defaultImg, commonParam);
                                packageOptionMap.put(product.getProductId(), product);
                                items.add(product);
                                productType.setItems(items);
                            } else {
                                ProductData product = new ProductData();
                                this.setProductDataValue(product, packageRecord, time, typeStr, defaultImg, commonParam);
                                packageOptionMap.put(product.getProductId(), product);
                                productType.getItems().add(product);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return yearPackageId;
    }

    private void setProductDataValue(ProductData productData, PricePackageListTpResponse.PackageRecord packageRecord, Integer time,
                                     String typeStr, String defaultImg, CommonParam commonParam) {
        if (productData == null || packageRecord == null) {
            return;
        }
        productData.setProductId(packageRecord.getMonthType());
        productData.setShowName(time
                + MessageUtils.getMessage(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + typeStr,
                commonParam.getLangcode()));
        productData.setOrder(time);
        productData.setTitle(packageRecord.getName());
        productData.setProductName(packageRecord.getName());
        productData.setImg(defaultImg);
        if (TerminalUtil.isLetvUs(commonParam)) {
            String curPrice = this.getVipConfigInfo(
                    VipConstants.VIP_PACKAGE_CURRENT_PRICE_US + productData.getProductId(), "");
            String oriPrice = this.getVipConfigInfo(
                    VipConstants.VIP_PACKAGE_ORIGIN_PRICE_US + productData.getProductId(), "");
            String desc = this.getVipConfigInfo(VipConstants.VIP_PACKAGE_DESC_US + productData.getProductId(), "");
            productData.setDesc(desc);// letv_us desc read from config file.
            if (StringUtil.isBlank(curPrice) && StringUtil.isBlank(oriPrice)) {
                if (packageRecord.getCurrentPrice() != null) {
                    productData.setCurrentPrice(packageRecord.getCurrentPrice());
                } else {
                    productData.setCurrentPrice(packageRecord.getOriginPrice());
                }
                productData.setOriginPrice(packageRecord.getOriginPrice());
            } else {
                if (StringUtil.isNotBlank(curPrice)) {
                    productData.setCurrentPrice(curPrice);
                } else {
                    productData.setCurrentPrice(oriPrice);
                }
                productData.setOriginPrice(oriPrice);
            }
        } else {
            productData.setDesc(packageRecord.getVipDesc());
            if (packageRecord.getCurrentPrice() != null) {
                productData.setCurrentPrice(packageRecord.getCurrentPrice());
            } else {
                productData.setCurrentPrice(packageRecord.getOriginPrice());
            }
            productData.setOriginPrice(packageRecord.getOriginPrice());
        }
        if (packageRecord.getDays() != null) {
            productData.setDuration(String.valueOf(VipUtil.parseDayToMonth(packageRecord.getDays().intValue(), 31)));
        }
        productData.setDiscount(VipUtil.getDiscount(packageRecord.getCurrentPrice(), packageRecord.getOriginPrice()));
    }

    /**
     * get activity by activity ids
     * @param activityIds
     *            activity ids
     * @param position
     *            the position of checkout counter entry
     * @param commonParam
     * @return
     */
    private Map<String, PaymentActivityDto> getPaymentActivityById(String activityIds, String position,
                                                                   CommonParam commonParam) {
        Map<String, PaymentActivityDto> paymentActivityMap = null;
        GetActivityDataResponse activityResponse = this.facadeTpDao.getVipTpDao().getActivityData(activityIds,
                commonParam);
        if (activityResponse == null || activityResponse.getCode() == null || activityResponse.getCode() != 0
                || CollectionUtils.isEmpty(activityResponse.getData())) {
            LOG.info("getCheckOutCounterNew_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + activityIds + "_" + position + " get payment activity [" + activityIds + "] failed.");
        } else {
            paymentActivityMap = this.parsePaymentActivityFromTpNew(activityResponse.getData(),
                    commonParam.getLangcode());
        }
        return paymentActivityMap;
    }

    /**
     * set checkout counter show text and sort vip package type
     * @param data
     *            checkout counter data
     * @param commonParam
     */
    private void setCheckoutCounterShowText(CheckOutCounter data, CommonParam commonParam) {
        boolean isDirect = VipConstants.VIP_CHECKOUTCOUNTER_TYPE_DIRECT.equals(data.getCheckOutCounterType());
        List<CheckOutCounter.TypeInfo> typeInfos = new LinkedList<CheckOutCounter.TypeInfo>();
        for (Entry<String, ProductType> entry : data.getProductDatas().entrySet()) {
            ProductType productType = entry.getValue();
            if (productType == null || CollectionUtils.isEmpty(productType.getItems())) {
                continue;
            }
            CheckOutCounter.TypeInfo typeInfo = new CheckOutCounter.TypeInfo();
            typeInfo.setProductType(entry.getKey());
            typeInfo.setProductTypeName(MessageUtils.getMessage(
                    VipConstants.PRODUCT_TYPE_TEXT_PREFIX_KEY + entry.getKey(), commonParam.getLangcode()));
            typeInfos.add(typeInfo);
            Collections.sort(productType.getItems());
            if (isDirect) {
                // direct checkout counter no need to calculate
                // the average price
                continue;
            }
            for (ProductData productData : productType.getItems()) {
                Float price = StringUtil.toFloat(productData.getCurrentPrice(), 0F);
                Float duration = StringUtil.toFloat(productData.getDuration());
                if (price != null && price > 0 && duration != null && duration > 0) {
                    DecimalFormat format = new DecimalFormat("#0.0");
                    String msgCode = null;
                    if (duration > 12) {
                        // If duration more than 12 month,show
                        // the price of per year.
                        duration = duration / 12;
                        msgCode = VipConstants.VIP_PACKAGE_AVERAGE_PERYEAR_TEXT;
                    } else if (duration > 1) {
                        // else if duration more than 1
                        // month,show the price of per month.
                        msgCode = VipConstants.VIP_PACKAGE_AVERAGE_PERMONTH_TEXT;
                    }
                    if (msgCode != null) {
                        String average = format.format(price / duration);
                        if (average != null && average.indexOf(".") > 0) {
                            Integer i = StringUtil.toInteger(average.substring(0, average.indexOf(".")), 0);
                            Float f = StringUtil.toFloat(average, 0F);
                            if (i >= f) {
                                average = i + "";
                            }
                        }
                        String[] codeMap = { average };
                        String msg = MessageUtils.getMessage(msgCode, commonParam.getLangcode(), codeMap);
                        productData.setAdditionalText(msg);
                    }
                }
            }
        }
        String sorts = this.getVipConfigInfo(VipConstants.VIP_PRODUCT_TYPE_SORT_ORDER,
                VipConstants.VIP_PRODUCT_TYPE_SORT_ORDER_DEFAULT);
        if (sorts != null) {
            String[] types = sorts.split(",");
            List<CheckOutCounter.TypeInfo> sortList = new LinkedList<CheckOutCounter.TypeInfo>();
            for (String type : types) {
                for (CheckOutCounter.TypeInfo typeInfo : typeInfos) {
                    if (type != null && type.equals(typeInfo.getProductType())) {
                        sortList.add(typeInfo);
                    }
                }
            }
            data.setProductTypes(sortList);
        } else {
            data.setProductTypes(typeInfos);
        }

    }

    private Integer getDataTypeByOpenType(Integer openType) {
        if (openType == null) {
            return DataConstant.DATA_TYPE_BLANK;
        }
        Integer dataType = DataConstant.DATA_TYPE_BLANK;
        switch (openType) {
            case DataConstant.OPENTYPE_CHECKOUTER:
                dataType = DataConstant.DATA_TYPE_CHECKSTAND;
                break;
            case DataConstant.OPENTYPE_H5_AND_CHECKOUT:
                dataType = DataConstant.DATA_TYPE_BROWSER;
                break;
            case DataConstant.OPENTYPE_H5:
                dataType = DataConstant.DATA_TYPE_BROWSER;
                break;
            case DataConstant.OPENTYPE_PRELIVE:
                dataType = DataConstant.DATA_TYPE_PRELIVE;
                break;
            case DataConstant.OPENTYPE_919_ACTIVITY:
                dataType = DataConstant.DATA_TYPE_TOPIC_ACTIVITY;
                break;
            case DataConstant.OPENTYPE_VIDEO_SALE:
                dataType = DataConstant.DATA_TYPE_VIDEO_SALE;
                break;
            default:
                break;
        }
        return dataType;
    }

    /**
     * 根据dataType构建一个jump对象
     * @param openType
     * @return
     */
    private void setPilotDtoData(PilotDto data, Integer dataType, Integer openType, String position) {
        if (dataType == null) {
            dataType = DataConstant.DATA_TYPE_BLANK;
        }
        JumpData jump = new JumpData();
        data.setJump(jump);
        data.setPosition(position);
        switch (dataType) {
            case DataConstant.DATA_TYPE_ALBUM:
                AlbumDto albumDto = new AlbumDto();
                albumDto.setAlbumId(data.getId());
                albumDto.setDataType(dataType);
                jump.setValue(albumDto);
                break;
            case DataConstant.DATA_TYPE_VIDEO:
                VideoDto videoDto = new VideoDto();
                videoDto.setVideoId(data.getId());
                videoDto.setDataType(dataType);
                jump.setValue(videoDto);
                break;
            case DataConstant.DATA_TYPE_SUBJECT:
                Subject subject = new Subject();
                subject.setSubjectId(data.getId());
                subject.setSubjectType(data.getSubjectType());
                subject.setDataType(dataType);
                jump.setValue(subject);
                break;
            case DataConstant.DATA_TYPE_LIVE:
                Live live = new Live();
                live.setLiveId(data.getId());
                live.setDataType(dataType);
                jump.setValue(live);
                break;
            case DataConstant.DATA_TYPE_CHANNEL:
                Channel channel = new Channel();
                channel.setChannelId(StringUtil.toInteger(data.getId()));
                channel.setDataType(dataType);
                jump.setValue(channel);
                break;
            case DataConstant.DATA_TYPE_CHECKSTAND:
                PilotDto pilotDto_skip = new PilotDto();
                pilotDto_skip.setId(data.getId());
                pilotDto_skip.setOrderType(data.getOrderType());
                pilotDto_skip.setActivityIds(data.getActivityIds());
                pilotDto_skip.setDataType(dataType);
                pilotDto_skip.setPosition(position);
                if (StringUtil.isNotBlank(data.getCPS_no())) {
                    pilotDto_skip.setCPS_no(data.getCPS_no());
                }
                data.setCPS_no(null);
                if (StringUtil.isNotBlank(data.getCode_no())) {
                    pilotDto_skip.setCode_no(data.getCode_no());
                }
                data.setCode_no(null);
                pilotDto_skip.setVendor(ApplicationUtils.get(ApplicationConstants.VIP_OPENECO_VENDER));
                jump.setValue(pilotDto_skip);
                break;
            case DataConstant.DATA_TYPE_BROWSER:
                Browser browser_skip = new Browser();
                jump.setType(dataType);
                browser_skip.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                if (data.getInteractive() != null && data.getInteractive() == 1) {
                    // 优先判断是不是交互h5
                    browser_skip.setOpenType(DataConstant.BROWSER_OPENTYPE_INTERACTIVE);
                } else {
                    if (openType != null && openType == DataConstant.OPENTYPE_H5_AND_CHECKOUT) {
                        browser_skip.setOpenType(DataConstant.BROWSER_OPENTYPE_CHECKOUT);
                    } else {
                        browser_skip.setOpenType(DataConstant.BROWSER_OPENTYPE_NONE);
                    }
                }
                browser_skip.setId(data.getId());
                // browser_skip.setUrl(data.getCashierUrl());
                Map<String, String> urlMap = new HashMap<String, String>();
                urlMap.put("0", data.getCashierUrl()); // 默认
                browser_skip.setUrlMap(urlMap);
                browser_skip.setDataType(dataType);
                browser_skip.setPosition(position);
                browser_skip.setOrderType(data.getOrderType());
                browser_skip.setActivityIds(data.getActivityIds());
                if (StringUtil.isNotBlank(data.getCPS_no())) {
                    browser_skip.setCPS_no(data.getCPS_no());
                }
                data.setCPS_no(null);
                if (StringUtil.isNotBlank(data.getCode_no())) {
                    browser_skip.setCode_no(data.getCode_no());
                }
                data.setCode_no(null);
                browser_skip.setVendor(ApplicationUtils.get(ApplicationConstants.VIP_OPENECO_VENDER));
                jump.setValue(browser_skip);
                break;
            case DataConstant.DATA_TYPE_PRELIVE:
                SubjectPreLive preSubject_skip = new SubjectPreLive();
                jump.setType(dataType);
                Map<String, String> map = data.getParamMap();
                if (map != null) {// 自定义参数
                    preSubject_skip.setCinemaId(map.get("cinemaId"));
                }
                preSubject_skip.setDataType(dataType);
                jump.setValue(preSubject_skip);
                break;
            case DataConstant.DATA_TYPE_TOPIC_ACTIVITY:
                dataType = DataConstant.DATA_TYPE_TOPIC_ACTIVITY;
                BaseBlock block_skip = new BaseBlock();
                jump.setType(dataType);
                Map<String, String> map2 = data.getParamMap();
                if (map2 != null) {// 自定义参数
                    block_skip.setBlockId(map2.get("blockId"));
                }
                block_skip.setDataType(dataType);
                jump.setValue(block_skip);
                break;
            case DataConstant.DATA_TYPE_VIDEO_SALE:
                dataType = DataConstant.DATA_TYPE_VIDEO_SALE;
                BaseBlock block_skip2 = new BaseBlock();
                jump.setType(dataType);
                Map<String, String> map3 = data.getParamMap();
                if (map3 != null) {// 自定义参数
                    block_skip2.setBlockId(map3.get("blockId"));
                }
                block_skip2.setDataType(dataType);
                block_skip2.setId(data.getId());
                jump.setValue(block_skip2);
                break;
            case DataConstant.DATA_TYPE_FREE_VIP_ACTIVE:// 开通会员白条
                VideoPlayConsumeGuideInfoDto freevip_skip = new VideoPlayConsumeGuideInfoDto();
                freevip_skip.setActivityIds(data.getActivityIds());
                freevip_skip.setId(StringUtil.toInteger(data.getId()));
                freevip_skip.setPosition(position);
                freevip_skip.setDataType(dataType);
                jump.setValue(freevip_skip);
                jump.setType(dataType);
                break;
            case DataConstant.DATA_TYPE_FREE_VIP_PAYBACK:// 会员白条还款
                PilotDto payback_skip = new PilotDto();
                payback_skip.setDataType(dataType);
                payback_skip.setPosition(position);
                payback_skip.setId(data.getId());
                jump.setValue(payback_skip);
                jump.setType(dataType);
            case DataConstant.DATA_TYPE_CONTAINER:// 芈月传容器
                PilotDto contain_skip = new PilotDto();
                contain_skip.setContainerId(data.getContainerId());
                contain_skip.setDataType(dataType);
                jump.setType(dataType);
                jump.setValue(contain_skip);
                break;
            default:
                break;
        }
        jump.setType(dataType);
        data.setDataType(dataType);
    }

    /**
     * 根据会员运营中心的跳转类型解析数据
     * @param data
     * @param param
     *            跳转参数
     * @param dataType
     *            跳转类型
     */
    private void parseParam(PilotDto data, GetVipCenterActivityResponse.VipCenterResultData.JumpDataParam param, Integer dataType, String position) {
        if (data == null || param == null || dataType == null) {
            return;
        }
        data.setContentImage(param.getImg());
        data.setTitle(param.getTitle());
        data.setButtonContent(param.getClickContent());
        data.setImg(param.getImg());
        if (VipTpConstant.USER_TOUCH_POSITION_BOTTOM.equals(position)) {
            data.setPrivilegeTips(param.getBrief());
        } else {
            if (VipTpConstant.USER_TOUCH_POSITION_POPUP.equals(position)) {
                data.setExpireTips(StringUtils.trimToNull(param.getTitle()));// 全屏弹窗主标题字段
            }
            data.setPrivilegeTips(param.getSubtitle());
        }
        switch (dataType) {
            case DataConstant.DATA_TYPE_ALBUM:
                break;
            case DataConstant.DATA_TYPE_VIDEO:
                break;
            case DataConstant.DATA_TYPE_SUBJECT:
                data.setSubjectType(param.getSubjectType());
                break;
            case DataConstant.DATA_TYPE_LIVE:
                break;
            case DataConstant.DATA_TYPE_CHANNEL:
                break;
            case DataConstant.DATA_TYPE_BROWSER:
                data.setCashierUrl(param.getUrl());
                if (param.getOpenType() != null && param.getOpenType() == 1) {// 此openType是客户端跳转h5后判断是否需要继续跳收银台
                    data.setOpenType(DataConstant.OPENTYPE_H5_AND_CHECKOUT);// 此openType是客户端用于判断属于哪种类型使用，兼容老版本(老版本只有openType，没有dataType)
                } else {
                    data.setOpenType(DataConstant.OPENTYPE_H5);
                }
                break;
            case DataConstant.DATA_TYPE_CHECKSTAND:
                data.setOpenType(DataConstant.OPENTYPE_CHECKOUTER);
                break;
            case DataConstant.DATA_TYPE_PRELIVE:
                data.setSubjectType(param.getSubjectType());
                data.setOpenType(DataConstant.OPENTYPE_PRELIVE);
                break;
            case DataConstant.DATA_TYPE_TOPIC_ACTIVITY:
                Map<String, String> ac = new HashMap<String, String>();
                ac.put("blockId", param.getBlockId());
                data.setParamMap(ac);
                data.setOpenType(DataConstant.OPENTYPE_919_ACTIVITY);
                break;
            case DataConstant.DATA_TYPE_VIDEO_SALE:
                Map<String, String> vs = new HashMap<String, String>();
                vs.put("blockId", param.getBlockId());
                data.setParamMap(vs);
                data.setOpenType(DataConstant.OPENTYPE_VIDEO_SALE);
                break;
            case DataConstant.DATA_TYPE_DOWN_NAVIGATION:
                break;
            default:
                break;
        }
    }

    private void mergePackageAndActivityValue(Map<String, ProductType> productTypes,
                                              Map<String, PaymentActivityDto> paymentActivityMap, Map<String, ProductData> productDataMap, String langcode) {
        if (CollectionUtils.isEmpty(productTypes) || CollectionUtils.isEmpty(productDataMap)
                || CollectionUtils.isEmpty(paymentActivityMap)) {
            return;
        }
        for (Entry<String, PaymentActivityDto> entry : paymentActivityMap.entrySet()) {
            String activityId = entry.getKey();
            PaymentActivityDto paymentActivityDto = entry.getValue();
            Integer type = paymentActivityDto.getType();
            if (paymentActivityDto.getMonthType() == null || type == null || type != 1) {
                continue;
            }
            ProductData productData = productDataMap.get(String.valueOf(paymentActivityDto.getMonthType()));
            if (productData == null) {
                continue;
            }
            productData.setTitle(paymentActivityDto.getBodyText());
            productData.setSubTitle(paymentActivityDto.getLableText());
            productData.setDesc(paymentActivityDto.getPackageText());
            String img = paymentActivityDto.getImg();
            if (StringUtil.isNotBlank(img)) {
                productData.setImg(img);
            }
            String url = paymentActivityDto.getUrl();
            if (StringUtil.isNotBlank(url)) {
                productData.setUrl(url);
                Browser browser = new Browser();
                browser.setDataType(DataConstant.DATA_TYPE_BROWSER);
                browser.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                browser.setOpenType(DataConstant.BROWSER_OPENTYPE_NONE);
                browser.setUrl(url);
                Map<String, String> urlMap = new HashMap<String, String>();
                urlMap.put("0", url);
                browser.setUrlMap(urlMap);
                JumpUtil.bulidJumpObj(browser, DataConstant.DATA_TYPE_BROWSER, null, null);
                productData.setDetail(browser);
            } else {
                productData.setUrl(null);
                productData.setDetail(null);
            }
            Float packagePrice = StringUtil.toFloat(productData.getCurrentPrice(), 0F);
            Float activityDiscount = StringUtil.toFloat(paymentActivityDto.getDiscount(), 0F);
            if (packagePrice >= activityDiscount) {
                productData.setAdditionalText(productData.getCurrentPrice());
                productData.setActivityIds(activityId);
                DecimalFormat format = new DecimalFormat("#0.00");
                String price = format.format(packagePrice - activityDiscount);
                productData.setCurrentPrice(price);
            } else {
                productData.setAdditionalText(productData.getCurrentPrice());
                productData.setActivityIds(activityId);
                productData.setCurrentPrice("0.0");
            }
            if (paymentActivityDto.getCoupon() != null && paymentActivityDto.getCoupon() == 13) {
                productData.setSupportVoucher(1);
            } else {
                productData.setSupportVoucher(0);
                productData.setUnsupportVoucherText(MessageUtils.getMessage(
                        VipConstants.VIP_ACTIVITY_NOT_SUPPORT_VOUCHER, langcode));
            }
        }
    }

    public String getVipConfigInfo(String key, String defaultValue) {
        this.updateVipConfigInfoMap();
        if (VipConstants.vipConfigInfoMap == null) {
            return defaultValue;
        }
        String value = VipConstants.vipConfigInfoMap.get(key);
        return StringUtil.isBlank(value) ? defaultValue : value;
    }

    /**
     * update the vip config info map
     */
    private void updateVipConfigInfoMap() {
        long curTime = System.currentTimeMillis();
        if (VipConstants.LAST_UPDATE_VIP_CONFIG_INFO_TIME + VipConstants.UPDATE_VIP_CONFIG_INFO_INTERVAL_TIME < curTime) {
            synchronized (VipConstants.vipConfigInfoMap) {
                if (VipConstants.LAST_UPDATE_VIP_CONFIG_INFO_TIME + VipConstants.UPDATE_VIP_CONFIG_INFO_INTERVAL_TIME < curTime) {
                    VipConstants.LAST_UPDATE_VIP_CONFIG_INFO_TIME = curTime;
                    Map<String, String> map = this.facadeTpDao.getStaticTpDao().getStaticFileContent(
                            ApplicationUtils.get(ApplicationConstants.VIP_PROFILE_URL));// 静态文件内容
                    if (!CollectionUtils.isEmpty(map)) {
                        VipConstants.vipConfigInfoMap = map;
                    }
                }
            }
        }
    }

    private PilotDto parseUrmActivityInfo(GetUrmActivityResponse.UrmActivityData urmActivityData, String position, CommonParam commonParam) {
        if (urmActivityData == null) {
            return null;
        }
        Map<String, String> showInfo = urmActivityData.getShow_info();
        GetUrmActivityResponse.UrmActivityData.UrmActivityInfo urmActivityInfo = urmActivityData.getTv_info();
        Integer dataType = urmActivityInfo.getJump_type();
        if ((showInfo == null) || (urmActivityInfo == null) || (dataType == null)) {
            return null;
        }
        String deliverAddress = urmActivityInfo.getDelivery_address();
        if (StringUtil.isNotBlank(deliverAddress)) {
            this.facadeCacheDao.getVipCacheDao().setDeliverAddress(commonParam.getUserId(),
                    VipTpConstant.getUserTouchPositionV2(position), deliverAddress);
        } else {
            this.facadeCacheDao.getVipCacheDao().deleteDeliverAddress(commonParam.getUserId(),
                    VipTpConstant.getUserTouchPositionV2(position));
        }
        PilotDto data = new PilotDto();
        data.setDataType(dataType);
        // set activity show info
        setUrmActivityShowInfo(data, position, showInfo);
        GetUrmActivityResponse.UrmActivityData.UrmActivityInfo.ConfigInfo jumpInfo = urmActivityInfo.getJump_info();
        // if true then need parse boss activity data
        boolean jumpCheckoutCounter = false;
        if (dataType == 21) {
            jumpCheckoutCounter = true;
            data.setOpenType(DataConstant.OPENTYPE_CHECKOUTER);
            if (null != jumpInfo) {
                if (StringUtil.isNotBlank(jumpInfo.getCPS_no())) {
                    data.setCPS_no(jumpInfo.getCPS_no());
                }
                if (StringUtil.isNotBlank(jumpInfo.getCode_no())) {
                    data.setCode_no(jumpInfo.getCode_no());
                }
            }
        } else if (dataType == 9) {
            if (jumpInfo != null) {
                data.setCashierUrl(jumpInfo.getUrl());
                Integer interactive = jumpInfo.getInteractive();
                if (interactive != null && interactive == 1) {
                    data.setInteractive(1);
                } else {
                    if ((jumpInfo != null) && (jumpInfo.getOpenType() != null) && (jumpInfo.getOpenType() == 1)) {
                        jumpCheckoutCounter = true;
                        data.setOpenType(DataConstant.OPENTYPE_H5_AND_CHECKOUT);
                    } else {
                        data.setOpenType(DataConstant.OPENTYPE_H5);
                    }
                }
                if (StringUtil.isNotBlank(jumpInfo.getCPS_no())) {
                    data.setCPS_no(jumpInfo.getCPS_no());
                }
                if (StringUtil.isNotBlank(jumpInfo.getCode_no())) {
                    data.setCode_no(jumpInfo.getCode_no());
                }
            }
        } else if (dataType == 29) {
            data.setOpenType(DataConstant.OPENTYPE_VIDEO_SALE);
            if (jumpInfo != null) {
                Map<String, String> vs = new HashMap<String, String>();
                vs.put("blockId", jumpInfo.getBlockId());
                data.setParamMap(vs);
            }
        } else if (dataType == 55) {// 开通会员白条
            data.setDataType(DataConstant.DATA_TYPE_FREE_VIP_ACTIVE);
            if (jumpInfo != null) {
                data.setActivityIds(jumpInfo.getFreeActivityId());
            }
        } else if (dataType == 100) {// 会员白条还款
            data.setDataType(DataConstant.DATA_TYPE_FREE_VIP_PAYBACK);
            // show payment info for free vip
            if (jumpInfo != null) {// set data to cache
                ActiveFreeVipDto activeFreeVipDto = parsePaymentInfo(jumpInfo.getPric(), jumpInfo.getDunning_button(),
                        jumpInfo.getDunning_image(), jumpInfo.getStart_time(), jumpInfo.getFree_end_time(),
                        jumpInfo.getRequired_pay_time(), jumpInfo.getEndTime(), jumpInfo.getPay_monthtype(),
                        String.valueOf(jumpInfo.getProductid()), jumpInfo.getDunning_desc(), commonParam.getLangcode());
                if (activeFreeVipDto != null) {
                    this.facadeCacheDao.getVipCacheDao().setPaymentInfo(commonParam, activeFreeVipDto);
                }
            }
        } else if (dataType == 28) {// 跳芈月传容器
            data.setDataType(DataConstant.DATA_TYPE_CONTAINER);
            if (jumpInfo != null) {
                data.setContainerId(jumpInfo.getContentId());
            }
        }
        if (jumpCheckoutCounter && (jumpInfo != null)) {
            List<BossActivityData> dataList = new LinkedList<BossActivityData>();
            dataList.add(jumpInfo.getActivity_package());
            Map<String, PaymentActivityDto> paymentActivityMap = this.parsePaymentActivityFromTpNew(dataList,
                    commonParam.getLangcode());
            if (!CollectionUtils.isEmpty(paymentActivityMap)) {
                for (Entry<String, PaymentActivityDto> entry : paymentActivityMap.entrySet()) {
                    PaymentActivityDto pa = entry.getValue();
                    data.setOrderType(pa.getMonthType());// 套餐id
                    data.setActivityIds(pa.getActivityId());
                    break;
                }
            }
        }
        return data;
    }

    /**
     * set urm activity show info
     * @param data
     * @param position
     * @param showInfo
     */
    private void setUrmActivityShowInfo(PilotDto data, String position, Map<String, String> showInfo) {
        if (VipTpConstant.URM_POSITION_TOP.equals(position)) {
            data.setTitle(showInfo.get("tv_" + position + "_text1"));// title
            // button value
            data.setButtonContent(showInfo.get("tv_" + position + "_text2"));
        } else if (VipTpConstant.URM_POSITION_BOTTOM.equals(position)) {
            data.setTitle(showInfo.get("tv_" + position + "_text1"));
            data.setPrivilegeTips(showInfo.get("tv_" + position + "_text2"));
            data.setButtonContent(showInfo.get("tv_" + position + "_text3"));
            data.setImg(showInfo.get("tv_" + position + "_image1"));
            data.setContentImage(showInfo.get("tv_" + position + "_image1"));
        } else if (VipTpConstant.URM_POSITION_POPUP.equals(position)) {
            String title = showInfo.get("tv_" + position + "_text1");
            data.setTitle(title);
            data.setExpireTips(StringUtils.trimToNull(title));
            data.setPrivilegeTips(showInfo.get("tv_" + position + "_text2"));
            data.setButtonContent(showInfo.get("tv_" + position + "_text3"));
            data.setImg(showInfo.get("tv_" + position + "_image1"));
            data.setContentImage(showInfo.get("tv_" + position + "_image1"));
        } else if (VipTpConstant.URM_POSITION_MINE_1.equals(position)) {
            data.setImg(showInfo.get("tv_" + position + "_image1"));
        } else if (VipTpConstant.URM_POSITION_MINE_2.equals(position)) {
            data.setImg(showInfo.get("tv_" + position + "_image1"));
        } else if (VipTpConstant.URM_POSITION_MINE_3.equals(position)) {
            data.setImg(showInfo.get("tv_" + position + "_image1"));
        } else if (VipTpConstant.URM_POSITION_MINE_4.equals(position)) {
            data.setImg(showInfo.get("tv_" + position + "_image1"));
        } else {// 播放引导页
            data.setTitle(showInfo.get("tv_" + position + "_text1"));
            data.setPrivilegeTips(showInfo.get("tv_" + position + "_text2"));
            data.setButtonContent(showInfo.get("tv_" + position + "_text3"));
            data.setImg(showInfo.get("tv_" + position + "_image1"));
        }
    }

    /**
     * 解析还款信息
     * @param price
     *            还款价格，其实是定向收银台里的套餐价格
     * @param buttonText
     *            还款文案
     * @param image
     *            还款图片
     * @param startTime
     *            会员白条开始时间
     * @param endTime
     *            会员白条结束时间
     * @param requirePayTime
     *            需要还款的最后时间
     * @param productId
     *            套餐id
     * @param activityId
     *            活动id
     * @param paymentDesc
     *            还款描述
     * @param langcode
     *            语言
     * @return
     */
    private ActiveFreeVipDto parsePaymentInfo(String price, String buttonText, String image, Long startTime,
                                              Long endTime, Long requirePayTime, Integer requirePayDays, Integer productId, String activityId,
                                              String paymentDesc, String langcode) {
        ActiveFreeVipDto data = new ActiveFreeVipDto();
        data.setTitle(MessageUtils.getMessage(VipConstants.VIP_FREEVIP_PAYBACK_TITLE, langcode) + " ");
        data.setUnit(" " + MessageUtils.getMessage(VipConstants.VIP_FREEVIP_PAYBACK_TITLE_UNIT, langcode));
        data.setPrice(price);
        data.setButtonText1(buttonText + " ￥" + price);
        data.setImg(image);
        data.setDelayPayBackDesc1(paymentDesc);
        data.setDelayPayBackNotice(MessageUtils.getMessage(VipConstants.VIP_FREEVIP_PAYBACK_STATEMENT, langcode));
        if (startTime != null) {
            data.setStartDate(TimeUtil.getDateStringFromLong(startTime * 1000, TimeUtil.SHORT_DATE_FORMAT));
        }
        if (endTime != null) {
            data.setEndDate(TimeUtil.getDateStringFromLong(endTime * 1000, TimeUtil.SHORT_DATE_FORMAT));
        }
        if (requirePayDays != null) {
            data.setDelayPayBackDays(requirePayDays);
        } else if (requirePayTime != null) {// 计算还剩多少天到最后还款日
            long currentTime = System.currentTimeMillis() / 1000;
            long sub = requirePayTime - currentTime;
            int days = (int) (sub / 86400);
            if ((sub % 86400 == 0) && (days > 0)) {
                days = days - 1;
            }
            data.setDelayPayBackDays(days);
        }
        JumpData jump = new JumpData();
        jump.setType(DataConstant.DATA_TYPE_CHECKSTAND);
        PilotDto value = new PilotDto();
        value.setDataType(DataConstant.DATA_TYPE_CHECKSTAND);
        value.setActivityIds(activityId);
        value.setOrderType(productId);
        value.setPosition("payment");
        jump.setValue(value);
        data.setJump(jump);
        return data;
    }

    /**
     * 直播鉴权接口，重新请求流地址并拼接鉴权信息
     * @param liveUrl
     * @param token
     * @param uinfo
     * @param commonParam
     * @return
     */
    protected String getLiveUrl(String liveUrl, String token, String uinfo, CommonParam commonParam) {
        StringBuilder sb = new StringBuilder();
        sb.append(liveUrl);
        if (StringUtil.isNotBlank(token)) {
            sb.append("&token=").append(token);
        } else {
            sb.append("&token=");
        }
        if (StringUtil.isNotBlank(commonParam.getUserId())) {
            sb.append("&uid=").append(commonParam.getUserId());
        } else {
            sb.append("&uid=").append(commonParam.getMac());
        }
        if (StringUtil.isNotBlank(uinfo)) {
            sb.append("&uinfo=").append(uinfo);
        } else {
            sb.append("&uinfo=");
        }
        return sb.toString();
    }

    public Response<VipValidateDto> validate(Integer productId, Integer packageId, Integer terminal,
                                             CommonParam commonParam) {
        String errorCode = null;
        VipValidateDto vipValidateDto = new VipValidateDto();
        boolean isLogin = true;
        if (StringUtils.isEmpty(commonParam.getUserToken()) || StringUtils.isEmpty(commonParam.getUserId())) { // 获取用户token
            UserStatus user = userService.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                isLogin = false;
            } else {
                commonParam.setUserToken(user.getUserToken());
                commonParam.setUserId(user.getUserId() + "");
            }
        }

        if (productId == null) {
            errorCode = ErrorCodeConstants.VIP_PAY_PRODUCTID_NULL;
        } else {
            BossTpResponse<Vip> vipTpResponse = this.facadeTpDao.getVipTpDao().getProductById(productId, commonParam);
            Vip vip = null; // 会员信息
            if (vipTpResponse != null && vipTpResponse.isSucceed()) {
                vip = vipTpResponse.getData();
            } else {
                errorCode = ErrorCodeConstants.VIP_GET_PRODUCT_INFO_FAIL;
            }
            if (errorCode == null) {
                vipValidateDto.setProductId(productId);
                // vipValidateDto.setSubscribed(VipValidateDto.UN_SUBSCRIBED);
                // // 默认未订阅
                vipValidateDto.setTryStatus(VipValidateDto.TRY_NOT_CAN); // 默认不可试用
                vipValidateDto.setVipStatus(VipValidateDto.VIP_NOT); // 默认非会员
                boolean used = false; // 是否试用或订阅过此会员, 默认未试用或订阅过此会员
                if (isLogin) {
                    Map<Integer, SubscribeInfo> vipInfoMap = this.getVipInfoV2(terminal, false, commonParam); // 获取用户订阅过的会员信息列表
                    if (vipInfoMap != null && vipInfoMap.get(productId) != null) {
                        used = true; // 已经使用过
                        SubscribeInfo subscribeInfo = vipInfoMap.get(productId);
                        vipValidateDto.setExpireTime(subscribeInfo.getEndTime()); // 设置会员有效期
                        vipValidateDto.setTryEndTime(subscribeInfo.getTryEndTime()); // 设置试用结束时间
                        if (subscribeInfo.getEndTime() != null) { // 会员时长不为空,设置会员状态
                            long nowStamp = System.currentTimeMillis();
                            if (nowStamp <= subscribeInfo.getEndTime()) {
                                vipValidateDto.setVipStatus(vipValidateDto.VIP_CAN_USE); // 会员可用
                            } else if (nowStamp > subscribeInfo.getEndTime()) {
                                vipValidateDto.setVipStatus(VipValidateDto.VIP_EXPIRE); // 会员过期
                            }
                        }
                        if (SubscribeInfo.IS_SUBSCRIBED.equals(subscribeInfo.getIsSubscribe())) { // 设置订阅状态
                            vipValidateDto.setSubscribed(VipValidateDto.IS_SUBSCRIBED);// 已订阅
                        } else if (SubscribeInfo.UN_SUBSCRIBED.equals(subscribeInfo.getIsSubscribe())) {
                            vipValidateDto.setSubscribed(VipValidateDto.UN_SUBSCRIBED); // 未订阅
                        }
                        if (subscribeInfo.isTryUsing()) { // 正在试用中
                            vipValidateDto.setTryStatus(VipValidateDto.TRY_USING);
                        }
                    }
                }
                if (!used || !isLogin) { // 未使用过,或未登录
                    if (vip != null && vip.getTrialData() != null && vip.getTrialData().getTrialDuration() != null
                            && vip.getTrialData().getTrialDuration() > 0) { // 有试用信息且试用时长大于0,则可以试用
                        // vipValidateDto.setSubscribed(VipValidateDto.TRY_USE);
                        // // 设置状态为可试用
                        vipValidateDto.setTryStatus(VipValidateDto.TRY_CAN); // 设置状态为可试用
                    }
                }
                if (vip != null) { // 设置会员信息
                    vipValidateDto.setName(vip.getName()); // 名称
                    vipValidateDto.setImg(vip.getPic()); // 图片
                    vipValidateDto.setDesc(vip.getExt()); // 描述
                    if (vip.getTrialData() != null) { // 设置试用信息
                        VipValidateDto.TrialData trialData = new VipValidateDto.TrialData();
                        trialData.setTrialDuration(vip.getTrialData().getTrialDuration());
                        trialData.setTrialField(this.getTrialField(vip.getTrialData().getTrialField(), vip
                                .getTrialData().getTrialDuration(), commonParam));
                        trialData.setTrialDurationName(vip.getTrialData().getTrialDurationName());
                        vipValidateDto.setTrialData(trialData);
                    }
                }
                String terminalType = VipPackage.TERMINAL_TV;// 默认tv终端
                List<PackageInfoDto> packageInfoDtoList = this.getPackageByProductId(productId, terminalType,
                        commonParam);
                if (packageId != null && packageId > 0) {
                    for (PackageInfoDto packageInfoDto : packageInfoDtoList) {
                        if (packageId.equals(packageInfoDto.getId())) { // 将packageId对应的套餐移到第一个位置
                            packageInfoDtoList.remove(packageInfoDto);
                            packageInfoDtoList.add(0, packageInfoDto);
                            break;
                        }
                    }
                }
                vipValidateDto.setPackageInfo(packageInfoDtoList);

                // 儿童锁
                UserChildLockTable childLockTable = userService.getUserChildLockFromCache(
                        commonParam);
                ChildLockDto childLockDto = userService.parseUserChildLockTable(
                        UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS, childLockTable);
                vipValidateDto.setChildLock(childLockDto);
            }
        }

        Response<VipValidateDto> response = new Response<VipValidateDto>();
        if (errorCode == null) {
            response.setData(vipValidateDto);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }
        return response;
    }

    /**
     * 获取套餐信息
     * @param productId
     *            会员id
     * @param terminal
     *            终端，用这个值过滤套餐，如tv传的值为{@link VipPackage.TERMINAL_TV}
     * @param commonParam
     * @return
     */
    public List<PackageInfoDto> getPackageByProductId(Integer productId, String terminal, CommonParam commonParam) {
        if (productId == null) {
            return null;
        }
        List<PackageInfoDto> packageInfoDtoList = new LinkedList<PackageInfoDto>();
        // 设置套餐价格
        BossTpResponse<List<VipPackage>> vipPackageListTpResponse = this.facadeTpDao.getVipTpDao()
                .getPackageByProductId(productId, commonParam);
        if (vipPackageListTpResponse != null && vipPackageListTpResponse.isSucceed()) {
            List<VipPackage> vipPackageList = vipPackageListTpResponse.getData();
            for (VipPackage vipPackage : vipPackageList) {
                if (StringUtils.isNotBlank(terminal)) {
                    if (vipPackage.getTerminal() == null || !vipPackage.getTerminal().contains(terminal)) { // 不包含可用终端，则过滤掉
                        continue;
                    }
                }
                PackageInfoDto packageInfoDto = parseVipPackage(vipPackage, commonParam);
                if (packageInfoDto != null) {
                    packageInfoDtoList.add(packageInfoDto);
                }
            }
        }
        return packageInfoDtoList;
    }

    /**
     * 获取套餐信息
     * @param productId
     *            会员id
     * @param terminal
     *            终端，用这个值过滤套餐，如tv传的值为{@link VipPackage.TERMINAL_TV}
     * @param commonParam
     * @return
     */
    public PackageInfoDto getPackageByPackageId(Integer packageId, String terminal, CommonParam commonParam) {
        if (packageId == null) {
            return null;
        }
        PackageInfoDto packageInfoDto = null;

        BossTpResponse<VipPackage> vipPackageTpResponse = this.facadeTpDao.getVipTpDao().getPackageById(packageId,
                commonParam);
        if (vipPackageTpResponse != null && vipPackageTpResponse.isSucceed()) {
            VipPackage vipPackage = vipPackageTpResponse.getData();
            if (StringUtils.isNotBlank(terminal)) {
                if (vipPackage.getTerminal() == null || !vipPackage.getTerminal().contains(terminal)) { // 不包含可用终端，则过滤掉
                    return null;
                }
            }
            packageInfoDto = parseVipPackage(vipPackage, commonParam);
        }
        return packageInfoDto;
    }

    /**
     * 解析会员套餐包
     * @param vipPackage
     *            会员套餐
     * @param commonParam
     * @return
     */
    public PackageInfoDto parseVipPackage(VipPackage vipPackage, CommonParam commonParam) {
        PackageInfoDto packageInfoDto = null;
        if (vipPackage != null && vipPackage.getStatus() == vipPackage.STATUS_PUBLISHED) { // 套餐为已发布状态
            packageInfoDto = new PackageInfoDto();
            packageInfoDto.setId(vipPackage.getId());
            packageInfoDto.setName(vipPackage.getName());
            packageInfoDto.setDesc(vipPackage.getDesc());
            packageInfoDto.setImg(vipPackage.getPic2()); // 取100*100的图片
            packageInfoDto.setBigImg(vipPackage.getPic3()); // 取800*800的图片
            packageInfoDto.setPrice(vipPackage.getPrice());
            packageInfoDto.setPriceField(this.getPackagePriceField(commonParam));
            packageInfoDto.setDuration(vipPackage.getDuration());
            packageInfoDto.setDurationType(vipPackage.getDurationType());
            packageInfoDto.setDurationTypeName(MessageUtils.getMessage(
                    VipConstants.getBossV2VipPackageDurationTypeMsgKey(vipPackage.getDurationType()),
                    commonParam.getLangcode()));
            packageInfoDto.setAutoRenew(vipPackage.getAutoRenew());
            packageInfoDto.setAutoRenewPeriod(vipPackage.getAutoRenewPeriod());
            if (vipPackage.getVipDiscountPriceInfo() != null && vipPackage.getVipDiscountPriceInfo().size() > 0) { // 设置会员折扣价格
                List<PackageInfoDto.VipDiscountPriceInfo> vipDiscountPriceInfoDtoList = new ArrayList<PackageInfoDto.VipDiscountPriceInfo>();
                packageInfoDto.setVipDiscountPriceInfo(vipDiscountPriceInfoDtoList);
                for (VipPackage.VipDiscountPriceInfo vipDiscountPriceInfo : vipPackage.getVipDiscountPriceInfo()) {
                    PackageInfoDto.VipDiscountPriceInfo vipDiscountPriceInfoDto = new PackageInfoDto.VipDiscountPriceInfo();
                    vipDiscountPriceInfoDto.setVipId(vipDiscountPriceInfo.getVipId());
                    vipDiscountPriceInfoDto.setPrice(vipDiscountPriceInfo.getPrice());
                    BossTpResponse<Vip> vipBossTpResponse = this.facadeTpDao.getVipTpDao().getProductById(
                            vipDiscountPriceInfo.getVipId(), commonParam);
                    if (vipBossTpResponse != null && vipBossTpResponse.isSucceed()) {
                        Vip vip = vipBossTpResponse.getData();
                        if (vip != null && VipTpConstant.Type_Group_US.BASIC.getCode().equals(vip.getCategory())) { // 保留basic会员
                            vipDiscountPriceInfoDtoList.add(vipDiscountPriceInfoDto);
                        }
                    } else { // 调用失败,不做过滤
                        vipDiscountPriceInfoDtoList.add(vipDiscountPriceInfoDto);
                    }
                }
            }
        }
        return packageInfoDto;
    }

    /**
     * 获取价格单位
     */
    private String getPackagePriceField(CommonParam commonParam) {
        String priceFiled = null;
        if (LocaleConstant.Wcode.US.equalsIgnoreCase(commonParam.getSalesArea())) {
            priceFiled = "$";
        } else {
            priceFiled = "￥"; // 默认'元'
        }
        return priceFiled;
    }

    /**
     * 解析会员套餐包
     * @param vipPackage
     *            会员套餐
     * @param terminal
     *            终端，用这个值过滤套餐，如tv传的值为{@link VipPackage.TERMINAL_TV}
     * @param commonParam
     * @return
     */
    public PackageInfoDto parseVipPackage(VipPackage vipPackage, String terminal, CommonParam commonParam) {
        PackageInfoDto packageInfoDto = null;
        if (vipPackage != null && vipPackage.getStatus() == vipPackage.STATUS_PUBLISHED) { // 套餐为已发布状态
            if (StringUtils.isNotBlank(terminal)) {
                if (vipPackage.getTerminal() == null || !vipPackage.getTerminal().contains(terminal)) { // 不包含可用终端，则过滤掉
                    return null;
                }
            }
            packageInfoDto = new PackageInfoDto();
            packageInfoDto.setId(vipPackage.getId());
            packageInfoDto.setProductId(vipPackage.getProductId());
            packageInfoDto.setName(vipPackage.getName());
            packageInfoDto.setDesc(vipPackage.getDesc());
            packageInfoDto.setImg(vipPackage.getPic2()); // 取100*100的图片
            packageInfoDto.setBigImg(vipPackage.getPic3()); // 取800*800的图片
            packageInfoDto.setPrice(vipPackage.getPrice());
            packageInfoDto.setPriceField(this.getPackagePriceField(commonParam));
            packageInfoDto.setDuration(vipPackage.getDuration());
            packageInfoDto.setDurationType(vipPackage.getDurationType());
            packageInfoDto.setDurationTypeName(MessageUtils.getMessage(
                    VipConstants.getBossV2VipPackageDurationTypeMsgKey(vipPackage.getDurationType()),
                    commonParam.getLangcode()));
            packageInfoDto.setAutoRenew(vipPackage.getAutoRenew());
            packageInfoDto.setAutoRenewPeriod(vipPackage.getAutoRenewPeriod());
        }
        return packageInfoDto;
    }

    /**
     * 返回试用的单位
     * @param field
     * @param duration
     * @param commonParam
     * @return
     */
    private String getTrialField(String field, Integer duration, CommonParam commonParam) {
        String finalField = null;
        String message = null;
        if (VipConstants.TRIAL_FIELD_DATE_TP_RESPONSE.equalsIgnoreCase(field)) {
            if (duration != null && duration > 1) {
                message = VipConstants.TRIAL_FIELD_DAY_COMPLEX_TEXT;
            } else {
                message = VipConstants.TRIAL_FIELD_DAY_TEXT;
            }
        } else if (VipConstants.TRIAL_FIELD_MONTH_TP_RESPONSE.equalsIgnoreCase(field)) {
            if (duration != null && duration > 1) {
                message = VipConstants.TRIAL_FIELD_MONTH_COMPLEX_TEXT;
            } else {
                message = VipConstants.TRIAL_FIELD_MONTH_TEXT;
            }
        } else if (VipConstants.TRIAL_FIELD_YEAR_TP_RESPONSE.equalsIgnoreCase(field)) {
            if (duration != null && duration > 1) {
                message = VipConstants.TRIAL_FIELD_YEAR_COMPLEX_TEXT;
            } else {
                message = VipConstants.TRIAL_FIELD_YEAR_TEXT;
            }
        }
        finalField = MessageUtils.getMessage(message, commonParam.getLangcode());
        if (StringUtil.isBlank(finalField)) { // 没找到,则返回传递过来的值
            finalField = field;
        }
        return finalField;
    }

    /**
     * TV端获取会员信息(V2).
     * @param isFromCache
     *            是否从缓存中取,true:先从缓存中取,取不到回源boss,false:直接从boss取
     * @param commonParam
     * @return
     */
    public Map<Integer, SubscribeInfo> getVipInfoV2(boolean isFromCache, CommonParam commonParam) {
        return this.getVipInfoV2(VipTpConstant.BossTerminalType.TV.getCode(), isFromCache, commonParam);
    }

    /**
     * 获取会员信息(V2).
     * @param terminal
     *            终端,见
     *            {@link com.letv.itv.v2.tp.vip.VipTpConstant.BossTerminalType}
     * @param isFromCache
     *            是否从缓存中取,true:先从缓存中取,取不到回源boss,false:直接从boss取
     * @param commonParam
     * @return
     */
    public Map<Integer, SubscribeInfo> getVipInfoV2(Integer terminal, boolean isFromCache, CommonParam commonParam) {
        Map<Integer, SubscribeInfo> vipInfoMap = null;

        if (null != commonParam && null != commonParam.getP_devType()) {
            terminal = Integer.parseInt(MmsDataUtil.getPayPlatform(String.valueOf(commonParam.getP_devType())));
        }

        if (isFromCache) { // get from cahce
            vipInfoMap = this.facadeCacheDao.getVipCacheDao().getVipInfoV2(commonParam.getUserId(), terminal); // 从缓存中取会员信息
            if (vipInfoMap == null) {
                vipInfoMap = this.updateVipInfoInCacheV2(terminal, commonParam);
            }
        } else { // get from boss
            vipInfoMap = this.getVipInfoFromBoss(terminal, commonParam);
        }
        return vipInfoMap;
    }

    /**
     * 更新缓存中的用户会员信息
     * @param terminal
     *            终端,见
     *            {@link com.letv.itv.v2.tp.vip.VipTpConstant.BossTerminalType}
     * @param commonParam
     * @return
     */
    public Map<Integer, SubscribeInfo> updateVipInfoInCacheV2(Integer terminal, CommonParam commonParam) {
        Map<Integer, SubscribeInfo> vipInfoMap = this.getVipInfoFromBoss(terminal, commonParam);
        if (vipInfoMap != null) {
            this.facadeCacheDao.getVipCacheDao().setVipInfoV2(commonParam.getUserId(), terminal, vipInfoMap); // 将会员信息放入缓存
        }
        return vipInfoMap;
    }

    /**
     * 从boss获取会员信息(V2)
     * @param terminal
     *            终端,见
     *            {@link com.letv.itv.v2.tp.vip.VipTpConstant.BossTerminalType}
     * @param commonParam
     * @return
     */
    public Map<Integer, SubscribeInfo> getVipInfoFromBoss(Integer terminal, CommonParam commonParam) {
        Map<Integer, SubscribeInfo> vipInfoMap = null;
        BossTpResponse<List<SubscribeInfo>> subscribeInfoListTpResponse = this.facadeTpDao.getVipTpDao().getVipInfo(
                terminal, commonParam); // 调boss接口获取会员数据
        if (subscribeInfoListTpResponse != null && subscribeInfoListTpResponse.isSucceed()) {
            List<SubscribeInfo> subscribeInfoList = subscribeInfoListTpResponse.getData();
            if (subscribeInfoList != null && subscribeInfoList.size() > 0) {
                vipInfoMap = new HashMap<Integer, SubscribeInfo>();
                for (SubscribeInfo subscribeInfo : subscribeInfoList) {
                    vipInfoMap.put(subscribeInfo.getProductId(), subscribeInfo);
                }
            }
        }
        return vipInfoMap;
    }

    /**
     * 判断用户是否可以试用某一会员
     * 不可试用则返回null,可以试用则返回试用的时长等信息
     * @param productId
     *            会员id
     * @param terminal
     *            终端,见
     *            {@link com.letv.itv.v2.tp.vip.VipTpConstant.BossTerminalType}
     * @param commonParam
     * @return
     */
    public TrialData isTryToUse(Integer productId, Integer terminal, CommonParam commonParam) {
        Map<Integer, SubscribeInfo> vipInfoMap = this.getVipInfoV2(terminal, false, commonParam); // 获取用户订阅过的会员信息列表
        boolean used = false; // 是否试用或订阅过此会员, 默认未试用或订阅过此会员
        boolean isTryToUse = false; // 是否支持试用,默认不支持
        if (vipInfoMap != null && vipInfoMap.get(productId) != null) {
            used = true;
        }
        if (!used) { // 未使用过,则查询此会员是否支持试用
            BossTpResponse<Vip> vipResponse = this.facadeTpDao.getVipTpDao().getProductById(productId, commonParam);
            if (vipResponse != null && vipResponse.isSucceed()) {
                Vip vip = vipResponse.getData();
                if (vip.getTrialData() != null && vip.getTrialData().getTrialDuration() == null
                        || vip.getTrialData().getTrialDuration() > 0) { // 有试用信息且试用时长大于0,则可以试用
                    return vip.getTrialData();
                }
            }
        }
        return null;
    }
}

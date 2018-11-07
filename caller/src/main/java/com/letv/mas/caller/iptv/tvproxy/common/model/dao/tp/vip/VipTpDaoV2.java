package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip;

import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.CashierVPConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean.VipMemberShip;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：apple on 17/3/8 23:30
 * eMail：dengliwei@le.com
 */
@Component
public class VipTpDaoV2 extends VipTpDao {
    @Autowired(required = false)
    private SessionCache sessionCache;

    private final static Logger LOG = LoggerFactory.getLogger(VipTpDaoV2.class);

    /**
     * 根据会员套餐ID获取活动列表接口
     * @param packageId
     *            会员套餐ID
     * @param commonParam
     * @return
     */
    public BossTpResponse<List<VipActivity>> getActivityData(Integer packageId, CommonParam commonParam) {
        BossTpResponse<List<VipActivity>> response = null;
        String logPrefix = "getActivityData_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + packageId;
        if (packageId != null && packageId > 0) {
            try {
                String type = "activitysByPackageId";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_ACTIVITY_BY_PACKAGE_ID()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&terminal=").append(VipTpConstant.TERMINAL_TV);
                params.append("&packageId=").append(packageId);
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                LOG.info(logPrefix + ": invoke return [" + result + "]");
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result,
                            new LetvTypeReference<BossTpResponse<List<VipActivity>>>() {
                            });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * generate order without payment channel
     * @param packageId
     * @param packageType
     * @param couponCode
     * @param cpsId
     * @param commonParam
     * @return
     */
    public BossTpResponse<VipProductOrder> purchaseProductV2(String packageId, int packageType, String couponCode,
                                                             String cpsId, CommonParam commonParam) {
        BossTpResponse<VipProductOrder> response = null;
        String logPrefix = "purchaseProductV2_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + packageId;
        if (StringUtil.isNotBlank(packageId) && StringUtil.isNotBlank(commonParam.getUserId())) {
            try {
                String type = "orderCreate";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.SET_ORDER_BY_PRODUCT_ID()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                params.append("&userId=").append(commonParam.getUserId());
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&packageId=").append(packageId);
                params.append("&packageType=").append(packageType);
                // 解决运营商对url参数中ip篡改导致支付验签问题 @dengliwei 20170210
                String tmpIp = commonParam.getClientIp();
                if (StringUtil.isNotBlank(tmpIp) && IpAddrUtil.isIP(tmpIp)) {
                    tmpIp = Long.toString(IpAddrUtil.ipToLong(tmpIp));
                }
                if (StringUtil.isNotBlank(couponCode)) {
                    params.append("&coupon=").append(couponCode);
                }
                if (StringUtil.isNotBlank(cpsId)) {
                    params.append("&cpsId=").append(cpsId);
                }
                params.append("&userIp=").append(commonParam.getClientIp());
                params.append("&platform=").append(VipTpConstant.VIP_GENERATE_ORDER_FROM); // 国广平台-乐视!!!
                params.append("&deviceType=").append(2); // 购买设备类型(0:代表pc
                                                         // 、1:代表手机 、2:代表电视)
                params.append("&device=").append(StringUtils.trimToEmpty(commonParam.getTerminalSeries()));
                params.append("&terminal=").append(MmsDataUtil.getPayPlatform(commonParam.getP_devType()));
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                LOG.info(logPrefix + ": invoke return [" + result + "]");
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<VipProductOrder>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 直播付费鉴权，查看当前用户是否已经购买过直播（是否有播放权限）
     * @param pid
     * @param liveId
     * @param streamId
     * @param splatId
     * @param isstart
     * @param termid
     * @param logPrefix
     *            日志关键参数
     * @param commonParam
     * @return
     */
    public CheckLiveTpResponse doCheckLiveV2(String pid, String liveId, String streamId, String splatId,
                                             Integer isstart, String termid, String logPrefix, CommonParam commonParam) {
        CheckLiveTpResponse response = null;
        // StringBuilder logPrefixBuilder = new StringBuilder("doCheckLiveV2_");
        // logPrefixBuilder.append(termid).append("_").append(commonParam.getMac()).append("_")
        // .append(commonParam.getDevId()).append("_").append(commonParam.getDeviceKey()).append("_")
        // .append(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_LIVE).append("_").append(pid);

        if (StringUtil.isNotBlank(liveId)) {
            Integer liveStatus = 1; // 1：未开始 2：进行中 3：已结束
            if (isstart == 1) {
                liveStatus = 2;
            } else {
                // TODO: liveStatus = 3;
            }

            try {
                JSONObject liveTokenInfo = new JSONObject();
                liveTokenInfo.put("streamId", streamId);
                liveTokenInfo.put("splatId", splatId);
                BossTpResponse<ValidateServiceTp> validateServiceTpBossTpResponse = this.validateLivePlayService(
                        commonParam.getUserId(), pid, liveId, liveStatus, streamId, splatId, commonParam);
                if (null != validateServiceTpBossTpResponse) {
                    response = this.parseLiveAuthV2(validateServiceTpBossTpResponse, isstart, commonParam);
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 查询用户是否购买过某一直播券
     * @param liveId
     * @param logPrefix
     * @param commonParam
     * @return
     */
    public BossTpResponse<List<LiveTicket>> checkUserLiveTicketV2(String liveId, String logPrefix,
                                                                  CommonParam commonParam) {
        BossTpResponse<List<LiveTicket>> response = null;
        if (StringUtil.isNotBlank(liveId)) {
            try {
                String type = "liveTicket";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_LIVE_HOST()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&status=").append(0);
                params.append("&userId=").append(commonParam.getUserId());
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                LOG.info(logPrefix + ": invoke return [" + result + "]");
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result,
                            new LetvTypeReference<BossTpResponse<List<LiveTicket>>>() {
                            });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 查询“直播票”可售卖套餐列表接口
     * 注：如果 liveId 是场次ID：返回该场次套餐 + 通用赛事套餐
     * 如果 liveId 是(频道+赛事)ID：仅返回通用赛事套餐
     * !!!目前只支取返回的场次套装!!!
     * @param liveIds
     * @param logPrefix
     * @param commonParam
     * @return
     */
    public Map<String, LivePackage> getLivePackage(String liveIds, String logPrefix, CommonParam commonParam) {
        Map<String, LivePackage> ret = null;
        BossTpResponse<List<LivePackage>> response = null;
        if (StringUtil.isNotBlank(liveIds)) {
            try {
                String[] urls = this.buildUrl4ReqLivePackage(liveIds, commonParam);
                Map<String, String> results = this.restTemplate.multiHttpRequests(urls, "GET", null, null);
                LivePackage livePackage = null;
                if (null != results && results.size() > 0) {
                    String result = null;
                    ret = new HashMap<String, LivePackage>();
                    for (String url : urls) {
                        result = results.get(url);
                        if (StringUtil.isNotBlank(result)) {
                            response = objectMapper.readValue(result,
                                    new LetvTypeReference<BossTpResponse<List<LivePackage>>>() {
                                    });
                            if (null != response && null != response.getData() && response.getData().size() > 0) {
                                livePackage = this.findLivePackage(2, response.getData()); // 套餐类型
                                                                                           // 1:
                                                                                           // 赛事套餐
                                                                                           // 2:
                                                                                           // 场次套餐
                                if (null != livePackage) {
                                    ret.put(livePackage.getExtendId(), livePackage);
                                }
                            }
                        }
                        LOG.info(logPrefix + ": invoke return [" + result + "]");
                    }
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return ret;
    }

    private LivePackage findLivePackage(int packageType, List<LivePackage> livePackages) {
        LivePackage ret = null;
        for (LivePackage livePackage : livePackages) {
            if (packageType == livePackage.getPackageType()) {
                ret = livePackage;
                break;
            }
        }
        return ret;
    }

    private String[] buildUrl4ReqLivePackage(String liveIds, CommonParam commonParam) {
        String[] liveIdArr = null, urls = null;
        if (StringUtil.isNotBlank(liveIds)) {
            liveIdArr = StringUtils.split(liveIds, ",");
            urls = new String[liveIdArr.length];
            StringBuilder subUrl = new StringBuilder();
            StringBuilder params = new StringBuilder();
            String type = "getLivePackage";
            String sign = null;
            VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
            for (int i = 0, length = liveIdArr.length; i < length; i++) {
                subUrl.setLength(0);
                params.setLength(0);
                subUrl.append(VipTpConstant.GET_LIVE_HOST()).append("?");
                params.append("type=").append(type);
                params.append("&liveId=").append(liveIdArr[i]);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                urls[i] = subUrl.toString();
            }
        }
        return urls;
    }

    /**
     * 激活用户已购买的某一直播券
     * @param liveId
     * @param logPrefix
     * @param commonParam
     * @return
     */
    public BossTpResponse<String> activeUserLiveTicketV2(String liveId, String logPrefix, CommonParam commonParam) {
        BossTpResponse<String> response = null;
        if (StringUtil.isNotBlank(liveId) && StringUtil.isNotBlank(commonParam.getUserId())) {
            try {
                String type = "checkTicket";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_LIVE_HOST()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&userId=").append(commonParam.getUserId());
                params.append("&liveId=").append(liveId);
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                LOG.info(logPrefix + ": invoke return [" + result + "]");
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<String>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 判断用户是否已购买过直播
     * @return
     */
    public boolean hasBoughtLiveTicket(BossTpResponse<List<LivePackage>> checkTicketTpResponse) {
        boolean ret = false;
        if (null != checkTicketTpResponse && checkTicketTpResponse.isSucceed()) {
            List<LivePackage> packageList = checkTicketTpResponse.getData();
            if (!CollectionUtils.isEmpty(packageList)) {
                for (LivePackage ticket : packageList) {
                    if (ticket.getPackageType() != null && VipTpConstant.LIVE_TYPE_TICKET == ticket.getPackageType()
                            && ticket.getStatus() != null && ticket.getStatus() == 1 && ticket.getCounts() != null
                            && ticket.getCounts() > 0) {
                        ret = true;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 判断用户是否已购买过直播
     * @return
     */
    public boolean hasBoughtLiveTicket(String liveId, BossTpResponse<List<LiveTicket>> checkTicketTpResponse) {
        boolean ret = false;
        if (null != checkTicketTpResponse && checkTicketTpResponse.isSucceed()) {
            List<LiveTicket> packageList = checkTicketTpResponse.getData();
            if (!CollectionUtils.isEmpty(packageList)) {
                for (LiveTicket ticket : packageList) {
                    if (StringUtil.isNotBlank(ticket.getScreening()) && liveId.equals(ticket.getScreening())) {
                        ret = true;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 获取视频或者专辑付费策略
     * @param pids
     *            专辑id列表（最多50个，半角逗号分隔，pids和vids二者至少有一个）
     * @param vids
     *            视频id列表（最多50个，半角逗号分隔，pids和vids二者至少有一个）
     * @param commonParam
     * @return
     */
    public BossTpResponse<PayInfo> getPayInfo(String pids, String vids, CommonParam commonParam, String logPrefix) {
        BossTpResponse<PayInfo> response = null;
        if ((StringUtil.isNotBlank(pids) || StringUtil.isNotBlank(vids))) {
            try {
                String type = "getPayInfo";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_PAYINFO_HOST()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                if (StringUtil.isNotBlank(pids)) {
                    params.append("&pids=").append(pids);
                }
                if (StringUtil.isNotBlank(vids)) {
                    params.append("&vids=").append(vids);
                }
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                LOG.info(logPrefix + ": invoke return [" + result + "]");
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<PayInfo>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 仅限V1=》V2直播鉴权返回结果翻译
     * @param validateServiceTpBossTpResponse
     * @param liveStatus
     * @param commonParam
     * @return
     */
    private CheckLiveTpResponse parseLiveAuthV2(BossTpResponse<ValidateServiceTp> validateServiceTpBossTpResponse,
            Integer liveStatus, // TODO
            CommonParam commonParam) {

        CheckLiveTpResponse response = null;

        if (null != validateServiceTpBossTpResponse && validateServiceTpBossTpResponse.isSucceed()
                && null != validateServiceTpBossTpResponse.getData()) {
            response = new CheckLiveTpResponse();
            CheckLiveTpResponse.CheckLiveInfo checkLiveInfo = new CheckLiveTpResponse.CheckLiveInfo();
            if (validateServiceTpBossTpResponse.getData().getStatus() == 0) {
                checkLiveInfo.setCode("1004");
                response.setIsPre(true);
            } else {
                checkLiveInfo.setCode(String.valueOf(validateServiceTpBossTpResponse.getCode()));
                response.setIsPre(false);
            }
            checkLiveInfo.setLiveStatus(liveStatus);
            response.setInfo(checkLiveInfo);
            response.setLiveStatus(liveStatus);
            response.setStatus(String.valueOf(validateServiceTpBossTpResponse.getData().getStatus()));
            response.setToken(validateServiceTpBossTpResponse.getData().getToken());
            response.setUinfo(validateServiceTpBossTpResponse.getData().getUinfo());
            response.setPreend(validateServiceTpBossTpResponse.getData().getTvodRts()); // TODO
        }

        return response;
    }

    /**
     * 取消会员自动订阅
     * @param commonParam
     * @return
     */
    public VipAccountTpResponse unsubscribeMonthlyPayment(CommonParam commonParam) {
        VipAccountTpResponse response = null;
        String logPrefix = "checkOrderStatus_" + commonParam.getMac() + "_" + commonParam.getUserId();
        try {
            StringBuilder subUrl = new StringBuilder();
            // subUrl.append(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL_V3);
            subUrl.append("http://api-vip.le.com/act/user-info/act/user/subscribe/cancel?");
            StringBuilder params = new StringBuilder();
            params.append("type=").append("pro");
            params.append("&terminal=").append(CashierVPConstant.FETCH_USER_VIP_INFO_TERM);
            params.append("&vipcsrf=vipcsrf");
            subUrl.append(params);
            HttpHeaders headers = new HttpHeaders();
            StringBuilder Cookie = new StringBuilder();
            Cookie.append("ssouid=").append(commonParam.getUserId()).append(";");
            Cookie.append("vipcsrf=vipcsrf;");
            Cookie.append("sso_tk=").append(commonParam.getToken());
            headers.add("Cookie", Cookie.toString());
            headers.add("Referer", "http://le.com");
            HttpEntity httpEntity = new HttpEntity(headers);
            ResponseEntity<String> result = this.restTemplate.exchange(subUrl.toString(), HttpMethod.GET, httpEntity,
                    String.class, new Object[0]);
            response = new VipAccountTpResponse();
            if (StringUtil.isNotBlank(result.getBody())) {
                response = objectMapper.readValue(result.getBody(), VipAccountTpResponse.class);
            }
            LOG.info(logPrefix + ": invoke return [" + result + "]");
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 获取用户影视会员权益信息
     * @param commonParam
     * @return
     */
    public MemberShipInfoResponse<VipMemberShip> queryMonthlyPaymentStatus(CommonParam commonParam) {
        MemberShipInfoResponse<VipMemberShip> response = null;
        String logPrefix = "checkOrderStatus_" + commonParam.getMac() + "_" + commonParam.getUserId();
        try {
            StringBuilder subUrl = new StringBuilder();
            // subUrl.append(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL_V3);
            subUrl.append("http://api-vip.le.com/act/user-info/act/user/membership/info?");
            StringBuilder params = new StringBuilder();
            params.append("term=").append(CashierVPConstant.FETCH_USER_VIP_INFO_TERM);
            params.append("&deviceKey=").append(commonParam.getDeviceKey());
            params.append("&mac=").append(commonParam.getMac());
            params.append("&vipcsrf=vipcsrf");
            subUrl.append(params);
            HttpHeaders headers = new HttpHeaders();
            StringBuilder Cookie = new StringBuilder();
            Cookie.append("vipcsrf=vipcsrf;");
            Cookie.append("ssouid=").append(commonParam.getUserId()).append(";");
            Cookie.append("sso_tk=").append(commonParam.getToken());
            headers.add("Cookie", Cookie.toString());
            headers.add("Referer", "http://www.le.com");
            response = new MemberShipInfoResponse<>();
            HttpEntity httpEntity = new HttpEntity(headers);
            ResponseEntity<String> result = this.restTemplate.exchange(subUrl.toString(), HttpMethod.GET, httpEntity,
                    String.class, new Object[0]);
            if (StringUtil.isNotBlank(result.getBody())) {
                response = objectMapper.readValue(result.getBody(),
                        new LetvTypeReference<MemberShipInfoResponse<VipMemberShip>>() {
                        });
            }
            LOG.info(logPrefix + ": invoke return [" + result + "]");
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 批量获取专辑、视频集合的付费信息
     * @param pids
     * @param vids
     * @param platforms
     * @param logPrefix
     * @param commonParam
     * @return
     */
    public BossTpResponse<BossChargeInfoData> mGetChargeInfo(String pids, String vids, String platforms,
                                                             String logPrefix, CommonParam commonParam) {
        BossTpResponse<BossChargeInfoData> response = null;
        if (StringUtil.isNotBlank(pids) || StringUtil.isNotBlank(vids)) {
            try {
                String type = "getChargeInfo";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.BOSS_YUANXIAN_V2_BASE_URL);
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                if (StringUtil.isNotBlank(pids)) {
                    params.append("&pids=").append(pids);
                }
                if (StringUtil.isNotBlank(vids)) {
                    params.append("&vids=").append(vids);
                }
                if (StringUtil.isNotBlank(platforms)) {
                    params.append("&platforms=").append(platforms);
                } else {
                    return response;
                }
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                LOG.info(logPrefix + ": invoke return [" + result + "]");
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result,
                            new LetvTypeReference<BossTpResponse<BossChargeInfoData>>() {
                            });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 获取会员状态时长信息(V2)
     * @param vendor_no_product_id
     *            : 格式：vendor_no_product_id=511703141887278862336:1,
     *            581710242538733297340416:1
     * @param commonParam
     *            公共参数: 其中的userId不能为空
     * @return
     */
    public LePayTpResponse<List<SubscribeInfoV2>> getVipInfoV2(String vendor_no_product_id, CommonParam commonParam) {
        if (StringUtil.isBlank(commonParam.getUserId())) {
            return null;
        }
        LePayTpResponse<List<SubscribeInfoV2>> response = null;

        try {
            String type = "info";
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.LEPAY_VIP_GETTIME_URL);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user_id", commonParam.getUserId());
            if (StringUtil.isNotBlank(vendor_no_product_id)) {
                params.put("vendor_no_product_id", vendor_no_product_id);
            }
            if (StringUtil.isNotBlank(commonParam.getMac())) {
                params.put("mac", commonParam.getMac());
            }
            if (StringUtil.isNotBlank(commonParam.getDeviceKey())) {
                params.put("device_key", commonParam.getDeviceKey());
            }
            params.put("business_id", VipTpConstant.BussinessIdAndSignKey.LEPAY_CIBN.getBussinessId());
            String sign = this.getSign(HttpClientUtil.getUrlParamsByMap(params),
                    VipTpConstant.BussinessIdAndSignKey.LEPAY_CIBN.getSignKey());
            params.put("sign", sign);

            // String result =
            // this.restTemplate.postForObject(subUrl.toString(), params,
            // String.class, params);
            subUrl.append("?").append(HttpClientUtil.getUrlParamsByMap(params));
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            LOG.info("getVipInfo_" + commonParam.getUserId() + "_" + commonParam.getMac() + "|invoke return:[" + result
                    + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result,
                        new LetvTypeReference<LePayTpResponse<List<SubscribeInfoV2>>>() {
                        });
            }

        } catch (Exception e) {
            LOG.error(
                    "getVipInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + commonParam.getDeviceKey() + " return error: ", e);
        }
        return response;
    }
}

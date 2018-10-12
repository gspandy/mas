package com.letv.mas.caller.iptv.tvproxy.user.service;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.constant.*;
import com.letv.mas.caller.iptv.tvproxy.user.model.bean.DeviceBindConentV2;
import com.letv.mas.caller.iptv.tvproxy.user.model.bean.SubscribeInfo;
import com.letv.mas.caller.iptv.tvproxy.user.model.bean.SubscribeInfoV2;
import com.letv.mas.caller.iptv.tvproxy.user.model.bean.UserStatus;
import com.letv.mas.caller.iptv.tvproxy.user.model.dao.tp.UserTpDao;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.LetvUserDto;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.LetvUserInfoDto;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.UserAccountDto;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.response.*;
import com.letv.mas.caller.iptv.tvproxy.user.util.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Iptv
public class UserService extends BaseService {

    @Autowired
    UserSubService userSubService;

    private final static Logger LOG = LoggerFactory.getLogger(UserService.class);

    public Response<UserAccountDto> getUserAccount(Integer deviceType, String sign, CommonParam commonParam) {
        Response<UserAccountDto> response = new Response<UserAccountDto>();
        String errorCode = null;
        String logPrefix = "getUserAccount_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                + commonParam.getUserId();

        // 取用户账号信息
        if (errorCode == null && StringUtils.isBlank(commonParam.getUserId())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]: user not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
            }
        }

        // 参数校验
        if (errorCode == null
                && (deviceType == null
                || DEVICE_BIND_LETV_APPLICATION_TYPE.getDeviceTypeApplicationById(deviceType) == null || StringUtils
                .isBlank(commonParam.getMac()))) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]: illegal parameter.");
        }

        // 签名校验
        boolean doSign = true;
        if (null != commonParam.getDebug() && commonParam.getDebug() > 0) {
            doSign = false;
        }
        if (doSign && !this.checkSignature(sign, commonParam)) {
            errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]: check Signature failed.]");
        }

        // 切乐购测试vendors
        Boolean switcher = ConfigOperationUtil.getBoolean(ApplicationConstants.IPTV_USER_LEPAY_ISTEST);
        boolean isTest = (null != switcher && switcher.booleanValue());

        if (errorCode == null) {
            UserAccountDto accountDto = new UserAccountDto();
            if (!doSign) {
                accountDto.setUrlSign(this.genSignature(sign, commonParam));
            }
            this.setLetvPoint(accountDto, commonParam); // 设置账号乐点

            // 设置会员信息
            accountDto.setIsvip(UserAccountDto.VIP_NOT);
            accountDto.setPackageType(MessageUtils.getMessage(UserConstants.TV_NOT_VIP_TITLE,
                    commonParam.getLangcode()));

            List<UserAccountDto.VipInfoV2Dto> vipInfoV2DtoList = new ArrayList<UserAccountDto.VipInfoV2Dto>();
            accountDto.setVipList(vipInfoV2DtoList);
            LePayTpResponse<List<SubscribeInfoV2>> subscrobeInfoListResponse = userSubService.getVipInfoV2FormTp(null/*VipConstant.LEPAY_VIP_TYPE.getVipProducts(isTest, null)*/, commonParam);
            //subscrobeInfoListResponse = null;
            if (subscrobeInfoListResponse != null && subscrobeInfoListResponse.isSucceed()) {
                List<SubscribeInfoV2> subscrobeInfoList = subscrobeInfoListResponse.getData();
                for (SubscribeInfoV2 subscribeInfo : subscrobeInfoList) {
//                    if (!VipConstant.LEPAY_VIP_TYPE.getVipByType(isTest,
//                            VipConstant.LEPAY_VIP_TYPE.LE_TV.getType()).getVendor().equals(subscribeInfo.getVendorNo())) { // 过滤非影视会员
//                        continue;
//                    }
                    if (subscribeInfo.getProductId() != null) {
                        if ((VipConstants.LEPAY_VIP_TYPE.getVipByVendor(isTest, subscribeInfo.getVendorNo()).getType() == VipConstants.LEPAY_VIP_TYPE.LE_TV.getType()
                                && subscribeInfo.getProductId().equals(VipConstants.PRODUCT_ID_CN.SUPERTV.getCode().toString()))
                                || (VipConstants.LEPAY_VIP_TYPE.getVipByVendor(isTest, subscribeInfo.getVendorNo()).getType() == VipConstants.LEPAY_VIP_TYPE.LE_HOME.getType()
                                && subscribeInfo.getProductId().equals(VipConstants.PRODUCT_ID_CN.SUPERTV.getCode().toString()))
                                ) { // 超级影视会员,兼容以前字段
                            Long endTime = subscribeInfo.getEndTime();
                            if (endTime != null) {
                                this.facadeCacheDao.getVipCacheDao().setUserVipSeniorEndTime(
                                        commonParam.getUserId(), commonParam.getDeviceKey(), endTime);
                                accountDto.setValidDate(TimeUtil.format(new Date(endTime),
                                        TimeUtil.SHORT_DATE_FORMAT));
                                if (endTime > System.currentTimeMillis()) { // 会员到期时间大于当前时间,会员可用
                                    accountDto.setIsvip(UserAccountDto.VIP_SENIOR); // 设置为高级会员
                                    accountDto.setPackageType(MessageUtils.getMessage(UserConstants.TV_VIP_TITLE,
                                            commonParam.getLangcode()));
                                }
                            }
                        } else if ((VipConstants.LEPAY_VIP_TYPE.getVipByVendor(isTest, subscribeInfo.getVendorNo()).getType() == VipConstants.LEPAY_VIP_TYPE.LE_TV.getType()
                                && subscribeInfo.getProductId().equals(VipConstants.PRODUCT_ID_CN.MOBILE.getCode().toString()))
                                || (VipConstants.LEPAY_VIP_TYPE.getVipByVendor(isTest, subscribeInfo.getVendorNo()).getType() == VipConstants.LEPAY_VIP_TYPE.LE_HOME.getType()
                                && subscribeInfo.getProductId().equals(VipConstants.PRODUCT_ID_CN.MOBILE.getCode().toString()))
                                ) {  // 乐次元会员
                            Long endTime = subscribeInfo.getEndTime();
                            if (endTime != null && endTime > System.currentTimeMillis()) { // 会员到期时间大于当前时间,会员可用
                                if (accountDto.getIsvip() == null
                                        || accountDto.getIsvip() < UserAccountDto.VIP_ORDINARY) { // 会员级别小于普通会员
                                    accountDto.setIsvip(UserAccountDto.VIP_ORDINARY); // 设置为普通会员
                                    if (VipConstants.LEPAY_VIP_TYPE.getVipByVendor(isTest, subscribeInfo.getVendorNo()).getType()
                                            == VipConstants.LEPAY_VIP_TYPE.LE_HOME.getType()) {
//                                        this.facadeCacheDao.getVipCacheDao().setUserVipSeniorEndTime(
//                                                commonParam.getUserId(), commonParam.getDeviceKey(), endTime);
//                                        accountDto.setValidDate(TimeUtil.format(new Date(endTime),
//                                                TimeUtil.SHORT_DATE_FORMAT));
                                        //====================解决机卡绑定用户动态时长补贴漏洞(B)==================//
                                        //accountDto.setIsvip(UserAccountDto.VIP_SENIOR); // 设置为高级会员
                                        //====================解决机卡绑定用户动态时长补贴漏洞(E)==================//
                                    }
                                }
                            }
                        }
                        UserAccountDto.VipInfoV2Dto vipInfoV2Dto = new UserAccountDto.VipInfoV2Dto();
                        // vipInfoV2Dto.setProductId(subscribeInfo.getProductId());
                        vipInfoV2Dto.setVipId(VipConstants.LEPAY_VIP_TYPE.getVipByVendor(isTest,
                                subscribeInfo.getVendorNo()).getType());
                        vipInfoV2Dto.setStatus((null != subscribeInfo.getIsExpire() && subscribeInfo.getIsExpire()) ? 0 : 1);
//                        if (StringUtil.isNotBlank(subscribeInfo.getName())) { // TODO
//                            vipInfoV2Dto.setName(subscribeInfo.getName());
//                        }
                        vipInfoV2Dto.setName(MessageUtils.getMessage(
                                VipConstants.VIP_ACCOUNT_TYPE_NAMES[vipInfoV2Dto.getVipId() - 1], commonParam.getLangcode()));
                        vipInfoV2Dto.setEndTime(subscribeInfo.getEndTime());
                        vipInfoV2DtoList.add(vipInfoV2Dto);
                    }
                }
                // 保存用户权益信息，方便相关业务检索
                this.facadeCacheDao.getVipCacheDao().setVipInfoV3(subscrobeInfoList, commonParam);
            } else {
                response = getUserAccount(deviceType, null, sign, commonParam);
                if (null != response) {
                    return response;
                }
                errorCode = ErrorCodeConstants.PAY_VIPINFO_SYNC_FAILURE;
            }

            if (errorCode == null) {
                // 获取“机卡绑定”需求中的用户绑定信息
                checkUserHasBindOrtherDevice(deviceType, accountDto, commonParam);

                if (StringUtils.isEmpty(accountDto.getUsername())) {
                    accountDto.setUsername(commonParam.getUsername());
                }

                if (accountDto.getUsername() != null && accountDto.getUsername().startsWith("cntv_")) {
                    accountDto.setFrom(1);
                }

                // 获取用户头像（200尺寸）,用户信息中的图像分4种尺寸，顺序为298,200,70,50.
                // 若解析出错，返回默认头像
                String defaultPic = "http://i0.letvimg.com/img/201207/30/tx200.png";
                LetvUserDto letvUserDto = null;
                try {
                    letvUserDto = this.getUserInfo(commonParam.getUsername());

                    String picture = null;
                    if (letvUserDto != null && letvUserDto.getBean() != null) {
                        picture = letvUserDto.getBean().getPicture();
                    }

                    if (picture == null || "".equals(picture.trim())) {
                        picture = defaultPic;
                    } else {
                        String[] pics = picture.split(",");
                        if (pics == null || 4 != pics.length) {
                            picture = defaultPic;
                        } else {
                            picture = pics[1];
                        }
                    }
                    // 国广版本的头像需要进行域名转换
                    accountDto.setPicture(DomainMapping.changeDomainByBraodcastId(picture,
                            commonParam.getBroadcastId(), commonParam.getTerminalApplication()));

                    String displayName = null;
                    if (letvUserDto != null && letvUserDto.getBean() != null) {
                        String[] names = new String[]{letvUserDto.getBean().getMobile(),
                                letvUserDto.getBean().getEmail(), letvUserDto.getBean().getNickname()};
                        int i = 0;
                        while (StringUtils.isEmpty(displayName) && i < names.length) {
                            displayName = names[i];
                            i++;
                        }
                    }
                    if (StringUtils.isEmpty(displayName)) {
                        displayName = commonParam.getUsername();
                    }
                    accountDto.setDisplayName(displayName);
                    accountDto.setUserId(commonParam.getUserId());
                } catch (Exception e) {
                    accountDto.setPicture(defaultPic);
                    LOG.error(logPrefix + ": get user basic info failure:", e);
                }

                response.setResultStatus(1);
                response.setData(accountDto);

                // ［异步执行］防盗链行为上报
                this.asyncSetAntiReport(VideoConstants.PLAY_MODEL_COMMON, commonParam, logPrefix);
            } else {
                ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            }
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    /**
     * get user account info and vip info
     *
     * @param deviceType  device type
     * @param from        If the value is golive means from golive application
     * @param sign        golive application then need to check the md5 sign
     * @param commonParam
     * @return
     */
    public Response<UserAccountDto> getUserAccount(Integer deviceType, String from, String sign, CommonParam commonParam) {
        if (UserConstants.USER_FROM_TYPE_GOLIVE.equals(from)) {
            // if it's from golive application
            return this.getVipInfoForGolive(sign, commonParam);
        } else {
            Response<UserAccountDto> response = new Response<UserAccountDto>();
            String errorCode = null;
            if (StringUtils.isBlank(commonParam.getUserId())) {
                // 取用户账号信息
                UserStatus user = this.getUserStatus(commonParam);
                if (user == null || user.getUserId() == 0) {
                    errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                    LOG.info("getUserAccount_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                            + commonParam.getUserId() + "[errorCode=" + errorCode + "]: user not exist.");
                } else {
                    commonParam.setUserId(String.valueOf(user.getUserId()));
                }
            }
            if (errorCode == null
                    && (deviceType == null
                    || DEVICE_BIND_LETV_APPLICATION_TYPE.getDeviceTypeApplicationById(deviceType) == null || StringUtils
                    .isBlank(commonParam.getMac()))) {
                errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
                LOG.info("getUserAccount_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                        + commonParam.getUserId() + "[errorCode=" + errorCode + "]: illegal parameter.");
            }

            if (errorCode == null) {
                UserAccountDto accountDto = new UserAccountDto();
                this.setLetvPoint(accountDto, commonParam); // 设置账号乐点

                // 设置会员信息
                accountDto.setIsvip(UserAccountDto.VIP_NOT);
                accountDto.setPackageType(MessageUtils.getMessage(UserConstants.TV_NOT_VIP_TITLE,
                        commonParam.getLangcode()));
                List<UserAccountDto.VipInfoV2Dto> vipInfoV2DtoList = new ArrayList<UserAccountDto.VipInfoV2Dto>();
                accountDto.setVipList(vipInfoV2DtoList);
                BossTpResponse<List<SubscribeInfo>> subscrobeInfoListResponse = this.facadeTpDao.getVipTpDao()
                        .getVipInfo(VipConstants.BossTerminalType.TV.getCode(), commonParam);
                if (subscrobeInfoListResponse != null && subscrobeInfoListResponse.isSucceed()) {
                    List<SubscribeInfo> subscrobeInfoList = subscrobeInfoListResponse.getData();
                    for (SubscribeInfo subscribeInfo : subscrobeInfoList) {
                        if (!VipConstants.Type_Group_CN.TV.getCode().equals(subscribeInfo.getTypeGroup())) { // 过滤非影视会员
                            continue;
                        }
                        if (subscribeInfo.getProductId() != null) {
                            if (subscribeInfo.getProductId().intValue() == VipConstants.PRODUCT_ID_CN.SUPERTV
                                    .getCode().intValue()) { // 超级影视会员,兼容以前字段
                                Long endTime = subscribeInfo.getEndTime();
                                if (endTime != null) {
                                    this.facadeCacheDao.getVipCacheDao().setUserVipSeniorEndTime(
                                            commonParam.getUserId(), commonParam.getDeviceKey(), endTime);
                                    accountDto.setValidDate(TimeUtil.format(new Date(endTime),
                                            TimeUtil.SHORT_DATE_FORMAT));
                                    if (endTime > System.currentTimeMillis()) { // 会员到期时间大于当前时间,会员可用
                                        accountDto.setIsvip(UserAccountDto.VIP_SENIOR); // 设置为高级会员
                                        accountDto.setPackageType(MessageUtils.getMessage(UserConstants.TV_VIP_TITLE,
                                                commonParam.getLangcode()));
                                    }
                                }
                            } else if (subscribeInfo.getProductId().intValue() == VipConstants.PRODUCT_ID_CN.MOBILE
                                    .getCode().intValue()) { // 乐次元会员
                                Long endTime = subscribeInfo.getEndTime();
                                if (endTime != null && endTime > System.currentTimeMillis()) { // 会员到期时间大于当前时间,会员可用
                                    if (accountDto.getIsvip() == null
                                            || accountDto.getIsvip() < UserAccountDto.VIP_ORDINARY) { // 会员级别小于普通会员
                                        accountDto.setIsvip(UserAccountDto.VIP_ORDINARY); // 设置为普通会员
                                    }
                                }
                            }
                            UserAccountDto.VipInfoV2Dto vipInfoV2Dto = new UserAccountDto.VipInfoV2Dto();
                            // vipInfoV2Dto.setProductId(subscribeInfo.getProductId());
                            vipInfoV2Dto.setVipId(VipConstants.PRODUCT_ID_AND_VIP_ID_MAP.get(subscribeInfo
                                    .getProductId()));
                            vipInfoV2Dto.setName(subscribeInfo.getName());
                            vipInfoV2Dto.setEndTime(subscribeInfo.getEndTime());
                            vipInfoV2DtoList.add(vipInfoV2Dto);
                        }
                    }
                } else {
                    errorCode = ErrorCodeConstants.PAY_VIPINFO_SYNC_FAILURE;
                }

                if (errorCode == null) {
                    // 获取“机卡绑定”需求中的用户绑定信息
                    checkUserHasBindOrtherDevice(deviceType, accountDto, commonParam);

                    if (StringUtils.isEmpty(accountDto.getUsername())) {
                        accountDto.setUsername(commonParam.getUsername());
                    }

                    if (accountDto.getUsername() != null && accountDto.getUsername().startsWith("cntv_")) {
                        accountDto.setFrom(1);
                    }

                    // 获取用户头像（200尺寸）,用户信息中的图像分4种尺寸，顺序为298,200,70,50.
                    // 若解析出错，返回默认头像
                    String defaultPic = "http://i0.letvimg.com/img/201207/30/tx200.png";
                    LetvUserDto letvUserDto = null;
                    try {
                        letvUserDto = this.getUserInfo(commonParam.getUsername());

                        String picture = null;
                        if (letvUserDto != null && letvUserDto.getBean() != null) {
                            picture = letvUserDto.getBean().getPicture();
                        }

                        if (picture == null || "".equals(picture.trim())) {
                            picture = defaultPic;
                        } else {
                            String[] pics = picture.split(",");
                            if (pics == null || 4 != pics.length) {
                                picture = defaultPic;
                            } else {
                                picture = pics[1];
                            }
                        }
                        // 国广版本的头像需要进行域名转换
                        accountDto.setPicture(DomainMapping.changeDomainByBraodcastId(picture,
                                commonParam.getBroadcastId(), commonParam.getTerminalApplication()));

                        String displayName = null;
                        if (letvUserDto != null && letvUserDto.getBean() != null) {
                            String[] names = new String[]{letvUserDto.getBean().getMobile(),
                                    letvUserDto.getBean().getEmail(), letvUserDto.getBean().getNickname()};
                            int i = 0;
                            while (StringUtils.isEmpty(displayName) && i < names.length) {
                                displayName = names[i];
                                i++;
                            }
                        }
                        if (StringUtils.isEmpty(displayName)) {
                            displayName = commonParam.getUsername();
                        }
                        accountDto.setDisplayName(displayName);
                        accountDto.setUserId(commonParam.getUserId());
                    } catch (Exception e) {
                        accountDto.setPicture(defaultPic);
                        LOG.error("getUserAccount_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                                + commonParam.getUserId() + ": get user basic info failure:", e);
                    }

                    response.setResultStatus(1);
                    response.setData(accountDto);
                } else {
                    ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                }
            } else {
                ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            }

            return response;
        }
    }

    /**
     * get user info by username
     *
     * @param username
     * @return
     */
    public LetvUserDto getUserInfo(String username) {
        LetvUserResponse userInfo = this.facadeTpDao.getUserTpDao().getUserInfo(username);
        if (userInfo != null) {
            try {
                LetvUserDto response = ParseUtil.copyBeanByResultField(LetvUserDto.class, userInfo);
                if (userInfo.getBean() != null && userInfo.getBean().size() > 0) {
                    LetvUserInfoDto userInfoDto = ParseUtil.copyBeanByResultField(LetvUserInfoDto.class, userInfo
                            .getBean().get(0));
                    response.setBean(userInfoDto);
                }
                return response;
            } catch (Exception e) {
                LOG.error("getUserInfo is error,username:" + username, e);
            }
        }
        return null;
    }

    public UserStatus getUserStatus(CommonParam commonParam) {
        if (TerminalUtil.isLetvUs(commonParam)) {
            // us version doesn't send userId
            return this.facadeCacheDao.getUserCacheDao().getUserStatusByName(commonParam.getUsername());
        }
        UserStatus user = this.facadeCacheDao.getUserCacheDao().getUserStatus(commonParam);
        if (user == null) {
            user = this.facadeCacheDao.getUserCacheDao().getUserStatusByName(commonParam.getUsername());
        }
        return user;
    }

    /**
     * 签名校验
     *
     * @param sign
     * @param commonParam
     * @return
     */
    private boolean checkSignature(String sign, CommonParam commonParam) {
        Map<String, Object> params = new HashMap<String, Object>();

        if (StringUtil.isNotBlank(commonParam.getUserId())) {
            params.put("userId", commonParam.getUserId());
        }
        if (StringUtil.isNotBlank(commonParam.getToken())) {
            params.put("token", commonParam.getToken());
        }
        if (StringUtil.isNotBlank(commonParam.getMac())) {
            params.put("mac", commonParam.getMac());
        }
        if (StringUtil.isNotBlank(commonParam.getDeviceKey())) {
            params.put("deviceKey", commonParam.getDeviceKey());
        }
        if (TerminalUtil.supportAntiReport(commonParam)) {
            params.put("appCode", commonParam.getAppCode());
            params.put("devSign", commonParam.getDevSign());
        }
        params.put("sig", sign);
        Boolean isValid = CommonUtil.checkSig(params);

        return isValid;
    }

    /**
     * TV端机卡绑定的deviceType适用范围
     *
     * @author yukun
     */
    public enum DEVICE_BIND_LETV_APPLICATION_TYPE {
        TV(VipConstants.DEVICE_BIND_DEVICE_TYPE_TV),
        LETV_BOX(VipConstants.DEVICE_BIND_DEVICE_TYPE_LETV_BOX);

        private final int id;

        private DEVICE_BIND_LETV_APPLICATION_TYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static DEVICE_BIND_LETV_APPLICATION_TYPE getDeviceTypeApplicationById(int id) {
            switch (id) {
                case VipConstants.DEVICE_BIND_DEVICE_TYPE_TV:
                    return TV;
                case VipConstants.DEVICE_BIND_DEVICE_TYPE_LETV_BOX:
                    return LETV_BOX;
                default:
                    return null;
            }
        }
    }

    /**
     * 签名校验
     *
     * @return
     */
    private String genSignature(String sign, CommonParam commonParam) {
        Map<String, Object> params = new HashMap<String, Object>();

        if (StringUtil.isNotBlank(commonParam.getUserId())) {
            params.put("userId", commonParam.getUserId());
        }
        if (StringUtil.isNotBlank(commonParam.getToken())) {
            params.put("token", commonParam.getToken());
        }
        if (StringUtil.isNotBlank(commonParam.getMac())) {
            params.put("mac", commonParam.getMac());
        }
        if (StringUtil.isNotBlank(commonParam.getDeviceKey())) {
            params.put("deviceKey", commonParam.getDeviceKey());
        }
        if (TerminalUtil.supportAntiReport(commonParam)) {
            params.put("appCode", commonParam.getAppCode());
            params.put("devSign", commonParam.getDevSign());
        }
        params.put("sig", sign);

        return CommonUtil.genSig(params);
    }

    /**
     * 设置账号乐点
     *
     * @param userAccountDto
     * @param commonParam
     */
    private void setLetvPoint(UserAccountDto userAccountDto, CommonParam commonParam) {
        // 获取账号乐点数
        Response<BalanceQueryResponse> balanceResp = this.queryBalance(commonParam);
        if (balanceResp != null && balanceResp.getData() != null) {
            userAccountDto.setLetvPoint(balanceResp.getData().getLetvPoint());
        } else {
            userAccountDto.setLetvPoint(0);
        }
    }

    /**
     * get user lepoint balance
     *
     * @param commonParam
     * @return
     */
    public Response<BalanceQueryResponse> queryBalance(CommonParam commonParam) {
        Response<BalanceQueryResponse> response = new Response<BalanceQueryResponse>();
        String errorCode = null;

        if (StringUtils.isBlank(commonParam.getUserId())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("queryBalance_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user does not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
            }
        }

        BalanceQueryResultResponse result = null;
        if (errorCode == null && commonParam != null && StringUtil.isNotBlank(commonParam.getUserId())) {
            // get user lepoint balance
            boolean isFormCache = true;
            result = userSubService.queryBalanceFormCache(commonParam);
            if (result == null) {
                result = this.facadeTpDao.getUserTpDao().queryBalance(commonParam);
                isFormCache = false;
            }
            if (result == null || (result.getCode() != null && result.getCode() == 1)) {
                errorCode = ErrorCodeConstants.PAY_GET_LETV_POINT_FAILURE;
                LOG.info("queryBalance_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: query letv point balance failure ");
            }
            if (result != null && !isFormCache) {
                this.facadeCacheDao.getUserCacheDao().setBalance(commonParam.getUserId(), result);
            }
        }

        if (errorCode == null) {
            BalanceQueryResponse queryDto = new BalanceQueryResponse();
            queryDto.setStatus(LetvPayResponseStatus.SUCCESS);
            queryDto.setLetvPoint(result.getLepoint());
            queryDto.setUsername(commonParam.getUsername());
            response.setResultStatus(1);
            response.setData(queryDto);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    private void checkUserHasBindOrtherDevice(final Integer deviceType, final UserAccountDto accountDto,
                                              CommonParam commonParam) {
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            BossTpResponse<DeviceBindConentV2> userDeviceBindResponse = this.facadeTpDao.getVipTpDao()
                    .getDeviceBindInfoV2(deviceType, commonParam);
            if (userDeviceBindResponse != null && userDeviceBindResponse.isSucceed()) {
                this.checkUserBindOrtherDevice(commonParam.getDeviceKey(), accountDto, userDeviceBindResponse);
            } else {
                accountDto.setHasBindOtherDevice(0);// 无效数据
            }
        } else {
            UserBindTpResponse userBindTp = this.facadeTpDao.getVipTpDao().getUserBindInfo(deviceType, commonParam);
            if (userBindTp != null && userBindTp.isSuccess()) {
                this.checkUserBindOrtherDevice(accountDto, commonParam.getDeviceKey(), userBindTp);
            } else {
                accountDto.setHasBindOtherDevice(0);// 无效数据
            }
        }
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
                                && (String.valueOf(VipConstants.DEVICE_BIND_DEVICE_TYPE_TV).equals(bindKeyTypeSuit[1]) || String
                                .valueOf(VipConstants.DEVICE_BIND_DEVICE_TYPE_LETV_BOX).equals(
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
        if ((bindDeviceKeys.size() == 1 && StringUtil.isNotBlank(currentDeviceKey) && !bindDeviceKeys
                .contains(currentDeviceKey.toUpperCase())) || bindDeviceKeys.size() > 1) {
            accountDto.setHasBindOtherDevice(1);
        } else {
            accountDto.setHasBindOtherDevice(2);
        }
    }

    private void checkUserBindOrtherDevice(String currentDeviceKey, UserAccountDto accountDto,
                                           BossTpResponse<DeviceBindConentV2> userDeviceBindResponse) {
        Set<String> bindDeviceKeys = new HashSet<String>();
        List<DeviceBindConentV2.DeviceBindConentItem> bindItems = userDeviceBindResponse.getData().getItems();
        if (!CollectionUtils.isEmpty(bindItems)) {
            for (DeviceBindConentV2.DeviceBindConentItem bindItem : bindItems) {
                if (bindItem != null && bindItem.getStatus() != null
                        && VipConstants.BOSS_DEVICE_BIND_V2_DEVICE_STATUS_6 == bindItem.getStatus()) {
                    String bindDeviceKey = StringUtils.trimToNull(bindItem.getDeviceKey());
                    if (bindDeviceKey != null) {
                        bindDeviceKeys.add(bindDeviceKey);
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

    public void asyncSetAntiReport(Integer model, CommonParam commonParam, String logPrefix) {
        if (TerminalUtil.supportAntiReport(commonParam)) {
            if (null != this.facadeCacheDao.getVipCacheDao().getAntiReport(commonParam.getDevSign(), commonParam)) {
                return;
            }
            int splatId = VideoUtil.getSplatId(model, commonParam);
            try {
                ReportTpResponse response = this.facadeTpDao.getActReportDao().setAntiReport(String.valueOf(splatId), commonParam);
                if (null != response && response.getCode() > 0) {
                    this.facadeCacheDao.getVipCacheDao().setAntiReport(commonParam.getDevSign(), commonParam);
                }
            } catch (Exception e) {
                LOG.error(logPrefix + "[antiReport]: error=" + e.getMessage(), e);
            }
        }
    }

    /**
     * get user info for golive application
     *
     * @param sign
     * @param commonParam
     * @return
     */
    public Response<UserAccountDto> getVipInfoForGolive(String sign, CommonParam commonParam) {
        Response<UserAccountDto> response = new Response<UserAccountDto>();

        return response;
    }

    @Service
    class UserSubService extends BaseService {
        private final Logger log = LoggerFactory.getLogger(UserTpDao.class);

        /*@HystrixCommand(commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
        },fallbackMethod = "getVipInfoV2Fallback")*/
        @HystrixCommand(threadPoolKey = "vipThreadPool",fallbackMethod = "getVipInfoV2FormTpFallback")
        public LePayTpResponse<List<SubscribeInfoV2>> getVipInfoV2FormTp(String vendor_no_product_id, CommonParam commonParam) {
            log.info(" ---------- getVipInfoV2FormTp  ----------- ");
            return this.facadeTpDao.getUserTpDao().getVipInfoV2(vendor_no_product_id, commonParam);
        }

        //@Upstream(resource = VipConstants.getLepayVipGettimeUrl(),resourceTy = Upstream.Type.NET)
        public LePayTpResponse<List<SubscribeInfoV2>> getVipInfoV2FormTpFallback(String vendor_no_product_id, CommonParam commonParam,Throwable e) {
            HystrixUtil.logFallBack("UserSubService","getVipInfoV2FormTp",VipConstants.getLepayVipGettimeUrl(),e);
            log.info(" ---------- getVipInfoV2Fallback ----------- ");
            return null;
        }

        //@HystrixCommand(threadPoolKey = "cacheThreadPool",fallbackMethod = "queryBalanceFormCacheFallback")
        public BalanceQueryResultResponse queryBalanceFormCache(CommonParam commonParam) {
            return this.facadeCacheDao.getUserCacheDao().queryBalance(commonParam);
        }

        public BalanceQueryResultResponse queryBalanceFormCacheFallback(CommonParam commonParam,Throwable e) {
            HystrixUtil.logFallBack("UserSubService","queryBalanceFormCache",CacheContentConstants.USER_BALANCE_PREFIX + commonParam.getUserId(),e);
            log.info(" ---------- queryBalanceFallback ----------- ");
            return null;
        }
    }

    public Response<UserAccountDto> test(Integer deviceType, String sign, CommonParam commonParam) {
        userSubService.getVipInfoV2FormTp(null/*VipConstant.LEPAY_VIP_TYPE.getVipProducts(isTest, null)*/, commonParam);
        Response<UserAccountDto> test = new Response<UserAccountDto>();
        return test;
    }
@Autowired
    Tracer tracer;
    public Response<UserAccountDto> test2(Integer deviceType, String sign, CommonParam commonParam) {
        Response<UserAccountDto> test = new Response<UserAccountDto>();
        Span span =tracer.createSpan("tvproxy/test");
        try {
            span.logEvent("redisService start");
            span.tag("name", "test2");
            span.tag("peer.service", "redisService");
            span.tag("peer.ipv4", "127.0.0.1");
            span.tag("peer.port", "6379");
            span.tag("method", "get");
            span.logEvent("redisService end");
        } catch (Exception e) {

        } finally {
            if (span != null) {
                tracer.close(span);
            }
        }
        System.out.println("test........");
        tracer.close(span);
        return test;
    }
}

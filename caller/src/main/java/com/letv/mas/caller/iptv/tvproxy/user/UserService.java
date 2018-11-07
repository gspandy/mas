package com.letv.mas.caller.iptv.tvproxy.user;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.channel.service.ChannelService;
import com.letv.mas.caller.iptv.tvproxy.channel.service.ChannelV2Service;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.RolePlayListTp.RolePlayList;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ResponseUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant.APPID;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.CheckPlaylistTp.PlayList;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LetvUserPlayListTp.LetvUserPlayList;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.RolePlayListTp.RolePlayListData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.OauthCodeResponse.OauthCode;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.BossTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LetvUserResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.BalanceQueryResultResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.UserBindTpResponse;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants.Category;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.PlayLogTp.PlayLogInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LetvUserFavoriteListTp.LetvUserFavorite;
import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.LePayTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.FollowListTp.FollowInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.UserStatus;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.UserChildLockTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectPackageDataDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectPackageDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.ErosToken;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant.FOLLOW_FROM;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant.FOLLOW_TYPE;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.LoginRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.RolePlayListRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.UserRoleRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.BossChargeInfoData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.LetvUserDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.LetvUserInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.UserAccountDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.response.BalanceQueryResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.response.LetvPayResponseStatus;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.RoleCommonGetTP.RoleBean;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeCommonUtil ;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.video.service.PlayService;
import com.letv.mas.caller.iptv.tvproxy.video.service.VideoService;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipMetadataService;
import com.letv.mas.caller.iptv.tvproxy.vip.util.VipUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants.VideoType;

import serving.*;

import static com.letv.mas.caller.iptv.tvproxy.user.model.dto.PlayLogListDto.TRY_LOOK;
import static com.letv.mas.caller.iptv.tvproxy.user.model.dto.PlayLogListDto.TRY_LOOK_COMPLETE;


@Service
public class UserService extends BaseService {

    @Autowired
    UserSubService userSubService;
    
    @Autowired
    AlbumVideoAccess albumVideoAccess;
    
    @Autowired
    PlayService playService;
    
    @Autowired
    VideoService videoService;
    
    @Autowired
    ChannelV2Service channelV2Service;
    
    @Autowired
    VipMetadataService vipMetadataService;
    
    @Autowired
    ChannelService channelService;

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
        Boolean switcher = ApplicationUtils.getBoolean(ApplicationConstants.IPTV_USER_LEPAY_ISTEST);
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
                BossTpResponse<List<com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.SubscribeInfo>> subscrobeInfoListResponse = this.facadeTpDao.getVipTpDao()
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
        @HystrixCommand(threadPoolKey = "vipThreadPool", fallbackMethod = "getVipInfoV2FormTpFallback")
        public LePayTpResponse<List<SubscribeInfoV2>> getVipInfoV2FormTp(String vendor_no_product_id, CommonParam commonParam) {
            log.info(" ---------- getVipInfoV2FormTp  ----------- ");
            return this.facadeTpDao.getVipTpDaoV2().getVipInfoV2(vendor_no_product_id, commonParam);
        }

        //@Upstream(resource = VipConstants.getLepayVipGettimeUrl(),resourceTy = Upstream.Type.NET)
        public LePayTpResponse<List<SubscribeInfoV2>> getVipInfoV2FormTpFallback(String vendor_no_product_id, CommonParam commonParam, Throwable e) {
            HystrixUtil.logFallBack("UserSubService", "getVipInfoV2FormTp", VipConstants.getLepayVipGettimeUrl(), e);
            log.info(" ---------- getVipInfoV2Fallback ----------- ");
            return null;
        }

        //@HystrixCommand(threadPoolKey = "cacheThreadPool",fallbackMethod = "queryBalanceFormCacheFallback")
        public BalanceQueryResultResponse queryBalanceFormCache(CommonParam commonParam) {
            return this.facadeCacheDao.getUserCacheDao().queryBalance(commonParam);
        }

        public BalanceQueryResultResponse queryBalanceFormCacheFallback(CommonParam commonParam, Throwable e) {
            HystrixUtil.logFallBack("UserSubService", "queryBalanceFormCache", CacheContentConstants.USER_BALANCE_PREFIX + commonParam.getUserId(), e);
            log.info(" ---------- queryBalanceFallback ----------- ");
            return null;
        }
    }


    //    private final static Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final static Logger MULTI_LOGIN = LoggerFactory.getLogger("multiloginLog");
    private final static List<String> specialUser = new ArrayList<String>();
    // static {
    // specialUser.add("24016511");
    // specialUser.add("11146098");// test user
    // specialUser.add("letv_52a2a5cfb82f0100");
    // specialUser.add("letv_50bed7279dda914");// test user
    // }

    private final static long MUTIL_MACHINE_LOGIN_UID_UPDATE_INTERVAL = 3600000L; // 1小时
    private static long MUTIL_MACHINE_LOGIN_UID_LASTUPDATE_TIME = 0L; // 上次更新时间
    private Lock MUTIL_MACHINE_LOGIN_UID_UPDATE_Lock = new ReentrantLock(); // 用于定时更新

    public Response<LoginCsDto> tokenLogin(Integer roleflag, CommonParam commonParam) {
        LoginCsDto data = null;
        String errorCode = null;

        if (StringUtil.isBlank(commonParam.getUserToken())) {
            errorCode = ErrorCodeConstant.USER_CHECK_TOKEN_LOGIN;
        } else {
            // check user token
            LetvUserTokenResponse tokenResponse = this.facadeTpDao.getUserTpDao().checkTokenLogin(commonParam);
            if ((tokenResponse == null) || (tokenResponse.getStatus() == null)) {
                errorCode = ErrorCodeConstant.USER_LOGIN_BAD;
                LOG.info("tokenLogin_" + commonParam.getMac() + "_" + commonParam.getUserId()
                        + " check user token failured.");
            } else if (tokenResponse.getBean() == null) {
                errorCode = ErrorCodeConstant.USER_CHECK_TOKEN_LOGIN;
                LOG.info("tokenLogin_" + commonParam.getMac() + "_" + commonParam.getUserId()
                        + " check user token failured.");
            } else {
                if (LocaleConstant.Wcode.IN.equalsIgnoreCase(commonParam.getWcode())
                        && CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) { // 印度levidi
                    // 更新用户token，方法里面包含更新token的操作
                    this.getErosToken(commonParam.getUserId(), commonParam.getUserToken(), null,
                            commonParam.getTerminalSeries());
                }
                // this.setUserCookieForTerminal(request, updateMac);//
                // 设置usercookie
                LetvUserResult bean = tokenResponse.getBean();
                // use user center uid to avoid client doesn't send userId
                commonParam.setUserId(String.valueOf(bean.getSsouid()));
                String username = tokenResponse.getBean().getUsername();
                commonParam.setUsername(username);
                // set user info to cache
                this.updateUserStatus(commonParam);
                data = new LoginCsDto();
                data.setUsername(username);
                data.setLoginTime(TimeUtil.getDateString(Calendar.getInstance(), TimeUtil.SIMPLE_DATE_FORMAT));
                data.setUid(bean.getSsouid());
                data.setToken(commonParam.getUserToken());
                data.setIsGG(commonParam.getBroadcastId());
                if (null != bean.getSsouid() && bean.getSsouid() > 0) {
                    data.setOpenId(VipUtil.encodeUid(String.valueOf(bean.getSsouid())));
                }

                // 儿童写缓存--暂时注释掉
                List<LetvUserRoleDto> roleList = this.setRoleListData(StringUtil.toLong(commonParam.getUserId()));
                this.updateRoleCacheStr(bean.getSsouid(), roleList);
                // if roleflag value is one then return user role list
                if ((roleflag != null) && (roleflag == 1)) {
                    data.setRoleList(roleList);
                }
            }
        }

        Response<LoginCsDto> response = new Response<LoginCsDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }

        return response;
    }

    public Response<LoginCsDto> tokenLogin(Integer deviceType, Integer from, Long timestamp, String sign,
                                           CommonParam commonParam) {
        LoginCsDto data = null;
        String errorCode = null;
        if (UserConstants.ErosConstant.FromType.findType(from) == null) { // 验证是否是我们允许的来源
            errorCode = ErrorCodeConstants.USER_FROM_ILLEGAL_ERROR;
        } else if (!this.checkErosTokenSign(commonParam.getUserId(), commonParam.getUserToken(), deviceType, timestamp,
                sign)) {// 验证签名
            errorCode = ErrorCodeConstants.USER_GET_USERINFO_SIGN_ERROR;
        } else {
            ErosToken erosToken = this.getErosToken(commonParam.getUserId(), commonParam.getUserToken(), deviceType,
                    null);
            if (erosToken != null) {
                data = new LoginCsDto();
                data.setErosToken(erosToken.getToken());
                data.setErosTokenSecret(erosToken.getTokenSecret());
                data.setExpireTime(erosToken.getExpireTime());
            } else {
                errorCode = ErrorCodeConstants.USER_GET_EROS_TOKEN_FAIL;
            }
        }

        Response<LoginCsDto> response = new Response<LoginCsDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }
        return response;
    }

    /**
     * check eros token signation
     *            opeataion type:1.get,2.update
     * @param userId
     * @param userToken
     * @param deviceType
     * @param timestamp
     * @param deviceType
     * @param sign
     * @return
     */
    private boolean checkErosTokenSign(String userId, String userToken, Integer deviceType, Long timestamp, String sign) {
        Map<String, Object> params = new HashMap<String, Object>();
        final String secretKeySrand = "%%eros*!@#%#$^&~%%"; // 密钥种子
        final String secretKey = MD5Util.md5(secretKeySrand); // 对密钥种子做一次md5生成密钥，签名结果"5d9405a59f73793fd49fd62b05a627eb"，这个是最终密钥
        StringBuilder signStr = new StringBuilder();
        signStr.append(userId).append(userToken).append(deviceType).append(timestamp).append(secretKey); // 拼接要签名的字符串
        String md5Sign = MD5Util.md5(signStr.toString()); // 对签名字符串进行签名
        if (md5Sign.equalsIgnoreCase(sign)) { // 比较签名
            return true;
        }
        return false;
    }

    /**
     * 2016-01-27，临时恢复线上接口
     * @param request
     * @param commonParam
     * @return
     */
    public LetvUserDto login_v3(LoginRequest request, CommonParam commonParam) {
        String logPrefix = "login_" + request.getLoginTime() + "_" + request.getMac();
        // 1、登陆到用户中心
        LetvUserResponse letvUserLoginResponse = this.facadeTpDao.getUserTpDao().loginToUserCenter_v2(request);
        request.setTv_token(letvUserLoginResponse.getTv_token());
        String username = null;
        if (letvUserLoginResponse != null && letvUserLoginResponse.getBean() != null
                && letvUserLoginResponse.getBean().size() > 0) {
            request.setUser_center_uid(letvUserLoginResponse.getBean().get(0).getUid());
            // 设置登录缓存
            username = letvUserLoginResponse.getBean().get(0).getUsername();
            commonParam.setUserId(String.valueOf(letvUserLoginResponse.getBean().get(0).getUid()));
        }
        commonParam.setUsername(username);
        commonParam.setUserToken(letvUserLoginResponse.getTv_token());
        this.updateUserStatus(commonParam);

        try {
            LetvUserDto response = ParseUtil.copyBeanByResultField(LetvUserDto.class, letvUserLoginResponse);
            LetvUserInfoDto userInfoDto = ParseUtil.copyBeanByResultField(LetvUserInfoDto.class,
                    letvUserLoginResponse.getBean());
            response.setBean(userInfoDto);
            return response;
        } catch (Exception e) {
            this.LOG.error(logPrefix + "_" + username, e);
        }

        return null;
    }

    public LetvUserDto register_v3(String mobile, String code, Integer isCIBN, String mac, String channel,
                                   Integer broadcastId, CommonParam commonParam) {
        String logPrefix = "register_" + mobile + "_" + mac;

        LetvUserResponse letvUserResponse = this.facadeTpDao.getUserTpDao().register_v3(mobile, code, isCIBN, mac,
                channel, broadcastId);
        if (letvUserResponse != null && letvUserResponse.getBean() != null && letvUserResponse.getBean().size() > 0) {
            String username = letvUserResponse.getBean().get(0).getUsername();
            commonParam.setUsername(username);
            commonParam.setUserId(String.valueOf(letvUserResponse.getBean().get(0).getUid()));
        }
        commonParam.setUserToken(letvUserResponse.getTv_token());
        this.updateUserStatus(commonParam);

        try {
            LetvUserDto response = ParseUtil.copyBeanByResultField(LetvUserDto.class, letvUserResponse);
            LetvUserInfoDto userInfoDto = ParseUtil.copyBeanByResultField(LetvUserInfoDto.class,
                    letvUserResponse.getBean());
            response.setBean(userInfoDto);
            return response;
        } catch (Exception e) {
            this.LOG.error(logPrefix + " return error: ", e);
        }
        return null;
    }

    private void updateRoleCacheStr(Long userId, List<LetvUserRoleDto> roleList) {
        if (userId != null) {
            String key = CacheContentConstants.USER_CACHE_FOR_ROLE_USERID + userId;
            // roleId = (Long) RedisUtil.get(key);
            Long roleId = this.facadeCacheDao.getUserCacheDao().getUserRoleId(userId);
            this.LOG.info("updateRoleCacheStr_" + userId + ",get data[" + roleId + "]");
            if (roleList != null && roleList.size() > 0) {// 接口返回角色，缓存中没有时增加
                if (roleId == null) {
                    LetvUserRoleDto LetvUserRoleDto = roleList.get(0);
                    roleId = LetvUserRoleDto.getRoleid();
                    if (roleId != null && roleId > 0) {
                        // RedisUtil.setex(key,
                        // CacheContentConstants.CACHE_EXP_YEAR_LEN, roleId);
                        this.facadeCacheDao.getUserCacheDao().setUserRoleId(userId, roleId);
                        this.LOG.info("updateRoleCacheStr_,key:USER_CACHE_FOR_ROLE_" + userId + ",set data[" + roleId
                                + "]");
                    }
                }
            } else {// 接口没有返回角色，缓存中存在时删除
                if (roleId != null) {
                    // RedisUtil.del(key);
                    this.facadeCacheDao.getUserCacheDao().deleteUserRoleId(userId);
                    this.LOG.info("updateRoleCacheStr_,key:USER_CACHE_FOR_ROLE_" + userId + ",del data[" + roleId + "]");
                }
            }
        } else {
            this.LOG.error("updateRoleCacheStr_,userId is null !");
        }
    }

    /**
     * set user data to cache
     * @param commonParam
     */
    private void updateUserStatus(CommonParam commonParam) {
        // UserStatus user = (UserStatus) RedisUtil.get(request.getUsername());
        String logPrefix = "updateUserStatus_" + commonParam.getMac() + "_" + commonParam.getUserId();
        UserStatus user = null;
        try {
            user = this.facadeCacheDao.getUserCacheDao().getUserStatus(commonParam);
            if (user == null) {
                user = this.facadeCacheDao.getUserCacheDao().getUserStatusByName(commonParam.getUsername());
            }
        } catch (Exception e) {
            LOG.info(logPrefix + e.getMessage(), e);
        }
        if (user == null) {
            user = new UserStatus();
        }
        String uid = commonParam.getUserId();
        user.setUserName(commonParam.getUsername());
        if (StringUtils.isNotBlank(uid)) {
            user.setUserId(StringUtil.toLong(uid, 0L));
        }
        user.setUserToken(commonParam.getUserToken());
        // if bschannel is mobileTv ,deviceId is not updated. wangshengkai
        // 20161101
        if (!TerminalCommonConstant.TERMINAL_APPLICATION_LETV_MOBILETV.equals(commonParam.getBsChannel())) {
            user.setDeviceId(commonParam.getMac());
        }
        user.setLoginTime(new Date().getTime());

        // to compatible us application
        if (TerminalUtil.isLetvUs(commonParam)) {
            this.facadeCacheDao.getUserCacheDao().setUserStatusByUsername(commonParam.getUsername(), user);
        } else {
            this.facadeCacheDao.getUserCacheDao().setUserStatus(uid, user);
        }
    }

    /**
     * get user roleid from cache
     * @param userId
     * @return
     */
    public Long getLetvUserRoleStr(Long userId) {
        Long roleId = null;
        if (userId != null && userId != 0) {
            String key = CacheContentConstants.USER_CACHE_FOR_ROLE_USERID + userId;
            // roleId = (Long) RedisUtil.get(key);
            roleId = this.facadeCacheDao.getUserCacheDao().getUserRoleId(userId);
            if (roleId == null) {
                LOG.info("getLetvUserRoleStr_ USER_CACHE_FOR_ROLE_" + userId
                        + "has not get data from redis, visit RestTP again!");
                List<LetvUserRoleDto> roleList = this.setRoleListData(userId);
                if (roleList != null && roleList.size() > 0) {
                    roleId = roleList.get(0).getRoleid();
                    // RedisUtil.setex(key,
                    // CacheContentConstants.CACHE_EXP_YEAR_LEN,
                    // roleId);
                    this.facadeCacheDao.getUserCacheDao().setUserRoleId(userId, roleId);
                }
            }
        }
        return roleId;
    }

    private List<LetvUserRoleDto> setRoleListData(Long uid) {
        String preFixLog = "setRoleListData_" + uid;
        List<LetvUserRoleDto> roleList = null;
        if (uid != null) {
            RoleCommonGetTP RoleCommonTP = this.facadeTpDao.getUserTpDao().getRole(uid);
            if (RoleCommonTP != null) {
                if (RoleCommonTP.getStatus() == 1 && RoleCommonTP.getErrorCode() == 0) {
                    List<RoleCommonGetTP.RoleBean> beanList = RoleCommonTP.getBean();
                    if (beanList != null && beanList.size() > 0) {
                        roleList = new ArrayList<LetvUserRoleDto>();
                        // for (RoleBean bean : beanList) {
                        RoleBean bean = beanList.get(0);
                        LetvUserRoleDto LetvUserRoleDto = new LetvUserRoleDto();
                        Long roleid = StringUtil.toLong(bean.getUser_role_id(), Long.valueOf(0));
                        LetvUserRoleDto.setRoleid(roleid);
                        String roleContent = bean.getContent();
                        if (roleContent != null) {
                            RoleContent reRole = JsonUtil.parse(roleContent, RoleContent.class);
                            if (reRole != null) {
                                LetvUserRoleDto.setRoleType(reRole.getRoleType());
                                LetvUserRoleDto.setNickName(reRole.getNickName());
                                LetvUserRoleDto.setBrithday(reRole.getBrithday());
                                LetvUserRoleDto.setGender(reRole.getGender());
                                LetvUserRoleDto.setTimeStart(reRole.getTimeStart());
                                LetvUserRoleDto.setTimeEnd(reRole.getTimeEnd());
                                LetvUserRoleDto.setDuration(reRole.getDuration());
                                LetvUserRoleDto.setSetAge(reRole.getSetAge());
                                LetvUserRoleDto.setTimestamp(reRole.getTimestamp());
                                LOG.info(preFixLog + " loginToken get roleList sucess, userid is ");
                            }
                        }
                        roleList.add(LetvUserRoleDto);
                    }
                    // }
                }
            }
        }
        return roleList;
    }

    public PageResponse<TagAndAlbumListDto> getPlayTagAndAlbumList(Integer page, Integer pageSize, Integer isFull,
                                                                   CommonParam commonParam) {
        List<TagAndAlbumListDto> dataList = null;
        String errorCode = null;
        // 获取用户token
        if (StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstant.USER_NOT_LOGIN;
                LOG.info("getPlayTagAndAlbumList_" + commonParam.getMac() + "_" + commonParam.getUserId()
                        + "[errorCode=" + errorCode + "]: user not login.");
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }

        if (errorCode == null) {
            // get user play list and favorite
            LetvUserPlayListTp resultTp = this.facadeTpDao.getUserTpDao().getPlayTagAndAlbumList(page, pageSize,
                    commonParam);
            if (resultTp == null || !"200".equals(resultTp.getCode()) || resultTp.getData() == null
                    || resultTp.getData().getList() == null) {
                // 接口调用成功,第三方返回code为"200"
                errorCode = ErrorCodeConstants.USER_GET_PLAYTAG_ABLUM_LIST_FAILURE;
                LOG.info("getPlayTagAndAlbumList_" + commonParam.getMac() + "_" + commonParam.getUserId()
                        + "[errorCode=" + errorCode + "]: get user play tag and album list failed.");
            } else {
                List<LetvUserPlayListTp.LetvUserPlayList> tpList = resultTp.getData().getList(); // 用户播放单,收藏,追剧数据
                List<LetvUserPlayList> tagList = new LinkedList<LetvUserPlayList>(); // 追剧数据
                List<LetvUserPlayList> favList = new LinkedList<LetvUserPlayList>(); // 收藏数据
                List<LetvUserPlayList> fullList = new LinkedList<LetvUserPlayList>(); // 追剧收藏合并的数据

                AlbumMysqlTable albumInfo = null;
                for (LetvUserPlayList playList : tpList) {
                    if (playList.getPid() == null) {
                        continue;
                    }
                    // 获取剧集信息
                    albumInfo = albumVideoAccess.getAlbum(StringUtil.toLong(playList.getPid()),
                            commonParam);
                    if (albumInfo == null) {
                        continue;
                    }
                    // 客户端优先取400x300,如果没有取300x400
                    String img = albumInfo.getPic(400, 300);
                    if (img == null || "".equals(img)) {
                        img = albumInfo.getPic(300, 400);
                    }
                    if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                        playList.setChargeInfo(JumpUtil.genChargeInfos(albumInfo.getPay_platform()));
                    }
                    playList.setPic(img);
                    playList.setTitle(albumInfo.getName_cn());
                    playList.setSubTitle(albumInfo.getSub_title());
                    playList.setFollownum(albumInfo.getFollownum());
                    playList.setEpisodes(albumInfo.getEpisode());
                    playList.setImg_300X400(albumInfo.getPic(300, 400));
                    // 设置视频状态
                    if (albumInfo.getCategory() == Category.FILM) { // 电影
                        if (albumInfo.getAlbum_type() == VideoType.ZHENG_PIAN) {
                            // 正片
                            playList.setVideoStatus("");
                        } else {
                            playList.setVideoStatus(VideoCommonUtil.getVideoOrAlbumEpisodeText(3, null, null,
                                    commonParam.getLangcode()));
                        }
                    } else if (albumInfo.getCategory() == Category.TV || albumInfo.getCategory() == Category.CARTOON
                            || Category.TEACH == albumInfo.getCategory()) { // 电视剧或者卡通、教育
                        if (albumInfo.getIs_end() == 0) { // 未完结
                            if ((albumInfo.getFollownum() == null || albumInfo.getFollownum() == 0)
                                    && albumInfo.getAlbum_type() != VideoType.ZHENG_PIAN) {
                                // 跟播集数为空和为0处理逻辑一样，尚没有下一集
                                playList.setVideoStatus(VideoCommonUtil.getVideoOrAlbumEpisodeText(3, null, null,
                                        commonParam.getLangcode()));
                            } else {
                                playList.setVideoStatus(VideoCommonUtil.getVideoOrAlbumEpisodeText(1,
                                        albumInfo.getCategory(), String.valueOf(albumInfo.getFollownum()),
                                        commonParam.getLangcode()));
                            }
                        } else {
                            // 已完结
                            playList.setVideoStatus(VideoCommonUtil.getVideoOrAlbumEpisodeText(2,
                                    albumInfo.getCategory(), String.valueOf(albumInfo.getFollownum()),
                                    commonParam.getLangcode()));
                        }
                    } else if (Category.VARIETY == albumInfo.getCategory() && albumInfo.getFollownum() != null) { // 综艺
                        playList.setVideoStatus(VideoCommonUtil.getVideoOrAlbumEpisodeTextV2(2,
                                albumInfo.getCategory(), albumInfo.getFollownum() + "", commonParam.getLangcode()));
                    } else {
                        // 其他专辑类型
                        playList.setVideoStatus("");
                    }

                    if (isFull == 0) {
                        // 不合并追剧和收藏数据
                        if (playList.getFromtype() == 1) { // 收藏数据
                            if (favList.size() < pageSize) {
                                favList.add(playList);
                            }
                        } else if (playList.getFromtype() == 2) { // 追剧数据
                            if (tagList.size() < pageSize) {
                                tagList.add(playList);
                            }
                        }
                    } else {
                        // 合并追剧和收藏数据
                        fullList.add(playList);
                    }
                }

                // 数据返回dto
                dataList = new ArrayList<TagAndAlbumListDto>();
                if (tagList.size() > 0) {
                    TagAndAlbumListDto dto = new TagAndAlbumListDto();
                    dto.setListType(2);
                    dto.setRealChannelCode("");
                    dto.setTagName(MessageUtils.getMessage(UserConstants.USER_FAVORITE_ALBUM, commonParam.getLangcode()));
                    dto.setItems(tagList);
                    dataList.add(dto);
                }

                if (favList.size() > 0) {
                    TagAndAlbumListDto dto = new TagAndAlbumListDto();
                    dto.setListType(1);
                    dto.setRealChannelCode("");
                    dto.setTagName(MessageUtils.getMessage(UserConstants.USER_FAVORITE_COLLECT,
                            commonParam.getLangcode()));
                    dto.setItems(favList);
                    dataList.add(dto);
                }

                if (fullList.size() > 0) {
                    TagAndAlbumListDto dto = new TagAndAlbumListDto();
                    dto.setListType(1);
                    dto.setRealChannelCode("");
                    dto.setTagName(MessageUtils.getMessage(UserConstants.USER_FAVORITE_ALBUM_COLLECT,
                            commonParam.getLangcode()));
                    dto.setItems(fullList);
                    dataList.add(dto);
                }
            }
        }

        PageResponse<TagAndAlbumListDto> response = new PageResponse<TagAndAlbumListDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(dataList);
        }
        return response;
    }

    public Response<Boolean> addPlayList(Long albumId, Integer platForm, Integer fromType, CommonParam commonParam) {
        boolean data = false;
        int resultStatus = 0;
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
                LOG.info("addPlayList_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user not login.");
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }

        if (errorCode == null) {
            // 获取专辑信息
            AlbumMysqlTable album = albumVideoAccess.getAlbum(albumId, commonParam);
            if (album == null) {
                errorCode = ErrorCodeConstants.USER_ADD_PLAYLIST_FALIURE;
                LOG.error("addPlayList_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: get album[id=" + albumId + "] info null.");
            } else {
                Integer categoryId = album.getCategory();
                // 获取专辑下的所有视频,不限制播放空方
                List<VideoMysqlTable> videoList = albumVideoAccess.getVideoList(albumId,
                        VideoTpConstant.QUERY_TYPE_ALL, -1, 1, 1, commonParam);
                if (videoList == null || videoList.size() == 0) {
                    errorCode = ErrorCodeConstants.USER_ADD_PLAYLIST_FALIURE;
                    LOG.error("addPlayList_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                            + errorCode + "]: get video under album[id=" + albumId + "] info null.");
                } else {
                    Long videoId = videoList.get(0).getId();
                    PlayListCommonTp addTp = this.facadeTpDao.getUserTpDao().addPlayList(albumId, videoId, categoryId,
                            platForm, fromType, commonParam);
                    if (addTp != null && "200".equals(addTp.getCode())) { // 添加成功,第三方返回code为"200"
                        data = true;
                        resultStatus = 1;
                    }
                }
            }
        }

        Response<Boolean> response = new Response<Boolean>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
            response.setResultStatus(resultStatus);
        }

        return response;
    }

    public Response<CheckPlaylistDto> checkPlaylist(Long albumid, CommonParam commonParam) {
        CheckPlaylistDto dto = new CheckPlaylistDto();
        String logPrefix = "checkPlaylist_" + commonParam.getMac() + "_" + commonParam.getUserId();
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
                LOG.info("checkPlaylist_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user not login.");
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }

        if (errorCode == null) {
            CheckPlaylistTp checkTp = this.facadeTpDao.getUserTpDao().checkPlaylist(albumid, commonParam);
            if (checkTp == null || !"200".equals(checkTp.getCode())) {
                // 第三方成功返回code200
                errorCode = ErrorCodeConstants.USER_CHECK_PLAYLIST_FAILURE;
                LOG.warn("checkPlaylist_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: check playlist failure");
            } else {
                PlayList playList = checkTp.getData();
                dto.setCode(checkTp.getCode());
                dto.setFromtype(playList.getFromtype());
                dto.setId(playList.getId());
            }
        }
        Response<CheckPlaylistDto> response = new Response<CheckPlaylistDto>();
        if (errorCode == null) {
            response.setResultStatus(1);
            response.setData(dto);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    /**
     * 删除用户单条播放记录
     * @return
     */
    public Response<Boolean> deletePlaylist(Long favid, CommonParam commonParam) {
        Response<Boolean> response = new Response<Boolean>();
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
                LOG.info("deletePlaylist_" + commonParam.getMac() + "_" + commonParam.getLangcode() + "[errorCode="
                        + errorCode + "]: user not login.");
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }

        if (errorCode == null) {
            PlayListCommonTp commonTp = this.facadeTpDao.getUserTpDao().deletePlaylist(favid, commonParam);
            if (commonTp != null && "200".equals(commonTp.getCode())) { // 删除成功,第三方返回200
                response.setResultStatus(1);
                response.setData(true);
            } else {
                LOG.error("deletePlaylist_" + commonParam.getMac() + "_" + commonParam.getLangcode() + "[errorCode="
                        + ErrorCodeConstants.USER_DELETE_PLAYLIST_FAILURE + "]: delete playlist [favid=" + favid
                        + "] failed.");
                response.setResultStatus(1);
                response.setData(false);
            }
        } else {
            ErrorCodeCommonUtil
                    .setErrorResponse(response, ErrorCodeConstants.USER_NOT_LOGIN, commonParam.getLangcode());
        }

        return response;
    }

    /**
     * 校验mac是否合法
     * @param mac
     * @return
     */
    private boolean isMacValid(String mac) {
        // TODO 校验mac合法性
        String regex = "[A-F\\d]{2}[A-F\\d]{2}[A-F\\d]{2}[A-F\\d]{2}[A-F\\d]{2}[A-F\\d]{2}";
        if (mac == null) {
            return false;
        }
        mac = mac.replaceAll("[:-]", "").toUpperCase();
        return mac.matches(regex);
    }

    /**
     * 异地登录判断
     * @param commonParam
     * @return
     */
    public Boolean checkLogin(CommonParam commonParam) {
        boolean result = false;
        String mac = commonParam.getMac();
        if (StringUtils.isNotEmpty(commonParam.getUserId()) && StringUtils.isNotEmpty(mac)) {
            result = true;
            // 移动TV暂时仍采用letv,用bsChannel特殊逻辑判断 wangshengkai 20161101
            if (!this.unlimitedUserId(commonParam.getUserId())
                    && !TerminalCommonConstant.TERMINAL_APPLICATION_LETV_MOBILETV.equals(commonParam.getBsChannel())) { // 不在用户id白名单内,则验证异地登录
                // 根据用户名取用户对象
                UserStatus user = this.getUserStatus(commonParam);
                if (user != null && !mac.equals(user.getDeviceId())) {
                    // 异地登录
                    MULTI_LOGIN.info("multilogin uid=" + user.getUserId() + ",username=" + commonParam.getUsername()
                            + ",mac=" + mac + ",dbMac=" + user.getDeviceId() + ",bsChannel="
                            + commonParam.getBsChannel() + ",app=" + commonParam.getTerminalApplication() + ",ip="
                            + commonParam.getClientIp());
                    ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.USER_BEEN_LOGOUT,
                            commonParam.getLangcode());
                }
            } else { // 打印log
                MULTI_LOGIN.info("macwhitelist uid=" + commonParam.getUserId() + ",mac=" + mac + ",bsChannel="
                        + commonParam.getBsChannel() + ",app=" + commonParam.getTerminalApplication() + ",ip="
                        + commonParam.getClientIp());
            }
        }
        return result;
    }

    /**
     * 用户id白名单---不限制用户登录设备数
     * @param userId
     */
    public boolean unlimitedUserId(String userId) {
        boolean flag = Boolean.FALSE;
        if (StringUtil.isNotBlank(userId)) {
            this.updateMutilMachineLoginUids();
            if (VideoUtil.MUTIL_MACHINE_LOGIN_USERID_WHITE_SET != null
                    && VideoUtil.MUTIL_MACHINE_LOGIN_USERID_WHITE_SET.contains(userId)) {
                flag = Boolean.TRUE;
            }
        }
        return flag;
    }

    /**
     * 更新允许登录多台设备的账号map
     * @return
     */
    public boolean updateMutilMachineLoginUids() {
        boolean result = false;
        String logPrefix = "updateMutilMachineLoginUids_";
        if (VideoUtil.MUTIL_MACHINE_LOGIN_USERID_WHITE_SET == null
                || ((System.currentTimeMillis() - MUTIL_MACHINE_LOGIN_UID_LASTUPDATE_TIME) > MUTIL_MACHINE_LOGIN_UID_UPDATE_INTERVAL)) {
            // 更新数据
            if (this.MUTIL_MACHINE_LOGIN_UID_UPDATE_Lock.tryLock()) {
                LOG.info(logPrefix + ": locked.");
                try {
                    VideoUtil.MUTIL_MACHINE_LOGIN_USERID_WHITE_SET = this.facadeTpDao.getStaticTpDao().getUserIdSet();
                    MUTIL_MACHINE_LOGIN_UID_LASTUPDATE_TIME = System.currentTimeMillis();
                    result = true;
                    LOG.info(logPrefix + "update mutil machine login uids success");
                } catch (Exception e) {
                    LOG.error(logPrefix + "update mutil machine login uids error", e);
                } finally {
                    this.MUTIL_MACHINE_LOGIN_UID_UPDATE_Lock.unlock();
                    LOG.info(logPrefix + ": unlocked.");
                }
            }
        }
        return result;
    }

    public Boolean checkIsLogin(CommonParam commonParam) {
        boolean result = false;
        if (StringUtils.isNotEmpty(commonParam.getUserId())) {
            result = true;
        } else if (getUserStatus(commonParam) != null) {
            result = true;
        }

        return result;
    }

    /**
     * 将多设备登录最近的更新时间设置为0L
     */
    public void clearMutilMachineLoginUidLastUpdateTime() {
        this.MUTIL_MACHINE_LOGIN_UID_LASTUPDATE_TIME = 0L;
    }

    public Integer getPlayTimeType(PlayLogListDto dto) {
        Integer playTimeType;
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        try {
            date = dateFormat.parse(strDate);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        int oneWeekDelta = 0 - dayOfWeek;
        calendar.add(Calendar.DATE, oneWeekDelta);
        Date lastWeekDate = calendar.getTime();
        calendar.add(Calendar.DATE, -7);
        Date ealierDate = calendar.getTime();
        SimpleDateFormat itemDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date itemDate = new Date();
        try {
            itemDate = itemDateFormat.parse(dto.getLastTime());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (itemDate.getTime() > lastWeekDate.getTime()) {
            playTimeType = 1; // 最近
        } else if (itemDate.getTime() > ealierDate.getTime() && itemDate.getTime() < lastWeekDate.getTime()) {
            playTimeType = 2; // 上周
        } else {
            playTimeType = 3; // 更久以前
        }

        return playTimeType;
    }

    /**
     * 获取播放记录
     * @return
     */
    public PageCommonResponse<PlayLogListDto> getPlaylog(Integer page, Integer pageSize, Integer intervalTime,
                                                         Long roleid, Integer type, Integer from, String validDate, CommonParam commonParam) {
        String errorCode = null;
        PageControl<PlayLogListDto> pageControl = new PageControl<PlayLogListDto>();
        if (StringUtils.isEmpty(commonParam.getUserId()) || StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }
        // 调用第三方接口获取播放记录
        if (errorCode == null) {
            String region = null; // tv版不区分地区
            APPID appid = APPID.getAppIdNew(commonParam.getTerminalApplication());
            PlayLogTp playTp = this.facadeTpDao.getUserTpDao().getPlaylogList(page, pageSize, intervalTime, roleid,
                    type, appid, from, region, commonParam);
            if (playTp == null || !"200".equalsIgnoreCase(playTp.getCode()) || playTp.getData() == null) {
                // 第三方成功返回code200
                errorCode = ErrorCodeConstants.USER_GET_PLAYLOG_FAILURE;
                LOG.warn("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: get Playlog failed.");
            } else {
                List<PlayLogInfo> playlogList = playTp.getData().getItems();
                List<PlayLogListDto> dtoList = new LinkedList<PlayLogListDto>();

                // 2016-06-30国广版只过滤无版权和播控不通过的
                boolean isCibn = (commonParam.getBroadcastId() != null && CommonConstants.CIBN == commonParam
                        .getBroadcastId());

                if (playlogList != null) {
                    long dataMargeStartTime = System.currentTimeMillis();

                    /**
                     * 批量获取album和video
                     */
                    List<Long> aids = new ArrayList<>();
                    List<Long> vids = new ArrayList<>();
                    // 收费专辑ID列表
                    List<Long> payAlbumIds = new ArrayList<>();
                    for (PlayLogInfo playlogInfo : playlogList) {
                        if (playlogInfo.getPid() != null) {
                            aids.add(playlogInfo.getPid());
                        }
                        if (playlogInfo.getVid() != null) {
                            vids.add(playlogInfo.getVid());
                        }
                    }
                    Map<String, AlbumMysqlTable> albums = albumVideoAccess.getAlbums(aids,
                            commonParam);
                    Map<String, VideoMysqlTable> videos = albumVideoAccess.getVideos(vids,
                            commonParam);
                    aids.clear();
                    vids.clear();

                    for (PlayLogInfo playlogInfo : playlogList) {
                        /* 过滤所有非正片 */
                        if (StringUtils.isNotBlank(playlogInfo.getVideoType())
                                && 180001 != Integer.parseInt(playlogInfo.getVideoType())) {
                            continue;
                        }
                        Long vid = playlogInfo.getVid();
                        /* 2015-08-14，修改非正片的判断逻辑 */
                        if (StringUtils.isNotBlank(playlogInfo.getVideoType())
                                && StringUtils.isNotBlank(playlogInfo.getCid())) {
                            Integer videoType = Integer.parseInt(playlogInfo.getVideoType());
                            Integer cid = Integer.parseInt(playlogInfo.getCid());
                            if (!VideoCommonUtil.isPositive(1, cid, null, videoType)) {
                                // 非正片不显示
                                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + ",This video[" + vid + "] is not positive.");
                                continue;
                            }
                        }

                        Long pid = playlogInfo.getPid();
                        AlbumMysqlTable album = null;
                        if (pid != null) {
                            // 获取专辑信息 => TODO: cache和db可走批处理，redis-pipeline
                            // album =
                            // albumVideoAccess.getAlbum(pid,
                            // commonParam);
                            if (albums != null && pid != null) {
                                album = albums.get(Long.toString(pid));
                            }
                            if (album == null) {
                                // pageControl.setCount(pageControl.getCount() -
                                // 1);
                                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + "Illegal albumid [albumid=" + pid + "]");
                            }
                        }

                        VideoMysqlTable video = null;
                        if (vid != null) {
                            // 获取视频信息 => TODO: cache和db可走批处理，redis-pipeline
                            // video =
                            // albumVideoAccess.getVideo(vid,
                            // commonParam);
                            if (videos != null && vid != null) {
                                video = videos.get(Long.toString(vid));
                            }
                            if (video == null) {
                                // pageControl.setCount(pageControl.getCount() -
                                // 1);
                                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + "Illegal videoid [videoid=" + vid + "]");
                                if (isCibn) {
                                    continue;
                                }
                            }
                        }
                        /**
                         * 检查时候TV有播放版权 1. 播控方cntv未上线，tv无版权 2. 自行设置的tv版权信息
                         */
                        boolean isTV = true; // 默认有版权
                        if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
                            // 判断播控版权
                            if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN
                                    && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                                if (album != null && album.getCibn() != null
                                        && album.getCibn() != UserConstants.BROADCAST_ONLINE_STATUS) {
                                    continue;
                                }
                            } else {
                                if (album != null && album.getCntv() != null
                                        && album.getCntv() != UserConstants.BROADCAST_ONLINE_STATUS) {
                                    // 播控方cntv未上线, TV无版权
                                    LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                            + ". This album[" + pid + "] CNTV not online and TV not has no copyright.");
                                    continue;
                                }
                            }
                        }
                        if (isTV) {
                            // 播控方版权过了，判断专辑版权
                            if (album != null && !album.isTVCopayright()) { // 专辑信息中设定的tv版权信息
                                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + ". This album[" + pid + "] has no TV copyright.");
                                continue;
                            } else {
                                String playPlatForm = playlogInfo.getPlayPlatform();
                                if (StringUtils.isBlank(playPlatForm) || !playPlatForm.contains("420007")) {
                                    LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                            + ". This album[" + pid + "] has no TV playplatform.");
                                    continue;
                                }
                            }
                        }

                        PlayLogListDto dto = new PlayLogListDto(album, video, playlogInfo, isTV, commonParam);
                        dto.setRoleid(roleid == null ? 0 : roleid);
                        dto.setPlayTimeType(getPlayTimeType(dto));
                        dtoList.add(dto);
                        // 如果是收费则加入payAlbumIds
                        if (album != null) {
                            boolean albumIsCharge = album.isPay(commonParam.getP_devType());
                            if (albumIsCharge) {
                                payAlbumIds.add(album.getId());
                            }
                        }
                    }

                    // 如果用户登录并且非会员，才获取试看状态
                    if (validDate != null
                            && commonParam.getUserId() != null
                            && !playService.isVip(validDate,
                            Long.valueOf(commonParam.getUserId()), commonParam.getDeviceKey())) {
                        // 获取收费专辑payAlbumIds试看时长
                        Map<Long, Long> albumsTryPlayTime = this.getAlbumsTryPlayTime(payAlbumIds, commonParam);
                        if (albumsTryPlayTime != null) {
                            for (PlayLogListDto playLogListDto : dtoList) {
                                if (albumsTryPlayTime.containsKey(playLogListDto.getPid())) {
                                    Long tryPlayTime = albumsTryPlayTime.get(playLogListDto.getPid());
                                    if (tryPlayTime != null) {
                                        long tryPlayTimeInMillis = tryPlayTime * 1000;
                                        playLogListDto.setTryPlayTime(tryPlayTimeInMillis);
                                        // 播放时间距离试看结束时间1000毫秒之内算试看完成，否则试看中
                                        if (tryPlayTimeInMillis - playLogListDto.getPlayTime() > 1000) {
                                            playLogListDto.setTryPlayType(TRY_LOOK);
                                        } else {
                                            playLogListDto.setTryPlayType(TRY_LOOK_COMPLETE);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    pageControl.setList(dtoList);
                    pageControl.setCount(playTp.getData().getTotal()); // 总数为用户中心返回的数量
                    pageControl.setCurrentPage(page);
                    pageControl.setPageSize(pageSize);

                    int pageCount = pageControl.getCount() / pageControl.getPageSize();
                    if (pageControl.getCount() % pageControl.getPageSize() > 0) {
                        pageCount += 1;
                    }
                    pageControl.setPageCount(pageCount);
                    // 2016-04-08添加日志监控，开启一段时间，分析后再删除
                    long dataMargeEndTime = System.currentTimeMillis();
                    if (dataMargeEndTime - dataMargeStartTime > 500) {
                        LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + ": data marge cost " + (dataMargeEndTime - dataMargeStartTime) + " ms");
                    }
                }
            }
        }

        PageCommonResponse<PlayLogListDto> response = new PageCommonResponse<PlayLogListDto>();
        if (errorCode == null) {
            response = new PageCommonResponse<PlayLogListDto>(pageControl);
            response.setResultStatus(1);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    /**
     * 根据专辑ID列表，获取专辑试看时长
     * @param albumIds
     * @param commonParam
     * @return
     */
    private Map<Long, Long> getAlbumsTryPlayTime(List<Long> albumIds, CommonParam commonParam) {
        Map<Long, Long> userTryPlayTimeMap = null;
        // 获取chargeInfo，解析chargeInfo取得专辑试看时长
        Map<Long, List<ChargeInfo>> chargeInfoMap = this.getChargeInfos(albumIds, commonParam);
        if (chargeInfoMap != null) {
            userTryPlayTimeMap = new HashMap<>();
            for (Map.Entry<Long, List<ChargeInfo>> chargeInfoEntry : chargeInfoMap.entrySet()) {
                Long albumId = chargeInfoEntry.getKey();
                List<ChargeInfo> chargeInfos = chargeInfoEntry.getValue();
                if (chargeInfos != null && chargeInfos.size() > 0) {
                    // 按端获取端ChargeInfo信息
                    ChargeInfo chargeInfo = MmsDataUtil.getChargeInfoFromPayDetail(commonParam.getP_devType(),
                            chargeInfos);
                    try {
                        Long tryLookTime = Long.valueOf(chargeInfo.getTryLookTime());
                        userTryPlayTimeMap.put(albumId, tryLookTime);
                    } catch (NumberFormatException e) {
                        LOG.error("get try play type error");
                        continue;
                    }
                }

            }
        }
        return userTryPlayTimeMap;
    }

    /**
     * 先从缓存中通过mget批量取，缓存粒度<key=albumId,value=List<ChargeInfo>>
     * 注：缓存中存的是全端List<ChargeInfo>信息
     * 如果缓存没有，则批量调用上游接口将未被缓存的album取一遍全端List<ChargeInfo>
     * 由于上游接口有album个数限制，所以采用分批次调用，最后再存入缓存
     * @param albumIds
     * @param commonParam
     * @return
     */
    private Map<Long, List<ChargeInfo>> getChargeInfos(List<Long> albumIds, CommonParam commonParam) {
        Map<Long, List<ChargeInfo>> allTerminalChargeInfoMap = null;
        if (albumIds != null && albumIds.size() > 0) {
            List<Long> unCachedAlbumIds = new ArrayList<>();
            // 先取缓存中的全端数据
            allTerminalChargeInfoMap = this.facadeCacheDao.getVideoCacheDao().getAlbumsChargeInfos(albumIds);
            if (allTerminalChargeInfoMap != null) {
                for (Long albumId : albumIds) {
                    List<ChargeInfo> chargeInfos = allTerminalChargeInfoMap.get(albumId);
                    if (chargeInfos == null) {
                        // 如果缓存没有，记录下来，稍后批量取上游接口取一次，再放入缓存中
                        unCachedAlbumIds.add(albumId);
                    }
                }
            } else {
                // 处理缓存异常情况
                unCachedAlbumIds.addAll(albumIds);
            }
            // 如果有缓存没取到的，则调用上游接口取"分批""批量"获取（因为上游有单次albumid个数限制)
            if (unCachedAlbumIds.size() > 0) {
                int queueLength = 50;
                int queueNum = unCachedAlbumIds.size() / queueLength + 1;
                for (int i = 0; i < queueNum; i++) {
                    int subListStartIndex = i * queueLength;
                    int subListEndIndex;
                    // 如果是最后一批
                    if (i == (queueNum - 1)) {
                        subListEndIndex = unCachedAlbumIds.size();
                    } else {
                        subListEndIndex = queueLength;
                    }
                    List<Long> tempAlbumIds = unCachedAlbumIds.subList(subListStartIndex, subListEndIndex);
                    String unCachedAlbumIdsStr = StringUtils.join(tempAlbumIds, ",");
                    BossTpResponse<BossChargeInfoData> bossChargeInfoDataBossTpResponse = this.facadeTpDao
                            .getVipTpDaoV2().mGetChargeInfo(unCachedAlbumIdsStr, null,
                                    CommonConstants.PAY_PLATFORM_TYPE.getAllPlatforms(), "getChargeInfo", commonParam);
                    // 解析返回,填充unCachedChargeInfoMap
                    if (bossChargeInfoDataBossTpResponse != null && bossChargeInfoDataBossTpResponse.getData() != null
                            && bossChargeInfoDataBossTpResponse.getData().getAlbums() != null) {
                        Map<Long, List<ChargeInfo>> unCachedAllTerminalChargeInfoMap = new HashMap<>();
                        List<VipObjInfo> albums = bossChargeInfoDataBossTpResponse.getData().getAlbums();
                        for (VipObjInfo vipObjInfo : albums) {
                            if (vipObjInfo != null) {
                                try {
                                    Long albumId = Long.valueOf(vipObjInfo.getPid());
                                    List<ChargeInfo> chargeInfos = vipObjInfo.getTerminalCharges();
                                    if (chargeInfos != null) {
                                        unCachedAllTerminalChargeInfoMap.put(albumId, chargeInfos);
                                    }
                                } catch (Exception e) {
                                    LOG.error("parse bossChargeInfoDataBossTpResponse to chargeinfo error");
                                    continue;
                                }
                            }
                        }
                        // 将接口取到的ChargeInfo缓存起来
                        if (unCachedAllTerminalChargeInfoMap != null && !unCachedAllTerminalChargeInfoMap.isEmpty()) {
                            // 合并数据
                            if (unCachedAllTerminalChargeInfoMap != null) {
                                allTerminalChargeInfoMap.putAll(unCachedAllTerminalChargeInfoMap);
                            } else {
                                allTerminalChargeInfoMap = new HashMap<>(unCachedAllTerminalChargeInfoMap);
                            }
                            this.facadeCacheDao.getVideoCacheDao().setAlbumsChargeInfos(
                                    unCachedAllTerminalChargeInfoMap);
                        }
                    }
                }
            }
        }
        return allTerminalChargeInfoMap;
    }

    public PeriodTvLog logGroupingByTime(List<PlayLogListDto> dtoList) {
        PeriodTvLog<PlayLogListDto> periodTvLog = new PeriodTvLog<PlayLogListDto>();
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        try {
            date = dateFormat.parse(strDate);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        int oneWeekDelta = 0 - dayOfWeek;
        calendar.add(Calendar.DATE, oneWeekDelta);
        Date lastWeekDate = calendar.getTime();
        calendar.add(Calendar.DATE, -7);
        Date ealierDate = calendar.getTime();
        SimpleDateFormat itemDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (PlayLogListDto p : dtoList) {
            Date itemDate = new Date();
            try {
                itemDate = itemDateFormat.parse(p.getLastTime());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (itemDate.getTime() > lastWeekDate.getTime()) {
                periodTvLog.getRecentlyItems().add(p);
            } else if (itemDate.getTime() > ealierDate.getTime() && itemDate.getTime() < lastWeekDate.getTime()) {
                periodTvLog.getLastWeekItems().add(p);
            } else {
                periodTvLog.getEalierItems().add(p);
            }
        }

        return periodTvLog;
    }

    public PagePeriodResponse<PeriodTvLog> getPeriodPlaylog(Integer page, Integer pageSize, Integer intervalTime,
                                                            Long roleid, Integer type, Integer from, CommonParam commonParam) {
        String errorCode = null;
        PagePeriodControl<PeriodTvLog> pagePeriodControl = new PagePeriodControl<>();
        PeriodTvLog<PlayLogListDto> periodTvLog = new PeriodTvLog<PlayLogListDto>();
        if (StringUtils.isEmpty(commonParam.getUserId()) || StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }
        // 调用第三方接口获取播放记录
        if (errorCode == null) {
            String region = null; // tv版不区分地区
            APPID appid = APPID.getAppId(commonParam.getTerminalApplication());
            PlayLogTp playTp = this.facadeTpDao.getUserTpDao().getPlaylogList(page, pageSize, intervalTime, roleid,
                    type, appid, from, region, commonParam);
            if (playTp == null || !"200".equalsIgnoreCase(playTp.getCode()) || playTp.getData() == null) {
                // 第三方成功返回code200
                errorCode = ErrorCodeConstants.USER_GET_PLAYLOG_FAILURE;
                LOG.warn("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: get Playlog failed.");
            } else {
                List<PlayLogInfo> playlogList = playTp.getData().getItems();
                List<PlayLogListDto> dtoList = new LinkedList<PlayLogListDto>();

                // 2016-06-30国广版只过滤无版权和播控不通过的
                boolean isCibn = (commonParam.getBroadcastId() != null && CommonConstants.CIBN == commonParam
                        .getBroadcastId());

                if (playlogList != null) {
                    long dataMargeStartTime = System.currentTimeMillis();

                    /**
                     * 批量获取album和video
                     */
                    List<Long> aids = new ArrayList<>();
                    List<Long> vids = new ArrayList<>();
                    for (PlayLogInfo playlogInfo : playlogList) {
                        if (playlogInfo.getPid() != null) {
                            aids.add(playlogInfo.getPid());
                        }
                        if (playlogInfo.getVid() != null) {
                            vids.add(playlogInfo.getVid());
                        }
                    }
                    Map<String, AlbumMysqlTable> albums = albumVideoAccess.getAlbums(aids,
                            commonParam);
                    Map<String, VideoMysqlTable> videos = albumVideoAccess.getVideos(vids,
                            commonParam);
                    aids.clear();
                    vids.clear();

                    for (PlayLogInfo playlogInfo : playlogList) {
                        Long vid = playlogInfo.getVid();
                        /* 2015-08-14，修改非正片的判断逻辑 */
                        if (StringUtils.isNotBlank(playlogInfo.getVideoType())
                                && StringUtils.isNotBlank(playlogInfo.getCid())) {
                            Integer videoType = Integer.parseInt(playlogInfo.getVideoType());
                            Integer cid = Integer.parseInt(playlogInfo.getCid());
                            if (!VideoCommonUtil.isPositive(1, cid, null, videoType)) {
                                // 非正片不显示
                                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + ",This video[" + vid + "] is not positive.");
                                continue;
                            }
                        }

                        Long pid = playlogInfo.getPid();
                        AlbumMysqlTable album = null;
                        if (pid != null) {
                            // 获取专辑信息 => TODO: cache和db可走批处理，redis-pipeline
                            // album =
                            // albumVideoAccess.getAlbum(pid,
                            // commonParam);
                            if (albums != null && pid != null) {
                                album = albums.get(Long.toString(pid));
                            }
                            if (album == null) {
                                // pageControl.setCount(pageControl.getCount() -
                                // 1);
                                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + "Illegal albumid [albumid=" + pid + "]");
                            }
                        }

                        VideoMysqlTable video = null;
                        if (vid != null) {
                            // 获取视频信息 => TODO: cache和db可走批处理，redis-pipeline
                            // video =
                            // albumVideoAccess.getVideo(vid,
                            // commonParam);
                            if (videos != null && vid != null) {
                                video = videos.get(Long.toString(vid));
                            }
                            if (video == null) {
                                // pageControl.setCount(pageControl.getCount() -
                                // 1);
                                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + "Illegal videoid [videoid=" + vid + "]");
                                if (isCibn) {
                                    continue;
                                }
                            }
                        }
                        /**
                         * 检查时候TV有播放版权 1. 播控方cntv未上线，tv无版权 2. 自行设置的tv版权信息
                         */
                        boolean isTV = true; // 默认有版权
                        if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
                            // 判断播控版权
                            if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN
                                    && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                                if (album != null && album.getCibn() != null
                                        && album.getCibn() != UserConstants.BROADCAST_ONLINE_STATUS) {
                                    continue;
                                }
                            } else {
                                if (album != null && album.getCntv() != null
                                        && album.getCntv() != UserConstants.BROADCAST_ONLINE_STATUS) {
                                    // 播控方cntv未上线, TV无版权
                                    LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                            + ". This album[" + pid + "] CNTV not online and TV not has no copyright.");
                                    continue;
                                }
                            }
                        }
                        if (isTV) {
                            // 播控方版权过了，判断专辑版权
                            if (album != null && !album.isTVCopayright()) { // 专辑信息中设定的tv版权信息
                                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + ". This album[" + pid + "] has no TV copyright.");
                                continue;
                            } else {
                                String playPlatForm = playlogInfo.getPlayPlatform();
                                if (StringUtils.isBlank(playPlatForm) || !playPlatForm.contains("420007")) {
                                    LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                            + ". This album[" + pid + "] has no TV playplatform.");
                                    continue;
                                }
                            }
                        }

                        PlayLogListDto dto = new PlayLogListDto(album, video, playlogInfo, isTV, commonParam);
                        dto.setRoleid(roleid == null ? 0 : roleid);
                        dtoList.add(dto);
                    }

                    // periodTvLog.setEalierItems(dtoList);

                    periodTvLog = this.logGroupingByTime(dtoList);
                    pagePeriodControl.setCount(playTp.getData().getTotal()); // 总数为用户中心返回的数量
                    pagePeriodControl.setCurrentPage(page);
                    pagePeriodControl.setPageSize(pageSize);

                    int pageCount = pagePeriodControl.getCount() / pagePeriodControl.getPageSize();
                    if (pagePeriodControl.getCount() % pagePeriodControl.getPageSize() > 0) {
                        pageCount += 1;
                    }
                    pagePeriodControl.setPageCount(pageCount);
                    // 2016-04-08添加日志监控，开启一段时间，分析后再删除
                    long dataMargeEndTime = System.currentTimeMillis();
                    if (dataMargeEndTime - dataMargeStartTime > 500) {
                        LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + ": data marge cost " + (dataMargeEndTime - dataMargeStartTime) + " ms");
                    }
                }
            }
        }
        pagePeriodControl.setItems(periodTvLog);
        PagePeriodResponse<PeriodTvLog> response = new PagePeriodResponse<PeriodTvLog>();
        if (errorCode == null) {
            response = new PagePeriodResponse<PeriodTvLog>(pagePeriodControl);
            response.setResultStatus(1);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    public NewPageResponse<PlayRecordDto> getUserPlayRecord(Integer page, Integer pageSize, Integer intervalTime,
                                                            Long roleid, Integer type, Integer from, CommonParam commonParam) {
        NewPageResponse<PlayRecordDto> response = null;
        String errorCode = null;

        if (StringUtils.isEmpty(commonParam.getUserId()) || StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("getUserPlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getUserToken() + "[errorCode=" + errorCode + "]: user not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (errorCode == null) {
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) { // 印度或美国levidi
                response = parsePlayRecord4Levidi(roleid, page, pageSize, intervalTime, type, from, commonParam);
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_TVLIVE.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {// TVLIVE的逻辑
                response = parsePlayRecord4Tvlive(roleid, page, pageSize, intervalTime, type, from, commonParam);
            } else {// 其他应用暂时无特殊逻辑
                response = parsePlayRecord(roleid, page, pageSize, intervalTime, type, from, commonParam);
            }
        } else {
            response = new NewPageResponse<PlayRecordDto>();
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    private NewPageResponse<PlayRecordDto> parsePlayRecord4Levidi(Long roleid, Integer page, Integer pageSize,
                                                                  Integer intervalTime, Integer type, Integer from, CommonParam commonParam) {
        NewPageResponse<PlayRecordDto> response = new NewPageResponse<PlayRecordDto>();
        String errorCode = null;

        if (type == null) {// levidi或le，type默认0
            type = 0;
        }
        APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
        String region = commonParam.getWcode(); // levidi区分地区
        PlayLogTp playTp = this.facadeTpDao.getUserTpDao().getPlaylogList(page, pageSize, intervalTime, roleid, type,
                appId, from, region, commonParam);
        if (playTp == null || !"200".equalsIgnoreCase(playTp.getCode()) || playTp.getData() == null) {
            // code为200表示第三方成功返回
            errorCode = ErrorCodeConstants.USER_GET_PLAYLOG_FAILURE;
            LOG.warn("getUserPlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getUserToken() + "[errorCode=" + errorCode + "]: get Playlog failed.");
        } else {
            List<PlayLogInfo> playlogList = playTp.getData().getItems();
            if (playlogList != null) {
                List<PlayRecordDto> dtoList = new LinkedList<PlayRecordDto>();
                List<String> lidList = new LinkedList<String>();
                List<String> aGlobalIdList = new LinkedList<String>();
                for (PlayLogInfo playlogInfo : playlogList) {
                    String lid = playlogInfo.getLid();
                    if (StringUtil.isNotBlank(lid)) {
                        lidList.add(lid);
                    }
                }
                short zero = 0;
                // 获取视频详情list
                GenericDetailResponse videoDetaiResponse = videoService.getDetails(lidList,
                        zero, zero, commonParam);
                // 获取aGlobalIdList,以此查询专辑详情list
                if (videoDetaiResponse != null) {
                    List<ResultDocInfo> docVideoInfos = videoDetaiResponse.getDetails();
                    if (docVideoInfos != null && docVideoInfos.size() > 0) {
                        for (ResultDocInfo docVideoInfo : docVideoInfos) {
                            if (docVideoInfo != null) {
                                VideoAttribute video = docVideoInfo.getVideo_attribute();
                                if (video != null) {
                                    String aGlobalId = video.getAlbum_original_id();
                                    if (StringUtil.isNotBlank(aGlobalId)) {
                                        aGlobalIdList.add(aGlobalId);
                                    }
                                }
                            }
                        }
                    }
                }
                // 获取专辑详情list
                GenericDetailResponse albumDetaiResponse = videoService.getDetails(
                        aGlobalIdList, zero, Short.MAX_VALUE, commonParam);

                if (videoDetaiResponse != null) {
                    List<ResultDocInfo> videoDocVideoInfos = videoDetaiResponse.getDetails();
                    if (videoDocVideoInfos != null && videoDocVideoInfos.size() > 0) {
                        for (PlayLogInfo playlogInfo : playlogList) {
                            String lid = playlogInfo.getLid();
                            if (StringUtil.isNotBlank(lid)) {
                                for (ResultDocInfo videoDocVideoInfo : videoDocVideoInfos) {
                                    if (videoDocVideoInfo != null) {
                                        String vGlobalId = videoDocVideoInfo.getLetv_original_id();
                                        if (vGlobalId != null && vGlobalId.equals(lid)) { // 播放记录的lid和视频详情列表的globalid匹配成功
                                            VideoAttribute videoAttribute = videoDocVideoInfo.getVideo_attribute();
                                            if (videoAttribute != null) {
                                                String aGlobalId = videoAttribute.getAlbum_original_id();
                                                LevidiPlayRecordDto playLogListDto = null;
                                                ResultDocInfo albumDocVideoInfo = null;
                                                AlbumAttribute albumAttribute = null;
                                                VideoAttributeInAlbum videoAttributeInAlbum = null;
                                                if (StringUtil.isNotBlank(aGlobalId)) { // aglobalId不为空，取专辑内的视频
                                                    if (albumDetaiResponse != null) {
                                                        List<ResultDocInfo> albumDocVideoInfos = albumDetaiResponse
                                                                .getDetails();
                                                        if (!CollectionUtils.isEmpty(albumDocVideoInfos)) {
                                                            for (ResultDocInfo albumDocInfo : albumDocVideoInfos) {
                                                                if (albumDocInfo != null) {
                                                                    String albumOriginId = albumDocInfo
                                                                            .getLetv_original_id();
                                                                    if (aGlobalId.equals(albumOriginId)) {
                                                                        albumDocVideoInfo = albumDocInfo;
                                                                        albumAttribute = albumDocInfo
                                                                                .getAlbum_attribute();
                                                                        if (albumAttribute != null) {
                                                                            List<VideoAttributeInAlbum> videoAttributeInAlbumList = albumAttribute
                                                                                    .getVideo_list();
                                                                            if (!CollectionUtils
                                                                                    .isEmpty(videoAttributeInAlbumList)) {
                                                                                for (VideoAttributeInAlbum videoAttributeInAlbum2 : videoAttributeInAlbumList) {
                                                                                    if (videoAttributeInAlbum2 != null
                                                                                            && lid.equals(videoAttributeInAlbum2
                                                                                            .getLetv_original_id())) {
                                                                                        videoAttributeInAlbum = videoAttributeInAlbum2;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                        break;// 跳出lid和vGlobalId的比较
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    if (albumAttribute == null) {
                                                        LOG.info("parsePlayRecord4Levidi_" + commonParam.getMac() + "_"
                                                                + commonParam.getUserId() + "album info is null");
                                                    }
                                                    playLogListDto = new LevidiPlayRecordDto(playlogInfo,
                                                            albumDocVideoInfo, albumAttribute, videoAttributeInAlbum,
                                                            commonParam);
                                                } else { // aglobalId为空,单视频
                                                    playLogListDto = new LevidiPlayRecordDto(playlogInfo,
                                                            videoDocVideoInfo, videoAttribute, commonParam);
                                                }
                                                playLogListDto.setRoleid(roleid == null ? 0 : roleid);
                                                dtoList.add(playLogListDto);
                                            }
                                            break; // 跳出lid和vGlobalId的比较
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                response.setData(dtoList);
                response.setCount(dtoList.size());
                response.setCurrentPage(page);
                response.setPageSize(pageSize);
                int pageCount = response.getCount() / pageSize;
                if (response.getCount() % pageSize > 0) {
                    pageCount += 1;
                }
                response.setPageCount(pageCount);
                response.caculatePageInfo();
            }
        }

        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    private NewPageResponse<PlayRecordDto> parsePlayRecord(Long roleid, Integer page, Integer pageSize,
                                                           Integer intervalTime, Integer type, Integer from, CommonParam commonParam) {
        NewPageResponse<PlayRecordDto> response = new NewPageResponse<PlayRecordDto>();
        String errorCode = null;

        if (type == null) {
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                type = 0;// levidi或le，type默认0
            } else {
                type = 1;
            }
        }
        String region = null; // tv版不区分地区
        APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
        PlayLogTp playTp = this.facadeTpDao.getUserTpDao().getPlaylogList(page, pageSize, intervalTime, roleid, type,
                appId, from, region, commonParam);
        if (playTp == null || !"200".equalsIgnoreCase(playTp.getCode()) || playTp.getData() == null) {
            // code为200表示第三方成功返回
            errorCode = ErrorCodeConstants.USER_GET_PLAYLOG_FAILURE;
            LOG.warn("getUserPlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getUserToken() + "[errorCode=" + errorCode + "]: get Playlog failed.");
        } else {
            List<PlayLogInfo> playlogList = playTp.getData().getItems();
            if (playlogList != null) {
                List<PlayRecordDto> dtoList = new LinkedList<PlayRecordDto>();
                // 2016-06-30国广版只过滤无版权和播控不通过的
                boolean isCibn = (commonParam.getBroadcastId() != null && CommonConstants.CIBN == commonParam
                        .getBroadcastId());
                for (PlayLogInfo playlogInfo : playlogList) {
                    Long vid = playlogInfo.getVid();
                    if (StringUtils.isNotBlank(playlogInfo.getVideoType())
                            && StringUtils.isNotBlank(playlogInfo.getCid())) {
                        Integer videoType = StringUtil.toInteger(playlogInfo.getVideoType());
                        Integer cid = StringUtil.toInteger(playlogInfo.getCid());
                        if (!VideoCommonUtil.isPositive(1, cid, null, videoType)) {// 非正片不显示
                            LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + ",This video[" + vid + "] is not positive.");
                            continue;
                        }
                    }
                    VideoMysqlTable video = null;
                    if (vid != null) {// 获取视频信息
                        video = albumVideoAccess.getVideo(vid, commonParam);
                        if (video == null) {
                            LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + "Illegal videoid [videoid=" + vid + "]");
                            if (isCibn) {
                                continue;
                            }
                        }
                    }
                    Long pid = playlogInfo.getPid();
                    AlbumMysqlTable album = null;
                    if (pid != null) {// 获取专辑信息
                        album = albumVideoAccess.getAlbum(pid, commonParam);
                        if (album == null) {
                            LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + "Illegal albumid [albumid=" + pid + "]");
                        }
                    }
                    if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
                        // 判断播控版权
                        if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN
                                && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                            if (album != null && album.getCibn() != null
                                    && album.getCibn() != UserConstants.BROADCAST_ONLINE_STATUS) {
                                continue;
                            }
                        } else {
                            if (album != null && album.getCntv() != null
                                    && album.getCntv() != UserConstants.BROADCAST_ONLINE_STATUS) {
                                // 播控方cntv未上线, TV无版权
                                LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + ". This album[" + pid + "] CNTV not online and TV not has no copyright.");
                                continue;
                            }
                        }
                    }
                    // 播控方版权过了，判断专辑版权
                    if (album != null && !album.isTVCopayright()) { // 专辑信息中设定的tv版权信息
                        LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + ". This album[" + pid + "] has no TV copyright.");
                        continue;
                    } else {
                        String playPlatForm = playlogInfo.getPlayPlatform();
                        if (StringUtils.isBlank(playPlatForm) || !playPlatForm.contains("420007")) {
                            LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + ". This album[" + pid + "] has no TV playplatform.");
                            continue;
                        }
                    }

                    CibnPlayRecordDto dto = new CibnPlayRecordDto(album, video, playlogInfo, true, commonParam);
                    dto.setRoleid(roleid == null ? 0 : roleid);
                    dtoList.add(dto);
                }

                response.setData(dtoList);
                response.setCount(playTp.getData().getTotal()); // 总数为用户中心返回的数量
                response.setCurrentPage(page);
                response.setPageSize(pageSize);

                int pageCount = response.getCount() / pageSize;
                if (response.getCount() % pageSize > 0) {
                    pageCount += 1;
                }
                response.setPageCount(pageCount);
                response.caculatePageInfo();
            }
        }

        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    private NewPageResponse<PlayRecordDto> parsePlayRecord4Tvlive(Long roleid, Integer page, Integer pageSize,
                                                                  Integer intervalTime, Integer type, Integer from, CommonParam commonParam) {
        NewPageResponse<PlayRecordDto> response = new NewPageResponse<PlayRecordDto>();
        String errorCode = null;

        if (type == null) {
            type = 1;
        }
        String region = null; // tv版不区分地区
        UserTpConstant.APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
        PlayLogTp playTp = this.facadeTpDao.getUserTpDao().getPlaylogList(page, pageSize, intervalTime, roleid, type,
                appId, from, region, commonParam);
        if (playTp == null || !"200".equalsIgnoreCase(playTp.getCode()) || playTp.getData() == null) {
            // code为200表示第三方成功返回
            errorCode = ErrorCodeConstants.USER_GET_PLAYLOG_FAILURE;
            LOG.warn("getUserPlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getUserToken() + "[errorCode=" + errorCode + "]: get Playlog failed.");
        } else {
            List<PlayLogInfo> playlogList = playTp.getData().getItems();
            if (playlogList != null) {
                List<PlayRecordDto> dtoList = new LinkedList<PlayRecordDto>();
                // 2016-06-30国广版只过滤无版权和播控不通过的
                boolean isCibn = (commonParam.getBroadcastId() != null && CommonConstants.CIBN == commonParam
                        .getBroadcastId());
                for (PlayLogInfo playlogInfo : playlogList) {
                    Long vid = playlogInfo.getVid();
                    if (StringUtils.isNotBlank(playlogInfo.getVideoType())
                            && StringUtils.isNotBlank(playlogInfo.getCid())) {
                        Integer videoType = StringUtil.toInteger(playlogInfo.getVideoType());
                        Integer cid = StringUtil.toInteger(playlogInfo.getCid());
                        if (!VideoCommonUtil.isPositive(1, cid, null, videoType)) {// 非正片不显示
                            LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + ",This video[" + vid + "] is not positive.");
                            continue;
                        }
                    }
                    VideoMysqlTable video = null;
                    if (vid != null) {// 获取视频信息
                        video = albumVideoAccess.getVideo(vid, commonParam);
                        if (video == null) {
                            LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + "Illegal videoid [videoid=" + vid + "]");
                            if (isCibn) {
                                continue;
                            }
                        } else if (VideoCommonUtil.isCharge(video.getPay_platform(), video.getPlay_platform(),
                                commonParam.getP_devType())) {
                            continue;// tvlive no need charge video
                        }
                    }
                    Long pid = playlogInfo.getPid();
                    AlbumMysqlTable album = null;
                    if (pid != null) {// 获取专辑信息
                        album = albumVideoAccess.getAlbum(pid, commonParam);
                        if (album == null) {
                            LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + "Illegal albumid [albumid=" + pid + "]");
                            continue;// tvlive no need short video
                        }
                    }
                    if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
                        // 判断播控版权
                        if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN
                                && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                            if (album != null && album.getCibn() != null
                                    && album.getCibn() != UserConstants.BROADCAST_ONLINE_STATUS) {
                                continue;
                            }
                        } else {
                            if (album != null && album.getCntv() != null
                                    && album.getCntv() != UserConstants.BROADCAST_ONLINE_STATUS) {
                                // 播控方cntv未上线, TV无版权
                                LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + ". This album[" + pid + "] CNTV not online and TV not has no copyright.");
                                continue;
                            }
                        }
                    }
                    // 播控方版权过了，判断专辑版权
                    if (album != null && !album.isTVCopayright()) { // 专辑信息中设定的tv版权信息
                        LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + ". This album[" + pid + "] has no TV copyright.");
                        continue;
                    } else {
                        String playPlatForm = playlogInfo.getPlayPlatform();
                        if (StringUtils.isBlank(playPlatForm) || !playPlatForm.contains("420007")) {
                            LOG.info("parsePlayRecord_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                    + ". This album[" + pid + "] has no TV playplatform.");
                            continue;
                        }
                    }

                    CibnPlayRecordDto dto = new CibnPlayRecordDto(album, video, playlogInfo, true, commonParam);
                    dto.setRoleid(roleid == null ? 0 : roleid);
                    dtoList.add(dto);
                }
                response.setData(dtoList);
                response.setCount(playTp.getData().getTotal()); // 总数为用户中心返回的数量
                response.setCurrentPage(page);
                response.setPageSize(pageSize);

                int pageCount = response.getCount() / pageSize;
                if (response.getCount() % pageSize > 0) {
                    pageCount += 1;
                }
                response.setPageCount(pageCount);
                response.caculatePageInfo();
            }
        }

        if (errorCode == null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    public Response<Boolean> deletePlaylog(String albumId, String videoId, String globalId, Long roleid,
                                           CommonParam commonParam) {
        Response<Boolean> response = new Response();
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserId()) || StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("deletePlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (errorCode == null) {
            if (albumId == null && videoId == null && globalId == null) {
                errorCode = ErrorCodeConstants.USER_DELETE_PLAYLOG_FAILURE;
                LOG.error("deletePlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: delete play log error: [albumid=" + albumId + ",videoid=" + videoId + "]");
            } else {
                final Integer flush = 0; // 非清空
                APPID appId = APPID.getAppIdNew(commonParam.getTerminalApplication());
                // delete user play log
                PlaylogCommonTp tp = this.facadeTpDao.getUserTpDao().deletePlaylog(albumId, videoId, globalId, roleid,
                        flush, appId, commonParam);
                if (tp != null && "200".equals(tp.getCode())) {
                    response.setResultStatus(1);
                    response.setData(true);
                } else {
                    response.setResultStatus(0);
                    response.setData(false);
                }
            }
        }

        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    /**
     * delete user all play log
     * @param roleid
     * @param commonParam
     * @return
     */
    public Response<Boolean> deleteAllPlaylog(Long roleid, CommonParam commonParam) {
        String errorCode = null;
        int resultStatus = 0;
        boolean data = false;
        if (StringUtils.isEmpty(commonParam.getUserId()) || StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("deleteAllPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }

        if (errorCode == null) {
            final Integer flush = 1; // 清空
            APPID appId = APPID.getAppIdNew(commonParam.getTerminalApplication());
            // delete user play log
            PlaylogCommonTp tp = this.facadeTpDao.getUserTpDao().deletePlaylog(null, null, null, roleid, flush, appId,
                    commonParam);
            resultStatus = 1;
            if (tp != null && "200".equals(tp.getCode())) {
                data = true;
            } else {
                data = false;
            }
        }
        Response<Boolean> response = new Response();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setResultStatus(resultStatus);
            response.setData(data);
        }

        return response;
    }

    /**
     * update user video play log
     * @param albumid
     * @param videoid
     * @param globalId
     * @param categoryId
     * @param playTime
     * @param roleid
     * @param from
     * @param commonParam
     * @return
     */
    public Response<Boolean> updatePlayTime(Long albumid, Long videoid, String globalId, Integer categoryId,
                                            Long playTime, Long roleid, Integer from, CommonParam commonParam) {
        String errorCode = null;
        boolean result = false;
        if (StringUtils.isEmpty(commonParam.getUserId()) || StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("updatePlayTime_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user does not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }

        if (errorCode == null) {
            AlbumMysqlTable album = albumVideoAccess.getAlbum(albumid, commonParam);
            VideoMysqlTable video = albumVideoAccess.getVideo(videoid, commonParam);
            if ((album == null && video == null && StringUtil.isBlank(globalId))) {
                errorCode = ErrorCodeConstants.USER_UPDATE_PLAYTIME_FAILURE;
                LOG.warn("updatePlayTime_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: update play time [albumid=" + albumid + ",videoid=" + videoid + ",htime="
                        + playTime + "] failed.");
            } else {
                if (categoryId == null) {
                    if (album != null || video != null) {
                        categoryId = ((album != null) ? album.getCategory() : video.getCategory());
                    }
                }

                if (playTime == null || playTime < 0 || playTime > Integer.MAX_VALUE) {
                    // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
                    playTime = -1L;
                } else {
                    playTime = playTime / 1000;
                }

                APPID appId = APPID.getAppIdNew(commonParam.getTerminalApplication());
                String region = commonParam.getWcode();
                // update user video play log
                PlaylogCommonTp tp = this.facadeTpDao.getUserTpDao().updatePlayTime(albumid, videoid, globalId,
                        categoryId, playTime, roleid, from, appId, region, commonParam);
                if (tp != null && "200".equals(tp.getCode())) {
                    result = true;
                } else {
                    errorCode = ErrorCodeConstants.USER_UPDATE_PLAYTIME_FAILURE;
                }
            }
        }

        Response<Boolean> response = new Response<Boolean>();
        if (errorCode == null) {
            response.setData(result);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    public NewResponse<Boolean> updateUserPlayRecord(Long albumid, Long videoid, String globalId, Integer categoryId,
                                                     Long playTime, Long roleid, Integer from, CommonParam commonParam) {
        String errorCode = null;
        boolean result = false;
        if (StringUtils.isEmpty(commonParam.getUserId()) || StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("updatePlayTime_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user does not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }

        if (errorCode == null) {
            AlbumMysqlTable album = albumVideoAccess.getAlbum(albumid, commonParam);
            VideoMysqlTable video = albumVideoAccess.getVideo(videoid, commonParam);
            if ((album == null && video == null && StringUtil.isBlank(globalId))) {
                errorCode = ErrorCodeConstants.USER_UPDATE_PLAYTIME_FAILURE;
                LOG.warn("updatePlayTime_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: update play time [albumid=" + albumid + ",videoid=" + videoid + ",htime="
                        + playTime + "] failed.");
            } else {
                if (categoryId == null) {
                    if (album != null || video != null) {
                        categoryId = ((album != null) ? album.getCategory() : video.getCategory());
                    }
                }

                if (playTime == null || playTime < 0 || playTime > Integer.MAX_VALUE) {
                    // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
                    playTime = -1L;
                } else {
                    playTime = playTime / 1000;
                }

                APPID appId = APPID.getAppIdNew(commonParam.getTerminalApplication());
                String region = commonParam.getWcode();
                // update user video play log
                PlaylogCommonTp tp = this.facadeTpDao.getUserTpDao().updatePlayTime(albumid, videoid, globalId,
                        categoryId, playTime, roleid, from, appId, region, commonParam);
                if (tp != null && "200".equals(tp.getCode())) {
                    result = true;
                } else {
                    errorCode = ErrorCodeConstants.USER_UPDATE_PLAYTIME_FAILURE;
                }
            }
        }

        NewResponse<Boolean> response = new NewResponse<Boolean>();
        if (errorCode == null) {
            response.setData(result);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }

        return response;
    }

    /**
     * get user account info and vip info
     * @param commonParam
     * @return
     */
    public Response<UserAccountDto> getUserAccount4Levidi(CommonParam commonParam) {
        String errorCode = null;
        if (StringUtils.isBlank(commonParam.getUserId()) || StringUtils.isBlank(commonParam.getUserToken())) {
            // 取用户账号信息
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("getUserAccount_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                        + commonParam.getUserId() + "[errorCode=" + errorCode + "]: user not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }

        UserAccountDto accountDto = new UserAccountDto();
        if (errorCode == null) {
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
                accountDto.setPicture(DomainMapping.changeDomainByBraodcastId(picture, commonParam.getBroadcastId(),
                        commonParam.getTerminalApplication()));

                String displayName = null;
                if (letvUserDto != null && letvUserDto.getBean() != null) {
                    String[] names = new String[] { letvUserDto.getBean().getMobile(),
                            letvUserDto.getBean().getEmail(), letvUserDto.getBean().getNickname() };
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

            // 设置会员信息
            List<UserAccountDto.VipInfoV2Dto> vipInfoV2DtoList = new ArrayList<UserAccountDto.VipInfoV2Dto>();
            accountDto.setVipList(vipInfoV2DtoList);
            BossTpResponse<List<SubscribeInfo>> subscrobeInfoListResponse = this.facadeTpDao.getVipTpDao().getVipInfo(
                    VipTpConstant.BossTerminalType.TV.getCode(), commonParam);
            if (subscrobeInfoListResponse != null && subscrobeInfoListResponse.isSucceed()) {
                List<SubscribeInfo> subscrobeInfoList = subscrobeInfoListResponse.getData();
                for (SubscribeInfo subscribeInfo : subscrobeInfoList) {
                    if (subscribeInfo == null || !VipTpConstant.VIP_INDIA.equals(subscribeInfo.getProductId())) { // 过滤非印度TV会员
                        continue;
                    }
                    UserAccountDto.VipInfoV2Dto vipInfoV2Dto = new UserAccountDto.VipInfoV2Dto();
                    vipInfoV2Dto.setProductId(subscribeInfo.getProductId());
                    vipInfoV2Dto.setName(subscribeInfo.getName());
                    vipInfoV2Dto.setEndTime(subscribeInfo.getEndTime());
                    if (subscribeInfo.getEndTime() != null && subscribeInfo.getEndTime() > System.currentTimeMillis()) { // 设置为可用会员
                        vipInfoV2Dto.setStatus(UserAccountDto.VipInfoV2Dto.STATUS_VIP_VALID);
                    } else {
                        vipInfoV2Dto.setStatus(UserAccountDto.VipInfoV2Dto.STATUS_VIP_INVALID); // 设置为不可用会员
                    }
                    vipInfoV2DtoList.add(vipInfoV2Dto);
                }
            }
        }

        Response<UserAccountDto> response = new Response<UserAccountDto>();
        if (errorCode == null) {
            response.setData(accountDto);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }
        return response;
    }

    /**
     * @param commonParam
     * @return
     */
    public Response<UserAccountDto> getUserAccount4Le(CommonParam commonParam) {
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
        if (errorCode == null) {
            vipMetadataService.updateVipInfoInCacheV2(
                    VipTpConstant.BossTerminalType.TV.getCode(), commonParam); // 更新tv端缓存的会员信息
            UserAccountDto accountDto = new UserAccountDto();
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
                accountDto.setPicture(DomainMapping.changeDomainByBraodcastId(picture, commonParam.getBroadcastId(),
                        commonParam.getTerminalApplication()));
                String displayName = null;
                if (letvUserDto != null && letvUserDto.getBean() != null) {
                    String[] names = new String[] { letvUserDto.getBean().getNickname(),
                            letvUserDto.getBean().getEmail(), letvUserDto.getBean().getMobile() };
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
        return response;
    }

    /**
     * 通过用户名获取该账户的用户中心id
     * @return Long 用户不存在或用户中心不存在时，返回null
     */
    public Long getUserCenterIdByUsername(CommonParam commonParam) {
        Long userid = null;
        // 读取缓存中的用户信息
        // User user = this.getUserByUsername(username);
        UserStatus user = this.getUserStatus(commonParam);
        if (user != null && user.getUserId() != 0) {
            userid = user.getUserId();
        }

        return userid;
    }

    /**
     * check third party application if is allowing register and login
     * @param commonParam
     * @return
     */
    public Response<Integer> checkRegisterAndLoginAuth(CommonParam commonParam) {
        Response<Integer> response = new Response<Integer>();
        // 1.参数校验
        Integer subend = VipTpConstantUtils.getSubendByTerminalBrand(commonParam.getBsChannel());
        if (!TerminalCommonConstant.TERMINAL_APPLICATION_TPSDK.equalsIgnoreCase(commonParam.getTerminalApplication())
                || !VipConstants.BSCHANNEL_TPSDK.equalsIgnoreCase(commonParam.getBsChannel()) || subend == null) {
            LOG.info("checkRegisterAndLoginAuth_" + commonParam.getTerminalApplication() + "_"
                    + commonParam.getBsChannel() + "[errorCode=" + ErrorCodeConstants.USER_ILLEGAL_PARAMETER
                    + ": checkRegisterAndLoginAuth failed.");
            ErrorCodeCommonUtil.setErrorResponse(response, ErrorCodeConstants.USER_ILLEGAL_PARAMETER,
                    commonParam.getLangcode());
        } else {
            // 2.对比数据
            Object object = this.facadeCacheDao.getUserCacheDao().getRegisterAndLoginAuth(subend);
            if (object != null) {
                response.setData(UserConstants.TPSDK_REGISTER_AND_LOGIN_WITH_AUTHORIZATION);
            } else {
                response.setData(UserConstants.TPSDK_REGISTER_AND_LOGIN_WITHOUT_AUTHORIZATION);
            }
            response.setResultStatus(LetvUserVipCommonConstant.RESPONSE_SUCCESS);
        }

        return response;
    }

    /**
     * 设置接口处理失败时的返回值
     * @param response
     *            返回的BaseResponse
     * @param errCode
     *            错误代码
     * @param locale
     *            用户语言环境
     */
    private BaseResponse setErrorResponse(BaseResponse response, String errCode, Locale locale) {
        if (response != null) {
            response.setResultStatus(0);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errCode, locale));
        }
        return response;
    }

    /**
     * 设置接口处理失败时的返回值
     * @param response
     *            返回的BaseResponse
     * @param errCode
     *            错误代码
     * @param langcode
     *            用户语言环境
     */
    private BaseResponse setErrorResponse(BaseResponse response, String errCode, String langcode) {
        if (response != null) {
            response.setResultStatus(0);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errCode, langcode));
        }
        return response;
    }

    private String getFollowNum(Integer Num, String format1, String format2, String format3) {
        try {
            if (Num == null || (Num.toString()).length() < 8) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat df = new SimpleDateFormat(format1);
            SimpleDateFormat df1 = new SimpleDateFormat(format2);
            SimpleDateFormat df2 = new SimpleDateFormat(format3);
            Date followDate = sdf.parse(Num.toString());
            Date nowDate = new Date();
            String followStr = null;
            String nowStr = null;
            String followNum = "";
            followStr = df.format(followDate);
            nowStr = df.format(nowDate);
            if (Integer.parseInt(followStr) < Integer.parseInt(nowStr)) {
                followNum = df1.format(followDate);
            } else {
                followNum = df2.format(followDate);
            }
            return followNum;
        } catch (ParseException e) {
            LOG.warn("getFollowNum_" + Num + ":" + e.getMessage());
        }
        return "";
    }

    public Response<ResultValueDto<Integer>> addUserRole(String nickname, String roleType, String birthday,
                                                         String gender, String duration, String timeStart, String timeEnd, String setAge, CommonParam commonParam) {
        ResultValueDto<Integer> data = new ResultValueDto<Integer>();
        int resultStatus = 0;
        String errorCode = null;

        if ((StringUtil.toLong(commonParam.getUserId(), 0L) < 1L) || StringUtil.isBlank(commonParam.getUserToken())) {
            errorCode = ErrorCodeConstants.USER_ILLEGAL_PARAMETER;
            LOG.info("addUserRole_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + roleType
                    + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            // get user roleid from cache
            Long roleId = this.getLetvUserRoleStr(StringUtil.toLong(commonParam.getUserId()));
            if (roleId != null && roleId > 0) {
                data.setResultCode(UserConstants.RESPONSE_SUCCESS);
                resultStatus = 1;
            } else {
                // add user role info
                RoleCommonPostTP tpResponse = this.facadeTpDao.getUserTpDao().addRole(null, nickname, roleType,
                        birthday, gender, duration, timeStart, timeEnd, setAge, commonParam);
                if (tpResponse == null || tpResponse.getBean() == null || tpResponse.getBean().size() < 1) {
                    // invoke interface failure
                    errorCode = ErrorCodeConstants.USER_ADD_ROLE_ERROR;
                    LOG.error("addUserRole_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + roleType
                            + "[errorCode=" + errorCode + "]:.parameter illegal.");
                } else {
                    if ("1".equals(tpResponse.getBean().get(0).getResult())) {
                        this.getLetvUserRoleStr(StringUtil.toLong(commonParam.getUserId()));
                        // add user role success
                        data.setResultCode(UserConstants.RESPONSE_SUCCESS);
                        resultStatus = 1;
                    } else {// add user role failure
                        data.setResultCode(UserConstants.RESPONSE_FAILURE);
                        resultStatus = 0;
                    }
                }
            }
        }
        Response<ResultValueDto<Integer>> response = new Response<ResultValueDto<Integer>>();
        if (errorCode != null) {
            ResponseUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setResultStatus(resultStatus);
            response.setData(data);
        }

        return response;
    }

    public Response<ResultValueDto<Integer>> updateUserRole(Long roleId, String nickname, String roleType,
                                                            String birthday, String gender, String duration, String timeStart, String timeEnd, String setAge,
                                                            CommonParam commonParam) {
        int resultStatus = 0;
        ResultValueDto<Integer> data = new ResultValueDto<Integer>();
        String errorCode = null;

        if (roleId == null || roleId < 1) {// roleid can not be empty or zero
            errorCode = ErrorCodeConstants.USER_ILLEGAL_PARAMETER;
            LOG.error("updateUserRole_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + roleId
                    + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            // update user role info or delete user role
            RoleCommonPostTP tpResponse = this.facadeTpDao.getUserTpDao().updateRole(roleId, nickname, roleType,
                    birthday, gender, duration, timeStart, timeEnd, setAge, commonParam);
            if (tpResponse == null || !"1".equals(tpResponse.getStatus()) || tpResponse.getBean() == null
                    || tpResponse.getBean().size() < 1) {
                errorCode = ErrorCodeConstants.USER_UPDATE_USER_ROLE_ERROR;
                LOG.error("updateUserRole_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + roleId
                        + "[errorCode=" + errorCode + "]: update user role info failure.");
            } else {
                if ("1".equals(tpResponse.getBean().get(0).getResult())) {
                    // update user role info success
                    data.setResultCode(UserConstants.RESPONSE_SUCCESS);
                    resultStatus = 1;
                } else {// update user role info failure
                    data.setResultCode(UserConstants.RESPONSE_FAILURE);
                    resultStatus = 0;
                }
            }
        }
        Response<ResultValueDto<Integer>> response = new Response<ResultValueDto<Integer>>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setResultStatus(resultStatus);
            response.setData(data);
        }

        return response;
    }

    /**
     * 接口返回roleList数据加载到类
     * @param bean
     * @return
     */
    private RoleContent setRoleContent(RoleBean bean) {
        String logPrefix = "setLetvUserRoleDto_";
        RoleContent reRole = null;
        if (bean != null) {
            String roleContent = bean.getContent();
            if (roleContent != null) {
                reRole = JsonUtil.parse(roleContent, RoleContent.class);
                LOG.info(logPrefix + " get roleInfo sucess, userid is ");
            }
        }
        return reRole;
    }

    private LetvUserRoleDto setLetvUserRoleDto(RoleContent reRole) {
        String logPrefix = "setLetvUserRoleDto_";
        LetvUserRoleDto LetvUserRoleDto = null;
        if (reRole != null) {
            LetvUserRoleDto = new LetvUserRoleDto();
            LetvUserRoleDto.setRoleType(reRole.getRoleType());
            LetvUserRoleDto.setNickName(reRole.getNickName());
            LetvUserRoleDto.setBrithday(reRole.getBrithday());
            LetvUserRoleDto.setGender(reRole.getGender());
            LetvUserRoleDto.setTimeStart(reRole.getTimeStart());
            LetvUserRoleDto.setTimeEnd(reRole.getTimeEnd());
            LetvUserRoleDto.setDuration(reRole.getDuration());
            LetvUserRoleDto.setSetAge(reRole.getSetAge());
            LetvUserRoleDto.setTimestamp(reRole.getTimestamp());
            LOG.info(logPrefix + " get roleInfo sucess, userid is ");
        }
        return LetvUserRoleDto;
    }

    /**
     * 获取用户的角色信息
     */
    public Response<LetvUserRoleListDto> getUserRole(UserRoleRequest addUserRoleRequest, Locale locale) {
        Response<LetvUserRoleListDto> response = new Response<LetvUserRoleListDto>();
        String logPrefix = "updateUserRole_" + addUserRoleRequest.getUserid() + "_" + addUserRoleRequest.getUsername()
                + "_" + addUserRoleRequest.getPreFixLog();
        String errorCode = null;

        int validCode = addUserRoleRequest.assertValid();
        if (CommonMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.USER_ILLEGAL_PARAMETER;
            ResponseUtil.setErrorResponse(response, errorCode,
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.USERCENTER, validCode), locale);
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            RoleCommonGetTP roleCommonTP = this.facadeTpDao.getUserTpDao().getRole(addUserRoleRequest, logPrefix);
            if (roleCommonTP == null || roleCommonTP.getStatus() == null || roleCommonTP.getStatus() != 1
                    || roleCommonTP.getErrorCode() == null || roleCommonTP.getErrorCode() != 0) {
                // 接口调用失败
                errorCode = ErrorCodeConstants.USER_GET_ROLE_ERROR;
                ResponseUtil.setErrorResponse(response, errorCode, locale);
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]:get user role list failure.");
            } else {
                LetvUserRoleListDto data = new LetvUserRoleListDto();
                data.setToken(addUserRoleRequest.getToken());
                data.setUserid(addUserRoleRequest.getUserid());
                List<RoleBean> beanList = roleCommonTP.getBean();
                if (beanList != null && beanList.size() != 0) {
                    List<LetvUserRoleDto> roleList = new ArrayList<LetvUserRoleDto>();
                    for (RoleBean bean : beanList) {
                        RoleContent reRole = this.setRoleContent(bean);
                        LetvUserRoleDto letvUserRoleDto = this.setLetvUserRoleDto(reRole);
                        if (letvUserRoleDto != null) {
                            letvUserRoleDto.setRoleid(StringUtil.toLong(bean.getUser_role_id(), Long.valueOf(0)));
                            roleList.add(letvUserRoleDto);
                        }
                    }
                    data.setRoleList(roleList);
                }
                response.setData(data);
            }
        }
        return response;
    }

    /**
     * 删除乐视儿童模式角色
     * @param locale
     */
    public Response<ResultValueDto<Integer>> deleteUserRole(UserRoleRequest userRoleRequest, Locale locale) {
        Response<ResultValueDto<Integer>> response = new Response<ResultValueDto<Integer>>();
        ResultValueDto<Integer> data = new ResultValueDto<Integer>();
        String logPrefix = "deleteUserRole_" + userRoleRequest.getUserid() + "_" + userRoleRequest.getUserid() + "_"
                + userRoleRequest.getPreFixLog();
        String errorCode = null;

        int validCode = userRoleRequest.assertValid4DeleteOrUpdate();
        if (validCode != CommonMsgCodeConstant.REQUEST_SUCCESS) {
            errorCode = ErrorCodeConstants.USER_ILLEGAL_PARAMETER;
            ResponseUtil.setErrorResponse(response, errorCode,
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.USERCENTER, validCode), locale);
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            if (errorCode == null) {
                RoleCommonPostTP tpResponse = this.facadeTpDao.getUserTpDao().deleteRole(userRoleRequest, logPrefix);
                if (tpResponse == null || tpResponse.getBean() == null || tpResponse.getBean().size() < 1) {
                    // 接口调用失败
                    errorCode = ErrorCodeConstants.USER_DELETE_ROLE_ERROR;
                    ResponseUtil.setErrorResponse(response, errorCode, locale);
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
                } else {
                    if ("1".equals(tpResponse.getBean().get(0).getResult())) {// 删除成功
                        this.facadeCacheDao.getUserCacheDao().deleteUserRoleId(userRoleRequest.getUserid());
                        data.setResultCode(UserConstants.RESPONSE_SUCCESS);
                        response.setResultStatus(1);
                    } else {// 删除失败
                        data.setResultCode(UserConstants.RESPONSE_FAILURE);
                        response.setResultStatus(0);
                    }
                    response.setData(data);
                }
            }
        }

        return response;
    }

    /**
     * 编辑角色默认播单
     * @param locale
     */
    public Response<ResultValueDto<Integer>> editRolePlayList(RolePlayListRequest rolePlayListRequest, Locale locale) {
        Response<ResultValueDto<Integer>> response = new Response<ResultValueDto<Integer>>();
        ResultValueDto<Integer> data = new ResultValueDto<Integer>();
        String logPrefix = "editRolePlayList_" + rolePlayListRequest.getPreFixLog();
        String errorCode = null;

        // 参数校验
        int validCode = rolePlayListRequest.assertValid();
        if (CommonMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.USER_ILLEGAL_PARAMETER;
            ResponseUtil.setErrorResponse(response, errorCode,
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.USERCENTER, validCode), locale);
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            if (errorCode == null) {
                RoleCommonPlayListTP tpResponse = this.facadeTpDao.getUserTpDao().editRolePlayList(rolePlayListRequest,
                        logPrefix);
                if (tpResponse == null) {
                    // 接口调用失败
                    errorCode = ErrorCodeConstants.USER_ADD_ROLEPLAYLIST_ERROR;
                    ResponseUtil.setErrorResponse(response, errorCode, locale);
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
                } else {
                    if (tpResponse.getCode() == 200) {// 添加成功
                        data.setResultCode(UserConstants.RESPONSE_SUCCESS);
                        response.setResultStatus(1);
                        response.setStatus(1);
                    } else {// 添加失败
                        data.setResultCode(UserConstants.RESPONSE_FAILURE);
                        response.setResultStatus(0);
                        response.setStatus(0);
                    }
                    response.setData(data);
                }
            }
        }

        return response;
    }

    /**
     * 删除角色默认播单中的专辑
     * @param locale
     */
    public Response<ResultValueDto<Integer>> deleteRolePlayList(RolePlayListRequest rolePlayListRequest, Locale locale) {
        Response<ResultValueDto<Integer>> response = new Response<ResultValueDto<Integer>>();
        ResultValueDto<Integer> data = new ResultValueDto<Integer>();
        String logPrefix = "addUserRole_" + rolePlayListRequest.getPreFixLog();
        String errorCode = null;

        // 参数校验
        int validCode = rolePlayListRequest.assertValid();
        if (CommonMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.USER_ILLEGAL_PARAMETER;
            ResponseUtil.setErrorResponse(response, errorCode,
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.USERCENTER, validCode), locale);
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            if (errorCode == null) {
                RoleCommonPlayListTP tpResponse = this.facadeTpDao.getUserTpDao().deleteRolePlayList(
                        rolePlayListRequest, logPrefix);
                if (tpResponse == null) {
                    // 接口调用失败
                    errorCode = ErrorCodeConstants.USER_DELETE_ROLEPLAYLIST_ERROR;
                    ResponseUtil.setErrorResponse(response, errorCode, locale);
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
                } else {
                    if (tpResponse.getCode() == 200) {// 删除成功
                        data.setResultCode(UserConstants.RESPONSE_SUCCESS);
                        response.setResultStatus(1);
                        response.setStatus(1);
                    } else {// 删除失败
                        data.setResultCode(UserConstants.RESPONSE_FAILURE);
                        response.setResultStatus(0);
                        response.setStatus(0);
                    }
                    response.setData(data);
                }
            }
        }

        return response;
    }

    /**
     * 获取播单中专辑列表
     * @param locale
     */
    public PageCommonResponse<RolePlayListDto> getRolePlayList(RolePlayListRequest rolePlayListRequest, Locale locale,
                                                               CommonParam commonParam) {
        PageCommonResponse<RolePlayListDto> response = new PageCommonResponse<RolePlayListDto>();

        PageControl<RolePlayListDto> pageControl = new PageControl<RolePlayListDto>();
        List<RolePlayListDto> dataList = new LinkedList<RolePlayListDto>();
        RolePlayListDto dto = null;
        String logPrefix = "getRolePlayList_" + rolePlayListRequest.getPreFixLog();
        String errorCode = null;

        // 参数校验
        int validCode = rolePlayListRequest.assertValid();
        if (CommonMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.USER_ILLEGAL_PARAMETER;
            ResponseUtil.setErrorResponse(response, errorCode,
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.USERCENTER, validCode), locale);
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            if (errorCode == null) {
                RolePlayListTp tpResponse = this.facadeTpDao.getUserTpDao().getRolePlayList(rolePlayListRequest,
                        logPrefix);
                if (tpResponse == null || (!"200".equals(tpResponse.getCode()) && !"400".equals(tpResponse.getCode()))) {
                    // 接口调用失败
                    errorCode = ErrorCodeConstants.USER_GET_ROLEPLAYLIST_ERROR;
                    ResponseUtil.setErrorResponse(response, errorCode, locale);
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
                } else {
                    if (!"400".equals(tpResponse.getCode()) && tpResponse.getData() != null) {

                        RolePlayListData rolePlayListData = tpResponse.getData();
                        List<RolePlayList> rolePlayList = rolePlayListData.getList();
                        for (RolePlayList rolePlay : rolePlayList) {
                            dto = new RolePlayListDto();
                            dto.setPid(rolePlay.getPid());
                            AlbumMysqlTable albumMysql = albumVideoAccess.getAlbum(
                                    rolePlay.getPid(), commonParam);
                            if (albumMysql == null) {
                                continue;
                            }
                            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                                dto.setChargeInfos(JumpUtil.genChargeInfos(albumMysql.getPay_platform()));
                            }
                            dto.setTitle(rolePlay.getTitle());
                            dto.setSubTitle(rolePlay.getSubTitle());
                            dto.setPic(rolePlay.getPic());
                            dto.setVid(rolePlay.getVid());
                            dto.setEpisode(rolePlay.getEpisode());
                            dto.setDataType("1");// 专辑
                            dataList.add(dto);
                        }
                        pageControl.setCount(Integer.parseInt(rolePlayListData.getCount()));
                    } else {
                        pageControl.setCount(0);
                    }
                    pageControl.setList(dataList);

                    pageControl.setCurrentPage(rolePlayListRequest.getPage());

                    pageControl.setPageSize(rolePlayListRequest.getPageSize());

                    int pageCount = pageControl.getCount() / pageControl.getPageSize();
                    if (pageControl.getCount() % pageControl.getPageSize() > 0) {
                        pageCount += 1;
                    }

                    pageControl.setPageCount(pageCount);
                    response = new PageCommonResponse<RolePlayListDto>(pageControl);
                    response.setResultStatus(1);
                    response.setStatus(1);
                }
            }
        }
        return response;
    }

    /**
     * 验证手机号是否存在
     * @return
     */
    public Boolean checkMobileExisting(String mobile, String mac, String channel, Integer broadcastId) {
        if (StringUtils.isEmpty(mobile)) {
            return Boolean.FALSE;
        }

        UserCenterCommonResponse<UserInfoCommonResponse> response = this.facadeTpDao.getUserTpDao()
                .checkMobileExisting(mobile, mac, channel, broadcastId);

        if (response != null && response.getBean() != null && response.getBean().getResult() != null
                && !response.getBean().getResult()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * 获取收藏列表
     * @param category
     * @param favoriteType
     * @param fromType
     * @param page
     * @param pagesize
     * @param isFull
     * @param commonParam
     * @return
     */
    public BaseResponse getFavoriteList(Integer category, String favoriteType, Integer fromType, Integer page,
                                        Integer pagesize, Integer isFull, CommonParam commonParam) {
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        LetvUserFavoriteListTp resultTp = null;
        List<FavoriteListDto> favoriteDtoList = new LinkedList<FavoriteListDto>();
        if (errorCode == null) {
            APPID appId = APPID.getAppId(commonParam.getTerminalApplication()); // 获取appid
            String region = null;
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                region = StringUtil.isBlank(commonParam.getSalesArea()) ? commonParam.getSalesArea() : commonParam
                        .getWcode();
            }
            resultTp = this.facadeTpDao.getUserTpDao().getFavoriteList(category, favoriteType, fromType, appId, page,
                    pagesize, region, commonParam); // 获取收藏列表
            if (resultTp == null || !"200".equals(resultTp.getCode())) { // 接口调用失败,第三方返回code不为"200"
                errorCode = ErrorCodeConstants.USER_GET_ZTFAVORITELIST_ERROR;
            } else if (resultTp.getData() != null && (!CollectionUtils.isEmpty(resultTp.getData().getItems()))) { // 收藏数据不为空
                favoriteDtoList = this.getFavoriteDto(favoriteType, resultTp.getData().getItems(), commonParam);
            }
        }

        // TODO(liudaojie) 干掉老版本兼容
        if (CommonConstants.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam.getTerminalApplication())
                && commonParam.getAppCode() != null && StringUtil.toInteger(commonParam.getAppCode(), 0) < 400) { // levidi老版本兼容
            PageResponse<TagAndAlbumListDto> response = new PageResponse<TagAndAlbumListDto>();
            if (errorCode == null) {
                response.setResultStatus(1);
                List<TagAndAlbumListDto> tagAndAlbumListDtoList = new ArrayList<TagAndAlbumListDto>();
                TagAndAlbumListDto tagAndAlbumListDto = new TagAndAlbumListDto();
                List<LetvUserPlayList> letvUserPlayList = new ArrayList<LetvUserPlayList>();
                if (favoriteDtoList != null && favoriteDtoList.size() > 0) {
                    for (FavoriteListDto favoriteDto : favoriteDtoList) {
                        letvUserPlayList.add(this.favoriteDto2playListDto(favoriteDto));
                    }
                }
                tagAndAlbumListDto.setItems(letvUserPlayList);
                tagAndAlbumListDtoList.add(tagAndAlbumListDto);
                response.setData(tagAndAlbumListDtoList);
            } else {
                response.setResultStatus(0);
                response.setErrCode(errorCode);
                String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
                response.setErrMsg(errorMsg);
                LOG.info("getFavoriteList_" + commonParam.getUsername() + " error. error code:" + errorCode
                        + ".error message:" + errorMsg);
            }
            return response;
        } else {
            PageCommonResponse<FavoriteListDto> response = new PageCommonResponse<FavoriteListDto>();
            if (errorCode == null) {
                response.setResultStatus(1);
                response.setItems(favoriteDtoList);
                // response.setCount(resultTp.getData().getTotal());这里有些问题，对于用户中心吐得视频有可能已经下线，也计算在数量内了
                response.setPageInfo(favoriteDtoList.size(), page, pagesize);
            } else {
                response.setResultStatus(0);
                response.setErrCode(errorCode);
                String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
                response.setErrMsg(errorMsg);
                LOG.info("getFavoriteList_" + commonParam.getUsername() + " error. error code:" + errorCode
                        + ".error message:" + errorMsg);
            }
            return response;
        }
    }

    private LetvUserPlayList favoriteDto2playListDto(FavoriteListDto favoriteDto) {
        LetvUserPlayList letvUserPlayList = null;
        if (favoriteDto != null) {
            letvUserPlayList = new LetvUserPlayList();
            letvUserPlayList.setTitle(favoriteDto.getTitle());
            letvUserPlayList.setSubTitle(favoriteDto.getSubTitle());
            letvUserPlayList.setCid(favoriteDto.getCid());
            letvUserPlayList.setVideoStatus(favoriteDto.getVideoStatus());
            letvUserPlayList.setPic(favoriteDto.getPic());
            letvUserPlayList.setGlobalid(favoriteDto.getGlobalid());
            letvUserPlayList.setFollownum(favoriteDto.getFollownum());
            letvUserPlayList.setEpisodes(favoriteDto.getEpisodes());
        }
        return letvUserPlayList;
    }

    /**
     * 拼接收藏dto
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源
     * @param favorite
     * @param commonParam
     * @return
     */
    private FavoriteListDto getFavoriteDto(Integer favoriteType, LetvUserFavorite favorite, CommonParam commonParam) {
        FavoriteListDto favoriteDto = null;
        if (favoriteType == 1) { // 点播收藏
            AlbumMysqlTable albumInfo = null;
            if (StringUtil.isNotBlank(favorite.getPlay_id())) {
                Long albumId = StringUtil.toLong(favorite.getPlay_id());
                // 获取剧集信息
                albumInfo = albumVideoAccess.getAlbum(albumId, commonParam);
                if (albumInfo != null) {
                    favoriteDto = getPlayFavoriteDto(albumInfo, commonParam);
                }
            }
        } else if (favoriteType == 5) { // 专题收藏
            favoriteDto = new FavoriteListDto(favorite);
            favoriteDto.setCategory(MessageUtils.getMessage(
                    CategoryConstants.SEARCH_CATEGORY_MAP.get(favorite.getCategory()), commonParam.getLangcode()));
        } else if (favoriteType == 10) { // 其他外网资源
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) { // levidi版本
                GenericDetailResponse albumDetail = videoService.getWebSiteDetails(
                        favorite.getGlobal_id(), (short) 0, (short) 0, commonParam); // 从作品库获取专辑详情
                if (albumDetail != null) {
                    List<ResultDocInfo> docInfos = albumDetail.getDetails();
                    if (docInfos != null) {
                        ResultDocInfo docInfo = docInfos.get(0);
                        if (docInfo != null) {
                            favoriteDto = getFavoriteDto(docInfo, commonParam);
                            AlbumAttribute album = docInfo.getAlbum_attribute();
                            if (album != null) {
                                favoriteDto.setPic(getFavoritePic4Levidi(album.getImages(), album.getAlbum_pic()));
                            }
                        }
                    }
                }
            }
        }
        if (favoriteDto != null) {
            favoriteDto.setFavoriteId(favorite.getFavorite_id()); // 设置favorite_id
        }
        return favoriteDto;
    }

    public String convertFavGlobalIdToWFSubjectId(String globalId) {
        String wfSubjectId = null;
        if (globalId != null && globalId.contains("112_")) {
            wfSubjectId = globalId.split("_")[1];
        }
        return wfSubjectId;
    }

    /**
     * 拼接收藏dto
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源
     * @param favoriteList
     * @param commonParam
     * @return
     */
    private List<FavoriteListDto> getFavoriteDto(String favoriteType, List<LetvUserFavorite> favoriteList,
                                                 CommonParam commonParam) {
        List<FavoriteListDto> favoriteDtoList = new LinkedList<FavoriteListDto>();
        if ("1".equals(favoriteType)) { // 点播收藏
            for (LetvUserFavorite favorite : favoriteList) {
                AlbumMysqlTable albumInfo = null;
                if (StringUtil.isNotBlank(favorite.getPlay_id())) {
                    Long albumId = StringUtil.toLong(favorite.getPlay_id());
                    // 获取剧集信息
                    albumInfo = albumVideoAccess.getAlbum(albumId, commonParam);
                    if (albumInfo != null) {
                        FavoriteListDto favoriteDto = getPlayFavoriteDto(albumInfo, commonParam);
                        if (favorite != null) {
                            favoriteDto.setFavoriteId(favorite.getFavorite_id()); // 设置favorite_id
                            favoriteDtoList.add(favoriteDto);
                        }
                    }
                }
            }
        } else if (favoriteType != null && Arrays.asList(favoriteType.split(",")).contains("5")) { // 专题收藏
            for (LetvUserFavorite favorite : favoriteList) {
                if ("12".equals(favorite.getFavorite_type())) {
                    // 瀑布流专题
                    String global_id = favorite.getGlobal_id();
                    String wfSubjectId = convertFavGlobalIdToWFSubjectId(global_id);
                    WFSubjectDto mWFSubjectDto = this.facadeCacheDao.getChannelCacheDao().getWFSubjectData(wfSubjectId,
                            false);

                    if (mWFSubjectDto == null) {
                        mWFSubjectDto = channelV2Service.getWFSubjectDtoById(wfSubjectId,
                                commonParam);
                        if (mWFSubjectDto != null) {
                            this.facadeCacheDao.getChannelCacheDao().setWFSubjectData(wfSubjectId, commonParam,
                                    mWFSubjectDto);
                        }
                    }
                    if (mWFSubjectDto != null) {
                        FavoriteListDto favoriteDto = new FavoriteListDto();
                        WFSubjectPackageDto banner = mWFSubjectDto.getBanner();
                        if (banner != null) {
                            List<WFSubjectPackageDataDto> bannerDataList = banner.getDataList();
                            if (bannerDataList != null && bannerDataList.size() > 0) {
                                WFSubjectPackageDataDto bannerData = bannerDataList.get(0);
                                Map<String, String> picList = bannerData.getPicList();
                                if (picList != null) {
                                    favoriteDto.setPic(picList.get("400x250"));
                                }
                            }
                        }
                        favoriteDto.setFavoriteId(favorite.getFavorite_id()); // 设置favorite_id
                        favoriteDto.setFavoriteType(favorite.getFavorite_type());
                        favoriteDto.setGlobalid(wfSubjectId);
                        favoriteDtoList.add(favoriteDto);
                    }
                } else if ("5".equals(favorite.getFavorite_type())) {
                    // 普通专题
                    FavoriteListDto favoriteDto = new FavoriteListDto(favorite);
                    if (favorite != null) {
                        favoriteDto.setFavoriteId(favorite.getFavorite_id()); // 设置favorite_id
                        favoriteDto.setCategory(MessageUtils.getMessage(
                                CategoryConstants.SEARCH_CATEGORY_MAP.get(favorite.getCategory()),
                                commonParam.getLangcode()));
                        favoriteDtoList.add(favoriteDto);
                    }
                }
            }
        } else if ("10".equals(favoriteType)) { // 其他外网资源
            List<String> globalIdList = new LinkedList<String>(); //
            Map<String, String> globalIdAndFavoriteIdMap = new HashMap<String, String>();
            for (LetvUserFavorite favorite : favoriteList) {
                globalIdList.add(favorite.getGlobal_id());
                globalIdAndFavoriteIdMap.put(favorite.getGlobal_id(), favorite.getFavorite_id());
            }
            GenericDetailResponse albumDetail = videoService.getDetails(globalIdList,
                    (short) 0, (short) 0, commonParam); // 从作品库获取专辑详情
            if (albumDetail != null) {
                List<ResultDocInfo> docInfos = albumDetail.getDetails();
                if (!CollectionUtils.isEmpty(docInfos)) {
                    for (ResultDocInfo docInfo : docInfos) {
                        FavoriteListDto favoriteDto = null;
                        favoriteDto = getFavoriteDto(docInfo, commonParam);
                        if (favoriteDto != null) {
                            favoriteDto.setFavoriteId(globalIdAndFavoriteIdMap.get(docInfo.getLetv_original_id())); // 设置favorite_id
                            favoriteDtoList.add(favoriteDto);
                        }
                    }
                }
            }
        }
        return favoriteDtoList;
    }

    /**
     * 获取收藏图片，取图尺寸：640*360
     * @param img
     * @param albumPic
     * @return
     */
    public String getFavoritePic4Levidi(Map<String, String> img, String defaultPic) {
        String pic = this.getPic(img, "640", "360", null);
        if (StringUtil.isBlank(pic)) {
            String cmsImages = img.get("cmsImages");
            List<Map<String, String>> cmsImageMaps = JsonUtil.parse(cmsImages,
                    new LetvTypeReference<List<Map<String, String>>>() {
                    });
            if (cmsImageMaps != null && cmsImageMaps.size() > 0) {
                for (Map<String, String> cmsImageMap : cmsImageMaps) {
                    pic = this.getPic(cmsImageMap, "640", "360", null);// eros内容取640*360尺寸的图片
                    if (StringUtil.isNotBlank(pic)) {
                        break;
                    }
                }
            }
        }
        return pic == null ? defaultPic : pic;
    }

    /**
     * 拼接收藏dto，来源：自有内容
     * @param album
     * @param commonParam
     * @return
     */
    private FavoriteListDto getPlayFavoriteDto(AlbumMysqlTable album, CommonParam commonParam) {
        FavoriteListDto favoriteDto = new FavoriteListDto();
        // 客户端优先取400x300,如果没有取300x400
        String img = album.getPic(400, 300);
        if (img == null || "".equals(img)) {
            img = album.getPic(300, 400);
        }

        favoriteDto.setPic(img);
        favoriteDto.setScore(album.getScore());
        favoriteDto.setAlbumId(album.getId().toString()); // 设置专辑id
        favoriteDto.setTitle(album.getName_cn());
        // favoriteDto.setSubTitle(albumInfo.getSub_title());
        // favoriteDto.setFollownum(albumInfo.getFollownum());
        // favoriteDto.setEpisodes(albumInfo.getEpisode());
        favoriteDto.setVideoStatus(getVideoStatus4Favorite(album.getCategory(), album.getAlbum_type(),
                album.getFollownum(), album.getIs_end(), commonParam));
        return favoriteDto;
    }

    /**
     * 拼接收藏dto，来源：自有内容
     * @param album
     * @param commonParam
     * @return
     */
    private FavoriteListDto getPlayFavoriteDto4Le(AlbumMysqlTable album, CommonParam commonParam) {
        FavoriteListDto favoriteDto = new FavoriteListDto();
        // 优先取300x400,取不到则取688*800的图
        String img = album.getPic(300, 400);
        if (img == null || "".equals(img)) {
            img = album.getPic(600, 800);
        }
        favoriteDto.setPic(img);
        favoriteDto.setScore(album.getScore());
        favoriteDto.setAlbumId(album.getId().toString()); // 设置专辑id
        favoriteDto.setTitle(album.getName_cn());
        favoriteDto.setVideoStatus(getVideoStatus4Favorite(album.getCategory(), album.getAlbum_type(),
                album.getFollownum(), album.getIs_end(), commonParam));
        return favoriteDto;
    }

    /**
     * 拼接收藏dto，来源：作品库
     * @param docInfo
     * @param commonParam
     * @return
     */
    private FavoriteListDto getFavoriteDto(ResultDocInfo docInfo, CommonParam commonParam) {
        AlbumAttribute album = docInfo.getAlbum_attribute();
        if (album == null) {
            return null;
        }
        FavoriteListDto favoriteDto = new FavoriteListDto();
        LetvUserPlayList playList = new LetvUserPlayList();
        String img = null;
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            img = getFavoritePic4Levidi(album.getImages(), album.getAlbum_pic());
        } else { // 客户端优先取400x300,如果没有取300x400
            img = getPic(album.getImages(), "400", "300", album.getAlbum_pic());
            if (img == null || "".equals(img)) {
                img = getPic(album.getImages(), "300", "300", album.getAlbum_pic());
            }
        }
        favoriteDto.setPic(img);
        favoriteDto.setTitle(album.getName());
        favoriteDto.setSubTitle(album.getSub_name());
        favoriteDto.setFollownum(Integer.valueOf(album.getEpisodes()));
        favoriteDto.setEpisodes(Integer.valueOf(album.getEpisodes()));
        favoriteDto.setGlobalid(docInfo.getLetv_original_id());
        favoriteDto.setCid(docInfo.getCategory());
        favoriteDto.setVideoStatus(getVideoStatus4Favorite(StringUtil.toInteger(docInfo.getCategory()),
                album.getVideo_type(), Integer.valueOf(album.getEpisodes()), Integer.valueOf(album.getIs_end()),
                commonParam));
        favoriteDto.setSourceType(album.getSub_src());
        try {
            // for tvod icon type favorite no modify
            favoriteDto.setIconType(JumpUtil.getIconType((int) album.getSrc(), String.valueOf(album.getIs_pay()), null,
                    StringUtil.toInteger(docInfo.getCategory())));
        } catch (Exception e) {
            // 不处理
        }
        return favoriteDto;
    }

    /**
     * 获取客户端显示的视频状态
     * @param category
     * @param albumType
     * @param followNum
     * @param isEnd
     * @param commonParam
     * @return
     */
    private String getVideoStatus4Favorite(Integer category, Integer albumType, Integer followNum, Integer isEnd,
                                           CommonParam commonParam) {
        String videoStatus = "";
        if (category == null) {
            return videoStatus;
        }
        // 设置视频状态
        if (category == Category.FILM) { // 电影
            if (albumType != null && albumType == VideoType.ZHENG_PIAN) { // 正片
                videoStatus = "";

            } else {
                videoStatus = VideoCommonUtil.getVideoOrAlbumEpisodeText(3, null, null, commonParam.getLangcode());
            }
        } else if (category == Category.TV || category == Category.CARTOON) { // 电视剧或者动漫
            if (isEnd == 0) { // 未完结
                if ((followNum == null) && ((albumType == null) || (albumType != VideoType.ZHENG_PIAN))) {
                    // 尚没有下一集
                    videoStatus = VideoCommonUtil.getVideoOrAlbumEpisodeText(3, null, null, commonParam.getLangcode());
                } else {
                    videoStatus = VideoCommonUtil.getVideoOrAlbumEpisodeText(1, category, String.valueOf(followNum),
                            commonParam.getLangcode());
                }
            } else { // 已完结
                videoStatus = VideoCommonUtil.getVideoOrAlbumEpisodeText(2, category, String.valueOf(followNum),
                        commonParam.getLangcode());
            }
        } else if (category == Category.VARIETY && followNum != null) { // 综艺
            videoStatus = VideoCommonUtil.getVideoOrAlbumEpisodeText(2, category,
                    this.getShowFollowNum(followNum, "yyyy", TimeUtil.SHORT_DATE_FORMAT, TimeUtil.DATE_MM_DD_FORMAT1),
                    commonParam.getLangcode());
        }
        return videoStatus;
    }

    /**
     * 解析剧集显示格式，主要是针对综艺或动漫等剧集格式为日期的频道
     * @param followNum
     *            剧集
     * @param followFormat
     * @param showFollowFormat1
     *            剧集时间小于当前时间时显示的格式
     * @param showFollowFormat2
     *            剧集时间大于当前时间时显示的格式
     * @return
     */
    private String getShowFollowNum(Integer followNum, String followFormat, String showFollowFormat1,
                                    String showFollowFormat2) {
        try {
            String showFollowNum = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sFollowFormat = new SimpleDateFormat(followFormat);
            SimpleDateFormat sShowFollowFormat1 = new SimpleDateFormat(showFollowFormat1);
            SimpleDateFormat sShowFollowFormat2 = new SimpleDateFormat(showFollowFormat2);
            Date followDate = sdf.parse(followNum.toString());
            Date nowDate = new Date();
            String followStr = null;
            String nowStr = null;
            followStr = sFollowFormat.format(followDate);
            nowStr = sFollowFormat.format(nowDate);
            if (StringUtil.toInteger(followStr) < StringUtil.toInteger(nowStr)) {
                showFollowNum = sShowFollowFormat1.format(followDate);
            } else {
                showFollowNum = sShowFollowFormat2.format(followDate);
            }
            return showFollowNum;
        } catch (ParseException e) {
            LOG.error("getShowFollowNum|followNum:" + followNum + "|parse followNum error");
            return "";
        }
    }

    /**
     * 获取关注列表
     * @param followType
     * @param fromType
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public BaseResponse getFollowList(FOLLOW_TYPE followType, FOLLOW_FROM fromType, Integer page, Integer pageSize,
                                      CommonParam commonParam) {
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setToken(user.getUserToken());
            }
        }
        List<BaseData> followChannelData = null;// 关注信息列表
        Integer totalCount = 0;
        UserTpResponse<FollowListTp> tpResponse = null;
        List<FavoriteListDto> favoriteDtoList = new LinkedList<FavoriteListDto>();
        if (errorCode == null) {
            tpResponse = this.facadeTpDao.getUserTpDao().getFollowList(followType, fromType, page, pageSize,
                    commonParam); // 获取关注列表
            if (tpResponse == null || !tpResponse.isSucceed()) { // 接口调用失败,第三方返回code不为"200"
                errorCode = ErrorCodeConstants.USER_GET_ZTFAVORITELIST_ERROR;
            } else {
                FollowListTp followListTp = tpResponse.getData();
                if (followListTp != null && followListTp.getList() != null) {
                    List<String> idList = new LinkedList<String>();
                    List<FollowInfo> followInfoList = followListTp.getList();
                    for (FollowInfo followInfo : followInfoList) {
                        idList.add(followInfo.getFollow_id());
                    }
                    totalCount = followListTp.getCount();
                    followChannelData = channelService.getCpInfo(idList, idList, commonParam);
                }
            }
        }

        PageResponse<BaseData> response = new PageResponse<BaseData>();
        if (errorCode == null) {
            response.setData(followChannelData);
            response.setTotalCount(totalCount);
            response.setCurrentIndex(page);
        } else {
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            response.setErrMsg(errorMsg);
            LOG.info("getFolllowList_" + commonParam.getUsername() + " error. error code:" + errorCode
                    + ".error message:" + errorMsg);
        }
        return response;
    }

    /**
     * 添加订阅
     * @param followId
     *            订阅传cpId
     * @param followType
     *            关注类型,见{@link FOLLOW_TYPE}
     * @param fromType
     *            平台标识（1：移动端，3：TV端）,见{@link FOLLOW_FROM}
     * @param commonParam
     * @return
     */
    public Response<Boolean> addFollow(String followId, FOLLOW_TYPE followType, FOLLOW_FROM fromType,
                                       CommonParam commonParam) {
        boolean result = false;
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (errorCode == null) {
            if (errorCode == null) {
                UserTpResponse<String> addTp = this.facadeTpDao.getUserTpDao().addFollow(followId, followType,
                        fromType, commonParam);
                if (addTp != null && addTp.isSucceed()) { // 添加成功,第三方返回code为"200"
                    result = true;
                }
            }
        }

        Response<Boolean> response = new Response<Boolean>();
        if (errorCode != null) {
            String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(errorMsg);
            LOG.error("addFollow_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + followId
                    + "|Error code=" + errorCode + ", error message:" + errorMsg);
        } else {
            response.setData(result);
        }
        return response;
    }

    /**
     * 检查用户收藏状态
     * @param favoriteType
     * @param ztId
     * @param albumId
     * @param globalId
     * @param commonParam
     * @return
     */
    public Response checkIsFavorite(Integer favoriteType, Integer ztId, Long albumId, String globalId,
                                    CommonParam commonParam) {
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setToken(user.getUserToken());
            }
        }

        CheckPlaylistDto playListDto = null;
        Response response = new Response();
        if (errorCode == null) {
            APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
            LetvUserCommonFavoriteListTp addTp = this.facadeTpDao.getUserTpDao().checkIsFavorite(ztId, albumId,
                    favoriteType, globalId, appId, commonParam);
            if (addTp != null && "200".equals(addTp.getCode())) { // 成功,第三方返回code为"200"
                // TODO(liudaojie) 干掉老版本兼容
                if (CommonConstants.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam.getTerminalApplication())
                        && commonParam.getAppCode() != null && StringUtil.toInteger(commonParam.getAppCode(), 0) < 400) { // levidi老版本兼容
                    // 印度版无法从用户中心获取视频为追剧还是收藏，故写死fromtype=1，只有收藏
                    playListDto = new CheckPlaylistDto();
                    playListDto.setFromtype(1);
                    response.setData(playListDto);
                } else {
                    response.setData(true);
                }
            } else {
                // TODO(liudaojie) 干掉老版本兼容
                if (!(CommonConstants.TERMINAL_APPLICATION_LEVIDI
                        .equalsIgnoreCase(commonParam.getTerminalApplication()) && commonParam.getAppCode() != null && StringUtil
                        .toInteger(commonParam.getAppCode(), 0) < 400)) { // levidi老版本兼容
                    response.setData(false);
                }
            }
        }

        if (errorCode != null) {
            String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(errorMsg);
            LOG.error("checkIsFavorite_" + commonParam.getMac() + "_" + commonParam.getUserId() + " | errorCode="
                    + errorCode + ", error message:" + errorMsg);
        }
        return response;
    }

    /**
     * 检查用户关注状态
     * @param followId
     *            订阅传cpId
     * @param followType
     *            关注类型,见{@link FOLLOW_TYPE}
     * @param commonParam
     * @return
     */
    public Response checkIsFollow(String followId, FOLLOW_TYPE followType, CommonParam commonParam) {

        boolean result = false;
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (errorCode == null) {
            UserTpResponse<String> tpResponse = this.facadeTpDao.getUserTpDao().checkIsFollow(followId, followType,
                    commonParam);
            if (tpResponse != null && "403".equals(tpResponse.getCode())) { // 成功,第三方返回code为"403"为已经关注
                result = true;
            }
        }

        Response response = new Response();
        if (errorCode != null) {
            String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(errorMsg);
            LOG.error("checkIsFavorite_" + commonParam.getMac() + "_" + commonParam.getUserId() + " | errorCode="
                    + errorCode + ", error message:" + errorMsg);
        } else {
            response.setData(result);
        }
        return response;
    }

    /**
     * 添加收藏
     * @param ztId
     * @param albumId
     * @param favoriteType
     * @param product
     * @param fromtype
     * @param globalId
     * @param commonParam
     * @return
     */
    public Response<Boolean> addFavorite(Integer ztId, Long albumId, Integer favoriteType, Integer product,
                                         Integer fromtype, String globalId, CommonParam commonParam) {
        boolean result = false;
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (errorCode == null) {
            if (favoriteType == 1) { // 点播收藏，验证专辑是否有版权
                AlbumMysqlTable album = albumVideoAccess.getAlbum(albumId, commonParam); // 获取专辑信息
                if (album == null) {
                    errorCode = ErrorCodeConstants.USER_ADD_PLAYLIST_FALIURE;
                }
            }

            if (errorCode == null) {
                APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
                String region = commonParam.getWcode();
                LetvUserCommonFavoriteListTp addTp = this.facadeTpDao.getUserTpDao().addFavorite(ztId, albumId,
                        favoriteType, product, fromtype, globalId, appId, region, commonParam);
                if (addTp != null && "200".equals(addTp.getCode())) { // 添加成功,第三方返回code为"200"
                    result = true;
                }
            }
        }

        Response<Boolean> response = new Response<Boolean>();
        if (errorCode != null) {
            String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(errorMsg);
            LOG.error("addFavorite_" + commonParam.getMac() + "_" + commonParam.getUserId() + " | Error code="
                    + errorCode + ", error message:" + errorMsg);
        } else {
            response.setResultStatus(1);
            response.setData(result);
        }
        return response;
    }

    /**
     * 删除收藏信息
     * @param favoriteType
     * @param favoriteIds
     * @param ztId
     * @param albumId
     * @param globalId
     * @param operationType
     * @param commonParam
     * @return
     */
    public Response<Boolean> delFavorite(Integer favoriteType, String favoriteIds, String ztId, Long albumId,
                                         String globalId, String operationType, CommonParam commonParam) {
        boolean result = false;
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstant.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (errorCode == null) {
            if ("deleteAll".equalsIgnoreCase(operationType)) {// 全部删除
                if (favoriteType == 1 && !LocaleConstant.Wcode.HK.equalsIgnoreCase(commonParam.getWcode())) {// 追剧与收藏删除的不是用户中心的收藏，所以此处需分开处理
                    PlayListCommonTp addTp = this.facadeTpDao.getUserTpDao().deleteAllPlaylist(
                            commonParam.getUsername(), commonParam);
                    if (addTp != null && "200".equals(addTp.getCode())) { // 添加成功,第三方返回code为"200"
                        result = true;
                    }
                } else {
                    final Integer flush = 1; // 清空
                    APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
                    LetvUserCommonFavoriteListTp addTp = this.facadeTpDao.getUserTpDao().multiDelFavorite(favoriteType,
                            flush, favoriteIds, appId, commonParam);
                    if (addTp != null && "200".equals(addTp.getCode())) { // 添加成功,第三方返回code为"200"
                        result = true;
                    }
                }
            } else { // 删除单条或多条
                if (favoriteType == 1 && !LocaleConstant.Wcode.HK.equalsIgnoreCase(commonParam.getWcode())) {// 追剧与收藏，如果使用此接口，则需要特殊处理
                    Long favoriteId = 0L;// 追剧与收藏的单条删除
                    if (!StringUtil.isBlank(favoriteIds)) {
                        String[] favorites = favoriteIds.split(",");
                        favoriteId = StringUtil.toLong(favorites[0], 0L);
                    }
                    PlayListCommonTp addTp = this.facadeTpDao.getUserTpDao().deletePlaylist(favoriteId, commonParam);
                    if (addTp != null && "200".equals(addTp.getCode())) { // 添加成功,第三方返回code为"200"
                        result = true;
                    }
                } else if (StringUtil.isBlank(operationType)
                        && (StringUtils.isNotBlank(ztId) || StringUtils.isNotBlank(globalId) || albumId != null)) { // 删除单条记录
                    APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
                    LetvUserCommonFavoriteListTp addTp = this.facadeTpDao.getUserTpDao().delFavorite(ztId, albumId,
                            globalId, favoriteType, appId, commonParam);
                    if (addTp != null && "200".equals(addTp.getCode())) { // 删除成功,第三方返回code为"200"
                        result = true;
                    }
                } else {// 删除多条记录
                    final Integer flush = 0; // 不清空，批量删除。此时favoriteIds必传
                    APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
                    LetvUserCommonFavoriteListTp addTp = this.facadeTpDao.getUserTpDao().multiDelFavorite(favoriteType,
                            flush, favoriteIds, appId, commonParam);
                    if (addTp != null && "200".equals(addTp.getCode())) { // 删除成功,第三方返回code为"200"
                        result = true;
                    }
                }
            }
        }

        Response<Boolean> response = new Response<Boolean>();
        if (errorCode != null) {
            String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(errorMsg);
            LOG.error("delFavorite_" + commonParam.getMac() + "_" + commonParam.getUsername() + "_" + favoriteType
                    + "_" + operationType + "_" + favoriteIds + "_" + albumId + "_" + ztId + "_" + globalId
                    + " | errorCode=" + errorCode + ", error message:" + errorMsg);
        } else {
            response.setResultStatus(1);
            response.setData(result);
        }

        return response;
    }

    /**
     * 检查用户关注状态
     * @param followId
     *            订阅传cpId
     * @param followType
     *            关注类型,见{@link FOLLOW_TYPE}
     * @param commonParam
     * @return
     */
    public Response<Boolean> delFollow(String followId, FOLLOW_TYPE followType, CommonParam commonParam) {
        boolean result = false;
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstant.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }

        if (errorCode == null) {
            UserTpResponse<String> tpResponse = this.facadeTpDao.getUserTpDao().delFollow(followId, followType,
                    commonParam);
            if (tpResponse != null && tpResponse.isSucceed()) { // 删除成功
                result = true;
            }
        }

        Response<Boolean> response = new Response<Boolean>();
        if (errorCode != null) {
            String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(errorMsg);
            LOG.error("delFollow_" + commonParam.getMac() + "_" + commonParam.getUserId() + " | errorCode=" + errorCode
                    + ", error message:" + errorMsg);
        } else {
            response.setResultStatus(1);
            response.setData(result);
        }

        return response;
    }

    /**
     * 删除专题收藏信息
     * @param operationType
     * @return
     */
    @Deprecated
    public Response<Boolean> delFavoriteByZtId(String username, String ztId, Integer favoriteType,
                                               String operationType, String global_id, CommonParam commonParam) {
        Response<Boolean> response = new Response<Boolean>();
        String logPrefix = "delFavoriteByZtId_" + username + "_" + operationType;
        String errorCode = null;
        Locale locale = new Locale(commonParam.getLangcode());
        if (StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]: user not login.");
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (errorCode == null) {
            APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
            LetvUserCommonFavoriteListTp addTp = this.facadeTpDao.getUserTpDao().delFavorite(ztId, null, global_id,
                    favoriteType, appId, commonParam);
            response.setResultStatus(1);
            if (addTp != null && "200".equals(addTp.getCode())) { // 添加成功,第三方返回code为"200"
                response.setData(true);
            } else {
                response.setData(false);
            }
        } else {
            response = (Response<Boolean>) this.setErrorResponse(response, errorCode, locale);
        }

        return response;
    }

    /**
     * 获取弹幕列表
     * @param videoId
     * @param playTime
     * @param albumId
     * @param param
     * @return
     */
    public DanmuDto getDanmuList(Long videoId, Long playTime, Long albumId, CommonParam param) {
        long s = System.currentTimeMillis();
        DanmuDto danmuDto = null;
        if (videoId != null && videoId != 0) {
            DanmuListResponse danmuTpList = this.facadeTpDao.getDanmuTpDao().getDanmuList(videoId, playTime, param);
            LOG.info("danmu===getDanmuList====1,waste time " + (System.currentTimeMillis() - s));
            DanmuCountResponse count = this.facadeTpDao.getDanmuTpDao().getDanmuCount(videoId, albumId);
            LOG.info("danmu===getDanmuCount====1,waste time " + (System.currentTimeMillis() - s));
            if (danmuTpList != null && danmuTpList.getData() != null) {
                danmuDto = new DanmuDto();
                List<DanmuResponse> danmu = new ArrayList<DanmuResponse>();
                danmuDto.setDanmu(danmu);
                DanmuListInnerResponse danmuInnerTp = danmuTpList.getData();
                Danmu tmp = null;
                if (!CollectionUtils.isEmpty(danmuInnerTp.getUser())) {
                    danmu.addAll(danmuInnerTp.getUser());
                }
                LOG.info("danmu===addAll====1,waste time " + (System.currentTimeMillis() - s));
                if (!CollectionUtils.isEmpty(danmuInnerTp.getList())) {
                    danmu.addAll(danmuInnerTp.getList());
                }
                LOG.info("danmu===addAl2l====1,waste time " + (System.currentTimeMillis() - s));
                if (count != null && count.getData() != null) {
                    danmuDto.setDmPidCount(count.getData().getPidCount());
                    danmuDto.setDmVidCount(count.getData().getVidCount());
                    if (count.getData().getVidCount() != null) {
                        if (100 < count.getData().getVidCount() && count.getData().getVidCount() < 1000) {
                            danmuDto.setDmNotice(count.getData().getVidCount() + UserConstants.dmNoticeMin);
                        } else if (count.getData().getVidCount() >= 1001) {
                            danmuDto.setDmNotice(count.getData().getVidCount() + UserConstants.dmNoticeMax);
                        }
                    }
                }
                LOG.info("danmu===end====1,waste time " + (System.currentTimeMillis() - s));
            }
        } else {
            LOG.info("getDanmuList_ videoId is null");
        }
        return danmuDto;
    }

    /**
     * 点播弹幕发送
     * @param vid
     *            视频id
     * @param pid
     *            专辑id
     * @param cid
     *            频道
     * @param start
     *            弹幕对应视频播放时间
     * @param txt
     *            发送内容
     * @param color
     *            内容颜色值，例如：00FCFF
     * @param font
     *            字体大小［s:小号 m:中号 l:大号］默认值：m
     * @param type
     *            弹幕类型［txt:文字 em:表情 redpaper:红包 vote:投票 recommend:推荐
     *            ］默认值：txt［备注：表情类型的弹幕无需审核］
     * @param position
     *            显示的位置 ［1:顶部 2:中间 3:底部 4:随机］默认值：4
     * @param x
     *            pc端表情x坐标
     * @param y
     *            pc端表情y坐标
     * @param from
     *            来源［1:Web 2:iPhone 3:iPad4:TV 5:PC桌面 6:Android
     *            Phone7:LePhone］，默认值：1
     * @param callback
     *            跨域
     * @param ip
     *            如客户端：TV、移动等设备必须传递用户的IP，否则IP禁言会封代理服务器的IP，导致该代理服务器的IP不可以发布弹幕
     * @param isIdentify
     *            如需判断是否实名认证此参数请传1
     * @param sso_tk
     *            用户token
     * @param param
     *            通用参数
     * @return
     */
    public Response<DanmuChatDto> sendDanmuMsg(Integer vid, Integer pid, Integer cid, Integer start, String txt,
                                               String color, String font, String type, Integer position, Integer from, String callback, String ip,
                                               Integer isIdentify, String x, String y, CommonParam param) {
        Response<DanmuChatDto> response = new Response<DanmuChatDto>();
        LOG.info("sendDanmuMsg_userId=" + param.getUserId() + "-" + param.getUserToken() + "===txt=" + txt);
        ChatMsgCommonResposne<String> r = this.facadeTpDao.getDanmuTpDao().sendDanmuMsgV2(vid, pid, cid, start, txt,
                color, font, type, position, from, callback, ip, isIdentify, x, y, param);
        if (r != null && UserTpConstant.USERCENTER_DANMU_RESULT_CODE_200.equals(r.getCode())) {
            // 发送成功，200--实际成功；405--未进行实名认证，产品需求为未通过实名认证的客户端不做提示，服务端修改为成功，（2016-05-03，产品负责人-纪璇）
            response.setResultStatus(1);
            DanmuChatDto data = new DanmuChatDto(1);
            response.setData(data);
        } else {
            // 发送失败
            String code = r == null ? null : r.getCode();
            response.setResultStatus(0);
            DanmuChatDto data = new DanmuChatDto(0);
            data.setCode(code);
            response.setData(data);
            response.setErrMsg(MessageUtils.getMessage(UserConstants.getDanmuResultMsgKeyByCode(code),
                    param.getLangcode()));
        }

        return response;
    }

    /**
     * 直播弹幕发送
     * @param roomId
     *            聊天室id
     * @param message
     *            消息内容
     * @param sso_tk
     *            用户token
     * @param forhost
     *            是否向嘉宾提问
     * @param color
     *            内容颜色值，例如：00FCFF
     * @param font
     *            字体大小［s:小号 m:中号 l:大号］默认值：m
     * @param position
     *            显示的位置 ［1:顶部 2:中间 3:底部 4:随机］默认值：4
     * @param from
     *            来源［1:Web 2:iPhone 3:iPad4:TV 5:PC桌面 6:Android
     *            Phone7:LePhone］，默认值：1
     * @param callback
     *            跨域
     * @param type
     *            类型 [1:普通消息 2:pc端带表情的消息 9:聊天室公告] 默认值1
     * @param realname
     *            实名制认证 1需要认证 其他值，不进行实名验证
     * @param x
     *            pc端表情x坐标
     * @param y
     *            pc端表情y坐标
     * @param vtkey
     *            join 聊天室时，socket服务器返回的vtkey
     * @param param
     *            通用参数
     * @return
     */
    public Response<DanmuChatDto> sendChatMsg(String roomId, String message, Boolean forhost, String color,
                                              String font, Integer position, Integer from, String callback, Integer type, Integer realname, String x,
                                              String y, String vtkey, CommonParam param) {
        Response<DanmuChatDto> response = null;
        LOG.info("sendChatMsg=======token=" + param.getUserId() + "-" + param.getUserToken() + "===message=" + message);
        LiveSendChatMsgResposne r = this.facadeTpDao.getDanmuTpDao().sendChatMsg(param.getUserId(), roomId,
                param.getClientIp(), forhost, message, param.getUserToken(), color, font, position, from, callback,
                type, realname, x, y, vtkey);
        DanmuChatDto data = null;
        if (r != null && r.getCode() != null) {
            response = new Response<DanmuChatDto>();
            if (r.getCode() != 200 && r.getData() != null) {
                response.setResultStatus(ErrorCodeCommonUtil.RESPONSE_FAILURE);
                response.setErrCode(ErrorCodeConstants.LIVE_CHANNEL_SEND_DANMU_CONTENT_ERROR);
                response.setData(new DanmuChatDto(0));
                response.setErrMsg((String) r.getData().get("errorMessage"));
            } else {
                response.setResultStatus(1);
                response.setData(new DanmuChatDto(1));
            }
        } else {
            response = (Response<DanmuChatDto>) ErrorCodeCommonUtil.setErrorResponse(response,
                    ErrorCodeConstants.LIVE_CHANNEL_SEND_DANMU_FAILED, param.getLangcode());
            response.setData(new DanmuChatDto(0));
        }

        return response;
    }

    /**
     * get user info for golive application
     * @param from
     * @param sign
     * @param commonParam
     * @return
     */
    public Response<LetvUserInfoDto> getUserInfo(String from, String sign, CommonParam commonParam) {
        LetvUserInfoDto userInfoDto = new LetvUserInfoDto();
        String errorCode = null;

        StringBuilder sb = new StringBuilder(commonParam.getUserId()).append("&").append(from).append("&")
                .append(UserConstants.USER_GET_USERINFO_SIGN_KEY);
        String value = "";
        try {
            value = MessageDigestUtil.md5(sb.toString().getBytes(CommonConstants.UTF8));
        } catch (NoSuchAlgorithmException e) {
            LOG.info("getUserInfo_" + commonParam.getMac() + "_" + commonParam.getMac() + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            LOG.info("getUserInfo_" + commonParam.getMac() + "_" + commonParam.getMac() + e.getMessage(), e);
        }
        if (value != null && value.equals(sign)) {
            // get user info by userId
            GetUserInfoTpResponse tpResponse = this.facadeTpDao.getUserTpDao().getUserInfoByUserid(commonParam);
            if (tpResponse != null && tpResponse.getBean() != null) {
                GetUserInfoTpResponse.UserInfoDetailTpResponse bean = tpResponse.getBean();
                userInfoDto.setMobile(bean.getMobile());
                userInfoDto.setEmail(bean.getEmail());
                userInfoDto.setNickname(bean.getNickname());
            } else {// get user info failure
                errorCode = ErrorCodeConstants.USER_GET_USER_INFO_FAILURE;
                LOG.info("getUserInfo_" + commonParam.getMac() + "_" + commonParam.getMac() + "[errorCode=" + errorCode
                        + "],get user info failure.");
            }
        } else {// md5 sign check failure
            errorCode = ErrorCodeConstants.USER_GET_USERINFO_SIGN_ERROR;
            LOG.info("getUserInfo_" + commonParam.getMac() + "_" + commonParam.getMac() + "[errorCode=" + errorCode
                    + "],get user info sign failure.");
        }

        Response<LetvUserInfoDto> response = new Response<LetvUserInfoDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(userInfoDto);
        }

        return response;
    }

    /**
     * Get eros token. 获取流程：先从缓存中取，如果缓存中取不到，则再从eros取最新的token，然后放入缓存中 userToken)
     * as well.
     * @param userName
     *            用户名
     * @param userToken
     *            用户token
     * @param deviceType
     * @param terminalSeries
     *            set deviceType by using this attribute if deviceType is null
     * @return
     */
    public ErosToken getErosToken(String userId, String userToken, Integer deviceType, String terminalSeries) {
        ErosToken erosToken = null;
        if (deviceType == null) { // 未传递deviceType,根据terminalSeries来设置deviceType
            if (UserConstants.hignStreamDeviceList.contains(terminalSeries)) { // 如果高码流列表中有此型号，设置为高码流
                deviceType = UserConstants.ErosConstant.DeviceType.HIGNSTREAM;
            } else { // 未传递terminalSeries，则更具deviceType来判断
                deviceType = UserConstants.ErosConstant.DeviceType.LOWSTREAM;
            }
        }

        // get eros-token from cache
        if (StringUtils.isNotBlank(userId)) {
            erosToken = this.facadeCacheDao.getUserCacheDao().getErosToken(userId, deviceType);
        }
        if (erosToken == null) {
            // get eros-token from eros
            erosToken = this.getErosTokenFromEros(userId, userToken, deviceType);
            // update eros-token
            this.updateErosToken(userId, deviceType, erosToken);
        }
        return erosToken;
    }

    /**
     * update eros-token
     * @param userId
     * @param deviceType
     * @param erosToken
     * @return
     */
    public boolean updateErosToken(String userId, Integer deviceType, ErosToken erosToken) {
        if (StringUtils.isNotBlank(userId) && deviceType != null && erosToken != null) {
            int result = this.facadeCacheDao.getUserCacheDao().setErosToken(userId, deviceType, erosToken);
            if (result == CacheConstants.SUCCESS) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public ErosToken getErosTokenFromEros(String userId, String userToken, Integer deviceType) {
        ErosToken erosToken = null;
        OauthCodeResponse oauthCodeResponse = null;
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken) && deviceType != null) { // 获取用户oAuthCode
            // get user auth by user token
            oauthCodeResponse = this.facadeTpDao.getUserTpDao().getOauthCode(userToken);
            if ((oauthCodeResponse != null) && oauthCodeResponse.isSucceeded()) {
                OauthCode oAuthCode = oauthCodeResponse.getResult();
                ErosTokenResponse erosTokenResponse = null;
                if ((oAuthCode != null) && (oAuthCode.getCode() != null)) {
                    erosTokenResponse = this.facadeTpDao.getUserTpDao().getErosToken(userId, deviceType,
                            oAuthCode.getCode()); // 获取eros-token
                    long endGetErosTokenTime = System.currentTimeMillis();
                }
                if ((erosTokenResponse != null) && StringUtils.isNotBlank(erosTokenResponse.getToken())) {
                    erosToken = new ErosToken();
                    erosToken.setToken(erosTokenResponse.getToken());
                    erosToken.setTokenSecret(erosTokenResponse.getToken_secret());
                    erosToken.setExpireTime(TimeUtil.getTimestampAfterDays(90)); // 有效期九十天
                    return erosToken;
                }
            }
        }
        return null;
    }

    public PageCommonResponse<PlayLogListDto> getPlaylog4Levidi(Integer page, Integer pageSize, Integer intervalTime,
                                                                Long roleid, Integer type, Integer from, CommonParam commonParam) {
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserId()) || StringUtils.isEmpty(commonParam.getUserToken())) {
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || user.getUserId() == 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info("getPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: user not exist.");
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
                commonParam.setUserToken(user.getUserToken());
            }
        }
        PlayLogTp playTp = null;
        if (errorCode == null) {
            APPID appId = APPID.getAppId(commonParam.getTerminalApplication());
            String region = commonParam.getWcode(); // levidi区分地区
            playTp = this.facadeTpDao.getUserTpDao().getPlaylogList(page, pageSize, intervalTime, roleid, type, appId,
                    from, region, commonParam);
            if (playTp == null || !"200".equalsIgnoreCase(playTp.getCode()) || playTp.getData() == null) {
                // 第三方成功返回code200
                errorCode = ErrorCodeConstants.USER_GET_PLAYLOG_FAILURE;
                LOG.warn("getSarrPlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId() + "[errorCode="
                        + errorCode + "]: get Playlog failed.");
            }
        }

        PageCommonResponse<PlayLogListDto> response = new PageCommonResponse<PlayLogListDto>();
        if (errorCode == null) {
            PageControl<PlayLogListDto> pageControl = new PageControl<PlayLogListDto>();
            List<PlayLogInfo> playlogList = playTp.getData().getItems();
            List<PlayLogListDto> dtoList = new LinkedList<PlayLogListDto>();

            if (playlogList != null) {
                List<String> lidList = new LinkedList<String>();
                List<String> aGlobalIdList = new LinkedList<String>();
                for (PlayLogInfo playlogInfo : playlogList) {
                    String lid = playlogInfo.getLid();
                    if (StringUtil.isNotBlank(lid)) {
                        lidList.add(lid);
                    }
                }

                // 获取视频详情list
                GenericDetailResponse videoDetaiResponse = videoService.getDetails(lidList,
                        (short) 0, (short) 0, commonParam);

                // 获取aGlobalIdList,以此查询专辑详情list
                if (videoDetaiResponse != null) {
                    List<ResultDocInfo> docVideoInfos = videoDetaiResponse.getDetails();
                    if (docVideoInfos != null && docVideoInfos.size() > 0) {
                        for (ResultDocInfo docVideoInfo : docVideoInfos) {
                            if (docVideoInfo != null) {
                                VideoAttribute video = docVideoInfo.getVideo_attribute();
                                if (video != null) {
                                    String aGlobalId = video.getAlbum_original_id();
                                    if (StringUtil.isNotBlank(aGlobalId)) {
                                        aGlobalIdList.add(aGlobalId);
                                    }
                                }
                            }
                        }
                    }
                }

                // 获取专辑详情list
                GenericDetailResponse albumDetaiResponse = videoService.getDetails(
                        aGlobalIdList, (short) 0, Short.MAX_VALUE, commonParam);

                if (videoDetaiResponse != null) {
                    List<ResultDocInfo> videoDocVideoInfos = videoDetaiResponse.getDetails();
                    if (videoDocVideoInfos != null && videoDocVideoInfos.size() > 0) {
                        for (PlayLogInfo playlogInfo : playlogList) {
                            String lid = playlogInfo.getLid();
                            if (StringUtil.isNotBlank(lid)) {
                                for (ResultDocInfo videoDocVideoInfo : videoDocVideoInfos) {
                                    if (videoDocVideoInfo != null) {
                                        String vGlobalId = videoDocVideoInfo.getLetv_original_id();
                                        if (vGlobalId != null && vGlobalId.equals(lid)) { // 播放记录的lid和视频详情列表的globalid匹配成功
                                            VideoAttribute videoAttribute = videoDocVideoInfo.getVideo_attribute();
                                            if (videoAttribute != null) {
                                                String aGlobalId = videoAttribute.getAlbum_original_id();
                                                PlayLogListDto playLogListDto = null;
                                                ResultDocInfo albumDocVideoInfo = null;
                                                AlbumAttribute albumAttribute = null;
                                                VideoAttributeInAlbum videoAttributeInAlbum = null;
                                                if (StringUtil.isNotBlank(aGlobalId)) { // aglobalId不为空，取专辑内的视频
                                                    if (albumDetaiResponse != null) {
                                                        List<ResultDocInfo> albumDocVideoInfos = albumDetaiResponse
                                                                .getDetails();
                                                        if (!CollectionUtils.isEmpty(albumDocVideoInfos)) {
                                                            for (ResultDocInfo albumDocInfo : albumDocVideoInfos) {
                                                                if (albumDocInfo != null) {
                                                                    String albumOriginId = albumDocInfo
                                                                            .getLetv_original_id();
                                                                    if (aGlobalId.equals(albumOriginId)) {
                                                                        albumDocVideoInfo = albumDocInfo;
                                                                        albumAttribute = albumDocInfo
                                                                                .getAlbum_attribute();
                                                                        if (albumAttribute != null) {
                                                                            List<VideoAttributeInAlbum> videoAttributeInAlbumList = albumAttribute
                                                                                    .getVideo_list();
                                                                            if (!CollectionUtils
                                                                                    .isEmpty(videoAttributeInAlbumList)) {
                                                                                for (VideoAttributeInAlbum videoAttributeInAlbum2 : videoAttributeInAlbumList) {
                                                                                    if (videoAttributeInAlbum2 != null
                                                                                            && lid.equals(videoAttributeInAlbum2
                                                                                            .getLetv_original_id())) {
                                                                                        videoAttributeInAlbum = videoAttributeInAlbum2;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                        break;// 跳出lid和vGlobalId的比较
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    if (albumAttribute == null) {
                                                        LOG.info("getPlaylog4Levidi_" + commonParam.getMac() + "_"
                                                                + commonParam.getUserId() + "album info is null");
                                                    }
                                                    playLogListDto = new PlayLogListDto(playlogInfo, albumDocVideoInfo,
                                                            albumAttribute, videoAttributeInAlbum, commonParam);
                                                } else { // aglobalId为空,单视频
                                                    playLogListDto = new PlayLogListDto(playlogInfo, videoDocVideoInfo,
                                                            videoAttribute, commonParam);
                                                }
                                                playLogListDto.setRoleid(roleid == null ? 0 : roleid);
                                                dtoList.add(playLogListDto);
                                            }
                                            break; // 跳出lid和vGlobalId的比较
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                pageControl.setList(dtoList);
                pageControl.setCount(dtoList.size());
                pageControl.setCurrentPage(page);
                pageControl.setPageSize(pageSize);

                int pageCount = pageControl.getCount() / pageControl.getPageSize();
                if (pageControl.getCount() % pageControl.getPageSize() > 0) {
                    pageCount += 1;
                }

                pageControl.setPageCount(pageCount);
                response = new PageCommonResponse<PlayLogListDto>(pageControl);
                response.setResultStatus(1);
            }
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }
        return response;
    }

    /**
     * 获取播放记录图片，取图尺寸：640*360
     * @param img
     * @param albumPic
     * @return
     */
    public String getPlayLogPic4Sarrs(Map<String, String> img, String defaultPic) {
        String pic = this.getPic(img, "640", "360", null);// eros内容取640*360尺寸的图片
        // String[] cmsImages
        if (StringUtil.isBlank(pic)) {
            String cmsImages = img.get("cmsImages");
            List<Map<String, String>> cmsImageMaps = JsonUtil.parse(cmsImages,
                    new LetvTypeReference<List<Map<String, String>>>() {
                    });
            if (cmsImageMaps != null && cmsImageMaps.size() > 0) {
                for (Map<String, String> cmsImageMap : cmsImageMaps) {
                    pic = this.getPic(cmsImageMap, "640", "360", null);// eros内容取640*360尺寸的图片
                    if (StringUtil.isNotBlank(pic)) {
                        break;
                    }
                }
            }
        }
        if (pic == null) {
            pic = this.getPic(img, "960", "540", defaultPic);// 内网专辑取960*540尺寸的图片
        }
        return pic;
    }

    private String getPic(Map<String, String> img, String width, String height, String defaultPic) {
        if (img == null) {
            return defaultPic;
        }
        if (StringUtil.isNotBlank(img.get(width + "*" + height))) {
            return img.get(width + "*" + height);
        }
        return defaultPic;
    }

    public Response<CommonResultDto<Map<String, Integer>>> checkRolePlayList(String albumIds, String roleId,
                                                                             CommonParam commonParam) {
        Response<CommonResultDto<Map<String, Integer>>> response = new Response<CommonResultDto<Map<String, Integer>>>();
        CommonResultDto<Map<String, Integer>> data = new CommonResultDto<Map<String, Integer>>();
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        String logPrefix = "checkRolePlayList_albumIds" + albumIds + commonParam.getMac();
        String errorCode = null;
        if (StringUtil.isNotBlank(albumIds) && StringUtil.isNotBlank(roleId)) {
            RolePlayListCheckTPResponse tpResponse = this.facadeTpDao.getUserTpDao().checkPlayList(albumIds, roleId,
                    commonParam, logPrefix);
            if (tpResponse == null || !"200".equals(tpResponse.getCode())) {
                // 接口调用失败
                errorCode = ErrorCodeConstants.USER_CHECK_ROLEPLAYLIST_ERROR;
                ResponseUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
            } else {
                resultMap = tpResponse.getData();
                data.setValue(resultMap);
                response.setData(data);
            }
        }
        return response;
    }

    public Response<UserCommentDto> getCommentList(Integer dataType, String albumId, String videoId, Integer page,
                                                   Integer pageSize, Integer source, CommonParam commonParam) {
        String errorCode = null;
        Response<UserCommentDto> response = new Response<UserCommentDto>();
        UserCommentDto userCommentDto = new UserCommentDto();
        List<UserCommentDto.CommentInfo> commentInfoList = new LinkedList<UserCommentDto.CommentInfo>();// 设置最新评论列表
        List<UserCommentDto.CommentInfo> hotCommentInfoList = new LinkedList<UserCommentDto.CommentInfo>();// 设置热门评论列表
        userCommentDto.setCommentList(commentInfoList);
        userCommentDto.setHotCommentList(hotCommentInfoList);
        // le过滤评论（临时,直接返回response客户端报错）
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            response.setData(userCommentDto);
            return response;
        }
        LetvUserCommentResponse letvUserCommentResponse = null;
        if (errorCode == null) {
            String ctype = "cmt,img,vote";// 设置为获取cmt/img/votel类型的评论
            letvUserCommentResponse = this.facadeTpDao.getUserTpDao().getCommentList(dataType, videoId, albumId, page,
                    pageSize, source, ctype, commonParam); // 获取评论数据
            if (letvUserCommentResponse.isSucceed()) { // 成功
                LetvUserCommentResponse.CommentInfo[] responseCommentInfoList = letvUserCommentResponse.getData(); // 获取最新评论数据
                if (responseCommentInfoList != null && responseCommentInfoList.length > 0) {
                    for (LetvUserCommentResponse.CommentInfo repsoneCommentInfo : responseCommentInfoList) { // 设置最新评论
                        UserCommentDto.CommentInfo commentInfo = this.covertCommintInfo(repsoneCommentInfo);
                        if (commentInfo != null) {
                            commentInfoList.add(commentInfo);
                        }
                    }
                }
                LetvUserCommentResponse.CommentInfo[] responseHotCommentInfoList = letvUserCommentResponse.getGodData();// 获取热门评论数据
                if (responseHotCommentInfoList != null && responseHotCommentInfoList.length > 0) {
                    for (LetvUserCommentResponse.CommentInfo repsoneCommentInfo : responseHotCommentInfoList) { // 设置热门评论
                        UserCommentDto.CommentInfo commentInfo = this.covertCommintInfo(repsoneCommentInfo);
                        if (commentInfo != null) {
                            hotCommentInfoList.add(commentInfo);
                        }
                    }
                }
            } else {
                errorCode = ErrorCodeConstants.USER_GET_COMMENT_FAILURE;
                LOG.error("getCommentList_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + albumId
                        + "_" + videoId + "[errorCode=" + errorCode + "]: get Comment failed.");
            }
        }

        if (errorCode == null) {
            userCommentDto.setPageInfo(letvUserCommentResponse.getTotal(), page, pageSize);
            response.setData(userCommentDto);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }
        return response;
    }

    private UserCommentDto.CommentInfo covertCommintInfo(LetvUserCommentResponse.CommentInfo repsoneCommentInfo) {
        if (repsoneCommentInfo == null) {
            return null;
        }
        UserCommentDto.CommentInfo commentInfo = new UserCommentDto.CommentInfo();
        commentInfo.setId(repsoneCommentInfo.get_id());
        commentInfo.setContent(repsoneCommentInfo.getContent());
        commentInfo.setCtime(repsoneCommentInfo.getCtime());
        commentInfo.setVtime(repsoneCommentInfo.getVtime());
        commentInfo.setLike(repsoneCommentInfo.getLike());
        LetvUserCommentResponse.User responseUserInfo = repsoneCommentInfo.getUser();
        if (responseUserInfo != null) { // 设置用户信息
            UserCommentDto.UserInfo userInfo = new UserCommentDto.UserInfo();
            userInfo.setUid(responseUserInfo.getUid());
            userInfo.setUsername(responseUserInfo.getUsername());
            userInfo.setPicture(responseUserInfo.getPhoto());
            commentInfo.setUser(userInfo);
        }
        return commentInfo;
    }

    /**
     * 获取用户订阅列表
     * @param userToken
     * @return
     */

    public List<String> getUserSubscribeIds(FOLLOW_TYPE followType, Integer page, Integer pageSize,
                                            CommonParam commonParam) {
        List<String> ids = null;
        UserTpResponse<FollowListTp> res = this.facadeTpDao.getUserTpDao().getFollowList(followType,
                FOLLOW_FROM.TV, page, pageSize, commonParam);
        if (res == null) {
            return ids;
        }
        FollowListTp followList = res.getData();
        if (followList == null) {
            return ids;
        }
        List<FollowInfo> infos = followList.getList();
        if (infos == null || infos.size() == 0) {
            return ids;
        }
        ids = new ArrayList<String>();
        for (FollowInfo info : infos) {
            ids.add(info.getFollow_id());
        }
        return ids;
    }

    /**
     * 添加收藏新接口
     * @param globalId
     * @param categoryId
     * @param commonParam
     * @return
     */
    public Response<Boolean> addCollection(String globalId, Integer categoryId, CommonParam commonParam) {
        boolean result = false;
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (errorCode == null) {
            if (categoryId == null) {
                categoryId = this.getCategory(globalId, commonParam);
            }
            CollectionTpResponse<Object[]> collectionTpResponse = this.facadeTpDao.getUserTpDao().addCollection(
                    globalId, categoryId, commonParam);
            if (collectionTpResponse != null && collectionTpResponse.isSucceed()) { // 第三方响应成功
                if (collectionTpResponse.getData() != null && collectionTpResponse.getData().length == 0) { // data为空时表示添加成功
                    CollectionListInfo.CollectionInfo collectionInfo = new CollectionListInfo.CollectionInfo();
                    collectionInfo.setLid(globalId);
                    this.facadeCacheDao.getUserCacheDao().setUserNewAddCollection(commonParam.getUserId(),
                            collectionInfo); // 缓存收藏数据
                    LOG.error("addCollection_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + "|add cache globalId:" + globalId);
                    result = true;
                }
            } else {
                errorCode = ErrorCodeConstants.USER_ADD_COLLECTION_FAILURE;
            }
        }
        Response<Boolean> response = new Response<Boolean>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            LOG.error("addCollection_" + commonParam.getMac() + "_" + commonParam.getUserId() + "|Error code="
                    + errorCode);
        } else {
            response.setData(result);
        }
        return response;
    }

    /**
     * 获取category分类id
     * @param globalId
     * @param commonParam
     * @return
     */
    private Integer getCategory(String globalId, CommonParam commonParam) {
        if (StringUtil.isNotBlank(globalId)) {
            if (globalId.startsWith("110_")) { // 内网专辑
                String albumId = globalId.substring(4);
                AlbumMysqlTable album = albumVideoAccess.getAlbum(StringUtil.toLong(albumId),
                        commonParam);
                if (album != null) {
                    return album.getCategory();
                }
            } else if (globalId.startsWith("101_")) { // 内网视频
                String videoId = globalId.substring(4);
                VideoMysqlTable video = albumVideoAccess.getVideo(StringUtil.toLong(videoId),
                        commonParam);
                if (video != null) {
                    return video.getCategory();
                }
            }
        }
        return null;
    }

    /**
     * 获取收藏列表新接口
     * @param type
     * @param categoryId
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public BaseResponse getCollectionList(String type, Integer categoryId, Integer page, Integer pageSize,
                                          CommonParam commonParam) {
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        CollectionTpResponse<CollectionListInfo> collectionTpResponse = null;
        List<FavoriteListDto> favoriteDtoList = null;
        Integer total = null;
        if (errorCode == null) {
            collectionTpResponse = this.facadeTpDao.getUserTpDao().getCollectionList(type, categoryId, page, pageSize,
                    commonParam);
            if (collectionTpResponse == null || collectionTpResponse.isSucceed()) { // 接口响应成功
                if (collectionTpResponse.getData() != null) { // 收藏数据不为空
                    total = collectionTpResponse.getData().getTotal();
                    if (!CollectionUtils.isEmpty(collectionTpResponse.getData().getRows())) {
                        CollectionListInfo.CollectionInfo collectionInfo = this.facadeCacheDao.getUserCacheDao()
                                .getUserNewAddColleciton(commonParam.getUserId()); // 从缓存中取收藏数据
                        if (collectionInfo != null && collectionInfo.getLid() != null) {
                            String[] tmp = collectionInfo.getLid().split("_");
                            if (tmp != null && tmp.length == 2) {
                                collectionInfo.setLidtype(StringUtil.toInteger(tmp[0]));
                                if (!collectionTpResponse.getData().getRows().get(0).getLid()
                                        .equals(collectionInfo.getLid())) { // 从用户中心取的收藏list不包括缓存的收藏数据,则将缓存的收藏数据拼接到收藏list中
                                    collectionTpResponse.getData().getRows().add(0, collectionInfo);
                                    total = (total == null ? 1 : total++);
                                    collectionTpResponse.getData().setTotal(total);
                                }
                            }
                        }
                        favoriteDtoList = parseCollectionInfo(collectionTpResponse.getData().getRows(), commonParam); // 拼接dto
                    }
                }
            } else {
                errorCode = ErrorCodeConstants.USER_GET_COLLECTION_LIST_FAILURE;
            }
        }

        PageCommonResponse<FavoriteListDto> response = new PageCommonResponse<FavoriteListDto>();
        if (errorCode == null) {
            response.setStatus(BaseResponse.STATUS_OK);
            response.setItems(favoriteDtoList);
            response.setPageInfo(total, page, pageSize);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            LOG.info("getCollectionList_" + commonParam.getUsername() + " error. error code:" + errorCode);
        }
        return response;
    }

    List<FavoriteListDto> parseCollectionInfo(List<CollectionListInfo.CollectionInfo> collectionInfoList,
                                              CommonParam commonParam) {
        if (collectionInfoList == null || collectionInfoList.size() <= 0) {
            return null;
        }
        List<FavoriteListDto> favoriteListDtoList = new LinkedList<FavoriteListDto>();
        // List<Long> albumId = new ArrayList<Long>();
        // List<Long> videoId = new ArrayList<Long>();
        for (CollectionListInfo.CollectionInfo collectionInfo : collectionInfoList) {
            if (collectionInfo.getLidtype() != null && collectionInfo.getLidtype() == 110) { // 内网专辑前缀110
                if (collectionInfo.getLid() != null) {
                    Long albumId = StringUtil.toLong(collectionInfo.getLid().substring(4)); // 截取出albumId
                    AlbumMysqlTable album = albumVideoAccess.getAlbum(albumId, commonParam);
                    if (album != null) {
                        FavoriteListDto favoriteListDto = null;
                        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                                .getTerminalApplication())) {
                            favoriteListDto = getPlayFavoriteDto4Le(album, commonParam);
                        } else {
                            favoriteListDto = getPlayFavoriteDto(album, commonParam);
                        }
                        favoriteListDto.setGlobalid(collectionInfo.getLid());
                        favoriteListDtoList.add(favoriteListDto);
                    }
                }
            }
        }
        return favoriteListDtoList;
    }

    /**
     * 检查用户收藏状态
     * @param globalId
     * @param commonParam
     * @return
     */
    public Response checkIsCollection(String globalId, CommonParam commonParam) {
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (StringUtil.isBlank(globalId)) {
            errorCode = ErrorCodeConstants.USER_CHECK_COLLECTION_FAILURE;
        }
        boolean result = false;
        if (errorCode == null) {
            CollectionTpResponse<Map<String, CollectionListInfo.CollectionInfo>> collectionTpResponse = this.facadeTpDao
                    .getUserTpDao().getCollection(globalId, commonParam);
            if (collectionTpResponse != null && collectionTpResponse.isSucceed()
                    && collectionTpResponse.getData() != null) { // 第三方响应成功
                Map<String, CollectionListInfo.CollectionInfo> collectionInfoMap = collectionTpResponse.getData();
                if (collectionInfoMap.get(globalId) != null) {
                    result = true;
                } else { // 用户中心接口获取不到,查询缓存中是否有此收藏数据
                    CollectionListInfo.CollectionInfo collectionInfo = this.facadeCacheDao.getUserCacheDao()
                            .getUserNewAddColleciton(commonParam.getUserId());
                    if (collectionInfo != null && collectionInfo.getLid() != null) {
                        LOG.error("checkIsCollection_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + " | cache globalId:" + collectionInfo.getLid());
                    } else {
                        LOG.error("checkIsCollection_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + " | cache null");
                    }
                    if (collectionInfo != null && collectionInfo.getLid() != null
                            && collectionInfo.getLid().equals(globalId)) {
                        result = true;
                    }
                }
            } else { // 用户中心接口获取不到,查询缓存中是否有此收藏数据
                CollectionListInfo.CollectionInfo collectionInfo = this.facadeCacheDao.getUserCacheDao()
                        .getUserNewAddColleciton(commonParam.getUserId());
                if (collectionInfo != null && collectionInfo.getLid() != null) {
                    LOG.error("checkIsCollection_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + " | cache globalId:" + collectionInfo.getLid());
                } else {
                    LOG.error("checkIsCollection_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + " | cache null");
                }
                if (collectionInfo != null && collectionInfo.getLid() != null
                        && collectionInfo.getLid().equals(globalId)) {
                    result = true;
                }
            }
        }
        Response response = new Response();
        if (errorCode != null) {
            response.setResultStatus(BaseResponse.STATUS_ERROR);
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            LOG.error("checkIsCollection_" + commonParam.getMac() + "_" + commonParam.getUserId() + " | errorCode="
                    + errorCode);
        } else {
            response.setResultStatus(BaseResponse.STATUS_OK);
            response.setData(result);
        }
        return response;
    }

    /**
     * 检查用户收藏状态
     * @param globalId
     * @param flush
     * @param commonParam
     * @return
     */
    public Response delCollection(String globalId, Integer flush, CommonParam commonParam) {
        String errorCode = null;
        if (StringUtils.isEmpty(commonParam.getUserToken())) { // 获取用户token
            UserStatus user = this.getUserStatus(commonParam);
            if (user == null || StringUtils.isEmpty(user.getUserToken())) {
                errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
            } else {
                commonParam.setUserToken(user.getUserToken());
            }
        }
        if (StringUtil.isBlank(globalId)) {
            errorCode = ErrorCodeConstants.USER_DEL_COLLECTION_FAILURE;
        }
        boolean result = false;
        if (errorCode == null) {
            CollectionTpResponse<List<Object>> collectionTpResponse = this.facadeTpDao.getUserTpDao().delCollection(
                    globalId, flush, commonParam);
            if (collectionTpResponse != null && collectionTpResponse.isSucceed()
                    && collectionTpResponse.getData() != null) { // 第三方响应成功
                if (collectionTpResponse.getData().size() == 0) {
                    CollectionListInfo.CollectionInfo collectionInfo = this.facadeCacheDao.getUserCacheDao()
                            .getUserNewAddColleciton(commonParam.getUserId()); // 查询是否缓存收藏数据
                    if (collectionInfo != null && collectionInfo.getLid() != null
                            && collectionInfo.getLid().equals(globalId)) { // 缓存中有且globalId相同,则删除缓存数据
                        this.facadeCacheDao.getUserCacheDao().deleteUserNewAddCollection(commonParam.getUserId());
                        LOG.error("delCollection_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + " |del cache globalId:" + globalId);
                    }
                    result = true;
                } else {
                    //
                }
            } else {
                errorCode = ErrorCodeConstants.USER_DEL_COLLECTION_FAILURE;
            }
        }
        Response response = new Response();
        if (errorCode != null) {
            response.setResultStatus(BaseResponse.STATUS_ERROR);
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            LOG.error("delCollection_" + commonParam.getMac() + "_" + commonParam.getUserId() + " | errorCode="
                    + errorCode);
        } else {
            response.setResultStatus(BaseResponse.STATUS_OK);
            response.setData(result);
        }
        return response;
    }

    public Response<ChildLockDto> checkUserChildLock(Integer actionType, String ticket, String pin,
                                                     CommonParam commonParam) {
        Response<ChildLockDto> response = new Response<ChildLockDto>();
        String errorCode = null;
        ChildLockDto childLockDto = null;
        UserChildLockTable childLockTable = null;

        if (!isLogin(commonParam)) {
            errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
        } else if (!isLegalCheckUserChildLockAction(actionType, ticket, pin)) {
            errorCode = ErrorCodeConstants.LECOM_USER_ILLEGAL_OPERATION;
        } else {
            switch (actionType) {
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS:
                    childLockTable = getUserChildLockFromCache(commonParam);
                    if (childLockTable != null) {
                        childLockDto = parseUserChildLockTable(actionType, childLockTable);
                        response.setData(childLockDto);
                    } else {
                        // 如果没有查到，这说明是初始状态，更新缓存但不更新数据库
                        childLockTable = genInitChildLockTable(commonParam);
                        this.facadeCacheDao.getUserCacheDao().setChildLockByuserId(commonParam.getUserId(), childLockTable);

                        childLockDto = parseUserChildLockTable(actionType, childLockTable);
                    }
                    break;
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PASSOWRD:
                    VerifyAccountPasswordTpResponse verifyPasswordresponse = this.facadeTpDao.getUserTpDao()
                            .verifyAccountPassword(ticket, commonParam);
                    if (verifyPasswordresponse != null && verifyPasswordresponse.isSuccessed()) {
                        childLockTable = getUserChildLockFromCache(commonParam);
                        if (childLockTable == null) {
                            // TODO 如果没查到，说明是初始状态，是否能走校验流程？
                            childLockTable = genInitChildLockTable(commonParam);
                        }
                        childLockTable.setLockToken(verifyPasswordresponse.getSign());
                        childLockTable.setTokenExpireTime(System.currentTimeMillis()
                                + UserConstants.USER_CHILD_LOCK_SET_TOKEN_EXPIRE_TIME);

                        int result = this.updateUserChildLockToCache(commonParam.getUserId(), childLockTable);
                        if (result > 0) {
                            childLockDto = parseUserChildLockTable(actionType, childLockTable);
                        } else {
                            // 数据更新失败
                            errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_SET_TOKEN_ERROR;
                        }
                    } else {
                        errorCode = ErrorCodeConstants.LECOM_USER_VERIFY_PWD_ERROR;
                    }
                    break;
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PIN:
                    childLockTable = getUserChildLockFromCache(commonParam);
                    if (childLockTable == null || UserConstants.USER_CHILD_LOCK_STATUS_UNSET == childLockTable.getStatus()) {
                        errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_NOT_EXISTED;
                    } else {
                        if (StringUtils.equalsIgnoreCase(pin, childLockTable.getMd5Pin())) {
                            String lockToken = genChildLockSimpleLockToken(commonParam);
                            childLockTable.setLockToken(lockToken);
                            childLockTable.setTokenExpireTime(System.currentTimeMillis()
                                    + UserConstants.USER_CHILD_LOCK_SET_TOKEN_EXPIRE_TIME);

                            int result = this.updateUserChildLockToCache(commonParam.getUserId(), childLockTable);
                            if (result > 0) {
                                childLockDto = parseUserChildLockTable(actionType, childLockTable);
                            } else {
                                // 数据更新失败
                                errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_SET_TOKEN_ERROR;
                            }
                        } else {
                            errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_VERIFY_PIN_ERROR;
                        }
                    }

                    break;
                default:
                    errorCode = ErrorCodeConstants.LECOM_USER_ILLEGAL_OPERATION;
                    break;
            }
        }

        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(childLockDto);
        }

        return response;
    }

    public Response<ChildLockDto> setUserChildLock(Integer actionType, String pin, Integer status, String lockToken,
                                                   CommonParam commonParam) {
        Response<ChildLockDto> response = new Response<ChildLockDto>();
        String errorCode = null;
        ChildLockDto childLockDto = null;
        UserChildLockTable childLockTable = null;

        if (!isLogin(commonParam)) {
            errorCode = ErrorCodeConstants.USER_NOT_LOGIN;
        } else if (!isLegalSetUserChildLockAction(actionType, pin, status, lockToken)) {
            errorCode = ErrorCodeConstants.LECOM_USER_ILLEGAL_OPERATION;
        } else {
            childLockTable = getUserChildLockFromCache(commonParam);

            switch (actionType) {
                case UserConstants.USER_CHILD_LOCK_SET_ACTION_TYPE_CREATE:
                    if (childLockTable == null || UserConstants.USER_CHILD_LOCK_STATUS_UNSET == childLockTable.getStatus()) {
                        if (childLockTable == null) {
                            childLockTable = genInitChildLockTable(commonParam);
                        }
                        childLockTable.setMd5Pin(pin);
                        childLockTable.setStatus(UserConstants.USER_CHILD_LOCK_STATUS_ON);
                        childLockTable.setMac(commonParam.getMac());
                    } else {
                        errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_EXISTED;
                    }
                    break;
                case UserConstants.USER_CHILD_LOCK_SET_ACTION_TYPE_RESET_WITH_PIN:
                    if (childLockTable != null && UserConstants.USER_CHILD_LOCK_STATUS_UNSET != childLockTable.getStatus()) {
                        if (StringUtils.equalsIgnoreCase(pin, childLockTable.getMd5Pin())) {
                            childLockTable.setStatus(status);
                            childLockTable.setMac(commonParam.getMac());
                        } else {
                            errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_VERIFY_PIN_ERROR;
                        }
                    } else {
                        errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_NOT_EXISTED;
                    }
                    break;
                case UserConstants.USER_CHILD_LOCK_SET_ACTION_TYPE_RESET_WITH_LOCALTOKEN:
                    if (childLockTable != null && UserConstants.USER_CHILD_LOCK_STATUS_UNSET != childLockTable.getStatus()) {

                        if (StringUtils.equals(lockToken, childLockTable.getLockToken())) {
                            if (childLockTable.getTokenExpireTime() == null
                                    || childLockTable.getTokenExpireTime() >= System.currentTimeMillis()) {
                                childLockTable.setMd5Pin(pin);
                                childLockTable.setStatus(status);
                                childLockTable.setMac(commonParam.getMac());
                            } else {
                                // 设置了lockToken有效时间，且当前操作时间已过期
                                errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_VERIFY_TOKEN_ERROR;
                            }
                        } else {
                            // lockToken不匹配
                            errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_VERIFY_TOKEN_ERROR;
                        }
                    } else {
                        errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_NOT_EXISTED;
                    }
                    break;
                default:
                    break;
            }
        }

        if (errorCode == null) {
            try {
                if (UserConstants.USER_CHILD_LOCK_SET_ACTION_TYPE_CREATE == actionType) {
                    // 儿童锁入库
                    this.facadeMysqlDao.getUserMysqlDao().insertChildLock(childLockTable);
                } else {
                    this.facadeMysqlDao.getUserMysqlDao().updateChildLock(childLockTable);
                }
            } catch (Exception ex) {
                LOG.error("update the childlock of database error!", ex);
            }
            int result = this.updateUserChildLockToCache(commonParam.getUserId(), childLockTable);
            if (result > 0) {
                childLockDto = parseUserChildLockTable(UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS,
                        childLockTable);
            } else {
                // 数据更新失败
                errorCode = ErrorCodeConstants.LECOM_USER_CHILD_LOCK_SET_LOCK_ERROR;
            }
        }

        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(childLockDto);
        }

        return response;
    }

    public UserChildLockTable getUserChildLockFromCache(CommonParam commonParam) {
        UserChildLockTable childLockTable = null;
        if (commonParam != null && StringUtils.trimToNull(commonParam.getUserId()) != null) {
            childLockTable = this.facadeCacheDao.getUserCacheDao().getChildLockByuserId(commonParam.getUserId());
            // 缓存未命中从数据库获取儿童锁信息
            if (childLockTable == null) {
                childLockTable = this.facadeMysqlDao.getUserMysqlDao().getChildLockByUserId(commonParam.getUserId());
                // 缓存未命中情况下，默认token永不过期
                if (childLockTable != null) {
                    childLockTable.setTokenExpireTime(System.currentTimeMillis()
                            + UserConstants.USER_CHILD_LOCK_SET_TOKEN_EXPIRE_TIME);
                    childLockTable.setLockToken(genChildLockSimpleLockToken(commonParam));
                    // this.updateUserChildLockToCache(commonParam.getUserId(),
                    // childLockTable);
                }
            }
        }
        return childLockTable;
    }

    private int updateUserChildLockToCache(String userId, UserChildLockTable childLockTable) {
        int result = this.facadeCacheDao.getUserCacheDao().setChildLockByuserId(userId, childLockTable);
        return result;
    }

    public ChildLockDto parseUserChildLockTable(Integer actionType, UserChildLockTable childLockTable) {
        ChildLockDto childLockDto = null;
        if (actionType != null && childLockTable != null) {
            switch (actionType) {
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS:
                    childLockDto = new ChildLockDto();
                    childLockDto.setStatus(childLockTable.getStatus());
                    if (childLockTable.getCanPlayCRIds() != null) {
                        childLockDto.setCanPlayCRIds(CollectionUtils.arrayToList(StringUtils.split(
                                childLockTable.getCanPlayCRIds(), ",")));
                    }
                    break;
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PASSOWRD:
                    childLockDto = new ChildLockDto();
                    childLockDto.setStatus(childLockTable.getStatus());
                    childLockDto.setLockToken(childLockTable.getLockToken());
                    break;
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PIN:
                    childLockDto = new ChildLockDto();
                    childLockDto.setStatus(childLockTable.getStatus());
                    childLockDto.setLockToken(childLockTable.getLockToken());
                    break;
                default:
                    break;
            }
        }
        return childLockDto;
    }

    private UserChildLockTable genInitChildLockTable(CommonParam commonParam) {
        UserChildLockTable childLockTable = new UserChildLockTable();
        childLockTable.setUserId(commonParam.getUserId());
        childLockTable.setMac(commonParam.getMac());
        return childLockTable;
    }

    private String genChildLockSimpleLockToken(CommonParam commonParam) {
        String lockToken = null;
        if (StringUtils.trimToNull(commonParam.getUserToken()) != null
                && StringUtils.trimToNull(commonParam.getMac()) != null) {
            lockToken = MD5Util.md5(commonParam.getUserToken() + "_" + commonParam.getMac() + "_"
                    + String.valueOf(System.currentTimeMillis()));
        }
        return lockToken;
    }

    private boolean isLogin(CommonParam commonParam) {
        return StringUtils.trimToNull(commonParam.getUserId()) != null
                && StringUtils.trimToNull(commonParam.getToken()) != null
                && StringUtils.trimToNull(commonParam.getMac()) != null;
    }

    private boolean isLegalCheckUserChildLockAction(Integer actionType, String ticket, String pin) {
        boolean isLegalAction = false;
        if (actionType != null) {
            switch (actionType) {
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS:
                    isLegalAction = true;
                    break;
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PASSOWRD:
                    isLegalAction = (StringUtils.trimToNull(ticket) != null);
                    break;
                case UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PIN:
                    isLegalAction = (StringUtils.trimToNull(pin) != null);
                    break;
                default:
                    break;
            }
        }
        return isLegalAction;
    }

    private boolean isLegalSetUserChildLockAction(Integer actionType, String pin, Integer status, String lockToken) {
        boolean isLegalAction = false;
        if (actionType != null) {
            switch (actionType) {
                case UserConstants.USER_CHILD_LOCK_SET_ACTION_TYPE_CREATE:
                    isLegalAction = (StringUtils.trimToNull(pin) != null);
                    break;
                case UserConstants.USER_CHILD_LOCK_SET_ACTION_TYPE_RESET_WITH_PIN:
                    isLegalAction = (StringUtils.trimToNull(pin) != null && isLegalUserChildLockEffectiveStatus(status));
                    break;
                case UserConstants.USER_CHILD_LOCK_SET_ACTION_TYPE_RESET_WITH_LOCALTOKEN:
                    isLegalAction = (StringUtils.trimToNull(lockToken) != null && isLegalUserChildLockEffectiveStatus(status));
                    break;
                default:
                    break;
            }
        }
        return isLegalAction;
    }

    private boolean isLegalUserChildLockEffectiveStatus(Integer status) {
        return status != null
                && (UserConstants.USER_CHILD_LOCK_STATUS_OFF == status || UserConstants.USER_CHILD_LOCK_STATUS_ON == status);
    }

    /**
     * 获取用户已订阅的addon信息
     * @param commonParam
     * @return
     */
    public Response<Set<UserAccountDto.VipInfoV2Dto>> getUserAddon(CommonParam commonParam) {
        // 设置会员信息
        Set<UserAccountDto.VipInfoV2Dto> vipInfoV2DtoSet = new TreeSet<UserAccountDto.VipInfoV2Dto>(
                (o1,o2) -> {
                    if (o1.getCreateTime() > o2.getCreateTime()) {
                        return -1;
                    } else if (o1.getCreateTime() < o2.getCreateTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
//                new Comparator<VipInfoV2Dto>() {
//                    @Override
//                    public int compare(VipInfoV2Dto o1, VipInfoV2Dto o2) {
//                        if (o1.getCreateTime() > o2.getCreateTime()) {
//                            return -1;
//                        } else if (o1.getCreateTime() < o2.getCreateTime()) {
//                            return 1;
//                        } else {
//                            return 0;
//                        }
//                    }
//
//                });
        BossTpResponse<List<SubscribeInfo>> subscrobeInfoListResponse = this.facadeTpDao.getVipTpDao().getVipInfo(
                VipTpConstant.BossTerminalType.TV.getCode(), commonParam);
        Response<Set<UserAccountDto.VipInfoV2Dto>> response = new Response<Set<UserAccountDto.VipInfoV2Dto>>();
        if (subscrobeInfoListResponse != null && subscrobeInfoListResponse.isSucceed()) {
            List<SubscribeInfo> subscrobeInfoList = subscrobeInfoListResponse.getData();
            for (SubscribeInfo subscribeInfo : subscrobeInfoList) {
                if (!VipTpConstant.Type_Group_US.ADD_ON.getCode().equals(subscribeInfo.getTypeGroup())) { // 过滤非影视会员
                    continue;
                }
                Long currentTime = System.currentTimeMillis();
                boolean isFilter = true;
                if (subscribeInfo.getIsSubscribe() == null
                        || SubscribeInfo.UN_SUBSCRIBED == subscribeInfo.getIsSubscribe()) {
                    isFilter = true;
                }
                if (subscribeInfo.getTryEndTime() != null && subscribeInfo.getTryEndTime() > currentTime) {
                    isFilter = false;
                } else if (subscribeInfo.getEndTime() != null && subscribeInfo.getEndTime() > currentTime) {
                    isFilter = false;
                }
                if (isFilter) { // 过滤非订阅add－on
                    continue;
                }
                if (subscribeInfo.getProductId() != null) {
                    UserAccountDto.VipInfoV2Dto vipInfo = new UserAccountDto.VipInfoV2Dto();
                    vipInfo.setName(subscribeInfo.getName());
                    vipInfo.setProductId(subscribeInfo.getProductId());
                    vipInfo.setCreateTime(subscribeInfo.getCreateTime());
                    vipInfoV2DtoSet.add(vipInfo);
                }
            }
        }
        response.setData(vipInfoV2DtoSet);
        return response;
    }

    /**
     * 获取用户成长值信息
     * @param channel
     * @param timestamp
     * @param saltkey
     * @param commonParam
     * @return
     */
    public Response getUserGrowth(String channel, Long timestamp, String saltkey, CommonParam commonParam) {
        Response<JSONObject> response = new Response();
        String logPrefix = "getUserGrowth_" + commonParam.getUserId() + "_" + commonParam.getMac();
        String errorCode = null;
        String uri = ApplicationConstants.ODP_MY_BASE_HOST + "/tv/growth";
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", commonParam.getUserId());
        params.put("channel", channel);
        if (null != timestamp)
            params.put("times", timestamp.toString());
        OdpTpResponse<JSONObject> tpResponse = this.facadeTpDao.getUserTpDao().odpProxyRequest(uri, params);
        if (tpResponse == null || !tpResponse.isSucceed()) {
            // 接口调用失败
            errorCode = ErrorCodeConstants.USER_GET_ROLE_ERROR;
            ResponseUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            response.setData(tpResponse.getData());
        }
        return response;
    }

    /**
     * 判断用户MAC是否在黑名单中
     * @param mac
     * @return
     */
    public boolean isMacInBlacklist(String mac) {
        Set<String> macSet = this.facadeTpDao.getStaticTpDao().getPlayMacBlackList();
        if (macSet != null && macSet.contains(mac)) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户MAC黑名单
     * @return
     */
    public Set<String> getMacBlacklist() {
        return this.facadeTpDao.getStaticTpDao().getPlayMacBlackList();
    }
}

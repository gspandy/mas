package com.letv.mas.caller.iptv.tvproxy.user;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.BroadcastConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.LetvUserVipCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant.FOLLOW_FROM;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant.FOLLOW_TYPE;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.RolePlayListRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.UserRoleRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.LetvUserInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.UserAccountDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TerminalUtil;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "获取会员信息(V2)",
            notes = "sig字段签名规则：md5(asort(k1=v1&k2=v2)SecrectKey)<br/>" +
                    "原版本：md5(ksort(deviceKey=xxx&mac=xxx&token=xxxx&userId=xxx)SecrectKey)<br/>" +
                    "支持防盗链版本：md5(ksort(appCode=xxx&deviceKey=xxx&devSign=xxxxxx&mac=xxx&token=xxxx&userId=xxx)SecrectKey)",
            httpMethod = "GET")
    @RequestMapping("/user/v2/account/getUserAccount")
    public Response<UserAccountDto> getUserAccount(
            @ApiParam(value = "机卡绑定设备类型，1-电视，2-手机传，3-盒子, 默认为1", required = false, defaultValue = "1") @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "签名, 规则见接口备注", required = true) @RequestParam(value = "sign", required = true) String sign,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return userService.getUserAccount(deviceType,sign,commonParam);
    }

    /**
     * user login by user token
     * @param roleflag
     *            1--return role info,otherwise not return role info
     * @param deviceType
     *            device-type，see{@link UserConstants.ErosConstant.DeviceType}
     * @param operationType
     *            operation type. 为1是表示某一端获取eros-token.空或其他值表示用户正常登陆
     * @param sign
     *            signature. It's used when @param[type] == 1
     * @param from
     *            see {@link UserConstants.ErosConstant.FromType}, It's used
     *            when @param[operationType] == 1
     * @param timestamp
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "登录", httpMethod = "GET")
    @RequestMapping(value = "/user/tokenLogin")
    public Response<LoginCsDto> tokenLogin(@RequestParam(value = "roleflag", required = false) Integer roleflag,
                                           @RequestParam(value = "deviceType", required = false) Integer deviceType,
                                           @RequestParam(value = "operationType", required = false) Integer operationType,
                                           @RequestParam(value = "sign", required = false) String sign,
                                           @RequestParam(value = "from", required = false) Integer from,
                                           @RequestParam(value = "timestamp", required = false) Long timestamp,
                                           @RequestParam(value = "token", required = false) String token,
                                           @RequestParam(value = "uid", required = false) String uid, @ModelAttribute CommonParam commonParam) {
        if (roleflag == null) {// default no need to return role info
            roleflag = 0;
        }
        if (TerminalUtil.isLetvUs(commonParam)) {
            if (StringUtil.isBlank(commonParam.getUserToken())) {
                commonParam.setUserId(uid);
                commonParam.setUserToken(token);
            }
        }
        Response<LoginCsDto> response = null;
        if (operationType == UserConstants.ErosConstant.Type.GET) { // 其他某一端发出了获取eros-token的请求
            response = this.userService.tokenLogin(deviceType, from, timestamp, sign, commonParam);
        } else {
            response = this.userService.tokenLogin(roleflag, commonParam);
        }
        return response;
    }

    /**
     * user login by user token
     * @param roleflag
     *            1--return role info,otherwise not return role info
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "登录V2", httpMethod = "GET")
    @RequestMapping(value = "/v2/user/tokenLogin")
    public Response<LoginCsDto> tokenLogin1(
            @RequestParam(value = "roleflag", required = false, defaultValue = "0") Integer roleflag,
            @RequestParam(value = "token", required = false) String token,
            @RequestParam(value = "uid", required = false) String uid, @ModelAttribute CommonParam commonParam) {
        if (TerminalUtil.isLetvUs(commonParam)) {
            if (StringUtil.isBlank(commonParam.getUserToken())) {
                commonParam.setUserId(uid);
                commonParam.setUserToken(token);
            }
        }
        return this.userService.tokenLogin(roleflag, commonParam);
    }

    /**
     * get user favorite album and chase drama
     * @param page
     *            current page
     * @param pageSize
     *            current page size
     * @param isFull
     *            if the value is one,merge the album and chase drama to one
     *            list
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取收藏、追剧", httpMethod = "GET")
//    @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/fav/getTagAndAlbum")
    public PageResponse<TagAndAlbumListDto> getPlayTagAndAlbumList(@RequestParam("page") Integer page,
                                                                   @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "token", required = false) String token,
                                                                   @RequestParam(value = "isFull", required = false, defaultValue = "0") Integer isFull,
                                                                   @ModelAttribute CommonParam commonParam) {
        if (TerminalUtil.isLetvUs(commonParam)) {
            if (StringUtil.isBlank(commonParam.getUserToken())) {
                commonParam.setUserToken(token);
            }
        }
        return this.userService.getPlayTagAndAlbumList(page, pageSize, isFull, commonParam);
    }

    /**
     * add user favorite album
     * @param albumid
     * @param platform
     * @param fromtype
     *            1--favorite,2--chase drama
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "添加收藏、追剧", httpMethod = "GET")
//    @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/fav/addPlaylist")
    public Response<Boolean> addPlaylist(@RequestParam("albumid") Long albumid,
                                         @RequestParam("platform") Integer platform, @RequestParam("fromtype") Integer fromtype,
                                         @RequestParam(value = "token", required = false) String token, @ModelAttribute CommonParam commonParam) {
        if (TerminalUtil.isLetvUs(commonParam)) {
            if (StringUtil.isBlank(commonParam.getUserToken())) {
                commonParam.setUserToken(token);
            }
        }
        return this.userService.addPlayList(albumid, platform, fromtype, commonParam);
    }

    /**
     * check if user has collect the album
     * @param albumid
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "检查用户是否收藏某专辑", httpMethod = "GET")
//    @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/fav/checkPlaylist")
    public Response<CheckPlaylistDto> checkPlaylist(@RequestParam(value = "albumid") Long albumid,
                                                    @RequestParam(value = "token", required = false) String token, @ModelAttribute CommonParam commonParam) {
        if (TerminalUtil.isLetvUs(commonParam)) {
            if (StringUtil.isBlank(commonParam.getUserToken())) {
                commonParam.setUserToken(token);
            }
        }
        return this.userService.checkPlaylist(albumid, commonParam);
    }

    /**
     * delete user favorite album or chase drama by favorite id
     * @param id
     *            favorite id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "取消收藏、追剧", httpMethod = "GET")
//    @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/fav/delPlaylist")
    public Response<Boolean> deletePlaylist(@RequestParam("id") Long id, @ModelAttribute CommonParam commonParam) {
        return this.userService.deletePlaylist(id, commonParam);
    }

    /**
     * get user play log
     * @param page
     * @param pageSize
     * @param intervalTime
     *            play log interval time
     * @param roleid
     *            if roleid is not empty then get user role play log
     * @param from
     *            来源 （1：WEB，2：MOBILE，3：PAD， 4：TV， 5：PC桌面 ，默认4：tv）
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取用户播放记录", notes = "获取用户播放记录；<br/>" + "版本更新：<br/>"
            + "- 2017.9月需求：增加角标，统一在chargeInfos中获取(免费则不返回chargeInfos)", httpMethod = "GET")
    @RequestMapping("/user/playlog/getPlaylog")
    public PageCommonResponse<PlayLogListDto> getPlaylog(
            @ApiParam(name = "page", value = "页数，默认为1") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "页长，默认为60") @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "intervalTime", value = "最近n天的播放记录，时间戳") @RequestParam(value = "intervalTime", required = false) Integer intervalTime,
            @ApiParam(name = "roleid") @RequestParam(value = "roleid", required = false, defaultValue = "0") Long roleid,
            @ApiParam(name = "validDate", value = "会员到期时间，YYYY-MM-DD") @RequestParam(value = "validDate", required = false) String validDate,
            @ApiParam(name = "from", value = "来源 （1：WEB，2：MOBILE，3：PAD， 4：TV， 5：PC桌面 ，默认4：tv）") @RequestParam(value = "from", required = false) Integer from,
            @ModelAttribute CommonParam commonParam) {
        page = (page == null) ? 1 : page;
        if (pageSize == null || pageSize > 60) {
            pageSize = 60;
        }
        if (from == null) { // 默认来源tv
            from = UserTpConstant.PLAY_LOG_FROM.TV.getCode();
        }
        Integer type = null;// 0：全部记录，1：点播记录，2：直播，3：轮播，4：卫视，10：外网; 暂不支持多个类型同时筛选
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam.getTerminalApplication())
                || TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            // levidi或le,type默认0
            type = 0;
        } else {
            type = 1;
        }

        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam.getTerminalApplication())) { // 印度或美国levidi
            return this.userService.getPlaylog4Levidi(page, pageSize, intervalTime, roleid, type,
                    from, commonParam);
        } else {
            return this.userService.getPlaylog(page, pageSize, intervalTime, roleid, type, from,
                    validDate, commonParam);
        }
    }

    /**
     * get user play log by period
     * @param page
     * @param pageSize
     * @param intervalTime
     *            play log interval time
     * @param roleid
     *            if roleid is not empty then get user role play log
     * @param from
     *            来源 （1：WEB，2：MOBILE，3：PAD， 4：TV， 5：PC桌面 ，默认4：tv）
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取用户播放记录", notes = "获取用户播放记录；<br/>" + "版本更新：<br/>"
            + "- 2017.9月需求：增加角标，统一在chargeInfos中获取(免费则不返回chargeInfos)", httpMethod = "GET")
    @RequestMapping("/user/playlog/getPeriodPlaylog")
    public PagePeriodResponse<PeriodTvLog> getPeriodPlaylog(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "intervalTime", required = false) Integer intervalTime,
            @RequestParam(value = "roleid", required = false, defaultValue = "0") Long roleid,
            @RequestParam(value = "from", required = false) Integer from, @ModelAttribute CommonParam commonParam) {
        page = (page == null) ? 1 : page;
        if (pageSize == null || pageSize > 60) {
            pageSize = 60;
        }
        if (from == null) { // 默认来源tv
            from = UserTpConstant.PLAY_LOG_FROM.TV.getCode();
        }
        Integer type = null;// 0：全部记录，1：点播记录，2：直播，3：轮播，4：卫视，10：外网; 暂不支持多个类型同时筛选
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam.getTerminalApplication())
                || TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            // levidi或le,type默认0
            type = 0;
        } else {
            type = 1;
        }

        return this.userService.getPeriodPlaylog(page, pageSize, intervalTime, roleid, type, from,
                commonParam);

    }

    /**
     * 获取用户播放记录接口
     * @param page
     *            分页信息，第几页
     * @param pageSize
     *            分页信息，分页大小
     * @param intervalTime
     *            获取某段时间内的播放记录
     * @param roleid
     *            乐视儿童角色id
     * @param type
     *            类型，0：全部类型记录，1：点播记录，2：直播，3：轮播，4：卫视，10：外网，默认 1。
     *            用户中心接口支持多个类型同时筛选，格式：type=1,2,3
     * @param from
     *            来源 （1：WEB，2：MOBILE，3：PAD， 4：TV， 5：PC桌面，6：超级汽车，7：LEVR。
     *            用户中心默认1：web）
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取用户播放记录", httpMethod = "GET")
    @RequestMapping("/p2/playrecord/get")
    public NewPageResponse<PlayRecordDto> getUserPlayRecord(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "60") Integer pageSize,
            @RequestParam(value = "intervalTime", required = false) Integer intervalTime,
            @RequestParam(value = "roleid", required = false, defaultValue = "0") Long roleid,
            @RequestParam(value = "type", required = false, defaultValue = "0") Integer type,
            @RequestParam(value = "from", required = false, defaultValue = "4") Integer from,
            @ModelAttribute CommonParam commonParam) {
        return this.userService.getUserPlayRecord(page, pageSize, intervalTime, roleid, type, from,
                commonParam);
    }

    /**
     * delete user play log,if roleid is not empty then delete the user roleid
     * play log
     * @param albumid
     *            use album id to delete play log
     * @param videoid
     *            use video id to delete play log
     * @param roleid
     *            user roleid
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "删除用户播放记录", httpMethod = "GET")
//    @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/playlog/delPlaylog")
    public Response<Boolean> deletePlaylog(@RequestParam(value = "albumid") String albumId,
                                           @RequestParam(value = "videoid", required = false) String videoId,
                                           @RequestParam(value = "globalId", required = false) String globalId,
                                           @RequestParam(value = "roleid", required = false) Long roleId, @ModelAttribute CommonParam commonParam) {
        if (roleId == null) { // 儿童模式加角色id,默认为0
            roleId = 0L;
        }
        return this.userService.deletePlaylog(albumId, videoId, globalId, roleId, commonParam);
    }

    /**
     * delete user all play log
     * @param roleid
     *            if roleid is not empty then delete the user role play log
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "删除所有播放记录", httpMethod = "GET")
//    @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/playlog/delAllPlaylog")
    public Response<Boolean> deleteAllPlayLog(@RequestParam(value = "roleid", required = false) Long roleId,
                                              @ModelAttribute CommonParam commonParam) {
        if (roleId == null) { // 儿童模式加角色id,默认为0
            roleId = 0L;
        }
        return this.userService.deleteAllPlaylog(roleId, commonParam);
    }

    /**
     * update user video play log
     * @param albumid
     *            专辑id
     * @param videoid
     *            视频id
     * @param categoryId
     *            频道id
     * @param nextVideoId
     * @param playTime
     *            观看时间点（0：开始播放，-1：播放完闭） 秒数
     * @param roleid
     *            角色ID，ID为正整数（不传默认为主账号）
     * @param from
     *            来源 （1：WEB，2：MOBILE，3：PAD， 4：TV， 5：PC桌面 ，默认4：tv）
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "更新用户播放记录", httpMethod = "GET")
    @RequestMapping("/user/playlog/updatePlaytime")
    public Response<Boolean> updatePlayTime(
            @ApiParam(name = "albumid", value = "专辑id") @RequestParam(value = "albumid", required = false) Long albumid,
            @ApiParam(name = "videoid", value = "视频id") @RequestParam(value = "videoid", required = false) Long videoid,
            @RequestParam(value = "globalId", required = false) String globalId,
            @ApiParam(name = "categoryId", value = "频道id") @RequestParam(value = "categoryId", required = false) Integer categoryId,
            // @RequestParam(value = "nextVideoId", required = false) Long
            // nextVideoId, //用户中心不需要传此参数，干掉
            @ApiParam(name = "playTime", value = "观看时间点（0：开始播放，-1：播放完闭） 秒数") @RequestParam(value = "playTime", required = false) Long playTime,
            @ApiParam(name = "roleid", value = "角色ID，ID为正整数（不传默认为主账号）:0-超影，非0-儿童") @RequestParam(value = "roleid", required = false) Long roleId,
            @ApiParam(name = "from", value = "来源 （1：WEB，2：MOBILE，3：PAD， 4：TV， 5：PC桌面 ，默认4：tv）") @RequestParam(value = "from", required = false) Integer from, // 客户端现在并没有传这个参数
            @ModelAttribute CommonParam commonParam) {
        if (roleId == null) { // 儿童模式加角色id,默认为0
            roleId = 0L;
        }
        if (from == null) { // 默认来源tv
            from = UserTpConstant.PLAY_LOG_FROM.TV.getCode();
        }
        return this.userService.updatePlayTime(albumid, videoid, globalId, categoryId, playTime,
                roleId, from, commonParam);
    }

    /**
     * 更新用户的播放记录
     * @param albumid
     *            专辑id
     * @param videoid
     *            视频id
     * @param globalId
     *            作品库id
     * @param categoryId
     *            分类id
     * @param playTime
     *            当前视频播放时长
     * @param roleId
     *            角色id，如果是儿童播放，则传此参数
     * @param from
     *            播放来源，1：网页，2：Mobile，3：Pad；4：TV，5：PC，默认4
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "更新用户的播放记录", httpMethod = "GET")
    @RequestMapping("/p2/playrecord/update")
    public NewResponse<Boolean> updateUserPlayRecord(@RequestParam(value = "albumid", required = false) Long albumid,
                                                     @RequestParam(value = "videoid") Long videoid,
                                                     @RequestParam(value = "globalId", required = false) String globalId,
                                                     @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                                     @RequestParam(value = "playTime", required = false) Long playTime,
                                                     @RequestParam(value = "roleid", required = false) Long roleId, @RequestParam(value = "from") Integer from,
                                                     @ModelAttribute CommonParam commonParam) {
        if (roleId == null) { // 儿童模式加角色id,默认为0
            roleId = 0L;
        }
        if (from == null) { // 默认来源tv
            from = UserTpConstant.PLAY_LOG_FROM.TV.getCode();
        }
        return this.userService.updateUserPlayRecord(albumid, videoid, globalId, categoryId,
                playTime, roleId, from, commonParam);
    }

    /**
     * get user account info and vip info
     * @param deviceType
     *            device type
     * @param from
     *            If the value is golive means from golive application
     * @param sign
     *            golive application then need to check the md5 sign
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取用户信息和会员信息", httpMethod = "GET")
//    @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/account/getUserAccount")
    public Response<UserAccountDto> getUserAccount(
            @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "sign", required = false) String sign, @ModelAttribute CommonParam commonParam) {
        if (deviceType == null) {
            deviceType = VipTpConstant.DEVICE_BIND_DEVICE_TYPE_TV;
        }
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            return this.userService.getUserAccount4Levidi(commonParam);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            return this.userService.getUserAccount4Le(commonParam);
        } else {
            return this.userService.getUserAccount(deviceType, from, sign, commonParam);
        }
    }

    /**
     * check third party application if is allowing register and login
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "检查第三方应用是否允许注册登录", httpMethod = "GET")
    @RequestMapping("/user/tpsdk/checkRegisterAndLoginAuth")
    public Response<Integer> checkRegisterAndLoginAuth(@ModelAttribute CommonParam commonParam) {
        return this.userService.checkRegisterAndLoginAuth(commonParam);
    }

    /**
     * add role for user
     * @param nickname
     *            nickname for show in the application
     * @param gender
     *            add user role sex
     * @param brithday
     *            child role birthday
     * @param roleType
     *            role type
     * @param timeStart
     *            setting the play startTime for the role
     * @param timeEnd
     *            setting the play endTime for the role
     * @param setAge
     *            the age of the role
     * @param duration
     *            the duration of the role
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "增加用户角色", httpMethod = "GET")
    @RequestMapping("/user/role/add")
    public Response<ResultValueDto<Integer>> addUserRole(
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "brithday", required = false) String brithday,
            @RequestParam(value = "roleType", required = false, defaultValue = "1") String roleType,
            @RequestParam(value = "timeStart", required = false) String timeStart,
            @RequestParam(value = "timeEnd", required = false) String timeEnd,
            @RequestParam(value = "setAge", required = false) String setAge,
            @RequestParam(value = "duration", required = false) String duration, @ModelAttribute CommonParam commonParam) {
        if (StringUtil.isBlank(nickname)) {
            if ("M".equals(gender)) {
                nickname = "小王子";
            } else if ("F".equals(gender)) {
                nickname = "小公主";
            }
        }
        return this.userService.addUserRole(nickname, roleType, brithday, gender, duration,
                timeStart, timeEnd, setAge, commonParam);
    }

    /**
     * update user role info
     * @param roleid
     *            user role id
     * @param nickname
     *            nickname for show in the application
     * @param roleType
     *            role type
     * @param timeStart
     *            setting the play startTime for the role
     * @param timeEnd
     *            setting the play endTime for the role
     * @param duration
     *            the duration of the role
     * @param gender
     *            user role sex
     * @param setAge
     *            the age of the role
     * @param brithday
     *            child role birthday
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "更新用户角色", httpMethod = "GET")
    @RequestMapping("/user/role/update")
    public Response<ResultValueDto<Integer>> updateUserRole(
            @RequestParam(value = "roleid", required = false, defaultValue = "0") Long roleid,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "roleType", required = false, defaultValue = "1") String roleType,
            @RequestParam(value = "timeStart", required = false) String timeStart,
            @RequestParam(value = "timeEnd", required = false) String timeEnd,
            @RequestParam(value = "duration", required = false) String duration,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "setAge", required = false) String setAge,
            @RequestParam(value = "brithday", required = false) String brithday, @ModelAttribute CommonParam commonParam) {
        if (StringUtil.isBlank(nickname)) {
            if ("M".equals(gender)) {
                nickname = "小王子";
            } else if ("F".equals(gender)) {
                nickname = "小公主";
            }
        }

        return this.userService.updateUserRole(roleid, nickname, roleType, brithday, gender,
                duration, timeStart, timeEnd, setAge, commonParam);
    }

    /**
     * 删除用户下的某个角色
     * @param username
     *            用户名
     * @param loginTime
     *            登录时间
     * @param userId
     *            用户ID
     * @param userToken
     * @param roleid
     *            角色ID
     * @param request
     * @return
     */
    @ApiOperation(value = "删除用户下的某个角色", httpMethod = "GET")
    @RequestMapping("/user/role/delete")
    public Response<ResultValueDto<Integer>> deleteUserRole(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "loginTime", required = false) String loginTime,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "userToken", required = false) String userToken,
            @RequestParam(value = "roleid") Long roleid, HttpServletRequest request) {

        String clientip = HttpUtil.getIP(request);
        UserRoleRequest userRoleRequest = new UserRoleRequest(username, userId, userToken, roleid, clientip);

        return this.userService.deleteUserRole(userRoleRequest,
                LetvUserVipCommonConstant.getLocale(request));
    }

    /**
     * 获取角色列表
     * @param userId
     * @param username
     * @param userToken
     * @param roleid
     * @param mac
     * @param request
     * @return
     */
    @ApiOperation(value = "获取角色列表", httpMethod = "GET")
    @RequestMapping("/user/role/get")
    public Response<LetvUserRoleListDto> getUserRole(@RequestParam(value = "userId", required = false) Long userId,
                                                     @RequestParam(value = "username", required = false) String username,
                                                     @RequestParam(value = "userToken", required = false) String userToken,
                                                     @RequestParam(value = "roleid", required = false, defaultValue = "0") Long roleid,
                                                     @RequestParam(value = "mac", required = false) String mac, HttpServletRequest request) {

        String clientip = HttpUtil.getIP(request);
        UserRoleRequest UserRoleRequest = new UserRoleRequest(username, userId, userToken, roleid, clientip);
        UserRoleRequest.setUsername(username);
        UserRoleRequest.setMac(mac);

        return this.userService.getUserRole(UserRoleRequest,
                LetvUserVipCommonConstant.getLocale(request));
    }

    /**
     * 创建父母-儿童角色播单
     * @param userId
     *            用户ID
     * @param mac
     * @param albumid
     *            专辑ID
     * @param userToken
     * @param roleid
     *            角色ID
     * @param broadcastId
     *            播控方
     * @param request
     * @return
     */
    @ApiOperation(value = "创建父母-儿童角色播单", httpMethod = "GET")
    @RequestMapping("/user/role/playList/edit")
    public Response<ResultValueDto<Integer>> editRolePlayList(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "mac", required = false) String mac,
            @RequestParam(value = "albumid", required = false) String albumid,
            @RequestParam(value = "userToken", required = false) String userToken,
            @RequestParam(value = "roleid", required = false, defaultValue = "0") Long roleid,
            @RequestParam(value = "broadcastId", required = false) Integer broadcastId, HttpServletRequest request) {
        RolePlayListRequest rolePlayListRequest = new RolePlayListRequest(userId, roleid, userToken, mac, albumid);
        return this.userService.editRolePlayList(rolePlayListRequest,
                LetvUserVipCommonConstant.getLocale(request));
    }

    /**
     * 删除儿童角色播单中的专辑
     * @param userId
     *            用户ID
     * @param mac
     * @param albumid
     *            专辑ID
     * @param userToken
     * @param roleid
     *            角色ID
     * @param broadcastId
     *            播控方
     * @param request
     * @return
     */
    @ApiOperation(value = "删除儿童角色播单中的专辑", httpMethod = "GET")
    @RequestMapping("/user/role/playList/delete")
    public Response<ResultValueDto<Integer>> deleteRolePlayList(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "mac", required = false) String mac,
            @RequestParam(value = "albumid", required = false) String albumid,
            @RequestParam(value = "userToken", required = false) String userToken,
            @RequestParam(value = "roleid", required = false, defaultValue = "0") Long roleid,
            @RequestParam(value = "broadcastId", required = false) Integer broadcastId, HttpServletRequest request) {
        RolePlayListRequest rolePlayListRequest = new RolePlayListRequest(userId, roleid, userToken, mac, albumid);
        return this.userService.deleteRolePlayList(rolePlayListRequest,
                LetvUserVipCommonConstant.getLocale(request));
    }

    /**
     * 获取父母播单数据
     * @param userId
     * @param userToken
     * @param page
     * @param pageSize
     * @param roleid
     * @param broadcastId
     * @param request
     * @return
     */
    @ApiOperation(value = "获取父母播单数据", notes = "获取父母播单数据；<br/>" + "版本更新：<br/>"
            + "- 2017.9月需求：增加角标，统一在chargeInfos中获取(免费则不返回chargeInfos)", httpMethod = "GET")
    @RequestMapping("/user/role/playList/get")
    public PageCommonResponse<RolePlayListDto> getRolePlayList(
            @ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = false) Long userId,
            @ApiParam(value = "用户TOKEN", required = true) @RequestParam(value = "userToken", required = false) String userToken,
            @ApiParam(value = "页号", required = true) @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            @ApiParam(value = "角色ID", required = true) @RequestParam(value = "roleid", required = false, defaultValue = "0") Long roleid,
            @ApiParam(value = "播放合作方", required = true) @RequestParam(value = "broadcastId", required = false) Integer broadcastId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam,
            HttpServletRequest request) {
        page = (page == null) ? 1 : page;
        if (pageSize == null || pageSize > 30) {
            pageSize = 30;
        }
        RolePlayListRequest rolePlayListRequest = new RolePlayListRequest(userId, roleid, userToken, page, pageSize);
        return this.userService.getRolePlayList(rolePlayListRequest,
                LetvUserVipCommonConstant.getLocale(request), commonParam);
    }

    /**
     * 检查手机号是否存在 直接调用用户中心判断手机号是否被注册
     * @param mobile
     *            手机号
     * @return true表示手机号不合法或已存在
     */
    @ApiOperation(value = "检查手机号是否存在 直接调用用户中心判断手机号是否被注册", httpMethod = "GET")
    @RequestMapping(value = "/v2/user/isMobileExisting", method = RequestMethod.GET)
    public Response<Boolean> isMobileExisting(@RequestParam("mobile") String mobile,
                                              @RequestParam(value = "broadcastId", required = false) Integer broadcastId,
                                              @RequestParam(value = "mac", required = false) String mac,
                                              @RequestParam(value = "channel", required = false) String channel) {
        Boolean existing = this.userService.checkMobileExisting(mobile, mac, channel, broadcastId);
        return new Response<Boolean>(existing);
    }

    /**
     * 获取用户收藏(订阅)列表
     * @param token
     * @param page
     * @param pageSize
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题
     * @param category
     *            频道ID［默认0全部 1:电影 2:电视剧 5:动漫 11:综艺 16:纪录片］
     * @param fromType
     *            平台标识 1:主站 2:TV端 3:移动端
     * @param isFull
     * @param type
     *            globalId的前缀.视频：101，专辑：110。默认返回所有，多个类型用英文逗号分隔，type=101,110
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取用户收藏(订阅)列表", httpMethod = "GET", notes = "2017-11月需求 -- 加入瀑布流专题：<br>"
            + "客户端调用时favoriteType=5,12(注意不要有空格,还有需要urlencode)<br>"
            + "返回数据通过favoriteType区别普通专题或者瀑布流专题，当favoriteType=12时为瀑布流专题，此时瀑布流ID看globalid字段")
//     @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/favorite/getFavoriteList")
    public BaseResponse getFavoriteList(
            @ApiParam(name = "token", value = "用户登录token") @RequestParam(value = "token", required = false) String token,
            @ApiParam(name = "page", value = "页数，默认第一页") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "页号，默认30个") @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "favoriteType", value = "收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题") @RequestParam(value = "favoriteType", required = false) String favoriteType,
            @ApiParam(name = "category", value = "频道ID［默认0全部 1:电影 2:电视剧 5:动漫 11:综艺 16:纪录片］") @RequestParam(value = "category", required = false) Integer category,
            @ApiParam(name = "fromType", value = "平台标识 1:主站 2:TV端 3:移动端") @RequestParam(value = "fromType", required = false) Integer fromType,
            @ApiParam(name = "isFull", value = "不传") @RequestParam(value = "isFull", required = false) Integer isFull,
            @ApiParam(name = "type", value = "不传") @RequestParam(value = "type", required = false) String type,
            CommonParam commonParam) {
        if (favoriteType == null) {// 兼容老版本，当favoriteType为空时，设置为专题收藏
            favoriteType = "5";
        }
        if (category == null) {// 默认全部频道的收藏
            category = 0;
        }
        if (fromType == null) {// 默认为tv平台
            fromType = 2;
        }
        if (isFull == null) { // 默认查询追剧收藏的数据合集
            isFull = 0;
        }
        if (StringUtils.isBlank(commonParam.getUserToken())) {
            commonParam.setUserToken(token);
        }
        if (page == null) { // 默认第一页
            page = 1;
        }
        if (pageSize == null) { // 默认30页
            pageSize = 30;
        }
        BroadcastConstant.transFromBroadcastUsername(commonParam);

        if (CommonConstants.BIZCODE_SUBSCRIBE.equalsIgnoreCase(commonParam.getBizCode())) { // 订阅功能
            FOLLOW_TYPE followType = null;
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) { // levidi设置fromType
                followType = FOLLOW_TYPE.LEVIDI;
            }
            FOLLOW_FROM followFrom = null;
            // 平台转换
            if (fromType == 2) {
                followFrom = FOLLOW_FROM.TV;
            } else if (fromType == 3) {
                followFrom = FOLLOW_FROM.MOBILE;
            }
            return this.userService.getFollowList(followType, followFrom, page, pageSize,
                    commonParam);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) { // le
            return this.userService.getCollectionList(type, category, page, pageSize, commonParam);
        } else {
            return this.userService.getFavoriteList(category, favoriteType, fromType, page,
                    pageSize, isFull, commonParam);
        }
    }

    /**
     * 添加收藏
     * @param token
     *            用户登录token
     * @param ztId
     *            专题id
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题
     * @param product
     *            硬件型号
     * @param fromType
     *            平台标识 1:WEB 2:TV端 3:移动端
     * @param globalId
     *            作品库数据的globalId，瀑布流专题以106_开头
     * @param albumId
     *            专辑id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "添加收藏", httpMethod = "GET", notes = "如果是瀑布流专题收藏，不传ztId，favoriteType传12，global_id传112_{{瀑布流专题id}}")
//     @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/favorite/addFavorite")
    public Response<Boolean> addFavorite(
            @ApiParam(name = "token", value = "用户登录token") @RequestParam(value = "token", required = false) String token,
            @ApiParam(name = "ztId", value = "专题ID(瀑布流专题不传，见说明)") @RequestParam(value = "ztId", required = false) Integer ztId,
            @ApiParam(name = "favoriteType", value = "收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题") @RequestParam(value = "favoriteType", required = false) Integer favoriteType,
            @ApiParam(name = "product", value = "硬件型号") @RequestParam(value = "product", required = false) Integer product,
            @ApiParam(name = "fromType", value = "平台标识 1:WEB 2:TV端 3:移动端") @RequestParam(value = "fromType", required = false) Integer fromType,
            @ApiParam(name = "globalid", value = "作品库数据的globalid，瀑布流专题传112_{{瀑布流专题id}}") @RequestParam(value = "globalid", required = false) String globalId,
            @ApiParam(name = "albumid", value = "专辑id") @RequestParam(value = "albumid", required = false) Long albumId,
            @RequestParam(value = "cid", required = false) Integer categoryId, CommonParam commonParam) {

        if (favoriteType == null) {// 兼容老版本，当favoriteType为空时，设置为专题收藏
            favoriteType = 5;
        }
        if (fromType == null) {// 默认为tv平台
            fromType = 2;
        }
        if (product == null) {
            product = 0;
        }

        BroadcastConstant.transFromBroadcastUsername(commonParam);
        if (StringUtils.isBlank(commonParam.getUserToken())) {
            commonParam.setUserToken(token);
        }

        if (CommonConstants.BIZCODE_SUBSCRIBE.equalsIgnoreCase(commonParam.getBizCode())) { // 订阅功能
            FOLLOW_TYPE followType = null;
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) { // levidi设置fromType
                followType = FOLLOW_TYPE.LEVIDI;
            }
            FOLLOW_FROM followFrom = null;
            if (fromType == 2) {
                followFrom = FOLLOW_FROM.TV;
            } else if (fromType == 3) {
                followFrom = FOLLOW_FROM.MOBILE;
            }
            return this.userService.addFollow(globalId, followType, followFrom, commonParam);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            return this.userService.addCollection(globalId, categoryId, commonParam);
        } else {
            return this.userService.addFavorite(ztId, albumId, favoriteType, product, fromType,
                    globalId, commonParam);
        }
    }

    /**
     * 检查收藏状态
     * @param token
     *            用户登录token
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题
     * @param globalId
     *            作品库数据的globalId，瀑布流专题以106_开头
     * @param ztId
     *            专题id
     * @param albumId
     *            专辑id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "检查收藏状态", httpMethod = "GET", notes = "如果是瀑布流专题收藏，不传ztId，favoriteType传12，global_id传112_{{瀑布流专题id}}")
    // @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/favorite/checkIsFavorite")
    public Response<?> checkIsFavorite(
            @ApiParam(name = "token", value = "用户登录token") @RequestParam(value = "token", required = false) String token,
            @ApiParam(name = "favoriteType", value = "收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题") @RequestParam(value = "favoriteType", required = false) Integer favoriteType,
            @ApiParam(name = "globalid", value = "作品库数据的globalid，瀑布流专题传112_{{瀑布流专题id}}") @RequestParam(value = "globalid", required = false) String globalId,
            @ApiParam(name = "ztId", value = "专题ID(瀑布流专题不传，见说明)") @RequestParam(value = "ztId", required = false) Integer ztId,
            @ApiParam(name = "albumid", value = "专辑id") @RequestParam(value = "albumid", required = false) Long albumId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (favoriteType == null) {// 兼容老版本，当favoriteType为空时，设置为专题收藏
            favoriteType = 5;
        }
        BroadcastConstant.transFromBroadcastUsername(commonParam);
        if (StringUtils.isBlank(commonParam.getUserToken())) {
            commonParam.setUserToken(token);
        }

        if (CommonConstants.BIZCODE_SUBSCRIBE.equalsIgnoreCase(commonParam.getBizCode())) { // 订阅功能
            FOLLOW_TYPE followType = null;
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) { // levidi设置fromType
                followType = FOLLOW_TYPE.LEVIDI;
            }
            return this.userService.checkIsFollow(globalId, followType, commonParam);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) { // le
            return this.userService.checkIsCollection(globalId, commonParam);
        } else { // 收藏
            return this.userService.checkIsFavorite(favoriteType, ztId, albumId, globalId,
                    commonParam);
        }
    }

    /**
     * 删除用户收藏信息
     * @param token
     *            用户登录token
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题
     * @param favoriteIds
     *            收藏id，批量删除时可以传多个id，id之间用逗号隔开
     * @param ztId
     *            专题id，如果favoriteType=5且favoriteIds为空，则表示根据ztId来取消专题收藏
     * @param albumId
     *            专辑id
     * @param globalId
     *            作品库数据的globalId，瀑布流专题以106_开头（优先取favoriteIds）
     * @param operationType
     *            操作类型，operationType=deleteAll表示要删除全部
     * @param request
     * @return
     */
    @ApiOperation(value = "删除用户收藏信息", httpMethod = "GET", notes = "如果是瀑布流专题取消收藏，不传ztId，favoriteType传12，favoriteIds传112_{{瀑布流专题id}}（英文逗号相隔）"

    )
    // @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/favorite/delFavorite")
    public Response<Boolean> delFavorite(
            @ApiParam(name = "token", value = "用户登录token") @RequestParam(value = "token", required = false) String token,
            @ApiParam(name = "favoriteType", value = "收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题") @RequestParam(value = "favoriteType", required = false) Integer favoriteType,
            @ApiParam(name = "favoriteIds", value = "收藏id，批量删除时可以传多个id，id之间用逗号隔开") @RequestParam(value = "favoriteIds", required = false) String favoriteIds,
            @ApiParam(name = "ztId", value = "专题ID(瀑布流专题不传，见说明)") @RequestParam(value = "ztId", required = false) String ztId,
            @ApiParam(name = "albumid", value = "专辑id") @RequestParam(value = "albumid", required = false) Long albumId,
            @ApiParam(name = "globalid", value = "作品库数据的globalid，瀑布流专题传112_{{瀑布流专题id}}") @RequestParam(value = "globalid", required = false) String globalId,
            @RequestParam(value = "flush", required = false) Integer flush,
            @ApiParam(name = "operationType", value = "操作类型，operationType=deleteAll表示要删除全部") @RequestParam(value = "operationType", required = false) String operationType,
            CommonParam commonParam) {
        BroadcastConstant.transFromBroadcastUsername(commonParam);
        if (StringUtils.isBlank(commonParam.getUserToken())) {
            commonParam.setUserToken(token);
        }
        if (favoriteType == null) {// 默认是删除追剧与收藏
            favoriteType = 1;
        }
        if (CommonConstants.BIZCODE_SUBSCRIBE.equalsIgnoreCase(commonParam.getBizCode())) { // 订阅功能
            FOLLOW_TYPE followType = null;
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) { // levidi设置fromType
                followType = FOLLOW_TYPE.LEVIDI;
            }
            return this.userService.delFollow(globalId, followType, commonParam);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) { // le
            return this.userService.delCollection(globalId, flush, commonParam);
        } else {
            return this.userService.delFavorite(favoriteType, favoriteIds, ztId, albumId, globalId,
                    operationType, commonParam);
        }
    }

    /**
     * 删除用户播放单追剧收藏信息
     * @param username
     * @param token
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源 12:瀑布流专题
     * @param favid
     *            收藏id，operationType不等于deleteAll时是必填参数
     * @param globalId
     *            作品库数据的globalId，瀑布流专题以106_开头
     * @param operationType
     *            操作类型，deleteAll--删除全部，否则就是单条或者批量删除
     * @param request
     * @return
     */
    @ApiOperation(value = "删除用户播放单追剧收藏信息", httpMethod = "GET", notes = "如果是瀑布流专题取消收藏，不传ztId，favoriteType传12，favoriteIds传112_{{瀑布流专题id}}（英文逗号相隔）")
//    @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/favorite/delFavoriteByZtId")
    @Deprecated
    public Response<Boolean> delFavoriteByZtId(@RequestParam("username") String username,
                                               @RequestParam(value = "token", required = false) String token,
                                               @RequestParam(value = "favoriteType", required = false, defaultValue = "5") Integer favoriteType,
                                               @RequestParam(value = "favoriteIds", required = false) String favoriteIds,
                                               @RequestParam(value = "ztId", required = false) String ztId,
                                               @RequestParam(value = "operationType", required = false) String operationType,
                                               @RequestParam(value = "global_id", required = false) String global_id, CommonParam commonParam) {
        username = BroadcastConstant.transFromBroadcastUsername(username, commonParam.getBroadcastId());
        if (StringUtils.isBlank(commonParam.getUserToken())) {
            commonParam.setUserToken(token);
        }
        return this.userService.delFavorite(favoriteType, favoriteIds, ztId, null, operationType,
                global_id, commonParam);
    }

    /**
     * 获取弹幕列表接口
     * @param videoId
     *            视频id
     * @param playTime
     *            视频播放时间 在0-300内，取前5分钟弹幕，300-600第二个5分钟。。。
     * @param token
     *            登录用户token
     * @param param
     * @return
     */
    @ApiOperation(value = "获取弹幕列表接口", httpMethod = "GET")
    @RequestMapping("/user/danmu/get")
    public Response<DanmuDto> getDanmuList(@RequestParam(value = "videoId") Long videoId,
                                           @RequestParam(value = "playTime", required = false) Long playTime,
                                           @RequestParam(value = "albumId") Long albumId, CommonParam param) {

        return new Response<DanmuDto>(this.userService.getDanmuList(videoId, playTime, albumId,
                param));
    }

    /**
     * 提交直播、点播、轮播弹幕信息
     * @param roomId
     *            聊天室id
     * @param message
     *            消息内容 （直播、点播）
     * @param userToken
     *            用户token（封装在CommonParam中）
     * @param color
     *            内容颜色值，例如：00FCFF
     * @param font
     *            字体大小［s:小号 m:中号 l:大号］默认值：m
     * @param position
     *            显示的位置 ［1:顶部 2:中间 3:底部 4:随机］默认值：4
     * @param from
     *            来源［1-Web，2-iPhone，3-iPad，4-TV，5-PC桌面，6-Android
     *            Phone，7-LePhone， 8-M站］，默认值：1
     * @param forhost
     *            是否向嘉宾提问
     * @param callback
     *            跨域
     * @param type
     *            类型 [1:普通消息 2:pc端带表情的消息 9:聊天室公告] 默认值1，白名单IP才能发布公告
     * @param realname
     *            实名制认证 1需要认证 其他值，不进行实名验证
     * @param x
     *            pc端表情x坐标
     * @param y
     *            pc端表情y坐标
     * @param vtkey
     *            join 聊天室时，socket服务器返回的vtkey
     * @param videoId
     *            点播弹幕
     * @param albumId
     *            点播弹幕
     * @param cid
     *            点播弹幕
     * @param start
     * @param dmType
     *            txt:文字 em:表情 redpaper:红包 vote:投票
     *            recommend:推荐］默认值：txt［备注：表情类型的弹幕无需审核］
     * @param ip
     *            点播弹幕
     *            如客户端：TV、移动等设备必须传递用户的IP，否则IP禁言会封代理服务器的IP，导致该代理服务器的IP不可以发布弹幕
     * @param isIdentify
     *            点播弹幕 如需判断是否实名认证此参数请传1，目前需求不需要实名认证，实际应该传0，但该值在用户中心目前不生效
     * @param identifyType
     *            0-直播弹幕，1-点播弹幕发送
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "提交直播、点播、轮播弹幕信息", httpMethod = "GET")
    @RequestMapping(value = "/user/danmu/msg/commit")
    public Response<DanmuChatDto> sendMsg(@RequestParam(value = "roomId", required = false) String roomId,
                                          @RequestParam("message") String message, @RequestParam(value = "color", required = false) String color,
                                          @RequestParam(value = "font", defaultValue = "m", required = false) String font,
                                          @RequestParam(value = "position", defaultValue = "4", required = false) Integer position,
                                          @RequestParam(value = "from", defaultValue = "7", required = false) Integer from,
                                          @RequestParam(value = "forhost", required = false) Boolean forhost,
                                          @RequestParam(value = "callback", required = false) String callback,
                                          @RequestParam(value = "type", defaultValue = "1", required = false) Integer type,
                                          @RequestParam(value = "realname", required = false) Integer realname,
                                          @RequestParam(value = "x", required = false) String x,
                                          @RequestParam(value = "y", required = false) String y,
                                          @RequestParam(value = "vtkey", required = false) String vtkey,
                                          @RequestParam(value = "videoId", required = false) Integer videoId,
                                          @RequestParam(value = "albumId", required = false) Integer albumId,
                                          @RequestParam(value = "cid", required = false) Integer cid,
                                          @RequestParam(value = "start", required = false) Integer start,
                                          @RequestParam(value = "dmType", defaultValue = "txt", required = false) String dmType,
                                          @RequestParam(value = "ip", required = false) String ip,
                                          @RequestParam(value = "isIdentify", defaultValue = "1", required = false) Integer isIdentify,
                                          @RequestParam(value = "identifyType", defaultValue = "-1", required = false) Integer identifyType,
                                          @ModelAttribute CommonParam commonParam) {
        if (identifyType == 1) { // 点播弹幕
            return this.userService.sendDanmuMsg(videoId, albumId, cid, start, message, color, font,
                    dmType, position, from, callback, ip, isIdentify, x, y, commonParam);
        } else if (identifyType == 0) { // 直播弹幕
            return this.userService.sendChatMsg(roomId, message, forhost, color, font, position,
                    from, callback, type, realname, x, y, vtkey, commonParam);
        }
        Response<DanmuChatDto> response = new Response<DanmuChatDto>();
        response.setResultStatus(0);
        response.setData(new DanmuChatDto(0));
        return response;
    }

    /**
     * get user info for golive application
     * @param from
     *            golive parameter
     * @param sign
     *            md5 sign
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取golive用户信息", httpMethod = "GET")
    @RequestMapping(value = "/user/info/get")
    public Response<LetvUserInfoDto> getUserInfo(@RequestParam(value = "from", required = false) String from,
                                                 @RequestParam(value = "sign", required = false) String sign, @ModelAttribute CommonParam commonParam) {
        return this.userService.getUserInfo(from, sign, commonParam);
    }

    /**
     * @param roleid
     * @param albumIds
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "playList/check", httpMethod = "GET")
    @RequestMapping("/user/role/playList/check")
    public Response<CommonResultDto<Map<String, Integer>>> checkRolePlayList(@RequestParam("roleid") String roleid,
                                                                             @RequestParam("albumIds") String albumIds, @ModelAttribute CommonParam commonParam) {
        return this.userService.checkRolePlayList(albumIds, roleid, commonParam);
    }

    /**
     * @param dataType
     *            类型：1、专辑；2、视频
     * @param albumId
     *            专辑id
     * @param videoId
     *            视频id
     * @param page
     * @param pageSize
     * @param source
     *            请求来源。1 web, 2 iPhone, 3 Android 4 wPhone 5 pad, 6 tv, 7 pc, 8
     *            msite
     *            9 SinaWeibo 10 LePhone(乐视手机) 30 sarrs(乐见) 31 乐搜
     *            见 {@link UserTpConstant.COMMIT_SOURCE}
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取评论列表", httpMethod = "GET")
    @RequestMapping("/user/comment/getCommentList")
    public Response<UserCommentDto> getCommentList(
            @RequestParam(value = "dataType", required = false) Integer dataType,
            @RequestParam(value = "albumId", required = false) String albumId,
            @RequestParam(value = "videoId", required = false) String videoId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "source", required = false) Integer source, @ModelAttribute CommonParam commonParam) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {// 默认每页20条数据
            pageSize = 20;
        }
        return this.userService.getCommentList(dataType, albumId, videoId, page, pageSize, source,
                commonParam);
    }

    /**
     * check user's child lock status, or check user password to get a lockToken
     * to update the PIN
     * @param actionType
     *            check type, 1--check child lock status, 2--check user password
     *            to get a lockToken, user's account password (assigned to
     *            ticket) is needed; 3--check user PIN code to get a lockToken,
     *            user's PIN code (assigned to pin) is needed; others are
     *            illegal
     * @param ticket
     *            user's account password
     * @param pin
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "检查儿童锁", httpMethod = "GET")
    @RequestMapping("/user/childlock/check")
    public Response<ChildLockDto> checkUserChildLock(@RequestParam(value = "actionType") Integer actionType,
                                                     @RequestParam(value = "ticket", required = false) String ticket,
                                                     @RequestParam(value = "pin", required = false) String pin, @ModelAttribute CommonParam commonParam) {
        return this.userService.checkUserChildLock(actionType, ticket, pin, commonParam);
    }

    /**
     * User uses PIN code or lock token to set child lock on or off;
     * @param actionType
     *            set type, 1--set user PIN code for the first time and set
     *            child lock on, "pin" is needed, 2--set user child lock to
     *            target status with PIN code, "status" and "pin" are needed,
     *            3--set user child lock to target status with lockToken,
     *            "status" , "pin" and "lockToken" are needed; others are
     *            illegal
     * @param pin
     * @param status
     *            0--child lock off, 1--child lock on; others are illegal,
     *            regard as off
     * @param lockToken
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "设置儿童锁", httpMethod = "GET")
    @RequestMapping("/user/childlock/set")
    public Response<ChildLockDto> setUserChildLock(@RequestParam(value = "actionType") Integer actionType,
                                                   @RequestParam(value = "pin", required = false) String pin,
                                                   @RequestParam(value = "status", required = false) Integer status,
                                                   @RequestParam(value = "lockToken", required = false) String lockToken,
                                                   @ModelAttribute CommonParam commonParam) {
        return this.userService.setUserChildLock(actionType, pin, status, lockToken, commonParam);
    }

    /**
     * 获取用户订阅的addon信息
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取用户订阅的addon信息", httpMethod = "GET")
    @RequestMapping("/user/addon/get")
    public Response<Set<UserAccountDto.VipInfoV2Dto>> getUserAddon(@ModelAttribute CommonParam commonParam) {
        return this.userService.getUserAddon(commonParam);
    }

    /**
     * 用户成长值信息
     * @param channel
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "用户成长值信息", notes = "示例：http://api.itv.cp21.ott.cibntv.net/iptv/api/new/user/odp/getUserGrowth.json?salesArea=&terminalApplication=media_cibn&appCode=294&userId=270868034&mac=9A7EEAF97662&bsChannel=01001001000&loginTime=2017-09-14+10%3A12%3A23&roleid=0&appVersion=2.12.0&username=stv_58f861a46a13555&terminalBrand=letv&client=android&deviceKey=&terminalSeries=U4S&broadcastId=2&langcode=zh_cn&wcode=cn", httpMethod = "GET")
//     @CheckLoginInterceptorAnnotation
    @RequestMapping("/user/odp/getUserGrowth")
    public Response getUserAccount(
            @ApiParam(value = "ODP平台分配的渠道ID", required = false) @RequestParam(value = "channel", required = false) String channel,
            @ApiParam(value = "ODP平台分配的密钥", required = false) @RequestParam(value = "saltkey", required = false) String saltkey,
            @ApiParam(value = "unix时间戳(s)", required = false) @RequestParam(value = "timestamp", required = false) Long timestamp,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return this.userService.getUserGrowth(channel, timestamp, saltkey, commonParam);
    }

}

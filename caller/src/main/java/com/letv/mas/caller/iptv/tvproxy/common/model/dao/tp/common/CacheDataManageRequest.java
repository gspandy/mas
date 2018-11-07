package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.common;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveMsgCodeConstant;

import java.util.Map;

/**
 * 缓存数据管理请求封装类；
 * 应用中有些缓存数据（在缓存服务器或内存中），特定条件下需要即时管理；
 * @author KevinYi
 */
public class CacheDataManageRequest {

    /**
     * 业务模块编号，比如，频道、直播、音乐、推荐、搜索、终端、用户、视频、播放等
     */
    private String moduleCode;

    /**
     * 业务模块名称
     */
    private String moduleName;

    /**
     * 操作指令，该命令决定操作那块业务的数据
     */
    private Integer operateCommand;

    /**
     * 缓存数据类型，1--内存数据，2--缓存数据，3--内存+缓存数据，参见CacheDataManageConstants.
     * CACHE_DATA_MANAGE_CACHE_DATA_TYPE_*
     */
    private Integer cacheDataType;

    /**
     * 操作类型，1--新增，2--删除，3--查询，4--通过配置文件修改，5--通过参数修改，参见CacheDataManageConstants.
     * CACHE_DATA_MANAGE_OPERATE_TYPE_*
     */
    private Integer operateType;

    /**
     * 数据参数，主要用户修改数据；作为通用request，不再定义与业务耦合的具体参数名，而是使用params封装
     */
    private Map<String, Object> params;

    public CacheDataManageRequest(String moduleCode, String moduleName, Integer operateCommand, Integer cacheDataType,
            Integer operateType) {
        super();
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.operateCommand = operateCommand;
        this.cacheDataType = cacheDataType;
        this.operateType = operateType;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (this.operateCommand == null || this.operateCommand < 0 || this.cacheDataType == null
                || this.cacheDataType < 0 || this.operateType == null || this.operateType < 0) {
            return LiveMsgCodeConstant.LIVE_CACHE_DATA_MANAGE_ILLEGAL_PARAMETERS;
        }
        return LiveMsgCodeConstant.COMMON_REQUEST_SUCCESS;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getOperateCommand() {
        return this.operateCommand;
    }

    public void setOperateCommand(Integer operateCommand) {
        this.operateCommand = operateCommand;
    }

    public Integer getCacheDataType() {
        return this.cacheDataType;
    }

    public void setCacheDataType(Integer cacheDataType) {
        this.cacheDataType = cacheDataType;
    }

    public Integer getOperateType() {
        return this.operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}

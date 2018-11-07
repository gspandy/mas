package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import java.util.Map;

/**
 * 缓存数据管理结果封装类；
 * 应用中有些缓存数据（在缓存服务器或内存中），特定条件下需要即时管理；
 * @author KevinYi
 */
public class CacheDataManageResultDto extends BaseDto {

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
     * 操作结果，0--失败，1--成功，2--未定义操作，参见CacheDataManageConstants.
     * CACHE_DATA_MANAGE_OPERATE_RESULT_*
     */
    private Integer operateResult;

    /**
     * 本次操作影响到的数据量
     */
    private Integer dataSize;

    /**
     * 数据快照，可以返回想看到的操作前或操作后的数据内容
     */
    private Map<String, Object> dataSnapshoot;

    /**
     * 操作开始时间，单位-毫秒
     */
    private Long operateStartTime;

    /**
     * 操作结束时间，单位-毫秒
     */
    private Long operateEndTime;

    public CacheDataManageResultDto() {
        super();
    }

    public CacheDataManageResultDto(String moduleCode, String moduleName, Integer operateCommand,
            Integer cacheDataType, Integer operateType) {
        super();
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.operateCommand = operateCommand;
        this.cacheDataType = cacheDataType;
        this.operateType = operateType;
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

    public Integer getOperateResult() {
        return this.operateResult;
    }

    public void setOperateResult(Integer operateResult) {
        this.operateResult = operateResult;
    }

    public Integer getDataSize() {
        return this.dataSize;
    }

    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }

    public Map<String, Object> getDataSnapshoot() {
        return this.dataSnapshoot;
    }

    public void setDataSnapshoot(Map<String, Object> dataSnapshoot) {
        this.dataSnapshoot = dataSnapshoot;
    }

    public Long getOperateStartTime() {
        return this.operateStartTime;
    }

    public void setOperateStartTime(Long operateStartTime) {
        this.operateStartTime = operateStartTime;
    }

    public Long getOperateEndTime() {
        return this.operateEndTime;
    }

    public void setOperateEndTime(Long operateEndTime) {
        this.operateEndTime = operateEndTime;
    }

}

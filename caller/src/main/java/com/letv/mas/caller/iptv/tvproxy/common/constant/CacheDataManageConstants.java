package com.letv.mas.caller.iptv.tvproxy.common.constant;

/**
 * 缓存数据管理业务静态常量类；
 * 应用中有些缓存数据（在缓存服务器或内存中），特定条件下需要即时管理；
 * @author KevinYi
 */
public class CacheDataManageConstants {

    /**
     * 直播缓存数据操作的数据类型，1--内存数据，2--缓存数据，3--内存+缓存数据
     */
    public static final int CACHE_DATA_MANAGE_CACHE_DATA_TYPE_1 = 1;
    public static final int CACHE_DATA_MANAGE_CACHE_DATA_TYPE_2 = 2;
    public static final int CACHE_DATA_MANAGE_CACHE_DATA_TYPE_3 = 3;

    /**
     * 直播缓存数据操作的操作类型，1--新增，2--删除，3--查询，4--通过配置文件修改，5--通过参数修改
     */
    public static final int CACHE_DATA_MANAGE_OPERATE_TYPE_1 = 1;
    public static final int CACHE_DATA_MANAGE_OPERATE_TYPE_2 = 2;
    public static final int CACHE_DATA_MANAGE_OPERATE_TYPE_3 = 3;
    public static final int CACHE_DATA_MANAGE_OPERATE_TYPE_4 = 4;
    public static final int CACHE_DATA_MANAGE_OPERATE_TYPE_5 = 5;

    /**
     * 直播缓存数据操作的操作结果，0--失败，1--成功，2--未定义操作
     */
    public static final int CACHE_DATA_MANAGE_OPERATE_RESULT_0 = 0;
    public static final int CACHE_DATA_MANAGE_OPERATE_RESULT_1 = 1;
    public static final int CACHE_DATA_MANAGE_OPERATE_RESULT_2 = 2;
}

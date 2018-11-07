package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChannelDataExtendMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 访问频道扩展映射表--乐视儿童
 */
@Component(value = "v2.ChannelDataExtendMysqlDao")
public interface ChannelDataExtendMysqlDao {
    /**
     * 年龄性别星期获取映射关系
     * @param blockId
     * @param age
     * @param gender
     * @param week
     * @return
     */
    public List<ChannelDataExtendMysqlTable> getChannelMapData(@Param(value = "blockId") String blockId,
                                                               @Param(value = "age") String age, @Param(value = "gender") String gender, @Param(value = "week") String week);

    /**
     * 年龄获取映射关系
     * @param blockId
     * @param age
     * @return
     */
    public List<ChannelDataExtendMysqlTable> getBlockIdByAge(@Param(value = "blockId") String blockId,
            @Param(value = "age") String age);

    /**
     * 年龄性别获取映射关系
     * @param blockId
     * @param age
     * @param gender
     * @return
     */
    public List<ChannelDataExtendMysqlTable> getBlockIdByAgeGender(@Param(value = "blockId") String blockId,
            @Param(value = "age") String age, @Param(value = "gender") String gender);

    /**
     * 年龄星期获取映射关系
     * @param blockId
     * @param age
     * @param week
     * @return
     */
    public List<ChannelDataExtendMysqlTable> getBlockIdByAgeWeek(@Param(value = "blockId") String blockId,
            @Param(value = "age") String age, @Param(value = "week") String week);
}

package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SpecialVideoMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * special video mysql dao
 */
@Component(value = "specialVideoMysqlDao")
public interface SpecialVideoMysqlDao {

    /**
     * Get special video by type.
     * @param type
     *            The type of special video. For example, 4k or drm.
     * @return
     */
    public List<SpecialVideoMysqlTable> getSpecialStreamVideo(@Param(value = "type") String type,
                                                              @Param(value = "near") String near);

    /**
     * Insert special video to the small table.
     * @param specialVideoMysqlTable
     */
    public void insertSpecialVideo(SpecialVideoMysqlTable specialVideoMysqlTable);

    /**
     * Update special video to the small table.
     * @param specialVideoMysqlTable
     */
    public void updateSpecialVideo(SpecialVideoMysqlTable specialVideoMysqlTable);

    /**
     * Delete special video from the small table.
     * @param videoId
     * @param type
     */
    public void deleteSpecialVideo(@Param(value = "videoid") String videoId, @Param(value = "type") String type);

    /**
     * Query special video by video id.
     * @param videoId
     * @param type
     * @return
     */
    public List<SpecialVideoMysqlTable> getSpecialStreamVideoByVideoId(@Param(value = "videoid") String videoId,
            @Param(value = "type") String type);

}

package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.MmsHotAlbumMonitorMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component(value = "v2.MmsHotAlbumMonitorMysqlDao")
public interface MmsHotAlbumMonitorMysqlDao {

    /**
     * 获取监控列表信息
     * @return
     */
    public List<MmsHotAlbumMonitorMysqlTable> getList();

    /**
     * 根据id删除指定记录
     * @param id
     */
    public void del(@Param(value = "album_id") Integer album_id);

    /**
     * 获取专辑视频最近更新的push_time
     */
    public List<String> getNewPushTime(@Param(value = "album_id") Integer album_id);
}

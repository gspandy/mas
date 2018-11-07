package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ItvLabelResource;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SearchIptvAlbumInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component(value = "v2.AlbumMysqlDao")
public interface AlbumMysqlDao {

    /**
     * 根据专辑id获得专辑
     * @param id
     *            专辑id
     * @return
     */
    AlbumMysqlTable getAlbumById(@Param(value = "id") Long id);

    List<AlbumMysqlTable> getAlbumListByIdList(List<Long> ids);

    List<AlbumMysqlTable> getAllChargeAlbumList(@Param(value = "start") Integer start,
            @Param(value = "size") Integer size, @Param(value = "payPlats") String[] payPlats);

    /**
     * 获取创建时间在createTime之后、更新日期在updateTime之前的若干条数据；即在createTime创建之后，
     * 在updateTime之后都没有更新过的专辑id列表
     * @param updateTime
     * @param createTime
     * @param start
     * @param end
     * @return
     */
    List<Long> getAlbumIdListUpdatedAfter(@Param(value = "updateTime") String updateTime,
            @Param(value = "createTime") String createTime, @Param(value = "start") Integer start,
            @Param(value = "end") Integer end);

    /**
     * 取创建时间或上映时间在createTime之后的所有专辑id列表；如果createTime为空，则返回全量数据
     * @param createTime
     * @return
     */
    List<Long> getAlbumIdListCreatedOrReleaseAfter(@Param(value = "createTime") String createTime);

    public List<Long> getIptvAlbumInfoIds();

    public List<Long> getIptvAlbumInfoIdsForSyncMedia();

    public void insertAlbumInfo(AlbumMysqlTable albumMysqlTable);

    public void updateAlbumInfoById(AlbumMysqlTable albumMysqlTable);

    public void deleteAlbumInfoById(Long id);

    public SearchIptvAlbumInfo getSearchIptvAlbumInfoById(Long id);

    public void updateSearchIptvAlbumInfoById(SearchIptvAlbumInfo searchIptvAlbumInfo);

    public void insertSearchIptvAlbumInfo(SearchIptvAlbumInfo searchIptvAlbumInfo);

    public void deleteSearchIptvAlbumInfoById(Long id);

    public List<AlbumMysqlTable> listIptvAlbumInfoByLimit(@Param("start") Integer start, @Param("limit") Integer limit);

    public Integer getCountByLabelId(@Param(value = "categoryId") Integer categoryId,
            @Param(value = "labelId") Long labelId, @Param(value = "broadcastId") Integer broadcastId);

    public List<ItvLabelResource> getListByLabelId(@Param(value = "categoryId") Integer categoryId,
                                                   @Param(value = "labelId") Long labelId, @Param(value = "broadcastId") Integer broadcastId,
                                                   @Param(value = "start") int start, @Param(value = "limit") int limit);

    public Integer getCountByLabelIdForZT(@Param(value = "categoryId") Integer categoryId,
            @Param(value = "labelId") Long labelId);

    public List<ItvLabelResource> getListByLabelIdForZT(@Param(value = "categoryId") Integer categoryId,
            @Param(value = "labelId") Long labelId, @Param(value = "start") int start, @Param(value = "limit") int limit);

    /**
     * 可分页获取指定分类的专辑id集
     * @param id
     * @param cids
     * @param size
     * @return
     */
    List<Long> getAlbumId4DailyRefresh(@Param(value = "id") Long id, @Param(value = "cids") int[] cids,
            @Param(value = "size") int size);
}

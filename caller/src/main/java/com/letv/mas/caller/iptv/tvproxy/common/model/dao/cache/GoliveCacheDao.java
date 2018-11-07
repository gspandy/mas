package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GoLiveNavigationDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import org.springframework.stereotype.Component;

@Component
public class GoliveCacheDao extends BaseCacheDao {

    /**
     * 设置golive桌面所有数据
     * @param list
     * @param commonParam
     */
    public void setGoLiveNavigationDtoList(List<GoLiveNavigationDto> list, CommonParam commonParam) {
        String key = CacheContentConstants.GOLIVE_DESKTOP_KEY + commonParam.getLangcode();
        this.cacheTemplate.set(key, list);
    }

    /**
     * 获取golive桌面所有数据
     * @param commonParam
     * @return
     */
    public List<GoLiveNavigationDto> getGoLiveNavigationDtoList(CommonParam commonParam) {
        String key = CacheContentConstants.GOLIVE_DESKTOP_KEY + commonParam.getLangcode();
        return this.cacheTemplate.get(key, new LetvTypeReference<List<GoLiveNavigationDto>>() {
        });
    }

    /**
     * 将CMS列表结果保存到缓存
     * @param list
     * @param commonParam
     */
    public void setCmsResultList(List<CmsPageTpResponse> list, CommonParam commonParam) {
        String key = CacheContentConstants.GOLIVE_DESKTOP_KEY2 + commonParam.getLangcode();
        this.cacheTemplate.set(key, list);
    }

    /**
     * 从缓存中取出列表
     * @param commonParam
     * @return
     */
    public List<CmsPageTpResponse> getCmsResultList(CommonParam commonParam) {
        String key = CacheContentConstants.GOLIVE_DESKTOP_KEY2 + commonParam.getLangcode();
        return this.cacheTemplate.get(key, new LetvTypeReference<List<CmsPageTpResponse>>() {
        });
    }

}

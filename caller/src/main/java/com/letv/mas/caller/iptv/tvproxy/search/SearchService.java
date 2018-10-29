package com.letv.mas.caller.iptv.tvproxy.search;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import serving.GenericServingResponse;
import com.letv.mas.caller.iptv.tvproxy.search.model.dao.tp.SearchTpDao;

@Component(value = "v2.SearchService")
@SuppressWarnings("all")
public class SearchService extends BaseService {

    private final static Logger log = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private SearchTpDao searchTpDao;

    /**
     * 复合检索
     * @param channelId
     * @param searchRequest
     *            检索条件
     * @param locale
     *            检索语言环境
     * @return
     */
    public GenericServingResponse searchTypes2(String from, String dt, Integer page, Integer pageSize,
                                                         String word, String categoryId, String ph, String searchContent, String id, String splatid,
                                                         Integer channelId, String leIds, String productId, String pushChild, String repo_type, String pcp,
                                                         CommonParam commonParam) {
        GenericServingResponse result = searchTpDao.search(from, dt, page, pageSize, word,
                categoryId, null, ph, "1", 1, null, searchContent, splatid, leIds, null, repo_type, null, null, null,
                null, null, null, null, productId, pushChild, pcp, commonParam);
        return result;
    }
}

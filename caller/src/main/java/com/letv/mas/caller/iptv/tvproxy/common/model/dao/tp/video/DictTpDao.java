package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.DictTp;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component("v2.DictTpDao")
public class DictTpDao extends BaseTpDao {

    private final static Logger log = LoggerFactory.getLogger(DictTpDao.class);

    private final static Map<String, String> VRS_LANG_MAP = new HashMap<String, String>();
    private final static String VRS_ZH_CN = "";
    private final static String VRS_ZH_HK = "Hk";
    private final static String VRS_EN_US = "En";
    private final static String VRS_ZH_TW = "Tw";

    static {
        VRS_LANG_MAP.put("zh_cn", VRS_ZH_CN);
        VRS_LANG_MAP.put("zh_hk", VRS_ZH_HK);
        VRS_LANG_MAP.put("en_us", VRS_EN_US);
        VRS_LANG_MAP.put("zh_tw", VRS_ZH_TW);
    }

    /**
     * 根据字典ID查询字典信息
     */
    private static final String getVrsMmsInnerDictUrl() {
        return ApplicationUtils.get(ApplicationConstants.VRS_MMS_INNER_DICT);
    }

    public DictTp getDictById(String dictId, String langCode) {
        DictTp dictTp = new DictTp();
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("dictId", dictId);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("lang", VRS_LANG_MAP.get(langCode.toLowerCase()));
            HttpEntity<String> httpEntity = new HttpEntity<String>(requestHeaders);

            ResponseEntity<String> result = this.restTemplate.exchange(getVrsMmsInnerDictUrl(), HttpMethod.GET,
                    httpEntity, String.class, params);

            log.info("[url]:" + getVrsMmsInnerDictUrl() + "[params]:" + "[dictId]" + dictId + "[result]:"
                    + result.getBody());
            if (result != null) {
                ObjectMapper m = new ObjectMapper();
                dictTp = m.readValue(result.getBody(), DictTp.class);
            }
        } catch (Exception e) {
            log.error(
                    "[url]:" + getVrsMmsInnerDictUrl() + "[params]:" + "[dictId]" + dictId + "[Exception]:"
                            + e.getStackTrace(), e);
        }

        return dictTp;
    }
}

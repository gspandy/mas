package com.letv.mas.caller.iptv.tvproxy.common.service;

import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.LanguageDictionaryMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.service.MutilLanguageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(value = "v2.MutilLanguageService")
public class MutilLanguageService extends BaseService {

    private static Map<String, String> langMap = new HashMap<String, String>();
    private static volatile long LAST_CACHETIME = 0;// 上次缓存时间
    private final static long CACHE_TIMEOUT = 24 * 60 * 60 * 1000; // 字典数据缓存时常24小时
    private final static Object LOCK_OBJECT = new Object();

    /**
     * 获取多语言字典表的所有数据
     * @return
     */
    public List<LanguageDictionaryMysqlTable> getAllMessage() {
        return this.facadeMysqlDao.getLanguageDictionaryMysqlDao().getAllMessage();
    }

    /**
     * 获取对应表对应字段的多语言key
     * @param tableName
     *            表名
     * @param rowid
     *            对应行的唯一标识
     * @param field
     *            对应字段名称
     * @param lang
     *            多语言
     * @return
     */
    public String getKey(String tableName, String rowid, String field, String lang) {
        if (StringUtils.isNotBlank(tableName) && StringUtils.isNotBlank(rowid) && StringUtils.isNotBlank(field)
                && StringUtils.isNotBlank(lang)) {
            // 香港老版本，多语言应为传递的是en,而不是en_us；强行做兼容
            if ("EN".equalsIgnoreCase(lang)) {
                lang = "en_US";
            }

            return (tableName + "_" + rowid + "_" + field + "_" + lang).toUpperCase();
        }
        return null;
    }

    /**
     * 从数据库中读取多语言，更新缓存
     */
    public void updateCache() {
        List<LanguageDictionaryMysqlTable> langList = this.getAllMessage();
        if (langList != null && langList.size() > 0) {
            langMap.clear();
            String key = null;
            for (LanguageDictionaryMysqlTable dic : langList) {
                key = this.getKey(dic.getTargetTable(), dic.getTargetRowId(), dic.getTargetField(), dic.getLang());
                langMap.put(key, dic.getMessage());
            }
        }
        LAST_CACHETIME = System.currentTimeMillis();
    }

    /**
     * 根据key，获取对应字段的多语言值
     * @param key
     *            由tablename_rowid_field组成
     * @return
     */
    public String getMessage(String key) {
        if (LAST_CACHETIME == 0 || (System.currentTimeMillis() - LAST_CACHETIME) > CACHE_TIMEOUT) {
            synchronized (LOCK_OBJECT) {
                if (LAST_CACHETIME == 0 || (System.currentTimeMillis() - LAST_CACHETIME) > CACHE_TIMEOUT) {
                    updateCache();
                }
            }
        }

        if (StringUtils.isNotBlank(key)) {
            return langMap.get(key.toUpperCase());
        }
        return null;
    }

    /**
     * 获取对应字段的多语言值
     * @param tableName
     *            表名
     * @param rowid
     *            对应行的唯一标识
     * @param field
     *            对应字段名称
     * @param lang
     *            多语言
     * @return
     */
    public String getMessage(String tableName, String rowid, String field, String lang) {
        return this.getMessage(getKey(tableName, rowid, field, lang));
    }
}

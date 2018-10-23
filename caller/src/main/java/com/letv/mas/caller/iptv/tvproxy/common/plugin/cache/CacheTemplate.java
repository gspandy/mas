package com.letv.mas.caller.iptv.tvproxy.common.plugin.cache;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvCacheExcepton;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存操作DAO；
 * CacheDao作为DB、第三方接口之外的数据操作底层方法；
 * 运行时依赖cache.properties，指定使用的缓存类型；正常运行还需加载具体的缓存配置，如当前缓存指定redis，则默认实现为RedisDao，
 * 需要加载如redis.properties等配置文件；
 * @author KevinYi
 */
//@Component
public class CacheTemplate implements ICacheTemplate {

    private static Logger logger = LoggerFactory.getLogger(CacheTemplate.class);

    //@Autowired(required = false)
    //private SessionCache sessionCache = null;

    /**
     * 默认缓存实例，一般用于读操作
     */
    private static ICacheTemplate defaultCacheDao;

    /**
     * 主缓存实例
     */
    private static List<ICacheTemplate> masterCacheDaos;

    /**
     * 备份缓存实例
     */

    private static List<ICacheTemplate> backupCacheDaos;
    /**
     * 缓存校验结果，true--校验通过，缓存可用；false--校验失败，配置不完整，不能使用；默认可用
     */
    private static boolean cacheValid = true;

    /**
     * 是否启用了主缓存，true--启用，false--未启用；当cacheStrategy为1或3时为true
     */
    private static boolean masterCacheEnable = false;

    /**
     * 是否启用了备用缓存，true--启用，false--未启用；当cacheStrategy为2或3时为true
     */
    private static boolean backupCacheEnable = false;

    public CacheTemplate(List<ICacheTemplate> cacheTemplates) {
        masterCacheDaos = new ArrayList<ICacheTemplate>();
        backupCacheDaos = new ArrayList<ICacheTemplate>();
        if (cacheTemplates != null) {
            for (ICacheTemplate cacheTemplate:
                    cacheTemplates) {
                if (CollectionUtils.isEmpty(masterCacheDaos)) {
                    masterCacheDaos.add(cacheTemplate);
                }else {
                    backupCacheDaos.add(cacheTemplate);
                }

            }
        }

        if (!CollectionUtils.isEmpty(masterCacheDaos)) {
            masterCacheEnable = true;
            cacheValid = true;
            defaultCacheDao = masterCacheDaos.get(0);
        }

        if (!CollectionUtils.isEmpty(backupCacheDaos)) {
            backupCacheEnable = true;
            if (defaultCacheDao == null) {
                defaultCacheDao = backupCacheDaos.get(0);
            }
        }

        cacheValid = cacheValid && (defaultCacheDao != null);

        // 配置校验
        if (!cacheValid) {
            // 如果配置了使用主缓存，则主缓存需要能实例化；如果配置了使用备份缓存，则备份缓存需要能实例化；同时使用主备份的话，备份缓存可以不存在
            logger.error("CacheTemplate_: can't load cache config!");
        }
    }

    @Override
    public int set(String key, Object value) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|set|key=" + key;
        long begin = System.currentTimeMillis();
        try {
            if (masterCacheEnable) {
                // 主缓存操作，失败则抛出异常，操作终止；暂不支持主缓存多实例的事务操作，数据一致性问题还需要更好的解决方案
                for (ICacheTemplate masterCacheDao : masterCacheDaos) {
                    masterCacheDao.set(key, value);
                }
            }

            if (backupCacheEnable) {
                // 备份缓存操作，失败仅记录日志
                int count = 0;
                for (ICacheTemplate backupCacheDao : backupCacheDaos) {
                    try {
                        backupCacheDao.set(key, value);
                    } catch (Exception e) {
                        // 记录第几个失败了，（从第0个开始）
                        logger.error(logPrefix + "|backup=" + count + "|errMsg=set error.", e);
                    }
                    count++;
                }
            }

            long end = System.currentTimeMillis();
            String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", value, logInfo);
            }

            if (end - begin >= 500) {
                logger.info(logInfo);
            }
        } catch (Exception e) {
            String logInfo = logPrefix + "|result=0|timeCost="
                    + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", value, logInfo);
            }
            logger.error(logInfo, e);
            return CacheConstants.FAIL;
        }

        return CacheConstants.SUCCESS;
    }

    @Override
    public int set(String key, Object value, int duration) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|set|duration=" + duration + "|key=" + key;
        long begin = System.currentTimeMillis();
        try {
            if (masterCacheEnable) {
                for (ICacheTemplate masterCacheDao : masterCacheDaos) {
                    masterCacheDao.set(key, value, duration);
                }
            }

            if (backupCacheEnable) {
                int count = 0;
                for (ICacheTemplate backupCacheDao : backupCacheDaos) {
                    try {
                        backupCacheDao.set(key, value, duration);
                    } catch (Exception e) {
                        // 记录第几个失败了，（从第0个开始）
                        logger.error(logPrefix + "|backup=" + count + "|errMsg=set error.", e);
                    }
                    count++;
                }
            }

            long end = System.currentTimeMillis();
            String logInfo = logPrefix + "|result=1|timeCost=" + (begin - begin);
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", value, logInfo);
            }
            if (end - begin >= 500) {
                logger.info(logInfo);
            }
        } catch (Exception e) {
            String logInfo = logPrefix + "|result=0|timeCost="
                    + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", value, logInfo);
            }
            logger.error(logInfo, e);
            return CacheConstants.FAIL;
        }

        return CacheConstants.SUCCESS;
    }

    @Override
    public <T> int mset(Map<String, T> valueMap) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|mset";
        long begin = System.currentTimeMillis();
        try {
            if (masterCacheEnable) {
                for (ICacheTemplate masterCacheDao : masterCacheDaos) {
                    masterCacheDao.mset(valueMap);
                }
            }

            if (backupCacheEnable) {
                int count = 0;
                for (ICacheTemplate backupCacheDao : backupCacheDaos) {
                    try {
                        backupCacheDao.mset(valueMap);
                    } catch (Exception e) {
                        // 记录第几个失败了，（从第0个开始）
                        logger.error(logPrefix + "|backup=" + count + "|errMsg=set error.", e);
                    }
                    count++;
                }
            }
            long end = System.currentTimeMillis();
            String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", valueMap, logInfo);
            }
            if (end - begin >= 500) {
                logger.info(logInfo);
            }
        } catch (Exception e) {
            String logInfo = logPrefix + "|result=0|timeCost="
                    + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", null, logInfo);
            }
            logger.error(logInfo, e);
            return CacheConstants.FAIL;
        }

        return CacheConstants.SUCCESS;
    }

    @Override
    public <T> int mset(Map<String, T> valueMap, int duration) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|mset|duration=" + duration;
        long begin = System.currentTimeMillis();
        try {
            if (masterCacheEnable) {
                for (ICacheTemplate masterCacheDao : masterCacheDaos) {
                    masterCacheDao.mset(valueMap, duration);
                }
            }

            if (backupCacheEnable) {
                int count = 0;
                for (ICacheTemplate backupCacheDao : backupCacheDaos) {
                    try {
                        backupCacheDao.mset(valueMap, duration);
                    } catch (Exception e) {
                        // 记录第几个失败了，（从第0个开始）
                        logger.error(logPrefix + "|backup=" + count + "|errMsg=set error.", e);
                    }
                    count++;
                }
            }

            long end = System.currentTimeMillis();
            String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", valueMap, logInfo);
            }
            if (end - begin >= 500) {
                logger.info(logInfo);
            }
        } catch (Exception e) {
            String logInfo = logPrefix + "|result=0|timeCost="
                    + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", null, logInfo);
            }
            logger.error(logInfo, e);
            return CacheConstants.FAIL;
        }

        return CacheConstants.SUCCESS;
    }

    @Override
    public <T> T get(String key, Class<T> c) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|get|key=" + key;
        long begin = System.currentTimeMillis();
        T result = defaultCacheDao.get(key, c);

        long end = System.currentTimeMillis();
        String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
        if (null != SessionCache.getSession()) {
            SessionCache.getSession().setResponse("cache://", result, logInfo);
        }
        if (end - begin >= 500) {
            logger.info(logInfo);
        }

        return result;
    }

    @Override
    public <T> Map<String, T> mget(List<String> keys, Class<T> c) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|mget";
        long begin = System.currentTimeMillis();
        Map<String, T> result = defaultCacheDao.mget(keys, c);

        long end = System.currentTimeMillis();
        String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
        if (null != SessionCache.getSession()) {
            SessionCache.getSession().setResponse("cache://", result, logInfo);
        }
        if (end - begin >= 500) {
            logger.info("CacheDao|mget|key=" + StringUtils.join(keys, ",") + "|timeCost=" + (end - begin));
        }

        return result;
    }

    @Override
    public <T> T get(String key, LetvTypeReference<T> typeReference) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|get|key=" + key;
        long begin = System.currentTimeMillis();
        T result = defaultCacheDao.get(key, typeReference);

        long end = System.currentTimeMillis();
        String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
        if (null != SessionCache.getSession()) {
            SessionCache.getSession().setResponse("cache://", result, logInfo);
        }
        if (end - begin >= 500) {
            logger.info(logInfo);
        }

        return result;
    }

    @Override
    public <T> Map<String, T> mget(List<String> keys, LetvTypeReference<T> typeReference) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|mget";
        long begin = System.currentTimeMillis();
        Map<String, T> result = defaultCacheDao.mget(keys, typeReference);

        long end = System.currentTimeMillis();
        String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
        if (null != SessionCache.getSession()) {
            SessionCache.getSession().setResponse("cache://", result, logInfo);
        }
        if (end - begin >= 500) {
            logger.info("CacheDao|mget|key=" + StringUtils.join(keys, ",") + "|timeCost=" + (end - begin));
        }

        return result;
    }

    @Override
    public int delete(String key) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|delete|key=" + key;
        long begin = System.currentTimeMillis();
        try {
            if (masterCacheEnable) {
                for (ICacheTemplate masterCacheDao : masterCacheDaos) {
                    masterCacheDao.delete(key);
                }
            }

            if (backupCacheEnable) {
                // 备份缓存操作，失败仅记录日志
                int count = 0;
                for (ICacheTemplate backupCacheDao : backupCacheDaos) {
                    try {
                        backupCacheDao.delete(key);
                    } catch (Exception e) {
                        // 记录第几个失败了，（从第0个开始）
                        logger.error(logPrefix + "|backup=" + count + "|errMsg=delete error.", e);
                    }
                    count++;
                }
            }

            long end = System.currentTimeMillis();
            String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", null, logInfo);
            }
            if (end - begin >= 500) {
                logger.info(logInfo);
            }
        } catch (Exception e) {
            String logInfo = logPrefix + "|result=0|timeCost="
                    + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", null, logInfo);
            }
            logger.error(logInfo, e);
            return CacheConstants.FAIL;
        }

        return CacheConstants.SUCCESS;
    }

    public int sismember(String key, String member) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|sismember|key=" + key;
        long begin = System.currentTimeMillis();
        int result = defaultCacheDao.sismember(key, member);

        long end = System.currentTimeMillis();
        String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
        if (null != SessionCache.getSession()) {
            SessionCache.getSession().setResponse("cache://", "{\"" + member + ":\""  + result + "}", logInfo);
        }
        if (end - begin >= 500) {
            logger.info(logInfo);
        }
        return result;
    }

    @Override
    public int sadd(String key, Set<String> member) {
        if (!cacheValid) {
            throw new LetvCacheExcepton(CacheConstants.NO_CACHE_AVAILABLE_EXCEPTION_MSG);
        }
        String logPrefix = "CacheDao|sadd|key=" + key;
        long begin = System.currentTimeMillis();
        try {
            if (masterCacheEnable) {
                for (ICacheTemplate masterCacheDao : masterCacheDaos) {
                    masterCacheDao.sadd(key, member);
                }
            }
            if (backupCacheEnable) {
                int count = 0;
                for (ICacheTemplate backupCacheDao : backupCacheDaos) {
                    try {
                        backupCacheDao.sadd(key, member);
                    } catch (Exception e) {
                        // 记录第几个失败了，（从第0个开始）
                        logger.error(logPrefix + "|backup=" + count + "|errMsg=set error.", e);
                    }
                    count++;
                }
            }

            long end = System.currentTimeMillis();
            String logInfo = logPrefix + "|result=1|timeCost=" + (end - begin);
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", member, logInfo);
            }
            if (end - begin >= 500) {
                logger.info(logInfo);
            }
        } catch (Exception e) {
            String logInfo = logPrefix + "|result=0|timeCost="
                    + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("cache://", null, logInfo);
            }
            logger.error(logInfo, e);
            return CacheConstants.FAIL;
        }

        return CacheConstants.SUCCESS;
    }

}

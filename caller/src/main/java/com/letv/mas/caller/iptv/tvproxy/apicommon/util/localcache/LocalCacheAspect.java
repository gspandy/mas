package com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache;

import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 该类为LocalCache配合LocalCacheAnnotation，使LocalCache支持AOP
 * 使用AspectJ
 * @author guoyunfeng
 */
@Aspect
@Component
public class LocalCacheAspect {
    private static final Logger logger = Logger.getLogger(LocalCacheAspect.class);

    @Autowired
    LocalCacheManager localCacheManager;

    @Around("@annotation(com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache.LocalCacheAnnotation)")
    public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable {
        String cacheBucketName = this.getCacheName(pjp);
        LocalCacheInterface<Object, Object> localCache = localCacheManager.getCache(cacheBucketName);
        String cacheKey = this.generateCacheKey(cacheBucketName, pjp);
        long expireTimeInMillis = this.getExpireInMillis(pjp);
        Object result = null;
        try {
            // 先从LocalCache中拿
            result = localCache.get(cacheKey);
        } catch (NoSuchKeyException | CacheExpiredException e) {
            // 如果缓存没有该键或者缓存过期，则从数据库里取一遍
            result = pjp.proceed();
            localCache.set(cacheKey, result, expireTimeInMillis);
        }
        return result;
    }

    /**
     * 根据被标记的方法，生成缓存键名，规则为"缓存名_入参A值_入参B值..._入参N值"
     * @param joinPoint
     * @return
     */
    private String generateCacheKey(String cacheName, JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        sb.append(cacheName);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            sb.append("_");
            sb.append(arg.toString());
        }
        return sb.toString();
    }

    /**
     * 根据被标记的方法，生成LocalCache名称，如果LocalCacheAnnotation中指定name，则取该name；如果没给，
     * 则取被标记的方法名
     * @param joinPoint
     * @return
     */
    private String getCacheName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LocalCacheAnnotation localCacheAnnotation = method.getAnnotation(LocalCacheAnnotation.class);
        if (StringUtil.isNotBlank(localCacheAnnotation.name())) {
            return localCacheAnnotation.name();
        } else {
            return method.getName();
        }
    }

    /**
     * 获取缓存过期时间，如果LocalCacheAnnotation中指定expireInMillis，则取该值；
     * 如果没给，则取LocalCacheInterface.DEFAULT_EXPIRE_TIME
     * @param joinPoint
     * @return
     */
    private long getExpireInMillis(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LocalCacheAnnotation localCacheAnnotation = method.getAnnotation(LocalCacheAnnotation.class);
        if (localCacheAnnotation.expireInMillis() > 0) {
            return localCacheAnnotation.expireInMillis();
        } else {
            return LocalCacheInterface.DEFAULT_EXPIRE_TIME;
        }
    }
}

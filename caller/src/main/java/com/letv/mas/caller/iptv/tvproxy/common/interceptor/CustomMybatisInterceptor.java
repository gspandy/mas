package com.letv.mas.caller.iptv.tvproxy.common.interceptor;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLTimeoutException;
import java.text.DateFormat;
import java.util.*;

/**
 * 自定义Mybatis插件，打印sql执行时间
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }) })
public class CustomMybatisInterceptor implements Interceptor {

    private final static Logger log = LoggerFactory.getLogger(CustomMybatisInterceptor.class);

    /*@Autowired(required = false)
    private SessionCache sessionCache = null;*/

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();
        long begin = System.currentTimeMillis();
        String sql = "";
        System.out.print("------------------------------");
        if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
            invocation.getArgs()[0] = this.rebuildMappedStatement(mappedStatement);
            sql = this.getSql(invocation);
        } else {
            sql = sqlId;
        }
        System.out.print("++++++++++++"+sql);
        String logPrefix = "CustomMybatisInterceptor|intercept|sql=" + sql;
        Object returnValue = null;
        try {
            returnValue = invocation.proceed();
        } catch (Exception e) {
            if (e instanceof SQLTimeoutException) {
                log.error("[sql-err-timeout]: sql=" + sql, e);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("mysql://", null, logPrefix + "|result=-1");
                }
            } else {
                log.error("[sql-err]: sql=" + sql, e);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("mysql://", null, logPrefix + "|result=0");
                }
            }
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            if (null != SessionCache.getSession() && null != returnValue) {
                SessionCache.getSession().setResponse("mysql://", returnValue, logPrefix + "|result=1|time=" + (end - begin));
            }
            if (end - begin > 200) {
                log.info("[sql-slow]:sql=" + sql + "|time=" + (end - begin) + "ms");
            }
        }
        return returnValue;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static String getSql(Invocation invocation) {
        Map parameter = null;
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        String sql = ms.getId();
        if (null != invocation.getArgs()[1] && invocation.getArgs()[1] instanceof Map) {
            parameter = (Map) invocation.getArgs()[1];
        }
        try {
            BoundSql boundSql = ms.getBoundSql(parameter);
            Configuration configuration = ms.getConfiguration();
            Object parameterObject = boundSql.getParameterObject();
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            sql = boundSql.getSql().replaceAll("[\\s]+", " ");
            if (parameterMappings.size() > 0 && parameterObject != null) {
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    for (ParameterMapping parameterMapping : parameterMappings) {
                        String propertyName = parameterMapping.getProperty();
                        if (metaObject.hasGetter(propertyName)) {
                            Object obj = metaObject.getValue(propertyName);
                            sql = sql.replaceFirst("\\?", getParameterValue(obj));
                        } else if (boundSql.hasAdditionalParameter(propertyName)) {
                            Object obj = boundSql.getAdditionalParameter(propertyName);
                            sql = sql.replaceFirst("\\?", getParameterValue(obj));
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        System.out.println(" sql： "+sql);
        return sql;
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private MappedStatement rebuildMappedStatement(MappedStatement ms) {
        MappedStatement.Builder builder = null;
        MappedStatement ret = ms;
        try {
            builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), ms.getSqlSource(),
                    ms.getSqlCommandType());
            builder.resource(ms.getResource());
            builder.fetchSize(ms.getFetchSize());
            builder.statementType(ms.getStatementType());
            builder.keyGenerator(ms.getKeyGenerator());
            if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
                StringBuilder keyProperties = new StringBuilder();
                for (String keyProperty : ms.getKeyProperties()) {
                    keyProperties.append(keyProperty).append(",");
                }
                keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
                builder.keyProperty(keyProperties.toString());
            }
            builder.timeout(2);
            builder.parameterMap(ms.getParameterMap());
            builder.resultMaps(ms.getResultMaps());
            builder.resultSetType(ms.getResultSetType());
            builder.cache(ms.getCache());
            builder.flushCacheRequired(ms.isFlushCacheRequired());
            builder.useCache(ms.isUseCache());
            ret = builder.build();
        } catch (Exception e) {

        }
        return ret;
    }
}

package com.letv.mas.caller.iptv.tvproxy.common.interceptor;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementExecuteType;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import org.apache.ibatis.mapping.SqlCommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Date;

/**
 * Created by leeco on 18/10/22.
 */
public class DruidLogFilter extends FilterEventAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger("sqlLog");
    private static final String LOG_TAG = "druid";

    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        super.statementExecuteBefore(statement, sql);
    }

    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        long stime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        String splitSymbol = "|";
        super.statementExecuteAfter(statement, sql, result);
        long time = System.currentTimeMillis() - stime;
        if (LOGGER.isInfoEnabled() && null != statement && null != statement.getRawObject()) {
            String sqlStr = statement.getRawObject().toString().replaceAll("[\\s]+", " ").replaceFirst(".*: ", "");
            sb.append(TimeUtil.getISO8601Timestamp(new Date())).append(splitSymbol)
                    .append(LOG_TAG).append(splitSymbol)
                    .append(sqlStr).append(splitSymbol)
                    .append(time).append(splitSymbol)
                    .append(result ? 1 : 0);
            LOGGER.info(sb.toString());
        }
        if (time > 200) {
            LOGGER.info("[sql-slow]:sql=" + sql + "|time=" + time + "ms");
        }
    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        long stime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        String splitSymbol = "|";
        super.statement_executeErrorAfter(statement, sql, error);
        if (LOGGER.isInfoEnabled() && null != statement && null != statement.getRawObject()) {
            String sqlStr = statement.getRawObject().toString().replaceAll("[\\s]+", " ").replaceFirst(".*: ", "");
            sqlStr = sqlStr + error.getMessage();
            sb.append(TimeUtil.getISO8601Timestamp(new Date())).append(splitSymbol)
                    .append(LOG_TAG).append(splitSymbol)
                    .append(sqlStr).append(splitSymbol)
                    .append(System.currentTimeMillis() - stime).append(splitSymbol)
                    .append(-1);
            LOGGER.info(sb.toString());
        }
        String logPrefix = "DruidLogFilter|intercept|sql=" + sql;
        if (error instanceof SQLTimeoutException) {
            LOGGER.error("[sql-err-timeout]: sql=" + sql, error);
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("mysql://", null, logPrefix + "|result=-1");
            }
        } else {
            LOGGER.error("[sql-err]: sql=" + sql, error);
            if (null != SessionCache.getSession()) {
                SessionCache.getSession().setResponse("mysql://", null, logPrefix + "|result=0");
            }
        }
    }

    @Override
    protected void resultSetOpenAfter(ResultSetProxy resultSet) {
        super.resultSetOpenAfter(resultSet);
        if (null == SessionCache.getSession()) {
            return;
        }
        if (resultSet == null) {
            return;
        }
        StatementProxy statement = resultSet.getStatementProxy();
        if (statement == null) {
            return;
        }
        String returnValue = null;
        String sql = statement.getRawObject().toString().replaceAll("[\\s]+", " ").replaceFirst(".*: ", "");
        String logPrefix = "DruidLogFilter|intercept|sql=" + sql;
        long time = resultSet.getSqlStat().getExecuteAndResultSetHoldTimeMilis();
        try {
            ResultSet rs = resultSet.getResultSetRaw();
            returnValue = resultSetToJson(rs);
            SessionCache.getSession().setResponse("mysql://", returnValue, logPrefix + "|result=1|time=" + time);
        }catch (Exception e){

        }
    }

    public String resultSetToJson(ResultSet rs) throws SQLException,JSONException
    {
        // json数组
        JSONArray array = new JSONArray();
        // 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();

            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
            array.add(jsonObj);
        }
        rs.beforeFirst();
        return array.toString();
    }
}

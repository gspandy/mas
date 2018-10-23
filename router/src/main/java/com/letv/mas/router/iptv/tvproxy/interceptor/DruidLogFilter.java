package com.letv.mas.router.iptv.tvproxy.interceptor;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.letv.mas.router.iptv.tvproxy.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by leeco on 18/10/22.
 */
public class DruidLogFilter extends FilterEventAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DruidLogFilter.class);
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
        if (LOGGER.isInfoEnabled() && null != statement && null != statement.getRawObject()) {
            String sqlStr = statement.getRawObject().toString().replaceAll("[\\s]+", " ").replaceFirst(".*: ", "");
            sb.append(TimeUtil.getISO8601Timestamp(new Date())).append(splitSymbol)
                    .append(LOG_TAG).append(splitSymbol)
                    .append(sqlStr).append(splitSymbol)
                    .append(System.currentTimeMillis() - stime).append(splitSymbol)
                    .append(result ? 1 : 0);
            LOGGER.info(sb.toString());
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
    }
}

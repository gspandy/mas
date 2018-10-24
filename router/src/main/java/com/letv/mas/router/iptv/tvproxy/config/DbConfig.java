package com.letv.mas.router.iptv.tvproxy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by leeco on 18/10/19.
 * 如不走 spring-boot-starter yml配置，可以走如下配置类代码bean定制，如灵活管理多个dataSource的情况！
 */
//@Configuration
public class DbConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbConfig.class);

    @Autowired
    DruidProperties druidProperties;


    @Autowired
    MybatisProperties mybatisProperties;

    @Data
    @Primary
    @Configuration
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public static class DruidProperties {
        private String url;
        private String username;
        private String password;
        private String driveClassName;
        private String type;
        private String filters;
        private String minIdle;
        private String maxActive;
        private String initialSize;
        private String maxWait;
        private String timeBetweenEvictionRunsMillis;
        private String minEvictableIdleTimeMillis;
        private String validationQuery;
        private String testWhileIdle;
        private String testOnBorrow;
        private String testOnReturn;
        private String poolPreparedStatements;
        private String maxOpenPreparedStatements;
        private String keepAlive;
        private WebStatFilter webStatFilter;
        private StatViewServlet statViewServlet;

        @Data
        public static class WebStatFilter {
            private String enabled;
            private String urlPattern;
            private String exclusions;
        }

        @Data
        public static class StatViewServlet {
            private String enabled;
            private String urlPattern;
            private String allow;
            private String deny;
            private String loginUsername;
            private String loginPassword;
        }
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "mybatis")
    public static class MybatisProperties {
        private String typeAliasesPackage;
        private String mapperLocations;
    }

    @Bean
    @ConditionalOnBean(DbConfig.DruidProperties.class)
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(druidProperties.getUrl());
        druidDataSource.setUsername(druidProperties.getUsername());
        druidDataSource.setPassword(druidProperties.getPassword());
        druidDataSource.setDriverClassName(StringUtils.isNotBlank(druidProperties.getDriveClassName()) ? druidProperties.getDriveClassName() : "com.mysql.jdbc.Driver");
        druidDataSource.setMaxActive(StringUtils.isNotBlank(druidProperties.getMaxActive()) ? Integer.parseInt(druidProperties.getMaxActive()) : 10);
        druidDataSource.setInitialSize(StringUtils.isNotBlank(druidProperties.getInitialSize()) ? Integer.parseInt(druidProperties.getInitialSize()) : 1);
        druidDataSource.setMaxWait(StringUtils.isNotBlank(druidProperties.getMaxWait()) ? Integer.parseInt(druidProperties.getMaxWait()) : 60000);
        druidDataSource.setMinIdle(StringUtils.isNotBlank(druidProperties.getMinIdle()) ? Integer.parseInt(druidProperties.getMinIdle()) : 3);
        druidDataSource.setTimeBetweenEvictionRunsMillis(StringUtils.isNotBlank(druidProperties.getTimeBetweenEvictionRunsMillis()) ?
                Integer.parseInt(druidProperties.getTimeBetweenEvictionRunsMillis()) : 60000);
        druidDataSource.setMinEvictableIdleTimeMillis(StringUtils.isNotBlank(druidProperties.getMinEvictableIdleTimeMillis()) ?
                Integer.parseInt(druidProperties.getMinEvictableIdleTimeMillis()) : 300000);
        druidDataSource.setValidationQuery(StringUtils.isNotBlank(druidProperties.getValidationQuery()) ? druidProperties.getValidationQuery() : "select 'x'");
        druidDataSource.setTestWhileIdle(StringUtils.isNotBlank(druidProperties.getTestWhileIdle()) ? Boolean.parseBoolean(druidProperties.getTestWhileIdle()) : true);
        druidDataSource.setTestOnBorrow(StringUtils.isNotBlank(druidProperties.getTestOnBorrow()) ? Boolean.parseBoolean(druidProperties.getTestOnBorrow()) : false);
        druidDataSource.setTestOnReturn(StringUtils.isNotBlank(druidProperties.getTestOnReturn()) ? Boolean.parseBoolean(druidProperties.getTestOnReturn()) : false);
        druidDataSource.setPoolPreparedStatements(StringUtils.isNotBlank(druidProperties.getPoolPreparedStatements()) ? Boolean.parseBoolean(druidProperties.getPoolPreparedStatements()) : true);
        druidDataSource.setMaxOpenPreparedStatements(StringUtils.isNotBlank(druidProperties.getMaxOpenPreparedStatements()) ? Integer.parseInt(druidProperties.getMaxOpenPreparedStatements()) : 20);
        druidDataSource.setKeepAlive(Boolean.valueOf(druidProperties.getKeepAlive()));

        try {
            druidDataSource.setFilters(StringUtils.isNotBlank(druidProperties.getFilters()) ? druidProperties.getFilters() : "stat, wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }

    @Bean
    @ConditionalOnBean(DbConfig.DruidProperties.class)
    public ServletRegistrationBean druidServlet() {
        LOGGER.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), druidProperties.getStatViewServlet().getUrlPattern());
        LOGGER.info("admin path: {}", druidProperties.getStatViewServlet().getUrlPattern());
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", druidProperties.getStatViewServlet().getAllow());
        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", druidProperties.getStatViewServlet().getDeny());
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", druidProperties.getStatViewServlet().getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", druidProperties.getStatViewServlet().getLoginPassword());
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    @ConditionalOnBean(DbConfig.DruidProperties.class)
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns(druidProperties.getWebStatFilter().getUrlPattern());
        filterRegistrationBean.addInitParameter("exclusions", druidProperties.getWebStatFilter().getExclusions());
        return filterRegistrationBean;
    }

    @Bean(name = "sqlSessionFactory")
    @ConditionalOnBean(DbConfig.MybatisProperties.class)
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置Mapper.java目录
        if (StringUtils.isNotBlank(mybatisProperties.getTypeAliasesPackage())) {
            sqlSessionFactoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        }
        // 设置Mapper.xml目录
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(mybatisProperties.getMapperLocations()));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

package com.letv.mas.caller.iptv.tvproxy.common.config;

import com.github.pagehelper.Dialect;
import com.github.pagehelper.PageHelper;
import com.letv.mas.caller.iptv.tvproxy.common.interceptor.CustomMybatisInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
// 开启注解事务支持
public class MybatisConfig implements TransactionManagementConfigurer {

	private static final Logger logger = Logger.getLogger(MybatisConfig.class);
	// spring容器管理，可以直接注入使用
	@Autowired
	DataSource dataSource;

	@Bean
	public DataSourceConfig getDataSourceConfig(){
		logger.info("DataSourceConfig init ....");
		return new DataSourceConfig();
	}


	@Bean(name = "dataSource")
	@Primary
	public DataSource getDataSource(DataSourceConfig dataSourceConfig){
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(dataSourceConfig.getDriverClassName()); // 驱动器
			dataSource.setJdbcUrl(dataSourceConfig.getUrl()); // 数据库url
			dataSource.setUser(dataSourceConfig.getUsername()); // 用户名
			dataSource.setPassword(dataSourceConfig.getPassword()); // 密码

			dataSource.setCheckoutTimeout(dataSourceConfig.getCheckoutTimeout());
			dataSource.setInitialPoolSize(dataSourceConfig.getInitialPoolSize()); // 初始化连接池大小
			dataSource.setMinPoolSize(dataSourceConfig.getMinimumConnectionCount()); // 最少连接数
			dataSource.setMaxPoolSize(dataSourceConfig.getMaximumConnectionCount()); // 最大连接数
			dataSource.setAcquireIncrement(dataSourceConfig.getAcquireIncrement()); // 连接数的增量
			dataSource.setMaxIdleTime(dataSourceConfig.getMaxIdleTime());
			dataSource.setMaxStatements(dataSourceConfig.getMaxStatements());
			dataSource.setBreakAfterAcquireFailure(dataSourceConfig.isBreakAfterAcquireFailure());
			dataSource.setIdleConnectionTestPeriod(dataSourceConfig.getIdleConnectionTestPeriod()); // 测连接有效的时间间隔
			dataSource.setTestConnectionOnCheckout(dataSourceConfig.isTestConnectionOnCheckout()); // 每次连接验证连接是否可用
			dataSource.setTestConnectionOnCheckin(dataSourceConfig.isTestConnectionOnCheckin());
			dataSource.setAcquireRetryAttempts(dataSourceConfig.getAcquireRetryAttempts());
			dataSource.setAcquireRetryDelay(dataSourceConfig.getAcquireRetryDelay());

		}catch (Exception e){
			logger.error("get DataSource error ... ",e);
		}finally {

		}

		return dataSource;
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setTypeAliasesPackage("com.letv.mas.caller.iptv.tvproxy.common.dao.sql.pojo");

		// 分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect",Dialect.mysql.name());
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("offsetAsPageNum", "true");
		//properties.setProperty("returnPageInfo", "check");
		properties.setProperty("pageSizeZero", "true");
		properties.setProperty("params", "count=count");
		pageHelper.setProperties(properties);

		CustomMybatisInterceptor interceptor = new CustomMybatisInterceptor();

		// 添加插件
		bean.setPlugins(new Interceptor[] { interceptor,pageHelper});

		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setMapperLocations(resolver
					.getResources("classpath:mybatis/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(
			SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	// 开启注解事务
	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}

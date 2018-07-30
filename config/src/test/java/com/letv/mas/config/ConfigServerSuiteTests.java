package com.letv.mas.config;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 套件测试
 *
 * Created by David.Liu on 2018/6/26.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ConfigServerApplicationTests.class,ConfigServerApplicationAuthTests.class, ConfigServerApplicationExceptionTests.class})
public class ConfigServerSuiteTests {

}

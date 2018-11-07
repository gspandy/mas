package com.letv.mas.caller.iptv.tvproxy.common.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChannelDataMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.service.TestService;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("${server.path}/test")
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private TestService testService;

    @RequestMapping("/testsql")
    public PageInfo<ChannelDataMysqlTable> testSql(@RequestParam(value = "chId", required = false, defaultValue = "1") Integer chId,
                                                   @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize
    ) {
        return testService.test(chId,pageNum,pageSize);
    }

    @ApolloConfig("config.properties")
    private static Config config;

    @ApolloConfig("httpurl.properties")
    private static Config httpurl;


    @RequestMapping("/testapollo")
    public String testApollo(@RequestParam(value = "name") String name) {
        ConfigFile configFile = ConfigService.getConfigFile(name, ConfigFileFormat.Properties);
        return configFile.getContent();
    }

    @Autowired
    StringEncryptor stringEncryptor;

    @RequestMapping("/testen")
    public String testen(@RequestParam(value = "name") String name) {
        String result = stringEncryptor.encrypt(name);
        return result;
    }

    @RequestMapping("/testde")
    public String testde(@RequestParam(value = "name") String name) {
        String result = stringEncryptor.decrypt(name);
        return result;
    }

}


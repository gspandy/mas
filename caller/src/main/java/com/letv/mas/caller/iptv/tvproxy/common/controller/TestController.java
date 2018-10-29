package com.letv.mas.caller.iptv.tvproxy.common.controller;

import com.github.pagehelper.PageInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.pojo.ChannelDataMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${server.path}/test")
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

}


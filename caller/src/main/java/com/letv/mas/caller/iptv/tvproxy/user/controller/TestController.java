package com.letv.mas.caller.iptv.tvproxy.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestController extends BaseController {
    @RequestMapping("/test2")
    public int test2() {
        return 1;
    }
}


package com.letv.mas.caller.iptv.tvproxy.user.controller;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.service.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Iptv
public class BaseController {

    @Autowired
    protected FacadeService facadeService;

}

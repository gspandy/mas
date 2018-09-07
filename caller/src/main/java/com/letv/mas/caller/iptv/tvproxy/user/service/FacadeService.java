package com.letv.mas.caller.iptv.tvproxy.user.service;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service门面类
 */
@Service
@Iptv
public class FacadeService {

    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return this.userService;
    }

}

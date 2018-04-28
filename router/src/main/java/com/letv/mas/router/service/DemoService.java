package com.letv.mas.router.service;

import com.letv.mas.router.data.dto.RouterConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by leeco on 18/4/28.
 */
@Service
public class DemoService {

    @Autowired
    RouterConfigDto routerConfigDto;

    public String getConfig() {
        return routerConfigDto.toString();
    }
}

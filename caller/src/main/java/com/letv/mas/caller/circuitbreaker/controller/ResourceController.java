package com.letv.mas.caller.circuitbreaker.controller;

import com.letv.mas.caller.circuitbreaker.model.ResourceInfo;
import com.letv.mas.caller.circuitbreaker.service.CircuitbreakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/circuitbreaker")
public class ResourceController {

    @Autowired
    private CircuitbreakerService circuitbreakerService;

    @RequestMapping(value = "/resource")
    public ResourceInfo getResource(@RequestParam(value = "delay_ms", defaultValue = "0") long delayMs) {
        return this.circuitbreakerService.getInfoFromRemoteAPI(delayMs);
    }
}

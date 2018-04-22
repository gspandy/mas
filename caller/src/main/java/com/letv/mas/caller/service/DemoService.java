package com.letv.mas.caller.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by leeco on 18/4/19.
 */
@Service
public class DemoService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "sayError")
    public String sayHello(String name) {
        return restTemplate.getForObject("http://SERVICE-PROVIDER/hi?name=" + name, String.class);
    }

    public String sayError(String name) {
        return "hi," + name + ",i am sorry, something was wrong!";
    }
}

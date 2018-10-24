package com.letv.mas.client.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientUtilDemo {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testRest(){
        Map map = new HashMap();
        map.put("Content-Type","text/plain; charset=utf-8");
        map.forEach((o1,o2) -> System.out.println(o1+"==="+o2));
        ResponseEntity<String> result = restTemplate.exchange("http://127.0.0.1:8901/acl/allAcls?jsoncallback=callback&pageNum=0&pageSize=10&_=1540260485572", HttpMethod.POST, null, String.class);
        String s = post("http://127.0.0.1:8901/acl/allAcls?jsoncallback=callback&pageNum=0&pageSize=10&_=1540260485572","",String.class,map);
        ResponseEntity<String> entity = restTemplate.getForEntity("http://127.0.0.1:8901/acl/allAcls?jsoncallback=callback&pageNum=0&pageSize=10&_=1540260485572", String.class);
        System.out.println(entity);
        System.err.println(s);
        System.out.println(result);
    }

    /**
     * get
     * @param url 请求地址
     * @param param  参数
     * @param returnClass 返回类型
     * @return
     */
    public <T> T get(String url, Class<T> returnClass, Map<String, ?> param) {
        return restTemplate.getForObject(url, returnClass, param);
    }


    /**
     * post
     * @param url 请求地址
     * @param param 参数
     * @param returnClass 返回类型
     * @param header 自定义的头信息
     * @return
     */
    public <E> E post(String url, E param, Class<E> returnClass, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        header.forEach((o1, o2) -> headers.set(o1, o2));
        HttpEntity<E> httpEntity = new HttpEntity<E>(param,headers);
        return restTemplate.postForObject(url, httpEntity, returnClass);
    }


    /**
     * post
     * @param url 请求地址
     * @param param 参数
     * @param returnClass 返回类型
     * @return
     */
    public <E> E postByDefault(String url, E param, Class<E> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        HttpEntity<E> httpEntity = new HttpEntity<E>(param,headers);
        return restTemplate.postForObject(url, httpEntity, returnClass);
    }

}

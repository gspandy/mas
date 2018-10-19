package com.letv.mas.client.demo.controller;

import com.google.gson.Gson;
import com.letv.mas.client.demo.model.dao.DemoMapper;
import com.letv.mas.client.demo.model.dto.AclDto;
import com.letv.mas.client.demo.model.dto.UserDto;

import com.letv.mas.client.demo.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;


/**
 * Created by leeco on 18/4/19.
 */
@RestController
public class DemoController {

    @Value("${eureka.instance.metadata-map.zone}")
    private String zone;

    @Value("${server.port}")
    String port;

    @Resource
    private DemoMapper demoMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @RequestMapping("/hi")
    public String hi(@RequestParam String name) {
        return "hi " + name + ",i am from " + zone + ":" + port;
    }

    private static final String INDEX = "https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";

    /**
     * 页面跳转
     */
    @RequestMapping("/isLogin")
    public void isLogin(@RequestParam String jsoncallback,HttpServletResponse response){
        callBackJson(jsoncallback,INDEX,response);
    }

    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public void logout(@RequestParam String jsoncallback,HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        if(cookies.length != 0){
            for (Cookie cookie:cookies){
                if("loginUser".equals(cookie.getName())){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    redisUtil().lPOP(cookie.getValue(), redisUtil().lGet(cookie.getValue(), 0, -1).size());
                    callBackJson(jsoncallback,"OK",response);
                }
            }
        }
    }

    /**
     * 检查是否登录
     */
    @RequestMapping("/checkLogin")
    public void checkLogin(@RequestParam String jsoncallback, HttpServletRequest request,HttpServletResponse response){
        String loginUser = (String) request.getAttribute("loginUser");
        if(StringUtils.isNotBlank(loginUser)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length != 0){
                for (Cookie cookie: cookies){
                    if("loginUser".equals(cookie.getName())){
                        callBackJson(jsoncallback,cookie.getValue(),response);
                    }
                }
            }
            Cookie cookie = new Cookie("loginUser", loginUser);
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            callBackJson(jsoncallback,loginUser,response);
        }
    }

    /**
     * 根据登录用户查询权限
     */
    @RequestMapping("/loadPageByAcl")
    public void loadPageByAcl(@RequestParam String jsoncallback,HttpServletRequest request,HttpServletResponse response){
        String loginUser = (String) request.getAttribute("loginUser");
        UserDto userByMail = demoMapper.findUserByMail(loginUser);
        if(userByMail==null){
            demoMapper.insertUser(loginUser);
            userByMail = demoMapper.findUserByMail(loginUser);
        }
        List allAcl = redisUtil().lGet("AllAcl", 0, -1);
        if(allAcl != null && allAcl.size() == 0){
            allAcl = demoMapper.findAllAcls();
            redisUtil().lSet("AllAcl",allAcl);
            redisUtil().expire("AllAcl", 86400);
        }
        List showAcl = redisUtil().lGet(userByMail.getMail(),0,-1);
        if(showAcl != null && showAcl.size() == 0 && allAcl != null && allAcl.size() != 0){
            String[] acls = userByMail.getCode().split(",");
            for (int i = 0; i < acls.length; i++) {
                for (Object acl : allAcl) {
                    if (((AclDto)acl).getId().equals(acls[i])) {
                        showAcl.add(acl);
                        break;
                    }
                }
            }
            redisUtil().lSet(userByMail.getMail(),showAcl);
            redisUtil().expire(userByMail.getMail(),1800);
        }
        showAcl.add(userByMail.getType());
        callBackJson(jsoncallback,showAcl,response);
    }

    private void callBackJson(@RequestParam String jsoncallback,Object s, HttpServletResponse response) {
        PrintWriter out = null;
        Gson gson = new Gson();
        try {
            out = response.getWriter();
            out.print(jsoncallback+"(" + gson.toJson(s) + ")");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            out.flush();
            out.close();
        }
    }

    private RedisUtil redisUtil(){
        RedisUtil redisUtil = new RedisUtil();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }
}

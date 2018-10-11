package com.letv.mas.client.demo.controller;

import com.google.gson.Gson;
import com.letv.mas.client.demo.mapper.DemoMapper;
import com.letv.mas.client.demo.model.dto.AclDto;
import com.letv.mas.client.demo.model.dto.UserDto;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @RequestMapping("/hi")
    public String hi(@RequestParam String name) {
        return "hi " + name + ",i am from " + zone + ":" + port;
    }

    private static final String INDEX = "https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";

    /**
     * 页面跳转
     * @param jsoncallback
     * @param request
     * @param response
     */
    @RequestMapping("/isLogin")
    public void isLogin(@RequestParam String jsoncallback,HttpServletRequest request,HttpServletResponse response){
        PrintWriter out = null;
        Gson gson = new Gson();
        try {
            out = response.getWriter();
            out.print(jsoncallback+"(" + gson.toJson(INDEX) + ")");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            out.flush();
            out.close();
        }
    }

    @Resource
    private DemoMapper demoMapper;

    /**
     * 退出登录
     * @param jsoncallback
     * @param request
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(@RequestParam String jsoncallback,HttpServletRequest request,HttpServletResponse response){
        PrintWriter out = null;
        Gson gson = new Gson();
        Cookie[] cookies = request.getCookies();
        if(cookies.length != 0){
            for (Cookie cookie:cookies){
                if("loginUser".equals(cookie.getName())){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    try {
                        response.setContentType("text/json;charset=UTF-8");
                        request.setCharacterEncoding("UTF-8");

                        out = response.getWriter();
                        out.print(jsoncallback+"(" + gson.toJson("OK") + ")");
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        out.flush();
                        out.close();
                    }
                }
            }
        }
    }

    /**
     * 检查是否登录
     * @param jsoncallback
     * @param request
     * @param response
     */
    @RequestMapping("/checkLogin")
    public void checkLogin(@RequestParam String jsoncallback,
                      HttpServletRequest request,HttpServletResponse response){
        PrintWriter out = null;
        Gson gson = new Gson();
        String loginUser = (String) request.getAttribute("loginUser");
        if(StringUtils.isNotBlank(loginUser)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length != 0){
                for (Cookie cookie: cookies){
                    if("loginUser".equals(cookie.getName())){
                        try {
                            out = response.getWriter();
                            out.print(jsoncallback+"(" + gson.toJson(cookie.getValue()) + ")");
                        }catch (IOException e){
                            e.printStackTrace();
                        } finally{
                            out.flush();
                            out.close();
                        }
                    }
                }
            }
            try {
                Cookie cookie = new Cookie("loginUser", loginUser);
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
                out = response.getWriter();
                out.print(jsoncallback+"(" + gson.toJson(loginUser) + ")");
            }catch (IOException e){
                e.printStackTrace();
            } finally{
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 根据登录用户查询权限
     * @param jsoncallback
     * @param request
     * @param response
     */
    @RequestMapping("/loadPageByAcl")
    public void loadPageByAcl(@RequestParam String jsoncallback,
                           HttpServletRequest request,HttpServletResponse response){
        PrintWriter out = null;
        Gson gson = new Gson();
        String loginUser = (String) request.getAttribute("loginUser");
        UserDto userByMail = demoMapper.findUserByMail(loginUser);
        if(userByMail==null){
            demoMapper.insertUser(loginUser);
            userByMail = demoMapper.findUserByMail(loginUser);
        }
        List<AclDto> showAcl = new ArrayList<>();
        if(StringUtils.isNotBlank(userByMail.getCode())) {
            List<AclDto> allAcl = demoMapper.findAllAcls();
            if (allAcl != null && allAcl.size() != 0) {
                String[] acls = userByMail.getCode().split(",");
                for (int i = 0; i < acls.length; i++) {
                    for (AclDto acl : allAcl) {
                        if (acl.getId().equals(acls[i])) {
                            showAcl.add(acl);
                            break;
                        }
                    }
                }
            }
        }
        try {
            out = response.getWriter();
            out.print(jsoncallback + "(" + gson.toJson(showAcl) + ")");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
}

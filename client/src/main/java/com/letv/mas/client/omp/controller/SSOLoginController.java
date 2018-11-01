package com.letv.mas.client.omp.controller;

import com.letv.mas.client.omp.service.SSOLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by leeco on 18/4/19.
 */
@RestController
public class SSOLoginController {

    @Autowired
    private SSOLoginService ssoLoginService;

    private static final String INDEX = "https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";

    /**
     * 页面跳转
     * @param jsoncallback 页面请求标识
     */
    @RequestMapping("/isLogin")
    public String isLogin(@RequestParam String jsoncallback){
        return ssoLoginService.isLogin(jsoncallback,INDEX);
    }

    /**
     * 退出登录
     * @param jsoncallback
     * @param user
     */
    @RequestMapping("/logout")
    public String logout(@RequestParam String jsoncallback,@RequestParam String user){
        return ssoLoginService.logout(jsoncallback,user);
    }

    /**
     * 检查是否登录并查询权限
     * @param jsoncallback
     * @param request
     * @param response
     */
    @RequestMapping("/checkLogin")
    public String checkLogin(@RequestParam String jsoncallback,String user, HttpServletRequest request,HttpServletResponse response){
        return ssoLoginService.checkLogin(jsoncallback,user,request,response);
    }

}

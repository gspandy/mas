package com.letv.mas.client.omp.controller;

import com.letv.mas.client.omp.model.dao.SSOLoginMapper;
import com.letv.mas.client.omp.model.dto.AclDto;
import com.letv.mas.client.omp.model.dto.UserDto;
import com.letv.mas.client.omp.service.UnifiedCallbackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Created by leeco on 18/4/19.
 */
@RestController
public class SSOLoginController {

    @Autowired
    private UnifiedCallbackService callbackService;

    @Resource
    private SSOLoginMapper ssoLoginMapper;

    private static final String INDEX = "https://sso.lecommons.com/login.php?site=workbench&backurl=http://omp.mas.letv.cn/index.html";

    /**
     * 页面跳转
     * @param jsoncallback 页面请求标识
     * @param response
     */
    @RequestMapping("/isLogin")
    public void isLogin(@RequestParam String jsoncallback,HttpServletResponse response){
        callbackService.callBackJson(jsoncallback,INDEX,response);
    }

    /**
     * 退出登录
     * @param jsoncallback
     * @param request
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(@RequestParam String jsoncallback,HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        if(cookies.length != 0){
            for (Cookie cookie:cookies){
                if("loginUser".equals(cookie.getName())){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    callbackService.redisUtil().lPOP(cookie.getValue(), callbackService.redisUtil().lGet(cookie.getValue(), 0, -1).size());
                    callbackService.callBackJson(jsoncallback,"OK",response);
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
    public void checkLogin(@RequestParam String jsoncallback, HttpServletRequest request,HttpServletResponse response){
        String loginUser = (String) request.getAttribute("loginUser");
        if(StringUtils.isNotBlank(loginUser)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length != 0){
                for (Cookie cookie: cookies){
                    if("loginUser".equals(cookie.getName())){
                        callbackService.callBackJson(jsoncallback,cookie.getValue(),response);
                    }
                }
            }
            Cookie cookie = new Cookie("loginUser", loginUser);
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            callbackService.callBackJson(jsoncallback,loginUser,response);
        }
    }

    /**
     * 根据登录用户查询权限
     * @param jsoncallback
     * @param request
     * @param response
     */
    @RequestMapping("/loadPageByAcl")
    public void loadPageByAcl(@RequestParam String jsoncallback,HttpServletRequest request,HttpServletResponse response){
        String loginUser = (String) request.getAttribute("loginUser");
        UserDto userByMail = ssoLoginMapper.findUserByMail(loginUser);
        if(userByMail==null){
            ssoLoginMapper.insertUser(loginUser);
            userByMail = ssoLoginMapper.findUserByMail(loginUser);
        }
        List allAcl = callbackService.redisUtil().lGet("AllAcl", 0, -1);
        if(allAcl != null && allAcl.size() == 0){
            allAcl = ssoLoginMapper.findAllAcls();
            callbackService.redisUtil().lSet("AllAcl",allAcl);
            callbackService.redisUtil().expire("AllAcl", 86400);
        }
        List showAcl = callbackService.redisUtil().lGet(userByMail.getMail(),0,-1);
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
            callbackService.redisUtil().lSet(userByMail.getMail(),showAcl);
            callbackService.redisUtil().expire(userByMail.getMail(),1800);
        }
        showAcl.add(userByMail.getType());
        callbackService.callBackJson(jsoncallback,showAcl,response);
    }

}

package com.letv.mas.client.omp.service;

import com.google.gson.Gson;
import com.letv.mas.client.omp.model.dao.SSOLoginMapper;
import com.letv.mas.client.omp.model.xdo.UserDo;
import com.letv.mas.client.omp.service.dto.AclDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SSOLoginService {

    @Autowired
    private UnifiedCallbackService callbackService;

    @Resource
    private SSOLoginMapper ssoLoginMapper;

    public String isLogin(String jsoncallback, String index) {
        return jsoncallback+"(" + new Gson().toJson(index) + ")";
    }


    public String logout(String jsoncallback, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if(cookies.length != 0){
            for (Cookie cookie:cookies){
                if("loginUser".equals(cookie.getName())){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    callbackService.redisUtil().lPOP(cookie.getValue(), callbackService.redisUtil().lGet(cookie.getValue(), 0, -1).size());
                    return jsoncallback+"(" + new Gson().toJson("OK") + ")";
                }
            }
        }
        return null;
    }


    public String checkLogin(String jsoncallback, HttpServletRequest request, HttpServletResponse response) {
        String loginUser = (String) request.getAttribute("loginUser");
        if(StringUtils.isNotBlank(loginUser)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length != 0){
                for (Cookie cookie: cookies){
                    if("loginUser".equals(cookie.getName())){
                        return jsoncallback+"(" + new Gson().toJson(cookie.getValue()) + ")";
                    }
                }
            }
            Cookie cookie = new Cookie("loginUser", loginUser);
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            return jsoncallback+"(" + new Gson().toJson(loginUser) + ")";
        }
        return null;
    }


    public String loadPageByAcl(String jsoncallback, HttpServletRequest request, HttpServletResponse response) {
        String loginUser = (String) request.getAttribute("loginUser");
        UserDo userByMail = ssoLoginMapper.findUserByMail(loginUser);
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
        return  jsoncallback+"(" + new Gson().toJson(showAcl) + ")";
    }
}

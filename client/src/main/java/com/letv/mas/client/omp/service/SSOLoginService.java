package com.letv.mas.client.omp.service;

import com.google.gson.Gson;
import com.letv.mas.client.omp.model.dao.SSOLoginMapper;
import com.letv.mas.client.omp.model.xdo.UserDo;
import com.letv.mas.client.omp.service.dto.AclDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class SSOLoginService {

    @Autowired
    private UnifiedCallbackService callbackService;

    @Resource
    private SSOLoginMapper ssoLoginMapper;

    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();

    public String isLogin(String jsoncallback, String index) {
        return jsoncallback+"(" + new Gson().toJson(index) + ")";
    }


    public String logout(String jsoncallback, String user) {
        String redisUser = "";
        try {
            redisUser = new String(decoder.decode(user), "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        callbackService.redisUtil().lPOP(redisUser, callbackService.redisUtil().lGet(redisUser, 0, -1).size());
        return jsoncallback+"(" + new Gson().toJson("OK") + ")";

    }


    public String checkLogin(String jsoncallback,String user, HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap<>();
        String loginUser = (String) request.getAttribute("OMP_LOGIN_USER");
        if(StringUtils.isNotBlank(loginUser)){
            loginUser = loginUser.split("@")[0];
            if(StringUtils.isBlank(loginUser) && StringUtils.isNotBlank(user)){
                loginUser = user;
            }
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
            map.put("acl",showAcl);
            if(StringUtils.isNotBlank(loginUser)){
                Cookie[] cookies = request.getCookies();
                if(cookies != null && cookies.length != 0){
                    for (Cookie cookie: cookies){
                        if("OMP_LOGIN_USER".equals(cookie.getName())){
                            String str = "";
                            try {
                                final byte[] textByte = loginUser.getBytes("UTF-8");
                                //编码
                                str = encoder.encodeToString(textByte);
                            }catch (UnsupportedEncodingException e){
                                e.printStackTrace();
                            }
                            map.put("OMP_LOGIN_USER",str);
                            return jsoncallback+"(" + new Gson().toJson(map) + ")";
                        }
                    }
                }
                String str = "";
                try {
                    final byte[] textByte = loginUser.getBytes("UTF-8");
                    //编码
                    str = encoder.encodeToString(textByte);
                    //解码
//                    System.out.println(new String(decoder.decode(str), "UTF-8"));
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                map.put("OMP_LOGIN_USER",str);
                return jsoncallback+"(" + new Gson().toJson(map) + ")";
            }
        }
        return null;
    }
}

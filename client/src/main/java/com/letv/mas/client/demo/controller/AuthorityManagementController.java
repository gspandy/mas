package com.letv.mas.client.demo.controller;

import com.google.gson.Gson;
import com.letv.mas.client.demo.model.dao.AuthorityManagementMapper;

import com.letv.mas.client.demo.model.dao.DemoMapper;
import com.letv.mas.client.demo.model.dto.AclDto;
import com.letv.mas.client.demo.model.dto.UserDto;
import com.letv.mas.client.demo.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/acl")
public class AuthorityManagementController {

    @Resource
    private AuthorityManagementMapper authorityManagementMapper;

    @Resource
    private DemoMapper demoMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 查询所有权限tree
     */
    @RequestMapping("/allAclsAsTree")
    public void allAclsAsTree(@RequestParam String jsoncallback,@RequestParam String name,HttpServletResponse response){
        UserDto user = demoMapper.findUserByMail(name);
        String[] acls = user.getCode().split(",");
        List allAcl = redisUtil().lGet("AllAcl", 0, -1);
        if(allAcl == null){
            allAcl = demoMapper.findAllAcls();
        }
        List showAcl = new ArrayList();
        for (int i = 0; i < acls.length; i++) {
            for (Object acl : allAcl) {
                if (((AclDto)acl).getId().equals(acls[i])) {
                    ((AclDto)acl).setChecked(true);
                    showAcl.add(acl);
                    break;
                }
            }
        }
        List returnData = new ArrayList();
        for (Object acl : allAcl) {
            if (((AclDto)acl).get_parentId().equals("0")){
                AclDto dto = (AclDto)acl;
                String id = dto.getId();
                for (Object _acl : allAcl) {
                    if (((AclDto) _acl).get_parentId().equals(id) && dto.getId().equals(id)) {
                        dto.getChildren().add(_acl);
                    }
                }
                returnData.add(dto);
            }
        }
        callBackJson(jsoncallback,returnData,response);
    }

    /**
     * 查询所有权限
     */
    @RequestMapping("/allAcls")
    public void allAcls(@RequestParam String jsoncallback, HttpServletResponse response){
        callBackJson(jsoncallback,redisUtil().lGet("AllAcl", 0, -1),response);
    }

    /**
     * 修改权限
     */
    @RequestMapping("/updateAcl")
    public void updateAcl(@RequestParam String jsoncallback,String id,String name,String path,String _parentId,HttpServletResponse response){
        if(StringUtils.isNotBlank(id)){
            callBackJson(jsoncallback,authorityManagementMapper.updateAcl(id,name,path,_parentId),response);
            reloadAllAcl();
        }else {
            if(!StringUtils.isNotBlank(_parentId)){
                _parentId = "0";
            }
            callBackJson(jsoncallback,authorityManagementMapper.insertAcl(id,name,path,_parentId),response);
            reloadAllAcl();
        }
    }

    /**
     * 删除权限
     */
    @RequestMapping("/deleteAcl")
    public void deleteAcl(@RequestParam String jsoncallback,@RequestParam int id,HttpServletResponse response){
        callBackJson(jsoncallback,authorityManagementMapper.deleteAcl(id),response);
        reloadAllAcl();
    }

    /**
     * 查询所有用户
     */
    @RequestMapping("/allUsers")
    public void allUsers(@RequestParam String jsoncallback,@RequestParam int pageSize,@RequestParam int pageNum,HttpServletResponse response){
        if(pageNum<1){
            pageNum = 1;
        }
        Map map = new HashMap();
        List<UserDto> allUsers = authorityManagementMapper.findPageUsers(pageSize*(pageNum-1),pageSize);
        int total = authorityManagementMapper.findAllUsers();
        map.put("total",total);
        map.put("rows",allUsers);
        callBackJson(jsoncallback,map,response);
    }

    /**
     * 按条件查找用户
     */
    @RequestMapping("/findUsersByMail")
    public void findUsersByMail(@RequestParam String jsoncallback,@RequestParam String mail, HttpServletResponse response){
        callBackJson(jsoncallback,authorityManagementMapper.findUsersByMail(mail),response);
    }

    /**
     * 修改用户权限
     */
    @RequestMapping("/updateUserAcl")
    public void updateUserAcl(@RequestParam String jsoncallback,@RequestParam String id,@RequestParam String type,
                              @RequestParam String code, HttpServletResponse response){
        callBackJson(jsoncallback,authorityManagementMapper.updateUserAcl(id,type,code),response);
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

    private void reloadAllAcl() {
        redisUtil().lPOP("AllAcl", redisUtil().lGet("AllAcl", 0, -1).size());
        List allAcl = demoMapper.findAllAcls();
        redisUtil().lSet("AllAcl",allAcl);
        redisUtil().expire("AllAcl", 86400);
    }

}

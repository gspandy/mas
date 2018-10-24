package com.letv.mas.client.demo.controller;

import com.letv.mas.client.demo.model.dao.AuthorityManagementMapper;

import com.letv.mas.client.demo.model.dao.SSOLoginMapper;
import com.letv.mas.client.demo.model.dto.AclDto;
import com.letv.mas.client.demo.model.dto.UserDto;
import com.letv.mas.client.demo.service.UnifiedCallbackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    private SSOLoginMapper ssoLoginMapper;

    @Autowired
    private UnifiedCallbackService callbackService;

    /**
     * 查询所有权限tree
     * @param jsoncallback
     * @param name 要修改的用户
     * @param response
     */
    @RequestMapping("/allAclsAsTree")
    public void allAclsAsTree(@RequestParam String jsoncallback,@RequestParam String name,HttpServletResponse response){
        UserDto user = ssoLoginMapper.findUserByMail(name);
        String[] acls = user.getCode().split(",");
        List allAcl = callbackService.redisUtil().lGet("AllAcl", 0, -1);
        if(allAcl == null){
            allAcl = ssoLoginMapper.findAllAcls();
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
        callbackService.callBackJson(jsoncallback,returnData,response);
    }

    /**
     * 查询所有权限
     * @param jsoncallback
     * @param response
     */
    @RequestMapping("/allAcls")
    public void allAcls(@RequestParam String jsoncallback, HttpServletResponse response){
        callbackService.callBackJson(jsoncallback,callbackService.redisUtil().lGet("AllAcl", 0, -1),response);
    }

    /**
     * 修改权限
     */
    @RequestMapping("/updateAcl")
    public void updateAcl(@RequestParam String jsoncallback,String id,String name,String path,String _parentId,HttpServletResponse response){
        if(StringUtils.isNotBlank(id)){
            callbackService.callBackJson(jsoncallback,authorityManagementMapper.updateAcl(id,name,path,_parentId),response);
            callbackService.reloadAllAcl();
        }else {
            if(!StringUtils.isNotBlank(_parentId)){
                _parentId = "0";
            }
            callbackService.callBackJson(jsoncallback,authorityManagementMapper.insertAcl(id,name,path,_parentId),response);
            callbackService.reloadAllAcl();
        }
    }

    /**
     * 删除权限
     * @param jsoncallback
     * @param id 权限对应ID
     * @param response
     */
    @RequestMapping("/deleteAcl")
    public void deleteAcl(@RequestParam String jsoncallback,@RequestParam int id,HttpServletResponse response){
        callbackService.callBackJson(jsoncallback,authorityManagementMapper.deleteAcl(id),response);
        callbackService.reloadAllAcl();
    }

    /**
     * 查询所有用户
     * @param jsoncallback
     * @param pageNum 当前页
     * @param pageSize 加载数量
     * @param response
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
        callbackService.callBackJson(jsoncallback,map,response);
    }

    /**
     * 按条件查找用户
     * @param jsoncallback
     * @param mail 用户邮箱片段
     * @param response
     */
    @RequestMapping("/findUsersByMail")
    public void findUsersByMail(@RequestParam String jsoncallback,@RequestParam String mail, HttpServletResponse response){
        callbackService.callBackJson(jsoncallback,authorityManagementMapper.findUsersByMail(mail),response);
    }

    /**
     * 修改用户权限
     * @param jsoncallback
     * @param id 要修改的用户ID
     * @param type 用户类型
     * @param code 权限简码
     * @param response
     */
    @RequestMapping("/updateUserAcl")
    public void updateUserAcl(@RequestParam String jsoncallback,@RequestParam String id,@RequestParam String type,
                              @RequestParam String code, HttpServletResponse response){
        callbackService.callBackJson(jsoncallback,authorityManagementMapper.updateUserAcl(id,type,code),response);
    }




}

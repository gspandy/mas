package com.letv.mas.client.omp.service;

import com.google.gson.Gson;
import com.letv.mas.client.omp.model.dao.AuthorityManagementMapper;
import com.letv.mas.client.omp.model.dao.SSOLoginMapper;
import com.letv.mas.client.omp.model.xdo.UserDo;
import com.letv.mas.client.omp.service.dto.AclDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorityManagementService {

    @Resource
    private AuthorityManagementMapper authorityManagementMapper;

    @Resource
    private SSOLoginMapper ssoLoginMapper;

    @Autowired
    private UnifiedCallbackService callbackService;

    public String allAclsAsTree(String jsoncallback, String name) {
        UserDo user = ssoLoginMapper.findUserByMail(name);
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
        return jsoncallback+"(" + new Gson().toJson(returnData) + ")";
    }


    public String allAcls(String jsoncallback, int pageSize, int pageNum) {
        if(pageNum<1){
            pageNum = 1;
        }
        Map map = new HashMap();
        List<AclDto> allAcls = authorityManagementMapper.findPageAcls(pageSize*(pageNum-1),pageSize);
        int total = authorityManagementMapper.findAllAcls();
        map.put("total",total);
        map.put("rows",allAcls);
        return jsoncallback+"(" + new Gson().toJson(map) + ")";
    }

    public String updateAcl(String jsoncallback, String id, String name, String path, String _parentId) {
        if(StringUtils.isNotBlank(id)){
            int i = authorityManagementMapper.updateAcl(id, name, path, _parentId);
            callbackService.reloadAllAcl();
            return jsoncallback+"(" + new Gson().toJson(i) + ")";
        }else {
            if(!StringUtils.isNotBlank(_parentId)){
                _parentId = "0";
            }
            int i = authorityManagementMapper.insertAcl(id, name, path, _parentId);
            callbackService.reloadAllAcl();
            return jsoncallback+"(" + new Gson().toJson(i) + ")";
        }
    }

    public String deleteAcl(String jsoncallback, int id) {
        int i = authorityManagementMapper.deleteAcl(id);
        callbackService.reloadAllAcl();
        List<UserDo> allUsers = ssoLoginMapper.findAllUsers();
        if(allUsers != null){
            for(UserDo user : allUsers){
                String[] code = user.getCode().split(",");
                if(code.length != 0){
                    List<String> list = new ArrayList<>();
                    for (int j = 0,end = code.length; j < end; j++) {
                        if(!code[j].equals(id+"")){
                            list.add(code[j]);
                        }
                    }
                    String newCode = "";
                    for (int j = 0,end = list.size(); j < end; j++) {
                        if(j == end -1){
                            newCode += list.get(j);
                        }else{
                            newCode += list.get(j) + ",";
                        }
                    }
                    authorityManagementMapper.updateUserAcl(user.getId(),user.getType(),newCode);
                }
            }
        }
        return jsoncallback+"(" + new Gson().toJson(i) + ")";
    }


    public String allUsers(String jsoncallback, int pageSize, int pageNum) {
        if(pageNum<1){
            pageNum = 1;
        }
        Map map = new HashMap();
        List<UserDo> allUsers = authorityManagementMapper.findPageUsers(pageSize*(pageNum-1),pageSize);
        int total = authorityManagementMapper.findAllUsers();
        map.put("total",total);
        map.put("rows",allUsers);
        return jsoncallback+"(" + new Gson().toJson(map) + ")";
    }

    public String findUsersByMail(String jsoncallback, String mail) {
        return jsoncallback+"(" + new Gson().toJson(authorityManagementMapper.findUsersByMail(mail)) + ")";
    }

    public String updateUserAcl(String jsoncallback, String id, String type, String code) {
        return jsoncallback+"(" + new Gson().toJson(authorityManagementMapper.updateUserAcl(id,type,code)) + ")";
    }
}

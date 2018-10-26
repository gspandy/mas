package com.letv.mas.client.omp.controller;

import com.letv.mas.client.omp.service.AuthorityManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acl")
public class AuthorityManagementController {

    @Autowired
    private AuthorityManagementService authorityManagementService;

    /**
     * 查询所有权限tree
     * @param jsoncallback
     * @param name 要修改的用户
     */
    @RequestMapping("/allAclsAsTree")
    public String allAclsAsTree(@RequestParam String jsoncallback,@RequestParam String name){
        return authorityManagementService.allAclsAsTree(jsoncallback,name);
    }

    /**
     * 查询所有权限
     * @param jsoncallback
     */
    @RequestMapping("/allAcls")
    public String allAcls(@RequestParam String jsoncallback,@RequestParam int pageSize,@RequestParam int pageNum){
        return authorityManagementService.allAcls(jsoncallback,pageSize,pageNum);
    }

    /**
     * 修改权限
     */
    @RequestMapping("/updateAcl")
    public String updateAcl(@RequestParam String jsoncallback,String id,String name,String path,String _parentId){
        return authorityManagementService.updateAcl(jsoncallback,id,name,path,_parentId);
    }

    /**
     * 删除权限
     * @param jsoncallback
     * @param id 权限对应ID
     */
    @RequestMapping("/deleteAcl")
    public String deleteAcl(@RequestParam String jsoncallback,@RequestParam int id){
        return authorityManagementService.deleteAcl(jsoncallback,id);
    }

    /**
     * 查询所有用户
     * @param jsoncallback
     * @param pageNum 当前页
     * @param pageSize 加载数量
     */
    @RequestMapping("/allUsers")
    public String allUsers(@RequestParam String jsoncallback,@RequestParam int pageSize,@RequestParam int pageNum){
        return authorityManagementService.allUsers(jsoncallback,pageSize,pageNum);
    }

    /**
     * 按条件查找用户
     * @param jsoncallback
     * @param mail 用户邮箱片段
     */
    @RequestMapping("/findUsersByMail")
    public String findUsersByMail(@RequestParam String jsoncallback,@RequestParam String mail){
        return authorityManagementService.findUsersByMail(jsoncallback,mail);
    }

    /**
     * 修改用户权限
     * @param jsoncallback
     * @param id 要修改的用户ID
     * @param type 用户类型
     * @param code 权限简码
     */
    @RequestMapping("/updateUserAcl")
    public String updateUserAcl(@RequestParam String jsoncallback,@RequestParam String id,@RequestParam String type,
                              @RequestParam String code){
        return authorityManagementService.updateUserAcl(jsoncallback,id,type,code);
    }

}

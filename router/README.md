# 微服务网关

#### tvproxy 大屏代理层公共网关

###### [JWT](https://tools.ietf.org/html/rfc7519)／支持

###### [OAuth2](https://tools.ietf.org/html/rfc6749)／可选支持

~~~
+--------+                               +---------------+
|        |--(A)- Authorization Request ->|   Resource    |
|        |                               |     Owner     |
|        |<-(B)-- Authorization Grant ---|               |
|        |                               +---------------+
|        |
|        |                               +---------------+
|        |--(C)-- Authorization Grant -->| Authorization |
| Client |                               |     Server    |
|        |<-(D)----- Access Token -------|               |
|        |                               +---------------+
|        |
|        |                               +---------------+
|        |--(E)----- Access Token ------>|    Resource   |
|        |                               |     Server    |
|        |<-(F)--- Protected Resource ---|               |
+--------+                               +---------------+
~~~  


###### RBAC权限管理
* 字典参考

~~~
user:
     id
     name
     description     
     domain_id
     group_id     
     project_id     
     role_id
     enabled
     
domain:
     id
     name
     enabled

group:
     id
     name
     description     
     domain_id
          
project:
     id
     name
     description
     domain_id
     enabled     

role:
     id
     name
~~~       

* 匹配参考

~~~ 
"identity:create_user": "domain:%(user.domain_id) [and group:%(user.group_id)] [and project:%(user.project_id)] and role:%(user.role_id)"
~~~ 

* 业务模型

| 界面元素描述 | 资源编码 | API |  请求类型 | 备注
| - | :- | :- | :- | -: | 
| 应用管理／添加按钮 | app_mgr_add | /i/app/manage |  POST | TODO
| 应用管理／删除按钮 | app_mgr_del | /i/app/manage/{id} |  DELETE | TODO
| 应用管理／修改按钮 | app_mgr_edit | /i/app/manage/{id} |  PUT | TODO
| 应用管理／查询按钮 | app_mgr_get | /i/app/manage/{id} |  GET | TODO

package com.letv.mas.router.iptv.tvproxy.model.xdo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by leeco on 18/10/19.
 * `id` int(11) NOT NULL AUTO_INCREMENT,
 * `user_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '用户（业务）id',
 * `login_type` varchar(10) DEFAULT NULL COMMENT '登录类型(1用户名|2邮箱|3手机|分割)',
 * `name` varchar(64) DEFAULT NULL COMMENT '用户名称(注册用户)',
 * `email` varchar(64) DEFAULT NULL COMMENT 'email地址(注册用户)',
 * `email_verify_flag` tinyint(4) DEFAULT 0 COMMENT '邮箱验证标志 0未验证 1验证通过',
 * `mobile` varchar(64) DEFAULT NULL COMMENT '手机电话号码(注册用户)',
 * `mobile_verify_flag` tinyint(4) DEFAULT 0 COMMENT '手机验证标志 0未验证 1验证通过',
 * `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
 * `telephone` varchar(64) DEFAULT NULL COMMENT '固定电话号码(只做登记)',
 * `gender` tinyint(4) DEFAULT NULL COMMENT '性别 0保密 1男性 2女性',
 * `pwd` char(32) NOT NULL COMMENT '密码, 签名后的hash值',
 * `province_id` int(10) DEFAULT NULL COMMENT '所在地省份编号',
 * `city_id` int(10) DEFAULT NULL COMMENT '所在地城市编号',
 * `address` varchar(64) DEFAULT NULL COMMENT '详细地址',
 * `post_code` varchar(10) DEFAULT NULL COMMENT '邮编',
 * `image` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像地址',
 * `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
 * `register_ip` varchar(20) DEFAULT NULL COMMENT '注册ip',
 * `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
 * `status` tinyint(4) DEFAULT NULL COMMENT '状态(0开通,1 锁定,2 注销)',
 * `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
 * `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登录ip',
 * `role` tinyint(4) DEFAULT 0 COMMENT '角色, 0-普通',
 * `badges` varchar(32) DEFAULT NULL COMMENT '用户标识，多重用逗号分隔，1-开发者',
 * `qq` varchar(32) DEFAULT NULL COMMENT 'qq号',
 * `openid` varchar(50) DEFAULT NULL COMMENT '微信openid',
 * `is_del` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记(0未删除 1删除)',
 */
@Data
public class UserDo extends BaseDo {
    public Long id;
    public String user_id;
    public String login_type;
    public String name;
    public String email;
    public int email_verify_flag;
    public String mobile;
    public int mobile_verify_flag;
    public String nick_name;
    public String telephone;
    public int gender;
    public String pwd;
    public int province_id;
    public int city_id;
    public String address;
    public String post_code;
    public String image;
    public Timestamp register_time;
    public String register_ip;
    public Timestamp update_time;
    public int status;
    public Timestamp last_login_time;
    public String last_login_ip;
    public int role;
    public int badges;
    public String qq;
    public String openid;
    public int is_del;
}

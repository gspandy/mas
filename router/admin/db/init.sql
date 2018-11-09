-- MySQL
--
-- Host: 127.0.0.1    Database: mas
-- ------------------------------------------------------
-- Server version	10.1.31-MariaDB
CREATE DATABASE IF NOT EXISTS `omp` /*DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci*/;
SET @@global.init_connect='';
# DROP USER mas;
# CREATE USER mas@'%' IDENTIFIED BY 'SVynKuZjR8zqSwRE';
# GRANT ALL PRIVILEGES ON *.* TO 'mas'@'%';
USE `omp`;

--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '用户（业务）id',
  `login_type` varchar(10) DEFAULT NULL COMMENT '登录类型(1用户名|2邮箱|3手机|分割)',
  `name` varchar(64) DEFAULT NULL COMMENT '用户名称(注册用户)',
  `email` varchar(64) DEFAULT NULL COMMENT 'email地址(注册用户)',
  `email_verify_flag` tinyint(4) DEFAULT 0 COMMENT '邮箱验证标志 0未验证 1验证通过',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机电话号码(注册用户)',
  `mobile_verify_flag` tinyint(4) DEFAULT 0 COMMENT '手机验证标志 0未验证 1验证通过',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `telephone` varchar(64) DEFAULT NULL COMMENT '固定电话号码(只做登记)',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别 0保密 1男性 2女性',
  `pwd` char(32) NOT NULL COMMENT '密码, 签名后的hash值',
  `province_id` int(10) DEFAULT NULL COMMENT '所在地省份编号',
  `city_id` int(10) DEFAULT NULL COMMENT '所在地城市编号',
  `address` varchar(64) DEFAULT NULL COMMENT '详细地址',
  `post_code` varchar(10) DEFAULT NULL COMMENT '邮编',
  `image` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像地址',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `register_ip` varchar(20) DEFAULT NULL COMMENT '注册ip',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0开通,1 锁定,2 注销)',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登录ip',
  `role` tinyint(4) DEFAULT 0 COMMENT '角色, 0-普通',
  `badges` varchar(32) DEFAULT NULL COMMENT '用户标识，多重用逗号分隔，1-开发者',
  `qq` varchar(32) DEFAULT NULL COMMENT 'qq号',
  `openid` varchar(50) DEFAULT NULL COMMENT '微信openid',
  `is_del` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记(0未删除 1删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (null, '1', '2', 'mas', 'letv_monitor', '1', null, 0, null, null, null, 'password', null, null, null, null, 'image', null, null, null, null, null, null, '0', null, null, null, 0);
COMMIT;

--
-- 表的结构 `oauth_client`
--
DROP TABLE IF EXISTS `oauth_client`;
CREATE TABLE `oauth_client` (
  `id` bigint(20) NOT NULL auto_increment,
  `client_name` varchar(100) NOT NULL COMMENT '应用名称',
  `en_name` varchar(50) DEFAULT NULL COMMENT '应用英文名称',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `client_id` varchar(32) DEFAULT '' COMMENT '应用（业务）id',
  `client_secret` varchar(32) DEFAULT '' COMMENT '应用密钥',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '终止使用时间',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '应用状态 (0：填报资料  1：送审中  2：审核失败  3：审核成功)',
  `cause` varchar(200) DEFAULT NULL COMMENT '驳回原因',
  `desc` varchar(200) DEFAULT NULL COMMENT '简介',
  `flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否启用(0:启用，1:删除，2:停用)',
  PRIMARY KEY  (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_client_id` (`client_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='应用表';

-- ----------------------------
--  Records of `oauth_client`
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client` VALUES (null, '超级影视', 'leitv', '1', '', 'tvproxy-apis@20181016', null, null, '2019-10-16 00:00:00', 3, null, null, 0);
COMMIT;

--
-- 表的结构 `oauth_code`
--
DROP TABLE IF EXISTS `auth_code`;
CREATE TABLE `oauth_code` (
  `id` bigint(20) NOT NULL auto_increment,
  `client_id` varchar(32) NOT NULL COMMENT '应用id',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `code` varchar(40) NOT NULL COMMENT '用户授权码',
  `redirect_uri` varchar(200) NOT NULL COMMENT '应用注册回调',
  `expires` timestamp NOT NULL COMMENT '过期时间',
  `scope` varchar(250) DEFAULT '' COMMENT '权限参数，API组名串。多个组名时，用,分隔',
  `is_del` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记(0未删除 1删除)',
  PRIMARY KEY  (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_client_id` (`client_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='oauth授权码信息表';

--
-- 表的结构 `oauth_token`
--
DROP TABLE IF EXISTS `oauth_token`;
CREATE TABLE `oauth_token` (
  `id` bigint(20) NOT NULL auto_increment,
  `client_id` varchar(32) NOT NULL COMMENT '应用id',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `access_token` varchar(40) NOT NULL COMMENT '用户授权token',
  `refresh_token` varchar(40) NOT NULL COMMENT '用户刷新token',
  `expires` timestamp NOT NULL COMMENT '过期时间',
  `scope` varchar(250) DEFAULT '' COMMENT '权限参数，API组名串。多个组名时，用,分隔',
  `is_del` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记(0未删除 1删除)',
  PRIMARY KEY  (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_client_id` (`client_id`),
  KEY `idx_access_token` (`access_token`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='oauth授权token信息表';

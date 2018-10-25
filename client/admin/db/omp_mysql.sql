#创建数据库
CREATE DATABASE omp;
USE omp;

#创建用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '用户（业务）id',
  `login_type` varchar(10) DEFAULT NULL COMMENT '登录类型(1用户名|2邮箱|3手机|分割)',
  `name` varchar(64) DEFAULT NULL COMMENT '用户名称(注册用户)',
  `mail` varchar(64) DEFAULT NULL COMMENT 'email地址(注册用户)',
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
  `type` tinyint(4) DEFAULT 1 COMMENT '类型(1用户，2管理员)',
  `badges` varchar(32) DEFAULT NULL COMMENT '用户标识，多重用逗号分隔，1-开发者',
  `qq` varchar(32) DEFAULT NULL COMMENT 'qq号',
  `openid` varchar(50) DEFAULT NULL COMMENT '微信openid',
  `is_del` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记(0未删除 1删除)',
  `code` varchar(40) NOT NULL DEFAULT '1,2,3,4,5' COMMENT '权限码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户信息表';


#创建权限表
DROP TABLE IF EXISTS `auth_data`;
CREATE TABLE `auth_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `path` varchar(200) NOT NULL DEFAULT '' COMMENT ' 权限对应路径',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级权限模块id',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限模块在当前层级下的顺序，由小到大',
  `checked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否选中',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限信息表';
BEGIN;
INSERT INTO `auth_data` (`name`, `path`, `parent_id`, `seq`, `checked`, `status`, `remark`, `operate_time`)
VALUES
	('MasDev', '', 0, 1, 0, 1, '', '2018-10-08 11:24:21'),
	('letv-mas-code', '../../pages/mas/c_code_git.html', 1, 0, 0, 1, '', '2018-10-08 11:28:12'),
	('letv-mas-deploy', '../../pages/mas/c_deploy_jenkins.html', 1, 1, 0, 1, '', '2018-10-08 11:29:01'),
	('letv-mas-manager', '../../pages/mas/c_eureka_manager.html', 1, 2, 0, 1, '', '2018-10-08 11:29:18'),
	('letv-mas-trace', '../../pages/mas/c_trace_zipkin.html', 1, 3, 0, 1, '', '2018-10-08 11:29:35'),
	('letv-mas-config', '../../pages/mas/c_apollo_config.html', 1, 0, 0, 1, '', '2018-10-08 11:29:40'),
	('MasOps', '', 0, 2, 0, 1, '', '2018-10-08 11:30:01'),
	('letv-mas-logs', '../../pages/mas/c_trace_kibana.html', 7, 0, 0, 1, '', '2018-10-08 11:30:25'),
	('letv-mw-kafka', '../../pages/mas/c_mw_kafka.html', 7, 1, 0, 1, '', '2018-10-08 11:30:45'),
	('letv-mas-dashboard', '../../pages/mas/c_docker_dashboard.html', 7, 2, 0, 1, '', '2018-10-08 11:31:16');
COMMIT;





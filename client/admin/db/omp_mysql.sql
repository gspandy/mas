#创建数据库
create database letv;
use letv;
#创建权限表
DROP TABLE IF EXISTS `letv_acl`;
CREATE TABLE `letv_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `path` varchar(200) NOT NULL DEFAULT '' COMMENT ' 权限对应路径',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级权限模块id',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限模块在当前层级下的顺序，由小到大',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
BEGIN;
INSERT INTO `letv_acl` (`id`, `name`, `path`, `parent_id`, `seq`, `status`, `remark`, `operate_time`)
VALUES
	(12, 'MasDev', '', 0, 1, 1, '', '2018-10-08 11:24:21'),
	(13, 'letv-mas-code', '../../pages/mas/c_code_git.html', 12, 0, 1, '', '2018-10-08 11:28:12'),
	(14, 'letv-mas-deploy', '../../pages/mas/c_deploy_jenkins.html', 12, 1, 1, '', '2018-10-08 11:29:01'),
	(15, 'letv-mas-manager', '../../pages/mas/c_eureka_manager.html', 12, 2, 1, '', '2018-10-08 11:29:18'),
	(16, 'letv-mas-trace', '../../pages/mas/c_trace_zipkin.html', 12, 3, 1, '', '2018-10-08 11:29:35'),
	(17, 'MasOps', '', 0, 2, 1, '', '2018-10-08 11:30:01'),
	(18, 'letv-mas-logs', '../../pages/mas/c_trace_kibana.html', 17, 0, 1, '', '2018-10-08 11:30:25'),
	(19, 'letv-mw-kafka', '../../pages/mas/c_mw_kafka.html', 17, 1, 1, '', '2018-10-08 11:30:45'),
	(20, 'letv-mas-dashboard', '../../pages/mas/c_docker_dashboard.html', 17, 2, 1, '', '2018-10-08 11:31:16');
COMMIT;


#创建用户表
DROP TABLE IF EXISTS `letv_sso_user`;
CREATE TABLE `letv_sso_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mail` varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '类型(1用户，2管理员)',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户所在部门的id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结状态，2：删除',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `code` varchar(40) NOT NULL DEFAULT '12,14,15,16' COMMENT '权限码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;


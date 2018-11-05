package com.letv.mas.router.iptv.tvproxy.model.xdo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by leeco on 18/10/19.
 * `id` bigint(20) NOT NULL auto_increment,
 * `client_name` varchar(100) NOT NULL COMMENT '应用名称',
 * `en_name` varchar(50) DEFAULT NULL COMMENT '应用英文名称',
 * `user_id` varchar(32) NOT NULL COMMENT '用户id',
 * `client_id` varchar(32) DEFAULT '' COMMENT '应用（业务）id',
 * `client_secret` varchar(32) DEFAULT '' COMMENT '应用密钥',
 * `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 * `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
 * `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '终止使用时间',
 * `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '应用状态 (0：填报资料  1：送审中  2：审核失败  3：审核成功)',
 * `cause` varchar(200) DEFAULT NULL COMMENT '驳回原因',
 * `desc` varchar(200) DEFAULT NULL COMMENT '简介',
 * `flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否启用(0:启用，1:删除，2:停用)',
 */
@Data
public class ClientDo extends BaseDo {
    public Long id;
    public String user_id;
    public String login_type;
    public String client_name;
    public String en_name;
    public String client_id;
    public String client_secret;
    public Timestamp create_time;
    public Timestamp update_time;
    public Timestamp end_time;
    public int status;
    public String cause;
    public String desc;
    public int flag;
}

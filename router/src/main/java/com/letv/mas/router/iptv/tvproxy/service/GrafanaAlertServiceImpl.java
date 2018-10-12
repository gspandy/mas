package com.letv.mas.router.iptv.tvproxy.service;

import com.letv.mas.router.iptv.tvproxy.model.dao.tp.alibaba.DingDingDao;
import com.letv.mas.router.iptv.tvproxy.model.dto.DingRobotDto;
import com.letv.mas.router.iptv.tvproxy.model.dto.GrafanaNotifyDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by leeco on 18/10/8.
 */
@Service("GrafanaAlertService")
public class GrafanaAlertServiceImpl implements IAlertService {

    @Resource
    DingDingDao dingDingDao;

    @Override
    public boolean handleMessage(Object msg) {

        if (msg instanceof GrafanaNotifyDto) {
            GrafanaNotifyDto grafanaNotifyDto = (GrafanaNotifyDto) msg;

            if (StringUtils.isEmpty(grafanaNotifyDto.getRuleId())) {
                return false;
            }

            DingRobotDto dingRobotDto = new DingRobotDto();
            StringBuilder content = new StringBuilder();
            dingRobotDto.setMsgtype("markdown");
            dingRobotDto.setTitle(grafanaNotifyDto.getTitle());

            if (StringUtils.isNotBlank(grafanaNotifyDto.getRuleName())) {
                content.append("### ").append(grafanaNotifyDto.getRuleName());
            } else {
                content.append("### ").append(grafanaNotifyDto.getTitle());
            }

            if (StringUtils.isNotBlank(grafanaNotifyDto.getState())) {
                content.append(" **{").append(grafanaNotifyDto.getState()).append("}**");
            }

            if (StringUtils.isNotBlank(grafanaNotifyDto.getMessage())) {
                content.append("\n").append("> ").append(grafanaNotifyDto.getMessage());
            }

            if (StringUtils.isNotBlank(grafanaNotifyDto.getImageUrl())) {
                content.append("\n\n").append("> ![screenshot](").append(grafanaNotifyDto.getImageUrl()).append(")");
            }

            if (null != grafanaNotifyDto.getEvalMatches() && grafanaNotifyDto.getEvalMatches().size() > 0) {
                for (GrafanaNotifyDto.EvalMatche evalMatche : grafanaNotifyDto.getEvalMatches()) {
                    content.append("\n- ").append(evalMatche);
                }
            }

            if (StringUtils.isNotBlank(grafanaNotifyDto.getRuleUrl())) {
                content.append("\n\n").append("> [查看详情](").append(grafanaNotifyDto.getRuleUrl()).append(")");
            }

            dingRobotDto.setContent(content.toString());
            return dingDingDao.notify(dingRobotDto);
        }

        return false;
    }
}

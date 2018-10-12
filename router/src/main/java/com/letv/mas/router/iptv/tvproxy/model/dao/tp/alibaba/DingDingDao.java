package com.letv.mas.router.iptv.tvproxy.model.dao.tp.alibaba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.letv.mas.router.iptv.tvproxy.config.BizConfig;
import com.letv.mas.router.iptv.tvproxy.constant.BizConsts;
import com.letv.mas.router.iptv.tvproxy.model.dto.DingRobotDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Refer: https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7629140.0.0.45484a97Y6hvQA&treeId=257&articleId=105735&docType=1
 * Created by leeco on 18/10/8.
 */
@Component
public class DingDingDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DingDingDao.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private BizConfig bizConfig;

    public boolean notify(DingRobotDto dingRobotDto) {
        boolean ret = false;
        String url = bizConfig.getExUrls().get(BizConsts.ExUrls.EXURLS_ALI_DINGDING_ROBOT_WEBHOOK);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(this.buildDingRobotBody(dingRobotDto), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (null != response) {
            ret = response.getStatusCode().is2xxSuccessful();
        }
        return ret;
    }

    private String buildDingRobotBody(DingRobotDto dingRobotDto) {
        JSONObject body = new JSONObject();

        if ("text".equals(dingRobotDto.getMsgtype())) {
            body.put("msgtype", dingRobotDto.getMsgtype());
            JSONObject text = new JSONObject();
            text.put("content", dingRobotDto.getContent());
            body.put("text", text);
            if (StringUtils.isNotBlank(dingRobotDto.getAtMobiles())) {
                String[] mobiles = dingRobotDto.getAtMobiles().split(",");
                JSONArray atMobiles = new JSONArray();
                for (String str : mobiles) {
                    atMobiles.add(str);
                }
                if (atMobiles.size() > 0) {
                    JSONObject at = new JSONObject();
                    at.put("atMobiles", atMobiles);
                    at.put("isAtAll", dingRobotDto.isAtAll());
                    body.put("at", at);
                }
            }
        } else if ("link".equals(dingRobotDto.getMsgtype())) {
            body.put("msgtype", dingRobotDto.getMsgtype());
            JSONObject link = new JSONObject();
            link.put("title", dingRobotDto.getTitle());
            link.put("text", dingRobotDto.getContent());
            link.put("picUrl", dingRobotDto.getPicUrl());
            link.put("messageUrl", dingRobotDto.getLink());
            body.put("link", link);
        } else if ("markdown".equals(dingRobotDto.getMsgtype())) {
            body.put("msgtype", dingRobotDto.getMsgtype());
            JSONObject markdown = new JSONObject();
            markdown.put("title", dingRobotDto.getTitle());
            markdown.put("text", dingRobotDto.getContent());
            body.put("markdown", markdown);
            if (StringUtils.isNotBlank(dingRobotDto.getAtMobiles())) {
                String[] mobiles = dingRobotDto.getAtMobiles().split(",");
                JSONArray atMobiles = new JSONArray();
                for (String str : mobiles) {
                    atMobiles.add(str);
                }
                if (atMobiles.size() > 0) {
                    JSONObject at = new JSONObject();
                    at.put("atMobiles", atMobiles);
                    at.put("isAtAll", dingRobotDto.isAtAll());
                    body.put("at", at);
                }
            }
        }

        return body.toString();
    }
}

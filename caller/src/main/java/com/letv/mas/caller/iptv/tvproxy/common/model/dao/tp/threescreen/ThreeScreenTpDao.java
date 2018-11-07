package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.threescreen;

import java.net.URLEncoder;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.threescreen.request.PushServerRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.threescreen.response.PushServerStatus;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("v2.ThreeScreenTpDao")
public class ThreeScreenTpDao extends BaseTpDao {
    private final Logger log = LoggerFactory.getLogger(ThreeScreenTpDao.class);

    private static final String PUSH_SERVER_URL = "http://10.154.252.153:9010/push";

    private static final String SECRETKEY = "";

    public PushServerStatus getPushServerStatus(String appId, String servId, String to, Object msg) {

        PushServerStatus pushServerStatus = null;
        try {

            PushServerRequest request = new PushServerRequest();
            request.setAppId(appId);
            request.setServId(servId);
            request.setType(1);// 点对点
            request.setTo(to);
            request.setMsg(this.getMsg(msg));
            request.setSign(this.sign(SECRETKEY, "appid=" + appId, "servid=" + servId, "type=1", "from=", "to=" + to,
                    "msg=" + msg));

            String response = this.restTemplate.postForObject(PUSH_SERVER_URL, request, String.class);
            if (StringUtil.isNotBlank(response)) {
                pushServerStatus = objectMapper.readValue(response, PushServerStatus.class);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }

        return pushServerStatus;
    }

    private String sign(String secretKey, String... params) {
        StringBuffer paramStr = new StringBuffer();
        String result = "";
        for (String param : params) {
            paramStr = paramStr.append(param);
        }

        paramStr = paramStr.append(secretKey);

        try {
            result = MessageDigestUtil.md5(URLEncoder.encode(paramStr.toString()).getBytes());
        } catch (Exception e) {
            log.error("sign_ error", e.getMessage(), e);
        }

        return result;
    }

    private String getMsg(Object obj) {
        String msg = null;
        try {
            msg = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("getMsg_ error", e.getMessage(), e);
        }
        return msg;
    }

}

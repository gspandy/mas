package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.reponse.AlarmEmailResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.reponse.AlarmSMSResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.reponse.AlarmVoiceResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.request.AlarmEmailRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.request.AlarmSMSRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.request.AlarmVoiceRequest;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvCommonException;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
/**
 * 报警接口封装类
 * 包括：邮件报警、短信报警、语音报警
 * 使用此类实现报警，请需要把必要字段填写，不然后报异常
 * post提交 ，请求包括title/content均未进行urlencode
 * 默认TOKEN=947a54eef233a2d0d5d914e58acfa3aa,以wangshengkai@le.com身份进行申请
 * 如需要更改发件人，请申请新token并request中传进来。
 * TODO 1.无阻塞优化（同步顺序执行有导致系统无响应风险）、2.重试机制处理
 */
@Repository("v2.AlarmTpDao")
public class AlarmTpDao extends BaseTpDao {
    private static final Logger log = LoggerFactory.getLogger(AlarmTpDao.class);
    private static final String DEFAULT_TOKEN = "947a54eef233a2d0d5d914e58acfa3aa";

    public AlarmEmailResponse alarmWithEmail(AlarmEmailRequest req) {
        if (req == null) {
            return null;
        }
        if (req.getEmail() == null || req.getMsg() == null || req.getTitle() == null) {
            throw new LetvCommonException("params is invalid . req=" + req);
        }
        AlarmEmailResponse alarmEmailResponse = null;
        String response = null;
        try {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
            params.add("email", req.getEmail());
            if (req.getEmail_cc() != null && req.getEmail_cc().trim().length() > 0) {
                params.add("email_cc", req.getEmail_cc());
            }
            params.add("msg", req.getMsg());
            params.add("title", req.getTitle());
            if (req.getToken() != null) {
                params.add("token", req.getToken());
            } else {
                params.add("token", DEFAULT_TOKEN);
            }
            response = restTemplate.postForObject(ApplicationUtils.get(ApplicationConstants.LETV_ALARM_EMAIL_API),
                    params, String.class);
            log.info("alarmWithEmail response=" + response);
        } catch (Exception e) {
            log.warn("alarmWithEmail alarm error .request=" + req);
            log.warn("alarmWithEmail alarm error .reponse=" + response);
        }
        if (response != null) {
            try {
                alarmEmailResponse = objectMapper.readValue(response, AlarmEmailResponse.class);
            } catch (Exception e) {
                log.warn("alarmWithEmail response convert error .request=" + req);
                log.warn("alarmWithEmail response convert error .reponse=" + response);
            }
        }
        return alarmEmailResponse;
    }

    public AlarmSMSResponse alarmWithSMS(AlarmSMSRequest req) {
        if (req == null) {
            return null;
        }
        if (req.getMobile() == null || req.getMsg() == null) {
            throw new LetvCommonException("params is invalid . req=" + req);
        }
        AlarmSMSResponse alarmSMSResponse = null;
        String response = null;
        try {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
            params.add("mobile", req.getMobile());
            params.add("msg", req.getMsg());
            if (req.getToken() != null) {
                params.add("token", req.getToken());
            } else {
                params.add("token", DEFAULT_TOKEN);
            }
            response = restTemplate.postForObject(ApplicationUtils.get(ApplicationConstants.LETV_ALARM_SMS_API),
                    params, String.class);
            log.info("alarmWithSMS response=" + response);
        } catch (Exception e) {
            log.warn("alarmWithSMS alarm error .request=" + req);
            log.warn("alarmWithSMS alarm error .reponse=" + response);
        }
        if (response != null) {
            try {
                alarmSMSResponse = objectMapper.readValue(response, AlarmSMSResponse.class);
            } catch (Exception e) {
                log.warn("alarmWithSMS response convert error .request=" + req);
                log.warn("alarmWithSMS response convert error .reponse=" + response);
            }
        }
        return alarmSMSResponse;
    }

    public AlarmVoiceResponse alarmWithVoice(AlarmVoiceRequest req) {
        if (req == null) {
            return null;
        }
        if (req.getMobile() == null || req.getMsg() == null) {
            throw new LetvCommonException("params is invalid . req=" + req);
        }
        AlarmVoiceResponse alarmVoiceResponse = null;
        String response = null;
        try {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
            params.add("mobile", req.getMobile());
            params.add("msg", req.getMsg());
            if (req.getToken() != null) {
                params.add("token", req.getToken());
            } else {
                params.add("token", DEFAULT_TOKEN);
            }
            response = restTemplate.postForObject(ApplicationUtils.get(ApplicationConstants.LETV_ALARM_VOICE_API),
                    params, String.class);
            log.info("alarmWithVoice response=" + response);
        } catch (Exception e) {
            log.warn("alarmWithVoice alarm error .request=" + req);
            log.warn("alarmWithVoice alarm error .reponse=" + response);
        }
        if (response != null) {
            try {
                alarmVoiceResponse = objectMapper.readValue(response, AlarmVoiceResponse.class);
            } catch (Exception e) {
                log.warn("alarmWithVoice response convert error .request=" + req);
                log.warn("alarmWithVoice response convert error .reponse=" + response);
            }
        }
        return alarmVoiceResponse;
    }
}

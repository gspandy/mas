package com.letv.mas.caller.iptv.tvproxy.vip.builder;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.VoucherStatusTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.VoucherStatusDto;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherStatusBuilder {

    public static VoucherStatusDto build(VoucherStatusTpResponse voucherStatusTpResponse, List<Integer> typeList) {
        VoucherStatusDto dto = null;

        if (voucherStatusTpResponse != null && voucherStatusTpResponse.isAvailable()) {
            dto = new VoucherStatusDto();
            dto.setAmount(voucherStatusTpResponse.getAmount());
            dto.setStartTime(TimeUtil.format(new Date(voucherStatusTpResponse.getStartTime()),
                    TimeUtil.SHORT_DATE_FORMAT));
            dto.setEndTime(TimeUtil.format(new Date(voucherStatusTpResponse.getEndTime()), TimeUtil.SHORT_DATE_FORMAT));
            List<VoucherStatusTpResponse.VoucherStatusRule> rules = voucherStatusTpResponse.getRules();
            Map<String, List<Integer>> ruleMap = new HashMap<String, List<Integer>>();
            for (VoucherStatusTpResponse.VoucherStatusRule rule : rules) {
                ruleMap.put(String.valueOf(rule.getType()), rule.getIds());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                // 如果传了特殊的适用产品范围，则需要过滤
                Map<String, List<Integer>> filteredRuleMap = new HashMap<String, List<Integer>>();
                for (Integer type : typeList) {
                    filteredRuleMap.put(String.valueOf(type), ruleMap.get(String.valueOf(type)));
                }
                dto.setRules(filteredRuleMap);
            } else {
                dto.setRules(ruleMap);
            }
        }

        return dto;
    }
}

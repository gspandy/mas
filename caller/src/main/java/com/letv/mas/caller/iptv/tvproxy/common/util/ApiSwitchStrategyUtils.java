package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 切服规则：
 * 1.按接口url，问号前面
 * 2。按域名
 * 3.按requrl 整体url
 * Captures URI template variable names.
 * private static final Pattern NAMES_PATTERN =
 * Pattern.compile("\\{([^/]+?)\\}");
 * private static String expandUriComponent(String source, UriTemplateVariables
 * uriVariables) {
 * if (source == null) {
 * return null;
 * }
 * if (source.indexOf('{') == -1) {
 * return source;
 * }
 * Matcher matcher = NAMES_PATTERN.matcher(source);
 * StringBuffer sb = new StringBuffer();
 * while (matcher.find()) {
 * String match = matcher.group(1);
 * String variableName = getVariableName(match);
 * Object variableValue = uriVariables.getValue(variableName);
 * String variableValueString = getVariableValueAsString(variableValue);
 * String replacement = Matcher.quoteReplacement(variableValueString);
 * matcher.appendReplacement(sb, replacement);
 * }
 * matcher.appendTail(sb);
 * return sb.toString();
 * }
 */
public class ApiSwitchStrategyUtils {

    public static final String getUrlTemp(String url) {
        if (url == null || url.trim().length() == 0) {
            return url;
        }
        if (url.contains(ApplicationConstants.BOSS_YUANXIAN_BASE_HOST)) {
            return url.replaceAll("=(.*?&|.*)", "=&");
        }
        URL aURL;
        try {
            aURL = new URL(url);
            String path = aURL.getPath();
            String protocol = aURL.getProtocol();
            String host = aURL.getHost();
            StringBuilder sb = new StringBuilder();
            sb.append(protocol).append("://").append(host);
            for (String pathItem : path.split("/")) {
                int digitInString = pathItem.replaceAll("\\D", "").length();
                // 如果Path某一截存在数字超过3个，视为变化部分，该部分不计入熔断器所存接口url中
                if (digitInString >= 3) {
                    break;
                }
                sb.append(pathItem).append("/");
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            return url.split("\\?")[0];
        } finally {
            aURL = null;
        }
    }

    public static void main(String[] args) {
        String url1 = "http://api.sso.cp21.ott.cibntv.net/api/checkTicket/tk/1038fe36edjobhgj8vHJCWCZkPNDOR6L4KboDbD2OUMwUxJcPglHkm3m1i7qTp6227kuaZsv?all=1&plat=letv_box_tv";
        String url2 = "http://api.zhifu.cp21.ott.cibntv.net/querylepoint/244828745?origin=tv&auth=1eb941a710f1f23b181bbe4efbc4c3e4";
        String url3 = "http://i.api.cp21.ott.cibntv.net/geturl?mmsid=3254392&platid=5&splatid=501&version=2.0&playid=0&vtype=22,17&tss=tvts&clientip=113.87.22.116&bc=2";
        String url4 = "http://api.sso.cp21.ott.cibntv.net/api/getUserRoleList?uid=132913270";
        String url5 = "http://api.my.cp21.ott.cibntv.net/vcs/list?sso_tk=103671ff06wcbgRqdCvlRAm1DQXY3Lm159XJFgXDCu6s51K7NEm2m2o1ccYSzCLYGOm1WLDg4ngVQ&page=1&pagesize=60&rid=0&btime=0&lang=zh_cn&type=1&from=4";
        String url6 = "http://api.sso.cp21.ott.cibntv.net/api/checkTicket/tk/111";
        System.out.println(ApiSwitchStrategyUtils.getUrlTemp(url1));
        System.out.println(ApiSwitchStrategyUtils.getUrlTemp(url2));
        System.out.println(ApiSwitchStrategyUtils.getUrlTemp(url3));
        System.out.println(ApiSwitchStrategyUtils.getUrlTemp(url4));
        System.out.println(ApiSwitchStrategyUtils.getUrlTemp(url5));
        System.out.println(ApiSwitchStrategyUtils.getUrlTemp(url6));
    }
}

package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetPackageDataTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CmsTpDao extends BaseTpDao {

    private final static Logger log = LoggerFactory.getLogger(CmsTpDao.class);

    /**
     * 获取CMS板块数据
     * http://static.api.letv.com/blockNew/get?id={blockId}&lang=zh_cn&platform=
     * tv
     * @param blockUrl
     * @return
     */
    public CmsBlockTpResponse getCmsBlockNew(String blockUrl, CommonParam commonParam) {
        String logPrefix = "getCmsBlockNew_" + blockUrl + commonParam.getMac();
        CmsBlockTpResponse cmsBlockTpResponse = null;
        // if (StringUtils.isNotBlank(commonParam.getLangcode())) {
        // String langcode = commonParam.getLangcode();
        // if (langcode != null && "en_us".equals(langcode)) {
        // langcode = "en";
        // }
        // blockUrl += "&lang=" + langcode;
        // }
        blockUrl += "&lang=zh_cn";
        try {
            // http://static.api.letv.com/blockNew/get?id=1190&platform=tv
            String response = this.restTemplate.getForObject(blockUrl, String.class);
            if (StringUtil.isNotBlank(response)) {
                log.info(logPrefix + ": invoke return [length=" + response.length() + "]");
                CmsTpResponse<CmsBlockTpResponse> cmsTpResponse = objectMapper.readValue(response,
                        new TypeReference<CmsTpResponse<CmsBlockTpResponse>>() {
                        });
                if (cmsTpResponse != null && cmsTpResponse.getData() != null) {
                    cmsBlockTpResponse = cmsTpResponse.getData();
                } else {
                    log.info(logPrefix + " return data is " + response);
                }
            } else {
                log.info(logPrefix + ": invoke return null.");
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return cmsBlockTpResponse;
    }

    /**
     * 根据CMS板块id获取板块数据
     * http://static.api.letv.com/blockNew/get?id={blockId}&lang=zh_cn&platform=
     * tv
     * @param blockId
     * @param commonParam
     * @return
     */
    public CmsBlockTpResponse getCmsBlockNewById(String blockId, CommonParam commonParam) {
        String logPrefix = "getCmsBlockNewById_" + blockId;
        CmsBlockTpResponse cmsBlockTpResponse = null;
        try {
            if (StringUtils.isNotBlank(blockId)) {
                StringBuilder url = new StringBuilder(CmsTpConstant.GET_CMS_BLOCK_BY_ID_URL);
                if (commonParam != null
                        && commonParam.getTerminalApplication() != null
                        && TerminalCommonConstant.TERMINAL_APPLICATION_CMS_LE != null
                        && TerminalCommonConstant.TERMINAL_APPLICATION_CMS_LE.contains(commonParam
                                .getTerminalApplication())) {
                    url = new StringBuilder(CmsTpConstant.GET_CMS_LE_BLOCK_BY_ID_URL);
                }
                url.append("?id=").append(blockId);
                if (commonParam != null
                        && CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) {
                    if (commonParam != null && !StringUtil.isBlank(commonParam.getLangcode())) {
                        String langcode = commonParam.getLangcode();
                        if (langcode != null && "en_us".equals(langcode)) {
                            langcode = "en";
                        }
                        url.append("&lang=").append(langcode);
                    }
                } else {
                    url.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
                }
                url.append("&platform=tv");
                // http://static.api.letv.com/blockNew/get?id={id}&lang={lang}&platform=tv
                String response = this.restTemplate.getForObject(url.toString(), String.class);
                if (StringUtil.isNotBlank(response)) {
                    log.info(logPrefix + ": invoke return [length=" + response.length() + "]");
                    CmsTpResponse<CmsBlockTpResponse> cmsTpResponse = objectMapper.readValue(response,
                            new TypeReference<CmsTpResponse<CmsBlockTpResponse>>() {
                            });
                    if (cmsTpResponse != null && cmsTpResponse.getData() != null) {
                        cmsBlockTpResponse = cmsTpResponse.getData();
                    } else {
                        log.info(logPrefix + " return data is " + response);
                    }
                } else {
                    log.info(logPrefix + ": invoke return null.");
                }
            } else {
                log.info(logPrefix + ": blockid is null.");
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return cmsBlockTpResponse;
    }

    /**
     * 批量根据CMS板块id获取板块数据
     * http://static.api.letv.com/blockNew/get?id={blockId}&lang=zh_cn&platform=
     * tv
     * @param blockIds
     * @param commonParam
     * @return
     */
    public Map<String, CmsBlockTpResponse> getCmsBlockNewsById(String blockIds, CommonParam commonParam) {
        Map<String, CmsBlockTpResponse> ret = null;
        String logPrefix = "getCmsBlockNewsById_" + commonParam.getMac() + "_";
        final CmsTpDao cmsTpDao = this;
        HttpHandler httpHandler = new HttpHandler() {
            @Override
            public String[] buildHttpGetUrls(int total, int pageSize, Object obj) {
                return null;
            }

            @Override
            public String[] buildHttpGetUrls(String ids, Object obj) {
                String[] idArr = null, urls = null;
                if (StringUtil.isNotBlank(ids)) {
                    idArr = StringUtils.split(ids, ",");
                    urls = new String[idArr.length];
                    StringBuilder subUrl = new StringBuilder();
                    StringBuilder params = new StringBuilder();
                    CommonParam commonParam = null;
                    if (obj instanceof CommonParam) {
                        commonParam = (CommonParam) obj;
                    }
                    for (int i = 0, length = idArr.length; i < length; i++) {
                        subUrl.setLength(0);
                        params.setLength(0);
                        subUrl.append(CmsTpConstant.GET_CMS_BLOCK_BY_ID_URL).append("?");
                        params.append("id=").append(idArr[i]);
                        if (commonParam != null
                                && CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam
                                        .getTerminalApplication())) {
                            if (commonParam != null && !StringUtil.isBlank(commonParam.getLangcode())) {
                                String langcode = commonParam.getLangcode();
                                if (langcode != null && "en_us".equals(langcode)) {
                                    langcode = "en";
                                }
                                params.append("&lang=").append(langcode);
                            }
                        } else {
                            params.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
                        }
                        params.append("&platform=").append("tv");
                        subUrl.append(params);
                        urls[i] = subUrl.toString();
                    }
                }
                return urls;
            }

            @Override
            public CmsBlockTpResponse formatResponse(Object result) throws Exception {
                CmsBlockTpResponse ret = null;
                if (null != result) {
                    if (result instanceof String) {
                        CmsTpResponse<CmsBlockTpResponse> cmsTpResponse = objectMapper.readValue((String) result,
                                new TypeReference<CmsTpResponse<CmsBlockTpResponse>>() {
                                });
                        if (cmsTpResponse != null && cmsTpResponse.getData() != null) {
                            ret = cmsTpResponse.getData();
                        }
                    }
                }
                return ret;
            }

            @Override
            public void log(String log, Exception e) {
                cmsTpDao.log.info(log, e);
            }
        };
        ret = this.getHttpPackages(blockIds, commonParam, httpHandler, CmsBlockTpResponse.class, logPrefix);
        return ret;
    }

    public CmsBlockTpResponse getCmsBlockNewByIdV2(String blockId, CommonParam commonParam) {
        String logPrefix = "getCmsBlockNewById_" + blockId;
        CmsBlockTpResponse cmsBlockTpResponse = null;
        try {
            if (StringUtils.isNotBlank(blockId)) {
                StringBuilder url = new StringBuilder(CmsTpConstant.GET_CMS_BLOCK_BY_ID_URL);
                if (commonParam != null
                        && commonParam.getTerminalApplication() != null
                        && TerminalCommonConstant.TERMINAL_APPLICATION_CMS_LE != null
                        && TerminalCommonConstant.TERMINAL_APPLICATION_CMS_LE.contains(commonParam
                                .getTerminalApplication())) {
                    url = new StringBuilder(CmsTpConstant.GET_CMS_LE_BLOCK_BY_ID_URL);
                }
                url.append("?id=").append(blockId);
                if (commonParam != null
                        && CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) {
                    if (commonParam != null && !StringUtil.isBlank(commonParam.getLangcode())) {
                        String langcode = commonParam.getLangcode();
                        if (langcode != null && "en_us".equals(langcode)) {
                            langcode = "en";
                        }
                        url.append("&lang=").append(langcode);
                    }
                } else {
                    url.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
                }
                url.append("&platform=tv");
                // http://static.api.letv.com/blockNew/get?id={id}&lang={lang}&platform=tv
                String response = this.restTemplate.getForObject(url.toString(), String.class);
                if (StringUtil.isNotBlank(response)) {
                    log.info(logPrefix + ": invoke return [length=" + response.length() + "]");
                    CmsTpResponse<CmsBlockTpResponse> cmsTpResponse = objectMapper.readValue(response,
                            new TypeReference<CmsTpResponse<CmsBlockTpResponse>>() {
                            });
                    if (cmsTpResponse != null && cmsTpResponse.getData() != null) {
                        cmsBlockTpResponse = cmsTpResponse.getData();
                    } else if (cmsTpResponse != null) {
                        cmsBlockTpResponse = new CmsBlockTpResponse();
                    } else {
                        log.info(logPrefix + " return data is " + response);
                    }
                } else {
                    log.info(logPrefix + ": invoke return null.");
                }
            } else {
                log.info(logPrefix + ": blockid is null.");
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return cmsBlockTpResponse;
    }

    /**
     * CMS页面
     * @param pageId
     * @return
     */
    public CmsPageTpResponse getCmsPage(String pageId, CommonParam commonParam) {
        String logPrefix = "getCmsPage_" + pageId;
        CmsPageTpResponse cmsPageTpResponse = null;
        try {
            String langCode = commonParam.getLangcode();
            if (langCode != null) {
                if (langCode.equalsIgnoreCase("en_us") || langCode.equalsIgnoreCase("en-us")) {
                    langCode = "en";
                }
            } else {
                langCode = "zh_cn";
            }
            StringBuilder url = new StringBuilder(CmsTpConstant.GET_CMS_PAGE_BY_ID_URL);
            if (commonParam != null
                    && commonParam.getTerminalApplication() != null
                    && TerminalCommonConstant.TERMINAL_APPLICATION_CMS_LE != null
                    && TerminalCommonConstant.TERMINAL_APPLICATION_CMS_LE
                            .contains(commonParam.getTerminalApplication())) {
                url = new StringBuilder(CmsTpConstant.GET_CMS_LE_PAGE_BY_ID_URL);
            }
            if (commonParam != null && commonParam.getLangcode() != null) {
                url.append("?lang=").append(langCode);
            }
            String response = this.restTemplate.getForObject(url.toString(), String.class, pageId);
            if (StringUtil.isNotBlank(response)) {
                log.info(logPrefix + ": invoke return [length=" + response.length() + "]");
                // response = new String(response.getBytes("ISO-8859-1"));
                cmsPageTpResponse = objectMapper.readValue(response, CmsPageTpResponse.class);
            } else {
                log.info(logPrefix + ": invoke return null.");
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return cmsPageTpResponse;
    }

    /**
     * 根据专题id获取专题内容
     * http://static.api.letv.com/cms/tj/getTjN?zid={subjectId}&lang={langcode}&
     * platform=tv
     * @param subjectId
     * @return
     */
    public GetSubjectTpResponse getCmsSubjectById(String subjectId, CommonParam commonParam) {
        GetSubjectTpResponse response = null;
        String logPrefix = "getCmsSubjectById_" + subjectId + "_" + commonParam.getMac();
        try {
            StringBuilder url = new StringBuilder(CmsTpConstant.GET_CMS_SUBJECT_BY_ID_URL);
            if (StringUtils.isNotBlank(subjectId)) {
                url.append("?zid=").append(subjectId);
            }
            // if (commonParam != null &&
            // StringUtils.isNotBlank(commonParam.getLangcode())) {
            // String langcode = commonParam.getLangcode();
            // if (langcode != null && "en_us".equals(langcode)) {
            // langcode = "en";
            // }
            // url.append("&lang=").append(langcode);
            // }
            url.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
            url.append("&platform=tv");
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                log.debug(logPrefix + ": invoke return [" + result.toString() + "]");
                response = objectMapper.readValue(result, GetSubjectTpResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 根据包Id获取专题包数据
     * http://static.api.letv.com/cms/tj/getPackageN?pkgid={packageId}
     * @param packageId
     * @return
     */
    public GetPackageDataTpResponse getSubjectPackageData(String packageId, CommonParam commonParam) {
        GetPackageDataTpResponse response = null;
        String logPrefix = "getSubjectPackageData_" + packageId + "_" + commonParam.getMac();
        try {
            StringBuilder url = new StringBuilder(CmsTpConstant.GET_CMS_PACKAGE_BY_ID_URL);
            if (StringUtils.isNotBlank(packageId)) {
                url.append("?pkgid=").append(packageId);
            }
            // if (commonParam != null &&
            // StringUtils.isNotBlank(commonParam.getLangcode())) {
            // String langcode = commonParam.getLangcode();
            // if (langcode != null && "en_us".equals(langcode)) {
            // langcode = "en";
            // }
            // url.append("&lang=").append(langcode);
            // }
            url.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
            url.append("&platform=tv");
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                log.info(logPrefix + ": invoke return [length=" + result.length() + "]");
                response = objectMapper.readValue(result, GetPackageDataTpResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: " + e.getMessage(), e);
        }
        return response;
    }

    /**
     * 批量根据包Id获取专题包数据
     * http://static.api.letv.com/cms/tj/getPackageN?pkgid={packageId}
     * @param packageIds
     * @return
     */
    public Map<String, GetPackageDataTpResponse> getSubjectPackagesData(String packageIds, CommonParam commonParam) {
        Map<String, GetPackageDataTpResponse> ret = null;
        GetPackageDataTpResponse response = null;
        String logPrefix = "getSubjectPackagesData_" + packageIds + "_" + commonParam.getMac();
        if (StringUtil.isBlank(packageIds)) {
            return ret;
        }
        ;
        try {
            String[] packageIdArr = StringUtils.split(packageIds, ",");
            if (null == packageIdArr || packageIdArr.length == 0) {
                return ret;
            }
            String[] urls = this.buildUrl4ReqSubjectPackages(packageIds, commonParam);
            Map<String, String> results = this.restTemplate.multiHttpRequests(urls, "GET", null, null);
            if (null != results && results.size() > 0) {
                String result = null;
                int index = 0;
                ret = new HashMap<String, GetPackageDataTpResponse>();
                for (String url : urls) {
                    result = results.get(url);
                    if (StringUtil.isNotBlank(result)) {
                        response = objectMapper.readValue(result, GetPackageDataTpResponse.class);
                        if (null != response && null != response.getData()) {
                            ret.put(packageIdArr[index++], response);
                        } else {
                            index++;
                        }
                    } else {
                        index++;
                    }
                    logPrefix = "getSubjectPackagesData_" + packageIdArr[index - 1] + "_" + commonParam.getMac();
                    log.info(logPrefix + ": invoke return [" + result + "]");
                }
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: " + e.getMessage(), e);
        }
        return ret;
    }

    private String[] buildUrl4ReqSubjectPackages(String packageIds, CommonParam commonParam) {
        String[] packageIdArr = null, urls = null;
        if (StringUtil.isNotBlank(packageIds)) {
            packageIdArr = StringUtils.split(packageIds, ",");
            urls = new String[packageIdArr.length];
            StringBuilder subUrl = new StringBuilder();
            StringBuilder params = new StringBuilder();
            for (int i = 0, length = packageIdArr.length; i < length; i++) {
                subUrl.setLength(0);
                params.setLength(0);
                subUrl.append(CmsTpConstant.GET_CMS_PACKAGE_BY_ID_URL).append("?");
                params.append("pkgid=").append(packageIdArr[i]);
                params.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
                params.append("&platform=").append("tv");
                subUrl.append(params);
                urls[i] = subUrl.toString();
            }
        }
        return urls;
    }

    /**
     * 根据样式Id获取输出样式数据
     * http://static.api.letv.com/cms/outputStyle/getById?id=99
     * @param packageId
     * @return
     */
    public CmsStyleTpResponse getContentStyleData(String styleId, CommonParam commonParam) {
        CmsStyleTpResponse response = null;
        String logPrefix = "getContentStyleData" + styleId + "_" + commonParam.getMac();
        try {
            StringBuilder url = new StringBuilder(CmsTpConstant.GET_CMS_CONTENTSTYLE_BY_ID_URL);
            if (StringUtils.isNotBlank(styleId)) {
                url.append("?id=").append(styleId);
            }
            url.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
            url.append("&platform=tv");
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                log.info(logPrefix + ": invoke return [length=" + result.length() + "]");
                response = objectMapper.readValue(result, new TypeReference<CmsTpResponse<CmsStyleTpResponse>>() {
                });
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: " + e.getMessage(), e);
        }
        return response;
    }

    /**
     * 根据样式Id获取输出样式数据
     * http://static.api.letv.com/cms/outputStyle/getById?id=99
     * @param packageId
     * @return
     */
    public CmsCourseTpResponse getCmsCourseData(String bdate, String edate, Integer age, CommonParam commonParam) {
        CmsCourseTpResponse response = null;
        String logPrefix = "getCmsCourseData_age:" + age + " bdate:" + bdate + " edate:" + edate + "_"
                + commonParam.getMac();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("bdate", bdate);
            params.put("edate", edate);
            params.put("age", age);
            String result = this.restTemplate.getForObject(CmsTpConstant.GET_CMS_COURSE_BY_DATE_GENDER_URL,
                    String.class, params);
            if (StringUtil.isNotBlank(result)) {
                log.info(logPrefix + ": invoke return [length=" + result.length() + "]");
                response = objectMapper.readValue(result, CmsCourseTpResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: " + e.getMessage(), e);
        }
        return response;
    }

    /**
     * 获取限免活动pids
     */
    public String getFreeTimeLimitPids(CommonParam commonParam) {
        String logPrefix = "getFreeTimeLimitPids";
        try {
            ResponseEntity<String> result = this.restTemplate.getForEntity(
                    ApplicationConstants.CMS_XIANMIAN_TV_HOST_URL, String.class, new Object[0]);
            if (result != null && result.getBody() != null) {
                this.log.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    JsonNode root = objectMapper.readTree(result.getBody());
                    JsonNode data = root.get("data");
                    JsonNode contents = data.get("blockContent");
                    JsonNode shorDesc = contents.get(0).get("shorDesc");
                    return shorDesc.asText();
                }
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " return error: ", e);
        }
        return null;
    }
}

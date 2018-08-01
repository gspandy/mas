package com.letv.mas.router;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceZuulApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceZuulApplicationTests.class);

    private static final String ENDPOINT_SERVICE_ID = "letv-mas-client";
    private static final String ROUTER_TRACE_TAG_HEADER = "X-Application-Context";
    private static final Pattern ROUTER_TRACE_TAG_HEADER_PATTERN = Pattern.compile("(.+):(.+):(.+):(.+):(.+):(.+)");
    private static final String ENDPOINT_INFO_URL = "http://cgi.mas.letv.cn/letv-mas-client/router/endpoint/info";
    private static final String ENDPOINT_FAULT_URL_FORMAT = "http://cgi.mas.letv.cn/letv-mas-client/router/endpoint/fault?p=%s";
    private static final String SINGLE_ENDPOINT_FAULT_URL_FORMAT = "http://10.124.65.234:8901/router/endpoint/fault?p=%s";
    private static final String FIX_SERVICE_INSTANCE_WEIGHT_URL_FORMAT = "http://10.112.34.124:8015/eureka/apps/%s/%s/metadata?weight=%s";
    private static final int SAMPLE_AMOUNT = 2000;
    private static final int DEFAULT_ENDPOINT_WEIGHT = 1;
    private static final int FIX_ENDPOINT_WEIGHT = 3;
    private static final OkHttpClient mClient = new OkHttpClient.Builder().build();


    @Test
    public void routeRuleZoneAvoidanceTest() throws IOException {
        Request request = new Request.Builder().get().url(ENDPOINT_INFO_URL).build();
        Response response = mClient.newCall(request).execute();
        String routerTagHeader = response.header(ROUTER_TRACE_TAG_HEADER);
        String responseBodyString = new String(response.body().bytes(), "UTF-8");
        LOG.info("ROUTER-HEADER-TAG={}", routerTagHeader);
        LOG.info("CLIENT-RESPONSE-INFO={}", responseBodyString);
        Endpoint endpoint = JSON.parseObject(responseBodyString, Endpoint.class);
        Matcher matcher = ROUTER_TRACE_TAG_HEADER_PATTERN.matcher(routerTagHeader);
        Assert.assertTrue(matcher.find());
        String routerRegion = matcher.group(3);
        String routerZone = matcher.group(4);
        Assert.assertTrue(routerRegion.equalsIgnoreCase(endpoint.getRegion()));
        Assert.assertTrue(routerZone.equalsIgnoreCase(endpoint.getZone()));
    }

    @Test
    public void endpointFaultTolerantTest() throws IOException {
        int singleHealthyCnt = 0;
        int singleFaultCnt = 0;
        final int faultProbability = 10;
        Request singleRequest = new Request.Builder().post(RequestBody.create(MediaType.parse("application/json"), "")).url(String.format(SINGLE_ENDPOINT_FAULT_URL_FORMAT, faultProbability)).build();
        for (int i = 0; i < SAMPLE_AMOUNT; i++) {
            int rspCode = mClient.newCall(singleRequest).execute().code();
            switch (rspCode) {
                case 200:
                    singleHealthyCnt++;
                    break;
                case 500:
                    singleFaultCnt++;
                    break;
            }
        }
        float singleFaultPercentage = ((float) singleFaultCnt) / SAMPLE_AMOUNT;
        int expectSingleFaultPercentage = Math.round(singleFaultPercentage * 100);
        LOG.info("SINGLE-ENDPOINT-TEST, HEALTHY-CNT={}, FAULT-CNT={}, FAULT-PERCENTAGE={}, ", singleHealthyCnt, singleFaultCnt, singleFaultPercentage);
        Assert.assertEquals(faultProbability, expectSingleFaultPercentage, 2);

        Integer routerHealthyCnt = 0;
        Integer routerFaultCnt = 0;
        Request routerRequest = new Request.Builder().post(RequestBody.create(MediaType.parse("application/json"), "")).url(String.format(ENDPOINT_FAULT_URL_FORMAT, faultProbability)).build();
        for (int i = 0; i < SAMPLE_AMOUNT; i++) {
            int rspCode = mClient.newCall(routerRequest).execute().code();
            switch (rspCode) {
                case 200:
                    routerHealthyCnt++;
                    break;
                case 500:
                    routerFaultCnt++;
                    break;
            }
        }
        float routerFaultPercentage = ((float) routerFaultCnt) / SAMPLE_AMOUNT;
        LOG.info("ROUTER-ENDPOINT-TEST, HEALTHY-CNT={}, FAULT-CNT={}, FAULT-PERCENTAGE={}, ", routerHealthyCnt, routerFaultCnt, routerFaultPercentage);
        Assert.assertEquals(0F, routerFaultPercentage, 0.02F);
    }

    @Test
    public void endpointTimeoutTolerantTest() {

    }

    @Test
    public void distributionControlTest() throws IOException, InterruptedException {
        fixServiceInstanceWeight(ENDPOINT_SERVICE_ID, "10.124.65.236:8902", DEFAULT_ENDPOINT_WEIGHT);
        fixServiceInstanceWeight(ENDPOINT_SERVICE_ID, "10.124.65.236:8901", DEFAULT_ENDPOINT_WEIGHT);
        fixServiceInstanceWeight(ENDPOINT_SERVICE_ID, "10.124.65.234:8902", DEFAULT_ENDPOINT_WEIGHT);
        fixServiceInstanceWeight(ENDPOINT_SERVICE_ID, "10.124.65.234:8901", DEFAULT_ENDPOINT_WEIGHT);
        Map<Endpoint, Integer> cnts = new HashMap<>();
        collectDistributionMap(SAMPLE_AMOUNT, cnts);
        LOG.info("DEFAULT-DISTRIBUTION={}", JSON.toJSONString(cnts));
        final float distributePercentage = 0.25F;
        for (Endpoint endpoint : cnts.keySet()) {
            float current = (float) cnts.get(endpoint) / SAMPLE_AMOUNT;
            Assert.assertEquals(0.25F, current, 0.02F);
        }
        cnts.clear();
        fixServiceInstanceWeight(ENDPOINT_SERVICE_ID, "10.124.65.236:8902", FIX_ENDPOINT_WEIGHT);
        // wait for eureka cluster to sync weight info
        Thread.sleep(2000L);
        collectDistributionMap(SAMPLE_AMOUNT, cnts);
        LOG.info("FIXED-DISTRIBUTION={}", JSON.toJSONString(cnts));
        Integer cnt = cnts.get(new Endpoint("10.124.65.236:8902", "cn-bj", "sjs-1"));
        Assert.assertEquals(0.75F, (float) cnt / (SAMPLE_AMOUNT / 2), 0.02F);
    }

    static void collectDistributionMap(int sampleNum, Map<Endpoint, Integer> cnts) throws IOException {
        for (int i = 0; i < sampleNum; i++) {
            Response response = mClient.newCall(new Request.Builder().get().url(ENDPOINT_INFO_URL).build()).execute();
            Endpoint endpoint = JSON.parseObject(response.body().bytes(), Endpoint.class);
            Integer cnt = cnts.get(endpoint);
            if (cnt == null) {
                cnt = 1;
            } else {
                cnt++;
            }
            cnts.put(endpoint, cnt);
        }
    }


    static void fixServiceInstanceWeight(String serviceId, String instance, int weight) throws IOException {
        mClient.newCall(new Request.Builder().url(String.format(FIX_SERVICE_INSTANCE_WEIGHT_URL_FORMAT, serviceId, instance, weight)).put(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), "")).build()).execute();
    }

    static class Endpoint implements Serializable {

        private String instanceId;
        private String region;
        private String zone;

        public Endpoint() {
        }

        public Endpoint(String instanceId, String region, String zone) {
            this.instanceId = instanceId;
            this.region = region;
            this.zone = zone;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Endpoint endpoint = (Endpoint) o;

            return instanceId.equals(endpoint.instanceId);
        }

        @Override
        public int hashCode() {
            return instanceId.hashCode();
        }
    }


}

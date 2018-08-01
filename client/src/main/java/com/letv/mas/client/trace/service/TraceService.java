package com.letv.mas.client.trace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.ContinueSpan;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by wangsk on 2018/7/5.
 */
@Service
//@ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "true", matchIfMissing = false)
public class TraceService {
    private final Logger log = LoggerFactory.getLogger(TraceService.class);
    /**
     * 定义私有化的resttemplate,供本类测试使用
     */
    private RestTemplate rt;
    {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory  = new SimpleClientHttpRequestFactory ();
        simpleClientHttpRequestFactory.setConnectTimeout(1000);
        simpleClientHttpRequestFactory.setReadTimeout(1000);
        rt= new RestTemplate(simpleClientHttpRequestFactory);
    }
    public Map<Object,Object> getTerminalConfig(){
        Map result =rt.getForObject("http://api-oeco-itv.cp21.ott.cibntv.net/iptv/api/new/terminal/config.json?salesArea=CN&countryArea=&terminalApplication=media_cibn&versionCode=2018040412&terminalUi=&bsChannel=cibn&versionName=2.0.0&ts=1526228131202&temrinalUiVersion=&terminalBrand=media&terminalSeries=Letv+X3-49&broadcastId=2&devId=B01BD219ADF9&model=1",Map.class);
        return  result;
    }
    @Autowired
    private RestTemplate restTemplate;

    /**
     * @NewSpan  创建span
     * @SpanTag 参数中,可选加入span tag中
     * @param name
     * @return
     */
    @NewSpan
    @ContinueSpan(log="createSpan")
    public boolean   createSpan(@SpanTag(key="name") String name){
        if(tracer!=null) {
            tracer.getCurrentSpan().logEvent("createSpan start");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tracer.getCurrentSpan().logEvent("createSpan end");
        }
        return  true;
    }
    /**
     * @ContinueSpan  共享span,log参数为logevent
     * @SpanTag 参数中,可选加入span tag中
     * @param name
     * @return
     */
    @ContinueSpan(log="continueSpan")
    public boolean continueSpan(@SpanTag(key="name") String name){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  true;
    }
    @Autowired
    Tracer tracer;
    /**
     * <a href="https://github.com/opentracing/opentracing-go/blob/master/ext/tags.go">As
     * in Open Tracing</a>
     */
    public boolean   createSpanWithCode( String name){
        if(tracer!=null) {
            Span span = tracer.createSpan("redisService");

            try {
                span.logEvent("redisService start");
                span.tag("name", name);
                Thread.sleep(200);
                span.tag("peer.service", "redisService");
                span.tag("peer.ipv4", "127.0.0.1");
                span.tag("peer.port", "6379");
                span.tag("method", "get");
                span.logEvent("redisService end");
            } catch (Exception e) {
                return false;
            } finally {
                if (span != null) {
                    tracer.close(span);
                }
            }
        }
        return  true;
    }
    public boolean continueSpanWithCode(String name){
        if(tracer!=null) {
            Span span = tracer.getCurrentSpan();
            span = tracer.continueSpan(span);
            try {
                span.logEvent("continueSpanWithCode start");
                span.tag("name", name);
                Thread.sleep(200);
                span.logEvent("continueSpanWithCode end");
            } catch (Exception e) {
                return false;
            } finally {

            }
        }
        return  true;
    }
    public Map  chain(){
        if(tracer!=null) {
            Span span = tracer.createSpan("chain service");

            try {
                span.logEvent("chain start");
                Map result = chain1(span);
                span.tag("peer.service", "chainservice");
                span.tag("peer.ipv4", "127.0.0.1");
                span.tag("peer.port", "6379");
                span.tag("method", "chain");
                span.logEvent("chain end");
                return result;
            } catch (Exception e) {
            } finally {
                if (span != null) {
                    tracer.close(span);
                }
            }
        }
        return null;
    }
    public Map chain1(Span s){
        if(tracer!=null) {
            Span span = tracer.createSpan("chain1 service", s);

            try {
                span.logEvent("chain1 start");
                Map result = chain2();
                span.tag("peer.service", "chain1service");
                span.tag("peer.ipv4", "127.0.0.2");
                span.tag("peer.port", "63791");
                span.tag("method", "chain1");
                span.logEvent("chain1 end");
                return result;
            } catch (Exception e) {
            } finally {
                if (span != null) {
                    tracer.close(span);
                }
            }
        }
        return  null;
    }
    public Map chain2(){
        if(tracer!=null) {
            Span span = tracer.createSpan("chain2 service");

            try {
                span.logEvent("chain2 start");
                Map result = chain3();
                span.tag("peer.service", "chain2service");
                span.tag("peer.ipv4", "127.0.0.1");
                span.tag("peer.port", "6379");
                span.tag("method", "chain2");

                span.logEvent("chain2 end");
            } catch (Exception e) {
            } finally {
                if (span != null) {
                    tracer.close(span);
                }
            }
            Span span1 = tracer.createSpan("chain21 service");

            try {
                span1.logEvent("chain21 start");
                Map result = chain3();
                span1.tag("peer.service", "chain21service");
                span1.tag("peer.ipv4", "127.0.0.1");
                span1.tag("peer.port", "6379");
                span1.tag("method", "chain21");
                span1.logEvent("chain21 end");
                return result;
            } catch (Exception e) {
            } finally {
                if (span1 != null) {
                    tracer.close(span1);
                }
            }
        }
        return  null;
    }
    public Map chain3(){
        if(tracer!=null) {
            Span span = tracer.createSpan("chain3 service");

            try {
                span.logEvent("chain3 start");
                Map result = chain4();
                span.tag("peer.service", "chain3service");
                span.tag("peer.ipv4", "127.0.0.1");
                span.tag("peer.port", "6379");
                span.tag("method", "chain3");
                span.logEvent("chain3 end");
                return result;
            } catch (Exception e) {
            } finally {
                if (span != null) {
                    tracer.close(span);
                }
            }
        }
        return  null;
    }
    public Map chain4(){
        if(tracer!=null) {
            Span span = tracer.createSpan("chain4 service");

            try {
                span.logEvent("chain4 start");
                Map result = chain5();
                span.tag("peer.service", "chain4service");
                span.tag("peer.ipv4", "127.0.0.1");
                span.tag("peer.port", "6379");
                span.tag("method", "chain4");
                span.logEvent("chain4 end");
                return result;
            } catch (Exception e) {
            } finally {
                if (span != null) {
                    tracer.close(span);
                }
            }
        }
        return  null;
    }
    public Map chain5(){
        Map result = getTerminalConfig();
        return result;
    }
    public String testDepth(Integer depth){
        if(depth<=0) {
            return "true";
        }
        log.info("depth:"+depth);
        String result = restTemplate.getForObject("http://letv-mas-client-trace/trace/custom/invokeDepth?depth="+(--depth),String.class);
        return result;
    }
    public String missTrace(String name,Integer depth){
        if(name==null||"".equals(name)){
            name="default";
        }
        tracer.addTag("tag_name",name);
        if(depth<=0) {
            return "true";
        }
        log.info("depth:"+depth+",name="+name);
        String result = restTemplate.getForObject("http://letv-mas-client-trace/trace/custom/miss/trace?depth="+(--depth)+"&name="+name,String.class);
        return result;
    }
}

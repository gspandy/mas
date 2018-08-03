package com.letv.mas.client.trace.controller;

import com.letv.mas.client.trace.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/trace")
@ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "true", matchIfMissing = false)
public class TraceController {
    @Autowired
    private TraceService traceService;

    /**
     * Hello World 接口
     * @param name  zipkin 中供查询使用Tag
     * @return
     */
    @RequestMapping("/helloworld")
    public String hello(@RequestParam String name) {
        return "hi welcome!";
    }

    /**
     * 测试调用springcloud外http
     * @return
     */
    @RequestMapping("/terminalconfig")
    public Map config() {
        return traceService.getTerminalConfig();
    }

    /**
     * 测试注解方式创建tag
     * @param name  zipkin 中供查询使用Tag
     * @return
     */
    @RequestMapping("/custom/createSpan")
    public boolean createSpan(@RequestParam String name) {
        return traceService.createSpan(name);
    }

    /**
     * 测试注解方式,重用Span
     * @param name  zipkin 中供查询使用Tag
     * @return
     */
    @RequestMapping("/custom/continueSpan")
    public boolean continueSpan(@RequestParam String name) {
        return traceService.continueSpan(name);
    }

    /**
     * 测试采用code方式创建span
     * @param name  zipkin 中供查询使用Tag
     * @return
     */
    @RequestMapping("/custom/createSpan1")
    public boolean createSpanWithCode(@RequestParam String name) {
        return traceService.createSpanWithCode(name);
    }
    /**
     * 测试采用code方式共享使用span
     * @param name  zipkin 中供查询使用Tag
     * @return
     */
    @RequestMapping("/custom/continueSpan1")
    public boolean continueSpanWithCode(@RequestParam String name) {
        return traceService.continueSpanWithCode(name);
    }

    /**
     * 自定义调用单元测试
     * @return
     */
    @RequestMapping("/invoke/chains")
    public Map continueSpanWithCode() {
        return traceService.chain();
    }

    /**
     * 测试调用链深度测试
     * @param depth  调用链深度
     * @return
     */
    @RequestMapping("/custom/invokeDepth")
    public String invokeDepth(@RequestParam Integer depth) {
        String b =traceService.testDepth(depth);
        return b;
    }

    /**
     * 测试丢消息
     * @param name 测试标记
     * @param depth  深度
     * @return
     */
    @RequestMapping("/custom/miss/trace")
    public String missTrace(@RequestParam String name,@RequestParam Integer depth) {
        String b =traceService.missTrace(name,depth);
        return b;
    }
}

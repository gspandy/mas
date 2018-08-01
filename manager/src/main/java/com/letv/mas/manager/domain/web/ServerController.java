package com.letv.mas.manager.domain.web;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
public class ServerController {

    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

    @Value("${spring.cloud.config.uri}")
    private String configUrl = "";

    @Value("${spring.cloud.config.username}")
    private String configUserName = "";

    @Value("${spring.cloud.config.password}")
    private String configPassWord = "";

    @Autowired
    OkHttpClient okHttpClient;

    //"http://config:config2018@config.mas.letv.cn/bus/refresh?destination=letv-mas-manager:prod:10.129.29.90"
    private String configURL = null;

    @RequestMapping(value = "/busRefresh", method = RequestMethod.POST)
    public boolean refresh(@RequestParam(value = "app", required = true) String app, @RequestParam(value = "profiles", required = false) String profiles, @RequestParam(value = "index", required = false) String index) {
        if (configURL == null) {
            configURL = configUrl.replace("//", "//" + configUserName + ":" + configPassWord + "@");
        }
        String destination = app.toLowerCase() +":**";
        if (profiles != null && index != null) {
            destination = app.toLowerCase() + ":" + profiles + ":" + index;
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String url = configURL + "bus/refresh?destination=" + destination;
        logger.info(" busRefresh --- "+url);
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                return true;
            }
        } catch (Exception e) {
            logger.error(" busRefresh error ---- ", e.fillInStackTrace());
        }
        return false;
    }

    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    public boolean pause(@RequestParam(value = "app", required = true) String app, @RequestParam(value = "id", required = false) String id) {
        return getServerContext().getRegistry().statusUpdate(app,id,InstanceInfo.InstanceStatus.valueOf("OUT_OF_SERVICE"),null,false);
    }

    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    public boolean resume(@RequestParam(value = "app", required = true) String app, @RequestParam(value = "id", required = false) String id) {
        return getServerContext().getRegistry().deleteStatusOverride(app,id,InstanceInfo.InstanceStatus.UP,null,false);
    }

    private EurekaServerContext getServerContext() {
        return EurekaServerContextHolder.getInstance().getServerContext();
    }

}

package com.letv.mas.manager;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaServerStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by maning on 2018/6/20.
 */
@Component
public class EurekaStateChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(EurekaStateChangeListener.class);

    @EventListener
    public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        //服务断线事件
        String appName = eurekaInstanceCanceledEvent.getAppName();
        String serverId = eurekaInstanceCanceledEvent.getServerId();
        logger.info("cancel .... " + appName + " " + serverId);
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        if (instanceInfo != null) {
            logger.info("register ...." + instanceInfo.getInstanceId());
        } else {
            logger.info("register ....instanceInfo is null");
        }
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        if (instanceInfo != null) {
            logger.info("renew ...." + instanceInfo.getInstanceId());
        } else {
            logger.info("renew ....instanceInfo is null");
        }

    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        logger.info(" Registry Available ");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        //Server启动
        logger.info(" Server start ");
    }
}

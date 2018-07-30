package com.letv.mas.common.bus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

/**
 * 默认自定义bus事件监听器
 *
 * @author dalvikchen
 * @date 18/5/28
 */
public class CustomBusListener {

    private ApplicationContext context;

    public CustomBusListener(ApplicationContext context) {
        this.context = context;
    }

    @EventListener
    private void pubListener(CustomApplicationEvent event) {
        System.out.println("Default Bus Listener Received Message:" + event.getMessage() +
                " Application ID:" + context.getId());
    }
}

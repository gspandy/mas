package com.letv.mas.common.bus;

import org.springframework.cloud.bus.endpoint.AbstractBusEndpoint;
import org.springframework.cloud.bus.endpoint.BusEndpoint;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dalvikchen
 * @date 18/5/28
 */
@ManagedResource
public class CustomBusEndpoint extends AbstractBusEndpoint {

    public CustomBusEndpoint(ApplicationEventPublisher context, String id,
                             BusEndpoint delegate) {
        super(context, id, delegate);
    }

    @RequestMapping(value = "/pub", method = RequestMethod.POST)
    @ResponseBody
    @ManagedOperation
    public void refresh(
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "msg") String msg) {
        publish(new CustomApplicationEvent(this, getInstanceId(), destination, msg));
    }

}


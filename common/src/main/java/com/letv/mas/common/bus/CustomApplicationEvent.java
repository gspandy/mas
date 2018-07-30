package com.letv.mas.common.bus;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author dalvikchen
 * @date 18/5/28
 */
public class CustomApplicationEvent extends RemoteApplicationEvent {

    private String message;

    private CustomApplicationEvent() {
    }

    public CustomApplicationEvent(Object source, String originService,
                                  String destinationService, String message) {
        super(source, originService, destinationService);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public CustomApplicationEvent setMessage(String message) {
        this.message = message;
        return this;
    }
}

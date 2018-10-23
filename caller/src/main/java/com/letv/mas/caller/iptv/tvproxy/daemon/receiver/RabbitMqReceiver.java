package com.letv.mas.caller.iptv.tvproxy.daemon.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${rabbitmq.vip.mq.queue_name}")
public class RabbitMqReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("-----------------Receiver  message : " + message);
    }

}

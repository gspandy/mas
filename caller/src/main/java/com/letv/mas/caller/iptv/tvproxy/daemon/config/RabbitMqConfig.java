package com.letv.mas.caller.iptv.tvproxy.daemon.config;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private static final Logger logger = Logger.getLogger(RabbitMqConfig.class);

    @Bean
    public RabbitMqBeanConfig getRabbitMqBeanConfig(){
        return new RabbitMqBeanConfig();
    }

    @Bean
    public ConnectionFactory connectionFactory(RabbitMqBeanConfig config) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(config.getHost(),config.getPort());

        connectionFactory.setUsername(config.getUsername());
        connectionFactory.setPassword(config.getPassword());
        connectionFactory.setVirtualHost(config.getVhost());
        connectionFactory.setPublisherConfirms(true);

        return connectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,RabbitMqBeanConfig config) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(config.getExchange_name());
        return template;
    }

    @Bean
    public FanoutExchange fanoutExchange(RabbitMqBeanConfig config) {
        return new FanoutExchange(config.getFanout_exchange_name());
    }

    @Bean
    public Queue queue(RabbitMqBeanConfig config) {
        return new Queue(config.getQueue_name(), true,false,false);
    }

    @Bean
    public Binding binding(RabbitMqBeanConfig config) {
        return BindingBuilder.bind(queue(config)).to(fanoutExchange(config));
    }

    /*@Bean
    public SimpleMessageListenerContainer messageContainer(RabbitMqBeanConfig config) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory(config));
        container.setQueues(queue(config));
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                byte[] body = message.getBody();
                logger.info("消费端接收到消息 : " + new String(body));
                if(message.getMessageProperties().getHeaders().get("error") == null){
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                    System.out.println("消息已经确认");
                }else {
                    //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
                    System.out.println("消息拒绝");
                }
            }
        });
        return container;
    }*/
}

package com.usabana.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String INVENTORY_EXCHANGE = "inventory.exchange";

    public static final String NOTIFICATION_RESERVED_QUEUE = "notification.reserved.queue";
    public static final String NOTIFICATION_REJECTED_QUEUE = "notification.rejected.queue";

    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(INVENTORY_EXCHANGE);
    }

    @Bean
    public Queue notificationReservedQueue() {
        return new Queue(NOTIFICATION_RESERVED_QUEUE);
    }

    @Bean
    public Queue notificationRejectedQueue() {
        return new Queue(NOTIFICATION_REJECTED_QUEUE);
    }

    @Bean
    public Binding notificationReservedBinding(Queue notificationReservedQueue, TopicExchange inventoryExchange) {
        return BindingBuilder.bind(notificationReservedQueue).to(inventoryExchange).with("inventory.reserved");
    }

    @Bean
    public Binding notificationRejectedBinding(Queue notificationRejectedQueue, TopicExchange inventoryExchange) {
        return BindingBuilder.bind(notificationRejectedQueue).to(inventoryExchange).with("inventory.rejected");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
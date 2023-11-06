package org.example.chat;


import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class RabbitMQConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue queueSendMessage() {
        return new Queue("chat-queue-send-message");
    }
    @Bean
    public Queue queueSendPhoto() {return new Queue("chat-queue-send-photo");}
    @Bean
    public Queue queueAddUser() {return new Queue("chat-queue-add-user");}
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("common-exchange");
    }
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queueSendMessage()).to(fanoutExchange());
    }
    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queueSendPhoto()).to(fanoutExchange());
    }
    @Bean
    public Binding binding3() {
        return BindingBuilder.bind(queueAddUser()).to(fanoutExchange());
    }

}
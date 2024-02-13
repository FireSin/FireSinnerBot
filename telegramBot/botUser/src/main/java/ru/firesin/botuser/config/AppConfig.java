package ru.firesin.botuser.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.firesin.rabbitMq.Queue.RabbitQueue.*;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Configuration
public class AppConfig {

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue weatherMessageQueue() {
        return new Queue(WEATHER_MESSAGE_UPDATE);
    }

    @Bean
    public Queue chatMessageQueue() {
        return new Queue(CHAT_MESSAGE_UPDATE);
    }

    @Bean
    public Queue answerWeatherMessageQueue() {
        return new Queue(WEATHER_MESSAGE_ANSWER);
    }
    @Bean
    public Queue answerChatMessageQueue() {
        return new Queue(CHAT_MESSAGE_ANSWER);
    }
}

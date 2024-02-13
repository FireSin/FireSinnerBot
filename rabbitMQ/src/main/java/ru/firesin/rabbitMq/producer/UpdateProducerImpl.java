package ru.firesin.rabbitMq.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@AllArgsConstructor
@Service
public class UpdateProducerImpl implements UpdateProducer {

    private final RabbitTemplate rabbitTemplate;
    @Override
    public void produce(String rabbitQueue, Update update) {
      rabbitTemplate.convertAndSend(rabbitQueue, update);
    }

    @Override
    public void produce(String rabbitQueue, SendMessage sndMsg) {
        rabbitTemplate.convertAndSend(rabbitQueue, sndMsg);
    }
}

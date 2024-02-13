package ru.firesin.gigachat.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.gigachat.service.ConsumerService;
import ru.firesin.gigachat.service.GigaChatService;
import ru.firesin.rabbitMq.producer.UpdateProducer;

import static ru.firesin.rabbitMq.Queue.RabbitQueue.CHAT_MESSAGE_ANSWER;
import static ru.firesin.rabbitMq.Queue.RabbitQueue.CHAT_MESSAGE_UPDATE;

/**
 * Author:    Artur Kubantsev
 * Date:      03.02.2024
 */

@Service
@AllArgsConstructor
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    private final GigaChatService gigaChatService;
    private final UpdateProducer updateProducer;
    @Override
    @RabbitListener(queues = CHAT_MESSAGE_UPDATE)
    public void consumeGigaChatMessage(Update update) {
        log.debug("Текстовое сообщение получено в GigaChat получено");
        String result;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChat().getId());
        sendMessage.setMessageThreadId(update.getMessage().getMessageThreadId());
        result = gigaChatService.getAnswer(update.getMessage().getText());
        sendMessage.setText(result);
        updateProducer.produce(CHAT_MESSAGE_ANSWER, sendMessage);
    }
}

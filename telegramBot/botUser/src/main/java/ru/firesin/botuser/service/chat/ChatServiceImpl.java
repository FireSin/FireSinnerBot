package ru.firesin.botuser.service.chat;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.messages.SendMessageService;
import ru.firesin.rabbitMq.producer.UpdateProducer;

import static ru.firesin.rabbitMq.Queue.RabbitQueue.CHAT_MESSAGE_ANSWER;
import static ru.firesin.rabbitMq.Queue.RabbitQueue.CHAT_MESSAGE_UPDATE;

/**
 * Author:    Artur Kubantsev
 * Date:      03.02.2024
 */

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final SendMessageService sendMsg;
    private final UpdateProducer updateProducer;

    @Override
    public void processChat(Update update) {
        updateProducer.produce(CHAT_MESSAGE_UPDATE, update);
    }


    @RabbitListener(queues = CHAT_MESSAGE_ANSWER)
    private void consumeChat(SendMessage sendMessage) {
        sendMsg.sendMessage(sendMessage);
    }
}

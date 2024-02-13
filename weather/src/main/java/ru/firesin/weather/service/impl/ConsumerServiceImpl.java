package ru.firesin.weather.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.rabbitMq.producer.UpdateProducer;
import ru.firesin.weather.service.ConsumerService;
import ru.firesin.weather.service.WeatherService;

import static ru.firesin.rabbitMq.Queue.RabbitQueue.WEATHER_MESSAGE_ANSWER;
import static ru.firesin.rabbitMq.Queue.RabbitQueue.WEATHER_MESSAGE_UPDATE;


/**
 * Author:    firesin
 * Date:      18.10.2023
 */
@Service
@Slf4j
@AllArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final UpdateProducer producerService;
    private final WeatherService weatherService;

    @Override
    @RabbitListener(queues = WEATHER_MESSAGE_UPDATE)
    public void consumeWeatherMessage(Update update) {
        log.debug("Текстовое сообщение в погоде получено");
        String result;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChat().getId());
        sendMessage.setMessageThreadId(update.getMessage().getMessageThreadId());
        result = weatherService.getWeather(update.getMessage().getText());
        sendMessage.setText(result);
        producerService.produce(WEATHER_MESSAGE_ANSWER, sendMessage);
    }
}

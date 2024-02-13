package ru.firesin.weather.service;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      18.10.2023
 */
public interface ConsumerService {

    void consumeWeatherMessage(Update update);
}

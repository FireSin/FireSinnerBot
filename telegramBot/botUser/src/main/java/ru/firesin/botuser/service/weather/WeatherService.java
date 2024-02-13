package ru.firesin.botuser.service.weather;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      29.10.2023
 */

public interface WeatherService {

    void processCommand(Update update);

    void processCity(Update update);

    void processAnotherCity(Update update);
}

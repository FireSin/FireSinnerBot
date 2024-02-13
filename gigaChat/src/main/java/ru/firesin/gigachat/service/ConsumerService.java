package ru.firesin.gigachat.service;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    Artur Kubantsev
 * Date:      03.02.2024
 */
public interface ConsumerService {
    void consumeGigaChatMessage(Update update);
}

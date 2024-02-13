package ru.firesin.botuser.service.chat;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    Artur Kubantsev
 * Date:      03.02.2024
 */
public interface ChatService {
    void processChat(Update update);
}

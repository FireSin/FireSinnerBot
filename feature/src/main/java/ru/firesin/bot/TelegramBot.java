package ru.firesin.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Author:    Artur Kubantsev
 * Date:      06.02.2024
 */
public interface TelegramBot {
    void sendAnswer(SendMessage msg);
}

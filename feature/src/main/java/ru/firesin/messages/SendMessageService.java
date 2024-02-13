package ru.firesin.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.bot.TelegramBot;


/**
 * Author:    firesin
 * Date:      28.10.2023
 */
public interface SendMessageService {

    void registerBot(TelegramBot telegramBot);
    void sendMessage(Update update, String text);
    void sendMessage(Message message, String text);
    void sendMessage(SendMessage msg, String text);

    void sendMessage(SendMessage msg);
}

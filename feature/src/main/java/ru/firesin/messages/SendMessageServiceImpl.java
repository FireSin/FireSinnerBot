package ru.firesin.messages;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.bot.TelegramBot;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */

@Service
@NoArgsConstructor
public class SendMessageServiceImpl implements SendMessageService {

    private TelegramBot telegramBot;

    @Override
    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(Update update, String text) {
        SendMessage sendMsg = new SendMessage();
        if (update.hasMessage()) {
            sendMsg.setChatId(update.getMessage().getChatId());
        } else if (update.hasCallbackQuery()) {
            sendMsg.setChatId(update.getCallbackQuery().getFrom().getId());
        } else if (update.hasEditedMessage()) {
            sendMsg.setChatId(update.getEditedMessage().getChatId());
        }
        sendMsg.setText(text);
        telegramBot.sendAnswer(sendMsg);
    }

    @Override
    public void sendMessage(Message message, String text) {
        SendMessage sendMsg = new SendMessage();
        sendMsg.setChatId(message.getChatId());
        sendMsg.setText(text);
        telegramBot.sendAnswer(sendMsg);
    }

    @Override
    public void sendMessage(SendMessage msg) {
        telegramBot.sendAnswer(msg);
    }

    @Override
    public void sendMessage(SendMessage sendMessage, String s) {
        sendMessage.setText(s);
        telegramBot.sendAnswer(sendMessage);
    }
}

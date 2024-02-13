package ru.firesin.admin.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.bot.TelegramBot;
import ru.firesin.messages.SendMessageService;
import ru.firesin.users.UserService;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
@AllArgsConstructor
public class UpdateProcessor {

    private final UserService userService;
    private final SendMessageService sendMsg;

    public void registerBot(TelegramBot telegramBot) {
        this.sendMsg.registerBot(telegramBot);
    }


    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Полученный Update null");
            return;
        }
        if (update.getMessage() != null) {
            distributeMessageByType(update);
        } else {
            log.error("Не поддерживаемый формат сообщения " + update);
        }
    }

    private void distributeMessageByType(Update update) {
        if (update.getMessage().hasContact()) {
            userService.addUser(update.getMessage().getContact());
        } else if (update.getMessage().hasText()) {
            userService.addUser(update.getMessage().getFrom());
        }
    }
}






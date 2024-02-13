package ru.firesin.botuser.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.bot.TelegramBot;
import ru.firesin.botuser.service.chat.ChatService;
import ru.firesin.botuser.service.command.CommandService;
import ru.firesin.botuser.service.weather.WeatherService;
import ru.firesin.messages.SendMessageService;
import ru.firesin.security.UserSecurity;
import ru.firesin.users.UserService;

import static ru.firesin.users.enums.UserState.CITY_WAIT;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
@AllArgsConstructor
public class UpdateProcessor {

    private final SendMessageService sendMsg;
    private final UserSecurity userSecurity;
    private final CommandService commandService;
    private final WeatherService weatherService;
    private final ChatService chatService;
    private final UserService userService;

    public void registerBot(TelegramBot telegramBot) {
        this.sendMsg.registerBot(telegramBot);
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Полученный Update null");
            return;
        }
        if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        } else if (update.hasMessage()) {
            processMessage(update);
        } else {
            sendMsg.sendMessage(update, "Какую-то неведому фигню мне прислали. Я вас не понимаю.");
        }
    }

    private void processMessage(Update update) {
        var user = update.getMessage().getFrom();
        if (!userSecurity.hasUser(user)) {
            sendMsg.sendMessage(update, "❌ Нет доступа ❌\n Для получения доступа пишите @SinnerFire");
        } else if (commandService.itsCommand(update)) {
            commandService.processCommand(update);
        } else if (update.getMessage().hasText()) {
            if (userService.getState(update.getMessage().getFrom()).equals(CITY_WAIT)) {
                weatherService.processCity(update);
            } else {
                chatService.processChat(update);
            }
        } else {
            sendMsg.sendMessage(update, "Неподдерживаемый формат сообщения");
        }
    }

    private void processCallbackQuery(Update update) {
        var user = update.getCallbackQuery().getFrom();
        if (!userSecurity.hasUser(user)) {
            sendMsg.sendMessage(update, "❌ Нет доступа ❌\n Для получения доступа пишите @SinnerFire");
        }
        if (update.getCallbackQuery().getData().equals("/setCity")) {
            weatherService.processAnotherCity(update);
        } else {
            sendMsg.sendMessage(update, "Кто-то что-то не туда тыкнул");
        }
    }
}






package ru.firesin.botuser.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.firesin.bot.TelegramBot;
import ru.firesin.botuser.utils.BotCommands;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
public class TelegramBotImpl extends TelegramLongPollingBot implements TelegramBot {
    private final String botUsername;
    private final UpdateProcessor updateProcessor;

    public TelegramBotImpl(@Value("${bot.name}") String botUsername,
                       @Value("${bot.token}") String botToken,
                       UpdateProcessor updateProcessor) {
        super(botToken);
        this.botUsername = botUsername;
        this.updateProcessor = updateProcessor;


        log.debug("Бот " + botUsername + " создан");
    }

    @PostConstruct
    private void registerBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
        updateProcessor.registerBot(this);

        List<BotCommand> commands = new ArrayList<>();
        List<BotCommands> userCommands = BotCommands.getUserCommands();

        for (BotCommands botCommand : userCommands) {
            commands.add(new BotCommand(botCommand.getCommand(), botCommand.getDescription()));
        }
        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Ошибка создания меню: " + e.getMessage());
            throw new RuntimeException(e);
        }
        log.debug("Меню бота создано");
    }


    public void sendAnswer(SendMessage msg){
        try {
            execute(msg);
            log.debug("Ответ отправлен");
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }


    @Override
    public void onUpdateReceived(Update update) {
        updateProcessor.processUpdate(update);
    }
}

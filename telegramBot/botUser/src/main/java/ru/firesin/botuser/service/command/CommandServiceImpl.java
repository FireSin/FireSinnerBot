package ru.firesin.botuser.service.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.botuser.service.weather.WeatherService;
import ru.firesin.botuser.utils.BotCommands;
import ru.firesin.messages.SendMessageService;
import ru.firesin.users.UserService;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import static ru.firesin.botuser.utils.BotCommands.WEATHER;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@Service
public class CommandServiceImpl implements CommandService {

    private final Map<BotCommands, Consumer<Update>> commandHandlers = new EnumMap<>(BotCommands.class);
    private final UserService userService;
    private final SendMessageService sendMsg;


    public CommandServiceImpl(UserService userService,
                              SendMessageService sendMsg,
                              WeatherService weatherService) {
        this.userService = userService;
        this.sendMsg = sendMsg;

        commandHandlers.put(BotCommands.START, this::processStartCommand);
        commandHandlers.put(WEATHER, weatherService::processCommand);
        commandHandlers.put(BotCommands.HELP, this::processHelpCommand);
    }

    @Override
    public Boolean itsCommand(Update update){
        if (update.getMessage() == null || !update.getMessage().hasText()) {
            return false;
        }
        return Arrays.stream(BotCommands.values())
                .map(BotCommands::getCommand)
                .anyMatch(update.getMessage().getText()::startsWith);
    }

    @Override
    public void processCommand(Update update) {
        String commandText = update.getMessage().getText();

        commandHandlers.keySet()
                .stream()
                .filter(cmd -> commandText.startsWith(cmd.getCommand()))
                .findFirst()
                .ifPresent(cmd -> commandHandlers.get(cmd).accept(update));

    }

    private void processStartCommand(Update update) {
        userService.addUser(update.getMessage().getFrom());
        sendMsg.sendMessage(update, """
                Начинаем начинать!
                Чтобы общаться с ботом просто пишите, что хотите знать.
                Чтобы узнать погоду отправте команду /weather, или тыкнете ее в меню.
                """);
    }

    private void processHelpCommand(Update update) {
        sendMsg.sendMessage(update, """
            Чтобы общаться с ботом просто пишите, что хотите знать.
            Чтобы узнать погоду отправте команду /weather, или тыкнете ее в меню.
            После получения погоды можете продолжать общаться в чате.
            """);
    }
}

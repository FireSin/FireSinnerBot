package ru.firesin.admin.service.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.admin.utils.BotCommands;
import ru.firesin.messages.SendMessageService;
import ru.firesin.users.UserService;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@Service
public class CommandServiceImpl implements CommandService {

    private final Map<BotCommands, Consumer<Update>> commandHandlers = new EnumMap<>(BotCommands.class);
    private final UserService userService;
    private final SendMessageService sndMsg;

    public CommandServiceImpl(UserService userService, SendMessageService sndMsg) {
        this.userService = userService;
        this.sndMsg = sndMsg;
        commandHandlers.put(BotCommands.START, this::processStartCommand);
        commandHandlers.put(BotCommands.LS_USER, this::processLsCommand);
        commandHandlers.put(BotCommands.RM_USER, this::processRemoveUserCommand);
        commandHandlers.put(BotCommands.ADD_USER, this::processAddUserCommand);
    }

    @Override
    public Boolean itsCommand(Update update){
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

    private void processLsCommand(Update message) {
        sndMsg.sendMessage(message, "ls");
    }

    private void processAddUserCommand(Update message) {
        sndMsg.sendMessage(message, "Add");
    }

    private void processStartCommand(Update message) {
        var user = userService.findUser(message.getMessage().getFrom());
        user.setUsername(message.getMessage().getFrom().getUserName());
        userService.saveUser(user);
        sndMsg.sendMessage(message, "Добро пожаловать!");
    }

    private void processRemoveUserCommand(Update message) {
        sndMsg.sendMessage(message, "rm");
    }

    private void errorMessage(Update msg) {
        SendMessage toSend = new SendMessage();
        toSend.setChatId(msg.getMessage().getChatId());
        toSend.setText("Ошибка обработки команды: ");
        sndMsg.sendMessage(toSend);
    }
}

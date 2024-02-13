package ru.firesin.botuser.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@Getter
public enum BotCommands {
    START("/start", "Инициализация бота"),
    WEATHER("/weather", "Переключение на канал погоды"),
    HELP("/help", "Тыкни для информации");

    private final String command;
    private final String description;

    BotCommands(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static List<BotCommands> getUserCommands() {
        List<BotCommands> commands = new ArrayList<>();
        commands.add(START);
        commands.add(WEATHER);
        commands.add(HELP);
        return commands;
    }

}

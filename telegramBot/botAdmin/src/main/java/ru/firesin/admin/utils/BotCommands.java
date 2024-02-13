package ru.firesin.admin.utils;

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
    ADD_USER("/add_user", "Добавить пользователя"),
    RM_USER("/rm_user", "Удалить пользователя"),
    LS_USER("/ls", "Вывод пользователей");

    private final String command;
    private final String description;

    BotCommands(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static List<BotCommands> getUserCommands() {
        List<BotCommands> commands = new ArrayList<>();
        commands.add(START);
        commands.add(ADD_USER);
        commands.add(RM_USER);
        commands.add(LS_USER);
        return commands;
    }

}

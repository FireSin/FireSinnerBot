package ru.firesin.botuser.service.command;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */
public interface CommandService {
    Boolean itsCommand(Update update);

    void processCommand(Update update);
}

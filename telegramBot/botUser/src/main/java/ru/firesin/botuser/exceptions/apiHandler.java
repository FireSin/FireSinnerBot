package ru.firesin.botuser.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.firesin.bot.TelegramBot;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@ControllerAdvice
@AllArgsConstructor
public class apiHandler {

    private TelegramBot telegramBot;

    @ExceptionHandler(MuExp.class)
    public void myApiHandler(MuExp e) {
        var tmp = new SendMessage();
        tmp.setChatId(e.getId());
        tmp.setText(e.getTxt());
        telegramBot.sendAnswer(tmp);
    }
}

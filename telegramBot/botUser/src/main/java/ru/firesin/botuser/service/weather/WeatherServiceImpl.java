package ru.firesin.botuser.service.weather;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.firesin.messages.SendMessageService;
import ru.firesin.rabbitMq.producer.UpdateProducer;
import ru.firesin.users.UserService;

import java.util.ArrayList;
import java.util.List;

import static ru.firesin.rabbitMq.Queue.RabbitQueue.WEATHER_MESSAGE_ANSWER;
import static ru.firesin.rabbitMq.Queue.RabbitQueue.WEATHER_MESSAGE_UPDATE;
import static ru.firesin.users.enums.UserState.CHAT;
import static ru.firesin.users.enums.UserState.CITY_WAIT;

/**
 * Author:    firesin
 * Date:      29.10.2023
 */

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final UserService userService;
    private final SendMessageService sendMsg;
    private final UpdateProducer updateProducer;

    @Override
    public void processCommand(Update update) {
        var user = userService.findUser(update.getMessage().getFrom());
        String city = null;
        var message = new Message();

        if (update.getMessage().getText().split(" ").length > 1) {
            city = update.getMessage().getText().split(" ")[1];
        } else if (user.getCity() == null || user.getUserState().equals(CITY_WAIT)) {
            userService.setState(user, CITY_WAIT);
            sendMsg.sendMessage(update, "Напишите город");
        } else {
            city = user.getCity();
        }

        if (city != null) {
            message.setText(city);
            message.setChat(update.getMessage().getChat());
            message.setReplyMarkup(anotherCityKeyboard());
            update.setMessage(message);
            updateProducer.produce(WEATHER_MESSAGE_UPDATE, update);
        }
    }

    @Override
    public void processCity(Update update) {
        updateProducer.produce(WEATHER_MESSAGE_UPDATE, update);
    }

    public void processAnotherCity(Update update) {
        userService.setState(update.getCallbackQuery().getFrom(), CITY_WAIT);
        sendMsg.sendMessage(update, "Напишите город");
    }

    private InlineKeyboardMarkup anotherCityKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        InlineKeyboardButton buttonAnotherCity = new InlineKeyboardButton("Выбрать другой город");
        buttonAnotherCity.setCallbackData("/setCity");

        buttons.add(buttonAnotherCity);
        keyboard.add(buttons);

        markup.setKeyboard(keyboard);

        return markup;
    }

    @RabbitListener(queues = WEATHER_MESSAGE_ANSWER)
    private void consumeWeather(SendMessage sendMessage) {
        var text = sendMessage.getText();

        if (sendMessage.getText().equals("502")) {
            sendMessage.setText("Сервис недоступен, попробуйте позже.");
            sendMsg.sendMessage(sendMessage);
        } else if (text.equals("404")) {
            sendMessage.setReplyMarkup(anotherCityKeyboard());
            sendMsg.sendMessage(sendMessage, "Такой город не найден");
        } else {
            userService.setCity(Long.valueOf(sendMessage.getChatId()),
                    sendMessage.getText().split("\n")[0].substring(16));
            sendMessage.setReplyMarkup(anotherCityKeyboard());
            sendMsg.sendMessage(sendMessage);
        }
        userService.setState(Long.valueOf(sendMessage.getChatId()), CHAT);
    }
}

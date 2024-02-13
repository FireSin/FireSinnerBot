package ru.firesin.users;

import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.users.entity.BotUser;
import ru.firesin.users.enums.UserRole;
import ru.firesin.users.enums.UserState;

import java.util.List;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */
public interface UserService {

    BotUser addUser(User botUser);

    void deleteUser(User botUser);

    UserRole getRole(User botUser);

    UserState getState(User botUser);

    void addUser(Contact contact);

    void setState(User botUser, UserState userState);

    void setState(BotUser botUser, UserState userState);

    void setState(Long id, UserState userState);

    BotUser findUser(User botUser);
    BotUser findUser(Long id);

    List<BotUser> getAllUsers();

    void saveUser(BotUser botUser);

    void setCity(Update update, String text);
    void setCity(Long id, String text);
}

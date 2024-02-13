package ru.firesin.users.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.users.UserService;
import ru.firesin.users.entity.BotUser;
import ru.firesin.users.enums.UserRole;
import ru.firesin.users.enums.UserState;
import ru.firesin.users.repository.BotUserDAO;

import java.util.List;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final BotUserDAO botUserDAO;

    @Override
    public BotUser addUser(User botUser) {
        return botUserDAO.save(BotUser.builder()
                .id(botUser.getId())
                .firstname(botUser.getFirstName())
                .lastname(botUser.getLastName())
                .username(botUser.getUserName())
                .userRole(UserRole.USER)
                .userState(UserState.CHAT)
                .build());
    }

    @Override
    public void deleteUser(User botUser) {
        botUserDAO.deleteBotUserById(botUser.getId());
    }

    public UserRole getRole(User botUser) {
        return botUserDAO.findUserRoleById(botUser.getId());
    }

    @Override
    public UserState getState(User botUser) {
        return botUserDAO.findUserStateById(botUser.getId());
    }

    @Override
    public void addUser(Contact contact) {
        BotUser user = botUserDAO.findBotUserById(contact.getUserId());

        if (user == null) {
            botUserDAO.save(BotUser.builder()
                    .id(contact.getUserId())
                    .firstname(contact.getFirstName())
                    .lastname(contact.getLastName())
                    .userRole(UserRole.USER)
                    .userState(UserState.CHAT)
                    .build());
        }
    }

    @Override
    public void setState(User botUser, UserState userState) {
        BotUser user = botUserDAO.findBotUserById(botUser.getId());
        user.setUserState(userState);
        botUserDAO.save(user);
    }

    @Override
    public void setState(BotUser botUser, UserState userState) {
        botUser.setUserState(userState);
        botUserDAO.save(botUser);
    }

    @Override
    public void setState(Long id, UserState userState) {
        BotUser user = botUserDAO.findBotUserById(id);
        user.setUserState(userState);
        botUserDAO.save(user);
    }

    @Override
    public BotUser findUser(User botUser) {
        return botUserDAO.findBotUserById(botUser.getId());
    }

    @Override
    public BotUser findUser(Long id) {
        return botUserDAO.findBotUserById(id);
    }

    @Override
    public List<BotUser> getAllUsers() {
        return botUserDAO.findAll();
    }

    @Override
    public void saveUser(BotUser botUser) {
        botUserDAO.save(botUser);
    }

    @Override
    public void setCity(Update update, String text) {
        BotUser user = botUserDAO.findBotUserById(update.getMessage().getChatId());
        user.setCity(text);
        botUserDAO.save(user);
    }

    @Override
    public void setCity(Long id, String text) {
        BotUser user = botUserDAO.findBotUserById(id);
        user.setCity(text);
        botUserDAO.save(user);
    }
}

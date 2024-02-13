package ru.firesin.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.users.UserService;
import ru.firesin.users.enums.UserRole;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */

@Component
@AllArgsConstructor
public class UserSecurityImpl implements UserSecurity {

    private final UserService userService;
    @Override
    public boolean hasUser(User user) {
        return userService.findUser(user) != null;
    }

    @Override
    public boolean isAdmin(User user) {
        var us = userService.findUser(user);
        return us != null && us.getUserRole().equals(UserRole.ADMIN);
    }
}

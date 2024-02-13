package ru.firesin.security;

import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */
public interface UserSecurity {

    boolean hasUser(User user);

    boolean isAdmin(User user);
}

package ru.firesin.users.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.firesin.users.enums.UserRole;
import ru.firesin.users.enums.UserState;


/**
 * Author:    firesin
 * Date:      23.10.2023
 */

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "bot_user")
public class BotUser {
    @Id
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String City;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Enumerated(EnumType.STRING)
    private UserState userState;
}


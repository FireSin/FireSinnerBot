package ru.firesin.gigachat.model;

import lombok.Data;

/**
 * Author:    Artur Kubantsev
 * Date:      03.02.2024
 */

@Data
public class TokenResponse {
    private String access_token;
    private long expires_at;
}

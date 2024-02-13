package ru.firesin.gigachat.model;

import lombok.Data;

/**
 * Author:    Artur Kubantsev
 * Date:      04.02.2024
 */

@Data
public class Message {
    private String role;
    private String content;
}
